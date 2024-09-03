set local_cache(gDeviceName) "N/A"
set local_cache(gDeviceModel) "N/A"
set local_cache(gDeviceFirmware) "N/A"
proc ntgrSystemManagementGetSwitchModelAndFirmware {strDevice _o_strModel _o_strFirmware} {
global local_cache

    upvar 1 $_o_strModel strModel
    upvar 1 $_o_strFirmware strFirmware
    set str {}
    if {
    [string equal $local_cache(gDeviceName) $strDevice] &&
    ![string equal $local_cache(gDeviceName) "N/A"] &&
    ![string equal $local_cache(gDeviceModel) "N/A"] &&
    ![string equal $local_cache(gDeviceFirmware) "N/A"]
    } {
        # use cached value
        set strModel $local_cache(gDeviceModel)
        set strFirmware $local_cache(gDeviceFirmware)
    } else {
        set local_cache(gDeviceName) "N/A"
        set local_cache(gDeviceModel) "N/A"
        set local_cache(gDeviceFirmware) "N/A"

        set strOutput [ntgrCLIExecute $strDevice "show version"]
        ntgrCLIParseParam $strOutput "machine model" lstParamValue
        set strModel {}
        regexp -nocase -- {([[J]?[FGXM][S]?[M]?[0-9]+[-]?[0-9]+[T]?[F]?[P]?[X]?[S]?[G]?[0-9]?[-]?[P]?[O]?[E]?[+]?)} [lindex $lstParamValue 0] str
        set strMode $str
        ntgrCLIParseParam $strOutput "software version" lstParamValue
        regexp -nocase -linestop -- {((R|N|[0-9]+)\.[0-9]+\.[0-9]+\.[0-9]+)} $lstParamValue str
        set strFirmware $str
        # update cache
        set local_cache(gDeviceName) $strDevice
        set local_cache(gDeviceModel) $strModel
        set local_cache(gDeviceFirmware) $strFirmware
    }

    Netgear_log_file "ntgrSystemManagementGetSwitchModelAndFirmware" "{Model: $strModel} {Firmare: $strFirmware}"
}

proc ntgrParseFirmware {strFirmware _o_intRelease _o_intVersion _o_intMaintenance _o_intPatch} {
    upvar 1 $_o_intRelease intRelease
    upvar 1 $_o_intVersion intVersion
    upvar 1 $_o_intMaintenance intMaintenance
    upvar 1 $_o_intPatch intPatch

    set lstFirmware [split $strFirmware "."]
    if {[string equal "N" [lindex $lstFirmware 0]]} {
        set intRelease 9
    } else {
        if {[string equal "R" [lindex $lstFirmware 0]]} {
           set intRelease 10
        } else {
            set intRelease [lindex $lstFirmware 0]
        }
    }
    set intVersion [lindex $lstFirmware 1]
    set intMaintenance [lindex $lstFirmware 2]
    set intPatch [lindex $lstFirmware 3]
    Netgear_log_file "ntgrParseFirmware" "{Release: $intRelease} {Version: $intVersion}"
    Netgear_log_file "ntgrParseFirmware" "{Maintenance: $intMaintenance} {Patch: $intPatch}"
}
 
proc ntgrSystemDeviceFWReleaseNumber {strDevice} {
    ntgrSystemManagementGetSwitchModelAndFirmware $strDevice strModel strFirmware
    ntgrParseFirmware $strFirmware intRelease intVersion intMaintenance intPatch
    return $intRelease
}

proc ntgrSystemManagementDNSConfiguration {strDev dns_config} {
   
    keylget dns_config DNSSTATUS stat
    if {$stat == "enable"} {
        set cmd "ip domain lookup"
    } else { set cmd  "no ip domain lookup" }
    ntgrCLIExecute $strDev "configure"
    ntgrCLIExecute $strDev $cmd
    ntgrCLIExecute $strDev "exit"
}

proc ntgrSystemManagementAddDNS {strDev dns_info} {
  keylget dns_info HOSTNAME  name
  keylget dns_info  HOSTIP   addr
  set cmd "ip host $name $addr"
  ntgrCLIExecute $strDev "configure"
  ntgrCLIExecute $strDev $cmd
  ntgrCLIExecute $strDev "exit"
}

proc ntgrSystemManagementDeleteDNS {strDev dns_info} {
  keylget dns_info HOSTNAME  name
  set cmd "no ip host $name"
  ntgrCLIExecute $strDev "configure"
  ntgrCLIExecute $strDev $cmd
  ntgrCLIExecute $strDev "exit"
}