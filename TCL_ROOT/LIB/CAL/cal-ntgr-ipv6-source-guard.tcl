####################################################################################
#  File Name   : cal-ntgr-ipv6-source-guard.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for  ip source guard configuration.
#
#  History     :
#        Date          Programmer         Description
#        May 19 2015  Jason Li            Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALIPv6SGAddStaticBindingConfig
#
#  Description    : This function is called to add static binding configuration.
#
#  Usage          : CALIPv6SGAddStaticBindingConfig <switch_name>
#
#*******************************************************************************
proc CALIPv6SGAddStaticBindingConfig {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPv6SGAddStaticBindingConfig $switch_name 
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
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIPv6SGDelStaticBindingConfig
#
#  Description    : This function is called to delete static binding configuration.
#
#  Usage          : CALIPv6SGDelStaticBindingConfig <switch_name>
#
#*******************************************************************************
proc CALIPv6SGDelStaticBindingConfig {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPv6SGDelStaticBindingConfig $switch_name 
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
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALIPv6SGEnableMode
#
#  Description    : This function is called to enable ip source guard mode.
#
#  Usage          : CALIPv6SGEnableMode <switch_name>
#
#*******************************************************************************
proc CALIPv6SGEnableMode {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPv6SGEnableMode $switch_name 
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
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALIPv6SGDisableMode
#
#  Description    : This function is called to disable ip source guard mode.
#
#  Usage          : CALIPv6SGDisableMode <switch_name>
#
#*******************************************************************************
proc CALIPv6SGDisableMode {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPv6SGDisableMode $switch_name 
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
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALIPv6SGEnablePortSecurity
#
#  Description    : This function is called to enable port security.
#
#  Usage          : CALIPv6SGEnablePortSecurity <switch_name>
#
#*******************************************************************************
proc CALIPv6SGEnablePortSecurity {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPv6SGEnablePortSecurity $switch_name 
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
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALIPv6SGDisablePortSecurity
#
#  Description    : This function is called to disable port security.
#
#  Usage          : CALIPv6SGDisablePortSecurity <switch_name>
#
#*******************************************************************************
proc CALIPv6SGDisablePortSecurity {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPv6SGDisablePortSecurity $switch_name 
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
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIPv6SGClearBindingConfiguration
#
#  Description    : This function is called to disable ip source guard mode.
#
#  Usage          : CALIPv6SGClearBindingConfiguration <switch_name>
#
#*******************************************************************************
proc CALIPv6SGClearBindingConfiguration {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPv6SGClearBindingConfiguration $switch_name 
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
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALCheckIPv6SGEntry
#
#  Description    : This function is called to .
#
#  Usage          : CALCheckIPv6SGEntry <switch_name>
#
#*******************************************************************************
proc CALCheckIPv6SGEntry {switch_name mac_list {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIPv6SGEntry $switch_name $mac_list $notin
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
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALSearchIPv6SGAddrByMAC
#
#  Description    : This function is called to .
#
#  Usage          : CALSearchIPv6SGAddrByMAC <switch_name> <mac>
#
#*******************************************************************************
proc CALSearchIPv6SGAddrByMAC {switch_name mac} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSearchIPv6SGAddrByMAC $switch_name $mac
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
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPv6SnoopingIntfTrustEnable
#
#  Description    : This function is called to enable igmp snooping interface trust on specified
#                   switch.
#
#  Usage          : CALDHCPv6SnoopingIntfTrustEnable <switch_name> <port>
#
#*******************************************************************************
proc CALDHCPv6SnoopingIntfTrustEnable {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPv6SnoopingIntfTrustEnable $switch_name $port
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingIntfTrustDisable
#
#  Description    : This function is called to disable igmp snooping interface trust on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingIntfTrustDisable <switch_name> <port>
#
#*******************************************************************************
proc CALDHCPv6SnoopingIntfTrustDisable {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPv6SnoopingIntfTrustDisable $switch_name $port
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPv6SnoopingVlanEnable
#
#  Description    : This function is called to enable igmp snooping vlan on specified
#                   switch.
#
#  Usage          : CALDHCPv6SnoopingVlanEnable <switch_name> <vlan_id>
#
#*******************************************************************************
proc CALDHCPv6SnoopingVlanEnable {switch_name vlan_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPv6SnoopingVlanEnable $switch_name $vlan_id
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingVlanDisable
#
#  Description    : This function is called to disable igmp snooping vlan on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingVlanDisable <switch_name> <vlan_id>
#
#*******************************************************************************
proc CALDHCPv6SnoopingVlanDisable {switch_name vlan_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPv6SnoopingVlanDisable $switch_name $vlan_id
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPv6SnoopingEnable
#
#  Description    : This function is called to enable igmp snooping mac address validation on specified
#                   switch.
#
#  Usage          : CALDHCPv6SnoopingEnable <switch_name>
#
#*******************************************************************************
proc CALDHCPv6SnoopingEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPv6SnoopingEnable $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPv6SnoopingDisable
#
#  Description    : This function is called to disable igmp snooping mac address validation on specified
#                   switch.
#
#  Usage          : CALDHCPv6SnoopingDisable <switch_name>
#
#*******************************************************************************
proc CALDHCPv6SnoopingDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPv6SnoopingDisable $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ipv6-source-guard.tcl" "Switch not defined"
        }
    }
}








