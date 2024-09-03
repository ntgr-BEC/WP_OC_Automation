####################################################################################
#  File Name   : lib-dhcpv6.tcl                                                 #
#                                                                                  #
#  Description :                                                                   #
#        This file includes functions used for DHCPv6 configuration.                 #
#                                                                                  #
#  History     :                                                                   #
#        Date          Programmer         Description                              #
#        April 14, 2014  jason        Created                                  #
#                                                                                  #
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrEnableDHCPv6
#
#  Description    : This function is called to enable DHCPv6 service.
#
#  Usage          : _ntgrEnableDHCPv6 <switch_name>
#
#*******************************************************************************
proc _ntgrEnableDHCPv6 {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "service dhcpv6\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableDHCPv6
#
#  Description    : This function is called to disable DHCPv6 service.
#
#  Usage          : _ntgrDisableDHCPv6 <switch_name>
#
#*******************************************************************************
proc _ntgrDisableDHCPv6 {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no service dhcpv6\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrCreateDhcpv6AddrPrefixOnPool
#
#  Description    : This function is called to Add DHCPv6 Address Pool.
#
#  Usage          : _ntgrCreateDhcpv6AddrPrefixOnPool <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrCreateDhcpv6AddrPrefixOnPool {switch_name pool_name prefix} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    
    exp_send -i $cnn_id "\r"
    exp_sleep 1  
  
  	exp_send -i $cnn_id "ipv6 dhcp pool $pool_name\r"
    exp_sleep 1   
	  exp_send -i $cnn_id "address prefix $prefix\r"
    exp_sleep 1
    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrCreateDhcpv6AddrPool
#
#  Description    : This function is called to Add DHCPv6 Address Pool.
#
#  Usage          : _ntgrCreateDhcpv6AddrPool <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrCreateDhcpv6AddrPool {switch_name pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    
    exp_send -i $cnn_id "\r"
    exp_sleep 1 
    
  if {$pool_name == {}} {
        set pool_name [array names ntgr_DHCPv6_AddrPool_$switch_name]; # All
    } 

 foreach pool $pool_name {
        set prefix        	  [_ntgrGetDhcpv6Prefix $switch_name $pool] 
	    set duid        	  [_ntgrGetDhcpv6DUID $switch_name $pool] 
		set prefertime        [_ntgrGetDhcpv6PreferTime $switch_name $pool]  
		set validtime        [_ntgrGetDhcpv6ValidTime $switch_name $pool]  
 
 	    exp_send -i $cnn_id "ipv6 dhcp pool $pool\r"
        exp_sleep 1 
        
        set strcmd "" 
		if {$prefix != {}} {
		   set strcmd [append strcmd "prefix-delegation $prefix"]  
            exp_sleep 1
        }   
  
        if {$duid != {}} { 
			set strcmd [append strcmd " $duid"]  
            exp_sleep 1
		}  
	
		if {$prefertime != {}} {
		   set strcmd [append strcmd " prefer-lifetime $prefertime"]  
            exp_sleep 1
        }  
   
		if {$validtime != {}} {
		   set strcmd [append strcmd " valid-lifetime  $validtime"]  
            exp_sleep 1
        }   
      
    }
	exp_send -i $cnn_id "$strcmd\r"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDelDhcpv6AddrPool
#
#  Description    : This function is called to delete DHCPv6 address pool.
#
#  Usage          : _ntgrDelDhcpv6AddrPool <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrDelDhcpv6AddrPool {switch_name pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1

    if {$pool_name == {}} {
        set pool_name [array names ntgr_DHCPv6_AddrPool_$switch_name]; # All
    }
    foreach pool $pool_name {
        exp_send -i $cnn_id "no ipv6 dhcp pool $pool\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrEnableDhcpv6PoolOnPort
#
#  Description    : This function is called to Binding DHCPv6 Pool on Port.
#
#  Usage          : _ntgrEnableDhcpv6PoolOnPort <switch_name> <port> <pool_name>
#
#*******************************************************************************
proc _ntgrEnableDhcpv6PoolOnPort {switch_name port pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    
    exp_send -i $cnn_id "\r"
    exp_sleep 1  
    
    exp_send -i $cnn_id "interface $port\r"
    
  if {$pool_name == {}} {
        set pool_name [array names ntgr_DHCPv6Pool_On_Port_$switch_name]; # All
    } 

 foreach pool $pool_name {
        set rapid_commit        	  [_ntgrGetDhcpv6RapidCommit $switch_name $pool] 
	    set preference        	  	  [_ntgrGetDhcpv6Preferece $switch_name $pool]   
 
        set strcmd "ipv6 dhcp server $pool" 
        
		if {$rapid_commit != {}} {
            set strcmd [append strcmd " rapid-commit"]
        }  

		if {$preference != {}  } {
            set strcmd [append strcmd " preference $preference"]
        }   
        
        exp_send -i $cnn_id $strcmd
        exp_sleep 1 
    }

    Netgear_disconnect_switch $switch_name
}  

#*******************************************************************************
#
#  Function Name  : _ntgrDisableDhcpv6PoolOnPort
#
#  Description    : This function is called to Delete DHCPv6 Pool Bindings on Port.
#
#  Usage          : _ntgrDisableDhcpv6PoolOnPort <switch_name> <port> <pool_name>
#
#*******************************************************************************
proc _ntgrDisableDhcpv6PoolOnPort {switch_name port pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    
    exp_send -i $cnn_id "\r"
    exp_sleep 1  
    
    exp_send -i $cnn_id "interface $port\r"
    
  if {$pool_name == {}} {
        set pool_name [array names ntgr_DHCPv6Pool_On_Port_$switch_name]; # All
    } 

 foreach pool $pool_name {
	exp_send -i $cnn_id "no ipv6 dhcp server\r"
 } 
     exp_send -i $cnn_id "exit\r"
     exp_sleep 1  
 
   Netgear_disconnect_switch $switch_name
}  

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpv6Prefix
#
#  Description    : This function is used to Get prefix From List .
#
#  Usage          : _ntgrGetDhcpv6Prefix <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpv6Prefix {switch_name pool_name} {
    global ntgr_DHCPv6_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCPv6_AddrPool_${switch_name}($pool_name) PREFIX ret
    return $ret
} 


#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpv6DUID
#
#  Description    : This function is used to get duid from List.
#
#  Usage          : _ntgrGetDhcpv6DUID <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpv6DUID {switch_name pool_name} {
    global ntgr_DHCPv6_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCPv6_AddrPool_${switch_name}($pool_name) DUID ret
    return $ret
}  

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpv6PreferTime
#
#  Description    : This function is used to get prefer time from List.
#
#  Usage          : _ntgrGetDhcpv6PreferTime <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpv6PreferTime {switch_name pool_name} {
    global ntgr_DHCPv6_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCPv6_AddrPool_${switch_name}($pool_name) PREFERTIME ret
    return $ret
} 

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpv6ValidTime
#
#  Description    : This function is used to get valid time from List.
#
#  Usage          : _ntgrGetDhcpv6ValidTime <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpv6ValidTime {switch_name pool_name} {
    global ntgr_DHCPv6_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCPv6_AddrPool_${switch_name}($pool_name) VALIDTIME ret
    return $ret
} 


