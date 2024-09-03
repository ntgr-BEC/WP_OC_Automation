################################################################################
#
#  File Name    : STK-002.tcl
#
#  Description  : This testcase used to test downloading image from a TFTP
#                 server to DUT at stack configure mode.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-002.tcl" "******************** Starting Test case STK-002.tcl ********************"
## Configure management IP address
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
}

## Copy image to a unit
foreach dut $ntgrDUTList {
    NtgrDumpLog  $NTGR_LOG_TERSE  "STK-002.tcl" "Press any key to start downloading image from $ntgrTftpImagePath($dut) to $dut."
    gets stdin
    CALStkArchiveDownload $dut $ntgrTftpImagePath($dut)
}

set bRet 1
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-002.tcl" "Image downloading complete, press 'y' if all DUT downloaded correctly; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(STK-002.tcl) "OK"
} else {
    set NtgrTestResult(STK-002.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-002.tcl" "******************** Complete Test case STK-002.tcl ********************"
#*************************  End of Test case  **********************************