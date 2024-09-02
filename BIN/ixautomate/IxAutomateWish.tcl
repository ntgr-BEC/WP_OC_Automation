#console show

#ixia paths
lappend auto_path "C:/Program Files/Ixia/"
append env(PATH) ";C:/Program Files/Ixia/"

#IxAutomate paths
set automatePath [file normalize [file join [file dirname [info script]] ../..]]

set env(IXAUTOMATE_PATH) $automatePath
lappend auto_path $automatePath
lappend auto_path ${automatePath}/TclScripts
lappend auto_path ${automatePath}/TclScripts/lib

#set appInfo path
set appInfoPath [registry get "HKEY_LOCAL_MACHINE\\SOFTWARE\\Ixia Communications\\AppInfo\\InstallInfo" "HOMEDIR"]
set appInfoCmd [file join $appInfoPath appinfo.exe]
#IxOS paths
set cmd "\"$appInfoCmd\" --get-single-dependency IxOS --app-path \"$automatePath\""
set ixosPath [eval exec $cmd]
regsub "IxOS: " $ixosPath "" ixosPath
if {$ixosPath == ""} {
    set ixosPath "C:/Program Files/Ixia"
}
set env(IXTCLHAL_LIBRARY) "${ixosPath}/TclScripts/lib/IxTcl1.0"
lappend auto_path "${ixosPath}/TclScripts/lib/IxTcl1.0"
lappend auto_path "${ixosPath}/TclScripts/lib"
lappend auto_path "${ixosPath}/TclScripts"
lappend auto_path "${ixosPath}/../../TclScripts/lib/IxTclProtocol"
lappend auto_path "${ixosPath}/../../TclScripts/lib"
set env(PATH) "$env(PATH);$ixosPath"

#IxNetwork paths
set cmd "\"$appInfoCmd\" --get-single-dependency IxNetwork --app-path \"$automatePath\""
set networkPath [eval exec $cmd]
regsub "IxNetwork: " $networkPath "" networkPath
if {$networkPath == ""} {
    set networkPath "C:/Program Files/Ixia"

    set env(IXTCLHAL_LIBRARY) "${networkPath}/TclScripts/lib"
    lappend auto_path "${networkPath}/TclScripts/lib"
    lappend auto_path "${networkPath}/TclScripts"
    set env(PATH) "$env(PATH);$networkPath"
} else {  
    set env(IXTCLHAL_LIBRARY) "${networkPath}/TclScripts/lib"
    lappend auto_path "${networkPath}/TclScripts/lib"
    lappend auto_path "${networkPath}/TclScripts"
    set env(PATH) "$env(PATH);$networkPath"
    set cmd "\"$appInfoCmd\" --get-version --app-path {$networkPath }"
    set version [eval exec $cmd]
    regsub "IxNetwork: IxNetwork " $version "" version
    set versionBits [split $version "."]
    set versionNum  "[lindex $versionBits 0].[lindex $versionBits 1]"
    if { $versionNum >= 5.50 } {
        #IxNProtocols paths
        set cmd "\"$appInfoCmd\" --get-single-dependency IxNProtocols --app-path {$networkPath }"
        set protocolsPath [eval exec $cmd]
        regsub "IxNProtocols: " $protocolsPath "" protocolsPath
        puts "protocolsPath = $protocolsPath"
        if {$protocolsPath == ""} {
            set protocolsPath "C:/Program Files/Ixia"
        }
        set env(IXTCLHAL_LIBRARY) "${protocolsPath}/TclScripts/Lib"
        lappend auto_path "${protocolsPath}/TclScripts/Lib"
        lappend auto_path "${protocolsPath}/TclScripts"
        set env(PATH) "$env(PATH);$protocolsPath"
    }
}


#wm iconbitmap . "${automatePath}/GUI/Images/IxAutomate.ico"
puts -nonewline ""
