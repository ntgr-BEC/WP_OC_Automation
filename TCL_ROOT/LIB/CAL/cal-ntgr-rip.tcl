#!/bin/sh
################################################################################   
#
#  File Name		: cal-ntgr-rip.tcl
#
#  Description       	:
#         This TCL contains APIs to configure RIP. This is CLI 
#	    abstraction Layer for RIP configuration
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        24-may-06     Nina.cheng        Created    
#
#
#
################################################################################

#1*******************************************************************************
#
#  Function Name	: CALRipEnableSwitch
#
#  Description    : This function Enable RIP on a switch
#         
#  Usage          : CALRipEnableSwitch (switch flag) 
# 
#*******************************************************************************
proc CALRipEnableSwitch {switch flag} {

	set switch_vendor [ _get_switch_vendor_name  $switch ]	
	switch $switch_vendor {
	  netgear {
				 _ntgrRipEnable $switch $flag
			}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#2*******************************************************************************
#
#  Function Name	: CALRipDisableSwitch
#
#  Description    : This function Disable RIP on a switch
#         
#  Usage          : CALRipDisableSwitch (switch flag) 
# 
#*******************************************************************************
proc CALRipDisableSwitch { switch flag} {

	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			 _ntgrRipDisable $switch $flag
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#3*******************************************************************************
#
#  Function Name	: CALRipEnableAutoSummary
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipEnableAutoSummary (switch flag) 
# 
#*******************************************************************************
proc CALRipEnableAutoSummary {switch flag} {

	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear	{
	      _ntgrRipEnableAutoSummary $switch $flag
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#4*******************************************************************************
#
#  Function Name	: CALRipDisableAutoSummary
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipDisableAutoSummary (switch flag) 
# 
#*******************************************************************************
proc CALRipDisableAutoSummary { switch flag} {

	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			 _ntgrRipDisableAutoSummary $switch $flag
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#5*******************************************************************************
#
#  Function Name	: CALRipEnableDefaultinformation
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipEnableDefaultinformation (switch flag) 
# 
#*******************************************************************************
proc CALRipEnableDefaultinformation {switch flag} {

	set switch_vendor [ _get_switch_vendor_name  $switch]
	switch $switch_vendor {
		netgear {
		 		_ntgrRipEnableDefaultinformation $switch $flag  
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#6*******************************************************************************
#
#  Function Name	: CALRipDisableDefaultinformation
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipDisableDefaultinformation (switch flag) 
# 
#*******************************************************************************
proc CALRipDisableDefaultinformation { switch flag} {

	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			 _ntgrRipDisableDefaultinformation $switch $flag  
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#7*******************************************************************************
#
#  Function Name	: CALRipEnableHostRoutesAccept
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipEnableHostRoutesAccept (switch flag) 
# 
#*******************************************************************************
proc CALRipEnableHostRoutesAccept { switch flag} {

	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear {
				_ntgrRipEnableHostRoutesAccept $switch $flag
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#8*******************************************************************************
#
#  Function Name	: CALRipDisableHostRoutesAccept
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipDisableHostRoutesAccept (switch flag) 
# 
#*******************************************************************************
proc CALRipDisableHostRoutesAccept { switch flag} {
    
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			 _ntgrRipDisableHostRoutesAccept $switch $flag
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#9******************************************************************************
#
#  Function Name	: CALRipEnableDefaultMetric
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipEnableDefaultMetric (switch)(metric) 
# 
#*******************************************************************************
proc CALRipEnableDefaultMetric { switch metric} {

	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear {
		_ntgrRipEnableDefaultMetric $switch $metric
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}
#10*******************************************************************************
#
#  Function Name	: CALRipDisableDefaultMetric
#
#  Description    : This function is called to delete default metric for RIP
#         
#  Usage          : CALRipDisableDefaultMetric (switch metric) 
# 
#*******************************************************************************
proc CALRipDisableDefaultMetric { switch metric} {

	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			 _ntgrRipDisableDefaultMetric $switch $metric
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#11*******************************************************************************
#
#  Function Name	: CALRipEnableDistance
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipEnableDistance (switch distance) 
# 
#*******************************************************************************
proc CALRipEnableDistance { switch distance } {

	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear {
		    _ntgrEnableRipDistance $switch $distance
	  }		
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#12*******************************************************************************
#
#  Function Name	: CALRipDisableDistance
#
#  Description    : This function is called to delete distance for RIP
#         
#  Usage          : CALRipDisableDistance (switch distance) 
# 
#*******************************************************************************
proc CALRipDisableDistance { switch distance} {

	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			 _ntgrDisableRipDistance $switch $distance
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#13*******************************************************************************
#
#  Function Name	: CALRipEnableSplitHorizon
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipEnableSplitHorizon (switch split_horizon_mode ) 
# 
#*******************************************************************************
proc CALRipEnableSplitHorizon { switch split_horizon_mode } {

	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear {
		 	_ntgrRipEnableSplitHorizon $switch $split_horizon_mode
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#14*******************************************************************************
#
#  Function Name	: CALRipDisableSplitHorizon
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipDisableSplitHorizon (switch split_horizon_mode) 
# 
#*******************************************************************************
proc CALRipDisableSplitHorizon { switch split_horizon_mode} {

	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			 _ntgrRipDisableSplitHorizon $switch $split_horizon_mode
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#15*******************************************************************************
#
#  Function Name	: CALRipEnableDistributelistOut
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipEnableDistributelistOut (switch dis_list) 
# 
#*******************************************************************************
proc CALRipEnableDistributelistOut {switch dis_list} {
  set number [llength $dis_list] 
	set switch_vendor [ _get_switch_vendor_name  $switch ]	
	switch $switch_vendor {
		netgear {
		    if {$number != 0} {
			      _ntgrRipEnableDistributelistOut $switch $dis_list
			  }
		} 		
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#16*******************************************************************************
#
#  Function Name	: CALRipDisableDistributelistOut
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipDisableDistributelistOut (switch dis_list) 
# 
#*******************************************************************************
proc CALRipDisableDistributelistOut {switch dis_list} {
  set number [llength $dis_list] 
	set switch_vendor [ _get_switch_vendor_name  $switch ]	
	switch $switch_vendor {
		netgear {
		    if {$number != 0} {
			      _ntgrRipDisableDistributelistOut $switch $dis_list
			  }
		} 		
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	}
}

#17*******************************************************************************
#
#  Function Name	: CALRipEnableRedistribute
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipEnableRedistribute (switch redis_list) 
# 
#*******************************************************************************
proc CALRipEnableRedistribute { switch redis_list} {
  set number [llength $redis_list] 
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear {
		  if {$number != 0} {
			    _ntgrRipEnableRedistribute $switch $redis_list	
			}
		} 		
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	}
	
}
#18.1*******************************************************************************
#
#  Function Name	: CALRipDisableRedistributeProtocol
#
#  Description    : This function is called to disable redistribute route for rip
#         
#  Usage          : CALRipDisableRedistributeProtocol (switch redis_list) 
# 
#*******************************************************************************
proc CALRipDisableRedistributeProtocol { switch redis_list} {
  set number [llength $redis_list] 
	set switch_vendor [ _get_switch_vendor_name  $switch ]	
	switch $switch_vendor {
		netgear {
		    if {$number != 0} {
			      _ntgrRipDisableRedistributeProtocol $switch $redis_list		
			  }
		} 
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#18.2*******************************************************************************
#
#  Function Name	: CALRipDisableRedistributeMetric
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipDisableRedistributeMetric (switch redis_list) 
# 
#*******************************************************************************
proc CALRipDisableRedistributeMetric { switch redis_list} {
  set number [llength $redis_list] 
	set switch_vendor [ _get_switch_vendor_name  $switch ]	
	switch $switch_vendor {
		netgear {
		    if {$number != 0} {
			      _ntgrRipDisableRedistributeMetric $switch $redis_list		
			  }
		} 
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#18.3*******************************************************************************
#
#  Function Name	: CALRipDisableRedisOspfMatch
#
#  Description    : This function is called to configure RIP distance on a switch
#         
#  Usage          : CALRipDisableRedisOspfMatch (switch redis_list) 
# 
#*******************************************************************************
proc CALRipDisableRedisOspfMatch { switch redis_list} {
  set number [llength $redis_list] 
	set switch_vendor [ _get_switch_vendor_name  $switch ]	
	switch $switch_vendor {
		netgear {
		    if {$number != 0} {
			      _ntgrRipDisableRedisOspfMatch $switch $redis_list		
			  }
		} 
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}
#19*******************************************************************************
#
#  Function Name	: CALRipEnableInterface
#
#  Description    : This function is called to enable RIP on a interface of switch
#         
#  Usage          : CALRipEnableInterface (switch interface_property) 
# 
#*******************************************************************************
proc CALRipEnableInterface { switch interface_property} {
  set number [llength $interface_property] 
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
		  if {$number != 0} {
			 _ntgrRipEnableInterface $switch $interface_property
			}
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}
#20*******************************************************************************
#
#  Function Name	: CALRipDisableInterface
#
#  Description    : This function is called to disable RIP on a interface of switch
#         
#  Usage          : CALRipDisableInterface (switch logical_if) 
# 
#*******************************************************************************
proc CALRipDisableInterface { switch logical_if } {

  set number [llength $logical_if]
  
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
		    if {$number != 0} {
			      _ntgrRipDisableInterface $switch $logical_if
			  }
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}

#21*******************************************************************************
#
#  Function Name	: CALRipAddPropertyforLF
#
#  Description    : This function is called to add RIP property on a interface
#         
#  Usage          : CALRipAddPropertyforLF (switch interface_list) 
# 
#*******************************************************************************
proc CALRipAddPropertyforLF { switch interface_list} {
    set number [llength $interface_list]
	  set switch_vendor [ _get_switch_vendor_name  $switch ]
	  switch $switch_vendor {
	      netgear 	{
	          if {$number != 0} {
	      	      _ntgrRipAddPorpertyforLF $switch $interface_list
	      	  }
	      }
	      cisco	{
	      	puts "TODO\n"
	      }
	      hp	{
	      	puts "TODO\n"
	      }
	      3com	{
	      	puts "TODO\n"
	      }
	      default		{
	      	Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
	      }
	  } 
}

#22*******************************************************************************
#
#  Function Name	: CALRipDeletePropertyforLF
#
#  Description    : This function is called to disable RIP on a interface of switch
#         
#  Usage          : CALRipDeletePropertyforLF (switch interface_list) 
# 
#*******************************************************************************
proc CALRipDeletePropertyforLF { switch interface_list} {
  set number [llength $interface_list]
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
		   if {$number != 0} {
			 _ntgrRipDeletePorpertyforLF $switch $interface_list
			 }
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "cal-ntgr-rip.tcl" "Switch not defined"
		}
	} 
}