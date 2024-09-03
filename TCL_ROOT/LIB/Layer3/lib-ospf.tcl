#!/bin/sh
################################################################################   
#
#  File Name		  : lib-ospf.tcl
#
#  Description      :
#         This TCL contains functions to configure OSPF
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        31-Aug-06     Nina Cheng        Created
#        31-Jan-12     Andy Xie          Modified some connect method, another reason 
#                                        is that some commands was been changed,
#                                        on 8.0.3 platform, vlan form is "0/4/1", 
#                                        on 9.0.x platform, vlan form is "vlan 100", the related function must be modify.
#
#
################################################################################
#1*******************************************************************************
#  Function Name	: _ntgrOspfEnable
#
#  Description    	: This function is called to enable OSPF on switch
#         
#  Usage          	: _ntgrOspfEnable <switch_name> <enable_flag>
#
#
#*******************************************************************************
proc _ntgrOspfEnable {switch_name enable_flag} {
  
  if {$enable_flag != "default"} {  

	  set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
          
    expect -i $connection_id -re "#"
 	  exp_send -i $connection_id "configure\r"
	  sleep 1
    exp_send -i $connection_id "router ospf\r"
    sleep 1
    exp_send -i $connection_id "enable\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"	  
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }   	      
	}
}

#2*******************************************************************************
#  Function Name	: _ntgrOspfDisable
#
#  Description    	: This function is called to disable OSPF on switch
#         
#  Usage          	: _ntgrOspfDisable <switch_name> <enable_flag>
#
#
#*******************************************************************************
proc _ntgrOspfDisable {switch_name enable_flag} {
  if {$enable_flag != "default"} {
    	 set connection_id [_get_switch_handle $switch_name]
       set bCnn 1
       if {[string first "exp" $connection_id] == -1} {
           Netgear_connect_switch $switch_name
           set connection_id [_get_switch_handle $switch_name]
           set bCnn 0
       }
  	   expect -i $connection_id -re "#"
       exp_send -i $connection_id "configure\r"
       sleep 1
       exp_send -i $connection_id "router ospf\r"
       sleep 1
       exp_send -i $connection_id "no enable\r"
       sleep 1
       exp_send -i $connection_id "exit\r"
       sleep 1
       exp_send -i $connection_id "exit\r"	
       if {$bCnn == 0} {
           Netgear_disconnect_switch $switch_name
      }      
   }
}

#3*******************************************************************************
#  Function Name	: _ntgrConfigureOspfRouterID
#
#  Description    	: This function is called to configure Router ID for OSPF on switch
#         
#  Usage          	: _ntgrConfigureOspfRouterID <switch_name> <router_id>
#
#
#*******************************************************************************
proc _ntgrConfigureOspfRouterID {switch_name router_id} {
  
  if {$router_id != "default"} {	    
	    
      set connection_id [_get_switch_handle $switch_name]
      set bCnn 1
      if {[string first "exp" $connection_id] == -1} {
          Netgear_connect_switch $switch_name
          set connection_id [_get_switch_handle $switch_name]
          set bCnn 0
      }	    
	    expect -i $connection_id -re "#"
   	  exp_send -i $connection_id "configure\r"
  	    sleep 1
 	    exp_send -i $connection_id "router ospf\r"
 	    sleep 1
 	    exp_send -i $connection_id "router-id $router_id\r"
 	    sleep 1
 	    exp_send -i $connection_id "exit\r"
 	    sleep 1
 	    exp_send -i $connection_id "exit\r"    
     if {$bCnn == 0} {
         Netgear_disconnect_switch $switch_name
     }  	    
	}
}

#4*******************************************************************************
#  Function Name	: _ntgr1583CompatibilityEnable
#
#  Description    	: This function is called to enable 1583 compatibility for OSPF on switch
#         
#  Usage          	: _ntgr1583CompatibilityEnable <switch_name> <enbale_falg>
#
#
#*******************************************************************************
proc _ntgr1583CompatibilityEnable {switch_name enbale_falg} {

  if {$enbale_falg != "default"} {	    
      set connection_id [_get_switch_handle $switch_name]
      set bCnn 1
      if {[string first "exp" $connection_id] == -1} {
          Netgear_connect_switch $switch_name
          set connection_id [_get_switch_handle $switch_name]
          set bCnn 0
      }
	    expect -i $connection_id -re "#"
   	  exp_send -i $connection_id "configure\r"
  	  sleep 1
 	    exp_send -i $connection_id "router ospf\r"
 	    sleep 1
 	    exp_send -i $connection_id "1583compatibility\r"
 	    sleep 1
 	    exp_send -i $connection_id "exit\r"
 	    sleep 1
 	    exp_send -i $connection_id "exit\r"	  
      if {$bCnn == 0} {
          Netgear_disconnect_switch $switch_name
      } 	      
	}
}

#5*******************************************************************************
#  Function Name	: _ntgr1583CompatibilityDisable
#
#  Description    	: This function is called to disable 1583 compatibility for OSPF on switch
#         
#  Usage          	: _ntgr1583CompatibilityDisable <switch_name> <enbale_falg>
#
#
#*******************************************************************************
proc _ntgr1583CompatibilityDisable {switch_name enbale_falg} {
    if {$enbale_falg != "default"} { 
      set connection_id [_get_switch_handle $switch_name]
      set bCnn 1
      if {[string first "exp" $connection_id] == -1} {
          Netgear_connect_switch $switch_name
          set connection_id [_get_switch_handle $switch_name]
          set bCnn 0
      }
	    expect -i $connection_id -re "#"
   	  exp_send -i $connection_id "configure\r"
  	    sleep 1
 	    exp_send -i $connection_id "router ospf\r"
 	    sleep 1
 	    exp_send -i $connection_id "no 1583compatibility\r"
 	    sleep 1
 	    exp_send -i $connection_id "exit\r"
 	    sleep 1
 	    exp_send -i $connection_id "exit\r"	    
      if {$bCnn == 0} {
          Netgear_disconnect_switch $switch_name
      } 	    
    }
}

#6*******************************************************************************
#  Function Name	: _ntgrAreaDefaultCost
#
#  Description    	: This function is called to configure area default cost for OSPF on switch
#         
#  Usage          	: _ntgrAreaDefaultCost <switch_name> <AreaDefaultCost_list>
#
#
#*******************************************************************************
proc _ntgrAreaDefaultCost {switch_name AreaDefaultCost_list} {
      
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	   
	   foreach Default_cost $AreaDefaultCost_list {	    
          set area_id [lindex $Default_cost 0]
          set cost [lindex $Default_cost 1]          
          expect -i $connection_id -re "#"
   	    exp_send -i $connection_id "configure\r"
  	       sleep 1
 	       exp_send -i $connection_id "router ospf\r"
 	       sleep 1
 	       exp_send -i $connection_id "area $area_id default-cost $cost\r"
 	       sleep 1
 	       exp_send -i $connection_id "exit\r"
 	       sleep 1
 	       exp_send -i $connection_id "exit\r"          
      }
      if {$bCnn == 0} {
          Netgear_disconnect_switch $switch_name
      }      	    
}

