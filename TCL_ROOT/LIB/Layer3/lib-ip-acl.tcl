#!/bin/sh
################################################################################   
#
#  File Name		  : lib-ip-acl.tcl
#
#  Description      :
#         This TCL contains functions to configure IP ACL
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        28-Aug-06     Nina Cheng        Created
#
#
#
#
################################################################################
#1*******************************************************************************
#  Function Name	: _ntgrAddIPACL
#
#  Description    	: This function is called to add ACL on switch
#         
#  Usage          	: _ntgrAddIPACL <switch_name> <ip_acl_rule_list> 
#
#
#*******************************************************************************
proc _ntgrAddIPACL {switch_name ip_acl_rule_list} {
   
  set Ret {}
  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	  expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
  	_ntgrAddIPACLwithoutLogin $ip_acl_rule_list $connection_id Ret
 
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return $Ret
}
#1.1*******************************************************************************
#  Function Name	: _ntgrAddIPACLwithoutLogin
#
#  Description    	: This function is called to add ACL on switch
#         
#  Usage          	: _ntgrAddIPACLwithoutLogin  <ip_acl_rule_list>  <retval>
#
#
#*******************************************************************************
proc _ntgrAddIPACLwithoutLogin {ip_acl_rule_list connection_id retval} {
    upvar 1 $retval Ret
    exp_send -i $connection_id "exit\r"
    set intRelease {}; set intVersion {}; set intMaintenance {}; set intPatch {} ; set strModel {}
    _ntgrGetVersionDetailedbyConnectionID $connection_id intRelease intVersion intMaintenance intPatch strModel
    
    expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
    
    foreach acl_rule_list $ip_acl_rule_list {
      set acl_id [lindex $acl_rule_list 0]
      set acl_rule [lindex $acl_rule_list 1]
      set strmatch [string match {[a-zA-Z]*} $acl_id] 
	  
      if {$strmatch ==1} {
          exp_send -i $connection_id "ip access-list $acl_id\r"
          sleep 1
          expect -i $connection_id "ip access-list $acl_id"
          sleep 1
          expect -i $connection_id -re "#"
      }  
      foreach rule $acl_rule {
          set cmd ""
          set i 1
          # initiate rule_$i
          for {} {$i < 9} {incr i} {
            set rule_$i {}
          }
          set i 1
          foreach rule_s $rule {
          	set rule_$i $rule_s
            incr i
          }   
          if {$rule_1 == {}} {
            continue
          }
          if {$acl_id <= 99} {
                  if {$rule_1 != "default"} {set cmd "$cmd $rule_1"}
                  if {$rule_2 != "default"} {set cmd "$cmd $rule_2"}
                  if {$rule_3 != "" && $rule_3 != "default" } {set cmd "$cmd $rule_3"}            
          } else {
                  set src_ip [lindex $rule_3 0]
                  set src_mask [lindex $rule_3 1]
                  set src_port [lindex $rule_3 2]
                  
                  set des_ip [lindex $rule_4 0]
                  set des_mask [lindex $rule_4 1]
                  set des_port [lindex $rule_4 2]
                  
                  if {$intRelease == 9} {
                      if { [string first "JGSM" $strModel] == -1 } {
                        if {$des_port == "http" ||$des_port == "www"} {
                            set des_port "www-http"
                        }
                      } 
                  }
                  
                  set qos_mode [lindex $rule_5 0]
                  set qos_value_1 [lindex $rule_5 1]
#                 set qos_value_2 [lindex $rule_5 2]
                  
                  if {$rule_1 != "default"} {set cmd "$cmd $rule_1"}
                  if {$rule_2 == "every"} {
                      set cmd "$cmd $rule_2"
                      if {$rule_6 != "default"} {set cmd "$cmd assign-queue $rule_6"}
                      if {$rule_7 != "default"} {set cmd "$cmd time-range $rule_7"}
                      if {$rule_8 != "" && $rule_8 != "default" } {set cmd "$cmd $rule_8"}
                  } else {
                      set cmd "$cmd $rule_2"
                      if {$src_ip != "default"} {
                  				if {$src_ip != "any"} {set cmd "$cmd $src_ip $src_mask"} else {
                  				set cmd "$cmd $src_ip"
				                 }
                      }
					  
					  # modify by jim.xie begin
					  if {$src_port == "range"} {
					      set src_start_port [lindex $rule_3 3]
						  set src_stop_port  [lindex $rule_3 4]
						  set cmd "$cmd range"
						  if {$src_start_port != "default"} {set cmd "$cmd $src_start_port"}
						  if {$src_stop_port != "default"} {set cmd "$cmd $src_stop_port"}
					  } elseif {$src_port == "neq"} {
					      set src_add_port [lindex $rule_3 3]
						  set cmd "$cmd neq $src_add_port"
					  } elseif {$src_port == "gt"} {
					      set src_add_port [lindex $rule_3 3]
						  set cmd "$cmd gt $src_add_port"
					  } elseif {$src_port == "lt"} {
					      set src_add_port [lindex $rule_3 3]
						  set cmd "$cmd lt $src_add_port"
					  } elseif {$src_port == "icmp-type"} {
					      set src_icmp_ip [lindex $rule_3 1]
					      set src_icmp_port [lindex $rule_3 3]
						  set cmd "$cmd $src_icmp_ip icmp-type $src_icmp_port"
					  } elseif {$src_port == "icmp-message"} {
					      set src_icmp_ip [lindex $rule_3 1]
					      set src_icmp_port [lindex $rule_3 3]
						  set cmd "$cmd $src_icmp_ip icmp-message $src_icmp_port"
					  } elseif {$src_port == "fragments"} {
					      set src_icmp_ip [lindex $rule_3 1]
						  set cmd "$cmd $src_icmp_ip fragments"
					  } elseif {$src_port == "default"} {
					      set cmd $cmd
					  } else {
					     set cmd "$cmd eq $src_port"
					  }
                      # modify by jim.xie end

                      if {$des_ip != "default"} {
                      		if {$des_ip != "any"} {set cmd "$cmd $des_ip $des_mask"} else {
	                      	set cmd "$cmd $des_ip"	
                      		}
                      }
					  
					  # modify by jim.xie begin
					  if {$des_port == "range"} {
					      set des_start_port [lindex $rule_4 3]
						  set des_stop_port  [lindex $rule_4 4]
						  set cmd "$cmd range"
						  if {$des_start_port != "default"} {set cmd "$cmd $des_start_port"}
						  if {$des_stop_port != "default"} {set cmd "$cmd $des_stop_port"}
					  } elseif {$des_port == "neq"} {
					      set dec_add_port [lindex $rule_4 3]
						  set cmd "$cmd neq $dec_add_port"
					  } elseif {$des_port == "gt"} {
					      set des_add_port [lindex $rule_4 3]
						  set cmd "$cmd gt $des_add_port"
					  } elseif {$des_port == "lt"} {
					      set des_add_port [lindex $rule_4 3]
						  set cmd "$cmd lt $des_add_port"
					  } elseif {$des_port == "default"} {
					      set cmd $cmd
					  }  else {
					     set cmd "$cmd eq $des_port"
					  }
                      # modify by jim.xie end
					  
                      set tmp_list ""
                      if {$qos_mode != "default"} {
                         if { [string first "JGSM" $strModel] != -1 } {
                            if { $qos_mode == "tos" } {
                                foreach qos $qos_value_1 {
                                    set tmp_qos 0x$qos
                                    set blank  " " 
                                    set tmp_list $tmp_list$tmp_qos
                                    set tmp_list $tmp_list$blank
                                }
                                set qos_value_1 [string trim $tmp_list]
                            }
                           
                         } 
			                   set cmd "$cmd $qos_mode $qos_value_1"
			                     
                      }
                      if {$rule_6 != "default"} {set cmd "$cmd assign-queue $rule_6"}
                      if {$rule_7 != "default"} {set cmd "$cmd time-range $rule_7"}
                      if {$rule_8 != "" && $rule_8 != "default" } {set cmd "$cmd $rule_8"}
                  }
          } 
          if {$strmatch ==1} {
              exp_send -i $connection_id "$cmd\r"
              sleep 1
              expect -i $connection_id "$cmd"
              sleep 1
              expect -i $connection_id -re "#"
              set Ret $expect_out(buffer) 
          
          } else {
            if {$cmd ==" "} {continue}
            exp_send -i $connection_id "access-list $acl_id $cmd\r"
            sleep 1
            expect -i $connection_id "access-list $acl_id"
            sleep 1
            expect -i $connection_id -re "#"
            set Ret $expect_out(buffer) 
          }              
      }

      if {$strmatch ==1} { exp_send -i $connection_id "exit\r"}
   }
	return $Ret
}
################################################################################
#2*******************************************************************************
#  Function Name	: _ntgrDeleteIPACL
#
#  Description    	: This function is called to delete ACL on switch
#         
#  Usage          	: _ntgrDeleteIPACL <switch_name> <ip_acl_id_list> 
#
#
#*******************************************************************************
proc _ntgrDeleteIPACL {switch_name ip_acl_id_list} {

  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
  _ntgrDeleteIPACLwithoutLogin $connection_id $ip_acl_id_list 
	Netgear_disconnect_switch $switch_name
}

