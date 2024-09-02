#!python
import socket, commands, sys, os, time
import xml.dom.minidom as minidom
import binascii
import subprocess
import re
import ftplib

# this is an helper application to pop login dialogbox for PEAP/TTLS
clickpop = 'clickpop.exe'
os.chdir(os.path.dirname(os.path.abspath(__file__)))
log_filename = os.path.basename(sys.argv[0])+'-%s.log'%time.strftime("%y%m%d%H%M%S")

# for download file in background
ftpdownload = 'ftpdownload.py'
ftpdownloadres = 'ftpdownloadres.txt'
print 'WP current revision: 102'

def logmsg (msg):
    msg = "%s %s\n" % (time.strftime("%y%m%d-%H:%M:%S"), msg)
    print msg
    f = open (log_filename, 'a')
    f.write (msg)
    f.close()

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
        lines = ["run in background"]
        proc = subprocess.Popen(cmnd, shell=True, close_fds=True)
        logmsg ("no wait result request, return in 4s")
        time.sleep(4)
    return lines

def checkwifiModuleState(adapter_name, state):
    for line in runproc ('netsh interface show interface name="%s" | findstr Admin' % adapter_name):
        if line.find('Disable') != -1 and not state:
            return "true"
        if line.find('Enable') != -1 and state:
            return "true"
    logmsg ("checkwifiModuleState -> state: %s" % line)
    return "false"

def checkwifiConnState(connected):
    # FIXME: Maybe error if more than one wirless card. Make sure ONLY 1 wireless interface is used
    for line in runproc ('netsh wlan show interface | findstr State'):
        if line.find(' disconnected') != -1 and not connected:
            return True
        if line.find(' connected') != -1 and connected:
            return True
    logmsg ("checkwifiConnState: wireless card connection check failed.")
    return False

def wifiOn(adapter_name):
    logmsg("wifiOn: adapter_name: %s" % adapter_name)
    runproc ('netsh interface set interface name="%s" enable' % adapter_name)
    time.sleep(5)
    return checkwifiModuleState (adapter_name, True)

def wifiOff(adapter_name):
    logmsg("wifiOff: adapter_name: %s" % adapter_name)
    runproc ('netsh interface set interface name="%s" disable' % adapter_name)
    time.sleep(5)
    return checkwifiModuleState (adapter_name, False)

def findSSID (ssid):
    # not to very big number to avoid a hidden ssid
    ntry = 5
    while ntry>0:
        ntry -= 1
        runproc ("scannet.exe")
        for line in runproc ("netsh wlan show network | findstr %s" % ssid):
            if line.find(ssid) != -1:
                return True
        time.sleep(4)
    logmsg ("findSSID -> not able to scan the ssid: %s" % ssid)
    runproc ("netsh wlan show network | findstr SSID")
    return False

def waitConnected ():
    timeout = 120
    now = time.time()
    while time.time()-now<timeout:
        if checkwifiConnState(True):
            # maybe sleep for dhcp
            time.sleep(5)
            return "true"
        time.sleep(4)
    logmsg ("waitConnected -> timeout on connect")
    return "false"

def connectProfile(ssid, filename):
    profile = filename[:-4]
    logmsg ("filename name is: %s" % filename)
    runproc ("netsh wlan delete profile name =*")
    findSSID(ssid)
    runproc ("netsh wlan add profile filename=\""+filename+"\"")
    runproc ("netsh wlan set profileparameter name=\""+profile+"\" connectionmode=auto")
    time.sleep(10)
    count = 1
    status = "false"
    while (count < 10):
        logmsg ("Trying connection in loop...The count is: %s" % count)
        count = count + 1
        runproc ("netsh wlan connect name =\""+ssid +"\""+" ssid=\""+ssid + "\"")
        if waitConnected () == "true":
            status = "true"
            runproc ("route print -4")
            break
    return status

def importCert():
    rootca = "root.pem"
    clientca = "netClient.p12"
    if not (os.path.isfile(rootca) and os.path.isfile(rootca)):
        logmsg ("Could not find certification files, please update files")
        return False
    else:
        os.system("certutil -addstore -f Root %s" % rootca)
        os.system("certutil -importpfx -p whatever -user -f %s NoRoot" % clientca)
        return True

