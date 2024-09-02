package require tdom

if {[file exist com]} {
    cd com
}

if {[file exist TransferLogToReportSystem.tcl]} {
    cd ..
}

set TestSuitxmlFile "./test-output/testng-results.xml"
set ConfigxmlFile "./src/test/resources/config_webportal.xml"

set DataFile "./target/webportal_automation_data.txt"
if {[file exists $DataFile]} {
    file delete -force $DataFile
}

set SERVER_PORT  "8888"
set SERVER_IP "10.10.0.30"
if {[file exists $ConfigxmlFile]} {
    set Cfh [open $ConfigxmlFile r]
    set Cxml [read $Cfh]
    set Cdom [dom parse $Cxml]
    set Cdoc [$Cdom documentElement]

    set SERVER_IP [$Cdoc selectNodes string(/config/LogSystemServer)]
    close $Cfh
}

if {$SERVER_IP == ""} {
	set SERVER_IP "10.10.0.30"
}

puts "Start at [clock format [clock seconds] -format %Y/%m/%d-%H:%M:%S] to $SERVER_IP"
set LoadDataLogFile "./target/webportal_import_data.log" 
set log_fd ""

proc start_log {args} {
    global log_fd
    global LoadDataLogFile
    set log_fd [open $LoadDataLogFile a+]
    fconfigure $log_fd -buffering line
}

proc stop_log {args} {
    global log_fd
    close $log_fd
}

proc log_file {log_flag log_string } {
    global log_fd
    set currentTime [clock seconds]
    set time1 [clock format $currentTime -format %H:%M:%S]
    set time2 [clock format $currentTime -format %D]
    puts $log_fd "$time2 $time1: $log_flag , $log_string"
}

proc ParseXMLFile {} {
    global TestSuitxmlFile
    global ConfigxmlFile
    global DataFile
    
    if {[file exists $TestSuitxmlFile] && [file exists $ConfigxmlFile]} {
        set Tsfh [open $TestSuitxmlFile r]
        set Tsxml [read $Tsfh]
        set Tsdom [dom parse $Tsxml]
        set Tsdoc [$Tsdom documentElement]

        set Cfh [open $ConfigxmlFile r]
        set Cxml [read $Cfh]
        set Cdom [dom parse $Cxml]
        set Cdoc [$Cdom documentElement]

        set Responsible [$Cdoc selectNodes string(/config/Responsible)]
        set WebPortalVersion [$Cdoc selectNodes string(/config/WebPortalVersion)]

        set data_fd [open $DataFile a+]
        fconfigure $data_fd -buffering line
        for {set i 1} {$i > 0} {incr i} {
            set node_classname "/testng-results/suite/test\[$i\]/@name"
            set classname [$Tsdoc selectNodes string($node_classname)]
			
            if {$classname == ""} {
                break
            }
			puts "classname=$classname"
			if {$classname == "Default test" || $classname == "Test Suite"} {
			    for {set j 1} {$j > 0} {incr j} {
			        set node_casename "/testng-results/suite/test\[$i\]/class\[$j\]/@name"
				    set casename [$Tsdoc selectNodes string($node_casename)]
					if {$casename == ""} {
                        break
                    }
				    set splitList [split $casename "."]
				    set CaseName [lindex $splitList end-1]
				    set functionName [lindex $splitList 2]
				    set CaseResult "PASS"
				    for {set k 1} {$k > 0} {incr k} {
				        set node_failedname "/testng-results/suite/test\[$i\]/class\[$j\]/test-method\[$k\]/@status"
					    set failedstep [$Tsdoc selectNodes string($node_failedname)]
					    if {$failedstep == "FAIL"} {
					        set CaseResult "FAIL"
					    }
					    if {$failedstep == ""} {
                            break
                        }
				    }
				    set data_line "\\\N	$WebPortalVersion	$functionName	$Responsible	$CaseName	$CaseResult	 "
                    puts $data_line
                    puts $data_fd $data_line
				
				}
			} else {
			
			    set splitList [split $classname "_"]
				set functionName [lindex $splitList 1]
				if {$functionName == ""} {
				    set functionName [lindex $splitList 0]
				}
                
            
			    for {set j 1} {$j > 0} {incr j} {
			        set node_casename "/testng-results/suite/test\[$i\]/class\[$j\]/@name"
                    set casename [$Tsdoc selectNodes string($node_casename)]
				    set CaseResult "PASS"
                    if {$casename == ""} {
                        break
                    }
				    set splitList [split $casename "."]
				    set CaseName [lindex $splitList end-1]
				
				    for {set k 1} {$k > 0} {incr k} {
				        set node_failedname "/testng-results/suite/test\[$i\]/class\[$j\]/test-method\[$k\]/@status"
					    set failedstep [$Tsdoc selectNodes string($node_failedname)]
					    if {$failedstep == "FAIL"} {
					        set CaseResult "FAIL"
					    }
					    if {$failedstep == ""} {
                            break
                        }
				    }
			
				    set data_line "\\\N	$WebPortalVersion	$functionName	$Responsible	$CaseName	$CaseResult	 "
                    puts $data_line
                    puts $data_fd $data_line
			    }
			}
        }
        close $data_fd
        close $Tsfh
        close $Cfh
        log_file "INFO::" "Succe parse total:$i cases result from $TestSuitxmlFile ."
    } else {
        log_file "ERROR::" "Testing Result file:$TestSuitxmlFile was not exist! Or configure file:$ConfigxmlFile was not exists. Please check it."
    }
}

