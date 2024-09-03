#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-ospfv3.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for OSPFv3 configuration.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        14/02/13     jim xie           Created
#
#
################################################################################

#1*******************************************************************************
#
#  Function Name	: CALOspfv3Enable
#
#  Description    : This function is an API used to enable ospfv3 on global
#         
#  Usage          : CALOspfv3Enable <switch_name>
# 
#*******************************************************************************
proc CALOspfv3Enable { switch_name } {
	   
    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrOspfv3Enable $switch_name
        }
        cisco {
            puts "TODO\n"
        } 
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        } 
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }
}

#2*******************************************************************************
#
#  Function Name	: CALOspfv3Disable
#
#  Description    : This function is an API used to disable ospfv3 on global
#         
#  Usage          : CALOspfv3Disable <switch_name>
# 
#*******************************************************************************
proc CALOspfv3Disable { switch_name } {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrOspfv3Disable $switch_name
        }
        cisco {
            puts "TODO\n"
        } 
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        } 
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }

}

#3*******************************************************************************
#
#  Function Name	: CALConfigOspfv3RouterID
#
#  Description    : This function is an API used to configure router id for ospfv3
#         
#  Usage          : CALConfigOspfv3RouterID <switch_name> <router_id>
# 
#*******************************************************************************
proc CALConfigOspfv3RouterID { switch_name router_id} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrConfigureOspfv3RouterID $switch_name $router_id
        }
        cisco {
            puts "TODO\n"
        } 
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        } 
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#4*******************************************************************************
#
#  Function Name	: CALConfigOspfv3InterfaceEnable
#
#  Description    : This function is called to enable ospfv3 admin mode on interface
#         
#  Usage          : CALConfigOspfv3InterfaceEnable <switch_name> <if_list>
# 
#*******************************************************************************
proc CALConfigOspfv3InterfaceEnable { switch_name if_list} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrOspfv3InterfaceEnable $switch_name $if_list
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#5*******************************************************************************
#
#  Function Name	: CALConfigOspfv3InterfaceDisable
#
#  Description    : This function is called to disable ospfv3 admin mode on interface
#         
#  Usage          : CALConfigOspfv3InterfaceDisable <switch_name> <if_list>
# 
#*******************************************************************************
proc CALConfigOspfv3InterfaceDisable { switch_name if_list} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrOspfv3InterfaceDisable $switch_name $if_list
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#6*******************************************************************************
#  Function Name	: CALConfigOspfv3Redistribute
#
#  Description    	: This function is called to configure Redistribute for OSPFv3 on switch
#         
#  Usage          	: CALConfigOspfv3Redistribute <switch_name> <source_type> <metric_value> <metric_type> <tag>
#
#  Params           :
#                     source_type        connected/static 
#                     metric_value       <0-16777214>
#                     metric_type        1/2
#                     tag                <0-4294967295>
#
#*******************************************************************************
proc CALConfigOspfv3Redistribute { switch_name source_type metric_value metric_type tag} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrConfigureOspfv3Redistribute $switch_name $source_type $metric_value $metric_type $tag
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#7*******************************************************************************
#  Function Name	: CALResetOspfv3Redistribute
#
#  Description    	: This function is called to reset Redistribute for OSPFv3 on switch
#         
#  Usage          	: CALResetOspfv3Redistribute <switch_name> <source_type>
#
#  Params           :
#                     source_type        connected/static 
#
#*******************************************************************************
proc CALResetOspfv3Redistribute { switch_name source_type} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrResetOspfv3Redistribute $switch_name $source_type
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#8*******************************************************************************
#
#  Function Name  : CALCheckOSPFv3Neighbor
#
#  Description    : This function is used to get the Neighbor info of OSPFv3
#
#  Usage          : CALCheckOSPFv3Neighbor <switch_id> <interface_id> <router_id> <state>
#
#*******************************************************************************
proc CALCheckOSPFv3Neighbor { switch_name interface_id router_id state} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrCheckOSPFv3Neighbor $switch_name $interface_id $router_id $state
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#9*******************************************************************************
#  Function Name	: CALEnableOspfv3Advertise
#
#  Description    	: This function is called to configure Advertise for OSPFv3 on switch
#         
#  Usage          	: CALEnableOspfv3Advertise <switch_name> <metric_value> <metric_type>
#
#  Params           :
#                     metric_value       <0-16777214>
#                     metric_type        1/2
#
#*******************************************************************************
proc CALEnableOspfv3Advertise { switch_name metric_value metric_type} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrEnableOspfv3Advertise $switch_name $metric_value $metric_type
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#10*******************************************************************************
#  Function Name	: CALDisableOspfv3Advertise
#
#  Description    	: This function is called to reset Advertise for OSPFv3 on switch
#         
#  Usage          	: CALDisableOspfv3Advertise <switch_name>
#
#  Params           :
#
#*******************************************************************************
proc CALDisableOspfv3Advertise { switch_name} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrDisableOspfv3Advertise $switch_name
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#11*******************************************************************************
#  Function Name	: CALConfigureOspfv3AreaRange
#
#  Description    	: This function is called to configure area range for OSPFv3 on switch
#         
#  Usage          	: CALConfigureOspfv3AreaRange <switch_name> <area_id> <ipv6_addr> <lsdb_type> <advertise>
#
#  Params           :
#                     area_id            0/1 ...
#                     ipv6_addr          <ipv6-prefix>/<prefix-length>
#                     lsdb_type          network_summary / nssa_external
#                     advertise          true / false
#
#*******************************************************************************
proc CALConfigureOspfv3AreaRange { switch_name area_id ipv6_addr lsdb_type advertise} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrConfigureOspfv3AreaRange $switch_name $area_id $ipv6_addr $lsdb_type $advertise
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#12*******************************************************************************
#  Function Name	: CALDeleteOspfv3AreaRange
#
#  Description    	: This function is called to delete area range for OSPFv3 on switch
#         
#  Usage          	: CALDeleteOspfv3AreaRange <switch_name> <area_id> <ipv6_addr> <lsdb_type>
#
#  Params           :
#                     area_id            0/1 ...
#                     ipv6_addr          <ipv6-prefix>/<prefix-length>
#                     lsdb_type          network_summary / nssa_external
#
#*******************************************************************************
proc CALDeleteOspfv3AreaRange { switch_name area_id ipv6_addr lsdb_type} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrDeleteOspfv3AreaRange $switch_name $area_id $ipv6_addr $lsdb_type
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#13*******************************************************************************
#  Function Name	: CALConfigureOspfv3InterfaceAreaID
#
#  Description    	: This function is called to configure ospfv3 area id on interface
#         
#  Usage          	: CALConfigureOspfv3InterfaceAreaID <switch_name> <if_list> <area_id>
#
#
#*******************************************************************************
proc CALConfigureOspfv3InterfaceAreaID { switch_name if_list area_id} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrConfigureOspfv3InterfaceAreaID $switch_name $if_list $area_id
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#14*******************************************************************************
#
#  Function Name  : CALCheckOSPFv3LinkStateDatabase
#
#  Description    : This function is used to get the link state database of OSPFv3
#
#  Params           :
#                     database_type       external / link
#                     ls_type             AS-External-LSA / Link-LSA
#
#  Usage          : CALCheckOSPFv3LinkStateDatabase <switch_name> <database_type> <ls_type>
#
#*******************************************************************************
proc CALCheckOSPFv3LinkStateDatabase { switch_name database_type ls_type} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrCheckOSPFv3LinkStateDatabase $switch_name $database_type $ls_type
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#15*******************************************************************************
#  Function Name	: CALSetOspfv3ExitOverflowInterval
#
#  Description    	: This function is called to set exit overflow interval for OSPFv3 on switch
#         
#  Usage          	: CALSetOspfv3ExitOverflowInterval <switch_name> <inter>
#
#  Params           :
#                     inter            (0 to 2147483647)
#
#*******************************************************************************
proc CALSetOspfv3ExitOverflowInterval { switch_name inter} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrSetOspfv3ExitOverflowInterval $switch_name $inter
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#16*******************************************************************************
#  Function Name	: CALResetOspfv3ExitOverflowInterval
#
#  Description    	: This function is called to reset exit overflow interval for OSPFv3 on switch
#         
#  Usage          	: CALResetOspfv3ExitOverflowInterval <switch_name>
#
#  Params           :
#
#*******************************************************************************
proc CALResetOspfv3ExitOverflowInterval { switch_name} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrResetOspfv3ExitOverflowInterval $switch_name
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#17*******************************************************************************
#  Function Name	: CALSetOspfv3ExternalLSDBLimit
#
#  Description    	: This function is called to set External LSDB Limit for OSPFv3 on switch
#         
#  Usage          	: CALSetOspfv3ExternalLSDBLimit <switch_name> <limit_cnt>
#
#  Params           :
#                     limit_cnt            (-1(No Limit) to 2147483647)
#
#*******************************************************************************
proc CALSetOspfv3ExternalLSDBLimit { switch_name limit_cnt} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrSetOspfv3ExternalLSDBLimit $switch_name $limit_cnt
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#18*******************************************************************************
#  Function Name	: CALResetOspfv3ExternalLSDBLimit
#
#  Description    	: This function is called to reset External LSDB Limit for OSPFv3 on switch
#         
#  Usage          	: CALResetOspfv3ExternalLSDBLimit <switch_name>
#
#  Params           :
#
#*******************************************************************************
proc CALResetOspfv3ExternalLSDBLimit { switch_name} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrResetOspfv3ExternalLSDBLimit $switch_name
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#19*******************************************************************************
#  Function Name	: CALOspfv3InterfaceEnableMTUIgnore
#
#  Description    	: This function is called to enable ospfv3 mtu ignore on interface
#         
#  Usage          	: CALOspfv3InterfaceEnableMTUIgnore <switch_name> <if_list>
#
#
#*******************************************************************************
proc CALOspfv3InterfaceEnableMTUIgnore { switch_name if_list} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrOspfv3InterfaceEnableMTUIgnore $switch_name $if_list
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#20*******************************************************************************
#  Function Name	: CALOspfv3InterfaceDisableMTUIgnore
#
#  Description    	: This function is called to disable ospfv3 mtu ignore on interface
#         
#  Usage          	: CALOspfv3InterfaceDisableMTUIgnore <switch_name> <if_list>
#
#
#*******************************************************************************
proc CALOspfv3InterfaceDisableMTUIgnore { switch_name if_list} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrOspfv3InterfaceDisableMTUIgnore $switch_name $if_list
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#21*******************************************************************************
#  Function Name	: CALSetOspfv3DistanceValue
#
#  Description    	: This function is called to set distance value for OSPFv3 on switch
#         
#  Usage          	: CALSetOspfv3DistanceValue <switch_name> <distance_type> <distance_value>
#
#  Params           :
#                      distance_type   external/inter-area/intra-area
#                      distance_value  1-255
#
#*******************************************************************************
proc CALSetOspfv3DistanceValue { switch_name distance_type distance_value} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrSetOspfv3DistanceValue $switch_name $distance_type $distance_value
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#22*******************************************************************************
#  Function Name	: CALEnableOspfv3InterfacePassiveMode
#
#  Description    	: This function is called to enable Passive Mode on interface
#         
#  Usage          	: CALEnableOspfv3InterfacePassiveMode <switch_name> <if_list>
#
#
#*******************************************************************************
proc CALEnableOspfv3InterfacePassiveMode { switch_name if_list} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrEnableOspfv3InterfacePassiveMode $switch_name $if_list
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#23*******************************************************************************
#  Function Name	: CALDisableOspfv3InterfacePassiveMode
#
#  Description    	: This function is called to disable Passive Mode on interface
#         
#  Usage          	: CALDisableOspfv3InterfacePassiveMode <switch_name> <if_list>
#
#
#*******************************************************************************
proc CALDisableOspfv3InterfacePassiveMode { switch_name if_list} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrDisableOspfv3InterfacePassiveMode $switch_name $if_list
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#24*******************************************************************************
#
#  Function Name	: CALSetOspfv3InterfaceAreaID
#
#  Description    : This function is called to set ospfv3 area id  on interface
#         
#  Usage          : CALSetOspfv3InterfaceAreaID <switch_name> <if_list> <area_id>
# 
#*******************************************************************************
proc CALSetOspfv3InterfaceAreaID { switch_name if_list area_id} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrSetOspfv3InterfaceAreaID $switch_name $if_list $area_id
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#25*******************************************************************************
#  Function Name	: CALConfigureOspfv3StubAreaID
#
#  Description    	: This function is called to configure stub area id for OSPFv3 on switch
#         
#  Usage          	: CALConfigureOspfv3StubAreaID <switch_name> <area_id> <summery>
#
#  Paramer          :
#                    area_id 
#                    summery  0(disable)/1(enable)
#
#*******************************************************************************
proc CALConfigureOspfv3StubAreaID { switch_name area_id summery} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrConfigureOspfv3StubAreaID $switch_name $area_id $summery
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#26*******************************************************************************
#  Function Name	: CALDeleteOspfv3StubAreaID
#
#  Description    	: This function is called to delete stub area id for OSPFv3 on switch
#         
#  Usage          	: CALDeleteOspfv3StubAreaID <switch_name> <area_id> <summery>
#
#  Paramer          :
#                    area_id 
#
#*******************************************************************************
proc CALDeleteOspfv3StubAreaID { switch_name area_id summery} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrDeleteOspfv3StubAreaID $switch_name $area_id $summery
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#27*******************************************************************************
#
#  Function Name  : CALCheckOSPFv3StubAreaSummary
#
#  Description    : This function is used to check the value of Stub Area Summary
#
#  Paramas        :
#                 area_id : 0.0.0.1
#                 expect_value : Enable or Disable
#
#  Usage          : CALCheckOSPFv3StubAreaSummary <switch_id> <area_id> <expect_value>
#
#*******************************************************************************
proc CALCheckOSPFv3StubAreaSummary { switch_name area_id expect_value} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrCheckOSPFv3StubAreaSummary $switch_name $area_id $expect_value
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#28*******************************************************************************
#  Function Name	: CALSetOspfv3InterfacePriority
#
#  Description    	: This function is called to set ospfv3 priority on interface
#         
#  Usage          	: CALSetOspfv3InterfacePriority <switch_name> <if_list> <priority>
#
#
#*******************************************************************************
proc CALSetOspfv3InterfacePriority { switch_name if_list priority} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrSetOspfv3InterfacePriority $switch_name $if_list $priority
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#29*******************************************************************************
#  Function Name	: CALClearIPv6Ospfv3
#
#  Description    	: This function is called to clear ipv6  OSPFv3 on switch
#         
#  Usage          	: CALClearIPv6Ospfv3 <switch_name>
#
#
#*******************************************************************************
proc CALClearIPv6Ospfv3 { switch_name} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrClearIPv6Ospfv3 $switch_name
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#30*******************************************************************************
#
#  Function Name  : CALGetOSPFv3LSSeqNumber
#
#  Description    : This function is used to get LS Seq Number from the link state database of OSPFv3
#
#  Params           :
#                     neighbor_rid       1.1.1.1
#
#  Usage          : CALGetOSPFv3LSSeqNumber <switch_id> <neighbor_rid>
#
#*******************************************************************************
proc CALGetOSPFv3LSSeqNumber { switch_name neighbor_rid} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrGetOSPFv3LSSeqNumber $switch_name $neighbor_rid
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}


