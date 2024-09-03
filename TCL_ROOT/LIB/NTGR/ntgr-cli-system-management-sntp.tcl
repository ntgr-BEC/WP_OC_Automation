proc ntgrSystemManagementTimeIsLocalClockEnabled {strDev} {
    set str {}
    set str1 {}
    set cmd "show running-config"
    set strOutput [ntgrCLIExecute $strDev $cmd]
    set pattern "no sntp client mode"
    set outputlist [split $strOutput "\n"]
    foreach output $outputlist {  
        regexp -nocase $pattern $output str str1
        if {$str != {}} {break}
    }
    if {$str != {}} {
       return 1
    } else {
       return 0
    }  
}

proc ntgrSystemManagementTimeEnableLocalClock {strDev} { 
  ntgrCLIExecute $strDev "configure"
  set cmd "no sntp client mode"
  ntgrCLIExecute $strDev $cmd
  ntgrCLIExecute $strDev "exit"
  
}

proc ntgrSystemManagementGetSystemDateTime {strDev sysDateTime initDateTime sysUpTime} {
    upvar $sysDateTime  sysDate
    upvar $initDateTime initDate
    upvar $sysUpTime    sysUp
    set str {}; set days {}; set hours {}; set mins {}; set secs {}
    set cmd "show running-config"
    set strOutput [ntgrCLIExecute $strDev $cmd] 
    set pattern "!System Up Time.*(\[0-9\]+)\ days.?(\[0-9\]+).?hrs.?(\[0-9\]+).?mins.?(\[0-9\]+).?secs.*"
    set outputlist [split $strOutput "\n"]  
    foreach output $outputlist { 
        regexp -nocase $pattern $output str days hours mins secs
        if {$str != {}} {break}  
    }
    Netgear_log_file "ntgrSystemManagementGetSystemDateTime" "System up $days days $hours hours $mins mins $secs secs"
    if {$days != 0} { set days [string trimleft $days "0"] }
    if {$hours != 0} { set hours [string trimleft $hours "0"] }
    if {$mins != 0} { set mins [string trimleft $mins "0"] }
    if {$secs != 0} { set secs [string trimleft $secs "0"] }  
    set sysUp [expr $days*86400 + $hours*3600 + $mins*60 + $secs]
    set str {}; set strMonth {}; set strDay {}; set strMinute {}; set strSecond {} ;set strYear {}
    set cmd "show version"
    set strOutput [ntgrCLIExecute $strDev $cmd]
    set pattern "Current Time\.* (\[A-Za-z\]+)\[ \]+(\[0-9\]\[0-9\]?)\[ \]+(\[0-9\]+):(\[0-9\]+):(\[0-9\]+)\[ \]+(\[0-9\]+).*"
    set outputlist [split $strOutput "\n"]  
    foreach output $outputlist { 
        regexp -nocase $pattern $output str strMonth strDay strHour strMinute strSecond strYear
        if {$str != {}} {break}  
    }
    set sysDate "$strMonth $strDay $strYear $strHour:$strMinute:$strSecond"
    Netgear_log_file "ntgrSystemManagementGetSystemDateTime" "Current time $strDay days $strYear year $strHour hours $strMinute mins $strSecond secs"
    set initDate $strYear
}

proc ntgrSystemManagementTimeDateDiff {unit start end} {
  if {$unit == "s"} {
      set str {}; set hours {}; set mins {}; set secs {}
      set pattern  {([0-9]+):([0-9]+):([0-9]+)}
      regexp -nocase $pattern $start str hours mins secs
      if {$hours != 0} { set hours [string trimleft $hours "0"] }
      if {$mins != 0} { set mins [string trimleft $mins "0"] }
      if {$secs != 0} { set secs [string trimleft $secs "0"] }   
      set startTime [expr $hours*3600+ $mins*60 + $secs]
      regexp -nocase $pattern $end str hours mins secs
      if {$hours != 0} { set hours [string trimleft $hours "0"] }
      if {$mins != 0} { set mins [string trimleft $mins "0"] }
      if {$secs != 0} { set secs [string trimleft $secs "0"] }     
      set endTime [expr $hours*3600+ $mins*60 + $secs]
      return [expr $endTime - $startTime]
  } 
}

proc ntgrSystemManagementTimeAddSNTPServer {strDev serv_info} {
  set prio 0 ; set port 0; set type 0; set vers 0;
  keylget serv_info TYPE      type
  keylget serv_info ADDR      addr
  keylget serv_info PORT      port
  keylget serv_info PRIORITY  prio
  keylget serv_info VERSION   vers
  ntgrCLIExecute $strDev "configure"
  set cnn_id [_get_switch_handle $strDev]
  
  exp_send -i $cnn_id "sntp server $addr $prio $vers $port\r"
  expect -i $cnn_id "Config"
  set ret  $expect_out(buffer) 
  ntgrCLIExecute $strDev "exit"
  return $ret
}


