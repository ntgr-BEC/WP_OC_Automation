#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-ospf.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for OSPF configuration.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        04/09/06     Nina Cheng        Created
#        31/01/07     Nina Cheng        Created  
#
#
################################################################################

#1*******************************************************************************
#
#  Function Name	: CALOspfEnable
#
#  Description    : This function is an API used to enable ospf on global
#         
#  Usage          : CALOspfEnable <switch_name> <enable_flag> 
# 
#*******************************************************************************
proc CALOspfEnable { switch_name enable_flag} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrOspfEnable $switch_name $enable_flag
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#2*******************************************************************************
#
#  Function Name	: CALOspfDisable
#
#  Description    : This function is an API used to disable ospf on global
#         
#  Usage          : CALOspfDisable <switch_name> <enable_flag>
# 
#*******************************************************************************
proc CALOspfDisable { switch_name enable_flag} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrOspfDisable $switch_name $enable_flag
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#3*******************************************************************************
#
#  Function Name	: CALConfigOspfRouterID
#
#  Description    : This function is an API used to configure router id for ospf
#         
#  Usage          : CALConfigOspfRouterID <switch_name> <router_id>
# 
#*******************************************************************************
proc CALConfigOspfRouterID { switch_name router_id} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrConfigureOspfRouterID $switch_name $router_id
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#4*******************************************************************************
#
#  Function Name	: CAL1583CompatibilityEnable
#
#  Description    : This function is an API used to configure 1583 compatibility for ospf
#         
#  Usage          : CAL1583CompatibilityEnable <switch_name> <enbale_falg>
# 
#*******************************************************************************
proc CAL1583CompatibilityEnable { switch_name enbale_falg} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgr1583CompatibilityEnable $switch_name $enbale_falg
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#5*******************************************************************************
#
#  Function Name	: CAL1583CompatibilityDisable
#
#  Description    : This function is an API used to disable 1583 compatibility for ospf
#         
#  Usage          : CAL1583CompatibilityDisable <switch_name> <enbale_falg>
# 
#*******************************************************************************
proc CAL1583CompatibilityDisable { switch_name enbale_falg} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgr1583CompatibilityDisable $switch_name $enbale_falg
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#6*******************************************************************************
#
#  Function Name	: CALAreaDefaultCost
#
#  Description    : This function is an API used to configure area default cost 
#                   for OSPF
#         
#  Usage          : CALAreaDefaultCost <switch_name> <defaultcost_list>
# 
#*******************************************************************************
proc CALAreaDefaultCost {switch_name defaultcost_list} {
	  
	  set number [llength $defaultcost_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				     _ntgrAreaDefaultCost $switch_name $defaultcost_list
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#7*******************************************************************************
#
#  Function Name	: CALDefaultInforEnable
#
#  Description    : This function is an API used to configure default information 
#                   originate for ospf
#         
#  Usage          : CALDefaultInforEnable <switch_name> <defaultInfo>
# 
#*******************************************************************************
proc CALDefaultInforEnable {switch_name defaultInfo} {
	  set number [llength $defaultInfo]
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				     _ntgrDefaultInformationOriginateEnable $switch_name $defaultInfo
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#8*******************************************************************************
#
#  Function Name	: CALDefaultInfOrDisable
#
#  Description    : This function is an API used to delete default information 
#                   originate for ospf
#         
#  Usage          : CALDefaultInfOrDisable <switch_name> <defaultInfo>
# 
#*******************************************************************************
proc CALDefaultInfOrDisable {switch_name defaultInfo} {
	  set number [llength $defaultInfo] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				     _ntgrDefaultInformationOriginateDisable $switch_name
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#9*******************************************************************************
#
#  Function Name	: CALAddDefaultMetric
#
#  Description    : This function is an API used to add default metric for ospf 
#         
#  Usage          : CALAddDefaultMetric <switch_name> <metric>
# 
#*******************************************************************************
proc CALAddDefaultMetric {switch_name metric} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrAddDefaultMetric $switch_name $metric
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#10*******************************************************************************
#
#  Function Name	: CALDeleteDefaultMetric
#
#  Description    : This function is an API used to delete default metric for ospf 
#         
#  Usage          : CALDeleteDefaultMetric <switch_name> <metric>
# 
#*******************************************************************************
proc CALDeleteDefaultMetric {switch_name metric} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrDeleteDefaultMetric $switch_name $metric
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#11*******************************************************************************
#
#  Function Name	: CALOspfDistanceEnable
#
#  Description    : This function is an API used to configure distance for ospf 
#         
#  Usage          : CALOspfDistanceEnable <switch_name> <distance_list>
# 
#*******************************************************************************
proc CALOspfDistanceEnable {switch_name distance_list} {
	  set number [llength $distance_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				     _ntgrOspfDistanceEnable $switch_name $distance_list
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#12*******************************************************************************
#
#  Function Name	: CALOspfDistanceDisable
#
#  Description    : This function is an API used to delete distance for ospf 
#         
#  Usage          : CALOspfDistanceDisable <switch_name> <distance_list>
# 
#*******************************************************************************
proc CALOspfDistanceDisable {switch_name distance_list} {
	  set number [llength $distance_list]  
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				     _ntgrOspfDistanceDisable $switch_name $distance_list
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#13*******************************************************************************
#
#  Function Name	: CALOspfDistributeListOutEnable
#
#  Description    : This function is an API used to enable distribute list for ospf 
#         
#  Usage          : CALOspfDistributeListOutEnable <switch_name> <distribute_list>
# 
#*******************************************************************************
proc CALOspfDistributeListOutEnable {switch_name distribute_list} {
	  set number [llength $distribute_list]  
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				     _ntgrOspfDistributeListOutEnable $switch_name $distribute_list
				 }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#14*******************************************************************************
#
#  Function Name	: CALOspfDistributeListOutDisable
#
#  Description    : This function is an API used to disable distribute list for ospf 
#         
#  Usage          : CALOspfDistributeListOutDisable <switch_name> <distribute_list>
# 
#*******************************************************************************
proc CALOspfDistributeListOutDisable {switch_name distribute_list} {
	  set number [llength $distribute_list]
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				     _ntgrOspfDistributeListOutDisable $switch_name $distribute_list
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#15*******************************************************************************
#
#  Function Name	: CALOspfExitOverflowIntEnable
#
#  Description    : This function is an API used to configuire interval for ospf 
#                   lsdb overflow 
#         
#  Usage          : CALOspfExitOverflowIntEnable <switch_name> <interval>
# 
#*******************************************************************************
proc CALOspfExitOverflowIntEnable {switch_name interval} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrOspfExitOverflowIntervalEnable $switch_name $interval
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#16*******************************************************************************
#
#  Function Name	: CALOspfExitOverflowIntDisable
#
#  Description    : This function is an API used to delete interval for ospf 
#                   lsdb overflow 
#         
#  Usage          : CALOspfExitOverflowIntDisable <switch_name> <interval>
# 
#*******************************************************************************
proc CALOspfExitOverflowIntDisable {switch_name interval} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrOspfExitOverflowIntervalDisable $switch_name $interval
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#17*******************************************************************************
#
#  Function Name	: CALOspfConfigExternalLSDBLimit
#
#  Description    : This function is an API used to configure limit for ospf 
#                   external lsdb
#         
#  Usage          : CALOspfConfigExternalLSDBLimit <switch_name> <limit>
# 
#*******************************************************************************
proc CALOspfConfigExternalLSDBLimit {switch_name limit} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrOspfConfigExternalLSDBLimit $switch_name $limit
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#18*******************************************************************************
#
#  Function Name	: CALOspfDeleteExternalLSDBLimit
#
#  Description    : This function is an API used to delete limit for ospf 
#                   external lsdb
#         
#  Usage          : CALOspfDeleteExternalLSDBLimit <switch_name> <limit>
# 
#*******************************************************************************
proc CALOspfDeleteExternalLSDBLimit {switch_name limit} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrOspfDeleteExternalLSDBLimit $switch_name $limit
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}


#19*******************************************************************************
#
#  Function Name	: CALOspfRedistributeEnable
#
#  Description    : This function is an API used to enable redistribute for ospf 
#         
#  Usage          : CALOspfRedistributeEnable <switch_name> <redistribute_list>
# 
#*******************************************************************************
proc CALOspfRedistributeEnable {switch_name redistribute_list} {
	  set number [llength $redistribute_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				     _ntgrOspfRedistributeEnable $switch_name $redistribute_list 
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}


#20*******************************************************************************
#
#  Function Name	: CALOspfRedistributeDisable
#
#  Description    : This function is an API used to disable redistribute for ospf 
#         
#  Usage          : CALOspfRedistributeDisable <switch_name> <redistribute_list>
# 
#*******************************************************************************
proc CALOspfRedistributeDisable {switch_name redistribute_list} {
	  set number [llength $redistribute_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				     _ntgrOspfRedistributeDisable $switch_name $redistribute_list 
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#21*******************************************************************************
#
#  Function Name	: CALOspfMaxPathEnable
#
#  Description    : This function is an API used to configuire max path for ospf 
#         
#  Usage          : CALOspfMaxPathEnable <switch_name> <max_path>
# 
#*******************************************************************************
proc CALOspfMaxPathEnable {switch_name max_path} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				_ntgrOspfMaxPathEnable $switch_name $max_path
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#22*******************************************************************************
#
#  Function Name	: CALOspfMaxPathDisable
#
#  Description    : This function is an API used to delete max path for ospf 
#         
#  Usage          : CALOspfMaxPathDisable <switch_name> <max_path>
# 
#*******************************************************************************
proc CALOspfMaxPathDisable {switch_name max_path} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				_ntgrOspfMaxPathDisable $switch_name $max_path
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#23*******************************************************************************
#
#  Function Name	: CALOspfTrapEnable
#
#  Description    : This function is an API used to enable trap for ospf 
#         
#  Usage          : CALOspfTrapEnable <switch_name> <trap_flag>
# 
#*******************************************************************************
proc CALOspfTrapEnable {switch_name trap_flag} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				_ntgrOspfTrapEnable $switch_name $trap_flag
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#24*******************************************************************************
#
#  Function Name	: CALOspfTrapDisable
#
#  Description    : This function is an API used to disable trap for ospf 
#         
#  Usage          : CALOspfTrapDisable <switch_name> <trap_flag>
# 
#*******************************************************************************
proc CALOspfTrapDisable {switch_name trap_flag} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				_ntgrOspfTrapDisable $switch_name $trap_flag
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#25*******************************************************************************
#
#  Function Name	: CALOspfAreaRangeEnable
#
#  Description    : This function is an API used to enable area range for ospf 
#         
#  Usage          : CALOspfAreaRangeEnable <switch_name> <range_list>
# 
#*******************************************************************************
proc CALOspfAreaRangeEnable {switch_name range_list} {
	  set number [llength $range_list]
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				       _ntgrOspfAreaRangeEnable $switch_name $range_list
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#26*******************************************************************************
#
#  Function Name	: CALOspfAreaRangeDisable
#
#  Description    : This function is an API used to disable area range for ospf 
#         
#  Usage          : CALOspfAreaRangeDisable <switch_name> <range>
# 
#*******************************************************************************
proc CALOspfAreaRangeDisable {switch_name range} {
	  set number [llength $range]
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				      _ntgrOspfAreaRangeDisable $switch_name $range
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#27*******************************************************************************
#
#  Function Name	: CALOspfAreaStubEnable
#
#  Description    : This function is an API used to enable stub area for ospf 
#         
#  Usage          : CALOspfAreaStubEnable <switch_name> <area_list>
# 
#*******************************************************************************
proc CALOspfAreaStubEnable {switch_name area_list} {
	  set number [llength $area_list]
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				      _ntgrOspfAreaStubEnable $switch_name $area_list
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#28*******************************************************************************
#
#  Function Name	: CALOspfAreaStubDisable
#
#  Description    : This function is an API used to disable stub area for ospf 
#         
#  Usage          : CALOspfAreaStubDisable <switch_name> <area_list>
# 
#*******************************************************************************
proc CALOspfAreaStubDisable {switch_name area_list} {
	  set number [llength $area_list]
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				      _ntgrOspfAreaStubDisable $switch_name $area_list
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#28.1*******************************************************************************
#
#  Function Name	: CALOspfStubsmmarylsaDisable
#
#  Description    : This function is an API used to disable summarylsa for stub area
#         
#  Usage          : CALOspfStubsmmarylsaDisable <switch_name> <area_list>
# 
#*******************************************************************************
proc CALOspfStubsmmarylsaDisable {switch_name area_list} {
	  set number [llength $area_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				      _ntgrOspfStubsummarylsaDisable $switch_name $area_list
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#28.2*******************************************************************************
#
#  Function Name	: CALOspfStubsmmarylsaEnable
#
#  Description    : This function is an API used to enable summarylsa for stub area
#         
#  Usage          : CALOspfStubsmmarylsaEnable <switch_name> <area_list>
# 
#*******************************************************************************
proc CALOspfStubsmmarylsaEnable {switch_name area_list} {
	  set number [llength $area_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				      _ntgrOspfStubsummarylsaEnable $switch_name $area_list
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#29*******************************************************************************
#
#  Function Name	: CALOspfInterfaceEnable
#
#  Description    : This function is an API used to enable ospf on interface
#         
#  Usage          : CALOspfInterfaceEnable <switch_name> <interface_list>
# 
#*******************************************************************************
proc CALOspfInterfaceEnable {switch_name interface_list} {
	  set number [llength $interface_list]
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				      _ntgrOspfInterfaceEnable $switch_name $interface_list
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#30*******************************************************************************
#
#  Function Name	: CALOspfInterfaceDisable
#
#  Description    : This function is an API used to disable ospf on interface
#         
#  Usage          : CALOspfInterfaceDisable <switch_name> <interface_list>
# 
#*******************************************************************************
proc CALOspfInterfaceDisable {switch_name interface_list} {
	  set number [llength $interface_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
				      _ntgrOspfInterfaceDisable $switch_name $interface_list
				  }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#31*******************************************************************************
#
#  Function Name	: CALOspfConfigAreaID
#
#  Description    : This function is an API used to configure area id for ospf
#         
#  Usage          : CALOspfConfigAreaID <switch_name> <interface_list>
# 
#*******************************************************************************
proc CALOspfConfigAreaID {switch_name interface_list} {
	  set number [llength $interface_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			        _ntgrOspfConfigAreaID $switch_name $interface_list
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#31-2*******************************************************************************
#
#  Function Name	: CALOspfConfigIntfAreaID
#
#  Description    : This function is an API used to configure interface area id for ospf
#         
#  Usage          : CALOspfConfigIntfAreaID <switch_name> <interface_list> <area_id>
# 
#*******************************************************************************
proc CALOspfConfigIntfAreaID {switch_name interface_list area_id} {
	  set number [llength $interface_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			        _ntgrOspfConfigIntfAreaID $switch_name $interface_list $area_id
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#31-3*******************************************************************************
#
#  Function Name	: CALOspfDeleteConfigIntfAreaID
#
#  Description    : This function is an API used to undo configure interface area id for ospf
#         
#  Usage          : CALOspfConfigIntfAreaID <switch_name> <interface_list> <area_id>
# 
#*******************************************************************************
proc CALOspfDeleteConfigIntfAreaID {switch_name interface_list} {
	  set number [llength $interface_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			        _ntgrOspfDeleteConfigIntfAreaID $switch_name $interface_list
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#32*******************************************************************************
#
#  Function Name	: CALOspfConfigIFProperty
#
#  Description    : This function is an API used to configure ospf property on 
#                   interface
#         
#  Usage          : CALOspfConfigIFProperty <switch_name> <interface_list>
# 
#*******************************************************************************
proc CALOspfConfigIFProperty {switch_name interface_list} {
	  set number [llength $interface_list]
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			        _ntgrOspfConfigIFProperty $switch_name $interface_list
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#33*******************************************************************************
#
#  Function Name	: CALOspfDeleteIFProperty
#
#  Description    : This function is an API used to delete ospf property on 
#                   interface
#         
#  Usage          : CALOspfDeleteIFProperty <switch_name> <interface_list>
# 
#*******************************************************************************
proc CALOspfDeleteIFProperty {switch_name interface_list} {
	  set number [llength $interface_list]
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			        _ntgrOspfDeleteIFProperty $switch_name $interface_list
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}


#34*******************************************************************************
#
#  Function Name	: CALOspfNSSAEnable
#
#  Description    : This function is an API used to enable NSSA
#         
#  Usage          : CALOspfNSSAEnable <switch_name> <nssa_list>
# 
#*******************************************************************************
proc CALOspfNSSAEnable {switch_name nssa_list} {
	  set number [llength $nssa_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			        _ntgrOspfNSSAEnable $switch_name $nssa_list
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#35*******************************************************************************
#
#  Function Name	: CALOspfNSSADisable
#
#  Description    : This function is an API used to disable NSSA
#         
#  Usage          : CALOspfNSSADisable <switch_name> <nssa_list>
# 
#*******************************************************************************
proc CALOspfNSSADisable {switch_name nssa_list} {
	  set number [llength $nssa_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			    _ntgrOspfNSSADisable $switch_name $nssa_list
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#36*******************************************************************************
#
#  Function Name	: CALOspfAddNSSAProperty
#
#  Description    : This function is an API used to add property for NSSA
#         
#  Usage          : CALOspfAddNSSAProperty <switch_name> <nssa_list>
# 
#*******************************************************************************
proc CALOspfAddNSSAProperty {switch_name nssa_list} {
	  set number [llength $nssa_list]  
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			    _ntgrOspfAddNSSAProperty $switch_name $nssa_list
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#37*******************************************************************************
#
#  Function Name	: CALOspfDeleteNSSAProperty
#
#  Description    : This function is an API used to delete property for NSSA
#         
#  Usage          : CALOspfDeleteNSSAProperty <switch_name> <nssa_list>
# 
#*******************************************************************************
proc CALOspfDeleteNSSAProperty {switch_name nssa_list} {
	  set number [llength $nssa_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			    _ntgrOspfDeleteNSSAProperty $switch_name $nssa_list
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#38*******************************************************************************
#
#  Function Name	: CALOspfVLinkEnable
#
#  Description    : This function is an API used to enable virtual link for OSPF
#         
#  Usage          : CALOspfVLinkEnable <switch_name> <vl_list>
# 
#*******************************************************************************
proc CALOspfVLinkEnable {switch_name vl_list} {
	  set number [llength $vl_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			    _ntgrOspfVLinkEnable $switch_name $vl_list  
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#39*******************************************************************************
#
#  Function Name	: CALOspfVLinkDisable
#
#  Description    : This function is an API used to disable virtual link for OSPF
#         
#  Usage          : CALOspfVLinkDisable <switch_name> <vl_list>
# 
#*******************************************************************************
proc CALOspfVLinkDisable {switch_name vl_list} {
	  set number [llength $vl_list]  
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			    _ntgrOspfVLinkDisable $switch_name $vl_list  
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#40*******************************************************************************
#
#  Function Name	: CALOspfAddVLinkProperty
#
#  Description    : This function is an API used to add property for virtual link
#         
#  Usage          : CALOspfAddVLinkProperty <switch_name> <vl_list>
# 
#*******************************************************************************
proc CALOspfAddVLinkProperty {switch_name vl_list} {
	  set number [llength $vl_list]  
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			    _ntgrOspfAddVLinkProperty $switch_name $vl_list  
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}

#41*******************************************************************************
#
#  Function Name	: CALOspfDeleteVLinkProperty
#
#  Description    : This function is an API used to delete property for virtual link
#         
#  Usage          : CALOspfDeleteVLinkProperty <switch_name> <vl_list>
# 
#*******************************************************************************
proc CALOspfDeleteVLinkProperty {switch_name vl_list} {
	  set number [llength $vl_list] 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$number != 0} {
			    _ntgrOspfDeleteVLinkProperty $switch_name $vl_list  
			    }
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
			}
		} 
}
#42*******************************************************************************
#
#  Function Name  : CALGetSequenceNumberOfOSPF
#
#  Description    : This function is an API used to get the sequence number of one lsa
#         
#  Usage          : CALGetSequenceNumberOfOSPF <switch_id> <area_id> <lsa_type>
#                                                <router_id> <adver_router>
# 
#*******************************************************************************
proc CALGetSequenceNumberOfOSPF {switch_name area_id lsa_type router_id adver_router} {
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
		    _ntgrGetSequenceNumberOfOSPF $switch_name $area_id $lsa_type $router_id $adver_router
		}
		cisco {
			puts "TODO\n"
		} 
		hp {
			puts "TODO\n"
		}
		3com {
			puts "TODO\n"
		} 
		default {
			Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
		}
	} 
}

#43*******************************************************************************
#
#  Function Name  : CALDeleteLoopBackInterface
#
#  Description    : This function is used to delete loopback interface
#
#  Usage          : CALDeleteLoopBackInterface <switch_name> <loopback_id>
#*******************************************************************************
proc CALDeleteLoopBackInterface {switch_name loopback_id} {
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
		    _ntgrDeleteLoopBackInterface $switch_name $loopback_id
		}
		cisco {
			puts "TODO\n"
		} 
		hp {
			puts "TODO\n"
		}
		3com {
			puts "TODO\n"
		} 
		default {
			Netgear_log_file "cal-ntgr-ospf.tcl" "Switch not defined"
		}
	} 
}