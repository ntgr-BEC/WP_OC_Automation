####################################################################################
#  File Name   : cal-ntgr-snmp.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for SNMP configuration.
#
#  History     :
#        Date          Programmer         Description
#        Mar 12, 2007  Scott Zhang        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALConfigSnmpSysName
#
#  Description    : This function is called to configure SNMP system name.
#
#  Usage          : CALConfigSnmpSysName <switch_name sysname>
#
#*******************************************************************************
proc CALConfigSnmpSysName {switch_name sysname} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigSnmpSysName $switch_name $sysname
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigSnmpLocation
#
#  Description    : This function is called to configure SNMP location infor.
#
#  Usage          : CALConfigSnmpLocation <switch_name location>
#
#*******************************************************************************
proc CALConfigSnmpLocation {switch_name location} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigSnmpLocation $switch_name $location
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigSnmpContact
#
#  Description    : This function is called to configure SNMP system contact.
#
#  Usage          : CALConfigSnmpContact <switch_name contact>
#
#*******************************************************************************
proc CALConfigSnmpContact {switch_name contact} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigSnmpContact $switch_name $contact
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigCommunity
#
#  Description    : This function is called to configure SNMP community.
#
#  Usage          : CALConfigCommunity <switch_name community>
#
#*******************************************************************************
proc CALConfigCommunity {switch_name community} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigCommunity $switch_name $community
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDeleteCommunity
#
#  Description    : This function is called to delete SNMP community.
#
#  Usage          : CALDeleteCommunity <switch_name community>
#
#*******************************************************************************
proc CALDeleteCommunity {switch_name community} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDeleteCommunity $switch_name $community
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetCommunityReadOnly
#
#  Description    : This function is called to configure SNMP community to read-only.
#
#  Usage          : CALSetCommunityReadOnly <switch_name community>
#
#*******************************************************************************
proc CALSetCommunityReadOnly {switch_name community} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetCommunityReadOnly $switch_name $community
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetCommunityReadWrite
#
#  Description    : This function is called to configure SNMP community to read-write.
#
#  Usage          : CALSetCommunityReadWrite <switch_name community>
#
#*******************************************************************************
proc CALSetCommunityReadWrite {switch_name community} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetCommunityReadWrite $switch_name $community
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALEnableCommunity
#
#  Description    : This function is called to enable SNMP community.
#
#  Usage          : CALEnableCommunity <switch_name community>
#
#*******************************************************************************
proc CALEnableCommunity {switch_name community} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableCommunity $switch_name $community
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableCommunity
#
#  Description    : This function is called to disable SNMP community.
#
#  Usage          : CALDisableCommunity <switch_name community>
#
#*******************************************************************************
proc CALDisableCommunity {switch_name community} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableCommunity $switch_name $community
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALLimitSnmpClient
#
#  Description    : This function is called to limit client access with SNMP community.
#
#  Usage          : CALLimitSnmpClient <switch_name community ip mask>
#
#*******************************************************************************
proc CALLimitSnmpClient {switch_name community ip mask} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrLimitSnmpClient $switch_name $community $ip $mask
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALAddTrapReceiver
#
#  Description    : This function is called to add a SNMP trap receiver.
#
#  Usage          : CALAddTrapReceiver <switch_name community ip {ver {}}>
#
#*******************************************************************************
proc CALAddTrapReceiver {switch_name community ip {ver {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddTrapReceiver $switch_name $community $ip $ver
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDelTrapReceiver
#
#  Description    : This function is called to delete a SNMP trap receiver.
#
#  Usage          : CALDelTrapReceiver <switch_name community ip>
#
#*******************************************************************************
proc CALDelTrapReceiver {switch_name community ip {ver {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelTrapReceiver $switch_name $community $ip $ver
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALEnableTrapReceiver
#
#  Description    : This function is called to enable a SNMP trap receiver.
#
#  Usage          : CALEnableTrapReceiver <switch_name community ip>
#
#*******************************************************************************
proc CALEnableTrapReceiver {switch_name community ip} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableTrapReceiver $switch_name $community $ip
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableTrapReceiver
#
#  Description    : This function is called to disable a SNMP trap receiver.
#
#  Usage          : CALDisableTrapReceiver <switch_name community ip>
#
#*******************************************************************************
proc CALDisableTrapReceiver {switch_name community ip} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableTrapReceiver $switch_name $community $ip
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALModifyTrapReceiver
#
#  Description    : This function is called to modify a SNMP trap receiver.
#
#  Usage          : CALModifyTrapReceiver <switch_name community oldip newip>
#
#*******************************************************************************
proc CALModifyTrapReceiver {switch_name community oldip newip} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrModifyTrapReceiver $switch_name $community $oldip $newip
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALModifyTrapReceiverVer
#
#  Description    : This function is called to modify a SNMP trap receiver.
#
#  Usage          : CALModifyTrapReceiverVer <switch_name community ip ver>
#
#*******************************************************************************
proc CALModifyTrapReceiverVer {switch_name community ip ver} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrModifyTrapReceiverVer $switch_name $community $ip $ver
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALEnableAuthTrap
#
#  Description    : This function is called to enable authenticaion trap.
#
#  Usage          : CALEnableAuthTrap <switch_name>
#
#*******************************************************************************
proc CALEnableAuthTrap {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableAuthTrap $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableAuthTrap
#
#  Description    : This function is called to disable authenticaion trap.
#
#  Usage          : CALDisableAuthTrap <switch_name>
#
#*******************************************************************************
proc CALDisableAuthTrap {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableAuthTrap $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALEnableTrapLink
#
#  Description    : This function is called to enable trap link up/down.
#
#  Usage          : CALEnableTrapLink <switch_name>
#
#*******************************************************************************
proc CALEnableTrapLink {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableTrapLink $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableTrapLink
#
#  Description    : This function is called to disable trap link up/down.
#
#  Usage          : CALDisableTrapLink <switch_name>
#
#*******************************************************************************
proc CALDisableTrapLink {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableTrapLink $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALEnableTrapPort
#
#  Description    : This function is called to enable trap link up/down on a port.
#
#  Usage          : CALEnableTrapPort <switch_name {port {}}>, 
#                   if omit port, it apply to all ports
#
#*******************************************************************************
proc CALEnableTrapPort {switch_name {port {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableTrapPort $switch_name $port
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableTrapPort
#
#  Description    : This function is called to disable trap link up/down on a port.
#
#  Usage          : CALDisableTrapPort <switch_name {port {}}>, 
#                   if omit port, it apply to all ports
#
#*******************************************************************************
proc CALDisableTrapPort {switch_name {port {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableTrapPort $switch_name $port
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALEnableTrapMultiUser
#
#  Description    : This function is called to enable trap multi users login.
#
#  Usage          : CALEnableTrapMultiUser <switch_name>
#
#*******************************************************************************
proc CALEnableTrapMultiUser {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableTrapMultiUser $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableTrapMultiUser
#
#  Description    : This function is called to disable trap multi users login.
#
#  Usage          : CALDisableTrapMultiUser <switch_name>
#
#*******************************************************************************
proc CALDisableTrapMultiUser {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableTrapMultiUser $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALEnableTrapSTP
#
#  Description    : This function is called to enable trap spanning-tree changing.
#
#  Usage          : CALEnableTrapSTP <switch_name>
#
#*******************************************************************************
proc CALEnableTrapSTP {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableTrapSTP $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableTrapSTP
#
#  Description    : This function is called to Disable trap spanning-tree changing.
#
#  Usage          : CALDisableTrapSTP <switch_name>
#
#*******************************************************************************
proc CALDisableTrapSTP {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableTrapSTP $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALEnableTrapViolation
#
#  Description    : This function is called to enable trap MAC violation.
#
#  Usage          : CALEnableTrapViolation <switch_name port>
#
#*******************************************************************************
proc CALEnableTrapViolation {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableTrapViolation $switch_name $port
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableTrapViolation
#
#  Description    : This function is called to disable trap MAC violation.
#
#  Usage          : CALDisableTrapViolation <switch_name port>
#
#*******************************************************************************
proc CALDisableTrapViolation {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableTrapViolation $switch_name $port
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-snmp.tcl" "Switch not defined"
        }
    }
}

