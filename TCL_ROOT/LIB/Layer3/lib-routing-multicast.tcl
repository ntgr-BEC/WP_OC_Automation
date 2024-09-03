#!/bin/sh
################################################################################   
#
#  File Name		  : cal-ntgr-routing-multicast.tcl
#
#  Description      :
#         This TCL contains functions to configure Routing Multicast
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#       2014-9-14     Jason.Li              Created
#
#
#
#

#*******************************************************************************
#  Function Name	: _ntgrMulticastRoutingEnable
#
#  Description    : This function is called to enable Multicast Routing on the Switch
#         
#  Usage          : _ntgrMulticastRoutingEnable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrMulticastRoutingEnable {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "ip multicast\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrMulticastRoutingDisable
#
#  Description    : This function is called to disable Multicast routing on Switch
#         
#  Usage          : _ntgrMulticastRoutingDisable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrMulticastRoutingDisable {switch_name} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "no ip multicast\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  
}

#*******************************************************************************
#  Function Name	: _ntgrPIMDenseEnable
#
#  Description    : This function is called to enable PIM Dense on the Switch
#         
#  Usage          : _ntgrPIMDenseEnable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrPIMDenseEnable {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "ip pim dense\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}
#*******************************************************************************
#  Function Name	: _ntgrIPv6PIMDenseEnable
#
#  Description    : This function is called to enable IPv6 PIM Dense on the Switch
#         
#  Usage          : _ntgrIPv6PIMDenseEnable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrIPv6PIMDenseEnable {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "ipv6 pim dense\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}


#*******************************************************************************
#  Function Name	: _ntgrPIMDenseDisable
#
#  Description    : This function is called to disable PIM Dense  on Switch
#         
#  Usage          : CALPIMDenseDisable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrPIMDenseDisable {switch_name} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "no ip pim dense\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    
}
#*******************************************************************************
#  Function Name	: _ntgrIPv6PIMDenseDisable
#
#  Description    : This function is called to disable IPv6 PIM Dense  on Switch
#         
#  Usage          : CALPIMDenseDisable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrIPv6PIMDenseDisable {switch_name} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "no ipv6 pim dense\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    
}

#*******************************************************************************
#  Function Name	: _ntgrPIMPortEnable
#
#  Description    	: This function is called to enable PIM  on port
#         
#  Usage          	: _ntgrPIMPortEnable <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrPIMPortEnable {switch_name if_list} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    

   foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "ip pim\r"
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

#*******************************************************************************
#  Function Name	: _ntgrConfigurePIMPortDRPriority
#
#  Description    	: This function is called to configutre dr-priority on port
#         
#  Usage          	: _ntgrConfigurePIMPortDRPriority <switch_name> <port> <priority>
#
#
#*******************************************************************************
proc _ntgrConfigurePIMPortDRPriority {switch_name if_list priority} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    

   foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "ip pim dr-priority $priority\r"
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

#*******************************************************************************
#  Function Name	: _ntgrIPv6PIMPortEnable
#
#  Description    	: This function is called to enable IPv6 PIM  on port
#         
#  Usage          	: _ntgrPIMPortEnable <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrIPv6PIMPortEnable {switch_name if_list} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    

   foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "ipv6 pim\r"
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


#*******************************************************************************
#  Function Name	: _ntgrPIMPortDisable
#
#  Description    	: This function is called to disable PIM  on port
#         
#  Usage          	: _ntgrPIMPortDisable <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrPIMPortDisable {switch_name if_list} {

	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }

      foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "no ip pim\r"
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
                           
#*******************************************************************************
#  Function Name	: _ntgrIPv6PIMPortDisable
#
#  Description    	: This function is called to disable IPv6 PIM  on port
#         
#  Usage          	: _ntgrIPv6PIMPortDisable <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrIPv6PIMPortDisable {switch_name if_list} {

	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }

      foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "no ipv6 pim\r"
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

#*******************************************************************************
#  Function Name	: _ntgrIGMPRoutingEnable
#
#  Description    : This function is called to enable IGMP on the Switch
#         
#  Usage          : _ntgrIGMPRoutingEnable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrIGMPRoutingEnable {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "ip igmp\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrMulticastRoutingDisable
#
#  Description    : This function is called to disable IGMP on Switch
#         
#  Usage          : _ntgrMulticastRoutingDisable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrIGMPRoutingDisable {switch_name} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "no ip igmp\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 
}

#*******************************************************************************
#  Function Name	: _ntgrIGMPRoutingPortEnable
#
#  Description    	: This function is called to enable IGMP on port
#         
#  Usage          	: _ntgrIGMPRoutingPortEnable <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrIGMPRoutingPortEnable {switch_name if_list} {

	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  
  foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "ip igmp\r"
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


#*******************************************************************************
#  Function Name	: _ntgrIGMPRoutingPortDisable
#
#  Description    	: This function is called to disable IGMP on port
#         
#  Usage          	: _ntgrIGMPRoutingPortDisable <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrIGMPRoutingPortDisable {switch_name if_list} {

	  set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   
  foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "no ip igmp\r"
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


#*******************************************************************************
#  Function Name	: _ntgrAddStaticMulticastRoute
#
#  Description    	: This function is called to add static multicast routes 
#         
#  Usage          	: _ntgrAddStaticMulticastRoute <switch_name> <address_list> 
#
#
#*******************************************************************************
proc _ntgrAddStaticMulticastRoute {switch_name address_list} {

	    set cnn_id [_get_switch_handle $switch_name]
    	set bCnn 1
    	if {[string first "exp" $cnn_id] == -1} {
        	Netgear_connect_switch $switch_name
        	set cnn_id [_get_switch_handle $switch_name]
        	set bCnn 0
    	}
    
	  expect -i $cnn_id -re "#"
   	exp_send -i $cnn_id "configure\r"
  	sleep 1
	
	foreach static_route $address_list {
	        set ip_address [lindex $static_route 0]
	        set subnet_mask [lindex $static_route 1]
	        set next_hop [lindex $static_route 2]
	        set prefrence [lindex $static_route 3]
         
	       
	        if { $prefrence == "default"} {
	               exp_send -i $cnn_id "ip mroute $ip_address $subnet_mask $next_hop\r"
	               sleep 1
	        } elseif {$prefrence<1 || $prefrence >255} {
	               Netgear_log_file "lib-static-ip.tcl" "Error:the prefrence must be 1~255!"
	               return
	        } else {
	 	             exp_send -i $cnn_id "ip mroute $ip_address $subnet_mask $next_hop $prefrence\r"
	 	             sleep 1
	  	}
	}
 	exp_send -i $cnn_id "exit\r"
 	
 	if {$bCnn == 0} {
        	Netgear_disconnect_switch $switch_name
    	}
}
#2*******************************************************************************
#  Function Name	: _ntgrDeleteStaticMulticastRoute
#
#  Description    	: This function is called to delete static multicast routes on switch
#         
#  Usage          	: _ntgrDeleteStaticMulticastRoute <switch_name> <address_list> 
#
#
#*******************************************************************************
proc _ntgrDeleteStaticMulticastRoute {switch_name address_list} {

	set cnn_id [_get_switch_handle $switch_name]
    	set bCnn 1
    	if {[string first "exp" $cnn_id] == -1} {
        	Netgear_connect_switch $switch_name
        	set cnn_id [_get_switch_handle $switch_name]
        	set bCnn 0
    	}

	 expect -i $cnn_id -re "#"
   exp_send -i $cnn_id "configure\r"
  sleep 1
	foreach static_route $address_list {
		set ip_address [lindex $static_route 0]
		set subnet_mask [lindex $static_route 1]
		exp_send -i $cnn_id "no ip mroute $ip_address $subnet_mask\r"
		sleep 1
	}
	exp_send -i $cnn_id "exit\r"
 	
 	if {$bCnn == 0} {
        	Netgear_disconnect_switch $switch_name
  }
}   

