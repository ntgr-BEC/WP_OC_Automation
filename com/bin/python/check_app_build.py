
import os, sys, re, time
import argparse, paramiko

parser = argparse.ArgumentParser(description='A tool to re-create location & devices')
parser.add_argument('-t', default='a', help="check build type, andriod(a), iOs(i), webportal(w)")
parser.add_argument('-b', default='buildinfo.txt', help="write version to this file")
parser.add_argument('-d', action='store_true', help="debug on")
parser.add_argument('-n', action='store_true', help="just download the lastet one")
parser.add_argument('-l', action='store_true', help="run without exit")
args = parser.parse_args()

ssh_host = '172.29.0.123'
ppk_file = 'insightarlo_pri.pem'
ssh_user = 'InsightBuild'
passphrase = 'Protect.Me'
path_daily = '/efs/updates.netgear.com/insighttest/Nightly'
path_daily_and = '%s/android' % path_daily
path_daily_ios = '%s/ios' % path_daily
path_daily_fin = path_daily_and
if args.t.lower() == 'i':
    path_daily_fin = path_daily_ios

log_filename = os.path.basename(sys.argv[0])+'-%s.log'%args.t
def logmsg (msg):
    msg = "%s %s\n" % (time.strftime("%y%m%d-%H:%M:%S"), msg)
    print msg
    f = open (log_filename, 'a')
    f.write (msg)
    f.close()
    
if args.d:
    path_daily_fin = '/tmp'
    
def sleep_check_update():
    print '*',
    if args.d:
        time.sleep(2)
    else:
        time.sleep(30)

def sleep_check_newfile():
    print '#',
    if args.d:
        time.sleep(2)
    else:
        time.sleep(120)

builds_info = []
builds_info_size = {}
def init_dir():
    bis = sf.listdir(path_daily_fin)
    for bi in bis:
        print 'adding file:', bi
        builds_info.append(bi)
        builds_info_size[bi] = sf.lstat(bi).st_size

def check_newbuid(fn):
    if not fn in builds_info:
        if path_daily_fin == path_daily_and:
            return True
        else:
            # only download ent not dev for ios
            if fn.find('Ent') != -1:
                return True
    return False

def make_buildinfo(fn):
    #insight_5.1_062818_dev_nightly_50ea423.apk
    #insight_5.0_062118_dev_nightly_56deaac47.ipa
    v = re.findall('ht_(.*)_dev', fn)
    if v:
        with open(args.b, 'w') as f:
            f.write("build_ver=%s\n"%v[0])
            f.write("build_name=%s\n"%fn)
    else:
        sys.exit(1)

def download_file(fn):
    logmsg ('try to download file: ' + fn)
    if not args.d:
        while True:
            sleep_check_update()
            new_size = sf.lstat(fn).st_size
            if builds_info_size[fn] == new_size:
                break
            else:
                print '.',
                builds_info_size[fn] = new_size
    logmsg ('download file: ' + fn)
    sf.get(fn, fn)
    logmsg ('downloaded file: ' + fn)
    make_buildinfo(fn)

logmsg ('connect to: '  + ssh_host)
pkey_file = paramiko.pkey.PKey.from_private_key_file(ppk_file, passphrase)
c = paramiko.SSHClient()
c.set_missing_host_key_policy(paramiko.AutoAddPolicy())
c.connect(ssh_host, 22, ssh_user, key_filename=ppk_file, passphrase=passphrase)
sf = c.open_sftp()
sf.chdir(path_daily_fin)
if args.d:
    sf.chdir('/tmp')
start_new = True
old_check_times = 0
while True:
    if start_new:
        start_new = False
        init_dir()
        sleep_check_newfile()
        continue
    
    new_builds_info = sf.listdir(path_daily_fin)
    # exit if there was new build
    if new_builds_info != builds_info:
        old_check_times = 1
        for bi in new_builds_info:
            print 'checking new file', bi
            if check_newbuid(bi):
                builds_info.append(bi)
                builds_info_size[bi] = sf.lstat(bi).st_size
                download_file(bi)
                if not args.l:
                    sys.exit(0)
        sleep_check_newfile()
        continue
    else:
        if args.d:
            download_file('insight_5.1_062318_dev_nightly_24b9e81.apk')
            sys.exit(0)

    # exit if build was uploading
    if old_check_times > 4:
        continue
    for bi in builds_info:
        print 'checking old file:', bi
        if builds_info_size[bi] != sf.lstat(bi).st_size:
            download_file(bi)
            if not args.l:
                sys.exit(0)
    old_check_times += 1
    sleep_check_newfile()

print 'done'
