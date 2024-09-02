'''
apip tftpip fm logfile # upload new fw
apip logfile # query version
go # use all default values to update
no argument # query version via default
'''

import os, sys, time
from fabric import Connection
from invoke import Responder
from invoke import UnexpectedExit, CommandTimedOut, ThreadException

tftphost = '10.208.230.36'
aphost = '10.208.230.33'
appass = "Netgear1@"
apfm = 'WBC502-5.8.0-V20.04.42.3-BETA.bin'
logfile = "outputaplog.txt"

getver = True
if len(sys.argv) > 1:
    if sys.argv[1].find('.') == -1:
        getver = False
    else:
        aphost = sys.argv[1]
    if len(sys.argv) > 2:
        if sys.argv[2].find('txt') != -1:
            logfile = sys.argv[2]
        else:
            getver = False
            tftphost = sys.argv[2]
            apfm = sys.argv[3]
            logfile = sys.argv[4]

logfile=open(logfile,"w")
logfile.write("Started at: %s\n"%time.asctime())
# connect ap
def connectap(host, passwd):
    return Connection(host="admin@%s"%host, connect_kwargs={"password": passwd}, connect_timeout=15)

# run cmd
def runcmd(towatch, timeout=15):
    global logfile, apconn
    try:
        res = apconn.run("", pty=True, encoding="utf-8", warn=True, timeout=timeout, watchers=towatch)
    except Exception as e:
        print 'Exception:', e
        lines = repr(e.__str__().encode("utf-8"))
        lines = lines.replace('\\\\r\\\\n', '\n')
        lines = lines.replace('\\n', '\n')
        logfile.write(lines)
    else:
        logfile.write(res.stdout.encode("utf-8"))
        
# connect to ap
apconn = connectap(aphost, appass)
if getver:
    cmd1 = Responder(pattern='stat -- Status',response='stat\n')
    cmd2 = Responder(pattern='<LAN Settings>>',response='exit\n')
    cmd3 = Responder(pattern='System log',response='main\n')
    cmd4 = Responder(pattern='exit\r\nWBC502>',response='logout\n')
    runcmd([cmd1, cmd2, cmd3, cmd4])
else:
    cmd1 = Responder(pattern='stat -- Status',response='uci\n')
    cmd2 = Responder(pattern='uci\r\nuci>',
                     response='tftp -g -r %s -l /tmp/firmware.img %s\n'%(apfm, tftphost))
    cmd2 = Responder(pattern='uci\r\nuci>',
                     response='curl -o /tmp/firmware.img ftp://%s/%s\n'%(tftphost, apfm))
    runcmd([cmd1, cmd2], 60)
    apconn.close()
    
    apconn = connectap(aphost, appass)
    cmd1 = Responder(pattern='stat -- Status',response='uci\n')
    cmd2 = Responder(pattern='uci\r\nuci>',
                     response='''curl -k -d '{"mode":"Upgrade_locally"}' https://localhost:4430/api/mgm/fw_upgrade'''+'\n')
    runcmd([cmd1, cmd2])
    apconn.close()

logfile.close()