#*******************************************************************************
#
#  Function Name  : _ntgrCheckMulticastRoutingEntry
#
#  Description    : This function is used to check multicast routing entry exist or not.
#
#  Usage          : _ntgrCheckMulticastRoutingEntry <switch_name> <mroute_list>
#
#*******************************************************************************
proc _ntgrCheckMulticastRoutingEntry {switch_name mroute_list} {
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
	set ver [_ntgrGetVersionReleasebyConnectionID $connection_id]
	if {$ver >= 11}  {
	    set cmds "show ip mroute summary" 
	} else {
        set cmds "show ip mcast mroute summary" 
	}
	Netgear_disconnect_switch $switch_name
  	set retStr ""
    CALCheckExpect $switch_name $cmds retStr 
  
    set tmpList [split $retStr \n] 
    set mroute_exists 0
    set mroute_times 0
    set result 0

   foreach mroute $mroute_list {
   
     set sip  [string tolower [lindex $mroute 0]]
     set gip  [string tolower [lindex $mroute 1]]
     set protocol  [string tolower [lindex $mroute 2]] 
     set InPort  [string tolower [lindex $mroute 3]]
     set OutPort  [string tolower [lindex $mroute 4]] 
     incr mroute_times 1
     
     foreach iList $tmpList { 
      
      # delete "vlan" in log
       set n0 [lsearch $iList "vlan"]
       while { $n0 != -1} { 
          set iList [lreplace $iList $n0 $n0]  
          set n0 [lsearch $iList "vlan"]
        } 
       
       set value0   [string tolower [lindex $iList 0]]
       set value1   [string tolower [lindex $iList 1]]
       set value2   [string tolower [lindex $iList 2]]
       set value3   [string tolower [lindex $iList 3]]
  
     if {[string equal $value0 $sip] == 1 && [string equal $value1 $gip] == 1 && [string equal $value2 $protocol] == 1 && [string equal $value3 $InPort] == 1 } { 
                
                  set n1 [llength $iList] 
                  set n2 4
                    while {$n2 <= $n1} { 
                       set value4   [string tolower [lindex $iList $n2]] 
                       if { [string compare $OutPort $value4] == 0 } {
                            incr mroute_exists 1
                            break
                        }
                       incr n2
                     }
                     break
            } 
  	  }  
   }
   if { $mroute_exists == $mroute_times && $mroute_exists > 0} { 
       set result 1
   } 
    return $result
}    

#*******************************************************************************
#  Function Name	: _ntgrPIMSparseEnable
#
#  Description    : This function is called to enable PIM Sparse on the Switch
#         
#  Usage          : _ntgrPIMSparseEnable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrPIMSparseEnable {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "ip pim sparse\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrPIMSparseDisable
#
#  Description    : This function is called to disable Sparse on Switch
#         
#  Usage          : _ntgrPIMSparseDisable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrPIMSparseDisable {switch_name} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "no ip pim sparse\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    
}

#*******************************************************************************
#  Function Name	: _ntgrAddPIMRPCandidateOnPort
#
#  Description    : This function is called to add PIM RP Candidate on the Port
#         
#  Usage          : _ntgrAddPIMRPCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc _ntgrAddPIMRPCandidateOnPort {switch_name} {

    global rp_candidate
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    set cmdstr "ip pim rp-candidate"
    set port [keylget rp_candidate($switch_name) PIM_RP_CANDIDATE_PORT] 
    append  cmdstr " interface $port"
    set gip [keylget rp_candidate($switch_name) PIM_RP_CANDIDATE_GIP] 
    append  cmdstr " $gip"
    set mask [keylget rp_candidate($switch_name) PIM_RP_CANDIDATE_MASK] 
    append  cmdstr " $mask"
    
       
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "$cmdstr\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrDelPIMRPCandidateOnPort
#
#  Description    : This function is called to Delete PIM RP Candidate On Port 
#         
#  Usage          : _ntgrDelPIMRPCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc _ntgrDelPIMRPCandidateOnPort {switch_name} {

    global rp_candidate
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }  
    
    set cmdstr "no ip pim rp-candidate"
    set port [keylget rp_candidate($switch_name) PIM_RP_CANDIDATE_PORT] 
    append  cmdstr " interface $port"
    set gip [keylget rp_candidate($switch_name) PIM_RP_CANDIDATE_GIP] 
    append  cmdstr " $gip"
    set mask [keylget rp_candidate($switch_name) PIM_RP_CANDIDATE_MASK] 
    append  cmdstr " $mask"
    
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "$cmdstr\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    
} 

#*******************************************************************************
#  Function Name	: _ntgrAddPIMBSRCandidateOnPort
#
#  Description    : This function is called to Add PIM BSR Candidate On Port
#         
#  Usage          : _ntgrAddPIMBSRCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc _ntgrAddPIMBSRCandidateOnPort {switch_name} {
  
    global bsr_candidate
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    set cmdstr "ip pim bsr-candidate"
    set port [keylget bsr_candidate($switch_name) PIM_BSR_CANDIDATE_PORT] 
    append  cmdstr " interface $port"
    set len [keylget bsr_candidate($switch_name) PIM_BSR_CANDIDATE_HASH_MASK_LEN] 
    append  cmdstr " $len" 
	
	foreach ilist $bsr_candidate($switch_name) {
	    set exit_flag [lsearch $ilist PIM_BSR_CANDIDATE_PRIORITY]
		if {$exit_flag != -1} {
		     break
		}
	}
    if {$exit_flag == -1} {
	    keylset bsr_candidate($switch_name) PIM_BSR_CANDIDATE_PRIORITY       "0"
	}

    set priority [keylget bsr_candidate($switch_name) PIM_BSR_CANDIDATE_PRIORITY]
    append  cmdstr " $priority" 
	
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "$cmdstr\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrDelPIMBSRCandidateOnPort
#
#  Description    : This function is called to Delete PIM BSR Candidate On Port
#         
#  Usage          : _ntgrDelPIMBSRCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc _ntgrDelPIMBSRCandidateOnPort {switch_name} {

    global  bsr_candidate
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    set cmdstr "no ip pim bsr-candidate"
    set port [keylget bsr_candidate($switch_name) PIM_BSR_CANDIDATE_PORT] 
    append  cmdstr " interface $port"    
    
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "$cmdstr\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckMulticastStaticRoutingEntry
#
#  Description    : This function is used to check multicast static routing exist or not.
#
#  Usage          : _ntgrCheckMulticastStaticRoutingEntry <switch_name> <mroute_list>
#
#*******************************************************************************
proc _ntgrCheckMulticastStaticRoutingEntry {switch_name mroute_list} {
 
  Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]
	set ver [_ntgrGetVersionReleasebyConnectionID $connection_id]
	if {$ver >= 11}  {
	    set cmds "show ip mroute static" 
	} else {
        set cmds "show ip mcast mroute static" 
	}
	Netgear_disconnect_switch $switch_name
   
	set retStr ""
  CALCheckExpect $switch_name $cmds retStr 

  set tmpList [split $retStr \n] 
  set static_route_exists 0
  set static_route_times 0
  set result 0

   foreach mroute $mroute_list {
   
     set sip  [string tolower [lindex $mroute 0]]
     set mask  [string tolower [lindex $mroute 1]]
     set rpf  [string tolower [lindex $mroute 2]] 
     set preference  [string tolower [lindex $mroute 3]] 
     incr static_route_times 1
     
     foreach iList $tmpList {  
     set value0   [string tolower [lindex $iList 0]]
     set value1   [string tolower [lindex $iList 1]]
     set value2   [string tolower [lindex $iList 2]]
     set value3   [string tolower [lindex $iList 3]] 
         
     if {[string equal $value0 $sip] == 1 && [string equal $value1 $mask] == 1 && [string equal $value2 $rpf] == 1 && [string equal $value3 $preference] == 1} { 
                    incr static_route_exists 1
                    break
                  
            } 
  	  }  
   }

   if { $static_route_times == $static_route_exists && $static_route_exists > 0} { 
       set result 1
   } 
    return $result
}

