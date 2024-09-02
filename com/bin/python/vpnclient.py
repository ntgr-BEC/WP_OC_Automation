#coding:utf-8



import sys, subprocess
sys.coinit_flags = 0 # pythoncom.COINIT_MULTITHREADED

import win32com,os
import win32com.client
import win32gui
import win32con
import pythoncom,time
from VPNObject import VPNObjectLib as objectlib
from PIL import ImageGrab


debugMode = False
mainDoc = None
mainHwnd = None
mainDocDom = None
try:
    os.mkdir('tmp')
except:
    pass
    
def capturess(func='call ss'):
    tmpFile = time.strftime("tmp/ss_%Y%m%d_%H%M%S.jpg")
    print func, tmpFile
    if debugMode:
        return
    ImageGrab.grab().save(tmpFile, "JPEG")

def getIEServer(hwnd, ieServer):
    if win32gui.GetClassName(hwnd) == 'Internet Explorer_Server':
        ieServer.append(hwnd)
        
def getVPNHandle():
    global mainDoc, mainHwnd, mainDocDom
    if mainDoc and mainHwnd and mainDocDom:
        return True
    mainHwnd = win32gui.FindWindow('WebView', 'Insight Instant VPN')
    capturess(sys._getframe().f_code.co_name)
    if mainHwnd:
        print time.asctime(), 'found vpn was running'
        ieServers = []
        win32gui.EnumChildWindows(mainHwnd, getIEServer, ieServers)
        if len(ieServers) > 0:
            ieServer = ieServers[0]
            msg = win32gui.RegisterWindowMessage('WM_HTML_GETOBJECT')
            ret, result = win32gui.SendMessageTimeout(ieServer, msg, 0, 0, win32con.SMTO_ABORTIFHUNG, 1000)
            ob = pythoncom.ObjectFromLresult(result, pythoncom.IID_IDispatch, 0)
            doc = win32com.client.dynamic.Dispatch(ob)

            mainDoc = doc
            mainDocDom = mainDoc.body
            print mainDoc.url
            return True
    else:
        print time.asctime(), 'found vpn was not running'
        return False

def getInsightVPNClientPath():
    vpath = "C:\\Program Files (x86)\\InsightVPNClient\\bin\\InsightVPNClient.exe"
    if os.path.isfile(vpath):
        return vpath
    vpath = "C:\\Program Files\\InsightVPNClient\\bin\\InsightVPNClient.exe"
    if os.path.isfile(vpath):
        return vpath

def getVPNDispatchDoc():
    getVPNHandle()
    if not mainDoc:
        p=subprocess.Popen(getInsightVPNClientPath(), shell=True)
    #    os.system(r'"C:\Program Files (x86)\InsightVPNClient\bin\InsightVPNClient.exe"')
        time.sleep(5)
        getVPNHandle()
        waitLoginPage()
        p.terminate()
    return mainDoc

try_times = 30
def findelemntbyID(byid):
    el = mainDocDom.all[byid]
    print 'findelemntbyID: %s' % byid
    if el:
        print 'found 1 element', el.nodeName
        return el
    else:
        print 'failed to to find'
        return None
    
def findelemntbyTag(text, tag):
    els = []
    print 'findelemntbyTag: %s/%s' % (text, tag)
    for el in mainDocDom.getElementsByTagName(tag.upper()):
        if el.innerText.lower().find(text.lower()) != -1:
            els.append(el)
    if len(els) > 0:
        print 'found %s elements' % len(els)
        return els[-1]
    else:
        print 'failed to to find'
        return None
    
def findelemntbyClass(bycls):
    print 'findelemntbyClass: %s' % bycls
    els = mainDocDom.getElementsByClassName(bycls)
    if els.length > 0:
        print 'found %s elements' % els.length
        return els[els.length-1]
    else:
        print 'failed to to find'
        return None

# only id/class & tag with text are support
def findElement(*args):
    print time.asctime(), 'findElement', args
    if len(args) == 1:
        try:
            el = findelemntbyID(args[0])
            if el:
                return el
        except:
            pass
        try:
            el = findelemntbyClass(args[0])
            if el:
                return el
        except:
            pass
    if len(args) == 2:
        el = findelemntbyTag(args[0], args[1])
        if el:
            return el
    return None
        
def waitElement(*args):
    i = 1
    while i<try_times:
        i+=1
        time.sleep(2)
        if findElement(*args):
            break
            
def waitElementNot(*args):
    i = 1
    while i<try_times:
        i+=1
        time.sleep(2)
        if not findElement(*args):
            break

def waitLoginPage():
    waitElement(objectlib['email'])
        
def waitGroupPage():
    waitElement('sortGroupName')

