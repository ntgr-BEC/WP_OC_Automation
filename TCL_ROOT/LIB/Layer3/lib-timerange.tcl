#!/bin/sh
################################################################################   
#
#  File Name		  : lib-timerange.tcl
#
#  Description      :
#         This TCL contains functions to timerange
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        21-Sep-11     Tony              Created
#
#
#
#


#*******************************************************************************
#  Function Name	: _ntgrAddTimeRange
#
#  Description    	: This function is called to add time-range on switch
#         
#  Usage          	: _ntgrAddTimeRange <switch_name> <timerange_rule_list> 
#
#
#*******************************************************************************
proc _ntgrAddTimeRange {switch_name timerange_rule_list} {
    
    set Ret ""
    Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	expect -i $connection_id -re "#"
  	_ntgrAddTimeRangewithoutLogin $timerange_rule_list $connection_id Ret
 
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
	return $Ret
}

#*******************************************************************************
#  Function Name	: _ntgrAddTimeRangewithoutLogin
#
#  Description    	: This function is called to add ACL on switch
#         
#  Usage          	: _ntgrAddTimeRangewithoutLogin <timerange_rule_list> <connection_id> <retval>
#
#
#*******************************************************************************
proc _ntgrAddTimeRangewithoutLogin {timerange_rule_list connection_id retval} {
    upvar 1 $retval Ret
    
    foreach rule_list $timerange_rule_list {
      set timerange_name [lindex $rule_list 0]
      set timerange_rule [lindex $rule_list 1]
      
      exp_send -i $connection_id "time-range $timerange_name\r"
      sleep 1
      expect -i $connection_id -re "#"

      foreach rule $timerange_rule {
          set cmd ""
          set flagStr [lindex $rule 0]
          if {[string equal $flagStr "absolute"]} {
              set keystr_1 [lindex $rule 1]
              set keystr_2 [lindex $rule 2]
              set cmd "$flagStr start $keystr_1"
              if {$keystr_2 != "default" && $keystr_2 != ""} {
                  append cmd " end $keystr_2"
              }
          } elseif {[string equal $flagStr "periodic"]} {
              set keystr_1 [lindex $rule 1]
              set keystr_2 [lindex $rule 2]
              set keystr_3 [lindex $rule 3]
              set cmd "$flagStr $keystr_1 $keystr_2 to $keystr_3"
          } else {
          
          }
          
          set cmd [string trim $cmd]

          exp_send -i $connection_id "$cmd\r"
          sleep 1
          expect -i $connection_id -re "#"
          append Ret $expect_out(buffer)
      }
      
      exp_send -i $connection_id "exit\r"
   }
   
	return $Ret
}


#*******************************************************************************
#  Function Name	: _ntgrDeleteTimeRange
#
#  Description    	: This function is called to delete time-range on switch
#         
#  Usage          	: _ntgrDeleteTimeRange <switch_name> <timerange_name_list> 
#
#
#*******************************************************************************
proc _ntgrDeleteTimeRange {switch_name timerange_name_list} {

    Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
    _ntgrDeleteTimeRangewithoutLogin $connection_id $timerange_name_list 
  
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#  Function Name	: _ntgrDeleteTimeRangewithoutLogin
#
#  Description    	: This function is called to delete time-range on switch
#         
#  Usage          	:  _ntgrDeleteTimeRangewithoutLogin <connection_id> <timerange_name_list> 
#
#
#*******************************************************************************
proc _ntgrDeleteTimeRangewithoutLogin {connection_id timerange_name_list} {
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    
    foreach timerange_name $timerange_name_list {
        exp_send -i $connection_id "no time-range $timerange_name\r"
        sleep 1
    }
    exp_send -i $connection_id "exit\r"
}





#*******************************************************************************
#
#  Function Name  : _ntgrGetTimeRangeStatus
#
#  Description    : This function is used to get time range status of a switch.
#
#  Usage          : _ntgrGetTimeRangeStatus <switch_name> <timeRangeName>
#
#*******************************************************************************
proc _ntgrGetTimeRangeStatus {switch_name timeRangeName} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show time-range $timeRangeName\r"
    exp_sleep 1
    expect -i $cnn_id -re "show time-range $timeRangeName"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase {Time Range Status\.+\s+([^\n]+)} $output match result
    set result [string trim $result]

    Netgear_log_file "lib-global-command.tcl" "get time-range status on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}