#7*******************************************************************************
#  Function Name	: _ntgrDefaultInformationOriginateEnable
#
#  Description    	: This function is called to advertise the default routes
#         
#  Usage          	: _ntgrDefaultInformationOriginateEnable <switch_name> <defaultInfo>
#
#
#*******************************************************************************
proc _ntgrDefaultInformationOriginateEnable {switch_name defaultInfo} {

    set enable_flag [lindex $defaultInfo 0]
    set mode [lindex $defaultInfo 1]
    set metric [lindex $defaultInfo 2]
    set metric_type [lindex $defaultInfo 3]     
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }    
    
    if {$enable_flag != "default" || $mode != "default" ||$metric != "default" || $metric_type != "default"} {
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router ospf\r"
        sleep 1
        if {$enable_flag != "default"} {	    
            exp_send -i $connection_id "default-information originate\r"
            sleep 1    
        }
        if {$mode != "default"} {
            exp_send -i $connection_id "default-information originate $mode\r"
            sleep 1   
        }
        if {$metric != "default"} {
            exp_send -i $connection_id "default-information originate  metric $metric\r"
            sleep 1
        }
        if {$metric_type != "default"} {
            exp_send -i $connection_id "default-information originate metric-type $metric_type\r"
            sleep 1  
        }
        exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"	  
    }

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }    
}

#8*******************************************************************************
#  Function Name	: _ntgrDefaultInformationOriginateDisable
#
#  Description    	: This function is called to withdraw the default routes
#         
#  Usage          	: _ntgrDefaultInformationOriginateDisable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrDefaultInformationOriginateDisable {switch_name} {
      
      set connection_id [_get_switch_handle $switch_name]
      set bCnn 1
      if {[string first "exp" $connection_id] == -1} {
          Netgear_connect_switch $switch_name
          set connection_id [_get_switch_handle $switch_name]
          set bCnn 0
      }
	    expect -i $connection_id -re "#"
   	  exp_send -i $connection_id "configure\r"
  	    sleep 1
 	    exp_send -i $connection_id "router ospf\r"
 	    sleep 1
 	    exp_send -i $connection_id "no default-information originate\r"
 	    sleep 1
 	    exp_send -i $connection_id "exit\r"
 	    sleep 1
 	    exp_send -i $connection_id "exit\r"	    
      if {$bCnn == 0} {
          Netgear_disconnect_switch $switch_name
      } 	    
}

#9*******************************************************************************
#  Function Name	: _ntgrAddDefaultMetric
#
#  Description    	: This function is called to add the default metric
#         
#  Usage          	: _ntgrAddDefaultMetric <switch_name> <metric>
#
#
#*******************************************************************************
proc _ntgrAddDefaultMetric {switch_name metric} {

      if {$metric != "default"} {          
	        set connection_id [_get_switch_handle $switch_name]
          set bCnn 1
          if {[string first "exp" $connection_id] == -1} {
              Netgear_connect_switch $switch_name
              set connection_id [_get_switch_handle $switch_name]
              set bCnn 0
          }       
	        expect -i $connection_id -re "#"
   	      exp_send -i $connection_id "configure\r"
  	      sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "default-metric $metric\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"	   
          if {$bCnn == 0} {
              Netgear_disconnect_switch $switch_name
          } 	             
	    }
}

#10*******************************************************************************
#  Function Name	: _ntgrDeleteDefaultMetric
#
#  Description    	: This function is called to delete the default metric
#         
#  Usage          	: _ntgrDeleteDefaultMetric <switch_name> <metric>
#
#
#*******************************************************************************
proc _ntgrDeleteDefaultMetric {switch_name metric} {
  if {$metric != "default"} { 
      set connection_id [_get_switch_handle $switch_name]
      set bCnn 1
      if {[string first "exp" $connection_id] == -1} {
          Netgear_connect_switch $switch_name
          set connection_id [_get_switch_handle $switch_name]
          set bCnn 0
      }
	    expect -i $connection_id -re "#"
   	  exp_send -i $connection_id "configure\r"
  	  sleep 1
 	    exp_send -i $connection_id "router ospf\r"
 	    sleep 1
 	    exp_send -i $connection_id "no default-metric\r"
 	    sleep 1
 	    exp_send -i $connection_id "exit\r"
 	    sleep 1
 	    exp_send -i $connection_id "exit\r"	 
      if {$bCnn == 0} {
          Netgear_disconnect_switch $switch_name
      } 	    
 	 }   
}

#11*******************************************************************************
#  Function Name	: _ntgrOspfDistanceEnable
#
#  Description    	: This function is called to configure the distance
#         
#  Usage          	: _ntgrOspfDistanceEnable <switch_name> <distance_list>
#
#
#*******************************************************************************
proc _ntgrOspfDistanceEnable {switch_name distance_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    foreach distance $distance_list {	    
        set type [lindex $distance 0]
        set prefrence [lindex $distance 1]
        
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router ospf\r"
        sleep 1
        exp_send -i $connection_id "distance ospf $type $prefrence\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"       
    }	 
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }         
}

#12*******************************************************************************
#  Function Name	: _ntgrOspfDistanceDisable
#
#  Description    	: This function is called to set the distance to default
#         
#  Usage          	: _ntgrOspfDistanceDisable <switch_name> <distance_list>
#
#
#*******************************************************************************
proc _ntgrOspfDistanceDisable {switch_name distance_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	   foreach distance $distance_list {
          set type [lindex $distance 0]         
          expect -i $connection_id -re "#"
   	    exp_send -i $connection_id "configure\r"
  	       sleep 1
 	       exp_send -i $connection_id "router ospf\r"
 	       sleep 1
 	       exp_send -i $connection_id "no distance ospf $type\r"
 	       sleep 1
 	       exp_send -i $connection_id "exit\r"
 	       sleep 1
 	       exp_send -i $connection_id "exit\r"          
      }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }        	    
}

#13*******************************************************************************
#  Function Name	: _ntgrOspfDistributeListOutEnable
#
#  Description    	: This function is called to configure the access list to filter
#                     routes received from the source protocol
#         
#  Usage          	: _ntgrOspfDistributeListOutEnable <switch_name> <distribute_list>
#
#
#*******************************************************************************
proc _ntgrOspfDistributeListOutEnable {switch_name distribute_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	   foreach distribute $distribute_list {    
          set acl [lindex $distribute 0]
          set protocol [lindex $distribute 1]
           
          expect -i $connection_id -re "#"
   	    exp_send -i $connection_id "configure\r"
  	       sleep 1
 	       exp_send -i $connection_id "router ospf\r"
 	       sleep 1
 	       exp_send -i $connection_id "distribute-list $acl out $protocol\r"
 	       sleep 1
 	       exp_send -i $connection_id "exit\r"
 	       sleep 1
 	       exp_send -i $connection_id "exit\r"          
      }	 
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }           
}

#14*******************************************************************************
#  Function Name	: _ntgrOspfDistributeListOutDisable
#
#  Description    	: This function is called to delete the access list to filter
#                     routes received from the source protocol
#         
#  Usage          	: _ntgrOspfDistributeListOutDisable <switch_name> <distribute_list>
#
#
#*******************************************************************************
proc _ntgrOspfDistributeListOutDisable {switch_name distribute_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	   foreach distribute $distribute_list {   
          set acl [lindex $distribute 0]
          set protocol [lindex $distribute 1]                  
           expect -i $connection_id -re "#"
   	     exp_send -i $connection_id "configure\r"
  	        sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "no distribute-list $acl out $protocol\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r" 
      }	   
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }         
}

