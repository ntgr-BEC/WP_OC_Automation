#!/bin/sh
################################################################################   
#
#  File Name		  : lib-rip.tcl
#
#  Description      :
#         This TCL contains functions to configure RIP
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        28-May-06     Nina Cheng        Created
#        02-Apr-2012   Andy Xie          Modified and add some function
#
#
#
#
################################################################################
#1*******************************************************************************
#  Function Name	: _ntgrRipEnable
#
#  Description    	: This function is called to enable RIP on switch
#         
#  Usage          	: _ntgrRipEnable <switch_name> <flag> 
#
#
#*******************************************************************************
proc _ntgrRipEnable {switch_name flag} {
	
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    if {$flag != "default"} {
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "configure\r"
        sleep 1
        exp_send -i $cnn_id "router rip\r"
        sleep 1
        exp_send -i $cnn_id "enable\r"
        sleep 1
        exp_send -i $cnn_id "exit\r"
        sleep 1
        exp_send -i $cnn_id "exit\r"
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }	
}

#2*******************************************************************************
#  Function Name	: _ntgrRipDisable
#
#  Description    	: This function is called to disable RIP on switch
#         
#  Usage          	: _ntgrRipDisable <switch_name> <flag>
#
#
#*******************************************************************************

proc _ntgrRipDisable {switch_name flag} {

    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    if {$flag != "default"} {
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "configure\r"
        sleep 1
        exp_send -i $cnn_id "router rip\r"
        sleep 1
        exp_send -i $cnn_id "no enable\r"
        sleep 1
        exp_send -i $cnn_id "exit\r"
        sleep 1
        exp_send -i $cnn_id "exit\r"
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }	
}

#3*******************************************************************************
#  Function Name	: _ntgrRipEnableAutoSummary 
#
#  Description    	: This function is called to enable RIP auto-summary on switch
#         
#  Usage          	: _ntgrRipEnableAutoSummary <switch_name> <flag>    
#
#
#*******************************************************************************

proc _ntgrRipEnableAutoSummary {switch_name flag} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }	
    
    if {$flag != "default"} {
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router rip\r"
        sleep 1
        exp_send -i $connection_id "auto-summary\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
    }
    if {$bCnn == 0} {
       Netgear_disconnect_switch $switch_name
    }      	
}

#4*******************************************************************************
#  Function Name	: _ntgrRipDisableAutoSummary 
#
#  Description    	: This function is called to disable RIP auto-summary on switch
#         
#  Usage          	: _ntgrRipDisableAutoSummary <switch_name> <flag>     
#
#
#*******************************************************************************

proc _ntgrRipDisableAutoSummary {switch_name flag} {
	
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
    if {$flag != "default"} {    
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router rip\r"
        sleep 1
        exp_send -i $connection_id "no auto-summary\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"	
    }
    if {$bCnn == 0} {
       Netgear_disconnect_switch $switch_name
    }  
}

#5*******************************************************************************
#  Function Name	: _ntgrRipEnableDefaultinformation
#
#  Description    	: This function is called to advertise default routes 
#         
#  Usage          	: _ntgrRipEnableDefaultinformation <switch_name> <flag>     
#
#       
#*******************************************************************************

proc _ntgrRipEnableDefaultinformation {switch_name flag} {
	
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
    if {$flag != "default"} {
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router rip\r"
        sleep 1
        exp_send -i $connection_id "default-information originate\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
    }
    if {$bCnn == 0} {
       Netgear_disconnect_switch $switch_name
    }    
}
#6*******************************************************************************
#  Function Name	: _ntgrRipDisableDefaultinformation
#
#  Description    	: This function is called to withdraw default routes 
#         
#  Usage          	: _ntgrRipDisableDefaultinformation <switch_name> <flag>
#
#       
#*******************************************************************************

