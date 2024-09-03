################################################################################
#
#  File Name    : STK-001.tcl
#
#  Description  : This testcase used to test downloading image from master to
#                 one or more slave units.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-001.tcl" "******************** Starting Test case STK-001.tcl ********************"

## Copy image to a unit
foreach dut $ntgrDUTList {
    CALStkArchiveCopy $dut $ntgrSlaveUnitID
}

set bRet 1
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-001.tcl" "Image downloading complete, press 'y' if all DUT downloaded correctly; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


## Copy image to all slave units
foreach dut $ntgrDUTList {
    CALStkArchiveCopy $dut
}

set bRet 1
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-001.tcl" "Image downloading complete, press 'y' if all DUT downloaded correctly; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(STK-001.tcl) "OK"
} else {
    set NtgrTestResult(STK-001.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-001.tcl" "******************** Complete Test case STK-001.tcl ********************"
#*************************  End of Test case  **********************************