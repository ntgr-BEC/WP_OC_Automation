#!/bin/sh
################################################################################   
#
#  File Name		: lib-ixautomate.tcl
#
#  Description       	:
#         This TCL contains utility functions to used by IxAutomate
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        2013-10-08    Jim       Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: ntgr_IxAutomate_setPortSpeed
#
#  Description    : This functions sets the IxAutomate port speed and physical_mode
#         
#  Usage          : ntgr_IxAutomate_setPortSpeed <port> <speed> <physical_mode>
# 
#*******************************************************************************
proc ntgr_IxAutomate_setPortSpeed {port speed physical_mode} {
    global testConf
    set portlist [split $port]
	set expect_chassis [lindex $portlist 0]
	set expect_card [lindex $portlist 1]
	set expect_port [lindex $portlist 2]
	if {$speed == "10G"} {
	    #set testConf(speed,$expect_chassis,$expect_card,$expect_port) 10000
		Netgear_log_file "lib-ixautomate.tcl" "Ixia Chassis=$expect_chassis Card=$expect_card Port=$expect_port Speed Mode=10000."
	} else {
	    if {$physical_mode == "copper"} {
		    set speed_mode "Copper$speed"
		} else {
		    set speed_mode "Fiber$speed"
		}
		Netgear_log_file "lib-ixautomate.tcl" "Ixia Chassis=$expect_chassis Card=$expect_card Port=$expect_port Speed Mode=$speed_mode."
		set testConf(speed,$expect_chassis,$expect_card,$expect_port) $speed_mode
	}
}

#*******************************************************************************
#
#  Function Name	: ntgr_IxAutomate_setexcludePorts
#
#  Description    : This functions sets the IxAutomate exclude ports
#         
#  Usage          : ntgr_IxAutomate_setexcludePorts <port>
# 
#*******************************************************************************
proc ntgr_IxAutomate_setexcludePorts {port} {
    global testConf
    set AllPortList $testConf(excludePorts)
	set portindex [lsearch $AllPortList $port]
	set testConf(excludePorts) [lreplace $AllPortList $portindex $portindex]
}

#*******************************************************************************
#
#  Function Name	: ntgr_IxAutomate_CheckReaultPass
#
#  Description    : This functions is to check the result is pass or fail
#         
#  Usage          : ntgr_IxAutomate_CheckReaultPass
# 
#*******************************************************************************
proc ntgr_IxAutomate_CheckReaultPass {} {
    global ixautomate_report_path
    set MatchFileList {}
    set loop_flag 0
    set return_value 0
    # wait the ixautomate end maximum is 180*60 seconds
    while {$loop_flag < 180} {
        after 60000
        # check the file is exist or not 
        set MatchFileList [glob "$ixautomate_report_path/*/*/*/*"]
        set i 0
        set file_exist 0
        while {$i < [llength $MatchFileList]} {
            set filename [lindex $MatchFileList $i]
            set tailname [file tail $filename]
            if {[regexp {^logFile.txt$} $tailname]} {
                set file_exist 1
                break
            }
            incr i 1
        }
        # if file exist check the result or continue to wait the file created
        if { $file_exist } {
            set file_handle [open $filename r]
            set data [read $file_handle]
            Netgear_log_file "lib-ixautomate.tcl" "Following info is IxNetwork run log." "YES"
            Netgear_log_file "$data" "" "YES"
            set finish_flag [string match {*PASS Criteria Evaluation*} $data]
            set fail_flag [string match {* FAIL*} $data]
            set error_flag [string match {*Error !!*} $data]
            set pass_flag [string match {*Of Trials Passed: [1-9]*} $data]
            close $file_handle
            if {$finish_flag} {
                if {$pass_flag} {
                    if { $fail_flag == 0 && $error_flag == 0} {
                        set return_value 1
                    }
                } else {
                    if { $fail_flag == 0  && $error_flag == 0 } {
                        set return_value 1
                    }
                }
            }
            break
        } else {
            incr loop_flag 1
            continue
        }
    }
    return $return_value
}

#*******************************************************************************
#
#  Function Name	: ntgr_IxAutomate_GetExcludePorts
#
#  Description      : use the file:C:\AUTOMATION\Functionality\IxAutomate Test Suites\IxiaCardList.cfg to create the all ixia port list
#         
#  Usage            : ntgr_IxAutomate_GetExcludePorts
# 
#  Return           : the list contains all ixia ports
#
#*******************************************************************************
proc ntgr_IxAutomate_GetExcludePorts {} {

    set filename "C:/AUTOMATION/Functionality/IxAutomate Test Suites/IxiaCardList.cfg"
	set allPortList {}
	if {[file exists $filename]} {
	    set fhandle [open $filename r]		
		set flag 0
		set bflag  0
		while {[gets $fhandle line] != -1 } {
            if {[regexp {(^ChassisID)[\s]*=[\s]*([0-9]*)[\s]*} $line total item ChassisID]} {
	            Netgear_log_file "lib-ixautomate.tcl" "Ixia Chassis ID=$ChassisID."
	        }
	        if {$flag} {
	            if {[regexp {[\s]*(Card_SlotNumber)[\s]*=[\s]*([0-9]*)[\s]*} $line total item Card_SlotNumber]} {
	        	    Netgear_log_file "lib-ixautomate.tcl" "Ixia Card Slot Number=$Card_SlotNumber."
				}
		        if {[regexp {[\s]*(Card_PortCount)[\s]*=[\s]*([0-9]*)[\s]*} $line total item Card_PortCount]} {
	                Netgear_log_file "lib-ixautomate.tcl" "Ixia Card Slot Number=$Card_SlotNumber. Port Count=$Card_PortCount"
			        set i 1
			        while { $i <= $Card_PortCount} {
			            set port "$ChassisID $Card_SlotNumber $i" 
				        set allPortList  [linsert $allPortList $bflag $port ]
				        incr bflag 1
				        incr i 1
			        }		
		            set flag 0
	            }
	        }
	
	        if {[regexp {^\[Card_Info_.*\]$} $line]} {
	            set flag 1
	        }
        }

        close $fhandle
	} else {
	    Netgear_log_file "lib-ixautomate.tcl" "Error: File: $filename is not exist."
	}
	
	Netgear_log_file "lib-ixautomate.tcl" "All Ixia Port List=$allPortList."
	
	return $allPortList
}


