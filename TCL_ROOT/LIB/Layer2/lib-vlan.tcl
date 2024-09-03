#!/bin/sh
################################################################################   
#
#  File Name   : lib-vlan.tcl
#
#  Description :
#         This TCL contains APIs to configure vlan on netgear switches. 
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        29/5/06      Scott Zhang       Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: _switch_create_vlan
#
#  Description    : This function is called to create a vlan on netgear switch.
#
#  Usage          : _switch_create_vlan <cnn_id> <vlan_id> <vlan_name> <mode>
#
#  Note           : mode could be "layer2" or "layer3"
# 
#*******************************************************************************
proc _switch_create_vlan {cnn_id vlan_id vlan_name mode} {
	exp_send -i $cnn_id "vlan database\r"
	exp_sleep 1
	
	exp_send -i $cnn_id "vlan $vlan_id\r"
	exp_sleep 1
	
	exp_send -i $cnn_id "vlan name $vlan_id $vlan_name\r"
	exp_sleep 1
	
	if {$mode == "layer3"} {
		exp_send -i $cnn_id "vlan routing $vlan_id\r"
		exp_sleep 1
	}
	
	exp_send -i $cnn_id "exit\r"
	exp_sleep 1
}

#*******************************************************************************
#                                                                                                
#  Function Name	: _ntgrGetL3LogicalIf                                                          
#                                                                                                
#  Description    : This function is called to get the logical interface of                      
#                   a L3 VLAN from the switch.                                                   
#                                                                                                
#  Usage          : _ntgrGetL3LogicalIf <cnn_id> <vlan_index_list>                               
#                                                                                                
#*******************************************************************************                 
proc _ntgrGetL3LogicalIf {cnn_id vlan_index_list} {                                              
                                                                                                 
  set vlan_if_list ""
  set vlan_if ""
  global NTGR_LOG_DEBUG
	exp_send -i $cnn_id "show ip vlan\r"
	expect -i $cnn_id -re "show ip vlan"

    set output ""
    expect {
        -i $cnn_id -re "#" { set output $output$expect_out(buffer) }
        -i $cnn_id -re "More" {
            set output $output$expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_continue
        }
    }
    exp_sleep 1
  foreach vlan_index $vlan_index_list {
	    set vlan_id [_getVlanID $vlan_index]
	    set reg "(\[\n]+$vlan_id)(\[ \t]+)(\[^\t ]*)"
	    regexp $reg $output string1 string2 string3 vlan_if
	    if {$vlan_if == "" || $vlan_if == {}} {
        NtgrDumpLog $NTGR_LOG_DEBUG "lib-vlan.tcl" "Get empty vlan logical interface name: VLAN=$vlan_index, output=$output"
        set vlan_if [ _ntgrGetL3LogicalIf $cnn_id $vlan_index ]
     }
	    lappend vlan_if_list $vlan_if
	}
    return $vlan_if_list                                                                         
}

