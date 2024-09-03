
proc ntgrSecurityManagementAddTACACSConfigure {strDev key_info} {
  set key 0 ; set time 0; set ret 0;
  keylget key_info KEYSTRING key
  keylget key_info TIMEOUT time
  set cnn_id [_get_switch_handle $strDev]
  set strcommand "tacacs-server key $key"
  if { $key !=0 } {
      ntgrCLIExecute $strDev "configure"
      exp_send -i $cnn_id "$strcommand \r"
      exp_send -i $cnn_id "exit \r"
      expect -i $cnn_id "exit"
      set ret  $expect_out(buffer)
  }
  if { $time!=0 } {
      ntgrCLIExecute $strDev "configure"
      ntgrCLIExecute $strDev "tacacs-server timeout $time"
      ntgrCLIExecute $strDev "exit"
  }
  return $ret
}

proc ntgrSecurityManagementAddTACACSServerConfigure {strDev serv_info} {
  set prio 0 ; set port 0; set key 0; set time 0;
  keylget serv_info SERVER    serv
  keylget serv_info PRIORITY  prio
  keylget serv_info PORT      port
  keylget serv_info KEYSTRING key
  keylget serv_info TIMEOUT   time
  ntgrCLIExecute $strDev "configure"
  set cnn_id [_get_switch_handle $strDev]
  if { $serv!=0 } {
      exp_send -i $cnn_id "tacacs-server host $serv\r"
      if { $prio!=0 } { exp_send -i $cnn_id "priority $prio\r"}
      if { $port!=0 } { exp_send -i $cnn_id "port $port\r"}
      if { $key !=0 } { exp_send -i $cnn_id "key $key\r"}
      if { $time!=0 } { exp_send -i $cnn_id "timeout $time\r"}    
     exp_send -i $cnn_id "exit \r"
     expect -i $cnn_id "exit"
     set ret  $expect_out(buffer) 
  }
   ntgrCLIExecute $strDev "exit"
   return $ret
 }

proc  ntgrSecurityManagementAuthListConfigLoginList {strDev list_login} {

    set method1 0 ; set method2 0 ; set method3 0;
    keylget list_login METHOD1    method1
    keylget list_login METHOD2    method2
    keylget list_login METHOD3    method3
    ntgrCLIExecute $strDev "configure"
   if { $method1 !=0 && $method2 !=0  && $method3 !=0 } {
        ntgrCLIExecute $strDev "aaa authentication login networkList $method1 $method2 $method3"
    }
    if { $method1 !=0 && $method2 !=0  && $method3 ==0 } {
        ntgrCLIExecute $strDev "aaa authentication login networkList $method1 $method2"
    }
   
    if { $method1 !=0 && $method2 ==0  && $method3 ==0 } {
        ntgrCLIExecute $strDev "aaa authentication login networkList $method1"
    }
       
    ntgrCLIExecute $strDev "exit"
}

proc ntgrSecurityManagementAuthListConfigEnableList {strDev list_enable} {
  set method1 0 ; set method2 0 ; set method3 0;
  keylget list_enable METHOD1    method1
  keylget list_enable METHOD2    method2
  keylget list_enable METHOD3    method3
  ntgrCLIExecute $strDev "configure"
  if { $method1 !=0 && $method2 !=0  && $method3 !=0 } {
      ntgrCLIExecute $strDev "aaa authentication enable enableList $method1 $method2 $method3"
  }
  if { $method1 !=0 && $method2 !=0  && $method3 ==0 } {
      ntgrCLIExecute $strDev "aaa authentication enable enableList $method1 $method2"
  }
  if { $method1 !=0 && $method2 ==0  && $method3 ==0 } {
      ntgrCLIExecute $strDev "aaa authentication enable enableList $method1"
  }
  ntgrCLIExecute $strDev "exit"
}
		 

proc ntgrSecurityManagementDelTACACSServerConfigure {strDev serv_info} {
  set serv 0
  keylget serv_info SERVER    serv
  ntgrCLIExecute $strDev "configure"

  if { $serv!=0 } {
      ntgrCLIExecute $strDev "no tacacs-server host $serv"
  }
  ntgrCLIExecute $strDev "exit"

}

