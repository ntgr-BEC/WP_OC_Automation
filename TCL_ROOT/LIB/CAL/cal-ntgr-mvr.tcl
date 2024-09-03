#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-mvr.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for MVR configuration.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        11/22/2013      jim xie        Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALEnableMVR
#
#  Description    : This function is an API used to enable MVR on global
#         
#  Usage          : CALEnableMVR <switch_name>
# 
#*******************************************************************************
proc CALEnableMVR { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMVREnable $switch_name
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
				Netgear_log_file "cal-ntgr-mvr.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALDisableMVR
#
#  Description    : This function is an API used to disable MVR on global
#         
#  Usage          : CALDisableMVR <switch_name>
# 
#*******************************************************************************
proc CALDisableMVR { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrMVRDisable $switch_name
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMVRSetMVRMode
#
#  Description    : This function is an API used to set MVR mode on global
#         
#  Usage          : CALMVRSetMVRMode <switch_name> <mvr_mode>
# 
#*******************************************************************************
proc CALMVRSetMVRMode { switch_name mvr_mode} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrMVRSetMVRMode $switch_name $mvr_mode
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMVRSetMVRMulticastVlan
#
#  Description    : This function is an API used to set MVR multicast vlan on global
#         
#  Usage          : CALMVRSetMVRMulticastVlan <switch_name> <vlan_id>
# 
#*******************************************************************************
proc CALMVRSetMVRMulticastVlan { switch_name vlan_id} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrMVRSetMVRMulticastVlan $switch_name $vlan_id
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMVRSetMVRResponseTime
#
#  Description    : This function is an API used to set MVR global query response time on global
#         
#  Usage          : CALMVRSetMVRResponseTime <switch_name> <res_time>
# 
#*******************************************************************************
proc CALMVRSetMVRResponseTime { switch_name res_time} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrMVRSetMVRResponseTime $switch_name $res_time
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMVRAddMVRGroup
#
#  Description    : This function is an API used to add MVR group on global
#         
#  Usage          : CALMVRAddMVRGroup <switch_name> <grp_ip> <grp_count>
# 
#*******************************************************************************
proc CALMVRAddMVRGroup { switch_name grp_ip grp_count} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrMVRAddMVRGroup $switch_name $grp_ip $grp_count
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMVRDeleteMVRGroup
#
#  Description    : This function is an API used to delete MVR group on global
#         
#  Usage          : CALMVRDeleteMVRGroup <switch_name> <grp_ip> <grp_count>
# 
#*******************************************************************************
proc CALMVRDeleteMVRGroup { switch_name grp_ip grp_count} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrMVRDeleteMVRGroup $switch_name $grp_ip $grp_count
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMVRCheckMVRGroupConfigure
#
#  Description    : This function is an API used to check MVR group configure on global
#         
#  Usage          : CALMVRCheckMVRGroupConfigure <switch_name> <grp_ip> <grp_status> <grp_members>
# 
#*******************************************************************************
proc CALMVRCheckMVRGroupConfigure { switch_name grp_ip grp_status grp_members} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  return [_ntgrMVRCheckMVRGroupConfigure $switch_name $grp_ip $grp_status $grp_members]
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMVRSetInterface
#
#  Description    : This function is an API used to set MVR interface configure on specified switch
#         
#  Usage          : CALMVRSetInterface <switch_name> <port> <mvr_type> <immediate_type>
# 
#*******************************************************************************
proc CALMVRSetInterface { switch_name port mvr_type immediate_type} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrMVRSetInterface $switch_name $port $mvr_type $immediate_type
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMVRResetInterface
#
#  Description    : This function is an API used to reset MVR interface configure on specified switch
#         
#  Usage          : CALMVRResetInterface <switch_name> <port>
# 
#*******************************************************************************
proc CALMVRResetInterface { switch_name port} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrMVRResetInterface $switch_name $port
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMVRAddMVRMembers
#
#  Description    : This function is called to add port to MVR group on specified switch
#         
#  Usage          : CALMVRAddMVRMembers <switch_name> <portlist> <vlan_id> <grp_ip>
# 
#*******************************************************************************
proc CALMVRAddMVRMembers { switch_name portlist vlan_id grp_ip} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  _ntgrMVRAddMVRMembers $switch_name $portlist $vlan_id $grp_ip
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMVRGetMVRTraffic
#
#  Description    : This function is called to check the traffic of MVR on specified switch
#         
#  Usage          : CALMVRGetMVRTraffic <switch_name> <expect_statistics>
# 
#*******************************************************************************
proc CALMVRGetMVRTraffic { switch_name expect_statistics} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  return [ _ntgrMVRGetMVRTraffic $switch_name $expect_statistics ]
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMVRGetMVRGlobalConfigure
#
#  Description    : This function is called to get the global configure of MVR on specified switch
#         
#  Usage          : CALMVRGetMVRGlobalConfigure <switch_name> <expect_text>
# 
#*******************************************************************************
proc CALMVRGetMVRGlobalConfigure { switch_name expect_text} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  return [ _ntgrMVRGetMVRGlobalConfigure $switch_name $expect_text ]
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMVRCheckMVRInterfaceConfigure
#
#  Description    : This function is called to check the interface of MVR on specified switch
#         
#  Usage          : CALMVRCheckMVRInterfaceConfigure <switch_name> <port> <type> <status> <leave_type>
# 
#*******************************************************************************
proc CALMVRCheckMVRInterfaceConfigure { switch_name port type status leave_type} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				  return [ _ntgrMVRCheckMVRInterfaceConfigure $switch_name $port $type $status $leave_type ]
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
				Netgear_log_file "cal-ntgr-mvrl.tcl" "Switch not defined"
			}
		} 
}
