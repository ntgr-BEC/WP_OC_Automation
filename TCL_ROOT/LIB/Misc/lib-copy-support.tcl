#!/bin/sh
################################################################################   
#
#  File Name			: lib-copy-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for copy.
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        13-Jan-11     Tony              Created
#
#
#
#
################################################################################
#*******************************************************************************
#
#  Function Name	: _getCopyFileType
#
#  Description    : This function is used to get copy's filetype
#         
#  Usage          : _getCopyFileType <switch>
# 
#*******************************************************************************
proc _getCopyFileType {switch} {
	global ntgr_CopyList
	return [keylget ntgr_CopyList($switch) COPY_FILE_TYPE]
}

#*******************************************************************************
#
#  Function Name	: _getCopyTransferMode
#
#  Description    : This function is used to get copy's transfermode
#         
#  Usage          : _getCopyTransferMode <switch>
# 
#*******************************************************************************
proc _getCopyTransferMode {switch} {
	global ntgr_CopyList
	return [keylget ntgr_CopyList($switch) COPY_TRANSFER_MODE]
}

#*******************************************************************************
#
#  Function Name	: _getCopyServerIP
#
#  Description    : This function is used to get copy's serverip
#         
#  Usage          : _getCopyServerIP <switch>
# 
#*******************************************************************************
proc _getCopyServerIP {switch} {
	global ntgr_CopyList
	return [keylget ntgr_CopyList($switch) COPY_SERVER_IP]
}

#*******************************************************************************
#
#  Function Name	: _getCopyRemoteFileName
#
#  Description    : This function is used to get copy's remote filename
#         
#  Usage          : _getCopyRemoteFileName <switch>
# 
#*******************************************************************************
proc _getCopyRemoteFileName {switch} {
	global ntgr_CopyList
	return [keylget ntgr_CopyList($switch) COPY_REMOTE_FILENAME]
}

#*******************************************************************************
#
#  Function Name	: _getCopySrcFileName
#
#  Description    : This function is used to get copy's source filename
#         
#  Usage          : _getCopySrcFileName <switch>
# 
#*******************************************************************************
proc _getCopySrcFileName {switch} {
	global ntgr_CopyList
	return [keylget ntgr_CopyList($switch) COPY_SRC_FILENAME]
}

#*******************************************************************************
#
#  Function Name	: _getCopyUserName
#
#  Description    : This function is used to get copy's user name
#         
#  Usage          : _getCopyUserName <switch>
# 
#*******************************************************************************
proc _getCopyUserName {switch} {
	global ntgr_CopyList
	return [keylget ntgr_CopyList($switch) COPY_USERNAME]
}

#*******************************************************************************
#
#  Function Name	: _getCopyPassword
#
#  Description    : This function is used to get copy's user password
#         
#  Usage          : _getCopyPassword <switch>
# 
#*******************************************************************************
proc _getCopyPassword {switch} {
	global ntgr_CopyList
	return [keylget ntgr_CopyList($switch) COPY_PASSWORD]
}

