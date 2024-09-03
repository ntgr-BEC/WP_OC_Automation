#!/bin/sh
################################################################################   
#
#  File Name		: lib-common-utils.tcl
#
#  Description       	:
#         This TCL contains functions commonly used - util functions.
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V       Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: _timedout
#
#  Description    : This function is called when a timeout occurs. It 
#                   outputs the reason for timeout.
#         
#  Usage          : timedout <message>
# 
#*******************************************************************************
proc _timedout {{msg {none}}} {
	global NTGR_LOG_ERROR
	NtgrDumpLog $NTGR_LOG_ERROR  "lib-common-utils.tcl" "Timed out Reason : $msg "
#	send_user "Timed out (reason: $msg)\n"
#	exit 0
}

#*******************************************************************************
#
#  Function Name	: _get_switch_vendor_name
#
#  Description    : This function is called to get the switch vendor name from
#			  the global ntgr_swList
#         
#  Usage          : _get_switch_vendor_name <switch_id>
# 
#*******************************************************************************
proc _get_switch_vendor_name {switch_id} {
	global _ntgr_swList
	return [keylget _ntgr_swList($switch_id) SWITCH_VENDOR]
}

#*******************************************************************************
#
#  Function Name	: _get_switch_model
#
#  Description    : This function is called to get the switch model from
#			  the global ntgr_swList
#         
#  Usage          : _get_switch_model <switch_id>
# 
#*******************************************************************************
proc _get_switch_model {switch_id} {
	global _ntgr_swList
	return [keylget _ntgr_swList($switch_id) SWITCH_MODEL]
}


#*******************************************************************************
#
#  Function Name	: _get_switch_ip_addr
#
#  Description    : This function is called to get the switch IP address from
#			  the global ntgr_swList
#         
#  Usage          : _get_switch_ip_addr <switch_id>
# 
#*******************************************************************************
proc _get_switch_ip_addr {switch_id} {
	global _ntgr_swList
	return [keylget _ntgr_swList($switch_id) SWITCH_IP_ADDR]
}

#*******************************************************************************
#
#  Function Name	: _get_switch_image
#
#  Description    : This function is called to get the switch version from
#			  the global ntgr_swList
#         
#  Usage          : _get_switch_type <switch_id>
# 
#*******************************************************************************
proc _get_switch_image {switch_id} {
	global _ntgr_swList
	return [keylget _ntgr_swList($switch_id) SWITCH_IMAGE_VERSION]
}

#*******************************************************************************
#
#  Function Name	: _get_switch_type
#
#  Description    : This function is called to get the switch Type from
#			  the global ntgr_swList
#         
#  Usage          : _get_switch_type <switch_id>
# 
#*******************************************************************************
proc _get_switch_type {switch_id} {
	global _ntgr_swList
	return [keylget _ntgr_swList($switch_id) SWITCH_TYPE]
}

#*******************************************************************************
#
#  Function Name	: _get_switch_handle
#
#  Description    : This function is called to get the switch handle from
#			  the global ntgr_swList
#         
#  Usage          : _get_switch_handle <switch_id>
# 
#*******************************************************************************
proc _get_switch_handle {switch_id} {
	global _ntgr_swList
	return [keylget _ntgr_swList($switch_id) SWITCH_HANDLE]
}

#*******************************************************************************
#
#  Function Name	: _get_switch_name
#
#  Description    : This function is called to get the switch name from
#                   the global _ntgr_swList according the given handle
#         
#  Usage          : _get_switch_name <switch_handle>
# 
#*******************************************************************************
proc _get_switch_name {switch_handle} {
    global _ntgr_swList
    for_array_keys switch_index _ntgr_swList {
        if {[keylget _ntgr_swList($switch_index) SWITCH_HANDLE] == $switch_handle} {
            return [keylget _ntgr_swList($switch_id) SWITCH_NAME]
        }
    }
}

#*******************************************************************************
#
#  Function Name	: _get_switch_project_handle
#
#  Description    : This function is called to get the switch project handle from
#			  the global ntgr_swList
#         
#  Usage          : _get_switch_project_handle <switch_id>
# 
#*******************************************************************************
proc _get_switch_project_handle {switch_id} {
	global _ntgr_swList
	return [keylget _ntgr_swList($switch_id) SWITCH_PROJECT_HANDLE]
}