#2.1*******************************************************************************
#  Function Name	: _ntgrDeleteIPACLwithoutLogin
#
#  Description    	: This function is called to delete ACL on switch
#         
#  Usage          	:  _ntgrDeleteIPACLwithoutLogin <connection_id> <ip_acl_id_list> 
#
#
#*******************************************************************************
proc _ntgrDeleteIPACLwithoutLogin {connection_id ip_acl_id_list} {
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  foreach acl_list $ip_acl_id_list {
    set strmatch [string match {[a-zA-Z]*} $acl_list] 
    if {$strmatch==1} {
      exp_send -i $connection_id "no ip access-list $acl_list\r"
    } else {
      exp_send -i $connection_id "no access-list $acl_list\r"
    }
    sleep 1
  }
  exp_send -i $connection_id "exit\r"
}


################################################################################
#3*******************************************************************************
#  Function Name	: _ntgrInterfaceIPACLEnable
#
#  Description    	: This function is called to enable ACL on interface
#         
#  Usage          	: _ntgrInterfaceIPACLEnable <switch_name> <interface_list> <bind_direction>
#
#  History          : Add parameter <bind_direction> by kenddy xie, for binding Acl in | out
#
#
#*******************************************************************************
proc _ntgrInterfaceIPACLEnable {switch_name interface_list {bind_direction in}} {
  set Ret {}
  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
 _ntgrInterfaceIPACLEnablewithoutLogin $connection_id $interface_list Ret $bind_direction
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return $Ret
}

#3.1*******************************************************************************
#  Function Name	: _ntgrInterfaceIPACLEnablewithoutLogin
#
#  Description    	: This function is called to enable ACL on interface
#         
#  Usage          	: _ntgrInterfaceIPACLEnablewithoutLogin <connection_id> <interface_list> <retval> <bind_direction>
#
#  History          : Add parameter <bind_direction> by kenddy xie, for binding Acl in | out
#
#*******************************************************************************
proc _ntgrInterfaceIPACLEnablewithoutLogin {connection_id interface_list retval {bind_direction in}} {
  upvar 1 $retval Ret
  set reg {^[0-9a-zA-Z]+$}            
  foreach int_list $interface_list {
      set acl_id [lindex $int_list 0]
      set property_interface_list [lindex $int_list 1]
      foreach acl_int_list $property_interface_list {
      set interface [lindex $acl_int_list 0]
      if {[regexp -nocase {vlan(\d+)} $interface dummy vlanid]} {
          set interface "vlan"
      } else {
          set mflag  [regexp $reg $interface]
          if {$mflag} {set interface [_ntgrGetChannelLogicalIfName $connection_id $interface]}
      }
      set seq [lindex $acl_int_list 1]
         if {$interface == "all"} {
            if {$seq == "default"} {set seq ""}
            exp_send -i $connection_id "ip access-group $acl_id $bind_direction $seq\r"
            sleep 1
         } elseif {$interface == "vlan"} {
             if {$seq == "default"} {set seq ""}
             exp_send -i $connection_id "configure\r"
  	         sleep 1
             exp_send -i $connection_id "ip access-group $acl_id vlan $vlanid $bind_direction $seq\r"
             expect -i $connection_id -re "#"
             exp_send -i $connection_id "exit\r"
             sleep 1
         } else {
            exp_send -i $connection_id "configure\r"
            expect -i $connection_id -re "#"
  	        sleep 1
            exp_send -i $connection_id "interface $interface\r"
            expect -i $connection_id -re "#"
            sleep 1
            if {$seq == "default"} {set seq ""} 
            exp_send -i $connection_id "ip access-group $acl_id $bind_direction $seq\r"  
            sleep 1
            expect -i $connection_id -re "ip access-group $acl_id $bind_direction"
            expect -i $connection_id -re "#"
            set Ret $expect_out(buffer) 
            sleep 1
            exp_send -i $connection_id "exit\r"
            sleep 1
            exp_send -i $connection_id "exit\r"
         }
      }
  }
  sleep 3
	return $Ret
}

#3.2*******************************************************************************
#  Function Name	: _ntgrInterfaceIPACLEnablewithoutconfig
#
#  Description    	: This function is called to enable ACL on interface
#         
#  Usage          	: _ntgrInterfaceIPACLEnablewithoutconfig <connection_id> <interface_list> <retval>
#
#
#*******************************************************************************
proc  _ntgrInterfaceIPACLEnablewithoutconfig {connection_id interface_list retval} {
  upvar 1 $retval Ret
  set reg {^[0-9a-zA-Z]+$}            
  foreach int_list $interface_list {
      set acl_id [lindex $int_list 0]
      set property_interface_list [lindex $int_list 1]
      foreach acl_int_list $property_interface_list {
      set interface [lindex $acl_int_list 0]
      set mflag  [regexp $reg $interface]
      if {$mflag} {set interface [_ntgrGetChannelLogicalIfName $connection_id $interface]}
      set seq [lindex $acl_int_list 1]
         if {$interface == "all"} {
            if {$seq == "default"} {set seq ""}
            exp_send -i $connection_id "ip access-group $acl_id in $seq\r"
            sleep 1
         } else {
            exp_send -i $connection_id "interface $interface\r"
            expect -i $connection_id -re "#"
            sleep 1
            if {$seq == "default"} {set seq ""} 
            exp_send -i $connection_id "ip access-group $acl_id in $seq\r"  
            sleep 1
            expect -i $connection_id -re "ip access-group"
            expect -i $connection_id -re "#"
            set Ret $expect_out(buffer) 
            sleep 1
            exp_send -i $connection_id "exit\r"
            sleep 1
         }
      }
  }
	return $Ret
}

