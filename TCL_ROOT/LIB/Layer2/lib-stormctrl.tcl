#!/bin/sh
################################################################################   
#
#  File Name		  : lib-stormctrl.tcl
#
#  Description      :
#         This TCL contains functions to configure Storm Control
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        15-Jun-06     Rajeswari        Created
#
#
#
#
################################################################################

#*******************************************************************************
#  Function Name	: _ntgrStormControlBroadcastEnable
#
#  Description    : This function is called to enable Broadcast storm control on 
#                   the Switch
#         
#  Usage          : _ntgrStormControlBroadcastEnable <switch_name> <port> <level> <leveltype> <action>
#
#
#*******************************************************************************
proc _ntgrStormControlBroadcastEnable {switch_name port level leveltype action} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	if {$port == "all"} {
		Netgear_log_file "lib-stormctrl.tcl" "Enabling Broadcast Storm Control on L2 Switch $switch_name"
#		Netgear_log_file "lib-stormctrl.tcl" "Broadcast Storm Control Level on L2 Switch $switch_name = $level"
		if {$level == "default"} {
			exp_send -i $connection_id "storm-control broadcast\r"
        	expect -i $connection_id "storm-control broadcast"
        	while {1} {
        		expect -i $connection_id "#" break
        	}
		} else {
		 	#exp_send -i $connection_id "storm-control broadcast level $level\r"
		 	#sleep 1
		}
	} else {
		Netgear_log_file "lib-stormctrl.tcl" "Enabling Broadcast Storm Control on L3 Switch $switch_name on port $port"
		Netgear_log_file "lib-stormctrl.tcl" "Broadcast Storm Control Level on L3 Switch $switch_name on port $port = $level"
		exp_send -i $connection_id "interface $port\r"
	 	sleep 1
		if {$level == "default"} {
			exp_send -i $connection_id "storm-control broadcast\r"
		 	sleep 1
		} else {
			if {$action == "shutdown"} {
    		exp_send -i $connection_id "storm-control broadcast action $action\r"
    	 	sleep 1
			} elseif {$action == "trap"} {
			exp_send -i $connection_id "storm-control broadcast action $action\r"
    	 	sleep 1
			}
			if {$leveltype == "pps"} {
			exp_send -i $connection_id "storm-control broadcast rate $level\r"
			} else {
    	 	exp_send -i $connection_id "storm-control broadcast level $level\r"
			}
			exp_send -i $connection_id "storm-control broadcast\r"
		 	sleep 1
		}
		exp_send -i $connection_id "exit\r"
	 	sleep 1
	}
 	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrStormControlBroadcastDisable
#
#  Description    : This function is called to disable broadcast storm control
#                   on the switch
#         
#  Usage          : _ntgrStormControlBroadcastDisable <switch_name> <port> <level> <leveltype> <action>
#
#
#*******************************************************************************
proc _ntgrStormControlBroadcastDisable {switch_name port level leveltype action} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	if {$port == "all"} {
		Netgear_log_file "lib-stormctrl.tcl" "Disabling Broadcast Storm Control on L2 Switch $switch_name"
#		Netgear_log_file "lib-stormctrl.tcl" "Broadcast Storm Control Level on L2 Switch $switch_name = $level"
		if {$level == "default"} {
			# exp_send -i $connection_id "no storm-control broadcast level\r"
			exp_send -i $connection_id "no storm-control broadcast\r"
        	expect -i $connection_id "storm-control broadcast"
        	while {1} {
        		expect -i $connection_id "#" break
        	}
		} else {
		 	#exp_send -i $connection_id "no storm-control broadcast\r"
		 	#sleep 1
		}
	} else {
		Netgear_log_file "lib-stormctrl.tcl" "Disabling Broadcast Storm Control on L3 Switch $switch_name on port $port"
		Netgear_log_file "lib-stormctrl.tcl" "Broadcast Storm Control Level on L3 Switch $switch_name on port $port = $level"
		exp_send -i $connection_id "interface $port\r"
	 	sleep 1
		if {$level == "default"} {
			exp_send -i $connection_id "no storm-control broadcast level\r"
		 	sleep 1
			exp_send -i $connection_id "no storm-control broadcast\r"
		 	sleep 1
		} else {
			if {$action == "shutdown"} {
    		exp_send -i $connection_id "no storm-control broadcast action\r"
    	 	sleep 1
			} elseif {$action == "trap"} {
			exp_send -i $connection_id "no storm-control broadcast action\r"
    	 	sleep 1
			}
			if {$leveltype == "pps"} {
			exp_send -i $connection_id "no storm-control broadcast rate \r"
			} else {
    	 	exp_send -i $connection_id "no storm-control broadcast level\r"
			}
		 	exp_send -i $connection_id "no storm-control broadcast\r"
		 	sleep 1
		}
		exp_send -i $connection_id "exit\r"
	 	sleep 1
	}
 	exp_send -i $connection_id "exit\r"
}


