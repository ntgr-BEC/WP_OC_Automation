import os, sys, time, subprocess

def logmsg (msg):
    msg = "%s %s\n" % (time.strftime("%y%m%d-%H:%M:%S"), msg)
    print (msg)
    
def run_ping_t():
    logmsg(run_ping_t)
    return subprocess.Popen(r'C:\AUTOMATION\BIN\ping-host.bat')
    
    
while True:
    try:
        logmsg ('start to run ping')
        pid = run_ping_t()

        time.sleep(12*60*60)

        logmsg('start to end ping')
        pid.kill()
        logmsg('end')
    except:
        pass