proc ntgrSystemManagementTimeEnableSNTP {strDev {mode unicast}} {
  ntgrCLIExecute $strDev "configure"
  set cmd "sntp client mode $mode"
  ntgrCLIExecute $strDev $cmd
  ntgrCLIExecute $strDev "exit"
}

proc ntgrSystemManagementTimeEnableSNTPUnicast {strDev mode} {
  ntgrCLIExecute $strDev "configure"
  set cmd "sntp unicast client $mode"
  ntgrCLIExecute $strDev $cmd
  ntgrCLIExecute $strDev "exit"
}


proc ntgrSystemManagementTimeEnableSNTPBroadcast {strDev mode} {
  ntgrCLIExecute $strDev "configure"
  set cmd "sntp broadcast client $mode"
  ntgrCLIExecute $strDev $cmd
  ntgrCLIExecute $strDev "exit"
}

proc ntgrSystemManagementTimeDisableSNTPUnicast {strDev mode} {
  ntgrCLIExecute $strDev "configure"
  set cmd "no sntp unicast client $mode"
  ntgrCLIExecute $strDev $cmd
  ntgrCLIExecute $strDev "exit"
}

proc ntgrSystemManagementTimeSetPort {strDev strPort} {
  ntgrCLIExecute $strDev "configure"
  set cmd "sntp client port $strPort"
  ntgrCLIExecute $strDev $cmd
  ntgrCLIExecute $strDev "exit"
}

proc ntgrSystemManagementTimeResetPort {strDev} {
  ntgrCLIExecute $strDev "configure"
  set cmd "no sntp client port"
  ntgrCLIExecute $strDev $cmd
  ntgrCLIExecute $strDev "exit"
}

proc ntgrSystemManagementTimeSetTimezone {strDev timezone {zone {}}} {
     set hours {} ; set mins {};
     set pattern  {UTC([+-][0-9]+):([0-9]+)}
     regexp -nocase $pattern $timezone str hours mins
     if { $zone== {} } {
        if {$mins == 0} { set cmd "clock timezone $hours" } else {set cmd "clock timezone $hours minutes $mins"}
     } else {
        if {$mins == 0} { set cmd "clock timezone $hours zone $zone" } else { \
            set cmd "clock timezone $hours minutes $mins zone $zone" }
     }
     ntgrCLIExecute $strDev "configure"
     ntgrCLIExecute $strDev $cmd
     ntgrCLIExecute $strDev "exit"
}

proc ntgrSystemManagementTimeGetTimezone {strDev} {
    set cmd "show running-config"
    set str {} ; set hours {} ; set mins {}; set direct {};
    set strOutput [ntgrCLIExecute $strDev $cmd] 
    set pattern {clock timezone (-?)([0-9]+) minutes ([0-9]+)} 
     set outputlist [split $strOutput "\n"]  
    foreach output $outputlist { 
        regexp -nocase $pattern $output str direct hours mins
        if {$str != {}} {break}  
    }
    set len [string length $hours]
    if {$len == 1} {set hours "0$hours"}
    set len [string length $mins]
    if {$len == 1} {set mins "0$mins"}
    if {$direct == {}} {set direct "+"}
    return "UTC$direct$hours:$mins"
}

proc ntgrSystemManagementTimeResetTimezone {strDev} {
    set cmd "no clock timezone"
    ntgrCLIExecute $strDev "configure"
    ntgrCLIExecute $strDev $cmd
    ntgrCLIExecute $strDev "exit"
}

proc ntgrSystemManagementTimeDeleteSNTPServer {strDev serv_info} {
    keylget serv_info ADDR  addr
    set cmd "no sntp server $addr"
    ntgrCLIExecute $strDev "configure"
    ntgrCLIExecute $strDev $cmd
    ntgrCLIExecute $strDev "exit"
}

proc TESTPC_NTP_CheckNTPServerStatus {} {
    set expect_out(buffer)  {}
    global TESTBED_ServerControlIP
    set strCmd "C:/TOOLS/cURL/curl.exe"
    spawn $strCmd "http://$TESTBED_ServerControlIP/control/check-ntp-server-status.php"
    set cnn_id $spawn_id
    expect -i $cnn_id "sec"
    set strOutput $expect_out(buffer) 
    Netgear_log_file "TESTPC_NTP_CheckNTPServerStatus:" $strOutput
    set ret [llength $strOutput]
    return $ret
}