def waitConnectedPage():
    capturess(sys._getframe().f_code.co_name)
    waitElement('totalReceived')
    waitElement('totalSent')
    waitElement('latency')
    i = 1
    while i<try_times:
        if int(findElement('latency').innerText)==0 and float(findElement('totalReceived').innerText)==0:
            continue
        time.sleep(20)
        break
    capturess(sys._getframe().f_code.co_name)

def VPNClientLogin(name,password):
    capturess(sys._getframe().f_code.co_name)
    print time.asctime(), 'VPNClientLogin', name
    if not findElement(objectlib['email']):
        return True
    for j in range(2):
        if mainDoc.all[objectlib['email']]:
            mainDoc.all[objectlib['email']].value = name
            mainDoc.all[objectlib['password']].value = password
            remember_pw = findElement('login_save_password')
            if remember_pw and not remember_pw.checked:
                remember_pw.click()
            time.sleep(1)
            mainDoc.all.tags('button').item(0).click()
            waitGroupPage()
            time.sleep(10)

    if mainDoc.all[objectlib['email']]:
        return False
    else:
        time.sleep(5)
        return True

def VPNClientLogout():
    capturess(sys._getframe().f_code.co_name)
    print time.asctime(), 'VPNClientLogout'
    if mainDoc.all[objectlib['email']]:
        return True
    el = findElement('user')
    if el:
        el.click()
        el = findElement('Logout', 'a')
        if el:
            el.click()
        time.sleep(4)
    else:
        return False
    if mainDoc.all[objectlib['email']]:
        return True
    else:
        return False

def VPNClientClose():
    capturess(sys._getframe().f_code.co_name)
    try:
        if not getVPNHandle():
            return
        print time.asctime(), 'VPNClientClose'
        el = findElement('Quit', 'a')
        if el:
            el.click()
            time.sleep(4)
    except Exception as err:
        print 'VPNClientClose', err
        
    try:
        win32gui.PostMessage(mainHwnd,win32con.WM_QUIT,0,0)
        time.sleep(4)
    except:
        pass
        
    mainDoc = None
    mainHwnd = None
    
    time.sleep(4)
    os.system("taskkill /f /im InsightVPNClient.exe")
    os.system("taskkill /f /im werfault.exe")

def ClientConnectToGroup(groupname):
    capturess(sys._getframe().f_code.co_name)
    print time.asctime(), 'ClientConnectToGroup', groupname
    
    for i in range(3):
        if findElement(groupname, 'span'):
            findElement(groupname, 'span').click()
            waitConnectedPage()
            break
        findElement('btns-refresh').click()
        time.sleep(20)
        
    time.sleep(10)
    return ClientConnectOrNot()

def GetConnectedInfo():
    print time.asctime(), 'GetConnectedInfo', mainDoc.all.tags('span').length
    for i in range(mainDoc.all.tags('span').length):
        print time.asctime(), str(i)+":"+mainDoc.all.tags('span').item(i).innerHTML

def ClientDisconnect():
    capturess(sys._getframe().f_code.co_name)
    if mainDoc.all['disconnect_btn']:
        print time.asctime(), mainDoc.all['disconnect_btn'].innerText
        mainDoc.all['disconnect_btn'].click()
        waitGroupPage()
        time.sleep(4)
        return True
    else:
        return False

def ClientConnectOrNot():
    capturess(sys._getframe().f_code.co_name)
    el = findElement('duration')
    if el and el.innerText.find('00:00:00') == -1:
        return True
    else:
        return False

def clickClose():
    try:
        while findElement('Close', 'button'):
            findElement('Close', 'button').click()
            time.sleep(5)
    except Exception as err:
        print 'clickClose', err

def VPNClientCheckUpdate():
    result = False
    mainDoc.all['user'].click()
    findElement('Check for Updates', 'a').click()
    time.sleep(30)
    a = findElement('version_info')
    if a and a.innerText.find('latest') != -1:
        result = True
    clickClose()
    print result
    return result

def VPNClientCheckAbout():
    result = False
    try:
        mainDoc.all['user'].click()
        findElement('About', 'a').click()
        time.sleep(4)
        a = findElement('Copyright', 'div')
        if a and a.innerText.find(time.strftime('%Y')) != -1:
            result = True
        clickClose()
    except Exception as err:
        print 'VPNClientCheckAbout', err
    print result
    return result

