#!/bin/sh
################################################################################   
#
#  File Name		  : lib-ping.tcl
#
#  Description      :
#         This TCL contains functions to ping
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        21-Apr-11     Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : _ntgrPingCheck
#
#  Description    : This function is used to check ping function.
#
#  Usage          : _ntgrPingCheck <switch_name>
#
#*******************************************************************************
proc _ntgrPingCheck {switch_name} {
    
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
#    Netgear_connect_switch $switch_name
#    set cnn_id [_get_switch_handle $switch_name]
        
    set ipaddr  [_getPingIPAddr $switch_name]
    set source_adr   [_getPingSource $switch_name]
    set count   [_getPingCount $switch_name]
    set inteval [_getPingInterval $switch_name]
    set size    [_getPingSize $switch_name]
    
    set cmdstr "ping $ipaddr"
    if {$source_adr != ""} {
        append cmdstr " source $source_adr"
    }
    if {$count != ""} {
        append cmdstr " count $count"
    }
    if {$inteval != ""} {
        append cmdstr " interval $inteval"
    }
    if {$size != ""} {
        append cmdstr " size $size"
    }
    
    set output ""
    exp_send -i $cnn_id "$cmdstr\r"
    exp_sleep 5
    expect -i $cnn_id -re "$cmdstr"
    
    set timeout 60
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
    }
   
    set output $expect_out(buffer)
    set result 0
        
    if {[regexp -nocase {(\d+)% packet} $output dummy loserate]} {  
       
        if {$loserate < 100} {
            set result 1
        }
    }
        
    Netgear_log_file "lib-ping.tcl" "ping on $switch_name"

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }     
#    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrPingConfigErrorCheck
#
#  Description    : This function is used to check ping config error.
#
#  Usage          : _ntgrPingConfigErrorCheck <switch_name>
#
#*******************************************************************************
proc _ntgrPingConfigErrorCheck {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    set ipaddr  [_getPingIPAddr $switch_name]
    set count   [_getPingCount $switch_name]
    set inteval [_getPingInterval $switch_name]
    set size    [_getPingSize $switch_name]
    
    set cmdstr "ping $ipaddr"
    if {$count != ""} {
        append cmdstr " count $count"
    }
    if {$inteval != ""} {
        append cmdstr " interval $inteval"
    }
    if {$size != ""} {
        append cmdstr " size $size"
    }
    
        
    set output ""
        
    exp_send -i $cnn_id "$cmdstr\r"
    exp_sleep 1
    expect -i $cnn_id -re "ping"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    
    if {[regexp -nocase {Value is out of range|Invalid input|Incorrect input|failed} $output]} {
        set result 1
    } else {
        set result 0
    }
    
    Netgear_log_file "lib-ping.tcl" "ping on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}









#*******************************************************************************
#
#  Function Name  : _ntgrPingIPv6Check
#
#  Description    : This function is used to check ping ipv6 function.
#
#  Usage          : _ntgrPingIPv6Check <switch_name>
#
#*******************************************************************************
proc _ntgrPingIPv6Check {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    set ipaddr  [_getPingIPv6IPAddr $switch_name]
    set intf    [_getPingIPv6Intferface $switch_name]
    set size    [_getPingIPv6Size $switch_name]
    set source_adr   [_getPingIPv6Source $switch_name]
    set count   [_getPingIPv6Count $switch_name]
    set inteval [_getPingIPv6Interval $switch_name]
    
    if {$intf != ""} {
        set cmdstr "ping ipv6 interface $intf $ipaddr"
    } else {
        set cmdstr "ping ipv6 $ipaddr"
    }
    if {$source_adr != ""} {
        append cmdstr " source $source_adr"
    }
    if {$size != ""} {
        append cmdstr " size $size"
    }
    if {$count != ""} {
        append cmdstr " count $count"
    }
    if {$inteval != ""} {
        append cmdstr " interval $inteval"
    }
        
    set output ""
    
    set timeout 120
    exp_send -i $cnn_id "$cmdstr\r"
    exp_sleep 1
    expect -i $cnn_id -re "ping ipv6"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
        
    if {[regexp -nocase {Receive count=(\d+)} $output dummy sum]} {
        if {$sum > 0} {
            set result 1
        } else {
            set result 0
        }
    } else {
        set result 0
    }
    
    Netgear_log_file "lib-ping.tcl" "ping ipv6 on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrPingIPv6ConfigErrorCheck
#
#  Description    : This function is used to check ping ipv6 config error.
#
#  Usage          : _ntgrPingIPv6ConfigErrorCheck <switch_name>
#
#*******************************************************************************
proc _ntgrPingIPv6ConfigErrorCheck {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    set ipaddr  [_getPingIPv6IPAddr $switch_name]
    set size    [_getPingIPv6Size $switch_name]
    set intf    [_getPingIPv6Intferface $switch_name]
    
    if {$intf != ""} {
        set cmdstr "ping ipv6 interface $intf $ipaddr"
    } else {
        set cmdstr "ping ipv6 $ipaddr"
        if {$size != ""} {
            append cmdstr " size $size"
        }
    }
    
        
    set output ""
        
    exp_send -i $cnn_id "$cmdstr\r"
    exp_sleep 1
    expect -i $cnn_id -re "ping ipv6"
    exp_sleep 1
    expect -i $cnn_id -re "\\) #"
    
    set output $expect_out(buffer)    
    if {[regexp -nocase {Invalid|Command not found|Incomplete command} $output]} {
        set result 1
    } else {
        set result 0
    }
    
    Netgear_log_file "lib-ping.tcl" "ping ipv6 on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}