####################################################################################
#  File Name   : lib-igmpsnp-support.tcl
#
#  Description :
#        This file includes common functions used for igmp snooping.
#
#  History     :
#        Date          Programmer         Description
#        05/06/06      Scott Zhang        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : _getIgmpSnpGrpInterval
#
#  Description    : This function is used to get igmp snooping's group interval
#                   from variable ntgr_igmpSnoopList/ntgr_igmpSnoopIfProperties.
#
#  Usage          : _getIgmpSnpGrpInterval <array_index>
#
#*******************************************************************************
proc _getIgmpSnpGrpInterval {array_index} {
    global ntgr_igmpSnoopList
    global ntgr_igmpSnoopIfProperties
    if {[lsearch [array names ntgr_igmpSnoopList] $array_index] !=-1 } {
        return [keylget ntgr_igmpSnoopList($array_index) IGMPSNP_GROUP_INTERVAL]
    }
    if {[lsearch [array names ntgr_igmpSnoopIfProperties] $array_index] !=-1 } {
        return [keylget ntgr_igmpSnoopIfProperties($array_index) IGMPSNP_GROUP_INTERVAL]
    }
}

#*******************************************************************************
#
#  Function Name  : _getIgmpSnpMaxReponseTime
#
#  Description    : This function is used to get igmp snooping's max reponse time
#                   from variable ntgr_igmpSnoopList/ntgr_igmpSnoopIfProperties.
#
#  Usage          : _getIgmpSnpMaxReponseTime <array_index>
#
#*******************************************************************************
proc _getIgmpSnpMaxReponseTime {array_index} {
    global ntgr_igmpSnoopList
    global ntgr_igmpSnoopIfProperties
    if {[lsearch [array names ntgr_igmpSnoopList] $array_index] !=-1 } {
        return [keylget ntgr_igmpSnoopList($array_index) IGMPSNP_MAX_RESPONSE_TIME]
    }
    if {[lsearch [array names ntgr_igmpSnoopIfProperties] $array_index] !=-1 } {
        return [keylget ntgr_igmpSnoopIfProperties($array_index) IGMPSNP_MAX_RESPONSE_TIME]
    }
}

