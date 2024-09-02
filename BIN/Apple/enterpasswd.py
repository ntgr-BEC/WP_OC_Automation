#!/usr/local/bin/python3

import os, sys, time, subprocess
import atomac
from atomac.AXKeyCodeConstants import RETURN

print ('starting to check...'),
while True:
    try:
        app = atomac.getAppRefByLocalizedName('SecurityAgent')
        print ('see a passwd msg'),
        app.activate()
        app.sendKeys('password')
        app.sendGlobalKey(RETURN)
    except:
        pass
    time.sleep(60)
    print ('.'),
