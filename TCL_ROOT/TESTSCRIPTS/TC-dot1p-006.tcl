################################################################################
#
#  File Name	    	: TC-dot1p-006.tcl
#
#  Description     	:
#         This TCL tests configures dot1p and traffic class mapping.
#
#  Config file       : TC-dot1p-006.cfg 
#
#  Global Variables	: ntgrDOT1PSwitchList (defined in CFG file)
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        5/28/06     	Tissa	        	Initial
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************


Netgear_log_file "TC-STP-004.tcl" "******************** Starting Test case TC-STP-004.tcl ********************"

Netgear_log_file "TC-dot1p-006.tcl" "Started DOT1P Configuration on the Switches"

Netgear_log_file "TC-dot1p-006.tcl" "Start DOT1P to Traffic Configuration on the Switches"
foreach  swL $swListToSet  {

# 
# 1. Get the Global Traffic class mapping for PTag
#
# 
# now call the CLI Abstraction fucntion to configure
#
		CALGlobalDot1PClassMap $swL ntgrDOT1PSwitchList

	
}
Netgear_log_file "TC-dot1p-006.tcl" "Completed DOT1P to Traffic Configuration on the Switches"

Netgear_log_file "TC-dot1p-006.tcl" "Start DOT1P to Traffic Configuration per interface"

foreach  swL $swListToSet  {

# 
# 2. Then Get per Port Traffic Class mapping for PTag value
# 
# now call the CLI Abstraction fucntion to configure
#
		
	CALPortDot1PClassMap $swL ntgrDOT1PSwitchList	
}
Netgear_log_file "TC-dot1p-006.tcl" "Completed DOT1P to Traffic Configuration per interface"
Netgear_log_file "TC-dot1p-006.tcl" "Start DOT1P configuring untag priority per switch"

foreach  swL $swListToSet  {

# 
# 3. Configure Priority value of untag packets for whole switch
# 
# now call the CLI Abstraction fucntion to configure
#
		
	CALDot1PGlobalUntagPriority $swL ntgrDOT1PSwitchList	
}
Netgear_log_file "TC-dot1p-006.tcl" "Completed DOT1P configuring untag priority per switch"

Netgear_log_file "TC-dot1p-006.tcl" "Start DOT1P configuring untag priority per port"

# 
# 4. Configure Priority value of untag packet per interface
# 
# now call the CLI Abstraction fucntion to configure
#
foreach  swL $swListToSet  {		
	CALPortDot1PUntagPriority $swL ntgrDOT1PSwitchList	
}

Netgear_log_file "TC-dot1p-006.tcl" "Completed DOT1P configuring untag priority per port"

Netgear_log_file "TC-dot1p-006.tcl" "--Completed DOT1P Configuration on the Switches--"

set packet_loss 0
if {$packet_loss == 0} {
	Netgear_log_file "TC-STP-004.tcl" "******************** TEST CASE TC-STP-004 PASSED ********************"
	set NTGR_TEST_RESULT TRUE
} else {
	Netgear_log_file "TC-STP-004.tcl" "******************** TEST CASE TC-STP-004 FAILED ********************"
	Netgear_log_file "TC-STP-004.tcl" "         Failed Reason : Frame_loss = $packet_loss"
	Netgear_log_file "TC-STP-004.tcl" "*********************************************************************"
	set NTGR_TEST_RESULT FALSE
}

Netgear_log_file "TC-dot1p-006.tcl" "******************** Completed Test case TC-dot1p-006.tcl ********************"

#*************************  End of Test case  ****************************************************************