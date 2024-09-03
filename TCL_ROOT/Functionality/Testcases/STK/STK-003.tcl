################################################################################
#
#  File Name    : STK-003.tcl
#
#  Description  : This testcase used to test downloading image from a tftp server
#                 to DUT by copy command and whether can we easily update image
#                 of a stack instead of just one unit.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-003.tcl" "******************** Starting Test case STK-003.tcl ********************"
## Configure management IP address
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
}

## Copy image to a unit
foreach dut $ntgrDUTList {
    NtgrDumpLog  $NTGR_LOG_TERSE  "STK-003.tcl" "Press any key to start downloading image from $ntgrTftpImagePath($dut) to $dut."
    gets stdin
    CALSwitchCopy $dut $ntgrTftpImagePath($dut) "system:image"
}

set bRet 1
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-003.tcl" "Image downloading complete, press 'y' if all DUT downloaded correctly; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(STK-003.tcl) "OK"
} else {
    set NtgrTestResult(STK-003.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-003.tcl" "******************** Complete Test case STK-003.tcl ********************"
#*************************  End of Test case  **********************************