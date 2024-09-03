###################################################################################################################
#
#  File Name	    	: TC-VRRP-034.tcl
#
#  Description     	:
#         This TCL tests configures port channel on switches.
#
#  Config file       : TC-VRRP-034.cfg
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

Netgear_log_file "TC-VRRP-034.tcl" "******** Starting Test case TC-VRRP-034.tcl **********"

foreach switch_name $SW_LIST {
    Netgear_log_file "TC-VRRP-034.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}

Netgear_log_file "TC-VRRP-034.tcl" "Started to modify TOPO"

foreach {switch_name portlist} [array get ntgr_swDownPortList_034] {
    Netgear_log_file "TC-VRRP-034.tcl" "Setting ports' status to down on switch $switch_name"
    Netgear_connect_switch $switch_name
    foreach port $portlist {
        CALPortLinkDown $switch_name $port
    }
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-034.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list {
	Netgear_log_file "TC-VRRP-034.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-VRRP-034.tcl" "Started routing on the Switches"

foreach switch_name $SW_LIST {
    Netgear_connect_switch $switch_name 
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-034.tcl" "Started Configuration of VRRP"

foreach switch_name $switch_VRRP_list {
   
   set vrid_list [getVrrpVID $switch_name]	
	 set pri_list  [getVrrpPriority $switch_name]
	 set interface_list [getVrrpInterface $switch_name]
   set vip_list  [getVrrpVIP $switch_name]
   
   Netgear_connect_switch $switch_name 
      
   Netgear_log_file "TC-VRRP-034.tcl" "Started enable VRRP on switch $switch_name"
   CALVRRPEnable $switch_name
   
   Netgear_log_file "TC-VRRP-0034.tcl" "Started Configuration vrid on switch $switch_name"
   CALAddVRRPVrid $switch_name $interface_list $vrid_list
   
   Netgear_log_file "TC-VRRP-034.tcl" "Started Configuration vrip on switch $switch_name"
   CALAddVRRPVip $switch_name $interface_list $vrid_list $vip_list  
   
   Netgear_log_file "TC-VRRP-034.tcl" "Started Configuration Priority on switch $switch_name"
   CALAddVrrpPriority $switch_name $interface_list $vrid_list $pri_list
   
   Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-034.tcl" "Started Configuration of STP"

foreach switch_name $SW_LIST {
	if {$switch_name != $master_sw} {
	    Netgear_log_file "TC-STP-034.tcl" "Setting the bridge's priority on switch $switch_name"
	    CALStpConfigBridgePriority $switch_name $pri_nroot
	} else {
	    Netgear_log_file "TC-STP-034.tcl" "Configuring VRRP master as ROOT"
      CALStpConfigBridgePriority $master_sw $pri_root
	}
}
    for {set i 1} {$i<=3} {incr i} {
        sleep 10  
        set result [CALCheckExpect B3 $cmds $expect_string_list]	
    }

if {$result == 1} {Netgear_log_file "TC-VRRP-034.tcl" "******************** TC-VRRP-034.tcl passed ********************"} else {
Netgear_log_file "TC-VRRP-034.tcl" "******************** TC-VRRP-034.tcl failed ********************"}

#####################################################################################################################


Netgear_log_file "TC-VRRP-034.tcl" "******************** Completed Test case TC-VRRP-034.tcl ********************"

#**************************************************  End of Test case  *************************************************
