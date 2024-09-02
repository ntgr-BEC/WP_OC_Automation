#*********************************************************************************   
#
#  File Name        : Automation_Log_System.tcl
#
#  Description      :
#         Using this function to import log records.
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        2016-10-18     Jim Xie        Created
#
#*********************************************************************************

set LoadDataLogFile "C:/AUTOMATION/BIN/Log report system/Log/Import_data_to_database.log" 
set currentTime [clock seconds]
set time [clock format $currentTime -format %Y%m%d%H%M%S]
set OldLogFile "C:/AUTOMATION/BIN/Log report system/Log/Import_data_to_database_$time.log"
if {[file exists $LoadDataLogFile]} {
    file rename -force $LoadDataLogFile $OldLogFile
}
set log_fd ""
set SERVER_IP ""
set SERVER_PORT 8888


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

proc Switch_get_count_of_tmp {} {
    set tmp_table_count 0
    # get the count from system.switch_temp 
    if {[catch {exec mysql -u root -proot -D system -e "select count(TEMP_CASE_NAME) from switch_temp;"} retStr]} {
        set tmpList [split $retStr \n]
        foreach iList $tmpList {
            if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                set tmp_table_count $count_case
            }
        }
        log_file "INFO" "Execute command <select count(TEMP_CASE_NAME) from switch_temp;> successful. Table Count: $tmp_table_count ."
    } else {
        log_file "ERROR" "Execute command <select count(TEMP_CASE_NAME) from switch_temp;> failed. Error message: $retStr"
        exit
    }
    return $tmp_table_count
}

proc Insight_get_count_of_tmp {} {
    set tmp_table_count 0
    # get the count from system.insight_temp 
    if {[catch {exec mysql -u root -proot -D system -e "select count(TEMP_CASE_NAME) from insight_temp;"} retStr]} {
        set tmpList [split $retStr \n]
        foreach iList $tmpList {
            if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                set tmp_table_count $count_case
            }
        }
        log_file "INFO" "Execute command <select count(TEMP_CASE_NAME) from insight_temp;> successful. Table Count: $tmp_table_count ."
    } else {
        log_file "ERROR" "Execute command <select count(TEMP_CASE_NAME) from insight_temp;> failed. Error message: $retStr"
        exit
    }
    return $tmp_table_count
}

proc Webportal_get_count_of_tmp {} {
    set tmp_table_count 0
    # get the count from system.webportal_temp 
    if {[catch {exec mysql -u root -proot -D system -e "select count(TEMP_TESTCASE_NAME) from webportal_temp;"} retStr]} {
        set tmpList [split $retStr \n]
        foreach iList $tmpList {
            if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                set tmp_table_count $count_case
            }
        }
        log_file "INFO" "Execute command <select count(TEMP_TESTCASE_NAME) from webportal_temp;> successful. Table Count: $tmp_table_count ."
    } else {
        log_file "ERROR" "Execute command <select count(TEMP_TESTCASE_NAME) from webportal_temp;> failed. Error message: $retStr"
        exit
    }
    return $tmp_table_count
}