#15*******************************************************************************
#  Function Name	: _ntgrOspfExitOverflowIntervalEnable
#
#  Description    	: This function is called to configure exit overflow interval
#         
#  Usage          	: _ntgrOspfExitOverflowIntervalEnable <switch_name> <interval>
#
#
#*******************************************************************************
proc _ntgrOspfExitOverflowIntervalEnable {switch_name interval} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    if {$interval != "default"} {
         expect -i $connection_id -re "#"
       exp_send -i $connection_id "configure\r"
          sleep 1
        exp_send -i $connection_id "router ospf\r"
        sleep 1
        exp_send -i $connection_id "exit-overflow-interval $interval\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
    }	 
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }           
}

#16*******************************************************************************
#  Function Name	: _ntgrOspfExitOverflowIntervalDisable
#
#  Description    	: This function is called to delete exit overflow interval
#         
#  Usage          	: _ntgrOspfExitOverflowIntervalDisable <switch_name> <interval>
#
#
#*******************************************************************************
proc _ntgrOspfExitOverflowIntervalDisable {switch_name interval} {
    
    if {$interval != "default"} {      
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 1
        if {[string first "exp" $connection_id] == -1} {
            Netgear_connect_switch $switch_name
            set connection_id [_get_switch_handle $switch_name]
            set bCnn 0
        }
       expect -i $connection_id -re "#"
   	  exp_send -i $connection_id "configure\r"
  	    sleep 1
 	    exp_send -i $connection_id "router ospf\r"
 	    sleep 1
 	    exp_send -i $connection_id "no exit-overflow-interval\r"
 	    sleep 1
 	    exp_send -i $connection_id "exit\r"
 	    sleep 1
 	    exp_send -i $connection_id "exit\r"	   
      if {$bCnn == 0} {
          Netgear_disconnect_switch $switch_name
      }   	    
    } 
}

#17*******************************************************************************
#  Function Name	: _ntgrOspfConfigExternalLSDBLimit
#
#  Description    	: This function is called to configure external LSDB limit
#         
#  Usage          	: _ntgrOspfConfigExternalLSDBLimit <switch_name> <limit>
#
#
#*******************************************************************************
proc _ntgrOspfConfigExternalLSDBLimit {switch_name limit} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
      if {$limit != "default"} {
           expect -i $connection_id -re "#"
   	     exp_send -i $connection_id "configure\r"
  	        sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "external-lsdb-limit $limit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
      }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }        	    
}

#18*******************************************************************************
#  Function Name	: _ntgrOspfDeleteExternalLSDBLimit
#
#  Description    	: This function is called to delete external LSDB limit
#         
#  Usage          	: _ntgrOspfDeleteExternalLSDBLimit <switch_name> <limit>
#
#
#*******************************************************************************
proc _ntgrOspfDeleteExternalLSDBLimit {switch_name limit} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
      if {$limit != "default"} {
          expect -i $connection_id -re "#"
   	    exp_send -i $connection_id "configure\r"
  	       sleep 1
 	       exp_send -i $connection_id "router ospf\r"
 	       sleep 1
 	       exp_send -i $connection_id "no external-lsdb-limit\r"
 	       sleep 1
 	       exp_send -i $connection_id "exit\r"
 	       sleep 1
 	       exp_send -i $connection_id "exit\r"
      }	    
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }        
}

#19*******************************************************************************
#  Function Name	: _ntgrOspfRedistributeEnable
#
#  Description    	: This function is called to enable the redistribution of routes 
#                     from the specified source protocol/routers
#         
#  Usage          	: _ntgrOspfRedistributeEnable <switch_name> <redistribute_list>
#
#
#*******************************************************************************
proc _ntgrOspfRedistributeEnable {switch_name redistribute_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    } 
      foreach redistribute_value $redistribute_list {     
          for {set k 1} {$k<=5} {incr k} {
              set m [expr $k - 1]
              set valute_$k [lindex $redistribute_value $m]
          }
          set cmd ""  
 			 set cmd "$cmd $valute_1"
 			 if { $valute_2 != "default" } {set cmd "$cmd metric $valute_2"}
 			 if { $valute_3 != "default" } {set cmd "$cmd metric-type $valute_3"}
 			 if { $valute_4 != "default" } {set cmd "$cmd tag $valute_4"}
 			 if { $valute_5 != "default" } {set cmd "$cmd $valute_5"}
 			    
          expect -i $connection_id -re "#"
   	    exp_send -i $connection_id "configure\r"
  	       sleep 1
 	       exp_send -i $connection_id "router ospf\r"
 	       sleep 1
 	       exp_send -i $connection_id "redistribute $cmd\r"
 	       sleep 1
 	       exp_send -i $connection_id "exit\r"
 	       sleep 1
 	       exp_send -i $connection_id "exit\r"
      }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }        	    
}

#20*******************************************************************************
#  Function Name	: _ntgrOspfRedistributeDisable
#
#  Description    	: This function is called to disable the redistribution of routes 
#                     from the specified source protocol/routers
#         
#  Usage          	: _ntgrOspfRedistributeDisable <switch_name> <redistribute_list>
#
#
#*******************************************************************************
proc _ntgrOspfRedistributeDisable {switch_name redistribute_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	   foreach redistribute_value $redistribute_list {
	    
         for {set k 1} {$k<=5} {incr k} {
              set m [expr $k - 1]
              set valute_$k [lindex $redistribute_value $m]
              
         }
         set cmd ""
   			 set cmd "$cmd $valute_1"
#   			 if { $valute_2 != "default" } {set cmd "$cmd metric"}
#   			 if { $valute_3 != "default" } {set cmd "$cmd metric-type"}
#   			 if { $valute_4 != "default" } {set cmd "$cmd tag"}
#   			 if { $valute_5 != "default" } {set cmd "$cmd subnets"}
   			 
   		   expect -i $connection_id -re "#"
   	     exp_send -i $connection_id "configure\r"
  	     exp_sleep 1
 	       exp_send -i $connection_id "router ospf\r"
 	       sleep 1
 	       exp_send -i $connection_id "no redistribute $cmd\r"
 	       sleep 1
 	       exp_send -i $connection_id "exit\r"
 	       sleep 1
 	       exp_send -i $connection_id "exit\r"
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }        	    
}

#21*******************************************************************************
#  Function Name	: _ntgrOspfMaxPathEnable
#
#  Description    	: This function is called to configure max paths
#         
#  Usage          	: _ntgrOspfMaxPathEnable <switch_name> <max_path>
#
#
#*******************************************************************************
proc _ntgrOspfMaxPathEnable {switch_name max_path} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
      if {$max_path != "default"} {
           expect -i $connection_id -re "#"
   	     exp_send -i $connection_id "configure\r"
  	        sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "maximum-paths $max_path\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
      }	  
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }          
}

#22*******************************************************************************
#  Function Name	: _ntgrOspfMaxPathDisable
#
#  Description    	: This function is called to delete max paths
#         
#  Usage          	: _ntgrOspfMaxPathDisable <switch_name> <max_path>
#
#
#*******************************************************************************
proc _ntgrOspfMaxPathDisable {switch_name max_path} {
    
    if {$max_path != "default"} {              
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 1
        if {[string first "exp" $connection_id] == -1} {
            Netgear_connect_switch $switch_name
            set connection_id [_get_switch_handle $switch_name]
            set bCnn 0
        }
        expect -i $connection_id -re "#"
     	  exp_send -i $connection_id "configure\r"
    	  sleep 1
   	    exp_send -i $connection_id "router ospf\r"
   	    sleep 1
   	    exp_send -i $connection_id "no maximum-paths\r"
   	    sleep 1
   	    exp_send -i $connection_id "exit\r"
   	    sleep 1
   	    exp_send -i $connection_id "exit\r"	 
    } 
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }        
}

