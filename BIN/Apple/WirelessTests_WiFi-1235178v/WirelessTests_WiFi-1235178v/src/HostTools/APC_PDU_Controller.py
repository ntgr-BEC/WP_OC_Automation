"""
Class to control an APC programmable power strip
"""

from os import system

class APC_PDU_Controller():

    def __init__(self, hostname, community):
        self.__hostname = hostname
        self.__community = community

    PDU_ON = 1
    PDU_OFF = 2


    def __GetHostnameAndCommunity(self, hostname, community):
        self.__hostname = hostname
        self.__community = community

    def toggle(self, port, onOrOff, hostname="", community=""):
        self.__GetHostnameAndCommunity(hostname, community)
        self.toggle(port, onOrOff)
        command = "snmpset -c " + self.__community + " " + self.__hostname + " .1.3.6.1.4.1.318.1.1.12.3.3.1.1.4." + str(port) + " i " + str(onOrOff)
        system(command)

    def toggleOn(self, port):
        self.toggle(port, self.PDU_ON)

    def toggleOff(self, port):
        self.toggle(port, self.PDU_OFF)
