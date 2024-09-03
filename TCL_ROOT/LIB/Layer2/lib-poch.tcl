#!/bin/sh
################################################################################   
#
#  File Name		: lib-poch.tcl
#
#  Description       	:
#         This TCL contains functions to configure port channel
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        22/5/06      Scott Zhang        Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: _ntgrCreatePortChannel
#
#  Description    : This function is called to create a port channel/LACP 
#         
#  Usage          : _ntgrCreatePortChannel <switch_name> <channel_name>
# 
#*******************************************************************************
proc _ntgrCreatePortChannel {switch_name channel_name} {

    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
	
    _ntgrConfigLAG $connection_id $channel_name
    
    # get the logical interface's name
	set ifName [ _ntgrGetChannelLogicalIfName $connection_id $channel_name]
	# Save the logical interface name to the global struct.
	_setChannelLogicalIfName $switch_name $channel_name $ifName
	
    # add by jim.xie begin
	# should enable admin mode for lag first
	set ifName [lindex $ifName 0]
    if {![regexp -nocase {lag \d+} $ifName]} {
        set ifName $channel_name
    }
    exp_send -i $connection_id "configure\r"
    exp_sleep 1
    exp_send -i $connection_id "interface $ifName\r"
    exp_sleep 1
	exp_send -i $connection_id "adminmode\r"
    exp_sleep 1
	exp_send -i $connection_id "no shutdown\r"
    exp_sleep 1
    exp_send -i $connection_id "exit\r"
    exp_sleep 1
	exp_send -i $connection_id "exit\r"
    exp_sleep 1
	# add by jim.xie end
	
    set members [getPortChannelMember $channel_name]
    foreach member $members {
        if { $switch_name == [lindex $member 0] } {
            set portlist [lindex $member 1]
            _ntgrAddPortToChannel $connection_id $channel_name $portlist
            break;
        }
    }

	Netgear_disconnect_switch $switch_name
	return $ifName
}

#*******************************************************************************
#
#  Function Name	: _ntgrConfigLAG
#
#  Description    : This function is called to create a port channel/LACP 
#         
#  Usage          : _ntgrConfigLAG <switch_name> <channel_name>
# 
#*******************************************************************************
proc _ntgrConfigLAG {connection_id channel_name} {

    #getting softerware version
    set ver [_ntgrGetVersionReleasebyConnectionID $connection_id]
    if {$ver >= 9} {return}
    
    exp_send -i $connection_id "configure\r"
    exp_sleep 1
    exp_send -i $connection_id "port-channel $channel_name\r"
    exp_sleep 1
    exp_send -i $connection_id "exit\r"
    exp_sleep 1
}

#*******************************************************************************
#
#  Function Name	: _ntgrDeletePortChannel
#
#  Description    : This function is called to delete a port channel. 
#         
#  Usage          : _ntgrDeletePortChannel <switch_name> <channel_name>
# 
#*******************************************************************************
proc _ntgrDeletePortChannel {switch_name channel_name} {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
    
    set ifName [_ntgrGetChannelLogicalIfName $connection_id $channel_name]
    ##set ifName [getChannelLogicalIfName $switch_name $channel_name]
    set ifName [lindex $ifName 0]
    
    if {[regexp -nocase {lag (\d+)} $ifName dummy lagid]} {        
        set members [getPortChannelMember $channel_name]
        foreach member $members {
            if { $switch_name == [lindex $member 0] } {
                set portlist [lindex $member 1]
                
                exp_send -i $connection_id "configure\r"
                exp_sleep 1
                foreach port $portlist {
                  exp_send -i $connection_id "interface $port\r"
                  exp_sleep 1
                  exp_send -i $connection_id "deleteport $ifName\r"
                  exp_sleep 1
                  exp_send -i $connection_id "exit\r"
                  exp_sleep 1
                }                
                break;
            }
        }
    } else {
      set ifName $channel_name
      
      exp_send -i $connection_id "configure\r"
      exp_sleep 1
      exp_send -i $connection_id "no port-channel $ifName\r"
      exp_sleep 1
      exp_send -i $connection_id "exit\r"
      exp_sleep 1
    }

	Netgear_disconnect_switch $switch_name
	
	# set the logical interface's name to ""
	_setChannelLogicalIfName $switch_name $channel_name ""
}

