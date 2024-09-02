LogPath = "C:\AUTOMATION\TESTSUITE\TestReport"
Set FSO = CreateObject("Scripting.FileSystemObject")
Dim dt, fn 
dt = "1990-1-1" 'use to compare time
If FSO.FolderExists(LogPath) Then
    Set Folder = FSO.GetFolder(LogPath)
    For Each File In Folder.Files
            If DateDiff("s", dt, File.DateLastModified) > 0 Then
               
                dt = File.DateCreated
                fn = File.Name
            End If
    Next
    Set Folder = Nothing
Else
    MsgBox "Folder " & LogPath & " not found!", vbExclamation
    WScript.Quit
End If

Set vbs=CreateObject("Wscript.Shell")
'filename="GC728XP v8.1.20.28-TS-GUI-FUNC-ARP-20171204.xlsx"
Set vbs=CreateObject("Wscript.Shell")
cmd="cmd /c java -jar C:\AUTOMATION\BIN\JIRA\importTestResultToJIRA.jar "+Chr(34)+fn+Chr(34)
vbs.Run cmd
'wscript.sleep 2000  
'vbs.SendKeys cmd  
'vbs.SendKeys "{ENTER}"