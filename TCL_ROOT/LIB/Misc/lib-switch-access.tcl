#!/bin/sh
################################################################################   
#
#  File Name		: lib-switch-access.tcl
#
#  Description       	:
#         This TCL contains functions to access the given switch
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V       Created
#
#
#
#
################################################################################

proc _close_spawned_session {id} {
    upvar 1 $id connection_id
    exp_close -i $connection_id
    set connection_id -1
    Netgear_log_file "lib-switch-access.tcl" "close switch login spawned session "
}
#*******************************************************************************
#
#  Function Name	: _switch_login
#
#  Description    : This function login to the switch using the given 
#		        connection id
#         
#  Usage          : _switch_login <connection_id>
# 
#*******************************************************************************
proc _switch_login {id} {
	upvar 1 $id connection_id
	set user 0
	set passwd 0
	set enpasswd 0
	global ntgr_USERPASSWD
  
	if {[info exists ntgr_USERPASSWD]!=0 } {
    	  keylget ntgr_USERPASSWD LOGIN_USERNAME user
        keylget ntgr_USERPASSWD LOGIN_USERPASS passwd
        keylget ntgr_USERPASSWD LOGIN_ENPASSWD enpasswd
    }

  set timeout 70
	expect -i $connection_id timeout {
			 _timedout "in user login"
			   Netgear_log_file "lib-switch-access.tcl" "timeout in user login"
			   _close_spawned_session connection_id
			   return 0
    		} eof {
			    _timedout "spawn failed with eof on login"
			      Netgear_log_file "lib-switch-access.tcl" "spawn failed with eof on login"
            set connection_id -1
          return 0
   		} "User:" {
   		      if { $user ==0 } {
			           exp_send -i $connection_id -- "admin\r"
			      } else {
			           exp_send -i $connection_id -- "$user\r"
			      }
	    	}

	  expect -i $connection_id timeout {
          _timedout "in Password"
          Netgear_log_file "lib-switch-access.tcl" "timeout in user password"
          _close_spawned_session connection_id 
          return 0
		} eof {
          _timedout "spawn failed with eof"
            Netgear_log_file "lib-switch-access.tcl" "spawn failed with eof on user password"
           set connection_id -1
          return 0
		} "Password" {
		       if { $passwd ==0 || $passwd == "NULL"} {
			           exp_send -i $connection_id -- "\r"
			      } else {
			           exp_send -i $connection_id -- "$passwd\r"
			      }
		}
		
	expect -i $connection_id timeout {
            _timedout " in Enable \n"
            Netgear_log_file "lib-switch-access.tcl" "timeout in enable"
            _close_spawned_session connection_id
            return 0
	   } "User:" {
            Netgear_log_file "lib-switch-access.tcl" "Login to the Switch with error enable"
           _close_spawned_session connection_id
            return 0
		} eof {
            _timedout "spawn failed with eof"
             set connection_id -1
            return 0
		} -re ">" {
			exp_send -i $connection_id -- "en\r"
		}

	expect -i $connection_id timeout {
          _timedout " in Enable Password\n"
          Netgear_log_file "lib-switch-access.tcl" "timeout in enable password"
          _close_spawned_session connection_id
          return 0
		} eof {	
          _timedout "spawn failed with eof"
           set connection_id -1
          return 0
		} "\) #" {
		   exp_send -i $connection_id -- "\r"
		} "Password" {
			if { $enpasswd ==0 } {
           exp_send -i $connection_id -- "\r"
      } else {
           exp_send -i $connection_id -- "$enpasswd\r"
      }
		}
		exp_send -i $connection_id -- "\r"
}

