#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-routing.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for enable routing.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        15/9/06      Nina Cheng        Created
#
#
#
#
################################################################################

#1*******************************************************************************
#
#  Function Name	: CALRoutingEnable
#
#  Description    : This function is an API used to enable routing on global
#         
#  Usage          : CALRoutingEnable <switch_name> 
# 
#*******************************************************************************
proc CALRoutingEnable { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRoutingEnable $switch_name 
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
				Netgear_log_file "cal-ntgr-routing.tcl" "Switch not defined"
			}
		} 
}

#2*******************************************************************************
#
#  Function Name	: CALRoutingDisable
#
#  Description    : This function is an API used to disable routing on global
#         
#  Usage          : CALRoutingDisable <switch_name> 
# 
#*******************************************************************************
proc CALRoutingDisable { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRoutingDisable $switch_name 
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
				Netgear_log_file "cal-ntgr-routing.tcl" "Switch not defined"
			}
		} 
}

#3*******************************************************************************
#
#  Function Name	: CALRoutingPortEnable
#
#  Description    : This function is an API used to enable routing on port
#         
#  Usage          : CALRoutingPortEnable <switch_name> <port> 
# 
#*******************************************************************************
proc CALRoutingPortEnable { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRoutingPortEnable $switch_name $port
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
				Netgear_log_file "cal-ntgr-routing.tcl" "Switch not defined"
			}
		} 
}

#4*******************************************************************************
#
#  Function Name	: CALRoutingPortDisable
#
#  Description    : This function is an API used to disable routing on port
#         
#  Usage          : CALRoutingPortDisable <switch_name> <port> 
# 
#*******************************************************************************
proc CALRoutingPortDisable { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRoutingPortDisable $switch_name $port
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
				Netgear_log_file "cal-ntgr-routing.tcl" "Switch not defined"
			}
		} 
}

#5*******************************************************************************
#
#  Function Name	: CALAddIPtoPort
#
#  Description    : This function is an API used to add IP on port
#         
#  Usage          : CALAddIPtoPort <switch_name> <port> <ip_addr> 
# 
#*******************************************************************************
proc CALAddIPtoPort { switch_name port ip_addr} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrAddIPtoPort $switch_name $port $ip_addr
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
				Netgear_log_file "cal-ntgr-routing.tcl" "Switch not defined"
			}
		} 
}

#5*******************************************************************************
#
#  Function Name	: CALAddIPv6toPort
#
#  Description    : This function is an API used to add IPv6 address on port
#         
#  Usage          : CALAddIPv6toPort <switch_name> <port> <ip_addr> 
# 
#*******************************************************************************
proc CALAddIPv6toPort { switch_name port ip_addr} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrAddIPv6toPort $switch_name $port $ip_addr
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
				Netgear_log_file "cal-ntgr-routing.tcl" "Switch not defined"
			}
		} 
}
#6*******************************************************************************
#
#  Function Name	: CALDeleteIPtoPort
#
#  Description    : This function is an API used to add IP on port
#         
#  Usage          : CALDeleteIPtoPort <switch_name> <port> <ip_addr> 
# 
#*******************************************************************************
proc CALDeleteIPtoPort { switch_name port ip_addr} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrDeleteIPtoPort $switch_name $port $ip_addr
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
				Netgear_log_file "cal-ntgr-routing.tcl" "Switch not defined"
			}
		} 
}

#6*******************************************************************************
#
#  Function Name	: CALDeleteIPv6toPort
#
#  Description    : This function is an API used to delete IPv6 address on port
#         
#  Usage          : CALDeleteIPv6toPort <switch_name> <port> <ip_addr> 
# 
#*******************************************************************************
proc CALDeleteIPv6toPort { switch_name port ip_addr} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrDeleteIPv6toPort $switch_name $port $ip_addr
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
				Netgear_log_file "cal-ntgr-routing.tcl" "Switch not defined"
			}
		} 
}



#*******************************************************************************
#
#  Function Name  : CALCheckIpRouteEntry
#
#  Description    : This function is used to check ip route entry exist or not.
#
#  Usage          : CALCheckIpRouteEntry <switch_name> <iproute_list> <notin>
#
#*******************************************************************************
proc CALCheckIpRouteEntry { switch_name iproute_list {notin 0}} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrCheckIpRouteEntry $switch_name $iproute_list $notin
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
				Netgear_log_file "cal-ntgr-routing.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name  : CALCheckIpRouteEntryByRoute
#
#  Description    : This function is used to check ip route entry exist or not by route.
#
#  Usage          : CALCheckIpRouteEntryByRoute <switch_name> <iproute> <check_list>
#
#*******************************************************************************
proc CALCheckIpRouteEntryByRoute { switch_name iproute check_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrCheckIpRouteEntryByRoute $switch_name $iproute $check_list
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
				Netgear_log_file "cal-ntgr-routing.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name  : CALCheckDelStaticRouteInfo
#
#  Description    : This function is used to check delete static route error info.
#
#  Usage          : CALCheckDelStaticRouteInfo <switch_name> <iproute>
#
#*******************************************************************************
proc CALCheckDelStaticRouteInfo { switch_name iproute} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrCheckDelStaticRouteInfo $switch_name $iproute
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
				Netgear_log_file "cal-ntgr-routing.tcl" "Switch not defined"
			}
		} 
}