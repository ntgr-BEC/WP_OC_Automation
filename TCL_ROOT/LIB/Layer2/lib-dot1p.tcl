#!/bin/sh
################################################################################   
#
#  File Name		: lib-dot1p.tcl
#
#  Description       	:
#         This file contain 802.1P and traffic class mapping
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        05/21/06		Tissa			Initial version
#
#
#
#
################################################################################


#*******************************************************************************
#
#  Function Name	: 
#
#  Description    :
#		     	
#         
#  Usage          : 
# 
#*******************************************************************************

#*******************************************************************************
#
#  Function Name	: _ntgrGlobalDot1PClassMap 
#
#  Description    : Configure Dot1P value to traffic class mapping
#			Called at Global configuration context
#		     	
#         
#  Usage          : _ntgrGlobalDot1PClassMap <switch_id> <Dot1P> <Class>  
# 
#*******************************************************************************

proc _ntgrGlobalDot1PClassMap {switch_id Dot1P Class} {

Netgear_log_file "lib-dot1p.tcl" "switch_id:$switch_id dot1p:$Dot1P class:$Class"

	exp_send -i $switch_id "configure\r"
	sleep 1
	exp_send -i $switch_id "classofservice dot1p-mapping $Dot1P $Class\r"
	sleep 1
	exp_send -i $switch_id "exit\r"
	sleep 1

}

#*******************************************************************************
#
#  Function Name	: _ntgrNOGlobalDot1PClassMap 
#
#  Description    : Remove Configuration of Dot1P value to traffic class mapping
#			Called at Global configuration context
#		     	
#         
#  Usage          : _ntgrNOGlobalDot1PClassMap <switch_id> <Dot1P>  
# 
#*******************************************************************************
proc _ntgrNOGlobalDot1PClassMap {switch_id Dot1P} {

Netgear_log_file "lib-dot1p.tcl" "switch_id:$switch_id dot1p:$Dot1P class:$Class"

Netgear_log_file "lib-dot1p.tcl-_ntgrNOGlobalDot1PClassMap " "CLI has a bug....... This command not yet implemented.."
return -1
	exp_send -i $switch_id "configure\r"
	sleep 1
	exp_send -i $switch_id "no classofservice dot1p-mapping $Dot1P\r"
	sleep 1
	exp_send -i $switch_id "exit\r"
	sleep 1

}

#*******************************************************************************
#
#  Function Name	:  _ntgrPortDot1PClassMap 
#
#  Description    : Configuration of Dot1P value to traffic class mapping
#			for each port
#		     	
#         
#  Usage          :  _ntgrPortDot1PClassMap <switch_id> <Ifname> <Dot1P> <Class> 
# 
#*******************************************************************************

proc _ntgrPortDot1PClassMap {switch_id Ifname Dot1P Class} {

Netgear_log_file "lib-dot1p.tcl" "switch_id:$switch_id  ifname:$Ifname dot1p:$Dot1P class:$Class"
	exp_send -i $switch_id "configure\r"
	sleep 1
	exp_send -i $switch_id "interface $Ifname\r"
	sleep 1
	exp_send -i $switch_id "classofservice dot1p-mapping $Dot1P $Class\r"
	sleep 1
	exp_send -i $switch_id "exit\r"
	sleep 1

	exp_send -i $switch_id "exit\r"
	sleep 1


}

#*******************************************************************************
#
#  Function Name	:  _ntgrCheckDot1PClassMap 
#
#  Description    :  check 802.1p queue mapping equal  1 0 0 1 2 2 3 3
#                    if equal return 1
#                    not equal return 0
#			
#		     	
#         
#  Usage          :  _ntgrCheckDot1PClassMap <switch_name>
# 
#*******************************************************************************

