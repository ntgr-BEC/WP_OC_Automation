#!/bin/sh
################################################################################   
#
#  File Name		: cal-ntgr-poe.tcl
#
#  Desciption       	:
#         This TCL contains APIs to configure mac filter. This is CLI 
#	    abstraction Layer for mac filter Configuration
#
#  Revision History 	:
#         Date         Programmer        Desciption
#        -----------------------------------------------------------------------
#        01-Nov-2010   kenddy            Created    
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALSetIntfPoeEnable
#
#  Desciption     : This function set poe enable for interface
#         
#  Usage          : CALSetIntfPoeEnable <switch_name> <port_list>
# 
#*******************************************************************************
proc CALSetIntfPoeEnable {switch_name port_list} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrSetIntfPoeEnable $switch_name $port_list
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALSetIntfPoeEnable
#
#  Desciption     : This function set poe enable for interface
#         
#  Usage          : CALSetIntfPoeEnable <switch_name> <port_list>
# 
#*******************************************************************************
proc CALSetIntfPoeDisable {switch_name port_list} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrSetIntfPoeDisable $switch_name $port_list
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}


#*******************************************************************************
#
#  Function Name	: CALSetIntfPoePowerLimitType
#
#  Desciption     : This function set poe power mode for interface
#         
#  Usage          : CALSetIntfPoePowerLimitType <switch_name> <port_list> <limit_type>
# 
#*******************************************************************************
proc CALSetIntfPoePowerLimitType {switch_name port_list limit_type} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrSetIntfPoePowerLimitType $switch_name $port_list $limit_type
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALRestoreIntfPoePowerLimitType
#
#  Desciption     : This function restore poe power mode to default for interface.
#         
#  Usage          : CALRestoreIntfPoePowerLimitType <switch_name> <port_list>
# 
#*******************************************************************************
proc CALRestoreIntfPoePowerLimitType {switch_name port_list} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrRestoreIntfPoePowerLimitType $switch_name $port_list
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALSetIntfPoeUserDefinedLimitWt
#
#  Desciption     : This function set poe power user-defined limit xx (mW) for interface.
#         
#  Usage          : CALSetIntfPoeUserDefinedLimitWt <switch_name> <port_list> <limit_wt>
# 
#*******************************************************************************
proc CALSetIntfPoeUserDefinedLimitWt {switch_name port_list limit_wt} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrSetIntfPoeUserDefinedLimitWt $switch_name $port_list $limit_wt
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALRestoreIntfPoeUserDefinedLimitWt
#
#  Desciption     : This function restore poe power user-defined limit to default (mW) for interface.
#         
#  Usage          : CALRestoreIntfPoeUserDefinedLimitWt <switch_name> <port_list>
# 
#*******************************************************************************
proc CALRestoreIntfPoeUserDefinedLimitWt {switch_name port_list} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrRestoreIntfPoeUserDefinedLimitWt $switch_name $port_list
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}


#*******************************************************************************
#
#  Function Name	: CALSetIntfPoeDetectionMode
#
#  Desciption     : This function set poe detection mode for interface.
#         
#  Usage          : CALSetIntfPoeDetectionMode <switch_name> <port_list> <detect_mode>
# 
#*******************************************************************************
proc CALSetIntfPoeDetectionMode {switch_name port_list detect_mode} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrSetIntfPoeDetectionMode $switch_name $port_list $detect_mode
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALRestoreIntfPoeDetectionMode
#
#  Desciption     : This function restore poe detection mode to default for interface.
#         
#  Usage          : CALRestoreIntfPoeDetectionMode <switch_name> <port_list>
# 
#*******************************************************************************
proc CALRestoreIntfPoeDetectionMode {switch_name port_list} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrRestoreIntfPoeDetectionMode $switch_name $port_list
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALSetIntfPoePriority
#
#  Desciption     : This function set poe priority for interface.
#         
#  Usage          : CALSetIntfPoePriority <switch_name> <port_list> <prio>
# 
#*******************************************************************************
proc CALSetIntfPoePriority {switch_name port_list prio} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrSetIntfPoePriority $switch_name $port_list $prio
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}