#23*******************************************************************************
#  Function Name	: _ntgrOspfTrapEnable
#
#  Description    	: This function is called to enable trap 
#         
#  Usage          	: _ntgrOspfTrapEnable <switch_name> <trap_flag>
#
#
#*******************************************************************************
proc _ntgrOspfTrapEnable {switch_name trap_flag} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    if {$trap_flag != "default"} {
         expect -i $connection_id -re "#"
       exp_send -i $connection_id "configure\r"
          sleep 1
        exp_send -i $connection_id "router ospf\r"
        sleep 1
        exp_send -i $connection_id "trapflags all\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
    }	  
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }          
}

#24*******************************************************************************
#  Function Name	: _ntgrOspfTrapDisable
#
#  Description    	: This function is called to disable trap 
#         
#  Usage          	: _ntgrOspfTrapDisable <switch_name> <trap_flag>
#
#
#*******************************************************************************
proc _ntgrOspfTrapDisable {switch_name trap_flag} {
    
    if {$trap_flag != "default"} {
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 1
        if {[string first "exp" $connection_id] == -1} {
            Netgear_connect_switch $switch_name
            set connection_id [_get_switch_handle $switch_name]
            set bCnn 0
        }
        expect -i $connection_id -re "#"
     	  exp_send -i $connection_id "configure\r"
    	  sleep 1
   	    exp_send -i $connection_id "router ospf\r"
   	    sleep 1
   	    exp_send -i $connection_id "no trapflags all\r"
   	    sleep 1
   	    exp_send -i $connection_id "exit\r"
         sleep 1
   	    exp_send -i $connection_id "exit\r"	  
      if {$bCnn == 0} {
          Netgear_disconnect_switch $switch_name
      }   	      
   }
}

#25*******************************************************************************
#  Function Name	: _ntgrOspfAreaRangeEnable
#
#  Description    	: This function is called to configure specified area range for 
#                     a specified NSSA 
#         
#  Usage          	: _ntgrOspfAreaRangeEnable <switch_name> <range_list>
#
#
#*******************************************************************************
proc _ntgrOspfAreaRangeEnable {switch_name range_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	    foreach range $range_list {
           for {set k 1} {$k <= 4} {incr k} {
               set m [expr $k - 1]
               set value_$k [lindex $range $m]
           }
           if {$value_4 == "default"} {set value_4 ""}
           expect -i $connection_id -re "#"
   	     exp_send -i $connection_id "configure\r"
  	        sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "area $value_1 range $value_2 $value_3 $value_4\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
          
      }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }        	    
}

#26*******************************************************************************
#  Function Name	: _ntgrOspfAreaRangeDisable
#
#  Description    	: This function is called to delete specified area range for 
#                     a specified NSSA 
#         
#  Usage          	: _ntgrOspfAreaRangeDisable <switch_name> <range_list>
#
#
#*******************************************************************************
proc _ntgrOspfAreaRangeDisable {switch_name range_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	   foreach range $range_list {	    
	       for {set k 1} {$k <= 3} {incr k} {
              set m [expr $k - 1]
              set value_$k [lindex $range $m]
          }          
           expect -i $connection_id -re "#"
   	     exp_send -i $connection_id "configure\r"
  	        sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "no area $value_1 range $value_2 $value_3\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"         
      }	   
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }         
}

#27*******************************************************************************
#  Function Name	: _ntgrOspfAreaStubEnable
#
#  Description    	: This function is called to configure stub area
#         
#  Usage          	: _ntgrOspfAreaStubEnable <switch_name> <area_list>
#
#
#*******************************************************************************
proc _ntgrOspfAreaStubEnable {switch_name area_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	    foreach stub_area $area_list {         
           set area_id [lindex $stub_area 0]
	        set summary_flag [lindex $stub_area 1]
	        
	        expect -i $connection_id -re "#"
   	     exp_send -i $connection_id "configure\r"
  	        sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "area $area_id stub\r"
 	        sleep 1
 	        if {$summary_flag != "default"} {
 	            exp_send -i $connection_id "area $area_id stub no-summary\r"
 	            sleep 1
 	        }
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
      } 
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }        	    
}

#28*******************************************************************************
#  Function Name	: _ntgrOspfAreaStubDisable
#
#  Description    	: This function is called to delete stub area
#         
#  Usage          	: _ntgrOspfAreaStubDisable <switch_name> <area_list>
#
#
#*******************************************************************************
proc _ntgrOspfAreaStubDisable {switch_name area_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    foreach stub_area $area_list {	             
        set area_id [lindex $stub_area 0]
        set summary_flag [lindex $stub_area 1]
        	        
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router ospf\r"
        sleep 1
        exp_send -i $connection_id "no area $area_id stub\r"
        sleep 1
        if {$summary_flag != "default"} {
            exp_send -i $connection_id "no area $area_id stub no-summary\r"
            sleep 1
        }
        exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"	        
    } 	 
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }           
}

#28.1****************************************************************************
#  Function Name	: _ntgrOspfStubsummarylsaDisable
#
#  Description    	: This function is called to disable summarylsa for stub area(totally stub)
#         
#  Usage          	: _ntgrOspfStubsummarylsaDisable <switch_name> <area_list>
#
#
#*******************************************************************************
proc _ntgrOspfStubsummarylsaDisable {switch_name area_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	    foreach stub_area $area_list {	              
          set area_id [lindex $stub_area 0]
	        
	        expect -i $connection_id -re "#"
   	      exp_send -i $connection_id "configure\r"
  	      sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "area $area_id stub no-summary\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"	        
      }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }         	    
}

#28.2****************************************************************************
#  Function Name	: _ntgrOspfStubsummarylsaEnable
#
#  Description    	: This function is called to enable summarylsa for stub area
#         
#  Usage          	: _ntgrOspfStubsummarylsaEnable <switch_name> <area_list>
#
#
#*******************************************************************************
proc _ntgrOspfStubsummarylsaEnable {switch_name area_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	    foreach stub_area $area_list {	              
          set area_id [lindex $stub_area 0]
	        
	        expect -i $connection_id -re "#"
   	      exp_send -i $connection_id "configure\r"
  	      sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "no area $area_id stub no-summary\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"	        
      }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }         	    
}

#29*******************************************************************************
#  Function Name	: _ntgrOspfInterfaceEnable
#
#  Description    	: This function is called to enable ospf on interface
#         
#  Usage          	: _ntgrOspfInterfaceEnable <switch_name> <interface_list>
#
#
#*******************************************************************************
proc _ntgrOspfInterfaceEnable {switch_name interface_list} {
      
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set vlan_index_list ""
    set ph_inter_list ""
    set vlan_id_list ""
    foreach interface $interface_list {
        set member [llength $interface]
        set k 1
        set reg {^[0-9a-zA-Z]+$}
        while {$k< $member} {
            set member_int [lindex $interface $k]
            set VLAN_NAME [lindex $member_int 0]
            set mflag  [regexp $reg $VLAN_NAME]
            if {$mflag} {lappend vlan_index_list $VLAN_NAME
            } else {lappend ph_inter_list $VLAN_NAME }
            incr k
	      }
    }
    
    set num1 [llength $vlan_index_list]
    
    if {$num1 != 0} {set vlan_id_list [_ntgrGetL3LogicalIf $connection_id $vlan_index_list]}
    
    set total_inter [concat $vlan_id_list $ph_inter_list]
    	    
    foreach LF_int $total_inter {
    
        expect -i $connection_id -re "#"
 	     exp_send -i $connection_id "configure\r"
	        sleep 1
        exp_send -i $connection_id "interface $LF_int\r"
        sleep 1
        exp_send -i $connection_id "ip ospf\r"
        sleep 1
	     exp_send -i $connection_id "exit\r" 
        sleep 1
        exp_send -i $connection_id "exit\r"           
    }	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }	        
}

