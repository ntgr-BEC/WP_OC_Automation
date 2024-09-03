#!/bin/sh
################################################################################   
#
#  File Name		: lib-psl.tcl
#
#  Description       	:
#         This TCL contains functions to control Sifos PSL-3000 (PoE tester)
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        18-Dec-06     Andy xie          Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: _spirentConnect
#
#  Description    : This API is called to connect and initialise  psl-3000
#         
#  Usage          : _plsConnect <chassis_id>
#  
#  Example        : _plsConnect $P1
#
#*******************************************************************************

proc _pslConnect {chassis_id} {
	  
	  set handle [_ntgrGetPGHandle $chassis_id]
	  if {$handle == 1} {
	      Netgear_log_file "[file tail [info script]]" "The PSL tester $chassis_ip_addr was already connected!!."
	      return
	  }
	  
    ## make sure install sifos software
    
    
    ##
    set psa_root "C:/Program Files/Sifos/PSA1200/tclshrc_psapi.tcl"
##    lappend auto_path {C:/Program Files/Sifos/PSA1200}
    source $psa_root
    
    set chassis_ip_addr [_ntgrGetPGIPAddr $chassis_id]       
    set handle -1
    if {1 == [psa_exists $chassis_ip_addr]} {
    
    	  Netgear_log_file "[file tail [info script]]" "Start to connect PoE tester, IP Address is: $chassis_ip_addr"	
        set chassisId [psa $chassis_ip_addr]
        Netgear_log_file "[file tail [info script]]" "Connect to PoE tester $chassis_ip_addr successful!"
        set handle 1
        
    } else {
        Netgear_log_file "[file tail [info script]]" "Error, Please make sure $chassis_ip_addr is PSL-3000!!!!"
        set handle -1
    }
    
##    puts "psl tester handle is: $handle"
    _ntgrSetPGHandle $chassis_id $handle
	  return $handle
}

