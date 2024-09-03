#!/bin/sh
################################################################################   
#
#  File Name		  : lib-remote_diagnostic.tcl
#
#  Description      :
#         This TCL contains functions to configure Remote_diagnostic
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        10-Oct-13     Jason.Li              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name	: _switch_remotediagnostic_check
#
#  Description    : This function check remotediagnastic enable or not 
#			  
#         
#  Usage          : _switch_remotediagnostic_check <switch_ipaddr> <username> <password>
# 
#*******************************************************************************
proc _switch_remotediagnostic_check {switch_ipaddr username password {telnet_port 23}} {

	set id [_switch_remotediagnostic_Login $switch_ipaddr $username $password $telnet_port] 
		
	if {$id == -1} {return -1}	
	
	#input Debug Command

	exp_send -i $id -- "Debug\r"	
	#check Debug Mode

	expect -i $id timeout {
			_timedout "in Debug"
			set id -1
			exit -noexit
		} eof {
			_timedout "spawn failed with eof"
			set id -1
			exit -noexit
		} -re ">" {                                    
		exp_send -i $id -- "\r\r"
		Netgear_log_file "lib-remote_diagnostic.tcl" "check debug Mode."
		}
   if {$id == -1} {
   		return -1
   } else {
   	   return 1
   }
}

#*******************************************************************************
#
#  Function Name	: _switch_remotediagnostic_Login
#
#  Description    : 
#			  
#         
#  Usage          : _switch_remotediagnostic_Login <switch_ipaddr> <username> <password>
# 
#*******************************************************************************
proc _switch_remotediagnostic_Login {switch_ipaddr username password {telnet_port 23}} {
    		#telnet to Switch;
        	spawn telnet $switch_ipaddr $telnet_port
        	set id $spawn_id
			Netgear_log_file "lib-remote_diagnostic.tcl" "Connected to Switch $switch_ipaddr using telnet"
        	if {$id == -1} {return -1}
             
          #input UserName;
        if {[string is space $username]} {
			  # skip input user, for user login with "line telnet"   
			  
			} else {    

			    	if {$id == -1} {return -1}
			    	expect -i $id timeout {
			    			_timedout "in user login"
			    			set id -1
			    			exit -noexit
			      } eof {
			        	_timedout "spawn failed with eof on login"
			        	set id -1
			        	exit -noexit
			    } "User Name:" {
			    	 exp_send -i $id -- "$username\r"
			    	 Netgear_log_file "lib-remote_diagnostic.tcl" "input User Name: $username"
					} "User:" {
			    	 exp_send -i $id -- "$username\r"
			    	 Netgear_log_file "lib-remote_diagnostic.tcl" "input User Name: $username"
					}
			}  
		

      #input Password
  if {$id == -1} {return -1}
		expect -i $id timeout {
			_timedout "in Password"
			set id -1
			exit -noexit
		} eof {
			_timedout "spawn failed with eof"
			set id -1
			exit -noexit
		} "Password:" {
			exp_send -i $id -- "$password\r"
			Netgear_log_file "lib-remote_diagnostic.tcl" "input Password: $password"
		}
		
	  if {$id == -1} {
   		return -1
   } else {
   	   return $id
	}
}

#*******************************************************************************
#
#  Function Name  : _ntgrAdd_RemoteDiagnostic_User
#
#  Description   : Add New User, Or Modify User Settings. 
#  Usage         :  _ntgrAdd_RemoteDiagnostic_User <id> <user_name> <password> <privilege>
#
#*******************************************************************************

proc _ntgrAdd_RemoteDiagnostic_User {id user_name pass_word lvl} { 
	if {[string first "exp" $id] == -1} { 
	return -1     
	}    

	Netgear_log_file "lib-remote_diagnostic.tcl" "Add/Modify Username $user_name password $pass_word privilege $lvl"
	exp_send -i $id "configure\r" 
  	exp_sleep 1
      
	exp_send -i $id "username $user_name password $pass_word privilege $lvl\r"
    exp_sleep 1  

	exp_send -i $id "exit\r"       
	exp_sleep 1             
	exp_send -i $id "exit\r"       
	exp_sleep 1 
	return 1   	
}
