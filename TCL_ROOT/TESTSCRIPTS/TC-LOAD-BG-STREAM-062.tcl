#!/bin/sh
################################################################################   
#
#  File Name   		: TC-LOAD-BG-STREAM-062.tcl
#
#  Description 		: This testcase is used to create the streams in the back
#		     		  ground ports 
#
#  Config File 		: TC-LOAD-BG-STREAM-UnknownUnicast-062.cfg
#
#  Global Variables	: ntgr_BackGroundTrafficPortList
#
#  History     :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-LOAD-BG-STREAM-062.tcl" "******************** Starting Test case TC-LOAD-BG-STREAM-062.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_BackGroundTrafficPortList] {
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-LOAD-BG-STREAM-062.tcl" "Loading Strems  in the Tester - $chassis_id  Ports - $portlist "
	UALLoadStream $chassis_id $portlist
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-LOAD-BG-STREAM-062.tcl" "******************** Completed Test case TC-LOAD-BG-STREAM-062.tcl ********************"
#*************************  End of Test case  ****************************************************************