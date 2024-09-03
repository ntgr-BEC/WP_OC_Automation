#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-static-ip.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for static route configuration.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        18/8/06      Nina Cheng        Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALAddStaticRoute
#
#  Description    : This function is an API used to add static ip on switch
#         
#  Usage          : CALAddStaticRoute <switch_name> <address_list> 
# 
#*******************************************************************************
proc CALAddStaticRoute { switch_name address_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrAddStaticRoute $switch_name $address_list
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
				Netgear_log_file "cal-ntgr-static-ip.tcl" "Switch not defined"
			}
		} 
}
#*******************************************************************************
#
#  Function Name	: CALDeleteStaticRoute
#
#  Description    : This function is an API used to delete static route on switch
#         
#  Usage          : CALDeleteStaticRoute <switch_name> <address_list>
# 
#*******************************************************************************
proc CALDeleteStaticRoute { switch_name address_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrDeleteStaticRoute $switch_name $address_list
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
				Netgear_log_file "cal-ntgr-static-ip.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALModifyStaticRoutePrefrence
#
#  Description    : This function is an API used to modify the prefrence for static route on switch
#         
#  Usage          : CALModifyStaticRoutePrefrence <switch_name> <address_list>
# 
#*******************************************************************************
proc CALModifyStaticRoutePrefrence { switch_name address_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrModifyStaticRoutePrefrence $switch_name $address_list
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
				Netgear_log_file "cal-ntgr-static-ip.tcl" "Switch not defined"
			}
		} 
}
#*******************************************************************************
#
#  Function Name	: CALSetStaticRoutePrefrenceToDefault
#
#  Description    : This function is an API used to set the prefrence for static route to default on switch
#         
#  Usage          : CALSetStaticRoutePrefrenceToDefault <switch_name> <address_list>
# 
#*******************************************************************************
proc CALSetStaticRoutePrefrenceToDefault { switch_name address_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrSetStaticRoutePrefrenceToDefault $switch_name $address_list
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
				Netgear_log_file "cal-ntgr-static-ip.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALAddDefaultRoute
#
#  Description    : This function is an API used to add default route on switch
#         
#  Usage          : CALAddDefaultRoute <switch_name> <default_route>
# 
#*******************************************************************************
proc CALAddDefaultRoute { switch_name default_routes} {
	foreach default_route $default_routes {	   
		set flag [lindex $default_route 0]	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			     if {$flag != "default"} {
				      _ntgrAddDefaultRoute $switch_name $default_route
				   } else {return}
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
				Netgear_log_file "cal-ntgr-static-ip.tcl" "Switch not defined"
			}
		}
	} 
}

#********************************************************************************
#  Function Name	: CALAddDefaultStaticRoute
#
#  Description    	: This function is called to add default routes 
#         
#  Usage          	: CALAddDefaultStaticRoute <switch_name> <default_route_list> 
#
#
#*******************************************************************************
proc CALAddDefaultStaticRoute { switch_name default_route_list} {
  
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    _ntgrAddDefaultStaticRoute $switch_name $default_route_list
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
				Netgear_log_file "cal-ntgr-static-ip.tcl" "Switch not defined"
			}
		}
}

#*******************************************************************************
#
#  Function Name	: CALDeleteDefaultRoute
#
#  Description    : This function is an API used to delete default route on switch
#         
#  Usage          : CALDeleteDefaultRoute <switch_name> 
#
#*******************************************************************************
proc CALDeleteDefaultRoute { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrDeleteDefaultRoute $switch_name 
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
				Netgear_log_file "cal-ntgr-static-ip.tcl" "Switch not defined"
			}
		} 
}

#********************************************************************************
#  Function Name	: CALDeleteDefaultStaticRoute
#
#  Description    	: This function is called to add default routes 
#         
#  Usage          	: CALDeleteDefaultStaticRoute <switch_name> 
#
#
#*******************************************************************************
proc CALDeleteDefaultStaticRoute { switch_name } {
  
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    _ntgrDeleteDefaultStaticRoute $switch_name
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
				Netgear_log_file "cal-ntgr-static-ip.tcl" "Switch not defined"
			}
		}
}

#*******************************************************************************
#
#  Function Name	: CALModifyDefaultRoutePrefrence
#
#  Description    : This function is an API used to modify prefrence for default route on switch
#         
#  Usage          : CALModifyDefaultRoutePrefrence <switch_name> <default_route> 
#
#*******************************************************************************
proc CALModifyDefaultRoutePrefrence { switch_name default_route} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrModifyDefaultRoutePrefrence $switch_name $default_route
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
				Netgear_log_file "cal-ntgr-static-ip.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALSetDefaultRoutePrefrenceToDefault
#
#  Description    : This function is an API used to set prefrence for default route to default on switch
#         
#  Usage          : CALSetDefaultRoutePrefrenceToDefault <switch_name> <default_route> 
#
#*******************************************************************************
proc CALSetDefaultRoutePrefrenceToDefault { switch_name default_route} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrSetDefaultRoutePrefrenceToDefault $switch_name $default_route
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
				Netgear_log_file "cal-ntgr-static-ip.tcl" "Switch not defined"
			}
		} 
}


#*******************************************************************************
#
#  Function Name	: CALModifyStaticRouteDistance
#
#  Description    : This function is an API used to set prefrence for default route to default on switch
#         
#  Usage          : CALModifyStaticRouteDistance <switch_name> <distance>
#
#*******************************************************************************
proc CALModifyStaticRouteDistance { switch_name distance} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			     if {$distance == "default"} {
			         return
			     } elseif {$distance <1 || $distance >255} {
			        puts "Error:distance must be 1~255!"
			        return
			     } else {
				       _ntgrModifyStaticRouteDistance $switch_name $distance
				   }
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
				Netgear_log_file "cal-ntgr-static-ip.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALSetStaticRouteDistanceToDefault
#
#  Description    : This function is an API used to set prefrence for default route to default on switch
#         
#  Usage          : CALSetStaticRouteDistanceToDefault <switch_name>
#
#*******************************************************************************
proc CALSetStaticRouteDistanceToDefault { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrSetStaticRouteDistanceToDefault $switch_name 
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
				Netgear_log_file "cal-ntgr-static-ip.tcl" "Switch not defined"
			}
		} 
}