################################################################################
#4*******************************************************************************
#  Function Name	: _ntgrInterfaceIPACLDisable
#
#  Description    	: This function is called to Disable ACL on interface
#         
#  Usage          	: _ntgrInterfaceIPACLDisable <switch_name> <interface_list> 
#
#
#*******************************************************************************
proc _ntgrInterfaceIPACLDisable {switch_name interface_list {bind_direction in}} {
	
  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
 _ntgrInterfaceIPACLDisablewithoutLogin $connection_id $interface_list $bind_direction
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}
#4.1*******************************************************************************
#  Function Name	: _ntgrInterfaceIPACLDisablewithoutLogin
#
#  Description    	: This function is called to Disable ACL on interface
#         
#  Usage          	: _ntgrInterfaceIPACLDisablewithoutLogin <connection_id> <interface_list> 
#
#
#*******************************************************************************
proc _ntgrInterfaceIPACLDisablewithoutLogin {connection_id interface_list {bind_direction in}} {
	
  set reg {^[0-9a-zA-Z]+$} 	
  foreach int_list $interface_list {
      set acl_id [lindex $int_list 0]
      set property_interface_list [lindex $int_list 1]
      foreach acl_int_list $property_interface_list {
      set interface [lindex $acl_int_list 0]
      if {[regexp -nocase {vlan(\d+)} $interface dummy vlanid]} {
          set interface "vlan"
      } else {
          set mflag  [regexp $reg $interface]
          if {$mflag} {set interface [_ntgrGetChannelLogicalIfName $connection_id $interface]}
      }
         if {$interface == "all"} {
            exp_send -i $connection_id "no ip access-group $acl_id $bind_direction\r"
            sleep 1
         } elseif {$interface == "vlan"} {
            exp_send -i $connection_id "configure\r"
  	        sleep 1
            exp_send -i $connection_id "no ip access-group $acl_id vlan $vlanid $bind_direction\r"
            sleep 1
            exp_send -i $connection_id "exit\r"
            sleep 1
         } else {
            exp_send -i $connection_id "configure\r"
  	        sleep 1
            exp_send -i $connection_id "interface $interface\r"
            sleep 1
            exp_send -i $connection_id "no ip access-group $acl_id $bind_direction\r"
            sleep 1
            exp_send -i $connection_id "exit\r"
            sleep 1
            exp_send -i $connection_id "exit\r"
            sleep 1
         }
      }
  }
}

################################################################################
#5*******************************************************************************
#  Function Name	: __ntgrgetIPACLMax
#
#  Description    : This function is called to get ACL max on interface
#         
#  Usage          :	_ntgrgetIPACLMax  <switch_name> <maxium_rules>
#
#
#*******************************************************************************
proc _ntgrgetIPACLMax {switch_name max_rules} {
  upvar 1 $max_rules max_num

	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]	
	expect -i $connection_id -re "#"
	exp_send -i $connection_id "show ip access-list\r"
	expect -i $connection_id "show ip access-list"
	sleep 2
	expect -i $connection_id -re "#"
  set token "Maximum"
  set pos [string first $token $expect_out(buffer)] 
  set start [expr $pos+24]
  set end   [expr $start+4]
  set max_num [string range $expect_out(buffer) $start $end]
  set max_num [string trim $max_num " "]
  Netgear_disconnect_switch $switch_name
}

################################################################################
#6*******************************************************************************
#  Function Name	: _ntgrVlanIPACLEnable
#
#  Description    	: This function is called to enable ACL on Vlan
#         
#  Usage          	: _ntgrInterfaceIPACLEnable <switch_name> <interface_list> <Vlan_ID> <bind_direction>
#
#
#*******************************************************************************
proc _ntgrVlanIPACLEnable {switch_name interface_list Vlan_ID {bind_direction in}} {
  set Ret {}
  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"  	  
	exp_send -i $connection_id "configure\r"
  expect -i $connection_id -re "#"
  sleep 1         
  _ntgrVlanIPACLEnablewithoutLogin $connection_id $interface_list $Vlan_ID Ret $bind_direction
  exp_send -i $connection_id "exit\r"
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return $Ret
}

################################################################################
#6.1*******************************************************************************
#  Function Name	: _ntgrVlanIPACLEnablewithoutLogin
#
#  Description    	: This function is called to enable ACL on Vlan
#         
#  Usage          	: _ntgrVlanIPACLEnablewithoutLogin <connection_id> <interface_list> <Vlan_ID> <retval> <bind_direction>
#
#
#*******************************************************************************
proc _ntgrVlanIPACLEnablewithoutLogin {connection_id interface_list Vlan_ID retval {bind_direction in}} {

  upvar 1 $retval Ret     
  foreach int_list $interface_list {
      set acl_id [lindex $int_list 0]
      set property_interface_list [lindex $int_list 1]
      foreach acl_int_list $property_interface_list {
          set interface [lindex $acl_int_list 0]
          set seq [lindex $acl_int_list 1]
          if {$seq == "default"} {set seq ""} 
          exp_send -i $connection_id "ip access-group $acl_id vlan $Vlan_ID $bind_direction $seq\r"  
          sleep 1
          expect -i $connection_id -re "ip access-group"
          expect -i $connection_id -re "#"
          set Ret $expect_out(buffer) 
      }
  }
	return $Ret
}

################################################################################
#7*******************************************************************************
#  Function Name	: _ntgrVlanIPACLDisable
#
#  Description    	: This function is called to enable ACL on Vlan
#         
#  Usage          	: _ntgrVlanIPACLDisable <switch_name> <interface_list> <Vlan_ID> <bind_direction>
#
#
#*******************************************************************************
proc _ntgrVlanIPACLDisable {switch_name interface_list Vlan_ID {bind_direction in}} {
	
  set Ret {}

  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"  	           
  _ntgrVlanIPACLDisablewithoutLogin $connection_id $interface_list $Vlan_ID $bind_direction
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return $Ret
}