proc Cli_Write {sock client_type} {
    global SERVER_IP SERVER_PORT DataFile
    
    set TmpTime [clock seconds]
    set log_time [clock format $TmpTime -format %Y%m%d%H%M%S]
    set DataFile "./target/webportal_automation_data.txt"
    set Cli_Bak_fileName "./target/webportal_automation_data.$log_time.txt"
    if {![file exists $DataFile]} {
        log_file "ERROR" "File: $DataFile not exists."
        set forever 1
        exit
    }
    file copy -force $DataFile $Cli_Bak_fileName
    puts $sock $client_type
    flush $sock
    after 1000
    if {[catch {open $DataFile r} get_handle]} {
        log_file "ERROR" "Open $DataFile ERROR: $get_handle"
        set forever 1
        exit
    } else {
        while {[gets $get_handle get_line] >=0} {
            puts $sock $get_line
            flush $sock 
        }
        after 5000
        if {[eof $get_handle]} {
            puts $sock "quit"
            close $sock
            set forever 1
            close $get_handle
            after 1000
            if {[catch {file delete -force $DataFile} delete_res]} {
                log_file "INFO" "Delete File:$DataFile successful."
            } else {
                log_file "WARN" "Delete File:$DataFile error: $delete_res."
            }
            file delete -force $Cli_Bak_fileName
            log_file "INFO" "Transfer log data to server:<$SERVER_IP:$SERVER_PORT> successful."
            exit
        }
    }

}

proc Create_Client_Socket {client_type} {
    global SERVER_IP SERVER_PORT
    set status [catch {socket $SERVER_IP $SERVER_PORT} sock_tmp]
    if {!$status} {
        log_file "INFO" "Create client socket connection to server:<$SERVER_IP:$SERVER_PORT> successful."
        fconfigure $sock_tmp -buffering line -blocking 0
        fileevent $sock_tmp writable [list Cli_Write $sock_tmp $client_type]
    } else {
        log_file "ERROR" "Create client socket connection to server:<$SERVER_IP:$SERVER_PORT> failed. Error message: $sock_tmp"
        set forever 1
        exit
    }
}
start_log
ParseXMLFile
Create_Client_Socket "webportal"

vwait forever
stop_log
puts "End at [clock format [clock seconds] -format %Y/%m/%d-%H:%M:%S]"
exit

