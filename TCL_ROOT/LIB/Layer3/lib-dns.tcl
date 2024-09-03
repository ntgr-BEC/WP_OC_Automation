#!/bin/sh
################################################################################   
#
#  File Name		  : lib-dns.tcl
#
#  Description      :
#         This TCL contains functions to configure ip host
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        24-Jan-11     Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : _ntgrAddIpHost
#
#  Description    : This function is called to add ip host and ip related
#
#  Usage          : _ntgrAddIpHost <switch_name> <hostname> <ipaddress>
#
#*******************************************************************************
proc _ntgrAddIpHost {switch_name hostname ipaddress} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip host $hostname $ipaddress\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dns.tcl" "add ip host and ip related on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrDeleteIpHost
#
#  Description    : This function is called to delete ip host and ip related
#
#  Usage          : _ntgrDeleteIpHost <switch_name> <hostname>
#
#*******************************************************************************
proc _ntgrDeleteIpHost {switch_name hostname} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip host $hostname\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dns.tcl" "delete ip host and ip related on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetNameServer
#
#  Description    : This function is called to get name server
#
#  Usage          : _ntgrGetNameServer <switch_name>
#
#*******************************************************************************
proc _ntgrGetNameServer {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show hosts\r"
    exp_sleep 1
    expect -i $cnn_id -re "show hosts"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
    
    set nameServer "(Default Gateway\[.\]+)(\[ ]+)(\[0-9.\]+)"
    regexp -nocase {Name servers \(Preference order\)\.+\s*(\d+\.\d+\.\d+\.\d+)} $output match str1
    
    Netgear_log_file "lib-dns.tcl" "get name server on switcher"
    Netgear_disconnect_switch $switch_name
    
    return $str1
}








#*******************************************************************************
#
#  Function Name  : _ntgrAddIpv6Host
#
#  Description    : This function is called to add ipv6 host and ip related
#
#  Usage          : _ntgrAddIpv6Host <switch_name> <hostname> <ipaddress>
#
#*******************************************************************************
proc _ntgrAddIpv6Host {switch_name hostname ipaddress} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 host $hostname $ipaddress\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dns.tcl" "add ipv6 host and ip related on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrDeleteIpv6Host
#
#  Description    : This function is called to delete ipv6 host and ip related
#
#  Usage          : _ntgrDeleteIpv6Host <switch_name> <hostname>
#
#*******************************************************************************
proc _ntgrDeleteIpv6Host {switch_name hostname} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 host $hostname\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dns.tcl" "delete ipv6 host and ip related on switcher"
    Netgear_disconnect_switch $switch_name
}  

#*******************************************************************************
#
#  Function Name  : _ntgrSetDNSDefaultName
#
#  Description    : This function is called to set dns default name
#
#  Usage          : _ntgrSetDNSDefaultName <switch_name> <dns_name>
#
#*******************************************************************************
proc _ntgrSetDNSDefaultName {switch_name dns_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip domain name $dns_name\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dns.tcl" "set dns domain name on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrDeleteDNSDefaultName
#
#  Description    : This function is called to delete dns default name
#
#  Usage          : _ntgrDeleteDNSDefaultName <switch_name>
#
#*******************************************************************************
proc _ntgrDeleteDNSDefaultName {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip domain name\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dns.tcl" "delete dns default name on switcher"
    Netgear_disconnect_switch $switch_name
}  

#*******************************************************************************
#
#  Function Name  : _ntgrSetDNSNameServer
#
#  Description    : This function is called to set dns name server
#
#  Usage          : _ntgrSetDNSNameServer <switch_name> <name_server>
#
#*******************************************************************************
proc _ntgrSetDNSNameServer {switch_name name_server} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip name server $name_server\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dns.tcl" "set dns server on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrDeleteDNSNameServer
#
#  Description    : This function is called to delete dns server
#
#  Usage          : _ntgrDeleteDNSNameServer <switch_name> <dns_server>
#
#*******************************************************************************
proc _ntgrDeleteDNSNameServer {switch_name dns_server} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip name server $dns_server\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dns.tcl" "delete dns server $dns_server on switcher"
    Netgear_disconnect_switch $switch_name
} 