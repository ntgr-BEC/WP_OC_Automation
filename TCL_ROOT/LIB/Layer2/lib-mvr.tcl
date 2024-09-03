####################################################################################
#  File Name   : lib-mvr.tcl
#
#  Description :
#        This file includes functions used for MVR configuration.
#
#  History     :
#        Date          Programmer         Description
#        11/22/2013    jim xie            Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrMVREnable
#
#  Description    : This function is called to enable MVR on specified
#                   switch.
#
#  Usage          : _ntgrMVREnable <switch_name>
#
#*******************************************************************************
proc _ntgrMVREnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "mvr\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mvr.tcl" "Enable MVR on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRDisable
#
#  Description    : This function is called to disable MVR on specified
#                   switch.
#
#  Usage          : _ntgrMVRDisable <switch_name>
#
#*******************************************************************************
proc _ntgrMVRDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no mvr\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mvr.tcl" "Disable MVR on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRSetMVRMode
#
#  Description    : This function is called to set MVR mode on specified
#                   switch.
#
#  Usage          : _ntgrMVRSetMVRMode <switch_name> <mvr_mode>
#
#*******************************************************************************
proc _ntgrMVRSetMVRMode {switch_name mvr_mode} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "mvr mode $mvr_mode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mvr.tcl" "Set MVR Mode as $mvr_mode on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRSetMVRMulticastVlan
#
#  Description    : This function is called to set MVR Multicast VLAN on specified
#                   switch.
#
#  Usage          : _ntgrMVRSetMVRMulticastVlan <switch_name> <vlan_id>
#
#*******************************************************************************
proc _ntgrMVRSetMVRMulticastVlan {switch_name vlan_id} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "mvr vlan $vlan_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mvr.tcl" "Set MVR multicast vlan as $vlan_id on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRSetMVRResponseTime
#
#  Description    : This function is called to set MVR global query response time on specified
#                   switch.
#
#  Usage          : _ntgrMVRSetMVRResponseTime <switch_name> <res_time>
#
#*******************************************************************************
proc _ntgrMVRSetMVRResponseTime {switch_name res_time} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "mvr querytime $res_time\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mvr.tcl" "Set MVR global query response time as $res_time on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRAddMVRGroup
#
#  Description    : This function is called to add MVR group on specified
#                   switch.
#
#  Usage          : _ntgrMVRAddMVRGroup <switch_name> <grp_ip> <grp_count>
#
#*******************************************************************************
proc _ntgrMVRAddMVRGroup {switch_name grp_ip grp_count} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "mvr group $grp_ip $grp_count\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mvr.tcl" "Add MVR Group start group ip as $grp_ip , Count as $grp_count on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRDeleteMVRGroup
#
#  Description    : This function is called to delete MVR group on specified
#                   switch.
#
#  Usage          : _ntgrMVRDeleteMVRGroup <switch_name> <grp_ip> <grp_count>
#
#*******************************************************************************
proc _ntgrMVRDeleteMVRGroup {switch_name grp_ip grp_count} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no mvr group $grp_ip $grp_count\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mvr.tcl" "Delete MVR Group start group ip as $grp_ip , Count as $grp_count on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRCheckMVRGroupConfigure
#
#  Description    : This function is called to check MVR group configure on specified
#                   switch.
#
#  Usage          : _ntgrMVRCheckMVRGroupConfigure <switch_name> <grp_ip>
#
#*******************************************************************************
proc _ntgrMVRCheckMVRGroupConfigure {switch_name grp_ip grp_status grp_members} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
	exp_send -i $cnn_id "show mvr members\r"
    exp_sleep 1
    exp_send -i $cnn_id "show mvr members $grp_ip \r"
    exp_sleep 1
    expect -i $cnn_id -re "show mvr members $grp_ip"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
	
	Netgear_log_file "lib-mvr.tcl" "MVR group configure info: $output"
    set result 1
	set ipflag [regexp -nocase $grp_ip $output]
	set activeflag [regexp -nocase "active" $output]
	set inactiveflag [regexp -nocase "inactive" $output]
	if {$ipflag} {
	    foreach grp_mem $grp_members {
            if {![regexp -nocase $grp_mem $output]} {
                set result 0
			    Netgear_log_file "lib-mvr.tcl" "Error: MVR member: $grp_mem is not matched."
            }
        }
		if {$grp_status == "active"} {
		   if {$inactiveflag == 1 || $activeflag == 0} {
		       set result 0
			   Netgear_log_file "lib-mvr.tcl" "Error: MVR Status is not matched."
		   }
		}
		if {$grp_status == "inactive"} {
		   if {! $inactiveflag} {
		       set result 0
			   Netgear_log_file "lib-mvr.tcl" "Error: MVR Status is not matched."
		   }
		}
		
	} else {
	    set result 0
		Netgear_log_file "lib-mvr.tcl" "Error: MVR group $grp_ip is not exist."
	}
    
    Netgear_disconnect_switch $switch_name
	return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRSetInterface
