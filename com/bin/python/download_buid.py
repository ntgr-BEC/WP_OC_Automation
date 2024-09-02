
import os, sys
import argparse, ftplib

parser = argparse.ArgumentParser(description='A tool to download file via ftp')
parser.add_argument('-i', default='10.40.1.183', help="ftp server address")
parser.add_argument('-p', type=int, default=2100, help="sever port")
parser.add_argument('-f', required=True, help="download this file")
parser.add_argument('-d', required=True, help="download this file to folder")
parser.add_argument('-l', action='store_true', help="download last file")
args = parser.parse_args()

if args.l:
    print 'TODO'

if not os.path.isdir(args.d):
    os.mkdir(args.d)
    
os.chdir(args.d)
if os.path.isfile(args.f):
    print "file was download", args.f
else:
    print 'start to download', args.f
    ftp = ftplib.FTP()
    ftp.connect(args.i, args.p)
    ftp.login()
    ftp.retrlines('LIST')
    f = open(args.f, 'wb')
    ftp.retrbinary('RETR '+args.f, f.write)
    f.close()
    ftp.close()

print 'download file', args.f
