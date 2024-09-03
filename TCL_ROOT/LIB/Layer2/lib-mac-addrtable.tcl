####################################################################################
#  File Name   : lib-mac-addrtable.tcl
#
#  Description :
#        This file includes functions used for mac address-table configuration.
#
#  History     :
#        Date          Programmer         Description
#        06/25/2012    Tony Jing          Created
#
####################################################################################


#*******************************************************************************
#
#  Function Name  : _ntgrMacAddrTableSetMulticastForwardMode
#
#  Description    : This function is called to config mac address-table multicast forwarding mode 
#                   on specified switch.
#
#  Usage          : _ntgrMacAddrTableSetMulticastForwardMode <switch_name> <vlan> <mode>
#
#*******************************************************************************
proc _ntgrMacAddrTableSetMulticastForwardMode {switch_name vlan mode} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "mac address-table multicast $mode vlan $vlan\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mac-addrtable.tcl" "set mac address-table multicast forwarding mode on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMacAddrTableGetAddrCount
#
#  Description    : This function is called to get address count of address table on specified
#                   switch.
#
#  Usage          : _ntgrMacAddrTableGetAddrCount <switch_name> <expect_text>
#
#  Author         : jim.xie
#*******************************************************************************
proc _ntgrMacAddrTableGetAddrCount {switch_name expect_text} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    set str1 {}
    set str2 {}
	set max_table {}
	set current_count {}

	set output ""
	exp_send -i $cnn_id "show mac-addr-table count\r"
    exp_sleep 1
    expect -i $cnn_id -re "show mac-addr-table count"
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

	set regSWMAC "(Total MAC Addresses available\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 max_table
	set regSWMAC "(Total MAC Addresses in use\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 current_count
	
	set result 0
	if {$expect_text == "Total MAC Addresses available"} {
	    set result $max_table
	} elseif {$expect_text == "Total MAC Addresses in use"} {
	    set result $current_count
	} else {
	    Netgear_log_file "lib-mac-addrtable.tcl" "Error: Invalid Table Configure Type."
	}
	
	return $result
}