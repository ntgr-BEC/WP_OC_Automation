#!/bin/sh
################################################################################   
#
#  File Name		  : lib-traceroute.tcl
#
#  Description      :
#         This TCL contains functions to traceroute
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        21-Apr-11     Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : _ntgrTraceRouteCheck
#
#  Description    : This function is used to check traceroute function.
#
#  Usage          : _ntgrTraceRouteCheck <switch_name>
#
#*******************************************************************************
proc _ntgrTraceRouteCheck {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set iptype  [_getTraceRouteIPType $switch_name]    
    set ipaddr  [_getTraceRouteIPAddr $switch_name]
    set count   [_getTraceRouteCount $switch_name]
    set inteval [_getTraceRouteInterval $switch_name]
    set initttl [_getTraceRouteInitTtl $switch_name]
    set maxttl  [_getTraceRouteMaxTtl $switch_name]
    set maxfail [_getTraceRouteMaxFail $switch_name]
    set port    [_getTraceRoutePort $switch_name]
    set size    [_getTraceRouteSize $switch_name]
    set checkIP [_getTraceRouteCheckIP $switch_name]
    set source [_getTraceRouteSource $switch_name]
	
	if {$iptype == "ipv6"} {
	    set cmdstr "traceroute ipv6 $ipaddr"
	} else {
	    set cmdstr "traceroute $ipaddr"
	}
	if {$source != ""} {
	    append cmdstr " source $source"
	}
    if {$count != ""} {
        append cmdstr " count $count"
    }
    if {$inteval != ""} {
        append cmdstr " interval $inteval"
    }
    if {$initttl != ""} {
        append cmdstr " initTtl $initttl"
    }
    if {$maxttl != ""} {
        append cmdstr " maxTtl $maxttl"
    }
    if {$maxfail != ""} {
        append cmdstr " maxFail $maxfail"
    }
    if {$port != ""} {
        append cmdstr " port $port"
    }
    if {$size != ""} {
        append cmdstr " size $size"
    }
    
        
    set output ""
    
    set timeout 120    
    exp_send -i $cnn_id "$cmdstr\r"
    exp_sleep 1
	if {$iptype == "ipv6"} {
	    expect -i $cnn_id -re "traceroute ipv6 $ipaddr"
	} else {
	    expect -i $cnn_id -re "traceroute $ipaddr"
	}
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    
    if {$checkIP != ""} {
        set expectStr $checkIP
    } else {
        set expectStr $ipaddr
    }
    
    if {[regexp -nocase -line "\\d+\\s+$expectStr" $output]} {
        set result 1
    } else {
        set result 0
    }

    Netgear_log_file "lib-traceroute.tcl" "traceroute on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrTraceRouteConfigErrorCheck
#
#  Description    : This function is used to check traceroute config error.
#
#  Usage          : _ntgrTraceRouteConfigErrorCheck <switch_name>
#
#*******************************************************************************
proc _ntgrTraceRouteConfigErrorCheck {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    set ipaddr  [_getTraceRouteIPAddr $switch_name]
    set count   [_getTraceRouteCount $switch_name]
    set inteval [_getTraceRouteInterval $switch_name]
    set initttl [_getTraceRouteInitTtl $switch_name]
    set maxttl  [_getTraceRouteMaxTtl $switch_name]
    set maxfail [_getTraceRouteMaxFail $switch_name]
    set port    [_getTraceRoutePort $switch_name]
    set size    [_getTraceRouteSize $switch_name]
    
    set cmdstr "traceroute $ipaddr"
    if {$count != ""} {
        append cmdstr " count $count"
    }
    if {$inteval != ""} {
        append cmdstr " interval $inteval"
    }
    if {$initttl != ""} {
        append cmdstr " initTtl $initttl"
    }
    if {$maxttl != ""} {
        append cmdstr " maxTtl $maxttl"
    }
    if {$maxfail != ""} {
        append cmdstr " maxFail $maxfail"
    }
    if {$port != ""} {
        append cmdstr " port $port"
    }
    if {$size != ""} {
        append cmdstr " size $size"
    }
    
        
    set output ""
          
    exp_send -i $cnn_id "$cmdstr\r"
    exp_sleep 1
    expect -i $cnn_id -re "traceroute"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    
    if {[regexp -nocase {Value is out of range|Invalid input|Incorrect input|Inconsistent values|failed} $output]} {
        set result 1
    } else {
        set result 0
    }
    
    Netgear_log_file "lib-traceroute.tcl" "traceroute on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}