#30*******************************************************************************
#  Function Name	: _ntgrOspfInterfaceDisable
#
#  Description    	: This function is called to disable ospf on interface
#         
#  Usage          	: _ntgrOspfInterfaceDisable <switch_name> <interface_list>
#
#
#*******************************************************************************
proc _ntgrOspfInterfaceDisable {switch_name interface_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set vlan_index_list ""
    set ph_inter_list ""
    set vlan_id_list ""
    
    foreach interface $interface_list {
        set member [llength $interface] 
       set k 1
       set reg {^[0-9a-zA-Z]+$}
       while {$k< $member} {
           set member_int [lindex $interface $k]
           set VLAN_NAME [lindex $member_int 0]
           set mflag  [regexp $reg $VLAN_NAME]
           if {$mflag} {lappend vlan_index_list $VLAN_NAME
           } else {lappend ph_inter_list $VLAN_NAME }
           incr k
	    }	        
    }
    set num1 [llength $vlan_index_list]
    
    if {$num1 != 0} {set vlan_id_list [_ntgrGetL3LogicalIf $connection_id $vlan_index_list]}
    set total_inter [concat $vlan_id_list $ph_inter_list]
    
    foreach LF_int $total_inter {	    
        expect -i $connection_id -re "#"
 	     exp_send -i $connection_id "configure\r"
	        sleep 1
        exp_send -i $connection_id "interface $LF_int\r"
        sleep 1
        exp_send -i $connection_id "no ip ospf\r"
        sleep 1
	     exp_send -i $connection_id "exit\r" 
        sleep 1
        exp_send -i $connection_id "exit\r"           
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }	    
}

#31*******************************************************************************
#  Function Name	: _ntgrOspfConfigAreaID
#
#  Description    	: This function is called to configure areaid for ospf interface
#         
#  Usage          	: _ntgrOspfConfigAreaID <switch_name> <interface_list>
#
#
#*******************************************************************************
proc _ntgrOspfConfigAreaID {switch_name interface_list} {
      
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set vlan_index_list ""
    set ph_inter_list ""
    set vlan_id_list ""
    
    foreach interface $interface_list {
       
       set member [llength $interface]
       set area_id [lindex $interface 0]
       set k 1
       set reg {^[0-9a-zA-Z]+$}
       while {$k< $member} {
        
           set member_int [lindex $interface $k]
           set VLAN_NAME [lindex $member_int 0]
           set mflag  [regexp $reg $VLAN_NAME]
           
           if {$mflag} {
               lappend vlan_index_list $VLAN_NAME
               array set VA "$VLAN_NAME $area_id"
           } else {
               lappend ph_inter_list $VLAN_NAME
               array set VA "$VLAN_NAME $area_id" 
           }
           incr k
	     }	         
    }
    set num1 [llength $vlan_index_list]
    
    if {$num1 != 0} {set vlan_id_list [_ntgrGetL3LogicalIf $connection_id $vlan_index_list]}
    
    set total_inter [concat $vlan_id_list $ph_inter_list]	    
    set total_name [concat $vlan_index_list $ph_inter_list]
    
    foreach LF_int $total_inter int_name $total_name {
    
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "interface $LF_int\r"
        sleep 1
        exp_send -i $connection_id "ip ospf\r"
        sleep 1
        exp_send -i $connection_id "ip ospf area $VA($int_name)\r"
        sleep 1
        exp_send -i $connection_id "exit\r" 
        sleep 1
        exp_send -i $connection_id "exit\r"      
    }	    
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }	    
}

