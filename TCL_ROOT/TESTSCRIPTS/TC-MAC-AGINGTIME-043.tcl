#!/bin/sh
################################################################################   
#
#  File Name     : TC-MAC-AGINGTIME-043.tcl
#
#  Description   :
#        This testcase periodicaly change MAC aging time of switches.
#
#  Config File   : TC-MAC-AGINGTIME-043.cfg
#
#  History       :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 28, 2006  Scott Zhang       Created
#
################################################################################

NtgrDumpLog $NTGR_LOG_TERSE "TC-MAC-AGINGTIME-043.tcl" "****************** Starting Test case TC-MAC-AGINGTIME-043.tcl ******************"

foreach {switch_name aging_time} [array get NTGR_MAC_AGING_TIME] {
    NtgrDumpLog $NTGR_LOG_TERSE "TC-MAC-AGINGTIME-043.tcl" "Setting MAC aging time to $aging_time on switch $switch_name"
    CALSetMacAgeTime $switch_name $aging_time
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-MAC-AGINGTIME-043.tcl" "******************** Completed Test case TC-MAC-AGINGTIME-043.tcl ********************"
#*************************  End of Test case  ****************************************************************