#  Function Name:  ntgrRoutingMLDGlobalEnable
#
#  Description  :  Enable MLD Global
#
#  Arguments    :
#                  strDevice---DUT
#  Author:         duke
#  Create Time:    2015-01-09
#
#  Return Value :
#                  
# 
proc ntgrRoutingMLDGlobalEnable {strDevice} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "ipv6 mld router"
    ntgrCLIExecute $strDevice "exit"  
    Netgear_log_file "ntgr-cli-routing-MLD.tcl" "ntgrRoutingMLDGlobalEnable"
}

#  Function Name:  ntgrRoutingMLDGlobalDisable
#
#  Description  :  Disable MLD Global
#
#  Arguments    :
#                  strDevice---DUT
#  Author:         duke
#  Create Time:    2015-01-09
#
#  Return Value :
#                  
# 
proc ntgrRoutingMLDGlobalDisable {strDevice} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no ipv6 mld router"
    ntgrCLIExecute $strDevice "exit"  
    Netgear_log_file "ntgr-cli-routing-MLD.tcl" "ntgrRoutingMLDGlobalDisable"
}

#  Function Name:  ntgrRoutingMLDInterfaceEnable
#
#  Description  :  Enable MLD on interface
#
#  Arguments    :
#                  strDevice---DUT
#  Author:         duke
#  Create Time:    2015-01-09
#
#  Return Value :
#                  
# 
proc ntgrRoutingMLDInterfaceEnable {strDevice intf} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface $intf"
    ntgrCLIExecute $strDevice "ipv6 mld router"
    ntgrCLIExecute $strDevice "exit"  
    ntgrCLIExecute $strDevice "exit"  
    Netgear_log_file "ntgr-cli-routing-MLD.tcl" "ntgrRoutingMLDInterfaceEnable"
}


#  Function Name:  ntgrRoutingMLDInterfaceDisable
#
#  Description  :  Disable MLD on interface
#
#  Arguments    :
#                  strDevice---DUT
#  Author:         duke
#  Create Time:    2015-01-09
#
#  Return Value :
#                  
# 
proc ntgrRoutingMLDInterfaceDisable {strDevice intf} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface $intf"
    ntgrCLIExecute $strDevice "no ipv6 mld router"
    ntgrCLIExecute $strDevice "exit"  
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingMLDSetLastMemberQueryCount {strDevice intf num} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface $intf"
    ntgrCLIExecute $strDevice "ipv6 mld last-member-query-count $num"
    ntgrCLIExecute $strDevice "exit"  
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingMLDSetLastMemberQueryInterval {strDevice intf num} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface $intf"
    ntgrCLIExecute $strDevice "ipv6 mld last-member-query-interval $num"
    ntgrCLIExecute $strDevice "exit"  
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingMLDSetQueryInterval {strDevice intf num} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface $intf"
    ntgrCLIExecute $strDevice "ipv6 mld query-interval $num"
    ntgrCLIExecute $strDevice "exit"  
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingMLDSetQueryMaxResponseTime {strDevice intf num} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface $intf"
    ntgrCLIExecute $strDevice "ipv6 mld query-max-response-time $num"
    ntgrCLIExecute $strDevice "exit"  
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingMLDSetStartupQueryCount {strDevice intf num} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface $intf"
    ntgrCLIExecute $strDevice "ipv6 mld startup-query-count $num"
    ntgrCLIExecute $strDevice "exit"  
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingMLDSetStartupQueryInterval {strDevice intf num} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface $intf"
    ntgrCLIExecute $strDevice "ipv6 mld startup-query-interval $num"
    ntgrCLIExecute $strDevice "exit"  
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingMLDSetVersion {strDevice intf version} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface $intf"
    ntgrCLIExecute $strDevice "ipv6 mld version $version"
    ntgrCLIExecute $strDevice "exit"  
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingMLDProxyInterfaceEnable {strDevice intf} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface $intf"
    ntgrCLIExecute $strDevice "ipv6 mld-proxy"
    ntgrCLIExecute $strDevice "exit"  
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingMLDProxyInterfaceDisable {strDevice intf} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface $intf"
    ntgrCLIExecute $strDevice "no ipv6 mld-proxy"
    ntgrCLIExecute $strDevice "exit"  
    ntgrCLIExecute $strDevice "exit"
}
