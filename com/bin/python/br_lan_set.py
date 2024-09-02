

import os, sys, re, time, argparse, ast
import urllib, urllib2, json, requests
from bs4 import BeautifulSoup

user = 'admin'
passwd = 'Netgear1@'
parser = argparse.ArgumentParser(description='A tool to update ip lan settings')
parser.add_argument('-u', default=user, help="The user name")
parser.add_argument('-p', default=passwd, help="The user password")
parser.add_argument('-i', default='192.168.124.11', help="DHCP Start")
parser.add_argument('-j', default='192.168.124.21', help="DHCP End")
parser.add_argument('-k', default='192.168.123.1', help="BR device ip")
parser.add_argument('-t', default='0', help="BR Mode: 0-br500, 1-br100")
args = parser.parse_args()

def getnet(ip):
    return ip[:ip.rfind('.')]

def getlastipseg(ip):
    return ip[ip.rfind('.')+1:]


brmode = int(args.t)
gw = getnet(args.i)+'.1'
host_url_prefix = 'https://%s/' % args.k

s = requests.Session()
s.verify = False
s.auth = (args.u, args.p)

## do login
if 0 == brmode:
    login_host_url = host_url_prefix + 'change_user.html'
    soup = BeautifulSoup(s.get(login_host_url).text)
    if soup.text.find('401 Authorization') != -1:
        print ("relogin")
        soup = BeautifulSoup(s.get(login_host_url).text)

    ## get old lan
    login_host_url = host_url_prefix + 'api/setup/lan_info.htm'
    soup = BeautifulSoup(s.get(login_host_url).text)
    ts = dev_name = dhcp_start_old = None
    for lines in soup.findAll('script'):
        for line in lines.text.split('\n'):
            if line.find('ts_lan') != -1:
                ts = line.strip().split("'")[-2]
                print 'ts', ts
                continue
            if line.find('device_name') != -1:
                dev_name = line.strip().split("'")[-2]
                print 'dev_name', dev_name
                continue
            if line.find('var d_start') != -1:
                dhcp_start_old = line.strip().split("'")[-2]
                print 'dhcp_start_old', dhcp_start_old
                continue
            if line.find('var d_end') != -1:
                dhcp_end_old = line.strip().split("'")[-2]
                print 'dhcp_end_old', dhcp_end_old
                continue

    if not (ts and dev_name and dhcp_start_old):
        print soup.text
        sys.exit(1)

    ## modify lan
    entry = {}
    entry['submit_flag'] = 'lan'
    entry['select_edit'] = ''
    entry['select_del'] = ''
    entry['dhcp_server'] = 1
    entry['dhcp_mode'] = 1
    entry['dhcp_start'] = args.i
    entry['dhcp_end'] = args.j
    entry['dmz_ip'] = ''
    entry['bs_trustedip'] = ''
    entry['network'] = getnet(args.i)
    entry['change_network_flag'] = 1
    entry['change_network2_flag'] = 0
    entry['change_ip_flag'] = 1
    entry['lan_ipaddr'] = gw
    entry['lan_subnet'] = '255.255.255.0'
    entry['out_of_range'] = ''
    entry['Trusted_IP_Enable'] = 0
    entry['cfTrusted_IPAddress'] = ''
    entry['dmz_value'] = 0
    entry['dmz_ipaddr'] = ''
    entry['net_leng'] = 3
    entry['hid_array_num'] = 0
    entry['dhcp_start_old'] = dhcp_start_old
    entry['dhcp_end_old'] = dhcp_end_old
    entry['dhcp_pool_tag'] = 1
    entry['device_name'] = dev_name
    entry['sysLANIPAddr1'] = gw.split('.')[0]
    entry['sysLANIPAddr2'] = gw.split('.')[1]
    entry['sysLANIPAddr3'] = gw.split('.')[2]
    entry['sysLANIPAddr4'] = gw.split('.')[3]
    entry['sysLANSubnetMask1'] = 255
    entry['sysLANSubnetMask2'] = 255
    entry['sysLANSubnetMask3'] = 255
    entry['sysLANSubnetMask4'] = 0
    entry['rip_direction'] = 'Both'
    entry['sysRIPVersion'] = 'Disabled'
    entry['sysPoolStartingAddr4'] = getlastipseg(args.i)
    entry['sysPoolFinishAddr4'] = getlastipseg(args.j)
    host_url = host_url_prefix + 'apply.cgi?/LAN_lan.htm timestamp=%s' % ts
    soup = BeautifulSoup(s.post(host_url, data=entry).text)
    print soup.text
elif 1 == brmode:
    # Do login
    postdata = {"method":"set","params":{"userName":args.u,"passWord":args.p}}
    host_url_prefix = 'https://%s/cgi-bin/' % args.k
    login_host_url = host_url_prefix + 'web-cgi/do_login'
    data = ast.literal_eval(BeautifulSoup(s.post(login_host_url, data=postdata).text).text)
    if data['code'] != 0:
        print "Re login"
        time.sleep(2)
        data = ast.literal_eval(BeautifulSoup(s.post(login_host_url, data=postdata).text).text)
        assert(data['code'] == 0)

    print 'logined'
    time.sleep(4)

    # Change Lan
    lan_host_url = host_url_prefix + 'web-cgi/set_lan_config'
    params = {}
    params['lan'] = 'LAN1'
    params['macaddr'] = '00:00:00:00:00:00'
    params['dhcpd_start'] = args.i
    params['rip_version'] = 0
    params['dhcpd_end'] = args.j
    params['rip_enable'] = 0
    params['ipaddr'] = gw
    params['vlanid'] = 1
    params['disabled'] = 0
    params['netmask'] = '255.255.255.0'
    params['dhcpd_enable'] = 1
    params['rip_direction'] = 0
    params['desc'] = ''
    postdata = {"method":"set","params":params}
    data = ast.literal_eval(BeautifulSoup(s.post(lan_host_url, data=postdata).text).text)
    assert(data['code'] == 0)
    print 'lan changed'

else:
    print 'ERROR: Not support'
