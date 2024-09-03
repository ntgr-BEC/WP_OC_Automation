#
# Tcl
#
proc ntgrTclDumpList {lstAny} {
    set strOutput "\n"
    for {set i 0} {$i < [llength $lstAny]} {incr i} {
        set strOutput "$strOutput\n[lindex $lstAny $i]"
    }

    return $strOutput
}

proc ntgrTclDumpCellTable {table} {
    set raw [keylget table raw]
    return [ntgrTclDumpList $raw]
}

#
# TESTCASE
#
proc TESTCASE_InitResult {{bResult 0}} {
global TESTCASE_bResult
global TESTCASE_CheckPoint_ID

    set TESTCASE_bResult $bResult
    set TESTCASE_CheckPoint_ID 0
}

proc TESTCASE_UpdateResult {bResult {strMessage ""}} {
global TESTCASE_bResult
global TESTCASE_CheckPoint_ID
global NTGR_LOG_TERSE

    if {$bResult} {
        set strTag "PASS"
    } else {
        set strTag "FAIL"
    }

    incr TESTCASE_CheckPoint_ID
    set strID [format "%02d" $TESTCASE_CheckPoint_ID]
    if {$TESTCASE_CheckPoint_ID <= 1} {
        set TESTCASE_bResult $bResult
    } else {
        set TESTCASE_bResult [expr $TESTCASE_bResult && $bResult]
    }

    NtgrDumpLog $NTGR_LOG_TERSE "TESTCASE_UpdateResult: ($strID:$strTag)" "$strMessage"
}

proc TESTCASE_GetResult {} {
global TESTCASE_bResult

    return $TESTCASE_bResult
}

proc TESTCASE_ReportResult {} {
    uplevel 1 {
        if {[TESTCASE_GetResult]} {
            set bFlag 1
            NtgrDumpLog $NTGR_LOG_TERSE "" "$NTGR_OK_MSG"
        } else {
            set bFlag 0
            NtgrDumpLog $NTGR_LOG_TERSE "" "$NTGR_NG_MSG"
        }
    }
}

proc TESTCASE_Log {{strMessage ""}} {
global NTGR_LOG_TERSE

    NtgrDumpLog $NTGR_LOG_TERSE "TESTCASE_Log:" "$strMessage"
}

proc TESTCASE_Abort {{strMessage ""}} {
    if {"" != $strMessage} {
        TESTCASE_Log "$strMessage"
    }
    UnExpectedError
}

#
# Traffic Stream
#
proc TSTREAM_SetSrcMAC {_io_tstream mac} {
    upvar 1 $_io_tstream tstream

    set TRAFFIC_L2_STREAM_GEN [keylget tstream TRAFFIC_L2_STREAM_GEN]
    keylset TRAFFIC_L2_STREAM_GEN \
        SRC_MAC_ADDR $mac
    keylset tstream \
        TRAFFIC_L2_STREAM_GEN $TRAFFIC_L2_STREAM_GEN
}

proc TSTREAM_SetDstMAC {_io_tstream mac} {
    upvar 1 $_io_tstream tstream

    set TRAFFIC_L2_STREAM_GEN [keylget tstream TRAFFIC_L2_STREAM_GEN]
    keylset TRAFFIC_L2_STREAM_GEN \
        DST_MAC_ADDR $mac
    keylset tstream \
        TRAFFIC_L2_STREAM_GEN $TRAFFIC_L2_STREAM_GEN
}

proc TSTREAM_SetSrcMACCount {_io_tstream count} {
    upvar 1 $_io_tstream tstream

    set TRAFFIC_L2_STREAM_GEN [keylget tstream TRAFFIC_L2_STREAM_GEN]
    keylset TRAFFIC_L2_STREAM_GEN \
        SRC_MAC_ADDR_CNT $count
    keylset tstream \
        TRAFFIC_L2_STREAM_GEN $TRAFFIC_L2_STREAM_GEN
}

proc TSTREAM_SetDstMACCount {_io_tstream count} {
    upvar 1 $_io_tstream tstream

    set TRAFFIC_L2_STREAM_GEN [keylget tstream TRAFFIC_L2_STREAM_GEN]
    keylset TRAFFIC_L2_STREAM_GEN \
        DST_MAC_ADDR_CNT $count
    keylset tstream \
        TRAFFIC_L2_STREAM_GEN $TRAFFIC_L2_STREAM_GEN
}

