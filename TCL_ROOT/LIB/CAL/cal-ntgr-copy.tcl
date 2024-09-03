####################################################################################
#  File Name   : cal-ntgr-copy.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for copy file upload and download.
#
#  History     :
#        Date          Programmer         Description
#        13-Jan-11     Tony              Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALCopyFileUpload
#
#  Description    : This function is used to copy file upload.
#
#  Usage          : CALCopyFileUpload <switch_name>
#
#*******************************************************************************
proc CALCopyFileUpload {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCopyFileUpload $switch_name
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
            Netgear_log_file "cal-ntgr-copy.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCopyFileDownload
#
#  Description    : This function is used to copy file download.
#
#  Usage          : CALCopyFileDownload <switch_name>
#
#*******************************************************************************
proc CALCopyFileDownload {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCopyFileDownload $switch_name
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
            Netgear_log_file "cal-ntgr-copy.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCopyImageToImage
#
#  Description    : This function is used to copy image to image.
#
#  Usage          : CALCopyImageToImage <switch_name> <fromimage> <toimage>
#
#*******************************************************************************
proc CALCopyImageToImage {switch_name fromimage toimage} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCopyImageToImage $switch_name $fromimage $toimage
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
            Netgear_log_file "cal-ntgr-copy.tcl" "Switch not defined"
        }
    }
}
