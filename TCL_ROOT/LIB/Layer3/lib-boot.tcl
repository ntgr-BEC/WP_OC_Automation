#!/bin/sh
################################################################################   
#
#  File Name		  : lib-boot.tcl
#
#  Description      :
#         This TCL contains functions to configure boot
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        15-Jul-11     Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : _ntgrBootAutoCopySwEnable
#
#  Description    : This function is called to enable boot auto-copy-sw
#
#  Usage          : _ntgrBootAutoCopySwEnable <switch_name>
#
#*******************************************************************************
proc _ntgrBootAutoCopySwEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "boot auto-copy-sw\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    
    Netgear_log_file "lib-boot.tcl" "enable boot auto-copy-sw on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrBootAutoCopySwDisable
#
#  Description    : This function is called to diable boot auto-copy-sw
#
#  Usage          : _ntgrBootAutoCopySwDisable <switch_name>
#
#*******************************************************************************
proc _ntgrBootAutoCopySwDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "no boot auto-copy-sw\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    
    Netgear_log_file "lib-boot.tcl" "diable boot auto-copy-sw on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrBootAutoCopySwDowngradeEnable
#
#  Description    : This function is called to enable boot auto-copy-sw all-downgrade
#
#  Usage          : _ntgrBootAutoCopySwDowngradeEnable <switch_name>
#
#*******************************************************************************
proc _ntgrBootAutoCopySwDowngradeEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "boot auto-copy-sw allow-downgrade\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    
    Netgear_log_file "lib-boot.tcl" "enable boot auto-copy-sw all-downgrade on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrBootAutoCopySwDowngradeDisable
#
#  Description    : This function is called to disable boot auto-copy-sw all-downgrade
#
#  Usage          : _ntgrBootAutoCopySwDowngradeDisable <switch_name>
#
#*******************************************************************************
proc _ntgrBootAutoCopySwDowngradeDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "no boot auto-copy-sw allow-downgrade\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    
    Netgear_log_file "lib-boot.tcl" "disable boot auto-copy-sw all-downgrade on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrBootAutoCopySwTrapEnable
#
#  Description    : This function is called to enable boot auto-copy-sw trap
#
#  Usage          : _ntgrBootAutoCopySwTrapEnable <switch_name>
#
#*******************************************************************************
proc _ntgrBootAutoCopySwTrapEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "boot auto-copy-sw trap\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    
    Netgear_log_file "lib-boot.tcl" "enable boot auto-copy-sw trap on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrBootAutoCopySwTrapDiable
#
#  Description    : This function is called to diable boot auto-copy-sw trap
#
#  Usage          : _ntgrBootAutoCopySwTrapDiable <switch_name>
#
#*******************************************************************************
proc _ntgrBootAutoCopySwTrapDiable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "no boot auto-copy-sw trap\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    
    Netgear_log_file "lib-boot.tcl" "diable boot auto-copy-sw trap on switcher"
    Netgear_disconnect_switch $switch_name
}





#*******************************************************************************
#
#  Function Name  : _ntgrBootSystemSetActiveImage
#
#  Description    : This function is called to set boot system active image
#
#  Usage          : _ntgrBootSystemSetActiveImage <switch_name> <imagename> <unitid>
#
#*******************************************************************************
proc _ntgrBootSystemSetActiveImage {switch_name imagename {unitid ""}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "boot system $unitid $imagename\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    
    Netgear_log_file "lib-boot.tcl" "set boot system active image on switcher"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrBootGetAutoCopySwSynStatus
#
#  Description    : This function is called to get auto-copy-sw synchronization status from switch.
#
#  Usage          : _ntgrBootGetAutoCopySwSynStatus <switch_name>
#
#*******************************************************************************
proc _ntgrBootGetAutoCopySwSynStatus { switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show auto-copy-sw\r"
    exp_sleep 1
    expect -i $cnn_id -re "show auto-copy-sw"
    exp_sleep 1
    expect -i $cnn_id -re "#"   

    set output $expect_out(buffer)
    
    set result ""
    regexp -nocase {Synchronization\.+\s+([^\n\r]+)} $output dummy result
    set result [string trim $result]
    
    Netgear_log_file "lib-ntgr-stk.tcl" "get auto-copy-sw synchronization status on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}