#31-2*******************************************************************************
#  Function Name	: _ntgrOspfConfigIntfAreaID
#
#  Description    	: This function is called to configure areaid for ospf interface
#         
#  Usage          	: _ntgrOspfConfigIntfAreaID <switch_name> <interface_list> <area_id>
#
#
#*******************************************************************************
proc _ntgrOspfConfigIntfAreaID {switch_name interface_list area_id} {

	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    expect -i $cnn_id "#" {}  
    append ret $expect_out(buffer) 
    foreach sIntf $interface_list {
        exp_send -i $cnn_id "interface $sIntf\r"
        exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)  
        exp_send -i $cnn_id "ip ospf area $area_id\r"
        exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)
      	exp_send -i $cnn_id "exit\r"
      	exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)                   
    }    
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#31-3*******************************************************************************
#  Function Name	: _ntgrOspfDeleteConfigIntfAreaID
#
#  Description    	: This function is called to undo configure areaid for ospf interface
#         
#  Usage          	: _ntgrOspfDeleteConfigIntfAreaID <switch_name> <interface_list>
#
#
#*******************************************************************************
proc _ntgrOspfDeleteConfigIntfAreaID {switch_name interface_list} {

	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    expect -i $cnn_id "#" {}  
    append ret $expect_out(buffer) 
    foreach sIntf $interface_list {
        exp_send -i $cnn_id "interface $sIntf\r"
        exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)  
        exp_send -i $cnn_id "no ip ospf area\r"
        exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)
      	exp_send -i $cnn_id "exit\r"
      	exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)                   
    }    
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#32*******************************************************************************
#  Function Name	: _ntgrOspfConfigIFProperty
#
#  Description    	: This function is called to configure property on ospf interface
#         
#  Usage          	: _ntgrOspfConfigIFProperty <switch_name> <interface_list>
#
#
#*******************************************************************************
proc _ntgrOspfConfigIFProperty {switch_name interface_list} {
      
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set vlan_index_list ""
    set ph_inter_list ""
    set PROph_list ""
    set property_list ""
    set vlan_id_list ""
    
    foreach interface $interface_list {
        set member [llength $interface]        
        set k 1
        set reg {^[0-9a-zA-Z]+$}
        while {$k< $member} {
            set member_int [lindex $interface $k]
            set VLAN_NAME [lindex $member_int 0]
            set mflag  [regexp $reg $VLAN_NAME]
            if {$mflag} {
                set IF_PROPERTY [lindex $member_int 1]
                if {$IF_PROPERTY != "IF_DefaultProperty"} {
                    lappend vlan_index_list $VLAN_NAME
                    lappend property_list $IF_PROPERTY
                }
            } else {
                set IF_PROPERTY [lindex $member_int 1]
                if {$IF_PROPERTY != "IF_DefaultProperty"} {
                    lappend ph_inter_list $VLAN_NAME
                    lappend PROph_list $IF_PROPERTY
                }
            }
            incr k
	      }	         
    }
    set num1 [llength $vlan_index_list]	
      
#    if {$num1 != 0} {set vlan_id_list [_ntgrGetL3LogicalIf $connection_id $vlan_index_list]}
    if {$num1 != 0} {
      foreach vlan_index $vlan_index_list {
        lappend vlan_id_list [concat vlan [_getVlanID $vlan_index]]
      }      
    }
    
    set total_inter [concat $vlan_id_list $ph_inter_list]
    set total_prop [concat $property_list $PROph_list]
    foreach LF_int $total_inter LF_property $total_prop {
    
        set auth [getOspfAuthforInterface $LF_property]
	          set auth_mode [lindex $auth 0]
	          set auth_key [lindex $auth 1]
	          set auth_keyid [lindex $auth 2]
	      set cost [getOspfCostforInterface $LF_property]
	      set deadinterval [getOspfDeadintervalforInterface $LF_property]
	      set hellointerval [getOspfHelloIntervalforInterface $LF_property]
	      set priority [getOspfPriorityforInterface $LF_property]
	      set retransmit_interval [getOspfRetrIntervalforInterface $LF_property]
	      set transmit_delay [getOspfTransDelayforInterface $LF_property]		              
	      set mtu_ignore [getOspfMtuIgnoreStatusforInterface $LF_property]
	      
	     expect -i $connection_id -re "#" 
	     exp_send -i $connection_id "configure\r"
	        sleep 1
        exp_send -i $connection_id "interface $LF_int\r"
        sleep 1
        if {$auth_mode != "default"} {
            exp_send -i $connection_id "ip ospf authentication $auth_mode $auth_key $auth_keyid\r"
            sleep 1
        }
        if {$cost != "default"} {
            exp_send -i $connection_id "ip ospf cost $cost\r"
            sleep 1
        }
        if {$deadinterval != "default"} {
            exp_send -i $connection_id "ip ospf dead-interval $deadinterval\r"
            sleep 1
        }
        if {$hellointerval != "default"} {
            exp_send -i $connection_id "ip ospf hello-interval $hellointerval\r"
            sleep 1
        }
        if {$priority != "default"} {
            exp_send -i $connection_id "ip ospf priority $priority\r"
            sleep 1
        }
        if {$retransmit_interval != "default"} {
            exp_send -i $connection_id "ip ospf retransmit-interval $retransmit_interval\r"
            sleep 1
        }
        if {$transmit_delay != "default"} {
            exp_send -i $connection_id "ip ospf transmit-delay $transmit_delay\r"
            sleep 1
        }
        if {$mtu_ignore != "default"} {
            exp_send -i $connection_id "ip ospf mtu-ignore\r"
            sleep 1
        }
	      exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
    }	  
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }      
}
#33*******************************************************************************
#  Function Name	: _ntgrOspfDeleteIFProperty
#
#  Description    	: This function is called to delete property on ospf interface
#         
#  Usage          	: _ntgrOspfDeleteIFProperty <switch_name> <interface_list>
#
#
#*******************************************************************************
proc _ntgrOspfDeleteIFProperty {switch_name interface_list} {
      
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
      set vlan_index_list ""
      set ph_inter_list ""
      set PROph_list ""
      set property_list ""
      set vlan_id_list ""
      
      foreach interface $interface_list {
           set member [llength $interface]	        
	        set k 1
	        set reg {^[0-9a-zA-Z]+$}
	        while {$k< $member} {
	            set member_int [lindex $interface $k]
	            set VLAN_NAME [lindex $member_int 0]
	            set mflag  [regexp $reg $VLAN_NAME]
	            if {$mflag} {
	                set IF_PROPERTY [lindex $member_int 1]
	                if {$IF_PROPERTY != "IF_DefaultProperty"} {
	                    lappend vlan_index_list $VLAN_NAME
	                    lappend property_list $IF_PROPERTY
	                }
	            } else {
	                set IF_PROPERTY [lindex $member_int 1]
	                if {$IF_PROPERTY != "IF_DefaultProperty"} {
	                    lappend ph_inter_list $VLAN_NAME
	                    lappend PROph_list $IF_PROPERTY
	                }
	            }
	            incr k
		      }	        
	    }
	    set num1 [llength $vlan_index_list]	    
	    if {$num1 != 0} {set vlan_id_list [_ntgrGetL3LogicalIf $connection_id $vlan_index_list]}	    
	    set total_inter [concat $vlan_id_list $ph_inter_list]
	    set total_prop [concat $property_list $PROph_list]
	    foreach LF_int $total_inter LF_property $total_prop {
	        set auth [getOspfAuthforInterface $LF_property]
		          set auth_mode [lindex $auth 0]
		          set auth_key [lindex $auth 1]
		          set auth_keyid [lindex $auth 2]
		      set cost [getOspfCostforInterface $LF_property]
		      set deadinterval [getOspfDeadintervalforInterface $LF_property]
		      set hellointerval [getOspfHelloIntervalforInterface $LF_property]
		      set priority [getOspfPriorityforInterface $LF_property]
		      set retransmit_interval [getOspfRetrIntervalforInterface $LF_property]
		      set transmit_delay [getOspfTransDelayforInterface $LF_property]		              
		      set mtu_ignore [getOspfMtuIgnoreStatusforInterface $LF_property]
		      
		     expect -i $connection_id -re "#" 
		     exp_send -i $connection_id "configure\r"
  	        sleep 1
 	        exp_send -i $connection_id "interface $LF_int\r"
 	        sleep 1
 	        if {$auth_mode != "default"} {
 	            exp_send -i $connection_id "no ip ospf authentication\r"
 	            sleep 1
 	        }
 	        if {$cost != "default"} {
 	            exp_send -i $connection_id "no ip ospf cost\r"
 	            sleep 1
 	        }
 	        if {$deadinterval != "default"} {
 	            exp_send -i $connection_id "no ip ospf dead-interval\r"
 	            sleep 1
 	        }
 	        if {$hellointerval != "default"} {
 	            exp_send -i $connection_id "no ip ospf hello-interval\r"
 	            sleep 1
 	        }
 	        if {$priority != "default"} {
 	            exp_send -i $connection_id "no ip ospf priority\r"
 	            sleep 1
 	        }
 	        if {$retransmit_interval != "default"} {
 	            exp_send -i $connection_id "no ip ospf retransmit-interval\r"
 	            sleep 1
 	        }
 	        if {$transmit_delay != "default"} {
 	            exp_send -i $connection_id "no ip ospf transmit-delay\r"
 	            sleep 1
 	        }
 	        if {$mtu_ignore != "default"} {
 	            exp_send -i $connection_id "no ip ospf mtu-ignore\r"
 	            sleep 1
 	        }
		     exp_send -i $connection_id "exit\r"   
		     sleep 1
		     exp_send -i $connection_id "exit\r"   
	    }	  
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	      
}

#34*******************************************************************************
#  Function Name	: _ntgrOspfNSSAEnable
#
#  Description    	: This function is called to configure area as NSSA
#         
#  Usage          	: _ntgrOspfNSSAEnable <switch_name> <nssa_list>
#
#
#*******************************************************************************
proc _ntgrOspfNSSAEnable {switch_name nssa_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	    foreach nssa_member $nssa_list {	    
	        set nssa_areaid [lindex $nssa_member 0]
	        expect -i $connection_id -re "#"
   	     exp_send -i $connection_id "configure\r"
  	        sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "area $nssa_areaid nssa\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"	        
	    }	  
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	      
}

