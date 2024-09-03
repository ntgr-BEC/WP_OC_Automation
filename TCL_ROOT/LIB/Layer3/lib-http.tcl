####################################################################################
#  File Name   : lib-http.tcl
#
#  Description :
#        This file includes functions used for igmp snooping configuration.
#
#  History     :
#        Date          Programmer         Description
#        03/09/2011    Tony Jing          Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrHttpJavaModeEnable
#
#  Description    : This function is called to enable http java mode on specified
#                   switch.
#
#  Usage          : _ntgrHttpJavaModeEnable <switch_name>
#
#*******************************************************************************
proc _ntgrHttpJavaModeEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "ip http java\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-http.tcl" "enable http java mode on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpJavaModeDisable
#
#  Description    : This function is called to disable http java mode on specified
#                   switch.
#
#  Usage          : _ntgrHttpJavaModeDisable <switch_name>
#
#*******************************************************************************
proc _ntgrHttpJavaModeDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "no ip http java\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-http.tcl" "disable http java mode on switcher"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrHttpSetSoftTimeout
#
#  Description    : This function is called to set http soft timeout on specified
#                   switch.
#
#  Usage          : _ntgrHttpSetSoftTimeout <switch_name> <minute>
#
#*******************************************************************************
proc _ntgrHttpSetSoftTimeout {switch_name minute} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "ip http session soft-timeout $minute\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-http.tcl" "set http soft timeout on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpSetHardTimeout
#
#  Description    : This function is called to set http hard timeout on specified
#                   switch.
#
#  Usage          : _ntgrHttpSetHardTimeout <switch_name> <hour>
#
#*******************************************************************************
proc _ntgrHttpSetHardTimeout {switch_name hour} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "ip http session hard-timeout $hour\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-http.tcl" "set http hard timeout on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpSetMaxSession
#
#  Description    : This function is called to set http maxsession on specified
#                   switch.
#
#  Usage          : _ntgrHttpSetMaxSession <switch_name> <number>
#
#*******************************************************************************
proc _ntgrHttpSetMaxSession {switch_name number} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "ip http session maxsessions $number\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-http.tcl" "set http maxsession on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpGetMaxSession
#
#  Description    : This function is called to get http maxsession on specified
#                   switch.
#
#  Usage          : _ntgrHttpGetMaxSession <switch_name>
#
#*******************************************************************************
proc _ntgrHttpGetMaxSession {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ip http\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip http"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    set output $expect_out(buffer)
    set maxsession ""
    regexp -nocase -line {Maximum Allowable HTTP Sessions\s*\.+\s+(\d+)} $output dummy maxsession
    
    Netgear_log_file "lib-http.tcl" "get http maxsession on switcher:  $maxsession"
    Netgear_disconnect_switch $switch_name
    
    return $maxsession
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpGetHardTimeout
#
#  Description    : This function is called to get http hard timeout on specified
#                   switch.
#
#  Usage          : _ntgrHttpGetHardTimeout <switch_name>
#
#*******************************************************************************
proc _ntgrHttpGetHardTimeout {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ip http\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip http"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    set output $expect_out(buffer)
    set timeout ""
    regexp -nocase -line {HTTP Session Hard Timeout\s*\.+\s+(\d+)\s+hours} $output dummy timeout
    
    Netgear_log_file "lib-http.tcl" "get http hard timeout on switcher:  $timeout"
    Netgear_disconnect_switch $switch_name
    
    return $timeout
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpGetSoftTimeout
#
#  Description    : This function is called to get http soft timeout on specified
#                   switch.
#
#  Usage          : _ntgrHttpGetSoftTimeout <switch_name>
#
#*******************************************************************************
proc _ntgrHttpGetSoftTimeout {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ip http\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip http"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    set output $expect_out(buffer)
    set timeout ""
    regexp -nocase -line {HTTP Session Soft Timeout\s*\.+\s+(\d+)\s+minutes} $output dummy timeout
    
    Netgear_log_file "lib-http.tcl" "get http soft timeout on switcher:  $timeout"
    Netgear_disconnect_switch $switch_name
    
    return $timeout
}








#*******************************************************************************
#
#  Function Name  : _ntgrHttpsEnable
#
#  Description    : This function is called to enable https on specified
#                   switch.
#
#  Usage          : _ntgrHttpsEnable <switch_name>
#
#*******************************************************************************
proc _ntgrHttpsEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    
    exp_send -i $cnn_id "crypto certificate generate\r"
    exp_sleep 10
    
    exp_send -i $cnn_id "exit\r"
    
    exp_send -i $cnn_id "ip http secure-server\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-http.tcl" "enable https on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpsDisable
#
#  Description    : This function is called to disable https on specified
#                   switch.
#
#  Usage          : _ntgrHttpsDisable <switch_name>
#
#*******************************************************************************
proc _ntgrHttpsDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "no ip http secure-server\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-http.tcl" "disable https on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpsSetProtocolLevel
#
#  Description    : This function is called to set https protocol level on specified
#                   switch.
#
#  Usage          : _ntgrHttpsSetProtocolLevel <switch_name> <plevel>
#
#*******************************************************************************
proc _ntgrHttpsSetProtocolLevel {switch_name plevel} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "ip http secure-protocol $plevel\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-http.tcl" "set https protocol level on switcher"
    Netgear_disconnect_switch $switch_name
}




#*******************************************************************************
#
#  Function Name  : _ntgrHttpsSetSoftTimeout
#
#  Description    : This function is called to set https soft timeout on specified
#                   switch.
#
#  Usage          : _ntgrHttpsSetSoftTimeout <switch_name> <minute>
#
#*******************************************************************************
proc _ntgrHttpsSetSoftTimeout {switch_name minute} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "ip http secure-session soft-timeout $minute\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-http.tcl" "set https soft timeout on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpsSetHardTimeout
#
#  Description    : This function is called to set https hard timeout on specified
#                   switch.
#
#  Usage          : _ntgrHttpsSetHardTimeout <switch_name> <hour>
#
#*******************************************************************************
proc _ntgrHttpsSetHardTimeout {switch_name hour} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "ip http secure-session hard-timeout $hour\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-http.tcl" "set https hard timeout on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpsSetMaxSession
#
#  Description    : This function is called to set https maxsession on specified
#                   switch.
#
#  Usage          : _ntgrHttpsSetMaxSession <switch_name> <number>
#
#*******************************************************************************
proc _ntgrHttpsSetMaxSession {switch_name number} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "ip http secure-session maxsessions $number\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-http.tcl" "set https maxsession on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpsGetMaxSession
#
#  Description    : This function is called to get https maxsession on specified
#                   switch.
#
#  Usage          : _ntgrHttpsGetMaxSession <switch_name>
#
#*******************************************************************************
proc _ntgrHttpsGetMaxSession {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ip http\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip http"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    set output $expect_out(buffer)
    set maxsession ""
    regexp -nocase -line {Maximum Allowable HTTPS Sessions\s*\.+\s+(\d+)} $output dummy maxsession
    
    Netgear_log_file "lib-http.tcl" "get https maxsession on switcher:  $maxsession"
    Netgear_disconnect_switch $switch_name
    
    return $maxsession
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpsGetHardTimeout
#
#  Description    : This function is called to get https hard timeout on specified
#                   switch.
#
#  Usage          : _ntgrHttpsGetHardTimeout <switch_name>
#
#*******************************************************************************
proc _ntgrHttpsGetHardTimeout {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ip http\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip http"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    set output $expect_out(buffer)
    set timeout ""
    regexp -nocase -line {HTTPS Session Hard Timeout\s*\.+\s+(\d+)\s+hours} $output dummy timeout
    
    Netgear_log_file "lib-http.tcl" "get https hard timeout on switcher:  $timeout"
    Netgear_disconnect_switch $switch_name
    
    return $timeout
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpsGetSoftTimeout
#
#  Description    : This function is called to get https soft timeout on specified
#                   switch.
#
#  Usage          : _ntgrHttpsGetSoftTimeout <switch_name>
#
#*******************************************************************************
proc _ntgrHttpsGetSoftTimeout {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ip http\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip http"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    set output $expect_out(buffer)
    set timeout ""
    regexp -nocase -line {HTTPS Session Soft Timeout\s*\.+\s+(\d+)\s+minutes} $output dummy timeout
    
    Netgear_log_file "lib-http.tcl" "get https soft timeout on switcher:  $timeout"
    Netgear_disconnect_switch $switch_name
    
    return $timeout
}



#*******************************************************************************
#
#  Function Name  : _ntgrHttpsSetPort
#
#  Description    : This function is called to set https port on specified
#                   switch.
#
#  Usage          : _ntgrHttpsSetPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrHttpsSetPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    if {$port == 443} {
        exp_send -i $cnn_id "no ip http secure-port\r"
    } else {
        exp_send -i $cnn_id "ip http secure-port $port\r"
    }
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-http.tcl" "set https port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpsGetPort
#
#  Description    : This function is called to get https port on specified
#                   switch.
#
#  Usage          : _ntgrHttpsGetPort <switch_name>
#
#*******************************************************************************
proc _ntgrHttpsGetPort {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ip http\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip http"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    set output $expect_out(buffer)
    set port ""
    regexp -nocase -line {Secure Port\s*\.+\s+(\d+)} $output dummy port
    
    Netgear_log_file "lib-http.tcl" "get https port on switcher:  $port"
    Netgear_disconnect_switch $switch_name
    
    return $port
}





#*******************************************************************************
#
#  Function Name  : _ntgrHttpsGenerateCertificate
#
#  Description    : This function is called to generate https certificate on specified
#                   switch.
#
#  Usage          : _ntgrHttpsGenerateCertificate <switch_name>
#
#*******************************************************************************
proc _ntgrHttpsGenerateCertificate {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "crypto certificate generate\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
        
    Netgear_log_file "lib-http.tcl" "generate https certificate on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpsDeleteCertificate
#
#  Description    : This function is called to delete https certificate on specified
#                   switch.
#
#  Usage          : _ntgrHttpsDeleteCertificate <switch_name>
#
#*******************************************************************************
proc _ntgrHttpsDeleteCertificate {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no crypto certificate generate\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
        
    Netgear_log_file "lib-http.tcl" "delete https certificate on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrHttpsGetCertificateStatus
#
#  Description    : This function is called to get https certificate status on specified
#                   switch.
#
#  Usage          : _ntgrHttpsGetCertificateStatus <switch_name>
#
#*******************************************************************************
proc _ntgrHttpsGetCertificateStatus {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ip http\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip http"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    set output $expect_out(buffer)
    set status ""
    regexp -nocase -line {Certificate Present\s*\.+\s+([^\r\n]+)} $output dummy status
    set status [string trim $status]
    
    Netgear_log_file "lib-http.tcl" "get https certificate status on switcher:  $status"
    Netgear_disconnect_switch $switch_name
    
    return $status
}