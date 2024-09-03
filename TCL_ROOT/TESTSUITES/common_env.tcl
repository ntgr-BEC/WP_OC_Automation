#!/bin/sh

package require Expect
package require Tcl
package require irc
package require Tclx
package require math
package require SpirentTestCenter
package require IxTclHal

###########################################################################
#
#  File Name		: common_env.tcl
#
#  Description       	:
#         This TCL sources the environment variables and common files.
#
#  Revision History 	:
#         Date        Programmer        Description
#        ----------------------------------------------------------------------
#        02-May-06    Rajeswari V       Created
#
#
#
#
################################################################################

#************** Start of Environment Variable Declaration Section **************
# set different test configuration files for different test environment
set NTGR_TEST_ENVIRONMENT "CTL"; # for China Test Lab
set NTGR_TEST_FUNC ""
# Variables to define the Path for lib, scripts and suites
set NTGR_HOME_PATH     		"./"
set NTGR_LIB_PATH      		"../LIB"
set NTGR_TEST_SCRIPT_PATH   	"../TESTSCRIPTS"
set NTGR_TEST_SUITE_PATH   	"../TESTSUITE"
set NTGR_CONFIG_PATH      	"../CONFIG"
set NTGR_LOG_PATH      		"../LOG"
set NTGR_FUNC_CFG      "../Functionality/Config"
set NTGR_FUNC_TC      "../Functionality/Testcases"


# Variables used for Logging functionality
set NTGR_LOCAL_CONSOLE	1
set NTGR_LOCAL_FILE	2
set NTGR_REMOTE_FILE	4
set NTGR_TESTDIR_FILE	8

set NTGR_LOG_ERROR	16
set NTGR_LOG_TERSE	32
set NTGR_LOG_INFORM     64
set NTGR_LOG_DEBUG     128

set NTGR_DUT_LIST     ""

set NTGR_LOG_FILE_NAME 	"temp.log"
set NTGR_TRAFFIC_LOG_FILE_NAME 	"traffictemp.log"

# Variables Used to print TestScript/Test Suite result
set NTGR_TEST_RESULT 		FALSE
set NTGR_SUITE_RESULT		0
set NTGR_TEST_LIST 		{}

#Variables used to hide/display the execution
exp_log_user 0
set ::exp::winnt_debug 1

#*************  End of Environment Variable Declaration Section *****************

#*************  Start of library File Include Section ***************************

# Include all Netgear libraries
# modify this file if you are adding new lib directories
source $NTGR_LIB_PATH/Netgear-lib.tcl

# Include all Topolgy Information
source $NTGR_CONFIG_PATH/$NTGR_TEST_ENVIRONMENT/Netgear_common.cfg

#*************  End of library File Include Section *****************************