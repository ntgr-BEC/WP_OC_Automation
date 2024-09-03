################################################################################
#
#  File Name	    	: TC-RIP-ModifyDefaultMetric-007.tcl
#
#  Description     	: This script modify default metric for RIP
#                     
#
#  Config file       : TC-RIP-ModifyDefaultMetric-007.tcl
#
#  Global Variables	:  ntgr_RipList(defined in CFG file)
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        18-9-06       Nina Cheng          Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-RIP-ModifyDefaultMetric-007.tcl" "******** Starting Test case TC-RIP-ModifyDefaultMetric-007.tcl *********"

foreach switch_name $ntgr_Rip_DM_List {
    
    Netgear_connect_switch $switch_name
    	
    Netgear_log_file "TC-RIP-ModifyDefaultMetric-007.tcl" "Configure RIP default metric on switch $switch_name"
    set metric  [getRipDefaultMetric $switch_name]
    CALRipEnableDefaultMetric  $switch_name $metric

    Netgear_disconnect_switch $switch_name
}

#************************  End of Test case  **********************************