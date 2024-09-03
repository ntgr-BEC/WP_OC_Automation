
#  Function Name:  ntgrSecurityPrivateVlanAssignVlanType
#
#  Description  :  Assign Private Vlan Type 
#
#  Arguments    :
#                  strDevice---DUT
#                  vlanId---vlan id to be set
#                  vlanType---private vlan type
#  Author:         duke
#  Create Time:    2013-02-19
#
#  Return Value :  
#
proc ntgrSecurityPrivateVlanAssignVlanType {strDevice vlanId vlanType} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "vlan $vlanId"
    ntgrCLIExecute $strDevice "private-vlan $vlanType"
    ntgrCLIExecute $strDevice "exit" 
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-security-privatevlan.tcl" "ntgrSecurityPrivateVlanAssignVlanType"
}

#  Function Name:  ntgrSecurityPrivateVlanConfigPrivateVlanAssociation
#
#  Description  :  config private vlan association
#
#  Arguments    :
#                  strDevice---DUT
#                  primaryVlan---Primary Vlan
#                  secondaryVlan---Secondary Vlan
#  Author:         duke
#  Create Time:    2013-02-19
#
#  Return Value :  
#                
# 
proc ntgrSecurityPrivateVlanConfigPrivateVlanAssociation {strDevice primaryVlan secondaryVlan} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "vlan $primaryVlan"
    ntgrCLIExecute $strDevice "private-vlan association $secondaryVlan"
    ntgrCLIExecute $strDevice "exit" 
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-security-privatevlan.tcl" "ntgrSecurityPrivateVlanConfigPrivateVlanAssociation"
}
#  Function Name:  ntgrSecurityPrivateVlanConfigPrivateVlanPortMode
#
#  Description  :  config private vlan port mode 
#
#  Arguments    :
#                  strDevice---DUT
#                  iflist---list of interface
#                  portMode---port mode
#  Author:         duke
#  Create Time:    2013-10-22
#
#  Return Value :  
#
proc ntgrSecurityPrivateVlanConfigPrivateVlanPortMode {strDevice iflist portMode} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "switchport mode private-vlan $portMode"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-security-privatevlan.tcl" "ntgrSecurityPrivateVlanConfigPrivateVlanPortMode"
}


#  Function Name:  ntgrSecurityPrivateVlanConfigPrivateVlanHostInterfaceConfiguration
#
#  Description  :  config private vlan port mode 
#
#  Arguments    :
#                  strDevice---DUT
#                  iflist---list of interface
#                  primaryVlan---Primary Vlan
#                  secondaryVlan---Secondary Vlan
#  Author:         duke
#  Create Time:    2013-10-22
#
#  Return Value :  
#                 
# 
proc ntgrSecurityPrivateVlanConfigPrivateVlanHostInterfaceConfiguration {strDevice iflist primaryVlan secondaryVlan} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "switchport  private-vlan host-association $primaryVlan $secondaryVlan"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-security-privatevlan.tcl" "ntgrSecurityPrivateVlanConfigPrivateVlanHostInterfaceConfiguration"
}

#  Function Name:  ntgrSecurityPrivateVlanConfigPrivateVlanPromiscuousInterfaceConfiguration
#
#  Description  :  config private vlan port mode 
#
#  Arguments    :
#                  strDevice---DUT
#                  iflist---list of interface
#                  primaryVlan---Primary Vlan
#                  secondaryVlan---Secondary Vlan
#  Author:         duke
#  Create Time:    2013-10-22
#
#  Return Value :  
#
proc ntgrSecurityPrivateVlanConfigPrivateVlanPromiscuousInterfaceConfiguration {strDevice iflist primaryVlan secondaryVlan} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "switchport  private-vlan mapping $primaryVlan $secondaryVlan"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-security-privatevlan.tcl" "ntgrSecurityPrivateVlanConfigPrivateVlanPromiscuousInterfaceConfiguration"
}

#  Function Name:  ntgrSecurityPrivateVlanResetPrivateVlanPortMode
#
#  Description  :  config private vlan port mode 
#
#  Arguments    :
#                  strDevice---DUT
#                  iflist---list of interface
#  Author:         duke
#  Create Time:    2013-10-22
#
#  Return Value :  
#
proc ntgrSecurityPrivateVlanResetPrivateVlanPortMode {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "no switchport mode"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-security-privatevlan.tcl" "ntgrSecurityPrivateVlanResetPrivateVlanPortMode"
}

#  Function Name:  ntgrSecurityPrivateVlanResetPrivateVlanHostInterfaceConfiguration
#
#  Description  :  config private vlan port mode 
#
#  Arguments    :
#                  strDevice---DUT
#                  iflist---list of interface
#
#  Return Value :  
#                 
# 
proc ntgrSecurityPrivateVlanResetPrivateVlanHostInterfaceConfiguration {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "no switchport  private-vlan host-association"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-security-privatevlan.tcl" "ntgrSecurityPrivateVlanResetPrivateVlanHostInterfaceConfiguration"
}

#  Function Name:  ntgrSecurityPrivateVlanResetPrivateVlanPromiscuousInterfaceConfiguration
#
#  Description  :  config private vlan port mode 
#
#  Arguments    :
#                  strDevice---DUT
#                  iflist---list of interface
#  Author:         duke
#  Create Time:    2013-10-22
#
#  Return Value :  
#
proc ntgrSecurityPrivateVlanResetPrivateVlanPromiscuousInterfaceConfiguration {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "no switchport  private-vlan mapping"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-security-privatevlan.tcl" "ntgrSecurityPrivateVlanResetPrivateVlanPromiscuousInterfaceConfiguration"
}

#  Function Name:  ntgrSecurityPrivateVlanResetVlanType
#
#  Description  :  Assign Private Vlan Type 
#
#  Arguments    :
#                  strDevice---DUT
#                  vlanId---vlan id to be set
#  Author:         duke
#  Create Time:    2013-02-19
#
#  Return Value :  
#
proc ntgrSecurityPrivateVlanResetVlanType {strDevice vlanId} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "vlan $vlanId"
    ntgrCLIExecute $strDevice "no private-vlan"
    ntgrCLIExecute $strDevice "exit" 
    ntgrCLIExecute $strDevice "exit" 
    Netgear_log_file "ntgr-webui-security-privatevlan.tcl" "ntgrSecurityPrivateVlanResetVlanType"
}

proc ntgrSecurityPrivateVlanGetVlanType {strDevice} {
    set ret [ntgrCLIExecute $strDevice "show vlan private-vlan "]
    Netgear_log_file "ntgr-webui-security-privatevlan.tcl" "ntgrSecurityPrivateVlanGetVlanType"
    return $ret
}