#*******************************************************************************
#
#  Function Name	: CALEnablePoeTrap
#
#  Desciption     : This function enable poe trap in global.
#         
#  Usage          : CALEnablePoeTrap <switch_name>
# 
#*******************************************************************************
proc CALEnablePoeTrap {switch_name} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrEnablePoeTrap $switch_name
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDisablePoeTrap
#
#  Desciption     : This function disable poe trap in global.
#         
#  Usage          : CALDisablePoeTrap <switch_name>
# 
#*******************************************************************************
proc CALDisablePoeTrap {switch_name} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrDisablePoeTrap $switch_name
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALPoePowerManagementMode
#
#  Desciption     : This function is called to config poe power management mode
#         
#  Usage          : CALPoePowerManagementMode <switch_name> <unit_id> <m_mode>
# 
#*******************************************************************************
proc CALPoePowerManagementMode {switch_name unit_id m_mode} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrPoePowerManagementMode $switch_name $unit_id $m_mode
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALSetPoeUsageThreshold
#
#  Desciption     : This function set poe usage threshold in global.
#         
#  Usage          : CALSetPoeUsageThreshold <switch_name> <unit_id> <thr_value>
# 
#*******************************************************************************
proc CALSetPoeUsageThreshold {switch_name unit_id thr_value} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrSetPoeUsageThreshold $switch_name $unit_id $thr_value
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALRestorePoeUsageThreshold
#
#  Desciption     : This function restore poe usage threshold in global.
#         
#  Usage          : CALRestorePoeUsageThreshold <switch_name> <unit_id>
# 
#*******************************************************************************
proc CALRestorePoeUsageThreshold {switch_name unit_id} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrRestorePoeUsageThreshold $switch_name $unit_id
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALPoeCheckPortInfo
#
#  Desciption     : This function is called to check port info is crrect or not.
#         
#  Usage          : CALPoeCheckPortInfo <switch_name> <port> <expect_class> <expect_power> <expect_status>
# 
#*******************************************************************************
proc CALPoeCheckPortInfo {switch_name port expect_class expect_power expect_status} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			return [_ntgrPoeCheckPortInfo $switch_name $port $expect_class $expect_power $expect_status]
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}



#*******************************************************************************
#
#  Function Name	: CALPoeCheckPortIfSupportPOEPlus
#
#  Desciption     : This function is called to check port support poe+ or not.
#         
#  Usage          : CALPoeCheckPortIfSupportPOEPlus <switch_name> <port>
# 
#*******************************************************************************
proc CALPoeCheckPortIfSupportPOEPlus {switch_name port} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			return [_ntgrPoeCheckPortIfSupportPOEPlus $switch_name $port]
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}


#*******************************************************************************
#
#  Function Name	: CALPoeGetPortListTotalPowerConsumed
#
#  Desciption     : This function to get port Power Consumed(mw)
#         
#  Usage          : CALPoeGetPortListTotalPowerConsumed <switch_name> <port_list>
# 
#*******************************************************************************
proc CALPoeGetPortListTotalPowerConsumed {switch_name port_list} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			return [_ntgrPoeGetPortListTotalPowerConsumed $switch_name $port_list]
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}


#*******************************************************************************
#
#  Function Name	: CALPoeGetTotalPowerConsumed
#
#  Desciption     : This function to get total Power Consumed(mw)
#         
#  Usage          : CALPoeGetTotalPowerConsumed <switch_name> 
# 
#*******************************************************************************
proc CALPoeGetTotalPowerConsumed {switch_name} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			return [_ntgrPoeGetTotalPowerConsumed $switch_name]
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}


#*******************************************************************************
#
#  Function Name	: CALPoeGetTotalPower
#
#  Desciption     : This function to get total Power (Main AC)
#         
#  Usage          : CALPoeGetTotalPower <switch_name> 
# 
#*******************************************************************************
proc CALPoeGetTotalPower {switch_name} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			return [_ntgrPoeGetTotalPower $switch_name]
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}
#*******************************************************************************
#
#  Function Name	: CALPoeGetThresholdPower
#
#  Desciption     : This function to get Threshold Power (Watts)
#         
#  Usage          : CALPoeGetTotalPowerConsumed <switch_name> 
# 
#*******************************************************************************
proc CALPoeGetThresholdPower {switch_name} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			return [_ntgrPoeGetThresholdPower $switch_name]
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
			Netgear_log_file "cal-ntgr-poe.tcl" "Switch not defined"
		}
	} 
}


