#!/bin/sh
################################################################################   
#
#  File Name   : lib-mlag.tcl
#
#  Description :
#         This TCL contains APIs to configure Mlag on netgear switches. 
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        3/1/14      Jason Li       Created
#
#
#
#
################################################################################

#*******************************************************************************
#  Function Name	: _ntgrMLAGVPCModeEnable
#
#  Description    	: This function is called to enable mlag vpc mode on switch
#         
#  Usage          	: _ntgrMLAGVPCModeEnable <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGVPCModeEnable {switch_name} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "feature vpc\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGVPCModeDisable
#
#  Description    	: This function is called to disable mlag vpc mode on switch
#         
#  Usage          	: _ntgrMLAGVPCModeDisable <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGVPCModeDisable {switch_name} {
  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "no feature vpc\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGSetKeepalivePriority
#
#  Description    	: This function is called to set keep alive priority on switch
#         
#  Usage          	: _ntgrMLAGSetKeepalivePriority <switch_name> <pri>
#
#
#*******************************************************************************
proc _ntgrMLAGSetKeepalivePriority {switch_name pri} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "vpc domain 1\r"
  expect -i $connection_id "#"
  exp_send -i $connection_id "role priority $pri\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGReSetKeepalivePriority
#
#  Description    	: This function is called to reset keep alive priority on switch
#         
#  Usage          	: _ntgrMLAGReSetKeepalivePriority <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGReSetKeepalivePriority {switch_name} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "vpc domain 1\r"
  expect -i $connection_id "#"
  exp_send -i $connection_id "no role priority\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGSetKeepaliveTimeout
#
#  Description    	: This function is called to set keep alive timeout on switch
#         
#  Usage          	: _ntgrMLAGSetKeepaliveTimeout <switch_name> <timeout>
#
#
#*******************************************************************************
proc _ntgrMLAGSetKeepaliveTimeout {switch_name timeout} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "vpc domain 1\r"
  expect -i $connection_id "#"
  exp_send -i $connection_id "peer-keepalive timeout $timeout\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGReSetKeepaliveTimeout
#
#  Description    	: This function is called to reset keep alive timeout on switch
#         
#  Usage          	: _ntgrMLAGSetKeepaliveTimeout <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGReSetKeepaliveTimeout {switch_name} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "vpc domain 1\r"
  expect -i $connection_id "#"
  exp_send -i $connection_id "no peer-keepalive timeout\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGKeepaliveModeEnable
#
#  Description    	: This function is called to enable keepalive mode on switch
#         
#  Usage          	: _ntgrMLAGKeepaliveModeEnable <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGKeepaliveModeEnable {switch_name } {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "vpc domain 1\r"
  expect -i $connection_id "#"
  exp_send -i $connection_id "peer-keepalive enable\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGKeepaliveModeDisable
#
#  Description    	: This function is called to disable keepalive mode on switch
#         
#  Usage          	: _ntgrMLAGKeepaliveModeDisable <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGKeepaliveModeDisable {switch_name } {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "vpc domain 1\r"
  expect -i $connection_id "#"
  exp_send -i $connection_id "no peer-keepalive enable\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGSetPeerLinkChannel
#
#  Description    	: This function is called to set peer link port on switch
#         
#  Usage          	: _ntgrMLAGSetPeerLinkChannel <switch_name> <portlist>
#
#
#*******************************************************************************
proc _ntgrMLAGSetPeerLinkChannel {switch_name portlist} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name] 
  foreach	channel  $portlist { 
  if { [string first "POCH" $channel] != -1 } {
		#This is a port channel,get the logical interface name first
		set channel [getChannelLogicalIfName $switch_name $channel] 
	}
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "interface $channel\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "vpc peer-link\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  }
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGReSetPeerLinkChannel
#
#  Description    	: This function is called to reset peer link port on switch
#         
#  Usage          	: _ntgrMLAGReSetPeerLinkChannel <switch_name> <channel>
#
#
#*******************************************************************************
proc _ntgrMLAGReSetPeerLinkChannel {switch_name portlist} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]  
  foreach	channel  $portlist { 
  if { [string first "POCH" $channel] != -1 } {
		#This is a port channel,get the logical interface name first
		set channel [getChannelLogicalIfName $switch_name $channel] 
	}
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "interface $channel\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "no vpc peer-link\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  }
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGPeerDetectionEnable
#
#  Description    	: This function is called to enable peer detection on switch
#         
#  Usage          	: _ntgrMLAGPeerDetectionEnable <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGPeerDetectionEnable {switch_name} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "vpc domain 1\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "peer detection enable\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGPeerDetectionDisable
#
#  Description    	: This function is called to disable peer detection on switch
#         
#  Usage          	: _ntgrMLAGPeerDetectionDisable <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGPeerDetectionDisable {switch_name} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "vpc domain 1\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "no peer detection enable\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGSetPeerDetectioninfo
#
#  Description    	: This function is called to set peer detection info on switch
#         
#  Usage          	: _ntgrMLAGSetPeerDetectioninfo <switch_name> <dstip> <srcip> <udpport>
#
#
#*******************************************************************************
proc _ntgrMLAGSetPeerDetectioninfo {switch_name dstip srcip udpport} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "vpc domain 1\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "peer-keepalive destination $dstip source $srcip udp-port $udpport\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGReSetPeerDetectioninfo
#
#  Description    	: This function is called to reset peer detection on switch
#         
#  Usage          	: _ntgrMLAGReSetPeerDetectioninfo <switch_name> <dstip> <srcip> 
#
#
#*******************************************************************************
proc _ntgrMLAGReSetPeerDetectioninfo {switch_name dstip srcip} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name]
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "vpc domain 1\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "no peer-keepalive destination $dstip source $srcip\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGAddVPCInterfaceConfig
#
#  Description    	: This function is called to reset peer link port on switch
#         
#  Usage          	: _ntgrMLAGAddVPCInterfaceConfig <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGAddVPCInterfaceConfig {switch_name portlist vpcid} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name] 
  foreach	channel  $portlist { 
  if { [string first "POCH" $channel] != -1 } {
		#This is a port channel,get the logical interface name first
		set channel [getChannelLogicalIfName $switch_name $channel] 
	}
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "interface $channel\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "vpc $vpcid\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000 
  }
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGDeleteVPCInterfaceConfig
#
#  Description    	: This function is called to reset peer link port on switch
#         
#  Usage          	: _ntgrMLAGDeleteVPCInterfaceConfig <switch_name> <portlist> <vpcid>
#
#
#*******************************************************************************
proc _ntgrMLAGDeleteVPCInterfaceConfig {switch_name portlist vpcid} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name] 
  foreach	channel  $portlist { 
  if { [string first "POCH" $channel] != -1 } {
		#This is a port channel,get the logical interface name first
		set channel [getChannelLogicalIfName $switch_name $channel] 
 }
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "interface $channel\r"
  sleep 1
  expect -i $connection_id "#"
  exp_send -i $connection_id "no vpc $vpcid\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  exp_send -i $connection_id "exit\r"
  after 1000
  }
  Netgear_disconnect_switch $switch_name
 return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGGetKeepaliveStatuebyId
#
#  Description    	: This function is called to get vpc interface status on switch
#         
#  Usage          	: _ntgrMLAGGetKeepaliveStatuebyId <switch_name> <vpcid>
#
#
#*******************************************************************************
proc _ntgrMLAGGetVPCInterfaceStatusbyId {switch_name vpcid} {

   set cmds "show vpc $vpcid" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
  	set tmpList [split $retStr \n] 
	foreach iList $tmpList {  
   if {[regexp -nocase -- "Operational mode*" $iList]} {
      set status [lindex $iList end]
      Netgear_log_file "lib-mlag.tcl" "keepalive status is $status"
      break
    } 
  }    
 return $status
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGGetVPCSelfRole
#
#  Description    	: This function is called to get vpc self role on switch
#         
#  Usage          	: _ntgrMLAGGetVPCSelfRole <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGGetVPCSelfRole {switch_name} {

   set cmds "show vpc brief" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
  	set tmpList [split $retStr \n] 
	foreach iList $tmpList {
   if {[regexp -nocase -- "Self role*" $iList]} {
      set role [lindex $iList end]
      Netgear_log_file "lib-mlag.tcl" "VPC Self role  is $role"
      break
    } 
  }    
 return $role
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGGetVPCPeerLinkStatus
#
#  Description    	: This function is called to get vpc peer link status on switch
#         
#  Usage          	: _ntgrMLAGGetVPCPeerLinkStatus <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGGetVPCPeerLinkStatus {switch_name} {

   set cmds "show vpc brief" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
  	set tmpList [split $retStr \n] 
	foreach iList $tmpList {
   if {[regexp -nocase -- "Peer-link admin status*" $iList]} {
      set status [lindex $iList end]
      Netgear_log_file "lib-mlag.tcl" "vpc peer link status  is $status"
      break
    } 
  }    
 return $status
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGGetVPCPeerDetectionStatus
#
#  Description    	: This function is called to get Peer detection admin status on switch
#         
#  Usage          	: _ntgrMLAGGetVPCPeerDetectionStatus <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGGetVPCPeerDetectionStatus {switch_name} {

   set cmds "show vpc brief" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
  	set tmpList [split $retStr \n] 
	foreach iList $tmpList {
   if {[regexp -nocase -- "Peer detection admin status*" $iList]} {
      Netgear_log_file "lib-mlag.tcl" "jim.xie = $iList"
	  set status_table [split $iList "."]
      #set status [lindex $iList end]
	  set status [lindex $status_table end]
      Netgear_log_file "lib-mlag.tcl" "Peer detection admin status  is $status"
      break
    }
  }    
 return $status
}