#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpv6RapidCommit
#
#  Description    : This function is used to get Rapid Commit Settings From List.
#
#  Usage          : _ntgrGetDhcpv6RapidCommit <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpv6RapidCommit {switch_name pool_name} {
    global ntgr_DHCPv6Pool_On_Port_$switch_name
    set ret {}
    keylget ntgr_DHCPv6Pool_On_Port_${switch_name}($pool_name) RAPID_COMMIT ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpv6Preferece
#
#  Description    : This function is used to get Preference From List.
#
#  Usage          : _ntgrGetDhcpv6Preferece <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpv6Preferece {switch_name pool_name} {
    global ntgr_DHCPv6Pool_On_Port_$switch_name
    set ret {}
    keylget ntgr_DHCPv6Pool_On_Port_${switch_name}($pool_name) PREFERENCE ret
    return $ret
} 

#*******************************************************************************
#  Function Name	: _ntgrCheckDHCPv6BindingEntry
#
#  Description    	: This function is called to check DHCPv6 Bindings entry Exists on switch
#         
#  Usage          	: _ntgrCheckDHCPv6BindingEntry <switch_name> <duid_list> <notin>
#
#
#*******************************************************************************
proc _ntgrCheckDHCPv6BindingEntry {switch_name duid_list {notin 0}} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    set output ""
    
    exp_send -i $cnn_id "show ipv6 dhcp binding\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ipv6 dhcp binding"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
    
    set result 1
    foreach duid $duid_list {
        if {$notin == 1} {
            if {[regexp -nocase $duid $output]} {
                set result 0
                break
            }
        } else {
            if {![regexp -nocase $duid $output]} {
                set result 0
                break
            }
        }
    }

    Netgear_log_file "lib-dhcpv6.tcl" "Check DHCPv6 Bindings entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}  