#*******************************************************************************
#
#  Function Name	: _set_switch_handle
#
#  Description    : This function is called to set the switch handle in the 
#			  global ntgr_swList
#         
#  Usage          : _set_switch_handle <switch_id> <handle_id>
# 
#*******************************************************************************
proc _set_switch_handle {switch_id handle_id} {
	global _ntgr_swList
	keylset _ntgr_swList($switch_id) SWITCH_HANDLE $handle_id
}

#*******************************************************************************
#
#  Function Name	: _set_switch_image
#
#  Description    : This function is called to set the switch image version in the 
#			  the global ntgr_swList
#         
#  Usage          : _set_switch_image <switch_id> <image_name>
# 
#*******************************************************************************
proc _set_switch_image {switch_id image_name} {
	global _ntgr_swList
	keylset _ntgr_swList($switch_id) SWITCH_IMAGE_VERSION $image_name
}

#*******************************************************************************
#
#  Function Name	: _get_switch_max_port
#
#  Description    : This function is used to get the switch's port number
#         
#  Usage          : _get_switch_max_port <switch_name>
# 
#*******************************************************************************
proc _get_switch_max_port {switch_name} {
	global _ntgr_swList
	return [keylget _ntgr_swList($switch_name) SWITCH_MAX_PORTS]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetSwitchPrompt
#
#  Description    : This function is used to get the prompt to be configured.
#         
#  Usage          : _ntgrGetSwitchPrompt <switch_name>
# 
#*******************************************************************************
proc _ntgrGetSwitchPrompt {switch_name} {
	global _ntgr_swList
	return [keylget _ntgr_swList($switch_name) SW_PROMPT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetSwitchMngrIP
#
#  Description    : This function is used to get the management ip to be configured.
#         
#  Usage          : _ntgrGetSwitchMngrIP <switch_name>
# 
#*******************************************************************************
proc _ntgrGetSwitchMngrIP {switch_name} {
	global _ntgr_swList
	return [keylget _ntgr_swList($switch_name) SW_MNGR_IP_ADDR]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetSwitchMngrVlan
#
#  Description    : This function is used to get the management vlan to be configured.
#         
#  Usage          : _ntgrGetSwitchMngrVlan <switch_name>
# 
#*******************************************************************************
proc _ntgrGetSwitchMngrVlan {switch_name} {
	global _ntgr_swList
	set vlan 1
	keylget _ntgr_swList($switch_name) SW_MNGR_VLAN vlan
	return $vlan
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetSwitchMngrGW
#
#  Description    : This function is used to get the management gateway to be configured.
#         
#  Usage          : _ntgrGetSwitchMngrGW <switch_name>
# 
#*******************************************************************************
proc _ntgrGetSwitchMngrGW {switch_name} {
	global _ntgr_swList
	set gw ""
	keylget _ntgr_swList($switch_name) SW_MNGR_GW gw
	return $gw
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetSwitchMngrIPMask
#
#  Description    : This function is used to the management ip mask to be configured.
#         
#  Usage          : _ntgrGetSwitchMngrIPMask <switch_name>
# 
#*******************************************************************************
proc _ntgrGetSwitchMngrIPMask {switch_name} {
	global _ntgr_swList
	return [keylget _ntgr_swList($switch_name) SW_MNGR_IP_MASK]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTSPort
#
#  Description    : This function is used to get the terminal server's telnet 
#                   port number.
#         
#  Usage          : _ntgrGetTSPort <switch_name>
# 
#*******************************************************************************
proc _ntgrGetTSPort {switch_name} {
	global _ntgr_swList
	return [keylget _ntgr_swList($switch_name) SWITCH_PORT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrIsUsingTS
#
#  Description    : This function is used to check whether using terminal server
#                   or not. Use this function during disconnect from switch.
#         
#  Usage          : _ntgrIsUsingTS <cnn_id>
# 
#*******************************************************************************
proc _ntgrIsUsingTS {cnn_id} {
    global _ntgr_swList
    for_array_keys switch_name _ntgr_swList {
        if { [_get_switch_handle $switch_name] == $cnn_id } {
            set ts_port [ _ntgrGetTSPort $switch_name ]
            if { $ts_port == -1 } {
                return 0
            } else {
                return 1
            }
            break;
        }
    }
}

#*******************************************************************************
#
#  Function Name	: _set_switch_project_handle
#
#  Description    : This function is called to set the switch project handle in the 
#			  global ntgr_swList
#         
#  Usage          : _set_switch_project_handle <switch_id> <handle_id>
# 
#*******************************************************************************
proc _set_switch_project_handle {switch_id handle_id} {
	global _ntgr_swList
	keylset _ntgr_swList($switch_id) SWITCH_PROJECT_HANDLE $handle_id
}

#*******************************************************************************
#
#  Function Name	: _get_tftp_server_ip_addr
#
#  Description    : This function is called to get the TFTP server IP address from
#			  the global ntgr_tftpList
#         
#  Usage          : _get_tftp_server_ip_addr <switch_id>
# 
#*******************************************************************************
proc _get_tftp_server_ip_addr {switch_id} {
	global ntgr_tftpList
	return [keylget ntgr_tftpList($switch_id) TFTP_SERVER_IP_ADDR]
}

#*******************************************************************************
#
#  Function Name	: _get_tftp_image_name
#
#  Description    : This function is called to get the image name from
#			  the global ntgr_tftpList
#         
#  Usage          : _get_tftp_image_name <switch_id>
# 
#*******************************************************************************
proc _get_tftp_image_name {switch_id} {
	global ntgr_tftpList
	return [keylget ntgr_tftpList($switch_id) IMAGE_NAME]
}

#*******************************************************************************
#
#  Function Name	: _get_tftp_transfer_type
#
#  Description    : This function is called to get the transfer type from
#			  the global ntgr_tftpList
#         
#  Usage          : _get_tftp_transfer_type <switch_id>
# 
#*******************************************************************************
proc _get_tftp_transfer_type {switch_id} {
	global ntgr_tftpList
	return [keylget ntgr_tftpList($switch_id) TRANSFER_TYPE]
}

#*******************************************************************************
#
#  Function Name	: _get_tftp_connect_type
#
#  Description    : This function is called to get the connect type from
#			  the global ntgr_tftpList
#         
#  Usage          : _get_tftp_connect_type <switch_id>
# 
#*******************************************************************************
proc _get_tftp_connect_type {switch_id} {
	global ntgr_tftpList
	return [keylget ntgr_tftpList($switch_id) CONNECT_TYPE]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetProductSwitchList
#
#  Description    : This function get the switch list from global ntgr_productList
#         
#  Usage          : _ntgrGetProductSwitchList
# 
#*******************************************************************************
proc _ntgrGetProductSwitchList {} {
	global ntgr_productList
	return [keylget ntgr_productList SWITCH_LIST]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetProductTesterList
#
#  Description    : This function get the switch list from global ntgr_productList
#         
#  Usage          : _ntgrGetProductTesterList
# 
#*******************************************************************************
proc _ntgrGetProductTesterList {} {
	global ntgr_productList
	return [keylget ntgr_productList TESTER_LIST]
}

#*******************************************************************************
#
#  Function Name  : _ntgrRand
#
#  Description    : This function is used to get a random value between $min
#                   $max.
#
#  Usage          : _ntgrRand <min max>
#
#*******************************************************************************
proc _ntgrRand {min max} {
    set r [expr rand()*100000000]
    return [expr int($r) % $min + $max - $min]
}
#*******************************************************************************
#
#  Function Name	: _ntgrGetJumboFrameListCount
#
#  Description    : This function get the count of interfaces-mtu pair
#         
#  Usage          : _ntgrGetJumboFrameListCount <switch>
# 
#*******************************************************************************
proc _ntgrGetJuboFrameListCount {switch} {
	global ntgr_jumboFrameList
	return [llength $ntgr_jumboFrameList($switch)]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetJumboFrameInterface
#
#  Description    : This function get the interface
#         
#  Usage          : _ntgrGetJumboFrameInterface <switch> <id>
# 
#*******************************************************************************
proc _ntgrGetJuboFrameInterface {switch id} {
	global ntgr_jumboFrameList
	return [lindex [lindex $ntgr_jumboFrameList($switch) $id] 0]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetJumboFrameSize
#
#  Description    : This function get the frame size
#         
#  Usage          : _ntgrGetJumboFrameSize <switch> <id>
# 
#*******************************************************************************
proc _ntgrGetJumboFrameSize {switch id} {
	global ntgr_jumboFrameList
	return [lindex [lindex $ntgr_jumboFrameList($switch) $id] 1]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTftpServer
#
#  Description    : This function get the TFTP Server IP Addresss
#         
#  Usage          : _ntgrGetTftpServer 
# 
#*******************************************************************************
proc _ntgrGetTftpServer {} {
	global tftpServer
	return $tftpServer
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetUploadLogFileName 
#
#  Description    : This function get file name in which Log file has to be saved.
#         
#  Usage          : _ntgrGetUploadLogFileName <switch> 
# 
#*******************************************************************************
proc _ntgrGetUploadLogFileName { switch } {
	global ntgrUploadLogFile
	return [keylget ntgrUploadLogFile($switch) FILE_NAME]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetUploadStartConfigFileName 
#
#  Description    : This function get file name in which Startup Config file has 
#			   to be saved.
#         
#  Usage          : _ntgrGetUploadStartConfigFileName <switch> 
# 
#*******************************************************************************
proc _ntgrGetUploadStartConfigFileName { switch } {
	global ntgrUploadStartUpFile
	return [keylget ntgrUploadStartUpFile($switch) FILE_NAME]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetDownloadStartConfigFileName 
#
#  Description    : This function get file name of the  Startup Config file has 
#			   to be downloaded from the TFTP server.
#         
#  Usage          : _ntgrGetDownloadStartConfigFileName <switch> 
# 
#*******************************************************************************
proc _ntgrGetDownloadStartConfigFileName { switch } {
	global ntgrDownloadStartUpFile
	return [keylget ntgrDownloadStartUpFile($switch) FILE_NAME]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetPortEthernetStatistics
#
#  Description    : This function get port statistics according input pattern.
#         
#  Usage          : _ntgrGetPortEthernetStatistics <switch port pattern> 
# 
#*******************************************************************************
proc _ntgrGetPortEthernetStatistics {switch port pattern} {
  set match [string first "lag" $port]
  if {$match != -1} {
      set start [expr $match + 3]
      set lagid [string range $port $start end]
      set lagid [string trimright $lagid "\}"]
      set lagid [string trim $lagid]
      
      set modename [_get_switch_model $switch]
      if {[regexp -nocase {S$|M5300|XCM.*|M4300} $modename]} {
          set prefix "0/3/"
      } else {
          set prefix "3/"
      }
      set port $prefix$lagid
  }
  set cmds "show interface ethernet $port"
  set retStr ""
  CALCheckExpect $switch $cmds retStr
  set tmpList [split $retStr \n]
  set stat 0
  foreach iList $tmpList {
     if {[regexp -nocase -- $pattern $iList]} {
        set stat [lindex [eval list $iList] end]
        Netgear_log_file "lib-common-utils.tcl" "Get Portstatistic of port $port $pattern - $stat"
        break
      } 
   }
  return $stat
}




#*******************************************************************************
#
#  Function Name	: _ntgrMacIncr
#
#  Description    : This function get mac address by increase sum.
#         
#  Usage          : _ntgrMacIncr <macStr count> 
# 
#*******************************************************************************
proc _ntgrMacIncr {macStr count} {
    set macStr [string trim $macStr]
    regsub -all {[\ \-:]} $macStr {} mac_tmp
    set mac_tmp "0000$mac_tmp"
 
    set mac1 [binary format H16 $mac_tmp]
    binary scan $mac1 W mac2
    set mac2 [expr $mac2 + $count]
 
    set mac1 [binary format W $mac2]
    binary scan $mac1 H2H2H2H2H2H2H2H2 m0 m1 m2 m3 m4 m5 m6 m7
    set mac_tmp "$m2 $m3 $m4 $m5 $m6 $m7"
 
    return $mac_tmp
}


