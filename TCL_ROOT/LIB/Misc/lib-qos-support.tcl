####################################################################################
#  File Name   : lib-qos-support.tcl
#
#  Description :
#        This file includes common functions used for Qos.
#
#  History     :
#        Date            Programmer         Description
#        --------------------------------------------------------------------------
#        Jan 15, 2007    Scott Zhang        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchAny
#
#  Description    : This function is used to get the match any argument.
#
#  Usage          : _ntgrGetClassMapMatchAny <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchAny {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_ANY ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchClassMap
#
#  Description    : This function is used to get the match nested class-map argument.
#
#  Usage          : _ntgrGetClassMapMatchClassMap <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchClassMap {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_CLS_MAP ret
    return $ret
}



#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchSrcMac
#
#  Description    : This function is used to get the match source MAC argument.
#
#  Usage          : _ntgrGetClassMapMatchSrcMac <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchSrcMac {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_SRC_MAC ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchDstMac
#
#  Description    : This function is used to get the match destination MAC argument.
#
#  Usage          : _ntgrGetClassMapMatchDstMac <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchDstMac {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_DST_MAC ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchCos
#
#  Description    : This function is used to get the match Cos value argument.
#
#  Usage          : _ntgrGetClassMapMatchCos <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchCos {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_COS ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchVlan
#
#  Description    : This function is used to get the match VLAN argument.
#
#  Usage          : _ntgrGetClassMapMatchVlan <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchVlan {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_VLAN ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchEthernetType
#
#  Description    : This function is used to get the match ethernet type argument.
#
#  Usage          : _ntgrGetClassMapMatchEthernetType <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchEthernetType {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_ETHERNET_TYPE ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchSrcIP
#
#  Description    : This function is used to get the match source IP argument.
#
#  Usage          : _ntgrGetClassMapMatchSrcIP <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchSrcIP {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_SRC_IP ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchDstIP
#
#  Description    : This function is used to get the match destination IP argument.
#
#  Usage          : _ntgrGetClassMapMatchDstIP <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchDstIP {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_DST_IP ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchIpTOS
#
#  Description    : This function is used to get the match IP TOS argument.
#
#  Usage          : _ntgrGetClassMapMatchIpTOS <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchIpTOS {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_IP_TOS ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchIpPrecedence
#
#  Description    : This function is used to get the match IP precedence argument.
#
#  Usage          : _ntgrGetClassMapMatchIpPrecedence <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchIpPrecedence {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_IP_PRECEDENCE ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchIpDSCP
#
#  Description    : This function is used to get the match IP DSCP argument.
#
#  Usage          : _ntgrGetClassMapMatchIpDSCP <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchIpDSCP {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_IP_DSCP ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchIpProtocol
#
#  Description    : This function is used to get the match IP protocol argument.
#
#  Usage          : _ntgrGetClassMapMatchIpProtocol <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchIpProtocol {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_PROTOCOL ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchL4SrcPort
#
#  Description    : This function is used to get the match layer4 source argument.
#
#  Usage          : _ntgrGetClassMapMatchL4SrcPort <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchL4SrcPort {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_SRC_L4PORT ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetClassMapMatchL4DstPort
#
#  Description    : This function is used to get the match layer4 destination argument.
#
#  Usage          : _ntgrGetClassMapMatchL4DstPort <switch_name cls>
#
#*******************************************************************************
proc _ntgrGetClassMapMatchL4DstPort {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_DST_L4PORT ret
    return $ret
}
##---------- Added by kenddy --------------#
proc _ntgrGetClassMapMatchSecondCos {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_SEC_COS ret
    return $ret
}

proc _ntgrGetClassMapMatchSecondVlan {switch_name cls} {
    global ntgr_ClassMap_$switch_name
    set ret {}
    keylget ntgr_ClassMap_${switch_name}($cls) MATCH_SEC_VLAN ret
    return $ret
}

##---------- Added IPV6 match by kenddy --------------#

proc _ntgrGetV6ClassMapMatchAny {switch_name cls} {
    global ntgr_V6ClassMap_$switch_name
    set ret {}
    keylget ntgr_V6ClassMap_${switch_name}($cls) V6_MATCH_ANY ret
    return $ret
}

proc _ntgrGetV6ClassMapMatchClassMap {switch_name cls} {
    global ntgr_V6ClassMap_$switch_name
    set ret {}
    keylget ntgr_V6ClassMap_${switch_name}($cls) V6_MATCH_CLS_MAP ret
    return $ret
}

