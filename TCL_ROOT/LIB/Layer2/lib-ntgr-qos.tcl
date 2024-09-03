####################################################################################
#  File Name   : lib-ntgr-qos.tcl                                                  #
#                                                                                  #
#  Description :                                                                   #
#        This file includes functions used for Qos configuration.                  #
#                                                                                  #
#  History     :                                                                   #
#        Date          Programmer         Description                              #
#        28/12/2006    Scott Zhang        Created                                  #
#                                                                                  #
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrQosSetQueueMinBandwidth
#
#  Description    : This function is called to configure the min guarantee for 
#                   each queue.
#
#  Usage          : _ntgrQosSetQueueMinBandwidth <switch_name> <port> <bwlist>
#
#*******************************************************************************
proc _ntgrQosSetQueueMinBandwidth {switch_name port bwlist} {    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
	if {$port == "all" } {
	
    } else {
		exp_send -i $cnn_id "interface $port\r"
		exp_sleep 1
    }
    exp_send -i $cnn_id "cos-queue min-bandwidth $bwlist\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosUnsetQueueMinBandwidth
#
#  Description    : This function is called to set the guarantee of queue to default.
#
#  Usage          : _ntgrQosUnsetQueueMinBandwidth <switch_name> <port>
#
#*******************************************************************************
proc _ntgrQosUnsetQueueMinBandwidth {switch_name port} {    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no cos-queue min-bandwidth\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosEnableQueueStrict
#
#  Description    : This function is called to enable the strict match for Qos
#
#  Usage          : _ntgrQosEnableQueueStrict <switch_name> <queueIDlist> <portlist>
#
#*******************************************************************************
proc _ntgrQosEnableQueueStrict {switch_name queueIDlist portlist} {    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$portlist == {}} {
            exp_send -i $cnn_id "cos-queue strict $queueIDlist\r"
            exp_sleep 1
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
            exp_send -i $cnn_id "cos-queue strict $queueIDlist\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        }
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosDisableQueueStrict
#
#  Description    : This function is called to disable the strict match for Qos
#
#  Usage          : _ntgrQosDisableQueueStrict <switch_name> <queueIDlist> <portlist>
#
#*******************************************************************************
proc _ntgrQosDisableQueueStrict {switch_name queueIDlist portlist} {    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$portlist == {}} {
            exp_send -i $cnn_id "no cos-queue strict $queueIDlist\r"
            exp_sleep 1
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
            exp_send -i $cnn_id "no cos-queue strict $queueIDlist\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        }
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosSetDot1PMap
#
#  Description    : This function is called to set the dot1P mapping
#
#  Usage          : _ntgrQosSetDot1PMap <switch_name> <dot1P> <queueID> <portlist>
#
#*******************************************************************************
proc _ntgrQosSetDot1PMap {switch_name dot1P queueID portlist} {    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$portlist == {}} {
            exp_send -i $cnn_id "classofservice dot1p-mapping $dot1P $queueID\r"
            exp_sleep 1
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
            exp_send -i $cnn_id "classofservice dot1p-mapping $dot1P $queueID\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        }
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosUnsetDot1PMap
#
#  Description    : This function is called to set the dot1P mapping to default
#
#  Usage          : _ntgrQosUnsetDot1PMap <switch_name> <portlist>
#
#*******************************************************************************
proc _ntgrQosUnsetDot1PMap {switch_name portlist} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$portlist == {}} {
            exp_send -i $cnn_id "no classofservice dot1p-mapping\r"
            exp_sleep 1
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
            exp_send -i $cnn_id "no classofservice dot1p-mapping\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        }
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosSetTrustMode
#
#  Description    : This function is called to set the trust mode
#
#  Usage          : _ntgrQosSetTrustMode <switch_name> <mode> <portlist>
#
#*******************************************************************************
proc _ntgrQosSetTrustMode {switch_name mode portlist} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$portlist == {}} {
            exp_send -i $cnn_id "classofservice trust $mode\r"
            exp_sleep 1
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
            exp_send -i $cnn_id "classofservice trust $mode\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        }
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosSetDSCPMap
#
#  Description    : This function is called to set the DSCP mapping
#
#  Usage          : _ntgrQosSetDSCPMap <switch_name> <dscp> <queueID> <portlist>
#
#*******************************************************************************
proc _ntgrQosSetDSCPMap {switch_name dscp queueID portlist} {    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$portlist == {}} {
            exp_send -i $cnn_id "classofservice ip-dscp-mapping $dscp $queueID\r"
            exp_sleep 1
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
            exp_send -i $cnn_id "classofservice ip-dscp-mapping $dscp $queueID\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        }
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosUnsetDSCPMap
#
#  Description    : This function is called to set the DSCP mapping to default
#
#  Usage          : _ntgrQosUnsetDSCPMap <switch_name> <portlist>
#
#*******************************************************************************
proc _ntgrQosUnsetDSCPMap {switch_name portlist} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$portlist == {}} {
            exp_send -i $cnn_id "no classofservice ip-dscp-mapping\r"
            exp_sleep 1
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
            exp_send -i $cnn_id "no classofservice ip-dscp-mapping\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        }
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosSetPrecedenceMap
#
#  Description    : This function is called to set the precedence mapping
#
#  Usage          : _ntgrQosSetPrecedenceMap <switch_name> <precedence> <queueID> <portlist>
#
#*******************************************************************************
proc _ntgrQosSetPrecedenceMap {switch_name precedence queueID portlist} {    
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$portlist == {}} {
            exp_send -i $cnn_id "classofservice ip-precedence-mapping $precedence $queueID\r"
            exp_sleep 1
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
            exp_send -i $cnn_id "classofservice ip-precedence-mapping $precedence $queueID\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        }
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosUnsetPrecedenceMap
#
#  Description    : This function is called to set the precedence mapping to default
#
#  Usage          : _ntgrQosUnsetPrecedenceMap <switch_name> <portlist>
#
#*******************************************************************************
proc _ntgrQosUnsetPrecedenceMap {switch_name portlist} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$portlist == {}} {
            exp_send -i $cnn_id "no classofservice ip-precedence-mapping\r"
            exp_sleep 1
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
            exp_send -i $cnn_id "no classofservice ip-precedence-mapping\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        }
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosCreateClassMap
#
#  Description    : This function is called to create class-map.
#
#  Usage          : _ntgrQosCreateClassMap <switch_name> <cls>
#
#*******************************************************************************
proc _ntgrQosCreateClassMap {switch_name cls} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    set any             [_ntgrGetClassMapMatchAny $switch_name $cls]
    set cls_map         [_ntgrGetClassMapMatchClassMap $switch_name $cls]
    set src_mac         [_ntgrGetClassMapMatchSrcMac $switch_name $cls]
    set dst_mac         [_ntgrGetClassMapMatchDstMac $switch_name $cls]
    set cos             [_ntgrGetClassMapMatchCos $switch_name $cls]
    set vlan            [_ntgrGetClassMapMatchVlan $switch_name $cls]
    set ethernet_type   [_ntgrGetClassMapMatchEthernetType $switch_name $cls]
    set src_ip          [_ntgrGetClassMapMatchSrcIP $switch_name $cls]
    set dst_ip          [_ntgrGetClassMapMatchDstIP $switch_name $cls]
    set tos             [_ntgrGetClassMapMatchIpTOS $switch_name $cls]
    set prece           [_ntgrGetClassMapMatchIpPrecedence $switch_name $cls]
    set dscp            [_ntgrGetClassMapMatchIpDSCP $switch_name $cls]
    set protocol        [_ntgrGetClassMapMatchIpProtocol $switch_name $cls]
    set l4_src_port     [_ntgrGetClassMapMatchL4SrcPort $switch_name $cls]
    set l4_dst_port     [_ntgrGetClassMapMatchL4DstPort $switch_name $cls]
    set sec_cos         [_ntgrGetClassMapMatchSecondCos $switch_name $cls]
    set sec_vlan        [_ntgrGetClassMapMatchSecondVlan $switch_name $cls]
    
     set intRelease {}; set intVersion {}; set intMaintenance {}; set intPatch {} ; set strModel {}
    _ntgrGetVersionDetailedbyConnectionID $cnn_id intRelease intVersion intMaintenance intPatch strModel
    
   if { [string first "JGSM" $strModel] != -1 } {
        set tmp_list ""
        foreach tosvalue $tos {
            set tmp_qos 0x$tosvalue
            set blank  " " 
            set tmp_list $tmp_list$tmp_qos
            set tmp_list $tmp_list$blank
        }
        set tos $tmp_list
       
    }
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "class-map match-all $cls\r"
    exp_sleep 1

    if {$any != {}} {
        exp_send -i $cnn_id "match $any\r"
        exp_sleep 1
    }

    if {$cls_map != {}} {
        exp_send -i $cnn_id "match class-map $cls_map\r"
        exp_sleep 1
    }

    if {$src_mac != {}} {
        exp_send -i $cnn_id "match source-address mac $src_mac\r"
        exp_sleep 1
    }

    if {$dst_mac != {}} {
        exp_send -i $cnn_id "match destination-address mac $dst_mac\r"
        exp_sleep 1
    }

    if {$cos != {}} {
        exp_send -i $cnn_id "match cos $cos\r"
        exp_sleep 1
    }

    if {$vlan != {}} {
        exp_send -i $cnn_id "match vlan $vlan\r"
        exp_sleep 1
    }

    if {$ethernet_type != {}} {
        exp_send -i $cnn_id "match ethertype $ethernet_type\r"
        exp_sleep 1
    }

    if {$src_ip != {}} {
        exp_send -i $cnn_id "match srcip $src_ip\r"
        exp_sleep 1
    }

    if {$dst_ip != {}} {
        exp_send -i $cnn_id "match dstip $dst_ip\r"
        exp_sleep 1
    }

    if {$tos != {}} {
        exp_send -i $cnn_id "match ip tos $tos\r"
        exp_sleep 1
    }

    if {$prece != {}} {
        exp_send -i $cnn_id "match ip precedence $prece\r"
        exp_sleep 1
    }

    if {$dscp != {}} {
        exp_send -i $cnn_id "match ip dscp $dscp\r"
        exp_sleep 1
    }

    if {$protocol != {}} {
        exp_send -i $cnn_id "match protocol $protocol\r"
        exp_sleep 1
    }

    if {$l4_src_port != {}} {
        exp_send -i $cnn_id "match srcl4port $l4_src_port\r"
        exp_sleep 1
    }

    if {$l4_dst_port != {}} {
        exp_send -i $cnn_id "match dstl4port $l4_dst_port\r"
        exp_sleep 1
    }

    if {$sec_cos != {}} {
        exp_send -i $cnn_id "match secondary-cos $sec_cos\r"
        exp_sleep 1
    }
    if {$sec_vlan != {}} {
        exp_send -i $cnn_id "match secondary-vlan $sec_vlan\r"
        exp_sleep 1
    }
        
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

proc _ntgrQosCreateV6ClassMap {switch_name cls} {
    
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    set any             [_ntgrGetV6ClassMapMatchAny $switch_name $cls]
    set cls_map         [_ntgrGetV6ClassMapMatchClassMap $switch_name $cls]
    set src_ip          [_ntgrGetV6ClassMapMatchSrcIPv6 $switch_name $cls]
    set dst_ip          [_ntgrGetV6ClassMapMatchDstIPv6 $switch_name $cls]
    set l4_src_port     [_ntgrGetV6ClassMapMatchL4SrcPort $switch_name $cls]
    set l4_dst_port     [_ntgrGetV6ClassMapMatchL4DstPort $switch_name $cls]
    set traffic_class   [_ntgrGetV6ClassMapMatchTrafficClass $switch_name $cls]
    set flow_label      [_ntgrGetV6ClassMapMatchFlowLabel $switch_name $cls]
    set protocol        [_ntgrGetV6ClassMapMatchProtocol $switch_name $cls]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "class-map match-all $cls ipv6\r"
    exp_sleep 1

    if {$any != {}} {
        exp_send -i $cnn_id "match $any\r"
        exp_sleep 1
    }

    if {$cls_map != {}} {
        exp_send -i $cnn_id "match class-map $cls_map\r"
        exp_sleep 1
    }

    if {$src_ip != {}} {
        exp_send -i $cnn_id "match srcip6 $src_ip\r"
        exp_sleep 1
    }
    if {$dst_ip != {}} {
        exp_send -i $cnn_id "match dstip6 $dst_ip\r"
        exp_sleep 1
    }

    if {$l4_src_port != {}} {
        exp_send -i $cnn_id "match srcl4port $l4_src_port\r"
        exp_sleep 1
    }

    if {$l4_dst_port != {}} {
        exp_send -i $cnn_id "match dstl4port $l4_dst_port\r"
        exp_sleep 1
    }

    if {$traffic_class != {}} {
        exp_send -i $cnn_id "match ip dscp $traffic_class\r"
        exp_sleep 1
    }
    if {$flow_label != {}} {
        exp_send -i $cnn_id "match ip6flowlbl $flow_label\r"
        exp_sleep 1
    }    

    if {$protocol != {}} {
        exp_send -i $cnn_id "match protocol $protocol\r"
        exp_sleep 1
    }
    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }    
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosDeleteClassMap
#
#  Description    : This function is called to delete class-map.
#
#  Usage          : _ntgrQosDeleteClassMap <switch_name> <cls>
#
#*******************************************************************************
proc _ntgrQosDeleteClassMap {switch_name cls} {

    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no class-map $cls\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  
  
}
  
#*******************************************************************************
#
#  Function Name  : _ntgrQosCreatePolicyMap
#
#  Description    : This function is called to create policy-map.
#
#  Usage          : _ntgrQosCreatePolicyMap <switch_name> <plc>
#
#*******************************************************************************
proc _ntgrQosCreatePolicyMap {switch_name plc direct} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set ret ""
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    expect -i $cnn_id "#" {}
    exp_send -i $cnn_id "policy-map $plc $direct\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer) 
    
    set cls_list [_ntgrGetPolicyMapClassList $switch_name $plc]
    foreach cls_attr $cls_list {
        set cls  [lindex $cls_attr 0]
        set attr [lindex $cls_attr 1]
        set drop            [_ntgrGetPolicyMapAttrDrop $switch_name $attr]
        set queue           [_ntgrGetPolicyMapAttrQueue $switch_name $attr]
        set cos             [_ntgrGetPolicyMapAttrCos $switch_name $attr]
        set sec_cos         [_ntgrGetPolicyMapAttrSecCos $switch_name $attr]
        set dscp            [_ntgrGetPolicyMapAttrDscp $switch_name $attr]
        set prec            [_ntgrGetPolicyMapAttrPrec $switch_name $attr]
        set mode            [_ntgrGetPolicyMapAttrClrMode $switch_name $attr]
        set police          [_ntgrGetPolicyMapAttrPolice $switch_name $attr]
        set police_two_rate [_ntgrGetPolicyMapAttrPoliceTwoRate $switch_name $attr]
        set police_single_rate [_ntgrGetPolicyMapAttrPoliceSingleRate $switch_name $attr]
        set mirror          [_ntgrGetPolicyMapAttrMirror $switch_name $attr]
        set redirect        [_ntgrGetPolicyMapAttrRedirect $switch_name $attr]
        
        exp_send -i $cnn_id "class $cls\r"
        exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer) 

        if {$drop != {}} {
            exp_send -i $cnn_id "$drop\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer)
        }

        if {$queue != {}} {
            exp_send -i $cnn_id "assign-queue $queue\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer)
        }

        if {$cos != {}} {
            exp_send -i $cnn_id "mark cos $cos\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer)
        }
        if {$sec_cos != {}} {
            exp_send -i $cnn_id "mark cos-as-sec-cos\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer)
        }        
        
        if {$dscp != {}} {
            exp_send -i $cnn_id "mark ip-dscp $dscp\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer)
        }

        if {$prec != {}} {
            exp_send -i $cnn_id "mark ip-precedence $prec\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer)
        }

        if {$police != {}} {
            exp_send -i $cnn_id "police-simple $police\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer)
        }
        
        if {$mode != {}} {
            exp_send -i $cnn_id "conform-color $mode\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer)
        }

        if {$police_single_rate != {}} {
            exp_send -i $cnn_id "police-single-rate $police_single_rate\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer)
        }
        
        if {$police_two_rate != {}} {
            exp_send -i $cnn_id "police-two-rate $police_two_rate\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer)
        }
        
        if {$mirror != {}} {
            exp_send -i $cnn_id "mirror $mirror\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer)
        }
        if {$redirect != {}} {
            exp_send -i $cnn_id "redirect $redirect\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer)
        }
                
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)
    }

    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosDeletePolicyMap
#
#  Description    : This function is called to del policy.
#
#  Usage          : _ntgrQosDeletePolicyMap <switch_name> <plc> <portlist>
#
#*******************************************************************************
proc _ntgrQosDeletePolicyMap {switch_name plc} {
    
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no policy-map $plc\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosEnableServicePolicy
#
#  Description    : This function is called to apply policy to ports.
#
#  Usage          : _ntgrQosEnableServicePolicy <switch_name> <plc> <portlist>
#
#*******************************************************************************
proc _ntgrQosEnableServicePolicy {switch_name plc portlist {direct in}} {
    
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
    if {$portlist == {}} {
        exp_send -i $cnn_id "service-policy $direct $plc\r"
        exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
          	expect -i $cnn_id -re "#"
            append ret $expect_out(buffer)            
            exp_send -i $cnn_id "service-policy $direct $plc\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer) 
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
            expect -i $cnn_id -re "#" 
            append ret $expect_out(buffer) 
        }
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    return $ret
}
#*******************************************************************************
#
#  Function Name  : _ntgrQosDisableServicePolicy
#
#  Description    : This function is called to no apply policy to ports.
#
#  Usage          : _ntgrQosDisableServicePolicy <switch_name> <plc> <portlist>
#
#*******************************************************************************
proc _ntgrQosDisableServicePolicy {switch_name plc portlist {direct in}} {
    
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1

    if {$portlist == {}} {
        exp_send -i $cnn_id "no service-policy $direct $plc\r"
        exp_sleep 1
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
            exp_send -i $cnn_id "no service-policy $direct $plc\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        }
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosRenameClassMap
#
#  Description    : This function is called to rename a class-map.
#
#  Usage          : _ntgrQosRenameClassMap <switch_name> <old> <new>
#
#*******************************************************************************
proc _ntgrQosRenameClassMap {switch_name old new} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "class-map rename $old $new\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosRenamePolicyMap
#
#  Description    : This function is called to rename a policy-map.
#
#  Usage          : _ntgrQosRenamePolicyMap <switch_name> <old> <new>
#
#*******************************************************************************
proc _ntgrQosRenamePolicyMap {switch_name old new} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "policy-map rename $old $new\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosEnableDiffService
#
#  Description    : This function is called to enable diff service globally.
#
#  Usage          : _ntgrQosEnableDiffService <switch_name>
#
#*******************************************************************************
proc _ntgrQosEnableDiffService {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "diffserv\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosDisableDiffService
#
#  Description    : This function is called to disable diff service globally.
#
#  Usage          : _ntgrQosDisableDiffService <switch_name>
#
#*******************************************************************************
proc _ntgrQosDisableDiffService {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no diffserv\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosEnableTrafficShape
#
#  Description    : This function is called to configure traffic shape on ports.
#
#  Usage          : _ntgrQosEnableTrafficShape <switch_name> <bw> <portlist>
#
#*******************************************************************************
proc _ntgrQosEnableTrafficShape {switch_name bw portlist} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1

    if {$portlist == {}} {
        exp_send -i $cnn_id "traffic-shape $bw\r"
        exp_sleep 1
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
            exp_send -i $cnn_id "traffic-shape $bw\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        }
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosDisableTrafficShape
#
#  Description    : This function is canceled to configure traffic shape on ports.
#
#  Usage          : _ntgrQosDisableTrafficShape <switch_name> <portlist>
#
#*******************************************************************************
proc _ntgrQosDisableTrafficShape {switch_name portlist} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1

    if {$portlist == {}} {
        exp_send -i $cnn_id "no traffic-shape\r"
        exp_sleep 1
    } else {
        foreach port $portlist {
            exp_send -i $cnn_id "interface $port\r"
            exp_sleep 1
            exp_send -i $cnn_id "no traffic-shape\r"
            exp_sleep 1
            exp_send -i $cnn_id "exit\r"
            exp_sleep 1
        }
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosGetDiffservStatus
#
#  Description    : This function is canceled to configure traffic shape on ports.
#
#  Usage          : _ntgrQosGetDiffservStatus <switch_name> <expect_text>
#
#*******************************************************************************
proc _ntgrQosGetDiffservStatus {switch_name expect_text} {

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    set str1 {}
    set str2 {}
	set str3 {}
	
	set cur_tablesize {}
	set max_tablesize {}
	set cur_rulesize {}
	set max_rulesize {}
	set cur_policysize {}
	set max_policysize {}
    set cur_instancesize {}
    set max_instancesize {}
    set cur_attsize {}
    set max_attsize {}
    set cur_sersize {}
    set max_sersize {}
	
	set output ""
	exp_send -i $cnn_id "show diffserv\r"
    exp_sleep 1
    expect -i $cnn_id -re "show diffserv"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }
    }
    Netgear_disconnect_switch $switch_name

	set regSWMAC "(Class Table Size Current/Max\[.\]+)(\[ ]+)(\[0-9\]+)(\[ ]+/\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 cur_tablesize str3 max_tablesize
	
	set regSWMAC "(Class Rule Table Size Current/Max\[.\]+)(\[ ]+)(\[0-9\]+)(\[ ]+/\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 cur_rulesize str3 max_rulesize
	
	set regSWMAC "(Policy Table Size Current/Max\[.\]+)(\[ ]+)(\[0-9\]+)(\[ ]+/\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 cur_policysize str3 max_policysize
	
	set regSWMAC "(Policy Instance Table Size Current/Max\[.\]+)(\[ ]+)(\[0-9\]+)(\[ ]+/\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 cur_instancesize str3 max_instancesize
	
	set regSWMAC "(Policy Attribute Table Size Current/Max\[.\]+)(\[ ]+)(\[0-9\]+)(\[ ]+/\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 cur_attsize str3 max_attsize
	
	set regSWMAC "(Service Table Size Current/Max\[.\]+)(\[ ]+)(\[0-9\]+)(\[ ]+/\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 cur_sersize str3 max_sersize

    set result 0
	if {$expect_text == "Current Class Table Size"} {
	    set result $cur_tablesize
	} elseif {$expect_text == "Max Class Table Size"} {
	    set result $max_tablesize
	} elseif {$expect_text == "Current Class Rule Table Size"} {
	    set result $cur_rulesize
	} elseif {$expect_text == "Max Class Rule Table Size"} {
	    set result $max_rulesize
	} elseif {$expect_text == "Current Policy Table Size"} {
	    set result $cur_policysize
	} elseif {$expect_text == "Max Policy Table Size"} {
	    set result $max_policysize
	} elseif {$expect_text == "Current Policy Instance Table Size"} {
	    set result $cur_instancesize
	} elseif {$expect_text == "Max Policy Instance Table Size"} {
	    set result $max_instancesize
	} elseif {$expect_text == "Current Policy Attribute Table Size"} {
	    set result $cur_attsize
	} elseif {$expect_text == "Max Policy Attribute Table Size"} {
	    set result $max_attsize
	} elseif {$expect_text == "Current Service Table Size"} {
	    set result $cur_sersize
	} elseif {$expect_text == "Max Service Table Size"} {
	    set result $max_sersize
	} else {
	    Netgear_log_file "lib-ntgr-qos.tcl" "Error: Invalid Diffserv Configure Type."
	}
	
	return $result
 
}