#*******************************************************************************
#  Function Name	: _ntgrStormControlMulticastEnable
#
#  Description    : This function is called to enable Multicast storm control on 
#                   the Switch
#         
#  Usage          : _ntgrStormControlMulticastEnable <switch_name> <port> <level> <leveltype> <action>
#
#
#*******************************************************************************
proc _ntgrStormControlMulticastEnable {switch_name port level leveltype action} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	if {$port == "all"} {
		Netgear_log_file "lib-stormctrl.tcl" "Enabling Multicast Storm Control on L2 Switch $switch_name"
		if {$level == "default"} {
			exp_send -i $connection_id "storm-control multicast\r"
        	expect -i $connection_id "storm-control multicast"
        	while {1} {
        		expect -i $connection_id "#" break
        	}
		} else {
		}
	} else {
    	Netgear_log_file "lib-stormctrl.tcl" "Enabling Multicast Storm Control on L3 Switch $switch_name on port $port"
    	Netgear_log_file "lib-stormctrl.tcl" "Multicast Storm Control Level on L3 Switch $switch_name on port $port = $level"
    	exp_send -i $connection_id "interface $port\r"
     	sleep 1
    	if {$level == "default"} {
    		exp_send -i $connection_id "storm-control multicast\r"
    	 	sleep 1
    	} else {
			if {$action == "shutdown"} {
    		exp_send -i $connection_id "storm-control multicast action $action\r"
    	 	sleep 1
			} elseif {$action == "trap"} {
			exp_send -i $connection_id "storm-control multicast action $action\r"
    	 	sleep 1
			}
			if {$leveltype == "pps"} {
			exp_send -i $connection_id "storm-control multicast rate $level\r"
			} else {
    	 	exp_send -i $connection_id "storm-control multicast level $level\r"
			}
			exp_send -i $connection_id "storm-control multicast\r"
    	 	sleep 1
    	}
    	exp_send -i $connection_id "exit\r"
     	sleep 1
   }
 	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrStormControlMulticastDisable
#
#  Description    : This function is called to disable Multicast storm control
#                   on the switch
#         
#  Usage          : _ntgrStormControlMulticastDisable <switch_name> <port> <level> <leveltype> <action>
#
#
#*******************************************************************************
proc _ntgrStormControlMulticastDisable {switch_name port level leveltype action} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	if {$port == "all"} {
		Netgear_log_file "lib-stormctrl.tcl" "Disabling Multicast Storm Control on L2 Switch $switch_name"
		if {$level == "default"} {
			exp_send -i $connection_id "no storm-control multicast\r"
        	expect -i $connection_id "storm-control multicast"
        	while {1} {
        		expect -i $connection_id "#" break
        	}
		} else {
		}
	} else {
    	Netgear_log_file "lib-stormctrl.tcl" "Disabling Multicast Storm Control on L3 Switch $switch_name on port $port"
    	Netgear_log_file "lib-stormctrl.tcl" "Multicast Storm Control Level on L3 Switch $switch_name on port $port = $level"
    	exp_send -i $connection_id "interface $port\r"
    	sleep 1
    	if {$level == "default"} {
    		exp_send -i $connection_id "no storm-control multicast level\r"
    	 	sleep 1
			exp_send -i $connection_id "no storm-control multicast\r"
    	 	sleep 1
    	} else {
			if {$action == "shutdown"} {
    		exp_send -i $connection_id "no storm-control multicast action\r"
    	 	sleep 1
			} elseif {$action == "trap"} {
			exp_send -i $connection_id "no storm-control multicast action\r"
    	 	sleep 1
			}
			if {$leveltype == "pps"} {
			exp_send -i $connection_id "no storm-control multicast rate \r"
			} else {
    	 	exp_send -i $connection_id "no storm-control multicast level\r"
			}
    	 	exp_send -i $connection_id "no storm-control multicast\r"
    	 	sleep 1
    	}
    	exp_send -i $connection_id "exit\r"
    	sleep 1
   }
 	exp_send -i $connection_id "exit\r"
}


