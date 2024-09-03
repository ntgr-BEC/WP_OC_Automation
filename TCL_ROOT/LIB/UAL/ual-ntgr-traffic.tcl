#!/bin/sh
################################################################################   
#
#  File Name		: ual-ntgr-traffic.tcl
#
#  Description       	:
#         This library defines the APIs to control traffic generator products like 
#	    Spirent, IXIA .
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V       Created
#        29-Jan-07     Nina Cheng        Modified for RIP and OSPF
#
#
################################################################################


#*******************************************************************************
#
#  Function Name	: UALConnectTester
#
#  Description    : This function initialise and connects to the traffic
#			  generator chassis (Ixia/Spirent)
#         
#  Usage          : UALConnectTester <product_id>
# 
#*******************************************************************************
proc UALConnectTester {product_id} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	set chassis_handle [_ntgrGetTGHandle $product_id]
	switch $switch_vendor {
		ixia	{
			if {$chassis_handle == -1} {
				_ntgrSetTGHandle $product_id [_ixiaConnect $product_id]
			}
		}
		spirent	{
        if {$chassis_handle == -1} {
            # Spirent returns 2 handle. The connection id and the project id
            set handle [_spirentConnect $product_id]
            _ntgrSetTGHandle $product_id [lindex $handle 0]
            _ntgrSetTGProjectHandle $product_id [lindex $handle 1]
        }
		}
		default		{
			Netgear_log_file "ual-ntgr-traffic.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: UALDisconnectTester
#
#  Description    : This function disconnects the traffic generator (Ixia/Spirent)
#         
#  Usage          : UALDisconnectTester <product_id>
# 
#*******************************************************************************
proc UALDisconnectTester {product_id} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				_ixiaDisconnect $product_id
				_ntgrSetTGHandle $product_id -1
			}
			spirent 	{
				_spirentDisconnect $product_id
				_ntgrSetTGHandle $product_id -1
				_ntgrSetTGProjectHandle $product_id -1
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
}

#*******************************************************************************
#
#  Function Name	: UALCheckTesterReachable
#
#  Description    : This function checks whether the Tester is reachable
#         
#  Usage          : UALCheckTesterReachable <product_id>
# 
#*******************************************************************************
proc UALCheckTesterReachable {product_id} {
	set switch_ip_addr  [_ntgrGetTGIPAddr $product_id]
	return [_switch_ping $switch_ip_addr]
}

#*******************************************************************************
#
#  Function Name	: UALLoadTester
#
#  Description    : This function configures the tester port
#         
#  Usage          : UALLoadTester <product_id>
# 
#*******************************************************************************
proc UALLoadTester {product_id} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	set port_list [_ntgrGetTrafficTestPortList $product_id]
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				_ixiaLoadConfig $product_id $port_list
			}
			spirent 	{
				_spirentLoadConfig $product_id $port_list
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
}

#*******************************************************************************
#
#  Function Name	: UALStartTraffic
#
#  Description    : This function starts the traffic stream created on the
#			  traffic generator
#         
#  Usage          : UALStartTraffic <product_id>
# 
#*******************************************************************************
proc UALStartTraffic {product_id} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				_ixiaStartTraffic $product_id
			}
			spirent 	{
				_spirentStartTraffic $product_id
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
}

#*******************************************************************************
#
#  Function Name	: UALStopTraffic
#
#  Description    : This function stops the traffic stream created on the
#			  traffic generator
#         
#  Usage          : UALStopTraffic <product_id>
# 
#*******************************************************************************
proc UALStopTraffic {product_id} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				_ixiaStopTraffic $product_id
			}
			spirent 	{
				_spirentStopTraffic $product_id
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
}

#*******************************************************************************
#
#  Function Name	: UALStartTrafficPerPort
#
#  Description    : This function starts the traffic stream created on the
#			  traffic generator per port
#         
#  Usage          : UALStartTrafficPerPort <product_id> <port>
# 
#*******************************************************************************
proc UALStartTrafficPerPort {product_id port} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				_ixiaStartTrafficPerPort $product_id $port
			}
			spirent 	{
				_spirentStartTrafficPerPort $product_id $port
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
}

