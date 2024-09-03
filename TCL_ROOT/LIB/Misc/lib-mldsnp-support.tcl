####################################################################################
#  File Name   : lib-mldsnp-support.tcl
#
#  Description :
#        This file includes common functions used for Mld snooping.
#
#  History     :
#        Date          Programmer         Description
#        2012/11/21    Tony Jing          Created
#
####################################################################################


#*******************************************************************************
#
#  Function Name  : _ntgrGetMldSnpQuerierAddress
#
#  Description    : This function is used to get queier address
#                   from ntgr_MldSnoopIfProperties.
#
#  Usage          : _ntgrGetMldSnpQuerierAddress <array_index>
#
#*******************************************************************************
proc _ntgrGetMldSnpQuerierAddress {array_index} {
    global ntgr_MldSnoopIfProperties
    set ret "default"
    keylget ntgr_MldSnoopIfProperties($array_index) MLDSNP_QUERIER_ADDRESS ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetMldSnpQuerierInterval
#
#  Description    : This function is used to get queier interval in seconds
#                   from ntgr_MldSnoopIfProperties.
#
#  Usage          : _ntgrGetMldSnpQuerierInterval <array_index>
#
#*******************************************************************************
proc _ntgrGetMldSnpQuerierInterval {array_index} {
    global ntgr_MldSnoopIfProperties
    set ret "default"
    keylget ntgr_MldSnoopIfProperties($array_index) MLDSNP_QUERIER_INTERVAL ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetMldSnpQuerierExpiry
#
#  Description    : This function is used to get queier expiry timer in seconds
#                   from ntgr_MldSnoopIfProperties.
#
#  Usage          : _ntgrGetMldSnpQuerierExpiry <array_index>
#
#*******************************************************************************
proc _ntgrGetMldSnpQuerierExpiry {array_index} {
    global ntgr_MldSnoopIfProperties
    set ret "default"
    keylget ntgr_MldSnoopIfProperties($array_index) MLDSNP_QUERIER_EXPIRY ret
    return $ret
}