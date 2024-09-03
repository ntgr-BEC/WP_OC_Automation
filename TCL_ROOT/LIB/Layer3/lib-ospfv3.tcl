#!/bin/sh
################################################################################   
#
#  File Name		  : lib-ospfv3.tcl
#
#  Description      :
#         This TCL contains functions to configure OSPFv3
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        14/02/13     jim xie           Created
#
#
################################################################################

#1*******************************************************************************
#  Function Name	: _ntgrOspfv3Enable
#
#  Description    	: This function is called to enable OSPFv3 on switch
#         
#  Usage          	: _ntgrOspfv3Enable <switch_name>
#
#
#*******************************************************************************
proc _ntgrOspfv3Enable {switch_name} {
    
	set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
          
    expect -i $connection_id -re "#"
 	exp_send -i $connection_id "configure\r"
	sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
    exp_send -i $connection_id "enable\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#2*******************************************************************************
#  Function Name	: _ntgrOspfv3Disable
#
#  Description    	: This function is called to disable OSPFv3 on switch
#         
#  Usage          	: _ntgrOspfv3Disable <switch_name>
#
#
#*******************************************************************************
proc _ntgrOspfv3Disable {switch_name} {
    
	set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
    exp_send -i $connection_id "no enable\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"	
    
	if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }      

}

#3*******************************************************************************
#  Function Name	: _ntgrConfigureOspfv3RouterID
#
#  Description    	: This function is called to configure Router ID for OSPFv3 on switch
#         
#  Usage          	: _ntgrConfigureOspfv3RouterID <switch_name> <router_id>
#
#
#*******************************************************************************
proc _ntgrConfigureOspfv3RouterID {switch_name router_id} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
    exp_send -i $connection_id "router-id $router_id\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#4*******************************************************************************
