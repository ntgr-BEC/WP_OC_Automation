
#
# synchronize data from config_dut.xml
#

proc _ntgrDumpNtgrSWList {} {
global DUT
global SW1
global SW2
global SW3
global DUT2
global _ntgr_swList
    
	# add by jim.xie for MLAG
    if { ! [ info exists DUT2 ] } {
	    set DUT2 $DUT
	}
    # dump
    foreach SWx "$DUT $SW1 $SW2 $SW3 $DUT2" {
        if {![info exists _ntgr_swList($SWx)]} {continue}
        set strOutput "\n\
        _ntgr_swList($SWx)\n\
        SWITCH_NAME             $SWx\n\
        SWITCH_VENDOR           [keylget _ntgr_swList($SWx) SWITCH_VENDOR]\n\
        SWITCH_MODEL            [keylget _ntgr_swList($SWx) SWITCH_MODEL]\n\
        SWITCH_IP_ADDR          [keylget _ntgr_swList($SWx) SWITCH_IP_ADDR]\n\
        SWITCH_IMAGE_VERSION    5.0.1.9\n\
        SWITCH_MAX_PORTS        52\n\
        SWITCH_TYPE             stack\n\
        SWITCH_HANDLE           0\n\
        SWITCH_PORT             23\n\
        SW_PROMPT               \"GSM7352S_C1\"\n\
        SW_MNGR_IP_ADDR         \"192.168.1.1\"\n\
        SW_MNGR_IP_MASK         \"255.255.255.0\"\n\
        \n"
        Netgear_log_file "lib-testbed-setup.tcl: " $strOutput
    }
}

proc _ntgrDumpNtgrTGList {} {
global _ntgr_tgList

    # dump
    foreach TGx "S1 S2 S3 S4 I1 I2 I3 I4" {
        if {![info exists _ntgr_tgList($TGx)]} {continue}
        set strOutput "\n\
        _ntgr_tgList($TGx)\n\
        CHASSIS_NAME                [keylget _ntgr_tgList($TGx) CHASSIS_NAME]\n\
        CHASSIS_VENDOR              [keylget _ntgr_tgList($TGx) CHASSIS_VENDOR]\n\
        CHASSIS_MODEL               [keylget _ntgr_tgList($TGx) CHASSIS_MODEL]\n\
        CHASSIS_IP_ADDR             [keylget _ntgr_tgList($TGx) CHASSIS_IP_ADDR]\n\
        CHASSIS_HANDLE              [keylget _ntgr_tgList($TGx) CHASSIS_HANDLE]\n\
        CHASSIS_PROJECT_HANDLE      [keylget _ntgr_tgList($TGx) CHASSIS_PROJECT_HANDLE]\n\
        TRAFFIC_PORT_HANDLE         [keylget _ntgr_tgList($TGx) TRAFFIC_PORT_HANDLE]\n\
        \n"
        Netgear_log_file "lib-testbed-setup.tcl: " $strOutput
    }
}

## Function:        _ntgrDumpNtgrPGList
## Description:     dump for PSL-3000
## Return:          None
## Reversion:       Added by Andy.xie 2012-12-14

proc _ntgrDumpNtgrPGList {} {

global P1
global P2
global _ntgr_pgList

    # dump
    foreach PGx "P1 P2" {
        if {![info exists _ntgr_pgList($PGx)]} {continue}
        set strOutput "\n\
        _ntgr_psaList($PGx)\n\
        CHASSIS_NAME                [keylget _ntgr_pgList($PGx) CHASSIS_NAME]\n\
        CHASSIS_VENDOR              [keylget _ntgr_pgList($PGx) CHASSIS_VENDOR]\n\
        CHASSIS_MODEL               [keylget _ntgr_pgList($PGx) CHASSIS_MODEL]\n\
        CHASSIS_IP_ADDR             [keylget _ntgr_pgList($PGx) CHASSIS_IP_ADDR]\n\
        CHASSIS_HANDLE              [keylget _ntgr_pgList($PGx) CHASSIS_HANDLE]\n\
        \n"
        Netgear_log_file "lib-testbed-setup.tcl: " $strOutput
    }
}

proc _ntgrGetXmlConfigItem {xmlConfig strItemName} {
    set xmlConfigItem [$xmlConfig getElementsByTagName $strItemName]
   
   # jason add for check TagName (e.g :TelnetPort) exist begin
   if { [llength $xmlConfigItem] != 0} {
      return [$xmlConfigItem text]
   } else { 
      if {[string compare -nocase $strItemName "TelnetPort"] == 0 } { 
          return "23"
       } else { 
         Netgear_log_file "lib-testbed-setup.tcl" "Error: $strItemName is exist in config_dut.xml"
        }
    } 
   # jason add for check TagName (e.g :TelnetPort) exist end 
}