#*******************************************************************************
#  Function Name	: _ntgrAddMulticastBoundaryOnPort
#
#  Description    	: This function is called to Add Multicast Boundary On Port 
#         
#  Usage          	: _ntgrAddMulticastBoundaryOnPort <switch_name> <boundary_list> 
#
#
#*******************************************************************************
proc _ntgrAddMulticastBoundaryOnPort {switch_name boundary_list} {

	    set cnn_id [_get_switch_handle $switch_name]
    	set bCnn 1
    	if {[string first "exp" $cnn_id] == -1} {
        	Netgear_connect_switch $switch_name
        	set cnn_id [_get_switch_handle $switch_name]
        	set bCnn 0
    	}
    
	  expect -i $cnn_id -re "#"
   	exp_send -i $cnn_id "configure\r"
  	sleep 1
	
	foreach boundary $boundary_list {
	        set port [lindex $boundary 0]
	        set ip [lindex $boundary 1]
	        set mask [lindex $boundary 2]
          exp_send -i $cnn_id "interface $port\r"
          sleep 1
          exp_send -i $cnn_id "ip mcast boundary $ip $mask\r"
	 	      sleep 1
          exp_send -i $cnn_id "exit\r"
          sleep 1
	}
 	exp_send -i $cnn_id "exit\r"
 	if {$bCnn == 0} {
        	Netgear_disconnect_switch $switch_name
    	}
}
#2*******************************************************************************
#  Function Name	: _ntgrDeleteMulticastBoundaryOnPort
#
#  Description    	: This function is called to delete Multicast Boundary On Port
#         
#  Usage          	: _ntgrDeleteMulticastBoundaryOnPort <switch_name> <boundary_list> 
#
#
#*******************************************************************************
proc _ntgrDeleteMulticastBoundaryOnPort {switch_name boundary_list} {

	   set cnn_id [_get_switch_handle $switch_name]
    	set bCnn 1
    	if {[string first "exp" $cnn_id] == -1} {
        	Netgear_connect_switch $switch_name
        	set cnn_id [_get_switch_handle $switch_name]
        	set bCnn 0
    	}

  	  expect -i $cnn_id -re "#"
     	exp_send -i $cnn_id "configure\r"
    	sleep 1
  	foreach boundary $boundary_list {
  		set port [lindex $boundary 0]
  		set ip [lindex $boundary 1]
      set mask [lindex $boundary 2]
      exp_send -i $cnn_id "interface $port\r"
      sleep 1
      exp_send -i $cnn_id "no ip mcast boundary $ip $mask\r"
	 	  sleep 1
      exp_send -i $cnn_id "exit\r"
      sleep 1
	}
	exp_send -i $cnn_id "exit\r"
 	
 	if {$bCnn == 0} {
        	Netgear_disconnect_switch $switch_name
    	}
}  

#*******************************************************************************
#  Function Name	: _ntgrRoutingDVMRPEnable
#
#  Description    : This function is called to Enable DVMRP on switch
#         
#  Usage          : _ntgrRoutingDVMRPEnable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrRoutingDVMRPEnable {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "ip dvmrp\r"
        after 200
        exp_send -i $connection_id "exit\r"
        after 200
    
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrRoutingDVMRPDisable
#
#  Description    : This function is called to Disable DVMRP on switch
#         
#  Usage          : _ntgrRoutingDVMRPDisable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrRoutingDVMRPDisable {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "no ip dvmrp\r"
        after 200
        exp_send -i $connection_id "exit\r"
        after 200

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrMulticastDVMRPPortEnable
#
#  Description    	: This function is called to Enable DVMRP on port
#         
#  Usage          	: _ntgrMulticastDVMRPPortEnable <switch_name> <if_list> 
#
#
#*******************************************************************************
proc _ntgrMulticastDVMRPPortEnable {switch_name if_list} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "ip dvmrp\r"
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


#*******************************************************************************
#  Function Name	: _ntgrMulticastDVMRPPortDisable
#
#  Description    	: This function is called to Disable DVMRP on port
#         
#  Usage          	: _ntgrMulticastDVMRPPortDisable <switch_name> <if_list> 
#
#
#*******************************************************************************
proc _ntgrMulticastDVMRPPortDisable {switch_name if_list} {

	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    	
       foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "no ip dvmrp\r"
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

#*******************************************************************************
#  Function Name	: _ntgrMulticastDVMRPPortDisable
#
#  Description    	: This function is called to Disable DVMRP on port
#         
#  Usage          	: _ntgrMulticastDVMRPPortDisable <switch_name> <if_list> 
#
#
#*******************************************************************************
proc _ntgrCheckMulticastDVMRPNeighbor {switch_name dvmrp_list} {
  
      set cmds "show ip dvmrp neighbor" 
    	set retStr ""
      ntgrCLILogin  $switch_name
      CALCheckExpect $switch_name $cmds retStr 
      set tmpList [split $retStr \n] 
      set bFlag 0


      set dvmrp_times 0
      set dvmrp_exist 0
      
    foreach dvmrp_entry $dvmrp_list {
   
       set tport  [string tolower [lindex $dvmrp_entry 0]]
       set tipaddr  [string tolower [lindex $dvmrp_entry 1]] 
       incr dvmrp_times 
     
      set n1 0 
      set i 0
      set j 0
      set k 0    
      foreach iList $tmpList {
    
            if {[regexp -nocase -- "Interface*" $iList]} {           
                      set port [lindex $iList end] 
                      if {[string equal -nocase $port $tport] == 1} {
                         set i $n1
                      }  
            }
              
           if {[regexp -nocase -- "Neighbor IP Address*" $iList]} {           
                      set neighbor [lindex $iList end] 
                      if {[string equal -nocase $neighbor $tipaddr] == 1} {
                         set j $n1
                      }  
           } 
                  
           if {[regexp -nocase -- "State*" $iList]} {           
                      set status [lindex $iList end] 
                      if {[string equal -nocase $status "Active"] == 1 && [expr $j - $i] == 1} {
                      set k $n1
                      incr dvmrp_exist
                      break
                }  
          }  
            set n1 [expr $n1 + 1]      
        }     
     
     }
      
     if {$dvmrp_exist == $dvmrp_times &&  $dvmrp_times > 0}  { 
        set bFlag 1
     }

  ntgrCLILogout  $switch_name
  Netgear_log_file "lib-routing-multicast.tcl" "dvmrp_exist is $dvmrp_exist dvmrp_times is $dvmrp_times"
  return $bFlag
}   

#*******************************************************************************
#  Function Name	: _ntgrSetMulticastDVMRPMetricOnPort
#
#  Description    : This function is called to set DVMRP metric on Port
#         
#  Usage          : _ntgrSetMulticastDVMRPMetricOnPort <switch_name> <port> <metric>
#
#
#*******************************************************************************
proc _ntgrSetMulticastDVMRPMetricOnPort {switch_name port metric} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "interface $port\r"
    sleep 1
   	exp_send -i $connection_id "ip dvmrp metric $metric\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  
}

#*******************************************************************************
#  Function Name	: _ntgrDelMulticastDVMRPMetricOnPort
#
#  Description    : This function is called to delete DVMRP metric on Port
#         
#  Usage          : _ntgrDelMulticastDVMRPMetricOnPort <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrDelMulticastDVMRPMetricOnPort {switch_name port} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "interface $port\r"
    sleep 1
   	exp_send -i $connection_id "no ip dvmrp metric\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  
}


#*******************************************************************************
#
#  Function Name  : _ntgrCheckDVMRPPruneEntry
#
#  Description    : This function is used to check dvmrp prune entry exist or not.
#
#  Usage          : _ntgrCheckDVMRPPruneEntry <switch_name> <dvmrp_list>
#
#*******************************************************************************
proc _ntgrCheckDVMRPPruneEntry {switch_name dvmrp_list} {

    set cmds "show ip dvmrp prune" 
  	set retStr ""
    CALCheckExpect $switch_name $cmds retStr 
  
    set tmpList [split $retStr \n] 
    set dvmrp_exists 0
    set dvmrp_times 0
    set result 0

   foreach dvmrp_entry $dvmrp_list {
   
     set gip  [string tolower [lindex $dvmrp_entry 0]]
     set sip  [string tolower [lindex $dvmrp_entry 1]]
     set smask  [string tolower [lindex $dvmrp_entry 2]]  
     incr dvmrp_times 1
     
     foreach iList $tmpList { 
       
       set value0   [string tolower [lindex $iList 0]]
       set value1   [string tolower [lindex $iList 1]]
       set value2   [string tolower [lindex $iList 2]]

  
     if {[string equal $value0 $gip] == 1 && [string equal $value1 $sip] == 1 && [string equal $value2 $smask] == 1 } {
                     incr dvmrp_exists 
                     break
            } 
  	  }  
   }

   if { $dvmrp_exists == $dvmrp_times && $dvmrp_exists > 0} { 
       set result 1
   } 
    return $result
}    