proc ntgrSecurityManagementChkTACACSServerConfigure {strDev serv_info {cmd "show tacacs"}} {
    set str {}
    set str1 {}
    set strOutput [ntgrCLIExecute $strDev $cmd]
    set tacasrv    [keylget serv_info SERVER]
    set outputlist [split $strOutput "\n"]
	  foreach output $outputlist {  
        regexp -nocase "$tacasrv" $output str str1
        if {$str != {}} {break}
    }
    if {$str != {}} {
       return "exist"
    } else {
       return "noexist"
    }  
}

proc ntgrSecurityManagementAuthListResetHTTPList { strDev } {
    ntgrCLIExecute $strDev "configure"
    ntgrCLIExecute $strDev "no ip http authentication"
    ntgrCLIExecute $strDev "exit"
}


proc ntgrSecurityManagementAuthListDelEnableList { strDev list_enable } {
  ntgrCLIExecute $strDev "configure"
  ntgrCLIExecute $strDev "no aaa authentication enable enableList"
  ntgrCLIExecute $strDev "exit"
}
	
proc ntgrSecurityManagementAuthListDelLoginList { strDev list_login } {
   ntgrCLIExecute $strDev "configure"
   ntgrCLIExecute $strDev "no aaa authentication login networkList"
   ntgrCLIExecute $strDev "exit"
}

proc ntgrSecurityManagementAuthListConfigHTTPList {strDev list_config} {
  set method1 0 ; set method2 0 ; set method3 0;
  keylget list_config METHOD1    method1
  keylget list_config METHOD2    method2
  keylget list_config METHOD3    method3
  
  ntgrCLIExecute $strDev "configure"
  if { $method1 !=0 && $method2 !=0  && $method3 !=0 } {
      ntgrCLIExecute $strDev "ip http authentication $method1 $method2 $method3"
  }
  if { $method1 !=0 && $method2 !=0  && $method3 ==0 } {
      ntgrCLIExecute $strDev "ip http authentication $method1 $method2"
  }
  if { $method1 !=0 && $method2 ==0  && $method3 ==0 } {
      ntgrCLIExecute $strDev "ip http authentication $method1"
  }
  ntgrCLIExecute $strDev "exit"
} 

proc ntgrSecurityManagementAuthListConfigHTTPSList {strDev list_config} {
  set method1 0 ; set method2 0 ; set method3 0;
  keylget list_config METHOD1    method1
  keylget list_config METHOD2    method2
  keylget list_config METHOD3    method3
  ntgrCLIExecute $strDev "configure"
  if { $method1 !=0 && $method2 !=0  && $method3 !=0 } {
      ntgrCLIExecute $strDev "ip https authentication $method1 $method2 $method3"
  }
  if { $method1 !=0 && $method2 !=0  && $method3 ==0 } {
      ntgrCLIExecute $strDev "ip https authentication $method1 $method2"
  }
  if { $method1 !=0 && $method2 ==0  && $method3 ==0 } {
      ntgrCLIExecute $strDev "ip https authentication $method1"
  }
  ntgrCLIExecute $strDev "exit"
} 

