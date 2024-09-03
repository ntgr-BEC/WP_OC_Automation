#!/bin/sh
################################################################################   
#
#  File Name		  : lib-ip_source_guard.tcl
#
#  Description      :
#         This TCL contains functions to configure ip source guard
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        20-Feb-14     Jason Li              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : _ntgrIPSGAddStaticBindingConfig
#
#  Description    : This function is called to add static binding configuration
#
#  Usage          : _ntgrIPSGAddStaticBindingConfig <switch_name>
#
#*******************************************************************************
proc _ntgrIPSGAddStaticBindingConfig {switch_name} {    

	global IPSG_Interface_Config
	set port [keylget IPSG_Interface_Config($switch_name) IPSG_PORT]  
	set mac [keylget IPSG_Interface_Config($switch_name) IPSG_MAC]  
	set vlanid [keylget IPSG_Interface_Config($switch_name) IPSG_VLANID]  
	set ipaddr [keylget IPSG_Interface_Config($switch_name) IPSG_IPADDR]  
	 
    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip verify binding $mac vlan $vlanid $ipaddr interface $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ip_source_guard.tcl" "add static binding configuration on switcher"
    Netgear_disconnect_switch $switch_name
}       

#*******************************************************************************
#
#  Function Name  : _ntgrIPSGDelStaticBindingConfig
#
#  Description    : This function is called to delete static binding configuration
#
#  Usage          : _ntgrIPSGDelStaticBindingConfig <switch_name>
#
#*******************************************************************************
proc _ntgrIPSGDelStaticBindingConfig {switch_name} {    

	global IPSG_Interface_Config
	set port [keylget IPSG_Interface_Config($switch_name) IPSG_PORT]  
	set mac [keylget IPSG_Interface_Config($switch_name) IPSG_MAC]  
	set vlanid [keylget IPSG_Interface_Config($switch_name) IPSG_VLANID]  
	set ipaddr [keylget IPSG_Interface_Config($switch_name) IPSG_IPADDR]  

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip verify binding $mac vlan $vlanid $ipaddr interface $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ip_source_guard.tcl" "delete static binding configuration on switcher"
    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrIPSGEnableMode
#
#  Description    : This function is called to enable ip source guard mode
#
#  Usage          : _ntgrIPSGEnableMode <switch_name>
#
#*******************************************************************************
proc _ntgrIPSGEnableMode {switch_name} {    

	global IPSG_Interface_Config
	set port [keylget IPSG_Interface_Config($switch_name) IPSG_PORT]  

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip verify source\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r" 
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ip_source_guard.tcl" "enable ip source guard mode on switcher"
    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrIPSGDisableMode
#
#  Description    : This function is called to disable ip source guard mode
#
#  Usage          : _ntgrIPSGDisableMode <switch_name>
#
#*******************************************************************************
proc _ntgrIPSGDisableMode {switch_name} {    

	global IPSG_Interface_Config
	set port [keylget IPSG_Interface_Config($switch_name) IPSG_PORT]  

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip verify source\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r" 
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ip_source_guard.tcl" "disable ip source guard mode on switcher"
    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrIPSGEnablePortSecurity
#
#  Description    : This function is called to enable port security
#
#  Usage          : _ntgrIPSGEnablePortSecurity <switch_name>
#
#*******************************************************************************
proc _ntgrIPSGEnablePortSecurity {switch_name} {    

	global IPSG_Interface_Config
	set port [keylget IPSG_Interface_Config($switch_name) IPSG_PORT]  

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip verify source port-security\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r" 
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ip_source_guard.tcl" "enable ip source guard mode on switcher"
    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrIPSGDisablePortSecurity
#
#  Description    : This function is called to disable port security
#
#  Usage          : _ntgrIPSGDisablePortSecurity <switch_name>
#
#*******************************************************************************
proc _ntgrIPSGDisablePortSecurity {switch_name} {    

	global IPSG_Interface_Config
	set port [keylget IPSG_Interface_Config($switch_name) IPSG_PORT]  

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip verify source\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r" 
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ip_source_guard.tcl" "disable ip source guard port security on switcher"
    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#  Function Name	: _ntgrCheckIPSGEntry
#
#  Description    	: This function is called to check ip source guard entry on switch
#         
#  Usage          	: _ntgrCheckIPSGEntry <switch_name> <ip_list> <{notin 0}>
#
#
#*******************************************************************************
proc _ntgrCheckIPSGEntry {switch_name ip_list {notin 0}} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    set output ""
    
    exp_send -i $cnn_id "show ip source binding\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip source binding"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
    
    set result 1
    foreach ip $ip_list {
        if {$notin == 1} {
            if {[regexp -nocase $ip $output]} {
                set result 0
                break
            }
        } else {
            if {![regexp -nocase $ip $output]} {
                set result 0
                break
            }
        }
    }

    Netgear_log_file "lib-ip_source_guard.tcl" "Check ip source guard entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
} 

#*******************************************************************************
#
#  Function Name  : _ntgrIPSGClearBindingConfiguration
#
#  Description    : This function is called to clear ip source guard binding configuration on specified
#                   switch.
#
#  Usage          : _ntgrIPSGClearBindingConfiguration <switch_name>
#
#*******************************************************************************
proc _ntgrIPSGClearBindingConfiguration {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "clear ip dhcp snooping binding\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ip_source_guard.tcl" "clear ip source guard binding on switcher"
    Netgear_disconnect_switch $switch_name
}

