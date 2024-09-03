#!/bin/sh
################################################################################   
#
#  File Name		: lib-global-command.tcl
#
#  Description       	:
#         This TCL contains functions to execute various global command on the switch
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V       Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: pause
#
#  Description    : This function is for suspending running for debugging
#         
#  Usage          : pause
# 
#*******************************************************************************
proc pause {} {
    puts "==============please press any button then continue to run=================="
    gets stdin
}

#*******************************************************************************
#
#  Function Name	: _switch_copy_command
#
#  Description    : This function copies the image/config using TFTP
#         
#  Usage          : _switch_copy_command <connection_id> <sever_ip_addr>
#						        <image_name> <image_type>
# 
#*******************************************************************************
proc _switch_copy_command {connection_id server_ip_addr image_name image_type} {
	if {$image_type == "image"}	{
		expect -i $connection_id -re "#" {	
			exp_send -i $connection_id "copy tftp://$server_ip_addr/$image_name system:image\r"
		}
	} else {
		expect -i $connection_id -re "#" {	
			exp_send -i $connection_id "copy tftp://$server_ip_addr/$image_name nvram:startup-config\r"
		}
	}
	expect -i $connection_id -re "(y/n)" {
    		exp_send -i $connection_id "y"
	}
	expect {
	    -i $connection_id -re "#" {}
	    timeout exp_continue
	}
}

#*******************************************************************************
#
#  Function Name	: _switch_copy_to_tftp
#
#  Description    : This function copies the nvram startup config or log file to 
#                   TFTP Server using TFTP
#         
#  Usage          : _switch_copy_to_tftp <connection_id> <sever_ip_addr>
#						        <file_name> <file_type>
# 
#*******************************************************************************
proc _switch_copy_to_tftp {connection_id server_ip_addr file_name file_type} {
	if {$image_type == "startup"}	{
		expect -i $connection_id -re "#" {	
			exp_send -i $connection_id "copy nvram:startup-config tftp://$server_ip_addr/$file_name\r"
		}
	} elseif {$image_type == "log"} {
		expect -i $connection_id -re "#" {	
			exp_send -i $connection_id "copy nvram:log tftp://$server_ip_addr/$file_name\r"
		}
	}
	expect -i $connection_id -re "(y/n)" {
    		exp_send -i $connection_id "y"
	}
}

#*******************************************************************************
#
#  Function Name	: _switch_copy_and_reload
#
#  Description    : This function copies the image/config using TFTP and 
#			  then reloads the switch
#         
#  Usage          : _switch_copy_and_reload <connection_id> <sever_ip_addr>
#						        <image_name> <image_type>
#			Note : image_type --> "config", "image"
# 
#*******************************************************************************
proc _switch_copy_and_reload {connection_id server_ip_addr image_name image_type} {
	_switch_copy_command $connection_id $server_ip_addr $image_name $image_type

	set loop 1   
	exp_send -i $connection_id "\r"   
	while {$loop} {
    		expect -i $connection_id -re "#" {
			exp_send -i $connection_id "reload\r"
			set loop 0
		}
	}

	expect -i $connection_id -timeout 2 -re "unsaved" {
        expect -i $connection_id -re "\(y/n\)" {
            exp_send -i $connection_id "n"
        }
	}
	
	expect -i $connection_id -re "\(y/n\)" {
  		exp_send -i $connection_id "y"
	}
	sleep 2;#wait for 'y' is really sent out.
	exp_close -i $connection_id
}

#*******************************************************************************
#
#  Function Name	: _switch_ping
#
#  Description    : This function verfies whether the switch is reachable
#         
#  Usage          : _switch_ping <switch_ip_addr>
# 
#*******************************************************************************
proc _switch_ping {switch_ip_addr} {

	set pid [spawn ping $switch_ip_addr]
	set id $spawn_id
#	expect -i $id "\n" {
	expect -i $id -timeout 60 eof {
		set response $expect_out(buffer)
	}
#	}

	if {[regexp  -nocase "bytes=.*TTL=.*" $response]} {
		set result TRUE
	} else {
		set result FALSE
	}
	return $result
}

