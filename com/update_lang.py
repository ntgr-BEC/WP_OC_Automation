

import os, sys
import xml.etree.ElementTree as ET

wp_file = os.path.join(os.path.dirname(os.path.abspath(__file__)), r'src\test\resources\config_webportal.xml')
xml_tree = ET.parse(wp_file)
xml_root = xml_tree.getroot()
log_root = xml_root.find('loginInfo')

getlang = xml_root.find('BrowserLanguage').text
geturl = log_root.find('serverUrl').text
#geturl = "https://insight.netgear.com/"
os.chdir(r'src\test\resources')

def getjson(lan):
    os.system('wget --no-check-certificate %s'%geturl + '/assets/api/%s.json'%lan + ' -O %s.json'%lan)

getjson('english')
getjson('german')
getjson('japanese')
getjson('chinese')

print 'done'
