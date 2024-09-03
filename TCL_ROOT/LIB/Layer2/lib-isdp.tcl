#!/bin/sh
################################################################################   
#
#  File Name		  : lib-isdp.tcl
#
#  Description      :
#         This TCL contains functions to configure isdp
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        26-Aug-11     Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpEnable
#
#  Description    : This function is called to enable isdp
#
#  Usage          : _ntgrIsdpEnable <switch_name>
#
#*******************************************************************************
proc _ntgrIsdpEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "isdp run\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lsdp.tcl" "enable isdp on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpDisable
#
#  Description    : This function is called to disable isdp
#
#  Usage          : _ntgrIsdpDisable <switch_name>
#
#*******************************************************************************
proc _ntgrIsdpDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no isdp run\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lsdp.tcl" "enable isdp on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrIsdpAdvertiseV2Enable
#
#  Description    : This function is called to enable isdp advertise-v2
#
#  Usage          : _ntgrIsdpAdvertiseV2Enable <switch_name>
#
#*******************************************************************************
proc _ntgrIsdpAdvertiseV2Enable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "isdp advertise-v2\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lsdp.tcl" "enable isdp advertise-v2  on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpAdvertiseV2Disable
#
#  Description    : This function is called to disable isdp advertise-v2
#
#  Usage          : _ntgrIsdpAdvertiseV2Disable <switch_name>
#
#*******************************************************************************
proc _ntgrIsdpAdvertiseV2Disable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no isdp advertise-v2\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lsdp.tcl" "disable isdp advertise-v2  on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpSetTimer
#
#  Description    : This function is called to set isdp timer
#
#  Usage          : _ntgrIsdpSetTimer <switch_name> <timer>
#
#*******************************************************************************
proc _ntgrIsdpSetTimer {switch_name timer} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "isdp timer $timer\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lsdp.tcl" "set isdp timer on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpSetHoldTime
#
#  Description    : This function is called to set isdp hold time
#
#  Usage          : _ntgrIsdpSetHoldTime <switch_name> <holdtime>
#
#*******************************************************************************
proc _ntgrIsdpSetHoldTime {switch_name holdtime} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "isdp holdtime $holdtime\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lsdp.tcl" "set isdp hold time on switcher"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrIsdpGetTimer
#
#  Description    : This function is used to get isdp timer.
#
#  Usage          : _ntgrIsdpGetTimer <switch_name>
#
#*******************************************************************************
proc _ntgrIsdpGetTimer {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    exp_send -i $cnn_id "show isdp\r"
    exp_sleep 1
    expect -i $cnn_id -re "show isdp"
    exp_sleep 1
    expect -i $cnn_id -re "#"

    set output ""
    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase {Timer\.+\s+(\d+)} $output dummy result
        
    Netgear_log_file "lib-lsdp.tcl" "get isdp timer on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpGetHoldTime
#
#  Description    : This function is used to get isdp hold time.
#
#  Usage          : _ntgrIsdpGetHoldTime <switch_name>
#
#*******************************************************************************
proc _ntgrIsdpGetHoldTime {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    exp_send -i $cnn_id "show isdp\r"
    exp_sleep 1
    expect -i $cnn_id -re "show isdp"
    exp_sleep 1
    expect -i $cnn_id -re "#"

    set output ""
    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase {Hold Time\.+\s+(\d+)} $output dummy result
        
    Netgear_log_file "lib-lsdp.tcl" "get isdp hold time on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpGetSerialNumber
#
#  Description    : This function is used to get isdp serial number.
#
#  Usage          : _ntgrIsdpGetSerialNumber <switch_name>
#
#*******************************************************************************
proc _ntgrIsdpGetSerialNumber {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    exp_send -i $cnn_id "show isdp\r"
    exp_sleep 1
    expect -i $cnn_id -re "show isdp"
    exp_sleep 1
    expect -i $cnn_id -re "#"

    set output ""
    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase {Device ID\.+\s+([^\r\n]+)} $output dummy result
    set result [string trim $result]
        
    Netgear_log_file "lib-lsdp.tcl" "get isdp serial number on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpGetNeighborLastChangedTime
#
#  Description    : This function is used to get isdp neighbor last changed time.
#
#  Usage          : _ntgrIsdpGetNeighborLastChangedTime <switch_name> <port>
#
#*******************************************************************************
proc _ntgrIsdpGetNeighborLastChangedTime {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    exp_send -i $cnn_id "show isdp neighbors $port detail\r"
    exp_sleep 1
    expect -i $cnn_id -re "show isdp neighbors $port detail"
    exp_sleep 1
    expect -i $cnn_id -re "#"

    set output ""
    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase -line {^Time .+ last changed\s+([^\r\n]+)} $output dummy result
    set result [string trim $result]
        
    Netgear_log_file "lib-lsdp.tcl" "get isdp neighbor last changed time on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpGetNeighborDeviceID
#
#  Description    : This function is used to get isdp neighbor device id.
#
#  Usage          : _ntgrIsdpGetNeighborDeviceID <switch_name> <port>
#
#*******************************************************************************
proc _ntgrIsdpGetNeighborDeviceID {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    exp_send -i $cnn_id "show isdp neighbors $port detail\r"
    exp_sleep 1
    expect -i $cnn_id -re "show isdp neighbors $port detail"
    exp_sleep 1
    expect -i $cnn_id -re "#"

    set output ""
    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase -line {^Device ID\s+([^\r\n]+)} $output dummy result
    set result [string trim $result]
        
    Netgear_log_file "lib-lsdp.tcl" "get isdp neighbor device id on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpGetNeighborHoldTime
#
#  Description    : This function is used to get isdp neighbor hole time.
#
#  Usage          : _ntgrIsdpGetNeighborHoldTime <switch_name> <port>
#
#*******************************************************************************
proc _ntgrIsdpGetNeighborHoldTime {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    exp_send -i $cnn_id "show isdp neighbors $port detail\r"
    exp_sleep 1
    expect -i $cnn_id -re "show isdp neighbors $port detail"
    exp_sleep 1
    expect -i $cnn_id -re "#"

    set output ""
    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase -line {^Holdtime\s+(\d+)} $output dummy result
        
    Netgear_log_file "lib-lsdp.tcl" "get isdp neighbor hold time on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpGetNeighborAdvertisementVersion
#
#  Description    : This function is used to get isdp neighbor advertisement version.
#
#  Usage          : _ntgrIsdpGetNeighborAdvertisementVersion <switch_name> <port>
#
#*******************************************************************************
proc _ntgrIsdpGetNeighborAdvertisementVersion {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    exp_send -i $cnn_id "show isdp neighbors $port detail\r"
    exp_sleep 1
    expect -i $cnn_id -re "show isdp neighbors $port detail"
    exp_sleep 1
    expect -i $cnn_id -re "#"

    set output ""
    set output $expect_out(buffer)
    regsub -all {\r|\n} $output "" output
   
    set result ""
    regexp -nocase {Advertisement Version\s+(\d+)} $output dummy result
        
    Netgear_log_file "lib-lsdp.tcl" "get isdp neighbor advertisement version on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpGetNeighborIpAddress
#
#  Description    : This function is used to get isdp neighbor ip address.
#
#  Usage          : _ntgrIsdpGetNeighborIpAddress <switch_name> <port>
#
#*******************************************************************************
proc _ntgrIsdpGetNeighborIpAddress {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    exp_send -i $cnn_id "show isdp neighbors $port detail\r"
    exp_sleep 1
    expect -i $cnn_id -re "show isdp neighbors $port detail"
    exp_sleep 1
    expect -i $cnn_id -re "#"

    set output ""
    set output $expect_out(buffer)
    
    set result ""
    set result_str [regexp -nocase -all -inline {IP Address:\s+([^\s]+)} $output]
    if {[string trim $result_str] != ""} {
        foreach {i j} $result_str {
            lappend result $j
        }
    }
        
    Netgear_log_file "lib-lsdp.tcl" "get isdp neighbor ip address on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpGetNeighborVersion
#
#  Description    : This function is used to get isdp neighbor ip address.
#
#  Usage          : _ntgrIsdpGetNeighborVersion <switch_name> <port>
#
#*******************************************************************************
proc _ntgrIsdpGetNeighborVersion {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    exp_send -i $cnn_id "show isdp neighbors $port detail\r"
    exp_sleep 1
    expect -i $cnn_id -re "show isdp neighbors $port detail"
    exp_sleep 1
    expect -i $cnn_id -re "#"

    set output ""
    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase -line {^Version\s*:?\s+([^\s]+)} $output dummy result
    set result [string trim $result]
            
    Netgear_log_file "lib-lsdp.tcl" "get isdp neighbor version on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpGetNeighborPortID
#
#  Description    : This function is used to get isdp neighbor port id.
#
#  Usage          : _ntgrIsdpGetNeighborPortID <switch_name> <port>
#
#*******************************************************************************
proc _ntgrIsdpGetNeighborPortID {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    exp_send -i $cnn_id "show isdp neighbors $port detail\r"
    exp_sleep 1
    expect -i $cnn_id -re "show isdp neighbors $port detail"
    exp_sleep 1
    expect -i $cnn_id -re "#"

    set output ""
    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase {Port ID\s+([^\s]+)} $output dummy result
    set result [string trim $result]
            
    Netgear_log_file "lib-lsdp.tcl" "get isdp neighbor port id on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpClearCounter
#
#  Description    : This function is called to clear isdp counters
#
#  Usage          : _ntgrIsdpClearCounter <switch_name>
#
#*******************************************************************************
proc _ntgrIsdpClearCounter {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "clear isdp counters\r"
    exp_sleep 1
    expect -i $cnn_id -re "\(y/n\)" {
            exp_send -i $cnn_id "y"
            exp_sleep 1
    }
    expect -i $cnn_id -re "#"
    
    Netgear_log_file "lib-lsdp.tcl" "clear isdp counters on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpClearTable
#
#  Description    : This function is called to clear isdp table
#
#  Usage          : _ntgrIsdpClearTable <switch_name>
#
#*******************************************************************************
proc _ntgrIsdpClearTable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "clear isdp table\r"
    exp_sleep 1
    expect -i $cnn_id -re "\(y/n\)" {
            exp_send -i $cnn_id "y"
            exp_sleep 1
    }
    expect -i $cnn_id -re "#"
    
    Netgear_log_file "lib-lsdp.tcl" "clear isdp table on switcher"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrIsdpGetPacketStatistic
#
#  Description    : This function is used to get isdp packet statistic.
#
#  Usage          : _ntgrIsdpGetPacketStatistic <switch_name> <inSend> <inReceive>
#
#*******************************************************************************
proc _ntgrIsdpGetPacketStatistic {switch_name inSend inReceive} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    upvar $inSend sendStr
    upvar $inReceive receiveStr
    set sendStr 0
    set receiveStr 0
    
    exp_send -i $cnn_id "show isdp traffic\r"
    exp_sleep 1
    expect -i $cnn_id -re "show isdp traffic"
    exp_sleep 1
    expect -i $cnn_id -re "#"

    set output ""
    set output $expect_out(buffer)
    
    regexp -nocase -line {^ISDP Packets Received\.+\s+(\d+)} $output dummy receiveStr
    regexp -nocase -line {^ISDP Packets Transmitted\.+\s+(\d+)} $output dummy sendStr
        
    Netgear_log_file "lib-lsdp.tcl" "get isdp packet statistic on switcher"
    Netgear_disconnect_switch $switch_name
}






#*******************************************************************************
#
#  Function Name  : _ntgrIsdpEnableOnPort
#
#  Description    : This function is called to enable isdp on port
#
#  Usage          : _ntgrIsdpEnableOnPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrIsdpEnableOnPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "isdp enable\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lsdp.tcl" "enable isdp on port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpDisableOnPort
#
#  Description    : This function is called to disable isdp on port
#
#  Usage          : _ntgrIsdpDisableOnPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrIsdpDisableOnPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no isdp enable\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-lsdp.tcl" "disable isdp on port on switcher"
    Netgear_disconnect_switch $switch_name
}







#*******************************************************************************
#
#  Function Name  : _ntgrIsdpSetTimerErrorCheck
#
#  Description    : This function is called to check whether seting isdp timer is error
#
#  Usage          : _ntgrIsdpSetTimerErrorCheck <switch_name> <timer>
#
#*******************************************************************************
proc _ntgrIsdpSetTimerErrorCheck {switch_name timer} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "isdp timer $timer\r"
    exp_sleep 1
    expect -i $cnn_id -re "isdp timer $timer"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    set output ""
    set output $expect_out(buffer)
    if {[regexp -nocase {out of range} $output]} {
        set result 1
    } else {
        set result 1
    }
    
    Netgear_log_file "lib-lsdp.tcl" "check error of setting isdp timer on switcher"
    Netgear_disconnect_switch $switch_name
    
    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrIsdpSetHoldTimeErrorCheck
#
#  Description    : This function is called to check whether seting isdp hold time is error
#
#  Usage          : _ntgrIsdpSetHoldTimeErrorCheck <switch_name> <holdtime>
#
#*******************************************************************************
proc _ntgrIsdpSetHoldTimeErrorCheck {switch_name holdtime} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "isdp holdtime $holdtime\r"
    exp_sleep 1
    expect -i $cnn_id -re "isdp holdtime $holdtime"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    set output ""
    set output $expect_out(buffer)
    if {[regexp -nocase {out of range} $output]} {
        set result 1
    } else {
        set result 1
    }
    
    Netgear_log_file "lib-lsdp.tcl" "check error of setting isdp hold time on switcher"
    Netgear_disconnect_switch $switch_name
    
    return $result
}







#*******************************************************************************
#
#  Function Name  : _ntgrCheckIsdpEntryByPort
#
#  Description    : This function is used to check lsdp entry exist or not.
#
#  Usage          : _ntgrCheckIsdpEntryByPort <switch_name> <port> <modelname>
#
#*******************************************************************************
proc _ntgrCheckIsdpEntryByPort {switch_name port modelname} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show isdp neighbors $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show isdp neighbors $port"
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
    foreach str $output {
        set str [string trim $str]
        if {[regexp -nocase "$port\.+$modelname" $str]} {
            set result 1
            break
        }
    }
    
    Netgear_log_file "lib-lsdp.tcl" "check lsdp entry based on port on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}