#  Function Name	: _ntgrOspfv3InterfaceEnable
#
#  Description    	: This function is called to enable ospfv3 admin mode on interface
#         
#  Usage          	: _ntgrOspfv3InterfaceEnable <switch_name> <if_list>
#
#
#*******************************************************************************
proc _ntgrOspfv3InterfaceEnable {switch_name if_list} {
      
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "ipv6 ospf\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#5*******************************************************************************
#  Function Name	: _ntgrOspfv3InterfaceDisable
#
#  Description    	: This function is called to disable ospfv3 admin mode on interface
#         
#  Usage          	: _ntgrOspfv3InterfaceDisable <switch_name> <if_list>
#
#
#*******************************************************************************
proc _ntgrOspfv3InterfaceDisable {switch_name if_list} {
            
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no ipv6 ospf\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#6*******************************************************************************
#  Function Name	: _ntgrConfigureOspfv3Redistribute
#
#  Description    	: This function is called to configure Redistribute for OSPFv3 on switch
#         
#  Usage          	: _ntgrConfigureOspfv3Redistribute <switch_name> <source_type> <metric_value> <metric_type> <tag>
#
#  Params           :
#                     source_type        connected/static 
#                     metric_value       <0-16777214>
#                     metric_type        1/2
#                     tag                <0-4294967295>
#
#*******************************************************************************
proc _ntgrConfigureOspfv3Redistribute {switch_name source_type metric_value metric_type tag} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
	exp_send -i $connection_id "redistribute $source_type\r"
    sleep 1
	
	if {$metric_value != ""} {
	    exp_send -i $connection_id "redistribute $source_type metric $metric_value\r"
        sleep 1
	}
	if {$metric_type != ""} {
	    exp_send -i $connection_id "redistribute $source_type metric-type $metric_type\r"
        sleep 1
	}
	if {$tag != ""} {
	    exp_send -i $connection_id "redistribute $source_type tag $tag\r"
        sleep 1
	}
	
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#7*******************************************************************************
#  Function Name	: _ntgrResetOspfv3Redistribute
#
#  Description    	: This function is called to reset Redistribute for OSPFv3 on switch
#         
#  Usage          	: _ntgrResetOspfv3Redistribute <switch_name> <source_type>
#
#  Params           :
#                     source_type        connected/static 
#
#*******************************************************************************
proc _ntgrResetOspfv3Redistribute {switch_name source_type} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
	exp_send -i $connection_id "no redistribute $source_type \r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#8*******************************************************************************
#
#  Function Name  : _ntgrCheckOSPFv3Neighbor
#
#  Description    : This function is used to get the Neighbor info of OSPFv3
#
#  Usage          : _ntgrCheckOSPFv3Neighbor <switch_id> <interface_id> <router_id> <state>
#
#*******************************************************************************
proc _ntgrCheckOSPFv3Neighbor {switch_id interface_id router_id state} {

    set cnn_id [_get_switch_handle $switch_id]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_id
        set cnn_id [_get_switch_handle $switch_id]
        set bCnn 0
    }
    set str1 {}
    set str2 {}
	set str3 {}
    set str4 {}
	set str5 {}
	set match {}
	
	set result ""
   	exp_send -i $cnn_id "show ipv6 ospf neighbor interface $interface_id \r"
    expect -i $cnn_id "show ipv6 ospf neighbor"
    expect {
        -i $cnn_id -re "#" { set result $result$expect_out(buffer) }
        -i $cnn_id -re "More" {
            set result $result$expect_out(buffer)
                exp_send -i $cnn_id " "
                exp_sleep 1
            	exp_continue
        }
        timeout { exp_send -i $cnn_id " "; exp_continue }
    }

	if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_id
    }

	set regSWMAC "$router_id\[ ]+(\[0-9\]+)\[ ]+(\[0-9\]+)\[ ]+$interface_id\[ ]+(\[a-zA-Z /-\]+)\[ ]+(\[0-9\]+)(.*)"
    regexp $regSWMAC $result match str1 str2 str3 str4 str5
	Netgear_log_file "Function: _ntgrOSPFv3Neighbor " "Interface: $interface_id neighbor state is: $str3."

	set return_value 1
	if {$str3 == ""} {
	    set return_value 0
		Netgear_log_file "Function: _ntgrOSPFv3Neighbor " "Error: Interface: $interface_id neighbor state is Error."
	} else {
	    set macth_value [string first $state [string tolower $str3] ]
		if {$macth_value < 0} {
		    set return_value 0
			Netgear_log_file "Function: _ntgrOSPFv3Neighbor " "Error: Interface: $interface_id neighbor state is Error."
		}
	}

	return $return_value
}

#9*******************************************************************************
#  Function Name	: _ntgrEnableOspfv3Advertise
#
#  Description    	: This function is called to configure Advertise for OSPFv3 on switch
#         
#  Usage          	: _ntgrEnableOspfv3Advertise <switch_name> <metric_value> <metric_type>
#
#  Params           :
#                     metric_value       <0-16777214>
#                     metric_type        1/2
#
#*******************************************************************************
proc _ntgrEnableOspfv3Advertise {switch_name metric_value metric_type} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
	if {$metric_value != "" && $metric_type != ""} {
        sleep 1
	    exp_send -i $connection_id "default-information originate always metric $metric_value metric-type $metric_type\r"
	} else {
	    sleep 1
	    exp_send -i $connection_id "default-information originate always\r"
	}
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }

}

#10*******************************************************************************
#  Function Name	: _ntgrDisableOspfv3Advertise
#
#  Description    	: This function is called to reset Advertise for OSPFv3 on switch
#         
#  Usage          	: _ntgrDisableOspfv3Advertise <switch_name>
#
#  Params           :
#
#*******************************************************************************
proc _ntgrDisableOspfv3Advertise {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
    exp_send -i $connection_id "no default-information originate\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }

}