proc ntgrSecurityManagementAddRADIUSServerConfiguration {strDev list_config} {
    set secret_password ""
    set fir_key ""
    set sec_key ""
    set port 1812
    set name ""
    keylget list_config SERVER server
    keylget list_config SECRET_MODE secret_mode
    keylget list_config SECRET_PASSWORD secret_password
    keylget list_config ACTIVE active
    keylget list_config FIR_KEY fir_key
    keylget list_config SEC_KEY sec_key
    keylget list_config PORT port
    keylget list_config NAME name
    
    ntgrCLIExecute $strDev "configure"
    ntgrCLIExecute $strDev "radius server host auth $server"
    if {[regexp -nocase "yes" $secret_mode] == 1} {
        ntgrCLIExecute $strDev "radius server key auth $server"
        if {[string length $fir_key] > 0 && [string length $sec_key] > 0} {
            ntgrCLIExecute $strDev $fir_key
            ntgrCLIExecute $strDev $sec_key    
        } else {
            set strRet [ntgrCLIExecute $strDev $secret_password]
            if {[regexp -nocase {error.+maximum length} $strRet]} {
                ntgrCLIExecute $strDev "exit"
                return "secret_password_exceed_err"
            }
            ntgrCLIExecute $strDev $secret_password         
        }          
    }
    if {$port != 1812} {
        ntgrCLIExecute $strDev "radius server host auth $server port $port"    
    } 
    
    if {$name != ""} {
        ntgrCLIExecute $strDev "radius server host auth $server name $name"    
    }      
    
    if {[regexp -nocase "yes" $active] == 1} {
        ntgrCLIExecute $strDev "radius server primary $server"        
    }
    ntgrCLIExecute $strDev "exit"
    ntgrCLIExecute $strDev "show radius servers"
    return "ok"
}

proc ntgrSecurityManagementAddRADIUSConfiguration {strDev list_config} {
    keylget list_config SOUR_PORT_TYPE sour_port_type
    keylget list_config SOUR_PORT   sour_port
    ntgrCLIExecute $strDev "configure"
    if {[regexp -nocase {service} $sour_port]} {
        ntgrCLIExecute $strDev "radius source-interface serviceport"    
    } else {
        set out_put [ntgrCLIExecute $strDev "radius source-interface $sour_port_type $sour_port"]
        if {[regexp {routing} $out_put]} {
            ntgrCLIExecute $strDev "radius source-interface vlan 1"        
        }
    }     
    ntgrCLIExecute $strDev "exit"
} 

proc ntgrSecurityManagementDelRADIUServerConfigure {strDev serv_info} {
    set serv 0
    keylget serv_info SERVER    serv
    ntgrCLIExecute $strDev "configure"
  
    if { $serv!=0 } {
        ntgrCLIExecute $strDev "no radius server host auth $serv"
    }
    ntgrCLIExecute $strDev "exit"
}

# Below proc added by luiz.Yao for support 802.1x auth func start 2016.4.22
proc ntgrSecurityPortAuthConfigPortAuthMode { strDev port_auth_mode } {
    ntgrCLIExecute $strDev "configure"
    for {set i 0} {$i<[llength $port_auth_mode]} {incr i 2} {
        set port_id [lindex $port_auth_mode $i]
        set port_mode [lindex $port_auth_mode [expr $i+1]]
        if {[regexp -nocase {service} $port_id] != 1} {
            ntgrCLIExecute $strDev "interface $port_id"
            ntgrCLIExecute $strDev "dot1x port-control $port_mode"
            ntgrCLIExecute $strDev "exit"    
        }             
    }
    ntgrCLIExecute $strDev "exit"   
}

proc ntgrSecurityPortAuthGetPortAuthSummaryInfo { strDev portLst colName } {
    set strOutput [ntgrCLIExecute $strDev "show dot1x summary all"]
    ntgrCLIParseCellTable $strOutput table

    set rows [keylget table rows]
    set colNamNum [ntgrCLICellTableColNumberFromText $table $colName]
    set colIntNum [ntgrCLICellTableColNumberFromText $table "interface"]
    
    set lstRet {}
    foreach portId $portLst {
        if {[regexp -nocase {service} $portId]} {
            if {[regexp -nocase $colName "Control Mode"]} {
                set temp "force-authorized"    
            }
        } else {
            set rowNamNum [ntgrCLICellTableRowNumberFromText $table $portId $colIntNum]
            set temp [ntgrCLICellTableCellData $table $rowNamNum $colNamNum]    
        }          
        Netgear_log_file "ntgrSecurityPortAuthGetPortAuthSummaryInfo portId:" $portId
        Netgear_log_file "ntgrSecurityPortAuthGetPortAuthSummaryInfo value:" $temp
        lappend lstRet $portId $temp    
    }
    return $lstRet   
}

