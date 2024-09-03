################################################################################
#
#  File Name	    	: TC-RIP-ModifyAntiLoop-007.tcl
#
#  Description     	:
#         This TCL tests enbale and disable RIP on switches. 
#	  And configure the RIP parameters on interface according to the configure file.
#
#  Config file       : TC-RIP-ModifyAntiLoop-007.tcl
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

Netgear_log_file "TC-RIP-ModifyAntiLoop-007.tcl" "****** Starting Test case TC-RIP-ModifyAntiLoop-007.tcl *****"

foreach switch_name $ntgr_Rip_HS_List {
    
    Netgear_connect_switch $switch_name
        
    Netgear_log_file "TC-RIP-ModifyAntiLoop-007.tcl" "Configure RIP SplitHorizon on switch $switch_name"	
    set mode [getRipSplitHorizon $switch_name]	
    CALRipEnableSplitHorizon $switch_name $mode
    
    Netgear_disconnect_switch $switch_name
}

#*************************  End of Test case  ************************************