#11*******************************************************************************
#  Function Name	: _ntgrConfigureOspfv3AreaRange
#
#  Description    	: This function is called to configure area range for OSPFv3 on switch
#         
#  Usage          	: _ntgrConfigureOspfv3AreaRange <switch_name> <area_id> <ipv6_addr> <lsdb_type> <advertise>
#
#  Params           :
#                     area_id            0/1 ...
#                     ipv6_addr          <ipv6-prefix>/<prefix-length>
#                     lsdb_type          network_summary / nssa_external
#                     advertise          true / false
#
#*******************************************************************************
proc _ntgrConfigureOspfv3AreaRange {switch_name area_id ipv6_addr lsdb_type advertise} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	set range_cmd ""
	if {$lsdb_type == "network_summary"} {
	    if {$advertise == "true"} {
		    set range_cmd "area $area_id range $ipv6_addr summarylink advertise \r"
		} else {
		    set range_cmd "area $area_id range $ipv6_addr summarylink not-advertise \r"
		}
	} else {
	    if {$advertise == "true"} {
		    set range_cmd "area $area_id range $ipv6_addr nssaexternallink advertise \r"
		} else {
		    set range_cmd "area $area_id range $ipv6_addr nssaexternallink not-advertise \r"
		}
	}

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
	exp_send -i $connection_id "$range_cmd\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#12*******************************************************************************
#  Function Name	: _ntgrDeleteOspfv3AreaRange
#
#  Description    	: This function is called to delete area range for OSPFv3 on switch
#         
#  Usage          	: _ntgrDeleteOspfv3AreaRange <switch_name> <area_id> <ipv6_addr> <lsdb_type>
#
#  Params           :
#                     area_id            0/1 ...
#                     ipv6_addr          <ipv6-prefix>/<prefix-length>
#                     lsdb_type          network_summary / nssa_external
#
#*******************************************************************************
proc _ntgrDeleteOspfv3AreaRange {switch_name area_id ipv6_addr lsdb_type} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	set range_cmd ""
	if {$lsdb_type == "network_summary"} {
	    set range_cmd "no area $area_id range $ipv6_addr summarylink \r"
	} else {
	    set range_cmd "no area $area_id range $ipv6_addr nssaexternallink \r"
	}
	
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
	exp_send -i $connection_id "$range_cmd\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#13*******************************************************************************
#  Function Name	: _ntgrConfigureOspfv3InterfaceAreaID
#
#  Description    	: This function is called to configure ospfv3 area id on interface
#         
#  Usage          	: _ntgrConfigureOspfv3InterfaceAreaID <switch_name> <if_list> <area_id>
#
#
#*******************************************************************************
proc _ntgrConfigureOspfv3InterfaceAreaID {switch_name if_list area_id} {
      
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "ipv6 ospf area $area_id\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#14*******************************************************************************
#
#  Function Name  : _ntgrCheckOSPFv3LinkStateDatabase
#
#  Description    : This function is used to get the link state database of OSPFv3
#
#  Params           :
#                     database_type       external / link
#                     ls_type             AS-External-LSA / Link-LSA
#
#  Usage          : _ntgrCheckOSPFv3LinkStateDatabase <switch_id> <database_type> <ls_type>
#
#*******************************************************************************
proc _ntgrCheckOSPFv3LinkStateDatabase {switch_id database_type ls_type} {

    set cnn_id [_get_switch_handle $switch_id]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_id
        set cnn_id [_get_switch_handle $switch_id]
        set bCnn 0
    }
    set str1 {}
	set str2 {}
	set match {}
	
	set result ""
   	exp_send -i $cnn_id "show ipv6 ospf database $database_type \r"
    expect -i $cnn_id "show ipv6 ospf database"
    expect {
        -i $cnn_id -re "#" { set result $result$expect_out(buffer) }
        -i $cnn_id -re "More" {
            set result $result$expect_out(buffer)
                exp_send -i $cnn_id " "
                exp_sleep 1
            	exp_continue
        }
        timeout { exp_send -i $cnn_id " "; exp_continue }
    }

	if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_id
    }
	
    set cnt 0
	set tmpList [split $result \n]
    foreach iList $tmpList {
		if {[string match "LS Type: $ls_type" $iList]} {
		     incr cnt
        } 
    }
	return $cnt
}

