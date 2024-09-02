

import os, sys, argparse
import xml.etree.ElementTree as ET

parser = argparse.ArgumentParser(description='Update wp version to firmware version.')
parser.add_argument('-i', help="sw1")
parser.add_argument('-j', help="sw2")
parser.add_argument('-k', help="location name")

args = parser.parse_args()

python_bin = os.path.dirname(os.path.abspath(__file__))
wp_file = os.path.join(python_bin, r'..\..\src\test\resources\config_webportal.xml')
xml_tree = ET.parse(wp_file)
xml_root = xml_tree.getroot()

def setVal (a, b, c):
    if a:
        print 'set', c, 'to', a
        b.find(c).text = a

http_bin = os.path.join(python_bin, r'..\miniweb')
http_docs = os.path.join(http_bin, 'htdocs')
os.chdir(http_docs)

os.chdir(python_bin)
version_file = 'version.txt'
if not os.path.isfile(version_file):
    sys.exit(1)
with open (version_file) as fp:
    fw_ver = fp.read()

setVal(fw_ver, xml_root, 'WebPortalVersion')
setVal(args.i, xml_root, 'SW1')
setVal(args.j, xml_root, 'SW2')
setVal(args.k, xml_root, 'location1')
xml_tree.write(wp_file)

print 'done'
