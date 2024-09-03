###################################################################################################################
#
#  File Name	    	: TC-VRRP-005.tcl
#
#  Description     	:
#                    authentication test, mode is simple.
#
#  Config file       : TC-VRRP-005.cfg
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

Netgear_log_file "TC-VRRP-005.tcl" "******** Starting Test case TC-VRRP-005.tcl **********"

foreach switch_name $SW_LIST {
    Netgear_log_file "TC-VRRP-005.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}

Netgear_log_file "TC-VRRP-005.tcl" "Started to modify TOPO"

foreach {switch_name portlist} [array get ntgr_swDownPortList_005] {
    Netgear_log_file "TC-VRRP-005.tcl" "Setting ports' status to down on switch $switch_name"
    Netgear_connect_switch $switch_name
    foreach port $portlist {
        CALPortLinkDown $switch_name $port
    }
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-005.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list {
	Netgear_log_file "TC-VRRP-005.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-VRRP-005.tcl" "Started routing on the Switches"

foreach switch_name $SW_LIST {
    Netgear_connect_switch $switch_name 
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-005.tcl" "Started Configuration of VRRP"

foreach switch_name $switch_VRRP_list {
   
   set vrid_list [getVrrpVID $switch_name]	
	 set pri_list  [getVrrpPriority $switch_name]
	 set interface_list [getVrrpInterface $switch_name]
   set vip_list  [getVrrpVIP $switch_name]
   
   Netgear_connect_switch $switch_name 
      
   Netgear_log_file "TC-VRRP-005.tcl" "Started enable VRRP on switch $switch_name"
   CALVRRPEnable $switch_name
   
   Netgear_log_file "TC-VRRP-005.tcl" "Started Configuration vrid on switch $switch_name"
   CALAddVRRPVrid $switch_name $interface_list $vrid_list
   
   Netgear_log_file "TC-VRRP-005.tcl" "Started Configuration vrip on switch $switch_name"
   CALAddVRRPVip $switch_name $interface_list $vrid_list $vip_list  
   
   Netgear_log_file "TC-VRRP-005.tcl" "Started Configuration Priority on switch $switch_name"
   CALAddVrrpPriority $switch_name $interface_list $vrid_list $pri_list
   
   Netgear_disconnect_switch $switch_name
}

	set interface_check [getVrrpInterface $backup_sw]
	set vrid_check [getVrrpVID $backup_sw]
	
	Netgear_connect_switch $backup_sw 
	set LF [CALGetLFofVLAN $backup_sw $interface_check]
	Netgear_disconnect_switch $backup_sw
	
	set cmds1 "$cmds $LF $vrid_check"
	
	set result [CALCheckExpect $backup_sw $cmds1 $expect_string_list]

if {$result == 1} {Netgear_log_file "TC-VRRP-005.tcl" "*******complete the basic configuration********************"} 


###############################################complete the basic configuration#######################################

for_array_keys switch_name ntgr_VrrpList {
   
	set auth_list [getVrrpAuthenticationMode $switch_name] 
	
	Netgear_connect_switch $switch_name 
	
	Netgear_log_file "TC-VRRP-005.tcl" "Started Configuration authentication on switch $switch_name"
	CALVRRPAuthenticationEnable $switch_name $interface_list $vrid_list $auth_list
	
	Netgear_disconnect_switch $switch_name
}  

####################################################check result#####################################################

	set result [CALCheckExpect $backup_sw $cmds1 $expect_string_list]

if {$result == 1} {Netgear_log_file "TC-VRRP-005.tcl" "*******TC-VRRP-005.tcl passed********************"} 

Netgear_log_file "TC-VRRP-005.tcl" "******************** Completed Test case TC-VRRP-005.tcl ********************"

#******************************************************  End of Test case  ***************************************