#15*******************************************************************************
#  Function Name	: _ntgrSetOspfv3ExitOverflowInterval
#
#  Description    	: This function is called to set exit overflow interval for OSPFv3 on switch
#         
#  Usage          	: _ntgrSetOspfv3ExitOverflowInterval <switch_name> <inter>
#
#  Params           :
#                     inter            (0 to 2147483647)
#
#*******************************************************************************
proc _ntgrSetOspfv3ExitOverflowInterval {switch_name inter} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
	exp_send -i $connection_id "exit-overflow-interval $inter\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#16*******************************************************************************
#  Function Name	: _ntgrResetOspfv3ExitOverflowInterval
#
#  Description    	: This function is called to reset exit overflow interval for OSPFv3 on switch
#         
#  Usage          	: _ntgrResetOspfv3ExitOverflowInterval <switch_name>
#
#  Params           :
#
#*******************************************************************************
proc _ntgrResetOspfv3ExitOverflowInterval {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
	exp_send -i $connection_id "no exit-overflow-interval\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#17*******************************************************************************
#  Function Name	: _ntgrSetOspfv3ExternalLSDBLimit
#
#  Description    	: This function is called to set External LSDB Limit for OSPFv3 on switch
#         
#  Usage          	: _ntgrSetOspfv3ExternalLSDBLimit <switch_name> <limit_cnt>
#
#  Params           :
#                     limit_cnt            (-1(No Limit) to 2147483647)
#
#*******************************************************************************
proc _ntgrSetOspfv3ExternalLSDBLimit {switch_name limit_cnt} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
	exp_send -i $connection_id "external-lsdb-limit $limit_cnt\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#18*******************************************************************************
#  Function Name	: _ntgrResetOspfv3ExternalLSDBLimit
#
#  Description    	: This function is called to reset External LSDB Limit for OSPFv3 on switch
#         
#  Usage          	: _ntgrResetOspfv3ExternalLSDBLimit <switch_name>
#
#  Params           :
#
#*******************************************************************************
proc _ntgrResetOspfv3ExternalLSDBLimit {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
	exp_send -i $connection_id "no external-lsdb-limit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#19*******************************************************************************
#  Function Name	: _ntgrOspfv3InterfaceEnableMTUIgnore
#
#  Description    	: This function is called to enable ospfv3 mtu ignore on interface
#         
#  Usage          	: _ntgrOspfv3InterfaceEnableMTUIgnore <switch_name> <if_list>
#
#
#*******************************************************************************
proc _ntgrOspfv3InterfaceEnableMTUIgnore {switch_name if_list} {
      
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "ipv6 ospf mtu-ignore\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#20*******************************************************************************
#  Function Name	: _ntgrOspfv3InterfaceDisableMTUIgnore
#
#  Description    	: This function is called to disable ospfv3 mtu ignore on interface
#         
#  Usage          	: _ntgrOspfv3InterfaceDisableMTUIgnore <switch_name> <if_list>
#
#
#*******************************************************************************
proc _ntgrOspfv3InterfaceDisableMTUIgnore {switch_name if_list} {
            
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no ipv6 ospf mtu-ignore\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#21*******************************************************************************
#  Function Name	: _ntgrSetOspfv3DistanceValue
#
#  Description    	: This function is called to set distance value for OSPFv3 on switch
#         
#  Usage          	: _ntgrSetOspfv3DistanceValue <switch_name> <distance_type> <distance_value>
#
#  Params           :
#                      distance_type   external/inter-area/intra-area
#                      distance_value  1-255
#
#*******************************************************************************
proc _ntgrSetOspfv3DistanceValue {switch_name distance_type distance_value} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
	exp_send -i $connection_id "distance ospf $distance_type $distance_value \r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#22*******************************************************************************
#  Function Name	: _ntgrEnableOspfv3InterfacePassiveMode
#
#  Description    	: This function is called to enable Passive Mode on interface
#         
#  Usage          	: _ntgrEnableOspfv3InterfacePassiveMode <switch_name> <if_list>
#
#
#*******************************************************************************
proc _ntgrEnableOspfv3InterfacePassiveMode {switch_name if_list} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    
     foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $connection_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "ipv6 router ospf\r"
        after 200
        exp_send -i $connection_id "passive-interface $if_name\r"
        after 200
        exp_send -i $connection_id "exit\r"
        after 200
        exp_send -i $connection_id "exit\r"
        after 200
    }

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#23*******************************************************************************
#  Function Name	: _ntgrDisableOspfv3InterfacePassiveMode
#
#  Description    	: This function is called to disable Passive Mode on interface
#         
#  Usage          	: _ntgrDisableOspfv3InterfacePassiveMode <switch_name> <if_list>
#
#
#*******************************************************************************
proc _ntgrDisableOspfv3InterfacePassiveMode {switch_name if_list} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    
     foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $connection_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "ipv6 router ospf\r"
        after 200
        exp_send -i $connection_id "no passive-interface $if_name\r"
        after 200
        exp_send -i $connection_id "exit\r"
        after 200
        exp_send -i $connection_id "exit\r"
        after 200
    }

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#24*******************************************************************************
#  Function Name	: _ntgrSetOspfv3InterfaceAreaID
#
#  Description    	: This function is called to set ospfv3 area id  on interface
#         
#  Usage          	: _ntgrSetOspfv3InterfaceAreaID <switch_name> <if_list> <area_id>
#
#
#*******************************************************************************
proc _ntgrSetOspfv3InterfaceAreaID {switch_name if_list area_id} {
      
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "ipv6 ospf area $area_id\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#25*******************************************************************************
#  Function Name	: _ntgrConfigureOspfv3StubAreaID
#
#  Description    	: This function is called to configure stub area id for OSPFv3 on switch
#         
#  Usage          	: _ntgrConfigureOspfv3StubAreaID <switch_name> <area_id> <summery>
#
#  Paramer          :
#                    area_id 
#                    summery  0(disable)/1(enable)
#
#*******************************************************************************
proc _ntgrConfigureOspfv3StubAreaID {switch_name area_id summery} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 2
	if {$summery} {
        exp_send -i $connection_id "area $area_id stub\r"
        sleep 2
	} else {
	    exp_send -i $connection_id "area $area_id stub\r"
        sleep 2
	    exp_send -i $connection_id "area $area_id stub no-summary\r"
        sleep 2
	}
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#26*******************************************************************************
#  Function Name	: _ntgrDeleteOspfv3StubAreaID
#
#  Description    	: This function is called to delete stub area id for OSPFv3 on switch
#         
#  Usage          	: _ntgrDeleteOspfv3StubAreaID <switch_name> <area_id> <summery>
#
#  Paramer          :
#                    area_id 
#                    summery  0(disable)/1(enable)
#
#*******************************************************************************
proc _ntgrDeleteOspfv3StubAreaID {switch_name area_id summery} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
	if {$summery} {
        exp_send -i $connection_id "no area $area_id stub\r"
        sleep 2
	} else {
	    exp_send -i $connection_id "no area $area_id stub\r"
        sleep 2
	    exp_send -i $connection_id "no area $area_id stub no-summary\r"
        sleep 2
	}
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#27*******************************************************************************
#
#  Function Name  : _ntgrCheckOSPFv3StubAreaSummary
#
#  Description    : This function is used to check the value of Stub Area Summary
#
#  Paramas        :
#                 area_id : 0.0.0.1
#                 expect_value : Enable or Disable
#
#  Usage          : _ntgrCheckOSPFv3StubAreaSummary <switch_id> <area_id> <expect_value>
#
#*******************************************************************************
proc _ntgrCheckOSPFv3StubAreaSummary {switch_id area_id expect_value} {

    set cnn_id [_get_switch_handle $switch_id]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_id
        set cnn_id [_get_switch_handle $switch_id]
        set bCnn 0
    }
    set str1 {}
    set str2 {}
	set str3 {}
    set str4 {}
	set match {}
	
	set result ""
   	exp_send -i $cnn_id "show ipv6 ospf stub table\r"
    expect -i $cnn_id "show ipv6 ospf stub table"
    expect {
        -i $cnn_id -re "#" { set result $result$expect_out(buffer) }
        -i $cnn_id -re "More" {
            set result $result$expect_out(buffer)
                exp_send -i $cnn_id " "
                exp_sleep 1
            	exp_continue
        }
        timeout { exp_send -i $cnn_id " "; exp_continue }
    }

	if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_id
    }

	set regSWMAC "$area_id\[ ]+(\[A-Za-z\]+)\[ ]+(\[0-9\])\[ ]+(\[A-Za-z\]+)(.*)"
    regexp $regSWMAC $result match str1 str2 str3 str4

	set return_value 0
	Netgear_log_file "Checking Result: " "str3=$str3"
	if {$str3 != ""} {
		if {[string tolower $str3] == [string tolower $expect_value]} {
		    set return_value 1
		}
	}

	return $return_value
}

#28*******************************************************************************
#  Function Name	: _ntgrSetOspfv3InterfacePriority
#
#  Description    	: This function is called to set ospfv3 priority on interface
#         
#  Usage          	: _ntgrSetOspfv3InterfacePriority <switch_name> <if_list> <priority>
#
#
#*******************************************************************************
proc _ntgrSetOspfv3InterfacePriority {switch_name if_list priority} {
      
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "ipv6 ospf priority $priority\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#29*******************************************************************************
#  Function Name	: _ntgrClearIPv6Ospfv3
#
#  Description    	: This function is called to clear ipv6  OSPFv3 on switch
#         
#  Usage          	: _ntgrClearIPv6Ospfv3 <switch_name>
#
#
#*******************************************************************************
proc _ntgrClearIPv6Ospfv3 {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "clear ipv6 ospf\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
	
}

#30*******************************************************************************
#
#  Function Name  : _ntgrGetOSPFv3LSSeqNumber
#
#  Description    : This function is used to get LS Seq Number from the link state database of OSPFv3
#
#  Params           :
#                     neighbor_rid       1.1.1.1
#
#  Usage          : _ntgrGetOSPFv3LSSeqNumber <switch_id> <neighbor_rid>
#
#*******************************************************************************
proc _ntgrGetOSPFv3LSSeqNumber {switch_id neighbor_rid} {

    set cnn_id [_get_switch_handle $switch_id]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_id
        set cnn_id [_get_switch_handle $switch_id]
        set bCnn 0
    }
    
	set result ""
   	exp_send -i $cnn_id "show ipv6 ospf database router adv-router $neighbor_rid \r"
    expect -i $cnn_id "show ipv6 ospf database router adv-router"
    expect {
        -i $cnn_id -re "#" { set result $result$expect_out(buffer) }
        -i $cnn_id -re "More" {
            set result $result$expect_out(buffer)
                exp_send -i $cnn_id " "
                exp_sleep 1
            	exp_continue
        }
        timeout { exp_send -i $cnn_id " "; exp_continue }
    }

	if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_id
    }
	
    set cnt 0
	set tmpList [split $result \n]
    foreach iList $tmpList {
	    set str1 {}
		set match {}
	    set regSWMAC "LS Seq Number:\[ ]+(0x\[0-9A-Za-z\]+)"
        regexp $regSWMAC $iList match str1
		if {$str1 != "" } {
		     set cnt $str1
        } 
    }
	return $cnt
}