#*******************************************************************************
#  Function Name	: _ntgrMLAGGetKeepalivestatus
#
#  Description    	: This function is called to get keep alive status on switch
#         
#  Usage          	: _ntgrMLAGGetKeepalivestatus <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGGetKeepalivestatus {switch_name} { 

   set cmds "show vpc peer-keepalive" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
  	set tmpList [split $retStr \n] 
	foreach iList $tmpList {
   if {[regexp -nocase -- "Peer detection operational status*" $iList]} {
      set status [lindex $iList end]
      Netgear_log_file "lib-mlag.tcl" "keep alive status is $status"
      break
    } 
  }    
 return $status
} 

#*******************************************************************************
#  Function Name	: _ntgrMLAGCheckKeepalivePriorityRange
#
#  Description    	: This function is called to check keep alive priority on switch
#					  if keepalive priority match Priority Range, it will return TRUE         
#					  if keepalive priority out of Priority Range, it will return FALSE
#
#  Usage          	: _ntgrMLAGCheckKeepalivePriorityRange <switch_name> <priority>
#
#  Return: TRUE/FALSE
#*******************************************************************************
proc _ntgrMLAGCheckKeepalivePriorityRange {switch_name priority} {

  set Ret FALSE
  set cmds "configure\nvpc domain 1\nrole priority $priority\nexit" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
  	set tmpList [split $retStr \n] 
	foreach iList $tmpList {
   if {[regexp -nocase -- "Value is out of range*" $iList]} { 
        Netgear_log_file "lib-mlag.tcl" "$iList"
      	set Ret TRUE  
      	break
    } 
  }    
 return $Ret
} 

#*******************************************************************************
#  Function Name	: _ntgrMLAGGetKeepalivePriority
#
#  Description    	: This function is called to get keep alive priority on switch
#         
#  Usage          	: _ntgrMLAGGetKeepalivePriority <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGGetKeepalivePriority {switch_name} { 

   set cmds "show vpc role" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
  	set tmpList [split $retStr \n] 
	foreach iList $tmpList {
   if {[regexp -nocase -- "Priority*" $iList]} {
      set pri [lindex $iList end]
      Netgear_log_file "lib-mlag.tcl" "keep alive priority is $pri"
      break
    } 
  }    
 return $pri
}  

#*******************************************************************************
#  Function Name	: _ntgrMLAGCheckKeepaliveTimeoutRange
#
#  Description    	: This function is called to check keep alive timeout on switch
#					  if keepalive timeout match timeout Range, it will return TRUE         
#					  if keepalive timeout out of timeout Range, it will return FALSE
#
#  Usage          	: _ntgrMLAGCheckKeepaliveTimeoutRange <switch_name> <timeout>
#
#  Return: TRUE/FALSE
#*******************************************************************************
proc _ntgrMLAGCheckKeepaliveTimeoutRange {switch_name timeout} {

  set Ret FALSE
  set cmds "configure\nvpc domain 1\npeer-keepalive timeout $timeout\nexit" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
  	set tmpList [split $retStr \n] 
	foreach iList $tmpList {
   if {[regexp -nocase -- "Value is out of range*" $iList]} { 
        Netgear_log_file "lib-mlag.tcl" "$iList"
      	set Ret TRUE  
      	break
    } 
  }    
 return $Ret
}  

