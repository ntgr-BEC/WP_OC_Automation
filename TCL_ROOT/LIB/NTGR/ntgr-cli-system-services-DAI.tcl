
#  Function Name:  ntgrGetDAIPacketStatistics
#
#  Description  :  get DAI Statistics 
#
#  Arguments    :
#                  strDevice---DUT
#                  vlanid--- vlan id     
#                  staname---the column name of dai statistics
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :the statistics value
#                  
# 
proc ntgrGetDAIPacketStatistics {strDevice vlanid staname} {
    set strOutput [ntgrCLIExecute $strDevice "show ip arp inspection statistics vlan $vlanid"]
    ntgrCLIParseCellTable $strOutput table
    set colStaName [ntgrCLICellTableColNumberFromText $table "$staname"]
    set colVlanName [ntgrCLICellTableColNumberFromText $table "VLAN"]
    set row [ntgrCLICellTableRowNumberFromText $table $vlanid $colVlanName]
    set statics [ntgrCLICellTableCellData $table $row $colStaName]
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrGetDAIPacketStatistics"
    return $statics
}

#  Function Name:  ntgrGetDAIPacketStatisticsEx
#
#  Description  :  get DAI Statistics 
#
#  Arguments    :
#                  strDevice---DUT
#                  vlanid--- vlan id     
#                  staname---the column name of dai statistics
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :the statistics value
#                  
# 
proc ntgrGetDAIPacketStatisticsEx {strDevice vlanid staname} {
    set strOutput [ntgrCLIExecute $strDevice "show ip arp inspection statistics"]
    ntgrCLIParseCellTable $strOutput table
    set colStaName [ntgrCLICellTableColNumberFromText $table "$staname"]
    set colVlanName [ntgrCLICellTableColNumberFromText $table "VLAN"]
    set row [ntgrCLICellTableRowNumberFromText $table $vlanid $colVlanName]
    set statics [ntgrCLICellTableCellData $table $row $colStaName]
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrGetDAIPacketStatisticsEx"
    return $statics
}
#  Function Name:  ntgrDAIDAIPacketStatisticsClear
#
#  Description  :  clear DAI Statistics 
#
#  Arguments    :
#                  strDevice---DUT
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#                  
# 
proc ntgrDAIDAIPacketStatisticsClear {strDevice} {
    ntgrCLIExecute $strDevice "clear ip arp inspection statistics" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrDAIDAIPacketStatisticsClear"
}


#  Function Name:  ntgrSystemDAIEnableInVlan
#
#  Description  :  Enable the DAI in particular vlan 
#
#  Arguments    :
#                  strDevice---DUT
#                  strVlanId---vlan id to be set
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#                  
# 
proc ntgrSystemDAIEnableInVlan {strDevice strVlanId} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "ip arp inspection vlan $strVlanId"
    ntgrCLIExecute $strDevice "exit"  
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIEnableInVlan"
}
#  Function Name:  ntgrSystemDAIDisableInVlan
#
#  Description  :  Enable the DAI in particular vlan 
#
#  Arguments    :
#                  strDevice---DUT
#                  strVlanId---vlan id to be set
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#                  
# 
proc ntgrSystemDAIDisableInVlan {strDevice strVlanId} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no ip arp inspection vlan $strVlanId"
    ntgrCLIExecute $strDevice "exit"  
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIDisableInVlan"
}

#  Function Name:  ntgrSystemDAIEnableLogInVlanLog
#
#  Description  :  Enable the DAI logging in particular vlan 
#
#  Arguments    :
#                  strDevice---DUT
#                  strVlanId---vlan id to be set
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#                  
# 
proc ntgrSystemDAIEnableLogInVlanLog {strDevice strVlanId} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "ip arp inspection vlan $strVlanId logging"
    ntgrCLIExecute $strDevice "exit"  
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIEnableLogInVlanLog"
}
#  Function Name:  ntgrSystemDAIDisableInVlan
#
#  Description  :  Enable the DAI in particular vlan 
#
#  Arguments    :
#                  strDevice---DUT
#                  strVlanId---vlan id to be set
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#                  
# 
proc ntgrSystemDAIDisableInVlan {strDevice strVlanId} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no ip arp inspection vlan $strVlanId"
    ntgrCLIExecute $strDevice "exit"  
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIDisableInVlan"
}

