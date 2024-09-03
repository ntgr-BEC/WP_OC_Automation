#
# *API: Private
#
#   Global Variable
#
#   global IPCServer_ListenSocket     # int
#   global IPCServer_SIG_FOREVER      # int
#   global TG_param                   # keylset
#
# *API: Public
#   IPCServer_SetResult
#   IPCServer_SendParam
#   IPCServer_RecvParam
#   IPCServer_WakeUpGUI
#   IPCServer_WaitForGUI
#

proc IPCServer_StartListen {} {
global IPCServer_ListenSocket
global IPCServer_SIG_FOREVER

    set IPCServer_ListenSocket [socket -server IPCServer_AcceptConnection -myaddr localhost 7788]
    Netgear_log_file "lib-ipc-sockettcp.tcl" "IPCServer: listening ..."

    set IPCServer_SIG_FOREVER 1
    vwait IPCServer_SIG_FOREVER
}

proc IPCServer_StopListen {} {
global IPCServer_ListenSocket

	close $IPCServer_ListenSocket
}

proc IPCServer_AcceptConnection {sock addr port} {
    fconfigure $sock -buffering line
    fileevent $sock readable [list IPCServer_Interpreter $sock]
    IPCServer_ShowPrompt $sock
    IPCServer_StopListen
    Netgear_log_file "lib-ipc-sockettcp.tcl" "IPCServer: Established"
}

proc IPCServer_ShowPrompt {sock} {
    puts -nonewline $sock "READY>"
    flush $sock
}

proc IPCServer_Interpreter {sock} {
global IPCServer_SIG_FOREVER

    if {[eof $sock] || [catch {gets $sock line}]} {
        close $sock
        set IPCServer_SIG_FOREVER 0
    }

    if { [ catch {
      	switch $line {
            quit {
        	    close $sock
                set IPCServer_SIG_FOREVER 0
      		}
      		noop {
        	    puts $sock "noop"
                IPCServer_ShowPrompt $sock
      		}
      		default {
        	    puts $sock [ eval $line ]
                IPCServer_ShowPrompt $sock
      		}
      	}
    } errMsg ] } {
        Netgear_log_file "lib-ipc-sockettcp.tcl" $errMsg
        set IPCServer_SIG_FOREVER 0
    }
}

#
# TG
#
proc TG_param_set {param_name param_value} {
global TG_param

    keylset TG_param [string tolower $param_name] $param_value
    Netgear_log_file "lib-ipc-sockettcp.tcl" "IPCServer: TG_param_set: ($param_name , $param_value)"
}

proc TG_param_get {param_name} {
global TG_param

    set param_value [ keylget TG_param [string tolower $param_name] ]
    Netgear_log_file "lib-ipc-sockettcp.tcl" "IPCServer: TG_param_get: ($param_name , $param_value)"
    return $param_value
}

proc TG_getresult {} {

    set bFlag [ TG_param_get bFlag ]

    if {$bFlag } {
        set retval "+OK"
    } else {
        set retval "-ERR"
    }

    return $retval
}

proc TG_GetResult {} {

    set bFlag [ TG_param_get bFlag ]

    if {$bFlag } {
        set retval "+OK"
    } else {
        set retval "-ERR"
    }

    return $retval
}

proc IPCServer_SetResult {} {
global bFlag

    Netgear_log_file "lib-ipc-sockettcp.tcl" ""
    Netgear_log_file "lib-ipc-sockettcp.tcl" "IPCServer: IPCServer_SetResult ..."
    TG_param_set bFlag $bFlag
    IPCServer_StartListen
}

#
#   Arguments:
#               args    {name1 value1} {name2 value2} ...
#
proc IPCServer_SendParam args {
    Netgear_log_file "lib-ipc-sockettcp.tcl" ""
    Netgear_log_file "lib-ipc-sockettcp.tcl" "IPCServer: IPCServer_SendParam ..."
    foreach arg $args {
        TG_param_set [lindex $arg 0] [lindex $arg 1]
    }
    IPCServer_StartListen
}

#
#   Arguments:
#               args    name1 name2 ...
#
proc IPCServer_RecvParam args {
    Netgear_log_file "lib-ipc-sockettcp.tcl" ""
    Netgear_log_file "lib-ipc-sockettcp.tcl" "IPCServer: IPCServer_RecvParam ..."
    IPCServer_StartListen
    foreach arg $args {
        upvar $arg param
        set param [TG_param_get $arg]
    }
}

proc IPCServer_WakeUpGUI {} {
    Netgear_log_file "lib-ipc-sockettcp.tcl" ""
    Netgear_log_file "lib-ipc-sockettcp.tcl" "IPCServer: IPCServer_WakeUpGUI ..."
    IPCServer_StartListen
}

proc IPCServer_WaitForGUI {} {
    Netgear_log_file "lib-ipc-sockettcp.tcl" ""
    Netgear_log_file "lib-ipc-sockettcp.tcl" "IPCServer: IPCServer_WaitForGUI ..."
    IPCServer_StartListen
}

proc VBGetValueFormTcl {} {

    set bFlag [ TG_param_get bFlag ]

    return $bFlag
}