#
#  Description    : This function is called to set MVR interface configure on specified
#                   switch.
#
#  Usage          : _ntgrMVRSetInterface <switch_name> <port> <mvr_type> <immediate_type>
#
#*******************************************************************************
proc _ntgrMVRSetInterface {switch_name port mvr_type immediate_type} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
	exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
	exp_send -i $cnn_id "mvr\r"
    exp_sleep 1
	exp_send -i $cnn_id "mvr type $mvr_type\r"
    exp_sleep 1
	if {$immediate_type != ""} {
	    exp_send -i $cnn_id "mvr immediate\r"
        exp_sleep 1
	}
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mvr.tcl" "Configure interface:$port mvr type as $mvr_type ."
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRResetInterface
#
#  Description    : This function is called to reset MVR interface configure on specified
#                   switch.
#
#  Usage          : _ntgrMVRResetInterface <switch_name> <port>
#
#*******************************************************************************
proc _ntgrMVRResetInterface {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
	exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
	exp_send -i $cnn_id "no mvr type\r"
    exp_sleep 1
	exp_send -i $cnn_id "no mvr immediate\r"
    exp_sleep 1
	exp_send -i $cnn_id "no mvr\r"
    exp_sleep 1
	expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mvr.tcl" "Reset configure of interface:$port ."
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRAddMVRMembers
#
#  Description    : This function is called to add port to MVR group on specified
#                   switch.
#
#  Usage          : _ntgrMVRAddMVRMembers <switch_name> <portlist> <vlan_id> <grp_ip>
#
#*******************************************************************************
proc _ntgrMVRAddMVRMembers {switch_name portlist vlan_id grp_ip} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
	for {set i 0 } {$i < [llength $portlist]} {incr i} {
	set port [lindex $portlist $i]
	exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
	exp_send -i $cnn_id "mvr vlan $vlan_id group $grp_ip\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    }
    Netgear_log_file "lib-mvr.tcl" "Add interface:$port to group: $grp_ip ."
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRGetMVRTraffic
#
#  Description    : This function is called to check the traffic of MVR on specified
#                   switch.
#
#  Usage          : _ntgrMVRGetMVRTraffic <switch_name> <expect_statistics>
#
#*******************************************************************************
proc _ntgrMVRGetMVRTraffic {switch_name expect_statistics} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    set str1 {}
    set str2 {}
    set receive_query {}
	set receive_reportV1 {}
	set receive_reportV2 {}
	set receive_leave {}
	set query_transmit {}
	set transmit_reportV1 {}
	set transmit_reportV2 {}
	set transmit_leave {}
	set receive_failures {}
	set transmit_failures {}
	
	set output ""
	exp_send -i $cnn_id "show mvr traffic\r"
    exp_sleep 1
    expect -i $cnn_id -re "show mvr traffic"
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
	
    set regSWMAC "(IGMP Query Received\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 receive_query
	set regSWMAC "(IGMP Report V1 Received\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 receive_reportV1
	set regSWMAC "(IGMP Report V2 Received\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 receive_reportV2
	set regSWMAC "(IGMP Leave Received\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 receive_leave
	set regSWMAC "(IGMP Query Transmitted\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 query_transmit
	set regSWMAC "(IGMP Report V1 Transmitted\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 transmit_reportV1
	set regSWMAC "(IGMP Report V2 Transmitted\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 transmit_reportV2
	set regSWMAC "(IGMP Leave Transmitted\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 transmit_leave
	set regSWMAC "(IGMP Packet Receive Failures\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 receive_failures
	set regSWMAC "(IGMP Packet Transmit Failures\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 transmit_failures
	
	set result 0
	if {$expect_statistics == "IGMP Query Received"} {
	    set result $receive_query
	} elseif {$expect_statistics == "IGMP Report V1 Received"} {
	    set result $receive_reportV1
	} elseif {$expect_statistics == "IGMP Report V2 Received"} {
	    set result $receive_reportV2
	} elseif {$expect_statistics == "IGMP Leave Received"} {
	    set result $receive_leave
	} elseif {$expect_statistics == "IGMP Query Transmitted"} {
	    set result $query_transmit
	} elseif {$expect_statistics == "IGMP Report V1 Transmitted"} {
	    set result $transmit_reportV1
	} elseif {$expect_statistics == "IGMP Report V2 Transmitted"} {
	    set result $transmit_reportV2
	} elseif {$expect_statistics == "IGMP Leave Transmitted"} {
	    set result $transmit_leave
	} elseif {$expect_statistics == "IGMP Packet Receive Failures"} {
	    set result $receive_failures
	} elseif {$expect_statistics == "IGMP Packet Transmit Failures"} {
	    set result $transmit_failures
	} else {
	    Netgear_log_file "lib-mvr.tcl" "Error: Invalid MVR Expect Statistics Type."
	}
	
	return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRGetMVRGlobalConfigure
#
#  Description    : This function is called to get the global configure of MVR on specified
#                   switch.
#
#  Usage          : _ntgrMVRGetMVRGlobalConfigure <switch_name> <expect_text>
#
#*******************************************************************************
proc _ntgrMVRGetMVRGlobalConfigure {switch_name expect_text} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    set str1 {}
    set str2 {}
	set mvr_vlan {}
	set mvr_max_group {}
	set mvr_real_group {}
	set mvr_mode {}
	
	set output ""
	exp_send -i $cnn_id "show mvr\r"
    exp_sleep 1
    expect -i $cnn_id -re "show mvr"
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

	set regSWMAC "(MVR multicast VLAN\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 mvr_vlan
	set regSWMAC "(MVR Max Multicast Groups\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 mvr_max_group
	set regSWMAC "(MVR Current multicast groups\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 mvr_real_group
	set regSWMAC "(MVR Mode\[.\]+)(\[ ]+)(\[a-z\]+)"
    regexp $regSWMAC $output match str1 str2 mvr_mode
	
	set result 0
	if {$expect_text == "MVR multicast VLAN"} {
	    set result $mvr_vlan
	} elseif {$expect_text == "MVR Max Multicast Groups"} {
	    set result $mvr_max_group
	} elseif {$expect_text == "MVR Current multicast groups"} {
	    set result $mvr_real_group
	} elseif {$expect_text == "MVR Mode"} {
	    set result $mvr_mode
	} else {
	    Netgear_log_file "lib-mvr.tcl" "Error: Invalid MVR Configure Type."
	}
	
	return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrMVRCheckMVRInterfaceConfigure
#
#  Description    : This function is called to check MVR group configure on specified
#                   switch.
#
#  Usage          : _ntgrMVRCheckMVRInterfaceConfigure <switch_name> <port> <type> <status> <leave_type>
#
#*******************************************************************************
proc _ntgrMVRCheckMVRInterfaceConfigure {switch_name port type status leave_type} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
	set str1 {}
	set str2 {}
	set str3 {}
	set str4 {}
	set str5 {}
	set real_type {}
	set real_status {}
	set real_leave_type {}
	
    exp_send -i $cnn_id "show mvr interface $port \r"
    exp_sleep 1
    expect -i $cnn_id -re "show mvr interface $port"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
	

	Netgear_log_file "lib-mvr.tcl" "Interface $port MVR status : $output"
    set result 1
	
	set regSWMAC "(Type:\[ ]+)(\[a-zA-Z\]+)(\[ ]+)(Status:\[ ]+)(\[a-zA-Z/a-zA-Z\]+)(\[ ]+)(Immediate Leave:\[ ]+)(\[a-zA-Z/a-zA-Z\]+)"
    regexp $regSWMAC $output match str1 real_type str2 str3 real_status str4 str5 real_leave_type
    Netgear_disconnect_switch $switch_name

	if {[string tolower $real_type] != [string tolower $type] || [string tolower $real_status] != [string tolower $status] || [string tolower $real_leave_type] != [string tolower $leave_type]} {
	    set result 0
		Netgear_log_file "lib-mvr.tcl" "Error: Check MVR Interface Configure Failed."
	}
	return $result
}
