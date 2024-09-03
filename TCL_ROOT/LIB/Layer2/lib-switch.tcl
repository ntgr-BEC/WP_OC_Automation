####################################################################################
#  File Name   : lib-switch.tcl
#
#  Description :
#        This file includes functions used for switch configuration.
#
#  History     :
#        Date          Programmer         Description
#        7/29/2011     Tony Jing          Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrSwitchRenumber
#
#  Description    : This function is called to renumber switch unit id on specified
#                   switch.
#
#  Usage          : _ntgrSwitchRenumber <switch_name> <fromunit> <tounit>
#
#*******************************************************************************
proc _ntgrSwitchRenumber {switch_name fromunit tounit} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "switch $fromunit renumber $tounit\r"
    exp_sleep 1
    expect -i $cnn_id -re "\(y/n\)" {
  		exp_send -i $cnn_id "y"
	}
    exp_sleep 1
    
    Netgear_log_file "lib-switch.tcl" "switch renumber on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSwitchSetPriority
#
#  Description    : This function is called to set switch priority on specified
#                   switch.
#
#  Usage          : _ntgrSwitchSetPriority <switch_name> <unitid> <priority>
#
#*******************************************************************************
proc _ntgrSwitchSetPriority {switch_name unitid priority} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "switch $unitid priority $priority\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
	exp_sleep 1
    
    Netgear_log_file "lib-switch.tcl" "set switch priority on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSwitchResetPriority
#
#  Description    : This function is called to reset switch priority on specified
#                   switch.
#
#  Usage          : _ntgrSwitchResetPriority <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrSwitchResetPriority {switch_name unitid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no switch $unitid priority\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
	exp_sleep 1
    
    Netgear_log_file "lib-switch.tcl" "set switch priority on switcher"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrSwitchGetPriority
