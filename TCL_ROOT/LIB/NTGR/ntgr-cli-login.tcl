proc ntgrCLILogin {strDevice} {
    set ret [Netgear_connect_switch $strDevice]
    Netgear_log_file "ntgr-cli-login.tcl" "ntgr cli login return value $ret"
    return $ret
}

proc ntgrCLILogout {strDevice} {
    Netgear_disconnect_switch $strDevice
}

proc _get_switch_ipv6_addr {switch_id} {
    set fdConfigDUTXml [open "C:/AUTOMATION/config/config_dut.xml"]
    set strXML [read $fdConfigDUTXml]
    close $fdConfigDUTXml

    # parse
    set xmlDoc  [dom parse $strXML]
    set xmlRoot [$xmlDoc documentElement]
    set xmlDUTList [$xmlRoot getElementsByTagName DUT_List]
    set xmlDUT [$xmlDUTList getElementsByTagName $switch_id]
    set ipv6address [[$xmlDUT getElementsByTagName Ipv6_Address] text]
    Netgear_log_file "ntgr-cli-login.tcl" " Switch:<$switch_id> ipv6 address=$ipv6address ."
    return $ipv6address
}


proc ntgrCLILoginIpv6 {product_id} {
    global NTGR_DUT_LIST
    Netgear_log_file "ntgr-cli-login.tcl" "Connecting to $product_id using ipv6..."
    set switch_ipv6_addr [_get_switch_ipv6_addr $product_id]
    set switch_ts_port [_ntgrGetTSPort $product_id]
    set switch_model   [_get_switch_model $product_id]
    
    set retId [_switch_connect_telnet $switch_ipv6_addr $switch_ts_port]
    if { $retId == -1 } {return -1}
    _set_switch_handle $product_id $retId
    if {[string first $product_id $NTGR_DUT_LIST] == -1} {
        set NTGR_DUT_LIST "$NTGR_DUT_LIST $product_id"
    }
}


proc ntgrKillTelnet {} {
  set pskill "C:/AUTOMATION/BIN/pstools/pskill.exe"
  spawn $pskill telnet.exe
  spawn $pskill cmd.exe
}