#*******************************************************************************
#
#  Function Name  : _getIgmpSnpMRouterExpireTime
#
#  Description    : This function is used to get igmp snooping's MRouter expire time
#                   from variable ntgr_igmpSnoopList/ntgr_igmpSnoopIfProperties.
#
#  Usage          : _getIgmpSnpMRouterExpireTime <array_index>
#
#*******************************************************************************
proc _getIgmpSnpMRouterExpireTime {array_index} {
    global ntgr_igmpSnoopList
    global ntgr_igmpSnoopIfProperties
    if {[lsearch [array names ntgr_igmpSnoopList] $array_index] !=-1 } {
        return [keylget ntgr_igmpSnoopList($array_index) IGMPSNP_MROUTER_EXPIRE_TIME]
    }
    if {[lsearch [array names ntgr_igmpSnoopIfProperties] $array_index] !=-1 } {
        return [keylget ntgr_igmpSnoopIfProperties($array_index) IGMPSNP_MROUTER_EXPIRE_TIME]
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetIgmpSnpQuerierIP
#
#  Description    : This function is used to get igmp snooping's querier ip address
#                   from variable ntgr_igmpSnoopList.
#
#  Usage          : _ntgrGetIgmpSnpQuerierIP <array_index>
#
#*******************************************************************************
proc _ntgrGetIgmpSnpQuerierIP {array_index} {
    global ntgr_igmpSnoopList
    set ret "default"
    keylget ntgr_igmpSnoopList($array_index) IGMPSNP_QUERIER_IP ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _getIgmpSnpInterfaceList
#
#  Description    : This function is used to get interfaces needed special
#                   configuration for igmp snooping from ntgr_igmpSnoopList.
#
#  Usage          : _getIgmpSnpInterfaceList <array_index>
#
#*******************************************************************************
proc _getIgmpSnpInterfaceList {array_index} {
    global ntgr_igmpSnoopList
    return [keylget ntgr_igmpSnoopList($array_index) IGMPSNP_INTERFACE_LIST]
}

#*******************************************************************************
#
#  Function Name  : _getIgmpSnpInterfaceStatus
#
#  Description    : This function is used to get interface's igmp snooping 
#                   status from ntgr_igmpSnoopList.
#
#  Usage          : _getIgmpSnpInterfaceStatus <array_index>
#
#*******************************************************************************
proc _getIgmpSnpInterfaceStatus {array_index} {
    global ntgr_igmpSnoopIfProperties
    return [keylget ntgr_igmpSnoopIfProperties($array_index) IGMPSNP_INTERFACE]
}

#*******************************************************************************
#
#  Function Name  : _getIgmpSnpFastLeaveStatus
#
#  Description    : This function is used to get interface's fast leave status
#                   from ntgr_igmpSnoopList.
#
#  Usage          : _getIgmpSnpFastLeaveStatus <array_index>
#
#*******************************************************************************
proc _getIgmpSnpFastLeaveStatus {array_index} {
    global ntgr_igmpSnoopIfProperties
    return [keylget ntgr_igmpSnoopIfProperties($array_index) IGMPSNP_FAST_LEAVE]
}

#*******************************************************************************
#
#  Function Name  : _getIgmpSnpMRouterVlanMember
#
#  Description    : This function is used to get interface's fast leave status
#                   from ntgr_igmpSnoopList.
#
#  Usage          : _getIgmpSnpMRouterVlanMember <array_index>
#
#*******************************************************************************
proc _getIgmpSnpMRouterVlanMember {array_index} {
    global ntgr_igmpSnoopIfProperties
    return [keylget ntgr_igmpSnoopIfProperties($array_index) IGMPSNP_MROUTER_MEMBER_VLANID]
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetIgmpSnpQuerierStatus
#
#  Description    : This function is used to get queier status(enable/disable) 
#                   from ntgr_igmpSnoopIfProperties.
#
#  Usage          : _ntgrGetIgmpSnpQuerierStatus <array_index>
#
#*******************************************************************************
proc _ntgrGetIgmpSnpQuerierStatus {array_index} {
    global ntgr_igmpSnoopIfProperties
    set ret "default"
    keylget ntgr_igmpSnoopIfProperties($array_index) IGMPSNP_QUERIER ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetIgmpSnpQuerierAddress
#
#  Description    : This function is used to get queier address
#                   from ntgr_igmpSnoopIfProperties.
#
#  Usage          : _ntgrGetIgmpSnpQuerierAddress <array_index>
#
#*******************************************************************************
proc _ntgrGetIgmpSnpQuerierAddress {array_index} {
    global ntgr_igmpSnoopIfProperties
    set ret "default"
    keylget ntgr_igmpSnoopIfProperties($array_index) IGMPSNP_QUERIER_ADDRESS ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetIgmpSnpQuerierVersion
#
#  Description    : This function is used to get queier version in seconds
#                   from ntgr_igmpSnoopIfProperties.
#
#  Usage          : _ntgrGetIgmpSnpQuerierVersion <array_index>
#
#*******************************************************************************
proc _ntgrGetIgmpSnpQuerierVersion {array_index} {
    global ntgr_igmpSnoopIfProperties
    set ret "default"
    keylget ntgr_igmpSnoopIfProperties($array_index) IGMPSNP_QUERIER_VERSION ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetIgmpSnpQuerierInterval
#
#  Description    : This function is used to get queier interval in seconds
#                   from ntgr_igmpSnoopIfProperties.
#
#  Usage          : _ntgrGetIgmpSnpQuerierInterval <array_index>
#
#*******************************************************************************
proc _ntgrGetIgmpSnpQuerierInterval {array_index} {
    global ntgr_igmpSnoopIfProperties
    set ret "default"
    keylget ntgr_igmpSnoopIfProperties($array_index) IGMPSNP_QUERIER_INTERVAL ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetIgmpSnpQuerierExpiry
#
#  Description    : This function is used to get queier expiry timer in seconds
#                   from ntgr_igmpSnoopIfProperties.
#
#  Usage          : _ntgrGetIgmpSnpQuerierExpiry <array_index>
#
#*******************************************************************************
proc _ntgrGetIgmpSnpQuerierExpiry {array_index} {
    global ntgr_igmpSnoopIfProperties
    set ret "default"
    keylget ntgr_igmpSnoopIfProperties($array_index) IGMPSNP_QUERIER_EXPIRY ret
    return $ret
}