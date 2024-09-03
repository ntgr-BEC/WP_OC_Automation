#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-ip-acl.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for IP ACL configuration.
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
#  Function Name	: CALAddIPACL
#
#  Description    : This function is an API used to add ip acl on global
#         
#  Usage          : CALAddIPACL <switch_name> <ip_acl_rule_list>
# 
#*******************************************************************************
proc CALAddIPACL { switch_name ip_acl_rule_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrAddIPACL $switch_name $ip_acl_rule_list
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALDeleteIPACL
#
#  Description    : This function is an API used to delete ip acl on global
#         
#  Usage          : CALDeleteIPACL <switch_name> <ip_acl_id_list>
# 
#*******************************************************************************
proc CALDeleteIPACL { switch_name ip_acl_id_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrDeleteIPACL $switch_name $ip_acl_id_list
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}


#*******************************************************************************
#
#  Function Name	: CALEnableIPACLOninterface
#
#  Description    : This function is an API used to enable ip acl on interface
#         
#  Usage          : CALEnableIPACLOninterface <switch_name> <interface_list> <bind_direction>
# 
#  History        : Add parameter <bind_direction> by kenddy xie, for binding Acl in | out
#
#*******************************************************************************
proc CALEnableIPACLOninterface { switch_name interface_list {bind_direction in}} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrInterfaceIPACLEnable $switch_name $interface_list $bind_direction
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALDisableIPACLOninterface
#
#  Description    : This function is an API used to disable ip acl on interface
#         
#  Usage          : CALDisableIPACLOninterface <switch_name> <interface_list> <bind_direction>
#
#  History        : Add parameter <bind_direction> by kenddy xie, for binding Acl in | out
# 
#*******************************************************************************
proc CALDisableIPACLOninterface { switch_name interface_list {bind_direction in}} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrInterfaceIPACLDisable $switch_name $interface_list $bind_direction
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALEnableIPACLOninVlan
#
#  Description    : This function is an API used to enable ip acl on VLAN
#         
#  Usage          : CALEnableIPACLOnVlan  <switch_name> <interface_list> <Vlan_ID>
# 
#*******************************************************************************
proc CALEnableIPACLOnVlan { switch_name interface_list Vlan_ID {bind_direction in}} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrVlanIPACLEnable $switch_name $interface_list $Vlan_ID $bind_direction
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALDisableIPACLOnVlan
#
#  Description    : This function is an API used to disable ip acl on Vlan
#         
#  Usage          : CALDisableIPACLOnVlan <switch_name> <interface_list> <Vlan_ID> <bind_direction>
# 
#*******************************************************************************
proc CALDisableIPACLOnVlan { switch_name interface_list Vlan_ID {bind_direction in}} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrVlanIPACLDisable $switch_name $interface_list $Vlan_ID $bind_direction
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALAddIPv6ACL
#
#  Description    : This function is an API used to add ip acl on global
#         
#  Usage          : CALAddIPv6ACL <switch_name> <ip_acl_rule_list>
# 
#*******************************************************************************
proc CALAddIPv6ACL { switch_name ip_acl_rule_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrAddIPv6ACL $switch_name $ip_acl_rule_list
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALDeleteIPv6ACL
#
#  Description    : This function is an API used to delete ip acl on global
#         
#  Usage          : CALDeleteIPv6ACL <switch_name> <ip_acl_id_list>
# 
#*******************************************************************************
proc CALDeleteIPv6ACL { switch_name ip_acl_id_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrDeleteIPv6ACL $switch_name $ip_acl_id_list
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}


#*******************************************************************************
#
#  Function Name	: CALEnableIPv6ACLOninterface
#
#  Description    : This function is an API used to enable ip acl on interface
#         
#  Usage          : CALEnableIPv6ACLOninterface <switch_name> <interface_list>
# 
#*******************************************************************************
proc CALEnableIPv6ACLOninterface { switch_name interface_list {bind_direction in}} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrInterfaceIPv6ACLEnable $switch_name $interface_list $bind_direction
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALDisableIPv6ACLOninterface
#
#  Description    : This function is an API used to disable ip acl on interface
#         
#  Usage          : CALDisableIPv6ACLOninterface <switch_name> <interface_list>
# 
#*******************************************************************************
proc CALDisableIPv6ACLOninterface { switch_name interface_list {bind_direction in}} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrInterfaceIPv6ACLDisable $switch_name $interface_list $bind_direction
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALEnableIPv6ACLOnVlan
#
#  Description    : This function is an API used to enable ipv6 acl on VLAN
#         
#  Usage          : CALEnableIPv6ACLOnVlan  <switch_name> <interface_list> <Vlan_ID> <bind_direction>
#
#  Reversion      : Create by kenddy xie 2010-07-29
#
#*******************************************************************************
proc CALEnableIPv6ACLOnVlan { switch_name interface_list Vlan_ID {bind_direction in}} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrVlanIPv6ACLEnable $switch_name $interface_list $Vlan_ID $bind_direction
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALDisableIPv6ACLOnVlan
#
#  Description    : This function is an API used to disable ipv6 acl on Vlan
#         
#  Usage          : CALDisableIPv6ACLOnVlan <switch_name> <interface_list> <Vlan_ID> <bind_direction>
#
#  Reversion      : Create by kenddy xie 2010-07-29
#
#*******************************************************************************
proc CALDisableIPv6ACLOnVlan { switch_name interface_list Vlan_ID {bind_direction in}} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrVlanIPv6ACLDisable $switch_name $interface_list $Vlan_ID $bind_direction
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}





#*******************************************************************************
#
#  Function Name  : CALGetIPACLRuleStatus
#
#  Description    : This function is used to get ip acl rule status of a switch.
#
#  Usage          : CALGetIPACLRuleStatus <switch_name> <aclName>
#
#*******************************************************************************
proc CALGetIPACLRuleStatus { switch_name aclName} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
		        _ntgrGetIPACLRuleStatus $switch_name $aclName
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name  : CALGetIPv6ACLRuleStatus
#
#  Description    : This function is used to get ipv6 acl rule status of a switch.
#
#  Usage          : CALGetIPv6ACLRuleStatus <switch_name> <aclName>
#
#*******************************************************************************
proc CALGetIPv6ACLRuleStatus { switch_name aclName} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
		        _ntgrGetIPv6ACLRuleStatus $switch_name $aclName
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#  Function Name	: CALAddRouteMap
#
#  Description    	: This function is called to add Route-map on switch
#         
#  Usage          	: CALAddRouteMap <switch_name>  <route_map_list>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc CALAddRouteMap { switch_name route_map_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
		        _ntgrAddRouteMap $switch_name $route_map_list
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#  Function Name	: CALDeleteRouteMap
#
#  Description    	: This function is called to delete Route-map on switch
#         
#  Usage          	: CALDeleteRouteMap <switch_name>  <route_map_name>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc CALDeleteRouteMap { switch_name route_map_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
		        _ntgrDeleteRouteMap $switch_name $route_map_name
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#  Function Name	: CALBlindRouteMapOnInterface
#
#  Description    	: This function is called to blind Route-map on interface on switch
#         
#  Usage          	: CALBlindRouteMapOnInterface <switch_name>  <route_map_name> <interface>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc CALBlindRouteMapOnInterface { switch_name route_map_name interface} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
		        _ntgrBlindRouteMapOnInterface $switch_name $route_map_name $interface
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#  Function Name	: CALUnBlindRouteMapOnInterface
#
#  Description    	: This function is called to unblind Route-map on interface on switch
#         
#  Usage          	: CALUnBlindRouteMapOnInterface <switch_name> <route_map_name> <interface>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc CALUnBlindRouteMapOnInterface { switch_name route_map_name interface} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
		        _ntgrUnBlindRouteMapOnInterface $switch_name $route_map_name $interface
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#  Function Name	: CALDeleteIPACLIDINRouteMap
#
#  Description    	: This function is called to delete match ip acl in Route-map on switch
#         
#  Usage          	: CALDeleteIPACLIDINRouteMap <switch_name>  <route_map_name> <IP_ACL_ID>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc CALDeleteIPACLIDINRouteMap { switch_name route_map_name IP_ACL_ID} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
		        _ntgrDeleteIPACLIDINRouteMap $switch_name $route_map_name $IP_ACL_ID
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name  : CALGetRouteMapStatus
#
#  Description    : This function is used to get route-map status of a switch.
#
#  Usage          : CALGetRouteMapStatus <switch_name> <route_map_name>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc CALGetRouteMapStatus { switch_name route_map_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
		        _ntgrGetRouteMapStatus $switch_name $route_map_name
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#  Function Name	: CALLoopAddRouteMapIPACL
#
#  Description    	: This function is called to loop add Route-map on switch
#         
#  Usage          	: CALLoopAddRouteMapIPACL <switch_name>  <route_map_name> <match_ip_addr>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc CALLoopAddRouteMapIPACL { switch_name route_map_name match_ip_addr} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
		        _ntgrLoopAddRouteMapIPACL $switch_name $route_map_name $match_ip_addr
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name  : CALCheckIPPolicyStatus
#
#  Description    : This function is used to check ip policy status of a switch.
#
#  Usage          : CALCheckIPPolicyStatus <switch_name> <route_map_name> <port>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc CALCheckIPPolicyStatus { switch_name route_map_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
		        _ntgrCheckIPPolicyStatus $switch_name $route_map_name $port
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}
#*******************************************************************************
#
#  Function Name	: CALAddIPACLRemark
#
#  Description    : This function is an API used to add ip acl remark
#         
#  Usage          : CALAddIPACLRemark <switch_name> <ip_acl_id> <ip_acl_Remark>
# 
#*******************************************************************************
proc CALAddIPACLRemark { switch_name ip_acl_id ip_acl_Remark} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrAddIPACLRemark $switch_name $ip_acl_id $ip_acl_Remark
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
				Netgear_log_file "cal-ntgr-ip-acl.tcl" "Switch not defined"
			}
		} 
}