#*******************************************************************************
#
#  Function Name  : _ntgrDeleteAllPortChannels
#
#  Description    : This function is called to delete all configured port channels
#                   on the given switch.
#
#  Usage          : _ntgrDeleteAllPortChannels <switch_name>
#
#  Note           : Modified by andy.xie for 10 version(H2 project) information changed.
#*******************************************************************************
proc _ntgrDeleteAllPortChannels { switch_name } {
    
    set dut_software_version [_ntgrGetSoftwareVersion $switch_name]
#	  puts "$dut_software_version"
    set ver [string trim $dut_software_version]
    regexp {^\d+} $ver ver_first
    ## ---- add branch for 10 version, modified by andy.xie ----##
    if {$ver_first >= 10} {
      
      _ntgrDeleteAllChannelMember1 $switch_name
      
    } elseif {$ver_first == 9} {
      
      _ntgrDeleteAllChannelMember $switch_name
      
    } else {  
	  
	      set cnn_id [_get_switch_handle $switch_name]
        set bCnn 1
        if {[string first "exp" $cnn_id] == -1} {
            Netgear_connect_switch $switch_name
            set cnn_id [_get_switch_handle $switch_name]
            set bCnn 0
        }
        Netgear_log_file "lib-poch.tcl" "Deleting all configured port channels on switch $switch_name"
    
        exp_send -i $cnn_id "configure\r"
        exp_sleep 1
        exp_send -i $cnn_id "no port-channel all\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
        if {$bCnn == 0} {
            Netgear_disconnect_switch $switch_name
        }  
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrDeleteAllChannelMember
#
#  Description    : This function is called to delete port channels all member
#                   on the given switch.
#
#  Usage          : _ntgrDeleteAllChannelMember <switch_name>
#
#*******************************************************************************
proc _ntgrDeleteAllChannelMember { switch_name } {
	  
    set cmd "show port-channel all"
    set ret ""
    CALCheckExpect $switch_name $cmd ret
#    puts "!!$Retstr!!"
   set tmplist [split $ret "\n"]
   foreach item $tmplist {
       set tmp1 [lindex $item 6]
       set tmp2 [lindex $item 0]
       set tmp3 [lindex $item 1]
       if { (![string is space $tmp1]) || [regexp -- {\d/\d} $tmp2]} {
      	   if {[string is digit $tmp3]} {
  		        set i 0
          		set lag_id $tmp3
          		set lag($lag_id,$i) $tmp1
  	       } 
  	      if {[regexp -- {\d/\d} $tmp2]} {
          		set lag($lag_id,[expr $i+1]) $tmp2
          		incr i
  	      }
       }
   }
  
    if {![info exists lag]} {
        return
    } else {
        
    	  set cnn_id [_get_switch_handle $switch_name]
        set bCnn 1
        if {[string first "exp" $cnn_id] == -1} {
            Netgear_connect_switch $switch_name
            set cnn_id [_get_switch_handle $switch_name]
            set bCnn 0
        }
        exp_send -i $cnn_id "configure\r"
        exp_sleep 1
        foreach {item} [lsort -di [array names lag]] {
            set lag_num [lindex [split $item ,] 0]
            exp_send -i $cnn_id "interface $lag($item)\r"
            exp_sleep 1
            exp_send -i $cnn_id "deleteport lag $lag_num\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        } 
        if {$bCnn == 0} {
            Netgear_disconnect_switch $switch_name
        }  
    }	
}

#11*******************************************************************************
#
#  Function Name  : _ntgrDeleteAllChannelMember1
#
#  Description    : This function is called to delete port channels all member
#                   on the switch which version is 10 or higher.
#
#  Usage          : _ntgrDeleteAllChannelMember1 <switch_name>
#
#  Note           : Addedy by andy xie for > 10 version, 2012-05-30
#*******************************************************************************
proc _ntgrDeleteAllChannelMember1 { switch_name } {
	  
    set cmd "show port-channel all"
    set ret ""
    CALCheckExpect $switch_name $cmd ret
#    puts "!!$Retstr!!"
   set tmplist [split $ret "\n"]
   foreach item $tmplist {
       set tmp1 [lindex $item 7]
       set tmp2 [lindex $item 0]
       set tmp3 [lindex $item 1]
       if { (![string is space $tmp1]) || [regexp -- {\d/\d} $tmp2]} {
      	   if {[string is digit $tmp3]} {
  		        set i 0
          		set lag_id $tmp3
          		set lag($lag_id,$i) $tmp1
  	       } 
  	      if {[regexp -- {\d/\d} $tmp2]} {
          		set lag($lag_id,[expr $i+1]) $tmp2
          		incr i
  	      }
       }
   }
  
    if {![info exists lag]} {
        return
    } else {
        
    	  set cnn_id [_get_switch_handle $switch_name]
        set bCnn 1
        if {[string first "exp" $cnn_id] == -1} {
            Netgear_connect_switch $switch_name
            set cnn_id [_get_switch_handle $switch_name]
            set bCnn 0
        }
        exp_send -i $cnn_id "configure\r"
        exp_sleep 1
        foreach {item} [lsort -di [array names lag]] {
            set lag_num [lindex [split $item ,] 0]
            exp_send -i $cnn_id "interface $lag($item)\r"
            exp_sleep 1
            exp_send -i $cnn_id "deleteport lag $lag_num\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        } 
        if {$bCnn == 0} {
            Netgear_disconnect_switch $switch_name
        }  
    }	
}
#*******************************************************************************
#
#  Function Name	: _ntgrGetChannelLogicalIfName
#
#  Description    : This function is called to get the logical interface name of
#                   a port channel/LACP. To get the logical interface name, send
#                   'show port-channel' command and parse the result.
#         
#  Usage          : _ntgrGetChannelLogicalIfName <connection_id> <channel_name>
# 
#*******************************************************************************
proc _ntgrGetChannelLogicalIfName {connection_id channel_name} {

    #getting softerware version
    set ver [_ntgrGetVersionReleasebyConnectionID $connection_id]
    if {$ver >= 9} {
        set ifName "\{lag [getPortChannelID $channel_name]\}"
        return $ifName
    }
    
    exp_send -i $connection_id "show port-channel brief\r"
    exp_sleep 1
    expect {
        -i $connection_id -re "------" {}
        timeout {
            exp_send -i $connection_id "\r"
            sleep 1
            exp_send -i $connection_id "show port-channel brief\r"
            sleep 1
            exp_continue
        }
    }
    set output ""
    expect {
        -i $connection_id -re "#" { set output $output$expect_out(buffer) }
        -i $connection_id -re "More" {
            set output $output$expect_out(buffer)
            exp_send -i $connection_id " "
            exp_continue
        }
        timeout exp_continue
    }
    exp_sleep 1

    set ifName ""
#    set last [string first "$channel_name" $output]
#    set regPattern "(\[^\r\n\t \]+)(\[ \t\]+)(\[0-9\]+)(\[ \t\]+)$"
#     set regPattern "(\[^\r\n\t \]+)(\[ \t\]+)$"
    set tmpList [split $output "\n"]
    foreach iList $tmpList {
      if {[regexp -- $channel_name $iList]} {
        set ifName [lindex $iList 0]
        break
      }
    }
    
#    set strRegExp [string range $output 0 [expr $last - 1]]
#    regexp -all -- {\D+} $strRegExp match ifName

    global NTGR_LOG_DEBUG
#    if {$ifName == ""} {
#        NtgrDumpLog $NTGR_LOG_DEBUG "lib-poch.tcl" "Get empty logical interface name: LAG=$channel_name, output=$output, last=$last, strRegExp=$strRegExp"
#        set ifName [ _ntgrGetChannelLogicalIfName $connection_id $channel_name ]
#    }

    return $ifName
}

#*******************************************************************************
#
#  Function Name	: _ntgrAddPortToChannel
#
#  Description    : This function is called to add one/more port(s) to
#                   a given port channel/LAG.
#         
#  Usage          : _ntgrAddPortToChannel <connection_id> <channel_name> <portlist> 
# 
#*******************************************************************************
proc _ntgrAddPortToChannel {connection_id channel_name portlist} {
    set ifName [_ntgrGetChannelLogicalIfName $connection_id $channel_name]
    set ifName [lindex $ifName 0]
    if {![regexp -nocase {lag \d+} $ifName]} {
        set ifName $channel_name
    }
         
    exp_send -i $connection_id "configure\r"
    exp_sleep 1
    foreach port $portlist {
        exp_send -i $connection_id "interface $port\r"
        after 500
        exp_send -i $connection_id "addport $ifName\r"
        after 500
        exp_send -i $connection_id "exit\r"
        after 500
    }
    exp_send -i $connection_id "exit\r"
    exp_sleep 1
}

#*******************************************************************************
#
#  Function Name	: _ntgrDelPortFromChannel
#
#  Description    : This function is called to delete one/more port(s) from
#                   a given port channel/LAG.
#         
#  Usage          : _ntgrDelPortFromChannel <switch_name> <channel_name> <portlist>
# 
#*******************************************************************************
proc _ntgrDelPortFromChannel {switch_name channel_name portlist} {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
    
    ##set ifName [_ntgrGetChannelLogicalIfName $connection_id $channel_name]
    set ifName [getChannelLogicalIfName $switch_name $channel_name]
    set ifName [lindex $ifName 0]
    if {![regexp -nocase {lag \d+} $ifName]} {
        set ifName $channel_name
    }

    exp_send -i $connection_id "configure\r"
    exp_sleep 1
    foreach port $portlist {
        exp_send -i $connection_id "interface $port\r"
        exp_sleep 1
        exp_send -i $connection_id "deleteport $ifName\r"
        exp_sleep 1
        exp_send -i $connection_id "exit\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name	
}

#*******************************************************************************
#
#  Function Name	: _ntgrShutPortChannel
#
#  Description    : This function is called to shutdown a given port channel/LAG.
#         
#  Usage          : _ntgrShutPortChannel <switch_name> <channel_name>
# 
#*******************************************************************************
proc _ntgrShutPortChannel {switch_name channel_name} {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
	
    ##set ifName [_ntgrGetChannelLogicalIfName $connection_id $channel_name]
    set ifName [getChannelLogicalIfName $switch_name $channel_name]
    set ifName [lindex $ifName 0]
    
		exp_send -i $connection_id "configure\r"
		exp_sleep 1
		exp_send -i $connection_id "interface $ifName\r"
		exp_sleep 1
		exp_send -i $connection_id "shutdown\r"
		exp_sleep 1
		exp_send -i $connection_id "exit\r"
		exp_sleep 1

		Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrNoShutPortChannel
#
#  Description    : This function is called to no shutdown a given port channel.
#         
#  Usage          : _ntgrNoShutPortChannel <switch_name> <channel_name>
# 
#*******************************************************************************
proc _ntgrNoShutPortChannel {switch_name channel_name} {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
	
		##set ifName [_ntgrGetChannelLogicalIfName $connection_id $channel_name]
		set ifName [getChannelLogicalIfName $switch_name $channel_name]
		set ifName [lindex $ifName 0]
		
		exp_send -i $connection_id "configure\r"
		exp_sleep 1
		exp_send -i $connection_id "interface $ifName\r"
		exp_sleep 1
		exp_send -i $connection_id "no shutdown\r"
		exp_sleep 1
		exp_send -i $connection_id "exit\r"
		exp_sleep 1
		
		Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDelPortChannelPerSwitch
#
#  Description    : This function is called to delete a port channel on
#                   one switch. It means that delete one side of a port channel.
#
#  Usage          : _ntgrDelPortChannelPerSwitch <switch_name channel_name>
#
#*******************************************************************************
proc _ntgrDelPortChannelPerSwitch { switch_name channel_name } {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    ##set ifName [_ntgrGetChannelLogicalIfName $cnn_id $channel_name]
    set ifName [getChannelLogicalIfName $switch_name $channel_name]
    set ifName [lindex $ifName 0]
    if {![regexp -nocase {lag \d+} $ifName]} {
        set ifName $channel_name
    }
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no port-channel $ifName\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_log_file "lib-poch.tcl" "Deleting port channel $channel_name on switch $switch_name"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrCreateAllPortChannels
#
#  Description    : This function is called to create all port channels on
#                   one switch. It means that create one side of port channels.
#
#  Usage          : _ntgrCreateAllPortChannels <switch_name>
#
#*******************************************************************************
proc _ntgrCreateAllPortChannels { switch_name } {
    global ntgr_poChanList
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    Netgear_log_file "lib-poch.tcl" "Creating all port channels on switch $switch_name"

    for_array_keys channel_name ntgr_poChanList {
        set members [getPortChannelMember $channel_name]
        foreach member $members {
            if { $switch_name == [lindex $member 0] } {
                _ntgrConfigLAG $cnn_id $channel_name
                set portlist [lindex $member 1]
                _ntgrAddPortToChannel $cnn_id $channel_name $portlist
                break;
            }
        }
    }
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrSetPortChannelName
#
#  Description    : This function is called to set port-channel name.
#
#  Usage          : _ntgrSetPortChannelName <switch_name> <port_channel> <name>
#
#*******************************************************************************
proc _ntgrSetPortChannelName {switch_name port_channel name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set port_channel [lindex $port_channel 0]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "port-channel name $port_channel $name\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-poch.tcl" "set port-channel name on $switch_name for port of $port_channel: $name"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrSetPortChannelLoadBalance
#
#  Description    : This function is called to set port-channel load-balance.
#         
#  Usage          : _ntgrSetPortChannelLoadBalance <switch_name> <channel_name> <loadbalanceNum>
# 
#*******************************************************************************
proc _ntgrSetPortChannelLoadBalance {switch_name channel_name loadbalanceNum} {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
	
    ##set ifName [_ntgrGetChannelLogicalIfName $connection_id $channel_name]
    set ifName [getChannelLogicalIfName $switch_name $channel_name]
    set ifName [lindex $ifName 0]
    
		exp_send -i $connection_id "configure\r"
		exp_sleep 1
		exp_send -i $connection_id "interface $ifName\r"
		exp_sleep 1
		exp_send -i $connection_id "port-channel load-balance $loadbalanceNum\r"
		exp_sleep 1
		exp_send -i $connection_id "exit\r"
		exp_sleep 1

    Netgear_log_file "lib-poch.tcl" "set port-channel loadbalance on port of $ifName on $switch_name"
		Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrSetPortChannelStaticModeEnable
#
#  Description    : This function is called to enable port-channel static mode.
#         
#  Usage          : _ntgrSetPortChannelStaticModeEnable <switch_name> <channel_name>
# 
#*******************************************************************************
proc _ntgrSetPortChannelStaticModeEnable {switch_name channel_name} {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
	
    ##set ifName [_ntgrGetChannelLogicalIfName $connection_id $channel_name]
    set ifName [getChannelLogicalIfName $switch_name $channel_name]
    set ifName [lindex $ifName 0]
    
		exp_send -i $connection_id "configure\r"
		exp_sleep 1
		exp_send -i $connection_id "interface $ifName\r"
		exp_sleep 1
		exp_send -i $connection_id "port-channel static\r"
		exp_sleep 1
		exp_send -i $connection_id "exit\r"
		exp_sleep 1

    Netgear_log_file "lib-poch.tcl" "enable port-channel static on port of $ifName on $switch_name"
		Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrSetPortChannelStaticModeDisable
#
#  Description    : This function is called to disable port-channel static mode.
#         
#  Usage          : _ntgrSetPortChannelStaticModeDisable <switch_name> <channel_name>
# 
#*******************************************************************************
proc _ntgrSetPortChannelStaticModeDisable {switch_name channel_name} {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
	
    set ifName [_ntgrGetChannelLogicalIfName $connection_id $channel_name]
    ##set ifName [getChannelLogicalIfName $switch_name $channel_name]
    set ifName [lindex $ifName 0]
    
		exp_send -i $connection_id "configure\r"
		exp_sleep 1
		exp_send -i $connection_id "interface $ifName\r"
		exp_sleep 1
		exp_send -i $connection_id "no port-channel static\r"
		exp_sleep 1
		exp_send -i $connection_id "exit\r"
		exp_sleep 1

    Netgear_log_file "lib-poch.tcl" "disable port-channel static on port of $ifName on $switch_name"
		Netgear_disconnect_switch $switch_name
}









#*******************************************************************************
#
#  Function Name  : _ntgrGetInfoAddPortToChannel
#
#  Description    : This function is used to get error info of adding port to port-channel.
#
#  Usage          : _ntgrGetInfoAddPortToChannel <switch_name> <port_channel> <port>
#
#*******************************************************************************
proc _ntgrGetInfoAddPortToChannel {switch_name port_channel port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set port_channel [lindex $port_channel 0]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1    
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "addport $port_channel\r"
    exp_sleep 1
    expect -i $cnn_id -re "addport $port_channel"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)    

    Netgear_log_file "lib-global-command.tcl" "Get info of adding port to port-channel on $switch_name: $output"
    Netgear_disconnect_switch $switch_name

    return $output
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetInfoDelPortFromChannel
#
#  Description    : This function is used to get error info of deleting port to port-channel.
#
#  Usage          : _ntgrGetInfoDelPortFromChannel <switch_name> <port_channel> <port>
#
#*******************************************************************************
proc _ntgrGetInfoDelPortFromChannel {switch_name port_channel port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set port_channel [lindex $port_channel 0]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1    
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "deleteport $port_channel\r"
    exp_sleep 1
    expect -i $cnn_id -re "deleteport $port_channel"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)    

    Netgear_log_file "lib-global-command.tcl" "Delete info of deleting port from port-channel on $switch_name: $output"
    Netgear_disconnect_switch $switch_name

    return $output
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetChannelName
#
#  Description    : This function is used to get port-channel name.
#
#  Usage          : _ntgrGetChannelName <switch_name> <channel_port>
#
#*******************************************************************************
proc _ntgrGetChannelName {switch_name channel_port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set channel_port [lindex $channel_port 0]
    
    set output ""
    
    exp_send -i $cnn_id "show port-channel brief\r"
    exp_sleep 1
    expect -i $cnn_id -re "show port-channel brief"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }    

    #================modified by Tony no 2010-11-12=============================#
    set result_str ""
    foreach str_tmp [split $output "\n"] {
        set str_tmp [string trim $str_tmp]
        if {[regexp "^$channel_port " $str_tmp]} {
            if {[regexp -nocase {lag \d+} $channel_port]} {
                set result_str [lindex $str_tmp 2]                
            } else {
                set result_str [lindex $str_tmp 1]
            }
            break
        }
    }
    
    Netgear_log_file "lib-poch.tcl" "Getting Channel Name on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name
    return $result_str
}



#*******************************************************************************
#
#  Function Name  : _ntgrGetChannelIntf
#
#  Description    : This function is used to get port-channel logical interface.
#
#  Usage          : _ntgrGetChannelIntf <switch_name> <channel_name>
#
#*******************************************************************************
proc _ntgrGetChannelIntf {switch_name channel_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show port-channel brief\r"
    exp_sleep 1
    expect -i $cnn_id -re "show port-channel brief"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }    

    #================modified by Tony no 2010-11-12=============================#
    set result_str ""
    foreach str_tmp [split $output "\n"] {
        set str_tmp [string trim $str_tmp]
        if {[regexp "$channel_name " $str_tmp]} {
            if {[regexp -nocase {^lag \d+} $str_tmp]} {
                set result_str "[lindex $str_tmp 0] [lindex $str_tmp 1]"
            } else {
                set result_str [lindex $str_tmp 0]
            }
            break
        }
    }
    
    Netgear_log_file "lib-poch.tcl" "Getting Channel Name on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name

    return $result_str
}



#*******************************************************************************
#
#  Function Name  : _ntgrChannelInfoCheck
#
#  Description    : This function is used to check channel info.
#
#  Usage          : _ntgrChannelInfoCheck <switch_name> <channelid> <checkinfo>
#
#*******************************************************************************
proc _ntgrChannelInfoCheck {switch_name channelid checkinfo} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show port-channel $channelid\r"
    exp_sleep 1
    expect -i $cnn_id -re "show port-channel $channelid"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }    

    if {[regexp -nocase $checkinfo $output]} {
        set result 1
    } else {
        set result 0
    }
        
    Netgear_log_file "lib-poch.tcl" "check channel info on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}

proc _ntgrGetVersionRelease {in_buf} {
    set ver ""
    set str1 {}
    set str2 {}
    set regver "(Software Version\[.\]+)(\[ ]+)(\[0-9RN\]+)"
    regexp $regver $in_buf match str1 str2 ver
    set ver [string trim $ver]
    if { $ver == "R"} {set ver 10}
    if { $ver == "N"} {set ver 9}  
    Netgear_log_file "_ntgrGetVersionRelease" "Softerware Version is $ver" 
    return $ver
}

proc _ntgrGetVersionReleasebyConnectionID {connection_id} {
    set ver {}
    set output {}
    exp_send -i $connection_id "show version\r"
    exp_sleep 1
    expect -i $connection_id -re "show version"
    exp_sleep 1
    expect {
        -i $connection_id -re "#" { append output $expect_out(buffer) }
        -i $connection_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $connection_id " "
            exp_sleep 1
            exp_continue
        }   
    }  
    set ver [_ntgrGetVersionRelease $output]
    return $ver
}

proc _ntgrGetVersionDetailedbyConnectionID {connection_id _o_intRelease _o_intVersion _o_intMaintenance _o_intPatch \
    _o_strModel} {
    set ver {}
    set output {}
    upvar 1 $_o_intRelease intRelease
    upvar 1 $_o_intVersion intVersion
    upvar 1 $_o_intMaintenance intMaintenance
    upvar 1 $_o_intPatch intPatch
    upvar 1 $_o_strModel strModel
    exp_send -i $connection_id "show version\r"
    exp_sleep 1
    expect -i $connection_id -re "show version"
    expect {
        -i $connection_id -re "#" { append output $expect_out(buffer) }
        -i $connection_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $connection_id " "
            exp_sleep 1
            exp_continue
        }   
    }  
    
    set strOutput $output
    ntgrCLIParseParam $strOutput "machine model" lstParamValue
    set str {} 
    regexp -nocase -- {([[J]?[FGXM][S]?[M]?[0-9]+[-]?[0-9]+[T]?[F]?[P]?[X]?[S]?[G]?[0-9]?[-]?[P]?[O]?[E]?[+]?)} [lindex $lstParamValue 0] str
    set strModel $str
    ntgrCLIParseParam $strOutput "software version" lstParamValue
    # modify by jim.xie for bug-888 begin
    # change variable strOutPut to strOutput 
    regexp -nocase -linestop -- {((R|N|[0-9]+)\.[0-9]+\.[0-9]+\.[0-9]+)} $lstParamValue strOutput
    ntgrParseFirmware $strOutput intRelease intVersion intMaintenance intPatch
    # modify by jim.xie for bug-888 end
}

