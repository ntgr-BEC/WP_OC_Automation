#!/bin/sh
#*********************************************************************************   
#
#  File Name		  : lib-vrrp.tcl
#
#  Description      :
#         This TCL contains functions to configure VRRP
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        2015-10-20     Jim Xie        Created
#
#*********************************************************************************

#*******************************************************************************
#  Function Name	: CALntgrEnableVRRPGlobal
#
#  Description    	: This function is called to enable VRRP on switch
#         
#  Usage          	: CALntgrEnableVRRPGlobal <switch_name> 
#
#*******************************************************************************
proc CALntgrEnableVRRPGlobal {switch_name} {
	ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "ip vrrp"
    ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrDisableVRRPGlobal
#
#  Description    	: This function is called to disable VRRP on switch
#         
#  Usage          	: CALntgrDisableVRRPGlobal <switch_name> 
#
#*******************************************************************************
proc CALntgrDisableVRRPGlobal {switch_name} {
	ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "no ip vrrp"
    ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrAddVRRPVRID
#
#  Description    	: This function is called to add VRID on switch
#         
#  Usage          	: CALntgrAddVRRPVRID <switch_name> <interface> <vrrid> 
#
#*******************************************************************************
proc CALntgrAddVRRPVRID {switch_name interface vrrid} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "ip vrrp $vrrid"
	ntgrCLIExecute $switch_name "ip vrrp $vrrid mode"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrDeleteVRRPVRID
#
#  Description    	: This function is called to delete VRID on switch
#         
#  Usage          	: CALntgrDeleteVRRPVRID <switch_name> <interface> <vrrid>
#
#*******************************************************************************
proc CALntgrDeleteVRRPVRID {switch_name interface vrrid} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "no ip vrrp $vrrid mode"
	ntgrCLIExecute $switch_name "no ip vrrp $vrrid"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrAddVRRPVIP
#
#  Description    	: This function is called to configure VIP and second ip on switch
#         
#  Usage          	: CALntgrAddVRRPVIP <switch_name> <interface> <vrrid> <VIP> <VIP_second> 
#
#*******************************************************************************
proc CALntgrAddVRRPVIP {switch_name interface vrrid VIP {VIP_second ""}} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "ip vrrp $vrrid ip $VIP"
	if { $VIP_second != "" } {
	    ntgrCLIExecute $switch_name "ip vrrp $vrrid ip $VIP_second secondary"
	}
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrDeleteVRRPVIP
#
#  Description    	: This function is called to delete VIP and second ip on switch
#         
#  Usage          	: CALntgrDeleteVRRPVIP <switch_name> <interface> <vrrid> <VIP> <VIP_second> 
#
#*******************************************************************************
proc CALntgrDeleteVRRPVIP {switch_name interface vrrid VIP {VIP_second ""}} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	if { $VIP_second != "" } {
	    ntgrCLIExecute $switch_name "no ip vrrp $vrrid ip $VIP_second secondary"
	}
	ntgrCLIExecute $switch_name "no ip vrrp $vrrid ip $VIP"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrVRRPAuthenticationEnable
#
#  Description    	: This function is called to configure VRRP authentication on switch
#         
#  Usage          	: CALntgrVRRPAuthenticationEnable <switch_name> <interface> <vrrid> <Auth_mode> <key> 
#
#*******************************************************************************
proc CALntgrVRRPAuthenticationEnable {switch_name interface vrrid Auth_mode {key ""}} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "ip vrrp $vrrid authentication $Auth_mode $key"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrVRRPAuthenticationDisable
#
#  Description    	: This function is called to delete VRRP authentication on switch
#         
#  Usage          	: CALntgrVRRPAuthenticationDisable <switch_name> <interface> <vrrid> 
#
#*******************************************************************************
proc CALntgrVRRPAuthenticationDisable {switch_name interface vrrid } {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "no ip vrrp $vrrid authentication"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrVRRPPreemptEnable
#
#  Description    	: This function is called to enable VRRP preempt on switch
#         
#  Usage          	: CALntgrVRRPPreemptEnable <switch_name> <interface> <vrrid>
#
#*******************************************************************************
proc CALntgrVRRPPreemptEnable {switch_name interface vrrid} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "ip vrrp $vrrid preempt"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrVRRPPreemptDisable
#
#  Description    	: This function is called to disable VRRP preempt on switch
#         
#  Usage          	: CALntgrVRRPPreemptDisable <switch_name> <interface> <vrrid>
#
#*******************************************************************************
proc CALntgrVRRPPreemptDisable {switch_name interface vrrid} {
  	ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "no ip vrrp $vrrid preempt"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrVRRPAcceptModeEnable
#
#  Description    	: This function is called to enable VRRP accept-mode on switch
#         
#  Usage          	: CALntgrVRRPAcceptModeEnable <switch_name> <interface> <vrrid>
#
#*******************************************************************************
proc CALntgrVRRPAcceptModeEnable {switch_name interface vrrid} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "ip vrrp $vrrid accept-mode"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrVRRPAcceptModeDisable
#
#  Description    	: This function is called to disable VRRP accept-mode on switch
#         
#  Usage          	: CALntgrVRRPAcceptModeDisable <switch_name> <interface> <vrrid>
#
#*******************************************************************************
proc CALntgrVRRPAcceptModeDisable {switch_name interface vrrid} {
  	ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "no ip vrrp $vrrid accept-mode"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrAddVRRPPriority
#
#  Description    	: This function is called to configure VRRP priority on switch
#         
#  Usage          	: CALntgrAddVRRPPriority <switch_name> <interface> <vrrid> <Priority> 
#
#*******************************************************************************
proc CALntgrAddVRRPPriority {switch_name interface vrrid Priority} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "ip vrrp $vrrid priority $Priority"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrDeleteVRRPPriority
#
#  Description    	: This function is called to delete VRRP priority on switch
#         
#  Usage          	: CALntgrDeleteVRRPPriority <switch_name> <interface> <vrrid>  
#
#*******************************************************************************
proc CALntgrDeleteVRRPPriority {switch_name interface vrrid} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "no ip vrrp $vrrid priority"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrAddVRRPAdvertiseTimer
#
#  Description    	: This function is called to add VRRP timer advertised on switch
#         
#  Usage          	: CALntgrAddVRRPAdvertiseTimer <switch_name> <interface> <vrrid> <Time_value>  
#
#*******************************************************************************
proc CALntgrAddVRRPAdvertiseTimer {switch_name interface vrrid Time_value} {
	ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "ip vrrp $vrrid timers advertise $Time_value"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrDeleteVRRPAdvertiseTimer
#
#  Description    	: This function is called to delete VRRP priority on switch
#         
#  Usage          	: CALntgrDeleteVRRPAdvertiseTimer <switch_name> <interface> <vrrid>
#
#*******************************************************************************
proc CALntgrDeleteVRRPAdvertiseTimer {switch_name interface vrrid} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	ntgrCLIExecute $switch_name "no ip vrrp $vrrid timers advertise"
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrAddVRRPTracking
#
#  Description    	: This function is called to add VRRP tracking on switch
#         
#  Usage          	: CALntgrAddVRRPTracking <switch_name> <interface> <vrrid> <ip_flag> <format_value>  
#
#*******************************************************************************
proc CALntgrAddVRRPTracking {switch_name interface vrrid ip_flag format_value decre_value} {
	ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	if {$ip_flag == "ip"} {
	    ntgrCLIExecute $switch_name "ip vrrp $vrrid track ip route $format_value decrement $decre_value"
	} else {
	    ntgrCLIExecute $switch_name "ip vrrp $vrrid track interface $format_value decrement $decre_value"
	}
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrDeleteVRRPTracking
#
#  Description    	: This function is called to delete VRRP tracking on switch
#         
#  Usage          	: CALntgrDeleteVRRPTracking <switch_name> <interface> <vrrid> <ip_flag> <format_value>  
#
#*******************************************************************************
proc CALntgrDeleteVRRPTracking {switch_name interface vrrid ip_flag format_value} {
	ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	if {$ip_flag == "ip"} {
	    ntgrCLIExecute $switch_name "no ip vrrp $vrrid track ip route $format_value"
	} else {
	    ntgrCLIExecute $switch_name "no ip vrrp $vrrid track interface $format_value"
	}
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrBatchSettingVRRPIPInfo
#
#  Description    	: This function is called to batch add VRRP info on switch
#         
#  Usage          	: CALntgrBatchSettingVRRPIPInfo 
#
#*******************************************************************************
proc CALntgrBatchSettingVRRPIPInfo {switch_name interface init_vrrid init_VIP Priority ClassMode Count runFlag} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "interface $interface"
	set ip_list [split $init_VIP "."]
	if {[llength $ip_list] == 4} {
	    set ip_Class1 [lindex $ip_list 0]
		set ip_Class2 [lindex $ip_list 1]
		set ip_Class3 [lindex $ip_list 2]
		set ip_Class4 [lindex $ip_list 3]
		set init_ip_Class1 $ip_Class1
		set init_ip_Class2 $ip_Class2
		set init_ip_Class3 $ip_Class3
		set init_ip_Class4 $ip_Class4
		for {set i 0} {$i < $Count} {incr i} {
		    switch $ClassMode {
			    classA {
				    set ip_Class1 [expr $init_ip_Class1 + $i]
				}
				classB {
					set ip_Class2 [expr $init_ip_Class2 + $i]
				}
				classC {
					set ip_Class3 [expr $init_ip_Class3 + $i]
				}
				classD {
					set ip_Class4 [expr $init_ip_Class4 + $i]
				}
				default {
					set ip_Class4 [expr $init_ip_Class4 + $i]
				}
			}
			if {$ip_Class4 >= 255 || $ip_Class3 >= 255 || $ip_Class2 >= 255 || $ip_Class1 >= 255} {
			    break;
            }
		    set vrrid [expr $init_vrrid + $i]
			set new_ip "$ip_Class1.$ip_Class2.$ip_Class3.$ip_Class4"
			
			if {$runFlag == "delete"} {
			    ntgrCLIExecute $switch_name "no ip vrrp $vrrid mode"
			    ntgrCLIExecute $switch_name "no ip vrrp $vrrid"
			} elseif {$runFlag == "modify priority"} {
                ntgrCLIExecute $switch_name "ip vrrp $vrrid priority $Priority"
			} else {
                ntgrCLIExecute $switch_name "ip vrrp $vrrid"
                ntgrCLIExecute $switch_name "ip vrrp $vrrid mode"
                ntgrCLIExecute $switch_name "ip vrrp $vrrid ip $new_ip"
                ntgrCLIExecute $switch_name "ip vrrp $vrrid priority $Priority"
			}
   	        #sleep 1
        }		
	}
    ntgrCLIExecute $switch_name "exit"
	ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name	: CALntgrGetVRRPConfiguration
#
#  Description    	: This function is called to get VRRP configuration on switch
#         
#  Usage          	: CALntgrGetVRRPConfiguration <switch_name> <interface> <vrrid>
#
#*******************************************************************************
proc CALntgrGetVRRPConfiguration {switch_name interface vrrid} {
    set cmds "show ip vrrp interface $interface $vrrid" 
	set retStr ""
	set vrrp(PrimaryIP) ""
	set vrrp(SecondaryIP) ""
	set vrrp(VMAC) ""
	set vrrp(AcceptMode) ""
	set vrrp(State) ""
	set vrrp(Priority) ""
  	CALCheckExpect $switch_name $cmds retStr 
  	set tmpList [split $retStr \n] 
	foreach iList $tmpList {
        if {[regexp -nocase -- "^Primary\\s+IP\\s+address.*\\s+(\[0-9\]+.\[0-9\]+.\[0-9\]+.\[0-9\]+)" $iList match primary_ip]} {
           set vrrp(PrimaryIP) $primary_ip
        }
		if {[regexp -nocase -- "^Secondary\\s+IP\\s+Address\\(es\\).*\\s+(\[0-9\]+.\[0-9\]+.\[0-9\]+.\[0-9\]+)" $iList match secondary_ip]} {
           set vrrp(SecondaryIP) $secondary_ip
        }
		if {[regexp -nocase -- "^VMAC\\s+Address.*\\s+(\[A-Za-z0-9\]{2}:\[A-Za-z0-9\]{2}:\[A-Za-z0-9\]{2}:\[A-Za-z0-9\]{2}:\[A-Za-z0-9\]{2}:\[A-Za-z0-9\]{2})" $iList match vmac]} {
           set vrrp(VMAC) $vmac
        }
		if {[regexp -nocase -- "^Accept\\s+Mode.*\\s+(\[A-Za-z\]+)" $iList match AcceptMode]} {
           set vrrp(AcceptMode) [string tolower $AcceptMode]
        }
		if {[regexp -nocase -- "^State.*\\s+(\[A-Za-z\]+)" $iList match status]} {
           set vrrp(State) [string tolower $status]
        }
		if {[regexp -nocase -- "^Priority.*\\s+(\[0-9\]+)" $iList match priority]} {
           set vrrp(Priority) [string tolower $priority]
        }
    }
    return [array get vrrp]
}

#*******************************************************************************
#  Function Name	: CALntgrLoopCheckVRRPConfiguration
#
#  Description    	: This function is called to loop check VRRP configuration on switch
#         
#  Usage          	: CALntgrLoopCheckVRRPConfiguration <switch_name> <interface> <vrrid>
#
#*******************************************************************************
proc CALntgrLoopCheckVRRPConfiguration {switch_name interface Startvrrid ExpectState Count MaxVRRPID} {
    set returnValue 1
    for {set i 0} {$i < $Count} {incr i} {
	    set retStr ""
		set RealState ""
	    set vrrid [expr $Startvrrid + $i]
		if {$vrrid > $MaxVRRPID} {
		     break
		}
        set cmds "show ip vrrp interface $interface $vrrid"
  	    CALCheckExpect $switch_name $cmds retStr 
  	    set tmpList [split $retStr \n]		
	    foreach iList $tmpList {
		    if {[regexp -nocase -- "^State.*\\s+(\[A-Za-z\]+)" $iList match state]} {
                set RealState [string tolower $state]
            }
        }
		if {$RealState != [string tolower $ExpectState]} {
		    set returnValue 0
            Netgear_log_file "Checkpoint ERROR: " " VRID $vrrid state should be <$ExpectState> but real state is <$RealState> ." "YES"
		}
    }
    return $returnValue
}