#31*******************************************************************************
#  Function Name	: CALSetOspfv3InterfaceMetricCost
#
#  Description    	: This function is called to set ospfv3 metric cost on interface
#         
#  Usage          	: CALSetOspfv3InterfaceMetricCost <switch_name> <if_list> <cost_value>
#
#  Parames          :
#                     cost_value  : 1-65535
#
#*******************************************************************************
proc CALSetOspfv3InterfaceMetricCost { switch_name if_list cost_value} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrSetOspfv3InterfaceMetricCost $switch_name $if_list $cost_value
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#32*******************************************************************************
#  Function Name	: CALAddOspfv3VirtualLink
#
#  Description    	: This function is called to add virtual-link for OSPFv3 on switch
#         
#  Usage          	: CALAddOspfv3VirtualLink <switch_name> <area_id> <nbrouter_id>
#
#  Paramer          :
#                    area_id      0.0.0.2
#                    nbrouter_id  4.4.4.4
#
#*******************************************************************************
proc CALAddOspfv3VirtualLink { switch_name area_id nbrouter_id} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrAddOspfv3VirtualLink $switch_name $area_id $nbrouter_id
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#33*******************************************************************************
#  Function Name	: CALDeleteOspfv3VirtualLink
#
#  Description    	: This function is called to delete virtual-link for OSPFv3 on switch
#         
#  Usage          	: CALDeleteOspfv3VirtualLink <switch_name> <area_id> <nbrouter_id>
#
#  Paramer          :
#                    area_id      0.0.0.2
#                    nbrouter_id  4.4.4.4
#
#*******************************************************************************
proc CALDeleteOspfv3VirtualLink { switch_name area_id nbrouter_id} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrDeleteOspfv3VirtualLink $switch_name $area_id $nbrouter_id
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#34*******************************************************************************
#
#  Function Name  : CALCheckOSPFv3VirtualLinkState
#
#  Description    : This function is used to check virtual link state of OSPFv3
#
#  Params           :
#                    area_id      0.0.0.2
#                    nbrouter_id  4.4.4.4
#                    nb_expect_state      down/up
#                    local_expect_state   down/up
#
#  Usage          : CALCheckOSPFv3VirtualLinkState <switch_name> <area_id> <nbrouter_id> <nb_expect_state> <local_expect_state>
#
#*******************************************************************************
proc CALCheckOSPFv3VirtualLinkState { switch_name area_id nbrouter_id nb_expect_state local_expect_state} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrCheckOSPFv3VirtualLinkState $switch_name $area_id $nbrouter_id $nb_expect_state $local_expect_state
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#35*******************************************************************************
#  Function Name	: CALConfigureOspfv3NSSAAreaID
#
#  Description    	: This function is called to configure nssa area id for OSPFv3 on switch
#         
#  Usage          	: CALConfigureOspfv3NSSAAreaID <switch_name> <area_id>
#
#  Paramer          :
#                    area_id 
#
#*******************************************************************************
proc CALConfigureOspfv3NSSAAreaID { switch_name area_id} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrConfigureOspfv3NSSAAreaID $switch_name $area_id
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#36*******************************************************************************
#  Function Name	: CALDeleteOspfv3NSSAAreaID
#
#  Description    	: This function is called to delete nssa area id for OSPFv3 on switch
#         
#  Usage          	: CALDeleteOspfv3NSSAAreaID <switch_name> <area_id>
#
#  Paramer          :
#                    area_id 
#
#*******************************************************************************
proc CALDeleteOspfv3NSSAAreaID { switch_name area_id} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrDeleteOspfv3NSSAAreaID $switch_name $area_id
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#37*******************************************************************************
#  Function Name	: CALConfigureOspfv3AutoCost
#
#  Description    	: This function is called to configure autocost Reference Bandwidth for OSPFv3 on switch
#         
#  Usage          	: CALConfigureOspfv3AutoCost <switch_name> <bandwidth_value>
#
#  Paramer          :
#                    bandwidth_value   <1-4294967>
#
#*******************************************************************************
proc CALConfigureOspfv3AutoCost { switch_name bandwidth_value} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrConfigureOspfv3AutoCost $switch_name $bandwidth_value
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#38*******************************************************************************
#  Function Name	: CALResetOspfv3AutoCost
#
#  Description    	: This function is called to reset autocost value for OSPFv3 on switch
#         
#  Usage          	: CALResetOspfv3AutoCost <switch_name>
#
#  Paramer          :
#
#*******************************************************************************
proc CALResetOspfv3AutoCost { switch_name} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrResetOspfv3AutoCost $switch_name
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#39*******************************************************************************
#
#  Function Name	: CALDelOspfv3InterfaceAreaID
#
#  Description    : This function is called to Delete ospfv3 area id  on interface
#         
#  Usage          : CALDelOspfv3InterfaceAreaID <switch_name> <if_list> <area_id> 
# 
#*******************************************************************************
proc CALDelOspfv3InterfaceAreaID { switch_name if_list area_id} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrDelOspfv3InterfaceAreaID $switch_name $if_list $area_id
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}

#*******************************************************************************
#  Function Name	: CALDelOspfv3InterfaceMetricCost
#
#  Description    	: This function is called to no ospfv3 metric cost on interface
#         
#  Usage          	: CALDelOspfv3InterfaceMetricCost <switch_name> <if_list> 
#
#  Parames          :
#                   
#
#*******************************************************************************
proc CALDelOspfv3InterfaceMetricCost { switch_name if_list} {

    set switch_vendor [_get_switch_vendor_name $switch_name]
    switch $switch_vendor {
        netgear {
            _ntgrDelOspfv3InterfaceMetricCost $switch_name $if_list
        }
        cisco {
            puts "TODO\n"
        }
        hp {
            puts "TODO\n"
        }
        3com {
            puts "TODO\n"
        }
        default {
            Netgear_log_file "cal-ntgr-ospfv3.tcl" "Switch not defined"
        }
    }  

}