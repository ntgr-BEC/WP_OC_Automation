####################################################################################
#  File Name   : lib-dot1x.tcl                                                     #
#                                                                                  #
#  Description :                                                                   #
#        This file includes functions used for 802.1X configuration.               #
#                                                                                  #
#  History     :                                                                   #
#        Date          Programmer         Description                              #
#        May 11, 2007  Scott Zhang        Created				   #
#	 May 14,2007   Nina Cheng         Modify                                   #
#                                                                                  #
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrEnableDot1X
#
#  Description    : This function is called to enable 802.1X on a switch.
#
#  Usage          : _ntgrEnableDot1X <switch_name>
#
#*******************************************************************************
proc _ntgrEnableDot1X {switch_name} {
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "dot1x system-auth-control\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableDot1X
#
#  Description    : This function is called to disable 802.1X on a switch.
#
#  Usage          : _ntgrDisableDot1X <switch_name>
#
#*******************************************************************************
proc _ntgrDisableDot1X {switch_name} {
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no dot1x system-auth-control\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetDot1XPortAuthMode
#
#  Description    : This function is called to set 802.1X port authentication mode.
#
#  Usage          : _ntgrSetDot1XPortAuthMode <switch_name portlist mode>
#
#*******************************************************************************
proc _ntgrSetDot1XPortAuthMode {switch_name portlist mode} {
    if {$mode != "default"} {
	    Netgear_connect_switch $switch_name
	    set cnn_id [_get_switch_handle $switch_name]
	
	    exp_send -i $cnn_id "configure\r"
	    exp_sleep 1
	    if {$portlist == "all"} {
	    	exp_send -i $cnn_id "dot1x port-control all $mode\r"
	    } else {
		    foreach pt $portlist {
		        exp_send -i $cnn_id "interface $pt\r"
		        exp_sleep 1
		        if {$mode == {}} {
		            exp_send -i $cnn_id "no dot1x port-control\r"
		        } else {
		            exp_send -i $cnn_id "dot1x port-control $mode\r"
		        }
		        exp_send -i $cnn_id "exit\r"
		        exp_sleep 1
		    }
	    }
	    exp_send -i $cnn_id "exit\r"
	    exp_sleep 1
	    Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetDot1XUser
#
#  Description    : This function is called to set 802.1X user.
#
#  Usage          : _ntgrSetDot1XUser <switch_name portlist user>
#
#*******************************************************************************
proc _ntgrSetDot1XUser {switch_name portlist user} {
    if {$user != "default"} {
	    Netgear_connect_switch $switch_name
	    set cnn_id [_get_switch_handle $switch_name]
	
	    exp_send -i $cnn_id "configure\r"
	    exp_sleep 1
	    if {$portlist == "all"} {
	    	if {$user == {}} {
			exp_send -i $cnn_id "no dot1x user $user all\r"
		} else {
		    	exp_send -i $cnn_id "dot1x user $user all\r"
		}
	    } else {
		    foreach pt $portlist {
		        if {$user == {}} {
		            exp_send -i $cnn_id "no dot1x user $user $pt\r"
		        } else {
		            exp_send -i $cnn_id "dot1x user $user $pt\r"
		        }
		        exp_send -i $cnn_id "exit\r"
		        exp_sleep 1
		    }
	    }
	    exp_send -i $cnn_id "exit\r"
	    exp_sleep 1
	    Netgear_disconnect_switch $switch_name
    }
}
#*******************************************************************************
#
#  Function Name  : _ntgrSetDot1XDefaultLogin
#
#  Description    : This function is called to set the default login for 802.1X.
#
#  Usage          : _ntgrSetDot1XDefaultLogin <switch_name login>
#
#*******************************************************************************
proc _ntgrSetDot1XDefaultLogin {switch_name login} {
    if {$login != "default"} {
	    Netgear_connect_switch $switch_name
	    set cnn_id [_get_switch_handle $switch_name]
	
	    exp_send -i $cnn_id "configure\r"
	    exp_sleep 1
	    exp_send -i $cnn_id "dot1x default-login $login\r"
	    exp_sleep 1
	
	    Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetDot1XLogin
#
#  Description    : This function is called to set the login for 802.1X.
#
#  Usage          : _ntgrSetDot1XLogin <switch_name user listname>
#
#*******************************************************************************
proc _ntgrSetDot1XLogin {switch_name user listname} {
    if {$listname != "default"} {
	    Netgear_connect_switch $switch_name
	    set cnn_id [_get_switch_handle $switch_name]
	
	    exp_send -i $cnn_id "configure\r"
	    exp_sleep 1
	    exp_send -i $cnn_id "dot1x login $user $listname\r"
	    exp_sleep 1
	    exp_send -i $cnn_id "exit\r"
	    exp_sleep 1
	    Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetDot1XPortMethod
#
#  Description    : This function is called to set 802.1X port access mode.
#
#  Usage          : _ntgrSetDot1XPortMethod <switch_name portlist mode>
#
#*******************************************************************************
proc _ntgrSetDot1XPortMethod {switch_name portlist mode} {
    if {$mode != "default"} {
	    Netgear_connect_switch $switch_name
	    set cnn_id [_get_switch_handle $switch_name]
	
	    exp_send -i $cnn_id "configure\r"
	    exp_sleep 1
	    foreach pt $portlist {
	        exp_send -i $cnn_id "interface $pt\r"
	        exp_sleep 1
	        if {$mode == {}} {
	            exp_send -i $cnn_id "no dot1x port-method\r"
	        } else {
	            exp_send -i $cnn_id "dot1x port-method $mode\r"
	        }
	        exp_send -i $cnn_id "exit\r"
	        exp_sleep 1
	    }
	    Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetDot1XPortTimeroftx
#
#  Description    : This function is called to set 802.1X port tx timer.
#
#  Usage          : _ntgrSetDot1XPortTimeroftx <switch_name portlist timer>
#
#*******************************************************************************
proc _ntgrSetDot1XPortTimeroftx {switch_name portlist timer} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach pt $portlist {
        exp_send -i $cnn_id "interface $pt\r"
        exp_sleep 1
        if {$timer == {}} {
            exp_send -i $cnn_id "no dot1x timeout tx-period\r"
        } else {
            exp_send -i $cnn_id "dot1x timeout tx-period $timer\r"
        }
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetDot1XPortTimerofsupp
#
#  Description    : This function is called to set 802.1X port supp timer.
#
#  Usage          : _ntgrSetDot1XPortTimerofsupp <switch_name portlist timer>
#
#*******************************************************************************
proc _ntgrSetDot1XPortTimerofsupp {switch_name portlist timer} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach pt $portlist {
        exp_send -i $cnn_id "interface $pt\r"
        exp_sleep 1
        if {$timer == {}} {
            exp_send -i $cnn_id "no dot1x timeout supp-timeout\r"
        } else {
            exp_send -i $cnn_id "dot1x timeout supp-timeout $timer\r"
        }
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetDot1XPortTimerofserver
#
#  Description    : This function is called to set 802.1X port server timer.
#
#  Usage          : _ntgrSetDot1XPortTimerofserver <switch_name portlist timer>
#
#*******************************************************************************
proc _ntgrSetDot1XPortTimerofserver {switch_name portlist timer} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach pt $portlist {
        exp_send -i $cnn_id "interface $pt\r"
        exp_sleep 1
        if {$timer == {}} {
            exp_send -i $cnn_id "no dot1x timeout server-timeout\r"
        } else {
            exp_send -i $cnn_id "dot1x timeout server-timeout $timer\r"
        }
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetDot1XPortTimerofreauth
#
#  Description    : This function is called to set 802.1X port reauth timer.
#
#  Usage          : _ntgrSetDot1XPortTimerofreauth <switch_name portlist timer>
#
#*******************************************************************************
proc _ntgrSetDot1XPortTimerofreauth {switch_name portlist timer} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach pt $portlist {
        exp_send -i $cnn_id "interface $pt\r"
        exp_sleep 1
        if {$timer == {}} {
            exp_send -i $cnn_id "no dot1x timeout reauth-period\r"
        } else {
            exp_send -i $cnn_id "dot1x timeout reauth-period $timer\r"
        }
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetDot1XPortTimerofquiet
#
#  Description    : This function is called to set 802.1X port quiet timer.
#
#  Usage          : _ntgrSetDot1XPortTimerofquiet <switch_name portlist timer>
#
#*******************************************************************************
proc _ntgrSetDot1XPortTimerofquiet {switch_name portlist timer} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach pt $portlist {
        exp_send -i $cnn_id "interface $pt\r"
        exp_sleep 1
        if {$timer == {}} {
            exp_send -i $cnn_id "no dot1x timeout quiet-period\r"
        } else {
            exp_send -i $cnn_id "dot1x timeout quiet-period $timer\r"
        }
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableDot1XReauth
#
#  Description    : This function is called to enable 802.1X reauthentication.
#
#  Usage          : _ntgrEnableDot1XReauth <switch_name portlist>
#
#*******************************************************************************
proc _ntgrEnableDot1XReauth {switch_name portlist} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach pt $portlist {
        exp_send -i $cnn_id "interface $pt\r"
        exp_sleep 1
        exp_send -i $cnn_id "dot1x re-authentication\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableDot1XReauth
#
#  Description    : This function is called to disable 802.1X reauthentication.
#
#  Usage          : _ntgrDisableDot1XReauth <switch_name portlist>
#
#*******************************************************************************
proc _ntgrDisableDot1XReauth {switch_name portlist} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach pt $portlist {
        exp_send -i $cnn_id "interface $pt\r"
        exp_sleep 1
        exp_send -i $cnn_id "no dot1x re-authentication\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetDot1XMaxreq
#
#  Description    : This function is called to set the max req for 802.1X.
#
#  Usage          : _ntgrSetDot1XMaxreq <switch_name portlist number>
#
#*******************************************************************************
proc _ntgrSetDot1XMaxreq {switch_name portlist number} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach pt $portlist {
        exp_send -i $cnn_id "interface $pt\r"
        exp_sleep 1
        if {$number == {}} {
            exp_send -i $cnn_id "no dot1x max-req $number\r"
        } else {
            exp_send -i $cnn_id "dot1x max-req $number\r"
        }
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetDot1XIF
#
#  Description    : This function is called to set the interface for 802.1X.
#
#  Usage          : _ntgrSetDot1XIF <switch_name portlist>
#
#*******************************************************************************
proc _ntgrSetDot1XIF {switch_name portlist} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    foreach pt $portlist {
    	set inter [lindex $pt 0]
    	set property [lindex $pt 1]
    	if {$property != "IF_DEFAULT"} {
    		exp_send -i $cnn_id "configure\r"
    		exp_sleep 1	
	        exp_send -i $cnn_id "interface $inter\r"
	        exp_sleep 1
	        set maxreq [getdot1xMaxreq $property]
	        if {$maxreq != "default"} {
	        	exp_send -i $cnn_id "dot1x max-req $maxreq"
	        }
	        set pcontrol [getdot1xPortcontrol $property]
	        if {$pcontrol != "default"} {
	        	exp_send -i $cnn_id "dot1x port-control $pcontrol\r"
	        }
	        set portreauth [getdot1xPortReauth $property]
	        if {$portreauth != "default"} {
	        	exp_send -i $cnn_id "dot1x re-authentication\r"
	        }
	        set maxuser [getdot1xPortMaxuser $property]
	        if {$maxuser != "default"} {
	        	exp_send -i $cnn_id "dot1x max-user $maxuser\r"
	        }
	        set pmethod [getdot1xPortMethod $property]
	        if {$pmethod != "default"} {
	        	exp_send -i $cnn_id "dot1x port-method $pmethod\r"
	        }
	        set timer [getdot1xPortTimeout $property]
	        if {$timer != "default"} {
		        set tx_timer [lindex $timer 0]
		        set supp_timer [lindex $timer 1]
		        set server_timer [lindex $timer 2]
		        set reauth_timer [lindex $timer 3]
		        set quiet_timer [lindex $timer 4]
		        if {$tx_timer != "default"} {
	                	exp_send -i $cnn_id "dot1x timeout tx-period $tx_timer\r"
	                }
	                if {$supp_timer != "default"} {
		        	exp_send -i $cnn_id "dot1x timeout supp-timeout $supp_timer\r"
		        }
		        if {$server_timer != "default"} {
		        	exp_send -i $cnn_id "dot1x timeout server-timeout $server_timer\r"
		        }
		        if {$reauth_timer != "default"} {
		        	exp_send -i $cnn_id "dot1x timeout reauth-period $reauth_timer\r"
		        }
		        if {$quiet_timer != "default"} {
		        	exp_send -i $cnn_id "dot1x timeout quiet-period $quiet_timer\r"
		        }
	        }
	        exp_send -i $cnn_id "exit\r"
	        exp_sleep 1
	}
    }

    Netgear_disconnect_switch $switch_name
}

