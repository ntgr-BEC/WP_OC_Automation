#!/bin/sh
################################################################################   
#
#  File Name   : TC-CLEAR-CONFIG-031.tcl
#
#  Description :
#        This testcase clear the current configuration and configure some initial
#        infomation.
#
#  History     :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 28, 2006  Scott Zhang       Created
#
################################################################################

Netgear_log_file "TC-CLEAR-CONFIG-031.tcl" "****************** Starting Test case TC-CLEAR-CONFIG-031.tcl ******************"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST {
    Netgear_log_file "TC-CLEAR-CONFIG-031.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
    CALDisableConsoleTimeout $switch_name
}

Netgear_log_file "TC-CLEAR-CONFIG-031.tcl" "******************** Completed Test case TC-CLEAR-CONFIG-031.tcl ********************"
#*************************  End of Test case  ****************************************************************