####################################################################################
#  File Name   : lib-stk-support.tcl
#
#  Description :
#        This file includes common functions used for stacking.
#
#  History     :
#        Date            Programmer         Description
#        --------------------------------------------------------------------------
#        Aug 15, 2006    Scott Zhang        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrStkGetUnitID
#
#  Description    : This function is used to get the unit ID of a stack.
#
#  Usage          : _ntgrStkGetUnitID <switch_name unit_index>
#
#*******************************************************************************
proc _ntgrStkGetUnitID {switch_name unit_index} {
    global ntgr_unitList_$switch_name    
    return [keylget ntgr_unitList_${switch_name}($unit_index) UNIT_ID]
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkGetUnitIP
#
#  Description    : This function is used to get ip address of the terminal
#                   server which the unit connected to.
#
#  Usage          : _ntgrStkGetUnitIP <switch_name unit_index>
#
#*******************************************************************************
proc _ntgrStkGetUnitIP {switch_name unit_index} {
    global ntgr_unitList_$switch_name    
    return [keylget ntgr_unitList_${switch_name}($unit_index) UNIT_TS_IP]
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkGetUnitPort
#
#  Description    : This function is used to get server port of the terminal
#                   server which the unit connected to.
#
#  Usage          : _ntgrStkGetUnitPort <switch_name unit_index>
#
#*******************************************************************************
proc _ntgrStkGetUnitPort {switch_name unit_index} {
    global ntgr_unitList_$switch_name    
    return [keylget ntgr_unitList_${switch_name}($unit_index) UNIT_TS_PORT]
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkGetUnitPriority
#
#  Description    : This function is used to get the unit priority of a stack.
#
#  Usage          : _ntgrStkGetUnitPriority <switch_name unit_index>
#
#*******************************************************************************
proc _ntgrStkGetUnitPriority {switch_name unit_index} {
    global ntgr_unitList_$switch_name    
    return [keylget ntgr_unitList_${switch_name}($unit_index) UNIT_PRIORITY]
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkGetUnitRoll
#
#  Description    : This function is used to get the unit roll in a stack.
#
#  Usage          : _ntgrStkGetUnitRoll <switch_name unit_index>
#
#*******************************************************************************
proc _ntgrStkGetUnitRoll {switch_name unit_index} {
    global ntgr_unitList_$switch_name    
    return [keylget ntgr_unitList_${switch_name}($unit_index) UNIT_ROLL]
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkGetUnitModel
#
#  Description    : This function is used to get the unit model in a stack.
#
#  Usage          : _ntgrStkGetUnitModel <switch_name unit_index>
#
#*******************************************************************************
proc _ntgrStkGetUnitModel {switch_name unit_index} {
    global ntgr_unitList_$switch_name    
    return [keylget ntgr_unitList_${switch_name}($unit_index) UNIT_MODEL]
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkGetUnitStackPortList
#
#  Description    : This function is used to get the unit stack port list.
#
#  Usage          : _ntgrStkGetUnitStackPortList <switch_name unit_index>
#
#*******************************************************************************
proc _ntgrStkGetUnitStackPortList {switch_name unit_index} {
    global ntgr_unitList_$switch_name    
    return [keylget ntgr_unitList_${switch_name}($unit_index) UNIT_STACK_PORT_LIST]
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkGetMasterUnitID
#
#  Description    : This function is used to get the unit ID of a the master.
#
#  Usage          : _ntgrStkGetMasterUnitID <switch_name>
#
#*******************************************************************************
proc _ntgrStkGetMasterUnitID {switch_name} {
    global ntgr_unitList_$switch_name
    for_array_keys unit_index ntgr_unitList_$switch_name {
        set unit_id         [_ntgrStkGetUnitID $switch_name $unit_index]
        set unit_roll       [_ntgrStkGetUnitRoll $switch_name $unit_index]
        if { $unit_roll == "master" } {
            return $unit_id
        }
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrStkExchangeUnitInfoWhenMoveMgrmt
#
#  Description    : This function is used to exchange unit info(for example, 
#                   access way) while move the primary management unit, because
#                   after finishing the moving operation, we need to configure
#                   the switch on another unit.
#
#  Usage          : _ntgrStkExchangeUnitInfoWhenMoveMgrmt <switch_name 
#                   master_id new_master_id>
#
#*******************************************************************************
proc _ntgrStkExchangeUnitInfoWhenMoveMgrmt {switch_name master_id new_master_id} {
    global _ntgr_swList
    global ntgr_unitList_$switch_name
    
    set master_ip         [_get_switch_ip_addr $switch_name]
    set master_port       [_ntgrGetTSPort $switch_name]
    
    set master_index        -1
    set new_master_index    -1
    
    for_array_keys unit_index ntgr_unitList_$switch_name {
        set unit_id         [_ntgrStkGetUnitID $switch_name $unit_index]
        if { $unit_id == $master_id } {
            set master_index $unit_index
        } elseif { $unit_id == $new_master_id } {
            set new_master_index $unit_index
        }
        if { $master_index != -1 && $new_master_index != -1} break;
    }
    set master_priority     [_ntgrStkGetUnitPriority $switch_name $master_index]
    set master_model        [_ntgrStkGetUnitModel $switch_name $master_index]
    set master_portlist     [_ntgrStkGetUnitStackPortList $switch_name $master_index]

    set new_master_ip           [_ntgrStkGetUnitIP $switch_name $new_master_index]
    set new_master_port         [_ntgrStkGetUnitPort $switch_name $new_master_index]
    set new_master_priority     [_ntgrStkGetUnitPriority $switch_name $new_master_index]
    set new_master_model        [_ntgrStkGetUnitModel $switch_name $new_master_index]
    set new_master_portlist     [_ntgrStkGetUnitStackPortList $switch_name $new_master_index]

    keylset ntgr_unitList_${switch_name}($master_index) UNIT_ROLL            "slave"
    keylset ntgr_unitList_${switch_name}($master_index) UNIT_TS_IP           $master_ip
    keylset ntgr_unitList_${switch_name}($master_index) UNIT_TS_PORT         $master_port
    keylset ntgr_unitList_${switch_name}($master_index) UNIT_MODEL           $master_model
    keylset ntgr_unitList_${switch_name}($master_index) UNIT_PRIORITY        $master_priority
    keylset ntgr_unitList_${switch_name}($master_index) UNIT_STACK_PORT_LIST $master_portlist

    keylset ntgr_unitList_${switch_name}($new_master_index) UNIT_ROLL            "master"
    keylset ntgr_unitList_${switch_name}($new_master_index) UNIT_TS_IP           $new_master_ip
    keylset ntgr_unitList_${switch_name}($new_master_index) UNIT_TS_PORT         $new_master_port
    keylset ntgr_unitList_${switch_name}($new_master_index) UNIT_MODEL           $new_master_model
    keylset ntgr_unitList_${switch_name}($new_master_index) UNIT_PRIORITY        $new_master_priority
    keylset ntgr_unitList_${switch_name}($new_master_index) UNIT_STACK_PORT_LIST $new_master_portlist

    # This is important for switch access next time
    keylset _ntgr_swList($switch_name) SWITCH_IP_ADDR $new_master_ip
    keylset _ntgr_swList($switch_name) SWITCH_PORT $new_master_port
}

