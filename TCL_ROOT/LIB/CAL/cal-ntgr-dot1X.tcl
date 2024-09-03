####################################################################################
#  File Name   : cal-ntgr-dot1x.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for 802.1X configuration.
#
#  History     :
#        Date          Programmer         Description
#        May 11, 2007  Scott Zhang        Created
#	 May 14, 2007  Nina Cheng         Modified
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALEnableDot1X
#
#  Description    : This function is called to enable 802.1X on a switch.
#
#  Usage          : CALEnableDot1X <switch_name>
#
#*******************************************************************************
proc CALEnableDot1X {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableDot1X $switch_name
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableDot1X
#
#  Description    : This function is called to disable 802.1X on a switch.
#
#  Usage          : CALDisableDot1X <switch_name>
#
#*******************************************************************************
proc CALDisableDot1X {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableDot1X $switch_name
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetDot1XPortAuthMode
#
#  Description    : This function is called to set 802.1X port authentication mode.
#
#  Usage          : CALSetDot1XPortAuthMode <switch_name portlist {mode {}}>
#
#*******************************************************************************
proc CALSetDot1XPortAuthMode {switch_name portlist {mode {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDot1XPortAuthMode $switch_name $portlist $mode
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetDot1XDefaultLogin
#
#  Description    : This function is called to set default login for 802.1X.
#
#  Usage          : CALSetDot1XDefaultLogin <switch_name login>
#
#*******************************************************************************
proc CALSetDot1XDefaultLogin {switch_name login} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDot1XDefaultLogin $switch_name $login
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetDot1XPortMethod
#
#  Description    : This function is called to set 802.1X port method.
#
#  Usage          : CALSetDot1XPortMethod <switch_name portlist mode>
#
#*******************************************************************************
proc CALSetDot1XPortMethod {switch_name portlist mode} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDot1XPortMethod $switch_name $portlist $mode
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetDot1XPortTimeroftx
#
#  Description    : This function is called to set 802.1X tx timer.
#
#  Usage          : CALSetDot1XPortTimeroftx <switch_name portlist timer>
#
#*******************************************************************************
proc CALSetDot1XPortTimeroftx {switch_name portlist timer} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDot1XPortTimeroftx $switch_name $portlist $timer
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetDot1XPortTimerofsupp
#
#  Description    : This function is called to set 802.1X supp timer.
#
#  Usage          : CALSetDot1XPortTimerofsupp <switch_name portlist timer>
#
#*******************************************************************************
proc CALSetDot1XPortTimerofsupp {switch_name portlist timer} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDot1XPortTimerofsupp $switch_name $portlist $timer
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetDot1XPortTimerofserver
#
#  Description    : This function is called to set 802.1X server timer.
#
#  Usage          : CALSetDot1XPortTimerofserver <switch_name portlist timer>
#
#*******************************************************************************
proc CALSetDot1XPortTimerofserver {switch_name portlist timer} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDot1XPortTimerofserver $switch_name $portlist $timer
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetDot1XPortTimerofreauth
#
#  Description    : This function is called to set 802.1X reauth timer.
#
#  Usage          : CALSetDot1XPortTimerofreauth <switch_name portlist timer>
#
#*******************************************************************************
proc CALSetDot1XPortTimerofreauth {switch_name portlist timer} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDot1XPortTimerofreauth $switch_name $portlist $timer
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetDot1XPortTimerofquiet
#
#  Description    : This function is called to set 802.1X quiet timer.
#
#  Usage          : CALSetDot1XPortTimerofquiet <switch_name portlist timer>
#
#*******************************************************************************
proc CALSetDot1XPortTimerofquiet {switch_name portlist timer} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDot1XPortTimerofquiet $switch_name $portlist $timer
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALEnableDot1XReauth
#
#  Description    : This function is called to enable 802.1X reauthentication.
#
#  Usage          : CALEnableDot1XReauth <switch_name portlist>
#
#*******************************************************************************
proc CALEnableDot1XReauth {switch_name portlist} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableDot1XReauth $switch_name $portlist
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableDot1XReauth
#
#  Description    : This function is called to disable 802.1X reauthentication.
#
#  Usage          : CALDisableDot1XReauth <switch_name portlist>
#
#*******************************************************************************
proc CALDisableDot1XReauth {switch_name portlist} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableDot1XReauth $switch_name $portlist
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetDot1XMaxreq
#
#  Description    : This function is called to set 802.1X Max req.
#
#  Usage          : CALSetDot1XMaxreq <switch_name portlist number>
#
#*******************************************************************************
proc CALSetDot1XMaxreq {switch_name portlist number} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDot1XMaxreq $switch_name $portlist $number
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetDot1XUser
#
#  Description    : This function is called to set 802.1X user.
#
#  Usage          : CALSetDot1XUser <switch_name portlist user>
#
#*******************************************************************************
proc CALSetDot1XUser {switch_name portlist user} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDot1XUser $switch_name $portlist $user
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetDot1XLogin
#
#  Description    : This function is called to set 802.1X login.
#
#  Usage          : CALSetDot1XLogin <switch_name user listname>
#
#*******************************************************************************
proc CALSetDot1XLogin {switch_name user listname} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDot1XLogin $switch_name $user $listname
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetDot1XIF
#
#  Description    : This function is called to set interface for 802.1X.
#
#  Usage          : CALSetDot1XIF <switch_name portlist>
#
#*******************************************************************************
proc CALSetDot1XIF {switch_name portlist} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDot1XIF $switch_name $portlist
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
            Netgear_log_file "cal-ntgr-dot1x.tcl" "Switch not defined"
        }
    }
}
