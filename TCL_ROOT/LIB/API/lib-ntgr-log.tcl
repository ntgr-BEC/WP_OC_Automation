#!/bin/sh
################################################################################   
#
#  File Name		: lib-ntgr-log.tcl
#
#  Description       	:
#         This TCL contains APIs to log the results
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V       Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: Netgear_start_log
#
#  Description    : This function is an API used to start the logging process
#         
#  Usage          : Netgear_start_log
# 
#*******************************************************************************
proc Netgear_start_log {args} {
	global ntgr_logOptions
	global NTGR_LOG_PATH
	global NTGR_LOG_FILE_NAME
	global NTGR_LOCAL_CONSOLE
	global NTGR_LOCAL_FILE
        puts "\n $args \n" 
	if {$ntgr_logOptions(LOG_FLAG) &  $NTGR_LOCAL_CONSOLE } {
		puts "**************************** Start Log *****************************************************\n"
        
	} 
	if {$ntgr_logOptions(LOG_FLAG) &  $NTGR_LOCAL_FILE } {
        if { 0 == [llength $args] } {
            set tclLogFile "$NTGR_LOG_PATH/$NTGR_LOG_FILE_NAME"
        } else {
            set tclLogFile "[lindex $args 0]"
        }
		set ntgr_logOptions(LOG_FD) [ntgr_log_open_file "$tclLogFile"]
	}
  set TelLogFile $NTGR_LOG_PATH
  append TelLogFile "/telnet.log"
	set telnetfd [open $TelLogFile w+]
	close $telnetfd
}

#*******************************************************************************
#
#  Function Name	: Netgear_log_file
#
#  Description    : This function is an API used to log the result
#         
#  Usage          : Netgear_log_file <test_name> <log_string>
# 
#*******************************************************************************
proc Netgear_log_file {test_name log_string args } {
	global ntgr_logOptions
	global NTGR_LOCAL_CONSOLE
	global NTGR_LOCAL_FILE
	global NTGR_REMOTE_FILE
	global NTGR_TESTDIR_FILE
    set currentTime [clock seconds]
    set time1 [clock format $currentTime -format %H:%M:%S]
    set time2 [clock format $currentTime -format %D]

	if {$ntgr_logOptions(LOG_FLAG) & $NTGR_LOCAL_CONSOLE} {
		puts "\n $time2 $time1: $test_name , $log_string \n"
	} 
	if {$ntgr_logOptions(LOG_FLAG)& $NTGR_LOCAL_FILE } {
		puts $ntgr_logOptions(LOG_FD) "\n $time2 $time1: $test_name , $log_string \n"
        if { 0 != [llength $args] } {
            puts $ntgr_logOptions(TRAFFIC_LOG_FD) "\n $time2 $time1: $test_name , $log_string \n"
       }   
	}
}

#*******************************************************************************
#
#  Function Name	: Netgear_stop_log
#
#  Description    : This function is an API used to start the logging process
#         
#  Usage          : Netgear_stop_log
# 
#*******************************************************************************
proc Netgear_stop_log {args} {
	global ntgr_logOptions
	global NTGR_LOCAL_CONSOLE
	global NTGR_LOCAL_FILE
	global NTGR_REMOTE_FILE
	global NTGR_TESTDIR_FILE
	global NTGR_SCRIPT_NAME
	global NTGR_LOG_PATH
	global NTGR_LOG_FILE_NAME
	global NTGR_LIB_PATH
	global NTGR_DUT_LIST

  source "$NTGR_LIB_PATH/Misc/lib-logging.tcl"

  set TelLogFile $NTGR_LOG_PATH
  append TelLogFile "/telnet.log"
  file delete $TelLogFile
  set NTGR_DUTLIST_File  $NTGR_LOG_PATH
  append NTGR_DUTLIST_File "/dutlist.log"
  set dutlistfd [open $NTGR_DUTLIST_File w]
  puts $dutlistfd  $NTGR_DUT_LIST
	close $dutlistfd

	if {$ntgr_logOptions(LOG_FLAG) & $NTGR_LOCAL_CONSOLE} {
		puts "**************************** End Log *****************************************************\n"
	} 
      if {$ntgr_logOptions(LOG_FLAG) & $NTGR_LOCAL_FILE }  {
		ntgr_log_close_file $ntgr_logOptions(LOG_FD)
        if { 0 == [llength $args] } {
    		file delete $NTGR_LOG_PATH/$NTGR_SCRIPT_NAME.log
    		file rename $NTGR_LOG_PATH/$NTGR_LOG_FILE_NAME $NTGR_LOG_PATH/$NTGR_SCRIPT_NAME.log
        } else {
            # TBD
        }
	#	spawn mv $NTGR_LOG_PATH/$NTGR_LOG_FILE_NAME $NTGR_LOG_PATH/$NTGR_SCRIPT_NAME.log
	} 
      if {$ntgr_logOptions(LOG_FLAG) & $NTGR_REMOTE_FILE || 
                $ntgr_logOptions(LOG_FLAG) & $NTGR_TESTDIR_FILE} {
			# Connect to the remote host using RCP
#			rcp -b $NTGR_LOG_PATH/$NTGR_SCRIPT_NAME $ntgr_logOptions(LOG_HOST).$ntgr_logOptions(LOG_USER_NAME): 
#									$ntgr_logOptions(LOG_HOST_PATH)/$NTGR_SCRIPT_NAME
			puts "RCP - TODO\n"
	}
	
}