#31*******************************************************************************
#  Function Name	: _ntgrSetOspfv3InterfaceMetricCost
#
#  Description    	: This function is called to set ospfv3 metric cost on interface
#         
#  Usage          	: _ntgrSetOspfv3InterfaceMetricCost <switch_name> <if_list> <cost_value>
#
#  Parames          :
#                     cost_value  : 1-65535
#
#*******************************************************************************
proc _ntgrSetOspfv3InterfaceMetricCost {switch_name if_list cost_value} {
      
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "ipv6 ospf cost $cost_value\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#32*******************************************************************************
#  Function Name	: _ntgrAddOspfv3VirtualLink
#
#  Description    	: This function is called to add virtual-link for OSPFv3 on switch
#         
#  Usage          	: _ntgrAddOspfv3VirtualLink <switch_name> <area_id> <nbrouter_id>
#
#  Paramer          :
#                    area_id      0.0.0.2
#                    nbrouter_id  4.4.4.4
#
#*******************************************************************************
proc _ntgrAddOspfv3VirtualLink {switch_name area_id nbrouter_id} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
    exp_send -i $connection_id "area $area_id virtual-link $nbrouter_id\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#33*******************************************************************************
#  Function Name	: _ntgrDeleteOspfv3VirtualLink
#
#  Description    	: This function is called to delete virtual-link for OSPFv3 on switch
#         
#  Usage          	: _ntgrDeleteOspfv3VirtualLink <switch_name> <area_id> <nbrouter_id>
#
#  Paramer          :
#                    area_id      0.0.0.2
#                    nbrouter_id  4.4.4.4
#
#*******************************************************************************
proc _ntgrDeleteOspfv3VirtualLink {switch_name area_id nbrouter_id} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
    exp_send -i $connection_id "no area $area_id virtual-link $nbrouter_id\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#34*******************************************************************************
#
#  Function Name  : _ntgrCheckOSPFv3VirtualLinkState
#
#  Description    : This function is used to check virtual link state of OSPFv3
#
#  Params           :
#                    area_id      0.0.0.2
#                    nbrouter_id  4.4.4.4
#                    nb_expect_state      down/up
#                    local_expect_state   down/up
#
#  Usage          : _ntgrCheckOSPFv3VirtualLinkState <switch_name> <area_id> <nbrouter_id> <nb_expect_state> <local_expect_state>
#
#*******************************************************************************
proc _ntgrCheckOSPFv3VirtualLinkState {switch_id area_id nbrouter_id nb_expect_state local_expect_state} {

    set cnn_id [_get_switch_handle $switch_id]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_id
        set cnn_id [_get_switch_handle $switch_id]
        set bCnn 0
    }
    
	set result ""
   	exp_send -i $cnn_id "show ipv6 ospf virtual-link $area_id $nbrouter_id \r"
    expect -i $cnn_id "show ipv6 ospf virtual-link"
    expect {
        -i $cnn_id -re "#" { set result $result$expect_out(buffer) }
        -i $cnn_id -re "More" {
            set result $result$expect_out(buffer)
                exp_send -i $cnn_id " "
                exp_sleep 1
            	exp_continue
        }
        timeout { exp_send -i $cnn_id " "; exp_continue }
    }

	if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_id
    }
	
    set real_nbstate {}
	set real_lcstate {}
	set tmpList [split $result \n]
    foreach iList $tmpList {
	    set str1 {}
		set str2 {}
		set match1 {}
		set match2 {}
	    set regSWMAC "^State\[ .]+(\[A-Za-z-\]+)"
		set regSWMAC2 "^Neighbor State\[ .]+(\[A-Za-z\]+)"
        regexp $regSWMAC $iList match1 str1
		regexp $regSWMAC2 $iList match2 str2
		if {$str1 != "" } {
		     set real_lcstate $str1
        } 
		if {$str2 != "" } {
		     set real_nbstate $str2
        } 
    }
	set re_value 0
	Netgear_log_file "Real Neighbor State is: $real_nbstate " " Expect Neighbor State is: $nb_expect_state"
	Netgear_log_file "Real State is: $real_lcstate " " Expect State is: $local_expect_state"
	if {$real_lcstate == $local_expect_state && $real_nbstate == $nb_expect_state} {
	    set re_value 1
	} else {
	    Netgear_log_file "Check Real Neighbor State and State of $area_id Error." ""
	}
	return $re_value
}

