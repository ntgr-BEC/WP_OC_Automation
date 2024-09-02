'''
download files to local http server

2019-06-18 22:08	build is built
2019-06-19 13:10:00 GC110_510_v19.25.11.3.stk
2019-06-19 13:12:00 GC110_510_v19.25.53.3.stk

CD--ServerAndBuildServer:
    0-Pri, 1- Aux, 2-Maint, 3-Load, 4-Beta, 5-Prod, 6-Demo and D is 0-Dev, 1-QA, 2- Beta, 3-Prod, 4-Demo

'''


import os, sys, time, ftplib, argparse, subprocess
from dateutil import parser as dparser

parser = argparse.ArgumentParser(description='A tool to download file for clients.')
parser.add_argument('-i', default='10.40.1.184', help="The ftp server ip")
parser.add_argument('-u', default='switch', help="ftp user")
parser.add_argument('-p', default='switch#1', help="The user passwd")
parser.add_argument('-d', default='/Insight_Agent/Switch_BRCM/U200', help="file path to download")
parser.add_argument('-t', default='11', help="build type to check")
args = parser.parse_args()

def logmsg (msg):
    msg = "%s %s" % (time.strftime("%y%m%d-%H:%M:%S"), msg)
    print msg

python_bin = os.path.dirname(os.path.abspath(__file__))
http_bin = os.path.join(python_bin, r'..\miniweb')
http_docs = os.path.join(http_bin, 'htdocs')

version_lines = []
for sw, path in dict(zip(['SW1', 'SW2'], args.d.split(','))).items():
	os.chdir(http_docs)
	logmsg ("Connect to: %s" % args.i)
	ftp = ftplib.FTP()
	ftp.connect(args.i, 21)
	logmsg ("Login with: %s" % args.u)
	ftp.login(args.u, args.p)
	logmsg ("cwd to: %s" % path)
	ftp.cwd(path)

	#TBD: 30m to wait a file
	foundNew = False
	for i in range(30):
		time.sleep(60)
		logmsg ("List files to check")
		lines = []
		ftp.retrlines('LIST', lines.append)

		timebackup = dparser.parse('01 01')
		for line in lines:
			try:
				versionType = line.split('.')[-3]
				if (versionType == args.t or versionType == args.t[-1]) and (line.find('.stk') != -1 or line.find('.img') != -1 or line.find('.tar') != -1 or line.find('.bix') != -1):
					tokens = line.split()
					timenew = dparser.parse(' '.join(line.split()[-4:-1]))
					fb_size = line.split()[-5]
					print timenew, line.split()[-1], fb_size
					if timenew > timebackup:
						timebackup = timenew
						fb = line.split()[-1]
			except IndexError:
				pass

		print 'checking on file:', fb
		if not os.path.isfile(fb):
			for i in range(30): # 10m for file uploading done
				time.sleep(20)
				lines = []
				ftp.retrlines('LIST %s' % fb, lines.append)
				fb_size_new = lines[0].split()[-5]
				print fb, fb_size, fb_size_new
				if fb_size_new == fb_size:
					break
				fb_size = fb_size_new
			try:
				logmsg('Downloading fw now: %s'%fb)
				f = open(fb, 'wb')
				ftp.retrbinary('RETR ' + fb , f.write , 1024)
				f.close()
				foundNew = True
				break
			except:
				logmsg ("Downloading failed.")
				f.close()
				ftp.close()
				if os.path.exists(fb):
					os.remove(fb)
		else:
			logmsg('sleep for next poll')

	ftp.close()
	os.chdir(python_bin)
	version_file = 'version.txt'
	if not foundNew:
		logmsg("No new build found, skip it")
		try:
			os.remove(version_file)
		except:
			pass
		sys.exit(1)
	
	if sw == 'SW1':
		with open(version_file, 'w') as fp:
			fp.write(fb)
	
	version_lines.append('%s:%s\n' % (sw, fb))

	logmsg("Restart http server")
	os.system('taskkill /f /im miniweb.exe')
	time.sleep(4)
	os.chdir(http_bin)
	DETACHED_PROCESS = 8
	subprocess.Popen("miniweb.exe -p 80 -v -l miniweb.log", shell=True, creationflags=DETACHED_PROCESS, close_fds=True)
	time.sleep(4)
	logmsg("Done")

os.chdir(python_bin)
with open('version_info.txt', 'w') as fp:
	fp.writelines(version_lines)