

import os, sys, time, argparse, subprocess, requests
import xml.etree.ElementTree as ET

parser = argparse.ArgumentParser(description='Update device firwmare.')
parser.add_argument('-t', default='sw', help="device type(sw, ap, br, ob)")

args = parser.parse_args()

python_bin = os.path.dirname(os.path.abspath(__file__))
http_bin = os.path.join(python_bin, r'..\miniweb')
http_docs = os.path.join(http_bin, 'htdocs')

wp_file = os.path.join(python_bin, r'..\..\src\test\resources\config_webportal.xml')
wp_xml_tree = ET.parse(wp_file)
wp_xml_root = wp_xml_tree.getroot()
dut_file = os.path.join(python_bin, r'..\..\src\test\resources\config_dut.xml')
dut_xml_tree = ET.parse(dut_file)
dut_xml_root = dut_xml_tree.getroot()

os.chdir(python_bin)
with open('version_info.txt') as fp:
	lines = fp.readlines()

for line in lines:
	dut_name, fw_ver = line.strip().split(':')
	dut_type = 0 # 0-sw, 1-ap, 2-br, 3-ob
	if args.t.find('ap') != -1:
		dut_name = 'AP1'
		dut_type = 1
	if args.t.find('br') != -1:
		dut_name = 'BR1'
		dut_type = 2
	if args.t.find('ob') != -1:
		dut_name = 'ORBI1'
		dut_type = 3

	dut_passwd = wp_xml_root.find('.//loginDevicePassword').text
	dut_sec = wp_xml_root.find(dut_name).text
	dut_ip = dut_xml_root.find('./DUT_List/%s/Ip_Address'%dut_sec).text
	print 'will update fw to device:', dut_sec, dut_ip, dut_passwd

	os.chdir(python_bin)
	version_file = 'version.txt'
	if not os.path.isfile(version_file):
		sys.exit(1)
		
	assert(fw_ver)
	dut_net = '.'.join(dut_ip.split('.')[:-1])
	ipconfig = subprocess.check_output('ipconfig.exe')
	http_ip = False
	for line in ipconfig.split('\n'):
		if line.find(dut_net) != -1:
			print line
			http_ip = line.split()[-1]
			break

	assert(http_ip)
	ses = requests.session()
	ses.verify = False
	ses.auth = ('admin', dut_passwd)
	headers = {'content-type':'application/json'}
	if dut_type == 0:
		dut_url = 'https://%s:8443/api/v1/device_fw_update'%dut_ip
		dut_json = {'deviceFirmwareUpdate':{'rebootNow':True,'fwUrl':'http://%s/%s'%(http_ip, fw_ver),'fwDownloadTimeout':600}}
		print(dut_url)
		print(dut_json)
	elif dut_type == 1:
		raise 'not support'
	else:
		raise 'not support'


	print dut_url, dut_json
	res = ses.post(dut_url, json=dut_json, headers=headers)
	time.sleep(300)

	if dut_name == 'SW2':
		os.chdir(http_docs)
		os.remove(fw_ver)
	
	print 'done'
