#!/bin/sh
################################################################################   
#
#  File Name		: cal-ntgr-poch.tcl
#
#  Description       	:
#         This TCL contains APIs to configure port channel. This is CLI 
#	       abstraction Layer for port channel configuration
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        22-May-06     Scott Zhang       Created
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALCreatePortChannel
#
#  Description    : This function config port channel on one/more switch(es).
#         
#  Usage          : CALCreatePortChannel <channel_name> 
# 
#*******************************************************************************
proc CALCreatePortChannel { channel_name } {
	set channel_switches [getPortChannelMember $channel_name]
	foreach sw_info $channel_switches {
		set switch_name [lindex $sw_info 0]
		set switch_vendor [ _get_switch_vendor_name  $switch_name ]
		
		switch $switch_vendor {
			netgear 	{
				_ntgrCreatePortChannel $switch_name $channel_name
			}
			cisco	{
				puts "TODO cisco\n"
			}
			hp	{
				puts "TODO hp\n"
			}
			3com	{
				puts "TODO hp\n"
			}
			default		{
				Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
			}
		} 
	}
}

#*******************************************************************************
#
#  Function Name	: CALDeletePortChannel
#
#  Description    : This function delete a port channel from one/more switch(es).
#         
#  Usage          : CALDeletePortChannel <channel_name> 
# 
#*******************************************************************************
proc CALDeletePortChannel { channel_name } {
	set channel_switches [getPortChannelMember $channel_name]
	foreach sw_info $channel_switches {
		set switch_name [lindex $sw_info 0]
		set switch_vendor [ _get_switch_vendor_name  $switch_name ]
		
		switch $switch_vendor {
			netgear 	{
				_ntgrDeletePortChannel $switch_name $channel_name
			}
			cisco	{
				puts "TODO cisco\n"
			}
			hp	{
				puts "TODO hp\n"
			}
			3com	{
				puts "TODO hp\n"
			}
			default		{
				Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
			}
		} 
	}
}

#*******************************************************************************
#
#  Function Name	: CALAddPortToChannel
#
#  Description    : This function add one/more ports to a port channel.
#         
#  Usage          : CALAddPortToChannel <switch_name> <channel_name> <portlist>
# 
#*******************************************************************************
proc CALAddPortToChannel { switch_name channel_name portlist } {
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrAddPortToChannel $connection_id $channel_name $portlist
		}
		cisco	{
			puts "TODO cisco\n"
		}
		hp	{
			puts "TODO hp\n"
		}
		3com	{
			puts "TODO hp\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
		}
	} 
	Netgear_disconnect_switch $switch_name	
}

