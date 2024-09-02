

import os, sys, time, threading, subprocess

testname = 'runsign'
suitename = 'testng-sign.xml'
curpwd = os.getcwd()
mvnbin = os.path.join(curpwd, 'bin/maven/bin/mvn.cmd')

if not os.path.isfile(mvnbin):
    raw_input("please run src_check.bat first")
    sys.exit(1)

def logmsg (msg):
    msg = "%s %s\n" % (time.strftime("%y%m%d-%H:%M:%S"), msg)
    print (msg)

def run_webportal():
    logfile = 'wp-%s-%s.log'%(testname, time.strftime("%y%m%d-%H%M%S"))
    logmsg('start to run wb: ' + logfile)
    fid = open(logfile, 'w')
    pid = subprocess.Popen(r"%s clean test -Dtestsuite=%s" % (mvnbin, suitename), stdout=fid, stderr=subprocess.STDOUT)
    pid.wait()
    fid.close()
    logmsg('end to run wb')

def run_test():
    t2 = threading.Thread(target=run_webportal)
    t2.start()
    t2.join()

while True:
    run_test()
    logmsg("next cycle")
    time.sleep(30)
