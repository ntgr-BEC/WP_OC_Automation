#coding:utf-8

import os, sys, time
import atomac

from AppKit import NSPNGFileType, NSMakeRect
from Quartz.CoreGraphics import CIImage
from Quartz.CoreGraphics import NSBitmapImageRep
from Quartz.CoreGraphics import CGWindowListCreateImage, CGRectInfinite


os.system('mkdir -p tmp')
def capturescreenshot():
    screenshot = CGWindowListCreateImage(CGRectInfinite, 1, 0, 0)
    image = CIImage.imageWithCGImage_(screenshot)
    bitmapRep = NSBitmapImageRep.alloc().initWithCIImage_(image)
    blob = bitmapRep.representationUsingType_properties_(NSPNGFileType, None)
    tmpFile = time.strftime("tmp/ss_%Y%m%d_%H%M%S.png")
    blob.writeToFile_atomically_(tmpFile, False)
    print 'capture screenshot to:', tmpFile


def getVPNDispatchDoc():
    global doc
    try:
        auto = atomac.getAppRefByBundleId('com.netgear.InstantVPN')
    except:
        atomac.launchAppByBundleId('com.netgear.InstantVPN')
        time.sleep(30)
        auto = atomac.getAppRefByBundleId('com.netgear.InstantVPN')
        
    #auto.sendKey("aaa")
    print auto
    win = auto.windows()
    print len(win)

    try:
        doc = auto.windows()[0]
        return doc
    except:
        capturescreenshot()
        return None

doc = None
doc = getVPNDispatchDoc()



def VPNClientLogin(name,password):
    doc = getVPNDispatchDoc()
    if not doc:
        return False
    for j in range(0, 2):
        capturescreenshot()
        if doc.findFirstR(AXRole="AXTextField", AXDOMIdentifier='inputEmail'):
            user = doc.findFirstR(AXRole="AXTextField", AXDOMIdentifier='inputEmail')

            user.setString('AXValue', name)
            userpassword = doc.findFirstR(AXRole="AXTextField", AXDOMIdentifier='inputPassword')
            userpassword.setString('AXValue', password)
            time.sleep(1)
            doc.findFirstR(AXRole="AXButton", AXTitle='Connect').Press()
            time.sleep(60)

    if doc.findFirstR(AXRole="AXTextField", AXDOMIdentifier='inputEmail'):
        return False
    else:
        return True

def VPNClientLogout():
    doc = getVPNDispatchDoc()
    if not doc:
        return False
    capturescreenshot()
    if doc.findFirstR(AXRole="AXButton", AXTitle='Cancel'):
        print 'see cancel button for logout'
        doc.findFirstR(AXRole="AXButton", AXTitle='Cancel').Press()
        time.sleep(10)
    if doc.findFirstR(AXRole="AXButton", AXTitle='Return'):
        print 'see return button for logout'
        doc.findFirstR(AXRole="AXButton", AXTitle='Return').Press()
        time.sleep(10)
    if doc.findFirstR(AXRole="AXButton", AXTitle='Refresh'):
        print 'see refresh button for logout'
        doc.findFirstR(AXRole="AXButton", AXTitle='Refresh').Press()
        time.sleep(10)
        
    if doc.findFirstR(AXRole="AXTextField", AXDOMIdentifier='inputEmail'):
        print 'return as we use login page'
        return True
    else:
        texts = doc.findAllR(AXRole="AXStaticText")
        for i in range(0, len(texts)):
#            print str(i)
#            print texts[i].__getattr__('AXValue')
            if '.com' in texts[i].__getattr__('AXValue'):
                print 'try to click one of .com link'
                texts[i].Press()
                break
        time.sleep(10)

        texts = doc.findAllR(AXRole="AXStaticText")
        for i in range(0, len(texts)):
#            print str(i)
#            print texts[i].__getattr__('AXValue')
            if 'Logout' in texts[i].__getattr__('AXValue'):
                print 'try to click one of logout link'
                texts[i].Press()
                break
        time.sleep(10)

    if doc.findFirstR(AXRole="AXTextField", AXDOMIdentifier='inputEmail'):
        return True
    else:
        return False


def VPNClientClose():
    time.sleep(1)

def ClientConnectToGroup(groupname):
    doc = getVPNDispatchDoc()
    if not doc:
        return False
    for j in range(0,2):
        capturescreenshot()
        texts = doc.findAllR(AXRole="AXStaticText")
        print texts

        for i in range(0, len(texts)):
            print str(i)
            print texts[i].__getattr__('AXValue')
            if groupname in texts[i].__getattr__('AXValue'):
                your_button = texts[i]
                button_position = your_button.AXPosition
                button_size = your_button.AXSize
                clickpoint = ((button_position[0] + button_size[0] / 2), (button_position[1] + button_size[1] / 2))
    #    		your_button.clickMouseButtonLeft(clickpoint)
                your_button.doubleClickMouse(clickpoint)
                time.sleep(50)
                break
        time.sleep(10)
        if doc.findFirstR(AXRole="AXButton", AXTitle='Refresh'):
            print 'see refresh button for connect to group'
            doc.findFirstR(AXRole="AXButton", AXTitle='Refresh').Press()
            time.sleep(30)
            
    time.sleep(10)
    return ClientConnectOrNot()

