#!/bin/sh
################################################################################   
#
#  File Name		  : lib-sntp.tcl
#
#  Description      :
#         This TCL contains functions to configure SNTP
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
#  Function Name	: _ntgrSntpClientModeEnable
#
#  Description    : This function is called to enable SNTP Client mode on the Switch
#         
#  Usage          : _ntgrSntpClientModeEnable <switch_name> <client_mode>
#
#
#*******************************************************************************
proc _ntgrSntpClientModeEnable {switch_name client_mode} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	if {$client_mode == "unicast"} {
		Netgear_log_file "lib-sntp.tcl" "SNTP client mode is unicast on switch $switch_name"
	   	exp_send -i $connection_id "sntp client mode unicast\r"
  		sleep 1
	} elseif {$client_mode == "default"} {
		Netgear_log_file "lib-sntp.tcl" "SNTP client mode is disabled on switch $switch_name"
	   	exp_send -i $connection_id "no sntp client mode\r"
  		sleep 1
	} elseif {$client_mode == "broadcast"} {
		Netgear_log_file "lib-sntp.tcl" "SNTP client mode is broadcast on switch $switch_name"
	   	exp_send -i $connection_id "sntp client mode broadcast\r"
  		sleep 1
	} else {
		Netgear_log_file "lib-sntp.tcl" "Error!Client mode should be either <unicast> or <broadcast> or <default>"
	}
 	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrSntpClientPortConfig
#
#  Description    : This function is called to configure SNTP Client port
#         
#  Usage          : _ntgrSntpClientPortConfig <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrSntpClientPortConfig {switch_name port} {
	Netgear_log_file "lib-sntp.tcl" "SNTP client port is $port on switch $switch_name"
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	if {$port == "default"} {
		exp_send -i $connection_id "no sntp client port\r"
		sleep 1
	} else {
		exp_send -i $connection_id "sntp client port $port\r"
		sleep 1
	}
 	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrSntpUnicastTimerConfig 
#
#  Description    : This function is called to delete SNTP server
#         
#  Usage          : _ntgrSntpUnicastTimerConfig <switch_name> <u_poll_int> 
#                                               <u_poll_ret> <u_poll_tout>
#
#
#*******************************************************************************
proc _ntgrSntpUnicastTimerConfig {switch_name u_poll_int u_poll_ret u_poll_tout} {
	set connection_id [_get_switch_handle $switch_name]
	Netgear_log_file "lib-sntp.tcl" "SNTP Unicast Client Timer Configuration on switch $switch_name"
	Netgear_log_file "lib-sntp.tcl" "POLL INTERVAL = $u_poll_int"
	Netgear_log_file "lib-sntp.tcl" "POLL RETRY = u_poll_ret"
	Netgear_log_file "lib-sntp.tcl" "POLL TIMEOUT = $u_poll_tout"

   	exp_send -i $connection_id "configure\r"
	sleep 1
	if {$u_poll_int == "default"} {
		exp_send -i $connection_id "no sntp unicast client poll-interval\r"
		sleep 1
	} else {
		exp_send -i $connection_id "sntp unicast client poll-interval $u_poll_int\r"
		sleep 1
	}

	if {$u_poll_ret == "default"} {
		exp_send -i $connection_id "no sntp unicast client poll-retry\r"
		sleep 1
	} else {
		exp_send -i $connection_id "sntp unicast client poll-retry $u_poll_ret\r"
		sleep 1
	}
	
	if {$u_poll_tout == "default"} {
		exp_send -i $connection_id "no sntp unicast client poll-timeout\r"
		sleep 1
	} else {
		exp_send -i $connection_id "sntp unicast client poll-timeout $u_poll_tout\r"
		sleep 1
	}	
	
	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrSntpBroadcastTimerConfig 
#
#  Description    : This function is called to delete SNTP server
#         
#  Usage          : _ntgrSntpBroadcastTimerConfig <switch_name> <b_poll_int> 
#                                               
#
#*******************************************************************************
proc _ntgrSntpBroadcastTimerConfig {switch_name b_poll_int} {
	set connection_id [_get_switch_handle $switch_name]
	Netgear_log_file "lib-sntp.tcl" "SNTP Broadcast Client Timer Configuration on switch $switch_name"
	Netgear_log_file "lib-sntp.tcl" "POLL INTERVAL = $b_poll_int"

   	exp_send -i $connection_id "configure\r"
	sleep 1
	if {$b_poll_int == "default"} {
		exp_send -i $connection_id "no sntp broadcast client poll-interval\r"
		sleep 1
	} else {
		exp_send -i $connection_id "sntp broadcast client poll-interval $b_poll_int\r"
		sleep 1
	}
	exp_send -i $connection_id "exit\r"
}
	
#*******************************************************************************
#  Function Name	: _ntgrSntpServerAddIpAddress 
#
#  Description    : This function is called to add SNTP server
#         
#  Usage          : _ntgrSntpServerAddIpAddress <switch_name> <server_list>
#
#
#*******************************************************************************
proc _ntgrSntpServerAddIpAddress {switch_name server_list} {
	set connection_id [_get_switch_handle $switch_name]
	exp_send -i $connection_id "configure\r"
  	sleep 1
	foreach server $server_list {
		set ip_addr   [lindex $server 0]
		set priority [lindex $server 1]
		Netgear_log_file "lib-sntp.tcl" "SNTP Server $ip_addr with priority $priority is added to switch $switch_name"
	   	
		exp_send -i $connection_id "sntp server $ip_addr $priority\r"
		sleep 1
	}
 	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrSntpServerDeleteIpAddress 
#
#  Description    : This function is called to delete SNTP server
#         
#  Usage          : _ntgrSntpServerDeleteIpAddress <switch_name> <server_list>
#
#
#*******************************************************************************
proc _ntgrSntpServerDeleteIpAddress {switch_name server_list} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
	sleep 1
	foreach server $server_list {
		Netgear_log_file "lib-sntp.tcl" "SNTP Server $server is deleted from switch $switch_name"
		exp_send -i $connection_id "no sntp server $server\r"
		sleep 1
	}
 	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrSntpClientConfig 
#
#  Description    : This function is called to configure SNTP client parameters
#         
#  Usage          : _ntgrSntpClientConfig <switch>
#
#
#*******************************************************************************
proc _ntgrSntpClientConfig {switch} {
	Netgear_connect_switch $switch

    set timezone [_ntgrGetTimeZone $switch]
    set zone_name [lindex $timezone 0]
    set offset [lindex $timezone 1]
    _ntgrSetTimeZone $switch $zone_name $offset

	set client_mode [_ntgrGetSntpClientMode $switch]
	_ntgrSntpClientModeEnable $switch $client_mode
	
	set client_port [_ntgrGetSntpClientPort $switch]
	_ntgrSntpClientPortConfig $switch $client_port

	if {$client_mode == "unicast"} {
		set u_poll_int  [_ntgrGetSntpUnicastPollInterval $switch]
		set u_poll_ret  [_ntgrGetSntpUnicastPollRetry $switch]
		set u_poll_tout [_ntgrGetSntpUnicastPollTimeout $switch]
		_ntgrSntpUnicastTimerConfig $switch $u_poll_int $u_poll_ret $u_poll_tout
	} elseif {$client_mode == "broadcast"} {
		set b_poll_int  [_ntgrGetSntpBroadcastPollInterval $switch]
		_ntgrSntpBroadcastTimerConfig $switch $b_poll_int
	} else {
		Netgear_log_file "lib-sntp.tcl" "Time intervals are configured only when mode is <unicast> or <broadcast>"
	}
	Netgear_disconnect_switch $switch
}

#*******************************************************************************
#  Function Name	: _ntgrSntpAddServer 
#
#  Description    : This function is called to adds SNTP server IP address
#         
#  Usage          : _ntgrSntpAddServer <switch>
#
#
#*******************************************************************************
proc _ntgrSntpAddServer {switch} {
	Netgear_connect_switch $switch

	set server_list [_ntgrGetSntpAddServerList $switch]
	_ntgrSntpServerAddIpAddress $switch $server_list
	
	Netgear_disconnect_switch $switch
}


#*******************************************************************************
#  Function Name	: _ntgrSntpDeleteServer 
#
#  Description    : This function is called to delete SNTP server IP address
#         
#  Usage          : _ntgrSntpDeleteServer <switch>
#
#
#*******************************************************************************
proc _ntgrSntpDeleteServer {switch} {
	Netgear_connect_switch $switch

	set server_list [_ntgrGetSntpDelServerList $switch]
	_ntgrSntpServerDeleteIpAddress $switch $server_list

	Netgear_disconnect_switch $switch
}

#*******************************************************************************
#  Function Name	: _ntgrSetTimeZone
#
#  Description    : This function is called to configure time zone
#         
#  Usage          : _ntgrSetTimeZone <switch_name> <name> <offset>
#
#
#*******************************************************************************
proc _ntgrSetTimeZone {switch_name name offset} {
	Netgear_log_file "lib-sntp.tcl" "Set timezone to $name ($offset) on switch $switch_name"
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	if {$name != ""} {
		exp_send -i $connection_id "clock timezone $name $offset\r"
		sleep 1
	}
 	exp_send -i $connection_id "exit\r"
}
