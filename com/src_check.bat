@echo off

if not exist bin\maven (
    echo "build maven"
    cd bin
    tool\unzip.exe -q apache-maven-3.6.3-bin.zip
    cd ..
)

where svn.exe >NUL 2>NUL
IF %ERRORLEVEL% EQU 0 (
    echo "update src"
    svn up -q --accept tc
)

call bin\maven\bin\mvn.cmd test-compile
pause