def GetConnectedInfo():
    print doc.all.tags('span').length
    for i in range(doc.all.tags('span').length):
        print str(i)+":"+doc.all.tags('span').item(i).innerHTML

def ClientDisconnect():
    if doc.findFirstR(AXRole="AXButton",AXTitle='Disconnect'):
        doc.findFirstR(AXRole="AXButton", AXTitle='Disconnect').Press()
        time.sleep(20)
        return True
    else:
        return False

def ClientConnectOrNot():
    capturescreenshot()
    if doc.findFirstR(AXRole="AXButton",AXTitle='Disconnect'):
        return True
    else:
        return False

def clickClose():
    try:
        if doc.findFirstR(AXRole="AXButton", AXTitle='Close'):
            doc.findFirstR(AXRole="AXButton", AXTitle='Close').Press()
            time.sleep(10)
    except Exception as err:
        print err

def VPNClientCheckUpdate():
    result = False

    texts = doc.findAllR(AXRole="AXStaticText")
    for i in range(0, len(texts)):
        if '.com' in texts[i].__getattr__('AXValue'):
            texts[i].Press()
            break
    time.sleep(3)

    texts = doc.findAllR(AXRole="AXStaticText")
    for i in range(0, len(texts)):
        #            print str(i)
        #            print texts[i].__getattr__('AXValue')
        if 'Check for Updates' in texts[i].__getattr__('AXValue'):
            texts[i].Press()
            break

    time.sleep(10)

    texts = doc.findAllR(AXRole="AXStaticText")
    for i in range(0, len(texts)):
        if 'have the latest version' in texts[i].__getattr__('AXValue'):
            result = True
            break

    if doc.findFirstR(AXRole="AXButton", AXDOMIdentifier='btn-update'):
        doc.findFirstR(AXRole="AXButton", AXDOMIdentifier='btn-update').Press()
        time.sleep(60)
        result = True


    clickClose()
    print result
    return result

def VPNClientCheckLinkInfo():
    result = True

    texts = doc.findAllR(AXRole="AXStaticText")

    current = ""
    for i in range(0, len(texts)):
        print str(i) + texts[i].__getattr__('AXValue')
        current = current + texts[i].__getattr__('AXValue')
    print current
    if ("Latency" not in current) or ("Duration" not in current) or ("Bytes Received" not in current):
        print "error 1"
        result = False
    if ("192." not in current) or ("ms" not in current) or ("00:" not in current):
        print "error 2"
        result = False
    if ("Bytes Sent" not in current) or ("Received Speed" not in current) or ("Sent Speed" not in current):
        print "error 3"
        result = False
    if ("MB" not in current) or ("kb/s" not in current) or ("Connected" not in current):
        print "error 4"
        result = False
    print result
    return result

def clickTermsOfUse():
    try:
        texts = doc.findAllR(AXRole="AXStaticText")
        print texts

        for i in range(0, len(texts)):
            print texts[i].__getattr__('AXValue')
            if 'Terms of Use' in texts[i].__getattr__('AXValue'):
                texts[i].Press()
                break
        time.sleep(10)
    except Exception as err:
        print err

def clickPrivacyPolicy():
    try:
        texts = doc.findAllR(AXRole="AXStaticText")
        print texts

        for i in range(0, len(texts)):
            print texts[i].__getattr__('AXValue')
            if 'Privacy Policy' in texts[i].__getattr__('AXValue'):
                texts[i].Press()
                break
        time.sleep(10)
    except Exception as err:
        print err

def clickForgotPassword():
    try:
        texts = doc.findAllR(AXRole="AXStaticText")
        print texts

        for i in range(0, len(texts)):
            print texts[i].__getattr__('AXValue')
            if 'Forgot Account' in texts[i].__getattr__('AXValue'):
                texts[i].Press()
                break
        time.sleep(10)
    except Exception as err:
        print err

def clickCreateAccount():
    try:
        texts = doc.findAllR(AXRole="AXStaticText")
        print texts

        for i in range(0, len(texts)):
            print texts[i].__getattr__('AXValue')
            if 'Create Account' in texts[i].__getattr__('AXValue'):
                texts[i].Press()
                break
        time.sleep(10)
    except Exception as err:
        print err

def checkText(value):
    result = False
    try:
        texts = doc.findAllR(AXRole="AXStaticText")
        print texts

        for i in range(0, len(texts)):
            print texts[i].__getattr__('AXValue')
            if value in texts[i].__getattr__('AXValue'):

                result = True
                break
        time.sleep(2)
    except Exception as err:
        print err
    return result
