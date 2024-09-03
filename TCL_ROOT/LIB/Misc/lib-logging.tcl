#!/bin/sh
################################################################################   
#
#  File Name		: lib-logging.tcl
#
#  Description       	:
#         This TCL contains functions to retrieve switch information
#
#
################################################################################

set cmdShowRun "show running-config"

global NTGR_DUT_LIST

foreach sw [string trim $NTGR_DUT_LIST] {
  set showRun ""
#  CALCheckExpect $sw $cmdShowRun showRun
  ntgrCLILogin $sw
  ntgrCLIExecute $sw  $cmdShowRun
  ntgrCLILogout $sw
}