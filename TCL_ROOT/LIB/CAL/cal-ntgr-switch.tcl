####################################################################################
#  File Name   : cal-ntgr-switch.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for switch configuration.
#
#  History     :
#        Date          Programmer         Description
#        Jul 29, 2011  Tony Jing          Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALSwitchRenumber
#
#  Description    : This function is called to renumber switch unit id on specified
#                   switch.
#
#  Usage          : CALSwitchRenumber <switch_name> <fromunit> <tounit>
#
#*******************************************************************************
proc CALSwitchRenumber {switch_name fromunit tounit} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSwitchRenumber $switch_name $fromunit $tounit
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
            Netgear_log_file "cal-ntgr-switch.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSwitchSetPriority
#
#  Description    : This function is called to set switch priority on specified
#                   switch.
#
#  Usage          : CALSwitchSetPriority <switch_name> <unitid> <priority>
#
#*******************************************************************************
proc CALSwitchSetPriority {switch_name unitid priority} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSwitchSetPriority $switch_name $unitid $priority
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
            Netgear_log_file "cal-ntgr-switch.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSwitchResetPriority
#
#  Description    : This function is called to reset switch priority on specified
#                   switch.
#
#  Usage          : CALSwitchResetPriority <switch_name> <unitid>
#
#*******************************************************************************
proc CALSwitchResetPriority {switch_name unitid} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSwitchResetPriority $switch_name $unitid
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
            Netgear_log_file "cal-ntgr-switch.tcl" "Switch not defined"
        }
    }
}




#*******************************************************************************
#
#  Function Name  : CALSwitchGetPriority
#
#  Description    : This function is used to get switch priority.
#
#  Usage          : CALSwitchGetPriority <switch_name> <unitid>
#
#*******************************************************************************
proc CALSwitchGetPriority {switch_name unitid} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSwitchGetPriority $switch_name $unitid
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
            Netgear_log_file "cal-ntgr-switch.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSwitchGetSerialNumber
#
#  Description    : This function is used to get switch serial number.
#
#  Usage          : CALSwitchGetSerialNumber <switch_name> <unitid>
#
#*******************************************************************************
proc CALSwitchGetSerialNumber {switch_name unitid} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSwitchGetSerialNumber $switch_name $unitid
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
            Netgear_log_file "cal-ntgr-switch.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSwitchGetSwitchStatus
#
#  Description    : This function is used to get switch status.
#
#  Usage          : CALSwitchGetSwitchStatus <switch_name> <unitid>
#
#*******************************************************************************
proc CALSwitchGetSwitchStatus {switch_name unitid} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSwitchGetSwitchStatus $switch_name $unitid
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
            Netgear_log_file "cal-ntgr-switch.tcl" "Switch not defined"
        }
    }
}
