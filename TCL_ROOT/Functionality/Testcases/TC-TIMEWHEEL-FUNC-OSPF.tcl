####################################################################################
#
#  File Name		: TC-TIMEWHEEL-FUNC-OSPF.tcl
#
#  Description       	: This file defines the test script of time wheel. 
#                         It executes multiple scripts peridically.
#
#  Test case Name 	: TC-TIMEWHEEL-FUNC-OSPF.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#####################################################################################

NtgrDumpLog $NTGR_LOG_TERSE  "TC-TIMEWHEEL-FUNC-OSPF.tcl" "******************** Starting Test case TC-TIMEWHEEL-FUNC-OSPF.tcl ********************"
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TIMEWHEEL-FUNC-OSPF.tcl" "******************** Starting Test case TC-TIMEWHEEL-FUNC-OSPF.tcl ********************"
set cycleInterval [getTimeWheelCycleInterval]
set totalNoOfCycle [getTimeWheelTotalNoOfCycle ]
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TIMEWHEEL-FUNC-OSPF.tcl" "Cycle Interval : $cycleInterval"
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TIMEWHEEL-FUNC-OSPF.tcl" "Total Number of Cycles : $totalNoOfCycle"

set cycle 1
set clockNextCycle [expr  [clock seconds ] + $cycleInterval ]
while { $cycle <= $totalNoOfCycle  || $totalNoOfCycle == 0 } { 
	NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TIMEWHEEL-FUNC-OSPF.tcl" "Started Cycle : $cycle"
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
			NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TIMEWHEEL-FUNC-OSPF.tcl" "Executing script $scriptName with Config File $cfgFileName"
			if { $cfgFileName != {} } {
			    source $NTGR_FUNC_CFG/$NTGR_TEST_ENVIRONMENT/$cfgFileName
			}
			source $NTGR_FUNC_TC/$scriptName
		} 
	}
	
	while { [clock seconds ] <= $clockNextCycle } { 
		sleep 1
	}
	incr clockNextCycle 10
	#sleep [expr $cycleInterval]	
	incr cycle 
}

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TIMEWHEEL-FUNC-OSPF.tcl" "Completed Test Script TC-TIMEWHEEL-FUNC-OSPF.tcl"