##*******************************************************************************
##
##  Function Name	: _ntgrGetL3LogicalIf
##
##  Description    : This function is called to get the logical interface of
##                   a L3 VLAN from the switch.
##         
##  Usage          : _ntgrGetL3LogicalIf <cnn_id> <vlan_index>
##
##*******************************************************************************
#proc _ntgrGetL3LogicalIf {cnn_id vlan_index} {
#    set vlan_if ""
#    exp_send -i $cnn_id "show ip vlan\r"
#    exp_sleep 1
#    expect {
#        -i $cnn_id -re "show ip vlan" {}
#        timeout {
#            exp_send -i $cnn_id "\r"
#            sleep 1
#            exp_send -i $cnn_id "show ip vlan\r"
#            sleep 1
#            exp_continue
#        }
#    }
#
#    set output ""
#    expect {
#        -i $cnn_id -re "#" { set output $output$expect_out(buffer) }
#        -i $cnn_id -re "More" {
#            set output $output$expect_out(buffer)
#            exp_send -i $cnn_id " "
#            exp_continue
#        }
#        timeout exp_continue
#    }
#    set reg "([_getVlanID $vlan_index])(\[ \t]*)(\[^\t ]*)"
#    regexp $reg $output string1 string2 string3 vlan_if
#
#    global NTGR_LOG_DEBUG
#    if {$vlan_if == ""} {
#        NtgrDumpLog $NTGR_LOG_DEBUG "lib-vlan.tcl" "Get empty vlan logical interface name: VLAN=$vlan_index, output=$output"
#        set vlan_if [ _ntgrGetL3LogicalIf $cnn_id $vlan_index ]
#    }
#
#    return $vlan_if
#}
#*******************************************************************************
#
#  Function Name	: _switch_add_vlan_port
#
#  Description    : This function is called to include a vlan on a interface.
#         
#  Usage          : _switch_add_vlan_port <cnn_id> <switch_port> <vlan_id> 
#                   <tag> <vlan_pvid>
#
#  Note           : the tag parameter could be "T" for tagged or "U" for untagged.
#
#*******************************************************************************
proc _switch_add_vlan_port {cnn_id switch_port vlan_id tag vlan_pvid} {	
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500
#	exp_send -i $cnn_id "no shutdown\r"
#	after 500

	if {$tag == "E"} {
		exp_send -i $cnn_id "vlan participation exclude $vlan_id\r"
	} else {
		exp_send -i $cnn_id "vlan participation include $vlan_id\r"
	}
	after 500
	
	if {$tag == "T"} {
		exp_send -i $cnn_id "vlan tagging $vlan_id\r"
	} elseif {$tag == "U"} {
		exp_send -i $cnn_id "no vlan tagging $vlan_id\r"
	}
	after 500
	
	if {$tag != "E"} {
  	exp_send -i $cnn_id "vlan pvid $vlan_pvid\r"
  	after 500
	}
	
	exp_send -i $cnn_id "exit\r"
	after 500
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_add_vlan_port
#
#  Description    : This function is called to include a vlan on a interface using vlan trunk
#         
#  Usage          : _switch_vlan_trunk_add_vlan_port <cnn_id> <switch_port> <vlan_id> 
#                   <tag> <vlan_pvid> <mode> 
#
#  Note           : the tag parameter could be "T" for tagged or "U" for untagged.
#
#  Author         : jim.xie ,modified by bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_add_vlan_port {cnn_id switch_port vlan_id tag vlan_pvid mode} {	
	#global SwitchPortMode, comments by bo
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500
	if {$tag == "T"} {
	    exp_send -i $cnn_id "switchport mode trunk\r"
		after 500
		set trunk_allow "1,$vlan_id"
		exp_send -i $cnn_id "switchport trunk allowed vlan $trunk_allow\r"
	} elseif {$tag == "U"} {
       #modified by bo
		if {$mode == "trunk"} {
        #modified by bo
		    exp_send -i $cnn_id "switchport mode trunk\r"
		    after 500
			exp_send -i $cnn_id "switchport trunk native vlan $vlan_id\r"
		    after 500
		    set trunk_allow "1,$vlan_id"
		    exp_send -i $cnn_id "switchport trunk allowed vlan $trunk_allow\r"
        #modified by bo
		} elseif {$mode == "access"} {
        #modified by bo
            exp_send -i $cnn_id "switchport mode access\r"
		    after 500
			exp_send -i $cnn_id "switchport access vlan $vlan_id\r"
		} else {
		    Netgear_log_file "Error: " "Variable mode  only support trunk/access"
		}
	}
	after 500
	
	exp_send -i $cnn_id "exit\r"
	after 500
}

#*******************************************************************************
#
#  Function Name	: _ntgrAddPrivateVlanGroup
#
#  Description    : This function is called to create a private vlan group.
#         
#  Usage          : _ntgrAddPrivateVlanGroup <switch_name> <group_name> <group_mode> 
#                   
#
#  Note           : group_name can not include blank character.
#
#*******************************************************************************
proc _ntgrAddPrivateVlanGroup {switch_name group_name group_mode} {

    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    expect -i $cnn_id "#" {}
  	exp_send -i $cnn_id "private-group name $group_name mode $group_mode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer) 
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }	  	
  	return $ret  	
}