#*******************************************************************************
#  Function Name	: _ntgrStormControlUnicastEnable
#
#  Description    : This function is called to enable Unicast storm control on 
#                   the Switch
#         
#  Usage          : _ntgrStormControlUnicastEnable <switch_name> <port> <level> <leveltype> <action>
#
#
#*******************************************************************************
proc _ntgrStormControlUnicastEnable {switch_name port level leveltype action} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	if {$port == "all"} {
		Netgear_log_file "lib-stormctrl.tcl" "Enabling Unicast Storm Control on L2 Switch $switch_name"
		if {$level == "default"} {
			exp_send -i $connection_id "storm-control unicast\r"
        	expect -i $connection_id "storm-control unicast"
        	while {1} {
        		expect -i $connection_id "#" break
        	}
		} else {
		}
	} else {
    	Netgear_log_file "lib-stormctrl.tcl" "Enabling Unicast Storm Control on L3 Switch $switch_name on port $port"
    	Netgear_log_file "lib-stormctrl.tcl" "Unicast Storm Control Level on L3 Switch $switch_name on port $port = $level"
    	exp_send -i $connection_id "interface $port\r"
    	sleep 1
    	if {$level == "default"} {
    		exp_send -i $connection_id "storm-control unicast\r"
    	 	sleep 1
    	} else {
			if {$action == "shutdown"} {
    		exp_send -i $connection_id "storm-control unicast action $action\r"
    	 	sleep 1
			} elseif {$action == "trap"} {
			exp_send -i $connection_id "storm-control unicast action $action\r"
    	 	sleep 1
			}
			if {$leveltype == "pps"} {
			exp_send -i $connection_id "storm-control unicast rate $level\r"
			} else {
    	 	exp_send -i $connection_id "storm-control unicast level $level\r"
			}
    	 	sleep 1
			exp_send -i $connection_id "storm-control unicast\r"
    	 	sleep 1
    	}
    	exp_send -i $connection_id "exit\r"
    	sleep 1
   }
 	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrStormControlUnicastDisable
#
#  Description    : This function is called to disable Unicast storm control
#                   on the switch
#         
#  Usage          : _ntgrStormControlUnicastDisable <switch_name> <port> <level> <leveltype> <action>
#
#
#*******************************************************************************
proc _ntgrStormControlUnicastDisable {switch_name port level leveltype action} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	if {$port == "all"} {
		Netgear_log_file "lib-stormctrl.tcl" "Disabling Unicast Storm Control on L2 Switch $switch_name"
		if {$level == "default"} {
			exp_send -i $connection_id "no storm-control unicast\r"
        	expect -i $connection_id "storm-control unicast"
        	while {1} {
        		expect -i $connection_id "#" break
        	}
		} else {
		}
	} else {
    	Netgear_log_file "lib-stormctrl.tcl" "Disabling Unicast Storm Control on L3 Switch $switch_name on port $port"
    	Netgear_log_file "lib-stormctrl.tcl" "Unicast Storm Control Level on L3 Switch $switch_name on port $port = $level"
    	exp_send -i $connection_id "interface $port\r"
    	sleep 1
    	if {$level == "default"} {
    		exp_send -i $connection_id "no storm-control unicast level\r"
    	 	sleep 1
			exp_send -i $connection_id "no storm-control unicast\r"
    	 	sleep 1
    	} else {
			if {$action == "shutdown"} {
    		exp_send -i $connection_id "no storm-control unicast action\r"
    	 	sleep 1
			} elseif {$action == "trap"} {
			exp_send -i $connection_id "no storm-control unicast action\r"
    	 	sleep 1
			}
			if {$leveltype == "pps"} {
			exp_send -i $connection_id "no storm-control unicast rate \r"
			} else {
    	 	exp_send -i $connection_id "no storm-control unicast level\r"
			}
    	 	exp_send -i $connection_id "no storm-control unicast\r"
    	 	sleep 1
    	}
    	exp_send -i $connection_id "exit\r"
    	sleep 1
   }
	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrStormControlFlowCtrlEnable
