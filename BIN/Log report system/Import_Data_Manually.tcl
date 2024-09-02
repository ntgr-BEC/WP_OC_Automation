#*********************************************************************************   
#
#  File Name        : Import_Data_Manually.tcl
#
#  Description      :
#         Using this function to import log records manually
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        2016-12-29     Jim Xie        Created
#
#*********************************************************************************

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
    } else {
        puts "ERROR:: Execute command <select count(TEMP_CASE_NAME) from switch_temp;> failed. Error message: $retStr"
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
    } else {
        puts "ERROR:: Execute command <select count(TEMP_CASE_NAME) from insight_temp;> failed. Error message: $retStr"
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
                }
            }
        } else {
            puts "ERROR:: Execute command <select count(TEMP_CASE_NAME) from switch_temp;> failed. Error message: $retStr"
            return 0
        }

        # load data from local file: 'C:/AUTOMATION/BIN/Log report system/Data/Switch_log.txt'
        if {[catch {exec mysql -u root -proot -D system -e "load data local infile '$import_file' into table system.switch_temp;"} retStr]} {
        } else {
            puts "ERROR:: Execute command <load data local infile $import_file into table system.switch_temp;> failed. Error message: $retStr"
            return 0
        }

        after 10000
        # get the count from system.switch_temp 
        if {[catch {exec mysql -u root -proot -D system -e "select count(TEMP_CASE_NAME) from switch_temp;"} retStr]} {
            set tmpList [split $retStr \n]
            foreach iList $tmpList {
                if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                    set after_import_count $count_case
                }
            }
        } else {
            puts "ERROR:: Execute command <select count(TEMP_CASE_NAME) from switch_temp;> failed. Error message: $retStr"
            return 0
        }
        set success_import [expr $after_import_count - $before_import_count]
    } else {
        puts "ERROR:: Data file:$import_file was not exist."
    }
    if {$success_import > 0} {
        file copy -force $import_file "C:/AUTOMATION/BIN/Log report system/Data/Backup Data/"
        after 2000
        file delete -force $import_file
        Import_records_from_tempDatabase
    } else {
        puts "ERROR:: Import records file:$import_file to table <switch_temp> failed."
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
            }
        }
    } else {
        puts "ERROR:: Execute command <select count(1) from switch_testcase_log;> failed. Error message: $retStr"
        return 0
    }

    # Using this procedure to export records from table switch_temp then import into following tables:
    # switch_dut_firmware, switch_dut_name, switch_feature, switch_testcase, and switch_testcase_log.
    # After import success, then delete the record from table switch_temp.
    if {[catch {exec mysql -u root -proot -D system -e "Call Exp_Switch_Log();"} retStr]} {
    } else {
        puts "ERROR:: Execute command <Call Exp_Switch_Log();> failed. Error message: $retStr"
        return 0
    }

    after 10000

    # get the count from system.switch_testcase_log
    if {[catch {exec mysql -u root -proot -D system -e "select count(1) from switch_testcase_log;"} retStr]} {
        set tmpList [split $retStr \n]
        foreach iList $tmpList {
            if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                set after_import_count $count_case
            }
        }
    } else {
        puts "ERROR:: Execute command <select count(1) from switch_testcase_log;> failed. Error message: $retStr"
        return 0
    }

    set success_import [expr $after_import_count - $before_import_count]
    if {$success_import > 0} {
    } else {
        puts "ERROR:: Using procedure:Exp_Switch_Log import records from table <switch_temp> failed."
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
                }
            }
        } else {
            puts "ERROR:: Execute command <select count(TEMP_CASE_NAME) from insight_temp;> failed. Error message: $retStr"
            return 0
        }

        # load data from local file: 'C:/AUTOMATION/BIN/Log report system/Data/Switch_log.txt'
        if {[catch {exec mysql -u root -proot -D system -e "load data local infile '$import_file' into table system.insight_temp;"} retStr]} {
        } else {
            puts "ERROR:: Execute command <load data local infile $import_file into table system.insight_temp;> failed. Error message: $retStr"
            return 0
        }

        after 10000
        # get the count from system.insight_temp 
        if {[catch {exec mysql -u root -proot -D system -e "select count(TEMP_CASE_NAME) from insight_temp;"} retStr]} {
            set tmpList [split $retStr \n]
            foreach iList $tmpList {
                if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                    set after_import_count $count_case
                }
            }
        } else {
            puts "ERROR:: Execute command <select count(TEMP_CASE_NAME) from insight_temp;> failed. Error message: $retStr"
            return 0
        }
        set success_import [expr $after_import_count - $before_import_count]
    } else {
        puts "ERROR:: Data file:$import_file was not exist."
    }
    if {$success_import > 0} {
        file copy -force $import_file "C:/AUTOMATION/BIN/Log report system/Data/Backup Data/"
        after 2000
        file delete -force $import_file
        Import_Insight_records_from_tempDatabase
    } else {
        puts "ERROR:: Import records file:$import_file to table <insight_temp> failed."
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
            }
        }
    } else {
        puts "ERROR:: Execute command <select count(1) from insight_testcase_log;> failed. Error message: $retStr"
        return 0
    }

    # Using this procedure to export records from table insight_temp then import into following tables:
    # insight_firmware, insight_os_version, insight_feature, insight_testcase, and insight_testcase_log.
    # After import success, then delete the record from table insight_temp.
    if {[catch {exec mysql -u root -proot -D system -e "Call Exp_Insight_Log();"} retStr]} {
    } else {
        puts "ERROR:: Execute command <Call Exp_Insight_Log()> failed. Error message: $retStr"
        return 0
    }

    after 10000

    # get the count from system.insight_testcase_log
    if {[catch {exec mysql -u root -proot -D system -e "select count(1) from insight_testcase_log;"} retStr]} {
        set tmpList [split $retStr \n]
        foreach iList $tmpList {
            if {[regexp -nocase -- "^(\[0-9\]+)$" $iList match count_case]} {
                set after_import_count $count_case
            }
        }
    } else {
        puts "ERROR:: Execute command <select count(1) from insight_testcase_log;> failed. Error message: $retStr"
        return 0
    }

    set success_import [expr $after_import_count - $before_import_count]
    if {$success_import > 0} {
    } else {
        puts "ERROR:: Using procedure:Exp_Insight_Log import records from table <insight_temp> failed."
        return 0
    }
    return $success_import
}