#*******************************************************************************
#  Function Name	: _ntgrCreateStaticRP
#
#  Description    : This function is called to Creates a static RP for the PIM router.
#         
#  Usage          : _ntgrCreateStaticRP <switch_name> <rp_ip> <group_addr> <group_mask> <override>
#
#
#*******************************************************************************
proc _ntgrCreateStaticRP {switch_name rp_ip group_addr group_mask override} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	
	if {$override == "enable"} {
   	    exp_send -i $connection_id "ip pim rp-address $rp_ip $group_addr $group_mask override\r"
   	    sleep 1
	} else {
	    exp_send -i $connection_id "ip pim rp-address $rp_ip $group_addr $group_mask\r"
   	    sleep 1
	}
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  
}


#*******************************************************************************
#  Function Name	: _ntgrDelStaticRP
#
#  Description    : This function is called to delete a static RP for the PIM router.
#         
#  Usage          : _ntgrDelStaticRP <switch_name> <rp_ip> <group_addr> <group_mask> <override>
#
#
#*******************************************************************************
proc _ntgrDelStaticRP {switch_name rp_ip group_addr group_mask override} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	
	if {$override == "enable"} {
   	    exp_send -i $connection_id "no ip pim rp-address $rp_ip $group_addr $group_mask override\r"
   	    sleep 1
	} else {
	    exp_send -i $connection_id "no ip pim rp-address $rp_ip $group_addr $group_mask\r"
   	    sleep 1
	}
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  
}

#*******************************************************************************
#  Function Name	: _ntgrCheckPIMBSRElectedTable
#
#  Description    	: This function is called to check PIM BSR Elected table
#         
#  Usage          	: _ntgrCheckPIMBSRElectedTable <switch_name> <bsr_addr> 
#
#
#*******************************************************************************
proc _ntgrCheckPIMBSRElectedTable {switch_name bsr_addr} {
    set bFlag 0
    set cmds "show ip pim bsr-router elected" 
    set retStr ""
    ntgrCLILogin  $switch_name
    CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    
	foreach iList $tmpList {
        if {[regexp -nocase -- ".*BSR Address*" $iList]} {           
            set real_addr [lindex $iList end] 
	        Netgear_log_file "_ntgrCheckPIMBSRElectedTable" " Real BSR Address on BSR Elected Table is: $real_addr . Expect BSR Address is: $bsr_addr ."
            if {[string equal -nocase $real_addr $bsr_addr] == 1} {
                set bFlag 1
            }  
        }
    }
    
	ntgrCLILogout  $switch_name

    return $bFlag

}


#*******************************************************************************
#
#  Function Name  : _ntgrCheckPIMNeighbors
#
#  Description    : This function is used to check router's PIM neighbors on the interface.
#
#  Usage          : _ntgrCheckPIMNeighbors <switch_name> <neighbor_list>
#
#*******************************************************************************
proc _ntgrCheckPIMNeighbors {switch_name neighbor_list} {

    set cmds "show ip pim neighbor" 
	set retStr ""
    ntgrCLILogin  $switch_name
    CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    ntgrCLILogout  $switch_name
	
    set result 1

    foreach mroute $neighbor_list {
        set expect_nei  [string tolower [lindex $mroute 0]]
        set expect_inter  [string tolower [lindex $mroute 1]]
        
		set exit_flag 0
        foreach iList $tmpList {
		    
	        set n0 [lsearch $iList "vlan"]
			set n1 [lsearch $iList $expect_nei]
            while { $n0 != -1} { 
                set iList [lreplace $iList $n0 $n0]  
                set n0 [lsearch $iList "vlan"]
            }
            set real_neighbor   [string tolower [lindex $iList 0]]
            set real_inter   [string tolower [lindex $iList 1]]
            set real_uptime   [string tolower [lindex $iList 2]]
            set real_expiry   [string tolower [lindex $iList 3]] 
	        set real_priority   [string tolower [lindex $iList 4]]
            if {[string equal $real_neighbor $expect_nei] == 1 && [string equal $real_inter $expect_inter] == 1} { 
                set exit_flag 1
                break      
            } elseif {$n1 !=-1 } {
				set exit_flag 1
			}
  	  }
	  if {$exit_flag == 0} {
	      Netgear_log_file "_ntgrCheckPIMNeighbors" "ERROR: Expect Neighbor:$expect_nei is not exist." 
		  set result 0
	  }
   }
   
   return $result
}

#*******************************************************************************
#  Function Name	: _ntgrCheckPIMRPAddressTable
#
#  Description    	: This function is called to check the PIM RP Address for a specific group.
#         
#  Usage          	: _ntgrCheckPIMRPAddressTable <switch_name> <group_ip> <rp_addr> 
#
#
#*******************************************************************************
proc _ntgrCheckPIMRPAddressTable {switch_name group_ip rp_addr} {
    set bFlag 0
    set cmds "show ip pim rp-hash $group_ip" 
    set retStr ""
    ntgrCLILogin  $switch_name
    CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    
	foreach iList $tmpList {
        if {[regexp -nocase -- ".*RP Address*" $iList]} {           
            set real_addr [lindex $iList end] 
	        Netgear_log_file "_ntgrCheckPIMRPAddressTable" " Real RP Address on PIM RP Address Table is: $real_addr . Expect RP Address is: $rp_addr ."
            if {[string equal -nocase $real_addr $rp_addr] == 1} {
                set bFlag 1
            }  
        }
    }

	ntgrCLILogout  $switch_name

    return $bFlag

}

#*******************************************************************************
#  Function Name	: _ntgrCheckPIMSMMode
#
#  Description    	: This function is called to check the PIM SM global mode 
#         
#  Usage          	: _ntgrCheckPIMSMMode <switch_name>
#
#
#*******************************************************************************
proc _ntgrCheckPIMSMMode {switch_name} {
    set bFlag 0
    set cmds "show ip pim" 
    set retStr ""
    ntgrCLILogin  $switch_name
    CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    
	foreach iList $tmpList {
        if {[regexp -nocase -- ".*PIM Mode*" $iList]} {           
            set real_mode [lindex $iList end] 
	        Netgear_log_file "_ntgrCheckPIMSMMode" " Real PIM SIM Mode is: $real_mode ."
            if {[string equal -nocase $real_mode "Sparse"] == 1} {
                set bFlag 1
            }  
        }
    }

	ntgrCLILogout  $switch_name

    return $bFlag

}


#*******************************************************************************
#  Function Name	: _ntgrCheckPIMSMInterfaceStatus
#
#  Description    	: This function is called to check the PIM SM global mode 
#         
#  Usage          	: _ntgrCheckPIMSMInterfaceStatus <switch_name> <port> <neighbor_count> <des_router>
#
#
#*******************************************************************************
proc _ntgrCheckPIMSMInterfaceStatus {switch_name port neighbor_count des_router} {
    set bFlag 0
    set cmds "show ip pim interface $port"
    set retStr ""
    ntgrCLILogin  $switch_name
    CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    set result 0
	foreach iList $tmpList {
        if {[regexp -nocase -- "Mode*" $iList]} {           
            set real_mode [lindex $iList end] 
	        Netgear_log_file "_ntgrCheckPIMSMInterfaceStatus" " Real PIM SIM Mode is: $real_mode ."
            if {[string equal -nocase $real_mode "Sparse"] == 1} {
                incr result 1
            }  
        }
		if {[regexp -nocase -- "Neighbor Count*" $iList]} {           
            set real_count [lindex $iList end] 
	        Netgear_log_file "_ntgrCheckPIMSMInterfaceStatus" " Real Neighbor Count is: $real_count ."
            if {$real_count == $neighbor_count} {
                incr result 1
            }  
        }
		if {[regexp -nocase -- "Designated Router*" $iList]} {           
            set real_router [lindex $iList end] 
	        Netgear_log_file "_ntgrCheckPIMSMInterfaceStatus" " Real Designated Router is: $real_router ."
             if {[string equal -nocase $real_router $des_router] == 1} {
                incr result 1
            }  
        }
    }

	ntgrCLILogout  $switch_name
    if {$result == 3} {
	    set bFlag 1
	}
	
    return $bFlag

}


