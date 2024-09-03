#!/bin/sh
################################################################################   
#
#  File Name		  : lib-lldp.tcl
#
#  Description      :
#         This TCL contains functions to configure lldp
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        23-May-11     Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : _ntgrLldpSetIntervalTimer
#
#  Description    : This function is called to set lldp timers of interval
#
#  Usage          : _ntgrLldpSetIntervalTimer <switch_name> <seconds>
#
#*******************************************************************************
proc _ntgrLldpSetIntervalTimer {switch_name seconds} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "lldp timers interval $seconds\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "set lldp timers of interval on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpResetIntervalTimer
#
#  Description    : This function is called to set lldp timers of interval to default
#
#  Usage          : _ntgrLldpResetIntervalTimer <switch_name>
#
#*******************************************************************************
proc _ntgrLldpResetIntervalTimer {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no lldp timers interval\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "set lldp timers of interval to default on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpSetHoldTimer
#
#  Description    : This function is called to set lldp timers of hold
#
#  Usage          : _ntgrLldpSetHoldTimer <switch_name> <hold>
#
#*******************************************************************************
proc _ntgrLldpSetHoldTimer {switch_name hold} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "lldp timers hold $hold\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "set lldp timers of hold on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpResetHoldTimer
#
#  Description    : This function is called to set lldp timers of hold to default
#
#  Usage          : _ntgrLldpResetHoldTimer <switch_name>
#
#*******************************************************************************
proc _ntgrLldpResetHoldTimer {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no lldp timers hold\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "set lldp timers of hold to default on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpSetReinitTimer
#
#  Description    : This function is called to set lldp timers of hold
#
#  Usage          : _ntgrLldpSetReinitTimer <switch_name> <seconds>
#
#*******************************************************************************
proc _ntgrLldpSetReinitTimer {switch_name seconds} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "lldp timers reinit $seconds\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "set lldp timers of reinit on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpResetReinitTimer
#
#  Description    : This function is called to set lldp timers of reinit to default
#
#  Usage          : _ntgrLldpResetReinitTimer <switch_name>
#
#*******************************************************************************
proc _ntgrLldpResetReinitTimer {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no lldp timers reinit\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "set lldp timers of reinit to default on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpSetNotificationInterval
#
#  Description    : This function is called to set lldp notificatin interval
#
#  Usage          : _ntgrLldpSetNotificationInterval <switch_name> <seconds>
#
#*******************************************************************************
proc _ntgrLldpSetNotificationInterval {switch_name seconds} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "lldp notification-interval $seconds\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "set lldp notification-interval on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpResetNotificationInterval
#
#  Description    : This function is called to set lldp notification interval to default
#
#  Usage          : _ntgrLldpResetNotificationInterval <switch_name>
#
#*******************************************************************************
proc _ntgrLldpResetNotificationInterval {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no lldp notification-interval\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "set lldp notification interval to default on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetIntervalTimer
#
#  Description    : This function is used to get lldp timers of interval
#
#  Usage          : _ntgrLldpGetIntervalTimer <switch_name>
#
#*******************************************************************************
proc _ntgrLldpGetIntervalTimer {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    set result ""
    regexp -nocase {Transmit Interval\.+\s+(\d+)} $output dummy result

    Netgear_log_file "lib-lldp.tcl" "get lldp timers of interval on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetHoldTimer
#
#  Description    : This function is used to get lldp timers of hold
#
#  Usage          : _ntgrLldpGetHoldTimer <switch_name>
#
#*******************************************************************************
proc _ntgrLldpGetHoldTimer {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    set result ""
    regexp -nocase {Transmit Hold Multiplier\.+\s+(\d+)} $output dummy result

    Netgear_log_file "lib-lldp.tcl" "get lldp timers of hold on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetReinitTimer
#
#  Description    : This function is used to get lldp timers of reinit delay
#
#  Usage          : _ntgrLldpGetReinitTimer <switch_name>
#
#*******************************************************************************
proc _ntgrLldpGetReinitTimer {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    set result ""
    regexp -nocase {Reinit Delay\.+\s+(\d+)} $output dummy result

    Netgear_log_file "lib-lldp.tcl" "get lldp timers of reinit on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetNotificationInterval
#
#  Description    : This function is used to get lldp notificaion interval
#
#  Usage          : _ntgrLldpGetNotificationInterval <switch_name>
#
#*******************************************************************************
proc _ntgrLldpGetNotificationInterval {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    set result ""
    regexp -nocase {Notification Interval\.+\s+(\d+)} $output dummy result

    Netgear_log_file "lib-lldp.tcl" "get lldp notificaion interval on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}




#*******************************************************************************
#
#  Function Name  : _ntgrLldpPortReceiveEnable
#
#  Description    : This function is called to set port lldp receive enable
#
#  Usage          : _ntgrLldpPortReceiveEnable <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpPortReceiveEnable {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "lldp receive\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "enable lldp receive on port of $port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpPortReceiveDisable
#
#  Description    : This function is called to set port lldp receive disable
#
#  Usage          : _ntgrLldpPortReceiveDisable <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpPortReceiveDisable {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no lldp receive\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "disable lldp receive on port of $port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpPortTransmitEnable
#
#  Description    : This function is called to set port lldp transmit enable
#
#  Usage          : _ntgrLldpPortTransmitEnable <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpPortTransmitEnable {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "lldp transmit\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "enable lldp transmit on port of $port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpPortTransmitDisable
#
#  Description    : This function is called to set port lldp transmit disable
#
#  Usage          : _ntgrLldpPortTransmitDisable <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpPortTransmitDisable {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no lldp transmit\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "disable lldp transmit on port of $port on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrLldpPortNotificationEnable
#
#  Description    : This function is called to set port lldp notification enable
#
#  Usage          : _ntgrLldpPortNotificationEnable <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpPortNotificationEnable {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "lldp notification\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "enable lldp notification on port of $port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpPortNotificationDisable
#
#  Description    : This function is called to set port lldp notification disable
#
#  Usage          : _ntgrLldpPortNotificationDisable <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpPortNotificationDisable {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no lldp notification\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "disable lldp notification on port of $port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpPortTransmitMgmtEnable
#
#  Description    : This function is called to set port lldp transmit-mgmt enable
#
#  Usage          : _ntgrLldpPortTransmitMgmtEnable <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpPortTransmitMgmtEnable {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "lldp transmit-mgmt\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "enable lldp transmit-mgmt on port of $port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpPortTransmitMgmtDisable
#
#  Description    : This function is called to set port lldp transmit-mgmt disable
#
#  Usage          : _ntgrLldpPortTransmitMgmtDisable <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpPortTransmitMgmtDisable {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no lldp transmit-mgmt\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "disable lldp transmit-mgmt on port of $port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpPortTransmitTlvEnable
#
#  Description    : This function is called to set port lldp transmit-tlv enable
#
#  Usage          : _ntgrLldpPortTransmitTlvEnable <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpPortTransmitTlvEnable {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "lldp transmit-tlv\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "enable lldp transmit-tlv on port of $port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpPortTransmitTlvDisable
#
#  Description    : This function is called to set port lldp transmit-tlv disable
#
#  Usage          : _ntgrLldpPortTransmitTlvDisable <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpPortTransmitTlvDisable {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no lldp transmit-tlv\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "disable lldp transmit-tlv on port of $port on switcher"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrCheckLldpEntry
#
#  Description    : This function is used to check lldp entry exist or not.
#
#  Usage          : _ntgrCheckLldpEntry <switch_name> <port> <mac>
#
#*******************************************************************************
proc _ntgrCheckLldpEntry {switch_name port mac} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp remote-device $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp remote-device $port"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }
    }
    
    set output [split $output "\n"]
    
    set result 0
    set i [lsearch -regexp $output "$port"]
    if {$i != -1} {
        set str [lindex $output $i]
        if {[regexp -nocase $mac $str]} {
            set result 1
        }
    }
    
    Netgear_log_file "lib-lldp.tcl" "get lldp entry based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetRemoteDevChassisIDSubtypeByPort
#
#  Description    : This function is used to get remote device chassis id sbutype by port
#
#  Usage          : _ntgrLldpGetRemoteDevChassisIDSubtypeByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetRemoteDevChassisIDSubtypeByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp remote-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp remote-device detail $port"
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
    regexp -nocase -line {^Chassis ID Subtype:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp remote device chassis id sbutype based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetRemoteDevChassisIDByPort
#
#  Description    : This function is used to get remote device chassis id by port
#
#  Usage          : _ntgrLldpGetRemoteDevChassisIDByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetRemoteDevChassisIDByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp remote-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp remote-device detail $port"
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
    regexp -nocase -line {^Chassis ID:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp remote device chassis id based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetRemoteDevPortIDSubtypeByPort
#
#  Description    : This function is used to get remote device port id subtype by port
#
#  Usage          : _ntgrLldpGetRemoteDevPortIDSubtypeByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetRemoteDevPortIDSubtypeByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp remote-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp remote-device detail $port"
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
    regexp -nocase -line {^Port ID Subtype:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp remote device port id subtype based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetRemoteDevPortIDByPort
#
#  Description    : This function is used to get remote device port id by port
#
#  Usage          : _ntgrLldpGetRemoteDevPortIDByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetRemoteDevPortIDByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp remote-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp remote-device detail $port"
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
    regexp -nocase -line {^Port ID:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp remote device port id based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetRemoteDevSysnameByPort
#
#  Description    : This function is used to get remote device system name by port
#
#  Usage          : _ntgrLldpGetRemoteDevSysnameByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetRemoteDevSysnameByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp remote-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp remote-device detail $port"
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
    regexp -nocase -line {^System Name:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp remote device system name based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetRemoteDevPortAdvertisementByPort
#
#  Description    : This function is used to get remote device port advertisement by port
#
#  Usage          : _ntgrLldpGetRemoteDevPortAdvertisementByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetRemoteDevPortAdvertisementByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp remote-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp remote-device detail $port"
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
    regexp -nocase -line {^\s+Address:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp remote device port advertisement based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}



#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetLocalDevChassisIDSubtypeByPort
#
#  Description    : This function is used to get local device chassis id subtype by port
#
#  Usage          : _ntgrLldpGetLocalDevChassisIDSubtypeByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetLocalDevChassisIDSubtypeByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp local-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp local-device detail $port"
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
    regexp -nocase -line {^Chassis ID Subtype:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp local device chassis id subtype based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetLocalDevChassisIDByPort
#
#  Description    : This function is used to get local device chassis id by port
#
#  Usage          : _ntgrLldpGetLocalDevChassisIDByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetLocalDevChassisIDByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp local-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp local-device detail $port"
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
    regexp -nocase -line {^Chassis ID:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp local device chassis id based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetLocalDevSysnameByPort
#
#  Description    : This function is used to get local device system name by port
#
#  Usage          : _ntgrLldpGetLocalDevSysnameByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetLocalDevSysnameByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp local-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp local-device detail $port"
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
    regexp -nocase -line {^System Name:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp local device system name based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetLocalDevSysDescriptionByPort
#
#  Description    : This function is used to get local device system description by port
#
#  Usage          : _ntgrLldpGetLocalDevSysDescriptionByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetLocalDevSysDescriptionByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp local-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp local-device detail $port"
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
    set start_pos [string first "System Description:" $output]
    if {$start_pos >= 0} {
        set start_pos [expr $start_pos + [string length "System Description:"]]
        set end_pos [string first "Port Description:" $output]
    }
    set result [string range $output $start_pos [expr $end_pos - 1]]
    regsub -all {\r|\n} $result "" result
    regsub -all {\s+} $result " " result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp local device system description based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetLocalDevSysCapabilitiesByPort
#
#  Description    : This function is used to get local device system capabilities by port
#
#  Usage          : _ntgrLldpGetLocalDevSysCapabilitiesByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetLocalDevSysCapabilitiesByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp local-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp local-device detail $port"
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
    regexp -nocase -line {^System Capabilities Supported:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp local device system capabilities based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetLocalDevPortIDSubtypeByPort
#
#  Description    : This function is used to get local device port id subtype by port
#
#  Usage          : _ntgrLldpGetLocalDevPortIDSubtypeByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetLocalDevPortIDSubtypeByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp local-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp local-device detail $port"
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
    regexp -nocase -line {^Port ID Subtype:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp local device port id subtype based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetLocalDevPortIDByPort
#
#  Description    : This function is used to get local device port id by port
#
#  Usage          : _ntgrLldpGetLocalDevPortIDByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetLocalDevPortIDByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp local-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp local-device detail $port"
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
    regexp -nocase -line {^Port ID:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp local device port id based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetLocalDevPortDescriptionByPort
#
#  Description    : This function is used to get local device port description by port
#
#  Usage          : _ntgrLldpGetLocalDevPortDescriptionByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetLocalDevPortDescriptionByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp local-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp local-device detail $port"
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
    regexp -nocase -line {^Port Description:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp local device port description based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpGetLocalDevPortAdvertisementByPort
#
#  Description    : This function is used to get local device port advertisement mgmt ip by port
#
#  Usage          : _ntgrLldpGetLocalDevPortAdvertisementByPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpGetLocalDevPortAdvertisementByPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp local-device detail $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp local-device detail $port"
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
    regexp -nocase -line {^\s+Address:\s*([^\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-lldp.tcl" "get lldp local device advertisement mgmt ip based on port of $port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}





#*******************************************************************************
#
#  Function Name  : _ntgrLldpMedSetFaststartrepeatcount
#
#  Description    : This function is called to set lldp med faststartrepeatcount
#
#  Usage          : _ntgrLldpMedSetFaststartrepeatcount <switch_name> <count>
#
#*******************************************************************************
proc _ntgrLldpMedSetFaststartrepeatcount {switch_name count} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "lldp med faststartrepeatcount $count\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "set lldp med faststartrepeatcount on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpMedGetFaststartrepeatcount
#
#  Description    : This function is used to get lldp med faststartrepeatcount
#
#  Usage          : _ntgrLldpGetNotificationInterval <switch_name>
#
#*******************************************************************************
proc _ntgrLldpMedGetFaststartrepeatcount {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show lldp med\r"
    exp_sleep 1
    expect -i $cnn_id -re "show lldp med"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    set result ""
    regexp -nocase {Fast Start Repeat Count:\s+(\d+)} $output dummy result

    Netgear_log_file "lib-lldp.tcl" "get lldp med faststartrepeatcount on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}



#*******************************************************************************
#
#  Function Name  : _ntgrLldpMedPortEnable
#
#  Description    : This function is called to set port lldp med enable
#
#  Usage          : _ntgrLldpMedPortEnable <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpMedPortEnable {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "lldp med\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "enable lldp med on port of $port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLldpMedPortDisable
#
#  Description    : This function is called to set port lldp med disable
#
#  Usage          : _ntgrLldpMedPortDisable <switch_name> <port>
#
#*******************************************************************************
proc _ntgrLldpMedPortDisable {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no lldp med\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lldp.tcl" "disable lldp med on port of $port on switcher"
    Netgear_disconnect_switch $switch_name
}