#!/bin/sh
################################################################################   
#
#  File Name   : TC-STK-025.tcl
#
#  Description :
#         This testcase is to configure stacking.
#
#  History     :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 15, 2006  Scott Zhang       Created
#
################################################################################

Netgear_log_file "TC-STK-025.tcl" "******************** Starting Test case TC-STK-025.tcl ********************"

Netgear_log_file "TC-STK-025.tcl" "Started configure stacking on the each switch"
foreach switch_name $SWITCH_STACK_LIST {
#     CALStkSetUnitPriority $switch_name
#     CALStkRenumberUnit $switch_name 1 7
#     CALStkDelMember $switch_name 1
#     CALStkDelMember $switch_name 8
#     CALStkAddMember $switch_name 1 3
#     CALStkQosModeEnable $switch_name
#     CALStkQosModeDisable $switch_name
#     CALStkArchiveCopy $switch_name 5
#     CALStkSetStackPortMode $switch_name

    CALStkMoveMgrmt $switch_name 2
    sleep 30
    CALStkMoveMgrmt $switch_name 3    
    sleep 30
    CALStkMoveMgrmt $switch_name 4    
    sleep 30
    CALStkMoveMgrmt $switch_name 5    
    sleep 30
    CALStkMoveMgrmt $switch_name 6    
    sleep 30
    CALStkMoveMgrmt $switch_name 1    
    sleep 30
    CALStkMoveMgrmt $switch_name 2    
    sleep 30
    CALStkMoveMgrmt $switch_name 3    
    sleep 30
    CALStkMoveMgrmt $switch_name 4    
    sleep 30
    CALStkMoveMgrmt $switch_name 5    
    sleep 30
    CALStkMoveMgrmt $switch_name 6    
    sleep 30
    CALStkMoveMgrmt $switch_name 1    
}

Netgear_log_file "TC-STK-025.tcl" "Completed stacking configuration on all Switches"

set packet_loss 0
if {$packet_loss == 0} {
	Netgear_log_file "TC-STK-025.tcl" "******************** TEST CASE TC-STK-025 PASSED ********************"
	set NTGR_TEST_RESULT TRUE
} else {
	Netgear_log_file "TC-STK-025.tcl" "******************** TEST CASE TC-STK-025 FAILED ********************"
	Netgear_log_file "TC-STK-025.tcl" "         Failed Reason : Frame_loss = $packet_loss"
	Netgear_log_file "TC-STK-025.tcl" "*********************************************************************"
	set NTGR_TEST_RESULT FALSE
}

Netgear_log_file "TC-STK-025.tcl" "******************** Completed Test case TC-STK-025.tcl ********************"

#*************************  End of Test case  ****************************************************************