proc ntgrSecurityManagementAuthListConfigDot1xList { strDev list_config } {
    ntgrCLIExecute $strDev "configure"
    if {$list_config == ""} {
        ntgrCLIExecute $strDev "no aaa authentication dot1x default"    
    } else {
        ntgrCLIExecute $strDev "aaa authentication dot1x default $list_config"
    }
    ntgrCLIExecute $strDev "exit" 
    ntgrCLIExecute $strDev "show authentication methods | section DOT1X"  
}

proc ntgrSecurityManagementPortAuthConfigAdminModeStatus { strDev admin_mode } {
    ntgrCLIExecute $strDev "configure"
    if {[regexp -nocase "enable" $admin_mode]} {
        ntgrCLIExecute $strDev "dot1x system-auth-control"    
    } elseif {[regexp -nocase "disable" $admin_mode]} {
        ntgrCLIExecute $strDev "no dot1x system-auth-control"
    }
    ntgrCLIExecute $strDev "exit"   
}

proc ntgrSecurityManagementPortAuthConfigVlanAssignModeStatus { strDev admin_mode } {
    ntgrCLIExecute $strDev "configure"
    if {[regexp -nocase "enable" $admin_mode]} {
        ntgrCLIExecute $strDev "authorization network radius"    
    } elseif {[regexp -nocase "disable" $admin_mode]} {
        ntgrCLIExecute $strDev "no authorization network radius"
    }
    ntgrCLIExecute $strDev "exit"   
}

proc ntgrSecurityManagementPortAuthConfigDynamicDot1xVlanModeStatus { strDev admin_mode } {
    ntgrCLIExecute $strDev "configure"
    if {[regexp -nocase "enable" $admin_mode]} {
        ntgrCLIExecute $strDev "dot1x dynamic-vlan enable"    
    } elseif {[regexp -nocase "disable" $admin_mode]} {
        ntgrCLIExecute $strDev "no dot1x dynamic-vlan enable"
    }
    ntgrCLIExecute $strDev "exit"   
}

proc ntgrSecurityPortAuthGetPortAuthClientInfo { strDev portLstInfo } {
    set lstRet {}
    foreach {portId priName} $portLstInfo {
        set strOutput [ntgrCLIExecute $strDev "show dot1x clients $portId"]
        set lstOutput [split $strOutput \n]
        set new_lstOutput [list]
        foreach str_line $lstOutput {
            if {[regexp {\w+\.+} $str_line]} {
                lappend new_lstOutput $str_line      
            } elseif {![string equal [string trim $str_line] ""]} { 
                set end_str [lindex $new_lstOutput end]
                append end_str $str_line
                set new_lstOutput [lreplace $new_lstOutput end end $end_str]
            }   
        }
        set lstOutput $new_lstOutput 
        foreach strLineOutput $lstOutput {
            set matValue ""
            if {[regexp -nocase "$priName\\.+ *(\[\\w0-9: /]+)" [string trim $strLineOutput] mat matValue]} {
                Netgear_log_file "ntgrSecurityPortAuthGetPortAuthClientInfo portId:" $portId
                Netgear_log_file "ntgrSecurityPortAuthGetPortAuthClientInfo value:" $matValue
                lappend lstRet $portId [string trim $matValue]
            }
        }
    } 
    return $lstRet       
}

proc ntgrSecurityManagementAddRADIUSAcctServerConfiguration {strDev list_config} {
    keylget list_config SERVER server
    keylget list_config SECRET_MODE secret_mode
    keylget list_config SECRET_PASSWORD secret_password
    keylget list_config ACCT_MODE acct_mode
    
    ntgrCLIExecute $strDev "configure"
    ntgrCLIExecute $strDev "radius server host acct $server"
    if {[regexp -nocase "yes" $secret_mode] == 1} {
        ntgrCLIExecute $strDev "radius server key acct $server"
        ntgrCLIExecute $strDev $secret_password
        ntgrCLIExecute $strDev $secret_password     
    }
    
    if {[regexp -nocase "yes" $acct_mode] == 1} {
        ntgrCLIExecute $strDev "radius accounting mode"        
    } else {
        ntgrCLIExecute $strDev "no radius accounting mode"
    }
    ntgrCLIExecute $strDev "exit"
}

