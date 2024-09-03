#!/bin/sh
################################################################################   
#
#  File Name		: lib-show-command.tcl
#
#  Description       	:
#         This TCL contains functions to execute various show command on the switch
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
#  Function Name	: _switch_show_hardware
#
#  Description    : This function is displays the command "show hardware"
#         
#  Usage          : _switch_show_hardware <connection_id>
# 
#*******************************************************************************
proc _switch_show_hardware {connection_id} {
	exp_send -i $connection_id "show hardware\r"
	expect -i $connection_id -re "Software Version......................................." {
      	set dir $expect_out(buffer)
	}

	if {[info exists dir]} {
		regsub "\r" $dir "" dir
		set files [split $dir \n]
		set files [lrange $files 1 [expr {[llength $files]}]]
		set x [llength $files]
      	set x [expr {$x-1}]
      	set version [lindex $files $x]
		set ver [lindex $version 2]
	}
	return $ver
}