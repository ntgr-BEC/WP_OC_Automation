
import os, sys, re, time
import ssl, requests
from bs4 import BeautifulSoup

start = time.time()
print time.strftime("%y%m%d-%H:%M:%S"), 'started'

s = requests.Session()
python_bin = os.path.dirname(os.path.abspath(__file__))
mailto_bin = os.path.join(python_bin, r'..\mailto')
os.chdir(python_bin)
version_file = 'version.txt'
if not os.path.isfile(version_file):
    sys.exit(1)
with open (version_file) as fp:
    wp_ver = fp.read()

print 'get wp version:', wp_ver

time.sleep(300)
    
log_sys_url = 'http://10.40.4.183:9090/automation/webportal/getStatistics'
d = {}
d['ReportType'] = 'ConsolidatedReport'
d['WEBPORTAL_VERSION'] = wp_ver
d['Feature']=''
d['TestCaseName']=''
d['ExecutionResult']=''
d['ExecutionOwner']=''
d['StartTime']=''
d['EndTime']=''
##d['page']=1
##d['rows']=20

print time.strftime("%y%m%d-%H:%M:%S"), 'downloading report'
soup = BeautifulSoup(s.post(log_sys_url, data=d).content)
p = soup.find('p')
if not p:
    p = soup

t = p.text
print 'get result', t

job_link = 'http://10.40.4.183:8081/view/for%20webportal/'
if os.environ.has_key('BUILD_URL'):
    job_link = os.environ['BUILD_URL'] + 'allure'

    
body = '''
Hi All,

  To get the more consolidated automation test result in detail, login http://10.40.1.183:9090/automation/webportal.jsp?WEBPORTAL_VERSION=%s

Refer to our Jenkins Server: %s

BR,
Switch Automation Team
''' % (wp_ver, job_link)

subject = '''Insight Switch [%s] Daily Automation Testing Report [%s]''' % (wp_ver, t.replace('"', ''))

os.chdir(mailto_bin)
with open('subject.txt', 'w') as f:
    f.write(subject)
with open('body.txt', 'w') as f:
    f.write(body)
print 'done. (time: %ss)' % int(time.time()-start)