proc ntgrSecurityManagementDelRADIUAcctServerConfigure {strDev serv_info} {
    set serv 0
    keylget serv_info SERVER    serv
    ntgrCLIExecute $strDev "configure"
  
    if { $serv!=0 } {
        ntgrCLIExecute $strDev "no radius server host acct $serv"
    }
    ntgrCLIExecute $strDev "exit"
}

proc ntgrSecurityManagementSecurityConfigAcctModeStatus { strDev admin_mode } {
    ntgrCLIExecute $strDev "configure"
    if {[regexp -nocase "enable" $admin_mode]} {
        set out_put [ntgrCLIExecute $strDev "radius accounting mode"]    
    } elseif {[regexp -nocase "disable" $admin_mode]} {
        set out_put [ntgrCLIExecute $strDev "no radius accounting mode"]
    }
    ntgrCLIExecute $strDev "exit" 
    Netgear_log_file "ntgrSecurityManagementSecurityConfigAcctModeStatus out_put:" $out_put 
    return $out_put 
}

proc ntgrSecurityManagementSecurityConfigReTranmit { strDev {num 4} } {
    ntgrCLIExecute $strDev "configure"
    ntgrCLIExecute $strDev "radius server retransmit $num"    
    ntgrCLIExecute $strDev "exit" 
    
    set re_transmit 0
    set out_put [ntgrCLIExecute $strDev "show radius"]  
    regexp -nocase {Number of Retransmits *\.+ *(\d+)} $out_put match re_transmit  
    Netgear_log_file "ntgrSecurityManagementSecurityConfigReTranmit, out_put:" $out_put 
    Netgear_log_file "ntgrSecurityManagementSecurityConfigReTranmit, re_transmit:" $re_transmit
    Netgear_log_file "ntgrSecurityManagementSecurityConfigReTranmit" "End!" 
    return $re_transmit 
}

proc ntgrSecurityManagementSecurityConfigReTranmitInterval { strDev {num 5} } {
    ntgrCLIExecute $strDev "configure"
    ntgrCLIExecute $strDev "radius server timeout $num"    
    ntgrCLIExecute $strDev "exit" 
    
    set re_transmit_interval 0
    set out_put [ntgrCLIExecute $strDev "show radius"]  
    regexp -nocase {Timeout Duration *\.+ *(\d+)} $out_put match re_transmit_interval  
    Netgear_log_file "ntgrSecurityManagementSecurityConfigReTranmit, out_put:" $out_put 
    Netgear_log_file "ntgrSecurityManagementSecurityConfigReTranmit, re_transmit:" $re_transmit_interval
    Netgear_log_file "ntgrSecurityManagementSecurityConfigReTranmit" "End!" 
    return $re_transmit_interval 
}

proc ntgrSecurityManagementSecurityEnOrDisMessageAuthenticator { strDev serv_info status } {
    set serv 0
    keylget serv_info SERVER    serv
    
    ntgrCLIExecute $strDev "configure"
    if {[regexp -nocase {enable} $status]} {
        set strCmd "radius server msgauth $serv"
    } elseif {[regexp -nocase {disable} $status]} {
        set strCmd "no radius server msgauth $serv"
    } 
    ntgrCLIExecute $strDev $strCmd  
    ntgrCLIExecute $strDev "exit" 

    set out_put [ntgrCLIExecute $strDev "show radius servers"]  
    Netgear_log_file "ntgrSecurityManagementSecurityEnOrDisMessageAuthenticator, out_put:" $out_put 
    Netgear_log_file "ntgrSecurityManagementSecurityEnOrDisMessageAuthenticator" "End!"  
}