#*******************************************************************************
#
#  Function Name	: UALStopTrafficPerPort
#
#  Description    : This function stops the traffic stream created on the
#			  traffic generator per port
#         
#  Usage          : UALStopTrafficPerPort <product_id> <port>
# 
#*******************************************************************************
proc UALStopTrafficPerPort {product_id port} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				_ixiaStopTrafficPerPort $product_id $port
			}
			spirent 	{
				_spirentStopTrafficPerPort $product_id $port
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
}

#*******************************************************************************
#
#  Function Name	: UALStartCapturePerPort
#
#  Description    : This function capture traffic per port
#         
#  Usage          : UALStartCapturePerPort <product_id> <port>
# 
#*******************************************************************************
proc UALStartCapturePerPort {product_id port} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				_ixiaStartCapturePerPort $product_id $port
			}
			spirent 	{
				_spirentStartCapturePerPort $product_id $port
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
}

#*******************************************************************************
#
#  Function Name	: UALStopCapturePerPort
#
#  Description    : This function stop capture traffic per port
#         
#  Usage          : UALStopCapturePerPort <product_id> <port>
# 
#*******************************************************************************
proc UALStopCapturePerPort {product_id port} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				_ixiaStopCapturePerPort $product_id $port
			}
			spirent 	{
				_spirentStopCapturePerPort $product_id $port
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
}

#*******************************************************************************
#
#  Function Name	: UALGetTotalFrameLoss
#
#  Description    : This function gets the TX and RX counters and verifies the
#			  frame loss.
#         
#  Usage          : UALGetTotalFrameLoss <product_id>
# 
#*******************************************************************************
proc UALGetTotalFrameLoss {product_id} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	set loss 0
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				set loss [_ixiaReportTotalLossFrame $product_id]
			}
			spirent 	{
				set loss [_spirentReportTotalLossFrame $product_id]
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
	return $loss
}

#*******************************************************************************
#
#  Function Name	: UALGetTotalFrameTx
#
#  Description    : This function gets the TX counters
#         
#  Usage          : UALGetTotalFrameTx <product_id>
# 
#*******************************************************************************
proc UALGetTotalFrameTx {product_id} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	set loss 0
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				set tx [_ixiaReportTotalFrameTx $product_id]
			}
			spirent 	{
				set tx [_spirentReportTotalFrameTx $product_id]
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
	return $tx
}

#*******************************************************************************
#
#  Function Name	: UALGetTotalFrameRx
#
#  Description    : This function gets the RX counters
#         
#  Usage          : UALGetTotalFrameRx <product_id>
# 
#*******************************************************************************
proc UALGetTotalFrameRx {product_id} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	set loss 0
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				set rx [_ixiaReportTotalFrameRx $product_id]
			}
			spirent 	{
				set rx [_spirentReportTotalFrameRx $product_id]
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
	return $rx
}


#*******************************************************************************
#
#  Function Name	: UALGetFrameLossPerPort
#
#  Description    : This function gets the TX and RX counters and verifies the
#			  frame loss on the given port.
#         
#  Usage          : UALGetFrameLossPerPort <product_id> <tx_port> <rx_port>
# 
#*******************************************************************************
proc UALGetFrameLossPerPort {product_id tx_port rx_port} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	set loss 0
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				set loss [_ixiaReportLossFramePerPort $product_id $tx_port $rx_port]
			}
			spirent 	{
				set loss [_spirentReportLossFramePerPort $product_id $tx_port $rx_port]
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
	return $loss
}

#*******************************************************************************
#
#  Function Name	: UALGetPortFrameLoss
#
#  Description    : This function gets the TX and RX counters and return a list of
#                    TX and RX.
#         
#  Usage          : UALGetPortFrameLoss <chassis_id> <port>
# 
#*******************************************************************************
proc UALGetPortFrameLoss {product_id port} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
			    # IXIA is not implemented.
			    	return [_ixiaReportTxRxFramePerPort $product_id $port ]
			}
			spirent 	{
				return [_spirentReportPortLossFrame $product_id $port]
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
}

