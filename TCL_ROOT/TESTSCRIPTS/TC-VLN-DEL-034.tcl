#!/bin/sh
################################################################################   
#
#  File Name     : TC-VLN-DEL-034.tcl
#
#  Description   :
#        This testcase does the VLAN removing operation periodically.
#
#  Config File   : TC-VLN-DEL-034.cfg
#
#  History       :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 22, 2006  Scott Zhang       Created
#
################################################################################

NtgrDumpLog $NTGR_LOG_TERSE "TC-VLN-DEL-034.tcl" "***** Starting Test case TC-VLN-DEL-034.tcl for VLAN removing *****"

foreach switch $ntgr_swList_AddDelVlan {
    NtgrDumpLog $NTGR_LOG_TERSE "TC-VLN-DEL-034.tcl" "Removing all VLAN configuration on switch $switch"
    CALDeleteVlanAll $switch
}

foreach vlan_index $ntgr_vlanList_AddDel {
    NtgrDumpLog $NTGR_LOG_TERSE "TC-VLN-DEL-034.tcl" "Removing VLAN $vlan_index configuration on all switches"
    CALDeleteVlan $vlan_index
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-VLN-DEL-034.tcl" "******************** Completed Test case TC-VLN-DEL-034.tcl ********************"

#*************************  End of Test case  ****************************************************************