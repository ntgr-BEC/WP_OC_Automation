################################################################################   
#
#  File Name		  : cal-ntgr-mac-addrtable.tcl
#
#  Description      :
#         This TCL contains functions to configure mac address-table multicast forward mode
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        26-Jun-12     Tony              Created
#
#
#
#




#*******************************************************************************
#
#  Function Name  : CALMacAddrTableSetMulticastForwardMode
#
#  Description    : This function is called to config mac address-table multicast forwarding mode 
#                   on specified switch.
#
#  Usage          : CALMacAddrTableSetMulticastForwardMode <switch_name> <vlan> <mode>
#
#*******************************************************************************
proc CALMacAddrTableSetMulticastForwardMode {switch_name vlan mode} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMacAddrTableSetMulticastForwardMode $switch_name $vlan $mode
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-mac-addrtable.tcl" "Switch not defined"
        }
    }
}
