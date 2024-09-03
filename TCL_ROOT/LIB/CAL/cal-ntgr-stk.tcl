####################################################################################
#  File Name   : cal-ntgr-stk.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for switches stacking.
#
#  History     :
#        Date          Programmer         Description
#        Aug 15,2006   Scott Zhang        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALStkSetUnitPriority
#
#  Description    : This function is called to set the unit priority of a stack.
#
#  Usage          : CALStkSetUnitPriority <switch_name {unitID 0} {priority 0}>
#
#*******************************************************************************
proc CALStkSetUnitPriority {switch_name {unitID 0} {priority 0}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkSetUnitPriority $switch_name $unitID $priority
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkRenumberUnit
#
#  Description    : This function is called to renumber a unit of the stack.
#
#  Usage          : CALStkRenumberUnit <switch_name old_id new_id>
#
#*******************************************************************************
proc CALStkRenumberUnit {switch_name old_id new_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkRenumberUnit $switch_name $old_id $new_id
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkAddMember
#
#  Description    : This function is called to add a unit to the stack.
#
#  Usage          : CALStkAddMember <switch_name unit_id unit_type>
#                 
#  Note : unit_type is the index of 'show supported switchtype'.
#
#*******************************************************************************
proc CALStkAddMember {switch_name unit_id unit_type} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkAddMember $switch_name $unit_id $unit_type
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkDelMember
#
#  Description    : This function is called to delete a unit from the stack.
#
#  Usage          : CALStkDelMember <switch_name unit_id>
#                 
#*******************************************************************************
proc CALStkDelMember {switch_name unit_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkDelMember $switch_name $unit_id
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkQosModeEnable
#
#  Description    : This function is called to enable qos mode for Front Panel
#                   Stacking on all interfaces.
#
#  Usage          : CALStkQosModeEnable <switch_name>
#                 
#*******************************************************************************
proc CALStkQosModeEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkQosModeEnable $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkQosModeDisable
#
#  Description    : This function is called to disable qos mode for Front Panel
#                   Stacking on all interfaces.
#
#  Usage          : CALStkQosModeDisable <switch_name>
#                 
#*******************************************************************************
proc CALStkQosModeDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkQosModeDisable $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkArchiveCopy
#
#  Description    : This function is called to copy the image from master to
#                   other stack member. It needs reboot to let the new image
#                   take effect on those stack members.
#
#  Usage          : CALStkArchiveCopy <switch_name {to_unit_id 0}>
#                 
#*******************************************************************************
proc CALStkArchiveCopy {switch_name {to_unit_id 0}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkArchiveCopy $switch_name $to_unit_id
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkArchiveDownload
#
#  Description    : This function is called to copy the image from a tftp server
#                   to the stack. It needs reboot to let the new image
#                   take effect on those stack members.
#
#  Usage          : CALStkArchiveDownload <switch_name tftp_path>
#                 
#*******************************************************************************
proc CALStkArchiveDownload {switch_name tftp_path} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkArchiveDownload $switch_name $tftp_path
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkMoveMgrmt
#
#  Description    : This function is called to move the primary management unit
#                   to another unit. Before moving, we assume that the current
#                   configuration should be saved first.
#
#  Usage          : CALStkMoveMgrmt <switch_name to_unit_id>
#                 
#*******************************************************************************
proc CALStkMoveMgrmt {switch_name to_unit_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkMoveMgrmt $switch_name $to_unit_id
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStackMoveMgrmt
#
#  Description    : This function is called to move the primary management unit
#                   to another unit. Before moving, we assume that the current
#                   configuration should be saved first.
#
#  Usage          : CALStackMoveMgrmt <switch_name to_unit_id>
#                 
#*******************************************************************************
proc CALStackMoveMgrmt {switch_name to_unit_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStackMoveMgrmt $switch_name $to_unit_id
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkGetMasterUnitID
#
#  Description    : This function is called to get the master unit id from global
#                   structure.
#
#  Usage          : CALStkGetMasterUnitID <switch_name>
#                 
#*******************************************************************************
proc CALStkGetMasterUnitID {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            return [_ntgrStkGetMasterUnitID $switch_name]
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkGetMasterID
#
#  Description    : This function is called to get the master unit id from switch.
#
#  Usage          : CALStkGetMasterID <switch_name>
#                 
#*******************************************************************************
proc CALStkGetMasterID {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            return [_ntgrStkGetMasterID $switch_name]
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkSetStackPortMode
#
#  Description    : This function is called to set the stack port's mode.
#
#  Usage          : CALStkSetStackPortMode <switch_name>
#
#*******************************************************************************
proc CALStkSetStackPortMode {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkSetStackPortMode $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkSetStackPortMode
#
#  Description    : This function is called to set the stack port's mode.
#
#  Usage          : CALStkSetStackPortMode <switch_name if_name mode>
#
#*******************************************************************************
proc CALStkConfigStackPortMode {switch_name if_name mode} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            Netgear_connect_switch $switch_name
            set cnn_id [_get_switch_handle $switch_name]
            _ntgrStkConfigStackPortMode $cnn_id $if_name $mode
            Netgear_disconnect_switch $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}







#*******************************************************************************
#
#  Function Name  : CALStkMoveManagement
#
#  Description    : This function is called to move the management switch to
#                   another unit of the stack.
#
#  Usage          : CALStkMoveManagement <switch_name> <master_id> <other_id>
#
#*******************************************************************************
proc CALStkMoveManagement {switch_name master_id other_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkMoveManagement $switch_name $master_id $other_id
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkNsfEnable
#
#  Description    : This function is called to enable stack nsf on specified
#                   switch.
#
#  Usage          : CALStkNsfEnable <switch_name>
#
#*******************************************************************************
proc CALStkNsfEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkNsfEnable $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkNsfDisable
#
#  Description    : This function is called to disable stack nsf on specified
#                   switch.
#
#  Usage          : CALStkNsfDisable <switch_name>
#
#*******************************************************************************
proc CALStkNsfDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkNsfDisable $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkSetStandbyUnit
#
#  Description    : This function is called to set standby unit on specified
#                   switch.
#
#  Usage          : CALStkSetStandbyUnit <switch_name> <unitid>
#
#*******************************************************************************
proc CALStkSetStandbyUnit {switch_name unitid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkSetStandbyUnit $switch_name $unitid
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkResetStandbyUnit
#
#  Description    : This function is called to reset standby unit on specified
#                   switch.
#
#  Usage          : CALStkResetStandbyUnit <switch_name>
#
#*******************************************************************************
proc CALStkResetStandbyUnit {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkResetStandbyUnit $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALStkGetSwitchNameByID
#
#  Description    : This function is called to get switch name by unit id from switch.
#
#  Usage          : CALStkGetSwitchNameByID <switch_name> <unitid>
#
#*******************************************************************************
proc CALStkGetSwitchNameByID {switch_name unitid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkGetSwitchNameByID $switch_name $unitid
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkGetSupportedSwitchtypeIDByName
#
#  Description    : This function is called to get supported switchtype id by name from switch.
#
#  Usage          : CALStkGetSupportedSwitchtypeIDByName <switch_name> <name>
#
#*******************************************************************************
proc CALStkGetSupportedSwitchtypeIDByName {switch_name name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkGetSupportedSwitchtypeIDByName $switch_name $name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}





#*******************************************************************************
#
#  Function Name  : CALStkMasterIDCheck
#
#  Description    : This function is called to checking unitid whether is master unit from switch.
#
#  Usage          : CALStkMasterIDCheck <switch_name> <unitid>
#
#*******************************************************************************
proc CALStkMasterIDCheck {switch_name unitid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkMasterIDCheck $switch_name $unitid
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkStandbyIDCheck
#
#  Description    : This function is called to checking unitid whether is standby unit from switch.
#
#  Usage          : CALStkStandbyIDCheck <switch_name> <unitid>
#
#*******************************************************************************
proc CALStkStandbyIDCheck {switch_name unitid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkStandbyIDCheck $switch_name $unitid
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkMemberIDCheck
#
#  Description    : This function is called to checking unitid whether is exist from switch.
#
#  Usage          : CALStkMemberIDCheck <switch_name> <unitid>
#
#*******************************************************************************
proc CALStkMemberIDCheck {switch_name unitid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrStkMemberIDCheck $switch_name $unitid
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}




#*******************************************************************************
#
#  Function Name  : CALGetActiveImageByUnitID
#
#  Description    : This function is used to get active image by unit id.
#
#  Usage          : CALGetActiveImageByUnitID <switch_name> <unitid>
#                 
#*******************************************************************************
proc CALGetActiveImageByUnitID {switch_name unitid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            return [_ntgrGetActiveImageByUnitID $switch_name $unitid]
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkGetSwitchVerByID
#
#  Description    : This function is called to get switch version by unit id from switch.
#
#  Usage          : CALStkGetSwitchVerByID <switch_name> <unitid>
#                 
#*******************************************************************************
proc CALStkGetSwitchVerByID {switch_name unitid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            return [_ntgrStkGetSwitchVerByID $switch_name $unitid]
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALStkGetSwitchStatusByID
#
#  Description    : This function is called to get switch status by unit id from switch.
#
#  Usage          : CALStkGetSwitchStatusByID <switch_name> <unitid>
#                 
#*******************************************************************************
proc CALStkGetSwitchStatusByID {switch_name unitid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            return [_ntgrStkGetSwitchStatusByID $switch_name $unitid]
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}




#*******************************************************************************
#
#  Function Name  : CALStkPortDiagnosticsErrorCheck
#
#  Description    : This function is used to check stack port diagnostic error.
#
#  Usage          : CALStkPortDiagnosticsErrorCheck <switch_name> <stackportnum>
#                 
#*******************************************************************************
proc CALStkPortDiagnosticsErrorCheck {switch_name stackportnum} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            return [_ntgrStkPortDiagnosticsErrorCheck $switch_name $stackportnum]
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-ntgr-stk.tcl" "Switch not defined"
        }
    }
}