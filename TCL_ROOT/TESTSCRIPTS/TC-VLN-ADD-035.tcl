#!/bin/sh
################################################################################   
#
#  File Name     : TC-VLN-ADD-035.tcl
#
#  Description   :
#        This testcase does the VLAN adding operation periodically.
#
#  Config File   : TC-VLN-DEL-034.cfg
#
#  History       :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 22, 2006  Scott Zhang       Created
#
################################################################################

NtgrDumpLog $NTGR_LOG_TERSE "TC-VLN-ADD-035.tcl" "***** Starting Test case TC-VLN-ADD-035.tcl for adding deleted VLAN *****"

foreach switch $ntgr_swList_AddDelVlan {
    Netgear_log_file "TC-VLN-ADD-035.tcl" "Reconfiguring all VLAN on switch $switch"
    CALCreateVlanAll $switch
}

foreach vlan_index $ntgr_vlanList_AddDel {
    NtgrDumpLog $NTGR_LOG_TERSE "TC-VLN-ADD-035.tcl" "Reconfiguring VLAN $vlan_index on all switches"
    CALCreateVlan $vlan_index
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-VLN-ADD-035.tcl" "******************** Completed Test case TC-VLN-ADD-035.tcl ********************"

#*************************  End of Test case  ****************************************************************