#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-mlag.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for MLAG configuration.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        3/1/13      Jason Li        Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALMLAGVPCModeEnable
#
#  Description    : This function is an API used to enable mlag vpc mode on global
#         
#  Usage          : CALMLAGVPCModeEnable <switch_name> 
# 
#*******************************************************************************
proc CALMLAGVPCModeEnable { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGVPCModeEnable $switch_name 
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGVPCModeDisable
#
#  Description    : This function is an API used to disable mlag vpc mode on global
#         
#  Usage          : CALMLAGVPCModeDisable <switch_name> 
# 
#*******************************************************************************
proc CALMLAGVPCModeDisable { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGVPCModeDisable $switch_name 
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGSetKeepalivePriority
#
#  Description    : This function is an API used to set mlag keepalive priority on global
#         
#  Usage          : CALMLAGSetKeepalivePriority <switch_name> <priority>
# 
#*******************************************************************************
proc CALMLAGSetKeepalivePriority { switch_name priority} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGSetKeepalivePriority $switch_name $priority
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGReSetKeepalivePriority
#
#  Description    : This function is an API used to reset mlag keepalive priority on global
#         
#  Usage          : CALMLAGReSetKeepalivePriority <switch_name> 
# 
#*******************************************************************************
proc CALMLAGReSetKeepalivePriority { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGReSetKeepalivePriority $switch_name 
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGSetKeepaliveTimeout
#
#  Description    : This function is an API used to set mlag keepalive timeout on global
#         
#  Usage          : CALMLAGSetKeepaliveTimeout <switch_name> <timeout>
# 
#*******************************************************************************
proc CALMLAGSetKeepaliveTimeout { switch_name timeout} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGSetKeepaliveTimeout $switch_name $timeout
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGReSetKeepaliveTimeout
#
#  Description    : This function is an API used to reset mlag keepalive timeout on global
#         
#  Usage          : CALMLAGReSetKeepaliveTimeout <switch_name> 
# 
#*******************************************************************************
proc CALMLAGReSetKeepaliveTimeout { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGReSetKeepaliveTimeout $switch_name
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGKeepaliveModeEnable
#
#  Description    : This function is an API used to enable mlag keepalive mode on global
#         
#  Usage          : CALMLAGVPCModeEnable <switch_name> 
# 
#*******************************************************************************
proc CALMLAGKeepaliveModeEnable { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGKeepaliveModeEnable $switch_name 
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGKeepaliveModeDisable
#
#  Description    : This function is an API used to disable mlag keepalive mode on global
#         
#  Usage          : CALMLAGKeepaliveModeDisable <switch_name> 
# 
#*******************************************************************************
proc CALMLAGKeepaliveModeDisable { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGKeepaliveModeDisable $switch_name 
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGSetPeerLinkChannel
#
#  Description    : This function is an API used to set peer link channel on global
#         
#  Usage          : CALMLAGSetPeerLinkChannel <switch_name> 
# 
#*******************************************************************************
proc CALMLAGSetPeerLinkChannel { switch_name channel} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGSetPeerLinkChannel $switch_name $channel
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGReSetPeerLinkChannel
#
#  Description    : This function is an API used to reset peer link channel on global
#         
#  Usage          : CALMLAGReSetPeerLinkChannel <switch_name> <channel>
# 
#*******************************************************************************
proc CALMLAGReSetPeerLinkChannel { switch_name channel} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGReSetPeerLinkChannel $switch_name $channel 
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGPeerDetectionEnable
#
#  Description    : This function is an API used to enable peer detection on global
#         
#  Usage          : CALMLAGPeerDetectionEnable <switch_name> 
# 
#*******************************************************************************
proc CALMLAGPeerDetectionEnable { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGPeerDetectionEnable $switch_name 
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGPeerDetectionDisable
#
#  Description    : This function is an API used to disable peer detection on global
#         
#  Usage          : CALMLAGPeerDetectionDisable <switch_name> 
# 
#*******************************************************************************
proc CALMLAGPeerDetectionDisable { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGPeerDetectionDisable $switch_name 
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGSetPeerDetectioninfo
#
#  Description    : This function is an API used to set mlag peer detection info on global
#         
#  Usage          : CALMLAGSetPeerDetectioninfo <switch_name> <srcip> <dstip> <udpport>
# 
#*******************************************************************************
proc CALMLAGSetPeerDetectioninfo { switch_name dstip srcip {udpport 50000}} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGSetPeerDetectioninfo $switch_name $dstip $srcip $udpport
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGReSetPeerDetectioninfo
#
#  Description    : This function is an API used to reset mlag peer detection info on global
#         
#  Usage          : CALMLAGReSetPeerDetectioninfo <switch_name> 
# 
#*******************************************************************************
proc CALMLAGReSetPeerDetectioninfo { switch_name dstip srcip} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGReSetPeerDetectioninfo $switch_name $dstip $srcip
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGAddVPCInterfaceConfig
#
#  Description    : This function is an API used to add vpc interface config on global
#         
#  Usage          : CALMLAGAddVPCInterfaceConfig <switch_name> <channel> <vpcid>
# 
#*******************************************************************************
proc CALMLAGAddVPCInterfaceConfig { switch_name channel vpcid} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGAddVPCInterfaceConfig $switch_name $channel $vpcid
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGDeleteVPCInterfaceConfig
#
#  Description    : This function is an API used to delete vpc interface config
#         
#  Usage          : CALMLAGDeleteVPCInterfaceConfig <switch_name> <channel> <vpcid>
# 
#*******************************************************************************
proc CALMLAGDeleteVPCInterfaceConfig { switch_name channel vpcid} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGDeleteVPCInterfaceConfig $switch_name $channel $vpcid
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}



#*******************************************************************************
#
#  Function Name	: CALMLAGGetVPCInterfaceStatusbyId
#
#  Description    : This function is an API used to get vpc interface details
#         
#  Usage          : CALMLAGGetVPCInterfaceStatusbyId <switch_name> <vpcid>
#  
#*******************************************************************************
proc CALMLAGGetVPCInterfaceStatusbyId { switch_name vpcid} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGGetVPCInterfaceStatusbyId $switch_name $vpcid
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGGetVPCSelfRole
#
#  Description    : This function is an API used to get vpc self role
#         
#  Usage          : CALMLAGGetVPCSelfRole <switch_name> 
#  
#*******************************************************************************
proc CALMLAGGetVPCSelfRole { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGGetVPCSelfRole $switch_name
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}


#*******************************************************************************
#
#  Function Name	: CALMLAGGetVPCPeerLinkStatus
#
#  Description    : This function is an API used to get vpc peer link admin status
#         
#  Usage          : CALMLAGGetVPCPeerLinkStatus <switch_name> 
#  
#*******************************************************************************
proc CALMLAGGetVPCPeerLinkStatus { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGGetVPCPeerLinkStatus $switch_name
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}  

#*******************************************************************************
#
#  Function Name	: CALMLAGGetVPCPeerDetectionStatus
#
#  Description    : This function is an API used to get Peer detection admin status
#         
#  Usage          : CALMLAGGetVPCPeerDetectionStatus <switch_name> 
#  
#*******************************************************************************
proc CALMLAGGetVPCPeerDetectionStatus { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGGetVPCPeerDetectionStatus $switch_name
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALMLAGCheckKeepalivePriorityRange
#
#  Description    : This function is an API used to Check  keepalive priority Range
#         
#  Usage          : CALMLAGCheckKeepalivePriority <switch_name> <priority>
# 
#*******************************************************************************
proc CALMLAGCheckKeepalivePriorityRange { switch_name priority} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGCheckKeepalivePriorityRange $switch_name $priority
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALMLAGGetKeepalivePriority
#
#  Description    : This function is an API used to get keep alive priority
#         
#  Usage          : CALMLAGGetKeepalivePriority <switch_name> 
#  
#*******************************************************************************
proc CALMLAGGetKeepalivePriority { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGGetKeepalivePriority $switch_name
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALMLAGCheckKeepaliveTimeoutRange
#
#  Description    : This function is an API used to Check  keepalive timeout Range
#         
#  Usage          : CALMLAGCheckKeepalivePriority <switch_name> <timeout>
# 
#*******************************************************************************
proc CALMLAGCheckKeepaliveTimeoutRange { switch_name timeout} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGCheckKeepaliveTimeoutRange $switch_name $timeout
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}  

#*******************************************************************************
#
#  Function Name	: CALMLAGCheckKeepaliveTimeoutRange
#
#  Description    : This function is an API used to Check  keepalive timeout Range
#         
#  Usage          : CALMLAGCheckKeepalivePriority <switch_name> <portlist> <timeout>
# 
#*******************************************************************************
proc CALMLAGCheckVPCIDRange { switch_name portlist vpcid} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGCheckVPCIDRange $switch_name $portlist $vpcid
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}  

#*******************************************************************************
#
#  Function Name	: CALMLAGGetKeepaliveStatistics
#
#  Description    : This function is an API used to Get Keep alive Statistics
#         
#  Usage          : CALMLAGGetKeepaliveStatistics <switch_name> <strtext>
# 
#*******************************************************************************
proc CALMLAGGetKeepaliveStatistics { switch_name strtext} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGGetKeepaliveStatistics $switch_name $strtext
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALMLAGGetPeerlinkStatistics
#
#  Description    : This function is an API used to Get peer link Statistics
#         
#  Usage          : CALMLAGGetPeerlinkStatistics <switch_name> <strtext>
# 
#*******************************************************************************
proc CALMLAGGetPeerlinkStatistics { switch_name strtext} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGGetPeerlinkStatistics $switch_name $strtext
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
}  

#*******************************************************************************
#
#  Function Name	: CALMLAGClearPeerlinkStatistics
#
#  Description    : This function is an API used to Get peer link Statistics
#         
#  Usage          : CALMLAGClearPeerlinkStatistics <switch_name> 
# 
#*******************************************************************************
proc CALMLAGClearPeerlinkStatistics { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGClearPeerlinkStatistics $switch_name
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALMLAGClearKeepaliveStatistics
#
#  Description    : This function is an API used to clear keep alive Statistics
#         
#  Usage          : CALMLAGClearKeepaliveStatistics <switch_name> 
# 
#*******************************************************************************
proc CALMLAGClearKeepaliveStatistics { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGClearKeepaliveStatistics $switch_name
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALMLAGCheckKeepaliveTimeout
#
#  Description    : This function is an API used to check keep alive timeout
#         
#  Usage          : CALMLAGCheckKeepaliveTimeout <switch_name> <timeout>
# 
#*******************************************************************************
proc CALMLAGCheckKeepaliveTimeout { switch_name timeout} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGCheckKeepaliveTimeout $switch_name $timeout
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALMLAGCheckPeerDetectionPortRange
#
#  Description    : This function is an API used to Check  keepalive priority Range
#         
#  Usage          : CALMLAGCheckPeerDetectionPortRange <switch_name> <dstip> <srcip> <udport>
# 
#*******************************************************************************
proc CALMLAGCheckPeerDetectionPortRange { switch_name dstip srcip udpport} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGCheckPeerDetectionPortRange $switch_name $dstip $srcip $udpport
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
} 

#*******************************************************************************
#
#  Function Name	: CALMLAGDebugModeEnable
#
#  Description    : This function is an API used to enable mlag debug mode on global
#         
#  Usage          : CALMLAGDebugModeEnable <switch_name> 
# 
#*******************************************************************************
proc CALMLAGDebugModeEnable { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrMLAGDebugModeEnable $switch_name 
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
				Netgear_log_file "cal-ntgr-mlag.tcl" "Switch not defined"
			}
		} 
} 

