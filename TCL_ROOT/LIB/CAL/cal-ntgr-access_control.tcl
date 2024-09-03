#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-access_control.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for access control configuration.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        22/5/15      Bo Shi        Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALAddAcessControlProfile
#
#  Description    : This function is an API used to add access control profile on global
#         
#  Usage          : CALAddAcessControlProfile <switch_name> <profilename>
# 
#*******************************************************************************
proc CALAddAcessControlProfile { switch_name profilename} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _switch_create_AccessProfileName $switch_name $profilename
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
				Netgear_log_file "cal-ntgr-access_control.tcl" "Switch not defined"
			}
		} 
}


#*******************************************************************************
#
#  Function Name	: CALGetAcessControlProfileName
#
#  Description    : This function is an API used to get access control profile name on global
#         
#  Usage          : CALAddAcessControlProfile <switch_name> <profilename>
# 
#*******************************************************************************
proc CALGetAcessControlProfileName { switch_name profilename} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _switch_get_AccessProfileName $switch_name $profilename
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
				Netgear_log_file "cal-ntgr-access_control.tcl" "Switch not defined"
			}
		} 
}


#*******************************************************************************
#
#  Function Name	: CALDeleteAcessControlProfile
#
#  Description    : This function is an API used to delete access control profile on global
#         
#  Usage          : CALDeleteAcessControlProfile <switch_name> <profilename>
# 
#*******************************************************************************
proc CALDeleteAcessControlProfile { switch_name profilename} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _switch_delete_AccessProfileName $switch_name $profilename
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
				Netgear_log_file "cal-ntgr-access_control.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALEnableAcessControlProfile
#
#  Description    : This function is an API used to enable access control profile on global
#         
#  Usage          : CALEnableAcessControlProfile <switch_name> <profilename>
# 
#*******************************************************************************
proc CALEnableAcessControlProfile { switch_name profilename} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _switch_enable_AccessProfileName $switch_name $profilename
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
				Netgear_log_file "cal-ntgr-access_control.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALDisableAcessControl
#
#  Description    : This function is an API used to disable access control on global
#         
#  Usage          : CALDisableAcessControl <switch_name> 
# 
#*******************************************************************************
proc CALDisableAcessControl { switch_name } {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _switch_disable_AccessControl $switch_name 
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
				Netgear_log_file "cal-ntgr-access_control.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALAddAcessControlRule
#
#  Description    : This function is an API used to disable access control on global
#         
#  Usage          : CALAddAcessControlRule <switch_name> <profile_name> <rule>
# 
#*******************************************************************************
proc CALAddAcessControlRule { switch_name profile_name rule } {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _switch_add_AccessControlrule $switch_name  $profile_name $rule
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
				Netgear_log_file "cal-ntgr-access_control.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALCreateAcessControlRule
#
#  Description    : This function is an API used to create access control rule
#         
#  Usage          : CALCreateAcessControlRule <switch_name> <action> <sourceip> <mask> <service> <priority>
# 
#*******************************************************************************
proc CALCreateAcessControlRule  {switch_name action sourceip mask service priority} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _switch_create_AccessControlrule $action  $sourceip $mask $service $priority
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
				Netgear_log_file "cal-ntgr-access_control.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALIFAcessControlRuleExisting
#
#  Description    : This function is an API used to create access control rule
#         
#  Usage          : CALIFAcessControlRuleExisting <switch_name> <profile_name> <action> <sourceip> <mask> <service> <priority>
# 
#*******************************************************************************
proc CALIFAcessControlRuleExisting {switch_name profile_name action sourceip mask service priority} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _switch_AccessProfileSpecifiedRule_IfExisting $switch_name $profile_name $action  $sourceip $mask $service $priority
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
				Netgear_log_file "cal-ntgr-access_control.tcl" "Switch not defined"
			}
		} 
}