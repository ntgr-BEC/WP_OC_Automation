#!/bin/sh
################################################################################   
#
#  File Name		  : lib-dos.tcl
#
#  Description      :
#         This TCL contains functions to configure Denial of Service
#
#  Revision History :
#        Date           Programmer        Description
#        -----------------------------------------------------------------------
#        17-Nov-2010     kenddy           Created
#
#
#
#
################################################################################



#*******************************************************************************
#  Function Name	: _ntgrDosEnableAll
#
#  Description    : This function is called to configure dos enable all
#         
#  Usage          : _ntgrDosEnableAll 
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosEnableAll {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control all\r"
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
#  Function Name	: _ntgrDosDisableAll
#
#  Description    : This function is called to configure dos disable all
#         
#  Usage          : _ntgrDosDisableAll 
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosDisableAll {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control all\r"
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
#  Function Name	: _ntgrDosCheckFirstFrag
#
#  Description    : This function is called to configure dos check first frag
#         
#  Usage          : _ntgrDosCheckFirstFrag <switch_name> <tcp_head_len>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckFirstFrag {switch_name tcp_head_len} {
	
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
    exp_send -i $cnn_id "dos-control firstfrag $tcp_head_len\r"
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
#  Function Name	: _ntgrDosUnCheckFirstFrag
#
#  Description    : This function is called to configure dos no check first frag
#         
#  Usage          : _ntgrDosUnCheckFirstFrag <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckFirstFrag {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control firstfrag\r"
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
#  Function Name	: _ntgrDosCheckIcmpFrag
#
#  Description    : This function is called to configure dos check icmp frag
#         
#  Usage          : _ntgrDosCheckIcmpFrag <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckIcmpFrag {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control icmpfrag\r"
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
#  Function Name	: _ntgrDosUnCheckIcmpFrag
#
#  Description    : This function is called to configure dos no check icmp frag
#         
#  Usage          : _ntgrDosUnCheckIcmpFrag <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckIcmpFrag {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control icmpfrag\r"
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
#  Function Name	: _ntgrDosCheckIcmpv4Size
#
#  Description    : This function is called to configure check icmpv4 size (default size:512)
#         
#  Usage          : _ntgrDosCheckIcmpv4Size <switch_name> <size>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckIcmpv4Size {switch_name size} {
	
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
    exp_send -i $cnn_id "dos-control icmpv4\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "dos-control icmpv4 $size\r"
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
#  Function Name	: _ntgrDosUnCheckIcmpv4Size
#
#  Description    : This function is called to configure no check icmpv4 size(restore to default: 512)
#         
#  Usage          : _ntgrDosUnCheckIcmpv4Size <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckIcmpv4Size {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control icmpv4\r"
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
#  Function Name	: _ntgrDosCheckIcmpv6Size
#
#  Description    : This function is called to configure check icmpv6 size (default size:512)
#         
#  Usage          : _ntgrDosCheckIcmpv6Size <switch_name> <size>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckIcmpv6Size {switch_name size} {
	
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
    exp_send -i $cnn_id "dos-control icmpv6\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "dos-control icmpv6 $size\r"
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
#  Function Name	: _ntgrDosUnCheckIcmpv6Size
#
#  Description    : This function is called to configure no check icmpv6 size(restore to default: 512)
#         
#  Usage          : _ntgrDosUnCheckIcmpv6Size <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckIcmpv6Size {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control icmpv6\r"
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
#  Function Name	: _ntgrDosCheckL4Port
#
#  Description    : This function is called to configure check L4 port
#         
#  Usage          : _ntgrDosCheckL4Port <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckL4Port {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control l4port\r"
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
#  Function Name	: _ntgrDosUnCheckL4Port
#
#  Description    : This function is called to configure no check L4 port
#         
#  Usage          : _ntgrDosUnCheckL4Port <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckL4Port {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control l4port\r"
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
#  Function Name	: _ntgrDosCheckSipDip
#
#  Description    : This function is called to configure check sip=dip
#         
#  Usage          : _ntgrDosCheckSipDip <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckSipDip {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control sipdip\r"
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
#  Function Name	: _ntgrDosUnCheckSipDip
#
#  Description    : This function is called to configure undo check sip=dip
#         
#  Usage          : _ntgrDosUnCheckSipDip <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckSipDip {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control sipdip\r"
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
#  Function Name	: _ntgrDosCheckSmacDmac
#
#  Description    : This function is called to configure check smac=dmac
#         
#  Usage          : _ntgrDosCheckSmacDmac <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckSmacDmac {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control smacdmac\r"
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
#  Function Name	: _ntgrDosUnCheckSmacDmac
#
#  Description    : This function is called to configure no check smac=dmac
#         
#  Usage          : _ntgrDosUnCheckSmacDmac <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckSmacDmac {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control smacdmac\r"
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
#  Function Name	: _ntgrDosCheckTcpFlag
#
#  Description    : This function is called to configure check tcp flag
#         
#  Usage          : _ntgrDosCheckTcpFlag <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckTcpFlag {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control tcpflag\r"
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
#  Function Name	: _ntgrDosUnCheckTcpFlag
#
#  Description    : This function is called to configure no check tcp flag
#         
#  Usage          : _ntgrDosUnCheckTcpFlag <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckTcpFlag {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control tcpflag\r"
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
#  Function Name	: _ntgrDosCheckTcpFinUrgPsh
#
#  Description    : This function is called to configure check tcp FIN URG PUSH
#         
#  Usage          : _ntgrDosCheckTcpFinUrgPsh <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckTcpFinUrgPsh {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control tcpfinurgpsh\r"
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
#  Function Name	: _ntgrDosUnCheckTcpFinUrgPsh
#
#  Description    : This function is called to configure no check tcp FIN URG PUSH
#         
#  Usage          : _ntgrDosUnCheckTcpFinUrgPsh <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckTcpFinUrgPsh {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control tcpfinurgpsh\r"
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
#  Function Name	: _ntgrDosCheckTcpFlagSeq
#
#  Description    : This function is called to configure check tcp flag and seq
#         
#  Usage          : _ntgrDosCheckTcpFlagSeq <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckTcpFlagSeq {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control tcpflagseq\r"
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
#  Function Name	: _ntgrDosUnCheckTcpFlagSeq
#
#  Description    : This function is called to configure no check tcp flag and seq
#         
#  Usage          : _ntgrDosUnCheckTcpFlagSeq <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckTcpFlagSeq {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control tcpflagseq\r"
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
#  Function Name	: _ntgrDosCheckTcpFrag
#
#  Description    : This function is called to configure check tcp frag
#         
#  Usage          : _ntgrDosCheckTcpFrag <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckTcpFrag {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control tcpfrag\r"
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
#  Function Name	: _ntgrDosUnCheckTcpFrag
#
#  Description    : This function is called to configure uncheck tcp frag
#         
#  Usage          : _ntgrDosUnCheckTcpFrag <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckTcpFrag {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control tcpfrag\r"
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
#  Function Name	: _ntgrDosCheckTcpOffset
#
#  Description    : This function is called to configure check tcp offset
#         
#  Usage          : _ntgrDosCheckTcpOffset <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckTcpOffset {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control tcpoffset\r"
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
#  Function Name	: _ntgrDosUnCheckTcpOffset
#
#  Description    : This function is called to configure uncheck tcp offset
#         
#  Usage          : _ntgrDosUnCheckTcpOffset <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckTcpOffset {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control tcpoffset\r"
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
#  Function Name	: _ntgrDosCheckTcpPort
#
#  Description    : This function is called to configure check tcp port
#         
#  Usage          : _ntgrDosCheckTcpPort <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckTcpPort {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control tcpport\r"
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
#  Function Name	: _ntgrDosUnCheckTcpPort
#
#  Description    : This function is called to configure no check tcp port
#         
#  Usage          : _ntgrDosUnCheckTcpPort <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckTcpPort {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control tcpport\r"
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
#  Function Name	: _ntgrDosCheckTcpSyn
#
#  Description    : This function is called to configure check tcp syn
#         
#  Usage          : _ntgrDosCheckTcpSyn <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckTcpSyn {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control tcpsyn\r"
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
#  Function Name	: _ntgrDosUnCheckTcpSyn
#
#  Description    : This function is called to configure no check tcp syn
#         
#  Usage          : _ntgrDosUnCheckTcpSyn <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckTcpSyn {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control tcpsyn\r"
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
#  Function Name	: _ntgrDosCheckTcpSynFin
#
#  Description    : This function is called to configure check tcp syn and fin
#         
#  Usage          : _ntgrDosCheckTcpSynFin <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckTcpSynFin {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control tcpsynfin\r"
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
#  Function Name	: _ntgrDosUnCheckTcpSynFin
#
#  Description    : This function is called to configure no check tcp syn and fin
#         
#  Usage          : _ntgrDosUnCheckTcpSynFin <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckTcpSynFin {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control tcpsynfin\r"
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
#  Function Name	: _ntgrDosCheckUdpPort
#
#  Description    : This function is called to configure check udp port
#         
#  Usage          : _ntgrDosCheckUdpPort <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckUdpPort {switch_name} {
	
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
    exp_send -i $cnn_id "dos-control udpport\r"
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
#  Function Name	: _ntgrDosUnCheckUdpPort
#
#  Description    : This function is called to configure no check udp port
#         
#  Usage          : _ntgrDosUnCheckUdpPort <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckUdpPort {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control udpport\r"
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
#  Function Name	: _ntgrDosCheckIcmpSize
#
#  Description    : This function is called to configure check icmp size (default size:512)
#         
#  Usage          : _ntgrDosCheckIcmpSize <switch_name> <size>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosCheckIcmpSize {switch_name size} {
	
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
    exp_send -i $cnn_id "dos-control icmp $size\r"
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
#  Function Name	: _ntgrDosUnCheckIcmpSize
#
#  Description    : This function is called to configure no check icmp size(restore to default: 512)
#         
#  Usage          : _ntgrDosUnCheckIcmpSize <switch_name>
#
#  Revision       : Create by kenddy xie 2010-11-17
#
#*******************************************************************************
proc _ntgrDosUnCheckIcmpSize {switch_name} {
	
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
    exp_send -i $cnn_id "no dos-control icmp\r"
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