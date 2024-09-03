###################################################################################################################
#
#  File Name	    	: TC-VRRP-018.tcl
#
#  Description     	:
#                    vrrp enable,save and reboot
#
#  Config file       : TC-VRRP-018.cfg
#
#  Global Variables	: ntgr_poChanList (defined in CFG file)
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        08-11-06      Nina Cheng          Created
#
#
#
#
###################################################################################################################

#############################################start to configure the TOPO###########################################

Netgear_log_file "TC-VRRP-018.tcl" "******** Starting Test case TC-VRRP-018.tcl **********"

foreach switch_name $switch_VRRP_list {
    Netgear_log_file "TC-VRRP-018.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}

Netgear_log_file "TC-VRRP-018.tcl" "Started Configuration of VRRP"

foreach switch_name $switch_VRRP_list {

   Netgear_connect_switch $switch_name 
      
   Netgear_log_file "TC-VRRP-018.tcl" "Started enable VRRP on switch $switch_name"
   CALVRRPEnable $switch_name
   
   Netgear_disconnect_switch $switch_name
   
   CALSaveConfig $switch_name
   
   CALRebootSwitch $switch_name
   
   set result [CALCheckExpect $switch_name $cmds $expect_string_list]
   
   if {$result == 1} {
       Netgear_log_file "TC-VRRP-018.tcl" "*******TC-VRRP-018.tcl passed********************"} else {
       Netgear_log_file "TC-VRRP-018.tcl" "*******TC-VRRP-018.tcl failed********************"} 
}
##################################################################################################################


Netgear_log_file "TC-VRRP-018.tcl" "******************** Completed Test case TC-VRRP-018.tcl ********************"

#**************************************************  End of Test case  *************************************************
