####################################################################################
#  File Name   : cal-ntgr-radius.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for RADIUS configuration.
#
#  History     :
#        Date          Programmer         Description
#        May 11, 2007  Scott Zhang        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALAddRadiusServer
#
#  Description    : This function is called add a radius server to switch.
#
#  Usage          : CALAddRadiusServer <switch_name mode ip>
#
#*******************************************************************************
proc CALAddRadiusServer {switch_name mode ip} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddRadiusServer $switch_name $mode $ip
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDelRadiusServer
#
#  Description    : This function is called delete a radius server to switch.
#
#  Usage          : CALDelRadiusServer <switch_name mode ip>
#
#*******************************************************************************
proc CALDelRadiusServer {switch_name mode ip} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelRadiusServer $switch_name $mode $ip
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetRadiusServerKey
#
#  Description    : This function is called set radius server's secret key.
#
#  Usage          : CALSetRadiusServerKey <switch_name mode ip key>
#
#*******************************************************************************
proc CALSetRadiusServerKey {switch_name mode ip key {re_enter {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetRadiusServerKey $switch_name $mode $ip $key $re_enter
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetPrimaryRadiusServer
#
#  Description    : This function is called set the primary radius server.
#
#  Usage          : CALSetPrimaryRadiusServer <switch_name ip>
#
#*******************************************************************************
proc CALSetPrimaryRadiusServer {switch_name ip} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetPrimaryRadiusServer $switch_name $ip
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetRadiusServerPort
#
#  Description    : This function is called set radius server port.
#
#  Usage          : CALSetRadiusServerPort <switch_name mode ip port>
#
#*******************************************************************************
proc CALSetRadiusServerPort {switch_name mode ip port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetRadiusServerPort $switch_name $mode $ip $port
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetAuthServerType
#
#  Description    : This function is called set authentication server type.
#
#  Usage          : CALSetAuthServerType <switch_name ip type>
#
#*******************************************************************************
proc CALSetAuthServerType {switch_name ip type} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetAuthServerType $switch_name $ip $type
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALEnableAccounting
#
#  Description    : This function is called to enable accounting function on switch.
#
#  Usage          : CALEnableAccounting <switch_name>
#
#*******************************************************************************
proc CALEnableAccounting {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableAccounting $switch_name
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableAccounting
#
#  Description    : This function is called to disable accounting function on switch.
#
#  Usage          : CALDisableAccounting <switch_name>
#
#*******************************************************************************
proc CALDisableAccounting {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableAccounting $switch_name
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALEnableRadiusMsgAuthAttr
#
#  Description    : This function is called to enable message authenticator attribute.
#
#  Usage          : CALEnableRadiusMsgAuthAttr <switch_name ip>
#
#*******************************************************************************
proc CALEnableRadiusMsgAuthAttr {switch_name ip} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableRadiusMsgAuthAttr $switch_name $ip
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableRadiusMsgAuthAttr
#
#  Description    : This function is called to disable message authenticator attribute.
#
#  Usage          : CALDisableRadiusMsgAuthAttr <switch_name ip>
#
#*******************************************************************************
proc CALDisableRadiusMsgAuthAttr {switch_name ip} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableRadiusMsgAuthAttr $switch_name $ip
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetRadiusTimeout
#
#  Description    : This function is called to set radius timeout value.
#
#  Usage          : CALSetRadiusTimeout <switch_name timeout>
#
#*******************************************************************************
proc CALSetRadiusTimeout {switch_name timeout} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetRadiusTimeout $switch_name $timeout
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALResetRadiusTimeout
#
#  Description    : This function is called to reset radius timeout value.
#
#  Usage          : CALResetRadiusTimeout <switch_name>
#
#*******************************************************************************
proc CALResetRadiusTimeout {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrResetRadiusTimeout $switch_name
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetRadiusRetransmitTime
#
#  Description    : This function is called to set radius retransmit times.
#
#  Usage          : CALSetRadiusRetransmitTime <switch_name time>
#
#*******************************************************************************
proc CALSetRadiusRetransmitTime {switch_name time} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetRadiusRetransmitTime $switch_name $time
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALResetRadiusRetransmitTime
#
#  Description    : This function is called to reset radius retransmit times to default.
#
#  Usage          : CALResetRadiusRetransmitTime <switch_name>
#
#*******************************************************************************
proc CALResetRadiusRetransmitTime {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrResetRadiusRetransmitTime $switch_name
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
            Netgear_log_file "cal-ntgr-radius.tcl" "Switch not defined"
        }
    }
}

