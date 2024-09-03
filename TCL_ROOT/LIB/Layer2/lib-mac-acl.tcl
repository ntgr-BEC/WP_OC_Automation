#!/bin/sh
################################################################################   
#
#  File Name		  : lib-mac-acl.tcl
#
#  Description      :
#         This TCL contains functions to configure MAC ACL
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
#  Function Name	: _ntgrAddMACACL
#
#  Description    	: This function is called to add MAC ACL on switch
#         
#  Usage          	: _ntgrAddMACACL <switch_name> <mac_acl_rule_list> 
#
#
#*******************************************************************************
proc _ntgrAddMACACL {switch_name mac_acl_rule_list} {

  set Ret {}
  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  _ntgrAddMACACLwithoutLogin $mac_acl_rule_list $connection_id retval
 	exp_send -i $connection_id "exit\r"
 	after 1000
	Netgear_disconnect_switch $switch_name
	return $Ret
}

#1.1*******************************************************************************
#  Function Name	: _ntgrAddMACACLwithoutLogin
#
#  Description    	: This function is called to add MAC ACL on switch
#         
#  Usage          	: _ntgrAddMACACLwithoutLogin <mac_acl_rule_list> <connection_id> <retval> 
#
#
#*******************************************************************************
proc _ntgrAddMACACLwithoutLogin { mac_acl_rule_list connection_id retval} {

    upvar 1 $retval  Ret
   
  	foreach acl_rule_list $mac_acl_rule_list {
      	set acl_name [lindex $acl_rule_list 0]     
      	set acl_rule [lindex $acl_rule_list 1]
    		foreach rule $acl_rule {
        	exp_send -i $connection_id "mac access-list extended $acl_name\r"
        	sleep 1
        	set cmd ""
        	for {set i 1} {$i<=13} {incr i} {
            		set j [expr $i - 1]
            		set rule_$i [lindex $rule $j]
        	}      
        	if {$rule_1 != "default"} {set cmd "$cmd $rule_1"}
        	if {$rule_2 != "default"} {
        			set cmd "$cmd $rule_2"
  #           			set cmd "$cmd $rule_2 ff:ff:ff:ff:ff:ff"
        	}
        	if {$rule_3 != "default"} {
        			set cmd "$cmd $rule_3"
  #           			set cmd "$cmd $rule_3 ff:ff:ff:ff:ff:ff"
        	}
			
        	if {$rule_4 != "default"} {set cmd "$cmd $rule_4"}
        	if {$rule_5 != "default" && $rule_5 != ""} {set cmd "$cmd vlan eq $rule_5"}
        	if {$rule_6 != "default" && $rule_6 != ""} {set cmd "$cmd cos $rule_6"}
        	if {$rule_7 != "default" && $rule_7 != ""} {set cmd "$cmd assign-queue $rule_7"}
        	if {$rule_8 != "default" && $rule_8 != ""} {set cmd "$cmd redirect $rule_8"}  
          if {$rule_9 != "default" && $rule_9 != ""} {set cmd "$cmd secondary-vlan eq $rule_9"}
          if {$rule_10!= "default" && $rule_10!= ""} {set cmd "$cmd secondary-cos $rule_10"}
          if {$rule_11!= "default" && $rule_11!= ""} {set cmd "$cmd mirror $rule_11"}
          if {$rule_12!= "default" && $rule_12!= ""} {set cmd "$cmd log"}
          if {$rule_13!= "default" && $rule_13!= ""} {set cmd "$cmd time-range $rule_13"}
          expect -i $connection_id -re "#"
        	exp_send -i $connection_id "$cmd\r"
        	after 1000 
          exp_send -i $connection_id "exit\r"
          after 1000 
          expect -i $connection_id "exit"
          set Ret $expect_out(buffer)
    		}
  	}
  	return $Ret
}

################################################################################
#2.1****************************************************************************
#  Function Name	: _ntgrDeleteMACACLwithoutLogin
#
#  Description    	: This function is called to delete MAC ACL on switch
#         
#  Usage          	: _ntgrDeleteMACACLwithoutLogin <connection_id mac_acl_rule_list> 
#
#
#*******************************************************************************
proc _ntgrDeleteMACACLwithoutLogin {connection_id mac_acl_rule_list} {
	
	foreach acl_rule_list $mac_acl_rule_list {
      	set acl_name [lindex $acl_rule_list 0]     
      	exp_send -i $connection_id "no mac access-list extended $acl_name\r"
      	sleep 1
  	}
}