#
#  Description    : This function is called to Enable flow control
#                   on the switch
#         
#  Usage          : _ntgrStormControlFlowCtrlEnable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrStormControlFlowCtrlEnable {switch_name} {
	set connection_id [_get_switch_handle $switch_name]
	set ver [_ntgrGetVersionReleasebyConnectionID $connection_id]
  exp_send -i $connection_id "configure\r"
  sleep 1
  if {$ver >= 10} {
      exp_send -i $connection_id "flowcontrol symmetric\r"
      sleep 3
      expect -i $connection_id "flowcontrol symmetric"
  } else {
      exp_send -i $connection_id "storm-control flowcontrol\r"
      sleep 3
      expect -i $connection_id "storm-control flowcontrol"
  }
  while {1} {
      expect -i $connection_id "#" break
  }
  exp_send -i $connection_id "exit\r"
  sleep 1
  Netgear_log_file "lib-stormctrl.tcl" "Enabling Flow Control on Switch $switch_name"
}

#*******************************************************************************
#  Function Name	: _ntgrStormControlFlowCtrlDisable
#
#  Description    : This function is called to disable flow control
#                   on the switch
#         
#  Usage          : _ntgrStormControlFlowCtrlDisable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrStormControlFlowCtrlDisable {switch_name} {
	set connection_id [_get_switch_handle $switch_name]
  exp_send -i $connection_id "configure\r"
  sleep 1
	Netgear_log_file "lib-stormctrl.tcl" "Disabling Flow Control on Switch $switch_name"
	exp_send -i $connection_id "no storm-control flowcontrol\r"
	sleep 5
	exp_send -i $connection_id "no flowcontrol\r"
	sleep 3
	#expect -i $connection_id "storm-control flowcontrol"
	while {1} {
	    expect -i $connection_id "#" break
	}
	exp_send -i $connection_id "exit\r"
	sleep 1
}

#*******************************************************************************
#  Function Name	: _ntgrStormControlNewFlowCtrlEnable
#
#  Description    : This function is called to Enable flow control
#                   on the switch
#         
#  Usage          : _ntgrStormControlNewFlowCtrlEnable <switch_name> <if_list>
# 
#  Author         : jim.xie for M6100
#
#*******************************************************************************
proc _ntgrStormControlNewFlowCtrlEnable {switch_name if_list} {
    set connection_id [_get_switch_handle $switch_name]
	#set ver [_ntgrGetVersionReleasebyConnectionID $connection_id]
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $connection_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "flowcontrol symmetric\r"
        after 200
        exp_send -i $connection_id "exit\r"
        after 200
        exp_send -i $connection_id "exit\r"
        after 200
    }
    Netgear_log_file "lib-stormctrl.tcl" "Enabling Flow Control on Switch $switch_name"
}

#*******************************************************************************
#  Function Name	: _ntgrStormControlNewFlowCtrlDisable
#
#  Description    : This function is called to disable flow control
#                   on the switch
#         
#  Usage          : _ntgrStormControlNewFlowCtrlDisable <switch_name> <if_list>
# 
#  Author         : jim.xie for M6100
#
#*******************************************************************************
proc _ntgrStormControlNewFlowCtrlDisable {switch_name if_list} {
	set connection_id [_get_switch_handle $switch_name]
    
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $connection_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "no flowcontrol\r"
        after 200
        exp_send -i $connection_id "exit\r"
        after 200
        exp_send -i $connection_id "exit\r"
        after 200
    }
	Netgear_log_file "lib-stormctrl.tcl" "Disabling Flow Control on Switch $switch_name"
}


