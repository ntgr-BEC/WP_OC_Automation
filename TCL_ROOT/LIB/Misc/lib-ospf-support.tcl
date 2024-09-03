#!/bin/sh
################################################################################   
#
#  File Name			: lib-ospf-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for OSPF.
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        01-09-06      Nina Cheng        Created
#
#
#
#
#1********************************************************************************
#
#  Function Name	: getOspfGlobalStatus
#
#  Description    : This function is used to get global status for ospf
#         
#  Usage          : getOspfGlobalStatus <switch_name>
# 
#*********************************************************************************
proc getOspfGlobalStatus {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_GLOBAL_STATUS]
}

#2********************************************************************************
#
#  Function Name	: getOspfRouterID
#
#  Description    : This function is used to get router-id for ospf 
#         
#  Usage          : getOspfRouterID <switch_name>
# 
#*********************************************************************************
proc getOspfRouterID {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_ROUTER_ID]
}

#3*******************************************************************************
#
#  Function Name	: getOspf1583Compatibility
#
#  Description    : This function is used to get 1583 compatibility for ospf 
#         
#  Usage          : getOspf1583Compatibility <switch_name>
# 
#*******************************************************************************
proc getOspf1583Compatibility {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_1583COMPATIBILITY]
}

#4*******************************************************************************
#
#  Function Name	: getOspfAreaDefaultCost
#
#  Description    : This function is used to get area default cost for ospf 
#         
#  Usage          : getOspfAreaDefaultCost <switch_name>
# 
#*******************************************************************************
proc getOspfAreaDefaultCost {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_AREA_DEFAULT_COST]
}

#5*******************************************************************************
#
#  Function Name	: getOspfDefaultinformationStatus
#
#  Description    : This function is used to get default information status for ospf 
#         
#  Usage          : getOspfDefaultinformationStatus <switch_name>
# 
#*******************************************************************************
proc getOspfDefaultinformationStatus {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_DEFAULT_INFORMATION_ORIGINATE]
}

#6*******************************************************************************
#
#  Function Name	: getOspfDefaultMetric
#
#  Description    : This function is used to get default metric for ospf 
#         
#  Usage          : getOspfDefaultMetric <switch_name>
# 
#*******************************************************************************
proc getOspfDefaultMetric {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_DEFAULT_METRIC]
}

#7*******************************************************************************
#
#  Function Name	: getOspfDistance
#
#  Description    : This function is used to get distance for ospf 
#         
#  Usage          : getOspfDistance <switch_name>
# 
#*******************************************************************************
proc getOspfDistance {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_DISTANCE]
}

#8*******************************************************************************
#
#  Function Name	: getOspfDistributeList
#
#  Description    : This function is used to get distribute list for ospf 
#         
#  Usage          : getOspfDistributeList <switch_name>
# 
#*******************************************************************************
proc getOspfDistributeList {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSFP_DISTRIBUTE_LIST_OUT]
}

#9*******************************************************************************
#
#  Function Name	: getOspfExitOverflowInterval
#
#  Description    : This function is used to get exit overflow interval for ospf 
#         
#  Usage          : getOspfExitOverflowInterval <switch_name>
# 
#*******************************************************************************
proc getOspfExitOverflowInterval {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_EXIT_OVERFLOW_INTERVAL]
}

#10*******************************************************************************
#
#  Function Name	: getOspfExternalLSBDLimit
#
#  Description    : This function is used to get external LSDB limit for ospf 
#         
#  Usage          : getOspfExternalLSBDLimit <switch_name>
# 
#*******************************************************************************
proc getOspfExternalLSBDLimit {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_EXTERNAL_LSDB_LIMIT]
}

#11*******************************************************************************
#
#  Function Name	: getOspfRedistribute
#
#  Description    : This function is used to get redistribute status for ospf 
#         
#  Usage          : getOspfRedistribute <switch_name>
# 
#*******************************************************************************
proc getOspfRedistribute {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_REDISTRIBUTE]
}

#12*******************************************************************************
#
#  Function Name	: getOspfMaxPath
#
#  Description    : This function is used to get max path for ospf 
#         
#  Usage          : getOspfMaxPath <switch_name>
# 
#*******************************************************************************
proc getOspfMaxPath {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_MAX_PATHS]
}

#13*******************************************************************************
#
#  Function Name	: getOspfTrapStatus
#
#  Description    : This function is used to get trap status for ospf 
#         
#  Usage          : getOspfTrapStatus <switch_name>
# 
#*******************************************************************************
proc getOspfTrapStatus {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_TRAP_FLAG]
}

#14*******************************************************************************
#
#  Function Name	: getOspfNssaInfor
#
#  Description    : This function is used to get nssa status for ospf 
#         
#  Usage          : getOspfNssaInfor <switch_name>
# 
#*******************************************************************************
proc getOspfNssaInfor {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_AREA_NSSA]
}

#15*******************************************************************************
#
#  Function Name	: getOspfRangeInfor
#
#  Description    : This function is used to get range information for ospf 
#         
#  Usage          : getOspfRangeInfor <switch_name>
# 
#*******************************************************************************
proc getOspfRangeInfor {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) AREA_RANGE]
}

