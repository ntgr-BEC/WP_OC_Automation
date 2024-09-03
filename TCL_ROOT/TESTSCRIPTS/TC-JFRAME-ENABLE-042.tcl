#!/bin/sh
################################################################################   
#
#  File Name     : TC-JFRAME-ENABLE-042.tcl
#
#  Description   :
#        This testcase enable jumbo frames support periodically.
#
#  Config File   : TC-JFRAME-DISABLE-041.cfg
#
#  History       :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 24, 2006  Scott Zhang       Created
#
################################################################################

NtgrDumpLog $NTGR_LOG_TERSE "TC-JFRAME-ENABLE-042.tcl" "***** Starting Test case TC-JFRAME-ENABLE-042.tcl *****"

foreach switch $ntgr_swList_JFrameEnableDisable {
    NtgrDumpLog $NTGR_LOG_TERSE "TC-JFRAME-ENABLE-042.tcl" "Reconfiguring jumbo frames support on switch $switch"
    CALJumboFrameConfig $switch
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-JFRAME-ENABLE-042.tcl" "******************** Completed Test case TC-JFRAME-ENABLE-042.tcl ********************"
#*************************  End of Test case  ****************************************************************