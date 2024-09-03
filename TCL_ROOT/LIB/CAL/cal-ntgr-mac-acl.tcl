#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-mac-acl.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for MAC ACL configuration.
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
#  Function Name	: CALAddMacACL
#
#  Description    : This function is an API used to add mac acl on interface
#         
#  Usage          : CALAddMacACL <switch_name> <mac_acl_rule_list>
# 
#*******************************************************************************
proc CALAddMacACL { switch_name mac_acl_rule_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrAddMACACL $switch_name $mac_acl_rule_list
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
				Netgear_log_file "cal-ntgr-mac-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALDeleteMacACL
#
#  Description    : This function is an API used to delete mac acl on interface
#         
#  Usage          : CALDeleteMacACL <switch_name> <mac_acl_name_list>
# 
#*******************************************************************************
proc CALDeleteMacACL { switch_name mac_acl_name_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrDeleteMACACL $switch_name $mac_acl_name_list
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
				Netgear_log_file "cal-ntgr-mac-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALInterfaceMACACLEnable
#
#  Description    : This function is an API used to enable mac acl on interface
#         
#  Usage          : CALInterfaceMACACLEnable <switch_name> <interface_list>
# 
#  History        : Add parameter <bind_direction> by kenddy xie, for binding Acl in | out
#
#*******************************************************************************
proc CALInterfaceMACACLEnable { switch_name interface_list {bind_direction in}} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrInterfaceMACACLenable $switch_name $interface_list $bind_direction
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
				Netgear_log_file "cal-ntgr-mac-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALInterfaceMACACLDisable
#
#  Description    : This function is an API used to disable mac acl on interface
#         
#  Usage          : CALInterfaceMACACLDisable <switch_name> <interface_list>
# 
#*******************************************************************************
proc CALInterfaceMACACLDisable { switch_name interface_list {bind_direction in}} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrInterfaceMACACLDisable $switch_name $interface_list $bind_direction
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
				Netgear_log_file "cal-ntgr-mac-acl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALRenameMACACL
#
#  Description    : This function is an API used to rename mac acl on interface
#         
#  Usage          : CALRenameMACACL <switch_name> <oldname> <newname>
# 
#*******************************************************************************
proc CALRenameMACACL { switch_name oldname newname} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrRenameMACACL $switch_name $oldname $newname
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
				Netgear_log_file "cal-ntgr-mac-acl.tcl" "Switch not defined"
			}
		} 
}
#*******************************************************************************
#
#  Function Name	: CALSetAclTrapFlags
#
#  Description    : This function is an API used to Get max mac acl on interface
#         
#  Usage          : CALSetAclTrapFlags <switch_name> 
# 
#*******************************************************************************
proc CALSetAclTrapFlags { switch_name} {
      	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrSetAclTrapflags $switch_name 
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
				Netgear_log_file "cal-ntgr-mac-acl.tcl" "Switch not defined"
			}
		} 
}





#*******************************************************************************
#
#  Function Name  : CALGetMACACLRuleStatus
#
#  Description    : This function is used to get mac acl rule status of a switch.
#
#  Usage          : CALGetMACACLRuleStatus <switch_name> <aclName>
# 
#*******************************************************************************
proc CALGetMACACLRuleStatus { switch_name aclName} {
      	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			     _ntgrGetMACACLRuleStatus $switch_name $aclName
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
				Netgear_log_file "cal-ntgr-mac-acl.tcl" "Switch not defined"
			}
		} 
}