################################################################################
#2*******************************************************************************
#  Function Name	: _ntgrDeleteMACACL
#
#  Description    	: This function is called to delete MAC ACL on switch
#         
#  Usage          	: _ntgrDeleteMACACL <switch_name> <mac_acl_rule_list> 
#
#
#*******************************************************************************
proc _ntgrDeleteMACACL {switch_name mac_acl_rule_list} {

  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	_ntgrDeleteMACACLwithoutLogin $connection_id $mac_acl_rule_list
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

################################################################################
#3.1*******************************************************************************
#  Function Name	: _ntgrInterfaceMACACLenablewithoutLogin
#
#  Description    	: This function is called to enable MAC ACL on interface
#         
#  Usage          	: _ntgrInterfaceMACACLenablewithoutLogin <connection_id> <interface_list> <retval>
#
#
#*******************************************************************************
proc _ntgrInterfaceMACACLenablewithoutLogin {connection_id interface_list retval {bind_direction in}} {
  upvar 1 $retval  Ret 
  foreach int_list $interface_list {
      set acl_name [lindex $int_list 0]
      set property_interface_list [lindex $int_list 1]
      foreach acl_int_list $property_interface_list {
          set interface [lindex $acl_int_list 0]
          set sequence [lindex $acl_int_list 1]
          set match [string first "VLAN" $interface]
          if {$match != -1} {
            set vlan_num [string range $interface 4 end]
            set interface "vlan"
          }
          switch -exact -- $interface {
            all {exp_send -i $connection_id "mac access-group $acl_name $bind_direction $sequence\r" 
                  expect -i $connection_id -re "#" 
                  set Ret $expect_out(buffer) 
                } 
            vlan {exp_send -i $connection_id "mac access-group $acl_name vlan $vlan_num $bind_direction $sequence\r"
                  expect -i $connection_id -re "#" 
                  set Ret $expect_out(buffer) 
                  exp_send -i $connection_id "exit\r"
                  sleep 1
                } 
            default {
                  exp_send -i $connection_id "interface $interface\r"
                  sleep 1
                  expect -i $connection_id -re "#"
                  exp_send -i $connection_id "mac access-group $acl_name $bind_direction $sequence\r"
                  sleep 1
                  expect -i $connection_id "mac access-group $acl_name $bind_direction"
                  sleep 1
                  expect -i $connection_id -re "#"
                  set Ret $expect_out(buffer) 
                  sleep 1
                  exp_send -i $connection_id "exit\r"
                  sleep 1
                }
          }
      }
  }
	return $Ret
}

#3*******************************************************************************
#  Function Name	: _ntgrInterfaceMACACLenable
#
#  Description    	: This function is called to enable MAC ACL on interface
#         
#  Usage          	: _ntgrInterfaceMACACLenable <switch_name> <interface_list> 
#
#
#*******************************************************************************
proc _ntgrInterfaceMACACLenable {switch_name interface_list {bind_direction in}} {
  set Ret {}
  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
 	exp_send -i $connection_id "configure\r"
	sleep 1
	expect -i $connection_id -re "#"
  
  _ntgrInterfaceMACACLenablewithoutLogin $connection_id $interface_list Ret $bind_direction
 	#exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return $Ret
}

#4.1*******************************************************************************
#  Function Name	: _ntgrInterfaceMACACLDisablewithoutLogin
#
#  Description    	: This function is called to disable MAC ACL on interface
#         
#  Usage          	: _ntgrInterfaceMACACLDisablewithoutLogin <connection_id> <interface_list> 
#
#
#*******************************************************************************
proc _ntgrInterfaceMACACLDisablewithoutLogin {connection_id interface_list {bind_direction in}} { 
  foreach int_list $interface_list {
    	set acl_name [lindex $int_list 0]
  		set property_interface_list [lindex $int_list 1]
  		foreach acl_int_list $property_interface_list {
      		set interface [lindex $acl_int_list 0]
      		 set match [string first "VLAN" $interface]
           if {$match != -1} {
              set vlan_num [string range $interface 4 end]
              set interface "vlan"
           }		
      	  switch -exact -- $interface {
            all {exp_send -i $connection_id "no mac access-group $acl_name $bind_direction \r"} \
            vlan {exp_send -i $connection_id "no mac access-group $acl_name vlan $vlan_num $bind_direction \r"} \
            default {
                  exp_send -i $connection_id "interface $interface\r"
	            		sleep 1
	            		exp_send -i $connection_id "no mac access-group $acl_name $bind_direction\r"
	            		sleep 1
	            		exp_send -i $connection_id "exit\r"
	            		sleep 1
                }
         
          }
      }
  }
}
################################################################################
#4*******************************************************************************
#  Function Name	: _ntgrInterfaceMACACLDisable
#
#  Description    	: This function is called to disable MAC ACL on interface
#         
#  Usage          	: _ntgrInterfaceMACACLDisable <switch_name> <interface_list> 
#
#
#*******************************************************************************
proc _ntgrInterfaceMACACLDisable {switch_name interface_list {bind_direction in}} {

	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
	exp_send -i $connection_id "configure\r"
	sleep 1
  _ntgrInterfaceMACACLDisablewithoutLogin $connection_id $interface_list $bind_direction
  
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#5*******************************************************************************
#  Function Name	: _ntgrRenameMACACL
#
#  Description    	: This function is called to rename MAC ACL on interface
#         
#  Usage          	: _ntgrRenameMACACL <switch_name> <oldname> <newname>
#
#
#*******************************************************************************
proc _ntgrRenameMACACL {switch_name oldname newname} {

	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	exp_send -i $connection_id "mac access-list extended rename $oldname $newname\r"
	sleep 1
	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}


#6*******************************************************************************
#  Function Name	: _ntgrSetAclTrapflags
#
#  Description    	: This function is called to enbale AclTrapflags
#         
#  Usage          	: _ntgrSetAclTrapflags <switch_name> 
#
#
#*******************************************************************************
proc _ntgrSetAclTrapflags {switch_name} {
  
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]	
	expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "acl-trapflags\r"
  sleep 1
	exp_send -i $connection_id "exit\r"
  Netgear_disconnect_switch $switch_name
}

#7*******************************************************************************
#  Function Name	: _ntgrDisableAclTrapflags
#
#  Description    	: This function is called to enbale AclTrapflags
#         
#  Usage          	: _ntgrDisableAclTrapflags <switch_name> 
#
#
#*******************************************************************************
proc _ntgrDisableAclTrapflags {switch_name} {
  
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]	
	expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "no acl-trapflags\r"
  sleep 1
	exp_send -i $connection_id "exit\r"
  Netgear_disconnect_switch $switch_name
}