#  Function Name:  ntgrSystemDAIDisableLoggingInVlan
#
#  Description  :  Disable logging  in particular vlan 
#
#  Arguments    :
#                  strDevice---DUT
#                  strVlanId---vlan id to be set
#                  strDAIIfEnable---enable/disable inspection in vlan
#                  strLogIfEnable---enable/disable Logging invalid packets
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#                  
# 
proc ntgrSystemDAIDisableLoggingInVlan {strDevice strVlanId} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no ip arp inspection vlan $strVlanId logging"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIDisableLoggingInVlan"
}

#  Function Name:  ntgrSystemDAIAddACL
#
#  Description  :  Add One ACL
#
#  Arguments    :
#                  strDevice---DUT
#                  arp_acl---ARP ACL     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIAddACL {strDevice arp_acl} {
    set arprule  [keylget arp_acl aclrule]
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "arp access-list [keylget arp_acl aclid]"
    ntgrCLIExecute $strDevice "permit ip host [keylget arprule ipaddr] mac host [keylget arprule macaddr]"
    ntgrCLIExecute $strDevice "exit"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIAddACL"
}

#  Function Name:  ntgrSystemApplyArpaclToVLan
#
#  Description  :  Apply acl to vlan
#
#  Arguments    :
#                  strDevice---DUT
#                  arp_acl---ARP ACL     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemApplyArpaclToVLan {strDevice vlanid arp_acl {status ""}} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "ip arp inspection filter [keylget arp_acl aclid] vlan $vlanid $status"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemApplyArpaclToVLan"
}
#  Function Name:  ntgrSystemApplyInvalidArpaclToVLan
#
#  Description  :  Apply acl to vlan
#
#  Arguments    :
#                  strDevice---DUT
#                  arp_acl---ARP ACL     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemApplyInvalidArpaclToVLan {strDevice vlanid aclname {status ""}} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "ip arp inspection filter $aclname vlan $vlanid $status"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemApplyInvalidArpaclToVLan"
}

#  Function Name:  ntgrSystemDAIDeleteACL
#
#  Description  :  Add One ACL
#
#  Arguments    :
#                  strDevice---DUT
#                  arp_acl---ARP ACL     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIDeleteACL {strDevice arp_acl} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no arp access-list [keylget arp_acl aclid]"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIAddACL"
}
#  Function Name:  ntgrSystemDAIDeleteInvalidACL
#
#  Description  :  Add One ACL
#
#  Arguments    :
#                  strDevice---DUT
#                  arp_acl---ARP ACL     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIDeleteInvalidACL {strDevice aclname} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no arp access-list $aclname"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIDeleteInvalidACL"
}

#  Function Name:  ntgrSystemDAIAddInvalidACL
#
#  Description  :  Add an invalid ACL
#
#  Arguments    :
#                  strDevice---DUT
#                  acl_name---ARP ACL name     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIAddInvalidACL {strDevice aclname} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "arp access-list $aclname"
    ntgrCLIExecute $strDevice "exit"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIAddInvalidACL"
}

#  Function Name:  ntgrSystemDAIConfigPortAsTrusted
#
#  Description  :  Add an invalid ACL
#
#  Arguments    :
#                  strDevice---DUT
#                  iflist---interface list      
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIConfigPortAsTrusted {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "ip arp inspection trust"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIConfigPortAsTrusted"
}

#  Function Name:  ntgrSystemDAIConfigPortAsUnTrusted
#
#  Description  :  Add an invalid ACL
#
#  Arguments    :
#                  strDevice---DUT
#                  iflist---interface list    
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIConfigPortAsUnTrusted {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "no ip arp inspection trust"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIConfigPortAsUnTrusted"
}
#  Function Name:  ntgrSystemDAIConfigPortAsUnTrusted
#
#  Description  :  Add an invalid ACL
#
#  Arguments    :
#                  strDevice---DUT
#                  iflist---interface list    
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIConfigPortAsUnTrusted {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "no ip arp inspection trust"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIConfigPortAsUnTrusted"
}

