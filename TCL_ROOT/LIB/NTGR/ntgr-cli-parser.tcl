#
# *API: Private
#       _ntgrParseCellTableHeader
#       _ntgrParseCellTableData
#       _ntgrBuildCellTable
#

#
# *API: Public
#       ntgrCLIParseCellTable
#       ntgrCLICellTableCellData
#       ntgrCLICellTableColNumberFromText
#       ntgrCLICellTableRowNumberFromText
#       ntgrCLIParseParam
#       ntgrCLIExecute
#       ntgrCLIQuery
#       ntgrCLIExecStatus
#

#
#   _ntgrParseCellTableHeader
#       [i] lstText
#       [o] _o_lstHeader
#       [i] intStart
#       [o] _o_intNext
#   RETURN
#       0/1
#
proc _ntgrParseCellTableHeader {lstText _o_lstHeader intStart _o_intNext} {
    upvar 1 $_o_lstHeader lstH
    upvar 1 $_o_intNext intN
    set lstH {}
    set intN -1

    set retval 0
    set m -1
    set n -1

    for {set i $intStart} {$i < [llength $lstText]} {incr i} {
        if {[regexp -- "^-+( +-+)*$" [lindex $lstText $i]]} {
            set n $i
            set intN [expr $i + 1]
            break
        }
    }

    for {set i [expr $i - 1]} {$i >= $intStart} {incr i -1} {
        if {"" == [lindex $lstText $i]} {
            set m [expr $i + 1]
            break
        }
    }

    if {$m >= 0 && $n >= 0 && $m != $n} {
        set retval 1
        for {set i $m} {$i <= $n} {incr i} {
            lappend lstH [lindex $lstText $i]
        }
    }

    return $retval
}

#
#   _ntgrParseCellTableData
#       [i] lstText
#       [i] lstHeader
#       [o] _o_lstData
#       [i] intStart
#       [o] _o_intNext
#   RETURN
#       0/1
#
proc _ntgrParseCellTableData {lstText lstHeader _o_lstData intStart _o_intNext} {
    upvar 1 $_o_lstData lstD
    upvar 1 $_o_intNext intN
    set lstD {}
    set intN -1

    set retval 0

    set i $intStart
    while {$i < [llength $lstText]} {
      # skip EMPTY line
        if {"" == [lindex $lstText $i]} {
            incr i
            continue
        }
        
       # skip --More-- line
        if {[regexp -- ".*More.*" [lindex $lstText $i]]} {
            incr i
            continue
        }
        
          # skip --Detach-- line
        if {[regexp -- ".*Detach.*" [lindex $lstText $i]]} {
            incr i
            continue
        }
    
        # end of INPUT
        if {[regexp -- "#$" [lindex $lstText $i]]} {
            incr i
            set retval 1
            break
        }
         # skip this TABLE Header
        if {[lindex $lstHeader 0] == [lindex $lstText $i]} {
            incr i [llength $lstHeader]
            continue
        }
            
        if {"" != [lindex $lstText $i]} {
            lappend lstD [lindex $lstText $i]
            incr i
            continue
        }
    }

    return $retval
}

#
#   _ntgrBuildCellTable
#       [i] lstHeader
#       [i] lstData
#   RETURN
#       keylset
#
proc _ntgrBuildCellTable {lstHeader lstData} {
    set rows [ llength $lstData ]
    set cols [ regexp -all -- "-+" [lindex $lstHeader [expr [llength $lstHeader] - 1]] ]
    set headerIndex {{-1 -1}}
    set headerName  {"#0"}
    set start 0
    for {set i 0} {$i < $cols} {incr i} {
        regexp -start $start -indices -- "-+" [lindex $lstHeader [expr [llength $lstHeader] - 1]] hIndex
        lappend headerIndex $hIndex
        set start [expr [lindex $hIndex 1] + 1]

        set hName ""
        for {set j 0} {$j < [expr [llength $lstHeader] - 1]} {incr j} {
            set hNameP [string range [lindex $lstHeader $j] [lindex $hIndex 0] [lindex $hIndex 1]]
            set hNameP [string trim $hNameP]
            set hName "$hName $hNameP"
        }
        set hName [string trim $hName]
        lappend headerName $hName
    }

    set cells {}
    lappend cells $headerName
    for {set row 1} {$row <= $rows} {incr row} {
        set rowData [lindex $lstData [expr $row - 1]]
        set lstRow {}
        lappend lstRow "#$row"
        for {set column 1} {$column <= $cols} {incr column} {
            set hIndex [lindex $headerIndex $column]
            set cellData [string range $rowData [lindex $hIndex 0] [lindex $hIndex 1]]
            set cellData [string trim $cellData]
            lappend lstRow $cellData
        }
        lappend cells $lstRow
    }

    set raw {}
    for {set i 0} {$i < [llength $lstHeader]} {incr i} {
        lappend raw [lindex $lstHeader $i]
    }
    for {set i 0} {$i < [llength $lstData]} {incr i} {
        lappend raw [lindex $lstData $i]
    }

    keylset retval \
        rows    $rows \
        cols    $cols \
        cells   $cells \
        raw     $raw

    return $retval
}

