################################################################################   
#
#  File Name		  : cal-ntgr-dns.tcl
#
#  Description      :
#         This TCL contains functions to configure ip host
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        24-Jan-11     Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : CALAddIpHost
#
#  Description    : This function is called to add ip host and ip related
#
#  Usage          : CALAddIpHost <switch_name> <hostname> <ipaddress>
#
#*******************************************************************************
proc CALAddIpHost {switch_name hostname ipaddress} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddIpHost $switch_name $hostname $ipaddress
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
            Netgear_log_file "cal-ntgr-dns.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDeleteIpHost
#
#  Description    : This function is called to delete ip host and ip related
#
#  Usage          : CALDeleteIpHost <switch_name> <hostname>
#
#*******************************************************************************
proc CALDeleteIpHost {switch_name hostname} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDeleteIpHost $switch_name $hostname
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
            Netgear_log_file "cal-ntgr-dns.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALGetNameServer
#
#  Description    : This function is called to get name server
#
#  Usage          : CALGetNameServer <switch_name>
#
#*******************************************************************************
proc CALGetNameServer {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrGetNameServer $switch_name
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
            Netgear_log_file "cal-ntgr-dns.tcl" "Switch not defined"
        }
    }
}









#*******************************************************************************
#
#  Function Name  : CALAddIpv6Host
#
#  Description    : This function is called to add ipv6 host and ip related
#
#  Usage          : CALAddIpv6Host <switch_name> <hostname> <ipaddress>
#
#*******************************************************************************
proc CALAddIpv6Host {switch_name hostname ipaddress} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddIpv6Host $switch_name $hostname $ipaddress
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
            Netgear_log_file "cal-ntgr-dns.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDeleteIpv6Host
#
#  Description    : This function is called to delete ipv6 host and ip related
#
#  Usage          : CALDeleteIpv6Host <switch_name> <hostname>
#
#*******************************************************************************
proc CALDeleteIpv6Host {switch_name hostname} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDeleteIpv6Host $switch_name $hostname
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
            Netgear_log_file "cal-ntgr-dns.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALSetDNSDefaultName
#
#  Description    : This function is called to set dns default name
#
#  Usage          : CALSetDNSDefaultName <switch_name> <dns_name>
#
#*******************************************************************************
proc CALSetDNSDefaultName {switch_name dns_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDNSDefaultName $switch_name $dns_name
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
            Netgear_log_file "cal-ntgr-dns.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDeleteDNSDefaultName
#
#  Description    : This function is called to delete dns default name
#
#  Usage          : CALDeleteDNSDefaultName <switch_name> 
#
#*******************************************************************************
proc CALDeleteDNSDefaultName {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDeleteDNSDefaultName $switch_name
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
            Netgear_log_file "cal-ntgr-dns.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALSetDNSNameServer
#
#  Description    : This function is called to set dns name server
#
#  Usage          : CALSetDNSNameServer <switch_name> <dns_server>
#
#*******************************************************************************
proc CALSetDNSNameServer {switch_name dns_server} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrSetDNSNameServer $switch_name $dns_server
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
            Netgear_log_file "cal-ntgr-dns.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDeleteDNSNameServer
#
#  Description    : This function is called to delete dns name server
#
#  Usage          : CALDeleteDNSNameServer <switch_name> <dns_server>
#
#*******************************************************************************
proc CALDeleteDNSNameServer {switch_name dns_server} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDeleteDNSNameServer $switch_name $dns_server
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
            Netgear_log_file "cal-ntgr-dns.tcl" "Switch not defined"
        }
    }
}