#35*******************************************************************************
#  Function Name	: _ntgrConfigureOspfv3NSSAAreaID
#
#  Description    	: This function is called to configure nssa area id for OSPFv3 on switch
#         
#  Usage          	: _ntgrConfigureOspfv3NSSAAreaID <switch_name> <area_id>
#
#  Paramer          :
#                    area_id 
#
#*******************************************************************************
proc _ntgrConfigureOspfv3NSSAAreaID {switch_name area_id} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 2
    exp_send -i $connection_id "area $area_id nssa\r"
    sleep 2
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#36*******************************************************************************
#  Function Name	: _ntgrDeleteOspfv3NSSAAreaID
#
#  Description    	: This function is called to delete nssa area id for OSPFv3 on switch
#         
#  Usage          	: _ntgrDeleteOspfv3StubAreaID <switch_name> <area_id>
#
#  Paramer          :
#                    area_id 
#
#*******************************************************************************
proc _ntgrDeleteOspfv3NSSAAreaID {switch_name area_id} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
    exp_send -i $connection_id "no area $area_id nssa\r"
    sleep 2
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}


#37*******************************************************************************
#  Function Name	: _ntgrConfigureOspfv3AutoCost
#
#  Description    	: This function is called to configure autocost Reference Bandwidth for OSPFv3 on switch
#         
#  Usage          	: _ntgrConfigureOspfv3AutoCost <switch_name> <bandwidth_value>
#
#  Paramer          :
#                    bandwidth_value   <1-4294967>
#
#*******************************************************************************
proc _ntgrConfigureOspfv3AutoCost {switch_name bandwidth_value} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 2
    exp_send -i $connection_id "auto-cost reference-bandwidth $bandwidth_value\r"
    sleep 2
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#38*******************************************************************************
#  Function Name	: _ntgrResetOspfv3AutoCost
#
#  Description    	: This function is called to reset autocost value for OSPFv3 on switch
#         
#  Usage          	: _ntgrResetOspfv3AutoCost <switch_name>
#
#  Paramer          :
#
#*******************************************************************************
proc _ntgrResetOspfv3AutoCost {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	    

	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "ipv6 router ospf\r"
    sleep 1
    exp_send -i $connection_id "no auto-cost reference-bandwidth\r"
    sleep 2
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#39*******************************************************************************
#  Function Name	: _ntgrDelOspfv3InterfaceAreaID
#
#  Description    	: This function is called to delete ospfv3 area id  on interface
#         
#  Usage          	: _ntgrDelOspfv3InterfaceAreaID <switch_name> <if_list> <area_id>
#
#
#*******************************************************************************
proc _ntgrDelOspfv3InterfaceAreaID {switch_name if_list area_id} {
      
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no ipv6 ospf area $area_id\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}


#*******************************************************************************
#  Function Name	: _ntgrDelOspfv3InterfaceMetricCost
#
#  Description    	: This function is called to delete ospfv3 metric cost on interface
#         
#  Usage          	: _ntgrDelOspfv3InterfaceMetricCost <switch_name> <if_list> 
#
#  Parames          :
#                     cost_value  : 1-65535
#
#*******************************************************************************
proc _ntgrDelOspfv3InterfaceMetricCost {switch_name if_list} {
      
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no ipv6 ospf cost\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}