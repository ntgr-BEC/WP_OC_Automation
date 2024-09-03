proc ntgrSwitchingSTPSetBridgePriority {strDevice priority {instance 0}} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "spanning-tree mst priority $instance $priority"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingSTPCSTSetBridgePriority {strDevice priority} {
    ntgrSwitchingSTPSetBridgePriority $strDevice $priority
}

proc ntgrSwitchingSTPCSTResetBridgePriority {strDevice} {
    ntgrSwitchingSTPSetBridgePriority $strDevice 32768
}

proc ntgrSwitchingSTPGetPortStatus {strDevice _o_table {instance 0}} {
    upvar 1 $_o_table table

    set strOutput [ntgrCLIExecute $strDevice "show spanning-tree mst port summary $instance all"]
    set retval [ntgrCLIParseCellTable $strOutput table]

    return $retval
}

proc ntgrSwitchingSTPCSTGetPortStatus {strDevice _o_table} {
    upvar 1 $_o_table table

    return [ntgrSwitchingSTPGetPortStatus $strDevice table]
}

