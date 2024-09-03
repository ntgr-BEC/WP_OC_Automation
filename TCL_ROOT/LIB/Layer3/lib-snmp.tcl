####################################################################################
#  File Name   : lib-ntgr-snmp.tcl                                                 #
#                                                                                  #
#  Description :                                                                   #
#        This file includes functions used for SNMP configuration.                 #
#                                                                                  #
#  History     :                                                                   #
#        Date          Programmer         Description                              #
#        Mar 12, 2007  Scott Zhang        Created                                  #
#                                                                                  #
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrConfigSnmpSysName
#
#  Description    : This function is called to configure SNMP system name.
#
#  Usage          : _ntgrConfigSnmpSysName <switch_name sysname>
#
#*******************************************************************************
proc _ntgrConfigSnmpSysName {switch_name sysname} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server sysname \"$sysname\"\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigSnmpLocation
#
#  Description    : This function is called to configure SNMP location infor.
#
#  Usage          : _ntgrConfigSnmpLocation <switch_name location>
#
#*******************************************************************************
proc _ntgrConfigSnmpLocation {switch_name location} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server location \"$location\"\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigSnmpContact
#
#  Description    : This function is called to configure SNMP system contact.
#
#  Usage          : _ntgrConfigSnmpContact <switch_name contact>
#
#*******************************************************************************
proc _ntgrConfigSnmpContact {switch_name contact} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server contact \"$contact\"\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigCommunity
#
#  Description    : This function is called to configure SNMP community.
#
#  Usage          : _ntgrConfigCommunity <switch_name community>
#
#*******************************************************************************
proc _ntgrConfigCommunity {switch_name community} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server community \"$community\"\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDeleteCommunity
#
#  Description    : This function is called to delete SNMP community.
#
#  Usage          : _ntgrDeleteCommunity <switch_name community>
#
#*******************************************************************************
proc _ntgrDeleteCommunity {switch_name community} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no snmp-server community \"$community\"\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetCommunityReadOnly
#
#  Description    : This function is called to configure SNMP community to read-only.
#
#  Usage          : _ntgrSetCommunityReadOnly <switch_name community>
#
#*******************************************************************************
proc _ntgrSetCommunityReadOnly {switch_name community} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server community ro \"$community\"\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetCommunityReadWrite
#
#  Description    : This function is called to configure SNMP community to read-write.
#
#  Usage          : _ntgrSetCommunityReadWrite <switch_name community>
#
#*******************************************************************************
proc _ntgrSetCommunityReadWrite {switch_name community} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server community rw \"$community\"\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableCommunity
#
#  Description    : This function is called to enable SNMP community.
#
#  Usage          : _ntgrEnableCommunity <switch_name community>
#
#*******************************************************************************
proc _ntgrEnableCommunity {switch_name community} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server community mode \"$community\"\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableCommunity
#
#  Description    : This function is called to disable SNMP community.
#
#  Usage          : _ntgrDisableCommunity <switch_name community>
#
#*******************************************************************************
proc _ntgrDisableCommunity {switch_name community} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no snmp-server community mode \"$community\"\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrLimitSnmpClient
#
#  Description    : This function is called to limit client access with SNMP community.
#
#  Usage          : _ntgrLimitSnmpClient <switch_name community ip mask>
#
#*******************************************************************************
proc _ntgrLimitSnmpClient {switch_name community ip mask} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server community ipaddr $ip \"$community\"\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server community ipmask $mask \"$community\"\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrAddTrapReceiver
#
#  Description    : This function is called to add a SNMP trap receiver.
#
#  Usage          : _ntgrAddTrapReceiver <switch_name community ip ver>
#
#*******************************************************************************
proc _ntgrAddTrapReceiver {switch_name community ip ver} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$ver =={}} {
        exp_send -i $cnn_id "snmptrap $community ipaddr $ip\r"
    } else {
        exp_send -i $cnn_id "snmptrap $community ipaddr $ip snmpversion $ver\r"
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrAddIpv6TrapReceiver
#
#  Description    : This function is called to add a SNMP trap receiver.
#
#  Usage          : _ntgrAddIpv6TrapReceiver <switch_name community ip ver>
#
#*******************************************************************************
proc _ntgrAddIpv6TrapReceiver {switch_name community ip {ver {}}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$ver =={}} {
        exp_send -i $cnn_id "snmptrap $community ip6addr $ip\r"
    } else {
        exp_send -i $cnn_id "snmptrap $community ip6addr $ip snmpversion $ver\r"
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDelTrapReceiver
#
#  Description    : This function is called to delete a SNMP trap receiver.
#
#  Usage          : _ntgrDelTrapReceiver <switch_name community ip>
#
#*******************************************************************************
proc _ntgrDelTrapReceiver {switch_name community ip ver} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    
    exp_send -i $cnn_id "no snmptrap $community ipaddr $ip\r"
   
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDelIpv6TrapReceiver
#
#  Description    : This function is called to delete a SNMP trap receiver.
#
#  Usage          : _ntgrDelIpv6TrapReceiver <switch_name community ip>
#
#*******************************************************************************
proc _ntgrDelIpv6TrapReceiver {switch_name community ip {ver {}}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    
    exp_send -i $cnn_id "no snmptrap $community ip6addr $ip\r"
   
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrEnableTrapReceiver
#
#  Description    : This function is called to enable a SNMP trap receiver.
#
#  Usage          : _ntgrEnableTrapReceiver <switch_name community ip>
#
#*******************************************************************************
proc _ntgrEnableTrapReceiver {switch_name community ip} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmptrap mode $community $ip\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableTrapReceiver
#
#  Description    : This function is called to disable a SNMP trap receiver.
#
#  Usage          : _ntgrDisableTrapReceiver <switch_name community ip>
#
#*******************************************************************************
proc _ntgrDisableTrapReceiver {switch_name community ip} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no snmptrap mode $community $ip\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrModifyTrapReceiver
#
#  Description    : This function is called to modify a SNMP trap receiver.
#
#  Usage          : _ntgrModifyTrapReceiver <switch_name community oldip newip>
#
#*******************************************************************************
proc _ntgrModifyTrapReceiver {switch_name community oldip newip} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmptrap address $community $oldip $newip\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrModifyTrapReceiverVer
#
#  Description    : This function is called to modify a SNMP receiver's version.
#
#  Usage          : _ntgrModifyTrapReceiverVer <switch_name community ip ver>
#
#*******************************************************************************
proc _ntgrModifyTrapReceiverVer {switch_name community ip ver} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmptrap snmpversion $community $ip $ver\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableAuthTrap
#
#  Description    : This function is called to enable authentication trap.
#
#  Usage          : _ntgrEnableAuthTrap <switch_name>
#
#*******************************************************************************
proc _ntgrEnableAuthTrap {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server traps\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableAuthTrap
#
#  Description    : This function is called to disable authentication trap.
#
#  Usage          : _ntgrDisableAuthTrap <switch_name>
#
#*******************************************************************************
proc _ntgrDisableAuthTrap {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no snmp-server traps\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableTrapLink
#
#  Description    : This function is called to enable trap link up/down.
#
#  Usage          : _ntgrEnableTrapLink <switch_name>
#
#*******************************************************************************
proc _ntgrEnableTrapLink {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server traps linkmode\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableTrapLink
#
#  Description    : This function is called to disable trap link up/down.
#
#  Usage          : _ntgrDisableTrapLink <switch_name>
#
#*******************************************************************************
proc _ntgrDisableTrapLink {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no snmp-server traps linkmode\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableTrapPort
#
#  Description    : This function is called to enable trap link up/down on port.
#
#  Usage          : _ntgrEnableTrapPort <switch_name port>
#
#*******************************************************************************
proc _ntgrEnableTrapPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$port == {}} {
        exp_send -i $cnn_id "snmp trap link-status all\r"
    } else {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "snmp trap link-status\r"
    }
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableTrapPort
#
#  Description    : This function is called to disable trap link up/down on port.
#
#  Usage          : _ntgrDisableTrapPort <switch_name port>
#
#*******************************************************************************
proc _ntgrDisableTrapPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$port == {}} {
        exp_send -i $cnn_id "no snmp trap link-status all\r"
    } else {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no snmp trap link-status\r"
    }
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableTrapMultiUser
#
#  Description    : This function is called to enable trap multi users login.
#
#  Usage          : _ntgrEnableTrapMultiUser <switch_name>
#
#*******************************************************************************
proc _ntgrEnableTrapMultiUser {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server traps multiusers\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableTrapMultiUser
#
#  Description    : This function is called to disable trap multi users login.
#
#  Usage          : _ntgrDisableTrapMultiUser <switch_name>
#
#*******************************************************************************
proc _ntgrDisableTrapMultiUser {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no snmp-server traps multiusers\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableTrapSTP
#
#  Description    : This function is called to enable trap spanning-tree changing.
#
#  Usage          : _ntgrEnableTrapSTP <switch_name>
#
#*******************************************************************************
proc _ntgrEnableTrapSTP {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server traps stpmode\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableTrapSTP
#
#  Description    : This function is called to disable trap spanning-tree changing.
#
#  Usage          : _ntgrDisableTrapSTP <switch_name>
#
#*******************************************************************************
proc _ntgrDisableTrapSTP {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no snmp-server traps stpmode\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableTrapViolation
#
#  Description    : This function is called to enable trap MAC violation.
#
#  Usage          : _ntgrEnableTrapViolation <switch_name port>
#
#*******************************************************************************
proc _ntgrEnableTrapViolation {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "snmp-server traps violation\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableTrapViolation
#
#  Description    : This function is called to disable trap MAC violation.
#
#  Usage          : _ntgrDisableTrapViolation <switch_name port>
#
#*******************************************************************************
proc _ntgrDisableTrapViolation {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no snmp-server traps violation\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrsnmpwalk
#
#  Description    : This function is called to snmpget 
#
#  Usage          : _ntgrsnmpwalk  index dutIP <expect string>
#
#*******************************************************************************

proc _ntgrsnmpwalk {index dutIP {expect {}}} {
  set ret 0
  set id -1
  global ntgr_NETSNMPWALK
  keylget ntgr_NETSNMPWALK($index)  SNMP_VERSION   version
  keylget ntgr_NETSNMPWALK($index)  SNMP_COMMUNITY community
  set netsnmpwalk "C:/AUTOMATION/BIN/net-snmp/snmpwalk.exe"
  spawn $netsnmpwalk -v $version -c $community $dutIP 
  Netgear_log_file "lib-snmp.tcl" " $netsnmpwalk -v $version -c $community $dutIP "
  set id $spawn_id
  if {$expect != {}} {
      expect -i $id expect
      Netgear_log_file "lib-snmp.tcl" "netsnmpwalk $expect_out(buffer)"
      set match [string first $expect $expect_out(buffer)]
      if {$match != -1} {
        set ret 1
        Netgear_log_file "lib-snmp.tcl" "snmpwalk result has expectation - $expect"
      }
  } 
  return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrsnmpget
#
#  Description    : This function is called to snmpget 
#
#  Usage          : _ntgrsnmpget  index dutIP <expect string>
#
#*******************************************************************************

proc _ntgrsnmpget {index dutIP {expect {}}} {
  set ret 0
  set id -1
  global ntgr_NETSNMPGET
  keylget ntgr_NETSNMPGET($index) SNMP_VERSION  version
  keylget ntgr_NETSNMPGET($index) SNMP_OBJECTIVE objective
  set netsnmpget "C:/AUTOMATION/BIN/net-snmp/snmpget.exe"
  if {$version !=3} {
      keylget ntgr_NETSNMPGET($index) SNMP_COMMUNITY community
      spawn $netsnmpget -v $version -c $community $dutIP $objective
      Netgear_log_file "lib-snmp.tcl" " $netsnmpget -v $version -c $community $dutIP $objective"
  } else {
      keylget ntgr_NETSNMPGET($index) SNMP_AUTHENTICATION    auth
      keylget ntgr_NETSNMPGET($index) SNMP_USERNAME          user
      keylget ntgr_NETSNMPGET($index) SNMP_AUTHALGORITHEM    algo
      keylget ntgr_NETSNMPGET($index) SNMP_PASSWORD          pass
      keylget ntgr_NETSNMPGET($index) SNMP_ENCRYPTION        enmode
      keylget ntgr_NETSNMPGET($index) SNMP_ENCRYPTKEY        enkey
      switch -exact -- $auth {
  		    NULL { 
  		        spawn $netsnmpget -v $version -u $user $dutIP $objective;
              Netgear_log_file "lib-snmp.tcl" " $netsnmpget -v $version -u $user $dutIP $objective"; 
  		    }
          authNoPriv  { 
              spawn $netsnmpget -v $version -u $user -l $auth -a $algo -A $pass $dutIP $objective; 
              Netgear_log_file "lib-snmp.tcl" " $netsnmpget -v $version -u $user -l $auth -a $algo -A $pass $dutIP $objective";
          }
          authPriv { 
                 spawn $netsnmpget -v $version -u $user -l $auth -a $algo -A $pass  -x $enmode -X $enkey $dutIP $objective; 
              Netgear_log_file "lib-snmp.tcl" " $netsnmpget -v $version -u $user -l $auth -a $algo -A $pass  -x $enmode -X $enkey $dutIP $objective";
          } 		   
  		}
  }

  set id $spawn_id
  if {$expect != {}} {
      expect -i $id expect
      Netgear_log_file "lib-snmp.tcl" "netsnmpget $expect_out(buffer)"
      set result $expect_out(buffer)
      regsub -all {\r|\n|\.} $result "" result
      set result [string trim $result]
      regsub -all {\r|\n|\.} $expect "" expect
      set expect [string trim $expect]
      set match [string first $expect $result]
      if {$match != -1} {
        set ret 1
        Netgear_log_file "lib-snmp.tcl" "snmpget result has expectation - $expect"
      }
  } else {
    expect -i $id timeout 
    Netgear_log_file "lib-snmp.tcl" "netsnmpget $expect_out(buffer)"
    set ret $expect_out(buffer)
  }
  return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrsnmpset
#
#  Description    : This function is called to snmpset 
#
#  Usage          : _ntgrsnmpset  index dutIP <expect string>
#
#*******************************************************************************

proc _ntgrsnmpset {index dutIP content  {expect {}}} {
  set ret 0
  set id -1
  global ntgr_NETSNMPSET
  keylget ntgr_NETSNMPSET($index) SNMP_VERSION  version
  keylget ntgr_NETSNMPSET($index) SNMP_OBJECTIVE objective
  set netsnmpset "C:/AUTOMATION/BIN/net-snmp/snmpset.exe"
  if {$version !=3} {
       keylget ntgr_NETSNMPSET($index) SNMP_COMMUNITY community
       spawn $netsnmpset -v $version -c $community $dutIP $objective s $content
       Netgear_log_file "lib-snmp.tcl" " $netsnmpset -v $version -c $community $dutIP $objective s $content"
  
  } else {
      keylget ntgr_NETSNMPSET($index) SNMP_AUTHENTICATION    auth
      keylget ntgr_NETSNMPSET($index) SNMP_USERNAME          user
      keylget ntgr_NETSNMPSET($index) SNMP_AUTHALGORITHEM    algo
      keylget ntgr_NETSNMPSET($index) SNMP_PASSWORD          pass
      keylget ntgr_NETSNMPSET($index) SNMP_ENCRYPTION        enmode
      keylget ntgr_NETSNMPSET($index) SNMP_ENCRYPTKEY        enkey
      switch -exact -- $auth {
  		    NULL  {
  		        spawn $netsnmpset -v $version -u $user $dutIP $objective s $content;
              Netgear_log_file "lib-snmp.tcl" "$netsnmpset -v $version -u $user $dutIP $objective s $content";
  		    }
          authNoPriv  {
              spawn $netsnmpset -v $version -u $user -l $auth -a $algo -A $pass $dutIP $objective s $content; 
              Netgear_log_file "lib-snmp.tcl" "$netsnmpset -v $version -u $user -l $auth -a $algo -A $pass $dutIP $objective s $content";
          }
          authPriv {
              spawn $netsnmpset -v $version -u $user -l $auth -a $algo -A $pass -x $enmode -X $enkey $dutIP $objective s $content; 
              Netgear_log_file "lib-snmp.tcl" "$netsnmpset -v $version -u $user -l $auth -a $algo -A $pass -x $enmode -X $enkey $dutIP $objective s $content";          
          } 		   
  		}
  }
  set id $spawn_id
  if {$expect != {}} {
      expect -i $id expect
      Netgear_log_file "lib-snmp.tcl" "_ntgrsnmpset $expect_out(buffer)"
      set match [string first $expect $expect_out(buffer)]
      if {$match != -1} {
        set ret 1
        Netgear_log_file "lib-snmp.tcl" "snmpset result has expectation - $expect"
      }
  }
  return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrsnmpv3authentication
#
#  Description    : This function is called to set snmpv3 authentication
#
#  Usage          : _ntgrsnmpv3authentication <switch> <user> <algorithm>
#
#*******************************************************************************
proc _ntgrsnmpv3authentication {switch_name user algorithm} {
   Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "username snmpv3 authentication $user $algorithm\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrsnmpv3accessmode
#
#  Description    : This function is called to set snmpv3 accessmode
#
#  Usage          : _ntgrsnmpv3accessmode <switch> <user> <mode>
#
#*******************************************************************************
proc _ntgrsnmpv3accessmode {switch_name user mode} {
   Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "username snmpv3 accessmod $user $mode\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrsnmpv3encryptionmode
#
#  Description    : This function is called to set snmpv3 encryption mode
#
#  Usage          : _ntgrsnmpv3encryptionmode <switch> <user> <encrypt_mode> <encrypt_key>
#
#*******************************************************************************
proc _ntgrsnmpv3encryptionmode {switch_name user mode key} {
   Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "username snmpv3 encryption $user $mode $key\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrsnmpv3encryptionkey
#
#  Description    : This function is called to set snmpv3 encryption key
#
#  Usage          : _ntgrsnmpv3encryptionkey <switch> <user> <key> <mode>
#
#*******************************************************************************
proc _ntgrsnmpv3encryptionkey {switch_name user key {mode "des"}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "username snmpv3 encryption encrypted $user $mode $key\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#6*******************************************************************************
#  Function Name	: _ntgrSetSnmpTrapflags
#
#  Description    	: This function is called to enbale SnmpTrapflags
#         
#  Usage          	: _ntgrSetSnmpTrapflags <switch_name> 
#
#
#*******************************************************************************
proc _ntgrSetSnmpTrapflags {switch_name flags} {
  
	Netgear_connect_switch $switch_name
    	set connection_id [_get_switch_handle $switch_name]	
    	expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "snmp-server enable traps $flags\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
    Netgear_disconnect_switch $switch_name
}

#7*******************************************************************************
#  Function Name	: _ntgrDisableSnmpTrapflags
#
#  Description    	: This function is called to disable SnmpTrapflags
#         
#  Usage          	: _ntgrDisableSnmpTrapflags<switch_name> 
#
#
#*******************************************************************************
proc _ntgrDisableSnmpTrapflags {switch_name {flags {}}} {
  
	Netgear_connect_switch $switch_name
    	set connection_id [_get_switch_handle $switch_name]	
    	expect -i $connection_id -re "#"
        exp_send -i $connection_id "configure\r"
        sleep 1
        expect -i $connection_id -re "#"
        exp_send -i $connection_id "no snmp-server enable traps $flags\r"
        sleep 1
        exp_send -i $connection_id "exit\r"
    Netgear_disconnect_switch $switch_name
}