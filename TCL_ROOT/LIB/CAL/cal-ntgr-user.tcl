####################################################################################
#  File Name   : cal-ntgr-user.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for user management configuration.
#
#  History     :
#        Date          Programmer         Description
#        May 11, 2007  Scott Zhang        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALAddAuthLoginList
#
#  Description    : This function is called to create a authentication login list.
#
#  Usage          : CALAddAuthLoginList <switch_name mode ip>
#
#*******************************************************************************
proc CALAddAuthLoginList {switch_name name mode} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddAuthLoginList $switch_name $name $mode
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
#  Function Name  : CALAddUser
#
#  Description    : This function is called to create a user.
#
#  Usage          : CALAddUser <switch_name name pwd>
#
#*******************************************************************************
proc CALAddUser {switch_name name pwd {level 1}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddUser $switch_name $name $pwd $level
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
#  Function Name  : CALAddUserWithOutPassWord
#
#  Description    : This function is called to create a user without password.
#
#  Usage          : CALAddUserWithOutPassWord <switch_name> <name> <level>
#
#*******************************************************************************
proc CALAddUserWithOutPassWord {switch_name name {level 1}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddUserWithoutPassWord $switch_name $name $level
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
#  Function Name  : CALDeleteUser
#
#  Description    : This function is called to delete a user.
#
#  Usage          : CALDeleteUser <switch_name name>
#
#*******************************************************************************
proc CALDeleteUser {switch_name name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDeleteUser $switch_name $name
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
#  Function Name  : CALAddUsertoList
#
#  Description    : This function is called to add user to a list.
#
#  Usage          : CALAddUsertoList <switch_name user list>
#
#*******************************************************************************
proc CALAddUsertoList {switch_name user list} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddUsertoList $switch_name $user $list
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
#  Function Name  : CALModifyPassword
#
#  Description    : This function is called to modify the password of a user.
#
#  Usage          : CALModifyPassword <switch_name user pwd_old pwd_new>
#
#*******************************************************************************
proc CALModifyPassword {switch_name user pwd_old pwd_new} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrModifyPassword $switch_name $user $pwd_old $pwd_new
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