#*******************************************************************************
#
#  Function Name	: _ntgrDeletePrivateVlanGroup
#
#  Description    : This function is called to delete a private vlan group.
#         
#  Usage          : _ntgrDeletePrivateVlanGroup <switch_name> <group_name> 
#                   
#
#  Note           : group_name can not include blank character.
#
#*******************************************************************************
proc _ntgrDeletePrivateVlanGroup {switch_name group_name} {
  
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    expect -i $cnn_id "#" {}  	
  	exp_send -i $cnn_id "no private-group name $group_name\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)     
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrEnablePrivateVlanOnInterface
#
#  Description    : This function is called to bind a private vlan group to special interface
#         
#  Usage          : _ntgrEnablePrivateVlanOnInterface <switch_name> <port_list> <name_id> 
#                   
#
#  Note           : name_id: group name or group id.
#
#*******************************************************************************

proc _ntgrEnablePrivateVlanOnInterface {switch_name port_list name_id} {
  
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	  set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    expect -i $cnn_id "#" {}  	
  	foreach iport $port_list {
    	exp_send -i $cnn_id "interface $iport\r"
    	exp_sleep 1
    	expect -i $cnn_id "#" {}
    	expect -i $cnn_id -re "#"
      append ret $expect_out(buffer)
    	exp_send -i $cnn_id "switchport private-group $name_id\r"
      exp_sleep 1
      expect -i $cnn_id -re "#" 
      append ret $expect_out(buffer) 
    	exp_send -i $cnn_id "exit\r"
  	  exp_sleep 1
      expect -i $cnn_id -re "#" 
      append ret $expect_out(buffer)  	  
    }
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrDisablePrivateVlanOnInterface
#
#  Description    : This function is called to unbind a private vlan group to special interface
#         
#  Usage          : _ntgrDisablePrivateVlanOnInterface <switch_name> <port_list> <name_id> 
#                   
#
#  Note           : name_id: group name or group id.
#
#*******************************************************************************

proc _ntgrDisablePrivateVlanOnInterface {switch_name port_list name_id} {

    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    expect -i $cnn_id "#" {}  
  	foreach iport $port_list {
    	exp_send -i $cnn_id "interface $iport\r"
    	exp_sleep 1
    	expect -i $cnn_id "#" {}
    	expect -i $cnn_id -re "#"    	
    	exp_send -i $cnn_id "no switchport private-group $name_id\r"
      exp_sleep 1
      expect -i $cnn_id -re "#" 
      append ret $expect_out(buffer)       
    	exp_send -i $cnn_id "exit\r"
  	  exp_sleep 1
      expect -i $cnn_id -re "#" 
      append ret $expect_out(buffer)   	  
    }
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer) 
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}
#*******************************************************************************
#
#  Function Name	: _switch_add_vlan_global
#
#  Description    : This function is called to include a vlan on all interface.
#         
#  Usage          : _switch_add_vlan_global <cnn_id> <vlan_id> <tag> <vlan_pvid>
#
#  Note           : the tag parameter could be "T" for tagged or "U" for untagged.
#
#*******************************************************************************
proc _switch_add_vlan_global {cnn_id vlan_id tag vlan_pvid} {
	exp_send -i $cnn_id "configure\r"
	exp_sleep 1
	
	if {$tag == "E"} {
		exp_send -i $cnn_id "vlan participation all exclude $vlan_id\r"
	} else {
		exp_send -i $cnn_id "vlan participation all include $vlan_id\r"
	}
	exp_sleep 1
	
	if {$tag == "T"} {
		exp_send -i $cnn_id "vlan port tagging all $vlan_id\r"
	} elseif {$tag == "U"} {
		exp_send -i $cnn_id "no vlan port tagging all $vlan_id\r"
	}
	exp_sleep 1
	
	if {$tag != "E"} {
		exp_send -i $cnn_id "vlan pvid $vlan_pvid\r"
		exp_sleep 1
	}
	
	exp_send -i $cnn_id "exit\r"
	exp_sleep 1
}

#*******************************************************************************
#
#  Function Name	: _switch_config_L3_vlan_ip_addr
#
#  Description    : This function is called to configure ip address for a L3 VLAN
#         
#  Usage          : _switch_config_L3_vlan_ip_addr <connection_id> <vlan_if> 
#								   <ip_addr>  <ip_mask>
# 
#*******************************************************************************
proc _switch_config_L3_vlan_ip_addr {switch_name vlan_if ip_addr ip_mask} {

	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    	
  	set vlan_if [lindex $vlan_if 0]

  	exp_send -i $cnn_id "configure\r"
  	sleep 1
  	exp_send -i $cnn_id "interface $vlan_if\r"
  	sleep 1
  	exp_send -i $cnn_id "ip address $ip_addr $ip_mask\r"
  	sleep 1
  	exp_send -i $cnn_id "exit\r"
  	sleep 1
  	exp_send -i $cnn_id "exit\r"

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 	
}

#*******************************************************************************
#
#  Function Name	: _ntgrCreateVlan
#
#  Description    : This function is an API used to create vlan on ntgr devices
#         
#  Usage          : _ntgrCreateVlan <switch_name> <vlan_id> <port_info>
# 
#*******************************************************************************
proc _ntgrCreateVlan {switch_name vlan_index port_info} {
	Netgear_connect_switch $switch_name
	set switch_handle [_get_switch_handle $switch_name]
	
	set vlan_name [_getVlanName $vlan_index]
	set vlan_id [_getVlanID $vlan_index]
	set vlan_mode [_getVlanMode $vlan_index $switch_name]
	
	# Create the vlan on the switch.
	_switch_create_vlan $switch_handle $vlan_id $vlan_name $vlan_mode
	if {$vlan_mode == "layer3"} {
	  ##------- modified by kenddy 2010-11-03 -------#
	  #------ for version 8.x ------#
#		set vlan_if [ _ntgrGetL3LogicalIf $switch_handle $vlan_index ]
    
    #------ for version 9.x ------#
    set vlan_if "\{vlan $vlan_id\}"
    ##------- End of modified by kenddy 2010-11-03 -------#
    
		# Save the logical interface name to the global struct.
		_setVlanLogicalIfName $switch_name $vlan_index $vlan_if

		set ip_addr [_getVlanIPAddr $vlan_index $switch_name]
		set ip_mask [_getVlanIPMask $vlan_index $switch_name]
        set if_enable_ipv6 [_getVlanIfEnableIPv6 $vlan_index $switch_name]
		
		if {[string match *.* $ip_addr]} {
		    Netgear_log_file "lib-vlan.tcl" "ip address type is ipv4!!"
		    _switch_config_L3_vlan_ip_addr $switch_name $vlan_if $ip_addr $ip_mask
		    
		} elseif { [string match *:* $ip_addr]} {
		  
		    Netgear_log_file "lib-vlan.tcl" "ip address type is ipv6!!"
		    _ntgrIpv6AddIpAddressOnPort $switch_name $vlan_if $ip_addr
            #beginning add by bo.shi enable ipv6 on vlan.
            if {[string equal -nocase "ipv6 enable" $if_enable_ipv6] } {

            _ntgrIpv6EnableOnVlan $switch_name $vlan_if
            
            } else {
            Netgear_log_file "lib-vlan.tcl" "no need to enable ipv6"
            }
            # ending add by bo.shi enable ipv6 on vlan.
		} else {
		    Netgear_log_file "lib-vlan.tcl" "ip address type is invalid!!!."
		}
		
	}
	global SwitchPortMode
	# add by jim.xie for vlan trunk begin
    set mode_flag [info exist SwitchPortMode]
	
	set trunk_flag 0
	if {$mode_flag == 1} {
	    if {$SwitchPortMode != "general"} {
		    set trunk_flag 1
		}
	}
	# add by jim.xie for vlan trunk end
	
	# Add port(s) to the vlan    
	exp_send -i $switch_handle "configure\r"
	sleep 1
	foreach p $port_info {
		set port [lindex $p 0]
		set tag  [lindex $p 1]
		set pvid [lindex $p 2]
		if {$port == "all"} {
			_switch_add_vlan_global $switch_handle $vlan_id $tag $pvid
		} elseif {[string first "POCH" $port] != -1}  {
			# This is a port channel.
			exp_send -i $switch_handle "exit\r"
			sleep 1
			set ifName [_ntgrGetChannelLogicalIfName $switch_handle $port]
			exp_send -i $switch_handle "configure\r"
			sleep 1
			
			# modify by jim.xie for vlan trunk begin
			if {$trunk_flag} {
                	# modify by bo.shi
			    _switch_vlan_trunk_add_vlan_port $switch_handle $ifName $vlan_id $tag $pvid $SwitchPortMode
                 # modify by bo.shi
			} else {
			    _switch_add_vlan_port $switch_handle $ifName $vlan_id $tag $pvid
            }
            # modify by jim.xie for vlan trunk end
			
		} else {
		    # modify by jim.xie for vlan trunk begin
		    if {$trunk_flag} {
                # modify by bo.shi
			    _switch_vlan_trunk_add_vlan_port $switch_handle $port $vlan_id $tag $pvid $SwitchPortMode
                # modify by bo.shi
			} else {
			    _switch_add_vlan_port $switch_handle $port $vlan_id $tag $pvid
		    }
			# modify by jim.xie for vlan trunk end
		}
	}
	exp_send -i $switch_handle "exit\r"
	sleep 1

	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _ntgrDeleteVlan
#
#  Description    : This function is called to delete a vlan on netgear switch.
#         
#  Usage          : _ntgrDeleteVlan <switch_name> <vlan_id>
# 
#*******************************************************************************
proc _ntgrDeleteVlan {switch_name vlan_id vlan_index} {
	Netgear_connect_switch $switch_name
	set cnn_id [_get_switch_handle $switch_name]

	# add by jim.xie for vlan trunk begin
	global SwitchPortMode
	set mode_flag [info exist SwitchPortMode]
	
	set trunk_flag 0
	if {$mode_flag == 1} {
	    if {$SwitchPortMode != "general"} {
		    set trunk_flag 1
		}
	}
	if {$trunk_flag && $vlan_index != -1} {
	    set vlan_members [_getVlanMembers $vlan_index]
        foreach switch_info $vlan_members {
            if { $switch_name == [lindex $switch_info 0] } {
                set port_info   [lindex $switch_info 1]
				break;
			}
		}
		foreach p $port_info {
		        set port [lindex $p 0]
		        
				if {[string first "POCH" $port] != -1}  {
			        # This is a port channel.
			        set ifName [_ntgrGetChannelLogicalIfName $cnn_id $port]
					set port $ifName
			    }
			    exp_send -i $cnn_id "configure\r"
			    sleep 1
				exp_send -i $cnn_id "interface $port\r"
			    sleep 1
                if {$SwitchPortMode == "trunk"} {
				    exp_send -i $cnn_id "switchport trunk native vlan 1\r"
					sleep 1
					exp_send -i $cnn_id "switchport trunk allowed vlan all\r"
					sleep 1
					exp_send -i $cnn_id "no switchport mode \r"
					sleep 1
				} elseif {$SwitchPortMode == "access"} {
				    exp_send -i $cnn_id "switchport access vlan 1\r"
					sleep 1
					exp_send -i $cnn_id "no switchport mode \r"
					sleep 1
				} else {
				    Netgear_log_file "Error: " "Variable SwitchPortMode in file:config_port.xml only support trunk/access/general"
				}
				exp_send -i $cnn_id "exit\r"
	            sleep 1
				exp_send -i $cnn_id "exit\r"
	            sleep 1
        }
	}
	# add by jim.xie for vlan trunk end
	
	exp_send -i $cnn_id "vlan database\r"
	sleep 1
	exp_send -i $cnn_id "no vlan $vlan_id\r"
	sleep 1
	exp_send -i $cnn_id "exit\r"
	sleep 1
	
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetVlanAll
#
#  Description    : This function is called to return a list of all configured
#                   VLAN on a switch. While VLAN 1 is the default vlan, it will
#                   not be included in returned list.
#
#  Usage          : _ntgrGetVlanAll <switch_name>
#
#*******************************************************************************
proc _ntgrGetVlanAll {cnn_id} {
    # get the configuration info by show command
    exp_send -i $cnn_id "show vlan\r"
    expect -i $cnn_id -re "show vlan"
    set output ""
    expect {
        -i $cnn_id -re "#" { set output $output$expect_out(buffer) }
        -i $cnn_id -re "More" {
            set output $output$expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_continue
        }
    }
    exp_sleep 1
    
    set reg "(\[\r\n\]+)(\[0-9\]+)(\[ \t\]+)"
    set vlanlist ""
    while {1} {
        set bMatch [regexp $reg $output str0 str1 str2 str3]
        if {$bMatch == 1} {
            set output [string range $output [expr [string first $str0 $output] + [string length $str0]] end]
            if {$str2 != 1} {lappend vlanlist $str2}
        } else break;
    }

    return $vlanlist
}

#*******************************************************************************
#
#  Function Name  : _ntgrDeleteVlanAll
#
#  Description    : This function is called to delete all configured VLAN on a
#                   switch.
#
#  Usage          : _ntgrDeleteVlanAll <switch_name>
#
#*******************************************************************************
proc _ntgrDeleteVlanAll {switch_name} {    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    set vlanList [_ntgrGetVlanAll $cnn_id]
    
    Netgear_log_file "lib-vlan.tcl" "Deleting all configured VLANs $vlanList on switch $switch_name"
    exp_send -i $cnn_id "vlan database\r"
    sleep 1
    foreach vlan_id $vlanList {
        exp_send -i $cnn_id "no vlan $vlan_id\r"
        sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrCreateVlanAll
#
#  Description    : This function is called to create all vlan(s) according to
#                   the global variable(ntgr_vlanList) on the given switch.
#
#  Usage          : _ntgrCreateVlanAll <switch_name>
#
#*******************************************************************************
proc _ntgrCreateVlanAll {switch_name} {
    global ntgr_vlanList
    for_array_keys vlan_index ntgr_vlanList {
        set vlan_members [_getVlanMembers $vlan_index]
        foreach switch_info $vlan_members {
            if { $switch_name == [lindex $switch_info 0] } {
                _ntgrCreateVlan $switch_name $vlan_index [lindex $switch_info 1]
                break;
            }
        }
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetInterfacePVID
#
#  Description    : This function is called to reset PVID on one port
#     
#
#  Usage          : _ntgrDeleteVlanAll <switch_name interface>
#
#*******************************************************************************
proc  _ntgrResetInterfacePVID {switch_name interface} {    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    Netgear_log_file "lib-vlan.tcl" "Reset PVID on $interface in switch $switch_name"
    expect -i $cnn_id -re "#"
   	exp_send -i $cnn_id "configure\r"
  	sleep 1
    exp_send -i $cnn_id "interface $interface\r"
    sleep 1
    exp_send -i $cnn_id "vlan pvid 1\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetInterfaceVlanParticipation
#
#  Description    : This function is called to reset vlan participation on one port
#     
#
#  Usage          : _ntgrResetInterfaceVlanParticipation <switch_name interface vlanid>
#
#*******************************************************************************
proc  _ntgrResetInterfaceVlanParticipation {switch_name interface vlanid} {    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    Netgear_log_file "lib-vlan.tcl" "reset vlan participation on $interface in switch $switch_name"
    expect -i $cnn_id -re "#"
   	exp_send -i $cnn_id "configure\r"
  	sleep 1
    exp_send -i $cnn_id "interface $interface\r"
    sleep 1
    exp_send -i $cnn_id "vlan participation auto $vlanid\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

