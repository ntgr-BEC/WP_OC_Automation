proc ntgrRoutingGlobalEnableRouting {strDevice} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "ip routing"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingGlobalDisableRouting {strDevice} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no ip routing"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingGetIPInterfaceTable {strDevice _o_table} {
    upvar 1 $_o_table table

    set strOutput [ntgrCLIExecute $strDevice "show ip interface brief"]
    set retval [ntgrCLIParseCellTable $strOutput table]
    return $retval
}

proc ntgrRoutingGetIPPortInterface {strDevice ifname _o_table} {
    upvar 1 $_o_table table

    set table [ntgrCLIExecute $strDevice "show ip interface $ifname"]
    return 1
}

proc ntgrRoutingGetIPVLANInterface {strDevice vlan _o_table} {
    upvar 1 $_o_table table

    set vlanid [keylget vlan vlanid]
    set table [ntgrCLIExecute $strDevice "show ip interface vlan $vlanid"]

    Netgear_log_file "ntgrRoutingGetIPVLANInterface" "\n$table"

    return 1
}
