################################################################################
#
#  File Name    : TC-L3ACL-035.tcl
#
#  Description  : ACL service on IGMP packet --- match any,deny any
#
#  Revision     :
#        Date        Programmer        Description
#        11/04/07    Nina.Cheng        Created    
#
################################################################################

#*************************  Start of Test case  ********************************

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

## Configure IGMP Snooping
CALIgmpSnpEnable $ntgrDUT
CALIgmpSnpConfigInterfaceProperties $ntgrDUT

Netgear_log_file "TC-L3ACL-026.tcl" "Started configuration of ACL on the Switches"

foreach switch_name $ntgr_L3ACL_List_035 {
  
  set ip_acl_id_list [getIPACLID $switch_name]
  set ip_acl_rule_list [ getIPACLRule $switch_name]
  set ip_acl_interface_list [getIPACLInterface $switch_name]
  
  CALAddIPACL $switch_name $ip_acl_rule_list
  CALEnableIPACLOninterface $switch_name $ip_acl_interface_list
}
sleep 2

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

UALStartTrafficPerPort $ntgrTG $ntgrPort4
UALStartTrafficPerPort $ntgrTG $ntgrPort2

sleep 50
set rx 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set rx [expr [lindex $TxRx4 1]/[lindex $TxRx2 0]]
if {$rx<1.05 && $rx>0.95} {set result1 1} else {set result1 0}

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result2 [CALCheckExpect $switch_checked $cmds $expect_string_list 0] 
}

Netgear_log_file "TC-L3ACL-035.tcl" "Started deleting ACL on the Switches"

foreach switch_name $ntgr_L3ACL_List_035 {  
  set ip_acl_id_list [getIPACLID $switch_name]
  CALDisableIPACLOninterface $switch_name $ip_acl_interface_list
}

sleep 50
set rx 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set rx [expr [lindex $TxRx4 1]/[lindex $TxRx2 0]]
if {$rx<1.05 && $rx>0.95} {set result3 1} else {set result3 0}

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result4 [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}

if {$result1 == 1 && $result2 == 1 && $result3 == 1 && $result4 == 1} {
	Netgear_log_file "TC-L3ACL-035.tcl" "***** TC-L3ACL-035.tcl passed *****"
} else {
	Netgear_log_file "TC-L3ACL-035.tcl" "***** TC-L3ACL-035.tcl failed *****"
}

## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}
Netgear_log_file "TC-L3ACL-035.tcl" "***** Completed Test case TC-L3ACL-035.tcl *****"

#*************************  End of Test case  **********************************
