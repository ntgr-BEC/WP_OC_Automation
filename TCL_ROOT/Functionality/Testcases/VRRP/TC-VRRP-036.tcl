###################################################################################################################
#
#  File Name	    	: TC-VRRP-036.tcl
#
#  Description     	:
#         This TCL tests configures port channel on switches.
#
#  Config file       : TC-VRRP-036.cfg
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

Netgear_log_file "TC-VRRP-036.tcl" "******** Starting Test case TC-VRRP-036.tcl **********"

foreach switch_name $SW_LIST {
    Netgear_log_file "TC-VRRP-036.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}

Netgear_log_file "TC-VRRP-036.tcl" "Started Configuration of LAG"

for_array_keys channel_name ntgr_poChanList {
	Netgear_log_file "TC-VRRP-036.tcl" "Configure port channel($channel_name) on each switch(es)."
	CALCreatePortChannel $channel_name
}

Netgear_log_file "TC-VRRP-036.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list {
	Netgear_log_file "TC-VRRP-036.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-VRRP-036.tcl" "Started routing on the Switches"

foreach switch_name $SW_LIST {
    Netgear_connect_switch $switch_name 
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-036.tcl" "Started Configuration of VRRP"

foreach switch_name $switch_VRRP_list {
   
   set vrid_list [getVrrpVID $switch_name]	
	 set pri_list  [getVrrpPriority $switch_name]
	 set interface_list [getVrrpInterface $switch_name]
   set vip_list  [getVrrpVIP $switch_name]
   
   Netgear_connect_switch $switch_name 
      
   Netgear_log_file "TC-VRRP-036.tcl" "Started enable VRRP on switch $switch_name"
   CALVRRPEnable $switch_name
   
   Netgear_log_file "TC-VRRP-0036.tcl" "Started Configuration vrid on switch $switch_name"
   CALAddVRRPVrid $switch_name $interface_list $vrid_list
   
   Netgear_log_file "TC-VRRP-036.tcl" "Started Configuration vrip on switch $switch_name"
   CALAddVRRPVip $switch_name $interface_list $vrid_list $vip_list  
   
   Netgear_log_file "TC-VRRP-036.tcl" "Started Configuration Priority on switch $switch_name"
   CALAddVrrpPriority $switch_name $interface_list $vrid_list $pri_list
   
   Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-036.tcl" "Started Configuration of STP"

foreach switch_name $SW_LIST {
	if {$switch_name != $master_sw} {
	    Netgear_log_file "TC-STP-036.tcl" "Setting the bridge's priority on switch $switch_name"
	    CALStpConfigBridgePriority $switch_name $pri_nroot
	} else {
	    Netgear_log_file "TC-STP-036.tcl" "Configuring VRRP master as ROOT"
      CALStpConfigBridgePriority $switch_name $pri_root
	}
}

	set interface_check [getVrrpInterface $backup_sw]
	set vrid_check [getVrrpVID $backup_sw]
	
	Netgear_connect_switch $backup_sw 
	set LF [CALGetLFofVLAN $backup_sw $interface_check]
	Netgear_disconnect_switch $backup_sw
	
	set cmds "$cmds $LF $vrid_check"
	
	set result [CALCheckExpect $backup_sw $cmds $expect_string_list]	
    
    
if {$result == 1} {Netgear_log_file "TC-VRRP-036.tcl" "******************** TC-VRRP-036.tcl passed ********************"} else {
Netgear_log_file "TC-VRRP-036.tcl" "******************** TC-VRRP-036.tcl failed ********************"}

#####################################################################################################################


Netgear_log_file "TC-VRRP-036.tcl" "******************** Completed Test case TC-VRRP-036.tcl ********************"

#**************************************************  End of Test case  *************************************************