#*******************************************************************************
#  Function Name	: _ntgrCheckPIMSMRPMapping
#
#  Description    	: This function is called to check the PIM SM rp mapping
#         
#  Usage          	: _ntgrCheckPIMSMRPMapping <switch_name> <neighbor_list>
#
#
#*******************************************************************************
proc _ntgrCheckPIMSMRPMapping {switch_name neighbor_list} {
    set bFlag 1
	
    set cmds "show ip pim rp mapping"
    set retStr ""
    ntgrCLILogin  $switch_name
    CALCheckExpect $switch_name $cmds retStr 
	regsub -all {..More..\s*or\s*\(q\)uit\s*} $retStr "" test
   
    set tmpList [split $test \n] 
   
	foreach mroute $neighbor_list {
        set expect_rp  [string tolower [lindex $mroute 0]]
        set expect_group  [string tolower [lindex $mroute 1]]
	    
		set count [expr {[llength $tmpList] -  1}]
	    for {set i 0} {$i < $count } {incr i} {
	         set iList [lindex $tmpList $i]
			 if {[regexp -nocase -- "RP Address*" $iList]} {
			     set real_rp [lindex $iList end]
				 if {[string equal -nocase $real_rp $expect_rp] == 1} {
				     set groupList [lindex $tmpList [expr $i + 1]]
					 set real_group [lindex $groupList end]
					 if {[string equal -nocase $real_group $expect_group] != 1} {
					      Netgear_log_file "_ntgrCheckPIMSMRPMapping" " Error: Real Group: $real_group Expect Group: $expect_group ."
						  set bFlag 0
					 } else {
					      break
					 }
				 }
			 }
		 
	    }
	}	
	
	ntgrCLILogout  $switch_name
    
    return $bFlag

}

#*******************************************************************************
#  Function Name	: _ntgrAddIPv6StaticMulticastRoute
#
#  Description    	: This function is called to add ipv6 static multicast routes 
#         
#  Usage          	: _ntgrAddStaticMulticastRoute <switch_name> <address_list> 
#
#
#*******************************************************************************
proc _ntgrAddIPv6StaticMulticastRoute {switch_name address_list} {

	    set cnn_id [_get_switch_handle $switch_name]
    	set bCnn 1
    	if {[string first "exp" $cnn_id] == -1} {
        	Netgear_connect_switch $switch_name
        	set cnn_id [_get_switch_handle $switch_name]
        	set bCnn 0
    	}
    
	  expect -i $cnn_id -re "#"
   	exp_send -i $cnn_id "configure\r"
  	sleep 1
	
	foreach static_route $address_list {
	        set prefix [lindex $static_route 0]
	        set next_hop [lindex $static_route 1]
            set next_int [lindex $static_route 2]
	        set prefrence [lindex $static_route 3]
         
	       
	       if {$prefrence<1 || $prefrence >255} {
	               Netgear_log_file "lib-static-ip.tcl" "Error:the prefrence must be 1~255!"
	               return
	        } else {
	 	             exp_send -i $cnn_id "ipv6 mroute $prefix $next_hop $next_int $prefrence\r"
	 	             sleep 1
	  	}
	}
 	exp_send -i $cnn_id "exit\r"
 	
 	if {$bCnn == 0} {
        	Netgear_disconnect_switch $switch_name
    	}
}
#2*******************************************************************************
#  Function Name	: _ntgrDeleteIPv6StaticMulticastRoute
#
#  Description    	: This function is called to delete ipv6 static multicast routes on switch
#         
#  Usage          	: _ntgrDeleteStaticMulticastRoute <switch_name> <address_list> 
#
#
#*******************************************************************************
proc _ntgrDeleteIPv6StaticMulticastRoute {switch_name prefixlist} {

	set cnn_id [_get_switch_handle $switch_name]
    	set bCnn 1
    	if {[string first "exp" $cnn_id] == -1} {
        	Netgear_connect_switch $switch_name
        	set cnn_id [_get_switch_handle $switch_name]
        	set bCnn 0
    	}

	 expect -i $cnn_id -re "#"
   exp_send -i $cnn_id "configure\r"
  sleep 1
		exp_send -i $cnn_id "no ipv6 mroute $prefixlist \r"
      sleep 1
	exp_send -i $cnn_id "exit\r"
 	
 	if {$bCnn == 0} {
        	Netgear_disconnect_switch $switch_name
  }
} 

#*******************************************************************************
#
#  Function Name  : _ntgrCheckIPv6MulticastRoutingEntry
#
#  Description    : This function is used to check ipv6 multicast routing entry exist or not.
#
#  Usage          : _ntgrCheckIPv6MulticastRoutingEntry <switch_name> <sip gip protocol InPort OutPort>
#
#*******************************************************************************

proc _ntgrCheckIPv6MulticastRoutingEntry {switch_id sip gip protocol InPort OutPort} {

    set cnn_id [_get_switch_handle $switch_id]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_id
        set cnn_id [_get_switch_handle $switch_id]
        set bCnn 0
    }

	set match {}
	
	set result ""
    set return_value 0
    if { $InPort=="" } {
   	         exp_send -i $cnn_id "show ipv6 mroute group $gip summary \r"   
          } else {
             exp_send -i $cnn_id "show ipv6 mroute source $sip summary \r"
          }
    expect -i $cnn_id "show ipv6 mroute"
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

	set regSWMAC "$sip\[ ]+$gip\[ ]+$protocol\[ ]+$InPort\[ ]+$OutPort$"
	set return_value [regexp -line -nocase $regSWMAC $result match]
	
	Netgear_log_file "Function: _ntgrCheckIPv6MulticastRoutingEntry " "match=$match"

	
	return $return_value
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckIPv6StaticMulticastRouteEntry
#
#  Description    : This function is used to check ipv6 static multicast route entry exist or not.
#
#  Usage          : _ntgrCheckIPv6StaticMulticastRouteEntry <switch_name> <sip prefixlen rpfip rpfint preference>
#
#*******************************************************************************

proc _ntgrCheckIPv6StaticMulticastRouteEntry {switch_id sip prefixlen rpfip rpfint preference} {

    set cnn_id [_get_switch_handle $switch_id]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_id
        set cnn_id [_get_switch_handle $switch_id]
        set bCnn 0
    }

	set match {}
	set str1 {}
  set str2 {}
	set str3 {}
	set str4 {}
	set result ""
    set return_value 0
   	exp_send -i $cnn_id "show ipv6 mroute static $sip \r"
    expect -i $cnn_id "show ipv6 mroute static"
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
  set prefix $sip/$prefixlen
	set regSWMAC "Source\\s+Address.*($prefix)"
  set return_value [regexp -line -nocase $regSWMAC $result match str1]
  
	Netgear_log_file "Function: _ntgrCheckIPv6StaticMulticastRouteEntry " "prefix=$str1"

	set regSWMAC "RPF\\s+Address.*($rpfip)"
  set return_value [expr $return_value && [regexp -line -nocase $regSWMAC $result match str2]]
  
	Netgear_log_file "Function: _ntgrCheckIPv6StaticMulticastRouteEntry " "RPF address=$str2"
  
  if {$rpfint!=""} {
	set regSWMAC "Interface.*($rpfint)"
  set return_value [expr $return_value && [regexp -line -nocase $regSWMAC $result match str3]]
  
	Netgear_log_file "Function: _ntgrCheckIPv6StaticMulticastRouteEntry " "RPF interface=$str3"  
  }
 
 	set regSWMAC "Preference.*($preference)"
  set return_value [expr $return_value && [regexp -line -nocase $regSWMAC $result match str4]]
  
	Netgear_log_file "Function: _ntgrCheckIPv6StaticMulticastRouteEntry " "Preference=$str4"
   
	return $return_value
} 