#8*******************************************************************************
#  Function Name	: __ntgrgetMACACLMax
#
#  Description    : This function is called to get MACACL max on interface
#         
#  Usage          :	_ntgrgetMACACLMax  <switch_name> <maxium_rules>
#
#
#*******************************************************************************
proc _ntgrgetMACACLMax {switch_name max_rules} {
  upvar 1 $max_rules max_num

	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]	
	expect -i $connection_id -re "#"
	exp_send -i $connection_id "show mac access-list\r"
	expect -i $connection_id -re "#"
  set token "Maximum"
  set pos [string first $token $expect_out(buffer)] 
  set start [expr $pos+27]
  set end   [expr $start+4]
  set max_num [string range $expect_out(buffer) $start $end]
  set max_num [string trim $max_num " "]
  Netgear_disconnect_switch $switch_name
}





#*******************************************************************************
#
#  Function Name  : _ntgrGetMACACLRuleStatus
#
#  Description    : This function is used to get mac acl rule status of a switch.
#
#  Usage          : _ntgrGetMACACLRuleStatus <switch_name> <aclName>
#
#*******************************************************************************
proc _ntgrGetMACACLRuleStatus {switch_name aclName} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show mac access-lists $aclName\r"
    exp_sleep 1
    expect -i $cnn_id -re "show mac access-lists $aclName"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase {Rule Status\.+\s+([^\n]+)} $output match result
    set result [string trim $result]

    Netgear_log_file "lib-global-command.tcl" "get mac acl rule status on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}
