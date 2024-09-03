#  Function Name:  ntgrMonitoringMemoryLogsClear
#
#  Description  :  Clear memory logs
#
#  Arguments    :
#                  strDevice
#                  strLogType
#
#  Return Value :  NONE
#
proc ntgrMonitoringMemoryLogsClear {strDevice } {
    ntgrCLIExecute $strDevice "clear logging buffered"
    Netgear_log_file "ntgr-webui-monitoring-logs.tcl" "ntgrMonitoringMemoryLogsClear"
}

#  Function Name:  ntgrMonitoringTrapLogsClear
#
#  Description  :  Clear trap logs
#
#  Arguments    :
#                  strDevice
#                  strLogType
#
#  Return Value :  NONE
#
proc ntgrMonitoringTrapLogsClear {strDevice } {
    ntgrCLIExecute $strDevice "clear traplog"
    Netgear_log_file "ntgr-cli-monitoring-logs.tcl" "ntgrMonitoringTrapLogsClear"
}

proc ntgrMonitoringEventLogsClear {strDevice } {
    ntgrCLIExecute $strDevice "clear eventlog"
    Netgear_log_file "ntgr-cli-monitoring-logs.tcl" "ntgrMonitoringEventLogsClear"
}

#  Function Name:  ntgrMonitoringLogsSearch
#
#  Description  :  Search in logs
#
#  Arguments    :
#                  strDevice
#                  strLogType(buffered,persistent,traplogs)
#                  strText
#
#  Return Value :  True/False
#
proc ntgrMonitoringLogsSearch {strDevice strLogType  strText} {
    set strOutput [ntgrCLIExecute $strDevice "show logging $strLogType"]
    set result [regexp -nocase -- "$strText" $strOutput]
    Netgear_log_file "ntgr-cli-monitoring-logs.tcl" "ntgrMonitoringLogsSearch"
    return $result
}

#  Function Name:  ntgrMonitoringConfigGlobalMemoryLog
#
#  Description  :  config syslog in global
#
#  Arguments    :
#                  strDevice   DUT 
#
#  Return Value :  NONE
#  Author       : duke
#++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

proc ntgrMonitoringConfigGlobalMemoryLog {strDevice} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "logging buffered"
    ntgrCLIExecute $strDevice "logging buffered wrap"
    ntgrCLIExecute $strDevice "exit"    
}

#  Function Name:  ntgrMonitoringConfigFlashLog
#
#  Description  :  config flash in global
#
#  Arguments    :
#                  strDevice   DUT 
#
#  Return Value :  NONE
#  Author       : duke
#++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

proc ntgrMonitoringConfigFlashLog {strDevice level} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "logging persistent $level"
    ntgrCLIExecute $strDevice "exit"    
}

#  Function Name:  ntgrMonitoringDisableMemoryLog
#
#  Description  :  config syslog in global
#
#  Arguments    :
#                  strDevice
#                  strLogType(traplogs/persistent/buffered)
#
#  Return Value :  NONE
#  Author       : duke
#++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

proc ntgrMonitoringDisableLog {strDevice strLogType} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no logging $strLogType"
    ntgrCLIExecute $strDevice "exit"    
}
#  Function Name:  ntgrMonitoringGetLogs
#
#  Description  :  get logs
#
#  Arguments    :
#                  strDevice
#                  strLogType(traplogs/persistent/buffered)
#
#  Return Value :  True/False
#
proc ntgrMonitoringGetLogs {strDevice strLogType} {
    set strOutput [ntgrCLIExecute $strDevice "show logging $strLogType"]
    return $strOutput
}

