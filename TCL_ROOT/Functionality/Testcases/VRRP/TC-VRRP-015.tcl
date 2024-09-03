###################################################################################################################
#
#  File Name	    	: TC-VRRP-015.tcl
#
#  Description     	:
#         This TCL tests configures port channel on switches.
#
#  Config file       : TC-VRRP-015.cfg
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

Netgear_log_file "TC-VRRP-015.tcl" "******** Starting Test case TC-VRRP-015.tcl **********"

foreach switch_name $SW_LIST {
    Netgear_log_file "TC-VRRP-015.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}
#################################################################################################
#Netgear_log_file "TC-VRRP-003.tcl" "Started Configuration of LAG"
#
#for_array_keys channel_name ntgr_poChanList {
#	Netgear_log_file "TC-VRRP-003.tcl" "Configure port channel($channel_name) on each switch(es)."
#	CALCreatePortChannel $channel_name
#}
#################################################################################################
Netgear_log_file "TC-VRRP-002.tcl" "Started to modify TOPO"

foreach {switch_name portlist} [array get ntgr_swDownPortList_015] {
    Netgear_log_file "TC-VRRP-002.tcl" "Setting ports' status to down on switch $switch_name"
    Netgear_connect_switch $switch_name
    foreach port $portlist {
        CALPortLinkDown $switch_name $port
    }
    Netgear_disconnect_switch $switch_name
}
##################################################################################################

Netgear_log_file "TC-VRRP-015.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list {
	Netgear_log_file "TC-VRRP-015.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-VRRP-015.tcl" "Started routing on the Switches"

foreach switch_name $SW_LIST {
    Netgear_connect_switch $switch_name 
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}
##################################################################################################
Netgear_log_file "TC-VRRP-015.tcl" "Started Configuration of VRRP"

foreach switch_name $VRRP_SW {
   
   Netgear_connect_switch $switch_name 
   
   set LF [CALGetLFofVLAN $switch_name $VRRP_INTERFACE]

   set interface_first [lindex $LF 0]
   set interface_second [lindex $LF 1]
   
   set vip_first [lindex $VRRP_VIP_START 0]
   set vip_second [lindex $VRRP_VIP_START 1]

   Netgear_log_file "TC-VRRP-015.tcl" "Started enable VRRP on switch $switch_name"
   CALVRRPEnable $switch_name
   
   set num_total [expr $VRRP_GROUPID_FIRST + $VRRP_GROUP_NUM - 1]
   
   for {set i $VRRP_GROUPID_FIRST} {$i <=$num_total} {incr i} {
       
       set num_perLF [expr $VRRP_GROUPID_FIRST + $VRRP_NUM_PER_INTER - 1]

       if {$i <= $num_perLF} {
           set interface $interface_first
           set vip ""
           lappend vip $vip_first$i
       } else {
           set interface $interface_second
           set vip ""
           lappend vip $vip_second$i
       }

       CALAddVRRPVrid $switch_name $interface $i

       CALAddVRRPVip $switch_name $interface $i $vip  
       
       if {$switch_name == $master_vlan10 && $i <= $num_perLF} {
           CALAddVrrpPriority $switch_name $interface $i $pri_master
       }
       
       if {$switch_name == $master_vlan20 && $i > $num_perLF} {
           CALAddVrrpPriority $switch_name $interface $i $pri_master
       }
   }
   
   Netgear_disconnect_switch $switch_name
}

############################################################################################
Netgear_log_file "TC-VRRP-015.tcl" "Started Configuration of RIP"

foreach switch_name $switch_RIP_list {
    
    Netgear_connect_switch $switch_name 
    
    Netgear_log_file "TC-VRRP-015.tcl" "Enable RIP on switch $switch_name"
    set flag_enable [getRipGlobalStatus $switch_name]
    CALRipEnableSwitch $switch_name $flag_enable
    	
    Netgear_log_file "TC-VRRP-015.tcl" "Enable RIP on logical or physical interface of switch $switch_name"
    set interface_list [getRipInterface $switch_name]
    CALRipEnableInterface $switch_name $interface_list
         
    Netgear_log_file "TC-VRRP-015.tcl" "Configure route RIP distributelist on switch $switch_name"	
    set redistribute_list [getRipRedistribute $switch_name]
    CALRipEnableRedistribute $switch_name $redistribute_list
    
    Netgear_disconnect_switch $switch_name   
}
#########################################Configure MSTP####################################################
Netgear_log_file "TC-VRRP-015.tcl" "Started Configuration of MSTP"

  set mst_instance_1 [lindex $MSTP_INSTANCE_LIST 0]
  set mst_instance_2 [lindex $MSTP_INSTANCE_LIST 1] 
  
  foreach switch_name $MSTP_REGION {
  
      #Netgear_connect_switch $switch_name
      #set LF [CALGetLFofVLAN $switch_name $MSTP_VLAN_LIST]
      #Netgear_disconnect_switch $switch_name
       
      CALMstpAddInstance $switch_name  $MSTP_INSTANCE_LIST
      CALMstpAddVlan  $switch_name $MSTP_INSTANCE_LIST $MSTP_VLAN_ID_LIST 
      
      if {$switch_name == $master_vlan10} {
          CALStpConfigBridgePriority  $switch_name $pri_root $mst_instance_1
          CALStpConfigBridgePriority  $switch_name $pri_nroot $mst_instance_2
      } elseif {$switch_name == $master_vlan20} {
          CALStpConfigBridgePriority  $switch_name $pri_root $mst_instance_2
          CALStpConfigBridgePriority  $switch_name $pri_nroot $mst_instance_1
      } else {
          CALStpConfigBridgePriority  $switch_name $pri_nroot $mst_instance_1
          CALStpConfigBridgePriority  $switch_name $pri_nroot $mst_instance_2
      }
      
      CALCheckExpect $switch_name $cmds $expect_string_list 
}

############################################################################################################

Netgear_log_file "TC-VRRP-015.tcl" "Started Configuration of TRAFFIC"

for_array_keys chassis_id ntgr_trafficTestInfo {
	set duration [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-015.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
}

for_array_keys chassis_id ntgr_trafficTestInfo {

	set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-015.tcl" "Loading the configuration on ports"
	foreach port $port_list {
	UALLoadPort $chassis_id $port
	}
}

for_array_keys chassis_id ntgr_trafficTestInfo {

	set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-015.tcl" "Starting traffic on ports"
	foreach port $port_list {
	UALStartTrafficPerPort $chassis_id $port
	}
}

##############################################check the receive rate of receive port#################################

for_array_keys chassis_id ntgr_trafficTestInfo {
   
    set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
    NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-015.tcl" "checking the rate on ports"
    sleep 10
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
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-015.tcl" "Receive rate is not equal to the send rate."   
	}

#######################################################################################################################

foreach switch_name $MSTP_REGION {

    CALSaveConfig $switch_name
    CALRebootSwitch $switch_name
    sleep 300
    for_array_keys chassis_id ntgr_trafficTestInfo {
   
        set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-015.tcl" "checking the rate on ports"
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
	      NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-015.tcl" "Receive rate is not equal to the send rate after rebooting $switch_name."  
	      set result 0 
	  } else { set result 1}	     
}

    UALDisconnectTester $chassis_id
    
    if {$result == 1} {NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-015.tcl" " TC-VRRP-015.tcl passed"} else {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-VRRP-015.tcl" " TC-VRRP-015.tcl failed"
    }
Netgear_log_file "TC-VRRP-015.tcl" "******************** Completed Test case TC-VRRP-015.tcl ********************"

#**************************************************  End of Test case  *************************************************
