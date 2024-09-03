################################################################################
#
#  File Name    : SNMP-021.tcl
#
#  Description  : This testcase used to test OSPF changing trap messages.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-021.tcl" "******************** Starting Test case SNMP-021.tcl ********************"

## Configure routing
foreach {switch_name portlist} [array get ntgr_L3SwitchList] {
    CALResetConfiguration $switch_name
    CALSetSwitchMngrIPAddr $switch_name
    CALDisableConsoleTimeout $switch_name

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

## Configure OSPF routing
foreach switch_name $ntgrDUTList {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	CALOspfConfigIFProperty $switch_name $interface_list
	Netgear_disconnect_switch $switch_name
}

set bRet 1
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-021.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin

CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver
Netgear_connect_switch $ntgrDUT
CALOspfTrapEnable $ntgrDUT ""
CALOspfDisable $ntgrDUT ""
CALOspfEnable $ntgrDUT ""
Netgear_disconnect_switch $ntgrDUT

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-021.tcl" "Press 'y' if OSPF changing trap messages send to PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

Netgear_connect_switch $ntgrDUT
CALOspfTrapDisable $ntgrDUT ""
CALOspfEnable $ntgrDUT ""
CALOspfDisable $ntgrDUT ""
Netgear_disconnect_switch $ntgrDUT
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-021.tcl" "Disable OSPF changing trap, press 'y' if no OSPF changing trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(SNMP-021.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-021.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-021.tcl" "******************** Complete Test case SNMP-021.tcl ********************"
#*************************  End of Test case  **********************************