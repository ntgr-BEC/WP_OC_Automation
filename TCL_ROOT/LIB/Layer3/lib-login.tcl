#!/bin/sh
################################################################################   
#
#  File Name		  : lib-login.tcl
#
#  Description      :
#         This TCL contains functions to login in
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        21-09-06      Nina Cheng        Created
#
#
#
#
################################################################################
#*******************************************************************************
#
#  Function Name	: _switch_login_by_terminal_server_1
#
#  Description    : This function used to login through Terminal Server.
#         
#  Usage          : _switch_login_by_terminal_server_1 <connection_id> <username> <password>
# 
#*******************************************************************************
proc _switch_login_by_terminal_server_1 {connection_id username password} {
  exp_send -i $connection_id -- "\r"
  sleep 1
  expect {
             -i $connection_id -re "#" {
                  exp_send -i $connection_id -- "\032"
                  sleep 1
                	exp_send -i $connection_id -- "logout\r"
                	sleep 1
                	expect -i $connection_id -re "\(y/n\)" {
                  		exp_send -i $connection_id "n"
					sleep 1
					exp_send -i $connection_id -- "\r"
                  		sleep 1
                	}
               	exp_continue              
             }
             
             -i $connection_id -re " >" {
	          exp_send -i $connection_id -- "en\r"
                sleep 1
                exp_send -i $connection_id -- "\r" 
			
		    #Before this was here 	
		    if { 0 } { 	
                	exp_send -i $connection_id -- "logout\r"
                	expect -i $connection_id -re "\(y/n\)" {
                  	exp_send -i $connection_id "n"
                	}
               	exp_continue              
		    } 	
             }
             
             -i $connection_id "\)>" {
             	exp_send -i $connection_id -- "\r"
               	sleep 3
               	exp_continue              
             }

            -i $connection_id -re "User:" {
                exp_send -i $connection_id -- "$username\r"
                sleep 1
                exp_send -i $connection_id -- "$password\r"
                #sleep 1
                #exp_send -i $connection_id -- "en\r"
                #sleep 1
                #exp_send -i $connection_id -- "\r" 
             }
             timeout {
                 exp_send -i $connection_id -- "\r"
                 exp_continue
             }
        }	
    Netgear_log_file "lib-switch-access.tcl" "Login to the Switch"
}

#*******************************************************************************
#
#  Function Name	: _switch_connect_telnet_1
#
#  Description    : This function telnets to the switch using the given 
#			  IP address
#         
#  Usage          : _switch_connect_telnet_1 <switch_ipaddr> <username> <password> <enable_pwd>
# 
#*******************************************************************************
proc _switch_connect_telnet_1 {switch_ipaddr {switch_ts_port 23} {username admin} {password ""} {enable_pwd ""}} {
    global NTGR_LOG_DEBUG
    if { $switch_ts_port == 23 } {
        # Using telnet
        	if {FALSE == [_switch_ping $switch_ipaddr]} {
        		Netgear_log_file "lib-switch-access.tcl" "Failed to ping Switch $switch_ipaddr."
        		return -1
        	} else {
        		Netgear_log_file "lib-switch-access.tcl" "Successfully ping Switch $switch_ipaddr."
        	}
        	Netgear_log_file "lib-switch-access.tcl" "Connecting to Switch $switch_ipaddr using telnet"
        	spawn telnet $switch_ipaddr
        	set id $spawn_id
        	Netgear_log_file "lib-switch-access.tcl" "Connected to Switch $switch_ipaddr using telnet"
        	_switch_login_1 id $username $password $enable_pwd
        	if {$id != -1} {
        	    Netgear_log_file "lib-switch-access.tcl" "Successfuly logged into the switch $switch_ipaddr"  
        	} else {
        	    Netgear_log_file "lib-switch-access.tcl" "fail to log into the switch $switch_ipaddr"   
        	}
        	
        	return $id
    } else {
        # Using Terminal Server
        	NtgrDumpLog $NTGR_LOG_DEBUG  "lib-switch-access.tcl" "Spawn telnet process by expect: ip addr = $switch_ipaddr, telnet port = $switch_ts_port"
        	spawn telnet $switch_ipaddr $switch_ts_port
        	NtgrDumpLog $NTGR_LOG_DEBUG  "lib-switch-access.tcl" "Successly spawned telnet process"
        	set id $spawn_id
        	Netgear_log_file "lib-switch-access.tcl" "Connecting to the port $switch_ts_port of the TS using telnet"
        	_switch_login_by_terminal_server_1 $id $username $password
        	Netgear_log_file "lib-switch-access.tcl" "Successfuly logged into the device connected port $switch_ts_port of the TS"
        	return $id
    }
}

#*******************************************************************************
#
#  Function Name	: _switch_disconnect_1
#
#  Description    : This function closes the telnet/serial connection to 
#                   the switch
#         
#  Usage          : _switch_disconnect_1 <connection_id>
# 
#*******************************************************************************
proc _switch_disconnect_1 {connection_id} {
    set isUsingTS [ _ntgrIsUsingTS $connection_id ]
    if { $isUsingTS == 1 } {
        	exp_send -i $connection_id -- "\r"
        	expect {
        	   -i $connection_id "User:" {}
             -i $connection_id "\)>" {}
             -i $connection_id -re "\(\[#>\]+\)" {
                exp_send -i $connection_id -- "\032"
                sleep 1
                exp_send -i $connection_id -- "logout\r"
                sleep 1
                exp_send -i $connection_id "n"
                sleep 1
             }
        	}
    }
	exp_close -i $connection_id
	Netgear_log_file "lib-switch-access.tcl" "Disconnected from the Switch"
}
