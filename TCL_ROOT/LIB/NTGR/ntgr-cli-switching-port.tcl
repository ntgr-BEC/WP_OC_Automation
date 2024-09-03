proc ntgrSwitchingPortLinkUp {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "no shutdown"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingPortLinkDown {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "shutdown"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingPortLinkStatus {strDevice ifname} {
    set retval 0

    set strOutput [ntgrCLIExecute $strDevice "show port $ifname"]
    ntgrCLIParseCellTable $strOutput table

    set colPORTIF       [ntgrCLICellTableColNumberFromText $table "intf"]
    set colPORTSTATUS   [ntgrCLICellTableColNumberFromText $table "ink status"]
    set row [ntgrCLICellTableRowNumberFromText $table $ifname $colPORTIF]
    if {$row > 0} {
        if {[string equal -nocase "up" [ntgrCLICellTableCellData $table $row $colPORTSTATUS]]} {
            set retval 1
        }
    }

    Netgear_log_file "ntgrSwitchingPortLinkStatus" "[ntgrTclDumpCellTable $table]"

    return $retval
}

proc ntgrSwitchingPortGetAllPorts {strDevice} {
    set strOutput [ntgrCLIExecute $strDevice "show port all"]
    ntgrCLIParseCellTable $strOutput table

    set rows [keylget table rows]
    set colIf [ntgrCLICellTableColNumberFromText $table "intf"]

    set lstPorts {}
    for {set row 1} {$row <= $rows} {incr row} {
       
        set temp [ntgrCLICellTableCellData $table $row $colIf]
        lappend lstPorts $temp
    }

    return $lstPorts
}

proc ntgrSwitchingPortAdminMode {strDevice ifname} {
    set retval 0

    set strOutput [ntgrCLIExecute $strDevice "show port $ifname"]
    Netgear_log_file "" "strOutput = $strOutput"
    ntgrCLIParseCellTable $strOutput table

    set colPORTIF       [ntgrCLICellTableColNumberFromText $table "intf"]
    set colADMINMODE   [ntgrCLICellTableColNumberFromText $table "admin mode"]
    set row [ntgrCLICellTableRowNumberFromText $table $ifname $colPORTIF]

    set retval [ntgrCLICellTableCellData $table $row $colADMINMODE]

    Netgear_log_file "ntgrSwitchingPortLinkStatus" "[ntgrTclDumpCellTable $table]"

    return $retval
}

proc ntgrSwitchingPortAdminModeForLag {strDevice ifname} {
    set retval 0

    set strOutput [ntgrCLIExecute $strDevice "show port status $ifname"]
    Netgear_log_file "" "strOutput = $strOutput"
    ntgrCLIParseCellTable $strOutput table

    set colPORTIF       [ntgrCLICellTableColNumberFromText $table "intf"]
    set colADMINMODE   [ntgrCLICellTableColNumberFromText $table "link status"]
    set row [ntgrCLICellTableRowNumberFromText $table $ifname $colPORTIF]

    set retval [ntgrCLICellTableCellData $table $row $colADMINMODE]

    Netgear_log_file "ntgrSwitchingPortLinkStatus" "[ntgrTclDumpCellTable $table]"

    return $retval
}
