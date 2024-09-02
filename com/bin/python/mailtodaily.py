#!/usr/bin/python
# -*- coding: UTF-8 -*-

import os
import smtplib
import sys
import time
from email.mime.text import MIMEText
from email.header import Header

from bs4 import BeautifulSoup
from requests import Session

version_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'version.txt')

try:
	with open(version_path) as f:
		version = f.read()
except FileNotFoundError:
	print('FileNotFoundError: {}'.format(version_path))
	sys.exit(1)


time.sleep(300)

with Session() as s:
	log_sys_url = 'http://10.40.4.183:9090/automation/webportal/getStatistics'
	data = {
		'ReportType': 'ConsolidatedReport',
		'WEBPORTAL_VERSION': version,
		'Feature': '',
		'TestCaseName': '',
		'ExecutionResult': '',
		'ExecutionOwner': '',
		'StartTime': '',
		'EndTime': ''
	}
	resp = s.post(log_sys_url, data=data)
	soup = BeautifulSoup(resp.text, 'html.parser')
	p = soup.find('p') or soup
	t = p.text
	print('get result: ', t)

job_link = 'http://10.40.4.183:8081/view/for%20webportal/'

try:
	job_link = os.environ['BUILD_URL'] + 'allure'
except KeyError:
	pass

body = '''
Hi All,

  To get the more consolidated automation test result in detail, login http://10.40.1.183:9090/automation/webportal.jsp?WEBPORTAL_VERSION=%s

Refer to our Jenkins Server: %s

BR,
Switch Automation Team
''' % (version, job_link)

subject = '''Insight Switch [%s] Daily Automation Testing Report [%s]''' % (version, t.replace('"', ''))


mail_host="10.40.4.183"

sender = 'insightalerts@netgear.com'
receivers = ['bruce.gu@netgear.com', 'luiz.yao@netgear.com']
copyto = ['lavi.xu@netgear.com', 'xuchen@netgear.com', 'jim.xie@netgear.com', 'dzeng@netgear.com', 'eric.zhou@netgear.com']

message = MIMEText(body, 'plain', 'utf-8')
message['From'] = sender
message['To'] =  ','.join(receivers)
message['Cc'] =  ','.join(copyto)
message['Subject'] = subject

try:
    smtpObj = smtplib.SMTP()
    smtpObj.connect(mail_host, 25)
    smtpObj.set_debuglevel(1)
    smtpObj.sendmail(sender, receivers + copyto, message.as_string())
    print ("Send email successful")
except smtplib.SMTPException as e:
    print ("Send email failed:", e)