proc _ntgrRipDisableDefaultinformation {switch_name flag} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
    if {$flag != "default"} {
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router rip\r"
        sleep 1
        exp_send -i $connection_id "no default-information originate\r"
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
#  Function Name	: _ntgrRipEnableHostRoutesAccept 
#
#  Description    	: This function is called to enbale RIP hostroutesaccept on switch
#         
#  Usage          	: _ntgrRipEnableHostRoutesAccept <switch_name> <flag>    
#
#
#*******************************************************************************

proc _ntgrRipEnableHostRoutesAccept {switch_name flag} {
	
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
    if {$flag != "default"} {
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router rip\r"
        sleep 1
        exp_send -i $connection_id "hostroutesaccept\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
    }
    if {$bCnn == 0} {
       Netgear_disconnect_switch $switch_name
    }	
}
#8*******************************************************************************
#  Function Name	: _ntgrRipDisableHostRoutesAccept 
#
#  Description    	: This function is called to disabale RIP hostroutesaccept on switch
#         
#  Usage          	: _ntgrRipDisableHostRoutesAccept <switch_name> <flag>    
#
#
#*******************************************************************************

proc _ntgrRipDisableHostRoutesAccept {switch_name flag} {
	
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
    if {$flag != "default"} {
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router rip\r"
        sleep 1
        exp_send -i $connection_id "no hostroutesaccept\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"	
    }
    if {$bCnn == 0} {
       Netgear_disconnect_switch $switch_name
    }    	
}

#9*******************************************************************************
#  Function Name	: _ntgrRipEnableDefaultMetric
#
#  Description    	: This function is called to configure RIP default metric on switch
#         
#  Usage          	: _ntgrRipEnableDefaultMetric <switch_name> <metric>   
#
#
#*******************************************************************************

proc _ntgrRipEnableDefaultMetric {switch_name metric} {
	
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
    set ret ""
    if {$metric != "default"} {
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        expect -i $connection_id -re "#"
#        expect -i $connection_id "#" {}  
        append ret $expect_out(buffer)         
        exp_send -i $connection_id "router rip\r"
        sleep 1
        expect -i $connection_id -re "#"
        append ret $expect_out(buffer)
        exp_send -i $connection_id "default-metric $metric\r"
        sleep 1
        expect -i $connection_id -re "#"
        append ret $expect_out(buffer)
        exp_send -i $connection_id "exit\r"
        sleep 1
        expect -i $connection_id -re "#"
        append ret $expect_out(buffer)
        exp_send -i $connection_id "exit\r"        
    }
    if {$bCnn == 0} {
       Netgear_disconnect_switch $switch_name
    }    
    return $ret
}

#10*******************************************************************************
#  Function Name	: _ntgrRipDisableDefaultMetric
#
#  Description    	: This function is called to disable RIP default metric on switch
#         
#  Usage          	: _ntgrRipDisableDefaultMetric <switch_name> <metric>    
#
#
#*******************************************************************************

proc _ntgrRipDisableDefaultMetric {switch_name metric} {
	
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
    if {$metric != "default"} {
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router rip\r"
        sleep 1
        exp_send -i $connection_id "no default-metric\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
        sleep 1
        exp_send -i $connection_id "exit\r"	
    }
    if {$bCnn == 0} {
       Netgear_disconnect_switch $switch_name
    } 
}

#11*******************************************************************************
#  Function Name	: _ntgrEnableRipDistance
#
#  Description    	: This function is called to configure RIP distance on switch
#         
#  Usage          	: _ntgrEnableRipDistance <switch_name> <distance> 
#
#
#*******************************************************************************

proc _ntgrEnableRipDistance {switch_name distance} {
	
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
    set ret ""
    if {$distance != "default"} {
        
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        expect -i $connection_id -re "#"
#        expect -i $connection_id "#" {}
        append ret $expect_out(buffer) 
        exp_send -i $connection_id "router rip\r"
        sleep 1
        expect -i $connection_id -re "#" 
        append ret $expect_out(buffer)  
        exp_send -i $connection_id "distance rip $distance\r"
        sleep 1
        expect -i $connection_id -re "#" 
        append ret $expect_out(buffer)
        exp_send -i $connection_id "exit\r"
        sleep 1
        expect -i $connection_id -re "#" 
        append ret $expect_out(buffer)
        exp_send -i $connection_id "exit\r"
        expect -i $connection_id -re "#" 
        append ret $expect_out(buffer)
    }
    if {$bCnn == 0} {
       Netgear_disconnect_switch $switch_name
    }
    return $ret
}

#12*******************************************************************************
#  Function Name	: _ntgrDisableRipDistance
#
#  Description    	: This function is called to disable RIP distance on switch
#         
#  Usage          	: _ntgrDisableRipDistance <switch_name> <distance> 
#
#
#*******************************************************************************

proc _ntgrDisableRipDistance {switch_name distance} {
	
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
    if {$distance != "default"} {
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    exp_send -i $connection_id "router rip\r"
    sleep 1
    exp_send -i $connection_id "no distance rip\r"
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
#  Function Name	: _ntgrRipEnableSplitHorizon
#
#  Description    	: This function is called to enable RIP split-horizon on switch
#         
#  Usage          	: _ntgrRipEnableSplitHorizon <switch_name> <split_horizon_mode>    
#
#
#*******************************************************************************

proc _ntgrRipEnableSplitHorizon {switch_name split_horizon_mode} {
	
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
    if {$split_horizon_mode != "default"} {
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router rip\r"
        sleep 1
        exp_send -i $connection_id "split-horizon $split_horizon_mode\r"
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
#  Function Name	: _ntgrRipDisableSplitHorizon
#
#  Description    	: This function is called to disable RIP split-horizon on switch
#         
#  Usage          	: _ntgrRipDisableSplitHorizon <switch_name> <split_horizon_mode>     
#
#
#*******************************************************************************

proc _ntgrRipDisableSplitHorizon {switch_name split_horizon_mode} {
	
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }    
    if {$split_horizon_mode != "default"} {	
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router rip\r"
        sleep 1
        exp_send -i $connection_id "no split-horizon\r"
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
#  Function Name	: _ntgrRipEnableDistributelistOut
#
#  Description    	: This function is called to specify the access list to filter
#                     routes received from the source protocal 
#         
#  Usage          	: _ntgrRipEnableDistributelistOut <switch_name> <dis_list>     
#
#       
#*******************************************************************************

proc _ntgrRipEnableDistributelistOut {switch_name dis_list} {
	  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    foreach distribute_list $dis_list {
        set acl_num [lindex $distribute_list 0]
        set protocol [lindex $distribute_list 1]
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "router rip\r"
        sleep 1
        exp_send -i $connection_id "distribute-list $acl_num out $protocol\r"
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
#  Function Name	: _ntgrRipDisableDistributelistOut
#
#  Description    	: This function is called to delete the access list to filter
#                     routes 
#         
#  Usage          	: _ntgrRipDisableDistributelistOut <switch_name> <dis_list>     
#
#       
#*******************************************************************************

proc _ntgrRipDisableDistributelistOut {switch_name dis_list} {
	  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
	  foreach distribute_list $dis_list {	         
        set acl_num [lindex $distribute_list 0]
        set protocol [lindex $distribute_list 1]
        expect -i $connection_id -re "#"
   	    exp_send -i $connection_id "configure\r"
  	    sleep 1
 	      exp_send -i $connection_id "router rip\r"
 	      sleep 1
 	      exp_send -i $connection_id "no distribute-list $acl_num out $protocol\r"
 	      sleep 1
 	      exp_send -i $connection_id "exit\r"
 	      sleep 1
 	      exp_send -i $connection_id "exit\r"        
    }	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#17******************************************************************************
#  Function Name	: _ntgrRipEnableRedistribute
#
#  Description    	: This function is called to enable redistribute route to RIP on switch
#         
#  Usage          	: _ntgrRipEnableRedistribute <switch_name> <redis_list>    
#
#        Note: if the protocal is ospf, math is not configured
#*******************************************************************************

proc _ntgrRipEnableRedistribute {switch_name redis_list} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }	  

	  foreach redistribute_list $redis_list { 	        
        set protocol [lindex $redistribute_list 0]
        if {$protocol != "ospf"} {
            set scmd ""
            set met [lindex $redistribute_list 1]
            expect -i $connection_id -re "#"
   	        exp_send -i $connection_id "configure\r"
  	        sleep 1
 	          exp_send -i $connection_id "router rip\r"
 	          sleep 1
 	          if {$met == "default"} {set scmd ""} else {set scmd "metric $met"}
            exp_send -i $connection_id "redistribute $protocol $scmd\r"
            sleep 1
 	          exp_send -i $connection_id "exit\r"
 	          sleep 1
 	          exp_send -i $connection_id "exit\r"
        } else {        
            set ncmd ""
            set mcmd ""
            set cmd ""            
            set metric [lindex $redistribute_list 1]
            if {$metric != "default"} {set ncmd "metric $metric"}            
            set num_t [llength $redistribute_list]                   
            for {set i 2} {$i < $num_t} {incr i} {
                set member [lindex $redistribute_list $i]
                set flag [lindex $member 0]
                if {$flag != "default"} {set mcmd "$mcmd $member"}
            }            
            if {$mcmd != ""} {set mcmd "match $mcmd"}            
            expect -i $connection_id -re "#"
   	        exp_send -i $connection_id "configure\r"
  	        sleep 1
 	          exp_send -i $connection_id "router rip\r"
 	          sleep 1
            exp_send -i $connection_id "redistribute $protocol $ncmd $mcmd\r"
            sleep 1
 	          exp_send -i $connection_id "exit\r"
 	          sleep 1
 	          exp_send -i $connection_id "exit\r"
        }        
    }	
    if {$bCnn == 0} {
       Netgear_disconnect_switch $switch_name
    }    
}

#18.1******************************************************************************
#  Function Name	: _ntgrRipDisableRedistributeProtocol
#
#  Description    	: This function is called to disable redistribute route to RIP
#         
#  Usage          	: _ntgrRipDisableRedistributeProtocol <switch_name> <redis_list>    
#
#*******************************************************************************

proc _ntgrRipDisableRedistributeProtocol {switch_name redis_list} {

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
 	  exp_send -i $connection_id "router rip\r"
 	  sleep 1
 	  foreach member $redis_list {
 	      set protocol [lindex $member 0]
         exp_send -i $connection_id "no redistribute $protocol\r"
         sleep 1
     }
 	  exp_send -i $connection_id "exit\r"
 	  sleep 1
 	  exp_send -i $connection_id "exit\r"	 
    if {$bCnn == 0} {
       Netgear_disconnect_switch $switch_name
    }  	   
}

#18.2******************************************************************************
#  Function Name	: _ntgrRipDisableRedistributeMetric
#
#  Description    	: This function is called to disable metric for redistribute route
#         
#  Usage          	: _ntgrRipDisableRedistributeMetric <switch_name> <redis_list>    
#
#*******************************************************************************

proc _ntgrRipDisableRedistributeMetric {switch_name redis_list} {
	  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }  
	  
 	  foreach member $redis_list { 
 	      set protocol [lindex $member 0]
 	      set metric [lindex $member 1]
 	      if {$metric != "default"} {
 	          expect -i $connection_id -re "#"
            exp_send -i $connection_id "configure\r"
  	        sleep 1
 	          exp_send -i $connection_id "router rip\r"
 	          sleep 1
            exp_send -i $connection_id "no redistribute $protocol metric\r"
            sleep 1
            exp_send -i $connection_id "exit\r"
 	          sleep 1
 	          exp_send -i $connection_id "exit\r"	  
        }
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#18.3******************************************************************************
#  Function Name	: _ntgrRipDisableRedisOspfMatch
#
#  Description    	: This function is called to disable redistribute route to RIP
#         
#  Usage          	: _ntgrRipDisableRedisOspfMatch <switch_name> <redis_list>    
#
#        Note: if the protocal is ospf, math is not configured
#*******************************************************************************

proc _ntgrRipDisableRedisOspfMatch {switch_name redis_list} {
    
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
 	  foreach member $redis_list { 
 	      set cmd ""
 	      set protocol [lindex $member 0]
 	      if {$protocol == "ospf"} {
 	          for {set j 2} {$j<=6} {incr j} {
 	              set value_mem [lindex $member $j]
 	              set type [lindex $value_mem 0]
 	              set value [lindex $value_mem 1]
 	              if {$type != "default"} {set cmd "$cmd $type $value"}
 	          }
 	          if {$cmd != ""} {
 	              expect -i $connection_id -re "#"
                exp_send -i $connection_id "configure\r"
  	            sleep 1
 	              exp_send -i $connection_id "router rip\r"
 	              sleep 1
                exp_send -i $connection_id "no redistribute ospf match $cmd\r"
                sleep 1
                exp_send -i $connection_id "exit\r"
 	              sleep 1
 	              exp_send -i $connection_id "exit\r"
            }
        }
    }	  
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#19*******************************************************************************
#  Function Name	: _ntgrRipEnableInterface
#
#  Description    	: This function is called to enable RIP on logical vlan
#                   	interface and configure the RIP property in the interface. 
#         
#  Usage          	: _ntgrRipEnableInterface <switch_name> <interface_list>
#
#
#*******************************************************************************

proc _ntgrRipEnableInterface {switch_name interface_list} {

    
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }    
    
    set IF_list ""
    set LF_list ""
    set vl_list ""
    set ph_list ""
    foreach inter_para $interface_list {
        set interface [lindex $inter_para 0]
        set reg {^[0-9a-zA-Z]+$}
        set mflag  [regexp $reg $interface]
        if {$mflag} {
        		lappend vl_list $interface
        } else {
            lappend ph_list $interface
        }
    }
    set num1 [llength $vl_list]
#    if {$num1 != 0} {set LF_list [_ntgrGetL3LogicalIf $connection_id $vl_list]}
    if {$num1 != 0} {
      foreach vlan_index $vl_list {
        lappend LF_list [concat vlan [_getVlanID $vlan_index]]
      }      
    }        
    set IF_list [concat $LF_list $ph_list]
     
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    foreach LF $IF_list {
        exp_send -i $connection_id "interface $LF\r"
        sleep 1
        exp_send -i $connection_id "ip rip\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
        sleep 1
    }
    exp_send -i $connection_id "exit\r"    

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }    
}
#*******************************************************************************
#  Function Name	: _ntgrRipEnablePort
#
#  Description    	: This function is called to enable RIP on logical vlan
#                   	interface and configure the RIP property in the interface. 
#         
#  Usage          	: _ntgrRipEnableInterface <switch_name> <interface_list>
#
#  Author           :zhenwei.li
#
#*******************************************************************************

proc _ntgrRipEnablePort {switch_name port} {

    
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
    exp_send -i $connection_id "interface $port\r"
    sleep 1
    exp_send -i $connection_id "ip rip\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"    

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }    
}

#20*******************************************************************************
#  Function Name	: _ntgrRipDisableInterface
#
#  Description    	: This function is called to disable RIP on logical vlan
#                   	interface and clear the RIP property on the interface
#         
#  Usage          	: _ntgrRipDisableInterface <switch_name> <interface_list>
#
#            
#
#*******************************************************************************

proc _ntgrRipDisableInterface {switch_name interface_list} {
	
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
    set IF_list ""
    set LF_list ""
    set vl_list ""
    set ph_list ""
    foreach inter_para $interface_list {
        set interface [lindex $inter_para 0]
        set reg {^[0-9a-zA-Z]+$}
        set mflag  [regexp $reg $interface]
        if {$mflag} {
        		lappend vl_list $interface
        } else {
            lappend ph_list $interface
        }
    }
    set num1 [llength $vl_list]

    #if {$num1 != 0} {set LF_list [_ntgrGetL3LogicalIf $connection_id $vl_list]} 

    if {$num1 != 0} {
      foreach vlan_index $vl_list {
        lappend LF_list [concat vlan [_getVlanID $vlan_index]]
      }      
    }
        
    set IF_list [concat $LF_list $ph_list]
    expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
    sleep 1
    foreach LF $IF_list {
        exp_send -i $connection_id "interface $LF\r"
        sleep 1
        exp_send -i $connection_id "no ip rip\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
        sleep 1
    }
    exp_send -i $connection_id "exit\r" 
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }   
}
#21******************************************************************************
#Function Name	: _ntgrRipAddPorpertyforLF
#
#  Description  :This function is called to add the RIP property on the interface.  
#         
#  Usage        : _ntgrRipAddPorpertyforLF <switch_name> <interface_list> 
#            
#
#*******************************************************************************

proc _ntgrRipAddPorpertyforLF {switch_name interface_list} {
        
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
	  set IFph_list ""
	  set PROph_list ""
	  set IFvl_list ""
	  set PROvl_list ""
	  set IF_list ""
	  set PRO_list ""	  
	  foreach inter_para $interface_list {
	      set interface [lindex $inter_para 0]
	      set property [lindex $inter_para 1] 
	      if {$property != "IF_DefaultProperty"} {
	          set reg {^[0-9a-zA-Z]+$}
	          set mflag  [regexp $reg $interface]
	          if {$mflag} {
	              lappend IFvl_list $interface
	              lappend PROvl_list $property
	          } else {
	              lappend IFph_list $interface
	              lappend PROph_list $property
	          }
        }
    }
    set num [llength $IFvl_list]
#    if {$num != 0} {set IF_list [_ntgrGetL3LogicalIf $connection_id $IFvl_list]}    
    set vlan_list ""
    if {$num != 0} {
      foreach vlan_index $IFvl_list {
        lappend vlan_list [concat vlan [_getVlanID $vlan_index]]
      }      
    }
    
    set total_inter ""
    set total_inter [concat $vlan_list $IFph_list]
    set total_prop [concat $PROvl_list $PROph_list]
    
    foreach LF $total_inter PROPERTY $total_prop {
        set auth [getRipAuthforInterface $PROPERTY]
		        set auth_mode [lindex $auth 0]
		        set auth_key [lindex $auth 1]
		        set auth_keyid [lindex $auth 2]
		  set ver_send [getRipSendVerforInterface $PROPERTY]
		  set ver_receive [getRipReveiVerforInterface $PROPERTY]
		  
		  set ret ""
		  expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        expect -i $connection_id -re "#"
        append ret $expect_out(buffer)
        exp_send -i $connection_id "interface $LF\r"
        sleep 1
        expect -i $connection_id -re "#"
        append ret $expect_out(buffer)
		    if {$ver_send != "default"} {
		        exp_send -i $connection_id "ip rip send version $ver_send\r"
		        sleep 1
		    }
		    if {$ver_receive != "default"} {
		        exp_send -i $connection_id "ip rip receive version $ver_receive\r"
		        sleep 1
		    }
		    if {$auth != "default"} {
		        if {$auth_mode == "none"} { 
		        	set auth_key ""
		        	set auth_keyid "" 
		        } 
		        if {$auth_mode == "simple"} {
		        	set auth_keyid "" 
		        }
		        exp_send -i $connection_id "ip rip authentication $auth_mode $auth_key $auth_keyid\r"
		        sleep 1
		    }
		    expect -i $connection_id -re "#"
        append ret $expect_out(buffer)
		    exp_send -i $connection_id "exit\r"
		    sleep 1
        expect -i $connection_id -re "#"
        append ret $expect_out(buffer)
		    exp_send -i $connection_id "exit\r"
		    sleep 1
        expect -i $connection_id -re "#"
        append ret $expect_out(buffer)
    }	      
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    return $ret
}

#22******************************************************************************
#Function Name	: _ntgrRipDeletePorpertyforLF
#
#  Description  :This function is called to delete the RIP property in the interface.  
#         
#  Usage        : _ntgrRipDeletePorpertyforLF <switch_name> <interface_list>
#            
#
#*******************************************************************************
proc _ntgrRipDeletePorpertyforLF {switch_name interface_list} {
        
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
       Netgear_connect_switch $switch_name
       set connection_id [_get_switch_handle $switch_name]
       set bCnn 0
    }
	  set IFph_list ""
	  set PROph_list ""
	  set IFvl_list ""
	  set PROvl_list ""
	  set IF_list ""
	  set PRO_list ""	 
	   
	  foreach inter_para $interface_list {
	      set interface [lindex $inter_para 0]
	      set property [lindex $inter_para 1] 
	      if {$property != "IF_DefaultProperty"} {
	          set reg {^[0-9a-zA-Z]+$}
	          set mflag  [regexp $reg $interface]
	          set mflag  [regexp $reg $interface]
	          if {$mflag} {
	              lappend IFvl_list $interface
	              lappend PROvl_list $property
	          } else {
	              lappend IFph_list $interface
	              lappend PROph_list $property
	          }
	      }        
    }  
   
    set num [llength $IFvl_list]
#    if {$num != 0} {set IF_list [_ntgrGetL3LogicalIf $connection_id $IFvl_list]}    
    if {$num != 0} {
      foreach vlan_index $IFvl_list {
        lappend LF_list [concat vlan [_getVlanID $vlan_index]]
      }      
    }
    
    set total_inter [concat $LF_list $IFph_list]
    set total_prop [concat $PROvl_list $PROph_list]
    foreach LF $total_inter PROPERTY $total_prop {
        set auth [getRipAuthforInterface $PROPERTY]
		        set auth_mode [lindex $auth 0]
		  set ver_send [getRipSendVerforInterface $PROPERTY]
		  set ver_receive [getRipReveiVerforInterface $PROPERTY]
		    
		  expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        exp_send -i $connection_id "interface $LF\r"
        sleep 1
		    if {$ver_send != "default"} {
		        exp_send -i $connection_id "no ip rip send version\r"
		        sleep 1
		    }
		    if {$ver_receive != "default"} {
		        exp_send -i $connection_id "no ip rip receive version\r"
		        sleep 1
		    }
		    if {$auth_mode != "default"} {
		        exp_send -i $connection_id "no ip rip authentication\r"
		    }
		    exp_send -i $connection_id "exit\r"
		    sleep 1
		    exp_send -i $connection_id "exit\r"
    }	  
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}
