###################################################################################################################
#
#  File Name	    	: TC-VRRP-014.tcl
#
#  Description     	:
#                    no preempt test  
#
#  Config file       : TC-VRRP-014.cfg
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

Netgear_log_file "TC-VRRP-014.tcl" "******** Starting Test case TC-VRRP-014.tcl **********"

foreach switch_name $SW_LIST {
    Netgear_log_file "TC-VRRP-014.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}

Netgear_log_file "TC-VRRP-014.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list {
	Netgear_log_file "TC-VRRP-014.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-VRRP-014.tcl" "Started routing on the Switches"

foreach switch_name $SW_LIST {
    Netgear_connect_switch $switch_name 
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-014.tcl" "Started Configuration of VRRP"

foreach switch_name $switch_VRRP_list {
   
   set vrid_list [getVrrpVID $switch_name]	
	 set interface_list [getVrrpInterface $switch_name]
   set vip_list  [getVrrpVIP $switch_name]
   
   Netgear_connect_switch $switch_name 
      
   Netgear_log_file "TC-VRRP-014.tcl" "Started enable VRRP on switch $switch_name"
   CALVRRPEnable $switch_name
   
   Netgear_log_file "TC-VRRP-014.tcl" "Started Configuration vrid on switch $switch_name"
   CALAddVRRPVrid $switch_name $interface_list $vrid_list
   
   Netgear_log_file "TC-VRRP-014.tcl" "Started Configuration vrip on switch $switch_name"
   CALAddVRRPVip $switch_name $interface_list $vrid_list $vip_list  
   
   Netgear_disconnect_switch $switch_name
}

	set interface_check [getVrrpInterface $master_sw]
	set vrid_check [getVrrpVID $master_sw]
	
	Netgear_connect_switch $master_sw 
	set LF [CALGetLFofVLAN $master_sw $interface_check]
	Netgear_disconnect_switch $master_sw
	
	set cmds "$cmds $LF $vrid_check"
	set result1 [CALCheckExpect $master_sw $cmds $expect_string_list]

##########################################################################################################

foreach switch_name $switch_VRRP_list {

    set vrid_list [getVrrpVID $switch_name]	
	  set interface_list [getVrrpInterface $switch_name]
    
	  Netgear_connect_switch $switch_name 
	  
    Netgear_log_file "TC-VRRP-014.tcl" "Started Configuration enable preempt on switch $switch_name"
    CALVRRPPreemptDisable $switch_name $interface_list $vrid_list

    Netgear_disconnect_switch $switch_name
}
    
    set interface_list [getVrrpInterface $master_sw]
    set vrid_list [getVrrpVID $master_sw]   
    Netgear_connect_switch $master_sw    
    Netgear_log_file "TC-VRRP-014.tcl" "Started Configuration Priority on switch $switch_name"
    CALAddVrrpPriority $master_sw $interface_list $vrid_list $new_pri  
    Netgear_disconnect_switch $master_sw
      
    set result2 [CALCheckExpect $master_sw $cmds $expect_string_list]


if {$result1 == 1 && $result2 == 1} {Netgear_log_file "TC-VRRP-014.tcl" "*******TC-VRRP-014.tcl passed********************"} else {
Netgear_log_file "TC-VRRP-014.tcl" "*******TC-VRRP-014.tcl failed********************"}

Netgear_log_file "TC-VRRP-014.tcl" "******************** Completed Test case TC-VRRP-014.tcl ********************"

#******************************************************  End of Test case  ***************************************
