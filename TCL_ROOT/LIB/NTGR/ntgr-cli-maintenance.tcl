proc ntgrMaintenanceSaveConfig {strDevice} {
    ntgrCLIExecute $strDevice "save"
}

proc ntgrMaintenanceDeviceReboot {strDevice} {
    ntgrCLILogin $strDevice 
    ntgrCLIExecute $strDevice "reload" 
    sleep 5
    ntgrKillTelnet
    _set_switch_handle $strDevice 0
    sleep 60
}

proc ntgrMaintenanceDeviceRebootwithoutSave {strDevice} {
    ntgrCLILogin $strDevice 
    keylset Options nosave 1
    ntgrCLIExecute $strDevice "reload" $Options
    sleep 5
    ntgrKillTelnet
    _set_switch_handle $strDevice 0
}