#*******************************************************************************
#
#  Function Name	: UALClearCounter
#
#  Description    : This function clears counter from traffic generators
#         
#  Usage          : UALClearCounter <product_id>
# 
#*******************************************************************************
proc UALClearCounter {product_id} {
	set switch_vendor  [_ntgrGetTGVendorName $product_id]
	set switch_handle [_ntgrGetTGHandle $product_id]
	Netgear_log_file "ual-ntgr-traffic.tcl" "Chassis = $product_id , vendor = $switch_vendor"
	if {$switch_handle == -1} {
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester is not connected."
		Netgear_log_file "ual-ntgr-traffic.tcl" "Tester cannot be disconnected"
	} else {
		switch $switch_vendor {
			ixia		{
				_ixiaClearCounter $product_id
			}
			spirent 	{
				_spirentClearCounter $product_id
			}
			default		{
				Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
			}
		}
	}
}

#*******************************************************************************
#
#  Function Name  : UALPortLinkDown
#
#  Description    : This function is called to set the link status to down.
#
#  Usage          : UALPortLinkDown <tester_id port>
#
#*******************************************************************************
proc UALPortLinkDown {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaPortLinkDown $tester_id $port
        }
        spirent {
            _spirentPortLinkDown $tester_id $port
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALPortLinkUP
#
#  Description    : This function is called to set the link status to up.
#
#  Usage          : UALPortLinkUP <tester_id port>
#
#*******************************************************************************
proc UALPortLinkUP {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaPortLinkUP $tester_id $port
        }
        spirent {
            _spirentPortLinkUP $tester_id $port
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALUnLoadStream 
#
#  Description    : This function is called to delete the streams created in the
#			  specified ports list
#
#  Usage          : UALUnLoadStream  <tester_id> <port>
#
#*******************************************************************************
proc UALUnLoadStream  {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
		# To be done
		        _ixiaUnLoadStream $tester_id $port
        }
        spirent {
            _spirentUnLoadStream $tester_id $port
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALLoadStream 
#
#  Description    : This function is called to create the streams  in the
#			  specified ports list.
#
#  Usage          : UALLoadStream  <tester_id> <port>
#
#*******************************************************************************
proc UALLoadStream {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]
    switch $switch_vendor {
        ixia {
		        _ixiaLoadStream $tester_id $port
        }
        spirent {
            _spirentLoadStream $tester_id $port
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALLoadPort
#
#  Description    : This function is called to a port's configuration.
#
#  Usage          : UALLoadPort <tester_id port>
#
#*******************************************************************************
proc UALLoadPort {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaLoadConfig $tester_id $port
        }
        spirent {
            _spirentLoadConfig $tester_id $port
            sleep 10
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALReleasePort
#
#  Description    : This function is called to release a reserved port.
#
#  Usage          : UALReleasePort <tester_id port>
#
#*******************************************************************************
proc UALReleasePort {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaReleasePort  $tester_id $port
        }
        spirent {
            _spirentReleasePort $tester_id $port
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALModifyPortLoad
#
#  Description    : This function is called to modify load of a port.
#
#  Usage          : UALModifyPortLoad <tester_id port load>
#
#*******************************************************************************
proc UALModifyPortLoad {tester_id port load} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
        }
        spirent {
            _spirentModifyPortLoad $tester_id $port $load
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALReportPortRate
#
#  Description    : This function is called to return port rate info.
#
#  Usage          : UALReportPortRate <tester_id port>
#
#*******************************************************************************
proc UALReportPortRate {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
            return [_ixiaReportRatePerPort $tester_id $port]
        }
        spirent {
            return [_spirentReportRatePerPort $tester_id $port]
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALReportPortSigRate
#
#  Description    : This function is called to return port rate info.
#
#  Usage          : UALReportPortSigRate <tester_id port>
#
#*******************************************************************************
proc UALReportPortSigRate {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
            return [_ixiaReportRatePerPort $tester_id $port]
        }
        spirent {
            return [_spirentReportSigRatePerPort $tester_id $port]
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : UALSetTrafficPortStreamNameList
#
#  Description    : This function is called to modify a port's stream definition.
#
#  Usage          : UALSetTrafficPortStreamNameList <tester_id port streamlist>
#
#*******************************************************************************
proc UALSetTrafficPortStreamNameList {tester_id port streamlist} {
    _ntgrSetTrafficPortStreamNameList $tester_id $port $streamlist
}
#*******************************************************************************
#
#  Function Name  : UALTakeOwnership
#
#  Description    : This function is called to take ownership of one IXIA port.
#
#  Usage          : UALTakeOwnership <tester_id port>
#
#*******************************************************************************
proc UALTakeOwnership {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaTakeOwnership  $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALClearOwnership
#
#  Description    : This function is called to clear ownership of one IXIA port.
#
#  Usage          : UALClearOwnership <tester_id port>
#
#*******************************************************************************
proc UALClearOwnership {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaClearOwnership  $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALLoadRipConfig
#
#  Description    : This function is called to load RIP configuration into IXIA.
#
#  Usage          : UALLoadRipConfig <tester_id port>
#
#*******************************************************************************
proc UALLoadRipConfig {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
              _ixiaLoadRipConfig $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : UALStartRIPRouter
#
#  Description    : This function is called to start RIP router.
#
#  Usage          : UALStartRIPRouter <tester_id port>
#
#*******************************************************************************
proc UALStartRIPRouter {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
              _ixiaStartRIPRouter $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : UALStopRIPRouter
#
#  Description    : This function is called to stop RIP router.
#
#  Usage          : UALStopRIPRouter <tester_id port>
#
#*******************************************************************************
proc UALStopRIPRouter {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
              _ixiaStopRIPRouter $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : UALLoadOspfConfig
#
#  Description    : This function is called to load OSPF configuration.
#
#  Usage          : UALLoadOspfConfig <tester_id port>
#
#*******************************************************************************
proc UALLoadOspfConfig {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
              _ixiaLoadOSPFConfig $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : UALStartOSPFRouter
#
#  Description    : This function is called to start OSPF router.
#
#  Usage          : UALStartOSPFRouter <tester_id port>
#
#*******************************************************************************
proc UALStartOSPFRouter {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
              _ixiaStartOSPFRouter $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : UALStopOSPFRouter
#
#  Description    : This function is called to stop OSPF router.
#
#  Usage          : UALStopOSPFRouter <tester_id port>
#
#*******************************************************************************
proc UALStopOSPFRouter {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
              _ixiaStopOSPFRouter $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : UALClearPortCounter
#
#  Description    : This function is called to clear port statistics.
#
#  Usage          : UALClearPortCounter <tester_id port_list>
#
#*******************************************************************************

proc UALClearPortCounter {tester_id port_list} {
  set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
           _ixiaClearPortCounter $port_list
        }
        spirent {
            _spirentClearPortCounter  $port_list
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : UALReportRxFrameCnt
#
#  Description    : This function is called to get RX frame count on traffic or port etc.
#
#  Usage          :  UALReportRxFrameCnt <tester_id cnt_handle>
#
#*******************************************************************************

proc UALReportRxFrameCnt {tester_id cnt_handle} {
  set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaReportRxCount $cnt_handle
        }
        spirent {
            _spirentReportRxCount $cnt_handle
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALSetRxPorts
#
#  Description    : This function is called to set traffic receive ports.
#
#  Usage          :  UALSetRxPorts <tester_id port_lists traffic_handle>
#
#*******************************************************************************

proc UALSetRxPorts {tester_id port_lists traffic_handle} {
  set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
            # To be done
        }
        spirent {
            _spirentSetRxPorts $port_lists $traffic_handle
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALReportTxFrameCnt
#
#  Description    : This function is called to get TX frame count on traffic or port etc.
#
#  Usage          :  UALReportTxFrameCnt <tester_id cnt_handle>
#
#*******************************************************************************

proc UALReportTxFrameCnt {tester_id cnt_handle} {
  set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]
  
    switch $switch_vendor {
        ixia {
            # To be done
            _ixiaReportTxCount $cnt_handle
        }
        spirent {
            _spirentReportTxCount $cnt_handle
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALReportTxSigFrameCnt
#
#  Description    : This function is called to get TX frame count on traffic or port etc.
#
#  Usage          :  UALReportTxSigFrameCnt <tester_id cnt_handle>
#
#*******************************************************************************

proc  UALReportTxSigFrameCnt {tester_id cnt_handle} {
  set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
           _ixiaReportTxSigCount $cnt_handle
        }
        spirent {
            _spirentReportTxSigCount $cnt_handle
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : UALReportRxSigFrameCnt
#
#  Description    : This function is called to get RX frame count on traffic or port etc.
#
#  Usage          :  UALReportRxSigFrameCnt <tester_id cnt_handle>
#
#*******************************************************************************

proc UALReportRxSigFrameCnt {tester_id cnt_handle} {
  set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
           _ixiaReportRxSigCount $cnt_handle
        }
        spirent {
            _spirentReportRxSigCount $cnt_handle
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALReportTxFrameRate
#
#  Description    : This function is called to get TX frame Rate on traffic or port etc.
#
#  Usage          :  UALReportTxFrameRate <tester_id cnt_handle>
#
#*******************************************************************************

proc UALReportTxFrameRate {tester_id cnt_handle} {
  set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaReportTxRate $cnt_handle
        }
        spirent {
            _spirentReportTxRate $cnt_handle
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALReportRxFrameRate
#
#  Description    : This function is called to get RX frame Rate on traffic or port etc.
#
#  Usage          :  UALReportRxFrameRate <Tester_id cnt_handle>
#
#*******************************************************************************

proc UALReportRxFrameRate {tester_id cnt_handle} {
  set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaReportRxRate $cnt_handle
        }
        spirent {
            _spirentReportRxRate $cnt_handle
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetL2L3RateArrayEx
#
#  Description    : This API is called to get rate statistics array for testcenter port. 
#         
#  Usage          : _ntgrGetL2L3CountArray <tester_id> <port> <aRateArray>
#  
#  Revision       : Added by kenddy.Xie
#
#*******************************************************************************
proc _ntgrGetL2L3RateArrayEx {tester_id port aRateArray} {
 set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]
    switch $switch_vendor {
        ixia {
            upvar 1 $aRateArray aTmpArray
            _ixiaGetL2L3RateArrayEx  $tester_id $port aTmpArray
        }
        spirent {
             upvar 1 $aRateArray aTmpArray
            _spirentGetL2L3RateArrayEx  $tester_id $port aTmpArray
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetL2L3RateArrayEx
#
#  Description    : This API is called to get rate statistics array for testcenter port. 
#         
#  Usage          : _ntgrGetL2L3CountArray <tester_id> <port> <aRateArray>
#  
#  Revision       : Added by kenddy.Xie
#
#*******************************************************************************
proc _ntgrGetL2L3CountArrayEx {tester_id port aCountArray} {
 set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]
    switch $switch_vendor {
        ixia {
            upvar 1 $aCountArray aTmpArray
            _ixiaGetL2L3CountArrayEx  $tester_id $port aTmpArray
        }
        spirent {
             upvar 1 $aCountArray aTmpArray
            _spirentGetL2L3CountArrayEx $tester_id $port aTmpArray
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}





#=====================================dhcp functions========================================
#*******************************************************************************
#
#  Function Name  : UALLoadDHCPClientConfig
#
#  Description    : This function is called to simulate dhcp client on one ixia port
#
#  Usage          : UALLoadDHCPClientConfig <tester_id> <port>
#
#*******************************************************************************
proc UALLoadDHCPClientConfig {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaLoadDHCPClientConfig $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALUnloadDHCPClientConfig
#
#  Description    : This function is called to delete dhcp client on one ixia port
#
#  Usage          : UALUnloadDHCPClientConfig <tester_id> <port>
#
#*******************************************************************************
proc UALUnloadDHCPClientConfig {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaUnloadDHCPClientConfig $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALReportDHCPClientAddrCount
#
#  Description    : This function is called to get dhcp client learned address count on one ixia port
#
#  Usage          : UALReportDHCPClientAddrCount <tester_id> <port>
#
#*******************************************************************************
proc UALReportDHCPClientAddrCount {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaReportDHCPClientAddrCount $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALCheckDHCPClientInfo
#
#  Description    : This function is called to check dhcp client info on one ixia port
#
#  Usage          : UALCheckDHCPClientInfo <tester_id> <port> <checkinfo_list>
#
#                 the format of checkinfo_list:  type1 value1 tyep2 value2 ...
#                 usually type and value are hex or hex list, for example:
#                     set checkinfo_list "01" "00" "40" "00 00 00 01"
#                 if checking value of ip, networklength, gateway ip, lease time from dhcp server, please using format as follows:
#                     set checkinfo_list "ipStr" "1.1.1.1" "netLengthStr" "24" "gatewayStr" "1.1.1.254" "leaseStr" "600"
#
#*******************************************************************************
proc UALCheckDHCPClientInfo {tester_id port {checkinfo_list ""}} {
    set switch_vendor [ _ntgrGetTGVendorName $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaCheckDHCPClientInfo $tester_id $port $checkinfo_list
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALCheckDHCPClientInfoNotExist
#
#  Description    : This function is called to check dhcp client info not exist on one ixia port
#
#  Usage          : UALCheckDHCPClientInfoNotExist <tester_id> <port> <checkinfo_list>
#
#                 the format of checkinfo_list:  type1 value1 tyep2 value2 ...
#                 usually type and value are hex or hex list, for example:
#                     set checkinfo_list "01" "00" "40" "00 00 00 01"
#                 if checking value of ip, networklength, gateway ip, lease time from dhcp server, please using format as follows:
#                     set checkinfo_list "ipStr" "1.1.1.1" "netLengthStr" "24" "gatewayStr" "1.1.1.254" "leaseStr" "600"
#
#*******************************************************************************
proc UALCheckDHCPClientInfoNotExist {tester_id port {checkinfo_list ""}} {
    set switch_vendor [ _ntgrGetTGVendorName $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaCheckDHCPClientInfoNotExist $tester_id $port $checkinfo_list
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALReportArpReplyRxSigFrameCnt
#
#  Description    : This function is called to get RX frame count on traffic or port etc.
#
#  Usage          :  UALReportRxSigFrameCnt <tester_id cnt_handle>
#  Add by         Duke 
#*******************************************************************************

proc UALReportArpReplyRxSigFrameCnt {tester_id cnt_handle} {
  set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
           _ixiaReportArpReplyRxSigCount $cnt_handle
        }
        spirent {
            _spirentReportRxSigCount $cnt_handle
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALReportArpReplyRxSigFrameCnt
#
#  Description    : This function is called to get RX frame count on traffic or port etc.
#
#  Usage          :  UALReportArpReplyRxSigFrameCnt <tester_id cnt_handle>
#  Add by         Duke 
#*******************************************************************************

proc UALReportArpRequestRxSigFrameCnt {tester_id cnt_handle} {
  set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
           _ixiaReportArpRequestRxSigCount $cnt_handle
        }
        spirent {
            _spirentReportRxSigCount $cnt_handle
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : UALReportPingReplyRxSigFrameCnt
#
#  Description    : This function is called to get ping recevice package count on traffic or port etc.
#
#  Usage          :  UALReportPingReplyRxSigFrameCnt <tester_id cnt_handle>
#  Add by         jim.xie 
#*******************************************************************************

proc UALReportPingReplyRxSigFrameCnt {tester_id cnt_handle} {
  set switch_vendor [ _ntgrGetTGVendorName  $tester_id ]

    switch $switch_vendor {
        ixia {
           _ixiaReportPingRequestRxSigCount $cnt_handle
        }
        spirent {
            Netgear_log_file "ual-ntgr-traffic.tcl" "The method is not support in spirent."
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : UALReportDHCPv6ClientAddrCount
#
#  Description    : This function is called to get dhcp client learned address count on one ixia port
#
#  Usage          : UALReportDHCPv6ClientAddrCount <tester_id> <port>
#
#  Author         : Jason
#
#*******************************************************************************
proc UALReportDHCPv6ClientAddrCount {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaReportDHCPv6ClientAddrCount $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
} 
#*******************************************************************************
#
#  Function Name  : UALLoadDHCPv6ClientConfig
#
#  Description    : This function is called to simulate dhcp client on one ixia port
#
#  Usage          : UALLoadDHCPv6ClientConfig <tester_id> <port>
#
#  Author         : Jason
#
#*******************************************************************************
proc UALLoadDHCPv6ClientConfig {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaLoadDHCPv6ClientConfig $tester_id $port
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
} 
#*******************************************************************************
#
#  Function Name  : UALUnloadDHCPClientConfig
#
#  Description    : This function is called to delete dhcp client on one ixia port
#
#  Usage          : UALUnloadDHCPClientConfig <tester_id> <port>
#
#  Author         : Jason
#
#*******************************************************************************
proc UALUnloadDHCPv6ClientConfig {tester_id port} {
    set switch_vendor [ _ntgrGetTGVendorName $tester_id ]

    switch $switch_vendor {
        ixia {
            _ixiaUnloadDHCPv6ClientConfig $tester_id $port 
        }
        spirent {
            # To be done
        }
        default {
            Netgear_log_file "ual-ntgr-traffic.tcl" "Tester not defined"
        }
    }
}