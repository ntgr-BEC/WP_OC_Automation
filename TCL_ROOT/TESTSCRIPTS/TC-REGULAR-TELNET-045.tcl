#!/bin/sh
################################################################################   
#
#  File Name   : TC-REGULAR-TELNET-045.tcl
#
#  Description :
#        This testcase regularly login switches by telnet and execute some 
#        comands at privileged mode.
#
#  History     :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 25, 2006  Scott Zhang       Created
#
################################################################################

NtgrDumpLog $NTGR_LOG_TERSE "TC-REGULAR-TELNET-045.tcl" "****************** Starting Test case TC-REGULAR-TELNET-045.tcl ******************"

foreach {switch_name cmdlist} [array get ntgr_RegularTelnetCmdList] {
    NtgrDumpLog $NTGR_LOG_TERSE "TC-REGULAR-TELNET-045.tcl" "Execute some commands on switch $switch_name at privileged mode by telnet"
    CALExecPrivilegedCmd $switch_name $cmdlist
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-REGULAR-TELNET-045.tcl" "******************** Completed Test case TC-REGULAR-TELNET-045.tcl ********************"
#*************************  End of Test case  ****************************************************************