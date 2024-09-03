proc ntgrSwitchingLAGGetLAGLogicalIntf {strDevice lagid} {
    regexp -- {[0-9]+} $lagid laggroupid
    set strOutput [ntgrCLIExecute $strDevice "show port-channel $laggroupid"]

    ntgrCLIParseParam $strOutput "local interface" lstParamValue
    regexp -nocase -- {(lag [0-9]+)|([0-9]+/[0-9]+/[0-9]+)|([0-9]+/[0-9]+)} [lindex $lstParamValue 0] strIntf

    Netgear_log_file "ntgrSwitchingLAGGetLAGLogicalIntf" "{Local Interface: $strIntf}"

    return $strIntf
}

proc ntgrSwitchingLAGAddLAG {strDevice lag} {
    set static 0
    keylget lag staticmode static
    if {[ntgrSystemDeviceFWReleaseNumber $strDevice] >= 9} {
        # do nothing
        if {$static == "enable"} {
            set lagname [keylget lag lagid]
            ntgrCLIExecute $strDevice "configure"
             ntgrCLIExecute $strDevice "interface $lagname"
            ntgrCLIExecute $strDevice "port-channel static"
            ntgrCLIExecute $strDevice "exit"
            ntgrCLIExecute $strDevice "exit"
        }
    } else {
        set lagname [keylget lag lagname]
        ntgrCLIExecute $strDevice "configure"
        ntgrCLIExecute $strDevice "port-channel $lagname"
        if {$static == "enable"} { ntgrCLIExecute $strDevice "port-channel static" }
        ntgrCLIExecute $strDevice "exit"
    }
    if {[keylget lag lagmember {}]} {
        set lagmember [keylget lag lagmember]
        ntgrSwitchingLAGAddLAGMember $strDevice $lag $lagmember
    }
}

proc ntgrSwitchingLAGDeleteLAG {strDevice lag} {
    if {[keylget lag lagmember {}]} {
        set lagmember [keylget lag lagmember]
        ntgrSwitchingLAGDeleteLAGMember $strDevice $lag $lagmember
    }
    set static 0
    keylget lag staticmode static
    if {[ntgrSystemDeviceFWReleaseNumber $strDevice] >= 9} {
        if {$static == "enable"} {
            set lagname [keylget lag lagid]
            ntgrCLIExecute $strDevice "configure"
             ntgrCLIExecute $strDevice "interface $lagname"
            ntgrCLIExecute $strDevice "no port-channel static"
            ntgrCLIExecute $strDevice "exit"
            ntgrCLIExecute $strDevice "exit"
        }
    } else {
        set logicalintf [ntgrSwitchingLAGGetLAGLogicalIntf $strDevice [keylget lag lagid]]
        ntgrCLIExecute $strDevice "configure"
        ntgrCLIExecute $strDevice "no port-channel $logicalintf"
        ntgrCLIExecute $strDevice "exit"
    }
}

proc ntgrSwitchingLAGAddLAGMember {strDevice lag lagmember} {
    set lagid [keylget lag lagid]
    set iflist [keylget lagmember iflist]
    set static 0
  

    if {[ntgrSystemDeviceFWReleaseNumber $strDevice] >= 9} {
        set laggroupid $lagid
    } else {
        regexp -- {[0-9]+} $lagid laggroupid
    }

    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "addport $laggroupid"
        ntgrCLIExecute $strDevice "exit"
    }
    
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingLAGDeleteLAGMember {strDevice lag lagmember} {
    set lagid [keylget lag lagid]
    set iflist [keylget lagmember iflist]

    if {[ntgrSystemDeviceFWReleaseNumber $strDevice] >= 9} {
        set laggroupid $lagid
    } else {
        regexp -- {[0-9]+} $lagid laggroupid
    }

    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "deleteport $laggroupid"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}