proc Switch_import_records_from_txt {import_file} {
    set success_import 0
    set before_import_count 0
    set after_import_count 0
    if {[file exists $import_file]} {
        # get the count from system.switch_temp 
        if {[catch {exec mysql -u root -proot -D system -e "select count(TEMP_CASE_NAME) from switch_temp;"} retStr]} {
            set tmpList [split $retStr \n]
            foreach iList $tmpList {
                if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                    set before_import_count $count_case
                    log_file "INFO" "Before import data into database , Count of the table <switch_temp> :$count_case ."
                }
            }
        } else {
            log_file "ERROR" "Execute command <select count(TEMP_CASE_NAME) from switch_temp;> failed. Error message: $retStr"
            return 0
        }

        # load data from local file: 'C:/AUTOMATION/BIN/Log report system/Data/Switch_log.txt'
        if {[catch {exec mysql -u root -proot -D system -e "load data local infile '$import_file' into table system.switch_temp;"} retStr]} {
            log_file "INFO" "Import records to table <switch_temp> from loacal file:$import_file successful."
        } else {
            log_file "ERROR" "Execute command <load data local infile $import_file into table system.switch_temp;> failed. Error message: $retStr"
            return 0
        }

        after 10000
        # get the count from system.switch_temp 
        if {[catch {exec mysql -u root -proot -D system -e "select count(TEMP_CASE_NAME) from switch_temp;"} retStr]} {
            set tmpList [split $retStr \n]
            foreach iList $tmpList {
                if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                    set after_import_count $count_case
                    log_file "INFO" "After import data into database , Count of the table <switch_temp> :$count_case ."
                }
            }
        } else {
            log_file "ERROR" "Execute command <select count(TEMP_CASE_NAME) from switch_temp;> failed. Error message: $retStr"
            return 0
        }
        set success_import [expr $after_import_count - $before_import_count]
    } else {
        log_file "ERROR" "Data file:$import_file was not exist."
    }
    if {$success_import > 0} {
        log_file "INFO" "Import records file:$import_file to table <switch_temp> successful."
        file copy -force $import_file "C:/AUTOMATION/BIN/Log report system/Data/Backup Data/"
        after 2000
        file delete -force $import_file
        Import_records_from_tempDatabase
    } else {
        log_file "ERROR" "Import records file:$import_file to table <switch_temp> failed."
        return 0
    }
    return $success_import
}

proc Import_records_from_tempDatabase {} {
    set success_import 0
    set before_import_count 0
    set after_import_count 0
    # get the count from system.switch_testcase_log
    if {[catch {exec mysql -u root -proot -D system -e "select count(1) from switch_testcase_log;"} retStr]} {
        set tmpList [split $retStr \n]
        foreach iList $tmpList {
            if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                set before_import_count $count_case
                log_file "INFO" "Before import data into database , Count of the table <switch_testcase_log>:$count_case."
            }
        }
    } else {
        log_file "ERROR" "Execute command <select count(1) from switch_testcase_log;> failed. Error message: $retStr"
        return 0
    }

    # Using this procedure to export records from table switch_temp then import into following tables:
    # switch_dut_firmware, switch_dut_name, switch_feature, switch_testcase, and switch_testcase_log.
    # After import success, then delete the record from table switch_temp.
    if {[catch {exec mysql -u root -proot -D system -e "Call Exp_Switch_Log();"} retStr]} {
        log_file "INFO" "Using procedure:Exp_Switch_Log import records from table <switch_temp> successful."
    } else {
        log_file "ERROR" "Execute command <Call Exp_Switch_Log();> failed. Error message: $retStr"
        return 0
    }

    after 10000

    # get the count from system.switch_testcase_log
    if {[catch {exec mysql -u root -proot -D system -e "select count(1) from switch_testcase_log;"} retStr]} {
        set tmpList [split $retStr \n]
        foreach iList $tmpList {
            if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                set after_import_count $count_case
                log_file "INFO" "After import data into database , Count of the table <switch_testcase_log>:$count_case."
            }
        }
    } else {
        log_file "ERROR" "Execute command <select count(1) from switch_testcase_log;> failed. Error message: $retStr"
        return 0
    }

    set success_import [expr $after_import_count - $before_import_count]
    if {$success_import > 0} {
        log_file "INFO" "Using procedure:Exp_Switch_Log import records from table <switch_temp> successful."
    } else {
        log_file "ERROR" "Using procedure:Exp_Switch_Log import records from table <switch_temp> failed."
        return 0
    }
    return $success_import
}

