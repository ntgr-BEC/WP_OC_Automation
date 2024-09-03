####################################################################################
#  File Name   : lib-user.tcl                                                      #
#                                                                                  #
#  Description :                                                                   #
#        This file includes functions used for user management configuration.      #
#                                                                                  #
#  History     :                                                                   #
#        Date          Programmer         Description                              #
#        May 11, 2007  Scott Zhang        Created                                  #
#	 May 14, 2007  Nina Cheng         Modified				   #
#                                                                                  #
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrAddAuthLoginList
#
#  Description    : This function is called to create a authentication login list.
#
#  Usage          : _ntgrAddAuthLoginList <switch_name name mode>
#
#*******************************************************************************
proc _ntgrAddAuthLoginList {switch_name name mode} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "authentication login $name $mode\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrAddUser
#
#  Description    : This function is called to create a user.
#
#  Usage          : _ntgrAddUser <switch_name name pwd>
#
#*******************************************************************************
proc _ntgrAddUser {switch_name user_name pass_word {lvl 1}} {
	  
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    expect -i $cnn_id "#" {}    
    exp_send -i $cnn_id "username $user_name password $pass_word level $lvl\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrAddUserwithEncrypt
#
#  Description    : This function is called to create a user.
#
#  Usage          : _ntgrAddUserwithEncryptr <switch_name name pwd>
#
#*******************************************************************************
proc _ntgrAddUserwithEncrypt {switch_name user_name pass_word {lvl 1}} {
	  
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    expect -i $cnn_id "#" {}    
    exp_send -i $cnn_id "username $user_name password $pass_word level $lvl encrypted\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrAddUserWithoutPassWord
#
#  Description    : This function is called to create a user without password.
#
#  Usage          : _ntgrAddUserWithoutPassWord <switch_name name pwd>
#
#*******************************************************************************
proc _ntgrAddUserWithoutPassWord {switch_name user_name {lvl 1}} {
	  
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "username $user_name nopassword level $lvl\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}
#*******************************************************************************
#
#  Function Name  : _ntgrDeleteUser
#
#  Description    : This function is called to delete a user.
#
#  Usage          : _ntgrDeleteUser <switch_name name>
#
#*******************************************************************************
proc _ntgrDeleteUser {switch_name name} {

	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    expect -i $cnn_id "#" {}   
    append ret $expect_out(buffer)  
    exp_send -i $cnn_id "no username $name\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer) 
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrAddUsertoList
#
#  Description    : This function is called to add a user to list.
#
#  Usage          : _ntgrAddUsertoList <switch_name user list>
#
#*******************************************************************************
proc _ntgrAddUsertoList {switch_name user list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "users login $user $list\r"
    exp_sleep 1    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrModifyPassword
#
#  Description    : This function is called to modify the passwrod of a user.
#
#  Usage          : _ntgrModifyPassword <switch_name user pwd_old pwd_new>
#
#*******************************************************************************
proc _ntgrModifyPassword {switch_name user pwd_old pwd_new} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "users passwd $user\r"
    exp_sleep 1
    exp_send -i $cnn_id "$pwd_old\r"
    exp_sleep 1
    exp_send -i $cnn_id "$pwd_new\r"
    exp_sleep 1
    exp_send -i $cnn_id "$pwd_new\r"
    exp_sleep 1     
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}
#*******************************************************************************
#
#  Function Name  : _ntgrModifyPassword
#
#  Description    : This function is called to modify the passwrod of a user.
#
#  Usage          : _ntgrModifyPassword <switch_name user pwd_old pwd_new>
#
#*******************************************************************************
proc _ntgrModifyPassword {switch_name user pwd_old pwd_new} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "users passwd $user\r"
    exp_sleep 1
    exp_send -i $cnn_id "$pwd_old\r"
    exp_sleep 1
    exp_send -i $cnn_id "$pwd_new\r"
    exp_sleep 1
    exp_send -i $cnn_id "$pwd_new\r"
    exp_sleep 1     
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrModifyUserPasswd
#
#  Description    : This function is called to modify the passwrod of a user.
#
#  Usage          : __ntgrModifyUserPasswd <switch_name user passwd>
#
#*******************************************************************************
proc _ntgrModifyUserPasswd {switch_name user passwd} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "username $user password $passwd\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrClearUserPasswd
#
#  Description    : This function is called to clear passwrod of a user.
#
#  Usage          : _ntgrClearUserPasswd <switch_name user>
#
#*******************************************************************************
proc _ntgrClearUserPasswd {switch_name user} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "passwords min-length 0\r"
    exp_sleep 1
    exp_send -i $cnn_id "username $user nopassword\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrModifyUserPasswdkeylset
#
#  Description    : This function is called to modify keylset ntgr_USERPASSWD
#  Usage          : _ntgrModifyUserPasswd <switch_name user passwd>
#
#*******************************************************************************
proc _ntgrModifyUserPasswdkeylset { user passwd} {
    global ntgr_USERPASSWD
    keylset ntgr_USERPASSWD                     \
    LOGIN_USERNAME         $user                \
    LOGIN_USERPASS         $passwd         
}

