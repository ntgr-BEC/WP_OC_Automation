####################################################################################
#  File Name   : cal-ntgr-boot.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for boot.
#
#  History     :
#        Date          Programmer         Description
#        15-Jul-11     Tony               Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALBootAutoCopySwEnable
#
#  Description    : This function is called to enable boot auto-copy-sw
#
#  Usage          : CALBootAutoCopySwEnable <switch_name>
#
#*******************************************************************************
proc CALBootAutoCopySwEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrBootAutoCopySwEnable $switch_name
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
            Netgear_log_file "cal-ntgr-boot.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALBootAutoCopySwDisable
#
#  Description    : This function is called to diable boot auto-copy-sw
#
#  Usage          : CALBootAutoCopySwDisable <switch_name>
#
#*******************************************************************************
proc CALBootAutoCopySwDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrBootAutoCopySwDisable $switch_name
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
            Netgear_log_file "cal-ntgr-boot.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALBootAutoCopySwDowngradeEnable
#
#  Description    : This function is called to enable boot auto-copy-sw all-downgrade
#
#  Usage          : CALBootAutoCopySwDowngradeEnable <switch_name>
#
#*******************************************************************************
proc CALBootAutoCopySwDowngradeEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrBootAutoCopySwDowngradeEnable $switch_name
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
            Netgear_log_file "cal-ntgr-boot.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALBootAutoCopySwDowngradeDisable
#
#  Description    : This function is called to disable boot auto-copy-sw all-downgrade
#
#  Usage          : CALBootAutoCopySwDowngradeDisable <switch_name>
#
#*******************************************************************************
proc CALBootAutoCopySwDowngradeDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrBootAutoCopySwDowngradeDisable $switch_name
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
            Netgear_log_file "cal-ntgr-boot.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALBootAutoCopySwTrapEnable
#
#  Description    : This function is called to enable boot auto-copy-sw trap
#
#  Usage          : CALBootAutoCopySwTrapEnable <switch_name>
#
#*******************************************************************************
proc CALBootAutoCopySwTrapEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrBootAutoCopySwTrapEnable $switch_name
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
            Netgear_log_file "cal-ntgr-boot.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALBootAutoCopySwTrapDisable
#
#  Description    : This function is called to diable boot auto-copy-sw trap
#
#  Usage          : CALBootAutoCopySwTrapDisable <switch_name>
#
#*******************************************************************************
proc CALBootAutoCopySwTrapDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrBootAutoCopySwTrapDiable $switch_name
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
            Netgear_log_file "cal-ntgr-boot.tcl" "Switch not defined"
        }
    }
}




#*******************************************************************************
#
#  Function Name  : CALBootSystemSetActiveImage
#
#  Description    : This function is called to set boot system active image
#
#  Usage          : CALBootSystemSetActiveImage <switch_name> <imagename> <unitid>
#
#*******************************************************************************
proc CALBootSystemSetActiveImage {switch_name imagename {unitid ""}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrBootSystemSetActiveImage $switch_name $imagename $unitid
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
            Netgear_log_file "cal-ntgr-boot.tcl" "Switch not defined"
        }
    }
}



#*******************************************************************************
#
#  Function Name  : CALBootGetAutoCopySwSynStatus
#
#  Description    : This function is called to get auto-copy-sw synchronization status from switch.
#
#  Usage          : CALBootGetAutoCopySwSynStatus <switch_name>
#
#*******************************************************************************
proc CALBootGetAutoCopySwSynStatus {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrBootGetAutoCopySwSynStatus $switch_name
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
            Netgear_log_file "cal-ntgr-boot.tcl" "Switch not defined"
        }
    }
}