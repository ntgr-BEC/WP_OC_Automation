proc ntgrSwitchingVLANResetVLANConfig {strDevice} {
    ntgrCLIExecute $strDevice "clear vlan"
    sleep 5
}

proc ntgrSwitchingVLANDefaultManagementVLAN {strDevice} {
    return 1
}

proc ntgrSwitchingVLANGetManagementVLAN {strDevice} {
    set strOutput [ntgrCLIExecute $strDevice "show network"]
    ntgrCLIParseParam $strOutput "management vlan id" lstParamValue
    regexp -- {[0-9]+} [lindex $lstParamValue 0] mgmtvlan
    return $mgmtvlan
}

proc ntgrSwitchingVLANGetVLAN {strDevice _io_vlan} {
    upvar 1 $_io_vlan vlan

    set retval 0

    set strOutput [ntgrCLIExecute $strDevice "show vlan"]
    sleep 5
    
    # modify by jim.xie for bug-892 begin
    #regsub {..More.*uit\s*} $strOutput "" test
    regsub -all {..More..\s*or\s*\(q\)uit\s*} $strOutput "" test
    ntgrCLIParseCellTable $test table
    # modify by jim.xie for bug-892 end
    
    set vlanid [keylget vlan vlanid]
    set colVLANID   [ntgrCLICellTableColNumberFromText $table "vlan id"]
    set colVLANNAME [ntgrCLICellTableColNumberFromText $table "vlan name"]
    set colVLANTYPE [ntgrCLICellTableColNumberFromText $table "vlan type"]
    set row [ntgrCLICellTableRowNumberFromText $table $vlanid $colVLANID]
    if {$row > 0} {
        set retval 1
        keylset vlan vlanname [ntgrCLICellTableCellData $table $row $colVLANNAME]
        keylset vlan vlantype [ntgrCLICellTableCellData $table $row $colVLANTYPE]
    }

    return $retval
}

proc ntgrSwitchingVLANAddVLAN {strDevice vlan} {
    set vlanid [keylget vlan vlanid]
    ntgrCLIExecute $strDevice "vlan database"
    ntgrCLIExecute $strDevice "vlan $vlanid"
    if {[keylget vlan vlanname {}]} {
        set vlanname [keylget vlan vlanname]
        ntgrCLIExecute $strDevice "vlan name $vlanid \"$vlanname\""
    }
    ntgrCLIExecute $strDevice "exit"
    # add by jim.xie for vlan trunk begin
	global SwitchPortMode
	set mode_flag [info exist SwitchPortMode]
	
	set trunk_flag 0
	if {$mode_flag == 1} {
	    if {$SwitchPortMode != "general"} {
		    set trunk_flag 1
		}
	}
	# add by jim.xie for vlan trunk end
    if {[keylget vlan vlanmember {}]} {
        set vlanmember [keylget vlan vlanmember]
		# add by jim.xie for vlan trunk begin
		if {$trunk_flag} {
		    ntgrSwitchingVLANAddVLANMember_Trunk_Mode $strDevice $vlan $vlanmember
		} else {
            ntgrSwitchingVLANAddVLANMember $strDevice $vlan $vlanmember
		}
		# add by jim.xie for vlan trunk end
    }

    if {[keylget vlan ipconfig {}]} {
        set ipconfig [keylget vlan ipconfig]
        ntgrRoutingVLANEnableRouting $strDevice $vlan
        ntgrRouingVLANAddIPConfig $strDevice $vlan $ipconfig
    }
}