#*******************************************************************************
#
#  Function Name	: _ntgrSwitchCopy
#
#  Description    : This function encapsulates the copy command 
#         
#  Usage          : _ntgrSwitchCopy <switch_name from to {filename {}}>
#
# 
#*******************************************************************************
proc _ntgrSwitchCopy {switch_name from to {filename {}} {bSyn 1}} {
    Netgear_log_file "lib-global-command.tcl" "Copying $from to $to"
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    if {$filename == {}} {
        exp_send -i $cnn_id "copy $from $to\r"
    } else {
        exp_send -i $cnn_id "copy $from $to $filename\r"
    }
    exp_sleep 1
    exp_send -i $cnn_id "y\r"
    exp_sleep 1
    expect -i $cnn_id -re "copy"

    if {$bSyn == 1} {
        expect -i $cnn_id {
            timeout exp_continue
            -re "#" ;#Do nothing, just exit expect
        }
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrRebootSwitch
#
#  Description    : This function reboot the netgear switch by CLI 
#         
#  Usage          : _ntgrRebootSwitch <switch_name>
#
# 
#*******************************************************************************
proc _ntgrRebootSwitch {switch_name {unitID 0}} {
	
#	Netgear_connect_switch $switch_name
#	set connection_id [_get_switch_handle $switch_name]

  set connection_id [_get_switch_handle $switch_name]
  set bCnn 1
  if {[string first "exp" $connection_id] == -1} {
      Netgear_connect_switch $switch_name
      set connection_id [_get_switch_handle $switch_name]
      set bCnn 0
  }
    	
	set timeout 50
	if { $connection_id != -1 } { 
		while {1} {
    			exp_send -i $connection_id "\r"
  	      exp_sleep 1
    			expect -i $connection_id -re "#" {
    				set reload "reload"
    				if {$unitID != 0 && $unitID != -1} {set reload "$reload $unitID"}
					exp_send -i $connection_id "$reload\r"
					break
			}
		}

		expect -i $connection_id -re "save them" {
        if {$unitID != -1} {
    		    # Save the current configuration
      			exp_send -i $connection_id "y"
        } else {
      			exp_send -i $connection_id "n"
        }
		}
		
		expect -i $connection_id -re "reset the system" {
  			exp_send -i $connection_id "y"
		}  
		
		expect -i $connection_id -re "reload" {
  			exp_send -i $connection_id "y"
		} restart {
		} "#" {}

		sleep 1
    #modify by luiz 2016/8/17
    #Netgear_disconnect_switch $switch_name"
    _switch_disconnect $connection_id
    _set_switch_handle $switch_name 0
    
	}
}

#*******************************************************************************
#
#  Function Name	: _ntgrClearMacSwitch
#
#  Description    : This function clears mac address of the netgear switch by CLI 
#         
#  Usage          : _ntgrClearMacSwitch <switch_name>
#
# 
#*******************************************************************************
proc _ntgrClearMacSwitch {switch_name mac if vlan} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	if { $connection_id != -1 } { 
			set cmd "clear mac-addr-table"
			if { $mac == 0 && $if == 0 && $vlan == 0 } {
				set cmd "$cmd all"
			} elseif { $if != 0 } {
				set cmd "$cmd interface $if"
			} elseif { $vlan != 0 } {
				set cmd "$cmd vlan $vlan"
			} elseif { $mac != 0 } {
				set cmd "$cmd $mac"
			}	
			exp_send -i $connection_id "$cmd\r"	
			sleep 5
		  Netgear_disconnect_switch $switch_name
	}
}

#*******************************************************************************
#
#  Function Name	: _ntgrShowLearnMacSwitchonInterface
#
#  Description    : This function show mac address of the netgear switch by CLI 
#         
#  Usage          : _ntgrShowLearnMacSwitchonInterface <switch_name Interface>
#
# 
#*******************************************************************************
proc _ntgrShowLearnMacSwitchonInterface {switch_name interface} {
    set retList {} 
    set interface [lindex $interface 0]
    set regSWMAC "(\[0-9a-fA-F:\]+)(\[ ]+)(\[0-9\]+)(\[ ]+)(Learned)"
    for {set i 1} {$i <6} {incr i} { set str($i) {}}
  	Netgear_connect_switch $switch_name
  	set connection_id [_get_switch_handle $switch_name]
  	set ret {}
  	expect -i $connection_id "#" {}
    append ret $expect_out(buffer)
		expect -i $connection_id -re "#" {
    		set cmd "show mac-addr-table interface $interface"
    		exp_send -i $connection_id "$cmd\r"
    		sleep 1
    		expect -i $connection_id -re "#"
        append ret $expect_out(buffer)
    }
   
	  set outputlist [split $ret "\n"]
	
	  foreach output $outputlist {  
	      if { $output!= {} } {
            set retval [regexp $regSWMAC $output match str(1) str(2) str(3) str(4) str(5)]
            if { $str(1) != {} } {
              lappend retList $str(1) 
              Netgear_log_file "lib-stp" "Learned Mac address : $str(1) on interface $interface"  
              for {set i 1} {$i <6} {incr i} { set str($i) {} } 
            }
        }
    }    
		Netgear_disconnect_switch $switch_name
		return $retList
}

proc _ntgrGetMacSwitchonInterface {switch_name interface} {
    set retList {} 
    set interface [lindex $interface 0]
    set regSWMAC "(\[0-9a-fA-F:\]+)(\[ ]+)(\[0-9\]+)(\[ ]+)"
    for {set i 1} {$i <6} {incr i} { set str($i) {}}
  	Netgear_connect_switch $switch_name
  	set connection_id [_get_switch_handle $switch_name]

  
  	set ret ""
  	expect -i $connection_id "#" {}
    append ret $expect_out(buffer)
  
		expect -i $connection_id -re "#" {
    		set cmd "show mac-addr-table interface $interface"
    		exp_send -i $connection_id "$cmd\r"
    		sleep 1
    		expect -i $connection_id -re "#"
        append ret $expect_out(buffer)
    }
   
	  set outputlist [split $ret "\n"]
	 
	  foreach output $outputlist {  
	     
	      if { $output!= {} } {
            set retval [regexp $regSWMAC $output match str(1) str(2) str(3) str(4) str(5)]
            if { $str(1) != {} } {
              lappend retList $str(1) 
              Netgear_log_file "lib-stp" "Learned Mac address : $str(1) on interface $interface"  
              for {set i 1} {$i <6} {incr i} { set str($i) {} } 
            }
        }
    }    
		Netgear_disconnect_switch $switch_name
		return $retList
}

#*******************************************************************************
#
#  Function Name	: CALCheckMacVlanonInterface
#
#  Description    : This function check mac address and vlan id of the netgear switch by CLI 
#         
#  Usage          : CALCheckMacVlanonInterface <switch_name Interface expect_mac expect_vlan> 
#
# 
#*******************************************************************************
proc CALCheckMacVlanonInterface {switch_name interface expect_mac expect_vlan} {
    set interface [lindex $interface 0]
    set regSWMAC "(\[0-9a-fA-F:\]+)\[ ]+(\[0-9\]+)\[ ]+Learned"
    for {set i 1} {$i <6} {incr i} { set str($i) {}}
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set ret {}
        expect -i $connection_id "#" {}
        append ret $expect_out(buffer)
        expect -i $connection_id -re "#" {
        set cmd "show mac-addr-table interface $interface"
        exp_send -i $connection_id "$cmd\r"
        sleep 1
        expect -i $connection_id -re "#"
        append ret $expect_out(buffer)
    }
    set return_vlaue 0
    set outputlist [split $ret "\n"]
    set real_mac ""
    set real_vlan ""
    foreach output $outputlist {  
        if { $output!= {} } {
            regexp $regSWMAC $output match real_mac real_vlan
            if {$expect_mac == $real_mac && $expect_vlan == $real_vlan} {
                set return_vlaue 1
            }
        }
    }
    Netgear_disconnect_switch $switch_name
    if {$return_vlaue == 0} {
        set return_vlaue 0
        Netgear_log_file "WARN:: Check MAC table failed." " Expect MAC Address:<$expect_mac> Real MAC Address: <$real_mac>. Expect VLAN ID:<$expect_vlan> Real VLAN ID: <$real_vlan>"
    }


    return $return_vlaue
}

#*******************************************************************************
#
#  Function Name  : _ntgrClearArpCache
#
#  Description    : This function is used to clear arp cache of a switch.
#
#  Usage          : _ntgrClearArpCache <switch_name>
#
#*******************************************************************************
proc _ntgrClearArpCache {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "clear arp-cache\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

proc _ntgrClearArpCacheGateway {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "clear arp-cache gateway\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrClearCounterSwitch
#
#  Description    : This function clears counters of the netgear switch by CLI 
#         
#  Usage          : _ntgrClearCounterSwitch  <switch_name>
#
# 
#*******************************************************************************
proc _ntgrClearCounterSwitch {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	if { $connection_id != -1 } { 
		#while {1} {
    	  #expect -i $connection_id -re "#" {
				exp_send -i $connection_id "clear counters all\r"
				sleep 1
				#break
			#}
		#}
		
		expect -i $connection_id -re "\(y/n\)" {
  			exp_send -i $connection_id "y"
		}
		sleep 10
		Netgear_disconnect_switch $switch_name
	}
}

#*******************************************************************************
#
#  Function Name	: _ntgrSaveConfig
#
#  Description    : This function save the switch's running-configuration
#                   to its flash
#         
#  Usage          : _ntgrSaveConfig <switch_name>
#
# 
#*******************************************************************************
proc _ntgrSaveConfig {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	#while {1} {
    	#expect -i $connection_id -re "#" {
			exp_send -i $connection_id "copy system:running-config nvram:startup-config\r"
			#break
		#}
	#}
	# Save the current configuration
	expect -i $connection_id -re "\(y/n\)" {
  		exp_send -i $connection_id "y"
	}
	
    while {1} {
        expect -i $connection_id "#" {
            break
        }
        sleep 1
    }
	
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrResetConfig
#
#  Description    : This function reset the switch's running-configuration
#                   to its flash
#         
#  Usage          : _ntgrResetConfig <switch_name>
#
# 
#*******************************************************************************
proc _ntgrResetConfig {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	# reset the current configuration
	exp_send -i $connection_id "clear config\r"
	exp_sleep 1
	exp_send -i $connection_id "y"
	exp_sleep 1
	expect {
		-i $connection_id "\)>" {}
		timeout exp_continue
	}

	Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name	: _ntgrResetConfig2AutomationDefault
#
#  Description    : This function copies automation default configuration
#                   to startup-config
#         
#  Usage          : _ntgrResetConfig2AutomationDefault <switch_name> <filepath>
#
# 
#*******************************************************************************
proc _ntgrResetConfig2AutomationDefault {switch_name filepath} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	exp_send -i $connection_id "copy $filepath nvram:startup-config\r"
	exp_sleep 1
	exp_send -i $connection_id "y"
	exp_sleep 1
	expect {
		-i $connection_id "TFTP download operation completed successfully." {}
 		-i $connection_id "File Not found!" {
 		  Netgear_log_file "_ntgrResetConfig2AutomationDefault" {No automation default configuration file exists, please upload one.}
 		}
		timeout exp_continue
	}

	catch [Netgear_disconnect_switch $switch_name]
	
	set ipaddr [_get_switch_ip_addr $switch_name]
	for {set i 0} {$i<10} {incr i} {
	  if {[_switch_ping $ipaddr] == TRUE} break;
	}
}

#*******************************************************************************
#
#  Function Name  : _ntgrPortLinkUp
#
#  Description    : This function is called to set port admin status up.
#
#  Usage          : _ntgrPortLinkUp <switch_name if_list>
#
#*******************************************************************************
proc _ntgrPortLinkUp {switch_name if_list} {
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    Netgear_log_file "lib-global-command.tcl" "Setting interface $if_list status to up"
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no shutdown\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrPortLinkDown
#
#  Description    : This function is called to set port admin status down.
#
#  Usage          : _ntgrPortLinkDown <switch_name if_list>
#
#*******************************************************************************
proc _ntgrPortLinkDown {switch_name if_list} {
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    Netgear_log_file "lib-global-command.tcl" "Setting interface $if_list status to down"
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }

        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "shutdown\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrAllPortsLinkUp
#
#  Description    : This function is called to set all ports admin status to up.
#
#  Usage          : _ntgrAllPortsLinkUp <switch_name>
#
#*******************************************************************************
proc _ntgrAllPortsLinkUp {switch_name} {
    set cnn_id [_get_switch_handle $switch_name]
    Netgear_log_file "lib-global-command.tcl" "Setting all ports admin status to up"
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no shutdown all\r"
    expect -i $cnn_id -re "shutdown all"
    expect -i $cnn_id -re "#"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
}

#*******************************************************************************
#
#  Function Name  : _ntgrAllPortsLinkDown
#
#  Description    : This function is called to set all ports admin status to down.
#
#  Usage          : _ntgrAllPortsLinkDown <switch_name>
#
#*******************************************************************************
proc _ntgrAllPortsLinkDown {switch_name} {
    set cnn_id [_get_switch_handle $switch_name]
    Netgear_log_file "lib-global-command.tcl" "Setting all ports admin status to down"
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "shutdown all\r"
    expect -i $cnn_id -re "shutdown all"
    expect -i $cnn_id -re "#"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetMngrIP
#
#  Description    : This function is used to get the management ip of a switch.
#
#  Usage          : _ntgrGetMngrIP <switch_name>
#
#*******************************************************************************
proc _ntgrGetMngrIP {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
	# modify by jim.xie begain 
	# New GUI Manage switch using management vlan to set ip
    set intRelease {}; set intVersion {}; set intMaintenance {}; set intPatch {} ; set strModel {}
    _ntgrGetVersionDetailedbyConnectionID $cnn_id intRelease intVersion intMaintenance intPatch strModel
    
    if { $intRelease > 10 } {
       Netgear_disconnect_switch $switch_name
       set str3 [CALGetIpAddressOnPort $switch_name "vlan 1"]
    } elseif {$intRelease == 10 && $intMaintenance >= 2} {
       Netgear_disconnect_switch $switch_name
       set str3 [CALGetIpAddressOnPort $switch_name "vlan 1"]
    } else {
        exp_send -i $cnn_id "show network\r"
        exp_sleep 1
        expect -i $cnn_id -re "show network"
	    expect -i $cnn_id -re "#"
        set output $expect_out(buffer)
        set regMngrIP "(IP Address\[.\]+)(\[ ]+)(\[0-9.\]+)"
        regexp $regMngrIP $output match str1 str2 str3
        Netgear_disconnect_switch $switch_name
    }
    Netgear_log_file "lib-global-command.tcl" "Management ip address on $switch_name: $str3"
    # modify by jim.xie end
     
    return $str3
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetMngrIPMask
#
#  Description    : This function is used to get the management ip mask of a switch.
#
#  Usage          : _ntgrGetMngrIPMask <switch_name>
#
#*******************************************************************************
proc _ntgrGetMngrIPMask {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    # modify by jim.xie begain 
	# New GUI Manage switch using management vlan to set ip
    set intRelease {}; set intVersion {}; set intMaintenance {}; set intPatch {} ; set strModel {}
    _ntgrGetVersionDetailedbyConnectionID $cnn_id intRelease intVersion intMaintenance intPatch strModel
    
    if { $intRelease > 10 } {
       Netgear_disconnect_switch $switch_name
       set str3 [_ntgrGetIpMaskOnPort $switch_name "vlan 1"]
    } elseif {$intRelease == 10 && $intMaintenance >= 2} {
       Netgear_disconnect_switch $switch_name
       set str3 [_ntgrGetIpMaskOnPort $switch_name "vlan 1"]
    } else {
        exp_send -i $cnn_id "show network\r"
        exp_sleep 1
        expect -i $cnn_id -re "show network"
		expect -i $cnn_id -re "#"
        set output $expect_out(buffer)
        set regMngrIP "(Subnet Mask\[.\]+)(\[ ]+)(\[0-9.\]+)"
        regexp $regMngrIP $output match str1 str2 str3
	}
	# modify by jim.xie end 
   
    
    Netgear_log_file "lib-global-command.tcl" "Management ip mask on $switch_name: $str3"
    Netgear_disconnect_switch $switch_name

    return $str3
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetMngrGateway
#
#  Description    : This function is used to get the management default gateway of a switch.
#
#  Usage          : _ntgrGetMngrGateway <switch_name>
#
#*******************************************************************************
proc _ntgrGetMngrGateway {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    # modify by jim.xie begain 
	# New GUI Manage switch using management vlan to set ip
    set intRelease {}; set intVersion {}; set intMaintenance {}; set intPatch {} ; set strModel {}
    _ntgrGetVersionDetailedbyConnectionID $cnn_id intRelease intVersion intMaintenance intPatch strModel
	if { $intRelease > 10 } {
        exp_send -i $cnn_id "show ip route\r"
        exp_sleep 1
        expect -i $cnn_id -re "show ip route"
		expect -i $cnn_id -re "#"
        set output $expect_out(buffer)
		Netgear_log_file "lib-global-command.tcl" "output=$output"
        #set regMngrIP "(Default\s+Gateway\s+is\s+(\d+\.\d+\.\d+\.\d+)"
        #regexp {Default\s+Gateway\s+is\s+(\d+\.\d+\.\d+\.\d+)} $output match str3
		regexp {Default\s+Gateway.*\s+(\d+\.\d+\.\d+\.\d+)\s+.*} $output match str3
    } elseif {$intRelease == 10 && $intMaintenance >= 2} {
        exp_send -i $cnn_id "show ip route\r"
        exp_sleep 1
        expect -i $cnn_id -re "show ip route"
		expect -i $cnn_id -re "#"
        set output $expect_out(buffer)
        regexp {Default\s+Gateway\s+is\s+(\d+\.\d+\.\d+\.\d+)} $output match str3
    } else {
        exp_send -i $cnn_id "show network\r"
        exp_sleep 1
        expect -i $cnn_id -re "show network"
		expect -i $cnn_id -re "#"
        set output $expect_out(buffer)
        set regMngrIP "(Default Gateway\[.\]+)(\[ ]+)(\[0-9.\]+)"
        regexp $regMngrIP $output match str1 str2 str3
	}
   
    Netgear_log_file "lib-global-command.tcl" "Management default gateway on $switch_name: $str3"
    Netgear_disconnect_switch $switch_name

    return $str3
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetSWMACAddress
#
#  Description    : This function is used to get Mac address of a switch.
#
#  Usage          : _ntgrGetSWMACAddress <switch_name>
#
#*******************************************************************************
proc _ntgrGetSWMACAddress {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    set str3 ""
    # modify by jim.xie for bug-1157 begain 
    # Check this command can get mac address or nto
    exp_send -i $cnn_id "show network\r"
    exp_sleep 1
    expect -i $cnn_id -re "show network"
    expect -i $cnn_id -re "#"
    set output $expect_out(buffer)
    set regSWMAC "(Burned In MAC Address\[.\]+)(\[ ]+)(\[0-9a-fA-F:\]+)"
    regexp $regSWMAC $output match str1 str2 str3
    if {$str3 == ""} {
         exp_send -i $cnn_id "show ip management\r"
         exp_sleep 1
         expect -i $cnn_id -re "show ip management"
         expect -i $cnn_id -re "#"
         set output $expect_out(buffer)
         set regSWMAC "(Burned In MAC Address\[.\]+)(\[ ]+)(\[0-9a-fA-F:\]+)"
         regexp $regSWMAC $output match str1 str2 str3
    }
    Netgear_log_file "lib-global-command.tcl" "Switch Mac address on $switch_name: $str3"
    Netgear_disconnect_switch $switch_name

    return $str3
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetSWMngrVlanID
#
#  Description    : This function is used to get management vlan id of a switch.
#
#  Usage          : _ntgrGetSWMngrVlanID <switch_name>
#
#*******************************************************************************
proc _ntgrGetSWMngrVlanID {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    # modify by jim.xie for bug-1157 begain 
	#set modename [_get_switch_model $switch_name]
    #if {[regexp -nocase {XCM.?} $modename]} {
	#    exp_send -i $cnn_id "show serviceport\r"
    #    exp_sleep 1
    #    expect -i $cnn_id -re "show serviceport"
	#} else {
        exp_send -i $cnn_id "show network\r"
        exp_sleep 1
        expect -i $cnn_id -re "show network"
	#}
	# modify by jim.xie for bug-1157 begain 
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    regexp -nocase -line {Management VLAN ID\.+\s+(\d+)} $output match vlanid
    
    Netgear_log_file "lib-global-command.tcl" "Switch management vlan id on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $vlanid
}


#*******************************************************************************
#
#  Function Name  : _ntgrExecPrivilegedCmd
#
#  Description    : This function is called to execute some commands at 
#                   privileged mode and return results infomation from the switch.
#
#  Usage          : _ntgrExecPrivilegedCmd <switch_name cmdlist {use_telnet 1}>
#
#*******************************************************************************
proc _ntgrExecPrivilegedCmd {switch_name cmdlist {use_telnet 1}} {
    if {$use_telnet == 1} {
        set mngr_ip [_ntgrGetMngrIP $switch_name]
        set cnn_id [ _switch_connect_telnet $mngr_ip]
    } else {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
    }
    
    Netgear_log_file "lib-global-command.tcl" "Execute the following commands on $switch_name: \r\n$cmdlist"
    set result ""
    expect -i $cnn_id -re "#"

    # Using 'for' against 'foreach' to execute these commands orderly.
    set lenth [llength $cmdlist]
    for {set idx 0} {$idx<$lenth} {incr idx} {
        set command [lindex $cmdlist $idx]
        exp_send -i $cnn_id "$command\r"
        exp_sleep 1
        expect {
            -i $cnn_id -re "#" { set result $result$expect_out(buffer) }
            -i $cnn_id -re "More" {
                set result $result$expect_out(buffer)
                exp_send -i $cnn_id " "
                exp_sleep 1
                exp_continue
            }
            -i $cnn_id -re "\(y/n\)" {
                set result $result$expect_out(buffer)
                exp_send -i $cnn_id "y"
                exp_sleep 1
                exp_continue
            }
            timeout { exp_send -i $cnn_id " "; exp_continue }
        }
    }

    _switch_disconnect $cnn_id
    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetMacAgeTime
#
#  Description    : This function is used to set mac aging time of a switch.
#
#  Usage          : _ntgrSetMacAgeTime <switch_name value>
#
#*******************************************************************************
proc _ntgrSetMacAgeTime {switch_name value} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    Netgear_log_file "lib-global-command.tcl" "Setting MAC aging time to $value on switch $switch_name"
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$value == "default"} {
        exp_send -i $cnn_id "no bridge aging-time\r"
    } else {
        exp_send -i $cnn_id "bridge aging-time $value\r"
    }
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetSwitchPrompt
#
#  Description    : This function is used to set the prompt of a switch.
#
#  Usage          : _ntgrSetSwitchPrompt <switch_name>
#
#*******************************************************************************
proc _ntgrSetSwitchPrompt {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    set prompt [_ntgrGetSwitchPrompt $switch_name]

    Netgear_log_file "lib-global-command.tcl" "Setting prompt to $prompt on switch $switch_name"
    exp_send -i $cnn_id "set prompt $prompt\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetSwitchMngrIPAddr
#
#  Description    : This function is used to set the management ip address.
#
#  Usage          : _ntgrSetSwitchMngrIPAddr <switch_name> <addr_mask_string>
#
#*******************************************************************************
proc _ntgrSetSwitchMngrIPAddr {switch_name addr_mask_string} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
	
    if {$addr_mask_string == {}} {
        set ip     [_ntgrGetSwitchMngrIP $switch_name]
        set mask   [_ntgrGetSwitchMngrIPMask $switch_name]
    } else {
        set ip     [lindex $addr_mask_string 0]
        set mask   [lindex $addr_mask_string 1]
    }
    set mngr_vlan  [_ntgrGetSwitchMngrVlan $switch_name]
    set gw         [_ntgrGetSwitchMngrGW $switch_name]
   
    Netgear_log_file "lib-global-command.tcl" "Setting management ip address to $ip on switch $switch_name"
	
	# modify by jim.xie begain 
	# New GUI Manage switch using management vlan to set ip
    set intRelease {}; set intVersion {}; set intMaintenance {}; set intPatch {} ; set strModel {}
    _ntgrGetVersionDetailedbyConnectionID $cnn_id intRelease intVersion intMaintenance intPatch strModel
	
    if {$ip != "DHCP"} {
	    if { $intRelease > 10 } {
            Netgear_disconnect_switch $switch_name
            CALAddIpAddressOnPort $switch_name "vlan 1" $ip $mask
        } elseif {$intRelease == 10 && $intMaintenance >= 2} {
            Netgear_disconnect_switch $switch_name
            CALAddIpAddressOnPort $switch_name "vlan 1" $ip $mask
        } else {
            Netgear_connect_switch $switch_name
            set cnn_id [_get_switch_handle $switch_name]
            exp_send -i $cnn_id "network protocol none\r"
            exp_sleep 1
            exp_send -i $cnn_id "y"
            exp_sleep 1
            exp_send -i $cnn_id "network parms $ip $mask $gw\r"
            exp_sleep 1
			      exp_send -i $cnn_id "network mgmt_vlan $mngr_vlan\r"
            exp_sleep 1
		}
    } else {
		if { $intRelease > 10 } {
            Netgear_disconnect_switch $switch_name
            _ntgrIpDhcpEnablePerPort $switch_name "vlan 1"
        } elseif {$intRelease == 10 && $intMaintenance >= 2} {
            Netgear_disconnect_switch $switch_name
            _ntgrIpDhcpEnablePerPort $switch_name "vlan 1"
        } else {
            Netgear_connect_switch $switch_name
            set cnn_id [_get_switch_handle $switch_name]
            exp_send -i $cnn_id "network protocol dhcp\r"
            exp_sleep 1
            exp_send -i $cnn_id "y"
            exp_sleep 1
			      exp_send -i $cnn_id "network mgmt_vlan $mngr_vlan\r"
            exp_sleep 1
	    }
    }
    # modify by jim.xie end 
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableConsoleTimeout
#
#  Description    : This function is used to set the timeout value of console to forever.
#
#  Usage          : _ntgrDisableConsoleTimeout <switch_name>
#
#*******************************************************************************
proc _ntgrDisableConsoleTimeout {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    set ip     [_ntgrGetSwitchMngrIP $switch_name]
    set mask   [_ntgrGetSwitchMngrIPMask $switch_name]

    Netgear_log_file "lib-global-command.tcl" "Disable console timeout on switch $switch_name"
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "lineconfig\r"
    exp_sleep 1
    exp_send -i $cnn_id "serial timeout 0\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrJumboFrameConfig
#
#  Description    : This function configures Jumbo frame
#         
#  Usage          : _ntgrJumboFrameConfig <switch>
#
# 
#*******************************************************************************
proc _ntgrJumboFrameConfig {switch} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	set count [_ntgrGetJumboFrameListCount $switch]
	for {set i 0} {$i < $count} {incr i} {
		set interface [_ntgrGetJumboFrameInterface $switch $i]
		set frame_size [_ntgrGetJumboFrameSize $switch $i]
		if {$interface == "all"} {
			exp_send -i $connection_id "configure\r"
			sleep 1
		} else {
			exp_send -i $connection_id "configure\r"
			sleep 1
			exp_send -i $connection_id "interface $interface\r"
			sleep 1
		}
		if {$frame_size == "default"} {
			exp_send -i $connection_id "no mtu"
		} else {
			exp_send -i $connection_id "mtu $frame_size"
		}
	}
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckExpect
#
#  Description    : This function is used to execute some commands with expected
#                   result. If get the expected result after executing those
#                   commands, it return 1; otherwise return 0.
#
#  Usage          : _ntgrCheckExpect <switch_id> <cmds> <expect_string_list>
#                   Note that commands should be seperated by '\r' in cmds.
#
#*******************************************************************************
proc _ntgrCheckExpect {switch_id cmds expect_str_list expect} {
    set expect_string_list $expect_str_list
    
    if { [string trim $expect_str_list] != "" && [uplevel 2 "info exists $expect_str_list"] == 1 } {
        unset expect_string_list
        upvar 2 $expect_str_list expect_string_list
    }

    set cnn_id [_get_switch_handle $switch_id]

    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_id
        set cnn_id [_get_switch_handle $switch_id]
        set bCnn 0
    }
    
#    Netgear_connect_switch $switch_id
#    set cnn_id [_get_switch_handle $switch_id]

    set result ""
    set cmdlist [split $cmds "\n"]
    expect -i $cnn_id "#" {}; # Add this clause to clear buffer.
    foreach cmd $cmdlist {
        set cmd [string trim $cmd " "]
        exp_send -i $cnn_id "$cmd\r"
#        expect -i $cnn_id "$cmd"
# The above command could not get the expected sometime, so try to modify as following
        set timeout  1
        expect {
            -i $cnn_id "$cmd" {}
            -i $cnn_id timeout {
                foreach sub $cmd {
                    expect -i $cnn_id "$sub" {}
                }
            }
        }
        set timeout  10
# End

        expect {
            -i $cnn_id -ex "\) #" { set result $result$expect_out(buffer) } 
            -i $cnn_id -ex "\)#" { set result $result$expect_out(buffer) }
            -i $cnn_id -ex "\) >" { set result $result$expect_out(buffer) }
            -i $cnn_id -re "User:" { set result $result$expect_out(buffer) }
            -i $cnn_id -re "More" {
                set result $result$expect_out(buffer)
                exp_send -i $cnn_id " "
                exp_sleep 1
                exp_continue
            }
            timeout { exp_send -i $cnn_id " "; exp_exit }
        }
    }

#    if { $expect == 1 } {
#        foreach exp_str $expect_string_list {
#            if { [string first $exp_str $result] == -1} {
#                set bRet 0
#                Netgear_log_file "lib-global-command.tcl" "Expected: $exp_str"
#                break;# Because some expected does not exist.
#            }
#        }
#    } else {
#        foreach exp_str $expect_string_list {
#            if { [string first $exp_str $result] != -1} {
#                Netgear_log_file "lib-global-command.tcl" "Unexpected: $exp_str"
#                set bRet 0
#                break;# Because some unexpected occur.
#            }
#        }
#    }

    set bRet 1
    if { $expect == 0 || $expect == 1 } {
        foreach exp_str $expect_string_list {
           if { $expect == 1 && [string first $exp_str $result] == -1} {
               set bRet 0
               Netgear_log_file "lib-global-command.tcl" "Expected: $exp_str"
               break;# Because some expected does not exist.
           }
           if { $expect == 0 && [string first $exp_str $result] != -1} {
               Netgear_log_file "lib-global-command.tcl" "Unexpected: $exp_str"
               set bRet 0
               break;# Because some unexpected occur.
           }
        }
    } elseif { $expect == 2 || $expect == 3 } {
        set lstResult [split $result "\r\n"]
        foreach exp_str $expect_string_list {
            set bMatch 0
            foreach line $lstResult {
                if { $exp_str == $line } {
                    set bMatch 1
                    break
                }
            }
            if { $expect == 3 && $bMatch == 0 } {
                set bRet 0
                Netgear_log_file "lib-global-command.tcl" "Expected: $exp_str"
                break;# Because some expected does not exist.
            } elseif { $expect == 2 && $bMatch == 1 } {
                set bRet 0
                Netgear_log_file "lib-global-command.tcl" "Unexpected: $exp_str"
                break;# Because some expected does not exist.
            }
        }
    }

    if { $bRet == 0 } {
        Netgear_log_file "lib-global-command.tcl" "Getting unexpected result: $result"
    }
    
    set expect_string_list $result
    
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_id
    }
#    Netgear_disconnect_switch $switch_id
    return $bRet
}

#*******************************************************************************
#
#  Function Name	: getTimeWheelCycleInterval
#
#  Description    : This function is called to get the time wheel cycle interval 
#		        from the global ntgrTimeWheelInfo
#         
#  Usage          : getTimeWheelCycleInterval
# 
#*******************************************************************************
proc getTimeWheelCycleInterval {} {
	global ntgrTimeWheelInfo
	return [keylget ntgrTimeWheelInfo CYCLE_INTERVAL]
}

#*******************************************************************************
#
#  Function Name	: getTimeWheelTotalNoOfCycle
#
#  Description    : This function is called to get the total number of cycles time 
#			  wheel has to run from the global ntgrTimeWheelInfo
#         
#  Usage          : getTimeWheelTotalNoOfCycle
# 
#*******************************************************************************
proc getTimeWheelTotalNoOfCycle {} {
	global ntgrTimeWheelInfo
	return [keylget ntgrTimeWheelInfo TOTAL_NO_CYCLE]
}

#*******************************************************************************
#
#  Function Name	: getTimeWheelScriptStartCycle
#
#  Description    : This function is called to get the script start cycle 
#		  	  from the global ntgrTWScriptInfo
#         
#  Usage          : getTimeWheelScriptStartCycle <script id>
# 
#*******************************************************************************
proc getTimeWheelScriptStartCycle { scriptId } {
	global ntgrTWScriptInfo
	return [keylget ntgrTWScriptInfo($scriptId) START_CYCLE]
}

#*******************************************************************************
#
#  Function Name	: getTimeWheelScriptStopCycle
#
#  Description    : This function is called to get the script stop cycle 
#		  	  from the global ntgrTWScriptInfo
#         
#  Usage          : getTimeWheelScriptStopCycle <script id>
# 
#*******************************************************************************
proc getTimeWheelScriptStopCycle { scriptId } {
	global ntgrTWScriptInfo
	return [keylget ntgrTWScriptInfo($scriptId) STOP_CYCLE]
}

#*******************************************************************************
#
#  Function Name	: getTimeWheelScriptRepeatInterval
#
#  Description    : This function is called to get the script repeat interval
#		  	  from the global ntgrTWScriptInfo
#         
#  Usage          : getTimeWheelScriptRepeatInterval <script id>
# 
#*******************************************************************************
proc getTimeWheelScriptRepeatInterval { scriptId } {
	global ntgrTWScriptInfo
	return [keylget ntgrTWScriptInfo($scriptId) REPEAT_INTERVAL]
}

#*******************************************************************************
#
#  Function Name	: getTimeWheelScriptName
#
#  Description    : This function is called to get the script name  
#		  	  from the global ntgrTWScriptInfo
#         
#  Usage          : getTimeWheelScriptName <script id>
# 
#*******************************************************************************
proc getTimeWheelScriptName { scriptId } {
	global ntgrTWScriptInfo
	return [keylget ntgrTWScriptInfo($scriptId) SCRIPT_NAME]
}

#*******************************************************************************
#
#  Function Name	: getTimeWheelScriptCfg
#
#  Description    : This function is called to get the script configuration file   
#		  	  from the global ntgrTWScriptInfo
#         
#  Usage          : getTimeWheelScriptCfg <script id>
# 
#*******************************************************************************
proc getTimeWheelScriptCfg { scriptId } {
	global ntgrTWScriptInfo
	return [keylget ntgrTWScriptInfo($scriptId) CONFIG_FILE]
}

#*******************************************************************************
#
#  Function Name	: _ntgrUploadLogFile
#
#  Description    : This function is used to Upload Log file from the Switch.
#         
#  Usage          : _ntgrUploadLogFile <switch_name>
#
# 
#*******************************************************************************
proc _ntgrUploadLogFile {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	if { $connection_id != -1 } { 
		set tftpServer [_ntgrGetTftpServer]
		set logFile [_ntgrGetUploadLogFileName $switch_name ]
		expect -i $connection_id -re "#" {	
			exp_send -i $connection_id "copy nvram:log tftp://$tftpServer/$logFile\r"
		}
		expect -i $connection_id -re "\(y/n\)" {
			exp_send -i $connection_id "y"
		} 
		expect -i $connection_id -re "#" 
		sleep 1
		Netgear_disconnect_switch $switch_name
	}
}

#*******************************************************************************
#
#  Function Name	: _ntgrUploadStartupConfigFile
#
#  Description    : This function is used to Upload Startup Config file from the Switch.
#         
#  Usage          : _ntgrUploadStartupConfigFile <switch_name>
#
# 
#*******************************************************************************
proc _ntgrUploadStartupConfigFile {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	if { $connection_id != -1 } { 
		set tftpServer [_ntgrGetTftpServer]
		set startConfigFile [_ntgrGetUploadStartConfigFileName $switch_name ]
		expect -i $connection_id -re "#" {	
			exp_send -i $connection_id "copy nvram:startup-config tftp://$tftpServer/$startConfigFile\r"
		}
		expect -i $connection_id -re "\(y/n\)" {
			exp_send -i $connection_id "y"
		} 
		expect -i $connection_id -re "#" 
		sleep 1
		Netgear_disconnect_switch $switch_name
	}
}

#*******************************************************************************
#
#  Function Name	: _ntgrDownloadStartupConfigFile
#
#  Description    : This function is used to Download Startup Config file to the Switch.
#         
#  Usage          : _ntgrDownloadStartupConfigFile <switch_name>
#
# 
#*******************************************************************************
proc _ntgrDownloadStartupConfigFile {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	if { $connection_id != -1 } { 
		set tftpServer [_ntgrGetTftpServer]
		set startConfigFile [_ntgrGetDownloadStartConfigFileName $switch_name ]
		expect -i $connection_id -re "#" {	
			exp_send -i $connection_id "copy tftp://$tftpServer/$startConfigFile nvram:startup-config \r"
		}
		expect -i $connection_id -re "\(y/n\)" {
			exp_send -i $connection_id "y"
		} 
		expect -i $connection_id -re "#" 
		sleep 1
		Netgear_disconnect_switch $switch_name
	}
}

#*******************************************************************************
#
#  Function Name	: _ntgrShutPort
#
#  Description    : This function is called to shutdown a given port.
#         
#  Usage          : _ntgrShutPort <switch_name> <port>
# 
#*******************************************************************************
proc _ntgrShutPort {switch_name port} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	if { $connection_id != -1 } { 
		if {[string first "POCH" $port] != -1}  {
			#set timeout 50
			# This is a port channel.
			while { 1 } { 
				exp_send -i $connection_id "show port-channel\r"
				expect -i $connection_id -re "show port-channel"
				expect -i $connection_id -re "#"
				set output $expect_out(buffer)
				exp_sleep 1

				set ifName ""
				set last [string first "$port" $output]
				if { $last != -1} {
					set regPattern "(\[^\r\n\t \]+)(\[ \t\]+)$"
					set strRegExp [string range $output 0 [expr $last - 1]]
					regexp $regPattern $strRegExp match ifName
				}
				#Sometime exepect_out(bufer) does not have the desired output. 
				#Following two line are work around for this defect. 
				if { [llength $ifName ] > 0 } { 
                        	break
			      }
			}	
		} else {
			set ifName $port
		}
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
}


#*******************************************************************************
#
#  Function Name	: _ntgrNoShutPort
#
#  Description    : This function is called to no shutdown a given port.
#         
#  Usage          : _ntgrNoShutPort <switch_name> <port>
# 
#*******************************************************************************
proc _ntgrNoShutPort {switch_name port} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	if { $connection_id != -1 } { 
		if {[string first "POCH" $port] != -1}  {
			# This is a port channel.
			#set timeout 50
			while { 1 } { 
				exp_send -i $connection_id "show port-channel\r"
				expect -i $connection_id -re "show port-channel"
				expect -i $connection_id -re "#"
				set output $expect_out(buffer)
				exp_sleep 1

				set ifName ""
				set last [string first "$port" $output]
				if { $last != -1} {
					set regPattern "(\[^\r\n\t \]+)(\[ \t\]+)$"
					set strRegExp [string range $output 0 [expr $last - 1]]
					regexp $regPattern $strRegExp match ifName
				}
				#Sometime exepect_out(bufer) does not have the desired output. 
				#Following two line are work around for this defect. 
				if { [llength $ifName ] > 0 } { 
                        	break
			      }
			}
		} else {
			set ifName $port
		}
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
}

#*******************************************************************************
#
#  Function Name	: _ntgrSetMTU
#
#  Description    : This function is called to set MTU of the switch port.
#         
#  Usage          : _ntgrSetMTU <switch_name> <port> <mtu>
# 
#*******************************************************************************
proc _ntgrSetMTU {switch_name port mtu} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	if { $connection_id != -1 } { 
		if {[string first "POCH" $port] != -1}  {
			while { 1 } { 
				exp_send -i $connection_id "show port-channel\r"
				expect -i $connection_id -re "show port-channel"
				expect -i $connection_id -re "#"
				set output $expect_out(buffer)
				exp_sleep 1

				set ifName ""
				set last [string first "$port" $output]
				if { $last != -1} {
					set regPattern "(\[^\r\n\t \]+)(\[ \t\]+)$"
					set strRegExp [string range $output 0 [expr $last - 1]]
					regexp $regPattern $strRegExp match ifName
				}
				#Sometime exepect_out(bufer) does not have the desired output. 
				#Following two line are work around for this defect. 
				if { [llength $ifName ] > 0 } { 
                        	break
			      }
			}
		} else {
		  set port [lindex $port 0]
			set ifName $port
		}

		exp_send -i $connection_id "configure\r"
		exp_sleep 1
		if { [string first "ALL" $port] == -1}  {
			exp_send -i $connection_id "interface $ifName\r"
			exp_sleep 1
		}		
		#If MTU is default unset MTU which was set before. 
		if { [string first "default" $mtu ] != -1 } { 
			exp_send -i $connection_id "no mtu\r"
			exp_sleep 1
		} else {
			exp_send -i $connection_id "mtu $mtu\r"
			exp_sleep 1
		}
		Netgear_disconnect_switch $switch_name	
	}
}

#*******************************************************************************
#
#  Function Name	: _ntgrSetSpeed
#
#  Description    : This function is called to set Speed of the switch port.
#         
#  Usage          : _ntgrSetSpeed <switch_name> <port> <speed> <duplex>
# 
#*******************************************************************************
proc _ntgrSetSpeed {switch_name port speed {duplex {}}} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	if { $connection_id != -1 } { 
		if {[string first "POCH" $port] != -1}  {
			# This is a port channel.
			exp_send -i $connection_id "show port-channel\r"
			expect -i $connection_id -re "show port-channel"
			expect -i $connection_id -re "#"
			exp_sleep 1
			set output $expect_out(buffer)

			set ifName ""
			set last [string first "$port" $output]
			if { $last != -1} {
				set regPattern "(\[^\r\n\t \]+)(\[ \t\]+)$"
				set strRegExp [string range $output 0 [expr $last - 1]]
				regexp $regPattern $strRegExp match ifName
			}
		} else {
			set ifName $port
		}

		exp_send -i $connection_id "configure\r"
		exp_sleep 1
		if { [string first "ALL" $port] == -1}  {
			exp_send -i $connection_id "interface $ifName\r"
			exp_sleep 1
			if { [string first "auto-negotiate" $speed ] == - 1 } {
				exp_send -i $connection_id "no auto-negotiate\r"
				exp_sleep 1
				# modify by jim.xie for bug-918 begin
				# when the speed more than 1000M should use command: speed auto 1000 full-duplex
				# changed on model:M7100(MLAG)
				if { $speed >= 1000 } {
				    exp_send -i $connection_id "speed auto $speed $duplex\r"
				} else {
				    exp_send -i $connection_id "speed $speed $duplex\r"
				}
				# modify by jim.xie for bug-918 end
				
				exp_sleep 1
			} else {
				exp_send -i $connection_id "auto-negotiate\r"
				exp_send -i $connection_id "speed auto\r"
				exp_sleep 1
			}
		} else {
			if { [string first "auto-negotiate" $speed ] == - 1 } {
				exp_send -i $connection_id "no auto-negotiate all\r"
				exp_sleep 5
				exp_send -i $connection_id "speed all $speed $duplex\r"
				exp_sleep 5
			} else {
				exp_send -i $connection_id "auto-negotiate all\r"
				exp_sleep 5
			}
		}		
		Netgear_disconnect_switch $switch_name	
	}
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetPortEncapsulation
#
#  Description    : This function is called to set port encapsulation.
#
#  Usage          : _ntgrSetPortEncapsulation <switch_name if_list encap>
#
#*******************************************************************************
proc _ntgrSetPortEncapsulation {switch_name if_list encap} {
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    Netgear_log_file "lib-global-command.tcl" "Setting port(s) $if_list encapsulation to $encap"
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach if_name $if_list {
        exp_send -i $cnn_id "interface $if_name\r"
        exp_sleep 1
        exp_send -i $cnn_id "encapsulation $encap\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetPortDescription
#
#  Description    : This function is called to set port description.
#
#  Usage          : _ntgrSetPortDescription <switch_name if_list description>
#
#*******************************************************************************
proc _ntgrSetPortDescription {switch_name if_list description} {
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    Netgear_log_file "lib-global-command.tcl" "Setting port(s) $if_list description to $description"
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach if_name $if_list {
        exp_send -i $cnn_id "interface $if_name\r"
        exp_sleep 1
        if {$description == {}} {
            exp_send -i $cnn_id "no description\r"
        } else {
            exp_send -i $cnn_id "description \"$description\"\r"
        }
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetPortDescriptionToMaxLength
#
#  Description    : This function is called to set port description to max length.
#
#  Usage          : _ntgrSetPortDescriptionToMaxLength <switch_name if_list>
#
#*******************************************************************************
proc _ntgrSetPortDescriptionToMaxLength {switch_name if_list} {
#    set maxlen [_ntgrGetPortDescriptionMaxLength $switch_name [lindex $if_list 0]]
    

    set maxlen [_ntgrGetPortDescriptionMaxLength $switch_name $if_list]
    set maxstr [string repeat "a" $maxlen]
    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach if_name $if_list {
        exp_send -i $cnn_id "interface $if_name\r"
        exp_sleep 1
        exp_send -i $cnn_id "description $maxstr\r"
        exp_sleep 1
        expect -i $cnn_id -re "description"
        expect -i $cnn_id -re "#"
    }
    
    Netgear_log_file "lib-global-command.tcl" "Port description to max length on $switch_name: $maxlen characters"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPortDescriptionMaxLength
#
#  Description    : This function is get port description max length
#
#  Usage          : _ntgrGetPortDescriptionMaxLength <switch_name port>
#
#*******************************************************************************
proc _ntgrGetPortDescriptionMaxLength {switch_name port} {
    
    foreach {pt} $port {
      set port $pt
    } 
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set maxstr [string repeat "a" 300]
    set maxlength 0
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "description $maxstr\r"
    exp_sleep 1
    expect -i $cnn_id -re "description"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    regexp -nocase {is (\d+) characters} $output dummy maxlength
 
    Netgear_log_file "lib-global-command.tcl" "Port description max length on $switch_name: $maxlength"
    Netgear_disconnect_switch $switch_name

    return $maxlength
}

#*******************************************************************************
#
#  Function Name  : _ntgrGenerateScriptFile
#
#  Description    : This function is used to generate the script file.
#
#  Usage          : _ntgrGenerateScriptFile <switch_name> <filename {}>
#
#*******************************************************************************
proc _ntgrGenerateScriptFile {switch_name {filename {}}} {
    
#    Netgear_connect_switch $switch_name
#    set cnn_id [_get_switch_handle $switch_name]

	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    if {$filename == {}} {
        set filename "12345.scr"
    }    
    exp_send -i $cnn_id "show running-config $filename\r"
    expect {
        -i $cnn_id -re "successfully" {}
        timeout {exp_continue}
    }
    exp_sleep 1
    exp_send -i $cnn_id "y\r"
    exp_sleep 1
#    Netgear_disconnect_switch $switch_name

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 
}

#*******************************************************************************
#
#  Function Name  : _ntgrDeleteScriptFile
#
#  Description    : This function is used to delete the script file.
#
#  Usage          : _ntgrDeleteScriptFile <switch_name> <filename {}>
#
#*******************************************************************************
proc _ntgrDeleteScriptFile {switch_name {filename {}}} {

	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
        
#    Netgear_connect_switch $switch_name
#    set cnn_id [_get_switch_handle $switch_name]

    if {$filename == {}} {
        set filename "12345.scr"
    }
    exp_send -i $cnn_id "script delete $filename\r"
    exp_sleep 1
    exp_send -i $cnn_id "y\r"
    exp_sleep 1
    
#    Netgear_disconnect_switch $switch_name

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 
}

#*******************************************************************************
#
#  Function Name  : _ntgrApplyScriptFile
#
#  Description    : This function is used to apply the script file.
#
#  Usage          : _ntgrApplyScriptFile <switch_name> <filename {}>
#
#*******************************************************************************
proc _ntgrApplyScriptFile {switch_name {filename {}}} {
    
#    Netgear_connect_switch $switch_name
#    set cnn_id [_get_switch_handle $switch_name]
    
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
        
    if {$filename == {}} {
        set filename "12345.scr"
    }
    exp_send -i $cnn_id "script apply $filename\r"
    exp_sleep 1
    exp_send -i $cnn_id "y\r"
    exp_sleep 1
    exp_send -i $cnn_id "n\r"
    exp_sleep 1
    
#    Netgear_disconnect_switch $switch_name

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 
}

#*******************************************************************************
#
#  Function Name  : _ntgrGenerateTechsupportFile
#
#  Description    : This function is used to create tech-support file.
#
#  Usage          : _ntgrGenerateTechsupportFile <switch_name>
#
#*******************************************************************************
proc _ntgrGenerateTechsupportFile {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    set output ""
    exp_send -i $cnn_id "show tech-support\r"
    exp_sleep 1
    expect -i $cnn_id -re "show tech-support"
    sleep 1
    expect -i $cnn_id -re "#"

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsSwitchRebooting
#
#  Description    : This function is used to make sure whether switch is rebooting.
#                   Using this function with switch console port, it analyses
#                   console output to judge whether the DUT is reloading.
#
#  Usage          : _ntgrIsSwitchRebooting <switch_name>
#
#*******************************************************************************
proc _ntgrIsSwitchRebooting {switch_name} {
    set switch_ip_addr [_get_switch_ip_addr $switch_name]
    set switch_ts_port [_ntgrGetTSPort $switch_name]

    # Here we assume using Terminal Server
    spawn telnet $switch_ip_addr $switch_ts_port
    set cnn_id $spawn_id
    sleep 5
    
    set rebooting 0
    set cycle 10
    for {set i 1} {$i<$cycle} {incr i} {
        exp_send -i $cnn_id "\r"
        expect {
            -i $cnn_id -re "#" { set i $cycle }
            -i $cnn_id -re " >" { set i $cycle }
            -i $cnn_id -re "User:" { set i $cycle }
            -i $cnn_id -re "More" { set i $cycle }
            -i $cnn_id "(Unit " { set rebooting 1; set i $cycle }
            -i $cnn_id "If no selection" { set rebooting 1; set i $cycle }
        }
    }

    if { $rebooting == 1} {
        Netgear_log_file "lib-global-command.tcl" "Switch is rebooting."
    } else {
        Netgear_log_file "lib-global-command.tcl" "Switch is not rebooting."
    }
    exp_close -i $cnn_id
    return $rebooting
}

proc IsApproximate {tx rx {dtErr 0.02} {allowZero 0}} {
    if { $tx < 1 && $rx < 30 } {
        if { $allowZero == 0 } {
            return 0
        } else {
            return 1
        }
    } else {
        if {$tx < 1} {set tx $rx; set rx 0}
        if {[expr abs([expr $rx - $tx])*1.0000/$tx-$dtErr] < 0} {
            return 1
        } else {
            return 0
        }
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrClearLoggingBuffer
#
#  Description    : This function is used to clear Logging Buffer.
#                  
#
#  Usage          : _ntgrClearLoggingBuffer <switch_name>
#
#*******************************************************************************
proc  _ntgrClearLoggingBuffer {switch_name } {  
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    Netgear_log_file "lib-global-command.tcl" "Clear logging buffer in switch $switch_name"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "clear loggin buffer\r"
  	sleep 5
    exp_send -i $cnn_id "y\r"
	sleep 5
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetLoggingBuffer
#
#  Description    : This function is used to get Logging Buffer.
#                  
#
#  Usage          : _ntgrGetLoggingBuffer <switch_name buffer>
#
#*******************************************************************************
proc  _ntgrGetLoggingBuffer {switch_name buffer} {  
    upvar 1 $buffer log_buff
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name] 
    Netgear_log_file "lib-global-command.tcl" "Clear logging buffer in switch $switch_name"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "show logging buffer\r"
    sleep 2
    expect -i $cnn_id -re "#"
    set log_buff $expect_out(buffer)] 
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrClearTrapLog
#
#  Description    : This function is used to clear traplog.
#                  
#
#  Usage          : _ntgrClearTrapLog <switch_name>
#
#*******************************************************************************
proc  _ntgrClearTrapLog {switch_name } {  
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    Netgear_log_file "lib-global-command.tcl" "Clear traplog in switch $switch_name"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "clear traplog\r"
  	sleep 3
    exp_send -i $cnn_id "y\r"
	  sleep 3
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetTrapLog
#
#  Description    : This function is used to traplog.
#                  
#
#  Usage          : _ntgrGetTrapLog <switch_name buffer>
#
#*******************************************************************************
proc _ntgrGetTrapLog {switch_name buffer} {  
    upvar 1 $buffer log_buff
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name] 
    Netgear_log_file "lib-global-command.tcl" "Get traplog in $switch_name"
    
    set log_buff ""
    exp_send -i $cnn_id "show logging traplog\r"
    exp_sleep 1
    expect -i $cnn_id -re "show logging traplog"
    expect {
        -i $cnn_id -re "#" { append log_buff $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append log_buff $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }

    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetPortPhyStatus
#
#  Description    : This function is used to get port physical status.
#
#  Usage          : _ntgrGetPortPhyStatus <switch_name> <port>
#
#*******************************************************************************
proc _ntgrGetPortPhyStatus {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show port $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show port $port"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)    
    set output_list [split $output "\n"]
    
    set index_start 0
    set index_end 0
    set expect_str ""
    foreach item_str $output_list {
        set item_str [string trim $item_str]
        if {[regexp {^-+} $item_str]} {
            # modify by jim.xie for M6100 begin
            set str_length [llength $item_str]
            if {$str_length == 10} {
                set flag_str "[lindex $item_str 0] [lindex $item_str 1] [lindex $item_str 2] [lindex $item_str 3] [lindex $item_str 4]"
                set index_start [expr [string length $flag_str] + 1]
                append flag_str " [lindex $item_str 5]"
                set index_end [expr [string length $flag_str] - 1]
            } else {
                set flag_str "[lindex $item_str 0] [lindex $item_str 1] [lindex $item_str 2] [lindex $item_str 3]"
                set index_start [expr [string length $flag_str] + 1]
                append flag_str " [lindex $item_str 4]"
                set index_end [expr [string length $flag_str] - 1]
            }
            # modify by jim.xie for M6100 end
        }
        
        if {[regexp $port $item_str]} {
            set expect_str $item_str
            break
        }
    }
    set result_str [string trim [string range $expect_str $index_start $index_end]]

    Netgear_log_file "lib-global-command.tcl" "Port physical status on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name

    return $result_str
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPortLinkStatus
#
#  Description    : This function is used to get port physical status.
#
#  Usage          : _ntgrGetPortLinkStatus <switch_name> <port>
#
#*******************************************************************************
proc _ntgrGetPortLinkStatus {switch_name port} {
	set switch_ts_port [_ntgrGetTSPort $switch_name]
	if {$switch_ts_port != 60000 } {
	set result_str [_ntgrShowPort $switch_name $port]
	} else {
	#add by zhenwei.li for check TelnetPort 60000 switch port status.begin
	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
	exp_send -i $cnn_id "show interfaces switchport $port\r"
    exp_sleep 1
	expect -i $cnn_id -re "show interfaces switchport $port"
	expect {
               -i $cnn_id -re "#" { append output $expect_out(buffer) }
               -i $cnn_id -re "More: <space>" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
         }   
    }
	
    set tmpList [split $output \n]
	foreach iList $tmpList {
        if {[regexp -all -nocase "Operational Mode" $iList]} {
		set result_str [lindex $iList 2]
        } else {
			set result_str 0
		}
    }
	Netgear_log_file "lib-global-command.tcl" "Port physical status on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name
	if {$result_str==0} {
		set result_str [_ntgrShowPort $switch_name $port]
	}
	return $result_str
	}
	#add by zhenwei.li for check TelnetPort 60000 switch port status.end
}
#*******************************************************************************
#
#  Function Name  : _ntgrShowPort
#
#  Description    : This function is used show port command to get port status
#
#  Usage          : _ntgrShowPort <switch_name> <port>
#
#*******************************************************************************

proc _ntgrShowPort {switch_name port} {
	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show port $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show port $port"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)    
    set output_list [split $output "\n"]
    
    set index_start 0
    set index_end 0
    set expect_str ""
    foreach item_str $output_list {
        set item_str [string trim $item_str]
        if {[regexp {^-+} $item_str]} {
            # modify by jim.xie for M6100 begin
            set str_length [llength $item_str]
            if {$str_length == 10} {
                set flag_str "[lindex $item_str 0] [lindex $item_str 1] [lindex $item_str 2] [lindex $item_str 3] [lindex $item_str 4] [lindex $item_str 5]"
                set index_start [expr [string length $flag_str] + 1]
                append flag_str " [lindex $item_str 6]"
                set index_end [expr [string length $flag_str] - 1]
            } else {
                set flag_str "[lindex $item_str 0] [lindex $item_str 1] [lindex $item_str 2] [lindex $item_str 3] [lindex $item_str 4]"
                set index_start [expr [string length $flag_str] + 1]
                append flag_str " [lindex $item_str 5]"
                set index_end [expr [string length $flag_str] - 1]
            }
            # modify by jim.xie for M6100 end
        }
        
        if {[regexp $port $item_str]} {
            set expect_str $item_str
            break
        }
    }
    set result_str [string trim [string range $expect_str $index_start $index_end]]

    Netgear_log_file "lib-global-command.tcl" "Port physical status on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name

    return $result_str
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPortPhyMode
#
#  Description    : This function is used to get port physical mode.
#
#  Usage          : _ntgrGetPortPhyMode <switch_name> <port>
#
#*******************************************************************************
proc _ntgrGetPortPhyMode {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show port $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show port $port"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)    
    set output_list [split $output "\n"]
    
    set index_start 0
    set index_end 0
    set expect_str ""
    foreach item_str $output_list {
        set item_str [string trim $item_str]
        if {[regexp {^-+} $item_str]} {
            # modify by jim.xie for M6100 begin
            set str_length [llength $item_str]
            if {$str_length == 10} {
                set flag_str "[lindex $item_str 0] [lindex $item_str 1] [lindex $item_str 2] [lindex $item_str 3]"
                set index_start [expr [string length $flag_str] + 1]
                append flag_str " [lindex $item_str 4]"
                set index_end [expr [string length $flag_str] - 1]
            } else {
                set flag_str "[lindex $item_str 0] [lindex $item_str 1] [lindex $item_str 2]"
                set index_start [expr [string length $flag_str] + 1]
                append flag_str " [lindex $item_str 3]"
                set index_end [expr [string length $flag_str] - 1]
            }
            # modify by jim.xie for M6100 end
        }
        
        if {[regexp $port $item_str]} {
            set expect_str $item_str
            break
        }
    }
    set result_str [string trim [string range $expect_str $index_start $index_end]]

    Netgear_log_file "lib-global-command.tcl" "Port physical mode on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name

    return $result_str
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetPortMtu
#
#  Description    : This function is used to get port mtu.
#
#  Usage          : _ntgrGetPortMtu <switch_name> <port>
#
#*******************************************************************************
proc _ntgrGetPortMtu {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show interface ethernet $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show interface ethernet $port"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }    

    set result_str ""
    #modify by luiz.yao start 2016.6.14
    #regexp -nocase {Max Frame Size\.+\s+(\d+)} $output match result_str       
    regexp -nocase {Maximum Transmit Unit\.+\s+(\d+)} $output match result_str
    #modify by luiz.yao end 2016.6.14
    
    Netgear_log_file "lib-global-command.tcl" "Port Mtu on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name

    return $result_str
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetPortDescription
#
#  Description    : This function is used to get port description.
#
#  Usage          : _ntgrGetPortDescription <switch_name> <port>
#
#*******************************************************************************
proc _ntgrGetPortDescription {switch_name port} {
    
    set port [lindex $port 0]
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
        
    exp_send -i $cnn_id "show port description $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show port description $port"
    expect -i $cnn_id -re "#"   

    set output $expect_out(buffer)
    
    set result_str ""
    regexp -nocase {Description\.+\s+(.+)MAC} $output match result_str
    set result_str [string trim $result_str]
    regsub -all {\r|\n} $result_str "" result_str
    
    Netgear_log_file "lib-global-command.tcl" "Port Description on $switch_name: $result_str"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }

    return $result_str
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPortStatistics
#
#  Description    : This function is used to get port statistics.
#
#  Usage          : _ntgrGetPortStatistics <switch_name> <port> <pattern>
#
#*******************************************************************************
proc _ntgrGetPortStatistics {switch_name port pattern} {
    set match [string first "lag" $port]
    if {$match != -1} {
        set start [expr $match + 3]
        set lagid [string range $port $start end]
        set lagid [string trimright $lagid "\}"]
        set lagid [string trim $lagid]
      
        set modename [_get_switch_model $switch_name]
        if {[regexp -nocase {S$|M5300|XCM.*} $modename]} {
            set prefix "0/3/"
        } else {
            set prefix "3/"
        }
        set port $prefix$lagid
    }
    set cmds "show interface $port"
    set retStr ""
    CALCheckExpect $switch_name $cmds retStr
    set tmpList [split $retStr \n]
    set stat 0
    foreach iList $tmpList {
        if {[regexp -nocase -- $pattern $iList]} {
            set stat [lindex [eval list $iList] end]
            Netgear_log_file "lib-global-command.tcl" "Get Portstatistic of port $port $pattern - $stat"
            break
        } 
    }
    return $stat
}


#*******************************************************************************
#
#  Function Name  : _ntgrSetPortRangeMtu
#
#  Description    : This function is used to set port range mtu.
#
#  Usage          : _ntgrSetPortRangeMtu <switch_name> <portrange> <mtu>
#
#*******************************************************************************
proc _ntgrSetPortRangeMtu {switch_name portrange mtu} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    set ver [_ntgrGetVersionReleasebyConnectionID $cnn_id]
    
    exp_send -i $cnn_id "configure\r"
		exp_sleep 1		
		if { $ver >= 10 } {
		    exp_send -i $cnn_id "interface $portrange\r"
		} else { 
		    exp_send -i $cnn_id "interface range $portrange\r"
	  }
		exp_sleep 1
		if { [string first "default" $mtu ] != -1 } { 
			exp_send -i $cnn_id "no mtu\r"
			exp_sleep 1
			expect -i $cnn_id -re "#"
		} else {
			exp_send -i $cnn_id "mtu $mtu\r"
			exp_sleep 1
			expect -i $cnn_id -re "#"
		}    
    
    Netgear_log_file "lib-global-command.tcl" "set port range $portrange mtu $mtu"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetPortRangeSpeed
#
#  Description    : This function is used to set port range speed.
#
#  Usage          : _ntgrSetPortRangeSpeed <switch_name> <portrange> <speed> <duplex>
#
#*******************************************************************************
proc _ntgrSetPortRangeSpeed {switch_name portrange speed duplex} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    set ver [_ntgrGetVersionReleasebyConnectionID $cnn_id]
    
    exp_send -i $cnn_id "configure\r"
		exp_sleep 1		
		if { $ver >= 10 } {
		    exp_send -i $cnn_id "interface $portrange\r"
		} else { 
		    exp_send -i $cnn_id "interface range $portrange\r"
	  }
		exp_sleep 1
		
		if { [string first "auto-negotiate" $speed ] != -1 } { 
			exp_send -i $cnn_id "auto-negotiate\r"
			exp_sleep 1
			expect -i $cnn_id -re "#"
		} else {
			exp_send -i $cnn_id "no auto-negotiate\r"
			exp_sleep 1
			expect -i $cnn_id -re "#"
			exp_send -i $cnn_id "speed $speed $duplex\r"
			exp_sleep 1
			expect -i $cnn_id -re "#"
		}    
    
    Netgear_log_file "lib-global-command.tcl" "set port range $portrange speed $speed $duplex"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrGetSoftwareVersion
#
#  Description    : This function is used to get softerware version.
#
#  Usage          : _ntgrGetSoftwareVersion <switch_name>
#
#*******************************************************************************
proc _ntgrGetSoftwareVersion {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    set result_str [_ntgrGetVersionReleasebyConnectionID $cnn_id]
    Netgear_log_file "lib-global-command.tcl" "Softerware Version on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name
    return $result_str
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetCPU
#
#  Description    : This function is used to get cpu.
#
#  Usage          : _ntgrGetCPU <switch_name>
#
#*******************************************************************************
proc _ntgrGetCPU {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show process cpu\r"
    exp_sleep 1
    expect -i $cnn_id -re "show process cpu"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }    

    set result_str "100.00"
    regexp -nocase {Total CPU Utilization\s+([^%]+)} $output match result_str
    
    Netgear_log_file "lib-global-command.tcl" "cpu on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name

    return $result_str
}









#*******************************************************************************
#
#  Function Name  : _ntgrGetMasterID
#
#  Description    : This function is used to get switcher master id.
#
#  Usage          : _ntgrGetMasterID <switch_name>
#
#*******************************************************************************
proc _ntgrGetMasterID {switch_name} {
    
    set modename [_get_switch_model $switch_name]
    if {[regexp -nocase {XCM.?} $modename]} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
    
        set output ""
    
        exp_send -i $cnn_id "show chassis \r"
        exp_sleep 1
        expect -i $cnn_id -re "show chassis"
        exp_sleep 1
        expect -i $cnn_id -re "#"   

        set output $expect_out(buffer)
    
        set id 0
        regexp -nocase -line {^(\d+)\s+ Primary} $output dummy id
    
        Netgear_log_file "lib-global-command.tcl" "get master id on $switch_name"
        Netgear_disconnect_switch $switch_name
        
        return $id    
    } elseif {[regexp -nocase {S$|M5300} $modename]} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
    
        set output ""
    
        exp_send -i $cnn_id "show switch\r"
        exp_sleep 1
        expect -i $cnn_id -re "show switch"
        exp_sleep 1
        expect -i $cnn_id -re "#"   

        set output $expect_out(buffer)
    
        set id 0
        regexp -nocase -line {^(\d+)\s+Mgmt Sw\s} $output dummy id
    
        Netgear_log_file "lib-global-command.tcl" "get master id on $switch_name"
        Netgear_disconnect_switch $switch_name
        
        return $id    
    } else {
        Netgear_log_file "lib-global-command.tcl" "get master id on $switch_name"
        return ""
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrDeleteImage
#
#  Description    : This function is used to delete image.
#
#  Usage          : _ntgrDeleteImage <switch_name> <imagename> <unitid>
#
#*******************************************************************************
proc _ntgrDeleteImage {switch_name imagename {unitid ""}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "delete $unitid $imagename\r"
    exp_sleep 1
    expect -i $cnn_id -re "delete $unitid $imagename"
    exp_sleep 1
    
    set timeout 120
    expect -i $cnn_id -re "#"
    
    Netgear_log_file "lib-global-command.tcl" "delete image on $switch_name"
    Netgear_disconnect_switch $switch_name
}

proc _ntgrDeleteImageCheckConfigError {switch_name imagename {unitid ""}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "delete $unitid $imagename\r"
    exp_sleep 1
    expect -i $cnn_id -re "delete $unitid $imagename"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    if {[regexp -nocase {Error} $output]} {
        set result_str 1
    } else {
        set result_str 0
    }
    
    Netgear_log_file "lib-global-command.tcl" "delete image on $switch_name"
    Netgear_disconnect_switch $switch_name
    
    return $result_str
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetActiveImage
#
#  Description    : This function is used to get active image.
#
#  Usage          : _ntgrGetActiveImage <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrGetActiveImage {switch_name {unitid ""}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    if {[string length [string trim $unitid]] == 0} {
        set regStr "^\\d+ "
    } else {
        set regStr "^$unitid "
    }
    
    set output ""
    
    exp_send -i $cnn_id "show bootvar\r"
    exp_sleep 1
    expect -i $cnn_id -re "show bootvar"
    exp_sleep 1
    expect -i $cnn_id -re "#"   

    set output $expect_out(buffer)
    
    set result_str ""
    if {[regexp {image1} $output]} {
        set output [split $output "\n"]
        foreach output_line $output {        
            set output_line [string trim $output_line]
            if {[regexp $regStr $output_line]} {
                set result_str [lindex $output_line 3]
            }
        }
    } else {
        set result_str "active"
    }
    
    Netgear_log_file "lib-global-command.tcl" "get active image on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name

    return $result_str
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetImageVer
#
#  Description    : This function is used to get two images version.
#
#  Usage          : _ntgrGetImageVer <switch_name> <ver1> <ver2> <unitid>
#
#*******************************************************************************
proc _ntgrGetImageVer {switch_name ver1 ver2 {unitid ""}} {
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    if {[string length [string trim $unitid]] == 0} {
        set regStr "^\\d+ "
    } else {
        set regStr "^$unitid "
    }
    
    set output ""
    upvar $ver1 _ver1
    upvar $ver2 _ver2
    
    exp_send -i $cnn_id "show bootvar\r"
    exp_sleep 1
    expect -i $cnn_id -re "show bootvar"
    expect -i $cnn_id -re "#"   

    set output $expect_out(buffer)

    set output [split $output "\n"]
    foreach output_line $output {
        set output_line [string trim $output_line]
        if {[regexp $regStr $output_line]} {
            set _ver1 [lindex $output_line 1]
            set _ver2 [lindex $output_line 2]
        }
    }

    Netgear_log_file "lib-global-command.tcl" "get two images version on $switch_name"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetImageDescription
#
#  Description    : This function is used to set image's description.
#
#  Usage          : _ntgrSetImageDescription <switch_name> <imagename> <description>
#
#*******************************************************************************
proc _ntgrSetImageDescription {switch_name imagename description} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "filedescr $imagename $description\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-global-command.tcl" "set image description on $switch_name"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetImageDescription
#
#  Description    : This function is used to get two images description.
#
#  Usage          : _ntgrGetImageDescription <switch_name> <ver1> <ver2>
#
#*******************************************************************************
proc _ntgrGetImageDescription {switch_name des1 des2} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    set output ""
    upvar $des1 _des1
    upvar $des2 _des2
    
    exp_send -i $cnn_id "show bootvar\r"
    exp_sleep 1
    expect -i $cnn_id -re "show bootvar"
    expect -i $cnn_id -re "#"   

    set output $expect_out(buffer)

    regexp -nocase -line {^\s*(image1|active) : ([^\r\n]+)} $output dummy dummy1 _des1
    regexp -nocase -line {^\s*(image2|backup) : ([^\r\n]+)} $output dummy dummy1 _des2

    Netgear_log_file "lib-global-command.tcl" "get two images description on $switch_name"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetSysDescription
#
#  Description    : This function is used to get system description of a switch.
#
#  Usage          : _ntgrGetSysDescription <switch_name>
#
#*******************************************************************************
proc _ntgrGetSysDescription {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show sysinfo\r"
    exp_sleep 1
    expect -i $cnn_id -re "show sysinfo"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }
    }
        
    set result ""
    set start_pos -1
    set end_pos -1
    set start_pos [string first "System Description." $output]
    if {$start_pos >= 0} {
        set start_pos [expr $start_pos + [string length "System Description."]]
        set end_pos [string first "System Name." $output]
    }
    set result [string range $output $start_pos [expr $end_pos - 1]]
    regsub -all {\r|\n} $result "" result
    regsub -all {\s+} $result " " result
    set result [string trim $result " ."]
    
    Netgear_log_file "lib-global-command.tcl" "get system description address on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetSystemClock
#
#  Description    : This function is used to get system clock.
#
#  Usage          : _ntgrGetSystemClock <switch_name>
#
#*******************************************************************************
proc _ntgrGetSystemClock {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show clock\r"
    exp_sleep 1
    expect -i $cnn_id -re "show clock"
    expect -i $cnn_id -re "#"

    set output $expect_out(buffer)
    set output [string trim $output]
    set output_list [split $output "\n"]
    set output [string trim [lindex $output_list 0]]

    Netgear_log_file "lib-global-command.tcl" "get system clock on $switch_name"
    Netgear_disconnect_switch $switch_name
    
    return $output
}

#*******************************************************************************
#
#  Function Name  : _ntgrFormatSystemClock
#
#  Description    : This function is used to format system clock string to time mode:  hh:mm day month year
#
#  Usage          : _ntgrClockFormat <sysclock> <seconds>
#
#*******************************************************************************
proc _ntgrClockFormat {sysclock {seconds ""}} {
    set intTime [clock scan $sysclock]
    
    if {$seconds != ""} {
        set intTime [expr $intTime + $seconds]
    }
    
    set timeStr [clock format $intTime -format "%H:%M %d %b %Y"]
    return $timeStr
}

#*******************************************************************************
#
#  Function Name  : _ntgrIpv4AddrIncr
#
#  Description    : This function is used incr ip address a num
#
#  Usage          : _ntgrIpv4AddrIncr <ip_address> <num> 
#
#  Author         : Luiz.yao 
#
#  DATE           : 2015/9/25
#
#*******************************************************************************
proc _ntgrIpv4AddrIncr {ip_address {num 1}} {
    set ipLst [split $ip_address .] 
    set retLst [list]
    for {set i [expr [llength $ipLst] - 1]} {$i >= 0} {incr i -1;set num [expr int(([lindex $ipLst $i] + $num)/256)]} {
        set retLst [linsert $retLst 0 [expr ([lindex $ipLst $i] + $num)%256]]
    }
    
    return [join $retLst .]
}