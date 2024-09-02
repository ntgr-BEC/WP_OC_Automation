#!/usr/bin/python
# -*- coding: UTF-8 -*-

'''
* Please update option 'receivers' below to your mailbox
* Make sure you called 'TransferLogToReportSystem.tcl' in your jenkins job to upload result
'''

import os, smtplib, sys, time
import xml.etree.ElementTree as ET

from email.mime.text import MIMEText
from bs4 import BeautifulSoup
from requests import Session

python_bin = os.path.dirname(os.path.abspath(__file__))
wp_file = os.path.join(python_bin, r'..\..\src\test\resources\config_webportal.xml')

if not os.path.isfile(wp_file):
	print ('sth went wrong')
	sys.exit(1)

wp_xml_tree = ET.parse(wp_file)
wp_xml_root = wp_xml_tree.getroot()
version = wp_xml_root.find('.//WebPortalVersion').text
assert(len(version)>7)
mailrecv = wp_xml_root.find('.//MailReceiver').text
assert(len(mailrecv)>8)
serverip = wp_xml_root.find('.//LogSystemServer').text
job_link = 'http://%s:8081/view/for%%20webportal/' % serverip
try:
	job_link = os.environ['BUILD_URL'] + 'allure'
	time.sleep(300)
except KeyError:
	pass

with Session() as s:
	log_sys_url = 'http://%s:9090/automation/webportal/getStatistics' % serverip
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
	rate = p.text.replace('"', '')
	print('get result: ', rate, 'for build:', version)

assert(rate.find('Total: 0') == -1)
body = '''
Hi All,

	To get the more consolidated automation test result in detail, login at:
		<http://%s:9090/automation/webportal.jsp?WEBPORTAL_VERSION=%s>

	To get jenkins job result in detail, login at our Jenkins Server:
		<%s>

BR,
Switch Automation Team
''' % (serverip, version, job_link)


subject = '''WebPortal Automation Testing Report [%s] on Build [%s]''' % (rate, version)
mail_host=serverip
sender = 'insightalerts@netgear.com'

message = MIMEText(body, 'plain', 'utf-8')
message['From'] = sender
message['To'] =  mailrecv
message['Subject'] = subject
try:
	smtpObj = smtplib.SMTP()
	smtpObj.connect(mail_host, 25)
	smtpObj.sendmail(sender, mailrecv.split(','), message.as_string())
	print ("Send email successful")
except smtplib.SMTPException as e:
	print ("Send email failed:", e)
