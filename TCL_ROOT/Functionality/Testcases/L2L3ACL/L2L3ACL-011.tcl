################################################################################
#
#  File Name    : L2L3ACL-011.tcl
#
#  Description  : This testcase used to test combined layer2/3 ACLs.
#                 With match conditions: Permit specified src/dst MAC, COS, VLAN,
#                 2nd COS, 2nd VLAN, src/dst IP, TCP/UDP/L4 src/dst Port, 
#                 TOS/DSCP/Precedence.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/05/22  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "L2L3ACL-011.tcl" "******************** Starting Test case L2L3ACL-011.tcl ********************"
CALResetConfiguration $ntgrDUT

## Create VLAN
CALCreateVlan "VLAN100"
CALCreateVlan "VLAN200"

## Configure MAC ACL
set mac_acl_name_list [getMACACLID $ntgrDUT]
set mac_acl_rule_list [getMACACLRule $ntgrDUT]
set mac_acl_interface_list [getMACACLInterface $ntgrDUT]
CALAddMacACL  $ntgrDUT $mac_acl_rule_list
CALInterfaceMACACLEnable $ntgrDUT $mac_acl_interface_list

## Configure IP ACL
set ip_acl_id_list [getIPACLID $ntgrDUT]
set ip_acl_rule_list [ getIPACLRule $ntgrDUT]
set ip_acl_interface_list [getIPACLInterface $ntgrDUT]
CALAddIPACL $ntgrDUT $ip_acl_rule_list
CALEnableIPACLOninterface $ntgrDUT $ip_acl_interface_list


# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}
sleep 5

# Collect rate info several times
set txRate 0
while {$txRate<1} {
    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort] 0]
    set rxRate  [lindex [UALReportPortRate $ntgrTG $ntgrRxPort] 1]
    sleep 10
}
sleep 20
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort] 0]
set rxRate  [lindex [UALReportPortRate $ntgrTG $ntgrRxPort] 1]
set txrx [expr $rxRate/$txRate]

# set key ""
# while {$key!="q"} {
#     set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort] 0]
#     set rxRate  [lindex [UALReportPortRate $ntgrTG $ntgrRxPort] 1]
#     set key [gets stdin]
# }

## The expected $txrx is 2/14
if { $txrx>0.13 && $txrx<0.16 } {
    set NtgrTestResult(L2L3ACL-011.tcl) "OK"
} else {
    set NtgrTestResult(L2L3ACL-011.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "L2L3ACL-011.tcl" "------------------- Complete Test case L2L3ACL-011.tcl, Testing result: $NtgrTestResult(L2L3ACL-011.tcl) ---------------------"
#*************************  End of Test case  **********************************