Function ntgrRegExpTest(strAny, strPattern)
Dim regEx

    Set regEx = New RegExp
    regEx.IgnoreCase = True
    regEx.Global = True

    regEx.Pattern = strPattern

    ntgrRegExpTest = regEx.Test(strAny)

    Set regEx = Nothing

End Function

Function Close_Process(ProcessName) 
On Error Resume Next 
     for each ps in getobject("winmgmts:\\.\root\cimv2:win32_process").instances_
           if Ucase(ps.name)=Ucase(ProcessName) then 
                 ps.terminate 
           end if 
     next 
End Function 
Function APisReachable(strIP)
    Set WshShell = CreateObject("WScript.Shell")
    Set WshScriptExec = WshShell.Exec("ping.exe -n 4 " & strIP)
	strOutput = vbNullString
    Do While Not WshScriptExec.StdOut.AtEndOfStream
        strOutput = strOutput & WshScriptExec.StdOut.ReadLine & vbCrLf
    Loop
	APisReachable = ntgrRegExpTest(strOutput, "Reply from " & strIP) And ntgrRegExpTest(strOutput, "Received = [1-9]")
End Function

set fso = createobject("Scripting.FileSystemObject")
ScriptFolder = fso.GetFolder(".").Path
BaseFolder = fso.GetParentFolderName(ScriptFolder)
'MsgBox "BaseFolder=" & BaseFolder
WebPortalXml = BaseFolder + "\com\src\test\resources\config_webportal.xml"
ConfigDUTXml = BaseFolder + "\com\src\test\resources\config_dut.xml"

set ie=createobject("internetexplorer.application")
set ws = createobject("wscript.shell")
Set objXML = CreateObject("Msxml2.DOMDocument.6.0")
'objXML.load("C:\Insight\Web_Portal_Automation\com\src\test\resources\config_webportal.xml")
objXML.load(WebPortalXml)
objXML.setProperty "SelectionLanguage", "XPath"
Set objNode1 = objXML.selectSingleNode("/config/AP1")
'objXML.load("C:\Insight\Web_Portal_Automation\com\src\test\resources\config_dut.xml")
objXML.load(ConfigDUTXml)
objXML.setProperty "SelectionLanguage", "XPath"
Set objNode2 = objXML.selectSingleNode("/Config/DUT_List/" & objNode1.text & "/Ip_Address")

If APisReachable(objNode2.text) Then
    ie.navigate "https://" & objNode2.text
    ie.Visible = True

    wscript.sleep 10000

    ie.Document.getElementById("moreInfoContainer").click

    wscript.sleep 10000

    ie.Document.getElementById("overridelink").click

    wscript.sleep 20000

    ie.Document.getElementById("userName").Value = "admin"
    ie.Document.getElementById("userPwd").Value = "Netgear1@"
    ie.Document.getElementById("enter").click

    wscript.sleep 15000

    ie.navigate "https://" & objNode2.text & "/advanced_debugging.html"

    wscript.sleep 10000

    ie.Document.getElementById("enssh").click

    wscript.sleep 3000

    ie.document.getelementsbytagname("button")(1).click

    wscript.sleep 90000

    Close_Process("iexplore.exe") 
End If
