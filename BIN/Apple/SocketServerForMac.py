#!python
import os, re, socket, sys, plistlib, time, commands
import xml.dom.minidom as minidom
from lockfile import LockFile, LockTimeout
from thread import *
import subprocess
import vpnclientformac


recvlen = 1024
certPath = "/Users/admin/Desktop/root.pem"
plistPath = "/Users/test/Desktop/WiFi/samples/default.plist"
testpath = "/Users/test/Desktop/WiFi/samples/WiFi"
WAFManager = "/AppleInternal/Library/WirelessAutomation/WATestHarness.py"
airport = "/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport"
ServerIP = '0.0.0.0'
log_filename = os.path.basename(sys.argv[0])+'.log'

# for download file in background
ftpdownload = 'ftpdownload.py'
ftpdownloadres = 'ftpdownloadres.txt'
ftpdownloadres_ios = '/tmp/_autores_.txt'

# for syslog


def logmsg (msg):
    msg = "%s %s\n" % (time.strftime("%y%m%d-%H:%M:%S"), msg)
    print msg
    with open (log_filename, 'a') as f:
        f.write (msg)

def runproc(cmnd, wait=1):
    logmsg("run app: %s" % cmnd)
    if cmnd[-2:].find('&') != -1: wait=0
    if wait:
        lines = []
        proc = subprocess.Popen(cmnd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
        while proc.returncode == None:
            lines += proc.stdout.readlines()
            proc.poll()
        if not lines:
            lines = ['dummy result']
        else:
            logmsg ("app output: %s" % "\n".join(lines))
    else:
        proc = subprocess.Popen(cmnd, shell=True, close_fds=True)
        logmsg ("no wait result request, return in 4s")
        time.sleep(4)
        lines = ["run in background"]
    return lines

def checkping(ip):
    info=os.popen("ping -c 4 %s"%ip).read()
    print info
    if '0 packets received' in info:
        return False
    else:
        return True

# return lost value
def checklongping(ip, duration=2*60):
    info=runproc("ping -nc %d %s"%(duration, ip))
    print info
    # 3 packets transmitted, 3 packets received, 0.0% packet loss
    for line in info:
        if line.find('packet loss') != -1:
            lost = re.search(', (\d+)\.\d+%', line)
            if lost:
                return lost.group(1)
    return "100"
            

"""
    threadWork function is use to implement the multiple thread. Try to execute the WAFCommand and generate plist file here.
    Parameters
        client: socket file descriptor
    WAF command example:
        WATestManager -f WiFi/WiFi_Associate.py -c default.plist -r -i -t 200
        WATestHarness -f WiFi/WiFi_Disassociate.py -c default.plist -u 16093412c27c350cc99521f1ff883ef3677d9741 -t 200
"""
def threadWork(client):
    data = recv_timeout(client)
    logmsg("Received command: %s" % data)
    print data
    msg = ''
    try:
        if "VPNPing" in data:
            ip = data.split()[1]
            if len(data.split()) > 2:
                msg = checklongping(ip, int(data.split()[2]))
            else:
                msg = checkping(ip)

        if "VPNLogin" in data:
            config = data.split()[1]
            user = config.split(",")[0]
            password = config.split(",")[1]
            msg = vpnclientformac.VPNClientLogin(user,password)

        if "VPNLogout" in data:
            msg = vpnclientformac.VPNClientLogout()

        if "VPNDisconnect" in data:
            msg = vpnclientformac.ClientDisconnect()

        if "VPNConnectGroup" in data:
            group = data.split()[1]
            msg = vpnclientformac.ClientConnectToGroup(group)

        if "VPNCheckUpdate" in data:
            msg = vpnclientformac.VPNClientCheckUpdate()

        if "VPNCheckLinkInfo" in data:
            msg = vpnclientformac.VPNClientCheckLinkInfo()

        if "VPNCheckText" in data:
            value = data.split(",")[1]
            msg = vpnclientformac.checkText(value)

        if "VPNCheckConnect" in data:
            msg = vpnclientformac.ClientConnectOrNot()


        if 'pkill' in data:
            if len(data.split())>2:
                os.system("pkill %s" % (" ".join(data.split()[1:])))
            else:
                os.system("pkill -9 %s" % data.split()[1])
            time.sleep(2)
            msg = "true"



        if 'route' in data:
            if len(data.split()) == 4:
                cmd_line = "%s/%s %s" % (data.split()[1], data.split()[2], data.split()[3])
                ret = runproc ("route -n delete %s" % cmd_line)
                ret = runproc ("route -n add %s" % cmd_line)
                msg = "true"
            else:
                logmsg("route: Parameter error!!")
                msg = "false"

    except:
        msg = "exception"


#    if not msg:
#        msg = runproc (data)

    msg=str(msg)
    logmsg ("result: %s" % "".join(msg))
    client.send("".join(msg))
    client.close()
    logmsg ("waiting for client request...")

"""
   recv_timeout function is a socket receive function. Socket server will try to received the data from socket in a timeout interval.
   parameters
        the_socket: socket fd
        timeout: time out interval, receiver will wait 2 second by default
"""
def recv_timeout(the_socket,timeout=2):
    #make socket non blocking
    the_socket.setblocking(0)

    #total data partwise in an array
    total_data=[];
    data='';

    #beginning time
    begin=time.time()
    while 1:
        #if you got some data, then break after timeout
        if total_data and time.time()-begin > timeout:
            break

        #if you got no data at all, wait a little longer, twice the timeout
        elif time.time()-begin > timeout*2:
            break

        #recv something
        try:
            data = the_socket.recv(recvlen)
            if data:
                total_data.append(data)
                #change the beginning time for measurement
                begin = time.time()
            else:
                #sleep for sometime to indicate a gap
                time.sleep(0.1)
        except:
            pass

    #join all parts to make final string
    return ''.join(total_data)


try:
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    sock.bind((ServerIP, 54321))
except socket.error, msg:
    sys.stderr.write("[ERROR] %s\n" % msg[1])
    sys.exit(1)

sock.listen(5)
logmsg ("Server is ready...")
while True:
    (csock, adr) = sock.accept()
    logmsg ("Client Info: %s" % repr(adr))
    start_new_thread(threadWork, (csock,))

