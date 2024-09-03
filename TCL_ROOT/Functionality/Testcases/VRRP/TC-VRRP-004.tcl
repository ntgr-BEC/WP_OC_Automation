###################################################################################################################
#
#  File Name	    	: TC-VRRP-004.tcl
#
#  Description     	:
#                    30 VRRP group on 1 vlan forwarding test
#
#  Config file       : TC-VRRP-004.cfg
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

##############################################start to configure the TOPO###########################################

Netgear_log_file "TC-VRRP-004.tcl" "******** Starting Test case TC-VRRP-004.tcl **********"

foreach switch_name $SW_LIST {
    Netgear_log_file "TC-VRRP-004.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}

#Netgear_log_file "TC-VRRP-004.tcl" "Started Configuration of LAG"
#
#for_array_keys channel_name ntgr_poChanList {
#	Netgear_log_file "TC-VRRP-004.tcl" "Configure port channel($channel_name) on each switch(es)."
#	CALCreatePortChannel $channel_name
#}
#############################################################################
Netgear_log_file "TC-VRRP-004.tcl" "Started to modify TOPO"

foreach {switch_name portlist} [array get ntgr_swDownPortList_004] {
	Netgear_log_file "TC-VRRP-004.tcl" "Setting ports' status to down on switch $switch_name"
	Netgear_connect_switch $switch_name
	foreach port $portlist {
		CALPortLinkDown $switch_name $port
	}
	Netgear_disconnect_switch $switch_name
}
###############################################################################

Netgear_log_file "TC-VRRP-004.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list {
	Netgear_log_file "TC-VRRP-004.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-VRRP-004.tcl" "Started routing on the Switches"

foreach switch_name $SW_LIST {
	Netgear_connect_switch $switch_name 
	CALRoutingEnable $switch_name
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-004.tcl" "Started Configuration of VRRP"

foreach switch_name $VRRP_SW {
   
	Netgear_connect_switch $switch_name 
	
	set LF [CALGetLFofVLAN $switch_name $VRRP_INTERFACE]
	
	Netgear_log_file "TC-VRRP-004.tcl" "Started enable VRRP on switch $switch_name"
	CALVRRPEnable $switch_name
	
	set num_total [expr $VRRP_GROUPID_FIRST + $VRRP_GROUP_NUM - 1]
	
	for {set i $VRRP_GROUPID_FIRST} {$i <=$num_total} {incr i} {
		set vip ""
		lappend vip $VRRP_VIP_START$i
		CALAddVRRPVrid $switch_name $LF $i
		CALAddVRRPVip $switch_name $LF $i $vip  
		if {$switch_name == $master_sw} {
	   		CALAddVrrpPriority $switch_name $LF $i $pri_master
		}
		
	}
	
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-004.tcl" "Started Configuration of RIP"

foreach switch_name $switch_RIP_list {
    
	Netgear_connect_switch $switch_name 
	Netgear_log_file "TC-VRRP-004.tcl" "Enable RIP on switch $switch_name"
	set flag_enable [getRipGlobalStatus $switch_name]
	CALRipEnableSwitch $switch_name $flag_enable
	    
	Netgear_log_file "TC-VRRP-004.tcl" "Enable RIP on logical or physical interface of switch $switch_name"
	set interface_list [getRipInterface $switch_name]
	CALRipEnableInterface $switch_name $interface_list
	Netgear_log_file "TC-VRRP-004.tcl" "Configure route RIP distributelist on switch $switch_name"	
	set redistribute_list [getRipRedistribute $switch_name]
	CALRipEnableRedistribute $switch_name $redistribute_list
	
	Netgear_disconnect_switch $switch_name   
}

Netgear_log_file "TC-VRRP-004.tcl" "Started Configuration of STP"

foreach switch_name $SW_LIST {
	if {$switch_name != $master_sw} {
		Netgear_log_file "TC-STP-004.tcl" "Setting the bridge's priority on switch $switch_name"
		CALStpConfigBridgePriority $switch_name $pri_nroot
	} else {
		Netgear_log_file "TC-STP-004.tcl" "Configuring VRRP master as ROOT"
		CALStpConfigBridgePriority $master_sw $pri_root
	}
}
  
Netgear_log_file "TC-VRRP-004.tcl" "Started Configuration of TRAFFIC"

for_array_keys chassis_id ntgr_trafficTestInfo {
	set duration [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-004.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
}

for_array_keys chassis_id ntgr_trafficTestInfo {

	set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-004.tcl" "Loading the configuration on ports"
	foreach port $port_list {
	UALLoadPort $chassis_id $port
	}
}

for_array_keys chassis_id ntgr_trafficTestInfo {

	set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-004.tcl" "Starting traffic on ports"
	foreach port $port_list {
		UALStartTrafficPerPort $chassis_id $port
	}
}

###############################################complete the basic configuration#######################################

##############################################check the receive rate of receive port#################################

for_array_keys chassis_id ntgr_trafficTestInfo {
   
	set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-004.tcl" "checking the rate on ports"

	set ratelist ""
	foreach port $port_list {
	    lappend ratelist [_spirentReportRatePerPort $chassis_id $port]
	}

}
	set first_value [lindex $ratelist 0]
	set second_value [lindex $ratelist 1]
	set tr_tp [lindex $first_value 0]
	set rr_tp [lindex $first_value 1]
	set tr_rp [lindex $second_value 0]
	set rr_rp [lindex $second_value 1]
	
	set loss [expr $tr_tp - $rr_rp]

	if {$loss > 5} {
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-004.tcl" "Receive rate is not equal to the send rate."   
	}

########################################################################################################################
##
##       CALSaveConfig $master_sw
##       CALRebootSwitch $master_sw
##
##sleep 20
##
#####################################Change VRRP master ###############################################################
Netgear_log_file "TC-VRRP-004.tcl" "Started to modify VRRP master"
Netgear_connect_switch $backup_sw 

set LF [CALGetLFofVLAN $backup_sw $VRRP_INTERFACE]   
set num_total [expr $VRRP_GROUPID_FIRST + $VRRP_GROUP_NUM - 1]

for {set i $VRRP_GROUPID_FIRST} {$i <=$num_total} {incr i} {
	CALAddVrrpPriority $backup_sw $LF $i $new_pri
}

Netgear_disconnect_switch $backup_sw
   
###################################Change STP ROOT#####################################################################

Netgear_log_file "TC-VRRP-004.tcl" "Started to modify STP root"

Netgear_log_file "TC-STP-004.tcl" "Configuring VRRP master as non-ROOT"
CALStpConfigBridgePriority $master_sw $pri_nroot

Netgear_log_file "TC-STP-004.tcl" "Configuring VRRP backup as ROOT"
CALStpConfigBridgePriority $backup_sw $pri_root

sleep 10   
###############################################check the receive rate of receive port#################################

for_array_keys chassis_id ntgr_trafficTestInfo {
   
	set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-004.tcl" "checking the rate on ports"
	for { set i 1} {$i <=3} {incr i} {
		sleep 10
		set ratelist ""
		foreach port $port_list {
			lappend ratelist [_spirentReportRatePerPort $chassis_id $port]
		}
	}
}
	set first_value [lindex $ratelist 0]
	set second_value [lindex $ratelist 1]
	set tr_tp [lindex $first_value 0]
	set rr_tp [lindex $first_value 1]
	set tr_rp [lindex $second_value 0]
	set rr_rp [lindex $second_value 1]
	
	set loss [expr $tr_tp - $rr_rp]
	if {$loss > 5} {
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-004.tcl" "Receive rate is not equal to the send rate after master-slave switching."   
	
	set result 0 
	} else {set result 1}
	
	UALDisconnectTester $chassis_id
	
if {$result == 1} {Netgear_log_file "TC-VRRP-004.tcl" "******************** TC-VRRP-004.tcl passed ********************"} else {
Netgear_log_file "TC-VRRP-004.tcl" "******************** TC-VRRP-004.tcl failed ********************"}
#####################################################################################################################


Netgear_log_file "TC-VRRP-004.tcl" "******************** Completed Test case TC-VRRP-004.tcl ********************"

#**************************************************  End of Test case  *************************************************
