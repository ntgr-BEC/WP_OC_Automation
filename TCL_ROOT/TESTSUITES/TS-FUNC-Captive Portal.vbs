'#  File Name   :     TS-GUI-HELP-CHECK.vbs
'#
'#  Description :     Testsuite to check GUI help pages.
'#
'#  Date              Programmer                          Note 
'#  2008-3-27         Scott                               Create
'#


'# Global variables, DO NOT MODIFY THEIR NAMES
'# Define how many cycles the time wheel will run
TOTAL_CYCLE_AMOUNT   = 1

'# Define cycle interval, in seconds
CYCLE_INTERVAL       = 0

'# Define not to run OK-tests again if there're more than 1 cycle
CYCLE_NOT_RUN_OK = True

'# Define test interval, in seconds
TEST_INTERVAL        = 0

'# Define a 2-dimension array to store testcases and initialize it with default value
ReDim TESTCASE (6, 0)
For idx = 1 To UBound(TESTCASE,2)
  TESTCASE(2,idx)=1:TESTCASE(3,idx)=0:TESTCASE(4,idx)=1:TESTCASE(5,idx)=0:TESTCASE(6,idx)=0
Next

'# Define the absolute root path of testcases
TC_ROOT_PATH = "\Functionality\Captive Portal\"


'# Here we use a 2-dimension array to store testcases in this time wheel.
'# We name the array "TESTCASE", PLEASE DO NOT MODIFY ITS NAME.
'#
'# We define it as   :         Dim TESTCASE (row, col) 
'#
'# row               : each row can be used to store a testcase in time wheel
'# TESTCASE (0,row)  : the full path of a test
'# TESTCASE (1,row)  : the name of testcase
'# TESTCASE (2,row)  : the cycle from which to begin run the testcase, 
'#                     never start if < 1 or larger than stop cycle, 1 by default
'# TESTCASE (3,row)  : the cycle from which stop run the testcase, 
'#                     never stop if < 1 till end, 0 by default
'# TESTCASE (4,row)  : the cycle interval for run the testcase between start and stop,
'#                     never repeat if < 1, 1 by default
'# TESTCASE (5,row)  : Reserved, read only, indicate how many times the testcase was run in the time wheel
'# TESTCASE (6,row)  : Reserved, read only, indicate how many OK times the testcase was in the time wheel



'# Here we use this var to indicate we're going to run all tests under the given root path.
'# And we don't need to list those tests in TESTCASE variable, script will do it.
'# If this variable is set to false, you MUST set TESTCASE manually.
TC_RUN_ALL_ROOTPATH = True





'----------------------- END OF CONFIGURATION DEFINE -----------------------------



'!!!!!!!!!!!!!!! DO NOT MODIFY !!!!!!!!!!!!!!!!!! DO NOT MODIFY !!!!!!!!!!!!!!!!!!!
'# Load the VBScript file(lib-testsuite.vbs), it should be kept in the same path
Set fso = CreateObject("Scripting.FileSystemObject")
If fso.FileExists("Lib-TimeWheel-Exec.vbs") Then
	Set ts = fso.OpenTextFile("Lib-TimeWheel-Exec.vbs", 1, False, -1) 
	script = ts.ReadAll 
	ExecuteGlobal script 
	ts.close
End If

'# Call this function to begin the time wheel
RunTimeWheel
'!!!!!!!!!!!!!!! DO NOT MODIFY !!!!!!!!!!!!!!!!!! DO NOT MODIFY !!!!!!!!!!!!!!!!!!!