#*******************************************************************************
#  Function Name	: _ntgrMLAGCheckVPCIDRange
#
#  Description    	: This function is called to check vpc id  on switch
#					  if vpc id match vpc id Range, it will return TRUE         
#					  if vpc id out of vpc id Range, it will return FALSE
#
#  Usage          	: _ntgrMLAGCheckVPCIDRange <switch_name> <vpcid>
#
#  Return: TRUE/FALSE
#
#*******************************************************************************
proc _ntgrMLAGCheckVPCIDRange {switch_name portlist vpcid} {

  set Ret FALSE 
	foreach	channel  $portlist { 
  		if { [string first "POCH" $channel] != -1 } {
			#This is a port channel,get the logical interface name first
			set channel [getChannelLogicalIfName $switch_name $channel] 
			} 
		set cmds "configure\ninterface $channel\nvpc $vpcid\nexit"
		set retStr ""
	  	CALCheckExpect $switch_name $cmds retStr 
	  	set tmpList [split $retStr \n] 
		foreach iList $tmpList {
		   if {[regexp -nocase -- "Value is out of range*" $iList]} { 
		        Netgear_log_file "lib-mlag.tcl" "$iList"
		      	set Ret TRUE  
		      	break
		    } 
  		}    
   	}
  
 return $Ret
} 

#*******************************************************************************
#  Function Name	: _ntgrMLAGGetKeepaliveStatistics
#
#  Description    	: This function is called to get keep alive statistics on switch
#         
#  Usage          	: _ntgrMLAGGetKeepaliveStatistics <switch_name> <strtext>
#
#
#*******************************************************************************
proc _ntgrMLAGGetKeepaliveStatistics {switch_name strtext} {

   set cmds "show vpc statistics peer-keepalive" 
	set retStr ""
    #set stat "NA"
  	CALCheckExpect $switch_name $cmds retStr 
  	set tmpList [split $retStr \n] 
	foreach iList $tmpList {
   if {[regexp -nocase -- "$strtext*" $iList]} {
      set stat [lindex $iList end]
      Netgear_log_file "lib-mlag.tcl" "$strtext  is $stat"
      break
    } 
  }    
 return $stat
} 

#*******************************************************************************
#  Function Name	: _ntgrMLAGGetPeerlinkStatistics
#
#  Description    	: This function is called to get peer link statistics on switch
#         
#  Usage          	: _ntgrMLAGGetPeerlinkStatistics <switch_name> <strtext>
#
#
#*******************************************************************************
proc _ntgrMLAGGetPeerlinkStatistics {switch_name strtext} {

   set cmds "show vpc statistics peer-link" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
  	set tmpList [split $retStr \n] 
	foreach iList $tmpList {
   if {[regexp -nocase -- "$strtext*" $iList]} {
      set stat [lindex $iList end]
      Netgear_log_file "lib-mlag.tcl" "$strtext  is $stat"
      break
    } 
  }    
 return $stat
}  

#*******************************************************************************
#  Function Name	: _ntgrMLAGClearPeerlinkStatistics
#
#  Description    	: This function is called to clear peer link statistics on switch
#         
#  Usage          	: _ntgrMLAGClearPeerlinkStatistics <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGClearPeerlinkStatistics {switch_name} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name] 
  expect -i $connection_id -re "#" 
  exp_send -i $connection_id "clear vpc statistics peer-link\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
}  

#*******************************************************************************
#  Function Name	: _ntgrMLAGClearKeepaliveStatistics
#
#  Description    	: This function is called to clear keep alive statistics on switch
#         
#  Usage          	: _ntgrMLAGClearKeepaliveStatistics <switch_name>
#
#
#*******************************************************************************
proc _ntgrMLAGClearKeepaliveStatistics {switch_name} {

  set Ret {}
  Netgear_connect_switch $switch_name
  set connection_id [_get_switch_handle $switch_name] 
  expect -i $connection_id -re "#" 
  exp_send -i $connection_id "clear vpc statistics peer-keepalive\r"
  after 1000
  Netgear_disconnect_switch $switch_name
 return $Ret
} 