#  Function Name:  ntgrSystemDAIModifyPortRate
#
#  Description  :  Add an invalid ACL
#
#  Arguments    :
#                  strDevice---DUT
#                  iflist---interface list    
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIModifyPortRate {strDevice iflist {ratelimit 15} {burstinterval 1}} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "ip arp inspection limit rate $ratelimit burst interval $burstinterval" 
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIModifyPortRate"
}

#  Function Name:  ntgrSystemDAIEnableSrcMacValidation
#
#  Description  :  enable src mac validation
#
#  Arguments    :
#                  strDevice---DUT     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIEnableSrcMacValidation {strDevice } {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "ip arp inspection validate src-mac"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIEnableSrcMacValidation"
}

#  Function Name:  ntgrSystemDAIEnableDstMacValidation
#
#  Description  :  enable dst mac validation
#
#  Arguments    :
#                  strDevice---DUT     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIEnableDstMacValidation {strDevice } {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "ip arp inspection validate dst-mac"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIEnableDstMacValidation"
}

#  Function Name:  ntgrSystemDAIEnableIpValidation
#
#  Description  :  enable dst mac validation
#
#  Arguments    :
#                  strDevice---DUT     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIEnableIpValidation {strDevice } {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "ip arp inspection validate ip"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIEnableIpValidation"
}

#  Function Name:  ntgrSystemDAIEnableALLValidation
#
#  Description  :  enable ALL validation
#
#  Arguments    :
#                  strDevice---DUT     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIEnableALLValidation {strDevice } {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "ip arp inspection validate src-mac dst-mac ip"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIEnableALLValidation"
}
#  Function Name:  ntgrSystemDAIDisableALLValidation
#
#  Description  :  enable ALL validation
#
#  Arguments    :
#                  strDevice---DUT     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIDisableALLValidation {strDevice } {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no ip arp inspection validate src-mac dst-mac ip"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIDisableALLValidation"
}


#  Function Name:  ntgrSystemDAIDisableSrcMacValidation
#
#  Description  :  diable src mac validation
#
#  Arguments    :
#                  strDevice---DUT     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIDisableSrcMacValidation {strDevice } {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice " no ip arp inspection validate src-mac"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIDisableSrcMacValidation"
}

#  Function Name:  ntgrSystemDAIDisableDstMacValidation
#
#  Description  :  diable dst mac validation
#
#  Arguments    :
#                  strDevice---DUT     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIDisableDstMacValidation {strDevice } {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no ip arp inspection validate dst-mac"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIDisableDstMacValidation"
}

#  Function Name:  ntgrSystemDAIDisableIpValidation
#
#  Description  :  diable dst mac validation
#
#  Arguments    :
#                  strDevice---DUT     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDAIDisableIpValidation {strDevice } {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no ip arp inspection validate ip"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIDisableIpValidation"
}



#  Function Name:  ntgrSystemDHCPSnoopingAddBindingConfiguration
#
#  Description  :  diable dst mac validation
#
#  Arguments    :
#                  strDevice---DUT     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDHCPSnoopingAddBindingConfiguration {strDevice  dhcpbinding_config} {
    set port      [keylget dhcpbinding_config port]
    set vlanid    [keylget dhcpbinding_config vlanid]
    set ipaddr    [keylget dhcpbinding_config ipaddr]
    set macaddr    [keylget dhcpbinding_config macaddr]
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "ip dhcp snooping binding $macaddr vlan $vlanid $ipaddr interface $port "
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIDisableIpValidation"
}

#  Function Name:  ntgrSystemDHCPSnoopingAddBindingConfiguration
#
#  Description  :  diable dst mac validation
#
#  Arguments    :
#                  strDevice---DUT     
#
#  Author:         duke
#  Create Time:    2013-11-22
#
#  Return Value :
#
proc ntgrSystemDHCPSnoopingClearBindingConfiguration {strDevice dhcpbinding_config} {
    set port      [keylget dhcpbinding_config port]
    set vlanid    [keylget dhcpbinding_config vlanid]
    set ipaddr    [keylget dhcpbinding_config ipaddr]
    set macaddr    [keylget dhcpbinding_config macaddr]
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no ip dhcp snooping binding $macaddr"
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-system-services-DAI.tcl" "ntgrSystemDAIDisableIpValidation"
}