#7.1*******************************************************************************
#  Function Name	: _ntgrVlanIPACLDisablewithoutLogin
#
#  Description    	: This function is called to enable ACL on Vlan
#         
#  Usage          	: _ntgrVlanIPACLDisablewithoutLogin <connection_id> <interface_list> <Vlan_ID> <bind_direction in>
#
#
#*******************************************************************************
proc _ntgrVlanIPACLDisablewithoutLogin  {connection_id interface_list Vlan_ID {bind_direction in}} {          
  foreach int_list $interface_list {
      set acl_id [lindex $int_list 0]
      set property_interface_list [lindex $int_list 1]
      foreach acl_int_list $property_interface_list {
          set interface [lindex $acl_int_list 0]
          set seq [lindex $acl_int_list 1]
          exp_send -i $connection_id "configure\r"
          expect -i $connection_id -re "#"
  	      sleep 1
          if {$seq == "default"} {set seq ""} 
          exp_send -i $connection_id "no ip access-group $acl_id vlan $Vlan_ID $bind_direction $seq\r"  
          sleep 3
          expect -i $connection_id -re "#" 
          exp_send -i $connection_id "exit\r"
      }
  }
}
#8*******************************************************************************
#  Function Name	: _ntgrRenameIPACL
#
#  Description    	: This function is called to delete ACL on switch
#         
#  Usage          	: _ntgrRenameIPACL <switch_name> <old_name> <new_name> 
#
#
#*******************************************************************************
proc _ntgrRenameIPACL {switch_name old_name new_name} {

  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
 	exp_send -i $connection_id "configure\r"
	sleep 1
  exp_send -i $connection_id "ip access-list rename $old_name $new_name \r"
  sleep 1
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#9*******************************************************************************
#  Function Name	: _ntgrAddIPv6ACL
#
#  Description    	: This function is called to add ACL on switch
#         
#  Usage          	: _ntgrAddIPv6ACL <switch_name> <ip_acl_rule_list> 
#
#
#             rule1        rule2    rule3+      rule4       rule5+
#             permit    |every   |
#              deny     |        |assign-queue|
#                       |        |log         |
#                       |        |mirror      |
#                       |        |redirect    |
#
#                       |<1-255> |
#                       |icmp    |                       |dscp
#                       |igmp    |                       |flow label
#                       |ipv6    | SIP Sport  |DIP Dport |assign queue
#                       |tcp     |                       |log 
#                       |udp     |                       |mirror
#                                                        |redirect 
#*******************************************************************************
proc _ntgrAddIPv6ACL {switch_name ip_acl_rule_list} {
   
  set Ret {}
  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
 	exp_send -i $connection_id "configure\r"
	sleep 1
  expect -i $connection_id -re "#"
  append Ret $expect_out(buffer)
  expect -i $connection_id "#" {}
  foreach acl_rule_list $ip_acl_rule_list {
      set acl_id [lindex $acl_rule_list 0]
      set acl_rule [lindex $acl_rule_list 1]
      exp_send -i $connection_id "ipv6 access-list $acl_id\r"
      sleep 1
      expect -i $connection_id -re "#"
      append Ret $expect_out(buffer)
#      expect -i $connection_id "#" {}
      foreach rule $acl_rule {
          set cmd ""
          for {set i 1} {$i < 9} {incr i} { set rule_$i {}}
          set i 1
          foreach rule_s $rule {
          	set rule_$i $rule_s
            incr i
          }   
          if {$rule_1 == ""} { continue }
          if {$rule_1 != "default"} {set cmd "$cmd $rule_1"} 
          set cmd "$cmd $rule_2"
          if {$rule_2 != "every"} {   
              set src_ip [lindex $rule_3 0]
              set src_port [lindex $rule_3 1]   
              set des_ip [lindex $rule_4 0]
              set des_port [lindex $rule_4 1]    
              if {$src_ip != "default"} {
          				if {$src_ip != "any"} {set cmd "$cmd $src_ip"} else {
          				    set cmd "$cmd $src_ip"
                 }
              }
              if {$src_port != "default"} {set cmd "$cmd eq $src_port"}
              
              if {$des_ip != "default"} {
              		if {$des_ip != "any"} {set cmd "$cmd $des_ip"} else {
                	    set cmd "$cmd $des_ip"	
              		}
              }
              if {$des_port != "default"} {set cmd "$cmd eq $des_port"}
           } else {
                set rule3_1 [lindex $rule_3 0]
                set rule3_2 [lindex $rule_3 1]
               
                if { $rule3_1 != "default" } {
                    set cmd "$cmd $rule3_1"
                    if {$rule3_2 != "default"} {set cmd "$cmd $rule3_2"}
                }
                
                set rule4_1 [lindex $rule_4 0]
                set rule4_2 [lindex $rule_4 1]
                if {$rule4_1 != "default"} {
                    set cmd "$cmd $rule4_1"
                    if {$rule4_2 != "default"} {set cmd "$cmd $rule4_2"}
                }
           }
          
          set rule5_1 [lindex $rule_5 0]
          set rule5_2 [lindex $rule_5 1]
          if {$rule5_1 != "default"} {
              set cmd "$cmd $rule5_1"
              if {$rule5_2 != "default"} {set cmd "$cmd $rule5_2"}
          }
          
          set rule6_1 [lindex $rule_6 0]
          set rule6_2 [lindex $rule_6 1]
          if {$rule6_1 != "default"} {
              set cmd "$cmd $rule6_1"
              if {$rule6_2 != "default"} {set cmd "$cmd $rule6_2"}
          }  
          
          set rule7_1 [lindex $rule_7 0]
          set rule7_2 [lindex $rule_7 1]
          if {$rule7_1 != "default"} {
              set cmd "$cmd $rule7_1"
              if {$rule7_2 != "default"} {set cmd "$cmd $rule7_2"}
          }
          
          set rule8_1 [lindex $rule_8 0]
          set rule8_2 [lindex $rule_8 1]
          if {$rule8_1 != "default"} {
              set cmd "$cmd $rule8_1"
              if {$rule8_2 != "default"} {set cmd "$cmd $rule8_2"}
          } 
          if {$cmd ==" "} {continue} 
          exp_send -i $connection_id "$cmd\r"
          sleep 1
          expect -i $connection_id -re "#"
          append Ret $expect_out(buffer)      
      }
      exp_send -i $connection_id "exit\r"
  }
  
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return $Ret
}
################################################################################
#10*******************************************************************************
#  Function Name	: _ntgrDeleteIPv6ACL
#
#  Description    	: This function is called to delete ACL on switch
#         
#  Usage          	: _ntgrDeleteIPv6ACL <switch_name> <ip_acl_id_list> 
#
#
#*******************************************************************************
proc _ntgrDeleteIPv6ACL {switch_name ip_acl_id_list} {

  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  
  foreach acl_list $ip_acl_id_list {
      exp_send -i $connection_id "no ipv6 access-list $acl_list\r"
      sleep 1
  }
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

################################################################################
#11*******************************************************************************
#  Function Name	: _ntgrInterfaceIPv6ACLEnable
#
#  Description    	: This function is called to enable ACL on interface
#         
#  Usage          	: _ntgrInterfaceIPv6ACLEnable <switch_name> <interface_list> <bind_direction>
#
#
#*******************************************************************************
proc _ntgrInterfaceIPv6ACLEnable {switch_name interface_list {bind_direction in}} {

  set Ret {}

  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	
  set reg {^[0-9a-zA-Z]+$}
            
  foreach int_list $interface_list {
      set acl_id [lindex $int_list 0]
      set property_interface_list [lindex $int_list 1]
      foreach acl_int_list $property_interface_list {
      set interface [lindex $acl_int_list 0]
      set mflag  [regexp $reg $interface]
      if {$mflag} {set interface [_ntgrGetChannelLogicalIfName $connection_id $interface]}
      set seq [lindex $acl_int_list 1]
         if {$interface == "all"} {
            if {$seq == "default"} {set seq ""}
            exp_send -i $connection_id "ipv6 traffic-filter $acl_id $bind_direction $seq\r"
            sleep 1
         } else {
            exp_send -i $connection_id "configure\r"
            expect -i $connection_id -re "#"
  	        sleep 1
            exp_send -i $connection_id "interface $interface\r"
            expect -i $connection_id -re "#"
            sleep 1
            if {$seq == "default"} {set seq ""} 
            exp_send -i $connection_id "ipv6 traffic-filter $acl_id $bind_direction $seq\r"  
            sleep 1
            expect -i $connection_id -re "#"
            set Ret $expect_out(buffer)  
            sleep 1
            exp_send -i $connection_id "exit\r"
            sleep 1
            exp_send -i $connection_id "exit\r"
         }
      }
  }
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return $Ret
}

################################################################################
#12*******************************************************************************
#  Function Name	: _ntgrInterfaceIPv6ACLDisable
#
#  Description    	: This function is called to enable ACL on interface
#         
#  Usage          	: _ntgrInterfaceIPv6ACLDisable <switch_name> <interface_list> <bind_direction>
#
#
#*******************************************************************************
proc _ntgrInterfaceIPv6ACLDisable {switch_name interface_list {bind_direction in}} {
	
  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
  set reg {^[0-9a-zA-Z]+$} 	
  
  foreach int_list $interface_list {
      set acl_id [lindex $int_list 0]
      set property_interface_list [lindex $int_list 1]
      foreach acl_int_list $property_interface_list {
      set interface [lindex $acl_int_list 0]
      set mflag  [regexp $reg $interface]
      if {$mflag} {set interface [_ntgrGetChannelLogicalIfName $connection_id $interface]}
         if {$interface == "all"} {
            exp_send -i $connection_id "no ipv6 traffic-filter $acl_id $bind_direction\r"
            sleep 1
         } else {
            exp_send -i $connection_id "configure\r"
  	        sleep 1
            exp_send -i $connection_id "interface $interface\r"
            sleep 1
            exp_send -i $connection_id "no ipv6 traffic-filter $acl_id $bind_direction\r"
            sleep 1
            exp_send -i $connection_id "exit\r"
            sleep 1
            exp_send -i $connection_id "exit\r"
            sleep 1
         }
      }
  }
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#13*******************************************************************************
#  Function Name	: _ntgrRenameIPv6ACL
#
#  Description    	: This function is called to rename ipv6 acl name from old to new.
#         
#  Usage          	: _ntgrRenameIPv6ACL <switch_name> <old_name> <new_name> 
#  
#  Reversion      :  Create by kenddy xie 2010-07-29
#
#*******************************************************************************
proc _ntgrRenameIPv6ACL {switch_name old_name new_name} {

  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
 	exp_send -i $connection_id "configure\r"
	sleep 1
  exp_send -i $connection_id "ipv6 access-list rename $old_name $new_name \r"
  sleep 1
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

################################################################################
#*******************************************************************************
#  Function Name	: _ntgrVlanIPv6ACLEnable
#
#  Description    	: This function is called to enable ipv6 ACL on Vlan
#         
#  Usage          	: _ntgrVlanIPv6ACLEnable <switch_name> <interface_list> <Vlan_ID> > <bind_direction>
#
#  Reversion      : Create by kenddy xie 2010-07-29
#
#*******************************************************************************
proc _ntgrVlanIPv6ACLEnable {switch_name interface_list Vlan_ID {bind_direction in}} {

  set Ret {}

  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"  	           
  foreach int_list $interface_list {
      set acl_id [lindex $int_list 0]
      set property_interface_list [lindex $int_list 1]
      foreach acl_int_list $property_interface_list {
          set interface [lindex $acl_int_list 0]
          set seq [lindex $acl_int_list 1]
          exp_send -i $connection_id "configure\r"
          expect -i $connection_id -re "#"
  	      sleep 1
          if {$seq == "default"} {set seq ""} 
          expect -i $connection_id -re "#"
          exp_send -i $connection_id "ipv6 traffic-filter $acl_id vlan $Vlan_ID $bind_direction $seq\r"  
          sleep 3
          expect -i $connection_id -re "#"
          set Ret $expect_out(buffer) 
          exp_send -i $connection_id "exit\r"
         
      }
  }
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return $Ret
}

################################################################################
#*******************************************************************************
#  Function Name	: _ntgrVlanIPv6ACLDisable
#
#  Description    	: This function is called to enable ipv6 ACL on Vlan
#         
#  Usage          	: _ntgrVlanIPv6ACLDisable <switch_name> <interface_list> <Vlan_ID> <bind_direction>
#
#  Reversion      : Create by kenddy xie 2010-07-29
#
#*******************************************************************************
proc _ntgrVlanIPv6ACLDisable {switch_name interface_list Vlan_ID {bind_direction in}} {
	
  set Ret {}

  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"  	           
  foreach int_list $interface_list {
      set acl_id [lindex $int_list 0]
      set property_interface_list [lindex $int_list 1]
      foreach acl_int_list $property_interface_list {
          set interface [lindex $acl_int_list 0]
          set seq [lindex $acl_int_list 1]
          exp_send -i $connection_id "configure\r"
          expect -i $connection_id -re "#"
  	      sleep 1
          if {$seq == "default"} {set seq ""} 
          exp_send -i $connection_id "no ipv6 traffic-filter $acl_id vlan $Vlan_ID $bind_direction $seq\r"  
          sleep 3
          expect -i $connection_id -re "#" 
          exp_send -i $connection_id "exit\r"
         
      }
  }
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return $Ret
}





#*******************************************************************************
#
#  Function Name  : _ntgrGetIPACLRuleStatus
#
#  Description    : This function is used to get ip acl rule status of a switch.
#
#  Usage          : _ntgrGetIPACLRuleStatus <switch_name> <aclName>
#
#*******************************************************************************
proc _ntgrGetIPACLRuleStatus {switch_name aclName} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ip access-lists $aclName\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip access-lists $aclName"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase {Rule Status\.+\s+([^\n]+)} $output match result
    set result [string trim $result]

    Netgear_log_file "lib-global-command.tcl" "get mac ip rule status on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}



#*******************************************************************************
#
#  Function Name  : _ntgrGetIPv6ACLRuleStatus
#
#  Description    : This function is used to get ipv6 acl rule status of a switch.
#
#  Usage          : _ntgrGetIPv6ACLRuleStatus <switch_name> <aclName>
#
#*******************************************************************************
proc _ntgrGetIPv6ACLRuleStatus {switch_name aclName} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ipv6 access-lists $aclName\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ipv6 access-lists $aclName"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase {Rule Status\.+\s+([^\n]+)} $output match result
    set result [string trim $result]

    Netgear_log_file "lib-global-command.tcl" "get mac ipv6 rule status on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#  Function Name	: _ntgrAddRouteMap
#
#  Description    	: This function is called to add Route-map on switch
#         
#  Usage          	: _ntgrAddRouteMap <switch_name>  <route_map_list>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc _ntgrAddRouteMap {switch_name route_map_list } {
    
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
    foreach map_list $route_map_list {
      set map_name [lindex $map_list 0]
      set map_rule_list [lindex $map_list 1]
      
	  set map_rule [lindex $map_rule_list 0]
	  set macth_rule [lindex $map_rule_list 1]
	  set set_rule [lindex $map_rule_list 2]
	  
	  set map_action [lindex $map_rule 0]
	  expect -i $connection_id -re "#"
   	  exp_send -i $connection_id "configure\r"
  	  sleep 1
  	  expect -i $connection_id -re "#"
	  if {$map_action != "default"} {
	      set map_value [lindex $map_rule 1]
	      exp_send -i $connection_id "route-map $map_name $map_action $map_value\r"
		  sleep 1
  	      expect -i $connection_id -re "#"
	  } else {
	      exp_send -i $connection_id "route-map $map_name \r"
		  sleep 1
  	      expect -i $connection_id -re "#"
	  }

	  set match_lengh [lindex $macth_rule 0]
	  set match_lengh_action [lindex $match_lengh 0]
	  if {$match_lengh_action != "default"} {
	      set match_lengh_min [lindex $match_lengh 1]
		  set match_lengh_max [lindex $match_lengh 2]
	      exp_send -i $connection_id "match length $match_lengh_min $match_lengh_max\r"
		  sleep 1
  	      expect -i $connection_id -re "#"
	  }
	  
	  set match_ip [lindex $macth_rule 1]
	  set match_ip_action [lindex $match_ip 0]
	  if {$match_ip_action != "default"} {
	      set match_ip_addr [lindex $match_ip 1]
	      exp_send -i $connection_id "match ip address $match_ip_addr\r"
		  sleep 1
  	      expect -i $connection_id -re "#"
	  }
	  
	  set match_ipv6 [lindex $macth_rule 2]
	  set match_ipv6_action [lindex $match_ipv6 0]
	  if {$match_ipv6_action != "default"} {
	      set match_ipv6_addr [lindex $match_ipv6 1]
	      exp_send -i $connection_id "match ipv6 address prefix-list $match_ipv6_addr\r"
		  sleep 1
  	      expect -i $connection_id -re "#"
	  }
	  
	  set match_mac [lindex $macth_rule 3]
	  set match_mac_action [lindex $match_mac 0]
	  if {$match_mac_action != "default"} {
	      set match_mac_addr [lindex $match_mac 1]
	      exp_send -i $connection_id "match mac-list $match_mac_addr\r"
		  sleep 1
  	      expect -i $connection_id -re "#"
	  }
	  
	  
	  set set_default_action [lindex $set_rule 0]
	  set set_item [lindex $set_rule 1]
	  set set_item_value [lindex $set_rule 2]
	  if {$set_default_action == "enable"} {
		  exp_send -i $connection_id "set ip default $set_item $set_item_value\r"
		  sleep 1
  	      expect -i $connection_id -re "#"
	  } elseif {$set_default_action == "default"} {
	  } elseif {$set_default_action == "interface"} {
	      exp_send -i $connection_id "set interface $set_item_value\r"
		  sleep 1
  	      expect -i $connection_id -re "#"
	  } else {
	      exp_send -i $connection_id "set ip $set_item $set_item_value\r"
		  sleep 1
  	      expect -i $connection_id -re "#"
	  }
	  exp_send -i $connection_id "exit\r"
	  exp_send -i $connection_id "exit\r"
   }
    
	Netgear_disconnect_switch $switch_name
	return 1
}


#*******************************************************************************
#  Function Name	: _ntgrDeleteRouteMap
#
#  Description    	: This function is called to delete Route-map on switch
#         
#  Usage          	: _ntgrDeleteRouteMap <switch_name>  <route_map_name>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc _ntgrDeleteRouteMap {switch_name route_map_name } {
    
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
    
    exp_send -i $connection_id "no route-map $route_map_name\r"
    sleep 1
    expect -i $connection_id -re "#"
	  
    exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return 1
}

#*******************************************************************************
#  Function Name	: _ntgrBlindRouteMapOnInterface
#
#  Description    	: This function is called to blind Route-map on interface on switch
#         
#  Usage          	: _ntgrBlindRouteMapOnInterface <switch_name> <route_map_name> <interface>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc _ntgrBlindRouteMapOnInterface {switch_name route_map_name interface } {
    set reg {^[0-9a-zA-Z]+$} 
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
    if {[regexp -nocase {vlan(\d+)} $interface dummy vlanid]} {
          set interface "vlan"
      } else {
          set mflag  [regexp $reg $interface]
          if {$mflag} {set interface [_ntgrGetChannelLogicalIfName $connection_id $interface]}
      }
	  
	exp_send -i $connection_id "interface $interface\r"
    expect -i $connection_id -re "#"
    sleep 1
    exp_send -i $connection_id "ip policy route-map $route_map_name\r"  
    sleep 1
    expect -i $connection_id -re "#"
	  
    exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return 1
}

#*******************************************************************************
#  Function Name	: _ntgrUnBlindRouteMapOnInterface
#
#  Description    	: This function is called to unblind Route-map on interface on switch
#         
#  Usage          	: _ntgrUnBlindRouteMapOnInterface <switch_name> <route_map_name> <interface>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc _ntgrUnBlindRouteMapOnInterface {switch_name route_map_name interface } {
    set reg {^[0-9a-zA-Z]+$} 
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
    if {[regexp -nocase {vlan(\d+)} $interface dummy vlanid]} {
          set interface "vlan"
      } else {
          set mflag  [regexp $reg $interface]
          if {$mflag} {set interface [_ntgrGetChannelLogicalIfName $connection_id $interface]}
      }
	  
	exp_send -i $connection_id "interface $interface\r"
    expect -i $connection_id -re "#"
    sleep 1
    exp_send -i $connection_id "no ip policy route-map $route_map_name\r"  
    sleep 1
    expect -i $connection_id -re "#"
	  
    exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return 1
}

