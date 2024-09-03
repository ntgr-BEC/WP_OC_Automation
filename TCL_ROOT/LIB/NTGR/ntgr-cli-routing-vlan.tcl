proc ntgrRoutingVLANEnableRouting {strDevice vlan} {
    set vlanid [keylget vlan vlanid]
    ntgrCLIExecute $strDevice "vlan database"
    ntgrCLIExecute $strDevice "vlan routing $vlanid"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingVLANDisableRouting {strDevice vlan} {
    set vlanid [keylget vlan vlanid]
    ntgrCLIExecute $strDevice "vlan database"
    ntgrCLIExecute $strDevice "no vlan routing $vlanid"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRoutingVLANGetIPVLANTable {strDevice _o_table} {
    upvar 1 $_o_table table

    set strOutput [ntgrCLIExecute $strDevice "show ip vlan"]
    set retval [ntgrCLIParseCellTable $strOutput table]
    return $retval
}

proc ntgrRoutingVLANGetIPVLAN {strDevice _io_ipvlan} {
    upvar 1 $_io_ipvlan ipvlan

    set retval 0

    if {[ntgrRoutingVLANGetIPVLANTable $strDevice table]} {
        set vlanid [keylget ipvlan vlanid]
        set colVLANID   [ntgrCLICellTableColNumberFromText $table "vlan id"]
        set colVLANIF   [ntgrCLICellTableColNumberFromText $table "logical interface"]
        set colIPADDR   [ntgrCLICellTableColNumberFromText $table "ip address"]
        set colNETMASK  [ntgrCLICellTableColNumberFromText $table "Subnet Mask"]
        set row [ntgrCLICellTableRowNumberFromText $table $vlanid $colVLANID]
        if {$row > 0} {
            set retval 1
            keylset ipvlan vlanif   [ntgrCLICellTableCellData $table $row $colVLANIF]
            keylset ipvlan ipaddr   [ntgrCLICellTableCellData $table $row $colIPADDR]
            keylset ipvlan netmask  [ntgrCLICellTableCellData $table $row $colNETMASK]
        }
    }

    Netgear_log_file "ntgrRoutingVLANGetIPVLAN" "[ntgrTclDumpList $ipvlan]"

    return $retval
}

proc ntgrRoutingVLANGetMACUsedByVLAN {strDevice} {
    set strOutput [ntgrCLIExecute $strDevice "show ip vlan"]
    regexp -line -nocase -- {([0-9A-F]{2}:){5}[0-9A-F]{2}} $strOutput retval
    Netgear_log_file "ntgrRoutingVLANGetMACUsedByVLAN" "$retval"
    return $retval
}

proc ntgrRouingVLANAddIPConfig {strDevice vlan ipconfig} {
    set vlanid [keylget vlan vlanid]
    set ipaddr [keylget ipconfig ipaddr]
    set netmask [keylget ipconfig netmask]

    ntgrCLIExecute $strDevice "configure"

    ntgrCLIExecute $strDevice "interface vlan $vlanid"
    ntgrCLIExecute $strDevice "ip address $ipaddr $netmask"
    ntgrCLIExecute $strDevice "exit"

    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRouingVLANDeleteIPConfig {strDevice vlan ipconfig} {
    set vlanid [keylget vlan vlanid]
    set ipaddr [keylget ipconfig ipaddr]
    set netmask [keylget ipconfig netmask]

    ntgrCLIExecute $strDevice "configure"

    ntgrCLIExecute $strDevice "interface vlan $vlanid"
    ntgrCLIExecute $strDevice "no ip address $ipaddr $netmask"
    ntgrCLIExecute $strDevice "exit"

    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRouingVLANAddSecondaryIPConfig {strDevice vlan ipconfig} {
    set vlanid [keylget vlan vlanid]
    set ipaddr [keylget ipconfig ipaddr]
    set netmask [keylget ipconfig netmask]

    ntgrCLIExecute $strDevice "configure"

    ntgrCLIExecute $strDevice "interface vlan $vlanid"
    ntgrCLIExecute $strDevice "ip address $ipaddr $netmask secondary"
    ntgrCLIExecute $strDevice "exit"

    ntgrCLIExecute $strDevice "exit"
}

proc ntgrRouingVLANDeleteSecondaryIPConfig {strDevice vlan ipconfig} {
    set vlanid [keylget vlan vlanid]
    set ipaddr [keylget ipconfig ipaddr]
    set netmask [keylget ipconfig netmask]

    ntgrCLIExecute $strDevice "configure"

    ntgrCLIExecute $strDevice "interface vlan $vlanid"
    ntgrCLIExecute $strDevice "no ip address $ipaddr $netmask secondary"
    ntgrCLIExecute $strDevice "exit"

    ntgrCLIExecute $strDevice "exit"
}
