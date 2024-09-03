###################################################################################################################
#
#  File Name	    	: TC-VRRP-048.tcl
#
#  Description     	:
#         This TCL tests configures port channel on switches.
#
#  Config file       : TC-VRRP-048.cfg
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

Netgear_log_file "TC-VRRP-048.tcl" "******** Starting Test case TC-VRRP-048.tcl **********"

foreach switch_name $switch_VRRP_list {
    Netgear_log_file "TC-VRRP-048.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}

Netgear_log_file "TC-VRRP-048.tcl" "Started Configuration of VRRP"

foreach switch_name $switch_VRRP_list {

    set vrid_list [getVrrpVID $switch_name]	
	  set interface_list [getVrrpInterface $switch_name]
    set auth_list [getVrrpAuthenticationMode $switch_name] 
    
   Netgear_connect_switch $switch_name 
      
   Netgear_log_file "TC-VRRP-048.tcl" "Started enable VRRP on switch $switch_name"
   CALVRRPEnable $switch_name
   
   Netgear_log_file "TC-VRRP-048.tcl" "Started Configuration vrid on switch $switch_name"
   CALAddVRRPVrid $switch_name $interface_list $vrid_list
   
   Netgear_log_file "TC-VRRP-028.tcl" "Started Configuration authentication on switch $switch_name"
   CALVRRPAuthenticationEnable $switch_name $interface_list $vrid_list $auth_list
   
   Netgear_disconnect_switch $switch_name       
  
}
   set result [CALCheckExpect $switch_name $cmds $expect_string_list 0]

   if {$result == 1} {
           Netgear_log_file "TC-VRRP-048.tcl" "*******TC-VRRP-048.tcl passed********************"} else {
           Netgear_log_file "TC-VRRP-048.tcl" "*******TC-VRRP-048.tcl failed********************"
   }   



#####################################################################################################################


Netgear_log_file "TC-VRRP-048.tcl" "******************** Completed Test case TC-VRRP-048.tcl ********************"

#**************************************************  End of Test case  *************************************************
