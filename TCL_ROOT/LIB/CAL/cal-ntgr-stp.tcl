#!/bin/sh
################################################################################   
#
#  File Name		: cal-ntgr-stp.tcl
#
#  Description       	:
#         This TCL contains APIs to configure STP. This is CLI 
#	    abstraction Layer for STP configuration
#
#  Revision History 	: 
#  Revision   Date         Programmer        Description
#  ----------------------------------------------------------------------------------
#    1.1     27-June-06  Selva Kumar   1. Two new procedures CALMstpAddInstance and 
#                                         CALMstpAddVlan added to support MSTP.
#                                      2. In the procedure CALStpConfigCost mstpid argument 
#                                         is added, to achieve backward compatiblity, this 
#                                         argument is made as optional. In the same procedure 
#                                         validation added to ensure portlist and costlist 
#                                         are one to one.
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALStpEnableSwitch
#
#  Description    : This function Enable STP on a switch
#         
#  Usage          : CALStpEnableSwitch (switch) 
# 
#*******************************************************************************
proc CALStpEnableSwitch { switch } {

#now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch ]

	switch $switch_vendor {
		netgear 	{
			_ntgrStpEnableSwitch $switch
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
			Netgear_log_file "cal-ntgr-stp.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALStpEnableSwitchwithID
#
#  Description    : This function Enable STP on a switch
#         
#  Usage          : CALStpEnableSwitchwithID (switch id) 
# 
#*******************************************************************************
proc CALStpEnableSwitchwithID { switch id} {

#now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch ]

	switch $switch_vendor {
		netgear 	{
			_ntgrStpEnableSwitchwithID $switch $id
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
			Netgear_log_file "cal-ntgr-stp.tcl" "Switch not defined"
		}
	} 
}
#*******************************************************************************
#
#  Function Name	: CALStpDisableSwitch
#
#  Description    : This function Disable STP on a switch
#         
#  Usage          : CALStpDisableSwitch (switch) 
# 
#*******************************************************************************
proc CALStpDisableSwitch { switch } {

#now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch ]

	switch $switch_vendor {
		netgear 	{
			_ntgrStpDisableSwitch $switch
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
			Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	} 


}
#*******************************************************************************
#
#  Function Name	: CALStpEnablePort
#
#  Description    : This function Enable STP on a port
#         
#  Usage          : CALStpEnablePort (switch port_list ) 
# 
#*******************************************************************************
proc CALStpEnablePort { switch port_list } {

#now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch ]

	switch $switch_vendor {
		netgear 	{
			_ntgrStpEnablePort $switch $port_list
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
			Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	} 


}

#*******************************************************************************
#
#  Function Name	: CALStpEDisablePort
#
#  Description    : This function Disable STP on a port
#         
#  Usage          : CALStpDisablePort (switch port_list ) 
# 
#*******************************************************************************
proc CALStpDisablePort { switch port_list } {

	#now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch ]

	switch $switch_vendor {
		netgear 	{
			_ntgrStpDisablePort $switch $port_list
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
			Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	} 


}

#*******************************************************************************
#
#  Function Name	: CALStpConfigBridgePriority
#
#  Description    : This function set bridge priority of a swithc
#         
#  Usage          : CALStpConfigBridgePriority (switch priority { mstp_id 0}   ) 
#			NOTE: mstp_id is optional and if unspecified assumes zero.
# 
#*******************************************************************************
proc CALStpConfigBridgePriority { switch priority { mstp_id 0}  } {
    
#now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch ]

	switch $switch_vendor {
		netgear 	{
	    		_ntgrStpConfigBridgePriority $switch $priority $mstp_id
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
			Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	} 


}

#*******************************************************************************
#  Function Name	: CALStpConfigCost
#  
#  Description    : This function set bridge ports' cost
#         
#  Usage          : CALStpConfigCost <switch_name> <portlist> <costlist> [ mstpid ]
#                   If it is used for STP, mstpid can be ignored and by default it takes mstpid as 0.
#                   If it is used for MSTP, mstpid should be MSTP instance to be configured.  
#*******************************************************************************
proc CALStpConfigCost { switch_name portlist costlist {mstpid 0 } } {
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]
    	if { [llength $portlist ] != [llength $costlist ] } { 
		Netgear_log_file "CALStpConfigCost" "Portlist and Costlist are not one to one in the CFG file"
    	}
    	switch $switch_vendor {
		netgear 	{
	    		_ntgrStpConfigCost $switch_name $portlist $costlist $mstpid
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
	    		Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
    	} 
}

#*******************************************************************************
#  Function Name	: CALStpConfigMaxAge
#
#  Description    : This function set bridge's max age parameter.
#         
#  Usage          : CALStpConfigMaxAge <switch_name> <max_age>
# 
#*******************************************************************************
proc CALStpConfigMaxAge { switch_name max_age } {
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]

	switch $switch_vendor {
		netgear 	{
			_ntgrStpConfigMaxAge $switch_name $max_age
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
			Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALStpConfigForwardTime
#
#  Description    : This function set bridge's forward delay parameter.
#         
#  Usage          : CALStpConfigForwardTime <switch_name> <forward_time>
# 
#*******************************************************************************
proc CALStpConfigForwardTime { switch_name forward_time } {
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]

	switch $switch_vendor {
		netgear 	{
			_ntgrStpConfigForwardTime $switch_name $forward_time
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
			Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALStpConfigHelloTime
#
#  Description    : This function set bridge's hello time parameter.
#         
#  Usage          : CALStpConfigHelloTime <switch_name> <hello_time>
# 
#*******************************************************************************
proc CALStpConfigHelloTime { switch_name hello_time } {
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]

	switch $switch_vendor {
		netgear 	{
			_ntgrStpConfigHelloTime $switch_name $hello_time
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
			Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	} 
}

#*******************************************************************************
#  Function Name	: CALMstpAddInstance
#
#  Description    : This function adds multiple spanning tree instance to the switch
#         
#  Usage          : CALMstpAddInstance <switch_name> <mstidlist> 
#
# 
#*******************************************************************************
proc CALMstpAddInstance { switch_name mstidlist } {
    global NTGR_LOG_ERROR
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]
    
    switch $switch_vendor {
	netgear 	{
	    _ntgrMstpAddInstance $switch_name $mstidlist
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
	    NtgrDumpLog  $NTGR_LOG_ERROR  "CALMstpAddInstance" "Switch $switch_vendor not defined"
	}
    } 
}

#*******************************************************************************
#  Function Name	: CALMstpAddVlan
#
#  Description    : This function adds an association between multiple spanning
#                   tree instance and a VLAN 
#         
#  Usage          : CALMstpAddVlan  <switch_name> <mstidlist> <vlanidlist> 
#                   mstidlist  - list of MSTP instances 
#                   vlanidlist - VLAN's to be associated with MSTP instances.
# 
#*******************************************************************************
proc CALMstpAddVlan { switch_name mstidlist  vlanidlist } {
	global NTGR_LOG_ERROR

	if { [llength $mstidlist ] != [llength $vlanidlist ] } { 
		NtgrDumpLog  $NTGR_LOG_ERROR  "CALMstpAddVlan" "mstpidlist and vlaidlist should be one to one in the CFG file"
	  # write a exit function to stop log and then exit
	}

    	set switch_vendor [ _get_switch_vendor_name  $switch_name ]
    
    	switch $switch_vendor {
		netgear 	{
	    		_ntgrMstpAddVlan $switch_name $mstidlist $vlanidlist
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
	    		NtgrDumpLog  $NTGR_LOG_ERROR  "CALMstpAddVlan" "Switch $switch_vendor not defined"
	    	}
    	} 
}

#*******************************************************************************
#  Function Name	: CALStpEnablePortEdgeport
#
#  Description    : This function is used to enable port edgeport.
#         
#  Usage          : CALStpEnablePortEdgeport  <switch_name> <port_list> 
#                   port_list  - list of port
# 
#*******************************************************************************
proc CALStpEnablePortEdgeport { switch_name port_list } {

    set switch_vendor [ _get_switch_vendor_name  $switch_name ]
    
    switch $switch_vendor {
		    netgear {
	          _ntgrStpEnablePortEdgeport $switch_name $port_list
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
		    default	{
	    	    NtgrDumpLog  $NTGR_LOG_ERROR  "CALMstpAddVlan" "Switch $switch_vendor not defined"
	    	}
    }
}
#*******************************************************************************
#  Function Name	: CALStpDisablePortEdgeport
#
#  Description    : This function is used to unset port edgeport.
#         
#  Usage          : CALStpDisablePortEdgeport  <switch_name> <port_list> 
#                   port_list  - list of port
# 
#*******************************************************************************
proc CALStpDisablePortEdgeport { switch_name port_list } {

    set switch_vendor [ _get_switch_vendor_name  $switch_name ]
    
    switch $switch_vendor {
		    netgear {
	          _ntgrStpDisablePortEdgeport $switch_name $port_list
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
		    default	{
	    	    NtgrDumpLog  $NTGR_LOG_ERROR  "CALMstpAddVlan" "Switch $switch_vendor not defined"
	    	}
    }
}

#*******************************************************************************
#
#  Function Name	: CALGetMstpPortStpState
#
#  Description    : This function is used to get mstp port stp state.
#         
#  Usage          : CALGetMstpPortStpState <switch_name> <port> <mstp_id>
#			NOTE: mstp_id is optional and if unspecified assumes zero.
# 
#*******************************************************************************
proc CALGetMstpPortStpState { switch port {mstp_id 0} } {
    
  #now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch ]

	switch $switch_vendor {
		netgear {
	      _ntgrGetMstpPortStpState $switch $port $mstp_id
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
		default	{
		    Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	}

}

#*****************************************************************************************************************************
#
#  Function Name	:  CALSetPVSTVlanConfigure
#
#  Description      :  This function is called setting PVST vlan configuration
#         
#  Usage            :  CALSetPVSTVlanConfigure <switch_name> <vlan_id> <root> <priority> <forward-time> <hello-time> <max-age>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc CALSetPVSTVlanConfigure { switch_name vlan_id root priority forward_time hello_time max_age } {
    
  #now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]

	switch $switch_vendor {
		netgear {
	      _ntgrSetPVSTVlanConfigure $switch_name $vlan_id $root $priority $forward_time $hello_time $max_age
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
		default	{
		    Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	}

}

#*******************************************************************************************************************************
#
#  Function Name  : CALCheckPVSTVlanStatus
#
#  Description    : This function is used to check pvst vlan status
#
#  Usage          : CALCheckPVSTVlanStatus <switch_name> <vlan_id>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc CALCheckPVSTVlanStatus { switch_name vlan_id  } {
    
  #now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]

	switch $switch_vendor {
		netgear {
	      _ntgrCheckPVSTVlanStatus $switch_name $vlan_id
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
		default	{
		    Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	}

}

#*****************************************************************************************************************************
#
#  Function Name	:  CALSetPVSTInterfaceConfigure
#
#  Description      :  This function is called setting PVST Interface configuration
#         
#  Usage            :  CALSetPVSTInterfaceConfigure <switch_name> <vlan_id> <port> <priority> <cost>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc CALSetPVSTInterfaceConfigure { switch_name vlan_id port priority cost} {
    
    #now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]

	switch $switch_vendor {
		netgear {
	      _ntgrSetPVSTInterfaceConfigure $switch_name $vlan_id $port $priority $cost
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
		default	{
		    Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	}

}

#*****************************************************************************************************************************
#
#  Function Name	:  CALDeleteAllPVSTVlan
#
#  Description      :  This function is called delete All PVST vlan configuration
#         
#  Usage            :  CALDeleteAllPVSTVlan <switch_name> <max_vlanID>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc CALDeleteAllPVSTVlan { switch_name max_vlanID} {
    
    #now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]

	switch $switch_vendor {
		netgear {
	      _ntgrDeleteAllPVSTVlan $switch_name $max_vlanID
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
		default	{
		    Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	}
}

#*****************************************************************************************************************************
#
#  Function Name	:  CALAddPVSTVlan
#
#  Description      :  This function is called add PVST vlan
#         
#  Usage            :  CALAddPVSTVlan <switch_name> <vlanID>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc CALAddPVSTVlan { switch_name vlanID} {
    
    #now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]

	switch $switch_vendor {
		netgear {
	      _ntgrAddPVSTVlan $switch_name $vlanID
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
		default	{
		    Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	}
}

#*****************************************************************************************************************************
#
#  Function Name	:  CALEnableSTPFastBackboneMode
#
#  Description      :  This function is enable fast Backbone 
#
#  Usage            :  CALEnableSTPFastBackboneMode <switch_name>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc CALEnableSTPFastBackboneMode { switch_name} {
    
    #now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]

	switch $switch_vendor {
		netgear {
	      _ntgrEnableSTPFastBackboneMode $switch_name
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
		default	{
		    Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	}
}

#*****************************************************************************************************************************
#
#  Function Name	:  CALDisableSTPFastBackboneMode
#
#  Description      :  This function is disable fast Backbone 
#
#  Usage            :  CALDisableSTPFastBackboneMode <switch_name>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc CALDisableSTPFastBackboneMode { switch_name} {
    
    #now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]

	switch $switch_vendor {
		netgear {
	      _ntgrDisableSTPFastBackboneMode $switch_name
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
		default	{
		    Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	}
}

#*****************************************************************************************************************************
#
#  Function Name	:  CALEnableSTPFastUplinkMode
#
#  Description      :  This function is enable fast Uplink 
#
#  Usage            :  CALEnableSTPFastUplinkMode <switch_name>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc CALEnableSTPFastUplinkMode { switch_name} {
    
    #now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]

	switch $switch_vendor {
		netgear {
	      _ntgrEnableSTPFastUplinkMode $switch_name
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
		default	{
		    Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	}
}

#*****************************************************************************************************************************
#
#  Function Name	:  CALDisableSTPFastUplinkMode
#
#  Description      :  This function is disable fast Uplink 
#
#  Usage            :  CALDisableSTPFastUplinkMode <switch_name>
#
#  Author           :  jim.xie
#
#*******************************************************************************************************************************
proc CALDisableSTPFastUplinkMode { switch_name} {
    
    #now get the switch type from the topology list
	set switch_vendor [ _get_switch_vendor_name  $switch_name ]

	switch $switch_vendor {
		netgear {
	      _ntgrDisableSTPFastUplinkMode $switch_name
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
		default	{
		    Netgear_log_file "cal-ntgr-stp.tcl" "Switch $switch_vendor not defined"
		}
	}
}