def VPNClientCheckLinkInfo():
    capturess(sys._getframe().f_code.co_name)
    result = True
    el = findElement('group_ip')
    if not (el and el.innerText.find('.') != -1):
        print 'check vpn client ip', el.innerText if el else ': err'
        result = False
    el = findElement('remoteNetworks')
    if not (el and el.innerText.find('.*') != -1):
        print 'check vpn sub net ip', el.innerText if el else ': err'
        result = False
    el = findElement('latency')
    if not (el and int(el.innerText)>0):
        print 'check latency', el.innerText if el else ': err'
        result = False
    el = findElement('duration')
    if not (el and el.innerText.find('00:00:00') == -1):
        print 'check duration', el.innerText if el else ': err'
        result = False
    el = findElement('totalReceived')
    if not (el and float(el.innerText)>0):
        print 'check totalReceived', el.innerText if el else ': err'
        result = False
    # totalSent may be 0 cause no one send traffic, lets wait for 5m more
    for i in range(6*5):
        el = findElement('totalSent')
        if not el or float(el.innerText)==0:
            print 'check totalSent', el.innerText if el else ': err'
            time.sleep(10)
            continue
        else:
            break

    return result

def clickTermsOfUse():
    capturess(sys._getframe().f_code.co_name)
    try:
        print mainDoc.all.tags('a').length
        for i in range(mainDoc.all.tags('a').length):
#            print str(i) + ":" + mainDoc.all.tags('a').item(i).innerText
            if "Terms of Use" in mainDoc.all.tags('a').item(i).innerText:
                mainDoc.all.tags('a').item(i).click()
                time.sleep(25)
    except Exception as err:
        print 'clickTermsOfUse', err

def clickPrivacyPolicy():
    capturess(sys._getframe().f_code.co_name)
    try:
        print mainDoc.all.tags('a').length
        for i in range(mainDoc.all.tags('a').length):
            print str(i) + ":" + mainDoc.all.tags('a').item(i).innerText
            if "Privacy Policy" in mainDoc.all.tags('a').item(i).innerText:
                mainDoc.all.tags('a').item(i).click()
                time.sleep(25)
    except Exception as err:
        print 'clickPrivacyPolicy', err

def clickForgotPassword():
    try:
        print mainDoc.all.tags('a').length
        for i in range(mainDoc.all.tags('a').length):
            print str(i) + ":" + mainDoc.all.tags('a').item(i).innerText
            if "Forgot" in mainDoc.all.tags('a').item(i).innerText:
                mainDoc.all.tags('a').item(i).click()
                time.sleep(40)
    except Exception as err:
        print 'clickForgotPassword', err

def clickCreateAccount():
    try:
        print mainDoc.all.tags('a').length
        for i in range(mainDoc.all.tags('a').length):
            print str(i) + ":" + mainDoc.all.tags('a').item(i).innerText
            if "Create Account" in mainDoc.all.tags('a').item(i).innerText:
                mainDoc.all.tags('a').item(i).click()
                time.sleep(40)
    except Exception as err:
        print 'clickCreateAccount', err

def restartApp():
    capturess(sys._getframe().f_code.co_name)
    VPNClientClose()
    getVPNDispatchDoc()

def getLinks():
    lsLink = []
    getVPNDispatchDoc()
    atag = mainDoc.all.tags('a')
    for i in range(atag.length):
        if atag.item(i).hasAttribute('onclick'):
            lsLink.append(atag.item(i).attributes('onclick').textContent)
    return lsLink

def checkDiagnosisTool(u,p):
    getVPNDispatchDoc()
    VPNClientLogin(u,p)
    waitElement('Diagnosis Tool', 'a')
    findElement('Diagnosis Tool', 'a').click()
    waitElementNot('Checking...', 'SPAN')
    capturess(sys._getframe().f_code.co_name)
    result = True
    el = findElement('fileFolder_check_result').innerText
    if  el != 'SUCCESS':
        print 'failed', el
        result = False
    el = findElement('driver_check_result').innerText
    if  el != 'SUCCESS':
        print 'failed', el
        result = False
    el = findElement('firewall_check_result').innerText
    if  el != 'SUCCESS':
        print 'failed', el
        result = False
    el = findElement('vpnService_check_result').innerText
    if  el != 'SUCCESS':
        print 'failed', el
        result = False

    clickClose()
    return result

def checkRemember(u,p):
    getVPNDispatchDoc()
    if findElement('login_save_password').checked:
        return True
    VPNClientLogin(u,p)
    VPNClientLogout()
    if findElement('login_save_password').checked:
        return True
    else:
        return False

def checkLoginLogout(u,p):
    getVPNDispatchDoc()
    findElement(objectlib['email']).value = u
    for i in range(10):
        capturess(sys._getframe().f_code.co_name)
        if VPNClientLogin(u,p) and VPNClientLogout():
            continue
        return False
    return True

