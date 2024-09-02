
import os, sys, re, time
import codecs, tabulate, sqlite3
import smtplib
from email.mime.text import MIMEText



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
  This is a notification from Insight Long-Run monitoring script at #%s.
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

#subject = 'Insight LongRun Report For Lab (2*BR500 in NEC, 1*BR500 in ChangSha) : [Total: %s Pass: %s Fail: %s] on Current Production Server.' % (passcount+failcount, passcount, failcount)
subject = 'Insight v5.9.5 LongRun Report on Production Server : [Total: %s Pass: %s Fail: %s]' % (passcount+failcount, passcount, failcount)
#subject = 'Insight LongRun Report For Lab (1*BR500 in NEC, 1*BR500 in BEC and 1*BR500 in SJ) : [Total: %s Pass: %s Fail: %s] on Current Production Server.' % (passcount+failcount, passcount, failcount)
#subject = 'Insight LongRun Report For Lab (1*BR500 in NEC, 1*BR500 in ChangSha): [Total: %s Pass: %s Fail: %s] on Outsource Production Server.' % (passcount+failcount, passcount, failcount)
pf(subject)

mail_host="10.40.4.183"

sender = 'insightalerts@netgear.com'
receivers = ['spalaniappan@netgear.com', 'tmruthunjaya@netgear.com', 'yzhang@netgear.com', 'mkrishnamurthy@netgear.com', 'bgu@netgear.com', 'switch_nj_team@netgear.com', 'luiz.yao@netgear.com', 'lavi.xu@netgear.com']
copyto = ['awaghe@netgear.com', 'hongyan.shao@netgear.com', 'sjena@netgear.com']

message = MIMEText(body, 'html', 'utf-8')
message['From'] = sender
message['To'] =  ','.join(receivers)
message['Cc'] =  ','.join(copyto)
message['Subject'] = subject

if failcount>0:
    try:
        smtpObj = smtplib.SMTP()
        smtpObj.connect(mail_host, 25)
        smtpObj.set_debuglevel(1)
        smtpObj.sendmail(sender, receivers + copyto, message.as_string())
        print "Send email successful"
    except smtplib.SMTPException:
        print "Send email failed:", e
else:
    pf('all check are passed')