proc Insight_import_records_from_txt {import_file} {
    set success_import 0
    set before_import_count 0
    set after_import_count 0
    if {[file exists $import_file]} {	    
        # get the count from system.insight_temp 
        if {[catch {exec mysql -u root -proot -D system -e "select count(TEMP_CASE_NAME) from insight_temp;"} retStr]} {
            set tmpList [split $retStr \n]
            foreach iList $tmpList {
                if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                    set before_import_count $count_case
                    log_file "INFO" "Before import data into database , Count of the table <insight_temp>:$count_case."
                }
            }
        } else {
            log_file "ERROR" "Execute command <select count(TEMP_CASE_NAME) from insight_temp;> failed. Error message: $retStr"
            return 0
        }

        # load data from local file: 'C:/AUTOMATION/BIN/Log report system/Data/Switch_log.txt'
        if {[catch {exec mysql -u root -proot -D system -e "load data local infile '$import_file' into table system.insight_temp;"} retStr]} {
            log_file "INFO" "Import records to table <insight_temp> from loacal file:$import_file successful."
        } else {
            log_file "ERROR" "Execute command <load data local infile $import_file into table system.insight_temp;> failed. Error message: $retStr"
            return 0
        }

        after 10000
        # get the count from system.insight_temp 
        if {[catch {exec mysql -u root -proot -D system -e "select count(TEMP_CASE_NAME) from insight_temp;"} retStr]} {
            set tmpList [split $retStr \n]
            foreach iList $tmpList {
                if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                    set after_import_count $count_case
                    log_file "INFO" "After import data into database , Count of the table <insight_temp>:$count_case."
                }
            }
        } else {
            log_file "ERROR" "Execute command <select count(TEMP_CASE_NAME) from insight_temp;> failed. Error message: $retStr"
            return 0
        }
        set success_import [expr $after_import_count - $before_import_count]
    } else {
        log_file "ERROR" "Data file:$import_file was not exist."
    }
    if {$success_import > 0} {
        log_file "INFO" "Import records file:$import_file to table <insight_temp> successful."
        file copy -force $import_file "C:/AUTOMATION/BIN/Log report system/Data/Backup Data/"
        after 2000
        file delete -force $import_file
        Import_Insight_records_from_tempDatabase
    } else {
        log_file "ERROR" "Import records file:$import_file to table <insight_temp> failed."
        return 0
    }
    return $success_import
}

proc Import_Insight_records_from_tempDatabase {} {
    set success_import 0
    set before_import_count 0
    set after_import_count 0
    # get the count from system.insight_testcase_log
    if {[catch {exec mysql -u root -proot -D system -e "select count(1) from insight_testcase_log;"} retStr]} {
        set tmpList [split $retStr \n]
        foreach iList $tmpList {
            if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                set before_import_count $count_case
                log_file "INFO" "Before import data into database , Count of the table <insight_testcase_log>:$count_case."
            }
        }
    } else {
        log_file "ERROR" "Execute command <select count(1) from insight_testcase_log;> failed. Error message: $retStr"
        return 0
    }

    # Using this procedure to export records from table insight_temp then import into following tables:
    # insight_firmware, insight_os_version, insight_feature, insight_testcase, and insight_testcase_log.
    # After import success, then delete the record from table insight_temp.
    if {[catch {exec mysql -u root -proot -D system -e "Call Exp_Insight_Log();"} retStr]} {
        log_file "INFO" "Using procedure:Exp_Insight_Log import records from table <insight_temp> successful."
    } else {
        log_file "ERROR" "Execute command <Call Exp_Insight_Log()> failed. Error message: $retStr"
        return 0
    }

    after 10000

    # get the count from system.insight_testcase_log
    if {[catch {exec mysql -u root -proot -D system -e "select count(1) from insight_testcase_log;"} retStr]} {
        set tmpList [split $retStr \n]
        foreach iList $tmpList {
            if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                set after_import_count $count_case
                log_file "INFO" "After import data into database , Count of the table <insight_testcase_log>:$count_case."
            }
        }
    } else {
        log_file "ERROR" "Execute command <select count(1) from insight_testcase_log;> failed. Error message: $retStr"
        return 0
    }

    set success_import [expr $after_import_count - $before_import_count]
    if {$success_import > 0} {
        log_file "INFO" "Using procedure:Exp_Insight_Log import records from table <insight_temp> successful."
    } else {
        log_file "ERROR" "Using procedure:Exp_Insight_Log import records from table <insight_temp> failed."
        return 0
    }
    return $success_import
}

