'''
'''

import os, sys, re, time
import subprocess


tarbin = '../tool/tar.exe'
if len(sys.argv) == 1:
    print 'please give a AP .tar file'
    sys.exit(1)
else:
    apfm = sys.argv[1]

new_apfm = 'NEW_%s' % apfm
output = subprocess.check_output('%s xvf %s'% (tarbin, apfm))
print output

f_version = 'version'
all_file = [f_version, 'wac5xx-ubifs-root.img', 'wac5xx-ubifs-root.md5sum', 'metadata.txt']
if output.find(f_version) == -1:
    print 'error ap image'
    sys.exit(1)

with open(f_version) as fp:
    ap_ver = fp.read()

print 'ap version: "%s"' % ap_ver.strip()
new_ap_ver = '%s_V99.9.9.9\r' % re.findall('(.*)_', ap_ver)[0]
print 'changed ap version: "%s"' % new_ap_ver.strip()

with open(f_version, 'w') as fp:
    fp.write(new_ap_ver)

output = subprocess.check_output('%s cvf %s %s'%(tarbin, new_apfm, ' '.join(all_file)))
print output
os.system('del %s' % ' '.join(all_file))
print 'New Firmware File: "%s"' % new_apfm
