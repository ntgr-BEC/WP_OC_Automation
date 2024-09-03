#!/bin/sh
################################################################################   
#
#  File Name		  : lib-ipv6_source_guard.tcl
#
#  Description      :
#         This TCL contains functions to configure ipv6 source guard
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        20-May-15     Jason Li              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : _ntgrIPv6SGAddStaticBindingConfig
#
#  Description    : This function is called to add static binding configuration
#
#  Usage          : _ntgrIPv6SGAddStaticBindingConfig <switch_name>
#
#*******************************************************************************
proc _ntgrIPv6SGAddStaticBindingConfig {switch_name} {    

  	global IPSG_Interface_Config
  	set port [keylget IPSG_Interface_Config($switch_name) IPSG_PORT]  
  	set mac [keylget IPSG_Interface_Config($switch_name) IPSG_MAC]  
  	set vlanid [keylget IPSG_Interface_Config($switch_name) IPSG_VLANID]  
  	set ipaddr [keylget IPSG_Interface_Config($switch_name) IPSG_IPADDR]  
	 
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 verify binding $mac vlan $vlanid $ipaddr interface $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6_source_guard.tcl" "add static binding configuration on switcher"
    Netgear_disconnect_switch $switch_name
}  

#*******************************************************************************
#
#  Function Name  : _ntgrIPv6SGDelStaticBindingConfig
#
#  Description    : This function is called to delete static binding configuration
#
#  Usage          : _ntgrIPv6SGDelStaticBindingConfig <switch_name>
#
#*******************************************************************************
proc _ntgrIPv6SGDelStaticBindingConfig {switch_name} {    

	global IPSG_Interface_Config
	set port [keylget IPSG_Interface_Config($switch_name) IPSG_PORT]  
	set mac [keylget IPSG_Interface_Config($switch_name) IPSG_MAC]  
	set vlanid [keylget IPSG_Interface_Config($switch_name) IPSG_VLANID]  
	set ipaddr [keylget IPSG_Interface_Config($switch_name) IPSG_IPADDR]  

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 verify binding $mac vlan $vlanid $ipaddr interface $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6_source_guard.tcl" "delete static binding configuration on switcher"
    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrIPv6SGEnableMode
#
#  Description    : This function is called to enable ipv6 source guard mode
#
#  Usage          : _ntgrIPv6SGEnableMode <switch_name>
#
#*******************************************************************************
proc _ntgrIPv6SGEnableMode {switch_name} {    

	  global IPSG_Interface_Config
	  set port [keylget IPSG_Interface_Config($switch_name) IPSG_PORT]  

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 verify source\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r" 
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6_source_guard.tcl" "enable ipv6 source guard mode on switcher"
    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrIPv6SGDisableMode
#
#  Description    : This function is called to disable ip source guard mode
#
#  Usage          : _ntgrIPv6SGDisableMode <switch_name>
#
#*******************************************************************************
proc _ntgrIPv6SGDisableMode {switch_name} {    

	global IPSG_Interface_Config
	set port [keylget IPSG_Interface_Config($switch_name) IPSG_PORT]  

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 verify source\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r" 
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6_source_guard.tcl" "disable ipv6 source guard mode on switcher"
    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrIPv6SGEnablePortSecurity
#
#  Description    : This function is called to enable port security
#
#  Usage          : _ntgrIPv6SGEnablePortSecurity <switch_name>
#
#*******************************************************************************
proc _ntgrIPv6SGEnablePortSecurity {switch_name} {    

	global IPSG_Interface_Config
	set port [keylget IPSG_Interface_Config($switch_name) IPSG_PORT]  

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 verify source port-security\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r" 
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6_source_guard.tcl" "enable ipv6 source guard mode on switcher"
    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrIPv6SGDisablePortSecurity
#
#  Description    : This function is called to disable port security
#
#  Usage          : _ntgrIPv6SGDisablePortSecurity <switch_name>
#
#*******************************************************************************
proc _ntgrIPv6SGDisablePortSecurity {switch_name} {    

	global IPSG_Interface_Config
	set port [keylget IPSG_Interface_Config($switch_name) IPSG_PORT]  

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 verify source\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r" 
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6_source_guard.tcl" "disable ipv6 source guard port security on switcher"
    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrIPv6SGClearBindingConfiguration
#
#  Description    : This function is called to clear ipv6 source guard binding configuration on specified
#                   switch.
#
#  Usage          : _ntgrIPv6SGClearBindingConfiguration <switch_name>
#
#*******************************************************************************
proc _ntgrIPv6SGClearBindingConfiguration {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "clear ipv6 dhcp snooping binding\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6_source_guard.tcl" "clear ipv6 source guard binding on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#  Function Name	: _ntgrCheckIPv6SGEntry
#
#  Description    	: This function is called to check ip source guard entry on switch
#         
#  Usage          	: _ntgrCheckIPv6SGEntry <switch_name> <mac_list> <{notin 0}>
#
#
#*******************************************************************************
proc _ntgrCheckIPv6SGEntry {switch_name mac_list {notin 0}} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    set output ""
    
    exp_send -i $cnn_id "show ipv6 source binding\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ipv6 source binding"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
   sleep 1 
   Netgear_log_file "lib-ipv6_source_guard.tcl" " the buffer is $output"
    
    set result 1
    foreach mac $mac_list {
        if {$notin == 1} {
            if {[regexp -nocase $mac $output]} {
                set result 0
                break
            }
        } else {
            if {![regexp -nocase $mac $output]} {
                set result 0
                break
            }
        }
    }

    Netgear_log_file "lib-ipv6_source_guard.tcl" "Check ipv6 source guard entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
} 

#*******************************************************************************
#
#  Function Name  : _ntgrIPv6SGClearBindingConfiguration
#
#  Description    : This function is called to clear ipv6 source guard binding configuration on specified
#                   switch.
#
#  Usage          : _ntgrIPv6SGClearBindingConfiguration <switch_name>
#
#*******************************************************************************
proc _ntgrSearchIPv6SGAddrByMAC {switch_name mac} {
  set cmds "show ipv6 source binding" 
	set retStr ""
  set ipv6_addr "::"
  CALCheckExpect $switch_name $cmds retStr 
  set tmpList [split $retStr \n] 
	foreach iList $tmpList {
   if {[lindex $iList 0] == "$mac" } {
      set ipv6_addr [lindex $iList 1]
      break
    } 
  } 
  Netgear_log_file "" "DHCPv6 Client mac is $mac, IPv6 Addr is $ipv6_addr."   
 return $ipv6_addr
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingIntfTrustEnable
#
#  Description    : This function is called to enable dhcp snooping interface on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingIntfTrustEnable <switch_name> <if_list>
#
#*******************************************************************************
proc _ntgrDHCPv6SnoopingIntfTrustEnable {switch_name if_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
	after 200
    Netgear_log_file "lib-dhcpsnooping.tcl" "Enable dhcp snooping trust on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "ipv6 dhcp snooping trust\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingIntfTrustDisable
#
#  Description    : This function is called to disable dhcp snooping interface on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingIntfTrustDisable <switch_name> <if_list>
#
#*******************************************************************************
proc _ntgrDHCPv6SnoopingIntfTrustDisable {switch_name if_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
	after 200
    Netgear_log_file "lib-dhcpsnooping.tcl" "Disable dhcp snooping trust on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no ipv6 dhcp snooping trust\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPv6SnoopingVlanEnable
#
#  Description    : This function is called to enable dhcp snooping vlan on specified
#                   switch.
#
#  Usage          : _ntgrDHCPv6SnoopingVlanEnable <switch_name> <vlan_id>
#
#*******************************************************************************
proc _ntgrDHCPv6SnoopingVlanEnable {switch_name vlan_id} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 dhcp snooping vlan $vlan_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6_source_guard.tcl" "enable dhcpsnooping vlan $vlan_id on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingVlanDisable
#
#  Description    : This function is called to disable dhcp snooping vlan on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingVlanDisable <switch_name> <vlan_id>
#
#*******************************************************************************
proc _ntgrDHCPv6SnoopingVlanDisable {switch_name vlan_id} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 dhcp snooping vlan $vlan_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6_source_guard.tcl" "disable dhcpsnooping vlan $vlan_id on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPv6SnoopingVlanEnable
#
#  Description    : This function is called to enable dhcp snooping vlan on specified
#                   switch.
#
#  Usage          : _ntgrDHCPv6SnoopingVlanEnable <switch_name> <vlan_id>
#
#*******************************************************************************
proc _ntgrDHCPv6SnoopingEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 dhcp snooping\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6_source_guard.tcl" "enable dhcpv6 snooping on switcher."
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPv6SnoopingDisable
#
#  Description    : This function is called to disable dhcp snooping vlan on specified
#                   switch.
#
#  Usage          : _ntgrDHCPv6SnoopingDisable <switch_name> <vlan_id>
#
#*******************************************************************************
proc _ntgrDHCPv6SnoopingDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 dhcp snooping\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6_source_guard.tcl" "disable dhcpv6 snooping on switcher"
    Netgear_disconnect_switch $switch_name
}




