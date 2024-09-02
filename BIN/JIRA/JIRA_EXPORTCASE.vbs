Set vbs=CreateObject("Wscript.Shell")
cmd="java -jar C:\AUTOMATION\BIN\JIRA\exportJIRAcase.jar"
vbs.Run "cmd"
wscript.sleep 1000  
vbs.SendKeys cmd  
vbs.SendKeys "{ENTER}"