#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-router-discovery.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for Router Discovery configuration.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        27/1/14      Jason Li        Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALRouterDiscoveryEnable
#
#  Description    : This function is an API used to enable router discovery on port
#         
#  Usage          : CALRouterDiscoveryEnable <switch_name> <port>
# 
#*******************************************************************************
proc CALRouterDiscoveryEnable { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRouterDiscoveryEnable $switch_name $port
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
				Netgear_log_file "cal-ntgr-router-discovery.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALRouterDiscoveryDisable
#
#  Description    : This function is an API used to disable router discovery on port
#         
#  Usage          : CALRouterDiscoveryDisable <switch_name> <port>
# 
#*******************************************************************************
proc CALRouterDiscoveryDisable { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRouterDiscoveryDisable $switch_name $port
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
				Netgear_log_file "cal-ntgr-router-discovery.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALRouterDiscoverySetMaxAdvert
#
#  Description    : This function is an API used to set max advertise interval on port
#         
#  Usage          : CALRouterDiscoverySetMaxAdvert <switch_name> <port> <maxadv>
# 
#*******************************************************************************
proc CALRouterDiscoverySetMaxAdvert { switch_name port maxadv} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRouterDiscoverySetMaxAdvert $switch_name $port $maxadv
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
				Netgear_log_file "cal-ntgr-router-discovery.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALRouterDiscoveryDeleteMaxAdvert
#
#  Description    : This function is an API used to delete max advertise interval on port
#         
#  Usage          : CALRouterDiscoveryDeleteMaxAdvert <switch_name> <port> 
# 
#*******************************************************************************
proc CALRouterDiscoveryDeleteMaxAdvert { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRouterDiscoveryDeleteMaxAdvert $switch_name $port
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
				Netgear_log_file "cal-ntgr-router-discovery.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALRouterDiscoverySetMinAdvert
#
#  Description    : This function is an API used to set min advertise interval on port
#         
#  Usage          : CALRouterDiscoverySetMinAdvert <switch_name> <port> <minadv>
# 
#*******************************************************************************
proc CALRouterDiscoverySetMinAdvert { switch_name port minadv} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRouterDiscoverySetMinAdvert $switch_name $port $minadv
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
				Netgear_log_file "cal-ntgr-router-discovery.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALRouterDiscoveryDeleteMinAdvert
#
#  Description    : This function is an API used to delete min advertise interval on port
#         
#  Usage          : CALRouterDiscoveryDeleteMinAdvert <switch_name> <port>
# 
#*******************************************************************************
proc CALRouterDiscoveryDeleteMinAdvert { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRouterDiscoveryDeleteMinAdvert $switch_name $port
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
				Netgear_log_file "cal-ntgr-router-discovery.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALRouterDiscoverySetHoldTime
#
#  Description    : This function is an API used to set hold time on port
#         
#  Usage          : CALRouterDiscoverySetHoldTime <switch_name> <port> <holdtime>
# 
#*******************************************************************************
proc CALRouterDiscoverySetHoldTime { switch_name port holdtime} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRouterDiscoverySetHoldTime $switch_name $port $holdtime
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
				Netgear_log_file "cal-ntgr-router-discovery.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALRouterDiscoveryDeleteHoldTime
#
#  Description    : This function is an API used to delete hold time on port
#         
#  Usage          : CALRouterDiscoveryDeleteHoldTime <switch_name> <port>
# 
#*******************************************************************************
proc CALRouterDiscoveryDeleteHoldTime { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRouterDiscoveryDeleteHoldTime $switch_name $port
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
				Netgear_log_file "cal-ntgr-router-discovery.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALRouterDiscoverySetPreference
#
#  Description    : This function is an API used to set preference on port
#         
#  Usage          : CALRouterDiscoverySetPreference <switch_name> <port> <preference>
# 
#*******************************************************************************
proc CALRouterDiscoverySetPreference { switch_name port preference} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRouterDiscoverySetPreference $switch_name $port $preference
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
				Netgear_log_file "cal-ntgr-router-discovery.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALRouterDiscoveryDeletePreference
#
#  Description    : This function is an API used to delete preference on port
#         
#  Usage          : CALRouterDiscoveryDeletePreference <switch_name> <port>
# 
#*******************************************************************************
proc CALRouterDiscoveryDeletePreference { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrRouterDiscoveryDeletePreference $switch_name $port
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
				Netgear_log_file "cal-ntgr-router-discovery.tcl" "Switch not defined"
			}
		} 
}

