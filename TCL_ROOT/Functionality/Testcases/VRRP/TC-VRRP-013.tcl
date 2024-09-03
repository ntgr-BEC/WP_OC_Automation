###################################################################################################################
#
#  File Name	    	: TC-VRRP-013.tcl
#
#  Description     	:
#                    preempt test  
#
#  Config file       : TC-VRRP-013.cfg
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

Netgear_log_file "TC-VRRP-013.tcl" "******** Starting Test case TC-VRRP-013.tcl **********"

foreach switch_name $SW_LIST {
    Netgear_log_file "TC-VRRP-013.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}

Netgear_log_file "TC-VRRP-013.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list {
	Netgear_log_file "TC-VRRP-013.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-VRRP-013.tcl" "Started routing on the Switches"

foreach switch_name $SW_LIST {
    Netgear_connect_switch $switch_name 
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-013.tcl" "Started Configuration of VRRP"

foreach switch_name $switch_VRRP_list {
   
   set vrid_list [getVrrpVID $switch_name]	
	 set interface_list [getVrrpInterface $switch_name]
   set vip_list  [getVrrpVIP $switch_name]
   
   Netgear_connect_switch $switch_name 
      
   Netgear_log_file "TC-VRRP-013.tcl" "Started enable VRRP on switch $switch_name"
   CALVRRPEnable $switch_name
   
   Netgear_log_file "TC-VRRP-0013.tcl" "Started Configuration vrid on switch $switch_name"
   CALAddVRRPVrid $switch_name $interface_list $vrid_list
   
   Netgear_log_file "TC-VRRP-013.tcl" "Started Configuration vrip on switch $switch_name"
   CALAddVRRPVip $switch_name $interface_list $vrid_list $vip_list  
   
   Netgear_disconnect_switch $switch_name
}

	set interface_check [getVrrpInterface $backup_sw]
	set vrid_check [getVrrpVID $backup_sw]
	
	Netgear_connect_switch $backup_sw 
	set LF [CALGetLFofVLAN $backup_sw $interface_check]
	Netgear_disconnect_switch $backup_sw
	
	set cmds "$cmds $LF $vrid_check"
	
	set result1 [CALCheckExpect $backup_sw $cmds $expect_string_list1]

foreach switch_name $switch_VRRP_list {

	set vrid_list [getVrrpVID $switch_name]	
	set interface_list [getVrrpInterface $switch_name]
	set pre_list  [getVrrpPreemptMode $switch_name]
	
	Netgear_connect_switch $switch_name 
	
	Netgear_log_file "TC-VRRP-013.tcl" "Started Configuration enable preempt on switch $switch_name"
	CALVRRPPreemptEnable $switch_name $interface_list $vrid_list $pre_list
	
	Netgear_disconnect_switch $switch_name
}
    
	set interface_list [getVrrpInterface $master_sw]
	set vrid_list [getVrrpVID $master_sw]	
	Netgear_connect_switch $master_sw 	
	Netgear_log_file "TC-VRRP-013.tcl" "Started Configuration Priority on switch $switch_name"
	CALAddVrrpPriority $master_sw $interface_list $vrid_list $new_pri	
	Netgear_disconnect_switch $master_sw
#########################################################################################################	

	set result2 [CALCheckExpect $backup_sw $cmds $expect_string_list2]

if {$result1 == 1 && $result2 == 1} {Netgear_log_file "TC-VRRP-013.tcl" "*******TC-VRRP-013.tcl passed********************"} else {
Netgear_log_file "TC-VRRP-013.tcl" "*******TC-VRRP-013.tcl failed********************"}

Netgear_log_file "TC-VRRP-013.tcl" "******************** Completed Test case TC-VRRP-013.tcl ********************"

#******************************************************  End of Test case  ***************************************