proc TESTPC_NTP_EnableNTPServer {strPort} {
    global TESTBED_ServerControlIP
    set strCmd "C:/TOOLS/cURL/curl.exe"
    spawn $strCmd "http://$TESTBED_ServerControlIP/control/enable-ntp-server.php?NTP_PORT=$strPort"
    set cnn_id $spawn_id
    expect -i $cnn_id $strPort
    set strOutput $expect_out(buffer) 
    Netgear_log_file "TESTPC_NTP_EnableNTPServer:" $strOutput
}


proc TESTPC_NTP_StopNTPServer {strAddr strPort} {
    global TESTBED_ServerControlIP
    set strCmd "C:/TOOLS/cURL/curl.exe"
    spawn $strCmd "http://$TESTBED_ServerControlIP/control/stop-ntp-server.php?NTP_IP=$strAddr&NTP_PORT=$strPort"
    set cnn_id $spawn_id
    expect -i $cnn_id $strAddr
    set strOutput $expect_out(buffer) 
    Netgear_log_file "TESTPC_NTP_StopNTPServer:" $strOutput
}

proc TESTPC_NTP_StartNTPServer {strAddr strPort} {
    global TESTBED_ServerControlIP
    set strCmd "C:/TOOLS/cURL/curl.exe"
    spawn $strCmd "http://$TESTBED_ServerControlIP/control/start-ntp-server.php?NTP_IP=$strAddr&NTP_PORT=$strPort"
    set cnn_id $spawn_id
    expect -i $cnn_id $strAddr
    set strOutput $expect_out(buffer) 
    Netgear_log_file "TESTPC_NTP_StartNTPServer:" " $strAddr, Port $strPort"
}

proc TESTPC_NTP_RestartNTPServer {} {
    global TESTBED_ServerControlIP
    set strCmd "C:/TOOLS/cURL/curl.exe"
    spawn $strCmd "http://$TESTBED_ServerControlIP/control/restart-ntp-server.php" 
    Netgear_log_file "TESTPC_NTP_RestartNTPServer:" "Restart"
}

proc TESTPC_NTP_AddBroadcast {ipaddr} {
    global TESTBED_ServerControlIP
    set strCmd "C:/TOOLS/cURL/curl.exe"
    spawn $strCmd "http://$TESTBED_ServerControlIP/control/add-ntp-broadcast.php?NTP_IP=$ipaddr" 
    Netgear_log_file "TESTPC_NTP_AddBroadcast:" "add broadcast in ntp.conf"
}

proc TESTPC_NTP_DelBroadcast {ipaddr} {
    global TESTBED_ServerControlIP
    set strCmd "C:/TOOLS/cURL/curl.exe"
    spawn $strCmd "http://$TESTBED_ServerControlIP/control/del-ntp-broadcast.php?NTP_IP=$ipaddr" 
    Netgear_log_file "TESTPC_NTP_DelBroadcast:" "del broadcast in ntp.conf"
}

proc TESTPC_NTP_ClearDebugLog {} {
    global TESTBED_ServerControlIP
    set strCmd "C:/TOOLS/cURL/curl.exe"
    spawn $strCmd "http://$TESTBED_ServerControlIP/control/clear-debug-log.php"
    set cnn_id $spawn_id
    expect -i $cnn_id "OUTPUT"
    set strOutput $expect_out(buffer) 
    Netgear_log_file "TTESTPC_NTP_ClearDebugLog:" $strOutput
}

proc TESTPC_NTP_DisableNTPServer {strPort} {
    global TESTBED_ServerControlIP
    set strCmd "C:/TOOLS/cURL/curl.exe"
    spawn $strCmd "http://$TESTBED_ServerControlIP/control/disable-ntp-server.php?NTP_PORT=$strPort"
    Netgear_log_file "TESTPC_NTP_DisableNTPServer:" "done"
}

proc TESTPC_NTP_GetDebugLog {strDebugLog} {
    upvar $strDebugLog strOutput
    global TESTBED_ServerControlIP
    set strCmd "C:/TOOLS/cURL/curl.exe"
    spawn $strCmd "http://$TESTBED_ServerControlIP/control/get-debug-log.php"
    set cnn_id $spawn_id
    expect -i $cnn_id "LEN=56"
    set strOutput $expect_out(buffer) 
    Netgear_log_file "TTESTPC_NTP_GetDebugLog:" $strOutput
}