proc ntgrMonitoringGetEventLogs {strDevice} {
    set strOutput [ntgrCLIExecute $strDevice "show eventlog"]
    return $strOutput
}
#*******************************************************************************
#
#  Function Name  : ntgrMonitoringEnableGlobalSyslog
#
#  Description    : This function is called to enable syslog on global
#
#  Usage          : ntgrMonitoringEnableGlobalSyslog <switch_name>
#
#*******************************************************************************
proc ntgrMonitoringEnableGlobalSyslog {switch_name } {
    
	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "logging syslog\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "ntgr-cli-monitoring-logs.tcl" "Function: ntgrMonitoringEnableGlobalSyslog . Enable syslog admin status on switcher."
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : ntgrMonitoringSyslogSourceinterface
#
#  Description    : This function is called to set source-interface
#
#  Usage          : ntgrMonitoringSyslogSourceinterface <switch_name>
#
#*******************************************************************************
proc ntgrMonitoringSyslogSourceinterface {switch_name port} {
    
	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "logging syslog source-interface $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "ntgr-cli-monitoring-logs.tcl" "Function: ntgrMonitoringSyslogSourceinterface .set source-interface $port"
    Netgear_disconnect_switch $switch_name
}
#*******************************************************************************
#
#  Function Name  : ntgrGetSyslogSourceinterface
#
#  Description    : This function is used to get Syslog Sourceinterface
#
#  Usage          : ntgrGetSyslogSourceinterface <switch_name>
#
#  return         :  management
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc ntgrGetSyslogSourceinterface  {switch_name} {
    set output {}
	set ip [_get_switch_ip_addr $switch_name]
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show logging | include Interface\r"
    exp_sleep 1
    expect -i $cnn_id -re "show logging | include Interface"
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
    set ifTunel [lindex $output end-3]
	if {$ifTunel=="tunnel"} {
		set port  [lindex $output end-3] 
		lappend port [lindex $output end-2]
	} else {
	set port [lindex $output end-2]
	}
	Netgear_log_file "ntgr-cli-monitoring-logs.tcl" "Function: ntgrGetSyslogSourceinterface .get source-interface = $port"
	return $port
}
#*******************************************************************************
#
#  Function Name  : ntgrMonitoringDisableGlobalSyslog
#
#  Description    : This function is called to disable syslog on global
#
#  Usage          : ntgrMonitoringDisableGlobalSyslog <switch_name>
#
#*******************************************************************************
proc ntgrMonitoringDisableGlobalSyslog {switch_name } {
    
	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no logging syslog\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "ntgr-cli-monitoring-logs.tcl" "Function: ntgrMonitoringDisableGlobalSyslog . Disable syslog admin status on switcher."
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : ntgrMonitoringSyslogChangePort
#
#  Description    : This function is called to change syslog port
#
#  Usage          : ntgrMonitoringSyslogChangePort <switch_name> <port>
#
#*******************************************************************************
proc ntgrMonitoringSyslogChangePort {switch_name port} {
    
	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "logging syslog port $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "ntgr-cli-monitoring-logs.tcl" "Function: ntgrMonitoringSyslogChangePort . change syslog port:$port on switcher."
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : ntgrMonitoringAddSyslogServer
#
#  Description    : This function is called to add syslog server
#
#  Usage          : ntgrMonitoringAddSyslogServer <switch_name> <ip_type> <ip_address> <port> <severity_level>
#
#*******************************************************************************
proc ntgrMonitoringAddSyslogServer {switch_name ip_type ip_address port severity_level} {
    
	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "logging host $ip_address $ip_type $port $severity_level \r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "ntgr-cli-monitoring-logs.tcl" "Function: ntgrMonitoringAddSyslogServer . Add syslog Server on switcher."
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : ntgrMonitoringRemoveSyslogServer
#
#  Description    : This function is called to remove syslog server
#
#  Usage          : ntgrMonitoringRemoveSyslogServer <switch_name>
#
#*******************************************************************************
proc ntgrMonitoringRemoveSyslogServer {switch_name} {
    
	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "logging host remove 1 \r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "ntgr-cli-monitoring-logs.tcl" "Function: ntgrMonitoringRemoveSyslogServer . Remove syslog Server on switcher."
    Netgear_disconnect_switch $switch_name
}
#*******************************************************************************
#
#  Function Name  : ntgrMonitoringEnableCommandlog
#
#  Description    : This function is called to enable command log on global
#
#  Usage          : ntgrMonitoringEnableCommandlog <switch_name>
#
#*******************************************************************************
proc ntgrMonitoringEnableCommandlog {switch_name } {
    
	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "logging cli-command\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "ntgr-cli-monitoring-logs.tcl" "Function: ntgrMonitoringEnableCommandlog . Enable command log on switcher."
    Netgear_disconnect_switch $switch_name
}
#*******************************************************************************
#
#  Function Name  : ntgrMonitoringDisableCommandlog
#
#  Description    : This function is called to Disable command log on global
#
#  Usage          : ntgrMonitoringDisableCommandlog <switch_name>
#
#*******************************************************************************
proc ntgrMonitoringDisableCommandlog {switch_name } {
    
	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no logging cli-command\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "ntgr-cli-monitoring-logs.tcl" "Function: ntgrMonitoringDisableCommandlog . Disable command log on switcher."
    Netgear_disconnect_switch $switch_name
}
proc ntgrSwitchingPSKill {processname} {
    if {[catch {exec taskkill /IM $processname} ret]} {
        puts "could not kill $processname,return msg is $ret"
        return 0
    }
    return 1
}

proc ntgrDeleteFile {filename} {
    if {[file exist $filename]} {
        if {[catch {file delete $filename} ret]} {
            puts "could not delete $filename,return msg is $ret"
            return 0
        }
        return 1        
    }
    return 0
}

proc ntgrReadFile {filename} {
    if {[file exist $filename]} {
        set fd [open $filename]
        set msg [read $fd]
        close $fd
        return $msg
    }
    return ""
}

proc openProgram {path} {
    if {[catch {exec $path &} ret]} {
        puts "could not open $path,return msg is $ret"
        return 0
    }
    return 1
}