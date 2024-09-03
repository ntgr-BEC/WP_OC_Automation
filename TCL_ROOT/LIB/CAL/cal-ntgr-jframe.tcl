#!/bin/sh
################################################################################   
#
#  File Name		: cal-ntgr-jframe.tcl
#
#  Desciption       	:
#         This TCL contains APIs to configure jumbo frame. This is CLI 
#	    abstraction Layer for jumbo frame configuration
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        24-may-06     Rajeswari	     Created    
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALJumboFrameConfig
#
#  Descjframetion    : This function configures jumbo frame on the switch
#         
#  Usage          : CALJumboFrameConfig <switch>
# 
#*******************************************************************************
proc CALJumboFrameConfig {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrJumboFrameConfig $switch	
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
			Netgear_log_file "cal-ntgr-jframe.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name  : CALJumboFrameDisable
#
#  Description    : This function is called to disable all configured jumbo
#                   support for a switch.
#
#  Usage          : CALJumboFrameDisable <switch_name>
#
#*******************************************************************************
proc CALJumboFrameDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrJumboFrameDisable $switch_name
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
#  Function Name  :	CALJbFrameEnable
#
#  Descjframetion :	This function configures jumbo frame on the switch
#         
#  Usage          :	CALJbFrameEnable <switch> <int_mtu_list>
# 
#  Revision       :
#			Date        Programmer        Description
#			2010/07/08  Kenddy            Newly created   
#
#*******************************************************************************
proc CALJbFrameEnable {switch_name int_mtu_list} {

	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrJbFrameEnalbe $switch_name $int_mtu_list
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
			Netgear_log_file "cal-ntgr-jframe.tcl" "Switch not defined"
		}
	} 
}
#*******************************************************************************
#
#  Function Name  :	CALJbFrameDisable
#
#  Description    :	This function is called to disable all configured jumbo
#			support for a switch.
#
#  Usage          :	CALJbFrameDisable <switch_name> <interface_list>
#
#  Revision       :
#			Date        Programmer        Description
#			2010/07/08  Kenddy            Newly created   
#
#*******************************************************************************
proc CALJbFrameDisable {switch_name interface_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrJbFrameDisable $switch_name $interface_list
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
