#!/bin/sh
################################################################################   
#
#  File Name   : cal-global-cmd.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for global commands.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        22/5/06      Scott Zhang        Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALSaveConfig
#
#  Description    : This CAL function is used to save configuration.
#         
#  Usage          : CALSaveConfig (switch) 
# 
#*******************************************************************************
proc CALSaveConfig { switch } {
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			_ntgrSaveConfig $switch
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALSwitchCopy
#
#  Description    : This CAL function encapsulates the copy command.
#         
#  Usage          : CALSwitchCopy <switch from to {filename {}}> 
# 
#*******************************************************************************
proc CALSwitchCopy { switch from to {filename {}} {bSyn 1}} {
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			_ntgrSwitchCopy $switch $from $to $filename $bSyn
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALRebootSwitch
#
#  Description    : This function reboot a switch
#         
#  Usage          : CALRebootSwitch (switch_name {unitID 0}) 
# 
#*******************************************************************************
proc CALRebootSwitch { switch {unitID 0}} {
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			_ntgrRebootSwitch $switch $unitID
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALClearMacSwitch
#
#  Description    : This function clear the mac address from the switch
#         
#  Usage          : CALClearMacSwitch (switch_name) 
# 
#*******************************************************************************
proc CALClearMacSwitch { switch {mac 0} {interface 0} {vlan 0}} {
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			_ntgrClearMacSwitch $switch $mac $interface $vlan
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALClearCounterSwitch
#
#  Description    : This function clear the counters from the switch
#         
#  Usage          : CALClearCounterSwitch (switch_name) 
# 
#*******************************************************************************
proc CALClearCounterSwitch { switch } {
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			_ntgrClearCounterSwitch $switch
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALJmboFrameConfig
#
#  Description    : This function configures the MTU
#         
#  Usage          : CALJumboFrameConfig <switch> 
# 
#*******************************************************************************
proc CALJumboFrameConfig {switch} {
	set switch_vendor [_get_switch_vendor_name  $switch]
	switch $switch_vendor {
		netgear 	{
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALUploadLogFile
#
#  Description    : This CAL function is used to Upload Log file from the Switch.
#         
#  Usage          : CALUploadLogFile 
# 
#*******************************************************************************
proc CALUploadLogFile {switch} {
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			_ntgrUploadLogFile $switch
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALUploadStartupConfigFile
#
#  Description    : This CAL function is used to Upload Startup Config file 
#		        from the Switch.
#         
#  Usage          : CALUploadStartupConfigFile
# 
#*******************************************************************************
proc CALUploadStartupConfigFile {switch} {
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			_ntgrUploadStartupConfigFile $switch
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}


#*******************************************************************************
#
#  Function Name	: CALDownloadStartupConfigFile
#
#  Description    : This CAL function is used to Download Startup Config file 
#		        to the Switch.
#         
#  Usage          : CALDownloadStartupConfigFile
# 
#*******************************************************************************
proc CALDownloadStartupConfigFile {switch} {
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			_ntgrDownloadStartupConfigFile $switch
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALSetMTU
#
#  Description    : This CAL function is used to set MTU of the swtich port.
#         
#  Usage          : CALSetMTU <switch> <port> <mtu>
# 
#*******************************************************************************
proc CALSetMTU {switch port mtu} {
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			_ntgrSetMTU $switch $port $mtu
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALSetSpeed
#
#  Description    : This CAL function is used to set Speed of the swtich port.
#         
#  Usage          : CALSetSpeed <switch> <port> <speed> <duplex>
# 
#*******************************************************************************
proc CALSetSpeed {switch port speed {duplex {}}} {
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			_ntgrSetSpeed $switch $port $speed $duplex
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name  : CALSetPortEncapsulation
#
#  Description    : This function is used to set the encapsulation of ports.
#
#  Usage          : CALSetPortEncapsulation <switch_id if_list encap>
#
#*******************************************************************************
proc CALSetPortEncapsulation {switch_id if_list encap} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrSetPortEncapsulation $switch_id $if_list $encap
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetPortDescription
#
#  Description    : This function is used to set port description.
#
#  Usage          : CALSetPortDescription <switch_id if_list {description {}}>
#
#*******************************************************************************
proc CALSetPortDescription {switch_id if_list {description {}}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrSetPortDescription $switch_id $if_list $description
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetPortDescriptionToMaxLength
#
#  Description    : This function is used to set port description to max length.
#
#  Usage          : CALSetPortDescriptionToMaxLength <switch_id if_list>
#
#*******************************************************************************
proc CALSetPortDescriptionToMaxLength {switch_id if_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrSetPortDescriptionToMaxLength $switch_id $if_list
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALGetPortDescriptionMaxLength
#
#  Description    : This function is used to get port description max length.
#
#  Usage          : CALGetPortDescriptionMaxLength <switch_id port>
#
#*******************************************************************************
proc CALGetPortDescriptionMaxLength {switch_id port} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrGetPortDescriptionMaxLength $switch_id $port
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALClearArpCache
#
#  Description    : This function is used to clear ARP cache of a switch.
#
#  Usage          : CALClearArpCache <switch_id>
#
#*******************************************************************************
proc CALClearArpCache {switch_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrClearArpCache $switch_id
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

proc CALClearArpCacheGateway {switch_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrClearArpCacheGateway $switch_id
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : CALResetConfiguration
#
#  Description    : This function is called to reset configuration of a switch.
#
#  Usage          : CALResetConfiguration <switch_id>
#
#*******************************************************************************
proc CALResetConfiguration {switch_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrResetConfig $switch_id
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALPortLinkUp
#
#  Description    : This function is called to set the link admin status to up.
#
#  Usage          : CALPortLinkUp <switch_id port>
#
#*******************************************************************************
proc CALPortLinkUp {switch_id port} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrPortLinkUp $switch_id $port
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALPortLinkDown
#
#  Description    : This function is called to set the link admin status to down.
#
#  Usage          : CALPortLinkDown <switch_id port>
#
#*******************************************************************************
proc CALPortLinkDown {switch_id port} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrPortLinkDown $switch_id $port
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALAllPortsLinkUp
#
#  Description    : This function is called to set the all ports admin status to up.
#
#  Usage          : CALAllPortsLinkUp <switch_id>
#
#*******************************************************************************
proc CALAllPortsLinkUp {switch_id} {
    Netgear_connect_switch $switch_id
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrAllPortsLinkUp $switch_id
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
    Netgear_disconnect_switch $switch_id
}

#*******************************************************************************
#
#  Function Name  : CALAllPortsLinkDown
#
#  Description    : This function is called to set the link admin status to down.
#
#  Usage          : CALAllPortsLinkDown <switch_id>
#
#*******************************************************************************
proc CALAllPortsLinkDown {switch_id} {
    Netgear_connect_switch $switch_id
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrAllPortsLinkDown $switch_id
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
    Netgear_disconnect_switch $switch_id
}

#*******************************************************************************
#
#  Function Name  : CALExecPrivilegedCmd
#
#  Description    : This function is called to login a switch by telnet, 
#                   execute some commands at privileged mode and return
#                   results infomation from the switch.
#
#  Usage          : CALExecPrivilegedCmd <switch_id cmdlist {use_telnet 1}>
#
#*******************************************************************************
proc CALExecPrivilegedCmd {switch_id cmdlist {use_telnet 1}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            return [ _ntgrExecPrivilegedCmd $switch_id $cmdlist $use_telnet]
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetMacAgeTime
#
#  Description    : This function is used to set mac aging time of a switch.
#
#  Usage          : CALSetMacAgeTime <switch_id value>
#                   a value of 'default' will reset the aging time to default.
#
#*******************************************************************************
proc CALSetMacAgeTime {switch_id value} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrSetMacAgeTime $switch_id $value
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetSwitchPrompt
#
#  Description    : This function is used to set the prompt of a switch.
#
#  Usage          : CALSetSwitchPrompt <switch_id>
#
#*******************************************************************************
proc CALSetSwitchPrompt {switch_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrSetSwitchPrompt $switch_id
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALSetSwitchMngrIPAddr
#
#  Description    : This function is used to set the management ip of a switch.
#
#  Usage          : CALSetSwitchMngrIPAddr <switch_id> <addr_mask_string {}>
#
#*******************************************************************************
proc CALSetSwitchMngrIPAddr {switch_id {addr_mask_string {}}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrSetSwitchMngrIPAddr $switch_id $addr_mask_string
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableConsoleTimeout
#
#  Description    : This function is used to set the timeout value of console to forever.
#
#  Usage          : CALDisableConsoleTimeout <switch_id>
#
#*******************************************************************************
proc CALDisableConsoleTimeout {switch_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableConsoleTimeout $switch_id
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCheckExpect
#
#  Description    : This function is used to execute some commands with expected
#                   result. If get the expected result after executing those
#                   commands, it return 1; otherwise return 0.
#                   When set the 'expect' argument to 0, if get the expected result
#                   after executing those commands, it return 0; otherwise return 1.
#
#  Usage          : CALCheckExpect <switch_id> <cmds> <expect_str_list>
#                   Note that commands should be seperated by '\n' in cmds.
#                   Note that 'expect' should be 1 or 0; default is 1.
#                   Note that 'expect_str_list' will be overwritten with echo output result.
#
#*******************************************************************************
proc CALCheckExpect {switch_id cmds expect_str_list {expect 1}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            return [_ntgrCheckExpect $switch_id $cmds $expect_str_list $expect]
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALGenerateScriptFile
#
#  Description    : This function is used to generate the script file.
#
#  Usage          : CALGenerateScriptFile <switch_id> <filename {}>
#
#*******************************************************************************
proc CALGenerateScriptFile {switch_id {filename {}}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrGenerateScriptFile $switch_id $filename
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDeleteScriptFile
#
#  Description    : This function is used to delete the script file.
#
#  Usage          : CALDeleteScriptFile <switch_id> <filename {}>
#
#*******************************************************************************
proc CALDeleteScriptFile {switch_id {filename {}}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrDeleteScriptFile $switch_id $filename
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALApplyScriptFile
#
#  Description    : This function is used to delete the script file.
#
#  Usage          : CALApplyScriptFile <switch_id> <filename {}>
#
#*******************************************************************************
proc CALApplyScriptFile {switch_id {filename {}}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            _ntgrApplyScriptFile $switch_id $filename
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIsSwitchRebooting
#
#  Description    : This function is used to make sure whether switch is rebooting.
#
#  Usage          : CALIsSwitchRebooting <switch_id>
#
#*******************************************************************************
proc CALIsSwitchRebooting {switch_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_id ]

    switch $switch_vendor {
        netgear {
            return [_ntgrIsSwitchRebooting $switch_id]
        }
        cisco {
        }
        default {
            Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name	: CALGetPortPhyStatus
#
#  Description    : This function is used to get port physical status.
#         
#  Usage          : CALGetPortPhyStatus <switch_id> <port>
# 
#*******************************************************************************
proc CALGetPortPhyStatus {switch_id port} {
	set switch_vendor [ _get_switch_vendor_name  $switch_id ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrGetPortPhyStatus $switch_id $port
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALGetPortPhyMode
#
#  Description    : This function is used to get port physical mode.
#         
#  Usage          : CALGetPortPhyMode <switch_id> <port>
# 
#*******************************************************************************
proc CALGetPortPhyMode {switch_id port} {
	set switch_vendor [ _get_switch_vendor_name  $switch_id ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrGetPortPhyMode $switch_id $port
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALGetPortMtu
#
#  Description    : This function is used to get port mtu.
#         
#  Usage          : CALGetPortMtu <switch_id> <port>
# 
#*******************************************************************************
proc CALGetPortMtu {switch_id port} {
	set switch_vendor [ _get_switch_vendor_name  $switch_id ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrGetPortMtu $switch_id $port
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALGetPortDescription
#
#  Description    : This function is used to get port description.
#         
#  Usage          : CALGetPortDescription <switch_id> <port>
# 
#*******************************************************************************
proc CALGetPortDescription {switch_id port} {
	set switch_vendor [ _get_switch_vendor_name  $switch_id ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrGetPortDescription $switch_id $port
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALGetPortStatistics
#
#  Description    : This function is used to get port statistics.
#         
#  Usage          : CALGetPortStatistics <switch_id> <port> <pattern>
# 
#*******************************************************************************
proc CALGetPortStatistics {switch_id port pattern} {
	set switch_vendor [ _get_switch_vendor_name  $switch_id ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrGetPortStatistics $switch_id $port $pattern
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}


#*******************************************************************************
#
#  Function Name	: CALSetPortRangeMtu
#
#  Description    : This function is used to set port range mtu.
#         
#  Usage          : CALSetPortRangeMtu <switch_name> <portrange> <mtu>
# 
#*******************************************************************************
proc CALSetPortRangeMtu {switch_id portrange mtu} {
	set switch_vendor [ _get_switch_vendor_name  $switch_id ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrSetPortRangeMtu $switch_id $portrange $mtu
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: CALSetPortRangeSpeed
#
#  Description    : This function is used to set port range speed.
#         
#  Usage          : CALSetPortRangeSpeed <switch_name> <portrange> <speed> <duplex>
# 
#*******************************************************************************
proc CALSetPortRangeSpeed {switch_id portrange speed duplex} {
	set switch_vendor [ _get_switch_vendor_name  $switch_id ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrSetPortRangeSpeed $switch_id $portrange $speed $duplex
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}


#*******************************************************************************
#
#  Function Name  : CALGetSysDescription
#
#  Description    : This function is used to get system description of a switch.
#
#  Usage          : CALGetSysDescription <switch_name>
#
#*******************************************************************************
proc CALGetSysDescription {switch_id} {
	set switch_vendor [ _get_switch_vendor_name  $switch_id ]
	
	switch $switch_vendor {
		netgear 	{
			_ntgrGetSysDescription $switch_id
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
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}