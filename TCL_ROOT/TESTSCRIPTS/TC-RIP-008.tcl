################################################################################
#
#  File Name	    	: TC-RIP-008.tcl
#
#  Description     	:This TCL tests clear RIP on switches.
#         
#
#  Config file       : TC-RIP-008.cfg 
#
#  Global Variables	:  ntgr_RipList(defined in CFG file)
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        25-May-06     Nina Cheng          Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-RIP-008.tcl" "******************** Starting Test case TC-RIP-008.tcl ********************"

Netgear_log_file "TC-RIP-008.tcl" "Started RIP Configuration on the Switches"

for_array_keys switch_name ntgr_RipList {

	  Netgear_connect_switch $switch_name
    
    Netgear_log_file "TC-RIP-008.cfg" "Disable autosummary for RIP on switch $switch_name"
    set flag_enable  [getRipAutoSummary $switch_name]
    CALRipDisableAutoSummary $switch_name $flag_enable	

    Netgear_log_file "TC-RIP-008.cfg" "Disable default information for RIP on switch $switch_name"	
    set flag_enable  [getRipDefaultInformation $switch_name]
    CALRipDisableDefaultinformation $switch_name  $flag_enable
    
    Netgear_log_file "TC-RIP-008.cfg" "Disable host route accept for RIP on switch $switch_name"	
    set flag_enable  [getRipHostRoutesAccept $switch_name]
    CALRipDisableHostRoutesAccept $switch_name  $flag_enable
    
    Netgear_log_file "TC-RIP-008.cfg" "Disable default metric for RIP on switch $switch_name"
    set metric  [getRipDefaultMetric $switch_name]
    CALRipDisableDefaultMetric  $switch_name $metric	
    
    Netgear_log_file "TC-RIP-008.cfg" "Disable distance for RIP on switch $switch_name"	
    set distance [getRipDistance $switch_name]
    CALRipDisableDistance $switch_name $distance
    
    Netgear_log_file "TC-RIP-008.cfg" "Disable splithorizon for RIP on switch $switch_name"	
    set mode [getRipSplitHorizon $switch_name]	
    CALRipDisableSplitHorizon $switch_name $mode
    
    Netgear_log_file "TC-RIP-008.cfg" "Disable distribute list for RIP on switch $switch_name"	
    set distribute_list [getRipDistributeListOut $switch_name]
    CALRipDisableDistributelistOut $switch_name $distribute_list
    
    Netgear_log_file "TC-RIP-008.cfg" "Disable reditribute for RIP on switch $switch_name"	
    set redistribute_list [getRipRedistribute $switch_name]
    CALRipDisableRedistributeProtocol $switch_name $redistribute_list    
    CALRipDisableRedistributeMetric $switch_name $redistribute_list
    CALRipDisableRedisOspfMatch $switch_name $redistribute_list
    
    Netgear_log_file "TC-RIP-008.cfg" "Disable RIP on interface"	
    set interface_list [getRipInterface $switch_name]
    CALRipDisableInterface $switch_name $interface_list
    CALRipDeletePropertyforLF $switch_name $interface_list
    
    Netgear_log_file "TC-RIP-008.cfg" "Disable RIP on switch $switch_name"	
    set flag_enable [getRipGlobalStatus $switch_name]
    CALRipDisableSwitch $switch_name $flag_enable
    
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-RIP-008.tcl" "******************** Completed Test case TC-RIP-008.tcl ********************"

#*************************  End of Test case  ***********