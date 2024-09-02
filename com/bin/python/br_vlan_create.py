

import os, sys, re, time, argparse, ast
import urllib, urllib2, json, requests
from bs4 import BeautifulSoup

user = 'admin'
passwd = 'Netgear1@'
parser = argparse.ArgumentParser(description='A tool to create vlan')
parser.add_argument('-u', default=user, help="The user name")
parser.add_argument('-p', default=passwd, help="The user password")
parser.add_argument('-i', default='192.168.123.1', help="The BR IP address")
parser.add_argument('-j', default='192.168.160.1', help="The DHCP IP address")
parser.add_argument('-n', type=int, default=1234, help="the vlan id")
parser.add_argument('-t', default='0', help="BR Mode: 0-br500, 1-br100")
args = parser.parse_args()

def get_dhcp_range(ip):
    return ip[:-1]+'2',ip[:-1]+'254'

brmode = int(args.t)
host_url_prefix = 'https://%s/' % args.i
start_end = get_dhcp_range(args.j)

s = requests.Session()
s.verify = False
s.auth = (args.u, args.p)

if 0 == brmode:
    ## do login
    login_host_url = host_url_prefix + 'change_user.html'
    soup = BeautifulSoup(s.get(login_host_url).text)
    if soup.text.find('401 Authorization') != -1:
        print ("relogin")
        soup = BeautifulSoup(s.get(login_host_url).text)

    time.sleep(4)

    ## create vlan
    host_url = host_url_prefix + 'cgi-bin/vlan.cgi?cmd=vlan_entry_list'
    soup = BeautifulSoup(s.get(host_url).text)
    foundvlan = False
    for vlan in json.loads(soup.text)['data']['entry']:
        if vlan['vid'] == args.n:
            foundvlan = True
            break

    if not foundvlan:
        postdata = {}
        entry = {}
        entry['vid'] = args.n
        entry['name'] = 'testvlan'
        entry['desc'] = 'testvlan%s'% args.n
        entry['ports'] = ['2t']
        postdata['entry'] = entry
        host_url = host_url_prefix + 'cgi-bin/vlan.cgi?cmd=create_vlan'
        print json.dumps(postdata)
        soup = BeautifulSoup(s.post(host_url, data=json.dumps(postdata)).text)
        print soup.text

        time.sleep(4)

    ## create dhcp
    postdata = {}
    subnet = {}
    subnet['name'] = 'LAN2'
    subnet['dhcp_enable'] = 1
    subnet['vlanid'] = args.n
    subnet['desc'] = ''
    subnet['netmask'] = '255.255.255.0'
    subnet['ipaddr'] = args.j
    subnet['dhcp_start'] = start_end[0]
    subnet['dhcp_end'] = start_end[1]
    subnet['macaddr'] = '00:00:00:00:00:00'
    postdata['subnet'] = subnet
    host_url = host_url_prefix + 'cgi-bin/network.cgi?cmd=lan_subnet_add'
    print json.dumps(postdata)
    soup = BeautifulSoup(s.post(host_url, data=json.dumps(postdata)).text)
    print soup.text
elif 1 == brmode:
    # Do login
    postdata = {"method":"set","params":{"userName":args.u,"passWord":args.p}}
    host_url_prefix = 'https://%s/cgi-bin/' % args.i
    login_host_url = host_url_prefix + 'web-cgi/do_login'
    data = ast.literal_eval(BeautifulSoup(s.post(login_host_url, json=postdata).text).text)
    if data['code'] != 0:
        print "Re login"
        time.sleep(2)
        data = ast.literal_eval(BeautifulSoup(s.post(login_host_url, json=postdata).text).text)
        assert(data['code'] == 0)

    print 'logined'
    time.sleep(4)

    # Set Vlan
    lan_host_url = host_url_prefix + 'web-cgi/vlan_entry_config'
    params = {}
    params['vid'] = args.n
    params['name'] = 'testvlan'
    params['desc'] = 'test'
    params['ports'] = ["5t"]
    postdata = {"method":"add","params":params}
    data = ast.literal_eval(BeautifulSoup(s.post(lan_host_url, json=postdata).text).text)
    assert(data['code'] == 0)
    print 'vlan added'
    
    time.sleep(4)

    # Add Subnet
    lan_host_url = host_url_prefix + 'web-cgi/set_lan_config'
    params = {}
    params['lan'] = 'LAN2'
    params['dhcpd_enable'] = 1
    params['vlanid'] = args.n
    params['desc'] = 'test'
    params['ipaddr'] = args.j
    params['netmask'] = '255.255.255.0'
    params['dhcpd_start'] = start_end[0]
    params['dhcpd_end'] = start_end[1]
    params['macaddr'] = '00:00:00:00:00:00'
    params['disabled'] = 0
    params['rip_enable'] = 0
    params['rip_direction'] = 0
    params['rip_version'] = 0
    postdata = {"method":"set","params":params}
    data = ast.literal_eval(BeautifulSoup(s.post(lan_host_url, json=postdata).text).text)
    assert(data['code'] == 0)
    print 'dhcp added'
else:
    print 'ERROR: Not support'