proc ntgrSecurityPortAuthConfigPortGuestVlan { strDev guest_vlan_info } {
    set intf_Id ""
    set vlan_id ""
    set period ""
    keylget guest_vlan_info INTFID          intf_Id
    keylget guest_vlan_info VLANID          vlan_id
    keylget guest_vlan_info VLANPERIOD      period
    ntgrCLIExecute $strDev "configure"
    ntgrCLIExecute $strDev "interface $intf_Id"
    if {$vlan_id == 0} {
        ntgrCLIExecute $strDev "no dot1x guest-vlan"    
    } else {
        ntgrCLIExecute $strDev "dot1x guest-vlan $vlan_id"
    } 
    if {$period != ""} {
        ntgrCLIExecute $strDev "dot1x timeout guest-vlan-period $period"    
    }         
    ntgrCLIExecute $strDev "exit" 
    ntgrCLIExecute $strDev "exit"   
}

proc ntgrSecurityPortAuthGetPortAuthDetailInfo { strDev portLstInfo } {
    set lstRet {}
    foreach {portId priName} $portLstInfo {
        set strOutput [ntgrCLIExecute $strDev "show dot1x detail $portId"]
        set lstOutput [split $strOutput \n]
        foreach strLineOutput $lstOutput {
            set matValue ""
            if {[regexp -nocase "$priName\\.+ *(\[\\w0-9: /]+)" [string trim $strLineOutput] mat matValue]} {
                Netgear_log_file "ntgrSecurityPortAuthGetPortAuthDetailInfo portId:" $portId
                Netgear_log_file "ntgrSecurityPortAuthGetPortAuthDetailInfo value:" $matValue
                lappend lstRet $portId [string trim $matValue]
            }
        }
    } 
    return $lstRet       
}

proc ntgrSecurityPortAuthConfigPortReauthPeriod { strDev port_reauth_period } {
    ntgrCLIExecute $strDev "configure"
    for {set i 0} {$i<[llength $port_reauth_period]} {incr i 2} {
        set port_id [lindex $port_reauth_period $i]
        set port_reauth_period [lindex $port_reauth_period [expr $i+1]]
        ntgrCLIExecute $strDev "interface $port_id"
        if {[regexp -nocase {default} $port_reauth_period]} {
            ntgrCLIExecute $strDev "no dot1x re-authentication"
            ntgrCLIExecute $strDev "dot1x timeout reauth-period 3600"      
        } else {
            ntgrCLIExecute $strDev "dot1x re-authentication"
            ntgrCLIExecute $strDev "dot1x timeout reauth-period $port_reauth_period"
        }  
        ntgrCLIExecute $strDev "exit"                
    }
    ntgrCLIExecute $strDev "exit"   
}

proc ntgrSecurityPortAuthConfigPortUnauthVlan { strDev unauth_vlan_info } {
    keylget unauth_vlan_info INTFID          intf_Id
    keylget unauth_vlan_info VLANID          vlan_id
    ntgrCLIExecute $strDev "configure"
    ntgrCLIExecute $strDev "interface $intf_Id"
    if {$vlan_id == 0} {
        ntgrCLIExecute $strDev "no dot1x unauthenticated-vlan"    
    } else {
        ntgrCLIExecute $strDev "dot1x unauthenticated-vlan $vlan_id"
    }     
    ntgrCLIExecute $strDev "exit" 
    ntgrCLIExecute $strDev "exit"   
}

proc ntgrSecurityPortAuthConfigEAPOLFloodMode { strDev eapol_flood_mode } {
    ntgrCLIExecute $strDev "configure"
	if {[regexp -nocase "enable" $eapol_flood_mode]} {
		ntgrCLIExecute $strDev "dot1x eapolflood"	
	} elseif {[regexp -nocase "disable" $eapol_flood_mode]} {
		ntgrCLIExecute $strDev "no dot1x eapolflood"		
	}
    ntgrCLIExecute $strDev "exit"   
}

proc ntgrSecurityPortAuthConfigMonitorMode { strDev monitor_mode } {
    ntgrCLIExecute $strDev "configure"
	if {[regexp -nocase "enable" $monitor_mode]} {
		ntgrCLIExecute $strDev "dot1x system-auth-control monitor"	
	} elseif {[regexp -nocase "disable" $monitor_mode]} {
		ntgrCLIExecute $strDev "no dot1x system-auth-control monitor"		
	}
    ntgrCLIExecute $strDev "exit"   
}

