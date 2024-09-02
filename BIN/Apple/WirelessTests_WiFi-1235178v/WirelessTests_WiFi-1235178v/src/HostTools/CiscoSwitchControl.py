#!/usr/bin/python
#
#  CiscoSwitchControl.py
#  WiFi Enterprise WirelessAutomation
#
#  This test is the base for Enterprise WiFi testing.
#
#  Created by Dani Gleser on 06/29/12.
#  Copyright (c) 2011 Apple. All rights reserved.
#
#import pdb
import os, sys, argparse
from time import sleep
from plistlib import readPlist
from common.CiscoCommon import CiscoCommon
from LogInit import LogIt

class CiscoSwitchControl():

    def __init__(self):
        self.logger = LogIt()

    def setup(self,arguments):
        print "Setup"
        if arguments.commandfile:
            self.logger.Info("Reading command file: %s" % arguments.commandfile)
            f=open(arguments.commandfile, 'r')
            self.Commands = f.readlines()
            f.close()
        else:
            self.logger.Error("No configuration file set on the command line!")

    def run(self,arguments):
        #Setup Cisco Switch
        self.logger.Info("Running Command Set: %s" % self.Commands)
        SwitchPOE = CiscoCommon(arguments.switchaddress, arguments.switchname, arguments.switchuser, arguments.switchpassword)
        SwitchPOE.ConfigT()
        SwitchPOE.Cmds(self.Commands)
        SwitchPOE.ExitConfig()
        SwitchPOE.Logout()

    def teardown(self):
        print "teardown"
    def showInterfacePower(self, arguments):
        self.logger.Info("Showing Interface Power State")

def main():
    parser = argparse.ArgumentParser(
        prog = 'CiscoSwitchControl.py',                                                           # the name of the program
        description = "This program enables or disabled POW on a Cisco Switch.",     # text displayed on top of --help
        epilog = 'Have a nice day.')
    #parser.add_argument('','',"",default=)
    parser.add_argument('-a','--switchaddress',action="store",dest='switchaddress',help='the FQDN or IP address of the switch')
    parser.add_argument('-n','--switchname',action="store",dest='switchname',help='The Name of the switch')
    parser.add_argument('-u','--switchuser',action="store",dest='switchuser',help='The User Name', default="qaadmin")
    parser.add_argument('-p','--switchpassword',action="store",dest='switchpassword',help='The Password for the User', default="toast!123")
    parser.add_argument('-f','--commandfile',action="store",dest='commandfile',help='The file where the command are located', default=False)
    arguments = parser.parse_args()
    print "Aruguments: %s" % arguments
    my_main = CiscoSwitchControl()
    my_main.setup(arguments)
    my_main.run(arguments)
if __name__ == '__main__':
    main()
    
    