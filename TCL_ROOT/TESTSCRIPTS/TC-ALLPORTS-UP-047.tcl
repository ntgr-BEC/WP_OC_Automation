#!/bin/sh
################################################################################   
#
#  File Name     : TC-ALLPORTS-UP-047.tcl
#
#  Description   :
#        This testcase no shutdown all ports of switch periodically.
#
#  Config File   : TC-ALLPORTS-DOWN-046.cfg
#
#  History       :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 23, 2006  Scott Zhang       Created
#
################################################################################

NtgrDumpLog $NTGR_LOG_TERSE "TC-ALLPORTS-UP-047.tcl" "***** Starting Test case TC-ALLPORTS-UP-047.tcl for no shutdown all ports *****"

foreach switch $ntgr_swList_AllPortsUpDown {
    NtgrDumpLog $NTGR_LOG_TERSE "TC-ALLPORTS-UP-047.tcl" "No shutdown all ports on switch $switch"
    CALAllPortsLinkUp $switch
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-ALLPORTS-UP-047.tcl" "******************** Completed Test case TC-ALLPORTS-UP-047.tcl ********************"
#*************************  End of Test case  ****************************************************************