#*******************************************************************************
#
#  Function Name	: ntgr_log_open_file
#
#  Description    : This function opens the file for logging
#         
#  Usage          : ntgr_log_open_file <file_name>
# 
#*******************************************************************************
proc ntgr_log_open_file {file_name} {
	global NTGR_SCRIPT_NAME
	set log_fd [open $file_name w+]
    fconfigure $log_fd -buffering line
	puts $log_fd "******************************** Start Log *************************************************\n"
	puts $log_fd "************************* File : $NTGR_SCRIPT_NAME.log ******************************************\n"
	puts $log_fd "********************************************************************************************\n"
	return $log_fd
}

#*******************************************************************************
#
#  Function Name	: ntgr_log_close_file
#
#  Description    : This function closes the file used for logging
#         
#  Usage          : ntgr_log_close_file <file_hndl>
# 
#*******************************************************************************
proc ntgr_log_close_file {file_hndl} {
	puts $file_hndl " *************************************** End Log ********************************************\n"
	puts $file_hndl " ********************************************************************************************\n"
	close $file_hndl
}
#*******************************************************************************
#
#  Function Name	: NtgrDumpLog
#
#  Description    : This function is an API used to log the result
#         
#  Usage          : NtgrDumpLog <log_level> <test_name> <log_string>
#  
#                   log_level options are NTGR_LOG_ERROR  = 16
#                                         NTGR_LOG_TERSE  = 32
#                                         NTGR_LOG_INFORM = 64
#                                         NTGR_LOG_DEBUG  = 128   
# 
#*******************************************************************************
proc NtgrDumpLog {log_level  test_name log_string } {
    global ntgr_logOptions
    global NTGR_LOG_ERROR
    global NTGR_LOG_TERSE
    global NTGR_LOG_INFORM
    global NTGR_LOG_DEBUG
    
    global NTGR_LOCAL_CONSOLE
    global NTGR_LOCAL_FILE
    global NTGR_REMOTE_FILE
    global NTGR_TESTDIR_FILE
    
    if { ( $ntgr_logOptions(LOG_FLAG) &  $NTGR_LOG_ERROR ) & ( $log_level & $NTGR_LOG_ERROR ) } { 
	if {$ntgr_logOptions(LOG_FLAG) & $NTGR_LOCAL_CONSOLE} {
	    puts "\n ERROR : $test_name : $log_string \n"
	} 
	if { $ntgr_logOptions(LOG_FLAG)& $NTGR_LOCAL_FILE } {
	    puts $ntgr_logOptions(LOG_FD) "\n ERROR : $test_name : $log_string \n"
	}
	
	if { $ntgr_logOptions(LOG_FLAG)& $NTGR_REMOTE_FILE } {
	    # To be done
	}
	if { $ntgr_logOptions(LOG_FLAG)& $NTGR_TESTDIR_FILE } {
	    # To be done
	}
    }
    
    
    if { ( $ntgr_logOptions(LOG_FLAG) &  $NTGR_LOG_TERSE ) & ( $log_level & $NTGR_LOG_TERSE )} { 
	if {$ntgr_logOptions(LOG_FLAG) & $NTGR_LOCAL_CONSOLE } {
	    puts "\n TERSE : $test_name : $log_string \n"
	} 
	if { $ntgr_logOptions(LOG_FLAG)& $NTGR_LOCAL_FILE } {
	    puts $ntgr_logOptions(LOG_FD) "\n TERSE : $test_name : $log_string \n"
	}
	
	if { $ntgr_logOptions(LOG_FLAG)& $NTGR_REMOTE_FILE } {
	    # To be done
	}
	if { $ntgr_logOptions(LOG_FLAG)& $NTGR_TESTDIR_FILE } {
		# To be done
	}
    }
    
    if { ( $ntgr_logOptions(LOG_FLAG) &  $NTGR_LOG_INFORM ) & ( $log_level & $NTGR_LOG_INFORM )} { 
	if {$ntgr_logOptions(LOG_FLAG) & $NTGR_LOCAL_CONSOLE} {
	    puts "\n INFORM : $test_name : $log_string \n"
	} 
	if { $ntgr_logOptions(LOG_FLAG)& $NTGR_LOCAL_FILE } {
	    puts $ntgr_logOptions(LOG_FD) "\n INFORM : $test_name : $log_string \n"
	}
	
	if { $ntgr_logOptions(LOG_FLAG)& $NTGR_REMOTE_FILE } {
	    # To be done
	}
	if { $ntgr_logOptions(LOG_FLAG)& $NTGR_TESTDIR_FILE } {
	    # To be done
	}
    }
    
    
    if { ( $ntgr_logOptions(LOG_FLAG) &  $NTGR_LOG_DEBUG ) & ( $log_level & $NTGR_LOG_DEBUG )} { 
	if {$ntgr_logOptions(LOG_FLAG) & $NTGR_LOCAL_CONSOLE} {
	    puts "\n DEBUG : $test_name : $log_string \n"
	} 
	if { $ntgr_logOptions(LOG_FLAG)& $NTGR_LOCAL_FILE } {
	    puts $ntgr_logOptions(LOG_FD) "\n DEBUG : $test_name : $log_string \n"
	}
	if { $ntgr_logOptions(LOG_FLAG)& $NTGR_REMOTE_FILE } {
	    # To be done
	}
	if { $ntgr_logOptions(LOG_FLAG)& $NTGR_TESTDIR_FILE } {
	    # To be done
	}
    }
}

