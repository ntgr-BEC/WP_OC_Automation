# =================================================================
# PROC ID:     psa1200_auto_execute_file for Tcl Shell Integration
# AUTHOR:      P Johnson
# SCRIPT TYPE: Shell Loader
# DESCRIPTION: Source PowerShell API to Tcl Initialization Script
# ================================================================= 

# VERSION INFORMATION *************************************
global psaVersionTclRc

  set majorVer    3
  set minorVer    5.01
  set lastUpdate  01-08-11

  set psaVersionTclRc "$majorVer.$minorVer $lastUpdate"
# *********************************************************

# Define Globals
#global psaVersionTclRc
global psaPath
global psaHomePath
global psaAppType
global psaConnectPause
global auto_path

# Allow initialization below level 0
if { [info exists tcl_platform] < 1 } {
  global tcl_platform
}

# Initialize Globals
set psaAppType "powershell_tcl"
set psa_shell_prog "tcl"

# Specify Maximum Number of PowerShell Sessions that May Open
set psaMaxShells 2

# Initial Connection Control
#   Set psaConnectPause to 0 to bypass initial connection address prompt and automatically connect
#     to most recently connected PSA
#   Set psaConnectPause to between 2 and 60 seconds set the prompt waiting time before 
#     connection to most recenlty connected PSA automatically occurs.
# NOTE!!!! 
#   The connection dialog should NOT be bypassed where multiple PSA's are utilized by multiple
#     users on the same network unless PSA's are fully dedicated to individual users!
set psaConnectPause 0
if {$psaConnectPause > 0 && $psaConnectPause < 2} {
  set psaConnectPause 2
}
if {$psaConnectPause > 60} {
  set psaConnectPause 60
}

# Windows Installations
#    Fixed program location: c:\Program Files\Sifos\PSA1200\
#    Fixed user-modifiable file location:  (same)   
if { $tcl_platform(platform) == "windows" } {

  set psaPath "c:/Program Files/Sifos/PSA1200"

  if { $tcl_platform(osVersion) < 6.0 } {
     # this case is for Windows XP or older
     set psaHomePath $psaPath
  } else {
     # this case is for Windows Vista or newer
     set psaHomePath "c:/Users/Public/Sifos/PSA1200"
  }

# Unix/Linux Installations
#    Primary program location: /usr/local/Sifos/PSA1200/
#    Alternative program location: $HOME/Sifos/PSA1200/
#    User-modifiable file location:  $HOME/Sifos/PSA1200/
} elseif { $tcl_platform(platform) == "unix" } {
  set psaPath "/usr/local/Sifos/PSA1200"
  if { [file exists "/usr/local/Sifos/PSA1200/psa1200_library.tbc"] == 0 && [file exists "/usr/local/Sifos/PSA1200/psa1200_library.tcl"] == 0 } {
    set psaPath  "$env(HOME)/Sifos/PSA1200"
  }
  set psaHomePath "$env(HOME)/Sifos/PSA1200"
}

# Title the Console Window
#if { $tcl_platform(platform) == "windows" } {
  #   Catch the error - the command works anyway in Windows!
#  catch {console title "PowerShell TCL"}
#} else {
  #  Do nothing for Unix TCL Shell Window Name
#}

# Enable Compiled TCL (TBC) Files
#  Select tbcload library compiled for Windows, Linux, or Unix
#  Support Windows, Linux, Solaris (no spaces)
set libDir "$psaPath/PSA Interactive/sifos_lib"
if { [file exists $libDir] == 0 } {
  set libDir "$psaPath/PSA_Interactive/sifos_lib"
}
lappend auto_path $libDir
if { $tcl_platform(platform) == "windows" } {
  ## modify by andy.xie for package tbcload conflict, 2012-12-14
  
#if {[package vcompare [package provide tbcload] 1.6.1] != 0} { return }
package ifneeded tbcload 1.7 [list load [file join $libDir tbcload14.dll] tbcload]

##  load [file join $libDir tbcload14.dll] tbcload

## End of modify bn andy.xie
} elseif { $tcl_platform(platform) == "unix" && $tcl_platform(os) == "Linux"} {
  load [file join $libDir tbcload14_linux.so] tbcload
} elseif { $tcl_platform(platform) == "unix" && $tcl_platform(os) == "SunOS"} {
  load [file join $libDir tbcload14_solaris.so] tbcload
} else {
  puts "WARNING:  Compiled PowerShell Files Not Supported On This Platform !!!"
  puts "PLATFORM: $tcl_platform(platform)   OS: $tcl_platform(os)"
}

# Initialize PowerShell
if { [file exists $psaPath/psaInit.tbc] } {
  source $psaPath/psaInit.tbc
} else {
  source $psaPath/psaInit.tcl
}

# ===============================================
# ENTER STARTUP COMMANDS OR AUTO-RUN SCRIPTS HERE
#   Use 'psa_echo' command to open a shell that 
#   accepts PowerShell commands from 
#   .../PSA1200/Config/env/psa_command.txt
# psa_echo


# ==========================================================================================
# REMOVE '#' BELOW IF YOU WANT TCL SHELL TO TERMINATE UPON COMPLETION OF COMMANDS OR SCRIPTS
# exit