proc Netgear_SyncFromConfigDUTXml {} {
global NTGR_HOME_PATH
global DUT
global SW1
global SW2
global SW3
global DUT2
global TG
global P1
global P2
global PG
global _ntgr_swList
global _ntgr_tgList
global _ntgr_pgList

    if {![info exists DUT] || ![info exists SW1] || ![info exists SW2] || ![info exists SW3] || ![info exists TG]} {
        return
    }
	
	# add by jim.xie for MLAG
    if { ! [ info exists DUT2 ] } {
	    set DUT2 $DUT
	}
    # load config_dut.xml
    set fdConfigDUTXml [open "${NTGR_HOME_PATH}../../config/config_dut.xml"]
    set strXML [read $fdConfigDUTXml]
    close $fdConfigDUTXml

    # parse
    set xmlDoc  [dom parse $strXML]
    set xmlRoot [$xmlDoc documentElement]
    set xmlSWList [$xmlRoot getElementsByTagName DUT_List]
    set xmlTGList [$xmlRoot getElementsByTagName TG_List]
    set xmlPGList [$xmlRoot getElementsByTagName PG_List]
    
    # delete _ntgr_swList()
    foreach SWx "$DUT $SW1 $SW2 $SW3 $DUT2" {
        if {[info exists _ntgr_swList($SWx)]} {unset _ntgr_swList($SWx)}
    }

    # sync
    foreach SWx "$DUT $SW1 $SW2 $SW3 $DUT2" {
        set xmlSWx [$xmlSWList getElementsByTagName $SWx]
        if {[llength $xmlSWx] != 1} {continue}
        keylset _ntgr_swList($SWx)                                                  \
            SWITCH_NAME                 $SWx                                        \
            SWITCH_VENDOR               [_ntgrGetXmlConfigItem $xmlSWx Vendor]      \
            SWITCH_MODEL                [_ntgrGetXmlConfigItem $xmlSWx Model]       \
            SWITCH_IP_ADDR              [_ntgrGetXmlConfigItem $xmlSWx Ip_Address]  \
            SWITCH_IMAGE_VERSION        5.0.1.9                                     \
            SWITCH_MAX_PORTS            52                                          \
            SWITCH_TYPE                 stack                                       \
            SWITCH_HANDLE               0                                           \
            SWITCH_PORT                 [_ntgrGetXmlConfigItem $xmlSWx TelnetPort]  \
            SW_PROMPT                   "GSM7352S_C1"                               \
            SW_MNGR_IP_ADDR             "192.168.1.1"                               \
            SW_MNGR_IP_MASK             "255.255.255.0"
    }

    # delete _ntgr_tgList()
    foreach TGx "S1 S2 S3 S4 I1 I2 I3 I4" {
        if {[info exists _ntgr_tgList($TGx)]} {unset _ntgr_tgList($TGx)}
    }

    # sync
    foreach TGx "S1 S2 S3 S4 I1 I2 I3 I4" {
        set xmlTGx [$xmlTGList getElementsByTagName $TGx]
        if {[llength $xmlTGx] != 1} {continue}
        keylset _ntgr_tgList($TGx)                                                      \
            CHASSIS_NAME                $TGx                                            \
            CHASSIS_VENDOR              [_ntgrGetXmlConfigItem $xmlTGx CHASSIS_VENDOR]  \
            CHASSIS_MODEL               [_ntgrGetXmlConfigItem $xmlTGx CHASSIS_MODEL]   \
            CHASSIS_IP_ADDR             [_ntgrGetXmlConfigItem $xmlTGx CHASSIS_IP_ADDR] \
            CHASSIS_HANDLE              -1                                              \
            CHASSIS_PROJECT_HANDLE      -1                                              \
            TRAFFIC_PORT_HANDLE         {}
    }
    
    ## Added by Andy.xie for PoE test.
    # delete _ntgr_pgList()
    foreach PGx "P1 P2" {
        if {[info exists _ntgr_pgList($PGx)]} {unset _ntgr_pgList($PGx)}
    }

    # sync
    foreach PGx "P1 P2" {
        set xmlPGx [$xmlPGList getElementsByTagName $PGx]
        if {[llength $xmlPGx] != 1} {continue}
        keylset _ntgr_pgList($PGx)                                                      \
            CHASSIS_NAME                $PGx                                            \
            CHASSIS_VENDOR              [_ntgrGetXmlConfigItem $xmlPGx CHASSIS_VENDOR]  \
            CHASSIS_MODEL               [_ntgrGetXmlConfigItem $xmlPGx CHASSIS_MODEL]   \
            CHASSIS_IP_ADDR             [_ntgrGetXmlConfigItem $xmlPGx CHASSIS_IP_ADDR] \
            CHASSIS_HANDLE              -1
    }
    
    # dump
    #_ntgrDumpNtgrSWList
    #_ntgrDumpNtgrTGList
}
