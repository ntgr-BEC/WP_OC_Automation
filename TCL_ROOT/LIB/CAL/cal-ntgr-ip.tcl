#!/bin/sh
################################################################################   
#
#  File Name		: cal-ntgr-ip.tcl
#
#  Desciption       	:
#         This TCL contains APIs to configure ip. This is CLI 
#	    abstraction Layer for ip configuration
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
#  Function Name	: CALIpRoutingConfig
#
#  Desciption    : This function configures routing properties on the switch
#         
#  Usage          : CALIpRoutingConfig (switch) 
# 
#*******************************************************************************
proc CALIpRoutingConfig {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIpRoutingEnable $switch	
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALIpConfigStaticRoute
#
#  Desciption    : This function adds/deletes static route
#         
#  Usage          : CALIpConfigStaticRoute(switch) 
# 
#*******************************************************************************
proc CALIpConfigStaticRoute {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIpConfigStaticRoute $switch	
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALIpConfigRoutingPerPort
#
#  Desciption    : This function enables routing at interface level
#         
#  Usage          : CALIpConfigRoutingPerPort (switch) 
# 
#*******************************************************************************
proc CALIpConfigRoutingPerPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIpConfigRoutingPerPort $switch	
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALIpConfigRoutingPerPort
#
#  Desciption    : This function enables routing at interface level
#         
#  Usage          : CALIpConfigRoutingPerPort (switch) 
# 
#*******************************************************************************
proc CALIpConfigRoutingPerPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIpConfigRoutingPerPort $switch	
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}






#*******************************************************************************
#  Function Name	: CALIpRoutingEnable
#
#  Description    : This function is called to enable IP Routing on the Switch
#         
#  Usage          : CALIpRoutingEnable <switch_name> 
#
#
#*******************************************************************************
proc CALIpRoutingEnable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIpRoutingEnable $switch	
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALIpRoutingDisable
#
#  Description    : This function is called to disable IP routing on Switch
#         
#  Usage          : CALIpRoutingDisable <switch_name> 
#
#
#*******************************************************************************
proc CALIpRoutingDisable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIpRoutingDisable $switch	
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALIpRoutingEnablePerPort
#
#  Description    : This function is called to enable IP Routing Per port
#         
#  Usage          : CALIpRoutingEnablePerPort <switch_name> <port>
#
#
#*******************************************************************************
proc CALIpRoutingEnablePerPort {switch port} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIpRoutingEnablePerPort $switch	$port
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALIpRoutingDisablePerPort
#
#  Description    : This function is called to disable IP Routing per port
#         
#  Usage          : CALIpRoutingDisablePerPort <switch_name> <port>
#
#
#*******************************************************************************
proc CALIpRoutingDisablePerPort {switch port} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIpRoutingDisablePerPort $switch	$port
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALIpDhcpEnablePerPort
#
#  Description    : This function is called to enable ip dhcp per port
#         
#  Usage          : CALIpDhcpEnablePerPort <switch_name> <port>
#
#*******************************************************************************
proc CALIpDhcpEnablePerPort {switch port} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIpDhcpEnablePerPort $switch	$port
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALIpDhcpDisablePerPort
#
#  Description    : This function is called to disable ip dhcp per port
#         
#  Usage          : CALIpDhcpDisablePerPort <switch_name> <port>
#
#*******************************************************************************
proc CALIpDhcpDisablePerPort {switch port} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIpDhcpDisablePerPort $switch	$port
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}



#*******************************************************************************
#  Function Name  : CALGetIpAddressOnPort
#
#  Description    : This function is get ip address on port.
#
#  Usage          : CALGetIpAddressOnPort <switch_name> <port>
#
#*******************************************************************************
proc CALGetIpAddressOnPort {switch port} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrGetIpAddressOnPort $switch	$port
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALAddIpAddressOnPort
#
#  Description    : This function is called to assign IP Address on the port
#         
#  Usage          : CALAddIpAddressOnPort <switch_name> <port> <ip_addr> <ip_mask>
#
#
#*******************************************************************************
proc CALAddIpAddressOnPort {switch port ip_addr ip_mask} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrAddIpAddressOnPort $switch	$port $ip_addr $ip_mask
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALAddSecondIpAddressOnPort
#
#  Description    : This function is called to assign secondary IP Address on the port
#         
#  Usage          : CALAddSecondIpAddressOnPort <switch_name> <port> <ip_addr> <ip_mask>
#
#  Note           : Added by kenddy.xie
#*******************************************************************************
proc CALAddSecondIpAddressOnPort {switch port ip_addr ip_mask} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrAddSecondIpAddressOnPort $switch	$port $ip_addr $ip_mask
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}
#*******************************************************************************
#  Function Name	: CALDeleteIpAddressOnPort
#
#  Description    : This function is called to delete IP Address on the port
#         
#  Usage          : CALDeleteIpAddressOnPort <switch_name> <port> <ip_addr> <ip_mask>
#
#
#*******************************************************************************
proc CALDeleteIpAddressOnPort {switch port ip_addr ip_mask} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDeleteIpAddressOnPort $switch	$port $ip_addr $ip_mask
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}




#*******************************************************************************
#  Function Name	: CALSetStaticARP
#
#  Description    : This function is to set switch static arp
#         
#  Usage          :  CALSetStaticARP <switch> <ipaddress> <macaddr>
#
#
#*******************************************************************************
proc CALSetStaticARP {switch ipaddress macaddr} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrSetStaticARP $switch	$ipaddress $macaddr
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALDisableStaticARP
#
#  Description    : This function is to disable switch static arp
#         
#  Usage          :  CALDisableStaticARP <switch> <ipaddress> 
#
#
#*******************************************************************************
proc CALDisableStaticARP {switch ipaddress} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDisableStaticARP $switch	$ipaddress
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

proc CALConfigArpCacheSize {switch cache_size} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrConfigArpCacheSize $switch	$cache_size
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

proc CALRestoreArpCacheSize {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrRestoreArpCacheSize $switch
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

proc CALEnableArpDynamicRenew {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrEnableArpDynamicRenew $switch
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

proc CALDisableArpDynamicRenew {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDisableArpDynamicRenew $switch
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

proc CALConfigArpResponseTime {switch resp_time} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrConfigArpResponseTime $switch $resp_time
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

proc CALRestoreArpResponseTime {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrRestoreArpResponseTime $switch
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

proc CALConfigArpRetriesCount {switch retr_time} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrConfigArpRetriesCount $switch $retr_time
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

proc CALRestoreArpRetriesCount {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrRestoreArpRetriesCount $switch
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

proc CALConfigArpAgeout {switch ageout_time} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrConfigArpAgeout $switch $ageout_time
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

proc CALRestoreArpAgeout {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrRestoreArpAgeout $switch
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

proc CALAddStaticArp {switch ip_add mac_add} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrAddStaticArp $switch $ip_add $mac_add
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}

proc CALDeleteStaticArp {switch ip_add} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDeleteStaticArp $switch $ip_add
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
			Netgear_log_file "cal-ntgr-ip.tcl" "Switch not defined"
		}
	} 
}