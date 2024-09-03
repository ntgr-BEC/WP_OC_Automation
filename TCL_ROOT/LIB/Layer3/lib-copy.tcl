#!/bin/sh
################################################################################   
#
#  File Name		  : lib-copy.tcl
#
#  Description      :
#         This TCL contains functions to copy file
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        13-Jan-11     Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : _ntgrCopyFileUpload
#
#  Description    : This function is used to copy file upload.
#
#  Usage          : _ntgrCopyFileUpload <switch_name>
#
#*******************************************************************************
proc _ntgrCopyFileUpload {switch_name} {
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set filetype       [_getCopyFileType $switch_name]
    set transfermode   [_getCopyTransferMode $switch_name]
    set serverip ""
    if {[regexp -nocase {tftp|sftp|scp|ftp} $transfermode]} {
        set serverip       [_getCopyServerIP $switch_name]
    }
    set remotefilename [_getCopyRemoteFileName $switch_name]
    
    set scrfilename ""
    if {[regexp -nocase {script} $filetype]} {
        set scrfilename [_getCopySrcFileName $switch_name]
    }
    
    set username ""
    set password ""
    if {[regexp -nocase {sftp|scp|ftp} $transfermode] && ![regexp -nocase {tftp} $transfermode]} {
        set username [_getCopyUserName $switch_name]
        set password [_getCopyPassword $switch_name]
    }
    
    #====for nvram:techSupport====#
    if {[regexp -nocase {techSupport} $filetype]} {
        set output ""
        
        exp_send -i $cnn_id "copy ?\r"
        exp_sleep 1
        expect -i $cnn_id -re "copy ?"
        expect {
            -i $cnn_id -re "#" { append output $expect_out(buffer) }
            -i $cnn_id -re "More--" {
                append output $expect_out(buffer)
                exp_send -i $cnn_id " "
                exp_sleep 1
                exp_continue
            }
        }
        exp_send -i $cnn_id "\r"
        regexp -nocase {nvram:TechSupport|nvram:tech-support} $output filetype
    }
    
    set output ""
    
    if {[regexp -nocase {sftp|scp|ftp} $transfermode] && ![regexp -nocase {tftp} $transfermode]} {
        exp_send -i $cnn_id "copy $filetype $scrfilename $transfermode://$username@$serverip/$remotefilename\r"
    } elseif {[regexp -nocase {usb} $transfermode]} {
        exp_send -i $cnn_id "copy $filetype $scrfilename $transfermode://$remotefilename\r"
    } else {
        exp_send -i $cnn_id "copy $filetype $scrfilename $transfermode://$serverip/$remotefilename\r"
    }
    exp_sleep 1
    expect -i $cnn_id -re "copy $filetype"
    exp_sleep 1
    
    set timeout 600
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "Password:" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id "$password\r"
            exp_sleep 1
            exp_continue
        }
        -i $cnn_id -re "\(y/n\)" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id "y"
            exp_sleep 1
            exp_continue
        }
        -i $cnn_id -re "bytes|transferred" {        
            exp_sleep 10
            append output $expect_out(buffer)
            exp_send -i $cnn_id "\r"
            exp_continue
        }
        -i $cnn_id -re "\.+" {
            append output $expect_out(buffer)
            exp_sleep 5
            exp_continue
        }
    }

    set result 0
    if {[regexp -nocase {successful} $output]} {
        set result 1
    } else {
        set result 0
    }
    
    Netgear_log_file "lib-copy.tcl" "copy file on $switch_name"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }

    return $result
}


#*******************************************************************************
#
#  Function Name  : _ntgrCopyFileDownload
#
#  Description    : This function is used to copy file download.
#
#  Usage          : _ntgrCopyFileDownload <switch_name>
#
#*******************************************************************************
proc _ntgrCopyFileDownload {switch_name} {
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
        
    set filetype       [_getCopyFileType $switch_name]
    set transfermode   [_getCopyTransferMode $switch_name]
    if {[regexp -nocase {tftp|sftp|scp|ftp} $transfermode]} {
        set serverip       [_getCopyServerIP $switch_name]
    }
    set remotefilename [_getCopyRemoteFileName $switch_name]
    
    set scrfilename ""
    if {[regexp -nocase {script} $filetype]} {
        set scrfilename [_getCopySrcFileName $switch_name]
    }
    
    set username ""
    set password ""
    if {[regexp -nocase {sftp|scp|ftp} $transfermode] && ![regexp -nocase {tftp} $transfermode]} {
        set username [_getCopyUserName $switch_name]
        set password [_getCopyPassword $switch_name]
    }
    
    set output ""
    
    if {[regexp -nocase {sftp|scp|ftp} $transfermode] && ![regexp -nocase {tftp} $transfermode]} {
        exp_send -i $cnn_id "copy $transfermode://$username@$serverip/$remotefilename $filetype $scrfilename\r"
        exp_sleep 1
        expect -i $cnn_id -re "copy $transfermode://$username@$serverip"
        exp_sleep 1
    } elseif {[regexp -nocase {usb} $transfermode]} {
        exp_send -i $cnn_id "copy $transfermode://$remotefilename $filetype $scrfilename\r"
        exp_sleep 1
        expect -i $cnn_id -re "copy $transfermode://"
        exp_sleep 1    
    } else {
        exp_send -i $cnn_id "copy $transfermode://$serverip/$remotefilename $filetype $scrfilename\r"
        exp_sleep 1
        expect -i $cnn_id -re "copy $transfermode://$serverip"
        exp_sleep 1  
    }
    
    set timeout 600
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "Password:" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id "$password\r"
            exp_sleep 1
            exp_continue
        }
        -i $cnn_id -re "\(y/n\)" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id "y"
            exp_sleep 1
            exp_continue
        }
        -i $cnn_id -re "\.+" {
            append output $expect_out(buffer)
            exp_sleep 5
            exp_continue
        }
    }

    set result 0
    if {[regexp -nocase {successful} $output]} {
        set result 1
    } else {
        set result 0
    }
    
    Netgear_log_file "lib-copy.tcl" "copy file on $switch_name"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }

    return $result
}


#*******************************************************************************
#
#  Function Name  : _ntgrCopyImageToImage
#
#  Description    : This function is used to copy image to image.
#
#  Usage          : _ntgrCopyImageToImage <switch_name> <fromimage> <toimage>
#
#*******************************************************************************
proc _ntgrCopyImageToImage {switch_name fromimage toimage} {
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    exp_send -i $cnn_id "copy $fromimage $toimage\r"
    exp_sleep 1
    expect -i $cnn_id -re "copy $fromimage $toimage"
    exp_sleep 1
    
    set output ""
    set timeout 60
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        timeout {exp_send -i $cnn_id "\r"; exp_continue}
    }
    
    set result 0
    if {[regexp -nocase {successful} $output] || ![regexp -nocase {error|warning} $output]} {
        set result 1
    } else {
        set result 0
    }
    
    Netgear_log_file "lib-copy.tcl" "copy $fromimage to $toimage on $switch_name"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    
    return $result
}