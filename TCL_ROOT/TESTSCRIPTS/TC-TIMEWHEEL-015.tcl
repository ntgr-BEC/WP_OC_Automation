####################################################################################
#
#  File Name		: TC-TIMEWHEEL-015.tcl
#
#  Description       	: This file defines the test script of time wheel. 
#                         It executes multiple scripts peridically.
#
#  Test case Name 	: TC-TIMEWHEEL-015.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#####################################################################################

NtgrDumpLog $NTGR_LOG_TERSE  "TC-TIMEWHEEL-015.tcl" "******************** Starting Test case TC-TIMEWHEEL-015.tcl ********************"
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TIMEWHEEL-015.tcl" "******************** Starting Test case TC-TIMEWHEEL-015.tcl ********************"
set cycleInterval [getTimeWheelCycleInterval]
set totalNoOfCycle [getTimeWheelTotalNoOfCycle ]
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TIMEWHEEL-015.tcl" "Cycle Interval : $cycleInterval"
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TIMEWHEEL-015.tcl" "Total Number of Cycles : $totalNoOfCycle"

set cycle 1
set clockNextCycle [expr  [clock seconds ] + $cycleInterval ]
while { $cycle <= $totalNoOfCycle  || $totalNoOfCycle == 0 } { 
	NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TIMEWHEEL-015.tcl" "Started Cycle : $cycle"
	# When using for_array_keys, script ids comes randomly, it is not coming in the order it is crated.
	# Following steps to get script ids in the order it has been added to the configuration file  
	set scriptIds [lsort -dictionary [array names ntgrTWScriptInfo ] ]
	foreach scriptId $scriptIds {
		set scrStartCycle [getTimeWheelScriptStartCycle $scriptId ]
		set scrStopCycle [getTimeWheelScriptStopCycle $scriptId ]
		set scrRepeatInterval [getTimeWheelScriptRepeatInterval $scriptId ]
		set scriptName [getTimeWheelScriptName $scriptId ]
		set cfgFileName [getTimeWheelScriptCfg $scriptId ]
		if { $cycle >= $scrStartCycle && ( $cycle <= $scrStopCycle || $scrStopCycle == 0 ) && \
		   [expr ( $cycle - $scrStartCycle ) % $scrRepeatInterval ] == 0  } {
			set scriptName [getTimeWheelScriptName $scriptId ]
			set cfgFileName [getTimeWheelScriptCfg $scriptId ]
			NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TIMEWHEEL-015.tcl" "Executing script $scriptName with Config File $cfgFileName"
			if { $cfgFileName != {} } {
			    source $NTGR_CONFIG_PATH/$NTGR_TEST_ENVIRONMENT/$cfgFileName
			}
			source $NTGR_TEST_SCRIPT_PATH/$scriptName
		} 
	}
	
	while { [clock seconds ] <= $clockNextCycle } { 
		sleep 1
	}
	incr clockNextCycle 10
	#sleep [expr $cycleInterval]	
	incr cycle 
}

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TIMEWHEEL-015.tcl" "Completed Test Script TC-TIMEWHEEL-015.tcl"
