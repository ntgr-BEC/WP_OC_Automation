#!/bin/sh
################################################################################   
#
#  File Name   : TC-STK-MOVE-MNGR-048.tcl
#
#  Description :
#         This testcase is used to move the stack's management unit from one
#         to another regularly.
#
#  History     :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Sep 6, 2006   Scott Zhang       Created
#
################################################################################

NtgrDumpLog $NTGR_LOG_TERSE "TC-STK-MOVE-MNGR-048.tcl" "******************** Starting Test case TC-STK-MOVE-MNGR-048.tcl ********************"

foreach switch_name $SWITCH_STACK_LIST {
    set masterID [_ntgrStkGetMasterUnitID $switch_name]
    for_array_keys unit_index ntgr_unitList_$switch_name {
        set unitID [_ntgrStkGetUnitID $switch_name $unit_index]
        if {$unitID != $masterID} {
            CALStkMoveMgrmt $switch_name $unitID
        }
    }
    # We must move the master to the origin
    CALStkMoveMgrmt $switch_name $masterID
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-STK-MOVE-MNGR-048.tcl" "******************** Completed Test case TC-STK-MOVE-MNGR-048.tcl ********************"

#*************************  End of Test case  ****************************************************************