def wifiConnectTTLSRadius(ssid,username,password,authvar,encrypvar):
    # TODO: password must be whatever because we have no way to encode the passwd
    if not importCert(): return "false"
    if not os.path.isfile(clickpop): return "false"
    
    logmsg ('wifiConnectTTLSRadius')
    DOMTree = minidom.parse("WPARadiusTTLS.xml")
    names = DOMTree.getElementsByTagName("name")
    hexvalue = DOMTree.getElementsByTagName("hex")
    auth = DOMTree.getElementsByTagName("authentication")
    encryp = DOMTree.getElementsByTagName("encryption")
    names[0].childNodes[0].data = ssid
    names[1].childNodes[0].data = ssid
    auth[0].childNodes[0].data = authvar.upper()
    encryp[0].childNodes[0].data = encrypvar.upper()
    hexvalue[0].childNodes[0].data = binascii.b2a_hex(ssid.encode("utf-8")).decode("utf-8")
    filename = ssid+".xml"
    f = open(filename,  'w')
    DOMTree.writexml(f)
    f.close()
    
    runproc ("taskkill /f /im %s" % clickpop)
    runproc ("%s %s %s" % (clickpop, username, password), 0)
    res = connectProfile (ssid, filename)
    runproc ("taskkill /f /im %s" % clickpop)
    return res

def wifiConnectTLSRadius(ssid,authvar,encrypvar):
    if not importCert(): return "false"
    
    logmsg ('wifiConnectTLSRadius')
    DOMTree = minidom.parse("WPARadiusTLS.xml")
    names = DOMTree.getElementsByTagName("name")
    hexvalue = DOMTree.getElementsByTagName("hex")
    auth = DOMTree.getElementsByTagName("authentication")
    encryp = DOMTree.getElementsByTagName("encryption")
    names[0].childNodes[0].data = ssid
    names[1].childNodes[0].data = ssid
    auth[0].childNodes[0].data = authvar.upper()
    encryp[0].childNodes[0].data = encrypvar.upper()
    hexvalue[0].childNodes[0].data = binascii.b2a_hex(ssid.encode("utf-8")).decode("utf-8")
    filename = ssid+".xml"
    f = open(filename,  'w')
    DOMTree.writexml(f)
    f.close()
    return connectProfile (ssid, filename)
    
def wifiConnectRadius(ssid,username,password,authvar,encrypvar):
    if not os.path.isfile(clickpop): return "false"
    
    logmsg ('wifiConnectRadius')
    DOMTree = minidom.parse("WPARadius.xml")
    names = DOMTree.getElementsByTagName("name")
    hexvalue = DOMTree.getElementsByTagName("hex")
    auth = DOMTree.getElementsByTagName("authentication")
    encryp = DOMTree.getElementsByTagName("encryption")
    names[0].childNodes[0].data = ssid
    names[1].childNodes[0].data = ssid
    auth[0].childNodes[0].data = authvar.upper()
    encryp[0].childNodes[0].data = encrypvar.upper()
    hexvalue[0].childNodes[0].data = binascii.b2a_hex(ssid.encode("utf-8")).decode("utf-8")
    filename = ssid+".xml"
    f = open(filename,  'w')
    DOMTree.writexml(f)
    f.close()
    
    runproc ("taskkill /f /im %s" % clickpop)
    runproc ("%s %s %s" % (clickpop, username, password), 0)
    res = connectProfile (ssid, filename)
    runproc ("taskkill /f /im %s" % clickpop)
    return res

def wifiConnect(ssid,password,authvar,encrypvar):
    logmsg ('wifiConnect')
    DOMTree = minidom.parse("general.xml")
    names = DOMTree.getElementsByTagName("name")
    hexvalue = DOMTree.getElementsByTagName("hex")
    keymaterial = DOMTree.getElementsByTagName("keyMaterial")
    auth = DOMTree.getElementsByTagName("authentication")
    encryp = DOMTree.getElementsByTagName("encryption")
    names[0].childNodes[0].data = ssid
    names[1].childNodes[0].data = ssid
    auth[0].childNodes[0].data = authvar.upper()
    encryp[0].childNodes[0].data = encrypvar.upper()
    hexvalue[0].childNodes[0].data = binascii.b2a_hex(ssid.encode("utf-8")).decode("utf-8")
    keymaterial[0].childNodes[0].data = password
    filename = ssid+".xml"
    f = open(filename,  'w')
    DOMTree.writexml(f)
    f.close()
    return connectProfile (ssid, filename)

