#!/bin/sh
################################################################################   
#
#  File Name		  : lib-stp.tcl
#
#  Description      :
#         This TCL contains functions to configure spanning-tree
#

#  Revision History :
#  Revision   Date         Programmer        Description
#  -----------------------------------------------------------------------------
#    1.0      18-May-06     Scott Zhang       Created
#    1.1      27-June-06    Selva Kumar    1. Procedures _ntgrMstpAddInstance and 
#                                             _ntgrMstpAddVlan added to support MSTP.
#                                          2. _ntgrStpConfigCost was assining first cost
#                                             in the list to all the ports, 
#                                             it is modifed to assign costs to the 
#                                             ports respectively.  
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: _ntgrStpEnableSwitch
#
#  Description    : This function is called to enable spanning-tree on netgear
#		     	        switch.
#         
#  Usage          : _ntgrStpEnableSwitch <switch_name>
#
#*******************************************************************************
proc _ntgrStpEnableSwitch {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]

	expect -i $connection_id -re "#" {
		exp_send -i $connection_id "configure\r"
		expect -i $connection_id -re "#" {
			exp_send -i $connection_id "spanning-tree\r"
		}
		expect -i $connection_id -re "#" {
			exp_send -i $connection_id "exit\r"
		}
	}
	
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrStpEnableSwitchwithID
#
#  Description    : This function is called to enable spanning-tree on netgear
#		     	        switch.
#         
#  Usage          : _ntgrCStpEnableSwitchwithID <switch_name protocol_id>
#                 protocol_id 1 classic stp
#                 protocol_id 2 rapid stp
#                 protocol_id 3 multiple stp
#
#*******************************************************************************
proc _ntgrStpEnableSwitchwithID {switch_name id} {
  # add by jim.xie for bug-1270 new gui for MR 11.0 begin
  set dut_software_version [_ntgrGetSoftwareVersion $switch_name]
  set ver [string trim $dut_software_version]
  regexp {^\d+} $ver ver_first
  # add by jim.xie for bug-1270 new gui for MR 11.0 end
  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]

	expect -i $connection_id -re "#" {
		exp_send -i $connection_id "configure\r"
		expect -i $connection_id -re "#" {
			exp_send -i $connection_id "spanning-tree\r"
		}
		expect -i $connection_id -re "#" {
      if {$ver_first >= 11} {
          switch -exact -- $id {
		          1 {exp_send -i $connection_id "spanning-tree mode stp\r"}
		          2 {exp_send -i $connection_id "spanning-tree mode rstp\r"}
		          3 {exp_send -i $connection_id "spanning-tree mode mst\r"}
				  4 {exp_send -i $connection_id "spanning-tree mode pvst\r"}
				  5 {exp_send -i $connection_id "spanning-tree mode rapid-pvst\r"}
		          default {}
		       }
      } else {
		      switch -exact -- $id {
		          1 {exp_send -i $connection_id "spanning-tree forceversion 802.1d\r"}
		          2 {exp_send -i $connection_id "spanning-tree forceversion 802.1w\r"}
		          3 {exp_send -i $connection_id "spanning-tree forceversion 802.1s\r"}
		          default {}
		       }
      }
		}
		expect -i $connection_id -re "#" {
			exp_send -i $connection_id "exit\r"
		}
	}
	
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrStpDisableSwitch
#
#  Description    : This function disable spt of the switch
#         
#  Usage          : _ntgrStpDisableSwitch <switch_name>
#
#*******************************************************************************

proc _ntgrStpDisableSwitch {switch_name} {
   Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r"
  	sleep 1
 	exp_send -i $connection_id "no spanning-tree\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrStpEnablePort
#
#  Description    : This function is called to enable spanning-tree on netgear
#		     	        switch's special ports.
#  
#  Usage          : _ntgrStpEnablePort <switch_name> <portlist>
#
# 				Note	: portlist should be a style of "0/1 0/2 0/3 ..."
# 
#*******************************************************************************
proc _ntgrStpEnablePort {switch_name portlist} {
   Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r"
   if { [string first "all" $portlist] != -1 } {
   	exp_send -i $connection_id "spanning-tree port mode all\r"
	 	sleep 1
	 	exp_send -i $connection_id "exit\r"
   } else {
   	foreach	port  $portlist {
	        # modify by jim.xie begin
			if {[string first "POCH" $port] != -1 } {
				#This is a port channel,get the logical interface name first
				set port [getChannelLogicalIfName $switch_name $port]
				exp_send -i $connection_id "exit\r"
				##set port [_ntgrGetChannelLogicalIfName $connection_id $port]
				set port [lindex $port 0]
				exp_send -i $connection_id "configure\r"
				sleep 1
			}
			# modify by jim.xie end
			exp_send -i $connection_id "interface $port\r"
			sleep 1
			exp_send -i $connection_id "spanning-tree port mode\r"
			sleep 1
			exp_send -i $connection_id "exit\r"
		}
		sleep 1	
   }	
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrStpDisablePort
#
#  Description    : This function disable stp to the switch's port
#         
#  Usage          : _ntgrStpDisablePort <switch_name><portlist>
#
#*******************************************************************************
proc _ntgrStpDisablePort {switch_name portlist} {
   Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r"
   if { [string first "all" $portlist] != -1 } {
   	exp_send -i $connection_id "no spanning-tree port mode all\r"
	 	sleep 1
	 	exp_send -i $connection_id "exit\r"
   } else {
   	foreach	port  $portlist {    	
			if { [string first "POCH" $port] != -1 } {
				#This is a port channel,get the logical interface name first
				set port [getChannelLogicalIfName $switch_name $port]
			}
			exp_send -i $connection_id "interface $port\r"
			sleep 1
			exp_send -i $connection_id "no spanning-tree port mode\r"
			sleep 1
			exp_send -i $connection_id "exit\r"
		}
		sleep 1	
   }	
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrStpConfigBridgePriority
#
#  Description    : This function is called to config the bridge's priority
#		     	        for spanning-tree.
#  
#  Usage          : _ntgrStpConfigBridgePriority <switch_name> <priority> ?mst?
#
# 				Note	: if mst is omitted,it assumes mst is 0
# 
#*******************************************************************************
proc _ntgrStpConfigBridgePriority {switch_name priority {mst 0}} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]

	expect -i $connection_id -re "#" {
		exp_send -i $connection_id "configure\r"
		expect -i $connection_id -re "#" {
		  if {![string equal $priority "default"]} {
			  exp_send -i $connection_id "spanning-tree mst priority $mst $priority\r"
			} else {
			  exp_send -i $connection_id "no spanning-tree mst priority $mst\r"
			}
			exp_sleep 1
			exp_send -i $connection_id "exit\r"
			expect -i $connection_id "exit"
		}
	}
	Netgear_disconnect_switch $switch_name
	return  $expect_out(buffer)
}

#*******************************************************************************
#
#  Function Name	: _ntgrStpSetBridgePriorityonInterface
#
#  Description    : This function is called to config the bridge's priority
#		     	        for spanning-tree on Interface.
#  
#  Usage          :_ntgrStpSetBridgePriorityinInterface <switch_name> <priority> ?mst? <interface>
#
# 				Note	: if mst is omitted,it assumes mst is 0
# 
#*******************************************************************************
proc _ntgrStpSetBridgePriorityonInterface {switch_name interface priority {mst 0} } {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]

	expect -i $connection_id -re "#" {
  		exp_send -i $connection_id "configure\r"
  		expect -i $connection_id -re "#" {
  		    exp_send -i $connection_id "interface $interface\r"
  		    expect -i $connection_id -re "#" {    
        		  if {![string equal $priority "default"]} {
        			  exp_send -i $connection_id "spanning-tree mst $mst port-priority $priority\r"
        			} else {
        			  exp_send -i $connection_id "no spanning-tree mst $mst port-priority\r"
        			}
        			exp_sleep 1
        			exp_send -i $connection_id "exit\r"
        			expect -i $connection_id "exit"
        	}
  		}
	}
	Netgear_disconnect_switch $switch_name
	return  $expect_out(buffer)
}

