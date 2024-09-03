#!/bin/sh
################################################################################   
#
#  File Name		: cal-ntgr-stormctrl.tcl
#
#  Desciption       	:
#         This TCL contains APIs to configure Storm Control. This is CLI 
#	    abstraction Layer for Storm Control Configuration
#
#  Revision History 	:
#         Date         Programmer        Desciption
#        -----------------------------------------------------------------------
#        24-may-06     Rajeswari Created    
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name  : CALConfigFlowCtrl
#
#  Description    : This function is called to configure flowcontrol on the switch.
#
#  Usage          : CALConfigFlowCtrl <switch_name>
#
#*******************************************************************************
proc CALConfigFlowCtrl {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigFlowCtrl $switch_name
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
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigNewFlowCtrl
#
#  Description    : This function is called to configure flowcontrol on the switch.
#
#  Usage          : CALConfigNewFlowCtrl <switch_name> <if_list>
# 
#  Author         : jim.xie for M6100
#
#*******************************************************************************
proc CALConfigNewFlowCtrl {switch_name if_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigNewFlowCtrl $switch_name $if_list
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
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigBCastStormCtrl
#
#  Description    : This function is called to configure broadcast storm control
#                   on the switch.
#
#  Usage          : CALConfigBCastStormCtrl <switch_name>
#
#*******************************************************************************
proc CALConfigBCastStormCtrl {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigBCastStormCtrl $switch_name
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
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigMCastStormCtrl
#
#  Description    : This function is called to configure multicast storm control
#                   on the switch.
#
#  Usage          : CALConfigMCastStormCtrl <switch_name>
#
#*******************************************************************************
proc CALConfigMCastStormCtrl {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigMCastStormCtrl $switch_name
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
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigUCastStormCtrl
#
#  Description    : This function is called to configure unicast storm control
#                   on the switch.
#
#  Usage          : CALConfigUCastStormCtrl <switch_name>
#
#*******************************************************************************
proc CALConfigUCastStormCtrl {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigUCastStormCtrl $switch_name
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
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name	: CALStormControlConfig
#
#  Desciption    : This function configures flow control and storm control for
#                  broadcast, multicast and unicast.
#         
#  Usage          : CALStormControlConfig (switch) 
# 
#*******************************************************************************
proc CALStormControlConfig {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrConfigureStormControl $switch	
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-stormctrl.tcl" "Switch not defined"
		}
	} 
}