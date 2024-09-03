####################################################################################
#  File Name   : cal-ntgr-qos.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for Qos configuration.
#
#  History     :
#        Date          Programmer         Description
#        Dec 28,2006   Scott Zhang        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALQosSetQueueMinBandwidth
#
#  Description    : This function is called to configure the min guarantee for 
#                   each queue.
#
#  Usage          : CALQosSetQueueMinBandwidth <switch_name> <port> <bwlist>
#
#*******************************************************************************
proc CALQosSetQueueMinBandwidth {switch_name port bwlist} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosSetQueueMinBandwidth $switch_name $port $bwlist
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosUnsetQueueMinBandwidth
#
#  Description    : This function is called to set the guarantee of queue to default.
#
#  Usage          : CALQosUnsetQueueMinBandwidth <switch_name> <port>
#
#*******************************************************************************
proc CALQosUnsetQueueMinBandwidth {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosUnsetQueueMinBandwidth $switch_name $port
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosEnableQueueStrict
#
#  Description    : This function is called to enable the strict match for Qos.
#
#  Usage          : CALQosEnableQueueStrict <switch_name> <queueIDlist> <portlist {}>
#
#*******************************************************************************
proc CALQosEnableQueueStrict {switch_name queueIDlist {portlist {}} } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosEnableQueueStrict $switch_name $queueIDlist $portlist
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosDisableQueueStrict
#
#  Description    : This function is called to disable the strict match for Qos.
#
#  Usage          : CALQosDisableQueueStrict <switch_name> <queueIDlist> <portlist {}>
#
#*******************************************************************************
proc CALQosDisableQueueStrict {switch_name queueIDlist {portlist {}} } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosDisableQueueStrict $switch_name $queueIDlist $portlist
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosSetDot1PMap
#
#  Description    : This function is called to set dot1P mapping.
#
#  Usage          : CALQosSetDot1PMap <switch_name> <dot1P> <queueID> <portlist {}>
#
#*******************************************************************************
proc CALQosSetDot1PMap {switch_name dot1P queueID {portlist {}} } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosSetDot1PMap $switch_name $dot1P $queueID $portlist
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosUnsetDot1PMap
#
#  Description    : This function is called to set dot1P mapping to default.
#
#  Usage          : CALQosUnsetDot1PMap <switch_name> <portlist {}>
#
#*******************************************************************************
proc CALQosUnsetDot1PMap {switch_name {portlist {}} } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosUnsetDot1PMap $switch_name $portlist
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosSetTrustMode
#
#  Description    : This function is called to set the trust mode.
#
#  Usage          : CALQosSetTrustMode <switch_name> <mode> <portlist {}>
#
#*******************************************************************************
proc CALQosSetTrustMode {switch_name mode {portlist {}} } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosSetTrustMode $switch_name $mode $portlist
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosSetDSCPMap
#
#  Description    : This function is called to set DSCP mapping.
#
#  Usage          : CALQosSetDSCPMap <switch_name> <dscp> <queueID> <portlist {}>
#
#*******************************************************************************
proc CALQosSetDSCPMap {switch_name dscp queueID {portlist {}} } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosSetDSCPMap $switch_name $dscp $queueID $portlist
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosUnsetDSCPMap
#
#  Description    : This function is called to set DSCP mapping to default.
#
#  Usage          : CALQosUnsetDSCPMap <switch_name> <portlist {}>
#
#*******************************************************************************
proc CALQosUnsetDSCPMap {switch_name {portlist {}} } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosUnsetDSCPMap $switch_name $portlist
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosSetPrecedenceMap
#
#  Description    : This function is called to set precedence mapping.
#
#  Usage          : CALQosSetPrecedenceMap <switch_name> <prec> <queueID> <portlist {}>
#
#*******************************************************************************
proc CALQosSetPrecedenceMap {switch_name prec queueID {portlist {}} } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosSetPrecedenceMap $switch_name $prec $queueID $portlist
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosUnsetPrecedenceMap
#
#  Description    : This function is called to set precedence mapping to default.
#
#  Usage          : CALQosUnsetPrecedenceMap <switch_name> <portlist {}>
#
#*******************************************************************************
proc CALQosUnsetPrecedenceMap {switch_name {portlist {}} } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosUnsetPrecedenceMap $switch_name $portlist
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosCreateClassMap
#
#  Description    : This function is called to create class-map.
#
#  Usage          : CALQosCreateClassMap <switch_name> <cls>
#
#*******************************************************************************
proc CALQosCreateClassMap {switch_name cls} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosCreateClassMap $switch_name $cls
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosCreateV6ClassMap
#
#  Description    : This function is called to create ipv6 class-map.
#
#  Usage          : CALQosCreateV6ClassMap <switch_name> <cls>
#
#*******************************************************************************
proc CALQosCreateV6ClassMap {switch_name cls} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosCreateV6ClassMap $switch_name $cls
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : CALQosDeleteClassMap
#
#  Description    : This function is called to del class-map.
#
#  Usage          : CALQosCreateClassMap <switch_name> <cls>
#
#*******************************************************************************
proc CALQosDeleteClassMap {switch_name cls} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosDeleteClassMap $switch_name $cls
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosCreatePolicyMap
#
#  Description    : This function is called to create policy-map.
#
#  Usage          : CALQosCreatePolicyMap <switch_name> <plc>
#
#*******************************************************************************
proc CALQosCreatePolicyMap {switch_name plc args} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]
    set arg_count [llength $args]

	if {$arg_count > 0} {
	    set direct [lindex $args 0]
	} else {
	    set direct "in"
	}
	
    switch $switch_vendor {
        netgear {
            _ntgrQosCreatePolicyMap $switch_name $plc $direct
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosDeletePolicyMap
#
#  Description    : This function is called to delete policy-map.
#
#  Usage          : CALQosCreatePolicyMap <switch_name> <plc>
#
#*******************************************************************************
proc CALQosDeletePolicyMap {switch_name plc} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosDeletePolicyMap $switch_name $plc
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosEnableServicePolicy
#
#  Description    : This function is called to apply policy to ports.
#
#  Usage          : CALQosEnableServicePolicy <switch_name> <plc> <portlist {}>
#
#*******************************************************************************
proc CALQosEnableServicePolicy {switch_name plc {portlist {}} {direct in}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosEnableServicePolicy $switch_name $plc $portlist $direct
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosDisableServicePolicy
#
#  Description    : This function is called to no apply policy to ports.
#
#  Usage          : CALQosDisableServicePolicy <switch_name> <plc> <portlist {}>
#
#*******************************************************************************
proc CALQosDisableServicePolicy {switch_name plc {portlist {}} {direct in}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosDisableServicePolicy $switch_name $plc $portlist $direct
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosRenameClassMap
#
#  Description    : This function is called to rename a class-map.
#
#  Usage          : CALQosRenameClassMap <switch_name> <old> <new>
#
#*******************************************************************************
proc CALQosRenameClassMap {switch_name old new} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosRenameClassMap $switch_name $old $new
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosRenamePolicyMap
#
#  Description    : This function is called to rename a policy-map.
#
#  Usage          : CALQosRenamePolicyMap <switch_name> <old> <new>
#
#*******************************************************************************
proc CALQosRenamePolicyMap {switch_name old new} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosRenamePolicyMap $switch_name $old $new
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosEnableDiffService
#
#  Description    : This function is called to enable diff service globally.
#
#  Usage          : CALQosEnableDiffService <switch_name>
#
#*******************************************************************************
proc CALQosEnableDiffService {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosEnableDiffService $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosDisableDiffService
#
#  Description    : This function is called to disable diff service globally.
#
#  Usage          : CALQosDisableDiffService <switch_name>
#
#*******************************************************************************
proc CALQosDisableDiffService {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosDisableDiffService $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosEnableTrafficShape
#
#  Description    : This function is called to configure traffic shape on ports.
#
#  Usage          : CALQosEnableTrafficShape <switch_name bw {portlist {}}>
#
#*******************************************************************************
proc CALQosEnableTrafficShape {switch_name bw {portlist {}}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosEnableTrafficShape $switch_name $bw $portlist
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosDisableTrafficShape
#
#  Description    : This function is canceled to configure traffic shape on ports.
#
#  Usage          : CALQosDisableTrafficShape <switch_name {portlist {}}>
#
#*******************************************************************************
proc CALQosDisableTrafficShape {switch_name {portlist {}}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosDisableTrafficShape $switch_name $portlist
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALQosGetDiffservStatus
#
#  Description    : This function is canceled to configure traffic shape on ports.
#
#  Usage          : CALQosGetDiffservStatus <switch_name expect_text>
#
#*******************************************************************************
proc CALQosGetDiffservStatus {switch_name expect_text} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrQosGetDiffservStatus $switch_name $expect_text
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-qos.tcl" "Switch not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : CALQosGetServiceStatisticsOfferedPackets
#
#  Description    : This function is get Offered Packets from Service Statistics
#
#  Usage          : CALQosGetServiceStatisticsOfferedPackets <switch_name expect_text>
#
#*******************************************************************************
proc CALQosGetServiceStatisticsOfferedPackets	 {switch_name port member_class} {
    set output {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show policy-map interface $port in | section $member_class include \"In Offered Packets\"\r"
    exp_sleep 1
    expect -i $cnn_id -re "show policy-map interface"
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
	set text [lindex $output [expr [lsearch $output "In Offered Packets"] + 4]]
    return $text	
}
#*******************************************************************************
#
#  Function Name  : CALQosGetServiceStatisticsDiscardedPackets
#
#  Description    : This function is get Discarded Packets from Service Statistics
#
#  Usage          : CALQosGetServiceStatisticsDiscardedPackets <switch_name expect_text>
#
#*******************************************************************************
proc CALQosGetServiceStatisticsDiscardedPackets	 {switch_name port member_class} {
    set output {}
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show policy-map interface $port in | section $member_class include \"In Discarded Packets\"\r"
    exp_sleep 1
    expect -i $cnn_id -re "show policy-map interface"
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
	set text [lindex $output [expr [lsearch $output "In Discarded Packets"] + 4]]
    return $text	
}