proc Webportal_import_records_from_txt {import_file} {
    set success_import 0
    set before_import_count 0
    set after_import_count 0
    if {[file exists $import_file]} {	    
        # get the count from system.webportal_temp 
        if {[catch {exec mysql -u root -proot -D system -e "select count(TEMP_TESTCASE_NAME) from webportal_temp;"} retStr]} {
            set tmpList [split $retStr \n]
            foreach iList $tmpList {
                if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                    set before_import_count $count_case
                    log_file "INFO" "Before import data into database , Count of the table <webportal_temp>:$count_case."
                }
            }
        } else {
            log_file "ERROR" "Execute command <select count(TEMP_TESTCASE_NAME) from webportal_temp;> failed. Error message: $retStr"
            return 0
        }
        # load data from local file: 'C:/AUTOMATION/BIN/Log report system/Data/Switch_log.txt'
        if {[catch {exec mysql -u root -proot -D system -e "load data local infile '$import_file' into table system.webportal_temp;"} retStr]} {
            log_file "INFO" "Import records to table <webportal_temp> from loacal file:$import_file successful."
        } else {
            log_file "ERROR" "Execute command <load data local infile $import_file into table system.webportal_temp;> failed. Error message: $retStr"
            return 0
        }
        after 10000
        # get the count from system.webportal_temp 
        if {[catch {exec mysql -u root -proot -D system -e "select count(TEMP_TESTCASE_NAME) from webportal_temp;"} retStr]} {
            set tmpList [split $retStr \n]
            foreach iList $tmpList {
                if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                    set after_import_count $count_case
                    log_file "INFO" "After import data into database , Count of the table <webportal_temp>:$count_case."
                }
            }
        } else {
            log_file "ERROR" "Execute command <select count(TEMP_TESTCASE_NAME) from webportal_temp;> failed. Error message: $retStr"
            return 0
        }
        set success_import [expr $after_import_count - $before_import_count]
    } else {
        log_file "ERROR" "Data file:$import_file was not exist."
    }
    if {$success_import > 0} {
        log_file "INFO" "Import records file:$import_file to table <webportal_temp> successful."
        file copy -force $import_file "C:/AUTOMATION/BIN/Log report system/Data/Backup Data/"
        after 2000
        file delete -force $import_file
        Import_Webportal_records_from_tempDatabase
    } else {
        log_file "ERROR" "Import records file:$import_file to table <webportal_temp> failed."
        return 0
    }
    return $success_import
}

proc Import_Webportal_records_from_tempDatabase {} {
    set success_import 0
    set before_import_count 0
    set after_import_count 0
    # get the count from system.webportal_testcase_log
    if {[catch {exec mysql -u root -proot -D system -e "select count(1) from webportal_testcase_log;"} retStr]} {
        set tmpList [split $retStr \n]
        foreach iList $tmpList {
            if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                set before_import_count $count_case
                log_file "INFO" "Before import data into database , Count of the table <webportal_testcase_log>:$count_case."
            }
        }
    } else {
        log_file "ERROR" "Execute command <select count(1) from webportal_testcase_log;> failed. Error message: $retStr"
        return 0
    }

    # Using this procedure to export records from table webportal_temp then import into following tables:
    # insight_firmware, insight_os_version, insight_feature, insight_testcase, and webportal_testcase_log.
    # After import success, then delete the record from table webportal_temp.
    if {[catch {exec mysql -u root -proot -D system -e "Call Exp_WebPortal_Log();"} retStr]} {
        log_file "INFO" "Using procedure:Exp_WebPortal_Log import records from table <webportal_temp> successful."
    } else {
        log_file "ERROR" "Execute command <Call Exp_WebPortal_Log()> failed. Error message: $retStr"
        return 0
    }

    after 10000

    # get the count from system.webportal_testcase_log
    if {[catch {exec mysql -u root -proot -D system -e "select count(1) from webportal_testcase_log;"} retStr]} {
        set tmpList [split $retStr \n]
        foreach iList $tmpList {
            if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                set after_import_count $count_case
                log_file "INFO" "After import data into database , Count of the table <webportal_testcase_log>:$count_case."
            }
        }
    } else {
        log_file "ERROR" "Execute command <select count(1) from webportal_testcase_log;> failed. Error message: $retStr"
        return 0
    }

    set success_import [expr $after_import_count - $before_import_count]
    if {$success_import > 0} {
        log_file "INFO" "Using procedure:Exp_WebPortal_Log import records from table <webportal_temp> successful."
    } else {
        log_file "ERROR" "Using procedure:Exp_WebPortal_Log import records from table <webportal_temp> failed."
        return 0
    }
    return $success_import
}

