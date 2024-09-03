#!/bin/sh
################################################################################   
#
#  File Name     : TC-POCH-DEL-036.tcl
#
#  Description   :
#        This testcase does the LAGs removing operation periodically.
#
#  Config File   : TC-POCH-DEL-036.cfg
#
#  History       :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 22, 2006  Scott Zhang       Created
#
################################################################################

Netgear_log_file "TC-POCH-DEL-036.tcl" "***** Starting Test case TC-POCH-DEL-036.tcl for LAGs removing *****"

foreach LAG $ntgr_pcList_AddDel {
    Netgear_log_file "TC-POCH-DEL-036.tcl" "Removing port channel $LAG on switches"
    CALDeletePortChannel $LAG
}

foreach switch $ntgr_swList_AddDelPortChannel {
    Netgear_log_file "TC-POCH-DEL-036.tcl" "Removing all configured port channels on switch $switch"
    CALDeleteAllPortChannels $switch
}

Netgear_log_file "TC-POCH-DEL-036.tcl" "******************** Completed Test case TC-POCH-DEL-036.tcl ********************"
#*************************  End of Test case  ****************************************************************