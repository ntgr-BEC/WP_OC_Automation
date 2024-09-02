import socket, sys, plistlib, time ,commands
from lockfile import LockFile
from thread import *
import subprocess
recvlen = 1024
plistPath = "/Users/test/Desktop/WiFi/samples/default.plist"
testpath = "/Users/test/Desktop/WiFi/samples/WiFi"
WAFManager = "/AppleInternal/Library/WirelessAutomation/WATestHarness.py"
ServerIP = '10.40.7.10'
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
    #data = client.recv(1024)
    print data
    if "WAFConnect" in data:
        config = data.split()[1]
        if len(config.split(",")) == 5:
            confInDic = {"udid":config.split(",")[0],"ssid":config.split(",")[1],"password":config.split(",")[2],"security":config.split(",")[3],"routerip":config.split(",")[4]}
        else:
            print("Parameter error!!")
        
        if genWiFiConf(confInDic,plistPath) & genTcpIPCong(confInDic,plistPath):
            command = "python "
            command += WAFManager
            command += " -f "
            command += testpath
            command += "/WiFi_Associate.py -c "
            command += plistPath
            command += " -u "
            command += confInDic["udid"]
            command += " -t 200"
            p = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
#for line in p.stdout.readlines():
#if "Test Summary" in line:
#break
            retval = p.wait()
            line = p.stdout.readlines()
            msg = line[-5]
            print msg
            retval = p.wait()
        else:
            msg = "failed"
    
    if "WAFDisConnect" in data:
        config = data.split()[1]
        if len(config.split(",")) == 1:
            confInDic = {"udid":config.split(",")[0]}
        else:
            print("Parameter error!!")
        command = "python "
        command += WAFManager
        command += " -f "
        command += testpath
        command += "/WiFi_Disassociate.py -c "
        command += plistPath
        command += " -u "
        command += confInDic["udid"]
        command += " -t 200"
        p = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
        time.sleep(10)
        line = p.stdout.readlines()
        msg = line[-5]
        print msg
        retval = p.wait()
    
    if bool("WAFDisConnect" not in data) & bool("WAFConnect" not in data):
        (ret,content) = commands.getstatusoutput(data)
        msg = str(ret) + '\n\t' + content + '\x1a'
        print("result is ",ret)
        print("content is ",content)                
    client.send(msg)
    client.close()

def parseWATestManageLog(resultline):
    print(line + "\r\n")
    #start to parse the result line
    resultarr = line.split("-")
    result = resultarr[len(resultarr)-1]
    passnum = int(result.split(",")[0].split()[0])
    if passnum > 0:
        msg = "pass"
    else:
        msg = "failed"
        retval = p.wait()
    return msg

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

"""
    genWiFiConf function is use to generate the plist file for WAFTestManager
    Parameters
        conf: A configuration, should be formated in "udid:xxxx,ssid:xxxx,security:xxxx,password:xxxx"
        confPath: The file path of the plist file
"""
def genWiFiConf(conf,confPath):
    lock = LockFile(confPath)
    while not lock.i_am_locking():
        try:
            lock.acquire(timeout=60)    # wait up to 60 seconds
        except LockTimeout:
            lock.break_lock()
            lock.acquire()
    print "SocketServer locked", lock.path
    try:
        with open(confPath,'r+') as fp:
            allList = plistlib.readPlist(fp)
    except:
        print("Open plist file failed!!")
        lock.release()
        return False
    point = allList["accesspoints"][0]
    point["ssid"] = conf["ssid"]
    if conf["security"].lower() == "WPA".lower():
        point["isWpa"] = True
        point["isWapi"] = False
        point["isWep"] = False
        point["isWpa2"] = False
        point["password"] = conf["password"]
        point["requiresPassword"] = True
    elif conf["security"].lower() == "WPA2".lower():
        point["isWpa2"] = True
        point["isWapi"] = False
        point["isWep"] = False
        point["isWpa"] = False
        point["password"] = conf["password"]
        point["requiresPassword"] = True
    elif conf["security"].lower() == "WEP".lower():
        point["isWep"] = True
        point["isWapi"] = False
        point["isWpa"] = False
        point["isWpa2"] = False
        point["password"] = conf["password"]
        point["requiresPassword"] = True
    elif conf["security"].lower() == "WPAMIX".lower():
        point["isWep"] = False
        point["isWapi"] = False
        point["isWpa"] = True
        point["isWpa2"] = True
        point["password"] = conf["password"]
        point["requiresPassword"] = True
    elif conf["security"].lower() == "None".lower():
        point["password"] = ""
        point["requiresPassword"] = False
        point["isWapi"] = False
        point["isWep"] = False
        point["isWpa"] = False
        point["isWpa2"] = False
    plistlib.writePlist(allList,confPath)
    fp.close()
    lock.release()
    return True

def genTcpIPCong(conf,confPath):
    lock = LockFile(confPath)
    while not lock.i_am_locking():
        try:
            lock.acquire(timeout=60)    # wait up to 60 seconds
        except LockTimeout:
            lock.break_lock()
            lock.acquire()
    print "SocketServer locked", lock.path
    try:
        with open(confPath,'r+') as fp:
            allList = plistlib.readPlist(fp)
    except:
        print("Open plist file failed!!")				
        lock.release()
        return False
    staticSettings = allList["tcpipsettings"][0]
    #staticSettings["ipAddress"] = conf["staticip"]
    staticSettings["router"] = conf["routerip"]
    plistlib.writePlist(allList,confPath)
    fp.close()
    lock.release()
    return True

try:
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
except socket.error, msg:
    sys.stderr.write("[ERROR] %s\n" % msg[1])
    sys.exit(1)

sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
sock.bind((ServerIP, 54321))
sock.listen(5)

while True:
    (csock, adr) = sock.accept()
    print "Client Info: ", csock, adr
    start_new_thread(threadWork, (csock,))

sock.close()