proc _ntgrGetV6ClassMapMatchSrcIPv6 {switch_name cls} {
    global ntgr_V6ClassMap_$switch_name
    set ret {}
    keylget ntgr_V6ClassMap_${switch_name}($cls) V6_MATCH_SRC_IP ret
    return $ret
}

proc _ntgrGetV6ClassMapMatchDstIPv6 {switch_name cls} {
    global ntgr_V6ClassMap_$switch_name
    set ret {}
    keylget ntgr_V6ClassMap_${switch_name}($cls) V6_MATCH_DST_IP ret
    return $ret
}

proc _ntgrGetV6ClassMapMatchL4SrcPort {switch_name cls} {

    global ntgr_V6ClassMap_$switch_name
    set ret {}
    keylget ntgr_V6ClassMap_${switch_name}($cls) V6_MATCH_SRC_L4 ret
    return $ret  
}
proc _ntgrGetV6ClassMapMatchL4DstPort {switch_name cls} {

    global ntgr_V6ClassMap_$switch_name
    set ret {}
    keylget ntgr_V6ClassMap_${switch_name}($cls) V6_MATCH_DST_L4 ret
    return $ret  
}

proc _ntgrGetV6ClassMapMatchTrafficClass {switch_name cls} {

    global ntgr_V6ClassMap_$switch_name
    set ret {}
    keylget ntgr_V6ClassMap_${switch_name}($cls) V6_MATCH_TRAFFIC_CLASS ret
    return $ret  
}

proc _ntgrGetV6ClassMapMatchFlowLabel {switch_name cls} {

    global ntgr_V6ClassMap_$switch_name
    set ret {}
    keylget ntgr_V6ClassMap_${switch_name}($cls) V6_MATCH_FLOW_LABEL ret
    return $ret  
}