#***********************************************************************************************
#
#  Function Name	: _switch_login_1
#
#  Description    : This function login to the switch using the given connection id
#         
#  Usage          : _switch_login_1 <connection_id> <user_name> <pass_word> <enable_pwd>
# 
#  Note           : create by kenddy for developing script of user-management.
#                   Modified for login without username, andy.xie 2012-06-06
#                   
#************************************************************************************************
proc _switch_login_1 {id user_name pass_word {enable_pwd ""}} {
	
	upvar 1 $id connection_id
	
	if {[string is space $user_name]} {
	  # skip input user, for user login with "line telnet"
	} else {

    	if {$connection_id == -1} {return 0}
    	expect -i $connection_id timeout {
    			_timedout "in user login"
    			set connection_id -1
    			exit -noexit
      } eof {
        	_timedout "spawn failed with eof on login"
        	set connection_id -1
        	exit -noexit
      } "User*" {
    			 exp_send -i $connection_id -- "$user_name\r"
    	}	  
	}

	 
  if {$connection_id == -1} {return 0}
	expect -i $connection_id timeout {
			_timedout "in Password"
			set connection_id -1
			exit -noexit
		} eof {
			_timedout "spawn failed with eof"
			set connection_id -1
			exit -noexit
		} "Password" {
			exp_send -i $connection_id -- "$pass_word\r"
		}
  if {$connection_id == -1} {return 0}
	expect -i $connection_id timeout {
			_timedout " in Enable \n"
			set connection_id -1
			exit -noexit
		} eof {
			_timedout "spawn failed with eof"
			set connection_id -1
			exit -noexit
		} -re ">" {
			exp_send -i $connection_id -- "en\r"
		} "#" {exp_send -i $connection_id -- "\r"
		}
  if {$connection_id == -1} {return 0}
	expect -i $connection_id timeout {
			_timedout " in Enable Password\n"
			set connection_id -1
			exit -noexit
		} eof {	
			_timedout "spawn failed with eof"
			set connection_id -1
			exit -noexit
		} "\) #" {
		} "\) >" {  
		} "Password" {
			exp_send -i $connection_id -- "$enable_pwd\r"
		} "#" {exp_send -i $connection_id -- "\r"
		}
  if {$connection_id == -1} {return 0}
  exp_send -i $connection_id -- "\r"
}

