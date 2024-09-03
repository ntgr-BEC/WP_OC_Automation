#!/bin/sh
################################################################################   
#
#  File Name     : TC-MAC-AGINGTIME-044.tcl
#
#  Description   :
#        This testcase periodicaly change the MAC aging time to the default value.
#
#  Config File   : TC-MAC-AGINGTIME-044.cfg
#
#  History       :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 28, 2006  Scott Zhang       Created
#
################################################################################
NtgrDumpLog $NTGR_LOG_TERSE "TC-MAC-AGINGTIME-044.tcl" "****************** Starting Test case TC-MAC-AGINGTIME-044.tcl ******************"

foreach {switch_name aging_time} [array get NTGR_MAC_AGING_TIME] {
    NtgrDumpLog $NTGR_LOG_TERSE "TC-MAC-AGINGTIME-044.tcl" "Setting MAC aging time to $aging_time on switch $switch_name"
    CALSetMacAgeTime $switch_name $aging_time
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-MAC-AGINGTIME-044.tcl" "******************** Completed Test case TC-MAC-AGINGTIME-044.tcl ********************"
#*************************  End of Test case  ****************************************************************