def wifiOpenConnect(ssid):
    logmsg ('wifiOpenConnect')
    DOMTree = minidom.parse("generalOpen.xml")
    names = DOMTree.getElementsByTagName("name")
    hexvalue = DOMTree.getElementsByTagName("hex")
    names[0].childNodes[0].data = ssid
    names[1].childNodes[0].data = ssid
    hexvalue[0].childNodes[0].data = binascii.b2a_hex(ssid.encode("utf-8")).decode("utf-8")
    auth = DOMTree.getElementsByTagName("authentication")
    encryp = DOMTree.getElementsByTagName("encryption")
    auth[0].childNodes[0].data = "open"
    encryp[0].childNodes[0].data = "none"
    filename = ssid+".xml"
    f = open(filename,  'w')
    DOMTree.writexml(f)
    f.close()
    return connectProfile (ssid, filename)

def wifiOpenConnectWep(ssid, wepkey):
    logmsg ('wifiOpenConnectWep')
    DOMTree = minidom.parse("openwep.xml")
    names = DOMTree.getElementsByTagName("name")
    hexvalue = DOMTree.getElementsByTagName("hex")
    keymaterial = DOMTree.getElementsByTagName("keyMaterial")
    names[0].childNodes[0].data = ssid
    names[1].childNodes[0].data = ssid
    hexvalue[0].childNodes[0].data = binascii.b2a_hex(ssid.encode("utf-8")).decode("utf-8")
    auth = DOMTree.getElementsByTagName("authentication")
    encryp = DOMTree.getElementsByTagName("encryption")
    auth[0].childNodes[0].data = "open"
    encryp[0].childNodes[0].data = "WEP"
    keymaterial[0].childNodes[0].data = wepkey
    filename = ssid+".xml"
    f = open(filename,  'w')
    DOMTree.writexml(f)
    f.close()
    return connectProfile (ssid, filename)

def wifiOpenConnectShared(ssid, wepkey):
    logmsg ('wifiOpenConnectShared')
    DOMTree = minidom.parse("sharedkey.xml")
    names = DOMTree.getElementsByTagName("name")
    hexvalue = DOMTree.getElementsByTagName("hex")
    keymaterial = DOMTree.getElementsByTagName("keyMaterial")
    names[0].childNodes[0].data = ssid
    names[1].childNodes[0].data = ssid
    hexvalue[0].childNodes[0].data = binascii.b2a_hex(ssid.encode("utf-8")).decode("utf-8")
    auth = DOMTree.getElementsByTagName("authentication")
    encryp = DOMTree.getElementsByTagName("encryption")
    auth[0].childNodes[0].data = "shared"
    encryp[0].childNodes[0].data = "WEP"
    keymaterial[0].childNodes[0].data = wepkey
    filename = ssid+".xml"
    f = open(filename,  'w')
    DOMTree.writexml(f)
    f.close()
    return connectProfile (ssid, filename)

def wifiDisconnect():
    command = "netsh wlan disconnect"
    runproc (command)
    time.sleep(2)
    if checkwifiConnState (False):
        return "true"
    else:
        return "false"

def addmulticastroute(interface):
    command = "route delete 239.1.1.1 mask 255.255.255.255"
    runproc (command)
    command = "route add 239.1.1.1 mask 255.255.255.255 %s" % interface
    runproc (command)
    return "true"

def runvlc():
    command = "\"C:\\Program Files\\VideoLAN\\VLC\\vlc.exe\" -vvv udp://@239.1.1.1"
    runproc (command, 0)
    return "true"

def quitvlc():
    command = "tskill vlc"
    runproc (command)
    return "true"

def checkvlcprocess():
    command = "tasklist|find /i \"vlc.exe\""
    strtest  = runproc (command)
    result = "".join(strtest)
    return result

def checkmulticast(adapter):
    command = "tshark -c 10 -f \"udp and dst host 239.1.1.1\" -l -a duration:60 -i "+adapter
    strtest  = runproc (command)
    result = "".join(strtest)
    return result

def ftpdownloadstart(ftpip, ftpuser, ftpuserpass, ftpfilename):
    return runproc ("python %s -i %s -u %s -p %s -f %s -l %s -s > ftpdownload_log.txt" %
        (ftpdownload, ftpip, ftpuser, ftpuserpass, ftpfilename, ftpdownloadres), 0)

