################################################################################
#
#  File Name		: TC-REGCFG-001.tcl
#
#  Description       	:
#         This test describes the initial configuration of the 48-Hr regression 
#         test suite
#
#  Config File          : TC-REGCFG-001.cfg
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
puts "hello"
Netgear_log_file "TC-REGCFG-001.tcl" "******************** Starting Test case TC-REGCFG-001.tcl ********************"
Netgear_log_file "TC-REGCFG-001.tcl" "Disabling Spanning Tree for Management Purpose"


Netgear_log_file "TC-REGCFG-001.tcl" "Started Port Channel Configuration on the Switches"
for_array_keys channel_name ntgr_poChanList {
	Netgear_log_file "TC-REGCFG-001.tcl" "Configure port channel($channel_name) on each switch(es)."
	CALCreatePortChannel $channel_name
	set members [getPortChannelMember $channel_name]
	foreach member $members {
		set switch_name [lindex $member 0]
		set portlist [lindex $member 1]
		Netgear_log_file "TC-REGCFG-001.tcl" "Adding ports $portlist to port channel($channel_name) on switch $switch_name."
		CALAddPortToChannel $switch_name $channel_name $portlist
	}
}

Netgear_log_file "TC-REGCFG-001.tcl" "Completed Port Channel Configuration on the Switches"

