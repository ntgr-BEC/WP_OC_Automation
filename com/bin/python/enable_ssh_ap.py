'''

'''

import os, sys, re, time
import requests, argparse

admin_passwd = 'Netgear1@'
ap_host = '172.16.20.46'
parser = argparse.ArgumentParser(description='A tool to enable ssh for ap')
parser.add_argument('-p', default=admin_passwd, help="The admin password")
parser.add_argument('-i', default=ap_host, help="AP ip")
args = parser.parse_args()

admin_passwd = args.p
ap_host = args.i
host_url_prefix = 'https://%s/' % ap_host

s = requests.Session()
s.verify = False

login_host_url = host_url_prefix + 'socketCommunication'

# init request
r = s.get(host_url_prefix)
assert r.status_code == 200

# do login
postdata = {"system":{"basicSettings":{"adminName":"admin","adminPasswd":admin_passwd}}}
r = s.post(login_host_url, json=postdata)
print(r)
data = r.json()
print(data)
if data['status'] != 0:
    print('plz reboot your ap and try again')
    sys.exit(1)
    
if "security_token" in data['system']:  
    headers = {'security': data['system']['security_token']}
else:
    headers = {'security': r.headers['security']}

# check ssh status
postdata = {"system":{"monitor":{"bootloaderVersion":{"crashMagicSupport":""}},"hiddenSettings":{"revSSHStatus":""},"remoteSettings":{"sshStatus":""}}}
r = s.post(login_host_url, json=postdata, headers=headers)
data = r.json()
if data['system']['remoteSettings']['sshStatus'] == '0':
    postdata = {"system":{"hiddenSettings":{"revSSHStatus":"0"},"remoteSettings":{"sshStatus":"1"}}}
    r = s.post(login_host_url, json=postdata, headers=headers)
    data = r.json()
    assert data['status'] == 0
    print('=='*20, 'ssh is enabled now')
else:
    print('=='*20, 'ssh already enabled')

# do logout
logout_host_url = host_url_prefix + 'logout'
postdata = {"admin":"admin"}
r = s.post(logout_host_url, json=postdata, headers=headers)
data = r.json()
assert data['status'] == 0
