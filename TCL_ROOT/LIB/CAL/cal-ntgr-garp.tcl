################################################################################   
#
#  File Name		  : cal-ntgr-garp.tcl
#
#  Description      :
#         This TCL contains functions to configure garp
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        18-Sep-2012   Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : CALGvrpEnable
#
#  Description    : This function is called to enable gvrp on specified switch.
#
#  Usage          : CALGvrpEnable <switch_name>
#
#*******************************************************************************
proc CALGvrpEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrGvrpEnable $switch_name
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
            Netgear_log_file "cal-ntgr-garp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALGvrpDisable
#
#  Description    : This function is called to diable gvrp on specified switch.
#
#  Usage          : CALGvrpDisable <switch_name>
#
#*******************************************************************************
proc CALGvrpDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrGvrpDisable $switch_name
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
            Netgear_log_file "cal-ntgr-garp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALGvrpIntfEnable
#
#  Description    : This function is called to enable gvrp on specified interface.
#
#  Usage          : CALGvrpIntfEnable <switch_name> <port_list>
#
#*******************************************************************************
proc CALGvrpIntfEnable {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrGvrpIntfEnable $switch_name $port_list
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
            Netgear_log_file "cal-ntgr-garp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALGvrpIntfDisable
#
#  Description    : This function is called to disable gvrp on specified interface.
#
#  Usage          : CALGvrpIntfDisable <switch_name> <port_list>
#
#*******************************************************************************
proc CALGvrpIntfDisable {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrGvrpIntfDisable $switch_name $port_list
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
            Netgear_log_file "cal-ntgr-garp.tcl" "Switch not defined"
        }
    }
}