#*******************************************************************************
#  Function Name	: _ntgrMLAGCheckKeepaliveTimeout  
#                    
#
#  Description    	: This function is called to check keep alive timeout 
#         
#  Usage          	: _ntgrMLAGCheckKeepaliveTimeout <switch_name> <timeout>
#
#
#*******************************************************************************
proc _ntgrMLAGCheckKeepaliveTimeout {switch_name timeout} { 

	# get current time
   set systemTime_start [clock seconds] 
   Netgear_log_file "lib-mlag.tcl" "the time is : [clock format $systemTime_start -format %H:%M:%S]" 
   set RetRolePrimary FALSE
   set Ret FALSE
   
   # check vpc role
   for {set i 0} {$i < 10} {incr i} { 
      set role [_ntgrMLAGGetVPCSelfRole $switch_name] 
      if { [string tolower $role] == "primary"} {  
	       set RetRolePrimary TRUE  
	  		break
	   } 
	   sleep 1 
	   Netgear_log_file "lib-mlag.tcl" "Check VPC Self Role $i time."
    }  
    
  # get current time
  set systemTime_end [clock seconds]    
  Netgear_log_file "lib-mlag.tcl" "the time is : [clock format $systemTime_end -format %H:%M:%S]"  
  
 # get the time offset between start and end
   set time_second_start [clock format $systemTime_start -format %S]  
   if { [string index $time_second_start 0] == "0"} { 
      set time_second_start [string replace $time_second_start 0 0 ""]      
   } 
   set time_minute_start [clock format $systemTime_start -format %M] 
      if { [string index $time_minute_start 0] == "0"} { 
      set time_minute_start [string replace $time_minute_start 0 0 ""]      
   }  
   set time_hour_start [clock format $systemTime_start -format %H]    
    if { [string index $time_hour_start 0] == "0"} { 
      set time_hour_start [string replace $time_hour_start 0 0 ""]      
   } 
   set time_start [expr $time_hour_start * 3600 + $time_minute_start * 60 + $time_second_start]   
   
   set time_second_end [clock format $systemTime_end -format %S]  
      if { [string index $time_second_end 0] == "0"} { 
      set time_second_end [string replace $time_second_end 0 0 ""]      
   } 
   set time_minute_end [clock format $systemTime_end -format %M] 
      if { [string index $time_minute_end 0] == "0"} { 
      set time_minute_end [string replace $time_minute_end 0 0 ""]      
   } 
   set time_hour_end [clock format $systemTime_end -format %H] 
    if { [string index $time_hour_end 0] == "0"} { 
      set time_hour_end [string replace $time_hour_end 0 0 ""]      
   } 
   set time_end [expr $time_hour_end * 3600 + $time_minute_end * 60 + $time_second_end]  
   
    # check role and timeout 
   if { $RetRolePrimary && [expr $time_end - $time_start] < [expr $timeout + 20] && [expr $time_end - $time_start] > 0} { 
       set Ret TRUE 
   }

 return $Ret
}  

#*******************************************************************************
#  Function Name	: _ntgrMLAGCheckPeerDetectionPortRange
#
#  Description    	: This function is called to check peer detection port range on switch
#					  if peer detection port match Range, it will return TRUE         
#					  if peer detection port out of  Range, it will return FALSE
#
#  Usage          	: _ntgrMLAGCheckPeerDetectionPortRange <switch_name> <dstip> <srcip> <udpport>
#
#  Return: TRUE/FALSE
#*******************************************************************************
proc _ntgrMLAGCheckPeerDetectionPortRange {switch_name dstip srcip udpport} {

  set Ret FALSE
  set cmds "configure\nvpc domain 1\npeer-keepalive destination $dstip source $srcip udp-port $udpport\nexit" 
	set retStr ""
  	CALCheckExpect $switch_name $cmds retStr 
  	set tmpList [split $retStr \n] 
	foreach iList $tmpList {
   if {[regexp -nocase -- "Value is out of range*" $iList]} { 
        Netgear_log_file "lib-mlag.tcl" "$iList"
      	set Ret TRUE  
      	break
    } 
  }    
 return $Ret
}   