#16*******************************************************************************
#
#  Function Name	: getOspfStubInfor
#
#  Description    : This function is used to get stub information for ospf 
#         
#  Usage          : getOspfStubInfor <switch_name>
# 
#*******************************************************************************
proc getOspfStubInfor {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_AREA_STUB]
}

#17*******************************************************************************
#
#  Function Name	: getOspfVirtualLinkStatus
#
#  Description    : This function is used to get virtual link status for ospf 
#         
#  Usage          : getOspfVirtualLinkStatus <switch_name>
# 
#*******************************************************************************
proc getOspfVirtualLinkStatus {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_AREA_VIRTUAL_LINK]
}

#18*******************************************************************************
#
#  Function Name	: getOspfInterfaceList
#
#  Description    : This function is used to get interface and its property for ospf 
#         
#  Usage          : getOspfInterfaceList <switch_name>
# 
#*******************************************************************************
proc getOspfInterfaceList {switch_name} {
	global ntgr_OSPF_List
	return [keylget ntgr_OSPF_List($switch_name) OSPF_INTERFACE_LIST]
}

#19*******************************************************************************
#
#  Function Name	: getOspfAuthforInterface
#
#  Description    : This function is used to get authentication for ospf interface 
#         
#  Usage          : getOspfAuthforInterface <IF>
# 
#*******************************************************************************
proc getOspfAuthforInterface {IF} {
	global ntgr_ospfInterfaceProperty
	return [keylget ntgr_ospfInterfaceProperty($IF) OSPF_AUTHENTICATION]
}

#20*******************************************************************************
#
#  Function Name	: getOspfCostforInterface
#
#  Description    : This function is used to get cost for ospf interface 
#         
#  Usage          : getOspfCostforInterface <IF>
# 
#*******************************************************************************
proc getOspfCostforInterface {IF} {
	global ntgr_ospfInterfaceProperty
	return [keylget ntgr_ospfInterfaceProperty($IF) OSPF_COST]
}

#21*******************************************************************************
#
#  Function Name	: getOspfDeadintervalforInterface
#
#  Description    : This function is used to get dead interval for ospf interface 
#         
#  Usage          : getOspfDeadintervalforInterface <IF>
# 
#*******************************************************************************
proc getOspfDeadintervalforInterface {IF} {
	global ntgr_ospfInterfaceProperty
	return [keylget ntgr_ospfInterfaceProperty($IF) OSPF_DEADINTERVAL]
}

#22*******************************************************************************
#
#  Function Name	: getOspfHelloIntervalforInterface
#
#  Description    : This function is used to get hello interval for ospf interface 
#         
#  Usage          : getOspfHelloIntervalforInterface <IF>
# 
#*******************************************************************************
proc getOspfHelloIntervalforInterface {IF} {
	global ntgr_ospfInterfaceProperty
	return [keylget ntgr_ospfInterfaceProperty($IF) OSPF_HELLO_INTERVAL]
}

#23*******************************************************************************
#
#  Function Name	: getOspfPriorityforInterface
#
#  Description    : This function is used to get priority for ospf interface 
#         
#  Usage          : getOspfPriorityforInterface <IF>
# 
#*******************************************************************************
proc getOspfPriorityforInterface {IF} {
	global ntgr_ospfInterfaceProperty
	return [keylget ntgr_ospfInterfaceProperty($IF) OSPF_PRIORITY]
}

#24*******************************************************************************
#
#  Function Name	: getOspfRetrIntervalforInterface
#
#  Description    : This function is used to get retransmit interval for ospf interface 
#         
#  Usage          : getOspfRetrIntervalforInterface <IF>
# 
#*******************************************************************************
proc getOspfRetrIntervalforInterface {IF} {
	global ntgr_ospfInterfaceProperty
	return [keylget ntgr_ospfInterfaceProperty($IF) OSPF_RETRANSMIT_INTERVAL]
}

#25*******************************************************************************
#
#  Function Name	: getOspfTransDelayforInterface
#
#  Description    : This function is used to get transmit delay for ospf interface 
#         
#  Usage          : getOspfTransDelayforInterface <IF>
# 
#*******************************************************************************
proc getOspfTransDelayforInterface {IF} {
	global ntgr_ospfInterfaceProperty
	return [keylget ntgr_ospfInterfaceProperty($IF) OSPF_TRANSMIT_DELAY]
}

#26*******************************************************************************
#
#  Function Name	: getOspfMtuIgnoreStatusforInterface
#
#  Description    : This function is used to get mtu ignore for ospf interface 
#         
#  Usage          : getOspfMtuIgnoreStatusforInterface <IF>
# 
#*******************************************************************************
proc getOspfMtuIgnoreStatusforInterface {IF} {
	global ntgr_ospfInterfaceProperty
	return [keylget ntgr_ospfInterfaceProperty($IF) OSPF_MTU_IGNORE]
}