#*******************************************************************************
#
#  Function Name  : CALAddMACACLRemark
#
#  Description    : This function is used to add MACACL Remark,<remark> like "\"123456\"" ,must include Double quotation marks
#
#  Usage          : CALAddMACACLRemark <switch_name> <mac_acl_name> <mac_acl_remark>
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALAddMACACLRemark {switch_name mac_acl_name mac_acl_remark} {
   
    set Ret {}
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]

    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "mac access-list extended $mac_acl_name\r"
    sleep 1
    expect -i $connection_id "mac access-list extended $mac_acl_name"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "remark $mac_acl_remark\r"
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
    Netgear_disconnect_switch $switch_name
    return $Ret
}
#*******************************************************************************
#
#  Function Name  : CALDelteMACACLRemark
#
#  Description    : This function is used to delete MACACL Remark,<remark> like "\"123456\"" ,must include Double quotation marks
#
#  Usage          : CALDelteMACACLRemark <switch_name> <ip_acl_id> <mac_acl_remark>
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALDelteMACACLRemark {switch_name mac_acl_name mac_acl_remark} {
   
    set Ret {}
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]

    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "mac access-list extended $mac_acl_name\r"
    sleep 1
    expect -i $connection_id "mac access-list extended $mac_acl_name"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "no remark $mac_acl_remark\r"
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "exit\r"
    exp_send -i $connection_id "exit\r"
    Netgear_disconnect_switch $switch_name
    return $Ret
}
#*******************************************************************************
#
#  Function Name  : CALCheckMACACLRemarkAssociatedRule
#
#  Description    : This function is used to check MACACL Remark with rule is associated,<remark> like "\"123456\"" ,must include Double quotation marks
#
#  Usage          : CALCheckMACACLRemarkAssociatedRule <switch_name> <remark> <expect_rule>
#
#  return         :  1 or 0
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALCheckMACACLRemarkAssociatedRule  {switch_name remark expect_rule} {
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
#  Function Name  : CALCheckMACACLRemarkExist
#
#  Description    : This function is used to check MACACL Remark is Exist,
#                    <remark> like "\"123456\"" ,must include Double quotation marks
#
#  Usage          : CALCheckMACACLRemarkExist <switch_name> <remark>
#
#  Return         : 1 or 0
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALCheckMACACLRemarkExist  {switch_name remark} {
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
#  Function Name  : CALDeleteMACACLRule
#
#  Description    : This function is used to delete MACACL rule
#
#  Usage          : CALDeleteMACACLRule <switch_name> <mac_acl_name> <rule_id>
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALDeleteMACACLRule  {switch_name mac_acl_name rule_id} {
    set Ret {}
    Netgear_connect_switch $switch_name
    set connection_id [_get_switch_handle $switch_name]

    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "mac access-list extended $mac_acl_name\r"
    sleep 1
    expect -i $connection_id "mac access-list extended $mac_acl_name"
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
#  Function Name  : CALGetMACACLRuleByID
#
#  Description    : This function is used to delete MACACL rule
#
#  Usage          : CALGetMACACLRuleByID <switch_name> <mac_acl_name> <rule_id>
#
#  return         : rule [list]
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALGetMACACLRuleByID  {switch_name mac_acl_name rule_id} {

    set output {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show mac access-lists $mac_acl_name\r"
    exp_sleep 1
    expect -i $cnn_id -re "show mac access-lists"
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
#  Function Name  : CALICheckMACACLRuleWithID
#
#  Description    : This function is used to  check MAC ACL rule by ID and expect rule
#
#  Usage          : CALICheckMACACLRuleWithID <switch_name> <mac_acl_name> <rule_id> <expect_rule>
#
#  return         : 1 or 0
#
#  Author         : zhenwei.li
#
#******************************************************************************* 
proc CALICheckMACACLRuleWithID  {switch_name mac_acl_name rule_id expect_rule} {
    set actual_rule [CALGetMACACLRuleByID $switch_name $mac_acl_name $rule_id]
	if {$expect_rule == $actual_rule} {
		set result 1
		} else { set result 0}
		
	return $result
}
#*******************************************************************************
#
#  Function Name  : CALAddMACACLAndCheckResult
#
#  Description    : This function is used to check MAC ACL add success or fail
#
#  Usage          : CALAddMACACLAndCheckResult <switch_name> <mac_acl_rule_list>
#
#  return         : 1 or 0 (1--success,0--fail) 
#
#  Author         : zhenwei.li
#
#******************************************************************************* 
proc CALAddMACACLAndCheckResult {switch_name mac_acl_rule_list} {
  set Ret {}
  Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	  expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
  	_ntgrAddMACACLwithoutLogin $mac_acl_rule_list $connection_id Ret
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
#  Function Name  : CALResequenceMACACL
#
#  Description    : This function is used to Resequence MAC ACL rule id
#
#  Usage          : CALResequenceMACACL <switch_name> <mac_acl_name> <rule_id> <increment>
#
#
#  Author         : zhenwei.li
#
#******************************************************************************* 
proc CALResequenceMACACL {switch_name mac_acl_name rule_id {increment 10}} {
    Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	  expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
    exp_send -i $connection_id "mac access-list resequence $mac_acl_name $rule_id $increment\r"
    sleep 1
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name

}