proc _ntgrGetV6ClassMapMatchProtocol {switch_name cls} {

    global ntgr_V6ClassMap_$switch_name
    set ret {}
    keylget ntgr_V6ClassMap_${switch_name}($cls) V6_MATCH_PROTOCOL ret
    return $ret  
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapClassList
#
#  Description    : This function is used to get the list of class and attr of a policy.
#
#  Usage          : _ntgrGetPolicyMapClassList <switch_name plc>
#
#*******************************************************************************
proc _ntgrGetPolicyMapClassList {switch_name plc} {
    global ntgr_PolicyMap_$switch_name
    return [keylget ntgr_PolicyMap_${switch_name}($plc) CLASS_MAP_LIST]
}

#*******************************************************************************
#
#  Function Name  : _ntgrQosGetPolicyApplyPorts
#
#  Description    : This function is used to get the list of ports of a policy.
#
#  Usage          : _ntgrQosGetPolicyApplyPorts <switch_name plc>
#
#*******************************************************************************
proc _ntgrQosGetPolicyApplyPorts {switch_name plc} {
    global ntgr_PolicyMap_$switch_name
    return [keylget ntgr_PolicyMap_${switch_name}($plc) APPLIED_TO_PORTS]
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapAttrDrop
#
#  Description    : This function is used to get the drop attribute of policy argument.
#
#  Usage          : _ntgrGetPolicyMapAttrDrop <switch_name attr>
#
#*******************************************************************************
proc _ntgrGetPolicyMapAttrDrop {switch_name attr} {
    global ntgr_PolicyAttr_$switch_name
    set ret {}
    keylget ntgr_PolicyAttr_${switch_name}($attr) DROP ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapAttrQueue
#
#  Description    : This function is used to get the queue attribute of policy argument.
#
#  Usage          : _ntgrGetPolicyMapAttrQueue <switch_name attr>
#
#*******************************************************************************
proc _ntgrGetPolicyMapAttrQueue {switch_name attr} {
    global ntgr_PolicyAttr_$switch_name
    set ret {}
    keylget ntgr_PolicyAttr_${switch_name}($attr) ASSIGN_QUEUE ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapAttrCos
#
#  Description    : This function is used to get the Cos attribute of policy argument.
#
#  Usage          : _ntgrGetPolicyMapAttrCos <switch_name attr>
#
#*******************************************************************************
proc _ntgrGetPolicyMapAttrCos {switch_name attr} {
    global ntgr_PolicyAttr_$switch_name
    set ret {}
    keylget ntgr_PolicyAttr_${switch_name}($attr) MARK_COS ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapAttrSecCos
#
#  Description    : This function is used to get the second Cos attribute of policy argument.
#
#  Usage          : _ntgrGetPolicyMapAttrSecCos <switch_name attr>
#
#*******************************************************************************

proc _ntgrGetPolicyMapAttrSecCos {switch_name attr} {
    global ntgr_PolicyAttr_$switch_name
    set ret {}
    keylget ntgr_PolicyAttr_${switch_name}($attr) MARK_SEC_COS ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapAttrDscp
#
#  Description    : This function is used to get the dscp attribute of policy argument.
#
#  Usage          : _ntgrGetPolicyMapAttrDscp <switch_name attr>
#
#*******************************************************************************
proc _ntgrGetPolicyMapAttrDscp {switch_name attr} {
    global ntgr_PolicyAttr_$switch_name
    set ret {}
    keylget ntgr_PolicyAttr_${switch_name}($attr) MARK_IP_DSCP ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapAttrPrec
#
#  Description    : This function is used to get the precedence attribute of policy argument.
#
#  Usage          : _ntgrGetPolicyMapAttrPrec <switch_name attr>
#
#*******************************************************************************
proc _ntgrGetPolicyMapAttrPrec {switch_name attr} {
    global ntgr_PolicyAttr_$switch_name
    set ret {}
    keylget ntgr_PolicyAttr_${switch_name}($attr) MARK_IP_PRECEDENCE ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapAttrClrMode
#
#  Description    : This function is used to get the color mode of policy argument.
#
#  Usage          : _ntgrGetPolicyMapAttrClrMode <switch_name attr>
#
#*******************************************************************************
proc _ntgrGetPolicyMapAttrClrMode {switch_name attr} {
    global ntgr_PolicyAttr_$switch_name
    set ret {}
    keylget ntgr_PolicyAttr_${switch_name}($attr) CONFORM_COLOR_MODE ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapAttrPolice
#
#  Description    : This function is used to get the police attr of policy argument.
#
#  Usage          : _ntgrGetPolicyMapAttrPolice <switch_name attr>
#
#*******************************************************************************
proc _ntgrGetPolicyMapAttrPolice {switch_name attr} {
    global ntgr_PolicyAttr_$switch_name
    set ret {}
    keylget ntgr_PolicyAttr_${switch_name}($attr) POLICE_SIMPLE ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapAttrPoliceTwoRate
#
#  Description    : This function is used to get the police-two-rate attr argument.
#
#  Usage          : _ntgrGetPolicyMapAttrPoliceTwoRate <switch_name attr>
#
#*******************************************************************************
proc _ntgrGetPolicyMapAttrPoliceTwoRate {switch_name attr} {
    global ntgr_PolicyAttr_$switch_name
    set ret {}
    keylget ntgr_PolicyAttr_${switch_name}($attr) POLICE_TWO_RATE ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapAttrPoliceSingleRate
#
#  Description    : This function is used to get the police-single-rate attr argument.
#
#  Usage          : _ntgrGetPolicyMapAttrPoliceSingleRate <switch_name attr>
#
#*******************************************************************************
proc _ntgrGetPolicyMapAttrPoliceSingleRate {switch_name attr} {
    global ntgr_PolicyAttr_$switch_name
    set ret {}
    keylget ntgr_PolicyAttr_${switch_name}($attr) POLICE_SINGLE_RATE ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapAttrMirror
#
#  Description    : This function is used to get the police attr of policy argument.
#
#  Usage          : _ntgrGetPolicyMapAttrMirror <switch_name attr>
#
#*******************************************************************************
proc _ntgrGetPolicyMapAttrMirror {switch_name attr} {
    global ntgr_PolicyAttr_$switch_name
    set ret {}
    keylget ntgr_PolicyAttr_${switch_name}($attr) MIRROR_INCOMING ret
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetPolicyMapAttrRedirect
#
#  Description    : This function is used to get the police attr of policy argument.
#
#  Usage          : _ntgrGetPolicyMapAttrMirror <switch_name attr>
#
#*******************************************************************************
proc _ntgrGetPolicyMapAttrRedirect {switch_name attr} {
    global ntgr_PolicyAttr_$switch_name
    set ret {}
    keylget ntgr_PolicyAttr_${switch_name}($attr) REDIRECT_INCOMING ret
    return $ret
}