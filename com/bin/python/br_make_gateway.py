#coding:utf-8

import os, sys, re, time
import subprocess


ipFind = '192.168.123.1'

'''
netsh int ipv4 show addr
Configuration for interface "net123.1"
IP Address:                           192.168.123.7

netsh int ipv4 show interfaces
21          20        1500  connected     net123.1
'''

def getoutput(cmd):
    print 'run:', cmd
    return subprocess.check_output(cmd, shell=True)

if len(sys.argv)>1:
    ipFind = sys.argv[1]

ipprefix = ipFind[:ipFind.rfind('.')]
ipnet = ipprefix + '.0'
ipgw = ipprefix + '.1'
addr = getoutput('netsh int ipv4 show addr')
intname = None
for line in addr.split('\n'):
    if line.find(' "') != -1:
        print line
        intname = line
        continue
    if line.find(ipprefix)!=-1:
        break
    
assert(intname)
devname = intname.strip().split('"')[-2]
interf = getoutput('netsh int ipv4 show interfaces')
intindex = None
for line in interf.split('\n'):
    if line.find(devname)!=-1:
        print line
        intindex = line
        break

assert(intindex)
devindex = intindex.strip().split()[0]
print getoutput('route delete %s' % ipnet)
print getoutput('route add %s mask 255.255.255.0 %s if %s' %
                (ipnet, ipgw, devindex))

