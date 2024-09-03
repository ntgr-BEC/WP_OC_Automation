#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-UploadStartupConfig-043.tcl
#
#  Description       	: This file defines the test scripts for uploading Startup 
#				  Config file from the swithces to the tftp server.
#
#  Test case Name 	: TC-UploadStartupConfig-043.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#####################################################################################

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-UploadStartupConfig-043.tcl" "******************** Starting Test case TC-UploadStartupConfig-043.tcl ********************"

for_array_keys switch ntgrUploadStartUpFile {
	NtgrDumpLog  $NTGR_LOG_TERSE  "TC-UploadStartupConfig-043.tcl" "Uploading Startup Config file from the swtich $switch "
	CALUploadStartupConfigFile $switch
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-UploadStartupConfig-043.tcl" "******************** Stopping Test case TC-UploadStartupConfig-043.tcl ********************"


