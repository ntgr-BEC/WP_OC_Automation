#!/usr/bin/env python2.3
#
#    Thread-safe busman control methods
#    Copyright (c) 2012 Apple. All rights reserved.
#    Created by Dmitry Halavin on 2/2/12.
#
from time import sleep
from twisted.python import lockfile

from LogInit import LogIt

busmanLock = lockfile.FilesystemLock('/tmp/busmanLock')

class BusmanControl():


    def __init__(self):
        try:
            self.logger = LogIt()
            from CoreAutomation import CAMCambrionixManager# @UnresolvedImport
            self.manager, error = CAMCambrionixManager.getManager_error_(0, None)
            if error:
                self.busman = None
            busmen = self.manager.getBusmanControllers()
            self.busman = busmen[0] if busmen else None
        except Exception as _err:
            self.logger.Info("Unable to set up busman, continuing. Error:%s", _err)
            self.busman = None

    def ConnectPort(self, port):
        if port < 0 or not self.busman:
            print "Error while getting port %s , returning isDeviceAvailable()" % port
        if self.__lock():
            try:
                self.busman.connectPort_(port)
            except Exception as error:
                print "Caught error %s" % error
            if self.__unlock():
                return True
        return False

    def DisconnectPort(self, port):
        if port < 0 or not self.busman:
            return False
        if self.__lock():
            try:
                self.busman.disconnectPort_(port)
            except Exception as error:
                print "Caught error %s" % error
            if self.__unlock():
                return True
        return False

    def ReconnectPort(self, port):
        self.disconnectPort(port)
        self.connectPort(port)

    def ChargePort(self, port):
        if port < 0 or not self.busman:
            return False
        if self.__lock():
            try:
                self.busman.chargePort_(port)
            except Exception as error:
                print "Caught error %s" % error
            if self.__unlock():
                return True
        return False

    def HighPowerCharge(self):
        if not self.busman:
            return False
        if self.__lock():
            try:
                self.manager.highPowerCharge()
            except Exception as error:
                print "Caught error %s" % error
            if self.__unlock():
                return True
        return False

    def DisconnectAll(self):
        if self.__lock():
            try:
                self.busman.disconnect()
            except Exception as error:
                print "Caught error %s" % error
            if self.__unlock():
                return True
        return False

    def ConnectAll(self):
        if self.__lock():
            try:
                self.busman.connect()
            except Exception as error:
                print "Caught error %s" % error
            if self.__unlock():
                return True
        return False

    def __lock(self):
        i = 0
        while (i < 60):
            if busmanLock.lock() == False:
                print "Can't get Busman lock, waiting 5 seconds to retry"
                i = i + 1
                sleep(5)
            else:
                print "Busman lock acquired"
                return True
        print "Could not lock port to set to charge, giving up"
        return False

    def __unlock(self):
        if busmanLock.locked:
            print "Letting go of the busman lock"
            busmanLock.unlock()
            return True
        else:
            print "We don't have the lock!"
            return False

class MissingBusman(Exception):
    def __init__(self, value):
        self.value = value

    def __str__(self):
        return repr(self.value)
