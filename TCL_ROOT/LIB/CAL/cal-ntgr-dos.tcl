#!/bin/sh
################################################################################   
#
#  File Name		: cal-ntgr-dos.tcl
#
#  Desciption       	:
#         This TCL contains APIs to configure Denial of Service
#
#  Revision History 	:
#         Date            Programmer        Desciption
#        -----------------------------------------------------------------------
#        17-Nov-2010      kenddy            Created    
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALDosEnableAll
#
#  Desciption    : This function configures dos enable all on the switch
#         
#  Usage          : CALDosEnableAll (switch) 
# 
#*******************************************************************************
proc CALDosEnableAll {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosEnableAll $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosDisableAll
#
#  Desciption    : This function configures dos disable all on the switch
#         
#  Usage          : CALDosDisableAll (switch) 
# 
#*******************************************************************************
proc CALDosDisableAll {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosDisableAll $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckFirstFrag
#
#  Desciption    : This function configures check ipv4 first frag
#         
#  Usage          : CALDosCheckFirstFrag <switch> <min_tcp_head_length>
# 
#*******************************************************************************
proc CALDosCheckFirstFrag {switch {min_tcp_head_length 20}} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckFirstFrag $switch $min_tcp_head_length	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckFirstFrag
#
#  Desciption    : This function configures no check first frag
#         
#  Usage          : CALDosUnCheckFirstFrag <switch> <min_tcp_head_length>
# 
#*******************************************************************************
proc CALDosUnCheckFirstFrag {switch} {
  
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckFirstFrag $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckIcmpFrag
#
#  Desciption    : This function configures check icmp frag
#         
#  Usage          : CALDosCheckIcmpFrag <switch>
# 
#*******************************************************************************
proc CALDosCheckIcmpFrag {switch} {
  
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckIcmpFrag $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckIcmpFrag
#
#  Desciption    : This function configures no check icmp frag
#         
#  Usage          : CALDosUnCheckIcmpFrag <switch>
# 
#*******************************************************************************
proc CALDosUnCheckIcmpFrag {switch} {
  
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckIcmpFrag $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckIcmpv4Size
#
#  Desciption    : This function configures check  max icmpv4 package size
#         
#  Usage          : CALDosCheckIcmpv4Size <switch> <max_icmp_size>
# 
#*******************************************************************************
proc CALDosCheckIcmpv4Size {switch {max_icmp_size 512}} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckIcmpv4Size $switch $max_icmp_size	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckIcmpv4Size
#
#  Desciption    : This function configures no check  max icmpv4 package size
#         
#  Usage          : CALDosUnCheckIcmpv4Size <switch> 
# 
#*******************************************************************************
proc CALDosUnCheckIcmpv4Size {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckIcmpv4Size $switch
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckIcmpv6Size
#
#  Desciption    : This function configures check  max icmpv6 package size
#         
#  Usage          : CALDosCheckIcmpv6Size <switch> <max_icmp_size>
# 
#*******************************************************************************
proc CALDosCheckIcmpv6Size {switch {max_icmp_size 512}} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckIcmpv6Size $switch $max_icmp_size	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckIcmpv6Size
#
#  Desciption    : This function configures no check  max icmpv6 package size
#         
#  Usage          : CALDosUnCheckIcmpv6Size <switch> 
# 
#*******************************************************************************
proc CALDosUnCheckIcmpv6Size {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckIcmpv6Size $switch
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}
#*******************************************************************************
#
#  Function Name	: CALDosCheckL4Port
#
#  Desciption    : This function configures check L4 port number
#         
#  Usage          : CALDosCheckL4Port <switch>
# 
#*******************************************************************************
proc CALDosCheckL4Port {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckL4Port $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckL4Port
#
#  Desciption    : This function configures uncheck L4 port number
#         
#  Usage          : CALDosUnCheckL4Port <switch>
# 
#*******************************************************************************
proc CALDosUnCheckL4Port {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckL4Port $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}


#*******************************************************************************
#
#  Function Name	: CALDosCheckSipDip
#
#  Desciption    : This function configures check sip=dip
#         
#  Usage          : CALDosCheckSipDip <switch>
# 
#*******************************************************************************
proc CALDosCheckSipDip {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckSipDip $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckSipDip
#
#  Desciption    : This function configures uncheck sip=dip
#         
#  Usage          : CALDosUnCheckSipDip <switch>
# 
#*******************************************************************************
proc CALDosUnCheckSipDip {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckSipDip $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckSmacDmac
#
#  Desciption    : This function configures check smac=dmac
#         
#  Usage          : CALDosCheckSmacDmac <switch>
# 
#*******************************************************************************
proc CALDosCheckSmacDmac {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckSmacDmac $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckSmacDmac
#
#  Desciption    : This function configures no check smac=dmac
#         
#  Usage          : CALDosUnCheckSmacDmac <switch>
# 
#*******************************************************************************
proc CALDosUnCheckSmacDmac {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckSmacDmac $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckTcpFlag
#
#  Desciption    : This function configures check tcp flag
#         
#  Usage          : CALDosCheckTcpFlag <switch>
# 
#*******************************************************************************
proc CALDosCheckTcpFlag {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckTcpFlag $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckTcpFlag
#
#  Desciption    : This function configures no check tcp flag
#         
#  Usage          : CALDosUnCheckTcpFlag <switch>
# 
#*******************************************************************************
proc CALDosUnCheckTcpFlag {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckTcpFlag $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckTcpFlagSeq
#
#  Desciption    : This function configures check tcp flag and seq
#         
#  Usage          : CALDosCheckTcpFlagSeq <switch>
# 
#*******************************************************************************
proc CALDosCheckTcpFlagSeq {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckTcpFlagSeq $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckTcpFlagSeq
#
#  Desciption    : This function configures no check tcp flag and seq
#         
#  Usage          : CALDosUnCheckTcpFlagSeq <switch>
# 
#*******************************************************************************
proc CALDosUnCheckTcpFlagSeq {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckTcpFlagSeq $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckTcpFlag
#
#  Desciption    : This function configures check tcp flag
#         
#  Usage          : CALDosCheckTcpFlag <switch>
# 
#*******************************************************************************
proc CALDosCheckTcpFinUrgPsh {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckTcpFinUrgPsh $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckTcpFinUrgPsh
#
#  Desciption    : This function configures no check tcp flag
#         
#  Usage          : CALDosUnCheckTcpFinUrgPsh <switch>
# 
#*******************************************************************************
proc CALDosUnCheckTcpFinUrgPsh {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckTcpFinUrgPsh $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckTcpFrag
#
#  Desciption    : This function configures check tcp frag
#         
#  Usage          : CALDosCheckTcpFrag <switch>
# 
#*******************************************************************************
proc CALDosCheckTcpFrag {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckTcpFrag $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}


#*******************************************************************************
#
#  Function Name	: CALDosUnCheckTcpFrag
#
#  Desciption    : This function configures no check tcp frag
#         
#  Usage          : CALDosUnCheckTcpFrag <switch>
# 
#*******************************************************************************
proc CALDosUnCheckTcpFrag {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckTcpFrag $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckTcpOffset
#
#  Desciption    : This function configures check if tcp offset bit be set.
#         
#  Usage          : CALDosCheckTcpOffset <switch>
# 
#*******************************************************************************
proc CALDosCheckTcpOffset {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckTcpOffset $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}


#*******************************************************************************
#
#  Function Name	: CALDosUnCheckTcpOffset
#
#  Desciption    : This function configures no check tcp offset
#         
#  Usage          : CALDosUnCheckTcpOffset <switch>
# 
#*******************************************************************************
proc CALDosUnCheckTcpOffset {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckTcpOffset $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckTcpPort
#
#  Desciption    : This function configures check if tcp port
#         
#  Usage          : CALDosCheckTcpPort <switch>
# 
#*******************************************************************************
proc CALDosCheckTcpPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckTcpPort $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckTcpPort
#
#  Desciption    : This function configures no check if tcp port
#         
#  Usage          : CALDosUnCheckTcpPort <switch>
# 
#*******************************************************************************
proc CALDosUnCheckTcpPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckTcpPort $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckTcpSyn
#
#  Desciption    : This function configures check if tcp syn
#         
#  Usage          : CALDosCheckTcpSyn <switch>
# 
#*******************************************************************************
proc CALDosCheckTcpSyn {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckTcpSyn $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckTcpSyn
#
#  Desciption    : This function configures no check if tcp syn
#         
#  Usage          : CALDosUnCheckTcpSyn <switch>
# 
#*******************************************************************************
proc CALDosUnCheckTcpSyn {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckTcpSyn $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckTcpSynFin
#
#  Desciption    : This function configures check if tcp syn fin
#         
#  Usage          : CALDosCheckTcpSynFin <switch>
# 
#*******************************************************************************
proc CALDosCheckTcpSynFin {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckTcpSynFin $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckTcpSynFin
#
#  Desciption    : This function configures no check if tcp syn and fin
#         
#  Usage          : CALDosUnCheckTcpSynFin <switch>
# 
#*******************************************************************************
proc CALDosUnCheckTcpSynFin {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckTcpSynFin $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckUdpPort
#
#  Desciption    : This function configures check if udp port
#         
#  Usage          : CALDosCheckUdpPort <switch>
# 
#*******************************************************************************
proc CALDosCheckUdpPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckUdpPort $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckUdpPort
#
#  Desciption    : This function configures no check if udp port
#         
#  Usage          : CALDosUnCheckUdpPort <switch>
# 
#*******************************************************************************
proc CALDosUnCheckUdpPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckUdpPort $switch	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosCheckIcmpSize
#
#  Desciption    : This function configures check  max icmp package size
#         
#  Usage          : CALDosCheckIcmpSize <switch> <max_icmp_size>
# 
#*******************************************************************************
proc CALDosCheckIcmpSize {switch {max_icmp_size 512}} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosCheckIcmpSize $switch $max_icmp_size	
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDosUnCheckIcmpSize
#
#  Desciption    : This function configures no check  max icmpv4 package size
#         
#  Usage          : CALDosUnCheckIcmpSize <switch> 
# 
#*******************************************************************************
proc CALDosUnCheckIcmpSize {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDosUnCheckIcmpSize $switch
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
			Netgear_log_file "cal-ntgr-dos.tcl" "Switch not defined"
		}
	} 
}