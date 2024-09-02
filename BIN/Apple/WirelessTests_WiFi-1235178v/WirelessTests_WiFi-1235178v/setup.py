#!/usr/bin/env python
"""
Creates package for WiFi Modules and Tests
$ python setup.py sdist
"""
import os
import re
import sys
from distutils.core import setup
from subprocess import Popen, PIPE, call
try:
    from setup_int import cleanSource
except:
    cleanSource = lambda x: None

#----------------------------------------------------------------------
#  Utility
#----------------------------------------------------------------------

def isClientInstall():
    """ Returns True if this is the client install """
    return os.path.exists('PKG-INFO')

def getSvnVersion(path='.'):
    """ Returns SVN revision number """
    if os.path.exists('PKG-INFO'):
        fh = open('PKG-INFO', 'r')
        txt = fh.read()
        fh.close()
        return int(re.search('Version:\s*(\d+)', txt, re.DOTALL|re.MULTILINE).group(1))
    else:
        txt = Popen(['svn info %s' % path], stdout=PIPE, shell=True).stdout.read()
        return int(re.search('Revision:\s*(\d+)', txt, re.DOTALL|re.MULTILINE).group(1))

def addSudoer(username=None):
    """ Adds user to /etc/sudoers """
    username = username or Popen('whoami', shell=True, stdout=PIPE).stdout
    inSudoers = Popen('sudo grep %s /etc/sudoers' % username, shell=True, stdout=PIPE).stdout

    # Skip if username already in sudoers
    if not inSudoers:
        call('echo "%s ALL=(ALL) NOPASSWD: ALL" | sudo tee -a /etc/sudoers' % username, shell=True)
        inSudoers = Popen('sudo grep %s /etc/sudoers' % username, shell=True, stdout=PIPE).stdout

    assert inSudoers  # Error, cannot add user to sudoers!

def easyInstall(name):
    """ Installs package w/ easy_install """
    cmd = 'sudo easy_install %s' % name
    p = call(cmd, shell=True)

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

# Option to skip clean
if '--clean=0' in sys.argv:
    sys.argv.remove('--clean=0')
elif isClientInstall():
    pass  # skip
else:
    cleanSource('../..')

# Init
addSudoer()
easyInstall('pyserial')
easyInstall('mysql-connector-python')
easyInstall('ipy')
easyInstall('sh')


setup(
    name = 'WirelessTests_WiFi',
    version = str(getSvnVersion('../..')) + 'v',  # vendor version
    description = 'WiFi Modules and Tests',
    author = '',
    auth_email = '',
    url = '',
    package_dir = {
        '' : 'src',
        'DUT' : 'src/DUT',
        'HostTools' : 'src/HostTools',
        'TrafficGen' : 'src/TrafficGen',
        'Utilities' : 'src/Utilities',
        'WiFi' : 'src/WiFi',
    },
    packages = [
        'DUT',
        'HostTools',
        'TrafficGen',
        'Utilities',
        'WiFi',
    ],
    py_modules = [
        'LogInit',
        'SSHControl',
        'WiFiModulesExceptions'
    ]
)



