#!/bin/sh
################################################################################   
#
#  File Name     : TC-POCH-ADD-037.tcl
#
#  Description   :
#        This testcase does the LAGs adding operation periodically.
#
#  Config File   : TC-POCH-DEL-036.cfg
#
#  History       :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 22, 2006  Scott Zhang       Created
#
################################################################################

NtgrDumpLog $NTGR_LOG_TERSE "TC-POCH-ADD-037.tcl" "***** Starting Test case TC-POCH-ADD-037.tcl for LAGs reconfiguring *****"

foreach switch $ntgr_swList_AddDelPortChannel {
    NtgrDumpLog $NTGR_LOG_TERSE "TC-POCH-ADD-037.tcl" "Reconfiguring all port channels on switch $switch"
    CALCreateAllPortChannels $switch
    NtgrDumpLog $NTGR_LOG_TERSE "TC-POCH-ADD-037.tcl" "Reconfiguring all VLANs on switch $switch"
    CALCreateVlanAll $switch
}

foreach LAG $ntgr_pcList_AddDel {
    NtgrDumpLog $NTGR_LOG_TERSE "TC-POCH-ADD-037.tcl" "Reconfiguring port channel $LAG on switches"
    CALCreatePortChannel $LAG
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-POCH-ADD-037.tcl" "******************** Completed Test case TC-POCH-ADD-037.tcl ********************"
#*************************  End of Test case  ****************************************************************