#
#  Description    : This function is used to get switch priority.
#
#  Usage          : _ntgrSwitchGetPriority <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrSwitchGetPriority {switch_name unitid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show switch $unitid\r"
    exp_sleep 1
    expect -i $cnn_id -re "show switch $unitid"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
    
    set result ""
    regexp -nocase {Switch Priority\.+\s+([^\r\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-switch.tcl" "getting switch priority on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrSwitchGetSerialNumber
#
#  Description    : This function is used to get switch serial number.
#
#  Usage          : _ntgrSwitchGetSerialNumber <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrSwitchGetSerialNumber {switch_name unitid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show switch $unitid\r"
    exp_sleep 1
    expect -i $cnn_id -re "show switch $unitid"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
    
    set result ""
    regexp -nocase {Serial Number\.+\s+([^\r\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-switch.tcl" "getting switch serial number on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrSwitchGetSwitchStatus
#
#  Description    : This function is used to get switch status.
#
#  Usage          : _ntgrSwitchGetSwitchStatus <switch_name> <unitid>
#
#*******************************************************************************
proc _ntgrSwitchGetSwitchStatus {switch_name unitid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show switch $unitid\r"
    exp_sleep 1
    expect -i $cnn_id -re "show switch $unitid"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
    
    set result ""
    regexp -nocase {Switch Status\.+\s+([^\r\n]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-switch.tcl" "getting switch status on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#  Function Name    : CALntgrConfigLinkDependencyGroup
#
#  Description      : This function is called to Configuration Link Dependency Group
#
#  Usage            : CALntgrConfigLinkDependencyGroup <switch_name> <group_id> <link_action>
#
#*******************************************************************************
proc CALntgrConfigLinkDependencyGroup {switch_name group_id link_action} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "link state group $group_id action $link_action"
    ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name    : CALntgrConfigLinkDependencyInterface
#
#  Description      : This function is called to Configuration Link Dependency Interface
#
#  Usage            : CALntgrConfigLinkDependencyInterface <switch_name> <interface> <group_id> <stream_action>
#
#*******************************************************************************
proc CALntgrConfigLinkDependencyInterface {switch_name interface group_id stream_action} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
    ntgrCLIExecute $switch_name "link state group $group_id $stream_action"
    ntgrCLIExecute $switch_name "exit"
    ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name    : CALntgrResetLinkDependencyInterface
#
#  Description      : This function is called to reset Link Dependency Interface
#
#  Usage            : CALntgrResetLinkDependencyInterface <switch_name> <interface> <group_id>
#
#*******************************************************************************
proc CALntgrResetLinkDependencyInterface {switch_name interface group_id} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
    ntgrCLIExecute $switch_name "no link state group $group_id downstream"
    ntgrCLIExecute $switch_name "no link state group $group_id upstream"
    ntgrCLIExecute $switch_name "exit"
    ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name    : CALntgrGetLinkDependencyDetail
#
#  Description      : This function is called to reset Link Dependency Interface
#
#  Usage            : CALntgrResetLinkDependencyInterface <switch_name> <group_id>
#
#*******************************************************************************
proc CALntgrGetLinkDependencyDetail {switch_name group_id} {
    set link(GroupId) ""
    set link(LinkAction) ""
    set link(GroupState) ""
    set link(DownstreamUpList) ""
    set link(DownstreamDownList) ""
    set link(UpstreamUpList) ""
    set link(UpstreamDownList) ""
    set cmds "show link state group $group_id detail"
    set retStr ""
    CALCheckExpect $switch_name $cmds retStr 
    set tmpList [split $retStr \n] 
    for {set i 0} {$i < [llength $tmpList]} {incr i} {
        set iList [lindex $tmpList $i]
        if {[regexp -nocase -- "^GroupId:\\s+(\[0-9\]+)" $iList match group_id]} {
            set link(GroupId) $group_id
        }
        if {[regexp -nocase -- "^Link\\s+Action:\\s+(\[A-Za-z\]+)" $iList match link_action]} {
            set link(LinkAction) $link_action
        }
        if {[regexp -nocase -- "^Group\\s+State:\\s+(\[A-Za-z\]+)" $iList match group_state]} {
            set link(GroupState) $group_state
        }
        if {[regexp -nocase -- "^Downstream\\s+Interface\\s+State:" $iList match]} {
           set downstream_up_list  [lindex $tmpList [expr $i + 1]]
           if {[regexp -nocase -- "^Link\\s+Up:\\s+(\[A-Za-z0-9/\\-, \]+)" $downstream_up_list match link_up_down]} {
               set link(DownstreamUpList) $link_up_down
           }
           incr i
           set downstream_down_list  [lindex $tmpList [expr $i + 1]]
           if {[regexp -nocase -- "^Link\\s+Down:\\s+(\[A-Za-z0-9/\\-, \]+)" $downstream_down_list match link_down_down]} {
               set link(DownstreamDownList) $link_down_down
           }
        }
        if {[regexp -nocase -- "^Upstream\\s+Interface\\s+State:" $iList match]} {
           set upstream_up_list  [lindex $tmpList [expr $i + 1]]
           if {[regexp -nocase -- "^Link\\s+Up:\\s+(\[A-Za-z0-9/\\-, \]+)" $upstream_up_list match link_up_up]} {
               set link(UpstreamUpList) $link_up_up
           }
           incr i
           set upstream_down_list  [lindex $tmpList [expr $i + 1]]
           if {[regexp -nocase -- "^Link\\s+Down:\\s+(\[A-Za-z0-9/\\-, \]+)" $upstream_down_list match link_down_up]} {
               set link(UpstreamDownList) $link_down_up
           }
        }
    }
    
    return [array get link]
}

#*******************************************************************************
#  Function Name    : CALntgrConfigLLPFPerInterface
#
#  Description      : This function is called to Configuration LLPF for Interface
#
#  Usage            : CALntgrConfigLLPFPerInterface <switch_name> <interface> <config_flag> <block_flag>
#
#  parameter        :
#                     config_flag      :  enable/disable
#                     block_flag       :  all/dtp/isdp/pagp/sstp/udld/vtp
#
#*******************************************************************************
proc CALntgrConfigLLPFPerInterface {switch_name interface config_flag block_flag} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
    set cmd ""
    if {$config_flag == "enable"} {
        if {$block_flag == "dtp"} {
            set cmd "llpf blockdtp"
        } elseif {$block_flag == "isdp"} {
            set cmd "llpf blockisdp"
        } elseif {$block_flag == "pagp"} {
            set cmd "llpf blockpagp"
        } elseif {$block_flag == "sstp"} {
            set cmd "llpf blocksstp"
        } elseif {$block_flag == "udld"} {
            set cmd "llpf blockudld"
        } elseif {$block_flag == "vtp"} {
            set cmd "llpf blockvtp"
        } else {
            set cmd "llpf blockall"
        }
    } else {
        if {$block_flag == "dtp"} {
            set cmd "no llpf blockdtp"
        } elseif {$block_flag == "isdp"} {
            set cmd "no llpf blockisdp"
        } elseif {$block_flag == "pagp"} {
            set cmd "no llpf blockpagp"
        } elseif {$block_flag == "sstp"} {
            set cmd "no llpf blocksstp"
        } elseif {$block_flag == "udld"} {
            set cmd "no llpf blockudld"
        } elseif {$block_flag == "vtp"} {
            set cmd "no llpf blockvtp"
        } else {
            set cmd "no llpf blockall"
        }
    }
    ntgrCLIExecute $switch_name $cmd
    ntgrCLIExecute $switch_name "exit"
    ntgrCLIExecute $switch_name "exit"
}


