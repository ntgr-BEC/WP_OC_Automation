####################################################################################
#  File Name   : cal-ntgr-port-secure.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for port secure configuration.
#
#  History     :
#        Date          Programmer         Description
#        Mar 15, 2007  Scott Zhang        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALEnablePortSecurity
#
#  Description    : This function is called to enable port security globally/port level.
#
#  Usage          : CALEnablePortSecurity <switch_name {port {}}>
#                   Note that if port == {}, globally enable port security, it always be
#                   done for port security.
#
#*******************************************************************************
proc CALEnablePortSecurity {switch_name {port {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnablePortSecurity $switch_name $port
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
            Netgear_log_file "cal-ntgr-port-secure.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisablePortSecurity
#
#  Description    : This function is called to disable port security globally/port level.
#
#  Usage          : CALDisablePortSecurity <switch_name {port {}}>
#                   Note that if port == {}, globally disable port security.
#
#*******************************************************************************
proc CALDisablePortSecurity {switch_name {port {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisablePortSecurity $switch_name $port
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
            Netgear_log_file "cal-ntgr-port-secure.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetMaxDynamic
#
#  Description    : This function is called to set port MAC dynamic limit.
#
#  Usage          : CALSetMaxDynamic <switch_name port max>
#
#*******************************************************************************
proc CALSetMaxDynamic {switch_name port max} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetMaxDynamic $switch_name $port $max
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
            Netgear_log_file "cal-ntgr-port-secure.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALResetMaxDynamic
#
#  Description    : This function is called to reset port MAC dynamic limit to default.
#
#  Usage          : CALResetMaxDynamic <switch_name port>
#
#*******************************************************************************
proc CALResetMaxDynamic {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrResetMaxDynamic $switch_name $port
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
            Netgear_log_file "cal-ntgr-port-secure.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetMaxStatic
#
#  Description    : This function is called to set port MAC static limit.
#
#  Usage          : CALSetMaxStatic <switch_name port max>
#
#*******************************************************************************
proc CALSetMaxStatic {switch_name port max} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetMaxStatic $switch_name $port $max
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
            Netgear_log_file "cal-ntgr-port-secure.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALResetMaxStatic
#
#  Description    : This function is called to reset port MAC static limit to default.
#
#  Usage          : CALResetMaxStatic <switch_name port>
#
#*******************************************************************************
proc CALResetMaxStatic {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrResetMaxStatic $switch_name $port
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
            Netgear_log_file "cal-ntgr-port-secure.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigStaticAddr
#
#  Description    : This function is called to configure static port security.
#                   If addr == move, then move the dynamic to static.
#
#  Usage          : CALConfigStaticAddr <switch_name port addr>
#
#*******************************************************************************
proc CALConfigStaticAddr {switch_name port addr vlan_id} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigStaticAddr $switch_name $port $addr $vlan_id
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
            Netgear_log_file "cal-ntgr-port-secure.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDeleteStaticAddr
#
#  Description    : This function is called to configure static port security.
#
#  Usage          : CALDeleteStaticAddr <switch_name port addr>
#
#*******************************************************************************
proc CALDeleteStaticAddr {switch_name port addr vlan_id} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDeleteStaticAddr $switch_name $port $addr $vlan_id
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
            Netgear_log_file "cal-ntgr-port-secure.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMoveDynamicAddr
#
#  Description    : This function is called to move dynamic mac-address to static.
#
#  Usage          : CALMoveDynamicAddr <switch_name port addr>
#
#*******************************************************************************
proc CALMoveDynamicAddr {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMoveDynamicAddr $switch_name $port
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
            Netgear_log_file "cal-ntgr-port-secure.tcl" "Switch not defined"
        }
    }
}