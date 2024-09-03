###############################################################
#   Function Name   :   ntgrSwitchiSCSISetConfiguration
#
#   Author          :   Luiz.Yao
#
#   Date            :   2016/11/21
#
#   Parameter       :   strDevice
#                       iscsi_config -- contain global status/remark status etc.
#
#   Return Value    :   None
################################################################
proc ntgrSwitchiSCSISetConfiguration { strDev iscsi_config } {
    # start configure 
    ntgrCLIExecute $strDev "configure"
    
    # config global status, default is enable
    set global_status "enable"
    if {[keylget iscsi_config globalstatus global_status]} {
        if {[string equal $global_status "disable"]} {
            ntgrCLIExecute $strDev "no iscsi enable"    
        } else {
            ntgrCLIExecute $strDev "iscsi $global_status"   
        }                
    }
    
    # config qos profile, default profile is vpt
    set qos_profile "vpt"
    if {[keylget iscsi_config qosprofile qos_profile ]} {
        # default profile value
        set vpt_or_dscp_value "5"
        keylget iscsi_config vptordscpvalue vpt_or_dscp_value
        
        # get remark status, default is enable
        set remark_status ""
        if {[keylget iscsi_config remarkstatus remark_status]} {
            if {[string equal -nocase $remark_status "disable"]} {
                set remark_status ""
            } elseif {[string equal -nocase $remark_status "enable"]} {
                set remark_status "remark"
            }
        }          
        ntgrCLIExecute $strDev "iscsi cos $qos_profile $vpt_or_dscp_value $remark_status"
        ntgrCLIExecute $strDev "iscsi cos enable"
    }
    
    # config aging time, default value is 10 mins
    set aging_time "10"
    if {[keylget iscsi_config agingtime aging_time]} {
        ntgrCLIExecute $strDev "iscsi aging time $aging_time"
    }  
    
    # end configure    
    ntgrCLIExecute $strDev "exit"     
}

###############################################################
#   Function Name   :   ntgrSwitchiSCSIGetConfiguration
#
#   Author          :   Luiz.Yao
#
#   Date            :   2016/11/21
#
#   Parameter       :   strDevice
#
#   Return Value    :   a keylset
################################################################
proc ntgrSwitchiSCSIGetConfiguration { strDev } {
    set strOutput [ntgrCLIExecute $strDev "show iscsi"]
    
    # return iscsi global status
    if {[regexp -nocase {iSCSI enabled} $strOutput]} {
        set global_status "enable"
    } else {
        set global_status "disable"
    }
    keylset iscsi_info globalstatus $global_status
    Netgear_log_file "Get iscsi global status" $global_status
    
    # return iscsi cos profile and profile value
    set str_match_cos_profil ""
    set str_match_profile_value ""
    if {[regexp -nocase {iSCSI ([dscpvt]+) is (\d+)} $strOutput str_match str_match_cos_profil str_match_profile_value]} {
        keylset iscsi_info qosprofile $str_match_cos_profil
        keylset iscsi_info vptordscpvalue $str_match_profile_value
        Netgear_log_file "Get iscsi qos profile" $str_match_cos_profil
        Netgear_log_file "Get iscsi qos profile value" $str_match_profile_value  
    }      
    
    # return iscsi remark status
    if {[regexp -nocase {remark} $strOutput]} {
        set remark_status "enable"
    } else {
        set remark_status "disable"   
    }   
    keylset iscsi_info remarkstatus $remark_status
    Netgear_log_file "Get iscsi remark status" $remark_status
    
    # return iscsi cos status
    if {[regexp -nocase {iSCSI CoS enabled} $strOutput]} {
        set cos_status "enable" 
    } else {
        set cos_status "disable"     
    }
    keylset iscsi_info cosstatus $cos_status
    Netgear_log_file "Get iscsi cos status" $cos_status
    
    # return iscsi aging time
    set str_match_aging_time ""
    if {[regexp -nocase {Session aging time: (\d+) min} $strOutput str_match str_match_aging_time]} {
        keylset iscsi_info agingtime $str_match_aging_time 
        Netgear_log_file "Get iscsi aging time" $str_match_aging_time
    }                       
    
    # return maximum number of sessions
    set str_max_sessions_num ""
    if {[regexp -nocase {Maximum number of sessions is (\d+)} $strOutput str_match str_max_sessions_num]} {
        keylset iscsi_info maxsessionsnum $str_max_sessions_num 
        Netgear_log_file "Get iscsi maximum number of sessions" $str_max_sessions_num
    }
    
    return $iscsi_info
}

