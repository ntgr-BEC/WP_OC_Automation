################################################################################
#
#  File Name		: TC-REGDELETECFG-001.tcl
#
#  Description       	:
#         This test describes the clearing configuration of the 48-Hr regression 
#         test suite
#
#  Config File          : TC-REGDELETECFG-001.cfg
#
#  Global Variables	: ntgr_poChanList
#                         ntgr_vlanList
#				  ntgr_stormControlList 
#				  ntgr_portMirrorList 
#                         ntgr_jumboFrameList 
#                         ntgr_sntpList 
#                         ntgr_IPList
#                         ntgr_RipList 
#
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V         Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-REGDELETECFG-001.tcl" "******************** Starting Test case TC-REGDELETECFG-001.tcl ********************"

Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing Port Channel Configuration on the Switches"
for_array_keys channel_name ntgr_poChanList {
	set members [getPortChannelMember $channel_name]
	foreach member $members {
		set switch_name [lindex $member 0]
		set portlist [lindex $member 1]
		Netgear_log_file "TC-REGDELETECFG-001.tcl" "Deleting ports $portlist to port channel($channel_name) on switch $switch_name."
		CALDelPortFromChannel $switch_name $channel_name $portlist
	}
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing port channel($channel_name) on each switch(es)."
	CALDeletePortChannel $channel_name
}
Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed Clearing Port Channel Configuration on the Switches"


Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing VLAN Configuration on the Switches"
for_array_keys vlan_index ntgr_vlanList {
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing Configuration of VLAN $vlan_index"
	CALDeleteVlan $vlan_index
}
Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed clearing of VLAN Configuration on the Switches"

Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing Port Mirror Configuration on the Switches"

for_array_keys switch_name ntgr_portMirrorList {
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing Port Mirror on $switch_name"
	CALPortMirrorConfig $switch_name
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed Clearing Port Mirror configuration on $switch_name"
}

Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed Clearing Port Mirror Configuration on the Switches"

Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing Storm Control Configuration on the Switches"

for_array_keys switch_name ntgr_stormControlList {
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing  Storm Control on $switch_name"
	CALStormControlConfig $switch_name
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed Clearing Storm Control configuration on $switch_name"
}

Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed Clearing Storm COntrol  Configuration on the Switches"

Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing Jumbo Frame Configuration on the Switches"

for_array_keys switch_name ntgr_jumboFrameList {
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing Jumbo Frame on $switch_name"
	CALJumboFrameConfig $switch_name
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed Clearing Jumbo Frame configuration on $switch_name"
}

Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed Clearing Jumho Frame Configuration on the Switches"

Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing SNTP Configuration on the Switches"

for_array_keys switch_name ntgr_sntpList {
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing  SNTP client on switch $switch_name"
	CALSNTPClientConfig $switch_name
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Deleting SNTP server on switch $switch_name"
	CALSNTPDeleteServer $switch_name
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed Clearing SNTP configuration on $switch_name"
}

Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed Clearing SNTP Configuration on the Switches"

Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing IP Configuration on the Switches"

for_array_keys switch_name ntgr_IPList {
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing IP Routing on $switch_name"
	CALIpConfigRouting $switch_name
	CALIpConfigStaticRoute $switch_name
	CALIpConfigRoutingPerPort $switch_name
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed Clearing IP Routing configuration on $switch_name"
}

Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed Clearing IP Configuration on the Switches"


Netgear_log_file "TC-REGDELETECFG-001.tcl" "Clearing RIP Configuration on the Switches"

for_array_keys switch_name ntgr_RipList {

	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Disable RIP on switch $switch_name"
	CALRipDisableSwitch $switch_name
		
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Disable RIP on logical or physical interface of switch $switch_name"
	set interface_list [getRipInterface $switch_name]	
	CALRipDisableInterface $switch_name $interface_list
	
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Disable RIP distance on switch $switch_name"
	set distance [getRipDistance $switch_name]
	CALRipDisableDistance $switch_name 
		
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Disable RIP default metric on switch $switch_name"
	CALRipDisableDefaultMetric  $switch_name 
	
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Disable RIP autosummary on switch $switch_name"
	CALRipDisableAutoSummary $switch_name
	
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Disable RIP HostRoutesAccept on switch $switch_name"	
	CALRipDisableHostRoutesAccept $switch_name 
	
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Disable RIP default information on switch $switch_name"	
	CALRipDisableDefaultinformation $switch_name 
	
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Disable RIP SplitHorizon on switch $switch_name"	
	CALRipDisableSplitHorizon $switch_name 
	
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Disable route redistribute into RIP on switch $switch_name"	
	set redis_list [getRipRedistribute $switch_name]
	CALRipDisableRedistribute $switch_name $redis_list 
	
	Netgear_log_file "TC-REGDELETECFG-001.tcl" "Disable route RIP distributelist on switch $switch_name"	
	set distribute_list [getRipDistributeList $switch_name]
	CALRipDisableDistributelist $switch_name $distribute_list
}

Netgear_log_file "TC-REGDELETECFG-001.tcl" "Completed Clearing RIP Configuration on the Switches"

set NTGR_TEST_RESULT TRUE
Netgear_log_file "TC-REGDELETECFG-001.tcl" "******************** TEST CASE TC-REGDELETECFG-001 PASSED ********************"
Netgear_log_file "TC-REGDELETECFG-001.tcl" "******************** Completed Test case TC-REGDELETECFG-001.tcl ********************"

#*************************  End of Test case  ****************************************************************