#*******************************************************************************
#
#  Function Name  : _ntgrIPv6MulticastClearMroute
#
#  Description    : This function is called to clear ipv6 mroute table
#                   switch.
#
#  Usage          : _ntgrIPv6MulticastClearMroute <switch_name>
#
#*******************************************************************************
proc _ntgrIPv6MulticastClearMroute {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 20
    exp_send -i $cnn_id "clear ipv6 mroute *\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "_ntgrIPv6MulticastClearMroute" "clear ipv6 mroute table on switcher"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#  Function Name	: _ntgrIPv6PIMSparseEnable
#
#  Description    : This function is called to enable ipv6 PIM Sparse on the Switch
#         
#  Usage          : _ntgrIPv6PIMSparseEnable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrIPv6PIMSparseEnable {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "ipv6 pim sparse\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrIPv6PIMSparseDisable
#
#  Description    : This function is called to disable ipv6 Sparse on Switch
#         
#  Usage          : _ntgrIPv6PIMSparseDisable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrIPv6PIMSparseDisable {switch_name} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "no ipv6 pim sparse\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    
}

#*******************************************************************************
#  Function Name	: _ntgrAddIPv6PIMRPCandidateOnPort
#
#  Description    : This function is called to add ipv6 PIM RP Candidate on the Port
#         
#  Usage          : _ntgrAddIPv6PIMRPCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc _ntgrAddIPv6PIMRPCandidateOnPort {switch_name} {

    global rp_candidate
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    set cmdstr "ipv6 pim rp-candidate"
    set port [keylget rp_candidate($switch_name) PIM_RP_CANDIDATE_PORT] 
    append  cmdstr " interface $port"
    set gip [keylget rp_candidate($switch_name) PIM_RP_CANDIDATE_GIP] 
    append  cmdstr " $gip"
    
       
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "$cmdstr\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrDelIPv6PIMRPCandidateOnPort
#
#  Description    : This function is called to Delete ipv6 PIM RP Candidate On Port 
#         
#  Usage          : _ntgrDelIPv6PIMRPCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc _ntgrDelIPv6PIMRPCandidateOnPort {switch_name} {

    global rp_candidate
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }  
    
    set cmdstr "no ipv6 pim rp-candidate"
    set port [keylget rp_candidate($switch_name) PIM_RP_CANDIDATE_PORT] 
    append  cmdstr " interface $port"
    set gip [keylget rp_candidate($switch_name) PIM_RP_CANDIDATE_GIP] 
    append  cmdstr " $gip"
    
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "$cmdstr\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    
} 

#*******************************************************************************
#  Function Name	: _ntgrAddIPv6PIMBSRCandidateOnPort
#
#  Description    : This function is called to Add ipv6 PIM BSR Candidate On Port
#         
#  Usage          : _ntgrAddIPv6PIMBSRCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc _ntgrAddIPv6PIMBSRCandidateOnPort {switch_name} {
  
    global bsr_candidate
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    set cmdstr "ipv6 pim bsr-candidate"
    set port [keylget bsr_candidate($switch_name) PIM_BSR_CANDIDATE_PORT] 
    append  cmdstr " interface $port"
    set len [keylget bsr_candidate($switch_name) PIM_BSR_CANDIDATE_HASH_MASK_LEN] 
    append  cmdstr " $len" 
	
	foreach ilist $bsr_candidate($switch_name) {
	    set exit_flag [lsearch $ilist PIM_BSR_CANDIDATE_PRIORITY]
		if {$exit_flag != -1} {
		     break
		}
	}
    if {$exit_flag == -1} {
	    keylset bsr_candidate($switch_name) PIM_BSR_CANDIDATE_PRIORITY       "0"
	}

    set priority [keylget bsr_candidate($switch_name) PIM_BSR_CANDIDATE_PRIORITY]
    append  cmdstr " $priority" 
	
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "$cmdstr\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrDelIPv6PIMBSRCandidateOnPort
#
#  Description    : This function is called to Delete ipv6 PIM BSR Candidate On Port
#         
#  Usage          : _ntgrDelIPv6PIMBSRCandidateOnPort <switch_name> 
#
#
#*******************************************************************************
proc _ntgrDelIPv6PIMBSRCandidateOnPort {switch_name} {

    global  bsr_candidate
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    set cmdstr "no ipv6 pim bsr-candidate"
    set port [keylget bsr_candidate($switch_name) PIM_BSR_CANDIDATE_PORT] 
    append  cmdstr " interface $port"    
    
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "$cmdstr\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    
}

#*******************************************************************************
#  Function Name	: _ntgrCreateIPv6PIMSMStaticRP
#
#  Description    : This function is called to Creates a static RP for the ipv6 PIM router.
#         
#  Usage          : _ntgrCreateStaticRP <switch_name> <rp_ip> <group_prefix> <override>
#
#
#*******************************************************************************
proc _ntgrCreateIPv6PIMSMStaticRP {switch_name rp_ipv6 group_prefix override} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	
	if {$override == "enable"} {
   	    exp_send -i $connection_id "ipv6 pim rp-address $rp_ipv6 $group_prefix override\r"
   	    sleep 1
	} else {
	    exp_send -i $connection_id "ipv6 pim rp-address $rp_ipv6 $group_prefix\r"
   	    sleep 1
	}
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  
}

#*******************************************************************************
#  Function Name	: _ntgrDelIPv6PIMSMStaticRP
#
#  Description    : This function is called to delete a static RP for IPV6 the PIM router.
#         
#  Usage          : _ntgrDelIPv6PIMSMStaticRP <switch_name> <rp_ipv6> <group_prefix>
#
#
#*******************************************************************************
proc _ntgrDelIPv6PIMSMStaticRP {switch_name rp_ipv6 group_prefix} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	

	    exp_send -i $connection_id "no ipv6 pim rp-address $rp_ipv6 $group_prefix\r"
   	    sleep 1

   	exp_send -i $connection_id "exit\r"
   	sleep 1
    exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigureLastMemberQueryCount
#
#  Description    : This function is called to clear configure Last Member Query Count for interface
#                   switch.
#
#  Usage          : _ntgrConfigureLastMemberQueryCount <switch_name> <interface> <count>
#
#*******************************************************************************
proc _ntgrConfigureLastMemberQueryCount {switch_name interface count} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 20
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    sleep 1
    exp_send -i $cnn_id "interface $interface\r"
    sleep 1
    exp_send -i $cnn_id "ip igmp last-member-query-count $count\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"	
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigureIGMPVersion
#
#  Description    : This function is called to clear configure IGMP version for interface
#                   switch.
#
#  Usage          : _ntgrConfigureIGMPVersion <switch_name> <interface> <version>
#
#*******************************************************************************
proc _ntgrConfigureIGMPVersion {switch_name interface version} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 20
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    sleep 1
    exp_send -i $cnn_id "interface $interface\r"
    sleep 1
    exp_send -i $cnn_id "ip igmp version $version\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"	
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigureLastMemberQueryInterval
#
#  Description    : This function is called to clear configure Last Member Query Interval for interface
#                   switch.
#
#  Usage          : _ntgrConfigureLastMemberQueryInterval <switch_name> <interface> <interval>
#
#*******************************************************************************
proc _ntgrConfigureLastMemberQueryInterval {switch_name interface interval} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 20
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    sleep 1
    exp_send -i $cnn_id "interface $interface\r"
    sleep 1
    exp_send -i $cnn_id "ip igmp last-member-query-interval $interval\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"	
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigureQueryMaxResponseTime
#
#  Description    : This function is called to clear configure Query Max Response Time for interface
#                   switch.
#
#  Usage          : _ntgrConfigureQueryMaxResponseTime <switch_name> <interface> <time>
#
#*******************************************************************************
proc _ntgrConfigureQueryMaxResponseTime {switch_name interface time} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 20
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    sleep 1
    exp_send -i $cnn_id "interface $interface\r"
    sleep 1
    exp_send -i $cnn_id "ip igmp query-max-response-time  $time\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"	
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigureQueryInterval
#
#  Description    : This function is called to clear configure Query Interval for interface
#                   switch.
#
#  Usage          : _ntgrConfigureQueryInterval <switch_name> <interface> <interval>
#
#*******************************************************************************
proc _ntgrConfigureQueryInterval {switch_name interface interval} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 20
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    sleep 1
    exp_send -i $cnn_id "interface $interface\r"
    sleep 1
    exp_send -i $cnn_id "ip igmp query-interval $interval\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"	
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigureStartupQueryCount
#
#  Description    : This function is called to clear configure Startup Query Count for interface
#                   switch.
#
#  Usage          : _ntgrConfigureStartupQueryCount <switch_name> <interface> <count>
#
#*******************************************************************************
proc _ntgrConfigureStartupQueryCount {switch_name interface count} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 20
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    sleep 1
    exp_send -i $cnn_id "interface $interface\r"
    sleep 1
    exp_send -i $cnn_id "ip igmp startup-query-count $count\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"	
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigureStartupQueryInterval
#
#  Description    : This function is called to clear configure Startup Query Interval for interface
#                   switch.
#
#  Usage          : _ntgrConfigureStartupQueryInterval <switch_name> <interface> <interval>
#
#*******************************************************************************
proc _ntgrConfigureStartupQueryInterval {switch_name interface interval} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 20
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    sleep 1
    exp_send -i $cnn_id "interface $interface\r"
    sleep 1
    exp_send -i $cnn_id "ip igmp startup-query-interval $interval\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"	
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigureIGMPProxy
#
#  Description    : This function is called to clear configure IGMP Proxy for interface
#                   switch.
#
#  Usage          : _ntgrConfigureIGMPProxy <switch_name> <interface>
#
#*******************************************************************************
proc _ntgrConfigureIGMPProxy {switch_name interface} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 20
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    sleep 1
    exp_send -i $cnn_id "interface $interface\r"
    sleep 1
    exp_send -i $cnn_id "ip igmp-proxy\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"	
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALDeleteIGMPProxy
#
#  Description    : This function is called to clear delete IGMP Proxy for interface
#                   switch.
#
#  Usage          : CALDeleteIGMPProxy <switch_name> <interface>
#
#*******************************************************************************
proc CALDeleteIGMPProxy {switch_name interface} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 20
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    sleep 1
    exp_send -i $cnn_id "interface $interface\r"
    sleep 1
    exp_send -i $cnn_id "no ip igmp-proxy\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"	
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#  Function Name	: _ntgrCfgIPv6PIMSSM
#
#  Description    : This function is called to configure ipv6 PIM ssm range on the Switch
#         
#  Usage          : _ntgrCfgIPv6PIMSSM <switch_name> <range>
#
#
#*******************************************************************************
proc _ntgrCfgIPv6PIMSSM {switch_name range} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "ipv6 pim ssm $range\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}