#*******************************************************************************
#  Function Name	: _ntgrConfigureStormControl
#
#  Description    : This function is called to configure Storm control
#         
#  Usage          : _ntgrConfigureStormControl <switch>
#
#
#*******************************************************************************
proc _ntgrConfigureStormControl {switch} {
	Netgear_connect_switch $switch
	set flowctrl_status [_ntgrGetStormControlFlowCtrl $switch]
	if {$flowctrl_status == "enable"} {
		Netgear_log_file "lib-stormctrl.tcl" "Enabling Flow COntrol on Switch $switch"
		_ntgrStormControlFlowCtrlEnable $switch
	} elseif {$flowctrl_status =="disable"} {
		Netgear_log_file "lib-stormctrl.tcl" "Disabling Port Mirroring on Switch $switch"
		_ntgrStormControlFlowCtrlDisable $switch
	} else {
		Netgear_log_file "lib-stormctrl.tcl" "Error!Status should be either <enable> or <disable>"
	}

	set bcast_port_list [_ntgrGetStormControlBcastList $switch]
	foreach bcast_port $bcast_port_list {
		set port 	[lindex $bcast_port 0]
		set status  [lindex $bcast_port 1]
		set level   [lindex $bcast_port 2]
		set leveltype    [lindex $bcast_port 3]
		set action  [lindex $bcast_port 4]
		if {$status == "enable"} {
			_ntgrStormControlBroadcastEnable $switch $port $level $leveltype $action
		} elseif {$status == "disable"} {
			_ntgrStormControlBroadcastDisable $switch $port $level $leveltype $action
		} else {
			Netgear_log_file "lib-stormctrl.tcl" "Error!Status should be either <enable> or <disable>"
		}
	} 

	set mcast_port_list [_ntgrGetStormControlMcastList $switch]
	foreach mcast_port $mcast_port_list {
		set port 	[lindex $mcast_port 0]
		set status  [lindex $mcast_port 1]
		set level   [lindex $mcast_port 2]
		set leveltype    [lindex $mcast_port 3]
		set action  [lindex $mcast_port 4]
		if {$status == "enable"} {
			_ntgrStormControlMulticastEnable $switch $port $level $leveltype $action
		} elseif {$status == "disable"} {
			_ntgrStormControlMulticastDisable $switch $port $level $leveltype $action
		} else {
			Netgear_log_file "lib-stormctrl.tcl" "Error!Status should be either <enable> or <disable>"
		}
	}

	set ucast_port_list [_ntgrGetStormControlUcastList $switch]
	foreach ucast_port $ucast_port_list {
		set port 	[lindex $ucast_port 0]
		set status  [lindex $ucast_port 1]
		set level   [lindex $ucast_port 2]
		set leveltype    [lindex $ucast_port 3]
		set action  [lindex $ucast_port 4]
		if {$status == "enable"} {
			_ntgrStormControlUnicastEnable $switch $port $level $leveltype $action
		} elseif {$status == "disable"} {
			_ntgrStormControlUnicastDisable $switch $port $level $leveltype $action
		} else {
			Netgear_log_file "lib-stormctrl.tcl" "Error!Status should be either <enable> or <disable>"
		}
	}
	Netgear_disconnect_switch $switch
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigFlowCtrl
#
#  Description    : This function is called to configure flowcontrol on the switch.
#
#  Usage          : _ntgrConfigFlowCtrl <switch_name>
#
#*******************************************************************************
proc _ntgrConfigFlowCtrl {switch_name} {
    Netgear_connect_switch $switch_name

    set flowctrl_status [_ntgrGetStormControlFlowCtrl $switch_name]
    if {$flowctrl_status == "enable"} {
        _ntgrStormControlFlowCtrlEnable $switch_name
    } elseif {$flowctrl_status =="disable"} {
        _ntgrStormControlFlowCtrlDisable $switch_name
    } else {
        Netgear_log_file "lib-stormctrl.tcl" "Error: flow control value should be either <enable> or <disable>"
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigNewFlowCtrl
#
#  Description    : This function is called to configure flowcontrol on the switch.
#
#  Usage          : _ntgrConfigNewFlowCtrl <switch_name> <if_list>
#
# 
#  Author         : jim.xie for M6100
#
#*******************************************************************************
proc _ntgrConfigNewFlowCtrl {switch_name if_list} {
    Netgear_connect_switch $switch_name

    set flowctrl_status [_ntgrGetStormControlFlowCtrl $switch_name]
    if {$flowctrl_status == "enable"} {
        _ntgrStormControlNewFlowCtrlEnable $switch_name $if_list
    } elseif {$flowctrl_status =="disable"} {
        _ntgrStormControlNewFlowCtrlDisable $switch_name $if_list
    } else {
        Netgear_log_file "lib-stormctrl.tcl" "Error: flow control value should be either <enable> or <disable>"
    }

    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrConfigBCastStormCtrl
#
#  Description    : This function is called to configure broadcast storm control
#                   on the switch.
#
#  Usage          : _ntgrConfigBCastStormCtrl <switch_name>
#
#*******************************************************************************
proc _ntgrConfigBCastStormCtrl {switch} {
    Netgear_connect_switch $switch

    set bcast_port_list [_ntgrGetStormControlBcastList $switch]
    foreach bcast_port $bcast_port_list {
        set port    [lindex $bcast_port 0]
        set status  [lindex $bcast_port 1]
        set level   [lindex $bcast_port 2]
		set leveltype    [lindex $bcast_port 3]
		set action  [lindex $bcast_port 4]
        if {$status == "enable"} {
            _ntgrStormControlBroadcastEnable $switch $port $level $leveltype $action
        } elseif {$status == "disable"} {
            _ntgrStormControlBroadcastDisable $switch $port $level $leveltype $action
        } else {
            Netgear_log_file "lib-stormctrl.tcl" "Error: status value should be either <enable> or <disable>"
        }
    }

    Netgear_disconnect_switch $switch
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigMCastStormCtrl
#
#  Description    : This function is called to configure multicast storm control
#                   on the switch.
#
#  Usage          : _ntgrConfigMCastStormCtrl <switch_name>
#
#*******************************************************************************
proc _ntgrConfigMCastStormCtrl {switch} {
    Netgear_connect_switch $switch

    set mcast_port_list [_ntgrGetStormControlMcastList $switch]
    foreach mcast_port $mcast_port_list {
        set port    [lindex $mcast_port 0]
        set status  [lindex $mcast_port 1]
        set level   [lindex $mcast_port 2]
		set leveltype    [lindex $mcast_port 3]
		set action  [lindex $mcast_port 4]
        if {$status == "enable"} {
            _ntgrStormControlMulticastEnable $switch $port $level $leveltype $action
        } elseif {$status == "disable"} {
            _ntgrStormControlMulticastDisable $switch $port $level $leveltype $action
        } else {
            Netgear_log_file "lib-stormctrl.tcl" "Error: status value should be either <enable> or <disable>"
        }
    }

    Netgear_disconnect_switch $switch
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigUCastStormCtrl
#
#  Description    : This function is called to configure unicast storm control
#                   on the switch.
#
#  Usage          : _ntgrConfigUCastStormCtrl <switch_name>
#
#*******************************************************************************
proc _ntgrConfigUCastStormCtrl {switch} {
    Netgear_connect_switch $switch

    set ucast_port_list [_ntgrGetStormControlUcastList $switch]
    foreach ucast_port $ucast_port_list {
        set port    [lindex $ucast_port 0]
        set status  [lindex $ucast_port 1]
        set level   [lindex $ucast_port 2]
		set leveltype    [lindex $ucast_port 3]
		set action  [lindex $ucast_port 4]
		
        if {$status == "enable"} {
			_ntgrStormControlUnicastEnable $switch $port $level $leveltype $action
        } elseif {$status == "disable"} {
            _ntgrStormControlUnicastDisable $switch $port $level $leveltype $action
        } else {
            Netgear_log_file "lib-stormctrl.tcl" "Error: status value should be either <enable> or <disable>"
        }
    }

    Netgear_disconnect_switch $switch
}