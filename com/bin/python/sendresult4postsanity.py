#!/usr/bin/python
# -*- coding: UTF-8 -*-

'''
'''

import os, smtplib, sys, time
import xml.etree.ElementTree as ET

from email.mime.text import MIMEText

if len(sys.argv) != 3:
	print ('sth went wrong arguments: p/f')
	sys.exit(1)

total = int(sys.argv[1]) + int(sys.argv[2])
testResult = 'Pass-%s/Fail-%s/Total-%s' % (sys.argv[1], sys.argv[2], total)
python_bin = os.path.dirname(os.path.abspath(__file__))
wp_file = os.path.join(python_bin, r'..\..\src\test\resources\config_webportal.xml')
if not os.path.isfile(wp_file):
	print ('sth went wrong with config_webportal.xml')
	sys.exit(1)

wp_xml_tree = ET.parse(wp_file)
wp_xml_root = wp_xml_tree.getroot()
mailrecv = wp_xml_root.find('.//MailReceiver').text
assert(len(mailrecv)>8)
serverip = wp_xml_root.find('.//LogSystemServer').text
job_link = 'http://%s:8081/view/for%%20webportal/' % serverip
try:
	job_link = os.environ['BUILD_URL'] + 'allure'
except KeyError:
	pass

body = '''
Hi All,

	To get jenkins job result in detail, login at our Jenkins Server:
		<%s>

BR,
Switch Automation Team
''' % job_link


subject = '''WebPortal Automation Testing Report: [%s]''' % testResult
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
