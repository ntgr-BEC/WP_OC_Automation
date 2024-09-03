#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-routing-multicast.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for Routing Multicast configuration.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        2014-9-14      Jason Li        Created
#
#
#
#


#*******************************************************************************
#  Function Name	: CALMulticastRoutingEnable
#
#  Description    : This function is called to enable Multicast Routing on the Switch
#         
#  Usage          : CALMulticastRoutingEnable <switch_name> 
#
#
#*******************************************************************************
proc CALMulticastRoutingEnable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrMulticastRoutingEnable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALMulticastRoutingDisable
#
#  Description    : This function is called to disable Multicast routing on Switch
#         
#  Usage          : CALMulticastRoutingDisable <switch_name> 
#
#
#*******************************************************************************
proc CALMulticastRoutingDisable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrMulticastRoutingDisable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALPIMDenseEnable
#
#  Description    : This function is called to enable PIM Dense on the Switch
#         
#  Usage          : CALPIMDenseEnable <switch_name> 
#
#
#*******************************************************************************
proc CALPIMDenseEnable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrPIMDenseEnable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALPIMDenseDisable
#
#  Description    : This function is called to Disable PIM Dense on Switch
#         
#  Usage          : CALPIMDenseDisable <switch_name> 
#
#
#*******************************************************************************
proc CALPIMDenseDisable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrPIMDenseDisable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#3*******************************************************************************
#
#  Function Name	: CALPIMPortEnable
#
#  Description    : This function is an API used to enable PIM on port
#         
#  Usage          : CALPIMPortEnable <switch_name> <port> 
# 
#*******************************************************************************
proc CALPIMPortEnable { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrPIMPortEnable $switch_name $port
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
}

