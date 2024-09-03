proc ntgrRoutingRTGetRoutingTable {strDevice _o_table} {
    upvar 1 $_o_table table

    set table [ntgrCLIExecute $strDevice "show ip route"]

    Netgear_log_file "ntgrRoutingRTGetRoutingTable" "\n$table"

    return 1
}

proc ntgrRoutingRTAddStaticRoute {strDevice route} {
    set ipaddr [keylget route ipaddr]
    set netmask [keylget route netmask]
    set gateway [keylget route gateway]

    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "ip route $ipaddr $netmask $gateway"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingRTDeleteStaticRoute {strDevice route} {
    set ipaddr [keylget route ipaddr]
    set netmask [keylget route netmask]
    set gateway [keylget route gateway]

    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no ip route $ipaddr $netmask $gateway"
    ntgrCLIExecute $strDevice "exit"
}