proc Server_Accept {newSock cli_addr cli_port} {
    global ClientTypeArray
    log_file "INFO" "Accepted socket connection from client <$cli_addr:$cli_port>."
    fconfigure $newSock -blocking 0
    #fconfigure $newSock -buffering line
    set TmpTime [clock seconds]
    set log_time [clock format $TmpTime -format %Y%m%d%H%M%S]
    set Ser_fileName "C:/AUTOMATION/BIN/Log report system/Data/Client_$cli_addr.$cli_port.Records.$log_time.txt"
    if {[catch {open $Ser_fileName a} write_handle]} {
        log_file "ERROR" "Open file:$Ser_fileName error."
    } else {
        log_file "INFO" "Open $Ser_fileName successful, Write handle:$write_handle ."
        fileevent $newSock readable [list Server_Read $newSock $cli_addr $Ser_fileName $write_handle]
    }
    #file delete -force $Ser_fileName
}

proc Server_Read {sock cli_addr Ser_fileName write_handle} {
    global ClientTypeArray

    if {[eof $sock] || [catch {gets $sock line}]} {
        log_file "INFO" "close $cli_addr for $Ser_fileName on dis."
        close $sock
        close $write_handle
        return
    }
    if {[string compare $line "check"] == 0} {
        log_file "INFO" "close $cli_addr for $Ser_fileName on check."
        close $sock
        close $write_handle
        file delete -force $Ser_fileName
        return
    }
    
    if {[string compare $line "quit"] == 0} {
        close $sock
        close $write_handle
        # import records to table: switch_temp 
        if {$ClientTypeArray($write_handle) == "switch"} {
            set i 0
            while {[Switch_get_count_of_tmp] > 0 } {
                after 5000
                if {$i > 10} {
                    exit while
                }
                incr i
                log_file "INFO" "Waiting for the table <switch_tmp> empty , Loop:$i, Write handle : $write_handle"
            }
            Switch_import_records_from_txt $Ser_fileName
            unset ClientTypeArray($write_handle)
        } elseif {$ClientTypeArray($write_handle) == "insight"} {
            set i 0
            while {[Insight_get_count_of_tmp] > 0} {
                after 5000
                if {$i > 10} {
                    exit while
                }
                incr i
                log_file "INFO" "Waiting for the table <insight_tmp> empty , Loop:$i, Write handle : $write_handle"
            }
            Insight_import_records_from_txt $Ser_fileName
            unset ClientTypeArray($write_handle)
        }  elseif {$ClientTypeArray($write_handle) == "webportal"} {
            set i 0
            while {[Webportal_get_count_of_tmp] > 0} {
                after 5000
                if {$i > 6} {
                    exit while
                }
                incr i
                log_file "INFO" "Waiting for the table <webportal_tmp> empty , Loop:$i, Write handle : $write_handle"
            }
            Webportal_import_records_from_txt $Ser_fileName
            unset ClientTypeArray($write_handle)
		} else {
            log_file "ERROR" "Client <$cli_addr> type <$ClientTypeArray($write_handle)> is ERROR, Only support <switch> and <insight> and <webportal>."
        }
    } elseif {[string compare $line "insight"] == 0 || [string compare $line "switch"] == 0 || [string compare $line "webportal"] == 0} {
        set ClientTypeArray($write_handle) $line
        log_file "INFO" "Client <$cli_addr> type is <$line>."
    } elseif {$line == ""} {
	    log_file "WARN" "Empty Line."
	} else {
        puts $write_handle $line
        flush $write_handle 
    }
}

