####################################################################################
#  File Name   : lib-port-secure.tcl                                               #
#                                                                                  #
#  Description :                                                                   #
#        This file includes functions used for port secure configuration.          #
#                                                                                  #
#  History     :                                                                   #
#        Date          Programmer         Description                              #
#        Mar 15, 2007  Scott Zhang        Created                                  #
#                                                                                  #
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrEnablePortSecurity
#
#  Description    : This function is called to enable port security globally/port level.
#
#  Usage          : _ntgrEnablePortSecurity <switch_name port>
#
#*******************************************************************************
proc _ntgrEnablePortSecurity {switch_name port} {
    
    foreach pt $port {
      set port $pt
    }

	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }  
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$port != {}} {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "port-security\r"
    exp_sleep 1

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisablePortSecurity
#
#  Description    : This function is called to disable port security globally/port level.
#
#  Usage          : _ntgrDisablePortSecurity <switch_name port>
#
#*******************************************************************************
proc _ntgrDisablePortSecurity {switch_name port} {

    foreach pt $port {
      set port $pt
    }
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$port != {}} {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "no port-security\r"
    exp_sleep 1

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetMaxDynamic
#
#  Description    : This function is called to set port MAC dynamic limit.
#
#  Usage          : _ntgrSetMaxDynamic <switch_name port max>
#
#*******************************************************************************
proc _ntgrSetMaxDynamic {switch_name port max} {

    foreach pt $port {
      set port $pt
    }
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "port-security max-dynamic $max\r"
    exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetMaxDynamic
#
#  Description    : This function is called to reset port MAC dynamic limit to default.
#
#  Usage          : _ntgrResetMaxDynamic <switch_name port>
#
#*******************************************************************************
proc _ntgrResetMaxDynamic {switch_name port} {
	  
	  foreach pt $port {
      set port $pt
    }
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no port-security max-dynamic\r"
    exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetMaxStatic
#
#  Description    : This function is called to set port MAC static limit.
#
#  Usage          : _ntgrSetMaxStatic <switch_name port max>
#
#*******************************************************************************
proc _ntgrSetMaxStatic {switch_name port max} {

    foreach pt $port {
      set port $pt
    }
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "port-security max-static $max\r"
    exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetMaxStatic
#
#  Description    : This function is called to reset port MAC static limit to default.
#
#  Usage          : _ntgrResetMaxStatic <switch_name port>
#
#*******************************************************************************
proc _ntgrResetMaxStatic {switch_name port} {

    foreach pt $port {
      set port $pt
    }	  
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no port-security max-static\r"
    exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigStaticAddr
#
#  Description    : This function is called to configure static port security.
#                   If addr == move, then move the dynamic to static.
#
#  Usage          : _ntgrConfigStaticAddr <switch_name port addr>
#
#*******************************************************************************
proc _ntgrConfigStaticAddr {switch_name port addr vlan_id} {

    foreach pt $port {
      set port $pt
    }    
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }    

    set temp_str {}
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    expect -i $cnn_id "#" {}
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    foreach ad $addr {
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "port-security mac-address $ad $vlan_id\r"
        exp_sleep 1        
        expect -i $cnn_id -re "#"
        append temp_str $expect_out(buffer)
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }	 
    return $temp_str
}

#*******************************************************************************
#
#  Function Name  : _ntgrDeleteStaticAddr
#
#  Description    : This function is called to configure static port security.
#
#  Usage          : _ntgrDeleteStaticAddr <switch_name port addr>
#
#*******************************************************************************
proc _ntgrDeleteStaticAddr {switch_name port addr vlan_id} {

    foreach pt $port {
      set port $pt
    }
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }  
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    foreach ad $addr {
        exp_send -i $cnn_id "no port-security mac-address $ad $vlan_id\r"
        exp_sleep 1
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }	
}


#*******************************************************************************
#
#  Function Name  : _ntgrMoveDynamicAddr
#
#  Description    : This function is called to move dynamic mac-address to static.
#
#  Usage          : _ntgrMoveDynamicAddr <switch_name port>
#
#  Revison:       : added by kenddy xie 2010-08-06
#
#*******************************************************************************
proc _ntgrMoveDynamicAddr {switch_name port} {

    foreach pt $port {
      set port $pt
    }
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }  
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "port-security mac-address move\r"
    exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }	
}
