#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-vrrp.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for VRRP configuration.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        18/8/06      Nina Cheng        Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALVRRPEnable
#
#  Description    : This function is an API used to enable vrrp on global
#         
#  Usage          : CALVRRPEnable <switch_name> 
# 
#*******************************************************************************
proc CALVRRPEnable { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrVrrpEnable $switch_name 
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		} 
}
#*******************************************************************************
#
#  Function Name	: CALVRRPDisable
#
#  Description    : This function is an API used to disable vrrp on global
#         
#  Usage          : CALVRRPDisable <switch_name> 
# 
#*******************************************************************************
proc CALVRRPDisable { switch_name} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				 _ntgrVrrpDisable $switch_name 
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALAddVRRPVrid
#
#  Description    : This function is an API used to configure vrid on LIF
#         
#  Usage          : CALAddVRRPVrid <switch_name> <interface_list> <vrid_list> 
# 
#*******************************************************************************
proc CALAddVRRPVrid { switch_name interface_list vrid_list} {
	  
	  set connection_id [_get_switch_handle $switch_name] 	  
	  set LIF_list ""
	  set ph_int ""
	  set vl_int ""
	  set lf_int ""
	  
	  set interface_number [llength $interface_list] 
	  
	  for {set i 1} {$i <= $interface_number} {incr i} {
	        set j [expr $i - 1]
	        set vlan_index [lindex $interface_list $j]
		    set reg {^[0-9a-zA-Z]+$}
		    set mflag  [regexp $reg $vlan_index]
		    if {$mflag} {
  	    		lappend vl_int $vlan_index 
		    } else {
		    	lappend ph_int $vlan_index
		    }
   	 }
	set num [llength $vl_int]
	if {$num != 0} {set lf_int [_ntgrGetL3LogicalIf $connection_id $vl_int]}
	set LIF_list [concat $lf_int $ph_int]
   
	set LIF_number [llength $LIF_list]
	set vrid_number [llength $vrid_list]
	 
	set switch_vendor [_get_switch_vendor_name $switch_name]
		
		switch $switch_vendor {
			netgear {
			    if {$LIF_number != 0 && $vrid_number != 0} {
			        foreach LIF $LIF_list vrid $vrid_list {
			            _ntgrAddVrrpVrid $connection_id $LIF $vrid
			        }
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		}
		
}

#*******************************************************************************
#
#  Function Name	: CALDeleteVRRPVrid
#
#  Description    : This function is an API used to delete vrid on LIF
#         
#  Usage          : CALDeleteVRRPVrid <switch_name> <interface_list> <vrid_list> 
# 
#*******************************************************************************
proc CALDeleteVRRPVrid { switch_name interface_list vrid_list} {
	  
	  set connection_id [_get_switch_handle $switch_name] 	  
	  set LIF_list ""
	  set ph_int ""
	  set vl_int ""
	  set lf_int ""
	  
	  set interface_number [llength $interface_list] 
	  
	  for {set i 1} {$i<=$interface_number} {incr i} {
        set j [expr $i - 1]
        set vlan_index [lindex $interface_list $j]
		    set reg {^[0-9a-zA-Z]+$}
		    set mflag  [regexp $reg $vlan_index]
		    if {$mflag} {
  	    	lappend vl_int $vlan_index 
		    } else {
		      lappend ph_int $vlan_index
		    }
   }
   set num [llength $vl_int]
   if {$num != 0} {set lf_int [_ntgrGetL3LogicalIf $connection_id $vl_int]}
   set LIF_list [concat $lf_int $ph_int]
   
   set LIF_number [llength $LIF_list]
	 set vrid_number [llength $vrid_list]
	  
		set switch_vendor [_get_switch_vendor_name $switch_name]
		
		switch $switch_vendor {
			netgear {
			    if {$LIF_number != 0 && $vrid_number != 0} {
			        foreach LIF $LIF_list vrid $vrid_list {
			            _ntgrDeleteVrrpVrid $connection_id $LIF $vrid
			        }
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		}
		
}

#*******************************************************************************
#
#  Function Name	: CALAddVRRPVip
#
#  Description    : This function is an API used to add vip on LIF
#         
#  Usage          : CALAddVRRPVip <switch_name> <interface_list> <vrid_list> <VIP_list>
# 
#*******************************************************************************
proc CALAddVRRPVip { switch_name interface_list vrid_list VIP_list} {
    
	  set connection_id [_get_switch_handle $switch_name] 	  
	  set LIF_list ""
	  set ph_int ""
	  set vl_int ""
	  set lf_int ""
	  set reg {^[0-9a-zA-Z]+$}
	  foreach vlan_index $interface_list {
		    set mflag  [regexp $reg $vlan_index]
		    if {$mflag} {
  	    	lappend vl_int $vlan_index 
		    } else {
		      lappend ph_int $vlan_index
		    }
    }
    set num [llength $vl_int]
    if {$num != 0} {set lf_int [_ntgrGetL3LogicalIf $connection_id $vl_int]}
    set LIF_list [concat $lf_int $ph_int]
    
    set LIF_number [llength $LIF_list]
	  set vrid_number [llength $vrid_list]
	  set VIP_number [llength $VIP_list]
	  
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$LIF_number != 0 && $vrid_number != 0 && $VIP_number != 0} {
			        foreach LIF $LIF_list vrid $vrid_list vip $VIP_list {
			            set vip_first [lindex $vip 0]
			            set vip_second [lindex $vip 1]
				          _ntgrAddVrrpVip $connection_id $LIF $vrid $vip_first $vip_second
				      }
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		} 
		
}

#****************************************************************************************************
#
#  Function Name	: CALVRRPAuthenticationEnable
#
#  Description    : This function is an API used to enable vrrp authentication on LIF
#         
#  Usage          : CALVRRPAuthenticationEnable <switch_name> <interface_list> <vrid_list> <auth_mode_list>
# 
#****************************************************************************************************
proc CALVRRPAuthenticationEnable { switch_name interface_list vrid_list auth_mode_list } {
    
	  set connection_id [_get_switch_handle $switch_name] 	  
	  set LIF_list ""
	  set ph_int ""
	  set vl_int ""
	  set lf_int ""
	  
	  set interface_number [llength $interface_list] 
	  
	  for {set i 1} {$i<=$interface_number} {incr i} {
        set j [expr $i - 1]
        set vlan_index [lindex $interface_list $j]
		    set reg {^[0-9a-zA-Z]+$}
		    set mflag  [regexp $reg $vlan_index]
		    if {$mflag} {
  	    	lappend vl_int $vlan_index 
		    } else {
		      lappend ph_int $vlan_index
		    }
   }
   set num [llength $vl_int]
   if {$num != 0} {set lf_int [_ntgrGetL3LogicalIf $connection_id $vl_int]}
   set LIF_list [concat $lf_int $ph_int]
   
   set LIF_number [llength $LIF_list]
	 set vrid_number [llength $vrid_list]
	 set auth_number [llength $auth_mode_list]
	  
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$LIF_number != 0 && $vrid_number != 0 && $auth_number != 0} {
			        for {set i 1} {$i <= $interface_number} {incr i} {
			            set j [expr $i - 1 ]
			            set LIF [lindex $LIF_list $j]
			            set vrid [lindex $vrid_list $j]
			            set auth [lindex $auth_mode_list $j]
	                set auth_mode [lindex $auth 0]
	                set auth_key [lindex $auth 1]
	                if {$auth_mode != "default" } {
                      _ntgrVrrpAuthenticationEnable $connection_id $LIF $vrid $auth_mode $auth_key
                  } else {
                  continue 
                  }
               }
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		}
		 
}

#****************************************************************************************
#
#  Function Name	: CALVRRPAuthenticationDisable
#
#  Description    : This function is an API used to disable vrrp authentication on LIF
#         
#  Usage          : CALVRRPAuthenticationDisable <switch_name> <interface_list> <vrid_list> 
# 
#*****************************************************************************************
proc CALVRRPAuthenticationDisable { switch_name interface_list vrid_list} {

    
	  set connection_id [_get_switch_handle $switch_name] 	  
	  set LIF_list ""
	  set ph_int ""
	  set vl_int ""
	  set lf_int ""
	  
	  set interface_number [llength $interface_list] 
	  
	  for {set i 1} {$i<=$interface_number} {incr i} {
        set j [expr $i - 1]
        set vlan_index [lindex $interface_list $j]
		    set reg {^[0-9a-zA-Z]+$}
		    set mflag  [regexp $reg $vlan_index]
		    if {$mflag} {
  	    	lappend vl_int $vlan_index 
		    } else {
		      lappend ph_int $vlan_index
		    }
   }
   set num [llength $vl_int]
   if {$num != 0} {set lf_int [_ntgrGetL3LogicalIf $connection_id $vl_int]}
   set LIF_list [concat $lf_int $ph_int]
   
   set LIF_number [llength $LIF_list]
	 set vrid_number [llength $vrid_list]
	  
		set switch_vendor [_get_switch_vendor_name $switch_name]
		
		switch $switch_vendor {
			netgear {
			    if {$LIF_number != 0 && $vrid_number != 0} {
			        for {set i 1} {$i <= $interface_number} {incr i} {
			           set j [expr $i - 1 ]
			           set LIF [lindex $LIF_list $j]
			           set vrid [lindex $vrid_list $j]        
	               _ntgrVrrpAuthenticationDisable $connection_id $LIF $vrid
              }
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		}
		 
}

#*****************************************************************************************
#
#  Function Name	: CALVRRPPreemptEnable
#
#  Description    : This function is an API used to enable vrrp preempt on LIF
#         
#  Usage          : CALVRRPPreemptEnable <switch_name> <interface_list> <vrid_list> <pre_list> 
# 
#******************************************************************************************
proc CALVRRPPreemptEnable { switch_name interface_list vrid_list pre_list} {
    
    
	  set connection_id [_get_switch_handle $switch_name] 	  
	  set LIF_list ""
	  set ph_int ""
	  set vl_int ""
	  set lf_int ""
	  
	  set interface_number [llength $interface_list] 
	  
	  for {set i 1} {$i<=$interface_number} {incr i} {
        set j [expr $i - 1]
        set vlan_index [lindex $interface_list $j]
		    set reg {^[0-9a-zA-Z]+$}
		    set mflag  [regexp $reg $vlan_index]
		    if {$mflag} {
  	    	lappend vl_int $vlan_index 
		    } else {
		      lappend ph_int $vlan_index
		    }
   }
   set num [llength $vl_int]
   if {$num != 0} {set lf_int [_ntgrGetL3LogicalIf $connection_id $vl_int]}
   set LIF_list [concat $lf_int $ph_int]
   
   set LIF_number [llength $LIF_list]
	 set vrid_number [llength $vrid_list]
	 set pre_number [llength $pre_list]
	 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$LIF_number != 0 && $vrid_number != 0 && $pre_number != 0} {
			        for {set i 1} {$i <= $interface_number} {incr i} {
			            set j [expr $i - 1 ]
			            set LIF [lindex $LIF_list $j]
			            set vrid [lindex $vrid_list $j]
	                set pre_mode [lindex $pre_list $j]
	                if {$pre_mode != "default" } {
                      _ntgrVrrpPreemptEnable $connection_id $LIF $vrid
                  } else { 
                  continue
                  } 
              }
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		}
		 
}

#*******************************************************************************
#
#  Function Name	: CALVRRPPreemptDisable
#
#  Description    : This function is an API used to enable vrrp preempt on LIF
#         
#  Usage          : CALVRRPPreemptDisable <switch_name> <interface_list> <vrid_list> 
# 
#*******************************************************************************
proc CALVRRPPreemptDisable { switch_name interface_list vrid_list} {
	  
	  
	  set connection_id [_get_switch_handle $switch_name] 	  
	  set LIF_list ""
	  set ph_int ""
	  set vl_int ""
	  set lf_int ""
	  
	  set interface_number [llength $interface_list] 
	  
	  for {set i 1} {$i<=$interface_number} {incr i} {
        set j [expr $i - 1]
        set vlan_index [lindex $interface_list $j]
		    set reg {^[0-9a-zA-Z]+$}
		    set mflag  [regexp $reg $vlan_index]
		    if {$mflag} {
  	    	lappend vl_int $vlan_index 
		    } else {
		      lappend ph_int $vlan_index
		    }
   }
   set num [llength $vl_int]
   if {$num != 0} {set lf_int [_ntgrGetL3LogicalIf $connection_id $vl_int]}
   set LIF_list [concat $lf_int $ph_int]
   
   set LIF_number [llength $LIF_list]
	 set vrid_number [llength $vrid_list]
	 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$LIF_number != 0 && $vrid_number != 0} {
			        for {set i 1} {$i <= $interface_number} {incr i} {
			            set j [expr $i - 1 ]
			            set LIF [lindex $LIF_list $j]
			            set vrid [lindex $vrid_list $j]
			               _ntgrVrrpPreemptDisable $connection_id $LIF $vrid  
              }
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		}
		 
}

#****************************************************************************************
#
#  Function Name	: CALAddVrrpPriority
#
#  Description    : This function is an API used to add vrrp priority on LIF
#         
#  Usage          : CALAddVrrpPriority <switch_name> <interface_list> <vrid_list> <pri_list> 
# 
#****************************************************************************************
proc CALAddVrrpPriority { switch_name interface_list vrid_list pri_list} { 
    
   
	  set connection_id [_get_switch_handle $switch_name] 	  
	  set LIF_list ""
	  set ph_int ""
	  set vl_int ""
	  set lf_int ""
	  
	  set interface_number [llength $interface_list] 
	  
	  for {set i 1} {$i<=$interface_number} {incr i} {
        set j [expr $i - 1]
        set vlan_index [lindex $interface_list $j]
		    set reg {^[0-9a-zA-Z]+$}
		    set mflag  [regexp $reg $vlan_index]
		    if {$mflag} {
  	    	lappend vl_int $vlan_index 
		    } else {
		      lappend ph_int $vlan_index
		    }
   }
   set num [llength $vl_int]
   if {$num != 0} {set lf_int [_ntgrGetL3LogicalIf $connection_id $vl_int]}
   set LIF_list [concat $lf_int $ph_int]
   
   set LIF_number [llength $LIF_list]
	 set vrid_number [llength $vrid_list]
	 set pri_number [llength $pri_list]
	 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		
		switch $switch_vendor {
			netgear {
			    if {$LIF_number != 0 && $vrid_number != 0 && $pri_number != 0} {
			       for {set i 1} {$i <= $interface_number} {incr i} {
			         set j [expr $i - 1 ]
			         set LIF [lindex $LIF_list $j]
			         set vrid [lindex $vrid_list $j]
	             set pri_value [lindex $pri_list $j]
	             if {$pri_value != "default" } { 
                _ntgrAddVrrpPriority $connection_id $LIF $vrid $pri_value
               } else {
               continue  
               }
             }
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		}
		 
}
#*******************************************************************************
#
#  Function Name	: CALDeleteVrrpPriority
#
#  Description    : This function is an API used to delete vrrp priority on LIF
#         
#  Usage          : CALDeleteVrrpPriority <switch_name> <interface_list> <vrid_list> 
# 
#*******************************************************************************
proc CALDeleteVrrpPriority { switch_name interface_list vrid_list} {
    
   
	  set connection_id [_get_switch_handle $switch_name] 	  
	  set LIF_list ""
	  set ph_int ""
	  set vl_int ""
	  set lf_int ""
	  
	  set interface_number [llength $interface_list] 
	  
	  for {set i 1} {$i<=$interface_number} {incr i} {
        set j [expr $i - 1]
        set vlan_index [lindex $interface_list $j]
		    set reg {^[0-9a-zA-Z]+$}
		    set mflag  [regexp $reg $vlan_index]
		    if {$mflag} {
  	    	lappend vl_int $vlan_index 
		    } else {
		      lappend ph_int $vlan_index
		    }
   }
   set num [llength $vl_int]
   if {$num != 0} {set lf_int [_ntgrGetL3LogicalIf $connection_id $vl_int]}
   set LIF_list [concat $lf_int $ph_int]
   
	 set LIF_number [llength $LIF_list]
	 set vrid_number [llength $vrid_list]
	  
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$LIF_number != 0 && $vrid_number != 0} {
			       for {set i 1} {$i <= $interface_number} {incr i} {
			         set j [expr $i - 1 ]
			         set LIF [lindex $LIF_list $j]
			         set vrid [lindex $vrid_list $j]
			         _ntgrDeleteVrrpPriority $connection_id $LIF $vrid
				     }
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		}
		 
}

#***************************************************************************************************
#
#  Function Name	: CALAddVrrpAdvertiseTimer
#
#  Description    : This function is an API used to add vrrp timer advertised on LIF
#         
#  Usage          : CALAddVrrpAdvertiseTimer <switch_name> <interface_list> <Vrid_list> <Time_value_list> 
# 
#***************************************************************************************************
proc CALAddVrrpAdvertiseTimer { switch_name interface_list vrid_list Time_value_list} {
	  
	 
	  set connection_id [_get_switch_handle $switch_name] 	  
	  set LIF_list ""
	  set ph_int ""
	  set vl_int ""
	  set lf_int ""
	  
	  set interface_number [llength $interface_list] 
	  
	  for {set i 1} {$i<=$interface_number} {incr i} {
        set j [expr $i - 1]
        set vlan_index [lindex $interface_list $j]
		    set reg {^[0-9a-zA-Z]+$}
		    set mflag  [regexp $reg $vlan_index]
		    if {$mflag} {
  	    	lappend vl_int $vlan_index 
		    } else {
		      lappend ph_int $vlan_index
		    }
   }
   set num [llength $vl_int]
   if {$num != 0} {set lf_int [_ntgrGetL3LogicalIf $connection_id $vl_int]}
   set LIF_list [concat $lf_int $ph_int]
   
   set LIF_number [llength $LIF_list]
	 set vrid_number [llength $vrid_list]
	 set timer_number [llength $Time_value_list]
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
		      if {$LIF_number != 0 && $vrid_number != 0 && $timer_number != 0} {
			       for {set i 1} {$i <= $interface_number} {incr i} {
			         set j [expr $i - 1 ]
			         set LIF [lindex $LIF_list $j]
			         set vrid [lindex $vrid_list $j]
			         set timer [lindex $Time_value_list $j]
	             if {$timer != "default" } {                        
               _ntgrAddVrrpAdvertiseTimer $connection_id $LIF $vrid $timer
               } else {
               continue 
               }
             }
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		}
		 
}

#******************************************************************************************
#
#  Function Name	: CALDeleteVrrpAdvertiseTimer
#
#  Description    : This function is an API used to delete vrrp timer advertised on LIF
#         
#  Usage          : CALDeleteVrrpAdvertiseTimer <switch_name> <interface_list> <vrid_list> 
# 
#*******************************************************************************************
proc CALDeleteVrrpAdvertiseTimer { switch_name interface_list vrid_list} {
    
	  set connection_id [_get_switch_handle $switch_name] 	  
	  set LIF_list ""
	  set ph_int ""
	  set vl_int ""
	  set lf_int ""
	  
	  set interface_number [llength $interface_list] 
	  
	  for {set i 1} {$i<=$interface_number} {incr i} {
        set j [expr $i - 1]
        set vlan_index [lindex $interface_list $j]
		    set reg {^[0-9a-zA-Z]+$}
		    set mflag  [regexp $reg $vlan_index]
		    if {$mflag} {
  	    	lappend vl_int $vlan_index 
		    } else {
		      lappend ph_int $vlan_index
		    }
   }
   set num [llength $vl_int]
   if {$num != 0} {set lf_int [_ntgrGetL3LogicalIf $connection_id $vl_int]}
   set LIF_list [concat $lf_int $ph_int]
    
   set LIF_number [llength $LIF_list]
	 set vrid_number [llength $vrid_list]
	 
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    if {$LIF_number != 0 && $vrid_number != 0} {
			       for {set i 1} {$i <= $interface_number} {incr i} {
			         set j [expr $i - 1 ]
			         set LIF [lindex $LIF_list $j]
			         set vrid [lindex $vrid_list $j]
				         _ntgrDeleteVrrpAdvertiseTimer $connection_id $LIF $vrid
				     }
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		}
		 
}
#*********************************************************************************************************
#
#  Function Name	: CALVRRPErrorImformation
#
#  Description    : This function is an API used to judge if the variable is correct 
#         
#  Usage          : CALVRRPErrorImformation <switch_name> <vrid_number> <interface_number> <vip_number>  
#                                           <auth_number> <pre_number> <pri_number> <timer_number>  
# 
#***********************************************************************************************************
proc CALVRRPErrorImformation {switch_name vrid_number interface_number vip_number auth_number pre_number pri_number timer_number } {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
				_ntgrVrrpErrorInformation $vrid_number $interface_number $vip_number $auth_number $pre_number $pri_number $timer_number
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
				Netgear_log_file "cal-ntgr-vrrp.tcl" "Switch not defined"
			}
		} 
}