#*******************************************************************************
#
#  Function Name	: CALDelPortFromChannel
#
#  Description    : This function delete one/more ports form a port channel.
#         
#  Usage          : CALDelPortFromChannel <switch_name> <channel_name> <portlist>
# 
#*******************************************************************************
proc CALDelPortFromChannel { switch_name channel_name portlist } {
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrDelPortFromChannel $switch_name $channel_name $portlist
		}
		cisco	{
			puts "TODO cisco\n"
		}
		hp	{
			puts "TODO hp\n"
		}
		3com	{
			puts "TODO hp\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDelPortFromChannel1
#
#  Description    : This function is duplicate of CALDelPortFromChannel. It will be used 
#                   in the cases where Port Channel is not created in this suite. 
#         
#  Usage          : CALDelPortFromChannel1 <switch_name> <channel_name> <portlist>
# 
#*******************************************************************************
proc CALDelPortFromChannel1 { switch_name channel_name portlist } {
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrDelPortFromChannel1 $switch_name $channel_name $portlist
		}
		cisco	{
			puts "TODO cisco\n"
		}
		hp	{
			puts "TODO hp\n"
		}
		3com	{
			puts "TODO hp\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALShutPortChannel
#
#  Description    : This function is called to shutdown a port channel.
#         
#  Usage          : CALShutPortChannel <switch_name> <channel_name>
# 
#*******************************************************************************
proc CALShutPortChannel { switch_name channel_name } {
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrShutPortChannel $switch_name $channel_name
		}
		cisco	{
			puts "TODO cisco\n"
		}
		hp	{
			puts "TODO hp\n"
		}
		3com	{
			puts "TODO hp\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALShutPort
#
#  Description    : This function is called to shutdown a port .
#         
#  Usage          : CALShutPort <switch_name> <port>
# 
#*******************************************************************************
proc CALShutPort { switch_name port } {
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrShutPort $switch_name $port
		}
		cisco	{
			puts "TODO cisco\n"
		}
		hp	{
			puts "TODO hp\n"
		}
		3com	{
			puts "TODO hp\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
		}
	} 
}


#*******************************************************************************
#
#  Function Name	: CALNoShutPortChannel
#
#  Description    : This function is called to no shutdown a port channel.
#         
#  Usage          : CALNoShutPortChannel <switch_name> <channel_name>
# 
#*******************************************************************************
proc CALNoShutPortChannel { switch_name channel_name } {
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrNoShutPortChannel $switch_name $channel_name
		}
		cisco	{
			puts "TODO cisco\n"
		}
		hp	{
			puts "TODO hp\n"
		}
		3com	{
			puts "TODO hp\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALNoShutPort
#
#  Description    : This function is called to no shutdown a port .
#         
#  Usage          : CALNoShutPort <switch_name> <port>
# 
#*******************************************************************************
proc CALNoShutPort { switch_name port } {
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrNoShutPort $switch_name $port
		}
		cisco	{
			puts "TODO cisco\n"
		}
		hp	{
			puts "TODO hp\n"
		}
		3com	{
			puts "TODO hp\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name  : CALDeleteAllPortChannels
#
#  Description    : This function is called to delete all port channels on
#                   a switch.
#
#  Usage          : CALDeleteAllPortChannels <switch_name>
#
#*******************************************************************************
proc CALDeleteAllPortChannels {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDeleteAllPortChannels $switch_name
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
            Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDelPortChannelPerSwitch
#
#  Description    : This function is called to delete a port channel on
#                   one switch.
#
#  Usage          : CALDelPortChannelPerSwitch <switch_name channel_name>
#
#*******************************************************************************
proc CALDelPortChannelPerSwitch {switch_name channel_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelPortChannelPerSwitch $switch_name $channel_name
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
            Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCreateAllPortChannels
#
#  Description    : This function is called to create all port channels on
#                   the given switch.
#
#  Usage          : CALCreateAllPortChannels <switch_name>
#
#*******************************************************************************
proc CALCreateAllPortChannels {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCreateAllPortChannels $switch_name
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
            Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALGetLAGLogicalIF
#
#  Description    : This function is called to get the logical if of LAG.
#
#  Usage          : CALGetLAGLogicalIF <switch_name lag>
#
#*******************************************************************************
proc CALGetLAGLogicalIF {switch_name lag } {
    set bCnn 1
    set connection_id [_get_switch_handle $switch_name]
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }

    set lag_if ""
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]
    switch $switch_vendor {
        netgear {
            set lag_if [_ntgrGetChannelLogicalIfName $connection_id $lag]
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
            Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
        }
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    Netgear_log_file "cal-ntgr-vlan.tcl" "Retrived LAG logical interface: $lag_if"
    return $lag_if 
}


#*******************************************************************************
#
#  Function Name  : CALSetPortChannelName
#
#  Description    : This function is called to set port-channel name.
#
#  Usage          : CALSetPortChannelName <switch_name> <port_channel> <name>
#
#*******************************************************************************
proc CALSetPortChannelName {switch_name port_channel name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetPortChannelName $switch_name $port_channel $name
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
            Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name	: CALSetPortChannelLoadBalance
#
#  Description    : This function is called to set port-channel load-balance.
#         
#  Usage          : CALSetPortChannelLoadBalance <switch_name> <channel_name> <loadbalanceNum>
#
#*******************************************************************************
proc CALSetPortChannelLoadBalance {switch_name channel_name loadbalanceNum} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetPortChannelLoadBalance $switch_name $channel_name $loadbalanceNum
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
            Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name	: CALSetPortChannelStaticModeEnable
#
#  Description    : This function is called to enable port-channel static mode.
#         
#  Usage          : CALSetPortChannelStaticModeEnable <switch_name> <channel_name>
#
#*******************************************************************************
proc CALSetPortChannelStaticModeEnable {switch_name channel_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetPortChannelStaticModeEnable $switch_name $channel_name
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
            Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name	: CALSetPortChannelStaticModeDisable
#
#  Description    : This function is called to disable port-channel static mode.
#         
#  Usage          : CALSetPortChannelStaticModeDisable <switch_name> <channel_name>
#
#*******************************************************************************
proc CALSetPortChannelStaticModeDisable {switch_name channel_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetPortChannelStaticModeDisable $switch_name $channel_name
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
            Netgear_log_file "cal-ntgr-poch.tcl" "Switch not defined"
        }
    }
}