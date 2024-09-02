
import os, sys, re, time
import codecs, tabulate, sqlite3
import smtplib
from email.mime.text import MIMEText


use163smtp = False

def pf(msg):
    print time.asctime(), msg

def dead(msg):
    pf(msg)
    sys.exit(1)

joburl = ''
if len(sys.argv)>1:
    joburl = sys.argv[1]

os.chdir(os.path.abspath(os.path.dirname(__file__)))
os.chdir('../mailto')
ptable = []
conn = sqlite3.connect(r'../stability_longrun.db')
c = conn.cursor()

# get header
##c.execute('select * from testresult')
##headers = []
##for desc in c.description:
##    pf('add header: %s' % desc[0])
##    headers.append(desc[0])
##headers.pop(0)
headers = ['Test Name', 'Test Step', 'Expect Result', 'Actual Result']
ptable.append(headers)

# get last result
c.execute("SELECT resultIndex FROM testresult order by resultIndex DESC limit 1")
rec = c.fetchall()
if len(rec) == 0:
    dead("not result found")

resNo = rec[0][0]
pf('result index is: %s' % resNo)

# check if result is exists then update
c.execute("SELECT testresult FROM mailsent limit 1")
rec = c.fetchall()
if len(rec) == 1:
    resNo_bk = rec[0][0]
    pf("previous test result is: %s" % resNo_bk)
    if resNo_bk == resNo:
        dead("old test result")
        
c.execute('delete from mailsent')
c.execute('insert into mailsent values (%s)' % resNo)
conn.commit()

# get all records
c.execute("SELECT tcName,stepInfo,expectResult,actualResult FROM testresult where resultIndex = %s and testResult = 'pass' " % resNo)
rec = c.fetchall()
passcount = len(rec)
c.execute("SELECT tcName,stepInfo,expectResult,actualResult FROM testresult where resultIndex = %s and testResult = 'fail' " % resNo)
rec = c.fetchall()
failcount = len(rec)
for tc in rec:
    rTable = []
    for t in tc:
        rTable.append(t)
    ptable.append(rTable)

ptable_fmt = tabulate.tabulate(ptable, tablefmt="html").replace('<table>', '<table border=1 rules=rows>')
body = u'''
<br/>
Hi All,
<br/>
<br/>
  This is a notification from Insight VPN-Stability monitoring script at #%s.
<br/>
<br/>
%s
<br/>

<br/>
    <a href="%s"> Link to Jenkins Job <a/>
<br/>
<br/>
BR,
<br/>
Switch Automation Team
<br/>
''' %  (resNo, ptable_fmt, joburl)

subject = 'Insight VPN Stability Report For Lab (2*BR500 in NEC): [Total: %s Pass: %s Fail: %s] on Production Server.' % (passcount+failcount, passcount, failcount)
pf(subject)

if use163smtp:
    mail_host="smtp.163.com"
    mail_user="insight_autoTest@163.com"
    mail_pass="Netgear1"
else:
    mail_host = '10.40.1.183'
    mail_user = 'report@auto.system.com'
    
sender = mail_user
receivers = ['yzhang@netgear.com', 'spalaniappan@netgear.com', 'sasingh@netgear.com', 'zzeng@netgear.com']
copyto = ['bgu@netgear.com', 'dzeng@netgear.com', 'luyao@netgear.com', 'laxu@netgear.com']

message = MIMEText(body, 'html', 'utf-8')
message['From'] = sender
message['To'] =  ','.join(receivers)
message['Cc'] =  ','.join(copyto)
message['Subject'] = subject

try:
    smtpObj = smtplib.SMTP()
    smtpObj.set_debuglevel(1)
    smtpObj.connect(mail_host, 25)
    if use163smtp:
        smtpObj.login(mail_user, mail_pass)
    smtpObj.sendmail(sender, receivers+copyto, message.as_string())
    print "Send email successful"
except smtplib.SMTPException:
    print "Send email failed:", e


