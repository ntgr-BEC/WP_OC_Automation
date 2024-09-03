####################################################################################
#  File Name   : lib-dhcp-support.tcl
#
#  Description :
#        This file includes common functions used for DHCP.
#
#  History     :
#        Date            Programmer         Description
#        --------------------------------------------------------------------------
#        Feb 26, 2007    Scott Zhang        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolBootfile
#
#  Description    : This function is used to get bootfile of address pool.
#
#  Usage          : _ntgrGetDhcpPoolBootfile <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolBootfile {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) BOOTFILE ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolClientID
#
#  Description    : This function is used to get client ID of address pool.
#
#  Usage          : _ntgrGetDhcpPoolClientID <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolClientID {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) CLIENT_ID ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolClientName
#
#  Description    : This function is used to get client name of address pool.
#
#  Usage          : _ntgrGetDhcpPoolClientName <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolClientName {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) CLIENT_NAME ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolDefaultRouter
#
#  Description    : This function is used to get default router of address pool.
#
#  Usage          : _ntgrGetDhcpPoolDefaultRouter <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolDefaultRouter {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) DEFAULT_ROUTER ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolDnsServer
#
#  Description    : This function is used to get DNS server of address pool.
#
#  Usage          : _ntgrGetDhcpPoolDnsServer <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolDnsServer {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) DNS_SERVER ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolDomainName
#
#  Description    : This function is used to get domain name of address pool.
#
#  Usage          : _ntgrGetDhcpPoolDomainName <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolDomainName {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) DOMAIN_NAME ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolHwAddr
#
#  Description    : This function is used to get hardware address of address pool.
#
#  Usage          : _ntgrGetDhcpPoolHwAddr <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolHwAddr {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) HARDWARE_ADDR ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolHostIP
#
#  Description    : This function is used to get host IP of address pool.
#
#  Usage          : _ntgrGetDhcpPoolHostIP <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolHostIP {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) HOST_IP ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolLeaseTime
#
#  Description    : This function is used to get lease time of address pool.
#
#  Usage          : _ntgrGetDhcpPoolLeaseTime <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolLeaseTime {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) LEASE_TIME ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolNetbiosNameServer
#
#  Description    : This function is used to get netbios name server of address pool.
#
#  Usage          : _ntgrGetDhcpPoolNetbiosNameServer <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolNetbiosNameServer {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) NETBIOS_NAME_SERVER ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolNetbiosNodeType
#
#  Description    : This function is used to get netbios node type of address pool.
#
#  Usage          : _ntgrGetDhcpPoolNetbiosNodeType <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolNetbiosNodeType {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) NETBIOS_NODE_TYPE ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolNextServer
#
#  Description    : This function is used to get next server of address pool.
#
#  Usage          : _ntgrGetDhcpPoolNextServer <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolNextServer {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) NEXT_SERVER ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolNetwork
#
#  Description    : This function is used to get network of address pool.
#
#  Usage          : _ntgrGetDhcpPoolNetwork <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolNetwork {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) NETWORK ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPoolOptionList
#
#  Description    : This function is used to get option list of address pool.
#
#  Usage          : _ntgrGetDhcpPoolOptionList <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPoolOptionList {switch_name pool_name} {
    global ntgr_DHCP_AddrPool_$switch_name
    set ret {}
    keylget ntgr_DHCP_AddrPool_${switch_name}($pool_name) OPTION_LIST ret
    return $ret
}
