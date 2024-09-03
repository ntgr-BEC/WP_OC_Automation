###################################################################################################################
#
#  File Name	    	: TC-VRRP-041.tcl
#
#  Description     	:
#         This TCL tests configures port channel on switches.
#
#  Config file       : TC-VRRP-041.cfg
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

Netgear_log_file "TC-VRRP-041.tcl" "******** Starting Test case TC-VRRP-041.tcl **********"

foreach switch_name $SW_LIST {
    Netgear_log_file "TC-VRRP-041.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}

Netgear_log_file "TC-VRRP-041.tcl" "Started to modify TOPO"

foreach {switch_name portlist} [array get ntgr_swDownPortList_041] {
    Netgear_log_file "TC-VRRP-041.tcl" "Setting ports' status to down on switch $switch_name"
    Netgear_connect_switch $switch_name
    foreach port $portlist {
        CALPortLinkDown $switch_name $port
    }
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-041.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list {
	Netgear_log_file "TC-VRRP-041.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-VRRP-041.tcl" "Started routing on the Switches"

foreach switch_name $SW_LIST {
    Netgear_connect_switch $switch_name 
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-041.tcl" "Started Configuration of VRRP"

foreach switch_name $switch_VRRP_list {
   
   set vrid_list [getVrrpVID $switch_name]	
	 set pri_list  [getVrrpPriority $switch_name]
	 set interface_list [getVrrpInterface $switch_name]
   set vip_list  [getVrrpVIP $switch_name]
   
   Netgear_connect_switch $switch_name 
      
   Netgear_log_file "TC-VRRP-041.tcl" "Started enable VRRP on switch $switch_name"
   CALVRRPEnable $switch_name
   
   Netgear_log_file "TC-VRRP-041.tcl" "Started Configuration vrid on switch $switch_name"
   CALAddVRRPVrid $switch_name $interface_list $vrid_list
   
   Netgear_log_file "TC-VRRP-041.tcl" "Started Configuration vrip on switch $switch_name"
   CALAddVRRPVip $switch_name $interface_list $vrid_list $vip_list  
   
   Netgear_log_file "TC-VRRP-041.tcl" "Started Configuration Priority on switch $switch_name"
   CALAddVrrpPriority $switch_name $interface_list $vrid_list $pri_list
   
   Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-041.tcl" "Started Configuration of RIP"

foreach switch_name $switch_RIP_list {
    
    Netgear_connect_switch $switch_name 
    
    Netgear_log_file "TC-VRRP-041.tcl" "Enable RIP on switch $switch_name"
    set flag_enable [getRipGlobalStatus $switch_name]
    CALRipEnableSwitch $switch_name $flag_enable
    	
    Netgear_log_file "TC-VRRP-041.tcl" "Enable RIP on logical or physical interface of switch $switch_name"
    set interface_list [getRipInterface $switch_name]
    CALRipEnableInterface $switch_name $interface_list
         
    Netgear_log_file "TC-VRRP-041.tcl" "Configure route RIP distributelist on switch $switch_name"	
    set redistribute_list [getRipRedistribute $switch_name]
    CALRipEnableRedistribute $switch_name $redistribute_list
    
    Netgear_disconnect_switch $switch_name   
}

Netgear_log_file "TC-VRRP-041.tcl" "Started Configuration of STP"

foreach switch_name $SW_LIST {
	if {$switch_name != $master_sw} {
	    Netgear_log_file "TC-STP-041.tcl" "Setting the bridge's priority on switch $switch_name"
	    CALStpConfigBridgePriority $switch_name $pri_nroot
	} else {
	    Netgear_log_file "TC-STP-041.tcl" "Configuring VRRP master as ROOT"
      CALStpConfigBridgePriority $master_sw $pri_root
	}
}
  
Netgear_log_file "TC-VRRP-041.tcl" "Started Configuration of TRAFFIC"

for_array_keys chassis_id ntgr_trafficTestInfo {
	set duration [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-041.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
}

for_array_keys chassis_id ntgr_trafficTestInfo {

	set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-041.tcl" "Loading the configuration on ports"
	foreach port $port_list {
	UALLoadPort $chassis_id $port
	}
}

for_array_keys chassis_id ntgr_trafficTestInfo {

	set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-041.tcl" "Starting traffic on ports"
	foreach port $port_list {
	UALStartTrafficPerPort $chassis_id $port
	}
}

##############################################check the receive rate of receive port#################################

for_array_keys chassis_id ntgr_trafficTestInfo {
   
    set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
    NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-041.tcl" "checking the rate on ports"
    #for { set i 1} {$i <=3} {incr i} {
        sleep 10
        set ratelist ""
        foreach port $port_list {
            lappend ratelist [_spirentReportRatePerPort $chassis_id $port]
        }
    #}
}
  set first_value [lindex $ratelist 0]
  set second_value [lindex $ratelist 1]
  set tr_tp [lindex $first_value 0]
	set rr_tp [lindex $first_value 1]
	set tr_rp [lindex $second_value 0]
	set rr_rp [lindex $second_value 1]
	
	set loss [expr $tr_tp - $rr_rp]

	if {$loss > 5} {
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-041.tcl" "Receive rate is not equal to the send rate."   
	#UALDisconnectTester $chassis_id
	#break
	}

  UALClearCounter $chassis_id
#######################################################################################################################

#Netgear_log_file "TC-VRRP-041.tcl" "Changing STP ROOT to VRRP backup"
#
#  Netgear_log_file "TC-STP-041.tcl" "Configuring VRRP master as non-ROOT"
#  CALStpConfigBridgePriority $master_sw $pri_nroot
#  
#  Netgear_log_file "TC-STP-041.tcl" "Configuring VRRP backup as ROOT"
#  CALStpConfigBridgePriority $backup_sw $pri_root
#
########################################################################################################################
#
#Netgear_log_file "TC-VRRP-041.tcl" "Changing backup as master for VRRP"
#   
#foreach switch_name $backup_sw {
#   
#   set vrid_list [getVrrpVID $switch_name]	
#	 set interface_list [getVrrpInterface $switch_name]
#   set vip_list  [getVrrpVIP $switch_name]
#   
#   Netgear_connect_switch $switch_name 
#
#   Netgear_log_file "TC-VRRP-041.tcl" "Started Configuration Priority on switch $switch_name"
#   CALAddVrrpPriority $switch_name $interface_list $vrid_list $pri_master
#   
#   Netgear_disconnect_switch $switch_name
#}
######################################################################################################################
    CALSaveConfig $master_sw
    CALRebootSwitch $master_sw
    
###############################################check the receive rate of receive port#################################
for_array_keys chassis_id ntgr_trafficTestInfo {
   
    set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
    NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-041.tcl" "checking the rate on ports"
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
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-041.tcl" "Receive rate is not equal to the send rate after master-slave switching."   

	set result 0 
	} else {set result 1}
	
#################################################get the port counter###################################################

  for_array_keys chassis_id ntgr_trafficTestInfo {
      set counterlist "" 
      set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
      NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-041.tcl" "get the packets on ports"
      foreach port $port_list {
          lappend counterlist [UALGetPortFrameLoss $chassis_id $port]
      }
}

    set first_counter [lindex $counterlist 0]
    set second_counter [lindex $counterlist 1]
    set tc_tp [lindex $first_counter 0]
	  set rc_tp [lindex $first_counter 1]
	  set tc_rp [lindex $second_counter 0]
	  set rc_rp [lindex $second_counter 1]
	  
	  set loss_packets [expr $tc_tp - $rc_rp]
	  
	  set time_convergence [expr $loss_packets/$tr_tp]
	  
	  UALDisconnectTester $chassis_id
		
    Netgear_log_file "TC-VRRP-041.tcl" "******************** VRRP convergence time is $time_convergence ********************"

Netgear_log_file "TC-VRRP-041.tcl" "******************** Completed Test case TC-VRRP-041.tcl ********************"

#**************************************************  End of Test case  *************************************************
