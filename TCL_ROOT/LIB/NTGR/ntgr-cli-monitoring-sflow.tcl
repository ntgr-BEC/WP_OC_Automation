#  File Name   :     ntgr-webui-monitoring-sflow.vbs
#
#  Function Name:  ntgrMonitoringsFlowReceiverConfiguration
#
#  Description  :  Config sFlow Receiver Configuration 
#
#  Arguments    :
#                  strDevice
#                  interface_params
#
#  Return Value :  NONE
#                                                                                 
proc ntgrMonitoringsFlowReceiverConfiguration {strDevice receiver_params} {
    ntgrCLIExecute $strDevice "configure"
    if {[keylget receiver_params receiver_address {}]} {
        ntgrCLIExecute $strDevice "sflow receiver [keylget receiver_params receiver_index] ip [keylget receiver_params receiver_address]"
    }
    if {[keylget receiver_params receiver_owner {}] && [keylget receiver_params receiver_timeout {}]} {
        ntgrCLIExecute $strDevice "sflow receiver [keylget receiver_params receiver_index] owner [keylget receiver_params receiver_owner] timeout [keylget receiver_params receiver_timeout]"
    }    
    if {[keylget receiver_params receiver_port {}]} {
        ntgrCLIExecute $strDevice "sflow receiver [keylget receiver_params receiver_index] port  [keylget receiver_params receiver_port]"
    }
    if {[keylget receiver_params max_datagram_size {}]} {
        ntgrCLIExecute $strDevice "sflow receiver [keylget receiver_params receiver_index] maxdatagram [keylget receiver_params max_datagram_size]"
    }
    ntgrCLIExecute $strDevice "exit"
    Netgear_log_file "ntgr-cli-security-sflow.tcl" "ntgrMonitoringsFlowReceiverConfiguration"
}


#  Function Name:  ntgrMonitoringsFlowSamplerConfiguration
#
#  Description  :  Config sFlow sampler Configuration 
#
#  Arguments    :
#                  strDevice
#                  sampler_params
#
#  Return Value :  NONE
#
proc ntgrMonitoringsFlowSamplerConfiguration {strDevice sampler_params} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface      [keylget sampler_params interface]"
    ntgrCLIExecute $strDevice "sflow sampler  [keylget sampler_params sampler_receiver_index]"
    ntgrCLIExecute $strDevice "sflow sampler rate [keylget sampler_params sampler_rate]"
    ntgrCLIExecute $strDevice "sflow sampler maxheadersize [keylget sampler_params sampler_max_headersize]"
    ntgrCLIExecute $strDevice "exit"
    ntgrCLIExecute $strDevice "exit"
    Netgear_log_file "ntgr-cli-security-sflow.tcl" "ntgrMonitoringsFlowSamplerConfiguration"
}


#  Function Name:  ntgrMonitoringsFlowPollerConfiguration
#
#  Description  :  Config sFlow poller Configuration 
#
#  Arguments    :
#                  strDevice
#                  sampler_params
#
#  Return Value :  NONE
#
proc ntgrMonitoringsFlowPollerConfiguration {strDevice poller_params} {
    ntgrCLIExecute $strDevice "configure"
    ntgrCLIExecute $strDevice "interface     [keylget poller_params interface]"
    ntgrCLIExecute $strDevice "sflow poller  [keylget poller_params poller_receiver_index]"
    ntgrCLIExecute $strDevice "sflow poller  interval [keylget poller_params poller_interval]"
    ntgrCLIExecute $strDevice "exit"
    ntgrCLIExecute $strDevice "exit"
    Netgear_log_file "ntgr-cli-security-sflow.tcl" "ntgrMonitoringsFlowPollerConfiguration"
}
