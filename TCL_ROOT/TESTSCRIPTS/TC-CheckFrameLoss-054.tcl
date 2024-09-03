################################################################################
#
#  File Name		: TC-CheckFrameLoss-054.tcl
#
#  Description       	: This test checks the frame loss.
#	
#  Config File          : None
#
#  Global Variables	: ntgr_ForeGroundTrafficPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
set tx 0
set rx 0
foreach {chassis_id portlist} [array get ntgr_ForeGroundTrafficPortList] {
    foreach pt $portlist {
        set TxRx [UALGetPortFrameLoss $chassis_id $pt]
        set  tx [expr $tx + [lindex $TxRx 0]]
        set  rx [expr $rx + [lindex $TxRx 1]]
    }
}

NtgrDumpLog  $NTGR_LOG_TERSE "TC-CheckFrameLoss-054.tcl" "Frame TX : $tx, RX : $rx"

set loss [expr $tx - $rx ]

if { $tx == 0 } { 
	NtgrDumpLog  $NTGR_LOG_ERROR "TC-CheckFrameLoss-054.tcl" "No Packets Transmitted TX is 0"
	return 	
} elseif { $loss > 0 } { 
	set lossPercent [expr ( $tx - $rx ) * 1.0 / $tx * 100  ]
	NtgrDumpLog  $NTGR_LOG_ERROR "TC-CheckFrameLoss-054.tcl" "Frame Loss : $loss Loss% - $lossPercent ********************"
} elseif { $loss < 0 } {
	set excessPercent [expr ( $rx - $tx ) * 1.0 / $tx * 100 ]
	NtgrDumpLog  $NTGR_LOG_ERROR "TC-CheckFrameLoss-054.tcl" "Frame Excess : ExcessPercent% - $excessPercent ********************"
}
#*************************  End of Test case  ****************************************************************