proc ntgrSwitchingVLANDeleteVLAN {strDevice vlan} {
    if {[keylget vlan ipconfig {}]} {
        set ipconfig [keylget vlan ipconfig]
        ntgrRouingVLANDeleteIPConfig $strDevice $vlan $ipconfig
        ntgrRoutingVLANDisableRouting $strDevice $vlan
    }
    # add by jim.xie for vlan trunk begin
	global SwitchPortMode
	set mode_flag [info exist SwitchPortMode]
	
	set trunk_flag 0
	if {$mode_flag == 1} {
	    if {$SwitchPortMode != "general"} {
		    set trunk_flag 1
		}
	}
	# add by jim.xie for vlan trunk end
	
    if {[keylget vlan vlanmember {}]} {
        set vlanmember [keylget vlan vlanmember]
		# add by jim.xie for vlan trunk begin
		if {$trunk_flag} {
		    ntgrSwitchingVLANDeleteVLANMember_Trunk_Mode $strDevice $vlan $vlanmember
		} else {
            ntgrSwitchingVLANDeleteVLANMember $strDevice $vlan $vlanmember
		}
		# add by jim.xie for vlan trunk end
    }

    set vlanid [keylget vlan vlanid]
    ntgrCLIExecute $strDevice "vlan database"
    ntgrCLIExecute $strDevice "no vlan $vlanid"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANAddVLANName {strDevice vlanid vlanname} {
    ntgrCLIExecute $strDevice "vlan database"
    ntgrCLIExecute $strDevice "vlan name $vlanid \"$vlanname\""
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANDeleteVLANName {strDevice vlanid} {
    ntgrCLIExecute $strDevice "vlan database"
    ntgrCLIExecute $strDevice "no vlan name $vlanid"

    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANAddVLANMember {strDevice vlan vlanmember} {
    set vlanid [keylget vlan vlanid]
    set iflist [keylget vlanmember iflist]
    set taginfo [string toupper [keylget vlanmember taginfo]]
    set laggroupid {}
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        regexp -- {(lag:)([0-9]+)} $iflist str str1 laggroupid
        if { $laggroupid!={} } {
             ntgrCLIExecute $strDevice "exit"
             set iface [ntgrSwitchingLAGGetLAGLogicalIntf $strDevice $laggroupid]
             ntgrCLIExecute $strDevice "configure"
             ntgrCLIExecute $strDevice "interface $iface"
        } else {
          ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        }
        ntgrCLIExecute $strDevice "vlan participation include $vlanid"
        if {"T" == $taginfo} {
            ntgrCLIExecute $strDevice "vlan tagging $vlanid"
        }
        if {"U" == $taginfo} {
            ntgrCLIExecute $strDevice "no vlan tagging $vlanid"
        }
        if {[keylget vlanmember pvid {}]} {
            set pvid [keylget vlanmember pvid]
            ntgrCLIExecute $strDevice "vlan pvid $pvid"
        }
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"

    if {[keylget vlanmember pvid {}]} {
        set pvid [keylget vlanmember pvid]
        ntgrSwitchingVLANSetPVID $strDevice $iflist $pvid
    }
}

proc ntgrSwitchingVLANDeleteVLANMember {strDevice vlan vlanmember} {
    set vlanid [keylget vlan vlanid]
    set iflist [keylget vlanmember iflist]
    set taginfo [string toupper [keylget vlanmember taginfo]]

    if {[keylget vlanmember pvid {}]} {
        set pvid [keylget vlanmember pvid]
        ntgrSwitchingVLANResetPVID $strDevice $iflist
    }

    set laggroupid {}
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
         regexp -- {(lag:)([0-9]+)} $iflist str str1 laggroupid
        if { $laggroupid!={} } {
             ntgrCLIExecute $strDevice "exit"
             set iface [ntgrSwitchingLAGGetLAGLogicalIntf $strDevice $laggroupid]
             ntgrCLIExecute $strDevice "configure"
             ntgrCLIExecute $strDevice "interface $iface"
        } else {
          ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        }
        if {[keylget vlanmember pvid {}]} {
            set pvid [keylget vlanmember pvid]
            if {$pvid == $vlanid} {
                ntgrCLIExecute $strDevice "no vlan pvid"
            }
        }
        ntgrCLIExecute $strDevice "no vlan tagging $vlanid"
        ntgrCLIExecute $strDevice "vlan participation exclude $vlanid"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANAddAllPortsAsUntagged {strDevice vlan} {
    set vlanid [keylget vlan vlanid]
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "vlan participation all include $vlanid"
    ntgrCLIExecute $strDevice "no vlan port tagging all $vlanid"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANAddAllPortsAsTagged {strDevice vlan} {
    set vlanid [keylget vlan vlanid]
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "vlan participation all include $vlanid"
    ntgrCLIExecute $strDevice "vlan port tagging all $vlanid"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANRemoveAllPorts {strDevice vlan} {
    set vlanid [keylget vlan vlanid]
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "vlan participation all exclude $vlanid"
    ntgrCLIExecute $strDevice "exit"
}

proc __ConvertToLogicalIntf {strDevice _io_iflist} {
    upvar 1 $_io_iflist iflist

    for {set i 0} {$i < [llength $iflist]} {incr i} {
        if {[regexp -nocase -- {lag [0-9]+} [lindex $iflist $i]]} {
            lset iflist $i [ntgrSwitchingLAGGetLAGLogicalIntf $strDevice [lindex $iflist $i]]
        }
    }
}

proc ntgrSwitchingVLANIsVLANMember {strDevice vlan vlanmember} {
    set vlanid [keylget vlan vlanid]
    set iflist [keylget vlanmember iflist]
    set taginfo [string toupper [keylget vlanmember taginfo]]
    if {"T" == $taginfo} {
        set taginfo "tagged"
    }
    if {"U" == $taginfo} {
        set taginfo "untagged"
    }

    set strOutput [ntgrCLIExecute $strDevice "show vlan $vlanid"]
    
    # modify by jim.xie for bug-892 begin
    #regsub {..More.*uit\s*$} $strOutput "" test
    regsub -all {..More..\s*or\s*\(q\)uit\s*} $strOutput "" test
   
    ntgrCLIParseCellTable $test table
    # modify by jim.xie for bug-892 end
   
    set colIf [ntgrCLICellTableColNumberFromText $table "interface"]
    set colCURRENT [ntgrCLICellTableColNumberFromText $table "current"]
    set colCONFIGURED [ntgrCLICellTableColNumberFromText $table "configured"]
    set colTAGGING [ntgrCLICellTableColNumberFromText $table "tagging"]

    set bResult 1
    set laggroupid {}
    __ConvertToLogicalIntf $strDevice iflist
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        set ifPort [lindex $iflist $i]
        if {$ifPort == "vlan 1"} {
            continue
        }
        regexp -- {(lag:)([0-9]+)} $ifPort str str1 laggroupid
        if {$laggroupid != {}} {
            set ifPort "lag $laggroupid"
        }
        set row [ntgrCLICellTableRowNumberFromText $table $ifPort $colIf]
        if {$row > 0} {
            set current [ntgrCLICellTableCellData $table $row $colCURRENT]
            set configured [ntgrCLICellTableCellData $table $row $colCONFIGURED]
            set tagging [ntgrCLICellTableCellData $table $row $colTAGGING]
            if {[string equal -nocase "include" $current]} {
                set bResult [expr $bResult && [string equal -nocase $taginfo $tagging]]
            } elseif {[string equal -nocase "include" $configured]} {
                set bResult [expr $bResult && [string equal -nocase $taginfo $tagging]]
            } else {
                set bResult 0
                break
            }
        } else {
            set bResult 0
            break
        }
    }

    set retval $bResult
    return $retval
}

proc ntgrSwitchingVLANIsNotVLANMember {strDevice vlan vlanmember} {
    set vlanid [keylget vlan vlanid]
    set iflist [keylget vlanmember iflist]

    set strOutput [ntgrCLIExecute $strDevice "show vlan $vlanid"]
    ntgrCLIParseCellTable $strOutput table
    set colIf [ntgrCLICellTableColNumberFromText $table "interface"]
    set colCURRENT [ntgrCLICellTableColNumberFromText $table "current"]
    set colCONFIGURED [ntgrCLICellTableColNumberFromText $table "configured"]
    set colTAGGING [ntgrCLICellTableColNumberFromText $table "tagging"]

    set bResult 1
    __ConvertToLogicalIntf $strDevice iflist
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        set row [ntgrCLICellTableRowNumberFromText $table [lindex $iflist $i] $colIf]
        if {$row > 0} {
            set current [ntgrCLICellTableCellData $table $row $colCURRENT]
            set configured [ntgrCLICellTableCellData $table $row $colCONFIGURED]
            set tagging [ntgrCLICellTableCellData $table $row $colTAGGING]
            if {[string equal -nocase "exclude" $current]} {
                set bResult [expr $bResult && 1]
            } elseif {[string equal -nocase "exclude" $configured]} {
                set bResult [expr $bResult && 1]
            } else {
                set bResult 0
                break
            }
        } else {
            set bResult [expr $bResult && 1]
            break
        }
    }

    set retval $bResult
    return $retval
}

proc ntgrSwitchingVLANSetPVID {strDevice iflist pvid} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "vlan pvid $pvid"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANResetPVID {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "no vlan pvid"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANGetPVID {strDevice iflist} {
    set strOutput [ntgrCLIExecute $strDevice "show vlan port all"]
    ntgrCLIParseCellTable $strOutput table
    set colIf [ntgrCLICellTableColNumberFromText $table "interface"]
    set colCONFIGURED [ntgrCLICellTableColNumberFromText $table "vlan id configured"]
    set colCURRENT [ntgrCLICellTableColNumberFromText $table "vlan id current"]

    for {set i 0} {$i < [llength $iflist]} {incr i} {
        set row [ntgrCLICellTableRowNumberFromText $table [lindex $iflist $i] $colIf]
        if {$row > 0} {
            set configured [ntgrCLICellTableCellData $table $row $colCONFIGURED]
            set current [ntgrCLICellTableCellData $table $row $colCURRENT]
            keylset ifpvidlist [lindex $iflist $i] $configured
        }
    }

    return $ifpvidlist

}

proc ntgrSwitchingVLANSetAllPortsPVID {strDevice pvid iflist_exclude} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIQuery $strDevice "vlan port pvid all "
    ntgrCLIExecute $strDevice "exit"

    set lstPorts  [ntgrSwitchingPortGetAllPorts $strDevice]
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $lstPorts]} {incr i} {
        if {[lsearch -exact $iflist_exclude [lindex $lstPorts $i]] >= 0} {
            continue
        }
        ntgrCLIExecute $strDevice "interface [lindex $lstPorts $i]"
        ntgrCLIExecute $strDevice "vlan pvid $pvid"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANResetAllPortsPVID {strDevice iflist_exclude} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIQuery $strDevice "no vlan port pvid all "
    ntgrCLIExecute $strDevice "exit"

    set lstPorts  [ntgrSwitchingPortGetAllPorts $strDevice]
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $lstPorts]} {incr i} {
        if {[lsearch -exact $iflist_exclude [lindex $lstPorts $i]] >= 0} {
            continue
        }
        ntgrCLIExecute $strDevice "interface [lindex $lstPorts $i]"
        ntgrCLIExecute $strDevice "no vlan pvid"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANGetAllPortsPVID {strDevice iflist_exclude} {
    set strOutput [ntgrCLIExecute $strDevice "show vlan port all"]
    ntgrCLIParseCellTable $strOutput table
    set colIf [ntgrCLICellTableColNumberFromText $table "interface"]
    set colCONFIGURED [ntgrCLICellTableColNumberFromText $table "vlan id configured"]
    set colCURRENT [ntgrCLICellTableColNumberFromText $table "vlan id current"]

    set lstPorts  [ntgrSwitchingPortGetAllPorts $strDevice]
    for {set i 0} {$i < [llength $lstPorts]} {incr i} {
        if {[lsearch -exact $iflist_exclude [lindex $lstPorts $i]] >= 0} {
            continue
        }
        set row [ntgrCLICellTableRowNumberFromText $table [lindex $lstPorts $i] $colIf]
        if {$row > 0} {
            set configured [ntgrCLICellTableCellData $table $row $colCONFIGURED]
            set current [ntgrCLICellTableCellData $table $row $colCURRENT]
            keylset ifpvidlist [lindex $lstPorts $i] $configured
        }
    }

    return $ifpvidlist

}

proc ntgrSwitchingVLANBatchAddVLAN {strDevice vlan_templete intStart intCount} {
    set intVLANs 0
    for {set i 0} {$i < $intCount} {incr i} {
        keylset vlan \
            vlanid [regsub "#vlanid#" [keylget vlan_templete vlanid] [expr $intStart + $i]]
        if {[keylget vlan_templete vlanname {}]} {
            keylset vlan \
                vlanname [regsub "#vlanid#" [keylget vlan_templete vlanname] [expr $intStart + $i]]
        }
        ntgrSwitchingVLANAddVLAN $strDevice $vlan
        if {[keylget vlan_templete vlanmember {}]} {
            set vlanmember [keylget vlan_templete vlanmember]
            ntgrSwitchingVLANAddVLANMember $strDevice $vlan $vlanmember
        }
		# add by jim.xie for scalability module begin
		if {[keylget vlan_templete vlanip {}]} {
            set vlanip [keylget vlan_templete vlanip]
            keylset vlanipconfig \
			    vlanid [regsub "#vlanid#" [keylget vlan_templete vlanid] [expr $intStart + $i]] \
				ipaddr      "$vlanip" \
                netmask     "255.255.255.0"
			ntgrRoutingVLANEnableRouting $strDevice $vlan
			ntgrRouingVLANAddIPConfig $strDevice $vlan $vlanipconfig
        }
		# add by jim.xie for scalability module end
        incr intVLANs
    }

    return $intVLANs
}

proc ntgrSwitchingVLANBatchDeleteVLAN {strDevice vlan_templete intStart intCount} {
    set intVLANs 0
    for {set i 0} {$i < $intCount} {incr i} {
        keylset vlan \
            vlanid [regsub "#vlanid#" [keylget vlan_templete vlanid] [expr $intStart + $i]]
		# add by jim.xie for scalability module begin	
		if {[keylget vlan_templete vlanip {}]} {	
		    set vlanip [keylget vlan_templete vlanip]
            keylset vlanipconfig \
			    vlanid [regsub "#vlanid#" [keylget vlan_templete vlanid] [expr $intStart + $i]] \
				ipaddr      "$vlanip" \
                netmask     "255.255.255.0"
	        ntgrRouingVLANDeleteIPConfig $strDevice $vlan $vlanipconfig
            ntgrRoutingVLANDisableRouting $strDevice $vlan
		}
		# add by jim.xie for scalability module end
        ntgrSwitchingVLANDeleteVLAN $strDevice $vlan
        incr intVLANs
    }

    return $intVLANs
}

#
# MAC VLAN
#
proc ntgrSwitchingMACVLANAddAssociation {strDevice macvlan} {
    set vlanid [keylget macvlan vlanid]
    set mac [keylget macvlan mac]
    ntgrCLIExecute $strDevice "vlan database"
    ntgrCLIExecute $strDevice "vlan association mac $mac $vlanid"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingMACVLANDeleteAssociation {strDevice macvlan} {
    set vlanid [keylget macvlan vlanid]
    set mac [keylget macvlan mac]
    ntgrCLIExecute $strDevice "vlan database"
    ntgrCLIExecute $strDevice "no vlan association mac $mac"
    ntgrCLIExecute $strDevice "exit"
}

#
# GARP
#
proc ntgrSwitchingGARPGlobalEnableGVRP {strDevice} {
    ntgrCLIExecute $strDevice "set gvrp adminmode"
}

proc ntgrSwitchingGARPGlobalDisableGVRP {strDevice} {
    ntgrCLIExecute $strDevice "no set gvrp adminmode"
}

proc ntgrSwitchingGARPGlobalEnableGMRP {strDevice} {
    ntgrCLIExecute $strDevice "set gmrp adminmode"
}

proc ntgrSwitchingGARPGlobalDisableGMRP {strDevice} {
    ntgrCLIExecute $strDevice "no set gmrp adminmode"
}

proc ntgrSwitchingGARPPortEnableGVRP {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "set gvrp interfacemode"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingGARPPortDisableGVRP {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "no set gvrp interfacemode"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingGARPPortEnableGMRP {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "set gmrp interfacemode"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingGARPPortDisableGMRP {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "no set gmrp interfacemode"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

#protcol vlan
proc ntgrSwitchingVLANAddProtocolVLAN {strDevice protovlan} {
     set groupName [keylget protovlan groupname]
     set groupId   [keylget protovlan groupid]
     set proto     [keylget protovlan proto]
     set vlanId    [keylget protovlan vlanid]
     ntgrCLIExecute $strDevice "configure"
     ntgrCLIExecute $strDevice "vlan protocol group $groupId"
     ntgrCLIExecute $strDevice "vlan protocol group name $groupId $groupName"
     ntgrCLIExecute $strDevice "vlan protocol group add protocol $groupId ethertype $proto"
     ntgrCLIExecute $strDevice "exit"
     ntgrCLIExecute $strDevice "vlan database"
     ntgrCLIExecute $strDevice "protocol group $groupId $vlanId"
     ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANGetProtocolVLANbyVlanID {strDevice protovlan} {
    set str {}
    set str1 {}
    set strOutput [ntgrCLIExecute $strDevice "show running-config"]
    set vlanId    [keylget protovlan vlanid]
    set outputlist [split $strOutput "\n"]
	  foreach output $outputlist {  
        regexp -nocase "protocol group (\[0-9\]*) $vlanId" $output str str1
        if {$str != {}} {break}
    }
    return $str1
}   

proc ntgrSwitchingVLANChkProtocolVLANGroupIDonInterface {strDevice protovlan} {
    set str {}
    set str1 {}
    set strOutput [ntgrCLIExecute $strDevice "show running-config"]
    set groupId   [keylget protovlan groupid]
    set outputlist [split $strOutput "\n"]
	  foreach output $outputlist {  
        regexp -nocase "protocol vlan group $groupId" $output str
        if {$str != {}} {set str $groupId; break}
    }
    return $str
}   

proc ntgrSwitchingVLANGetProtocolVLANName {strDevice protovlan} {
    set str {}
    set str1 {}
    set strOutput [ntgrCLIExecute $strDevice "show running-config"]
    set groupId   [keylget protovlan groupid]
    set outputlist [split $strOutput "\n"]
	  foreach output $outputlist {
        regexp -nocase "vlan protocol group name $groupId \"(\[0-9a-zA-H\]*)\"" $output str str1
        if {$str != {}} {break}
    }
    return $str1
}   

proc ntgrSwitchingVLANDelProtocolVLAN {strDevice protovlan} { 
    set groupId   [keylget protovlan groupid]
    set vlanId    [keylget protovlan vlanid]
    ntgrCLIExecute $strDevice "vlan database"
    ntgrCLIExecute $strDevice "no protocol group $groupId $vlanId"
    ntgrCLIExecute $strDevice "exit"
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "no vlan protocol group $groupId"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANAddProtocolVLANmembership {strDevice protovlan member} {
    set groupId   [keylget protovlan groupid]
    set iflist [keylget member iflist]
    ntgrCLIExecute $strDevice "configure"
    foreach ifport $iflist {
         ntgrCLIExecute $strDevice "interface $ifport"
         ntgrCLIExecute $strDevice "protocol vlan group $groupId"
         ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANDelProtocolVLANmembership {strDevice protovlan member} {
    set groupId   [keylget protovlan groupid]
    set iflist [keylget member iflist]
    ntgrCLIExecute $strDevice "configure"
    foreach ifport $iflist {
         ntgrCLIExecute $strDevice "interface $ifport"
         ntgrCLIExecute $strDevice "no protocol vlan group $groupId"
         ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANSetDualVlan {strDevice iFace dType action} {
    if {$dType != {}} {
    
    }
    set laggroupid {}
    ntgrCLIExecute $strDevice "configure"
        foreach ifport $iFace {
          regexp -- {(lag:)([0-9]+)} $ifport str str1 laggroupid
          if { $laggroupid!={} } {
             ntgrCLIExecute $strDevice "exit"
             set iface [ntgrSwitchingLAGGetLAGLogicalIntf $strDevice $laggroupid]
             ntgrCLIExecute $strDevice "configure"
             ntgrCLIExecute $strDevice "interface $iface"
          } else {
             ntgrCLIExecute $strDevice "interface $ifport"
          }
           if {$action == "Enable"} {
                  ntgrCLIExecute $strDevice "mode dvlan-tunnel"
           } else {
                 ntgrCLIExecute $strDevice "no mode dvlan-tunnel"
           }
           ntgrCLIExecute $strDevice "exit"
   }
   ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANSetAcceptableFrameTypeAsAdmitAll {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "vlan acceptframe all"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANSetAcceptableFrameTypeAsVlanOnly {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "vlan acceptframe vlanonly"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANEnableIngressFiltering {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "vlan ingressfilter"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANDisableIngressFiltering {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "no vlan ingressfilter"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANDisableIngressFiltering {strDevice iflist} {
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        ntgrCLIExecute $strDevice "no vlan ingressfilter"
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANAddPvid {strDevice vlan vlanmember} {
    set vlanid [keylget vlan vlanid]
    ntgrCLIExecute $strDevice "vlan database"
    ntgrCLIExecute $strDevice "vlan $vlanid"
    if {[keylget vlan vlanname {}]} {
        set vlanname [keylget vlan vlanname]
        ntgrCLIExecute $strDevice "vlan name $vlanid \"$vlanname\""
    }
    ntgrCLIExecute $strDevice "exit"
    if {[keylget vlanmember pvid {}]} {
        set pvid [keylget vlanmember pvid]
        ntgrSwitchingVLANSetPVID $strDevice [keylget vlanmember iflist] $pvid
    }
}


#*******************************************************************************
#  Function Name	: ntgrSwitchingVLANChangeVLANTagModel
#
#  Description    	: This function is used to change the tag model of the port
#         
#  Usage          	: ntgrSwitchingVLANChangeVLANTagModel <switch_name>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc ntgrSwitchingVLANChangeVLANTagModel {switch_name vlanid iflist taginfo} {
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }

    set laggroupid {}
	expect -i $connection_id -re "#"
	exp_send -i $connection_id "configure\r"
	sleep 1
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        regexp -- {(lag:)([0-9]+)} $iflist str str1 laggroupid
        if { $laggroupid!={} } {
            set iface [ntgrSwitchingLAGGetLAGLogicalIntf $switch_name $laggroupid]
        } else {
            set iface [lindex $iflist $i]
        }
		exp_send -i $connection_id "interface $iface\r"
        sleep 1
        if {"T" == $taginfo} {
		    exp_send -i $connection_id "vlan tagging $vlanid\r"
            sleep 1
        }
        if {"U" == $taginfo} {
		    exp_send -i $connection_id "no vlan tagging $vlanid\r"
            sleep 1
        }
        exp_send -i $connection_id "exit\r"
        sleep 1
    }
    exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: ntgrSwitchingVLANAddVLANMember_Trunk_Mode
#
#  Description    	: This function is called to include a vlan on a interface using vlan trunk
#         
#  Usage          	: ntgrSwitchingVLANAddVLANMember_Trunk_Mode <switch_name> <vlan> <vlanmember>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc ntgrSwitchingVLANAddVLANMember_Trunk_Mode {strDevice vlan vlanmember} {
    global SwitchPortMode
    set vlanid [keylget vlan vlanid]
    set iflist [keylget vlanmember iflist]
    set taginfo [string toupper [keylget vlanmember taginfo]]
    set laggroupid {}
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
        regexp -- {(lag:)([0-9]+)} $iflist str str1 laggroupid
        if { $laggroupid!={} } {
             ntgrCLIExecute $strDevice "exit"
             set iface [ntgrSwitchingLAGGetLAGLogicalIntf $strDevice $laggroupid]
             ntgrCLIExecute $strDevice "configure"
             ntgrCLIExecute $strDevice "interface $iface"
        } else {
          ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        }
        #ntgrCLIExecute $strDevice "vlan participation include $vlanid"
        if {"T" == $taginfo} {
            ntgrCLIExecute $strDevice "switchport mode trunk"
		    set trunk_allow "1,$vlanid"
		    ntgrCLIExecute $strDevice "switchport trunk allowed vlan $trunk_allow"
        }
        if {"U" == $taginfo} {
            ntgrCLIExecute $strDevice "no vlan tagging $vlanid"
			if {$mode == "trunk"} {
		        ntgrCLIExecute $strDevice "switchport mode trunk"
		        ntgrCLIExecute $strDevice "switchport trunk native vlan $vlanid"
		        set trunk_allow "1,$vlanid"
		        ntgrCLIExecute $strDevice "switchport trunk allowed vlan $trunk_allow"
		    } elseif {$mode == "access"} {
                ntgrCLIExecute $strDevice "switchport mode access"
			    ntgrCLIExecute $strDevice "switchport access vlan $vlanid"
		    } else {
		        Netgear_log_file "Error: " "Variable mode  only support trunk/access"
		    }
        }
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"

}

#*******************************************************************************
#  Function Name	: ntgrSwitchingVLANDeleteVLANMember_Trunk_Mode
#
#  Description    	: This function is called to delete a vlan on a interface using vlan trunk
#         
#  Usage          	: ntgrSwitchingVLANDeleteVLANMember_Trunk_Mode <switch_name> <vlan> <vlanmember>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc ntgrSwitchingVLANDeleteVLANMember_Trunk_Mode {strDevice vlan vlanmember} {
    global SwitchPortMode
    set vlanid [keylget vlan vlanid]
    set iflist [keylget vlanmember iflist]
    set taginfo [string toupper [keylget vlanmember taginfo]]

    set laggroupid {}
    ntgrCLIExecute $strDevice "configure"
    for {set i 0} {$i < [llength $iflist]} {incr i} {
         regexp -- {(lag:)([0-9]+)} $iflist str str1 laggroupid
        if { $laggroupid!={} } {
             ntgrCLIExecute $strDevice "exit"
             set iface [ntgrSwitchingLAGGetLAGLogicalIntf $strDevice $laggroupid]
             ntgrCLIExecute $strDevice "configure"
             ntgrCLIExecute $strDevice "interface $iface"
        } else {
          ntgrCLIExecute $strDevice "interface [lindex $iflist $i]"
        }
		if {$SwitchPortMode == "trunk"} {
            ntgrCLIExecute $strDevice "switchport trunk native vlan 1"
            ntgrCLIExecute $strDevice "switchport trunk allowed vlan all"
            ntgrCLIExecute $strDevice "no switchport mode"
        } elseif {$SwitchPortMode == "access"} {
            ntgrCLIExecute $strDevice "switchport access vlan 1"
            ntgrCLIExecute $strDevice "no switchport mode"
        } else {
            Netgear_log_file "Error: " "Variable SwitchPortMode in file:config_port.xml only support trunk/access/general"
        }
        ntgrCLIExecute $strDevice "exit"
    }
    ntgrCLIExecute $strDevice "exit"
}

#*******************************************************************************
#  Function Name	: ntgrSwitchingVLANAddSubnetVlan
#
#  Description    	: This function is called to add subnet based vlan
#         
#  Usage          	: ntgrSwitchingVLANAddSubnetVlan <switch_name> <subnetIP> <subnetMask> <vlanid> 
#
#  Author           : jim.xie
#
#*******************************************************************************
proc ntgrSwitchingVLANAddSubnetVlan {strDevice subnetIP subnetMask vlanid} {
    ntgrCLIExecute $strDevice "vlan database"
    ntgrCLIExecute $strDevice "vlan association subnet $subnetIP $subnetMask $vlanid"
    ntgrCLIExecute $strDevice "exit"
}

#*******************************************************************************
#  Function Name	: ntgrSwitchingVLANDeleteSubnetVlan
#
#  Description    	: This function is called to delete subnet based vlan
#         
#  Usage          	: ntgrSwitchingVLANDeleteSubnetVlan <switch_name> <subnetIP> <subnetMask>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc ntgrSwitchingVLANDeleteSubnetVlan {strDevice subnetIP subnetMask} {
    ntgrCLIExecute $strDevice "vlan database"
    ntgrCLIExecute $strDevice "no vlan association subnet $subnetIP $subnetMask"
    ntgrCLIExecute $strDevice "exit"
}

proc ntgrSwitchingVLANExists {strDevice vlan} {
    set vlanid [keylget vlan vlanid]
    set strOutput [ntgrCLIExecute $strDevice "show vlan $vlanid"]
    if {[regexp -nocase {not.+exist} $strOutput]} {
        return 0
    } else {
        return 1
    }
}