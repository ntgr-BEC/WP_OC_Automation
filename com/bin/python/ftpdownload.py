
import os, sys, time, ftplib, argparse

parser = argparse.ArgumentParser(description='A tool to download file for clients.')
parser.add_argument('-i', default='', help="The ftp server ip")
parser.add_argument('-u', default='test', help="ftp user: test")
parser.add_argument('-p', default='test', help="The user passwd: test")
parser.add_argument('-f', default='1.avi', help="file name to be download, no space")
parser.add_argument('-l', default='ftpdownloadres.txt', help="The result file")
parser.add_argument('-s', action='store_true', help="By default to start download, otherwise to check result")
args = parser.parse_args()

def logmsg (msg):
    msg = "%s %s" % (time.strftime("%y%m%d-%H:%M:%S"), msg)
    print msg

def writeres():
    f = open(args.l, 'w')
    f.write(result)
    f.close()
    logmsg ("write: %s" % result)

result = "0:0:0"
if args.s:
    if not args.i:
        writeres()
        sys.exit(0)
    if os.path.exists(args.l):
        logmsg ("remove log file first: %s" % args.l)
        os.remove(args.l)

    fb = os.path.basename(args.f)
    if os.path.exists(fb):
        logmsg ("remove local file first: %s" % fb)
        os.remove(fb)

    start = time.time()
    logmsg ("Connect to: %s" % args.i)
    ftp = ftplib.FTP()
    ftp.connect(args.i, 21)
    logmsg ("Login with: %s" % args.u)
    ftp.login(args.u, args.p)
    fd = os.path.dirname(args.f)
    if fd:
        logmsg ("cwd to: %s" % fd)
        ftp.cwd(fd)

    logmsg ("Downloading : %s" % fb)
    try:
        f = open(fb, 'wb')
        ftp.retrbinary('RETR ' + fb , f.write , 1024)
    except:
        logmsg ("Downloading failed.")
        f.close()
        ftp.close()
        writeres ()
        sys.exit(0)
    f.close()
    ftp.close()
    downloadtime = time.time() - start
    size = 0
    if os.path.exists(fb):
        size=os.path.getsize(fb)
        result = "1:%d:%d" % (size, downloadtime)
    else:
        result = "0:0:%d" % downloadtime
        
    if os.path.exists(fb):
        logmsg ("remove file after download: %s" % fb)
        os.remove(fb)
    writeres ()
else:
    logmsg ("check if log file exists: %s" % args.l)
    while not os.path.exists(args.l):
        time.sleep(4)
    time.sleep(2)
    logmsg ("check log file content: %s" % args.l)
    f = open(args.l)
    res = f.read()
    f.close()
    logmsg ("result: %s" % res)
    