#*******************************************************************************
#  Function Name	: _ntgrDelIPv6PIMSSM
#
#  Description    : This function is called to delete ipv6 PIM ssm range on the Switch
#         
#  Usage          : _ntgrCfgIPv6PIMSSM <switch_name> <range>
#
#
#*******************************************************************************
proc _ntgrDelIPv6PIMSSM {switch_name range} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "no ipv6 pim ssm $range\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}


#*******************************************************************************
#  Function Name	: _ntgrCfgIPv6PIMHelloInterval
#
#  Description    	: This function is called to configure ipv6 PIM hello interval on port of  Switch
#         
#  Usage          	: _ntgrCfgIPv6PIMHelloInterval <switch_name> <port> <interval>
#
#
#*******************************************************************************
proc _ntgrCfgIPv6PIMHelloInterval {switch_name if_list interval} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    

   foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "ipv6 pim hello-interval $interval\r"
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

#*******************************************************************************
#  Function Name	: _ntgrDelIPv6PIMHelloInterval
#
#  Description    	: This function is called to delete ipv6 PIM hello interval on port of  Switch
#         
#  Usage          	: _ntgrDelIPv6PIMHelloInterval <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrDelIPv6PIMHelloInterval {switch_name if_list} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    

   foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "no ipv6 pim hello-interval\r"
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


#*******************************************************************************
#  Function Name	: _ntgrIPv6PIMBsrBorderEnable
#
#  Description    	: This function is called to enable ipv6 PIM BSR border on port of  Switch
#         
#  Usage          	: _ntgrIPv6PIMBsrBorderEnable <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrIPv6PIMBsrBorderEnable {switch_name if_list} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    

   foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "ipv6 pim bsr-border\r"
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

#*******************************************************************************
#  Function Name	: _ntgrIPv6PIMBsrBorderDisable
#
#  Description    	: This function is called to disable ipv6 PIM BSR border on port of  Switch
#         
#  Usage          	: _ntgrIPv6PIMBsrBorderDisable <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrIPv6PIMBsrBorderDisable {switch_name if_list} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    

   foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "no ipv6 pim bsr-border\r"
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

#*******************************************************************************
#  Function Name	: _ntgrCfgIPv6PIMPruneInterval
#
#  Description    	: This function is called to configure ipv6 PIM prune interval on port of  Switch
#         
#  Usage          	: _ntgrCfgIPv6PIMPruneInterval <switch_name> <port> <interval>
#
#
#*******************************************************************************
proc _ntgrCfgIPv6PIMPruneInterval {switch_name if_list interval} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    

   foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "ipv6 pim join-prune-interval $interval\r"
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

#*******************************************************************************
#  Function Name	: _ntgrDelIPv6PIMPruneInterval
#
#  Description    	: This function is called to delete ipv6 PIM prune-join interval on port of  Switch
#         
#  Usage          	: _ntgrDelIPv6PIMPruneInterval <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrDelIPv6PIMPruneInterval {switch_name if_list} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    

   foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "no ipv6 pim join-prune-interval\r"
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

#*******************************************************************************
#  Function Name	: _ntgrCheckIPv6PIMBSRElectedTable
#
#  Description    	: This function is called to check ipv6 PIM BSR Elected table
#         
#  Usage          	: _ntgrCheckIPv6PIMBSRElectedTable <switch_name> <bsr_addr> 
#
#
#*******************************************************************************
proc _ntgrCheckIPv6PIMBSRElectedTable {switch_name bsr_addr} {
    set bFlag 0
    set cmds "show ipv6 pim bsr-router elected" 
    set retStr ""
    ntgrCLILogin  $switch_name
    CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    
	foreach iList $tmpList {
        if {[regexp -nocase -- ".*BSR Address*" $iList]} {           
            set real_addr [lindex $iList end] 
	        Netgear_log_file "_ntgrCheckIPv6PIMBSRElectedTable" " Real BSR Address on BSR Elected Table is: $real_addr . Expect BSR Address is: $bsr_addr ."
            if {[string equal -nocase $real_addr $bsr_addr] == 1} {
                set bFlag 1
            }  
        }
    }
    
	ntgrCLILogout  $switch_name

    return $bFlag

}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckIPv6PIMNeighbors
#
#  Description    : This function is used to check router's ipv6 PIM neighbors on the interface.
#
#  Usage          : _ntgrCheckIPv6PIMNeighbors <switch_name> <neighbor_list>
#
#*******************************************************************************
proc _ntgrCheckIPv6PIMNeighbors {switch_name neighbor_list} {

    set cmds "show ipv6 pim neighbor" 
	set retStr ""
    ntgrCLILogin  $switch_name
    CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    ntgrCLILogout  $switch_name
	
    set result 1

    foreach mroute $neighbor_list {
        set expect_nei  [string tolower [lindex $mroute 0]]
        set expect_inter  [string tolower [lindex $mroute 1]]
        
		set exit_flag 0
        foreach iList $tmpList {
		    
	        set n0 [lsearch $iList "vlan"]
            while { $n0 != -1} { 
                set iList [lreplace $iList $n0 $n0]  
                set n0 [lsearch $iList "vlan"]
            } 
            set real_neighbor   [string tolower [lindex $iList 0]]
            set real_inter   [string tolower [lindex $iList 1]]
            set real_uptime   [string tolower [lindex $iList 2]]
            set real_expiry   [string tolower [lindex $iList 3]] 
	        set real_priority   [string tolower [lindex $iList 4]]
            if {[string equal $real_neighbor $expect_nei] == 1 && [string equal $real_inter $expect_inter] == 1} { 
                set exit_flag 1
                break      
            } 
  	  }
	  
	  if {$exit_flag == 0} {
	      Netgear_log_file "_ntgrCheckIPv6PIMNeighbors" "ERROR: Expect Neighbor:$expect_nei is not exist." 
		  set result 0
	  }
   }
   
   return $result
}


#*******************************************************************************
#
#  Function Name  : _ntgrCheckIPv6MulticastSSMGroup
#
#  Description    : This function is used to check ipv6 multicast ssm group exist or not.
#
#  Usage          : _ntgrCheckIPv6MulticastSSMGroup <switch_name> <ssmgroup>
#
#*******************************************************************************