###############################################################
#   Function Name   :   ntgrSwitchiSCSIAddTarget
#
#   Author          :   Luiz.Yao
#
#   Date            :   2016/11/22
#
#   Parameter       :   strDevice
#                       target_info -- target ip, tcp port, target name
#
#   Return Value    :   1, successful
#                       0, failed
################################################################
proc ntgrSwitchiSCSIAddTarget {strDev target_info} {
    # initialize result
    set ntgrSwitchiSCSIAddTarget 0
    
    # initialize command
    set command "iscsi target "
    
    # add tcp ports, default tcp port is 3260
    set tcp_ports "3260"
    keylget target_info tcpports tcp_ports
    if {[llength [list $tcp_ports]] > 1} {
        set tcp_ports [join $tcp_ports " "]
    }
    append command "port " $tcp_ports " "
    
    # add target address
    set target_ip ""
    keylget target_info targetip target_ip 
    Netgear_log_file "target_ip" $target_ip
    if {$target_ip != ""} {
        append command "address " $target_ip " "
    }
    
    # add target name 
    set target_name ""
    if {[keylget target_info targetname target_name ]} {
        append command "name " $target_name
    }
    
    # start configure 
    ntgrCLIExecute $strDev "configure"
    ntgrCLIExecute $strDev $command
    
    # check result
    set check_flag 1
    set strOutput [ntgrCLIExecute $strDev "show iscsi"]
    ntgrCLIExecute $strDev "exit"
    
    regsub -all {[\(\) ]+} $strOutput " " strOutputEx
    set strOutputLst [split $strOutputEx " "] 
    foreach tcp_port [split $tcp_ports " "] { 
        set in_flag 0
        set search_idx [lsearch -all -regexp $strOutputLst $tcp_port]
        foreach idx $search_idx {
            set actual_ip [lindex $strOutputLst [expr $idx + 1]]
            set actul_name [lindex $strOutputLst [expr $idx + 2]]
            regsub -all {\n+} $actul_name "" actul_name_ex
            if {$target_ip == ""} {
                set target_ip "-"
            }  
            if {$target_name == ""} {
                set target_name {-}
            }              
            
            if {$target_ip == $actual_ip && [regexp -- $target_name $actul_name_ex]} {
                set in_flag 1
                Netgear_log_file "Add tcp port $tcp_port target ip $target_ip target name $target_name successful;" ""
            }   
        }
        if {$in_flag == 0} {
            set check_flag 0
            break
        }
    }
    set ntgrSwitchiSCSIAddTarget $check_flag
    return $ntgrSwitchiSCSIAddTarget 
}

###############################################################
#   Function Name   :   ntgrSwitchiSCSIDelTarget
#
#   Author          :   Luiz.Yao
#
#   Date            :   2016/11/22
#
#   Parameter       :   strDevice
#                       target_info -- target ip, tcp port, target name
#
#   Return Value    :   1, successful
#                       0, failed
################################################################
proc ntgrSwitchiSCSIDelTarget {strDev target_info} {
    # initialize result
    set ntgrSwitchiSCSIDelTarget 0
    
    # initialize command
    set command "no iscsi target "
    
    # add tcp ports, default tcp port is 3260
    set tcp_ports "3260"
    keylget target_info tcpports tcp_ports
    if {[llength $tcp_ports] > 1} {
        set tcp_ports [join $tcp_ports " "]
    }
    append command "port " $tcp_ports " "
    
    # add target address
    set target_ip ""
    keylget target_info targetip target_ip 
    if {$target_ip != ""} {
        append command "address " $target_ip " "
    }
    
    # start configure 
    ntgrCLIExecute $strDev "configure"
    ntgrCLIExecute $strDev $command
    
    # check result
    set check_flag 1
    set strOutput [ntgrCLIExecute $strDev "show iscsi"]
    ntgrCLIExecute $strDev "exit"
    foreach tcp_port [split $tcp_ports " "] {
        set str_reg_pattern "TCP Port +Target IP Address +Name.+$tcp_port +$target_ip"
        if {[regexp -nocase $str_reg_pattern $strOutput]} {
            set check_flag 0
            Netgear_log_file "delete target ip $target_ip at tcp port $tcp_port failed;" ""
            break                 
        } else {             
            Netgear_log_file "delete target ip $target_ip at tcp port $tcp_port successful;" ""            
        }
    }
    set ntgrSwitchiSCSIDelTarget $check_flag
    return $ntgrSwitchiSCSIDelTarget 
}


###############################################################
#   Function Name   :   ntgrSwitchiSCSIGetSessionsDetailed
#
#   Author          :   Luiz.Yao
#
#   Date            :   2016/11/22
#
#   Parameter       :   strDevice
#
#   Return Value    :   a keylset
################################################################
proc ntgrSwitchiSCSIGetSessionsDetailed {strDev} {
    set strOutput [ntgrCLIExecute $strDev "show iscsi sessions detailed"]
    
    set str_reg_pattern {Target: *([\w\.:-]+)[^-]+Initiator: *([\w\.:-]+)}  
    set all_lst_match [regexp -inline -all $str_reg_pattern $strOutput]  
    set lst_len [llength $all_lst_match]
    if {$lst_len > 0} {
        keylset ret result "OK"
        for {set i 1} {$i < $lst_len} {incr i 2} {
            set target_name [lindex $all_lst_match $i]
            set initiator_name [lindex $all_lst_match [expr $i+1]]
            keylset ret $initiator_name $target_name
            Netgear_log_file "target name : $target_name" "initiator name: $initiator_name"
        }   
    } else {
        keylset ret result "NG"
        Netgear_log_file "no sessions exists;" ""
    } 
    return $ret
}