#*******************************************************************************
#
#  Function Name	: NtgrSetLogDestination
#
#  Description    : This function choses one or more log outputs. 
#         
#  Usage          : NtgrSetLogDestination [ $NTGR_LOCAL_CONSOLE ] [ $NTGR_LOCAL_FILE ] [ $NTGR_REMOTE_FILE ] [ $NTGR_TESTDIR_FILE ] 
# 
#*******************************************************************************
proc NtgrSetLogDestination { args } {
    global ntgr_logOptions
    foreach output $args { 
	set ntgr_logOptions(LOG_FLAG) [expr $ntgr_logOptions(LOG_FLAG) | $output ]
    }    
}

#*******************************************************************************
#
#  Function Name	: NtgrSetLogLevel
#
#  Description    : This function choses one of more log outputs. 
#         
#  Usage          : NtgrSetLogLevel [$NTGR_LOG_ERROR ] [ $NTGR_LOG_TERSE ] [ $NTGR_LOG_INFORM ] [ $NTGR_LOG_DEBUG ] 
# 
#*******************************************************************************
proc NtgrSetLogLevel { args } {
    global ntgr_logOptions
    foreach level $args { 
	set ntgr_logOptions(LOG_FLAG) [expr $ntgr_logOptions(LOG_FLAG) | $level ]
    }
}

# ******************************************************************************
#  Function Name	: ntgr_log_open_traffic_file
#
#  Description    : This function opens the file for logging
#         
#  Usage          : ntgr_log_open_traffic_file <file_name>
# 
#*******************************************************************************
proc ntgr_log_open_traffic_file {file_name} {
	global NTGR_SCRIPT_NAME
	set log_fd [open $file_name w+]
    fconfigure $log_fd -buffering line
	#puts $log_fd "******************************** Start traffic Log *************************************************\n"
	#puts $log_fd "************************* File : $NTGR_SCRIPT_NAME.log ******************************************\n"
	#puts $log_fd "********************************************************************************************\n"
	return $log_fd
}

#*******************************************************************************
#
#  Function Name	: Netgear_start_traffic_log
#
#  Description    : This function is an API used to start the logging process
#         
#  Usage          : Netgear_start_traffic_log
# 
#*******************************************************************************
proc Netgear_start_traffic_log {args} {
	global ntgr_logOptions
	global NTGR_LOG_PATH
	global NTGR_LOG_FILE_NAME
	global NTGR_LOCAL_CONSOLE
	global NTGR_LOCAL_FILE
    puts "\n $args \n"
	if {$ntgr_logOptions(LOG_FLAG) &  $NTGR_LOCAL_CONSOLE } {
		puts "**************************** Start Traffic *****************************************************\n"
        
	} 
	if {$ntgr_logOptions(LOG_FLAG) &  $NTGR_LOCAL_FILE } {
        if { 0 == [llength $args] } {
            set tclTrafficLogFile "$NTGR_LOG_PATH/$NTGR_TRAFFIC_LOG_FILE_NAME"
        } else {
            set tclTrafficLogFile "[lindex $args 0]"
        }
		set ntgr_logOptions(TRAFFIC_LOG_FD) [ntgr_log_open_traffic_file "$tclTrafficLogFile"]
	}
  set tclTrafficLogFile $NTGR_LOG_PATH
  append tclTrafficLogFile "/telnet1.log"
	set telnetfd [open $tclTrafficLogFile w+]
	close $telnetfd
}

 #*******************************************************************************
#
#  Function Name	: ntgr_log_close_file
#
#  Description    : This function closes the file used for logging
#         
#  Usage          : ntgr_log_close_file <file_hndl>
# 
#*******************************************************************************
proc ntgr_log_close_traffic_file {file_hndl} {
	#puts $file_hndl " *************************************** End Log ********************************************\n"
	#puts $file_hndl " ********************************************************************************************\n"
	close $file_hndl
}