###################################################################################################################
#
#  File Name	    	: TC-VRRP-061.tcl
#
#  Description     	:
#         This TCL tests configures port channel on switches.
#
#  Config file       : TC-VRRP-061.cfg
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

Netgear_log_file "TC-VRRP-061.tcl" "******** Starting Test case TC-VRRP-061.tcl **********"

foreach switch_name $SW_LIST {
    Netgear_log_file "TC-VRRP-061.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}

#Netgear_log_file "TC-VRRP-061.tcl" "Started Configuration of LAG"
#
#for_array_keys channel_name ntgr_poChanList {
#	Netgear_log_file "TC-VRRP-061.tcl" "Configure port channel($channel_name) on each switch(es)."
#	CALCreatePortChannel $channel_name
#}
######################################################################################################################
Netgear_log_file "TC-VRRP-061.tcl" "Started to modify TOPO"

foreach {switch_name portlist} [array get ntgr_swDownPortList_061] {
    Netgear_log_file "TC-VRRP-061.tcl" "Setting ports' status to down on switch $switch_name"
    Netgear_connect_switch $switch_name
    foreach port $portlist {
        CALPortLinkDown $switch_name $port
    }
    Netgear_disconnect_switch $switch_name
}
########################################################################################################################

Netgear_log_file "TC-VRRP-061.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list {
	Netgear_log_file "TC-VRRP-061.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-VRRP-061.tcl" "Started routing on the Switches"

foreach switch_name $SW_L3_LIST {
    Netgear_connect_switch $switch_name 
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-061.tcl" "Started Configuration of VRRP"

foreach switch_name $switch_VRRP_list {
   
   set vrid_list [getVrrpVID $switch_name]	
	 set pri_list  [getVrrpPriority $switch_name]
	 set interface_list [getVrrpInterface $switch_name]
   set vip_list  [getVrrpVIP $switch_name]
   
   Netgear_connect_switch $switch_name 
      
   Netgear_log_file "TC-VRRP-061.tcl" "Started enable VRRP on switch $switch_name"
   CALVRRPEnable $switch_name
   
   Netgear_log_file "TC-VRRP-061.tcl" "Started Configuration vrid on switch $switch_name"
   CALAddVRRPVrid $switch_name $interface_list $vrid_list
   
   Netgear_log_file "TC-VRRP-061.tcl" "Started Configuration vrip on switch $switch_name"
   CALAddVRRPVip $switch_name $interface_list $vrid_list $vip_list  
   
   Netgear_log_file "TC-VRRP-061.tcl" "Started Configuration Priority on switch $switch_name"
   CALAddVrrpPriority $switch_name $interface_list $vrid_list $pri_list
   
   Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-062.tcl" "Started Configuration of STP"

foreach switch_name $SW_LIST {
	if {$switch_name != $master_sw} {
	    if {$switch_name == $SW_NSTP_LIST} {
	        CALStpDisableSwitch $switch_name
	    } else {
	        Netgear_log_file "TC-STP-062.tcl" "Setting the bridge's priority on switch $switch_name"
	        CALStpConfigBridgePriority $switch_name $pri_nroot
	    }
	} else {
	    Netgear_log_file "TC-STP-062.tcl" "Configuring VRRP master as ROOT"
      CALStpConfigBridgePriority $master_sw $pri_root
	}
}

	set interface_check [getVrrpInterface $backup_sw]
	set vrid_check [getVrrpVID $backup_sw]
	
	Netgear_connect_switch $backup_sw 
	set LF [CALGetLFofVLAN $backup_sw $interface_check]
	Netgear_disconnect_switch $backup_sw
	    
	set cmds "$cmds $LF $vrid_check"
	
	set result1 [CALCheckExpect $backup_sw $cmds $expect_string_list]

foreach {switch_name portlist} [array get virtual_link] {
	Netgear_log_file "TC-VRRP-061.tcl" "Setting ports' status to down on switch $switch_name"
	Netgear_connect_switch $switch_name
	foreach port $portlist {
	CALPortLinkDown $switch_name $port
	}
	Netgear_disconnect_switch $switch_name
}

	set result2 [CALCheckExpect $backup_sw $cmds $expect_string_list]

if {$result1 == 1 && $result2 == 1} {
Netgear_log_file "TC-VRRP-061.tcl" "******************** TC-VRRP-061.tcl passed ********************"} else {
Netgear_log_file "TC-VRRP-061.tcl" "******************** TC-VRRP-061.tcl failed ********************"}

Netgear_log_file "TC-VRRP-061.tcl" "******************** Completed Test case TC-VRRP-061.tcl ********************"

#**************************************************  End of Test case  *************************************************