proc _ntgrCheckIPv6MulticastSSMGroup {switch_id ssmgroup} {

    set cnn_id [_get_switch_handle $switch_id]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_id
        set cnn_id [_get_switch_handle $switch_id]
        set bCnn 0
    }

	set match {}
	
	set result ""
    set return_value 0
   	exp_send -i $cnn_id "show ipv6 pim ssm\r"
    expect -i $cnn_id "show ipv6 pim ssm"
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

	set regSWMAC "$ssmgroup$"
	set return_value [regexp -line -nocase $regSWMAC $result match]
	
	Netgear_log_file "Function: _ntgrCheckIPv6MulticastSSMGroup " "match=$match"

	
	return $return_value
}

#*******************************************************************************
#  Function Name	: _ntgrCheckIPv6PIMSMRPMapping
#
#  Description    	: This function is called to check the ipv6 PIM SM rp mapping
#         
#  Usage          	: _ntgrCheckIPv6PIMSMRPMapping <switch_name> <neighbor_list>
#
#
#*******************************************************************************
proc _ntgrCheckIPv6PIMSMRPMapping {switch_name neighbor_list} {
    set bFlag 0
	
    set cmds "show ipv6 pim rp mapping"
    set retStr ""
    ntgrCLILogin  $switch_name
    CALCheckExpect $switch_name $cmds retStr 
	regsub -all {..More..\s*or\s*\(q\)uit\s*} $retStr "" test
   
    set tmpList [split $test \n] 
   
	foreach mroute $neighbor_list {
        set expect_rp  [string tolower [lindex $mroute 0]]
        set expect_group  [string tolower [lindex $mroute 1]]
	    
		set count [expr {[llength $tmpList] -  1}]
	    for {set i 0} {$i < $count } {incr i} {
	         set iList [lindex $tmpList $i]
			 if {[regexp -nocase -- "RP Address*" $iList]} {
			     set real_rp [lindex $iList end]
				 if {[string equal -nocase $real_rp $expect_rp] == 1} {
				     set groupList [lindex $tmpList [expr $i + 1]]
					 set real_group [lindex $groupList end]
					 if {[string equal -nocase $real_group $expect_group] != 1} {
					      Netgear_log_file "_ntgrCheckIPv6PIMSMRPMapping" " Error: Real Group: $real_group Expect Group: $expect_group ."
						  set bFlag 0
					 } else {
					      set bFlag [expr $bFlag+1]
					 }
				 }
			 }
		 
	    }
	}	
	
	ntgrCLILogout  $switch_name
    
    return $bFlag

}


#*******************************************************************************
#  Function Name	: _ntgrCheckIPv6PIMSMMode
#
#  Description    	: This function is called to check the ipv6 PIM SM global mode 
#         
#  Usage          	: _ntgrCheckIPv6PIMSMMode <switch_name>
#
#
#*******************************************************************************
proc _ntgrCheckIPv6PIMSMMode {switch_name} {
    set bFlag 0
    set cmds "show ipv6 pim" 
    set retStr ""
    ntgrCLILogin  $switch_name
    CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    
	foreach iList $tmpList {
        if {[regexp -nocase -- ".*PIM Mode*" $iList]} {           
            set real_mode [lindex $iList end] 
	        Netgear_log_file "_ntgrCheckIPv6PIMSMMode" " Real PIM SIM Mode is: $real_mode ."
            if {[string equal -nocase $real_mode "Sparse"] == 1} {
                set bFlag 1
            }  
        }
    }

	ntgrCLILogout  $switch_name

    return $bFlag

}


#*******************************************************************************
#  Function Name	: _ntgrCheckIPv6PIMSMInterfaceStatus
#
#  Description    	: This function is called to check the ipv6 PIM SM global mode 
#         
#  Usage          	: _ntgrCheckIPv6PIMSMInterfaceStatus <switch_name> <port> <neighbor_count> <des_router>
#
#
#*******************************************************************************
proc _ntgrCheckIPv6PIMSMInterfaceStatus {switch_name port neighbor_count des_router} {
    set bFlag 0
    set cmds "show ipv6 pim interface $port"
    set retStr ""
    ntgrCLILogin  $switch_name
    CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    set result 0
	foreach iList $tmpList {
        if {[regexp -nocase -- "Mode*" $iList]} {           
            set real_mode [lindex $iList end] 
	        Netgear_log_file "_ntgrCheckIPv6PIMSMInterfaceStatus" " Real PIM SIM Mode is: $real_mode ."
            if {[string equal -nocase $real_mode "Sparse"] == 1} {
                incr result 1
            }  
        }
		if {[regexp -nocase -- "Neighbor Count*" $iList]} {           
            set real_count [lindex $iList end] 
	        Netgear_log_file "_ntgrCheckIPv6PIMSMInterfaceStatus" " Real Neighbor Count is: $real_count ."
            if {$real_count == $neighbor_count} {
                incr result 1
            }  
        }
		if {[regexp -nocase -- "Designated Router*" $iList]} {           
            set real_router [lindex $iList end] 
	        Netgear_log_file "_ntgrCheckIPv6PIMSMInterfaceStatus" " Real Designated Router is: $real_router ."
             if {[string equal -nocase $real_router $des_router] == 1} {
                incr result 1
            }  
        }
    }

	ntgrCLILogout  $switch_name
    if {$result == 3} {
	    set bFlag 1
	}
	
    return $bFlag

}

#*******************************************************************************
#  Function Name	: _ntgrCheckIPv6PIMRPAddressTable
#
#  Description    	: This function is called to check the ipv6 PIM RP Address for a specific group.
#         
#  Usage          	: _ntgrCheckPIMRPAddressTable <switch_name> <group_ip> <rp_addr> 
#
#
#*******************************************************************************
proc _ntgrCheckIPv6PIMRPAddressTable {switch_name group_ip rp_addr} {
    set bFlag 0
    set cmds "show ipv6 pim rp-hash $group_ip" 
    set retStr ""
    ntgrCLILogin  $switch_name
    CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    
	foreach iList $tmpList {
        if {[regexp -nocase -- ".*RP Address*" $iList]} {           
            set real_addr [lindex $iList end] 
	        Netgear_log_file "_ntgrCheckIPv6PIMRPAddressTable" " Real RP Address on PIM RP Address Table is: $real_addr . Expect RP Address is: $rp_addr ."
            if {[string equal -nocase $real_addr $rp_addr] == 1} {
                set bFlag 1
            }  
        }
    }

	ntgrCLILogout  $switch_name

    return $bFlag

}

#*******************************************************************************
#  Function Name	: _ntgrConfigureIPv6PIMPortDRPriority
#
#  Description    	: This function is called to configutre dr-priority on port
#         
#  Usage          	: _ntgrConfigureIPv6PIMPortDRPriority <switch_name> <port> <priority>
#
#
#*******************************************************************************
proc _ntgrConfigureIPv6PIMPortDRPriority {switch_name if_list priority} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    

   foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "ipv6 pim dr-priority $priority\r"
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

#*******************************************************************************
#  Function Name	: _ntgrDelIPv6PIMPortDRPriority
#
#  Description    	: This function is called to delete dr-priority on port
#         
#  Usage          	: _ntgrDelIPv6PIMPortDRPriority <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrDelIPv6PIMPortDRPriority {switch_name if_list} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    

   foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $connection_id "configure\r"
        after 200
        exp_send -i $connection_id "interface $if_name\r"
        after 200
        exp_send -i $connection_id "no ipv6 pim dr-priority\r"
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


#*******************************************************************************
#  Function Name	: _ntgrCfgIPPIMSSM
#
#  Description    : This function is called to configure ip PIM ssm range on the Switch
#         
#  Usage          : _ntgrCfgIPPIMSSM <switch_name> <range>
#
#
#*******************************************************************************
proc _ntgrCfgIPPIMSSM {switch_name range} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "ip pim ssm $range\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}


#*******************************************************************************
#  Function Name	: _ntgrDelIPPIMSSM
#
#  Description    : This function is called to delete ip PIM ssm range on the Switch
#         
#  Usage          : _ntgrDelIPPIMSSM <switch_name> <range>
#
#
#*******************************************************************************
proc _ntgrDelIPPIMSSM {switch_name range} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "no ip pim ssm $range\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}