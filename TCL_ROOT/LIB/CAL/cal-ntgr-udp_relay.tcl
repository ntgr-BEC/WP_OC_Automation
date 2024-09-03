####################################################################################
#  File Name   : cal-ntgr-udp_relay.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for udp relay configuration.
#
#  History     :
#        Date          Programmer         Description
#        Dec 19, 2012  Tony               Created
#
####################################################################################


#*******************************************************************************
#
#  Function Name  : CALIPHelperEnable
#
#  Description    : This function is called to enable ip helper.
#
#  Usage          : CALIPHelperEnable <switch_name>
#
#*******************************************************************************
proc CALIPHelperEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPHelperEnable $switch_name
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
            Netgear_log_file "cal-ntgr-user.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIPHelperDisable
#
#  Description    : This function is called to disable ip helper.
#
#  Usage          : CALIPHelperDisable <switch_name>
#
#*******************************************************************************
proc CALIPHelperDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPHelperDisable $switch_name
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
            Netgear_log_file "cal-ntgr-user.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALAddIPHelperAddr
#
#  Description    : This function is called to add ip helper address.
#
#  Usage          : CALAddIPHelperAddr <switch_name>
#
#*******************************************************************************
proc CALAddIPHelperAddr {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddIPHelperAddr $switch_name
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
            Netgear_log_file "cal-ntgr-user.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDelIPHelperAddr
#
#  Description    : This function is called to delete ip helper address.
#
#  Usage          : CALDelIPHelperAddr <switch_name>
#
#*******************************************************************************
proc CALDelIPHelperAddr {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelIPHelperAddr $switch_name
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
            Netgear_log_file "cal-ntgr-user.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALDelAllIPHelperAddr
#
#  Description    : This function is called to all delete ip helper address.
#
#  Usage          : CALDelAllIPHelperAddr <switch_name>
#
#*******************************************************************************
proc CALDelAllIPHelperAddr {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelAllIPHelperAddr $switch_name
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
            Netgear_log_file "cal-ntgr-user.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALAddIPHelperInterfaceAddr
#
#  Description    : This function is called to add ip helper address on interface.
#
#  Usage          : CALAddIPHelperInterfaceAddr <switch_name>
#
#*******************************************************************************
proc CALAddIPHelperInterfaceAddr {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddIPHelperInterfaceAddr $switch_name
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
            Netgear_log_file "cal-ntgr-user.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALDelIPHelperAddrOnInterface
#
#  Description    : This function is called to  delete ip helper address on interface.
#
#  Usage          : CALDelIPHelperAddrOnInterface <switch_name>
#
#*******************************************************************************
proc CALDelIPHelperAddrOnInterface {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelIPHelperAddrOnInterface $switch_name
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
            Netgear_log_file "cal-ntgr-user.tcl" "Switch not defined"
        }
    }
}