#
#   ntgrCLIParseCellTable
#       [i] strCLIOutput
#       [o] _o_table
#       [i] index
#   RETURN
#       0/1
#
proc ntgrCLIParseCellTable {strCLIOutput _o_table {index 1}} {
    upvar 1 $_o_table table

    set retval 0

    set lstOutput [split $strCLIOutput "\n"]
    set intStart 0
    set intNext -1
    for {set i 1} {$i <= $index} {incr i} {
        if {![_ntgrParseCellTableHeader $lstOutput lstHeader $intStart intNext]} {break}
        set intStart $intNext
        set intNext -1

        if {![_ntgrParseCellTableData $lstOutput $lstHeader lstData $intStart intNext]} {break}
        set intStart $intNext
        set intNext -1

        if {$i == $index} {
            set retval 1
            set table [_ntgrBuildCellTable $lstHeader $lstData]
            break
        }
    }

    return $retval
}

proc ntgrCLICellTableCellData {table rowEx columnEx} {
    set rows [keylget table rows]
    set cols [keylget table cols]
    set cells [keylget table cells]
    set header [lindex $cells 0]

    if {[string is integer -strict $rowEx]} {
        set row $rowEx
    } elseif {2 == [llength $rowEx]} {
        set colID [ntgrCLICellTableColNumberFromText $table [lindex $rowEx 0]]
        set row [ntgrCLICellTableRowNumberFromText $table [lindex $rowEx 1] $colID]
    }

    if {[string is integer -strict $columnEx]} {
        set column $columnEx
    } else {
        set column [ntgrCLICellTableColNumberFromText $table $columnEx]
    }

    set lstRow [lindex $cells $row]
    set cellData [lindex $lstRow $column]

    return $cellData
}

proc ntgrCLICellTableColNumberFromText {table strText} {
    set rows [keylget table rows]
    set cols [keylget table cols]
    set cells [keylget table cells]
    set header [lindex $cells 0]

    set column -1
    for {set i 1} {$i <= $cols} {incr i} {
        set cellData [lindex $header $i]
        if {[regexp -nocase -- "$strText" $cellData]} {
            set column $i
            break
        }
    }

    return $column
}

proc ntgrCLICellTableRowNumberFromText {table strText column} {
    set rows [keylget table rows]
    set cols [keylget table cols]
    set cells [keylget table cells]
    set header [lindex $cells 0]

    set row -1
    for {set i 1} {$i <= $rows} {incr i} {
        set lstRow [lindex $cells $i]
        set cellData [lindex $lstRow $column]
        if {$strText == $cellData} {
            set row $i
            break
        }
    }

    return $row
}

proc ntgrCLIParseParam {strCLIOutput strParamName _o_lstParamValue} {
    upvar 1 $_o_lstParamValue lstParamValue

    set retval 0
    set m -1
    set n -1
    set lstParamValue {}

    set lstOutput [split $strCLIOutput "\n"]

    set intStart 0
    set intNext -1
    for {set i $intStart} {$i < [llength $lstOutput]} {incr i} {
        if {[regexp -nocase -- "^$strParamName" [lindex $lstOutput $i]]} {
            set m $i
            set n $i
            set intNext [expr $i + 1]
            break
        }
    }

    set intStart $intNext
    set intNext -1
    for {set i $intStart} {$i < [llength $lstOutput]} {incr i} {
        if {[regexp -- {^\.+} [lindex $lstOutput $i]]} {
            set n $i
            continue
        } else {
            break
        }
    }

    if {$m >= 0 && $n >= 0} {
        set retval 1
        for {set i $m} {$i <= $n} {incr i} {
            lappend lstParamValue [lindex $lstOutput $i]
        }
    }

    Netgear_log_file "ntgrCLIParseParam" "[ntgrTclDumpList $lstParamValue]"

    return $retval
}