proc _ntgrCheckDot1PClassMap {switch_name} {

    set output {}
	set ip [_get_switch_ip_addr $switch_name]
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show classofservice dot1p-mapping\r"
    exp_sleep 1
    expect -i $cnn_id -re "show classofservice dot1p-mapping"
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
	set a0 [lindex $output 7]
	set a1 [lindex $output 9]
	set a2 [lindex $output 11]
	set a3 [lindex $output 13]
	set a4 [lindex $output 15]
	set a5 [lindex $output 17]
	set a6 [lindex $output 19]
	set a7 [lindex $output 21]
	puts "a0=$a0,a1=$a1,a2=$a2,a3=$a3,a4=$a4,a5=$a5,a6=$a6,a7=$a7"
	set expect [list 1 0 0 1 2 2 3 3]
	set result [list $a0 $a1 $a2 $a3 $a4 $a5 $a6 $a7]
	Netgear_log_file "lib-dot1p.tcl" "Function: _ntgrCheckDot1PClassMap dot1p=$result"
	if {$result==$expect} {
		return 1
	} else {
	    return 0
	}
}

#*******************************************************************************
#
#  Function Name	:  _ntgrGetClassMapCs1 
#
#  Description    :  get IPDSCP queue mapping :     CS1(001000)->0
#                                                     CS7(111000)->3
#		     	
#         
#  Usage          :  _ntgrGetClassMapCs1 <switch_name> 
# 
#*******************************************************************************

proc _ntgrGetClassMapCs1 {switch_name} {

    set output {}
	set ip [_get_switch_ip_addr $switch_name]
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show classofservice ip-dscp-mapping | include cs1\r"
    exp_sleep 1
    expect -i $cnn_id -re "show classofservice ip-dscp-mapping | include cs1"
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

	set cs1 [lindex $output end-2]
	
	Netgear_log_file "lib-dot1p.tcl" "Function: _ntgrGetClassMapCs1 cs1=$cs1"
	
	return $cs1
}

#*******************************************************************************
#
#  Function Name	:  _ntgrGetClassMapCs7 
#
#  Description    :  get IPDSCP queue mapping :     CS1(001000)->0
#                                                     CS7(111000)->3
#		     	
#         
#  Usage          :  _ntgrGetClassMapCs7 <switch_name> 
# 
#*******************************************************************************

proc _ntgrGetClassMapCs7 {switch_name} {

    set output {}
	set ip [_get_switch_ip_addr $switch_name]
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show classofservice ip-dscp-mapping | include cs7\r"
    exp_sleep 1
    expect -i $cnn_id -re "show classofservice ip-dscp-mapping | include cs7"
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

	set cs7 [lindex $output end-2]
	
	Netgear_log_file "lib-dot1p.tcl" "Function: _ntgrGetClassMapCs1 cs7=$cs7"
	
	return $cs7
}
#*******************************************************************************
#
#  Function Name	:  _ntgrCheckDSCPClassMap
#
#  Description    :  get IPDSCP queue mapping :     CS1(001000)->0
#                                                     CS7(111000)->3
#                    if equal return 1
#                    not equal return 0
#			
#		     	
#         
#  Usage          :  _ntgrCheckDSCPClassMap <switch_name> 
# 
#*******************************************************************************

proc _ntgrCheckDSCPClassMap {switch_name} {

   set cs1 [_ntgrGetClassMapCs1 $switch_name]
   set cs7 [_ntgrGetClassMapCs7 $switch_name]
   
   if { $cs1==0 && $cs7==3} {
	return 1
   } else {
	return 0
   }
	
}



#*******************************************************************************
#
#  Function Name	:  _ntgrNOPortDot1PClassMap 
#
#  Description    : Remove Configuration of Dot1P value to traffic class mapping
#			for each port
#		     	
#         
#  Usage          :  _ntgrNOPortDot1PClassMap <switch_name> <Ifname> <Dot1P> 
# 
#*******************************************************************************