#*******************************************************************************
#
#  Function Name	: _switch_login_by_terminal_server
#
#  Description    : This function used to login through Terminal Server.
#         
#  Usage          : _switch_login_by_terminal_server <connection_id>
# 
#*******************************************************************************
proc _switch_login_by_terminal_server {connection_id} {
  exp_send -i $connection_id -- "\r"
  after 50
  expect {
             -i $connection_id -re "#" {
                  exp_send -i $connection_id -- "\032"
                  after 50
                	exp_send -i $connection_id -- "logout\r"
                	after 50
                	expect -i $connection_id -re "logout" {}
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
                exp_send -i $connection_id -- "admin\r"
                after 50
                exp_send -i $connection_id -- "\r"
                after 50
                exp_send -i $connection_id -- "en\r"
                after 50
                exp_send -i $connection_id -- "\r" 
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
#  Function Name	: _switch_connect_telnet
#
#  Description    : This function telnets to the switch using the given 
#			  IP address
#         
#  Usage          : _switch_connect_telnet <switch_ipaddr>
# 
#*******************************************************************************
proc _switch_connect_telnet {switch_ipaddr {switch_ts_port 23}} {
    global NTGR_LOG_DEBUG
    global NTGR_LOG_PATH
    set LoginCycles 6
    set TelLogFile $NTGR_LOG_PATH
    set waitingTime 5
    append TelLogFile "/telnet.log"
    
    # jason add for smart switch telnet port 60000 begin
    if { ($switch_ts_port == 23) || ($switch_ts_port == 60000) } {
        # Using telnet
#        	if {FALSE == [_switch_ping $switch_ipaddr]} {
#        		Netgear_log_file "lib-switch-access.tcl" "Failed to ping Switch $switch_ipaddr."
#        		return -1
#        	} else {
#        		Netgear_log_file "lib-switch-access.tcl" "Successfully ping Switch $switch_ipaddr."
#        	}
        	Netgear_log_file "lib-switch-access.tcl" "Connecting to Switch $switch_ipaddr using telnet"      	
          set id -1
          for {set i 0} { ([ info exists spawn_id ] == 0 || $id == -1) && $i< $LoginCycles } {incr i} {
              Netgear_log_file "lib-switch-access.tcl" "Begin try to login switch $switch_ipaddr"
              spawn telnet -f $TelLogFile $switch_ipaddr $switch_ts_port      
              set id $spawn_id
              Netgear_log_file "lib-switch-access.tcl" "spawn id is $id, telnet with the spawn"
              if {$switch_ts_port == 23} {
                _switch_login id
              } else { 
                 	_switch_login_1 id "admin" "password"
               }
              
              if {$id == -1} {  
                  Netgear_log_file "lib-switch-access.tcl" "spawn id is -1, then wait $waitingTime, telnet again $i times"
                  sleep $waitingTime
              }
          }
          if {$i >= $LoginCycles} {
              Netgear_log_file "lib-switch-access.tcl" "Failed login switch $switch_ipaddr"
            	return $id
          } else {
            	Netgear_log_file "lib-switch-access.tcl" "Successfuly logged into the switch $switch_ipaddr"
            	return $id
        	}
       # jason add for smart switch telnet port 60000 end       
    } else {
        # Using Terminal Server
        	NtgrDumpLog $NTGR_LOG_DEBUG  "lib-switch-access.tcl" "Spawn telnet process by expect: ip addr = $switch_ipaddr, telnet port = $switch_ts_port"
        	while { [ info exists spawn_id ] == 0 } {
          	spawn telnet $switch_ipaddr $switch_ts_port
          	set id $spawn_id
          }
        	NtgrDumpLog $NTGR_LOG_DEBUG  "lib-switch-access.tcl" "Successly spawned telnet process"
        	Netgear_log_file "lib-switch-access.tcl" "Connecting to the port $switch_ts_port of the TS using telnet"
        	_switch_login_by_terminal_server $id
        	Netgear_log_file "lib-switch-access.tcl" "Successfuly logged into the device connected port $switch_ts_port of the TS"
        	return $id
    }
}

#*******************************************************************************
#
#  Function Name	: _switch_disconnect
#
#  Description    : This function closes the telnet/serial connection to 
#                   the switch
#         
#  Usage          : _switch_disconnect <connection_id>
# 
#*******************************************************************************
proc _switch_disconnect {connection_id} {
    set isUsingTS [ _ntgrIsUsingTS $connection_id ]
    if { $isUsingTS == 1 } {
        	exp_send -i $connection_id -- "\r"
        	expect {
             -i $connection_id "\)>" {}
             -i $connection_id -re "\(\[#>\]+\)" {
                exp_send -i $connection_id -- "\032"
                sleep 3
                exp_send -i $connection_id -- "logout\r"
                sleep 3
                expect -i $connection_id -timeout 1 -re "\(y/n\)" {
                    exp_send -i $connection_id "n"
                    sleep 1
                }
             }
        	}
    }
	exp_close -i $connection_id
  global NTGR_LOG_PATH
  global ntgr_logOptions
  set TelLogFile $NTGR_LOG_PATH
  append TelLogFile "/telnet.log"
  set telnetfd [open $TelLogFile r]
  while {[gets $telnetfd line] >=0} {
     puts $line     
     puts $ntgr_logOptions(LOG_FD) $line
  }
  close $telnetfd
	Netgear_log_file "lib-switch-access.tcl" "Disconnected from the Switch"
}
#*******************************************************************************
#
#  Function Name	: _switch_disconnect_60000
#
#  Description    : This function closes the telnet/serial connection to 
#                   the switch,only use to TelnetPort 60000
#         
#  Usage          : _switch_disconnect_60000 <connection_id>
# 
#*******************************************************************************
proc _switch_disconnect_60000 {connection_id} {
    set isUsingTS [ _ntgrIsUsingTS $connection_id ]
    if { $isUsingTS == 1 } {
        	exp_send -i $connection_id -- "\r"
        	expect {
             -i $connection_id "\)>" {}
             -i $connection_id -re "\(\[#>\]+\)" {
                exp_send -i $connection_id -- "\032"
                sleep 3
                exp_send -i $connection_id -- "exit\r"
                sleep 3
             }
        	}
    }
	exp_close -i $connection_id
  global NTGR_LOG_PATH
  global ntgr_logOptions
  set TelLogFile $NTGR_LOG_PATH
  append TelLogFile "/telnet.log"
  set telnetfd [open $TelLogFile r]
  while {[gets $telnetfd line] >=0} {
     puts $line     
     puts $ntgr_logOptions(LOG_FD) $line
  }
  close $telnetfd
	Netgear_log_file "lib-switch-access.tcl" "Disconnected from the Switch"
}

#*******************************************************************************
#
#  Function Name	: _switch_connect_serial
#
#  Description    : This function exits the telnet connection to the switch
#         
#  Usage          : _switch_connect_serial <com_port>
# 
#*******************************************************************************
proc _switch_connect_serial {com_port} {
	Netgear_log_file "lib-switch-access.tcl" "Connecting to Switch using serial port $com_port"
	spawn -open [open $com_port RDWR]
	set id $spawn_id
	Netgear_log_file "lib-switch-access.tcl" "Connected to Switch using serial port $com-port"
	switch_login $id
	Netgear_log_file "lib-switch-access.tcl" "Successfuly logged into the switch"
	return $id
}