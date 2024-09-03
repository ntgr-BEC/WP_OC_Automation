#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-UploadLogFile-042.tcl
#
#  Description       	: This file defines the test scripts for uploading log file from 
#				  the swithces to the tftp server.
#
#  Test case Name 	: TC-UploadLogFile-042.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#####################################################################################

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-UploadLogFile-042.tcl" "******************** Starting Test case TC-UploadLogFile-042.tcl ********************"

for_array_keys switch ntgrUploadLogFile {
	NtgrDumpLog  $NTGR_LOG_TERSE  "TC-UploadLogFile-042.tcl" "Uploading log file from the swtich $switch "
	CALUploadLogFile $switch
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-UploadLogFile-042.tcl" "******************** Stopping Test case TC-UploadLogFile-042.tcl ********************"