proc Cli_Write {sock client_type} {
    global SERVER_IP SERVER_PORT
    set TmpTime [clock seconds]
    set log_time [clock format $TmpTime -format %Y%m%d%H%M%S]
    if {$client_type == "switch"} {
        set Cli_Bak_fileName "C:/AUTOMATION/BIN/Log report system/Data/Switch_Client_Log_Records.$log_time.txt"
        set Cli_fileName "C:/AUTOMATION/BIN/Log report system/Data/Switch_Client_Log_Records.txt"
    } else {
        set Cli_Bak_fileName "C:/AUTOMATION/BIN/Log report system/Data/Insight_Client_Log_Records.$log_time.txt"
        set Cli_fileName "C:/AUTOMATION/BIN/Log report system/Data/Insight_Client_Log_Records.txt"
    }
    if {![file exists $Cli_fileName]} {
        log_file "ERROR" "File: $Cli_fileName not exists."
        set forever 1
        exit
    }
    file copy -force $Cli_fileName $Cli_Bak_fileName
    puts $sock $client_type
    flush $sock
    after 1000
    if {[catch {open $Cli_fileName r} get_handle]} {
        log_file "ERROR" "Open $Cli_fileName ERROR: $get_handle"
        set forever 1
        exit
    } else {
        while {[gets $get_handle get_line] >=0} {
            puts $sock $get_line
			after 1000
            flush $sock 
        }
        after 5000
        if {[eof $get_handle]} {
            puts $sock "quit"
            close $sock
            set forever 1
            close $get_handle
            after 1000
            if {[catch {file delete -force $Cli_fileName} delete_res]} {
                log_file "INFO" "Delete File:$Cli_fileName successful."
            } else {
                log_file "WARN" "Delete File:$Cli_fileName error: $delete_res."
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

proc Check_Server_Socket {} {
    global SERVER_IP SERVER_PORT
    set status [catch {socket $SERVER_IP $SERVER_PORT} sock_tmp]
    if {!$status} {
        log_file "INFO" "Check server:<$SERVER_IP:$SERVER_PORT> successful."
        puts $sock_tmp "check"
        close $sock_tmp
        set forever 1
        exit
    } else {
        log_file "ERROR" "Connect server:<$SERVER_IP:$SERVER_PORT> failed. CHECKSERVERERROR: Error message: $sock_tmp"
        set forever 1
        exit
    }
}

# ------------------------ Following is main script ---------------------------------------------------
start_log
if {$argc < 1} {
    log_file "ERROR" "Usage: <tclsh Automation_Log_System.tcl Server> or <tclsh Automation_Log_System.tcl Client Switch Server_IP> or <tclsh Automation_Log_System.tcl Client Insight Server_IP> or <tclsh Automation_Log_System.tcl Client Check Server_IP>."
    stop_log
    set forever 1
    exit
} else {
    set ser_flag [string tolower [lindex $argv 0]]
    if {$ser_flag == "server"} {
        array set ClientTypeArray {}
        set status [catch {socket -server Server_Accept $SERVER_PORT} ser_listen]
        if {!$status} {
            set ser_sock $ser_listen
            log_file "INFO" "Create server socket successful. Server's socket is $ser_listen."
        } else {
            log_file "ERROR" "Create server's socket failed: $ser_listen"
            set forever 1
            exit
        }
    } elseif {$ser_flag == "client"} {
        if {$argc != 3} {
            log_file "ERROR" "Usage: <tclsh Automation_Log_System.tcl Client Switch Server_IP> or <tclsh Automation_Log_System.tcl Client Insight Server_IP> or <tclsh Automation_Log_System.tcl Client Check Server_IP>."
            stop_log
            set forever 1
            exit
        } else {
            set project_type [string tolower [lindex $argv 1]]
            set SERVER_IP [lindex $argv 2]
            if {$project_type != "switch" && $project_type != "insight" && $project_type != "check" } {
                log_file "ERROR" "Usage: <tclsh Automation_Log_System.tcl Client Switch Server_IP> or <tclsh Automation_Log_System.tcl Client Insight Server_IP> or <tclsh Automation_Log_System.tcl Client Check Server_IP>."
                stop_log
                set forever 1
                exit
            } else {
                if {$project_type == "check"} {
                    Check_Server_Socket
                } else {
                    Create_Client_Socket $project_type
                }
            }
        }
    } else {
        log_file "ERROR" "Usage: <tclsh Automation_Log_System.tcl Server> or <tclsh Automation_Log_System.tcl Client Switch Server_IP> or <tclsh Automation_Log_System.tcl Client Insight Server_IP> or <tclsh Automation_Log_System.tcl Client Check Server_IP>"
        stop_log
        set forever 1
        exit
    }
}

vwait forever
stop_log
exit