def ftpdownloadcheck():
    logmsg ("check if log file exists: %s" % ftpdownloadres)
    while not os.path.exists(ftpdownloadres):
        time.sleep(4)
    time.sleep(2)
    f = open(ftpdownloadres)
    res = f.read()
    f.close()
    return res
    
HOST = '0.0.0.0'
PORT = 54321
try:
    sock = socket.socket(socket.AF_INET , socket.SOCK_STREAM)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    sock.bind((HOST,PORT))
except socket.error, msg:
    sys.stderr.write("[ERROR] %s\n" % msg[1])
    sys.exit(1)


sock.listen(5)
while True:
    try:
        logmsg ("Server is ready...")
        conn , addr = sock.accept()
        logmsg('Connected by: %s' % repr(addr))
        data = conn.recv(1024)
        if not data:break
        logmsg("Received data is: %s" % data)
        msgSend = ''
        if 'WAFSetIpStatic' in data:
            if len(data.split()) < 5:
                msgSend = "WAFSetIpStatic: Wrong arguments!"
            else:
                newip = data.split()[1]
                if 1 == os.system("ping -n 1 %s" % newip):
                    interface = data.split(None, 4)[4]
                    runproc('netsh interface ip set address name="%s" source=static addr=%s mask=255.255.255.0 gateway=%s' %
                            (interface, newip, data.split()[2]))
                    runproc('netsh interface ip add dns name="%s" addr=%s' % (interface, data.split()[3]))
                    msgSend = "true"
                else:
                    logmsg("ip %s is existed" % newip)
                    msgSend = "false"
        if 'WAFSetIpDHCP' in data:
            if len(data.split()) != 2:
                msgSend = "WAFSetIpDHCP: Wrong arguments!"
            else:
                interface=data.split(None, 1)[1]
                runproc('netsh interface ip set address "%s" dhcp' % interface)
                runproc('netsh interface ip set dns "%s" dhcp' % interface)
                msgSend = "true"
        if 'WAFwifiOn' in data:
            if len(data.split()) != 2:
                msgSend = "WAFwifiOn: Wrong arguments!"
            else:
                msgSend = wifiOn(data.split(None, 1)[1])
        if 'WAFwifiOff' in data:
            if len(data.split()) != 2:
                msgSend = "WAFwifiOff: Wrong arguments!"
            else:
                msgSend = wifiOff(data.split(None, 1)[1])
        if 'WAFfindSSID' in data:
            if len(data.split()) != 2:
                msgSend = "WAFfindSSID: Wrong arguments!"
            else:
                ret = findSSID(data.split(None, 1)[1])
                if ret:
                    msgSend = "true"
                else:
                    msgSend = "false"
        if 'WAFradiusttlsconnect' in data:
            if len(data.split()) != 6:
                msgSend = "WAFradiusttlsconnect: Wrong arguments!"
            else:
                msgSend = wifiConnectTTLSRadius(data.split()[1],data.split()[2],data.split()[3],data.split()[4],data.split()[5])
        if 'WAFradiustlsconnect' in data:
            if len(data.split()) != 4:
                msgSend = "WAFradiustlsconnect: Wrong arguments!"
            else:
                msgSend = wifiConnectTLSRadius(data.split()[1],data.split()[2],data.split()[3])
        if 'WAFradiusconnect' in data:
            if len(data.split()) != 6:
                msgSend = "WAFradiusconnect: Wrong arguments!"
            else:
                msgSend = wifiConnectRadius(data.split()[1],data.split()[2],data.split()[3],data.split()[4],data.split()[5])
        if 'WAFconnect' in data:
            if len(data.split()) != 5:
                msgSend = "WAFconnect: Wrong arguments!"
            else:
                msgSend = wifiConnect(data.split()[1],data.split()[2],data.split()[3],data.split()[4])
        if 'WAFopenwepconnect' in data:
            if len(data.split()) != 3:
                msgSend = "WAFopenwepconnect: Wrong arguments!"
            else:
                if len(data.split()[2]) > 26:
                    msgSend = "WAFopenwepconnect: key must not be great than 26!"
                else:
                    msgSend = wifiOpenConnectWep(data.split()[1], data.split()[2])
        if 'WAFopensharedconnect' in data:
            if len(data.split()) != 3:
                msgSend = "WAFopensharedconnect: Wrong arguments!"
            else:
                if len(data.split()[2]) > 26:
                    msgSend = "WAFopensharedconnect: key must not be great than 26!"
                else:
                    msgSend = wifiOpenConnectShared(data.split()[1], data.split()[2])
        if 'WAFopenconnect' in data:
            if len(data.split()) != 2:
                msgSend = "WAFopenconnect: Wrong arguments!"
            else:
                msgSend = wifiOpenConnect(data.split()[1])
        if 'WAFdisconnect' in data:
            msgSend = wifiDisconnect()
        if 'WAFgetip' in data:
            strlst = runproc ("netsh interface ip show ipaddress \"%s\"" % data.split(None, 1)[1])
            logmsg("ip info is: %s" % strlst)
            if len(strlst) > 0:
                msgSend = strlst[1]
            else:
                msgSend = "false"
        if 'WAFping' in data:
            if len(data.split()) != 3:
                msgSend = "WAFping: Wrong arguments!"
            else:
                str1  = runproc ("ping %s -S %s" % (data.split()[1], data.split()[2]))
                msgSend = "".join(str1)
        if 'WAFlongping' in data:
            if len(data.split()) != 4:
                msgSend = "WAFlongping: Wrong arguments!"
            else:
                logmsg("WAFlongping info is: %s %s %s" % (data.split()[3], data.split()[1], data.split()[2]))
                msgSend  = runproc ("ping -n %s %s -S %s > c:\\windowspingresult.txt" % (data.split()[3], data.split()[1], data.split()[2]), 0)
        if 'WAFgetpingresult' in data:
            filename = "c:\\windowspingresult.txt"
            if os.path.exists(filename):
                str1  = runproc("type %s | findstr \"Sent\"" % filename)
                os.remove(filename)
            else:
                str1 = ["longpingerror \n"]
            msgSend = "".join(str1)     
        if 'WAFruncaptive' in data:
            DOMTree = minidom.parse("Config.xml")
            testurl = DOMTree.getElementsByTagName("testurl")
            username = DOMTree.getElementsByTagName("username")
            userpassword = DOMTree.getElementsByTagName("userpassword")
            testurl[0].childNodes[0].data = data.split()[2]
            username[0].childNodes[0].data = data.split()[3]
            userpassword[0].childNodes[0].data = data.split()[4]
            filename = "captive.xml"
            f = open(filename, 'w')
            DOMTree.writexml(f)
            f.close()
            strtest  = runproc ('python "%s"' % data.split(None)[1])
            msgSend = "".join(strtest)
        if 'WAFRunVLC' in data:
            msgSend = runvlc()
        if 'WAFCheckVLCProcess' in data:
            msgSend = checkvlcprocess()
        if 'WAFAddMulticastRoute' in data:
            msgSend = addmulticastroute(data.split(None)[1])
        if 'WAFCheckMulticast' in data:
            msgSend = checkmulticast(data.split(None, 1)[1])
        if 'WAFQuitVLC' in data:
            msgSend = quitvlc()
        if 'WAFFTPDownloadStart' in data:
            if len(data.split()) != 5:
                msgSend = "WAFFTPDownloadStart: Wrong arguments or filename has space!"
            else:
                msgSend = ftpdownloadstart(data.split()[1], data.split()[2], data.split()[3], data.split()[4])
        if 'WAFFTPDownloadCheck' in data:
            msgSend = ftpdownloadcheck ()
        if 'WAFhttpredirect' in data:
            DOMTree = minidom.parse("Config.xml")
            testurl = DOMTree.getElementsByTagName("testurl")
            testurl[0].childNodes[0].data = data.split()[2]
            filename = "httpredirect.xml"
            f = open(filename,  'w')
            DOMTree.writexml(f)
            f.close()
            strtest  = runproc ('python '+data.split()[1])
            msgSend = "".join(strtest)
        if 'WAFwifiStatusConnected' in data:
            if checkwifiConnState (True):
                msgSend = "true"
            else:
                msgSend = "false"
        if not msgSend:
            msgSend = runproc (data)
        logmsg ("sending result: %s" % "".join(msgSend))
        conn.sendall ("".join(msgSend))
    except Exception, e:
        logmsg (e)

    conn.close()
    logmsg ("waiting for client request...")

conn.close()