#*******************************************************************************
#  Function Name	: _ntgrDeleteIPACLIDINRouteMap
#
#  Description    	: This function is called to delete match ip acl in Route-map on switch
#         
#  Usage          	: _ntgrDeleteIPACLIDINRouteMap <switch_name>  <route_map_name> <IP_ACL_ID>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc _ntgrDeleteIPACLIDINRouteMap {switch_name route_map_name IP_ACL_ID} {
    
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
    
    exp_send -i $connection_id "route-map $route_map_name\r"
    sleep 1
    expect -i $connection_id -re "#"
	
	exp_send -i $connection_id "no match ip address $IP_ACL_ID\r"
    sleep 1
    expect -i $connection_id -re "#"
	
    exp_send -i $connection_id "exit\r"
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "exit\r"
	
	Netgear_disconnect_switch $switch_name
	return 1
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetRouteMapStatus
#
#  Description    : This function is used to get route-map status of a switch.
#
#  Usage          : _ntgrGetRouteMapStatus <switch_name> <route_map_name>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc _ntgrGetRouteMapStatus {switch_name route_map_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show route-map $route_map_name\r"
    exp_sleep 1
    expect -i $cnn_id -re "show route-map $route_map_name"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)   
    set result ""
    regexp -nocase {Policy routing matches:\s+([0-9]+)\s+packets\.*} $output match result
    set result [string trim $result]

    Netgear_log_file "lib-global-command.tcl" "get route-map status on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}



