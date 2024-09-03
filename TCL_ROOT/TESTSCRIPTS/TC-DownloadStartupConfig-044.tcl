#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-DownloadStartupConfig-044.tcl
#
#  Description       	: This file defines the test scripts for Downloading Startup 
#				  Config file to the swithces from the tftp server.
#
#  Test case Name 	: TC-DownloadStartupConfig-044.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#####################################################################################

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-DownloadStartupConfig-044.tcl" "******************** Starting Test case TC-DownloadStartupConfig-044.tcl ********************"

for_array_keys switch ntgrDownloadStartUpFile {
	NtgrDumpLog  $NTGR_LOG_TERSE  "TC-DownloadStartupConfig-044.tcl" "Downloading Startup Config file to the swtich $switch "
	CALDownloadStartupConfigFile $switch
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-DownloadStartupConfig-044.tcl" "******************** Stopping Test case TC-DownloadStartupConfig-044.tcl ********************"


