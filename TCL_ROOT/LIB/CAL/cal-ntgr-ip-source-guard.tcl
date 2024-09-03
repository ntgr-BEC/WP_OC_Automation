####################################################################################
#  File Name   : cal-ntgr-ip-source-guard.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for  ip source guard configuration.
#
#  History     :
#        Date          Programmer         Description
#        Feb 20 2014  Jason Li            Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALIPSGAddStaticBindingConfig
#
#  Description    : This function is called to add static binding configuration.
#
#  Usage          : CALIPSGAddStaticBindingConfig <switch_name>
#
#*******************************************************************************
proc CALIPSGAddStaticBindingConfig {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPSGAddStaticBindingConfig $switch_name 
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
#  Function Name  : CALIPSGDelStaticBindingConfig
#
#  Description    : This function is called to delete static binding configuration.
#
#  Usage          : CALIPSGDelStaticBindingConfig <switch_name>
#
#*******************************************************************************
proc CALIPSGDelStaticBindingConfig {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPSGDelStaticBindingConfig $switch_name 
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
#  Function Name  : CALIPSGEnableMode
#
#  Description    : This function is called to enable ip source guard mode.
#
#  Usage          : CALIPSGEnableMode <switch_name>
#
#*******************************************************************************
proc CALIPSGEnableMode {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPSGEnableMode $switch_name 
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
#  Function Name  : CALIPSGDisableMode
#
#  Description    : This function is called to disable ip source guard mode.
#
#  Usage          : CALIPSGDisableMode <switch_name>
#
#*******************************************************************************
proc CALIPSGDisableMode {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPSGDisableMode $switch_name 
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
#  Function Name  : CALIPSGEnablePortSecurity
#
#  Description    : This function is called to enable port security.
#
#  Usage          : CALIPSGEnablePortSecurity <switch_name>
#
#*******************************************************************************
proc CALIPSGEnablePortSecurity {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPSGEnablePortSecurity $switch_name 
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
#  Function Name  : CALIPSGDisablePortSecurity
#
#  Description    : This function is called to disable port security.
#
#  Usage          : CALIPSGDisablePortSecurity <switch_name>
#
#*******************************************************************************
proc CALIPSGDisablePortSecurity {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPSGDisablePortSecurity $switch_name 
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
#  Function Name  : CALCheckIPSGEntry
#
#  Description    : This function is used to check ip source guard entry exist or not.
#
#  Usage          : CALCheckIPSGEntry <switch_name> <ip_list> <notin>
#
#*******************************************************************************
proc CALCheckIPSGEntry {switch_name ip_list {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIPSGEntry $switch_name $ip_list $notin
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ip-source-guard.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALIPSGClearBindingConfiguration
#
#  Description    : This function is called to clear ip source guard binding configuration on specified
#                   switch.
#
#  Usage          : CALIPSGClearBindingConfiguration <switch_name>
#
#*******************************************************************************
proc CALIPSGClearBindingConfiguration {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPSGClearBindingConfiguration $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ip-source-guard.tcl" "Switch not defined"
        }
    }
}