proc ntgrSystemManagementTimeGetSNTPServerStatus {strDev serv_info} {
  
    set str {}; set month {}; set day {}; set time {}; set year {} ;set strDate {};
    set cmd "show running-config"
    set strOutput [ntgrCLIExecute $strDev $cmd] 
    set pattern {!Current SNTP Synchronized Time: ([A-Za-z]+) *([0-9]+) ([0-9]+:[0-9]+:[0-9]+) ([0-9]+) UTC.*}
    set outputlist [split $strOutput "\n"]  
    foreach output $outputlist { 
        regexp -nocase $pattern $output str month day time year
        if {$str != {}} {
           set strDate "$month $day $year $time"
        break}  
    }
    Netgear_log_file "ntgrSystemManagementTimeGetSNTPServerStatus" "Current SNTP Synchronized Time: $month $day $time $year"
    set cmd "show version"
    set strOutput [ntgrCLIExecute $strDev $cmd]
    
    set pattern {Current SNTP Sync Status\.* (.*)}
    set outputlist [split $strOutput "\n"]  
    set strStatus {} ; set str {}
    foreach output $outputlist { 
        regexp -nocase $pattern $output str strStatus
        if {$str != {}} {break}  
    }
    Netgear_log_file "ntgrSystemManagementTimeGetSNTPServerStatus" "Current SNTP Sync Status: $strStatus"
    if {$strStatus != "Success"} {
       set strDate {}
    } 
    
    return $strDate
}
 
proc ntgrCompareSyncAndCurrentTime {strDev syncTime curTime} {
  set retval 1
   if {[lindex $syncTime 0] != [lindex $curTime 0]} {set retval 0 ; \
      Netgear_log_file "ntgrCompareSyncAndCurrentTime" "error : different in month" }
   if {[lindex $syncTime 1] != [lindex $curTime 1]} {set retval 0 ; \
      Netgear_log_file "ntgrCompareSyncAndCurrentTime" "error : different in day" }
   set hours {}; set mins {}; set secs {}
   set pattern  {([0-9]+):([0-9]+):([0-9]+)}
    regexp -nocase $pattern [lindex $syncTime 3] str hours mins secs
    if {$hours != 0} { set hours [string trimleft $hours "0"] }
    if {$mins != 0} { set mins [string trimleft $mins "0"] }
    if {$secs != 0} { set secs [string trimleft $secs "0"] }   
    set syncSec [expr $hours*3600+ $mins*60 + $secs]
   regexp -nocase $pattern [lindex $curTime 3] str hours mins secs
   if {$hours != 0} { set hours [string trimleft $hours "0"] }
   if {$mins != 0} { set mins [string trimleft $mins "0"] }
   if {$secs != 0} { set secs [string trimleft $secs "0"] }   
   set curSec [expr $hours*3600+ $mins*60 + $secs]
   set minus [expr $curSec-$syncSec]
   if {$minus > 20} {set retval 0 ; \
      Netgear_log_file "ntgrCompareSyncAndCurrentTime" "error : different in $minus seconds " }
   return $retval
}

proc ntgrSystemManagementTimeWorkRound {strDev} {
  ntgrMaintenanceDeviceRebootwithoutSave $strDev
  sleep 120
}

proc ntgrSystemManagementCheckServer {strDev strLog serv_addr} {
   set retval 0;
   set str {} ; set addr {};
   set pattern {^.*NTP.*DST=([0-9]+.[0-9]+.[0-9]+.[0-9]+).*}

   set outputlist [split $strLog "\n"]  
    foreach output $outputlist { 
        regexp -nocase $pattern $output str addr
        if {$str != {}} { break }  
    }
   Netgear_log_file "ntgrSystemManagementCheckServer" "ip $addr in server should match $serv_addr" ;
   if {$serv_addr == $addr} {
     set retval 1 ;
     Netgear_log_file "ntgrSystemManagementCheckServer" "There is $addr in server log match for expect $serv_addr" ;
   }
   return $retval
}

proc ntgrSystemManagementCheckSrcPort {strDev strLog port} {
   set retval 0;
   set str {} ; set srcport {};
   set pattern {^.*NTP.*SPT=([0-9]+).*}

   set outputlist [split $strLog "\n"]  
    foreach output $outputlist { 
        regexp -nocase $pattern $output str srcport
        if {$str != {}} { break }  
    }
   Netgear_log_file "ntgrSystemManagementCheckSrcPort" "swtch sntp port $srcport in server should match $port" ;
   if {$srcport == $port} {
     set retval 1 ;
     Netgear_log_file "ntgrSystemManagementCheckSrcPort" "There is $srcport in server log match for expect $port" ;
   }
   return $retval
}

