#!/bin/sh
################################################################################   
#
#  File Name   : TC-TG-PORTS-DOWN-029.tcl
#
#  Description :
#         This testcase is to set tester ports' status to down. Tester ports'
#         up/down used to simulate end user access, such as users' online or offline.
#
#  History     :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 21, 2006  Scott Zhang       Created
#
################################################################################

NtgrDumpLog $NTGR_LOG_TERSE "TC-TG-PORTS-DOWN-029.tcl" "******************** Starting Test case TC-TG-PORTS-DOWN-029.tcl ********************"

foreach {tg_name portlist} [array get ntgr_tgUpDownPortList] {
    NtgrDumpLog $NTGR_LOG_TERSE "TC-TG-PORTS-DOWN-029.tcl" "Setting ports($portlist) status to down on $tg_name"

#   UALConnectTester $tg_name
    foreach port $portlist {
        UALPortLinkDown $tg_name $port
    }
#   UALDisconnectTester $tg_name
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-TG-PORTS-DOWN-029.tcl" "******************** Completed Test case TC-TG-PORTS-DOWN-029.tcl ********************"
#*************************  End of Test case  ****************************************************************