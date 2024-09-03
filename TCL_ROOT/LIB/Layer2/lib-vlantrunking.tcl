#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_allow_vlan
#
#  Description    : This function is called to allow vlanlist to vlan trunk
#         
#  Usage          : _switch_vlan_trunk_allow_vlan <switch_name> <switch_port> <vlan_list> 
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_allow_vlan {switch_name switch_port vlan_list } {

        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "switchport trunk allowed vlan $vlan_list\r"

	exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_add_vlan
#
#  Description    : This function is called to add a vlan to vlan trunk
#         
#  Usage          : _switch_vlan_trunk_add_vlan <switch_name> <switch_port> <vlan_id> 
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_add_vlan {switch_name switch_port vlan_id } {

        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "switchport trunk allowed vlan add $vlan_id\r"

	exp_send -i $cnn_id "exit\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_remove_vlan
#
#  Description    : This function is called to remove a vlan to vlan trunk
#         
#  Usage          : _switch_vlan_trunk_remove_vlan <cnn_id> <switch_port> <vlan_id> 
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_remove_vlan {switch_name switch_port vlan_id } {	

        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
    
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "switchport trunk allowed vlan remove $vlan_id\r"

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_except_vlan
#
#  Description    : This function is called to except a vlan to vlan trunk
#         
#  Usage          : _switch_vlan_trunk_except_vlan <cnn_id> <switch_port> <vlan_id> 
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_except_vlan {switch_name switch_port vlan_id } {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "switchport trunk allowed vlan except $vlan_id\r"

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_native_vlan
#
#  Description    : This function is called to configure native vlan to vlan trunk
#         
#  Usage          : _switch_vlan_trunk_native_vlan <cnn_id> <switch_port> <native_vlan_id> 
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_native_vlan {switch_name switch_port native_vlan_id } {	
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "switchport trunk native vlan $native_vlan_id\r"

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_delete_mode
#
#  Description    : This function is called to delete vlan trunk mode
#         
#  Usage          : _switch_vlan_trunk_delete_mode <cnn_id> <switch_port> 
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_delete_mode {switch_name switch_port } {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "no switchport mode\r"

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_switch_mode
#
#  Description    : This function is called to switch vlan trunk mode
#         
#  Usage          : _switch_vlan_trunk_switch_mode <cnn_id> <switch_port> <mode>
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_switch_mode {switch_name switch_port mode} {
         Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500
        if {$mode == "access"} {
		  exp_send -i $cnn_id "switchport mode access\r"
        } elseif {$mode == "trunk"}  {
          exp_send -i $cnn_id "switchport mode trunk\r"
        } elseif {$mode == "general"}  {
          exp_send -i $cnn_id "switchport mode general\r"
        } else {
          Netgear_log_file "Error: " "Variable mode  only support trunk/access/general" 
        }

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_delete_access
#
#  Description    : This function is called to delete access vlan
#         
#  Usage          : _switch_vlan_trunk_delete_access <cnn_id> <switch_port>  
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_delete_access {switch_name switch_port} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "no switchport access vlan\r"

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_add_vlans_to_port
#
#  Description    : This function is called to add vlan to port 
#         
#  Usage          : _switch_vlan_trunk_add_vlans_to_port <switch_name> <switch_port> <mode> <vlanlist>
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_add_vlans_to_port {switch_name switch_port mode vlanid} {
         Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500
        if {$mode == "access"} {
		  exp_send -i $cnn_id "switchport mode access\r"
          after 500
          exp_send -i $cnn_id "switchport access vlan $vlanid\r"
          after 500
          
        } elseif {$mode == "trunk"}  {
          exp_send -i $cnn_id "switchport mode trunk\r"
          after 500
          exp_send -i $cnn_id "switchport trunk allowed vlan $vlanid\r"
          after 500
        } else {
          Netgear_log_file "Error: " "Variable mode  only support trunk/access" 
        }

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_Get_accessvlan_CFG
#
#  Description    : This function is  to  get access vlan configuration
#         
#  Usage          : _switch_vlan_trunk_Get_accessvlan_CFG <switch_name> <switch_port>  
#                   <expected_mode> <expected_vlanid>
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_Get_accessvlan_CFG {switch_name switch_port expected_mode expected_vlanid} {
       set output {}
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "show running-config interface $switch_port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show running-config interface $switch_port"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }  
    
    set strOutput $output
    
    if {[regexp -nocase -line "switchport mode (\\w+)" $strOutput all real_mode]} {
       Netgear_log_file "lib-vlan.tcl" "real mode is $real_mode"
       if {[string tolower $expected_mode] == [string tolower $real_mode]} {
          set match1 1
       } else {
          set match1 0
       }
    } else {
          set match1 0
    }
       
   if {[regexp -nocase -line "switchport access vlan (\\d+)" $strOutput all real_vlanid]} {
       Netgear_log_file "lib-vlan.tcl" "real access vlan is $real_vlanid"
       if {$expected_vlanid== $real_vlanid} {
          set match2 1
       } else {
          set match2 0
       }
    } else {
          set match2 2
    } 
    
    if {$match1 && $match2==1 } {
     set match 1
    } elseif {$match1 && $match2==2} {
     set match 2
    } else {
     set match 0
    }
    Netgear_disconnect_switch $switch_name
    
    return $match
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_modify_access
#
#  Description    : This function is called to modify access vlan
#         
#  Usage          : _switch_vlan_trunk_modify_access <cnn_id> <switch_port> <vlanid> 
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_modify_access {switch_name switch_port vlanid} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "switchport access vlan $vlanid\r"

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_Get_trunkvlan_CFG
#
#  Description    : This function is  to  get trunk vlan configuration
#         
#  Usage          : _switch_vlan_trunk_Get_trunkvlan_CFG <switch_name> <switch_port>  
#                   <expected_mode> <expected_vlanlist> <expected_nativevlan>
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_Get_trunkvlan_CFG {switch_name switch_port expected_mode expected_vlanlist expected_nativevlan} {
       set output {}
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "show running-config interface $switch_port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show running-config interface $switch_port"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }  
    
    set strOutput $output
    
    if {[regexp -nocase -line "switchport mode (\\w+)" $strOutput all real_mode]} {
       Netgear_log_file "lib-vlan.tcl" "real mode is $real_mode"
       if {[string tolower $expected_mode] == [string tolower $real_mode]} {
          set match1 1
       } else {
          set match1 0
       }
    } else {
          set match1 0
    }
       
   if {[regexp -nocase -line "switchport trunk allowed vlan (.+)" $strOutput all real_vlanlist]} {
       Netgear_log_file "lib-vlan.tcl" "real trunk vlanlist is $real_vlanlist"
       if {[string tolower $expected_vlanlist] == [string tolower $real_vlanlist]} {
          set match2 1
       } else {
          set match2 0
       }
    } else {
          set match2 2
    }
     
   if {[regexp -nocase -line "switchport trunk native vlan (\\d+)" $strOutput all real_nativevlanid]} {
       Netgear_log_file "lib-vlan.tcl" "real native vlan is $real_nativevlanid"
       if {$expected_nativevlan== $real_nativevlanid} {
          set match3 1
       } else {
          set match3 0
       }
    } else {
          set match3 2
    }     
    if {$match1 && $match2==1 && $match3==1 } {
     set match 1
    } elseif {$match1 && $match2==2 && $match3==2} {
     set match 2
    } elseif {$match1 && $match2==1 && $match3==2} {
     set match 3
    } elseif {$match1 && $match2==2 && $match3==1} {
     set match 4
    } else {
     set match 0
    }
    Netgear_disconnect_switch $switch_name
    
    return $match
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_reset_trunkvlan
#
#  Description    : This function is called to reset trunk vlan
#         
#  Usage          : _switch_vlan_trunk_reset_trunkvlan <cnn_id> <switch_port>  
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_reset_trunkvlan {switch_name switch_port} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "no switchport trunk allowed vlan\r"
         exp_sleep 1
        exp_send -i $cnn_id "no switchport trunk native vlan\r"
        exp_sleep 1

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_include_general
#
#  Description    : This function is called to include general vlan
#         
#  Usage          : _switch_vlan_trunk_include_general <cnn_id> <switch_port> <vlanid> 
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_include_general {switch_name switch_port vlanid} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "vlan participation include $vlanid\r"

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_delete_include_general
#
#  Description    : This function is called to delete include vlan
#         
#  Usage          : _switch_vlan_trunk_delete_include_general <cnn_id> <switch_port> <vlanid> 
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_delete_include_general {switch_name switch_port vlanid} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "no vlan participation include $vlanid\r"

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_tagvlan_general
#
#  Description    : This function is called to tag  vlan
#         
#  Usage          : _switch_vlan_trunk_tagvlan_general <cnn_id> <switch_port> <vlanid> 
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_tagvlan_general {switch_name switch_port vlanid} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "vlan tagging $vlanid\r"

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_vlan_trunk_deleting_tag_general
#
#  Description    : This function is called to delete tag  vlan
#         
#  Usage          : _switch_vlan_trunk_deleting_tag_general <cnn_id> <switch_port> <vlanid> 
#       
#
#  Author         : bo.shi
#
#*******************************************************************************
proc _switch_vlan_trunk_deleting_tag_general {switch_name switch_port vlanid} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]


  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
    exp_sleep 1
	exp_send -i $cnn_id "interface $switch_port\r"
	after 500

		exp_send -i $cnn_id "no vlan tagging $vlanid\r"

	exp_send -i $cnn_id "exit\r"
	   sleep 1
    exp_send -i $cnn_id "exit\r"
    sleep 1

    Netgear_disconnect_switch $switch_name
}