#*******************************************************************************
#  Function Name	: _ntgrCheckDHCPv6BindingPreferLifetime
#
#  Description    	: This function is called to check DHCPv6 Bindings Prefer Lifetime on switch
#         
#  Usage          	: _ntgrCheckDHCPv6BindingPreferLifetime <switch_name> <pretime> <notin>
#
#
#*******************************************************************************
proc _ntgrCheckDHCPv6BindingPreferLifetime {switch_name pretime {notin 0}} { 
    
    set cmds "show ipv6 dhcp binding" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    
        set result 1
        
        if {$notin == 1} { 
          	foreach iList $tmpList { 
             	if {[regexp -nocase -- "Binding Prefix Preferred Lifetime*" $iList]} {
		         set pretime1 [lindex $iList end]
		           if {$pretime1 == $pretime} { 
				      set result 0
				   }
		    	 } 
			}	 
        } else {
        		set result 0 
				foreach iList $tmpList { 
             	if {[regexp -nocase -- "Binding Prefix Preferred Lifetime*" $iList]} {
		         set pretime1 [lindex $iList end]
		           if {$pretime1 == $pretime} { 
				      set result 1
				   }
		    	 } 	 
        		}
        	}

    return $result
}  

#*******************************************************************************
#  Function Name	: _ntgrCheckDHCPv6BindingValidLifetime
#
#  Description    	: This function is called to check Valid Lifetime on DHCPv6 Bindings Table
#         
#  Usage          	: _ntgrCheckDHCPv6BindingValidLifetime <switch_name> <validtime> <notin>
#
#
#*******************************************************************************
proc _ntgrCheckDHCPv6BindingValidLifetime {switch_name validtime {notin 0}} { 
    
    set cmds "show ipv6 dhcp binding" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    
        set result 1
        
        if {$notin == 1} { 
          	foreach iList $tmpList { 
             	if {[regexp -nocase -- "Binding Prefix Valid Lifetime*" $iList]} {
		         set validtime1 [lindex $iList end]
		           if {$validtime1 == $validtime} { 
				      set result 0
				   }
		    	 } 
			}	 
        } else {
        		set result 0 
				foreach iList $tmpList { 
             	if {[regexp -nocase -- "Binding Prefix Valid Lifetime*" $iList]} {
		         set validtime1 [lindex $iList end]
		           if {$validtime1 == $validtime} { 
				      set result 1
				   }
		    	 } 	 
       		 } 
       	}

    return $result
} 

#*******************************************************************************
#  Function Name	: _ntgrCheckDHCPv6BindingPrefix
#
#  Description    	: This function is called to check Prefix on DHCPv6 bindings Table
#         
#  Usage          	: _ntgrCheckDHCPv6BindingPrefix <switch_name> <pre_fix> <notin}>
#
#
#*******************************************************************************
proc _ntgrCheckDHCPv6BindingPrefix {switch_name pre_fix {notin 0}} { 
    
    set cmds "show ipv6 dhcp binding" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    
        set result 1
        
        if {$notin == 1} { 
          	foreach iList $tmpList { 
             	if {[regexp -nocase -- "Binding Prefix Address*" $iList]} {
		         set prefix1 [lindex $iList end]
		           if {$prefix1 == $pre_fix} { 
				      set result 0
				   }
		    	 } 
			}	 
        } else {
        		set result 0 
				foreach iList $tmpList { 
             	if {[regexp -nocase -- "Binding Prefix Address*" $iList]} {
		         set prefix1 [lindex $iList end]
		           if {$prefix1 == $pre_fix} { 
				      set result 1
				   }
		    	 } 	 
       		 }
       	}

    return $result
} 

#*******************************************************************************
#  Function Name	: _ntgrClearDHCPv6ServerPacketStatistic
#
#  Description    	: This function is called to clear DHCPv6 Packet Statistic on switch
#         
#  Usage          	: _ntgrClearDHCPv6ServerPacketStatistic <switch_name> 
#
#
#*******************************************************************************
proc _ntgrClearDHCPv6ServerPacketStatistic {switch_name} { 
    
   Netgear_connect_switch $switch_name
   set cnn_id [_get_switch_handle $switch_name]
    after 200
    set output ""
    
    exp_send -i $cnn_id "clear ipv6 dhcp statistics\r"
    exp_sleep 1 
    
    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#  Function Name	: _ntgrGetDHCPv6ServerPacketStatistic
#
#  Description    	: This function is called to Get DHCPv6 Server Packet Statistic
#         
#  Usage          	: _ntgrGetDHCPv6ServerPacketStatistic <switch_name> <strtext>
#
#
#*******************************************************************************
proc _ntgrGetDHCPv6ServerPacketStatistic {switch_name strtext} {

   set cmds "show ipv6 dhcp statistics" 
   set result {} 
   
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
        
    foreach stattext $strtext {   
    	set retVar 0
    
    	foreach iList $tmpList { 
             	if {[regexp -nocase -- "$stattext*" $iList]} {
		         	set retVar [lindex $iList end]
		    	 } 
			}  
		
	  lappend result $stattext 	
	  lappend result $retVar
	}	  
 return $result
}  