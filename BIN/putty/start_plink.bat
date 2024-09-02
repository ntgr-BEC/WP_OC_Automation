cd \
cd C:\AUTOMATION\BIN\putty

start usr_mgt_053.bat /K
ping -n 10 127.0.0.1 > NUL
taskkill /F /IM plink.exe
taskkill /F /IM cmd.exe