# get all the txt data files under dictionary <C:/AUTOMATION/BIN/Log report system/Data>
set MatchFileList [glob "C:/AUTOMATION/BIN/Log report system/Data/*.txt"]
set i 0
set file_exist 0
while {$i < [llength $MatchFileList]} {
    set filename [lindex $MatchFileList $i]
    # replace "/" to "\" for findstr
    set fild_filename [string map {/ \\} $filename]
    set tailname [file tail $filename]
    if {[catch {exec findstr "System/Insight" $fild_filename} ret]} {
        #switch data files
        puts "INFO:: switch data file: $filename"
        set j 0
        while {[Switch_get_count_of_tmp] > 0 } {
            after 5000
            if {$j > 600} {
                exit while
            }
            incr j
            puts "INFO:: Waiting for the table <switch_tmp> empty , Loop:$j."
        }
        Switch_import_records_from_txt $filename
    } else {
        #insight data files
        puts "INFO:: insight data file: $filename"
        set j 0
        while {[Insight_get_count_of_tmp] > 0} {
            after 5000
            if {$j > 600} {
                exit while
            }
            incr j
            puts "INFO:: Waiting for the table <insight_tmp> empty , Loop:$j."
        }
        Insight_import_records_from_txt $filename
    }
    #puts "ret=$ret"
    incr i 1
}