#*******************************************************************************
#
#  Function Name	: _ntgrStpConfigCost
#
#  Description    : This function configure spanning-tree on a set of ports
#         
#  Usage          : _ntgrStpConfigCost <switch_name> <portlist> <costlist> [mstpid ]
#
#*******************************************************************************
proc _ntgrStpConfigCost {switch_name portlist costlist {mstpid 0 }} {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
    
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"

    if { [string first "all" $portlist] != -1 } {
   	    puts "Warning: not supportted interface name 'all'."
    } else {
	  foreach port $portlist cost $costlist { 	
  	    if { [string first "POCH" $port] != -1 } {
      		#This is a port channel,get the logical interface name first
      		set port [getChannelLogicalIfName $switch_name $port]
      	}
  	    sleep 1
  	    exp_send -i $connection_id "interface $port\r"
  	    sleep 1
  	     if {![string equal $cost "default"]} {
  	       exp_send -i $connection_id "spanning-tree mst $mstpid cost $cost\r"
  	    } else {
  	       exp_send -i $connection_id "no spanning-tree mst $mstpid cost\r"
  	    }   
  	    sleep 1
  	    exp_send -i $connection_id "exit\r"
  	    sleep 1
      	}
      }
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrStpConfigMaxAge
#
#  Description    : This function configure spt max-age 
#         
#  Usage          : _ntgrStpConfigMaxAge <switch_name> <max_age>
#
#*******************************************************************************
proc _ntgrStpConfigMaxAge {switch_name max_age} {
   Netgear_connect_switch $switch_name
   set connection_id [_get_switch_handle $switch_name]
   
	 expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r" 	
   sleep 1
 	exp_send -i $connection_id "spanning-tree max-age $max_age\r"
 	sleep 1
	exp_send -i $connection_id "exit\r"
	sleep 1
	expect -i $connection_id "exit"
	Netgear_disconnect_switch $switch_name
	return  $expect_out(buffer)
}

#*******************************************************************************
#
#  Function Name	: _ntgrStpConfigForwardTime
#
#  Description    : This function configure spt forward time
#         
#  Usage          : _ntgrStpConfigForwardTime <switch_name> <forward_time>
#
#*******************************************************************************
proc _ntgrStpConfigForwardTime {switch_name forward_time} {
   Netgear_connect_switch $switch_name
   set connection_id [_get_switch_handle $switch_name]
   
	expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r" 	
   sleep 1
 	exp_send -i $connection_id "spanning-tree forward-time $forward_time\r"
 	sleep 1
	exp_send -i $connection_id "exit\r"
	sleep 1
	expect -i $connection_id "exit"
	Netgear_disconnect_switch $switch_name
	return  $expect_out(buffer)
}

#*******************************************************************************
#
#  Function Name	: _ntgrStpConfigHelloTime
#
#  Description    : This function configure spt hello time
#         
#  Usage          : _ntgrStpConfigHelloTime <switch_name> <hello_time>
#
#*******************************************************************************
proc _ntgrStpConfigHelloTime {switch_name hello_time} {
   Netgear_connect_switch $switch_name
   set connection_id [_get_switch_handle $switch_name]
   
	expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r" 	
   sleep 1
 	exp_send -i $connection_id "spanning-tree hello-time $hello_time\r"
 	sleep 1
	exp_send -i $connection_id "exit\r"
	sleep 1
	expect -i $connection_id "exit"
	Netgear_disconnect_switch $switch_name
	return  $expect_out(buffer)
}

#*******************************************************************************
#
#  Function Name	: _ntgrMstpAddInstance
#
#  Description    : This function adds multiple spanning tree instance to the switch
#         
#  Usage          : _ntgrMstpAddInstance <switch_name> <mstidlist> 
#
#*******************************************************************************
proc _ntgrMstpAddInstance {switch_name mstidlist } {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    foreach mstid $mstidlist { 
	exp_send -i $connection_id "spanning-tree mst instance $mstid \r"
	sleep 1
    }
    Netgear_disconnect_switch $switch_name
}
#*******************************************************************************
#
#  Function Name	: _ntgrMstpNoAddInstance
#
#  Description    : This function don't adds multiple spanning tree instance to the switch
#         
#  Usage          : _ntgrMstpNoAddInstance <switch_name> <mstidlist> 
#
#*******************************************************************************
proc _ntgrMstpNoAddInstance {switch_name mstidlist } {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    foreach mstid $mstidlist { 
	exp_send -i $connection_id "no spanning-tree mst instance $mstid \r"
	sleep 1
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrMstpAddVlan
#
#  Description    : This function adds an association between multiple spanning tree instance and a VLAN 
#         
#  Usage          : _ntgrMstpAddVlan <switch_name> <mstidlist> <vlanidlist> 
#
#*******************************************************************************
proc _ntgrMstpAddVlan {switch_name mstidlist vlanidlist } {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    foreach mstid $mstidlist vlanids $vlanidlist { 
	foreach vlanid $vlanids { 
	    exp_send -i $connection_id "spanning-tree mst vlan $mstid $vlanid\r"
	    sleep 1
	}
    }
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name	: _ntgrMstpNoAddVlan
#
#  Description    : This function don't adds an association between multiple spanning tree instance and a VLAN 
#         
#  Usage          : _ntgrMstpNoAddVlan <switch_name> <mstidlist> <vlanidlist> 
#
#*******************************************************************************
proc _ntgrMstpNoAddVlan {switch_name mstidlist vlanidlist } {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    foreach mstid $mstidlist vlanids $vlanidlist { 
	foreach vlanid $vlanids { 
	    exp_send -i $connection_id "no spanning-tree mst vlan $mstid $vlanid\r"
	    sleep 1
	}
    }
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrStpDisablePortEdgeport
#
#  Description    : This function is used to unset port edgeport.
#
#  Usage          : _ntgrStpDisablePortEdgeport <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrStpDisablePortEdgeport {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
		exp_sleep 1
		foreach port $port_list {
	      exp_send -i $cnn_id "interface $port\r"
		    exp_sleep 1
		    expect -i $cnn_id -re "#"
		    exp_send -i $cnn_id "no spanning-tree edgeport\r"
		    exp_sleep 1
		    expect -i $cnn_id -re "#"
		    
		    exp_send -i $cnn_id "exit\r"
		    exp_sleep 1
		}
		
    Netgear_log_file "lib-global-command.tcl" "unset port edgeport"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrGetMstpPortStpState
#
#  Description    : This function is used to get mstp port stp state.
#
#  Usage          : _ntgrGetMstpPortStpState <switch_name> <port> <mst>
#
#*******************************************************************************
proc _ntgrGetMstpPortStpState {switch_name port {mst 0}} {

    set port [lindex $port 0]
    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show spanning-tree mst port summary $mst $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show spanning-tree mst port summary $mst $port"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)    
    set output_list [split $output "\n"]
    
    set index_start 0
    set index_end 0
    set expect_str ""
    foreach item_str $output_list {
        set item_str [string trim $item_str]
        if {[regexp {^-+} $item_str]} {
            set flag_str "[lindex $item_str 0] [lindex $item_str 1] [lindex $item_str 2]"
            set index_start [expr [string length $flag_str] + 1]
            append flag_str " [lindex $item_str 3]"
            set index_end [expr [string length $flag_str] - 1]
        }
        
        if {[regexp $port $item_str]} {
            set expect_str $item_str
            break
        }
    }
    set result_str [string trim [string range $expect_str $index_start $index_end]]

    Netgear_log_file "lib-global-command.tcl" "mstp port stp state on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name

    return $result_str
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDesignatedRoot
#
#  Description    : This function is used to get Mac address of STP root switch.
#
#  Usage          : _ntgrGetDesignatedRoot <switch_name>
#
#*******************************************************************************
proc _ntgrGetDesignatedRoot {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show spanning-tree\r"
    exp_sleep 1
    expect -i $cnn_id -re "show spanning-tree"
    expect -i $cnn_id -re "Port Trigger"
    
    set output $expect_out(buffer)
    set regSWMAC "(Designated Root\[.\]+)(\[ ]+)(\[0-9a-fA-F:\]+)"
    regexp $regSWMAC $output match str1 str2 str3
    
    Netgear_log_file "lib-stp" "STP Root Switch Mac address: $str3"
    Netgear_disconnect_switch $switch_name

    return $str3
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDesignatedRootfromMstdetail
#
#  Description    : This function is used to get Mac address of STP root switch.
#
#  Usage          : _ntgrGetDesignatedRootfromMstdetail <switch_name> <mst>
#
#*******************************************************************************
proc _ntgrGetDesignatedRootfromMstdetail {switch_name mst} {
    set str1 {}
    set str2 {}
    set str3 {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show spanning-tree mst detailed $mst\r"
    exp_sleep 1
    expect -i $cnn_id -re "show spanning-tree"
    expect -i $cnn_id -re "Root Port"
    
    set output $expect_out(buffer)
    set regSWMAC "(Designated Root\[.\]+)(\[ ]+)(\[0-9a-fA-F:\]+)"
    regexp $regSWMAC $output match str1 str2 str3
    
    Netgear_log_file "lib-stp" "STP Root Switch Mac address: $str3"
    Netgear_disconnect_switch $switch_name

    return $str3
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetBridgeID
#
#  Description    : This function is used to get bridge identifier of switch.
#
#  Usage          : _ntgrGetBridgeID <switch_name>
#
#*******************************************************************************
proc _ntgrGetBridgeID {switch_name} {
    set str1 {}
    set str2 {}
    set str3 {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show spanning-tree\r"
    exp_sleep 1
    expect -i $cnn_id -re "show spanning-tree"
    expect -i $cnn_id -re "Port Trigger"
    
    set output $expect_out(buffer)
    set regSWMAC "(Bridge Identifier\[.\]+)(\[ ]+)(\[0-9a-fA-F:\]+)"
    regexp $regSWMAC $output match str1 str2 str3
    
    Netgear_log_file "lib-stp.tcl" "Switch bridge Identifier is: $str3"
    Netgear_disconnect_switch $switch_name

    return $str3
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetBridgeIDfromMstDetail
#
#  Description    : This function is used to get bridge identifier of switch.
#
#  Usage          : _ntgrGetBridgeIDfromMstDetail <switch_name mst>
#
#*******************************************************************************
proc _ntgrGetBridgeIDfromMstDetail {switch_name mst} {
    set str1 {}
    set str2 {}
    set str3 {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show spanning-tree mst detailed $mst \r"
    
    exp_sleep 1
    expect -i $cnn_id -re "show spanning-tree"
    expect -i $cnn_id -re "Root Port"
    
    set output $expect_out(buffer)
    set regSWMAC "(MST Bridge Identifier\[.\]+)(\[ ]+)(\[0-9a-fA-F:\]+)"
    regexp $regSWMAC $output match str1 str2 str3
    
    Netgear_log_file "lib-stp.tcl" "Switch bridge Identifier is: $str3"
    Netgear_disconnect_switch $switch_name

    return $str3
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetRootPortID
#
#  Description    : This function is used to get bridge identifier of switch.
#
#  Usage          : _ntgrGetRootPortID <switch_name>
#
#*******************************************************************************
proc _ntgrGetRootPortID {switch_name} {
    set str1 {}
    set str2 {}
    set str3 {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show spanning-tree\r"
    exp_sleep 1
    expect -i $cnn_id -re "show spanning-tree"
    expect -i $cnn_id -re "Port Trigger"
    
    set output $expect_out(buffer)
    set regSWMAC "(Root Port Identifier\[.\]+)(\[ ]+)(\[0-9a-fA-F:\]+)"
    regexp $regSWMAC $output match str1 str2 str3
    
    Netgear_log_file "lib-stp.tcl" "Switch bridge Identifier is: $str3"
    Netgear_disconnect_switch $switch_name

    return $str3
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetRootPortIDfromMstDetail
#
#  Description    : This function is used to get bridge identifier of switch.
#
#  Usage          : _ntgrGetRootPortIDfromMstDetail <switch_name mst>
#
#*******************************************************************************
proc _ntgrGetRootPortIDfromMstDetail {switch_name mst} {
    set str1 {}
    set str2 {}
    set str3 {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show spanning-tree mst detailed $mst\r"
    exp_sleep 1
    expect -i $cnn_id -re "show spanning-tree"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
       
    set regSWMAC "(Root.*Identifier\[.\]+)(\[ ]+)(\[0-9a-fA-F:\]+)"
    regexp $regSWMAC $output match str1 str2 str3
    Netgear_log_file "lib-stp.tcl" "Switch bridge Identifier is: $str3"
    Netgear_disconnect_switch $switch_name
    return $str3
}

#*******************************************************************************
#
#  Function Name	: _ntgrEnableBpduForwarding
#
#  Description    : This function is called to enable spanning-tree bpduforwarding on netgear
#		     	        switch.
#         
#  Usage          : _ntgrEnableBpduForwarding <switch_name>
#
#*******************************************************************************
proc _ntgrEnableBpduForwarding {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	set ver [_ntgrGetVersionReleasebyConnectionID $connection_id]
	if {$ver >= 10} {
	    set stpcommand "spanning-tree bpduflood\r"
	} else {
	    set stpcommand "spanning-tree bpduforwarding\r"
	}
	
		exp_send -i $connection_id "configure\r"
		expect -i $connection_id -re "#" {
			exp_send -i $connection_id $stpcommand
		}
		expect -i $connection_id -re "#" {
			exp_send -i $connection_id "exit\r"
		}
	
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrDisableBpduForwarding
#
#  Description    : This function is called to disable spanning-tree bpduforwarding on netgear
#		     	        switch.
#         
#  Usage          : _ntgrDisableBpduForwarding <switch_name>
#
#*******************************************************************************
proc _ntgrDisableBpduForwarding {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	set ver [_ntgrGetVersionReleasebyConnectionID $connection_id]
	if {$ver >= 10} {
	    set stpcommand "no spanning-tree bpduflood\r"
	} else {
	    set stpcommand "no spanning-tree bpduforwarding\r"
	}

	exp_send -i $connection_id "configure\r"
	expect -i $connection_id -re "#" {
		exp_send -i $connection_id  $stpcommand
	}
	expect -i $connection_id -re "#" {
		exp_send -i $connection_id "exit\r"
	}
	
	Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name	: _ntgrEnableBpduForwardingonInterface
#
#  Description    : This function is called to enable spanning-tree bpduforwarding on netgear
#		     	        switch.
#         
#  Usage          : _ntgrEnableBpduForwardingonInterface <switch_name interface>
#
#*******************************************************************************
proc _ntgrEnableBpduForwardingonInterface {switch_name interface} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	set ver [_ntgrGetVersionReleasebyConnectionID $connection_id]
	if {$ver >= 10} {
	    set stpcommand "spanning-tree bpduflood\r"
	} else {
	    set stpcommand "spanning-tree bpduforwarding\r"
	}
	
	exp_send -i $connection_id "configure\r"
	expect -i $connection_id -re "#" {
	    exp_send -i $connection_id "interface $interface\r"
		  expect -i $connection_id -re "#" {    
      		exp_send -i $connection_id $stpcommand
      		exp_sleep 1
          exp_send -i $connection_id "exit\r"
      }
    	expect -i $connection_id -re "#" {
    			  exp_send -i $connection_id "exit\r"
    	}
	}
	
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrDisableBpduForwardingonInterface
#
#  Description    : This function is called to disable spanning-tree bpduforwarding on netgear
#		     	        switch.
#         
#  Usage          : _ntgrDisableBpduForwardingonInterface <switch_name interface>
#
#*******************************************************************************
proc _ntgrDisableBpduForwardingonInterface {switch_name interface} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	set ver [_ntgrGetVersionReleasebyConnectionID $connection_id]
	if {$ver >= 10} {
	    set stpcommand "no spanning-tree bpduflood\r"
	} else {
	    set stpcommand "no spanning-tree bpduforwarding\r"
	}

	
	exp_send -i $connection_id "configure\r"
	expect -i $connection_id -re "#" {
		    exp_send -i $connection_id "interface $interface\r"
  		  expect -i $connection_id -re "#" {    
        		exp_send -i $connection_id $stpcommand 
        		exp_sleep 1
            exp_send -i $connection_id "exit\r"
        }
		
    		expect -i $connection_id -re "#" {
    			  exp_send -i $connection_id "exit\r"
    		}
	}
	
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetMstpPortRole
#
#  Description    : This function is used to get mstp port role
#
#  Usage          : _ntgrGetMstpPortRole <switch_name> <port> <mst>
#
#*******************************************************************************
proc _ntgrGetMstpPortRole {switch_name port {mst 0}} {
    Netgear_connect_switch $switch_name
    set match 0
    set retStr1 {}
    set retStr2 {}
    set portRoleList {"Backup" "Root" "Alternate" "Designated" "Master"}
    set stpStateList {"Forwarding" "Discarding" "Disabled"}
    set cnn_id [_get_switch_handle $switch_name]
	
	# add by jim.xie begin
    if {[string first "POCH" $port] != -1 } {
        #This is a port channel,get the logical interface name first
        set port [getChannelLogicalIfName $switch_name $port]
        ##set port [_ntgrGetChannelLogicalIfName $cnn_id $port]
        set port [lindex $port 0]
    }
	# add by jim.xie end
	
    exp_send -i $cnn_id "show spanning-tree mst port summary $mst $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show spanning-tree mst port summary $mst $port"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    foreach pattern $portRoleList {    
        set Ret [string first $pattern $output]
        if {$Ret != -1} {set retStr1 $pattern;set match 1;break}
    }
    if {!$match} {set retStr1 "NULL"}
    set match 0
    foreach pattern $stpStateList {    
        set Ret [string first $pattern $output]
        if {$Ret != -1} {set retStr2 $pattern ;set match 1;break}
    }
    if {!$match} {set retStr2 "NULL"}
    set retStr [concat $retStr2 $retStr1]
    Netgear_log_file "lib-stp.tcl" "mstp port role on $switch_name: $retStr"
    Netgear_disconnect_switch $switch_name
    return $retStr
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetMstpPortPathCost
#
#  Description    : This function is used to get mstp port cost
#
#  Usage          : _ntgrGetMstpPortPathCost <switch_name> <port> <mst>
#
#*******************************************************************************
proc _ntgrGetMstpPortPathCost {switch_name port {mst 0}} {
    set str1 {}
    set str2 {}
    set str3 {}
    Netgear_connect_switch $switch_name  
    set cnn_id [_get_switch_handle $switch_name]
	
	# add by jim.xie begin
    if {[string first "POCH" $port] != -1 } {
        #This is a port channel,get the logical interface name first
        set port [getChannelLogicalIfName $switch_name $port]
        ##set port [_ntgrGetChannelLogicalIfName $cnn_id $port]
        set port [lindex $port 0]
    }
	# add by jim.xie end
	
    exp_send -i $cnn_id "show spanning-tree mst port detailed $mst $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show spanning-tree"
    exp_sleep 1
    expect -i $cnn_id -re "Transitions Out Of Loop Inconsistent State"
    
    set output $expect_out(buffer)
 
    set regSWMAC "(Port Path Cost\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 str3 
    set retStr $str3
    
    Netgear_log_file "lib-stp.tcl" "mstp port cost on $switch_name: $retStr"
    Netgear_disconnect_switch $switch_name
    return $retStr
}

#*******************************************************************************
#
#  Function Name  : _ntgrStpEnablePortEdgeport
#
#  Description    : This function is used to enable port edgeport.
#
#  Usage          : _ntgrStpEnablePortEdgeport <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrStpEnablePortEdgeport {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
		exp_sleep 1
		foreach port $port_list {
	      exp_send -i $cnn_id "interface $port\r"
		    exp_sleep 1
		    expect -i $cnn_id -re "#"
		    exp_send -i $cnn_id "spanning-tree edgeport\r"
		    exp_sleep 1
		    expect -i $cnn_id -re "#"
		    
		    exp_send -i $cnn_id "exit\r"
		    exp_sleep 1
		}
		
    Netgear_log_file "lib-stp.tcl" "unset port edgeport"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrInterfaceEnableSTPCommand
#
#  Description    : This function is called enable spanning-tree command on interface .
#         
#  Usage          : _ntgrInterfaceEnableSTPCommand <switch_name interface action>
#                
#*******************************************************************************
proc _ntgrInterfaceEnableSTPCommand {switch_name interface action} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#" {
  		exp_send -i $connection_id "configure\r"
  		expect -i $connection_id -re "#" {
      		 exp_send -i $connection_id "interface $interface\r"
      		 expect -i $connection_id -re "#" {    
            		exp_send -i $connection_id "spanning-tree $action\r"
            		exp_sleep 1
                exp_send -i $connection_id "exit\r"
           }
  		}
      expect -i $connection_id -re "#" {
  	      exp_send -i $connection_id "exit\r"
      }
	} 
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrInterfaceDisableSTPCommand
#
#  Description    : This function is called disable spanning-tree command on interface .
#         
#  Usage          : _ntgrInterfaceDisableSTPCommand <switch_name interface action>
#                
#*******************************************************************************
proc _ntgrInterfaceDisableSTPCommand {switch_name interface action} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#" {
  		exp_send -i $connection_id "configure\r"
  		expect -i $connection_id -re "#" {
      		 exp_send -i $connection_id "interface $interface\r"
      		  expect -i $connection_id -re "#" {    
            		exp_send -i $connection_id "no spanning-tree $action\r"
            		exp_sleep 1
                exp_send -i $connection_id "exit\r"
            }
  		}
      expect -i $connection_id -re "#" {
  	      exp_send -i $connection_id "exit\r"
      }
	} 
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrGlobalEnableSTPCommand
#
#  Description    : This function is called enable spanning-tree command globaly.
#         
#  Usage          : _ntgrGlobalEnableSTPCommand <switch_name action>
#                
#*******************************************************************************
proc _ntgrGlobalEnableSTPCommand {switch_name action} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#" {
  		exp_send -i $connection_id "configure\r"
  		expect -i $connection_id -re "#" {
      		exp_send -i $connection_id "spanning-tree $action\r"
      		exp_sleep 1
          exp_send -i $connection_id "exit\r"
  		}
      expect -i $connection_id -re "#" {
  	      exp_send -i $connection_id "exit\r"
      }
	} 
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrGlobalDisableSTPCommand
#
#  Description    :This function is called disable spanning-tree command globaly.
#         
#  Usage          : _ntgrGlobalDisableSTPCommand <switch_name action>
#                
#*******************************************************************************
proc _ntgrGlobalDisableSTPCommand {switch_name action} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#" {
  		exp_send -i $connection_id "configure\r"
  		expect -i $connection_id -re "#" {
      		exp_send -i $connection_id "no spanning-tree $action\r"
      		exp_sleep 1
          exp_send -i $connection_id "exit\r"
  		}
      expect -i $connection_id -re "#" {
  	      exp_send -i $connection_id "exit\r"
      }
	} 
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrBPDUGuardEnable
#
#  Description    : This function is called enable spanning-tree command bpduguard globaly.
#         
#  Usage          : _ntgrBPDUGuardEnable <switch_name >
#                
#*******************************************************************************
proc _ntgrBPDUGuardEnable {switch_name} {
   set action "bpduguard"
   _ntgrGlobalEnableSTPCommand $switch_name $action
}

#*******************************************************************************
#
#  Function Name	: _ntgrBPDUGuardDisable
#
#  Description    : This function is called disable spanning-tree command bpduguard globaly.
#         
#  Usage          : _ntgrBPDUGuardDisable <switch_name action>
#                
#*******************************************************************************
proc _ntgrBPDUGuardDisable {switch_name} {
   set action "bpduguard"
   _ntgrGlobalDisableSTPCommand $switch_name $action
}

#*******************************************************************************
#
#  Function Name	: _ntgrBPDUFilterEnable
#
#  Description    : This function is called enable spanning-tree command bpdufilter globaly.
#         
#  Usage          : _ntgrBPDUFilterEnable <switch_name >
#                
#*******************************************************************************
proc _ntgrBPDUFilterEnable {switch_name} {
   set action "bpdufilter default"
   _ntgrGlobalEnableSTPCommand $switch_name $action
}

#*******************************************************************************
#
#  Function Name	: _ntgrBPDUFilterDisable
#
#  Description    : This function is called disable spanning-tree command bpdufilter globaly.
#         
#  Usage          : _ntgrBPDUFilterDisable <switch_name action>
#                
#*******************************************************************************
proc _ntgrBPDUFilterDisable {switch_name} {
   set action "bpdufilter default"
   _ntgrGlobalDisableSTPCommand $switch_name $action
}

#*******************************************************************************
#
#  Function Name	: _ntgrBPDUFilterEnableonInterface
#
#  Description    : This function is called to enable spanning-tree bpdu filter on port of netgear
#		     	        switch.
#         
#  Usage          : _ntgrBPDUFilterEnableonInterface <switch_name interface>
#                 
#*******************************************************************************
proc _ntgrBPDUFilterEnableonInterface {switch_name interface} {
  set command "bpdufilter "
  _ntgrInterfaceEnableSTPCommand $switch_name $interface $command
}

#*******************************************************************************
#
#  Function Name	: _ntgrBPDUFilterDisableonInterface
#
#  Description    : This function is called to enable spanning-tree bpdu filter on port of netgear
#		     	        switch.
#         
#  Usage          : _ntgrBPDUFilterDisableonInterface <switch_name interface>
#            
#*******************************************************************************
proc _ntgrBPDUFilterDisableonInterface {switch_name interface} {
  set command "bpdufilter "
  _ntgrInterfaceDisableSTPCommand $switch_name $interface $command
}
#*******************************************************************************
#
#  Function Name	: _ntgrGuardonInterface
#
#  Description    : This function is called to enable spanning-tree bpdu guard on port of netgear
#		     	        switch.
#         
#  Usage          : _ntgrGuardonInterface <switch_name interface action>
#                 action : loop ;none ;root
#*******************************************************************************
proc _ntgrGuardonInterface {switch_name interface action} {
  set command "guard "
  lappend command $action
  _ntgrInterfaceEnableSTPCommand $switch_name $interface $command
}

#*******************************************************************************
#
#  Function Name	: _ntgrSetSTPConfigurationName
#
#  Description    : This function set spanning tree configuration name.
#         
#  Usage          : _ntgrSetSTPConfigurationName <switch_name  name>
#                
#*******************************************************************************
proc  _ntgrSetSTPConfigurationName {switch_name name} {
   set action "configuration name $name"
   _ntgrGlobalEnableSTPCommand $switch_name $action
}

#*******************************************************************************
#
#  Function Name	: _ntgrNoSetSTPConfigurationName
#
#  Description    : his function no set spanning tree configuration name.
#         
#  Usage          : _ntgrNoSetSTPConfigurationName <switch_name>
#                
#*******************************************************************************
proc  _ntgrNoSetSTPConfigurationName {switch_name} {
    set action "configuration name"
   _ntgrGlobalDisableSTPCommand $switch_name $action
}

#*******************************************************************************
#
#  Function Name	: _ntgrSetSTPConfigurationRevision
#
#  Description    : This function set spanning tree configuration revision.
#         
#  Usage          : _ntgrSetSTPConfigurationRevision <switch_name revision>
#                
#*******************************************************************************
proc  _ntgrSetSTPConfigurationRevision {switch_name revision} {
   set action "configuration revision $revision"
   _ntgrGlobalEnableSTPCommand $switch_name $action
}

#*******************************************************************************
#
#  Function Name	: _ntgrNoSetSTPConfigurationRevision
#
#  Description    : his function no set spanning tree configuration revision.
#         
#  Usage          : _ntgrNoSetSTPConfigurationRevision <switch_name>
#                
#*******************************************************************************
proc  _ntgrNoSetSTPConfigurationRevision {switch_name} {
    set action "configuration Revision"
   _ntgrGlobalDisableSTPCommand $switch_name $action
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetHellotime
#
#  Description    : This function is used to get hello time of switch.
#
#  Usage          : _ntgrGetHellotime <switch_name>
#
#*******************************************************************************
proc _ntgrGetHellotime {switch_name} {
    set str1 {}
    set str2 {}
    set str3 {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show spanning-tree\r"
    exp_sleep 1
    expect -i $cnn_id -re "show spanning-tree"
    expect -i $cnn_id -re "Port Trigger"
    
    set output $expect_out(buffer)
    set regSWMAC "(Hello Time\[.\]+)(\[ ]+)(\[0-9a-fA-F:\]+)"
    regexp $regSWMAC $output match str1 str2 str3
    
    Netgear_log_file "lib-stp.tcl" "Switch bridge Identifier is: $str3"
    Netgear_disconnect_switch $switch_name

    return $str3
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetForwardingDelay
#
#  Description    : This function is used to get ForwardingDelay of switch.
#
#  Usage          : _ntgrGetForwardingDelay <switch_name>
#
#*******************************************************************************
proc _ntgrGetForwardingDelay {switch_name} {
    set str1 {}
    set str2 {}
    set str3 {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show spanning-tree\r"
    exp_sleep 1
    expect -i $cnn_id -re "show spanning-tree"
    expect -i $cnn_id -re "Port Trigger"
    
    set output $expect_out(buffer)
    set regSWMAC "(Bridge Forwarding Delay\[.\]+)(\[ ]+)(\[0-9a-fA-F:\]+)"
    regexp $regSWMAC $output match str1 str2 str3
    
    Netgear_log_file "lib-stp.tcl" "Switch bridge Identifier is: $str3"
    Netgear_disconnect_switch $switch_name

    return $str3
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPortNumfrominterface
#
#  Description    : This function is used to get swith port num via siwtch name and port.
#
#  Usage          : _ntgrGetPortNumfrominterface <switch_name> <switch_port>
#
#*******************************************************************************
 proc _ntgrGetPortNumfrominterface {swName interface} {
     set listInterface [split $interface /]
     set listlength [llength $listInterface]
     set listIndex [expr $listlength -1]
     set portNum [lindex $listInterface $listIndex]  
     set stackPortNum 0
     set stackNum [lindex $listInterface 0]
     if {$stackNum !=1} {
        set swList [_ntgrGetNamefromSwitchInfo $swName]
        for {set num 1;set index 0} {$num <$stackNum} {incr num; incr index} {
            set name [lindex $swList $index]
            set port [string range $name 5 6]
            set stackPortNum [expr $port + $stackPortNum]
        }
     }
     set portNum [expr $portNum + $stackPortNum]
     return $portNum
}
  
#*****************************************************************************************************************************
#
#  Function Name	:  _ntgrSetPVSTVlanConfigure
#
#  Description      :  This function is called setting PVST vlan configuration
#         
#  Usage            :  _ntgrSetPVSTVlanConfigure <switch_name> <vlan_id> <root> <priority> <forward-time> <hello-time> <max-age>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc _ntgrSetPVSTVlanConfigure {switch_name vlan_id root priority forward_time hello_time max_age} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    expect -i $connection_id "#"
	set basic_cmd "spanning-tree vlan $vlan_id "
	if {$root != ""} {
	    set root_cmd "$basic_cmd root $root"
		expect -i $connection_id "#"
		exp_send -i $connection_id "$root_cmd\r"
	}
	if {$priority != ""} {
	    set priority_cmd "$basic_cmd priority $priority"
		expect -i $connection_id "#"
		exp_send -i $connection_id "$priority_cmd\r"
	}
	if {$forward_time != ""} {
	    set forward_cmd "$basic_cmd forward-time $forward_time"
		expect -i $connection_id "#"
		exp_send -i $connection_id "$forward_cmd\r"
	}
	if {$hello_time != ""} {
	    set hello_cmd "$basic_cmd hello-time $hello_time"
		expect -i $connection_id "#"
		exp_send -i $connection_id "$hello_cmd\r"
	}
	if {$max_age != ""} {
	    set max_cmd "$basic_cmd max-age $max_age"
		expect -i $connection_id "#"
		exp_send -i $connection_id "$max_cmd\r"
	}
	sleep 1
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrCheckPVSTVlanStatus
#
#  Description    : This function is used to check pvst vlan status
#
#  Usage          : _ntgrCheckPVSTVlanStatus <switch_name> <vlan_id>
#
#  Author           :  jim.xie
#
# output sample of command <show spanning-tree vlan $vlan_id>
#(M5300-28G3) #show spanning-tree vlan 100                                                                                           
#                                                                                                                                    
#VLAN  100                                                                                                                           
# Spanning-tree enabled protocol pvst                                                                                                
# RootID    Priority        100                                                                                                      
#           Address         4C:60:DE:DB:77:D0                                                                                        
#           Cost            20000                                                                                                    
#           Port            14(1/0/14 )                                                                                              
#           Hello Time 2 Sec Max Age 20 sec Forward Delay 15 sec                                                                     
# BridgeID  Priority        32868 (priority 32768 sys-id-ext 100)                                                                    
#           Address         20:E5:2A:5E:CE:12                                                                                        
#           Hello Time 2 Sec Max Age 20 sec Forward Delay 15 sec                                                                     
#           Aging Time 300 sec                                                                                                       
#                                                                                                                                    
#Interface  Role        Sts            Cost         Prio.Nbr                                                                         
# ---------  ----------  -------------  -----------  --------                                                                         
# 1/0/2      Designated  Forwarding     20000        128.2                                                                            
# 1/0/13     Disabled    Disabled       0            128.13                                                                           
# 1/0/14     Root        Forwarding     20000        128.14                                                                           
# 1/0/15     Alternate   Discarding     20000        128.15     	
#
#*******************************************************************************
proc _ntgrCheckPVSTVlanStatus {switch_name vlan_id } {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    set output ""
    
    exp_send -i $cnn_id "show spanning-tree vlan $vlan_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "show spanning-tree vlan $vlan_id"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }
    }
	Netgear_disconnect_switch $switch_name

	set return_list {}
	set vlan_root_list {}
	set vlan_bridge_list {}
	set vlan_port_list {}

    set tmpList [split $output \n]
	for { set i 0 } { $i <[llength $tmpList] } { incr i } {
        set iList [lindex $tmpList $i]
		
	    if {[regexp -nocase -- ".*RootID\\s+Priority\\s+(\\d+).*" $iList line_info root_priority]} {
		    Netgear_log_file "Info: " "VLAN $vlan_id Root ID Priority is $root_priority ."
			set iList [lindex $tmpList [expr $i + 1]]
			if {[regexp -nocase -- "\\s+Address\\s+(\\w{2}:\\w{2}:\\w{2}:\\w{2}:\\w{2}:\\w{2}).*" $iList line_info root_address]} {
			    Netgear_log_file "Info: " "VLAN $vlan_id Root ID Address is $root_address ."
				incr i
			} else {
			    Netgear_log_file "Error: " "Can't found Root ID Address in VLAN $vlan_id."
				set root_address 0
			}
			
			set iList [lindex $tmpList [expr $i + 1]]
			if {[regexp -nocase -- "\\s+Cost\\s+(\\d+).*" $iList line_info root_cost]} {
			    Netgear_log_file "Info: " "VLAN $vlan_id Root ID Cost is $root_cost ."
				incr i
			} else {
			    Netgear_log_file "Error: " "Can't found Root ID Cost in VLAN $vlan_id."
				set root_cost 0
			}
			
			set iList [lindex $tmpList [expr $i + 1]]
      
			if {[regexp -nocase -- "\\s+Port\\s+(\\w.*)" $iList line_info root_port]} {
			    Netgear_log_file "Info: " "VLAN $vlan_id Root ID Port is $root_port ."
				incr i
			} else {
			    Netgear_log_file "Error: " "Can't found Root ID Port in VLAN $vlan_id."
				set root_port 0
			}

			set iList [lindex $tmpList [expr $i + 1]]
			if {[regexp -nocase -- "\\s+Hello\\s+Time\\s+(\\d+)\\s+Sec\\s+Max\\s+Age\\s+(\\d+)\\s+sec\\s+Forward\\s+Delay\\s+(\\d+)\\s+sec" $iList line_info root_hello_time root_max_age root_delay]} {
			    Netgear_log_file "Info: " "VLAN $vlan_id Root ID Hello Time is $root_hello_time Sec."
				Netgear_log_file "Info: " "VLAN $vlan_id Root ID Max Age is $root_max_age Sec."
				Netgear_log_file "Info: " "VLAN $vlan_id Root ID Forward Delay is $root_delay Sec."
			} else {
			    Netgear_log_file "Error: " "Can't found Root ID Hello Time/Max Age/Forward Delay in VLAN $vlan_id."
				set root_hello_time 0
				set root_max_age 0
				set root_delay 0
			}
			
			lappend vlan_root_list $root_priority
			lappend vlan_root_list $root_address
			lappend vlan_root_list $root_cost
			lappend vlan_root_list $root_port
			lappend vlan_root_list $root_hello_time
			lappend vlan_root_list $root_max_age
			lappend vlan_root_list $root_delay
		}
		if {[regexp -nocase -- "\\s+BridgeID\\s+Priority\\s+(\\d+).*" $iList line_info bridge_priority]} {
		    Netgear_log_file "Info: " "VLAN $vlan_id Bridge ID Priority is $bridge_priority ."
			set iList [lindex $tmpList [expr $i + 1]]
			if {[regexp -nocase -- "\\s+Address\\s+(\\w{2}:\\w{2}:\\w{2}:\\w{2}:\\w{2}:\\w{2}).*" $iList line_info bridge_address]} {
			    Netgear_log_file "Info: " "VLAN $vlan_id Bridge ID Address is $bridge_address ."
				incr i
			} else {
			    Netgear_log_file "Error: " "Can't found Bridge ID Address in VLAN $vlan_id."
				set bridge_address 0
			}
			set iList [lindex $tmpList [expr $i + 1]]
			if {[regexp -nocase -- "\\s+Hello\\s+Time\\s+(\\d+)\\s+Sec\\s+Max\\s+Age\\s+(\\d+)\\s+sec\\s+Forward\\s+Delay\\s+(\\d+)\\s+sec" $iList line_info bridge_hello_time bridge_max_age bridge_delay]} {
			    Netgear_log_file "Info: " "VLAN $vlan_id Bridge ID Hello Time is $bridge_hello_time Sec."
				Netgear_log_file "Info: " "VLAN $vlan_id Bridge ID Max Age is $bridge_max_age Sec."
				Netgear_log_file "Info: " "VLAN $vlan_id Bridge ID Forward Delay is $bridge_delay Sec."
				incr i
			} else {
			    Netgear_log_file "Error: " "Can't found Bridge ID Hello Time/Max Age/Forward Delay in VLAN $vlan_id."
				set bridge_hello_time 0
				set bridge_max_age 0
				set bridge_delay 0
			}
			set iList [lindex $tmpList [expr $i + 1]]
			if {[regexp -nocase -- "\\s+Aging\\s+Time\\s+(\\d+)\\s+sec.*" $iList line_info bridge_aging_time]} {
			    Netgear_log_file "Info: " "VLAN $vlan_id Bridge ID Ageing Time is $bridge_aging_time Sec."
			} else {
			    Netgear_log_file "Error: " "Can't found Bridge ID Ageing Time in VLAN $vlan_id."
				set bridge_aging_time 0
			}
			lappend vlan_bridge_list $bridge_priority
			lappend vlan_bridge_list $bridge_address
			lappend vlan_bridge_list $bridge_hello_time
			lappend vlan_bridge_list $bridge_max_age
			lappend vlan_bridge_list $bridge_delay
			lappend vlan_bridge_list $bridge_aging_time
		}
		if {[regexp -nocase -- "(\\d?/?0/\\d+)\\s+(\[A-Z\]+)\\s+(\[A-Z\]+)\\s+(\\d+)\\s+(\\d+).(\\d+)" $iList line_info port port_role port_status port_cost port_priority port_num]} {
		    Netgear_log_file "Info: " "Interface:$port    Role:$port_role    Sts:$port_status    Cost$port_cost    Prio.Nbr:$port_priority.$port_num "
		    lappend vlan_port_list $port
			lappend vlan_port_list $port_role
			lappend vlan_port_list $port_status
			lappend vlan_port_list $port_cost
			lappend vlan_port_list $port_priority
			lappend vlan_port_list $port_num
		}
		if {[regexp -nocase -- "(lag\\s+\\d+)\\s+(\[A-Z\]+)\\s+(\[A-Z\]+)\\s+(\\d+)\\s+(\\d+).(\\d+)" $iList line_info port port_role port_status port_cost port_priority port_num]} {
		    Netgear_log_file "Info: " "Interface:$port    Role:$port_role    Sts:$port_status    Cost$port_cost    Prio.Nbr:$port_priority.$port_num "
		    lappend vlan_port_list $port
			lappend vlan_port_list $port_role
			lappend vlan_port_list $port_status
			lappend vlan_port_list $port_cost
			lappend vlan_port_list $port_priority
			lappend vlan_port_list $port_num
		}
	}
	
	if {$vlan_root_list == ""} {
	    Netgear_log_file "Error: " "Not found any ROOT ID detail info in VLAN $vlan_id."
	}
	if {$vlan_bridge_list == ""} {
	    Netgear_log_file "Error: " "Not found any BridgeID detail info in VLAN $vlan_id."
	}
	if {$vlan_port_list == ""} {
	    Netgear_log_file "Error: " "Not found any Interface detail info in VLAN $vlan_id."
	}

    lappend return_list $vlan_root_list
    lappend return_list $vlan_bridge_list
    lappend return_list $vlan_port_list

	return $return_list
}

#*****************************************************************************************************************************
#
#  Function Name	:  _ntgrSetPVSTInterfaceConfigure
#
#  Description      :  This function is called setting PVST Interface configuration
#         
#  Usage            :  _ntgrSetPVSTInterfaceConfigure <switch_name> <vlan_id> <port> <priority> <cost>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc _ntgrSetPVSTInterfaceConfigure {switch_name vlan_id port priority cost} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    expect -i $connection_id "#"
	exp_send -i $connection_id "interface $port\r"
	expect -i $connection_id "#"
	set basic_cmd "spanning-tree vlan $vlan_id "
	if {$priority != ""} {
	    set priority_cmd "$basic_cmd port-priority $priority"
		expect -i $connection_id "#"
		exp_send -i $connection_id "$priority_cmd\r"
	}
	if {$cost != ""} {
	    set cost_cmd "$basic_cmd cost $cost"
		expect -i $connection_id "#"
		exp_send -i $connection_id "$cost_cmd\r"
	}
	sleep 1
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#*****************************************************************************************************************************
#
#  Function Name	:  _ntgrDeleteAllPVSTVlan
#
#  Description      :  This function is called delete All PVST vlan configuration
#         
#  Usage            :  _ntgrDeleteAllPVSTVlan <switch_name> <max_vlanID>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc _ntgrDeleteAllPVSTVlan {switch_name max_vlanID} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    expect -i $connection_id "#"

    exp_send -i $connection_id "no spanning-tree vlan 2-$max_vlanID\r"
	sleep 1
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#*****************************************************************************************************************************
#
#  Function Name	:  _ntgrAddPVSTVlan
#
#  Description      :  This function is called add PVST vlan
#         
#  Usage            :  _ntgrAddPVSTVlan <switch_name> <vlanID>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc _ntgrAddPVSTVlan {switch_name vlanID} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    expect -i $connection_id "#"

    exp_send -i $connection_id "spanning-tree vlan $vlanID\r"
	sleep 1
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#*****************************************************************************************************************************
#
#  Function Name	:  _ntgrEnableSTPFastBackboneMode
#
#  Description      :  This function is enable fast Backbone 
#
#  Usage            :  _ntgrEnableSTPFastBackboneMode <switch_name>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc _ntgrEnableSTPFastBackboneMode {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    expect -i $connection_id "#"

    exp_send -i $connection_id "spanning-tree backbonefast\r"
	sleep 1
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#*****************************************************************************************************************************
#
#  Function Name	:  _ntgrDisableSTPFastBackboneMode
#
#  Description      :  This function is disable fast Backbone 
#
#  Usage            :  _ntgrDisableSTPFastBackboneMode <switch_name>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc _ntgrDisableSTPFastBackboneMode {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    expect -i $connection_id "#"

    exp_send -i $connection_id "no spanning-tree backbonefast\r"
	sleep 1
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#*****************************************************************************************************************************
#
#  Function Name	:  _ntgrEnableSTPFastUplinkMode
#
#  Description      :  This function is enable fast Uplink 
#
#  Usage            :  _ntgrEnableSTPFastUplinkMode <switch_name>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc _ntgrEnableSTPFastUplinkMode {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    expect -i $connection_id "#"

    exp_send -i $connection_id "spanning-tree uplinkfast\r"
	sleep 1
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#*****************************************************************************************************************************
#
#  Function Name	:  _ntgrDisableSTPFastUplinkMode
#
#  Description      :  This function is disable fast Uplink 
#
#  Usage            :  _ntgrDisableSTPFastUplinkMode <switch_name>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc _ntgrDisableSTPFastUplinkMode {switch_name} {
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    expect -i $connection_id "#"

    exp_send -i $connection_id "no spanning-tree uplinkfast\r"
	sleep 1
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

