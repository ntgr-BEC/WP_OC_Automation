################################################################################
#
#  File Name	    	: TC-Static-Routes-056.tcl
#
#  Description     	: This TCL tests configures Static Routes  on switches.
#
#  Config file       : TC-Static-Routes-056.cfg
#
#  Global Variables	: ntgr_IPList (defined in CFG file)
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#		
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-Static-Routes-056.tcl" "******************** Starting Test case TC-Static-Routes-056.tcl ********************"

Netgear_log_file "TC-Static-Routes-056.tcl" "Started Configuring Static Routes"

set switch_names [lsort -dictionary [array names ntgr_IPList ] ]
foreach switch_name $switch_names {
	NtgrDumpLog $NTGR_LOG_TERSE  "TC-Static-Routes-056.tcl" "Enable Routing in the switch $switch_name"
	CALIpRoutingConfig $switch_name
	NtgrDumpLog $NTGR_LOG_TERSE  "TC-Static-Routes-056.tcl" "Adding Routes in the switch $switch_name"
	CALIpConfigStaticRoute $switch_name
	NtgrDumpLog $NTGR_LOG_TERSE  "TC-CFGREG-014.tcl" "Completed IP Routing configuration on $switch_name"
}

Netgear_log_file "TC-Static-Routes-056.tcl" "******************** Completed Test case TC-Static-Routes-056.tcl ********************"

#*************************  End of Test case  ****************************************************************