Netgear_log_file "TC-REGCFG-001.tcl" "Started VLAN Configuration on the Switches"
for_array_keys vlan_index ntgr_vlanList {
	Netgear_log_file "TC-REGCFG-001.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}
Netgear_log_file "TC-REGCFG-001.tcl" "Completed VLAN Configuration on the Switches"

Netgear_log_file "TC-REGCFG-001.tcl" "Started Port Mirror Configuration on the Switches"

for_array_keys switch_name ntgr_portMirrorList {
	Netgear_log_file "TC-REGCFG-001.tcl" "Configure Port Mirror on $switch_name"
	CALPortMirrorConfig $switch_name
	Netgear_log_file "TC-REGCFG-001.tcl" "Completed Port Mirror configuration on $switch_name"
}

Netgear_log_file "TC-REGCFG-001.tcl" "Completed Port Mirror Configuration on the Switches"

Netgear_log_file "TC-REGCFG-001.tcl" "Started Jumbo Frame Configuration on the Switches"

for_array_keys switch_name ntgr_jumboFrameList {
	Netgear_log_file "TC-REGCFG-001.tcl" "Configure Jumbo Frame on $switch_name"
	CALJumboFrameConfig $switch_name
	Netgear_log_file "TC-REGCFG-001.tcl" "Completed Jumbo Frame configuration on $switch_name"
}

Netgear_log_file "TC-REGCFG-001.tcl" "Completed Jumho Frame Configuration on the Switches"

Netgear_log_file "TC-REGCFG-001.tcl" "Started Storm Control Configuration on the Switches"

for_array_keys switch_name ntgr_stormControlList {
	Netgear_log_file "TC-REGCFG-001.tcl" "Configure Port Mirror on $switch_name"
	CALStormControlConfig $switch_name
	Netgear_log_file "TC-REGCFG-001.tcl" "Completed Port Mirror configuration on $switch_name"
}

Netgear_log_file "TC-REGCFG-001.tcl" "Completed Port Mirror Configuration on the Switches"

Netgear_log_file "TC-REGCFG-001.tcl" "Started SNTP Configuration on the Switches"

for_array_keys switch_name ntgr_sntpList {
	Netgear_log_file "TC-REGCFG-001.tcl" "Configure SNTP client on switch $switch_name"
	CALSNTPClientConfig $switch_name
	Netgear_log_file "TC-REGCFG-001.tcl" "Add SNTP server on switch $switch_name"
	CALSNTPAddServer $switch_name
	Netgear_log_file "TC-REGCFG-001.tcl" "Delete SNTP server on switch $switch_name"
	CALSNTPDeleteServer $switch_name
	Netgear_log_file "TC-REGCFG-001.tcl" "Completed SNTP configuration on $switch_name"
}

Netgear_log_file "TC-REGCFG-001.tcl" "Completed SNTP Configuration on the Switches"

Netgear_log_file "TC-REGCFG-001.tcl" "Started IP Configuration on the Switches"

for_array_keys switch_name ntgr_IPList {
	Netgear_log_file "TC-REGCFG-001.tcl" "Configure IP Routing on $switch_name"
	CALIpConfigRouting $switch_name
	Netgear_log_file "TC-REGCFG-001.tcl" "Completed IP Routing configuration on $switch_name"
}

Netgear_log_file "TC-REGCFG-001.tcl" "Completed IP Configuration on the Switches"

Netgear_log_file "TC-REGCFG-001.tcl" "Started RIP Configuration on the Switches"

for_array_keys switch_name ntgr_RipList {

	Netgear_log_file "TC-RIP-007.tcl" "Enable RIP on switch $switch_name"
	set flag_enable [getRipGlobalStatus $switch_name]
	CALRipEnableSwitch $switch_name $flag_enable
		
	Netgear_log_file "TC-RIP-007.tcl" "Enable RIP on logical or physical interface of switch $switch_name"
	set interface_list [getRipInterface $switch_name]
	CALRipEnableInterface $switch_name $interface_list
	
	Netgear_log_file "TC-RIP-002.tcl" "Confiure RIP distance on switch $switch_name"
	set distance [getRipDistance $switch_name]
	CALRipEnableDistance $switch_name $distance
		
	Netgear_log_file "TC-RIP-002.tcl" "Configure RIP default metric on switch $switch_name"
	set metric  [getRipDefaultMetric $switch_name]
	CALRipEnableDefaultMetric  $switch_name $metric
	
	Netgear_log_file "TC-RIP-002.tcl" "Configure RIP autosummary on switch $switch_name"
	set flag_enbale  [getRipAutoSummary $switch_name]
	CALRipEnableAutoSummary $switch_name $flag_enable
	
	Netgear_log_file "TC-RIP-002.tcl" "Configure RIP HostRoutesAccept on switch $switch_name"	
	set flag_enbale  [getRipHostRoutesAccept $switch_name]
	CALRipEnableHostRoutesAccept $switch_name  $flag_enable
		
	Netgear_log_file "TC-RIP-002.tcl" "Configure RIP default information on switch $switch_name"	
	set flag_enbale  [getRipDefaultInformation $switch_name]
	CALRipEnableDefaultinformation $switch_name  $flag_enable
	
	
	Netgear_log_file "TC-RIP-002.tcl" "Configure RIP SplitHorizon on switch $switch_name"	
  	set mode [getRipSplitHorizon $switch_name]	
	CALRipEnableSplitHorizon $switch_name $mode
        
        Netgear_log_file "TC-RIP-002.tcl" "Configure route RIP distributelist on switch $switch_name"	
	set redistribute_list [getRipRedistribute $switch_name]
	CALRipEnableRedistribute $switch_name $redistribute_list

	Netgear_log_file "TC-RIP-002.tcl" "Configure route RIP distributelist on switch $switch_name"
	puts "$switch_name"	
	set distribute_list [getRipDistributeList $switch_name]
	CALRipEnableDistributelist $switch_name $distribute_list
}

Netgear_log_file "TC-REGCFG-001.tcl" "Completed RIP Configuration on the Switches"

set NTGR_TEST_RESULT TRUE
Netgear_log_file "TC-REGCFG-001.tcl" "******************** TEST CASE TC-REGCFG-001 PASSED ********************"
Netgear_log_file "TC-REGCFG-001.tcl" "******************** Completed Test case TC-REGCFG-001.tcl ********************"

#*************************  End of Test case  ****************************************************************