proc _ntgrNOPortDot1PClassMap {switch_id Ifname Dot1P} {

Netgear_log_file "lib-dot1p.tcl" "switch_id:$switch_id  ifname:$Ifname dot1p:$Dot1P"

Netgear_log_file "lib-dot1p.tcl-_ntgrNOGlobalDot1PClassMap " "CLI has a bug....... This command not yet implemented.."
return -1

	exp_send -i $switch_id "configure\r"
	sleep 1
	exp_send -i $switch_id "interface $Ifname\r"
	sleep 1
	exp_send -i $switch_id "no classofservice dot1p-mapping $Dot1P $Class\r"
	sleep 1
	exp_send -i $switch_id "exit\r"
	sleep 1

	exp_send -i $switch_id "exit\r"
	sleep 1


}
#*******************************************************************************
#
#  Function Name	:  _ntgDot1PGlobalUntagPriority  
#
#  Description    : Configuration of Dot1P value for all incoming untag packets
#			at the switch level
#		     	
#         
#  Usage          :  _ntgDot1PGlobalUntagPriority <switch_id> <Priority>  
# 
#*******************************************************************************


proc _ntgDot1PGlobalUntagPriority {switch_id Priority} {

	Netgear_log_file "lib-dot1p.tcl" "switch_id:$switch_id $Priority"

	exp_send -i $switch_id "configure\r"
	sleep 1
	exp_send -i $switch_id "vlan port priority all $Priority\r"
	sleep 1
	exp_send -i $switch_id "exit\r"
	sleep 1

	


}

#*******************************************************************************
#
#  Function Name	:  _ntgNODot1PGlobalUntagPriority  
#
#  Description    : Remove Configuration of Dot1P value for all incoming untag packets
#			at the switch level
#		     	
#         
#  Usage          :  _ntgNODot1PGlobalUntagPriority <switch_name> 
# 
#*******************************************************************************
proc _ntgrNODot1PGlobalUntagPriority switch_id {
	Netgear_log_file "lib-dot1p.tcl" "switch_id:$switch_id"
	
	Netgear_log_file "lib-dot1p.tcl" "_ntgrNODot1PGlobalUntagPriority : No command does not work CLI BUG"
	
	return -1
	
	exp_send -i $switch_id "configure\r"
	sleep 1
	exp_send -i $switch_id "no vlan port priority all\r"
	sleep 1
	exp_send -i $switch_id "exit\r"
	sleep 1

	

}

#*******************************************************************************
#
#  Function Name	:  _ntgrPortDot1PUntagPriority   
#
#  Description    : Configuration of Dot1P value for all incoming untag packets
#			at the port level
#		     	
#         
#  Usage          :   _ntgrPortDot1PUntagPriority <switch_id> <IfName> <Priority> 
# 
#*******************************************************************************

proc _ntgrPortDot1PUntagPriority {switch_id IfName Priority} {

Netgear_log_file "lib-dot1p.tcl" "switch_id:$switch_id  ifname:$IfName Priority:$Priority"
	exp_send -i $switch_id "configure\r"
	sleep 1
	exp_send -i $switch_id "interface $IfName\r"
	sleep 1
	exp_send -i $switch_id "vlan priority $Priority\r"
	sleep 1
	exp_send -i $switch_id "exit\r"
	sleep 1

	exp_send -i $switch_id "exit\r"
	sleep 1


}

#*******************************************************************************
#
#  Function Name	:  _ntgrPortDot1PNOUntagPriority   
#
#  Description    : Configuration of Dot1P value for all incoming untag packets
#			at the port level
#		     	
#         
#  Usage          :   _ntgrPortDot1PNOUntagPriority <switch_id> <IfName>
# 
#*******************************************************************************


proc _ntgrPortDot1PNOUntagPriority {switch_id IfName} {


Netgear_log_file "lib-dot1p.tcl" "switch_id:$switch_id  ifname:$Ifname"
Netgear_log_file "lib-dot1p.tcl" "CLI has a bug on interface no command...."
return -1

	exp_send -i $switch_id "configure\r"
	sleep 1
	exp_send -i $switch_id "interface $Ifname\r"
	sleep 1
	exp_send -i $switch_id "no vlan priority\r"
	sleep 1
	exp_send -i $switch_id "exit\r"
	sleep 1

	exp_send -i $switch_id "exit\r"
	sleep 1



}


