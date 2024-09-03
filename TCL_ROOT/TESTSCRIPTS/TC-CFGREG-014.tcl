################################################################################
#
#  File Name		: TC-CFGREG-014.tcl
#
#  Description       	: This test script configures Port Channel, Spanning Tree,
#				  VLAN and Static Routes
#
#  Config File          : TC-CFGREG-014.cfg
#
#  Global Variables	: ntgr_poChanList
#                         ntgr_stpList
#				  ntgr_vlanList
#				  ntgr_IPList
#  Revision History 	:
#
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog $NTGR_LOG_TERSE "TC-CFGREG-014.tcl" "******************** Starting Test case TC-CFGREG-014.tcl ********************"
NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Started Port Channel Configuration on the Switches"
for_array_keys channel_name ntgr_poChanList {
	NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Configure port channel($channel_name) on each switch(es)."
	CALCreatePortChannel $channel_name
	set members [getPortChannelMember $channel_name]
	foreach member $members {
		set switch_name [lindex $member 0]
		set portlist [lindex $member 1]
		NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Adding ports $portlist to port channel($channel_name) on switch $switch_name."
		CALAddPortToChannel $switch_name $channel_name $portlist
	}
}

NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Completed Port Channel Configuration on the Switches"


NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Started Spanning Configuration on the Switches"
for_array_keys switch_name ntgr_stpList {
	set priority [getStpBridgePriority $switch_name]
	NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Setting the bridge's priority $priority on switch $switch_name"
	CALStpConfigBridgePriority $switch_name $priority

	set portlist [getStpPortList $switch_name]
	set costlist [getStpPortCostList $switch_name]
	NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Setting switch $switch_name ports $portlist cost as $costlist"
	CALStpConfigCost $switch_name $portlist $costlist
}
NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Completed Spanning Tree Configuration on the Switches"

NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Started VLAN Configuration on the Switches"
for_array_keys vlan_index ntgr_vlanList {
	NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}
NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Completed Spanning Tree Configuration on the Switches"

NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Started Static Route configuration on the Switches"
for_array_keys switch_name ntgr_IPList {
	NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Configure IP Routing on $switch_name"
	CALIpConfigStaticRoute $switch_name
	NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Completed IP Routing configuration on $switch_name"
}
NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Completed IP Routing Configuration on the Switches"

NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Started MTU configuration to carry Jumbo Frames on the Switches"
for_array_keys switch ntgr_switchPortMtu {
	set portList [keylget ntgr_switchPortMtu($switch) PORT_LIST ]
	set mtuList [keylget ntgr_switchPortMtu($switch) MTU_LIST ]
	NtgrDumpLog  $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Changing MTU of the switch $switch port $portList "
	foreach port $portList mtu $mtuList {
		NtgrDumpLog  $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Settomg MTU of the switch $switch port $port as $mtu"
		CALSetMTU $switch $port $mtu
	}
}
NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Completed MTU Configuration on the Switches"

NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "******************** TEST CASE TC-CFGREG-014.tcl PASSED ********************"
NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "******************** Completed Test case TC-CFGREG-014.tcl ********************"

set NTGR_TEST_RESULT TRUE
#*************************  End of Test case  ****************************************************************