proc ntgrSecurityPortAuthConfigMabStatus { strDev port_mab_status } {
    ntgrCLIExecute $strDev "configure"
    for {set i 0} {$i<[llength $port_mab_status]} {incr i 2} {
        set port_id [lindex $port_mab_status $i]
        set port_mode [lindex $port_mab_status [expr $i+1]]
        ntgrCLIExecute $strDev "interface $port_id"
        if {[regexp -nocase "enable" $port_mode]} {
			ntgrCLIExecute $strDev "dot1x mac-auth-bypass"	
		} elseif {[regexp -nocase "disable" $port_mode]} {
			ntgrCLIExecute $strDev "no dot1x mac-auth-bypass"		
		}
        ntgrCLIExecute $strDev "exit"               
    }
    ntgrCLIExecute $strDev "exit"   
}

proc ntgrSecurityPortAuthClearAuthenticationHistory { strDev } {
    ntgrCLIExecute $strDev "clear dot1x authentication-history"          
}

proc ntgrSecurityPortAuthGetAuthenticationHistoryClientCount { strDev } {
    set strOutput [ntgrCLIExecute $strDev "show dot1x authentication-history all"] 
    if {[regexp -nocase {interface} $strOutput] == 1} {
        ntgrCLIParseCellTable $strOutput table
        set rows [keylget table rows]
    } else {
        set rows 0
    }         
    Netgear_log_file "ntgrSecurityPortAuthGetAuthenticationHistoryClientCount rows:" $rows
    return $rows
} 

proc ntgrSecurityPortAuthGetPortAuthHistoryInfo { strDev portLst colName } {
    set strOutput [ntgrCLIExecute $strDev "show dot1x authentication-history all"]
    ntgrCLIParseCellTable $strOutput table

    set rows [keylget table rows]
    set colNamNum [ntgrCLICellTableColNumberFromText $table $colName]
    set colIntNum [ntgrCLICellTableColNumberFromText $table "interface"]
    
    set lstRet {}
    foreach portId $portLst {
        set rowNamNum [ntgrCLICellTableRowNumberFromText $table $portId $colIntNum]
        set temp [ntgrCLICellTableCellData $table $rowNamNum $colNamNum]           
        Netgear_log_file "ntgrSecurityPortAuthGetPortAuthHistoryInfo portId:" $portId
        Netgear_log_file "ntgrSecurityPortAuthGetPortAuthHistoryInfo value:" $temp
        lappend lstRet $portId $temp    
    }
    return $lstRet   
}

proc ntgrSecurityPortAuthConfigQuietPeriod { strDev port_quiet_period } {
    ntgrCLIExecute $strDev "configure"
    for {set i 0} {$i<[llength $port_quiet_period]} {incr i 2} {
        set port_id [lindex $port_quiet_period $i]
        set quiet_period [lindex $port_quiet_period [expr $i+1]]
        ntgrCLIExecute $strDev "interface $port_id"
        ntgrCLIExecute $strDev "dot1x timeout quiet-period $quiet_period"
        ntgrCLIExecute $strDev "exit"               
    }
    ntgrCLIExecute $strDev "exit"   
}

proc ntgrSecurityManagementRadiusClearRadiusStatistics { strDev } {
    ntgrCLIExecute $strDev "clear radius statistics"   
}

proc ntgrSecurityManagementGetRadiusStatistics { strDev radiusServerLstInfo } {
    set lstRet {}
    foreach {radiusName priName} $radiusServerLstInfo {
        set strOutput [ntgrCLIExecute $strDev "show radius statistics $radiusName"]
        set lstOutput [split $strOutput \n]
        foreach strLineOutput $lstOutput {
            set matValue ""
            if {[regexp -nocase "$priName\\.+ *(\[\\w0-9: /]+)" [string trim $strLineOutput] mat matValue]} {
                Netgear_log_file "ntgrSecurityManagementGetRadiusStatistics radiusName:" $radiusName
                Netgear_log_file "ntgrSecurityManagementGetRadiusStatistics value:" $matValue
                lappend lstRet $radiusName [string trim $matValue]
            }
        }
    } 
    return $lstRet       
}
