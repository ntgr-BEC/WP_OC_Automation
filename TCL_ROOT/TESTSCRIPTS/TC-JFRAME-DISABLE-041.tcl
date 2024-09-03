#!/bin/sh
################################################################################   
#
#  File Name     : TC-JFRAME-DISABLE-041.tcl
#
#  Description   :
#        This testcase disable jumbo frames support periodically.
#
#  Config File   : TC-JFRAME-DISABLE-041.cfg
#
#  History       :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 24, 2006  Scott Zhang       Created
#
################################################################################

NtgrDumpLog $NTGR_LOG_TERSE "TC-JFRAME-DISABLE-041.tcl" "***** Starting Test case TC-JFRAME-DISABLE-041.tcl *****"

foreach switch $ntgr_swList_JFrameEnableDisable {
    NtgrDumpLog $NTGR_LOG_TERSE "TC-JFRAME-DISABLE-041.tcl" "Disable jumbo frames support on switch $switch"
    CALJumboFrameDisable $switch
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-JFRAME-DISABLE-041.tcl" "******************** Completed Test case TC-JFRAME-DISABLE-041.tcl ********************"
#*************************  End of Test case  ****************************************************************