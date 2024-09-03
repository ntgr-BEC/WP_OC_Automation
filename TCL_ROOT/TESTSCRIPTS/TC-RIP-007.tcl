################################################################################
#
#  File Name	    	: TC-RIP-007.tcl
#
#  Description     	:
#         This TCL tests enbale and disable RIP on switches. 
#	  And configure the RIP parameters on interface according to the configure file.
#
#  Config file       : TC-RIP-007.cfg 
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

Netgear_log_file "TC-RIP-007.tcl" "******************** Starting Test case TC-RIP-007.tcl ********************"

Netgear_log_file "TC-RIP-007.tcl" "Started RIP Configuration on the Switches"

for_array_keys switch_name ntgr_RipList {
    
    Netgear_connect_switch $switch_name
    
    Netgear_log_file "TC-RIP-007.tcl" "Enable RIP on switch $switch_name"
    set flag_enable [getRipGlobalStatus $switch_name]
    CALRipEnableSwitch $switch_name $flag_enable
    	
    Netgear_log_file "TC-RIP-007.tcl" "Enable RIP on logical or physical interface of switch $switch_name"
    set interface_list [getRipInterface $switch_name]
    CALRipEnableInterface $switch_name $interface_list
    CALRipAddPropertyforLF $switch_name $interface_list
    
    Netgear_log_file "TC-RIP-002.tcl" "Confiure RIP distance on switch $switch_name"
    set distance [getRipDistance $switch_name]
    CALRipEnableDistance $switch_name $distance
    	
    Netgear_log_file "TC-RIP-002.tcl" "Configure RIP default metric on switch $switch_name"
    set metric  [getRipDefaultMetric $switch_name]
    CALRipEnableDefaultMetric  $switch_name $metric
    
    Netgear_log_file "TC-RIP-002.tcl" "Configure RIP autosummary on switch $switch_name"
    set flag_enable  [getRipAutoSummary $switch_name]
    CALRipEnableAutoSummary $switch_name $flag_enable
    
    Netgear_log_file "TC-RIP-002.tcl" "Configure RIP HostRoutesAccept on switch $switch_name"	
    set flag_enable  [getRipHostRoutesAccept $switch_name]
    CALRipEnableHostRoutesAccept $switch_name  $flag_enable
    	
    Netgear_log_file "TC-RIP-002.tcl" "Configure RIP default information on switch $switch_name"	
    set flag_enable  [getRipDefaultInformation $switch_name]
    CALRipEnableDefaultinformation $switch_name  $flag_enable
    
    
    Netgear_log_file "TC-RIP-002.tcl" "Configure RIP SplitHorizon on switch $switch_name"	
    set mode [getRipSplitHorizon $switch_name]	
    CALRipEnableSplitHorizon $switch_name $mode
         
    Netgear_log_file "TC-RIP-002.tcl" "Configure route RIP distributelist on switch $switch_name"	
    set redistribute_list [getRipRedistribute $switch_name]
    CALRipEnableRedistribute $switch_name $redistribute_list
    
    Netgear_log_file "TC-RIP-002.tcl" "Configure route RIP distributelist on switch $switch_name"
    set distribute_list [getRipDistributeListOut $switch_name]
    CALRipEnableDistributelistOut $switch_name $distribute_list
       
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-RIP-007.tcl" "******************** Completed Test case TC-RIP-007.tcl ********************"

#*************************  End of Test case  ***********
