'''
'''

import os, sys, re, time
import shutil, hashlib

if len(sys.argv) == 1:
    print 'please give a BR/Orbi .img file'
    sys.exit(1)
else:
    deviceFM = sys.argv[1]

# verify file name
isOrbi=False
if deviceFM.lower().find('br') == -1:
    isOrbi = True
    assert(deviceFM.lower().find('srr') != -1)
else:
    assert(deviceFM.lower().find('br') != -1)
try:
    deviceVersion = re.findall('((\.\d+){3})', deviceFM)[0][0]
except:
    print 'Cannot find version info'
    sys.exit(1)

# make new file name
devVerPrefix = deviceFM[:deviceFM.find('V')+1]
devVerSuffix = deviceFM[-4:]
new_DeviceDirName = devVerPrefix+'99%s' % deviceVersion     # BR100-V99.36.11.6
new_DeviceFileName = new_DeviceDirName + devVerSuffix       # BR100-V99.36.11.6.img

devMD5 = hashlib.md5(open(deviceFM,'rb').read()).hexdigest()
if not isOrbi:
    verFile = '''file=%s
md5=%s
size=20000
''' % (new_DeviceFileName, devMD5)
else:
    devMD5 = devMD5.upper()
    verFile = '''[Major2]
file=%s
md5=%s
size=30000000''' % (new_DeviceFileName, devMD5)
print verFile

# ready to build folder
if not os.path.isdir(new_DeviceDirName):
    if not isOrbi:
        os.mkdir(new_DeviceDirName)
    else:
        new_DeviceDirName = r'%s\srr60\ww' % new_DeviceDirName
        os.system('mkdir %s' % new_DeviceDirName)

curpath = os.getcwd()
os.chdir(new_DeviceDirName)
if isOrbi:
    with open('fileinfo.txt', 'w') as fp:
        fp.write(verFile)
else:
    with open('fileinfo_insight.txt', 'w') as fp:
        fp.write(verFile)

os.chdir(curpath)
shutil.copy(deviceFM, r'%s/%s'%(new_DeviceDirName, new_DeviceFileName))
print 'Done'