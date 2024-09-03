#!/bin/sh
################################################################################   
#
#  File Name   		: TC-UNLOAD-BG-STREAM-061.tcl
#
#  Description 		: This testcase is used to delete the streams created in the back
#		     		  ground ports 
#
#  Config File 		: TC-UNLOAD-BG-STREAM-061.tcl
#
#  Global Variables	: ntgr_BackGroundTrafficPortList
#
#  History     :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-UNLOAD-BG-STREAM-061.tcl" "******************** Starting Test case TC-UNLOAD-BG-STREAM-061.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_BackGroundTrafficPortList] {
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-UNLOAD-BG-STREAM-061.tcl" "Unloading Strems  in the Tester - $chassis_id  Ports - $portlist "
	UALUnLoadStream $chassis_id $portlist
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-UNLOAD-BG-STREAM-061.tcl" "******************** Completed Test case TC-UNLOAD-BG-STREAM-061.tcl ********************"
#*************************  End of Test case  ****************************************************************