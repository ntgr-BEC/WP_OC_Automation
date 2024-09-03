####################################################################################
#  File Name   : lib-ntgr-stk.tcl                                                  #
#                                                                                  #
#  Description :                                                                   #
#        This file includes functions used for switches stacking configuration.    #
#                                                                                  #
#  History     :                                                                   #
#        Date          Programmer         Description                              #
#        15/08/2006    Scott Zhang        Created                                  #
#                                                                                  #
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrStkSetUnitPriority
#
#  Description    : This function is called to configure the unit priority of a
#                   stack.
#
#  Usage          : _ntgrStkSetUnitPriority <switch_name {unitID 0} {priority 0}>
#
#*******************************************************************************
proc _ntgrStkSetUnitPriority {switch_name {unitID 0} {priority 0} } {    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    Netgear_log_file "lib-ntgr-stk.tcl" "About to configure unit priority"
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$unitID ==0} {
        global ntgr_unitList_$switch_name
        
        for_array_keys unit_index ntgr_unitList_$switch_name {
            set unit_id         [_ntgrStkGetUnitID $switch_name $unit_index]
            set priority        [_ntgrStkGetUnitPriority $switch_name $unit_index]
            exp_send -i $cnn_id "switch $unit_id priority $priority\r"
            exp_sleep 1
        }
    } else {
        exp_send -i $cnn_id "switch $unitID priority $priority\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkRenumberUnit
#
#  Description    : This function is called to renumber the unit ID of a stack.
#
#  Usage          : _ntgrStkRenumberUnit <switch_name old_id new_id>
#
#*******************************************************************************
proc _ntgrStkRenumberUnit {switch_name old_id new_id} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    Netgear_log_file "lib-ntgr-stk.tcl" "About to renumber unit id from $old_id to $new_id"

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "switch $old_id renumber $new_id\r"
    exp_sleep 1
    exp_send -i $cnn_id "y\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkAddMember
#
#  Description    : This function is called to add a new unit to the stack.
#
#  Usage          : _ntgrStkAddMember <switch_name unit_id unit_type>
#
#*******************************************************************************
proc _ntgrStkAddMember {switch_name unit_id unit_type} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    Netgear_log_file "lib-ntgr-stk.tcl" "About to add stack member unit id = $unit_id, unit type = $unit_type"

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1
    exp_send -i $cnn_id "member $unit_id $unit_type\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkDelMember
#
#  Description    : This function is called to delete a unit from the stack.
#
#  Usage          : _ntgrStkDelMember <switch_name unit_id>
#
#*******************************************************************************
proc _ntgrStkDelMember {switch_name unit_id} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    Netgear_log_file "lib-ntgr-stk.tcl" "About to delete stack member unit id = $unit_id"

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1
    exp_send -i $cnn_id "no member $unit_id\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkMoveMgrmt
#
#  Description    : This function is called to move the management switch to
#                   another unit of the stack.
#
#  Usage          : _ntgrStkMoveMgrmt <switch_name to_unit_id>
#
#*******************************************************************************
proc _ntgrStkMoveMgrmt {switch_name to_unit_id} {
    set master_id [_ntgrStkGetMasterID $switch_name]
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    Netgear_log_file "lib-ntgr-stk.tcl" "About to move primary management from $master_id to $to_unit_id"
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1

    set cnt_loop 0
    while {1} {
        incr cnt_loop
        exp_send -i $cnn_id "movemanagement $master_id $to_unit_id\r"
        exp_sleep 1
        exp_send -i $cnn_id "y\r"
        exp_sleep 1
        expect -i $cnn_id "(Config-stack)#"
        exp_sleep 1
        expect -i $cnn_id "\)>" {
            break
        }
    }

    if { $cnt_loop > 1 } {
        Netgear_log_file "lib-ntgr-stk.tcl" "It takes $cnt_loop times to move primary management from $master_id to $to_unit_id."
        Netgear_log_file "lib-ntgr-stk.tcl" "It seems that [expr $cnt_loop - 1] time(s) failed for command 'movemanagement $master_id $to_unit_id'."
    }
    # It's important to exchange access info.
    _ntgrStkExchangeUnitInfoWhenMoveMgrmt $switch_name $master_id $to_unit_id

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStackMoveMgrmt
#
#  Description    : This function is called to move the management switch to
#                   another unit of the stack.
#
#  Usage          : _ntgrStackMoveMgrmt <switch_name to_unit_id>
#
#*******************************************************************************
proc _ntgrStackMoveMgrmt {switch_name to_unit_id} {
    set master_id [_ntgrStkGetMasterID $switch_name]
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    Netgear_log_file "lib-ntgr-stk.tcl" "About to move primary management from $master_id to $to_unit_id"
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1

    set cnt_loop 0
    while {1} {
        incr cnt_loop
        exp_send -i $cnn_id "movemanagement $master_id $to_unit_id\r"
        exp_sleep 1
        exp_send -i $cnn_id "y\r"
        exp_sleep 1
        expect -i $cnn_id "(Config-stack)#"
        exp_sleep 1
        expect -i $cnn_id "\)>" {
            break
        }
    }

    if { $cnt_loop > 1 } {
        Netgear_log_file "lib-ntgr-stk.tcl" "It takes $cnt_loop times to move primary management from $master_id to $to_unit_id."
        Netgear_log_file "lib-ntgr-stk.tcl" "It seems that [expr $cnt_loop - 1] time(s) failed for command 'movemanagement $master_id $to_unit_id'."
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkQosModeEnable
#
#  Description    : This function is called to enable QOS Mode for Front Panel
#                   Stacking on all interfaces.
#
#  Usage          : _ntgrStkQosModeEnable <switch_name>
#
#*******************************************************************************
proc _ntgrStkQosModeEnable { switch_name } {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    Netgear_log_file "lib-ntgr-stk.tcl" "About to enable Qos mode for front panel stacking on all interfaces."

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1
    exp_send -i $cnn_id "qos-mode\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkQosModeDisable
#
#  Description    : This function is called to disable QOS Mode for Front Panel
#                   Stacking on all interfaces.
#
#  Usage          : _ntgrStkQosModeDisable <switch_name>
#
#*******************************************************************************
proc _ntgrStkQosModeDisable { switch_name } {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    Netgear_log_file "lib-ntgr-stk.tcl" "About to disable Qos mode for front panel stacking on all interfaces."

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1
    exp_send -i $cnn_id "no qos-mode\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkArchiveCopy
#
#  Description    : This function is called to copy the image from master to
#                   other stack member. It needs reboot to let the new image
#                   take effect on those stack members.
#
#  Usage          : _ntgrStkArchiveCopy <switch_name> <dest_unit_id 0>
#
#*******************************************************************************
proc _ntgrStkArchiveCopy {switch_name {dest_unit_id 0}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    Netgear_log_file "lib-ntgr-stk.tcl" "About to execute archive copy image"

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1
    if { $dest_unit_id == 0 } {
        exp_send -i $cnn_id "archive copy-sw\r"
    } else {
        exp_send -i $cnn_id "archive copy-sw destination-system $dest_unit_id\r"
    }
    exp_sleep 1
    exp_send -i $cnn_id "y\r"
    exp_sleep 1
    expect -i $cnn_id -re "archive copy-sw"
    expect -i $cnn_id {
        timeout exp_continue
        -re "#" ;#Do nothing, just exit expect
    }

    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkArchiveDownload
#
#  Description    : This function is called to copy the image from a tftp server
#                   to the stack. It needs reboot to let the new image
#                   take effect on those stack members.
#
#  Usage          : _ntgrStkArchiveDownload <switch_name> <tftp_path>
#
#*******************************************************************************
proc _ntgrStkArchiveDownload {switch_name tftp_path} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    Netgear_log_file "lib-ntgr-stk.tcl" "About to execute archive download image from tftp server..."

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1
    exp_send -i $cnn_id "archive download-sw $tftp_path\r"
    exp_sleep 1
    exp_send -i $cnn_id "y\r"
    exp_sleep 1
    expect -i $cnn_id -re "archive"
    expect -i $cnn_id {
        timeout exp_continue
        -re "#" ;#Do nothing, just exit expect
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkConfigStackPortMode
#
#  Description    : This function is called to configure the stack port's mode.
#                   It's only for front panel stacking.
#
#  Usage          : _ntgrStkConfigStackPortMode <cnn_id if_name mode>
#
#*******************************************************************************
proc _ntgrStkConfigStackPortMode { cnn_id if_name mode} {
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack-port $if_name $mode\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkSetStackPortMode
#
#  Description    : This function is called to configure the stack ports' mode.
#                   It's only for front panel stacking.
#
#  Usage          : _ntgrStkSetStackPortMode <switch_name>
#
#*******************************************************************************
proc _ntgrStkSetStackPortMode { switch_name } {
    global ntgr_unitList_$switch_name

    Netgear_log_file "lib-ntgr-stk.tcl" "About to set stack ports' mode"

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    for_array_keys unit_index ntgr_unitList_$switch_name {
        set portlist        [_ntgrStkGetUnitStackPortList $switch_name $unit_index]
        foreach portinfo $portlist {
            set if_name [lindex $portinfo 0]
            set mode    [lindex $portinfo 1]
            _ntgrStkConfigStackPortMode $cnn_id $if_name $mode
        }
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkGetMasterID
#
#  Description    : This function is called to get the master unit id from switch.
#
#  Usage          : _ntgrStkGetMasterID <switch_name>
#
#*******************************************************************************
proc _ntgrStkGetMasterID { switch_name } {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show switch\r"
    exp_sleep 1
    expect -i $cnn_id -re "show switch"
    exp_sleep 1
    expect -i $cnn_id -re "#"   

    set output $expect_out(buffer)
    
    set id 0
    regexp -nocase -line {^(\d+)\s+Mgmt Sw\s} $output dummy id

    Netgear_log_file "lib-ntgr-stk.tcl" "get stack master id on $switch_name: $id"
    Netgear_disconnect_switch $switch_name

    return $id
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkGetSwitchNameByID
#
#  Description    : This function is called to get switch name by unit id from switch.
#
#  Usage          : _ntgrStkGetSwitchNameByID <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrStkGetSwitchNameByID { switch_name unitid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""    
    exp_send -i $cnn_id "show switch\r"
    exp_sleep 1
    expect -i $cnn_id -re "show switch"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)    
    set output_list [split $output "\n"]
    
    set index_start 0
    set index_end 0
    set expect_str ""
    set result_str ""
    foreach item_str $output_list {
        set item_str [string trim $item_str]
        if {[regexp {^-+} $item_str]} {
            set flag_str "[lindex $item_str 0] [lindex $item_str 1] [lindex $item_str 2]"
            set index_start [expr [string length $flag_str] + 1]
            append flag_str " [lindex $item_str 3]"
            set index_end [expr [string length $flag_str] - 1]
        }
        
        if {[regexp "^$unitid\\s+" $item_str]} {
            set expect_str $item_str
            break
        }
    }
    set result_str [string trim [string range $expect_str $index_start $index_end]]

    Netgear_log_file "lib-ntgr-stk.tcl" "get switch name by unit id on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result_str
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkGetSupportedSwitchtypeIDByName
#
#  Description    : This function is called to get supported switchtype id by name from switch.
#
#  Usage          : _ntgrStkGetSupportedSwitchtypeIDByName <switch_name> <name>
#
#*******************************************************************************
proc _ntgrStkGetSupportedSwitchtypeIDByName { switch_name name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    exp_send -i $cnn_id "show supported switchtype\r"
    exp_sleep 1
    expect -i $cnn_id -re "show supported switchtype"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    
    set id ""
    regexp -nocase -line "^\(\\d+\)\\s+$name" $output dummy id
    
    Netgear_log_file "lib-ntgr-stk.tcl" "get supported switchtype id by name on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $id
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetNamefromSwitchInfo
#
#  Description    : This function is called to get switch name via CLI
#
#  Usage          : _ntgrGetNamefromSwitchInfo <switch_name>
#
#*******************************************************************************
proc _ntgrGetNamefromSwitchInfo { switch_name } {
    Netgear_connect_switch $switch_name
    set retlist {}
    for {set _i 0} {$_i <10} {incr _i} {
      set str($_i) {}
    }
    set cnn_id [_get_switch_handle $switch_name]
    expect -i $cnn_id "#" {}
    exp_send -i $cnn_id "show switch\r"
    exp_sleep 1
    set result ""
    expect -i $cnn_id "#" { set result $expect_out(buffer)}
    set output [split $result "\n"]
    foreach output_line $output {
      set pattern1 "(\[0-9])(\[ ]+)(Mgmt Sw)(\[ ]+)(\[0-9a-zA-Z\]+)(\[ ]+)(\[0-9a-zA-Z\]+)(\[ ]+)(OK)"
      set pattern2 "(\[0-9])(\[ ]+)(Stack Mbr  Oper Stby)(\[ ]+)(\[0-9a-zA-Z\]+)(\[ ]+)(\[0-9a-zA-Z\]+)(\[ ]+)(OK)"
      set ret [regexp $pattern1 $output_line match str0 str1 str2 str3 str4 str5 str6 str7 str8 str9]
      if $ret { lappend retlist $str4; continue } 
      set ret [regexp $pattern2 $output_line match str0 str1 str2 str3 str4 str5 str6 str7 str8 str9]
      if $ret { lappend retlist $str4; continue }  
    }
    Netgear_disconnect_switch $switch_name
    return $retlist
}







#*******************************************************************************
#
#  Function Name  : _ntgrStkMoveManagement
#
#  Description    : This function is called to move the management switch to
#                   another unit of the stack.
#
#  Usage          : _ntgrStkMoveManagement <switch_name> <master_id> <other_id>
#
#*******************************************************************************
proc _ntgrStkMoveManagement {switch_name master_id other_id} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1   
    exp_send -i $cnn_id "movemanagement $master_id $other_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "\(y/n\)" {
        exp_send -i $cnn_id "y"
	}

    sleep 5
    Netgear_log_file "lib-ntgr-stk.tcl" "movemanagement $master_id to $other_id on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkNsfEnable
#
#  Description    : This function is called to enable stack nsf on specified
#                   switch.
#
#  Usage          : _ntgrStkNsfEnable <switch_name>
#
#*******************************************************************************
proc _ntgrStkNsfEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1
    exp_send -i $cnn_id "nsf\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ntgr-stk.tcl" "enable stack nsf on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkNsfDisable
#
#  Description    : This function is called to disable stack nsf on specified
#                   switch.
#
#  Usage          : _ntgrStkNsfDisable <switch_name>
#
#*******************************************************************************
proc _ntgrStkNsfDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1
    exp_send -i $cnn_id "no nsf\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ntgr-stk.tcl" "disable stack nsf on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkSetStandbyUnit
#
#  Description    : This function is called to set standby unit on specified
#                   switch.
#
#  Usage          : _ntgrStkSetStandbyUnit <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrStkSetStandbyUnit {switch_name unitid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1
    exp_send -i $cnn_id "standby $unitid\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ntgr-stk.tcl" "set standby unit on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkResetStandbyUnit
#
#  Description    : This function is called to reset standby unit on specified
#                   switch.
#
#  Usage          : _ntgrStkResetStandbyUnit <switch_name>
#
#*******************************************************************************
proc _ntgrStkResetStandbyUnit {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1
    exp_send -i $cnn_id "no standby\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ntgr-stk.tcl" "reset standby unit on switcher"
    Netgear_disconnect_switch $switch_name
}





#*******************************************************************************
#
#  Function Name  : _ntgrStkMasterIDCheck
#
#  Description    : This function is called to checking unitid whether is master unit from switch.
#
#  Usage          : _ntgrStkMasterIDCheck <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrStkMasterIDCheck { switch_name unitid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show switch\r"
    exp_sleep 1
    expect -i $cnn_id -re "show switch"
    exp_sleep 1
    expect -i $cnn_id -re "#"   

    set output $expect_out(buffer)
    
    set regstr "^$unitid\\s+Mgmt Sw\\s"
    if {[regexp -nocase -line $regstr $output]} {
        set result 1
    } else {
        set result 0
    }
    
    Netgear_log_file "lib-ntgr-stk.tcl" "checking unitid whether is master unit on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkStandbyIDCheck
#
#  Description    : This function is called to checking unitid whether is standby unit from switch.
#
#  Usage          : _ntgrStkStandbyIDCheck <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrStkStandbyIDCheck { switch_name unitid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show switch\r"
    exp_sleep 1
    expect -i $cnn_id -re "show switch"
    exp_sleep 1
    expect -i $cnn_id -re "#"   

    set output $expect_out(buffer)
    
    set regstr "^$unitid\\s+Stack Mbr\\s+\\w+\\sStby\\s"
    if {[regexp -nocase -line $regstr $output]} {
        set result 1
    } else {
        set result 0
    }
    
    Netgear_log_file "lib-ntgr-stk.tcl" "checking unitid whether is standby unit on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkMemberIDCheck
#
#  Description    : This function is called to checking unitid whether is exist from switch.
#
#  Usage          : _ntgrStkMemberIDCheck <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrStkMemberIDCheck { switch_name unitid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show switch\r"
    exp_sleep 1
    expect -i $cnn_id -re "show switch"
    exp_sleep 1
    expect -i $cnn_id -re "#"   

    set output $expect_out(buffer)
    
    set regstr "^$unitid\\s+"
    if {[regexp -nocase -line $regstr $output]} {
        set result 1
    } else {
        set result 0
    }
    
    Netgear_log_file "lib-ntgr-stk.tcl" "checking unitid whether is exist on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}




#*******************************************************************************
#
#  Function Name  : _ntgrGetActiveImageByUnitID
#
#  Description    : This function is used to get active image by unit id.
#
#  Usage          : _ntgrGetActiveImageByUnitID <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrGetActiveImageByUnitID {switch_name unitid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show bootvar\r"
    exp_sleep 1
    expect -i $cnn_id -re "show bootvar"
    exp_sleep 1
    expect -i $cnn_id -re "#"   

    set output $expect_out(buffer)
    
    set result_str ""
    set output [split $output "\n"]
    foreach output_line $output {
        set output_line [string trim $output_line]
        if {[regexp "^$unitid\\s+" $output_line]} {
            set result_str [lindex $output_line 3]
            break
        }
    }

    Netgear_log_file "lib-ntgr-stk.tcl" "get active image by unit id on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result_str
}


#*******************************************************************************
#
#  Function Name  : _ntgrStkGetSwitchVerByID
#
#  Description    : This function is called to get switch version by unit id from switch.
#
#  Usage          : _ntgrStkGetSwitchVerByID <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrStkGetSwitchVerByID { switch_name unitid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""    
    exp_send -i $cnn_id "show switch\r"
    exp_sleep 1
    expect -i $cnn_id -re "show switch"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)    
    set output_list [split $output "\n"]
    
    set index_start 0
    set index_end 0
    set expect_str ""
    set result_str ""
    foreach item_str $output_list {
        set item_str [string trim $item_str]
        if {[regexp {^-+} $item_str]} {
            set flag_str "[lindex $item_str 0] [lindex $item_str 1] [lindex $item_str 2] [lindex $item_str 3] [lindex $item_str 4] [lindex $item_str 5]"
            set index_start [expr [string length $flag_str] + 1]
            append flag_str " [lindex $item_str 6]"
            set index_end [expr [string length $flag_str] - 1]
        }
        
        if {[regexp "^$unitid\\s+" $item_str]} {
            set expect_str $item_str
            break
        }
    }
    set result_str [string trim [string range $expect_str $index_start $index_end]]

    Netgear_log_file "lib-ntgr-stk.tcl" "get switch version by unit id on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result_str
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkGetSwitchStatusByID
#
#  Description    : This function is called to get switch status by unit id from switch.
#
#  Usage          : _ntgrStkGetSwitchStatusByID <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrStkGetSwitchStatusByID { switch_name unitid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""    
    exp_send -i $cnn_id "show switch\r"
    exp_sleep 1
    expect -i $cnn_id -re "show switch"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)    
    set output_list [split $output "\n"]
    
    set index_start 0
    set index_end 0
    set expect_str ""
    set result_str ""
    foreach item_str $output_list {
        set item_str [string trim $item_str]
        if {[regexp {^-+} $item_str]} {
            set flag_str "[lindex $item_str 0] [lindex $item_str 1] [lindex $item_str 2] [lindex $item_str 3] [lindex $item_str 4]"
            set index_start [expr [string length $flag_str] + 1]
            append flag_str " [lindex $item_str 5]"
            set index_end [expr [string length $flag_str] - 1]
        }
        
        if {[regexp "^$unitid\\s+" $item_str]} {
            set expect_str $item_str
            break
        }
    }
    set result_str [string trim [string range $expect_str $index_start $index_end]]

    Netgear_log_file "lib-ntgr-stk.tcl" "get switch version by unit id on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result_str
}





#*******************************************************************************
#
#  Function Name  : _ntgrStkAddMemberErrorCheck
#
#  Description    : This function is used to check stack add member whether is correct.
#
#  Usage          : _ntgrStkAddMemberErrorCheck <switch_name> <unit_id> <unit_type>
#
#*******************************************************************************
proc _ntgrStkAddMemberErrorCheck {switch_name unit_id unit_type} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "stack\r"
    exp_sleep 1
    exp_send -i $cnn_id "member $unit_id $unit_type\r"
    exp_sleep 1
    expect -i $cnn_id -re "member $unit_id $unit_type"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    
    if {[regexp -nocase {already exists} $output]} {
        set result 1
    } else {
        set result 0
    }
    
    Netgear_log_file "lib-ntgr-stk.tcl" "checking command line of stack adding member on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}






#*******************************************************************************
#
#  Function Name  : _ntgrStkPortStatusCheck
#
#  Description    : This function is used to check stack port status up or down.
#
#  Usage          : _ntgrStkPortStatusCheck <switch_name> <status>
#
#*******************************************************************************
proc _ntgrStkPortStatusCheck {switch_name {status up}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show stack-port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show stack-port"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
    
    if {$status == "up"} {
        set expectstr "Link Up"
    } else {
        set expectstr "Link Down"
    }
    
    set result 0
    set output_list [split $output "\n"]
    foreach item $output_list {
        set item [string trim $item]
        if {[regexp -nocase {^\d+.+Stack} $item]} {
            if {[regexp -nocase "$expectstr" $item]} {
                incr result
            }
        }
    }

    Netgear_log_file "lib-ntgr-stk.tcl" "check stack port status up or down on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkPortDiagnosticsErrorCheck
#
#  Description    : This function is used to check stack port diagnostic error.
#
#  Usage          : _ntgrStkPortDiagnosticsErrorCheck <switch_name> <stackportnum>
#
#*******************************************************************************
proc _ntgrStkPortDiagnosticsErrorCheck {switch_name stackportnum} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show stack-port diag\r"
    exp_sleep 1
    expect -i $cnn_id -re "show stack-port diag"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
    
    set strNum [regexp -nocase -all {TERR:0} $output]
    if {$strNum == $stackportnum} {
        set result 0
    } else {
        set result 1
    }

    Netgear_log_file "lib-ntgr-stk.tcl" "check stack port diagnotic error on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}
