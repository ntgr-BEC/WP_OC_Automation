####################################################################################
#  File Name   : cal-ntgr-dhcp-l2relay.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for dhcp l2relay configuration.
#
#  History     :
#        Date          Programmer         Description
#        Jan 09 2013   Tony Jing          Created
#
####################################################################################


#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelayEnable
#
#  Description    : This function is called to enable dhcp l2relay.
#
#  Usage          : CALDHCPL2RelayEnable <switch_name>
#
#*******************************************************************************
proc CALDHCPL2RelayEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelayEnable $switch_name
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelayDisable
#
#  Description    : This function is called to disable dhcp l2relay.
#
#  Usage          : CALDHCPL2RelayDisable <switch_name>
#
#*******************************************************************************
proc CALDHCPL2RelayDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelayDisable $switch_name
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelayEnableOnVlan
#
#  Description    : This function is called to enable dhcp l2relay on vlan.
#
#  Usage          : CALDHCPL2RelayEnableOnVlan <switch_name> <vlan_list>
#
#*******************************************************************************
proc CALDHCPL2RelayEnableOnVlan {switch_name vlan_list} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelayEnableOnVlan $switch_name $vlan_list
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelayDisableOnVlan
#
#  Description    : This function is called to disable dhcp l2relay.
#
#  Usage          : CALDHCPL2RelayDisableOnVlan <switch_name> <vlan_list>
#
#*******************************************************************************
proc CALDHCPL2RelayDisableOnVlan {switch_name vlan_list} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelayDisableOnVlan $switch_name $vlan_list
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelayCircuitIDEnableOnVlan
#
#  Description    : This function is called to enable dhcp l2relay circuit-id on vlan.
#
#  Usage          : CALDHCPL2RelayCircuitIDEnableOnVlan <switch_name> <vlan_list>
#
#*******************************************************************************
proc CALDHCPL2RelayCircuitIDEnableOnVlan {switch_name vlan_list} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelayCircuitIDEnableOnVlan $switch_name $vlan_list
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelayCircuitIDDisableOnVlan
#
#  Description    : This function is called to disable dhcp l2relay circuit-id on vlan.
#
#  Usage          : CALDHCPL2RelayCircuitIDDisableOnVlan <switch_name> <vlan_list>
#
#*******************************************************************************
proc CALDHCPL2RelayCircuitIDDisableOnVlan {switch_name vlan_list} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelayCircuitIDDisableOnVlan $switch_name $vlan_list
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelaySetRemoteIDOnVlan
#
#  Description    : This function is called to set dhcp l2relay remote-id on vlan.
#
#  Usage          : CALDHCPL2RelaySetRemoteIDOnVlan <switch_name> <vlan_list> <remoteid>
#
#*******************************************************************************
proc CALDHCPL2RelaySetRemoteIDOnVlan {switch_name vlan_list remoteid} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelaySetRemoteIDOnVlan $switch_name $vlan_list $remoteid
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelayUnsetRemoteIDOnVlan
#
#  Description    : This function is called to unset dhcp l2relay remote-id on vlan.
#
#  Usage          : CALDHCPL2RelayUnsetRemoteIDOnVlan <switch_name> <vlan_list>
#
#*******************************************************************************
proc CALDHCPL2RelayUnsetRemoteIDOnVlan {switch_name vlan_list} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelayUnsetRemoteIDOnVlan $switch_name $vlan_list
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelayEnableOnPort
#
#  Description    : This function is called to enable dhcp l2relay on port.
#
#  Usage          : CALDHCPL2RelayEnableOnPort <switch_name> <port_list>
#
#*******************************************************************************
proc CALDHCPL2RelayEnableOnPort {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelayEnableOnPort $switch_name $port_list
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelayDisableOnPort
#
#  Description    : This function is called to disable dhcp l2relay on port.
#
#  Usage          : CALDHCPL2RelayDisableOnPort <switch_name> <port_list>
#
#*******************************************************************************
proc CALDHCPL2RelayDisableOnPort {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelayDisableOnPort $switch_name $port_list
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelayTrustEnableOnPort
#
#  Description    : This function is called to enable dhcp l2relay trust on port.
#
#  Usage          : CALDHCPL2RelayTrustEnableOnPort <switch_name> <port_list>
#
#*******************************************************************************
proc CALDHCPL2RelayTrustEnableOnPort {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelayTrustEnableOnPort $switch_name $port_list
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelayTrustDisableOnPort
#
#  Description    : This function is called to disable dhcp l2relay on port.
#
#  Usage          : CALDHCPL2RelayTrustDisableOnPort <switch_name> <port_list>
#
#*******************************************************************************
proc CALDHCPL2RelayTrustDisableOnPort {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelayTrustDisableOnPort $switch_name $port_list
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPL2RelayClearAllStats
#
#  Description    : This function is called to clear DHCP L2 Relay Statistics.
#
#  Usage          : CALDHCPL2RelayClearAllStats <switch_name>
#
#*******************************************************************************
proc CALDHCPL2RelayClearAllStats {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPL2RelayClearAllStats $switch_name
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
            Netgear_log_file "cal-ntgr-dhcp-l2relay.tcl" "Switch not defined"
        }
    }
}

proc getDHCPOption82CircuitID {Inport} {
   puts $Inport 
   if {[regexp {/} $Inport] != 1} {
        set portNum ""
        set tranferPort ""
        regexp {.*(\d+)} $Inport match portNum
        append tranferPort "0/" $portNum
        set Inport $tranferPort
        puts $Inport
   } 
       
   set result ""
   set portlist [split $Inport "/"]
   
   if {[llength $portlist] == 3} {
       # port as "xx/xx/xx"
       for {set i 0} {$i < 3} {incr i} {
          set port [lindex $portlist $i]
          if {$port >=0 && $port <16} {
             append result "0"   
          } 
          append result [format "%x" $port]
       }
       
   }  elseif {[llength $portlist] == 2} {
        # port as "xx/xx"
       set chassis [lindex $portlist 0]
       if {$chassis >=0 && $chassis <16} {
             append result "0"   
        } 
       append result [format "%x" $chassis]
       append result "00"
       set port [lindex $portlist 1]
       for {set i 0} {$i < [string length $port]} {incr i} {
           set p1 [string index $port $i]
           if { [string is integer $p1]} {
                set num1 [string range $port $i end]
                if {$num1 >=0 && $num1 <16} {
                     append result "0"   
                }
                append result [format "%x" $num1]
                break
           }
       }
   } else {
       set result "Error!" 
       Netgear_log_file "Error!" "$Inport format is not match."
   }
   return "$result"   
}   