#*******************************************************************************
#  Function Name	: _ntgrLoopAddRouteMapIPACL
#
#  Description    	: This function is called to loop add Route-map on switch
#         
#  Usage          	: _ntgrLoopAddRouteMapIPACL <switch_name>  <map_name> <match_ip_addr>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc _ntgrLoopAddRouteMapIPACL {switch_name map_name match_ip_addr } {

	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]

    expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
	exp_send -i $connection_id "route-map $map_name \r"
	sleep 1
  	expect -i $connection_id -re "#"
	exp_send -i $connection_id "match ip address $match_ip_addr\r"
    sleep 1
  	expect -i $connection_id -re "#"  
	exp_send -i $connection_id "set ip next-hop 90.1.1.2\r"
    sleep 1
    expect -i $connection_id -re "#"
	exp_send -i $connection_id "exit\r"
	exp_send -i $connection_id "exit\r"

	Netgear_disconnect_switch $switch_name
	return 1
}


#*******************************************************************************
#
#  Function Name  : _ntgrCheckIPPolicyStatus
#
#  Description    : This function is used to check ip policy status of a switch.
#
#  Usage          : _ntgrCheckIPPolicyStatus <switch_name> <route_map_name> <port>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc _ntgrCheckIPPolicyStatus {switch_name route_map_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ip policy\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip policy"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)   
    set result 0
	
    if {[regexp -nocase $port $output] && [regexp -nocase $route_map_name $output]} {
	   set result 1
	   Netgear_log_file "lib-ip-acl.tcl" "Route-MAP $route_map_name is exist."
	} else {
	   Netgear_log_file "lib-ip-acl.tcl" "Error:  Route-MAP $route_map_name is not exist."
	}

    Netgear_disconnect_switch $switch_name

    return $result
}
#*******************************************************************************
#
#  Function Name  : _ntgrAddIPACLRemark
#
#  Description    : This function is used to add IPACL Remark,<remark> like "\"123456\"" ,must include Double quotation marks
#
#  Usage          : _ntgrAddIPACLRemark <switch_name> <ip_acl_id> <ip_acl_remark>
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc _ntgrAddIPACLRemark {switch_name ip_acl_id ip_acl_remark} {
   
    set Ret {}
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]

    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "ip access-list $ip_acl_id\r"
    sleep 1
    expect -i $connection_id "ip access-list $ip_acl_id"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "remark $ip_acl_remark\r"
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
    Netgear_disconnect_switch $switch_name
    return $Ret
}
#*******************************************************************************
#
#  Function Name  : CALDelteIPACLRemark
#
#  Description    : This function is used to delete IPACL Remark,<remark> like "\"123456\"" ,must include Double quotation marks
#
#  Usage          : CALDelteIPACLRemark <switch_name> <ip_acl_id> <ip_acl_remark>
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALDelteIPACLRemark {switch_name ip_acl_id ip_acl_remark} {
   
    set Ret {}
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]

    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "ip access-list $ip_acl_id\r"
    sleep 1
    expect -i $connection_id "ip access-list $ip_acl_id"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "no remark $ip_acl_remark\r"
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
    Netgear_disconnect_switch $switch_name
    return $Ret
}
#*******************************************************************************
#
#  Function Name  : CALCheckIPACLRemarkAssociatedRule
#
#  Description    : This function is used to check IPACL Remark with rule is associated,<remark> like "\"123456\"" ,must include Double quotation marks
#
#  Usage          : CALCheckIPACLRemarkAssociatedRule <switch_name> <remark> <expect_rule>
#
#  return         :  1 or 0
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALCheckIPACLRemarkAssociatedRule  {switch_name remark expect_rule} {
    set output {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show running-config | section $remark include \"$expect_rule\"\r"
    exp_sleep 1
    expect -i $cnn_id -re "show running-config"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }  
	#set result [regexp -all "$remark" $output]
    Netgear_disconnect_switch $switch_name
	set tmpList [split $output \n]
	set actual_rule [lsearch $tmpList $expect_rule]
	set result 0
	if {$actual_rule > 1} {
	set result 1 }
    return $result	
} 
#*******************************************************************************
#
#  Function Name  : CALCheckIPACLRemarkExist
#
#  Description    : This function is used to check IPACL Remark is Exist,
#                    <remark> like "\"123456\"" ,must include Double quotation marks
#
#  Usage          : CALCheckIPACLRemarkExist <switch_name> <remark>
#
#  Return         : 1 or 0
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALCheckIPACLRemarkExist  {switch_name remark} {
    set output {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show running-config | section $remark\r"
    exp_sleep 1
    expect -i $cnn_id -re "show running-config"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }  
	#set result [regexp -all "$remark" $output]
    Netgear_disconnect_switch $switch_name
	set text [lindex $output [expr [lsearch $output "remark"] + 1]]
	set temp [join [split $text \n] ""]
	set actual_remark "\""
	append actual_remark $temp
	append actual_remark "\""
    set result [string equal $remark $actual_remark]
	Netgear_log_file "lib-ip-acl.tcl" "actual_remark=$actual_remark"
	Netgear_log_file "lib-ip-acl.tcl" "except_remark=$remark"
    return $result	
} 
#*******************************************************************************
#
#  Function Name  : CALDeleteIPACLRule
#
#  Description    : This function is used to delete IPACL rule
#
#  Usage          : CALDeleteIPACLRule <switch_name> <ip_acl_id> <rule_id>
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALDeleteIPACLRule  {switch_name ip_acl_id rule_id} {
    set Ret {}
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]

    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "ip access-list $ip_acl_id\r"
    sleep 1
    expect -i $connection_id "ip access-list $ip_acl_id"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "no $rule_id\r"
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
    Netgear_disconnect_switch $switch_name
    return $Ret	
} 