proc _pslDisconnect {chassis_id port_list} {

	  set handle [_ntgrGetPGHandle $chassis_id]
	  
	  if {$handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {
	      foreach iport $port_list {

            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
            psa_disconnect $pt
            sleep 5
	          Netgear_log_file "[file tail [info script]]" "PSA disconnect to port $pt. "
	      }
	      set handle -1
	      _ntgrSetPGHandle $chassis_id $handle
	      return
	  }
}

proc _pslAutoConfigPort {chassis_id port_list} {
	 
	  set handle [_ntgrGetPGHandle $chassis_id]

	  if {$handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {

        foreach iport $port_list {
            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
##            puts "$pt"
            psa_auto_port $pt
            Netgear_log_file "[file tail [info script]]" "auto config for port $pt"
        }
	  }
}

proc _pslSetPortAltMode {chassis_id port_list mode_list} {
	 
	  set handle [_ntgrGetPGHandle $chassis_id]

	  if {$handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {

        foreach iport $port_list imode $mode_list {
##            puts "$iport---$imode"
            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
##            puts "$pt----$imode"
            alt $pt $imode
            Netgear_log_file "[file tail [info script]]" "port $pt set to ALT $imode"
        }
	  }
}

proc _pslSetPortPolarity {chassis_id port_list polarity_list} {
	  
	  set handle [_ntgrGetPGHandle $chassis_id]
	  
	  if { $handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {
	      foreach iport $port_list ipolarity $polarity_list { 
            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
	          polarity $pt $ipolarity
	          Netgear_log_file "[file tail [info script]]" "port $pt set polarity to $ipolarity"
	      }
        
	  }

}

proc _pslSetPortSwitchMode {chassis_id port_list switch_list} {
	 
	  set handle [_ntgrGetPGHandle $chassis_id]

	  if {$handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {

        foreach iport $port_list iswitch $switch_list {
            
            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
            puts "$pt----$iswitch"
            port $pt $iswitch
            sleep 1
            Netgear_log_file "[file tail [info script]]" "port $pt set to $iswitch mode"
        }
	  }
}

proc _pslPowerPortByClassDefaultPower {chassis_id port_list class_list} {
	 
	  set handle [_ntgrGetPGHandle $chassis_id]
    set tempList ""
    
	  if {$handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {

        foreach iport $port_list iclass $class_list {
            
            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
##            puts "$pt----$iclass"
            Netgear_log_file "[file tail [info script]]" "power port $pt by class $iclass."
            psa_disconnect $pt
            sleep 5
            set status_list [power_port $pt c $iclass]
			      # modify by jim.xie attend the wait times
            sleep 10
            Netgear_log_file "[file tail [info script]]" "Port $pt status is: $status_list"
            if {[lindex $status_list 0] == "POWERED"} {
                Netgear_log_file "[file tail [info script]]" "Port $pt powered."
                
            } else {
                Netgear_log_file "[file tail [info script]]" "Port $pt failed to power up!!!"
            }
            lappend tempList $status_list
        }
        puts "$tempList"
        return $tempList
	  }
}

#*******************************************************************************
#
#  Function Name	: _pslPowerPortLagacyDefaultPower
#
#  Description    : power port lagacy or pre-ieee type
#                   change resistance t0 39 Kom Capacitance to 11uF
#  Author        : jim.xie
#
#*******************************************************************************
proc _pslPowerPortLagacyDefaultPower {chassis_id port_list class_list} {
	 
	  set handle [_ntgrGetPGHandle $chassis_id]
    set tempList ""
    
	  if {$handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {

        foreach iport $port_list iclass $class_list {
            
            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
            Netgear_log_file "[file tail [info script]]" "power port $pt by class $iclass."
            psa_disconnect $pt
            sleep 5
            set status_list [power_port $pt c $iclass dr 39 dc 11]
            sleep 30
            Netgear_log_file "[file tail [info script]]" "Port $pt status is: $status_list"
            if {[lindex $status_list 0] == "POWERED"} {
                Netgear_log_file "[file tail [info script]]" "Port $pt powered."
                
            } else {
                Netgear_log_file "[file tail [info script]]" "Port $pt failed to power up!!!"
            }
            lappend tempList $status_list
        }
        return $tempList
	  }
}

proc _pslPowerPortByClassByPower {chassis_id port_list class_list power_list} {
	 
	  set handle [_ntgrGetPGHandle $chassis_id]
    set tempList ""
    
	  if {$handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {

        foreach iport $port_list iclass $class_list ipower $power_list {
            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
##            puts "$pt----$iclass----$ipower"
            Netgear_log_file "[file tail [info script]]" "power port $pt class $iclass power $ipower Watts"
            psa_disconnect $pt
            sleep 5
            set status_list [power_port $pt c $iclass p $ipower]
            # modify by jim.xie attend the wait times
            sleep 10
            if {[lindex $status_list 0] == "POWERED"} {
                Netgear_log_file "[file tail [info script]]" "Port $pt status is: $status_list \
                V=[lindex $status_list 1](V), i=[lindex $status_list 2](mA), P=[expr [lindex $status_list 1] * [lindex $status_list 2]/1000.00](W)"
                
            } else {
                Netgear_log_file "[file tail [info script]]" "Port $pt failed to power up!!!"
            }
            lappend tempList $status_list
            
        }
        puts "$tempList"
        return $tempList
	  }
}

proc _pslSetPortCurrentLoad {chassis_id port_list cu_list} {
	 
	  set handle [_ntgrGetPGHandle $chassis_id]

	  if {$handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {

        foreach iport $port_list icurrent $cu_list {
            
            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
            puts "$pt----$icurrent"
            iload $pt i $icurrent
            sleep 1
            Netgear_log_file "[file tail [info script]]" "port $pt load current $icurrent (mA)"
        }
	  }
}

proc _pslGetPortAverageVolt {chassis_id port_list period_list} {

	  set handle [_ntgrGetPGHandle $chassis_id]
    set tempList ""
	  if {$handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {

        foreach iport $port_list iperiod $period_list {
            
            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
            puts "$pt----$period_list"
            set vPort [lindex [vdcaverage $pt period $iperiod stat] 3]
            Netgear_log_file "[file tail [info script]]" "port $pt average volt is $vPort V"
            lappend tempList $vPort
        }
	  }
    puts "$tempList"
    return $tempList
}
  
proc _pslGetPortAverageCurrent {chassis_id port_list period_list} {

	  set handle [_ntgrGetPGHandle $chassis_id]
    set tempList ""
	  if {$handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {

        foreach iport $port_list iperiod $period_list {
            
            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
            puts "$pt----$period_list"
            set iCurrent [lindex [idcaverage $pt period $iperiod stat] 3]
            Netgear_log_file "[file tail [info script]]" "port $pt average current is $iCurrent mA"
            lappend tempList $iCurrent
        }
	  }
    puts "$tempList"
    return $tempList
}

proc _pslGetPortAveragePower {chassis_id port_list period_list} {

	  set handle [_ntgrGetPGHandle $chassis_id]
    set tempList ""
	  if {$handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {

        foreach iport $port_list iperiod $period_list {
            
            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
            puts "$pt----$iperiod"
            set aStatus [paverage $pt period $iperiod stat]
            Netgear_log_file "[file tail [info script]]" "port $pt average status is: $aStatus"
            set iPower [lindex $aStatus 3]
            Netgear_log_file "[file tail [info script]]" "port $pt average power is: $iPower W"
            lappend tempList $iPower
        }
	  }
    puts "$tempList"
    return $tempList
}

proc _pslSetPortLanStatus {chassis_id port_list lan_list} {
	  
	  set handle [_ntgrGetPGHandle $chassis_id]
	  
	  if { $handle == -1 } {
	      Netgear_log_file "[file tail [info script]]" "warning, not connect psl-3000 test!!"  
	      return
	  } else {
	      foreach iport $port_list ilan $lan_list { 
            set slot [_ntgrGetPGPortSlotId $chassis_id $iport]
            set port [_ntgrGetPGPortId $chassis_id $iport]
            set pt [join [lappend slot $port] ","]
	          psa_lan $pt $ilan
	          Netgear_log_file "[file tail [info script]]" "port $pt set lan status to $ilan mode"
	      }
        
	  }

}