def checkLoginWithWrongAccount(u,p):
    getVPNDispatchDoc()
    findElement(objectlib['email']).value = 'test1234@testtest.com'
    findElement(objectlib['password']).value = p
    findElement('Connect', 'button').click()
    time.sleep(60)
    capturess(sys._getframe().f_code.co_name)
    if not findElement('login_fail_tip'):
        return False
    findElement(objectlib['email']).value = u
    findElement(objectlib['password']).value = 'test1234@test1234'
    findElement('Connect', 'button').click()
    time.sleep(60)
    capturess(sys._getframe().f_code.co_name)
    if not findElement('login_fail_tip'):
        return False
    return True

def setGroupFavorite(toFav):
    # 2 item in list, 0 is fav, 1 is unfav
    rows = mainDocDom.getElementsByClassName('row')
    groups = mainDocDom.getElementsByClassName('icon-favorite')
    capturess(sys._getframe().f_code.co_name)
    groupCount = rows[1].childNodes.length
    if toFav:
        groupCount = rows[1].childNodes.length
    else:
        groupCount = rows[0].childNodes.length
    for i in range(groupCount):
        groups[groupCount-1-i].click()
        time.sleep(2)
    capturess(sys._getframe().f_code.co_name)

def checkFavorite(u, p):
    getVPNDispatchDoc()
    VPNClientLogin(u,p)
    setGroupFavorite(1)
    VPNClientLogout()

    result = False
    if VPNClientLogin(u,p):
        result = True
        setGroupFavorite(0)
        VPNClientLogout()
    return result

def checkUpdates(u, p):
    getVPNDispatchDoc()
    VPNClientLogin(u,p)
    result = VPNClientCheckUpdate()
    VPNClientLogout()
    return result

def returnValue(code):
    print time.asctime(), 'returnValue'
    VPNClientClose()
    if code == 0:
        print 'tcispass'
    else:
        print 'tcisfail'
    sys.exit(code)

class Logger(object):
    def __init__(self, filename="vpnclient.log"):
        self.terminal = sys.stdout
        self.log = open(filename, "a")

    def write(self, message):
        self.terminal.write(message)
        self.log.write(message)  

sys.stdout = Logger("%s.txt"%time.strftime("%Y%m%d-%H%M%S"))
print 'App started'
if len(sys.argv) > 1:
    # do login/logout
    if sys.argv[1] == '1':
        assert(len(sys.argv)>3)
        restartApp()
        if not VPNClientLogin(sys.argv[2], sys.argv[3]):
            returnValue(1)
        if not VPNClientLogout():
            returnValue(1)
    # do quit
    if sys.argv[1] == '2':
        returnValue(0)
    # do check policy&term
    if sys.argv[1] == '3':
        restartApp()
        foundterm = False
        foundpolicy = False
        for line in getLinks():
            if line.lower().find('policy') != -1:
                print time.asctime(), 'found policy'
                foundpolicy = True
                continue
            if line.lower().find('terms') != -1:
                print time.asctime(), 'found terms'
                foundterm = True
                continue
        if foundterm and foundpolicy:
            returnValue(0)
        else:
            returnValue(1)
    # check to connect a group
    if sys.argv[1] == '4':
        assert(len(sys.argv)>4)
        getVPNDispatchDoc()
        if not VPNClientLogin(sys.argv[2], sys.argv[3]):
            returnValue(1)
        bRet = 1
        if ClientConnectToGroup(sys.argv[4]):
            bRet = 0
        ClientDisconnect()
        returnValue(bRet)
    # check link status
    if sys.argv[1] == '5':
        assert(len(sys.argv)>4)
        getVPNDispatchDoc()
        if not VPNClientLogin(sys.argv[2], sys.argv[3]):
            returnValue(1)
        bRet = 1
        if ClientConnectToGroup(sys.argv[4]):
            time.sleep(120)# have some traffic
            if VPNClientCheckLinkInfo():
                bRet = 0
        ClientDisconnect()
        returnValue(bRet)
    # check create account
    if sys.argv[1] == '6':
        restartApp()
        for line in getLinks():
            if line.lower().find('signup_url') != -1:
                returnValue(0)
        returnValue(1)
    # check forgot account
    if sys.argv[1] == '7':
        restartApp()
        for line in getLinks():
            if line.lower().find('accounts.netgear.com') != -1:
                returnValue(0)
        returnValue(1)
    # check copyright
    if sys.argv[1] == '8':
        getVPNDispatchDoc()
        if not VPNClientLogin(sys.argv[2], sys.argv[3]):
            returnValue(1)
        if not VPNClientCheckAbout():
            returnValue(1)
    # call args like func call
    try:
        # with user/pass
        if not eval(sys.argv[1])(sys.argv[2], sys.argv[3]):
            returnValue(1)
    except Exception as err:
        print sys.argv[1], err
        returnValue(1)
    # default to pass
    returnValue(0)
    

