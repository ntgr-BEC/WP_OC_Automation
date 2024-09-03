################################################################################   
#
#  File Name		  : cal-ntgr-aaa.tcl
#
#  Description      :
#         This TCL contains functions to configure aaa
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        06-Dec-11     Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : CALAaaAddIasUser
#
#  Description    : This function is called to add aaa ias user
#
#  Usage          : CALAaaAddIasUser <switch_name> <username>
#
#*******************************************************************************
proc CALAaaAddIasUser {switch_name username} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAaaAddIasUser $switch_name $username
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
            Netgear_log_file "cal-ntgr-aaa.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALAaaDeleteIasUser
#
#  Description    : This function is called to delete aaa ias user
#
#  Usage          : CALAaaDeleteIasUser <switch_name> <username>
#
#*******************************************************************************
proc CALAaaDeleteIasUser {switch_name username} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAaaDeleteIasUser $switch_name $username
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
            Netgear_log_file "cal-ntgr-aaa.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALCheckAaaIasUser
#
#  Description    : This function is used to check aaa ias user exist or not.
#
#  Usage          : CALCheckAaaIasUser <switch_name> <user_list> <notin>
#
#*******************************************************************************
proc CALCheckAaaIasUser {switch_name user_list {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckAaaIasUser $switch_name $user_list $notin
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
            Netgear_log_file "cal-ntgr-aaa.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALAddAaaEnableAuthList
#
#  Description    : This function is used to create enable authen list
#
#  Usage          : CALAddAaaEnableAuthList <switch_name> <list_name> <auth_mode>
#
#*******************************************************************************
proc CALAddAaaEnableAuthList {switch_name list_name auth_mode} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddAaaEnableAuthList $switch_name $list_name $auth_mode
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
            Netgear_log_file "cal-ntgr-aaa.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDelAaaEnableAuthList
#
#  Description    : This function is used to delete enable authen list
#
#  Usage          : CALDelAaaEnableAuthList <switch_name> <list_name>
#
#*******************************************************************************
proc CALDelAaaEnableAuthList {switch_name list_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelAaaEnableAuthList $switch_name $list_name
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
            Netgear_log_file "cal-ntgr-aaa.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALAddAaaLoginAuthList
#
#  Description    : This function is used to add login authen list
#
#  Usage          : CALAddAaaLoginAuthList <switch_name> <list_name> <auth_mode>
#
#*******************************************************************************
proc CALAddAaaLoginAuthList {switch_name list_name auth_mode} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddAaaLoginAuthList $switch_name $list_name $auth_mode
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
            Netgear_log_file "cal-ntgr-aaa.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDelAaaLoginAuthList
#
#  Description    : This function is used to del login authen list
#
#  Usage          : CALAddAaaLoginAuthList <switch_name> <list_name> 
#
#*******************************************************************************
proc CALDelAaaLoginAuthList {switch_name list_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelAaaLoginAuthList $switch_name $list_name
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
            Netgear_log_file "cal-ntgr-aaa.tcl" "Switch not defined"
        }
    }
}