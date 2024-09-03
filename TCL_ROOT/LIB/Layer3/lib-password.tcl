#!/bin/sh
################################################################################   
#
#  File Name		  : lib-password.tcl
#
#  Description      :
#         This TCL contains functions to configure password paramter
#
#  Revision History :
#        Date            Programmer        Description
#        -----------------------------------------------------------------------
#        22-Aug-2011     Kenddy            Created
#
#
#
#
################################################################################


#*******************************************************************************
#  Function Name	: _ntgrAddPasswordExcludeKey
#
#  Description    : This function is called to configure password exclude-keyword
#         
#  Usage          : _ntgrAddPasswordExcludeKey <switch_name> <key_word>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrAddPasswordExcludeKey {switch_name key_word} {
	
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
    exp_send -i $cnn_id "passwords strength exclude-keyword $key_word\r"
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
#  Function Name	: _ntgrDeletePasswordExcludeKey
#
#  Description    : This function is called to delete password exclude-keyword
#         
#  Usage          : _ntgrDeletePasswordExcludeKey <switch_name> <key_word>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrDeletePasswordExcludeKey {switch_name key_word} {
	
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
    exp_send -i $cnn_id "no passwords strength exclude-keyword $key_word\r"
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
#  Function Name	: _ntgrSetPassWordStrengthMinCharacter
#
#  Description    : This function is called to configure password min character-class
#         
#  Usage          : _ntgrSetPassWordStrengthMinCharacter <switch_name> <min_num>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrSetPassWordStrengthMinCharacterClass {switch_name min_num} {
	
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
    exp_send -i $cnn_id "passwords strength minimum character-classes $min_num\r"
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
#  Function Name	: _ntgrSetPassWordStrengthMinCharacter
#
#  Description    : This function is called to restore password min character-class
#         
#  Usage          : _ntgrSetPassWordStrengthMinCharacter <switch_name>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrRestorPassWordStrengthMinCharacterClass {switch_name} {
	
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
    exp_send -i $cnn_id "no passwords strength minimum character-classes\r"
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
#  Function Name	: _ntgrSetPassWordStrengthMinLowCaseLetter
#
#  Description    : This function is called to configure password min lowcase-letter
#         
#  Usage          : _ntgrSetPassWordStrengthMinLowCaseLetter <switch_name> <min_num>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrSetPassWordStrengthMinLowCaseLetter {switch_name min_num} {
	
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
    exp_send -i $cnn_id "passwords strength minimum lowercase-letters $min_num\r"
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
#  Function Name	: _ntgrSetPassWordStrengthMinLowCaseLetter
#
#  Description    : This function is called to restore password min lowcase-letter
#         
#  Usage          : _ntgrSetPassWordStrengthMinLowCaseLetter <switch_name>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrRestorePassWordStrengthMinLowCaseLetter {switch_name} {
	
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
    exp_send -i $cnn_id "no passwords strength minimum lowercase-letters\r"
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
#  Function Name	: _ntgrSetPassWordStrengthMinNumericChar
#
#  Description    : This function is called to configure password min numeric-char
#         
#  Usage          : _ntgrSetPassWordStrengthMinNumericChar <switch_name> <min_num>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrSetPassWordStrengthMinNumericChar {switch_name min_num} {
	
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
    exp_send -i $cnn_id "passwords strength minimum numeric-characters $min_num\r"
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
#  Function Name	: _ntgrRestorePassWordStrengthMinNumericChar
#
#  Description    : This function is called to restore password min numeric-char
#         
#  Usage          : _ntgrRestorePassWordStrengthMinNumericChar <switch_name>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrRestorePassWordStrengthMinNumericChar {switch_name} {
	
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
    exp_send -i $cnn_id "no passwords strength minimum numeric-characters\r"
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
#  Function Name	: _ntgrSetPassWordStrengthMinSpecialChar
#
#  Description    : This function is called to configure password min special-char
#         
#  Usage          : _ntgrSetPassWordStrengthMinSpecialChar <switch_name> <min_num>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrSetPassWordStrengthMinSpecialChar {switch_name min_num} {
	
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
    exp_send -i $cnn_id "passwords strength minimum special-characters $min_num\r"
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
#  Function Name	: _ntgrRestorePassWordStrengthMinSpecialChar
#
#  Description    : This function is called to restore password min special-char
#         
#  Usage          : _ntgrRestorePassWordStrengthMinSpecialChar <switch_name>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrRestorePassWordStrengthMinSpecialChar {switch_name} {
	
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
    exp_send -i $cnn_id "no passwords strength minimum special-characters\r"
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
#  Function Name	: _ntgrSetPassWordStrengthMinUpperCaseChar
#
#  Description    : This function is called to configure password min uppercase letter
#         
#  Usage          : _ntgrSetPassWordStrengthMinUpperCaseChar <switch_name> <min_num>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrSetPassWordStrengthMinUpperCaseChar {switch_name min_num} {
	
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
    exp_send -i $cnn_id "passwords strength minimum uppercase-letter $min_num\r"
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
#  Function Name	: _ntgrRestorePassWordStrengthMinUpperCaseChar
#
#  Description    : This function is called to restore password min uppercase letter
#         
#  Usage          : _ntgrRestorePassWordStrengthMinUpperCaseChar <switch_name> 
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrRestorePassWordStrengthMinUpperCaseChar {switch_name} {
	
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
    exp_send -i $cnn_id "no passwords strength minimum uppercase-letter\r"
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
#  Function Name	: _ntgrSetPassWordStrengthMaxConsecutive
#
#  Description    : This function is called to configure password max consecutive
#         
#  Usage          : _ntgrSetPassWordStrengthMaxConsecutive <switch_name> <max_num>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrSetPassWordStrengthMaxConsecutive {switch_name max_num} {
	
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
    exp_send -i $cnn_id "passwords strength maximum consecutive-characters $max_num\r"
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
#  Function Name	: _ntgrRestorePassWordStrengthMaxConsecutive
#
#  Description    : This function is called to restore password max consecutive
#         
#  Usage          : _ntgrRestorePassWordStrengthMaxConsecutive <switch_name>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrRestorePassWordStrengthMaxConsecutive {switch_name} {
	
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
    exp_send -i $cnn_id "no passwords strength maximum consecutive-characters\r"
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
#  Function Name	: _ntgrSetPassWordStrengthMaxRepeat
#
#  Description    : This function is called to configure password max repeated-char
#         
#  Usage          : _ntgrSetPassWordStrengthMaxRepeat <switch_name> <max_num>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrSetPassWordStrengthMaxRepeat {switch_name max_num} {
	
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
    exp_send -i $cnn_id "passwords strength maximum repeated-characters $max_num\r"
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
#  Function Name	: _ntgrSetPassWordStrengthMaxRepeat
#
#  Description    : This function is called to configure password max repeated-char
#         
#  Usage          : _ntgrSetPassWordStrengthMaxRepeat <switch_name>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrRestorePassWordStrengthMaxRepeat {switch_name} {
	
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
    exp_send -i $cnn_id "no passwords strength maximum repeated-characters\r"
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
#  Function Name	: _ntgrPassWordEnableStrengthcheck
#
#  Description    : This function is called to enable password strength check
#         
#  Usage          : _ntgrPassWordEnableStrengthcheck <switch_name> 
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrPassWordEnableStrengthcheck {switch_name} {
	
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
    exp_send -i $cnn_id "passwords strength-check\r"
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
#  Function Name	: _ntgrPassWordDisableStrengthcheck
#
#  Description    : This function is called to disable password strength check
#         
#  Usage          : _ntgrPassWordDisableStrengthcheck <switch_name> 
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrPassWordDisableStrengthcheck {switch_name} {
	
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
    exp_send -i $cnn_id "no passwords strength-check\r"
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
#  Function Name	: _ntgrSetPassWordMinLength
#
#  Description    : This function is called to configure password min length
#         
#  Usage          : _ntgrSetPassWordMinLength <switch_name> <min_num>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrSetPassWordMinLength {switch_name min_num} {
	
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
    exp_send -i $cnn_id "passwords min-length $min_num\r"
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
#  Function Name	: _ntgrRestoreDefaultPassWordMinLength
#
#  Description    : This function is called to restore password min length to default
#         
#  Usage          : _ntgrRestoreDefaultPassWordMinLength <switch_name>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrRestoreDefaultPassWordMinLength {switch_name} {
	
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
    exp_send -i $cnn_id "no passwords min-length\r"
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
#  Function Name	: _ntgrAddEnablePassWord
#
#  Description    : This function is called to configure enable password
#         
#  Usage          : _ntgrAddEnablePassWord <switch_name> <enable_pass>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrAddEnablePassWord {switch_name enable_pass} {
	
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
    exp_send -i $cnn_id "enable password $enable_pass\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#  Function Name	: _ntgrDelEnablePassWord
#
#  Description    : This function is called to delete enable password
#         
#  Usage          : _ntgrDelEnablePassWord <switch_name> <enable_pass>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrDelEnablePassWord {switch_name} {
	
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
    exp_send -i $cnn_id "no enable password\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)     
  	exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrAddLineTelnetPassWord {switch_name line_password} {
	  
	  set ret ""
	  if {[string is space $line_password]} {
	    Netgear_log_file "[file tail [info script]]" "line password is empty, so not to config!"
	    return $ret
	  }
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
    exp_send -i $cnn_id "line telnet\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    
    exp_send -i $cnn_id "password $line_password\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
  	exp_send -i $cnn_id "exit\r"
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

proc _ntgrAddLineSshPassWord {switch_name line_password} {
	
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
    exp_send -i $cnn_id "line ssh\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "password\r"
    exp_sleep 1
    exp_send -i $cnn_id "$line_password\r"
    exp_sleep 1
    exp_send -i $cnn_id "$line_password\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
  	exp_send -i $cnn_id "exit\r"
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

proc _ntgrAddLineConsolePassWord {switch_name line_password} {
	
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
    exp_send -i $cnn_id "line console\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "password $line_password\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
  	exp_send -i $cnn_id "exit\r"
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
proc _ntgrDelLineTelnetPassWord {switch_name} {
	
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
    exp_send -i $cnn_id "line telnet\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "no password\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
  	exp_send -i $cnn_id "exit\r"
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

proc _ntgrDelLineSshPassWord {switch_name} {
	
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
    exp_send -i $cnn_id "line ssh\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "no password\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
  	exp_send -i $cnn_id "exit\r"
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

proc _ntgrDelLineConsolePassWord {switch_name} {
	
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
    exp_send -i $cnn_id "line console\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "no password\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
  	exp_send -i $cnn_id "exit\r"
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

proc _ntgrAddLineTelnetAuthenList {switch_name list_name} {
	
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
    exp_send -i $cnn_id "line telnet\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "login authentication $list_name\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
  	exp_send -i $cnn_id "exit\r"
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

proc _ntgrAddLineSshAuthenList {switch_name list_name} {
	
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
    exp_send -i $cnn_id "line ssh\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "login authentication $list_name\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
  	exp_send -i $cnn_id "exit\r"
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

proc _ntgrAddLineConsoleAuthenList {switch_name list_name} {
	
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
    exp_send -i $cnn_id "line console\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "login authentication $list_name\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
  	exp_send -i $cnn_id "exit\r"
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

proc _ntgrDelLineTelnetAuthenList {switch_name} {
	
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
    exp_send -i $cnn_id "line telnet\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "no login authentication\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
  	exp_send -i $cnn_id "exit\r"
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

proc _ntgrDelLineSshAuthenList {switch_name} {
	
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
    exp_send -i $cnn_id "line ssh\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "no login authentication\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
  	exp_send -i $cnn_id "exit\r"
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

proc _ntgrDelLineConsoleAuthenList {switch_name} {
	
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
    exp_send -i $cnn_id "line console\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "no login authentication\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
  	exp_send -i $cnn_id "exit\r"
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
#  Function Name	: _ntgrSetPassWordHistory
#
#  Description    : This function is called to set password history count for prevention of password reuse.
#         
#  Usage          : _ntgrSetPassWordHistory <switch_name> <his_cout> 
#
#  Revision       : Create by kenddy xie 2011-08-23
#
#*******************************************************************************
proc _ntgrSetPassWordHistory {switch_name his_cout} {
	
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
    exp_send -i $cnn_id "passwords history $his_cout\r"
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
#  Function Name	: _ntgrRestorePassWordHistory
#
#  Description    : This function is called to restore password history count.
#         
#  Usage          : _ntgrRestorePassWordHistory <switch_name> 
#
#  Revision       : Create by kenddy xie 2011-08-23
#
#*******************************************************************************
proc _ntgrRestorePassWordHistory {switch_name} {
	
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
    exp_send -i $cnn_id "no passwords history\r"
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
#  Function Name	: _ntgrGenerateSshRsaKey
#
#  Description    : This function is called to generate rsa key for ssh.
#         
#  Usage          : _ntgrGenerateSshRsaKey <switch_name> 
#
#  Revision       : Create by kenddy xie 2011-08-23
#
#*******************************************************************************
proc _ntgrGenerateSshRsaKey {switch_name} {
	
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
    exp_send -i $cnn_id "crypto key generate rsa\r"
    exp_sleep 2
    exp_send -i $cnn_id "y\r"
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
#  Function Name	: _ntgrGenerateSshDsaKey
#
#  Description    : This function is called to generate dsa key for ssh.
#         
#  Usage          : _ntgrGenerateSshDsaKey <switch_name> 
#
#  Revision       : Create by kenddy xie 2011-08-23
#
#*******************************************************************************
proc _ntgrGenerateSshDsaKey {switch_name} {
	
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
    exp_send -i $cnn_id "crypto key generate dsa\r"
    exp_sleep 2
    exp_send -i $cnn_id "y\r"
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
#  Function Name	: _ntgrEnableSshServer
#
#  Description    : This function is called to enable ssh server
#         
#  Usage          : _ntgrEnableSshServer <switch_name> 
#
#  Revision       : Create by kenddy xie 2011-08-23
#
#*******************************************************************************
proc _ntgrEnableSshServer {switch_name} {
	
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "ip ssh server enable\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#  Function Name	: _ntgrDisableSshServer
#
#  Description    : This function is called to disable ssh server
#         
#  Usage          : _ntgrDisableSshServer <switch_name> 
#
#  Revision       : Create by kenddy xie 2011-08-23
#
#*******************************************************************************
proc _ntgrDisableSshServer {switch_name} {
	
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "no ip ssh server enable\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#  Function Name	: _ntgrSetConsoleBaudRate
#
#  Description    : This function is called to set serial baudrate
#         
#  Usage          : _ntgrSetConsoleBaudRate <switch_name> <rate_number> 
#
#  Revision       : Create by kenddy xie 2011-08-23
#
#*******************************************************************************
proc _ntgrSetConsoleBaudRate {switch_name rate_number} {
	
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
    exp_send -i $cnn_id "line console\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "serial baudrate $rate_number\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)  
  	exp_send -i $cnn_id "exit\r"
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
#  Function Name	: _ntgrResetConsoleBaudRate
#
#  Description    : This function is called to reset serial baudrate to default.
#         
#  Usage          : _ntgrSetConsoleBaudRate <switch_name>
#
#  Revision       : Create by kenddy xie 2011-08-23
#
#*******************************************************************************
proc _ntgrResetConsoleBaudRate {switch_name} {
	
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
    exp_send -i $cnn_id "line console\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "no serial baudrate\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)  
  	exp_send -i $cnn_id "exit\r"
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