#4*******************************************************************************
#
#  Function Name	: CALPIMPortDisable
#
#  Description    : This function is an API used to disable PIM on port
#         
#  Usage          : CALPIMPortDisable <switch_name> <port> 
# 
#*******************************************************************************
proc CALPIMPortDisable { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrPIMPortDisable $switch_name $port
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#  Function Name	: CALIPv6PIMDenseEnable
#
#  Description    : This function is called to enable IPv6PIM Dense on the Switch
#         
#  Usage          : CALIPv6PIMDenseEnable <switch_name> 
#
#
#*******************************************************************************
proc CALIPv6PIMDenseEnable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIPv6PIMDenseEnable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALIPv6PIMDenseDisable
#
#  Description    : This function is called to Disable IPv6PIM Dense on Switch
#         
#  Usage          : CALIPv6PIMDenseDisable <switch_name> 
#
#
#*******************************************************************************
proc CALIPv6PIMDenseDisable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIPv6PIMDenseDisable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#3*******************************************************************************
#
#  Function Name	: CALIPv6PIMPortEnable
#
#  Description    : This function is an API used to enable IPv6PIM on port
#         
#  Usage          : CALIPv6PIMPortEnable <switch_name> <port> 
# 
#*******************************************************************************
proc CALIPv6PIMPortEnable { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrIPv6PIMPortEnable $switch_name $port
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
}

#4*******************************************************************************
#
#  Function Name	: CALIPv6PIMPortDisable
#
#  Description    : This function is an API used to disable IPv6PIM on port
#         
#  Usage          : CALIPv6PIMPortDisable <switch_name> <port> 
# 
#*******************************************************************************
proc CALIPv6PIMPortDisable { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrIPv6PIMPortDisable $switch_name $port
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#  Function Name	: CALIGMPRoutingEnable
#
#  Description    : This function is called to enable IGMP Global Configuration on the Switch
#         
#  Usage          : CALIGMPRoutingEnable <switch_name> 
#
#
#*******************************************************************************
proc CALIGMPRoutingEnable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIGMPRoutingEnable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALIGMPRoutingDisable
#
#  Description    : This function is called to disable IGMP Global Configuration on the Switch
#         
#  Usage          : CALIGMPRoutingDisable <switch_name> 
#
#
#*******************************************************************************
proc CALIGMPRoutingDisable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIGMPRoutingDisable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#3*******************************************************************************
#
#  Function Name	: CALIGMPRoutingPortEnable
#
#  Description    : This function is an API used to enable IGMP  on Port
#         
#  Usage          : CALIGMPRoutingPortEnable <switch_name> <port> 
# 
#*******************************************************************************
proc CALIGMPRoutingPortEnable { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrIGMPRoutingPortEnable $switch_name $port
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
}

#4*******************************************************************************
#
#  Function Name	: CALIGMPRoutingPortDisable
#
#  Description    : This function is an API used to  disable IGMP on Port
#         
#  Usage          : CALIGMPRoutingPortDisable <switch_name> <port> 
# 
#*******************************************************************************
proc CALIGMPRoutingPortDisable { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrIGMPRoutingPortDisable $switch_name $port
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALAddStaticMulticastRoute
#
#  Description    : This function is an API used to add static multicast route on switch
#         
#  Usage          : CALAddStaticMulticastRoute <switch_name> <address_list> 
# 
#*******************************************************************************
proc CALAddStaticMulticastRoute { switch_name address_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrAddStaticMulticastRoute $switch_name $address_list
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
}
#*******************************************************************************
#
#  Function Name	: CALDeleteStaticMulticastRoute
#
#  Description    : This function is an API used to delete static multicast route on switch
#         
#  Usage          : CALDeleteStaticMulticastRoute <switch_name> <address_list>
# 
#*******************************************************************************
proc CALDeleteStaticMulticastRoute { switch_name address_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrDeleteStaticMulticastRoute $switch_name $address_list
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name  : CALCheckMulticastRoutingEntry
#
#  Description    : This function is used to check multicast routing entry exist or not.
#
#  Usage          : CALCheckMulticastRoutingEntry <switch_name> <mroute_list> 
#
#*******************************************************************************
proc CALCheckMulticastRoutingEntry {switch_name mroute_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckMulticastRoutingEntry $switch_name $mroute_list
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#  Function Name	: CALPIMSparseEnable
#
#  Description    : This function is called to enable PIM Sparse on the Switch
#         
#  Usage          : CALPIMSparseEnable <switch_name> 
#
#
#*******************************************************************************
proc CALPIMSparseEnable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrPIMSparseEnable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALPIMSparseDisable
#
#  Description    : This function is called to Disable PIM Sparse on Switch
#         
#  Usage          : CALPIMSparseDisable <switch_name> 
#
#
#*******************************************************************************
proc CALPIMSparseDisable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrPIMSparseDisable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
} 

#*******************************************************************************
#  Function Name	: CALAddPIMBSRCandidateOnPort
#
#  Description    : This function is called to add PIM BSR Candidate On Port 
#         
#  Usage          : CALAddPIMBSRCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc CALAddPIMBSRCandidateOnPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrAddPIMBSRCandidateOnPort $switch
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALDelPIMBSRCandidateOnPort
#
#  Description    : This function is called to delete PIM BSR Candidate On Port 
#         
#  Usage          : CALDelPIMBSRCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc CALDelPIMBSRCandidateOnPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDelPIMBSRCandidateOnPort $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALAddPIMRPCandidateOnPort
#
#  Description    : This function is called to Add PIM RP Candidate On Port
#         
#  Usage          : CALAddPIMRPCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc CALAddPIMRPCandidateOnPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrAddPIMRPCandidateOnPort $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALDelPIMRPCandidateOnPort
#
#  Description    : This function is called to Delete PIM RP Candidate On Port
#         
#  Usage          : CALDelPIMRPCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc CALDelPIMRPCandidateOnPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDelPIMRPCandidateOnPort $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name  : CALCheckMulticastStaticRoutingEntry
#
#  Description    : This function is used to check multicast static routing entry exist or not.
#
#  Usage          : CALCheckMulticastStaticRoutingEntry <switch_name> <mroute_list> 
#
#*******************************************************************************
proc CALCheckMulticastStaticRoutingEntry {switch_name mroute_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckMulticastStaticRoutingEntry $switch_name $mroute_list
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name	: CALAddMulticastBoundaryOnPort
#
#  Description    : This function is an API used to add multicast Boundary On Port 
#         
#  Usage          : CALAddMulticastBoundaryOnPort <switch_name> <boundary_list> 
# 
#*******************************************************************************
proc CALAddMulticastBoundaryOnPort { switch_name boundary_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrAddMulticastBoundaryOnPort $switch_name $boundary_list
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
}
#*******************************************************************************
#
#  Function Name	: CALDeleteMulticastBoundaryOnPort
#
#  Description    : This function is an API used to delete Boundary On Port
#         
#  Usage          : CALDeleteMulticastBoundaryOnPort <switch_name> <boundary_list>
# 
#*******************************************************************************
proc CALDeleteMulticastBoundaryOnPort { switch_name boundary_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrDeleteMulticastBoundaryOnPort $switch_name $boundary_list
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#  Function Name	: CALRoutingDVMRPEnable
#
#  Description    : This function is called to enable DVMRP on the Switch
#         
#  Usage          : CALRoutingDVMRPEnable <switch_name> 
#
#
#*******************************************************************************
proc CALRoutingDVMRPEnable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrRoutingDVMRPEnable $switch
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALRoutingDVMRPDisable
#
#  Description    : This function is called to Disable DVMRP on Switch
#         
#  Usage          : CALRoutingDVMRPDisable <switch_name> 
#
#
#*******************************************************************************
proc CALRoutingDVMRPDisable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrRoutingDVMRPDisable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#3*******************************************************************************
#
#  Function Name	: CALMulticastDVMRPPortEnable
#
#  Description    : This function is an API used to enable DVMRP on port
#         
#  Usage          : CALMulticastDVMRPPortEnable <switch_name> <port> 
# 
#*******************************************************************************
proc CALMulticastDVMRPPortEnable { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMulticastDVMRPPortEnable $switch_name $port
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
}

#4*******************************************************************************
#
#  Function Name	: CALMulticastDVMRPPortDisable
#
#  Description    : This function is an API used to disable DVMRP on port
#         
#  Usage          : CALMulticastDVMRPPortDisable <switch_name> <port> 
# 
#*******************************************************************************
proc CALMulticastDVMRPPortDisable { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMulticastDVMRPPortDisable $switch_name $port
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
} 

#4*******************************************************************************
#
#  Function Name	: CALMulticastDVMRPPortDisable
#
#  Description    : This function is an API used to disable DVMRP on port
#         
#  Usage          : CALMulticastDVMRPPortDisable <switch_name> <port> 
# 
#*******************************************************************************
proc CALCheckMulticastDVMRPNeighbor { switch_name dvmrp_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrCheckMulticastDVMRPNeighbor $switch_name $dvmrp_list
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
} 

#9*******************************************************************************
#
#  Function Name	: CALSetMulticastDVMRPMetricOnPort
#
#  Description    : This function is an API used to set Multicast DVMRP Metric On Port 
#         
#  Usage          : CALSetMulticastDVMRPMetricOnPort <switch_name> <port> <metric>
# 
#*******************************************************************************
proc CALSetMulticastDVMRPMetricOnPort {switch_name port metric} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrSetMulticastDVMRPMetricOnPort $switch_name $port $metric
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
}

#9*******************************************************************************
#
#  Function Name	: CALDelMulticastDVMRPMetricOnPort
#
#  Description    : This function is an API used to set Multicast DVMRP Metric On Port 
#         
#  Usage          : CALDelMulticastDVMRPMetricOnPort <switch_name> <port> 
# 
#*******************************************************************************
proc CALDelMulticastDVMRPMetricOnPort {switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrDelMulticastDVMRPMetricOnPort $switch_name $port
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
}



#*******************************************************************************
#
#  Function Name  : CALCheckDVMRPPruneEntry
#
#  Description    : This function is used to check dvmrp prune entry exist or not.
#
#  Usage          : CALCheckDVMRPPruneEntry <switch_name> <dvmrp_list> 
#
#*******************************************************************************
proc CALCheckDVMRPPruneEntry {switch_name dvmrp_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckDVMRPPruneEntry $switch_name $dvmrp_list
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#  Function Name	: CALCreateStaticRP
#
#  Description    : This function is called to Creates a static RP for the PIM router.
#         
#  Usage          : CALCreateStaticRP <switch_name> <rp_ip> <group_addr> <group_mask> <override>
#
#
#*******************************************************************************
proc CALCreateStaticRP {switch_name rp_ip group_addr group_mask override} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCreateStaticRP $switch_name $rp_ip $group_addr $group_mask $override
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#  Function Name	: CALDelStaticRP
#
#  Description    : This function is called to delete a static RP for the PIM router.
#         
#  Usage          : CALDelStaticRP <switch_name> <rp_ip> <group_addr> <group_mask> <override>
#
#
#*******************************************************************************
proc CALDelStaticRP {switch_name rp_ip group_addr group_mask override} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelStaticRP $switch_name $rp_ip $group_addr $group_mask $override
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
} 


#*******************************************************************************
#  Function Name	: CALCheckPIMBSRElectedTable
#
#  Description    	: This function is called to check PIM BSR Elected table
#         
#  Usage          	: CALCheckPIMBSRElectedTable <switch_name> <bsr_addr> 
#
#
#*******************************************************************************
proc CALCheckPIMBSRElectedTable {switch_name bsr_addr} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckPIMBSRElectedTable $switch_name $bsr_addr
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALCheckPIMNeighbors
#
#  Description    : This function is used to check router's PIM neighbors on the interface.
#
#  Usage          : CALCheckPIMNeighbors <switch_name> <neighbor_list>
#
#*******************************************************************************
proc CALCheckPIMNeighbors {switch_name neighbor_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckPIMNeighbors $switch_name $neighbor_list
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#  Function Name	: CALCheckPIMRPAddressTable
#
#  Description    	: This function is called to check the PIM RP Address for a specific group.
#         
#  Usage          	: CALCheckPIMRPAddressTable <switch_name> <group_ip> <rp_addr> 
#
#
#*******************************************************************************
proc CALCheckPIMRPAddressTable {switch_name group_ip rp_addr} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckPIMRPAddressTable $switch_name $group_ip $rp_addr
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#  Function Name	: CALCheckPIMSMMode
#
#  Description    	: This function is called to check the PIM SM global mode 
#         
#  Usage          	: CALCheckPIMSMMode <switch_name>
#
#
#*******************************************************************************
proc CALCheckPIMSMMode {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckPIMSMMode $switch_name
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#  Function Name	: CALCheckPIMSMRPMapping
#
#  Description    	: This function is called to check the PIM SM rp mapping
#         
#  Usage          	: CALCheckPIMSMRPMapping <switch_name> <neighbor_list>
#
#
#*******************************************************************************
proc CALCheckPIMSMRPMapping {switch_name neighbor_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckPIMSMRPMapping $switch_name $neighbor_list
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#  Function Name	: CALConfigurePIMPortDRPriority
#
#  Description    	: This function is called to configutre dr-priority on port
#         
#  Usage          	: CALConfigurePIMPortDRPriority <switch_name> <port> <priority>
#
#
#*******************************************************************************
proc CALConfigurePIMPortDRPriority {switch_name port priority} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigurePIMPortDRPriority $switch_name $port $priority
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#  Function Name	: CALCheckPIMSMInterfaceStatus
#
#  Description    	: This function is called to check the PIM SM global mode 
#         
#  Usage          	: CALCheckPIMSMInterfaceStatus <switch_name> <port> <neighbor_count> <des_router>
#
#
#*******************************************************************************
proc CALCheckPIMSMInterfaceStatus {switch_name port neighbor_count des_router} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckPIMSMInterfaceStatus $switch_name $port $neighbor_count $des_router
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name	: CALAddIPv6StaticMulticastRoute
#
#  Description    : This function is an API used to add ipv6 static multicast route on switch
#         
#  Usage          : CALAddIPv6StaticMulticastRoute <switch_name> <address_list> 
# 
#*******************************************************************************
proc CALAddIPv6StaticMulticastRoute { switch_name address_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrAddIPv6StaticMulticastRoute $switch_name $address_list
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
}
#*******************************************************************************
#
#  Function Name	: CALDeleteIPv6StaticMulticastRoute
#
#  Description    : This function is an API used to delete ipv6 static multicast route on switch
#         
#  Usage          : CALDeleteIPv6StaticMulticastRoute <switch_name> <prefix>
# 
#*******************************************************************************
proc CALDeleteIPv6StaticMulticastRoute { switch_name prefix} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrDeleteIPv6StaticMulticastRoute $switch_name $prefix
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name  : CALCheckIPv6MulticastRoutingEntry
#
#  Description    : This function is used to check ipv6 multicast routing entry exist or not.
#
#  Usage          : CALCheckIPv6MulticastRoutingEntry <switch_name> <mroute_list> 
#
#*******************************************************************************
proc CALCheckIPv6MulticastRoutingEntry {switch_name sip gip protocol InPort OutPort} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIPv6MulticastRoutingEntry $switch_name $sip $gip $protocol $InPort $OutPort
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCheckIPv6StaticMulticastRouteEntry
#
#  Description    : This function is used to check ipv6 multicast routing entry exist or not.
#
#  Usage          : CALCheckIPv6StaticMulticastRouteEntry <switch_name> <sip prefixlen rpfip rpfint preference> 
#
#*******************************************************************************
proc CALCheckIPv6StaticMulticastRouteEntry {switch_name sip prefixlen rpfip rpfint preference} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIPv6StaticMulticastRouteEntry $switch_name $sip $prefixlen $rpfip $rpfint $preference
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIPv6MulticastClearMroute
#
#  Description    : This function is called to clear ipv6 mroute table
#
#  Usage          : CALIPv6MulticastClearMroute <switch_name>
#
#*******************************************************************************
proc CALIPv6MulticastClearMroute {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPv6MulticastClearMroute $switch_name
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#  Function Name	: CALIPv6PIMSparseEnable
#
#  Description    : This function is called to enable ipv6 PIM Sparse on the Switch
#         
#  Usage          : CALIPv6PIMSparseEnable <switch_name> 
#
#
#*******************************************************************************
proc CALIPv6PIMSparseEnable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIPv6PIMSparseEnable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALIPv6PIMSparseDisable
#
#  Description    : This function is called to Disable ipv6 PIM Sparse on Switch
#         
#  Usage          : CALIPv6PIMSparseDisable <switch_name> 
#
#
#*******************************************************************************
proc CALIPv6PIMSparseDisable {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIPv6PIMSparseDisable $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
} 

#*******************************************************************************
#  Function Name	: CALAddIPv6PIMBSRCandidateOnPort
#
#  Description    : This function is called to add ipv6 PIM BSR Candidate On Port 
#         
#  Usage          : CALAddIPv6PIMBSRCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc CALAddIPv6PIMBSRCandidateOnPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrAddIPv6PIMBSRCandidateOnPort $switch
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALDelIPv6PIMBSRCandidateOnPort
#
#  Description    : This function is called to delete ipv6 PIM BSR Candidate On Port 
#         
#  Usage          : CALDelIPv6PIMBSRCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc CALDelIPv6PIMBSRCandidateOnPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDelIPv6PIMBSRCandidateOnPort $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALAddIPv6PIMRPCandidateOnPort
#
#  Description    : This function is called to Add ipv6 PIM RP Candidate On Port
#         
#  Usage          : CALAddIPv6PIMRPCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc CALAddIPv6PIMRPCandidateOnPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrAddIPv6PIMRPCandidateOnPort $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALDelIPv6PIMRPCandidateOnPort
#
#  Description    : This function is called to Delete ipv6 PIM RP Candidate On Port
#         
#  Usage          : CALDelIPv6PIMRPCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc CALDelIPv6PIMRPCandidateOnPort {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDelIPv6PIMRPCandidateOnPort $switch	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALCreateIPv6PIMSMStaticRP
#
#  Description    : This function is called to Creates a static RP for the PIM router.
#         
#  Usage          : CALCreateStaticRP <switch_name> <rp_ipv6> <group_prefix> <override>
#
#
#*******************************************************************************
proc CALCreateIPv6PIMSMStaticRP {switch_name rp_ipv6 group_prefix override} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCreateIPv6PIMSMStaticRP $switch_name $rp_ipv6 $group_prefix $override
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#  Function Name	: CALDelIPv6PIMSMStaticRP
#
#  Description    : This function is called to delete a static RP for the IPV6 PIM router.
#         
#  Usage          : CALDelIPv6PIMSMStaticRP <switch_name> <rp_ipv6> <group_prefix> 
#
#
#*******************************************************************************
proc CALDelIPv6PIMSMStaticRP {switch_name rp_ipv6 group_prefix} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelIPv6PIMSMStaticRP $switch_name $rp_ipv6 $group_prefix
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigureLastMemberQueryCount
#
#  Description    : This function is called to clear configure Last Member Query Count for interface
#                   switch.
#
#  Usage          : CALConfigureLastMemberQueryCount <switch_name> <interface> <count>
#
#*******************************************************************************
proc CALConfigureLastMemberQueryCount {switch_name interface count } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigureLastMemberQueryCount $switch_name $interface $count
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigureIGMPVersion
#
#  Description    : This function is called to clear configure IGMP version for interface
#                   switch.
#
#  Usage          : CALConfigureIGMPVersion <switch_name> <interface> <version>
#
#*******************************************************************************
proc CALConfigureIGMPVersion {switch_name interface version } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigureIGMPVersion $switch_name $interface $version
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigureLastMemberQueryInterval
#
#  Description    : This function is called to clear configure Last Member Query Interval for interface
#                   switch.
#
#  Usage          : CALConfigureLastMemberQueryInterval <switch_name> <interface> <interval>
#
#*******************************************************************************
proc CALConfigureLastMemberQueryInterval {switch_name interface interval } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigureLastMemberQueryInterval $switch_name $interface $interval
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigureQueryMaxResponseTime
#
#  Description    : This function is called to clear configure Query Max Response Time for interface
#                   switch.
#
#  Usage          : CALConfigureQueryMaxResponseTime <switch_name> <interface> <time>
#
#*******************************************************************************
proc CALConfigureQueryMaxResponseTime {switch_name interface time } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigureQueryMaxResponseTime $switch_name $interface $time
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigureQueryInterval
#
#  Description    : This function is called to clear configure Query Interval for interface
#                   switch.
#
#  Usage          : CALConfigureQueryInterval <switch_name> <interface> <interval>
#
#*******************************************************************************
proc CALConfigureQueryInterval {switch_name interface interval } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigureQueryInterval $switch_name $interface $interval
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigureStartupQueryCount
#
#  Description    : This function is called to clear configure Startup Query Count for interface
#                   switch.
#
#  Usage          : CALConfigureStartupQueryCount <switch_name> <interface> <count>
#
#*******************************************************************************
proc CALConfigureStartupQueryCount {switch_name interface count } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigureStartupQueryCount $switch_name $interface $count
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALConfigureStartupQueryInterval
#
#  Description    : This function is called to clear configure Startup Query Interval for interface
#                   switch.
#
#  Usage          : CALConfigureStartupQueryInterval <switch_name> <interface> <interval>
#
#*******************************************************************************
proc CALConfigureStartupQueryInterval {switch_name interface interval } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigureStartupQueryInterval $switch_name $interface $interval
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALConfigureIGMPProxy
#
#  Description    : This function is called to clear configure IGMP Proxy for interface
#                   switch.
#
#  Usage          : CALConfigureIGMPProxy <switch_name> <interface>
#
#*******************************************************************************
proc CALConfigureIGMPProxy {switch_name interface } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigureIGMPProxy $switch_name $interface
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#  Function Name	: CALCFGIPv6PIMSSM
#
#  Description    : This function is called to config ipv6 PIM ssm range
#         
#  Usage          : CALCFGIPv6PIMSSM <switch_name> <range>
#
#
#*******************************************************************************
proc CALCFGIPv6PIMSSM {switch range} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrCfgIPv6PIMSSM $switch $range
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALDelIPv6PIMSSM
#
#  Description    : This function is called to delete ipv6 PIM ssm range
#         
#  Usage          : CALDelIPv6PIMSSM <switch_name> <range>
#
#
#*******************************************************************************
proc CALDelIPv6PIMSSM {switch range} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDelIPv6PIMSSM $switch $range
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALCfgIPv6PIMHelloIntervalOnPort
#
#  Description    : This function is called to configure ipv6 PIM hello interval on port of  Switch
#         
#  Usage          : CALCfgIPv6PIMHelloIntervalOnPort <switch_name> <port> <interval>
#
#
#*******************************************************************************
proc CALCfgIPv6PIMHelloIntervalOnPort {switch port interval} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrCfgIPv6PIMHelloInterval $switch $port $interval	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALDelIPv6PIMHelloIntervalOnPort
#
#  Description    : This function is called to Disable ipv6 PIM Sparse on Switch
#         
#  Usage          : CALDelIPv6PIMHelloIntervalOnPort <switch_name> <port>
#
#
#*******************************************************************************
proc CALDelIPv6PIMHelloIntervalOnPort {switch port} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDelIPv6PIMHelloInterval $switch $port	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
} 


#*******************************************************************************
#  Function Name	: CALIPv6PIMBSRBorderOnPortEnable
#
#  Description    : This function is called to enable ipv6 PIM BSR border on port of  Switch
#         
#  Usage          : CALIPv6PIMBSRBorderOnPortEnable <switch_name> <port> 
#
#
#*******************************************************************************
proc CALIPv6PIMBSRBorderOnPortEnable {switch port} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIPv6PIMBsrBorderEnable $switch $port
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALIPv6PIMBSRBorderOnPortDisable
#
#  Description    : This function is called to disable ipv6 PIM BSR border on port of  Switch
#         
#  Usage          : CALIPv6PIMBSRBorderOnPortDisable <switch_name> <port>
#
#
#*******************************************************************************
proc CALIPv6PIMBSRBorderOnPortDisable {switch port} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrIPv6PIMBsrBorderDisable $switch $port	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
} 

#*******************************************************************************
#  Function Name	: CALCFGIPv6PIMPruneIntervalOnPort
#
#  Description    : This function is called to Configure ipv6 PIM Sparse prune interval on port
#         
#  Usage          : CALCFGIPv6PIMPruneIntervalOnPort <switch_name> <port> <int>
#
#
#*******************************************************************************
proc CALCFGIPv6PIMPruneIntervalOnPort {switch port int} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrCfgIPv6PIMPruneInterval $switch $port $int	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALDelIPv6PIMPruneIntervalOnPort
#
#  Description    : This function is called to delete ipv6 PIM Sparse prune interval on port
#         
#  Usage          : CALDelIPv6PIMPruneIntervalOnPort <switch_name> <port>
#
#
#*******************************************************************************
proc CALDelIPv6PIMPruneIntervalOnPort {switch port} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDelIPv6PIMPruneInterval $switch $port	
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
} 


#*******************************************************************************
#  Function Name	: CALCheckIPv6PIMBSRElectedTable
#
#  Description    	: This function is called to check ipv6 PIM BSR Elected table
#         
#  Usage          	: CALCheckIPv6PIMBSRElectedTable <switch_name> <bsr_addr> 
#
#
#*******************************************************************************
proc CALCheckIPv6PIMBSRElectedTable {switch_name bsr_addr} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIPv6PIMBSRElectedTable $switch_name $bsr_addr
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALCheckIPv6PIMNeighbors
#
#  Description    : This function is used to check router's ipv6 PIM neighbors on the interface.
#
#  Usage          : CALCheckIPv6PIMNeighbors <switch_name> <neighbor_list>
#
#*******************************************************************************
proc CALCheckIPv6PIMNeighbors {switch_name neighbor_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIPv6PIMNeighbors $switch_name $neighbor_list
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCheckIPv6MulticastSSMGroup
#
#  Description    : This function is used to check ipv6 multicast ssm group exist or not.
#
#  Usage          : CALCheckIPv6MulticastSSMGroup <switch_name> <ssmgroup> 
#
#*******************************************************************************
proc CALCheckIPv6MulticastSSMGroup {switch_name ssmgroup} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIPv6MulticastSSMGroup $switch_name $ssmgroup
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#  Function Name	: CALCheckIPv6PIMSMRPMapping
#
#  Description    	: This function is called to check the ipv6 PIM SM rp mapping
#         
#  Usage          	: CALCheckIPv6PIMSMRPMapping <switch_name> <neighbor_list>
#
#
#*******************************************************************************
proc CALCheckIPv6PIMSMRPMapping {switch_name neighbor_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIPv6PIMSMRPMapping $switch_name $neighbor_list
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#  Function Name	: CALCheckIPv6PIMSMMode
#
#  Description    	: This function is called to check the ipv6 PIM SM global mode 
#         
#  Usage          	: CALCheckIPv6PIMSMMode <switch_name>
#
#
#*******************************************************************************
proc CALCheckIPv6PIMSMMode {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIPv6PIMSMMode $switch_name
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#  Function Name	: CALCheckIPv6PIMSMInterfaceStatus
#
#  Description    	: This function is called to check the ipv6 PIM SM global mode 
#         
#  Usage          	: CALCheckIPv6PIMSMInterfaceStatus <switch_name> <port> <neighbor_count> <des_router>
#
#
#*******************************************************************************
proc CALCheckIPv6PIMSMInterfaceStatus {switch_name port neighbor_count des_router} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIPv6PIMSMInterfaceStatus $switch_name $port $neighbor_count $des_router
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#  Function Name	: CALCheckIPv6PIMRPAddressTable
#
#  Description    	: This function is called to check the ipv6 PIM RP Address for a specific group.
#         
#  Usage          	: CALCheckIPv6PIMRPAddressTable <switch_name> <group_ip> <rp_addr> 
#
#
#*******************************************************************************
proc CALCheckIPv6PIMRPAddressTable {switch_name group_ip rp_addr} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIPv6PIMRPAddressTable $switch_name $group_ip $rp_addr
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#  Function Name	: CALConfigureIPv6PIMPortDRPriority
#
#  Description    	: This function is called to configutre ipv6 dr-priority on port
#         
#  Usage          	: CALConfigureIPv6PIMPortDRPriority <switch_name> <port> <priority>
#
#
#*******************************************************************************
proc CALConfigureIPv6PIMPortDRPriority {switch_name port priority} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigureIPv6PIMPortDRPriority $switch_name $port $priority
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#  Function Name	: CALDelIPv6PIMPortDRPriority
#
#  Description    	: This function is called to delete ipv6 dr-priority on port
#         
#  Usage          	: CALDelIPv6PIMPortDRPriority <switch_name> <port> 
#
#
#*******************************************************************************
proc CALDelIPv6PIMPortDRPriority {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelIPv6PIMPortDRPriority $switch_name $port
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
            Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#  Function Name	: CALCFGIPPIMSSM
#
#  Description    : This function is called to config ip PIM ssm range
#         
#  Usage          : CALCFGIPPIMSSM <switch_name> <range>
#
#
#*******************************************************************************
proc CALCFGIPPIMSSM {switch range} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrCfgIPPIMSSM $switch $range
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALDelIPPIMSSM
#
#  Description    : This function is called to delete ip PIM ssm range
#         
#  Usage          : CALDelIPPIMSSM <switch_name> <range>
#
#
#*******************************************************************************
proc CALDelIPPIMSSM {switch range} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDelIPPIMSSM $switch $range
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
			Netgear_log_file "cal-ntgr-routing-multicast.tcl" "Switch not defined"
		}
	} 
}