@echo off

set sport=4444
Title "Selenium Standalone %sport%"
java -Dwebdriver.chrome.driver=chromedriver.exe -jar selenium-server-standalone-3.13.0.jar -timeout 7200 -port %sport%
