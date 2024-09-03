################################################################################   
#
#  File Name		  : cal-ntgr-isdp.tcl
#
#  Description      :
#         This TCL contains functions to configure isdp
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        29-Aug-11     Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : CALIsdpEnable
#
#  Description    : This function is called to enable isdp
#
#  Usage          : CALIsdpEnable <switch_name>
#
#*******************************************************************************
proc CALIsdpEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpEnable $switch_name
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpDisable
#
#  Description    : This function is called to disable isdp
#
#  Usage          : CALIsdpDisable <switch_name>
#
#*******************************************************************************
proc CALIsdpDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpDisable $switch_name
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpAdvertiseV2Enable
#
#  Description    : This function is called to enable isdp advertise-v2
#
#  Usage          : CALIsdpAdvertiseV2Enable <switch_name>
#
#*******************************************************************************
proc CALIsdpAdvertiseV2Enable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpAdvertiseV2Enable $switch_name
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpAdvertiseV2Disable
#
#  Description    : This function is called to disable isdp advertise-v2
#
#  Usage          : CALIsdpAdvertiseV2Disable <switch_name>
#
#*******************************************************************************
proc CALIsdpAdvertiseV2Disable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpAdvertiseV2Disable $switch_name
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpSetTimer
#
#  Description    : This function is called to set isdp timer
#
#  Usage          : CALIsdpSetTimer <switch_name> <timer>
#
#*******************************************************************************
proc CALIsdpSetTimer {switch_name timer} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpSetTimer $switch_name $timer
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpSetHoldTime
#
#  Description    : This function is called to set isdp hold time
#
#  Usage          : CALIsdpSetHoldTime <switch_name> <holdtime>
#
#*******************************************************************************
proc CALIsdpSetHoldTime {switch_name holdtime} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpSetHoldTime $switch_name $holdtime
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALIsdpGetTimer
#
#  Description    : This function is used to get isdp timer.
#
#  Usage          : CALIsdpGetTimer <switch_name>
#
#*******************************************************************************
proc CALIsdpGetTimer {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpGetTimer $switch_name
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpGetHoldTime
#
#  Description    : This function is used to get isdp hold time.
#
#  Usage          : CALIsdpGetHoldTime <switch_name>
#
#*******************************************************************************
proc CALIsdpGetHoldTime {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpGetHoldTime $switch_name
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpGetSerialNumber
#
#  Description    : This function is used to get isdp serial number.
#
#  Usage          : CALIsdpGetSerialNumber <switch_name>
#
#*******************************************************************************
proc CALIsdpGetSerialNumber {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpGetSerialNumber $switch_name
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpGetNeighborLastChangedTime
#
#  Description    : This function is used to get isdp neighbor last changed time.
#
#  Usage          : CALIsdpGetNeighborLastChangedTime <switch_name> <port>
#
#*******************************************************************************
proc CALIsdpGetNeighborLastChangedTime {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpGetNeighborLastChangedTime $switch_name $port
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpGetNeighborDeviceID
#
#  Description    : This function is used to get isdp neighbor device id.
#
#  Usage          : CALIsdpGetNeighborDeviceID <switch_name> <port>
#
#*******************************************************************************
proc CALIsdpGetNeighborDeviceID {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpGetNeighborDeviceID $switch_name $port
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpGetNeighborHoldTime
#
#  Description    : This function is used to get isdp neighbor hole time.
#
#  Usage          : CALIsdpGetNeighborHoldTime <switch_name> <port>
#
#*******************************************************************************
proc CALIsdpGetNeighborHoldTime {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpGetNeighborHoldTime $switch_name $port
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpGetNeighborAdvertisementVersion
#
#  Description    : This function is used to get isdp neighbor advertisement version.
#
#  Usage          : CALIsdpGetNeighborAdvertisementVersion <switch_name> <port>
#
#*******************************************************************************
proc CALIsdpGetNeighborAdvertisementVersion {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpGetNeighborAdvertisementVersion $switch_name $port
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpGetNeighborIpAddress
#
#  Description    : This function is used to get isdp neighbor ip address.
#
#  Usage          : CALIsdpGetNeighborIpAddress <switch_name> <port>
#
#*******************************************************************************
proc CALIsdpGetNeighborIpAddress {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpGetNeighborIpAddress $switch_name $port
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpGetNeighborVersion
#
#  Description    : This function is used to get isdp neighbor ip address.
#
#  Usage          : CALIsdpGetNeighborVersion <switch_name> <port>
#
#*******************************************************************************
proc CALIsdpGetNeighborVersion {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpGetNeighborVersion $switch_name $port
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpGetNeighborPortID
#
#  Description    : This function is used to get isdp neighbor port id.
#
#  Usage          : CALIsdpGetNeighborPortID <switch_name> <port>
#
#*******************************************************************************
proc CALIsdpGetNeighborPortID {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpGetNeighborPortID $switch_name $port
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpClearCounter
#
#  Description    : This function is called to clear isdp counters
#
#  Usage          : CALIsdpClearCounter <switch_name>
#
#*******************************************************************************
proc CALIsdpClearCounter {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpClearCounter $switch_name
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpClearTable
#
#  Description    : This function is called to clear isdp table
#
#  Usage          : CALIsdpClearTable <switch_name>
#
#*******************************************************************************
proc CALIsdpClearTable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpClearTable $switch_name
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}





#*******************************************************************************
#
#  Function Name  : CALIsdpEnableOnPort
#
#  Description    : This function is called to enable isdp on port
#
#  Usage          : CALIsdpEnableOnPort <switch_name> <port>
#
#*******************************************************************************
proc CALIsdpEnableOnPort {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpEnableOnPort $switch_name $port
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsdpDisableOnPort
#
#  Description    : This function is called to disable isdp on port
#
#  Usage          : CALIsdpDisableOnPort <switch_name> <port>
#
#*******************************************************************************
proc CALIsdpDisableOnPort {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIsdpDisableOnPort $switch_name $port
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}






#*******************************************************************************
#
#  Function Name  : CALCheckIsdpEntryByPort
#
#  Description    : This function is used to check lsdp entry exist or not.
#
#  Usage          : CALCheckIsdpEntryByPort <switch_name> <port> <modelname>
#
#*******************************************************************************
proc CALCheckIsdpEntryByPort {switch_name port modelname} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIsdpEntryByPort $switch_name $port $modelname
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
            Netgear_log_file "cal-ntgr-isdp.tcl" "Switch not defined"
        }
    }
}