#35*******************************************************************************
#  Function Name	: _ntgrOspfNSSADisable
#
#  Description    	: This function is called to delete NSSA area
#         
#  Usage          	: _ntgrOspfNSSADisable <switch_name> <nssa_list>
#
#
#*******************************************************************************
proc _ntgrOspfNSSADisable {switch_name nssa_list} {
      
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
      foreach nssa_member $nssa_list {    
	        set nssa_areaid [lindex $nssa_member 0]	        
	        expect -i $connection_id -re "#"
   	     exp_send -i $connection_id "configure\r"
  	        sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "no area $nssa_areaid nssa\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"	        
	    }    
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	    
}

#36*******************************************************************************
#  Function Name	: _ntgrOspfAddNSSAProperty
#
#  Description    	: This function is called to configure property for NSSA
#         
#  Usage          	: _ntgrOspfAddNSSAProperty <switch_name> <nssa_list>
#
#
#*******************************************************************************
proc _ntgrOspfAddNSSAProperty {switch_name nssa_list} {
          
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    foreach nssa_member $nssa_list {
        set nssa_areaid [lindex $nssa_member 0]
        set nssa_property [lindex $nssa_member 1]
        
        if {$nssa_property != "NSSADefaultPorperty"} {  
            set defaultinfor [getOspfDefaultInforNSSA $nssa_property]
                set enable_flag [lindex $defaultinfor 0]
                set metric [lindex $defaultinfor 1]
                set mode [lindex $defaultinfor 2]
            set nodistribute [getOspfNodistributeforNSSA $nssa_property]
            set nosummary [getOspfNoSummaryforNSSA $nssa_property]
            set transrole [getOspfTransRoleforNSSA $nssa_property]
            set transstabint [getOspfTransStabIntvforNSSA $nssa_property]	        
            
            expect -i $connection_id -re "#"
           exp_send -i $connection_id "configure\r"
              sleep 1
            exp_send -i $connection_id "router ospf\r"
            sleep 1
            if {$enable_flag != "default"} {
                exp_send -i $connection_id "area $nssa_areaid nssa default-info-originate\r"
                sleep 1
            }
            if {$metric != "default"} {
                exp_send -i $connection_id "area $nssa_areaid nssa default-info-originate $metric\r"
                sleep 1
            }
            if {$mode != "default"} {
                exp_send -i $connection_id "area $nssa_areaid nssa default-info-originate $metric $mode\r"
                sleep 1
            }
            if {$nodistribute != "default"} {
                exp_send -i $connection_id "area $nssa_areaid nssa no-redistribute\r"
                sleep 1
            }
            if {$nosummary != "default"} {
                exp_send -i $connection_id "area $nssa_areaid nssa no-summary\r"
                sleep 1
            }
            if {$transrole != "default"} {
                exp_send -i $connection_id "area $nssa_areaid nssa translator-role $transrole\r"
                sleep 1
            }
            if {$transstabint != "default"} {
                exp_send -i $connection_id "area $nssa_areaid nssa translator-stab-intv $transstabint\r"
                sleep 1
            }
            exp_send -i $connection_id "exit\r"
            sleep 1
            exp_send -i $connection_id "exit\r"
        }
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	    	    
}

#37*******************************************************************************
#  Function Name	: _ntgrOspfDeleteNSSAProperty
#
#  Description    	: This function is called to delete property for NSSA
#                     only delete nosummary
#         
#  Usage          	: _ntgrOspfDeleteNSSAProperty <switch_name> <nssa_list>
#
#
#*******************************************************************************
proc _ntgrOspfDeleteNSSAProperty {switch_name nssa_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	    foreach nssa_member $nssa_list { 	    
	        set nssa_areaid [lindex $nssa_member 0]
	        expect -i $connection_id -re "#"
   	     exp_send -i $connection_id "configure\r"
  	        sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "area $nssa_areaid nssa no-summary\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"	           
	    }	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	        
}

#38*******************************************************************************
#  Function Name	: _ntgrOspfVLinkEnable
#
#  Description    	: This function is called to enable virtual link
#         
#  Usage          	: _ntgrOspfVLinkEnable <switch_name> <vl_list>
#
#
#*******************************************************************************
proc _ntgrOspfVLinkEnable {switch_name vl_list} {
           
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	    foreach vl_member $vl_list {	        
	        set vl_areaid [lindex $vl_member 0]
	        set vl_neighbor [lindex $vl_member 1]	        	        
	        expect -i $connection_id -re "#"
   	     exp_send -i $connection_id "configure\r"
  	        sleep 1
 	        exp_send -i $connection_id "router ospf\r"
 	        sleep 1
 	        exp_send -i $connection_id "area $vl_areaid virtual-link $vl_neighbor\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"
 	        sleep 1
 	        exp_send -i $connection_id "exit\r"	        
	    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	    	    
}

#39*******************************************************************************
#  Function Name	: _ntgrOspfVLinkDisable
#
#  Description    	: This function is called to disable virtual link
#         
#  Usage          	: _ntgrOspfVLinkDisable <switch_name> <vl_list>
#
#
#*******************************************************************************
proc _ntgrOspfVLinkDisable {switch_name vl_list} {
           
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    foreach vl_member $vl_list { 	        
        set vl_areaid [lindex $vl_member 0]
        set vl_neighbor [lindex $vl_member 1]		                
        expect -i $connection_id -re "#"
       exp_send -i $connection_id "configure\r"
          sleep 1
        exp_send -i $connection_id "router ospf\r"
        sleep 1
        exp_send -i $connection_id "no area $vl_areaid virtual-link $vl_neighbor\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
    }	  
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	      
}

#40*******************************************************************************
#  Function Name	: _ntgrOspfAddVLinkProperty
#
#  Description    	: This function is called to add property for virtual link
#         
#  Usage          	: _ntgrOspfAddVLinkProperty <switch_name> <vl_list>
#
#
#*******************************************************************************
proc _ntgrOspfAddVLinkProperty {switch_name vl_list} {
            
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    foreach vl_member $vl_list { 	    
        set vl_areaid [lindex $vl_member 0]
        set vl_neighbor [lindex $vl_member 1]
        set vl_property [lindex $vl_member 2]
        
        if {$vl_property != "VLink_DefaultProperty"} {
        
            set auth_vl [getOspfAuthforVL $vl_property]
            set deadint_vl [getOspfDeadIntervalforVL $vl_property]
            set helloint_vl [getOspfHelloIntervalforVL $vl_property]
            set reint_vl [getOspfRetranIntervalforVL $vl_property]
            set delay_vl [getOspfTransDelayforVL $vl_property] 
            
            expect -i $connection_id -re "#"
           exp_send -i $connection_id "configure\r"
              sleep 1
            exp_send -i $connection_id "router ospf\r"
            sleep 1
            if {$auth_vl != "default"} {
                exp_send -i $connection_id "area $vl_areaid virtual-link $vl_neighbor authentication $auth_vl\r"
                sleep 1
            }
            if {$deadint_vl != "default"} {
                exp_send -i $connection_id "area $vl_areaid virtual-link $vl_neighbor dead-interval $deadint_vl\r"
                sleep 1
            }
            if {$helloint_vl != "default"} {
                exp_send -i $connection_id "area $vl_areaid virtual-link $vl_neighbor hello-interval $helloint_vl\r"
                sleep 1
            }
            if {$reint_vl != "default"} {
                exp_send -i $connection_id "area $vl_areaid virtual-link $vl_neighbor retransmit-interval $reint_vl\r"
                sleep 1
            }
            if {$delay_vl != "default"} {
                exp_send -i $connection_id "area $vl_areaid virtual-link $vl_neighbor transmit-delay $delay_vl\r"
                sleep 1
            }
            exp_send -i $connection_id "exit\r" 
            sleep 1
            exp_send -i $connection_id "exit\r"
                
        }         
    }	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	        
}

#41*******************************************************************************
#  Function Name	: _ntgrOspfDeleteVLinkProperty
#
#  Description    	: This function is called to delete property for virtual link
#         
#  Usage          	: _ntgrOspfDeleteVLinkProperty <switch_name> <vl_list>
#
#
#*******************************************************************************
proc _ntgrOspfDeleteVLinkProperty {switch_name vl_list} {
           
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	    foreach vl_member $vl_list {	        
	        set vl_areaid [lindex $vl_member 0]
	        set vl_neighbor [lindex $vl_member 1]
	        set vl_property [lindex $vl_member 2]
	        
	        if {$vl_property != "VLink_DefaultProperty"} {	        
	             set auth_vl [getOspfAuthforVL $vl_property]
	                set auth_mode [lindex $auth_vl 0]
		          set deadint_vl [getOspfDeadIntervalforVL $vl_property]
		          set helloint_vl [getOspfHelloIntervalforVL $vl_property]
		          set reint_vl [getOspfRetranIntervalforVL $vl_property]
		          set delay_vl [getOspfTransDelayforVL $vl_property] 
	            
	            expect -i $connection_id -re "#"
   	         exp_send -i $connection_id "configure\r"
  	            sleep 1
 	            exp_send -i $connection_id "router ospf\r"
 	            sleep 1
 	            if {$auth_mode != "default"} {
 	                exp_send -i $connection_id "no area $vl_areaid virtual-link $vl_neighbor authentication $auth_mode\r"
 	                sleep 1
 	            }
 	            if {$deadint_vl != "default"} {
 	                exp_send -i $connection_id "no area $vl_areaid virtual-link $vl_neighbor dead-interval\r"
 	                sleep 1
 	            }
 	            if {$helloint_vl != "default"} {
 	                exp_send -i $connection_id "no area $vl_areaid virtual-link $vl_neighbor hello-interval\r"
 	                sleep 1
 	            }
 	            if {$reint_vl != "default"} {
 	                exp_send -i $connection_id "no area $vl_areaid virtual-link $vl_neighbor retransmit-interval\r"
 	                sleep 1
 	            }
 	            if {$delay_vl != "default"} {
 	                exp_send -i $connection_id "no area $vl_areaid virtual-link $vl_neighbor transmit-delay $delay_vl\r"
 	                sleep 1
 	            }
 	            exp_send -i $connection_id "exit\r" 
 	            sleep 1
 	            exp_send -i $connection_id "exit\r"    
 	        }          
	    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	    	    
}

##*******************************************************************************
##
##  Function Name  : _ntgrGetSequenceNumberOfOSPF
##
##  Description    : This function is used to get the sequence number of OSPF
##
##  Usage          : _ntgrGetSequenceNumberOfOSPF <switch_id> <area_id> <lsa_type>
##                                                <router_id> <adver_router>
##*******************************************************************************
#proc _ntgrGetSequenceNumberOfOSPF {switch_id area_id lsa_type router_id adver_router} {
#	
#	Netgear_connect_switch $switch_id
#	set cnn_id [_get_switch_handle $switch_id]
#
#	set result ""
#	set s_number ""
#   	exp_send -i $cnn_id "show ip ospf database\r"
#    	expect -i $cnn_id "show ip ospf database"
#    	expect {
#        	-i $cnn_id -re "#" { set result $result$expect_out(buffer) }
#        	-i $cnn_id -re "More" {
#         		set result $result$expect_out(buffer)
#            		exp_send -i $cnn_id " "
#            		exp_sleep 1
#            		exp_continue
#        	}
#        	timeout { exp_send -i $cnn_id " "; exp_continue }
#    	}
#
#       	set string_start  "$lsa_type (Area $area_id)"
#    	set first [string first $string_start $result]
#    	set string_strip [string range $result $first end]
#    	
#    	set last [string first "\n\n\n" $string_strip]
#    	set start [string first $string_start $string_strip]
#    	set string_expect [string range $string_strip $start $last]
#	set reg_express "($router_id)(\[ \t\]+)($adver_router)(\[ \t\]+)(\[0-9\]+)(\[ \t\]+)(\[0-9a-z\]*)"
#	
#	if { [regexp $reg_express $string_expect] != 1 } {
#		puts "ERROR! NO MATCH!"} else {
#		regexp $reg_express $string_expect a b c d e f g s_number 
#	}
#	
#	Netgear_disconnect_switch $switch_id	
#        return $s_number
#}
#*******************************************************************************
#
#  Function Name  : _ntgrGetSequenceNumberOfOSPF
#
#  Description    : This function is used to get the sequence number of OSPF
#
#  Usage          : _ntgrGetSequenceNumberOfOSPF <switch_id> <area_id> <lsa_type>
#                                                <router_id> <adver_router>
#*******************************************************************************
proc _ntgrGetSequenceNumberOfOSPF {switch_id area_id lsa_type router_id adver_router} {

    set cnn_id [_get_switch_handle $switch_id]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_id
        set cnn_id [_get_switch_handle $switch_id]
        set bCnn 0
    }
#	Netgear_connect_switch $switch_id
#	set cnn_id [_get_switch_handle $switch_id]

	set result ""
	set s_number ""
   	exp_send -i $cnn_id "show ip ospf database\r"
    	expect -i $cnn_id "show ip ospf database"
    	expect {
        	-i $cnn_id -re "#" { set result $result$expect_out(buffer) }
        	-i $cnn_id -re "More" {
         		set result $result$expect_out(buffer)
            		exp_send -i $cnn_id " "
            		exp_sleep 1
            		exp_continue
        	}
        	timeout { exp_send -i $cnn_id " "; exp_continue }
    	}

       	set string_start  "$lsa_type (Area $area_id)"
    	set first [string first $string_start $result]
    	set string_strip [string range $result $first end]
    	
    	set last [string first "\n\n\n" $string_strip]
    	set start [string first $string_start $string_strip]
    	set string_expect [string range $string_strip $start $last]
	set reg_express "($router_id)(\[ \t\]+)($adver_router)(\[ \t\]+)(\[0-9\]+)(\[ \t\]+)(\[0-9a-z\]*)"
	
	if { [regexp $reg_express $string_expect] != 1 } {
		puts "ERROR! NO MATCH!"} else {
		regexp $reg_express $string_expect a b c d e f g s_number 
	}

  if {$bCnn == 0} {
      Netgear_disconnect_switch $switch_id
  }	
#	Netgear_disconnect_switch $switch_id	
        return $s_number
}

#43*******************************************************************************
#
#  Function Name  : _ntgrDeleteLoopBackInterface
#
#  Description    : This function is used to delete loopback interface
#
#  Usage          : _ntgrDeleteLoopBackInterface <switch_name> <loopback_id>
#*******************************************************************************
proc _ntgrDeleteLoopBackInterface {switch_name loopback_id } {
	
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1  	  	
    exp_send -i $cnn_id "no interface loopback $loopback_id\r"
    exp_sleep 1
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
}