#*******************************************************************************
#
#  Function Name  : CALGetIPACLRuleByID
#
#  Description    : This function is used to delete IPACL rule
#
#  Usage          : CALGetIPACLRuleByID <switch_name> <ip_acl_id> <rule_id>
#
#  return         : rule [list]
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALGetIPACLRuleByID  {switch_name ip_acl_id rule_id} {

    set output {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show ip access-lists $ip_acl_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip access-lists"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }  
	#set result [regexp -all "$remark" $output]
    Netgear_disconnect_switch $switch_name
    set buffer ""
	regexp "Sequence Number: $rule_id *?(.+ACL Hit Count)" $output mat buffer
	set strLst [split $buffer \n]
	set retLst []
	for {set i 0} {$i < [llength $strLst]} {incr i} {
		set strIdx [lindex $strLst $i]
		set strItem ""
		set strValue ""
		set ret ""
		set flag [regexp {([\w ]+)\.+ (.+)} $strIdx mat2 strItem strValue]
		if {$flag} {
			lappend retLst [string trim $strItem "\n "] [string trim $strValue "\n "]
		}
	}
	return $retLst
}
#*******************************************************************************
#
#  Function Name  : CALICheckIPACLRuleWithID
#
#  Description    : This function is used to  check IP ACL rule by ID and expect rule
#
#  Usage          : CALICheckIPACLRuleWithID <switch_name> <ip_acl_id> <rule_id> <expect_rule>
#
#  return         : 1 or 0
#
#  Author         : zhenwei.li
#
#******************************************************************************* 
proc CALICheckIPACLRuleWithID  {switch_name ip_acl_id rule_id expect_rule} {
    set actual_rule [CALGetIPACLRuleByID $switch_name $ip_acl_id $rule_id]
	if {$expect_rule == $actual_rule} {
		set result 1
		} else { set result 0}
		
	return $result
}
#*******************************************************************************
#
#  Function Name  : CALAddIPACLAndCheckResult
#
#  Description    : This function is used to check IP ACL add success or fail
#
#  Usage          : CALAddIPACLAndCheckResult <switch_name> <ip_acl_rule_list>
#
#  return         : 1 or 0 (1--success,0--fail) 
#
#  Author         : zhenwei.li
#
#******************************************************************************* 
proc CALAddIPACLAndCheckResult {switch_name ip_acl_rule_list} {
  set Ret {}
  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	  expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
  	_ntgrAddIPACLwithoutLogin $ip_acl_rule_list $connection_id Ret
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
    set index [string first "Failed" $Ret]
	if { $index >0} { 
	set result 0
	} else {
	set result 1
	} 
	return $result
}
#*******************************************************************************
#
#  Function Name  : CALResequenceIPACL
#
#  Description    : This function is used to check IP ACL add success or fail
#
#  Usage          : CALResequenceIPACL <switch_name> <ip_acl_rule_list>
#
#
#  Author         : zhenwei.li
#
#******************************************************************************* 
proc CALResequenceIPACL {switch_name ip_acl_id rule_id {increment 10}} {
    Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	  expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
    exp_send -i $connection_id "ip access-list resequence $ip_acl_id $rule_id $increment\r"
    sleep 1
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name

}
#*******************************************************************************
#
#  Function Name  : CALAddIPv6ACLRemark
#
#  Description    : This function is used to add IPv6ACL Remark,<remark> like "\"123456\"" ,must include Double quotation marks
#
#  Usage          : CALAddIPv6ACLRemark <switch_name> <ip_acl_id> <ip_acl_remark>
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALAddIPv6ACLRemark {switch_name ip_acl_id ip_acl_remark} {
   
    set Ret {}
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]

    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "ipv6 access-list $ip_acl_id\r"
    sleep 1
    expect -i $connection_id "ipv6 access-list $ip_acl_id"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "remark $ip_acl_remark\r"
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
    Netgear_disconnect_switch $switch_name
    return $Ret
}
#*******************************************************************************
#
#  Function Name  : CALDelteIPv6ACLRemark
#
#  Description    : This function is used to delete IPv6ACL Remark,<remark> like "\"123456\"" ,must include Double quotation marks
#
#  Usage          : CALDelteIPv6ACLRemark <switch_name> <ip_acl_id> <ip_acl_remark>
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALDelteIPv6ACLRemark {switch_name ip_acl_id ip_acl_remark} {
   
    set Ret {}
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]

    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "ipv6 access-list $ip_acl_id\r"
    sleep 1
    expect -i $connection_id "ipv6 access-list $ip_acl_id"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "no remark $ip_acl_remark\r"
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
    Netgear_disconnect_switch $switch_name
    return $Ret
}
#*******************************************************************************
#
#  Function Name  : CALDeleteIPv6ACLRule
#
#  Description    : This function is used to delete IPv6ACL rule
#
#  Usage          : CALDeleteIPv6ACLRule <switch_name> <ip_acl_id> <rule_id>
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALDeleteIPv6ACLRule  {switch_name ip_acl_id rule_id} {
    set Ret {}
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]

    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "ipv6 access-list $ip_acl_id\r"
    sleep 1
    expect -i $connection_id "ipv6 access-list $ip_acl_id"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "no $rule_id\r"
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
    Netgear_disconnect_switch $switch_name
    return $Ret	
} 
#*******************************************************************************
#
#  Function Name  : CALGetIPv6ACLRuleByID
#
#  Description    : This function is used to delete IPv6ACL rule
#
#  Usage          : CALGetIPv6ACLRuleByID <switch_name> <ip_acl_id> <rule_id>
#
#  return         : rule [list]
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALGetIPv6ACLRuleByID  {switch_name ip_acl_id rule_id} {

    set output {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show ipv6 access-lists $ip_acl_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ipv6 access-lists"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }  
	#set result [regexp -all "$remark" $output]
    Netgear_disconnect_switch $switch_name
    set buffer ""
	regexp "Sequence Number: $rule_id *?(.+ACL Hit Count)" $output mat buffer
	set strLst [split $buffer \n]
	set retLst []
	for {set i 0} {$i < [llength $strLst]} {incr i} {
		set strIdx [lindex $strLst $i]
		set strItem ""
		set strValue ""
		set ret ""
		set flag [regexp {([\w ]+)\.+ (.+)} $strIdx mat2 strItem strValue]
		if {$flag} {
			lappend retLst [string trim $strItem "\n "] [string trim $strValue "\n "]
		}
	}
	return $retLst
}
#*******************************************************************************
#
#  Function Name  : CALICheckIPv6ACLRuleWithID
#
#  Description    : This function is used to  check IP ACL rule by ID and expect rule
#
#  Usage          : CALICheckIPv6ACLRuleWithID <switch_name> <ip_acl_id> <rule_id> <expect_rule>
#
#  return         : 1 or 0
#
#  Author         : zhenwei.li
#
#******************************************************************************* 
proc CALICheckIPv6ACLRuleWithID  {switch_name ip_acl_id rule_id expect_rule} {
    set actual_rule [CALGetIPv6ACLRuleByID $switch_name $ip_acl_id $rule_id]
	if {$expect_rule == $actual_rule} {
		set result 1
		} else { set result 0}
	return $result
}
#********************************************************************************
#  Function Name	: CALAddIPv6ACLAndCheckResult
#
#  Description    	: This function is used to add ACL on switch,and check result
#         
#  Usage          	: CALAddIPv6ACLAndCheckResult <switch_name> <ip_acl_rule_list> 
#
#
#             rule1        rule2    rule3+      rule4       rule5+
#             permit    |every   |
#              deny     |        |assign-queue|
#                       |        |log         |
#                       |        |mirror      |
#                       |        |redirect    |
#
#                       |<1-255> |
#                       |icmp    |                       |dscp
#                       |igmp    |                       |flow label
#                       |ipv6    | SIP Sport  |DIP Dport |assign queue
#                       |tcp     |                       |log 
#                       |udp     |                       |mirror
#                                                        |redirect 
#  return         : 1 or 0
#
#  Author         : zhenwei.li
#*******************************************************************************
proc CALAddIPv6ACLAndCheckResult {switch_name ip_acl_rule_list} {
    set Ret [_ntgrAddIPv6ACL $switch_name $ip_acl_rule_list]
	 set index [string first "Failed" $Ret]
	if { $index >0} { 
	set result 0
	} else {
	set result 1
	} 
	return $result
}
#*******************************************************************************
#
#  Function Name  : CALResequenceIPACL
#
#  Description    : This function is used to Resequence IP ACL rule
#
#  Usage          : CALResequenceIPACL <switch_name> <ip_acl_rule_list>
#
#
#  Author         : zhenwei.li
#
#******************************************************************************* 
proc CALResequenceIPv6ACL {switch_name ip_acl_id rule_id {increment 10}} {
    Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	  expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
    exp_send -i $connection_id "ipv6 access-list resequence $ip_acl_id $rule_id $increment\r"
    sleep 1
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name

}