proc ntgrSystemManagementTimeGetSNTPGlobalStatus {strDev} {
    set cmd "show running-config"
    set str {}; set rule {}
    set strOutput [ntgrCLIExecute $strDev $cmd] 
    set pattern {sntp server (.*)} 
     set outputlist [split $strOutput "\n"]  
    foreach output $outputlist { 
        regexp -nocase $pattern $output str rule
        if {$str != {}} {break}  
    }
    return $rule
}

proc ntgrSystemManagementTimeSetSummerTime {strDev serv_info} {
  set beginweek {}; set beginday {}; set beginmonth {}; set beginhours {}; set beginmins {};
  set endweek {}; set endday {}; set endmonth {}; set endhours {}; set endmins {};
  set offset {}; set zone {};
  keylget serv_info summertime     summertime
  if {$summertime != "Disable" } {
      keylget serv_info beginweek      beginweek
      keylget serv_info beginday       beginday
      keylget serv_info beginmonth     beginmonth
      keylget serv_info beginhours     beginhours
      keylget serv_info beginmins      beginmins
      keylget serv_info endweek        endweek
      keylget serv_info endday         endday
      keylget serv_info endmonth       endmonth
      keylget serv_info endhours       endhours
      keylget serv_info endmins        endmins
      keylget serv_info offset         offset
      keylget serv_info zone           zone
      
      
  }
  if {$summertime == "NonRecurring" } {
      keylget serv_info nonbeginmonth    nonbeginmonth
      keylget serv_info nonbegindate     nonbegindate 
      keylget serv_info nonbeginyear     nonbeginyear
      keylget serv_info nonbeginhour     nonbeginhour
      keylget serv_info nonbeginmins     nonbeginmins
      keylget serv_info nonendmonth      nonendmonth
      keylget serv_info nonenddate       nonenddate 
      keylget serv_info nonendyear       nonendyear
      keylget serv_info nonendhour       nonendhour
      keylget serv_info nonendmins       nonendmins
      keylget serv_info nonoffset        offset
      keylget serv_info nonzone          zone   
  }

  set extracmd {}

  if { $offset !=0 && $offset != {} } {set extracmd "offset $offset"}
  if { $zone != 0 && $zone != {}} {set extracmd "$extracmd zone $zone"}
      
  set summercmd "clock summer-time"
  ntgrCLIExecute $strDev "configure"
  set cnn_id [_get_switch_handle $strDev]
  switch -exact $summertime {
     "Disable"       { set cmd "no clock summer-time" }
     "Recurring"     { set cmd "$summercmd recurring $beginweek $beginday $beginmonth $beginhours:$beginmins \
                       $endweek $endday $endmonth $endhours:$endmins $extracmd" }
     "Recurring EU"  { set cmd "$summercmd recurring EU $extracmd" }
     "Recurring USA" { set cmd "$summercmd recurring USA $extracmd" }
     "NonRecurring"  { set cmd "$summercmd date $nonbegindate $nonbeginmonth $nonbeginyear $nonbeginhour:$nonbeginmins \
                                                $nonenddate $nonendmonth $nonendyear $nonendhour:$nonendmins $extracmd" }
  }
  exp_send -i $cnn_id "$cmd\r"
  expect -i $cnn_id "Config"
  set ret  $expect_out(buffer) 
  ntgrCLIExecute $strDev "exit"
  return $ret
}

proc ntgrSystemManagementTimeGetSummerTimeStatus {strDev} {

  set cmd "show running-config"
  set strSummer {}; set str {}
  set strOutput [ntgrCLIExecute $strDev $cmd] 
  set pattern {clock summer-time (.*)} 
   set outputlist [split $strOutput "\n"]  
  foreach output $outputlist { 
      regexp -nocase $pattern $output str strSummer
      if {$str != {}} {break}  
  }
  return $strSummer
}

proc ntgrSystemManagementTimeSetLocalClock {strDev dicClock} {
  keylget dicClock localdate  localdate
  keylget dicClock localtime  localtime  
  
  ntgrCLIExecute $strDev "configure"
  set cmd "no sntp client port"
  ntgrCLIExecute $strDev $cmd
  ntgrCLIExecute $strDev "exit"
  
  if {$localdate !=0 } {
      set cmd "clock set $localdate"
      ntgrCLIExecute $strDev "configure"
      ntgrCLIExecute $strDev $cmd
      ntgrCLIExecute $strDev "exit"
  }
  if {$localtime !=0 } {
      set cmd "clock set $localtime"
      ntgrCLIExecute $strDev "configure"
      ntgrCLIExecute $strDev $cmd
      ntgrCLIExecute $strDev "exit"
  }
}