#27*******************************************************************************
#
#  Function Name	: getOspfAuthforVL
#
#  Description    : This function is used to get authentication infor for ospf virtual link 
#         
#  Usage          : getOspfAuthforVL <VLProperty>
# 
#*******************************************************************************
proc getOspfAuthforVL {VLProperty} {
	global ntgr_ospfVLinkProperty
	return [keylget ntgr_ospfVLinkProperty($VLProperty) VIRTUAL_LINK_AUTHENTICATION]
}

#28*******************************************************************************
#
#  Function Name	: getOspfDeadIntervalforVL
#
#  Description    : This function is used to get dead interval for ospf virtual link 
#         
#  Usage          : getOspfDeadIntervalforVL <VLProperty>
# 
#*******************************************************************************
proc getOspfDeadIntervalforVL {VLProperty} {
	global ntgr_ospfVLinkProperty
	return [keylget ntgr_ospfVLinkProperty($VLProperty) VIRTUAL_LINK_DEAD_INTERVAL]
}

#29*******************************************************************************
#
#  Function Name	: getOspfHelloIntervalforVL
#
#  Description    : This function is used to get hello interval for ospf virtual link 
#         
#  Usage          : getOspfHelloIntervalforVL <VLProperty>
# 
#*******************************************************************************
proc getOspfHelloIntervalforVL {VLProperty} {
	global ntgr_ospfVLinkProperty
	return [keylget ntgr_ospfVLinkProperty($VLProperty) VIRTUAL_LINK_HELLO_INTERVAL]
}

#30*******************************************************************************
#
#  Function Name	: getOspfRetranIntervalforVL
#
#  Description    : This function is used to get retransmit interval for ospf virtual link 
#         
#  Usage          : getOspfRetranIntervalforVL <VLProperty>
# 
#*******************************************************************************
proc getOspfRetranIntervalforVL {VLProperty} {
	global ntgr_ospfVLinkProperty
	return [keylget ntgr_ospfVLinkProperty($VLProperty) VIRTUAL_LINK_RETRANSMIT_INTERVAL]
}

#31*******************************************************************************
#
#  Function Name	: getOspfTransDelayforVL
#
#  Description    : This function is used to get transmit delay for ospf virtual link 
#         
#  Usage          : getOspfTransDelayforVL <VLProperty>
# 
#*******************************************************************************
proc getOspfTransDelayforVL {VLProperty} {
	global ntgr_ospfVLinkProperty
	return [keylget ntgr_ospfVLinkProperty($VLProperty) VIRTUAL_LINK_TRANSMIT_DELAY]
}

#32*******************************************************************************
#
#  Function Name	: getOspfDefaultInforNSSA
#
#  Description    : This function is used to get default infor for ospf NSSA 
#         
#  Usage          : getOspfDefaultInforNSSA <NP>
# 
#*******************************************************************************
proc getOspfDefaultInforNSSA {NP} {
	global ntgr_ospfNSSAPorperty
	return [keylget ntgr_ospfNSSAPorperty($NP) NSSA_DEFAULT_INFO_ORIGINATE]
}

#33*******************************************************************************
#
#  Function Name	: getOspfNodistributeforNSSA
#
#  Description    : This function is used to get no distribute infor for ospf NSSA 
#         
#  Usage          : getOspfNodistributeforNSSA <NP>
# 
#*******************************************************************************
proc getOspfNodistributeforNSSA {NP} {
	global ntgr_ospfNSSAPorperty
	return [keylget ntgr_ospfNSSAPorperty($NP) NSSA_NO_REDISTRIBUTE]
}

#34*******************************************************************************
#
#  Function Name	: getOspfNoSummaryforNSSA
#
#  Description    : This function is used to get no summary infor for ospf NSSA 
#         
#  Usage          : getOspfNoSummaryforNSSA <NSSAProperty>
# 
#*******************************************************************************
proc getOspfNoSummaryforNSSA {NP} {
	global ntgr_ospfNSSAPorperty
	return [keylget ntgr_ospfNSSAPorperty($NP) NSSA_NO_SUMMARY]
}

#35*******************************************************************************
#
#  Function Name	: getOspfTransRoleforNSSA
#
#  Description    : This function is used to get translator role for ospf NSSA 
#         
#  Usage          : getOspfTransRoleforNSSA <NSSAProperty>
# 
#*******************************************************************************
proc getOspfTransRoleforNSSA {NP} {
	global ntgr_ospfNSSAPorperty
	return [keylget ntgr_ospfNSSAPorperty($NP) NSSA_TRANSLATOR_ROLE]
}

#36*******************************************************************************
#
#  Function Name	: getOspfTransStabIntvforNSSA
#
#  Description    : This function is used to get translator stab interval for ospf NSSA 
#         
#  Usage          : getOspfTransStabIntvforNSSA <NSSAProperty>
# 
#*******************************************************************************
proc getOspfTransStabIntvforNSSA {NP} {
	global ntgr_ospfNSSAPorperty
	return [keylget ntgr_ospfNSSAPorperty($NP) NSSA_TRANSLATOR_STAB_INTV]
}