proc ntgrCLIExecute {strDevice strCommand {keylsetOptions {}}} {
    set options $keylsetOptions
  
    # "{nomore 1}"
    # "{noreboot 1}"
    # "{nosave 1}"
    # "{noyes 1}"
    # ...

    set cnn_id [_get_switch_handle $strDevice]
    set strOutput ""

    exp_send -i $cnn_id "$strCommand"
    exp_send -i $cnn_id "\r"
    expect -i $cnn_id "$strCommand" {}

    expect {
        -i $cnn_id -ex ") #" {
            set strOutput $strOutput$expect_out(buffer)
        }
        -i $cnn_id -ex ")#" {
            set strOutput $strOutput$expect_out(buffer)
        }
        -i $cnn_id -re "More" {
            set strOutput $strOutput$expect_out(buffer)
            if {[keylget options nomore {}]} {
                exp_send -i $cnn_id "q"
            } else {
                exp_send -i $cnn_id " "
            }
            exp_continue
        }
        
        -i $cnn_id -ex "save? (y/n)" {
            set strOutput $strOutput$expect_out(buffer)
            if {[keylget options nosave {}]} {
                exp_send -i $cnn_id "n"
            } else {
                exp_send -i $cnn_id "y"
            }
            exp_sleep 15
            exp_continue
        }
        -i $cnn_id -ex "save them now? (y/n)" {
            set strOutput $strOutput$expect_out(buffer)
            if {[keylget options nosave {}]} {
                exp_send -i $cnn_id "n"
            } else {
                exp_send -i $cnn_id "y"
            }
            exp_sleep 15
            exp_continue
        }
        
        -i $cnn_id -ex "reset the system? (y/n)" {
            set strOutput $strOutput$expect_out(buffer)
            if {[keylget options noreboot {}]} {
                exp_send -i $cnn_id "n"
            } else {
                exp_send -i $cnn_id "y"
            }
            exp_continue
        }
        
        -i $cnn_id -ex "reload the stack? (y/n)" {
            set strOutput $strOutput$expect_out(buffer)
            if {[keylget options noreboot {}]} {
                exp_send -i $cnn_id "n"
            } else {
                exp_send -i $cnn_id "y"
            }
            exp_continue
        }
        
        -i $cnn_id -ex "? (y/n)" {
            set strOutput $strOutput$expect_out(buffer)
            if {[keylget options noyes {}]} {
                exp_send -i $cnn_id "n"
            } else {
                exp_send -i $cnn_id "y"
            }
            exp_sleep 15
            exp_continue
        }
        timeout {}
    }

    #if {![ntgrCLIExecStatus $strOutput]} {
    #}

    return $strOutput
}

proc ntgrCLIQuery {strDevice {strCommand ""}} {
    set cnn_id [_get_switch_handle $strDevice]
    set strOutput ""

    exp_send -i $cnn_id "$strCommand?"
    expect -i $cnn_id -ex "$strCommand?" {}

    expect {
        -i $cnn_id -ex ") #$strCommand" {
            set strOutput $strOutput$expect_out(buffer)
        }
        -i $cnn_id -ex ")#$strCommand" {
            set strOutput $strOutput$expect_out(buffer)
        }
        -i $cnn_id -re "More" {
            set strOutput $strOutput$expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_continue
        }
        timeout {}
    }

    if {0 != [string length "$strCommand"]} {
        exp_send -i $cnn_id [ string repeat "\b" [string length $strCommand] ]
    }

    return $strOutput
}

proc ntgrCLIExecStatus {strCLIOutput} {
    set retval 1

    set lstErrors {}
    lappend lstErrors "command not found"
    lappend lstErrors "incomplete command"
    lappend lstErrors "invalid input detected"

    for {set i 0} {$i < [llength $lstErrors]} {incr i} {
        if {[regexp -nocase -- [lindex $lstErrors $i] $strCLIOutput]} {
            set retval 0
            break
        }
    }

    return $retval
}
