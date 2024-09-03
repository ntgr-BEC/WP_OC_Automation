#################################################################################
#
#  File Name	    	: TC-OSPF-009.tcl
#
#  Description     	: Default route used by routers in stub area
#
#  Config file       	: TC-OSPF-009.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        01/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-009.tcl" "******** Starting Test case TC-OSPF-009.tcl **********"

foreach switch_name $ntgr_OSPF_List_009 {
    Netgear_log_file "TC-OSPF-009.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-009.tcl" "Started to Modify TOPO"
Netgear_connect_switch $TOPO_SW
CALPortLinkDown $TOPO_SW $port_down
Netgear_disconnect_switch $TOPO_SW

Netgear_log_file "TC-OSPF-009.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_009] {
    Netgear_connect_switch $switch_name
    CALRoutingEnable $switch_name
    foreach port_property $portlist {
        set port [lindex $port_property 0]
        CALRoutingPortEnable $switch_name $port
        set num1 [llength $port_property]
        set num [expr $num1 - 1]
        for {set i 1} {$i <= $num} {incr i} {
            set ip_addr [lindex $port_property $i]
            CALAddIPtoPort $switch_name $port $ip_addr
        }
    }
    Netgear_disconnect_switch $switch_name
}
Netgear_log_file "TC-OSPF-009.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_009 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	set stub_infor [getOspfStubInfor $switch_name]
	set redistribute [getOspfRedistribute $switch_name]
	  
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	CALOspfConfigIFProperty $switch_name $interface_list
	CALOspfAreaStubEnable $switch_name $stub_infor
	CALOspfRedistributeEnable $switch_name $redistribute
	
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-009.tcl" "Started Configuration of OSPF on IXIA"

foreach {chassis_id portlist} [array get ntgr_trafficPortList_009] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port	
		UALLoadPort $chassis_id $port
	}
}
	
sleep 5
	UALStartTrafficPerPort $ntgrTG $ntgrPort1
sleep 30

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/[lindex $TxRx1 0]]

if {$dif >= 0.9} {
	Netgear_log_file "TC-OSPF-009.tcl" "***** TC-OSPF-009.tcl passed *****"
	} else {
	Netgear_log_file "TC-OSPF-009.tcl" "***** TC-OSPF-009.tcl failed *****"
}		

#foreach {chassis_id portlist} [array get ntgrTGPortList] {
#    foreach pt $portlist {
#        UALStopTrafficPerPort $chassis_id $pt
#    }
#}
#*********************  End of Test case  ***************************************

