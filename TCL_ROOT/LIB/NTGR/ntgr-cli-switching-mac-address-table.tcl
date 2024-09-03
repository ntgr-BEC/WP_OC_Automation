proc ntgrSwitchingFDBGetMACAddrTable {strDevice _o_table} {
    upvar 1 $_o_table table

    set strOutput [ntgrCLIExecute $strDevice "show mac-addr-table"]
    set retval [ntgrCLIParseCellTable $strOutput table]

    Netgear_log_file "ntgrSwitchingFDBGetMACAddrTable" "[ntgrTclDumpCellTable $table]"

    return $retval
}

proc ntgrSwitchingFDBGetMACAddrTableCount {strDevice _o_table} {
    upvar 1 $_o_table table
    set table [ntgrCLIExecute $strDevice "show mac-addr-table count"]
}
