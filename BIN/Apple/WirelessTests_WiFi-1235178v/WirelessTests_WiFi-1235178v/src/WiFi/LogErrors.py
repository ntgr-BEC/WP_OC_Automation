'''
LogErrors.py

WirelessAutomation

Created by Quint Friesen 2012-10-17
Copyright (c) 2011 Apple, Inc. All rights reserved.
Class for returning error strings we search logs for
'''

class LogErrors(object):
    """
    Class to store log error strings in one place so every plist does not
    need to copy paste them and the changes
    """

    UART_UNSUPPORTED = "enableUartTraps():  Failed to enable UART, BCOM Unsupported"
    UART_TRIGGER = "Triggering trap via UART!"
    WATCHDOG_CHIP_TRAP = "AppleBCMWLANCore::reportChipErrorsGated(): BCMWLAN Chip Trap"
    WATCHDOG_TRAP_NOTALLOWED = "enableUartTraps(): UART logging required for UART traps to work."
    WATCHDOG_USER_INDUCED = "BCMWLAN Core User Induced Watchdog"

    def GetKnownErrorStrings(self):
        # TODO: Keep updating this list as we find/fix bugs.
        return [
                'processCompletedCommand():  Command Failed on "WLC_SET_VAR: escan"', # <rdar> description
                'processCompletedCommand():  Command Failed on "WLC_SET_VAR: iscan"', # <rdar> description
                'BCMWLAN Core User Induced',
                ]

    def GetIORegErrorStrings(self):
        return ['handleChipWatchdog.socRAM' ]

    def GetWatchdogStrings(self):
        return [
                    'AppleBCMWLANCore::triggerWatchdog',
                    'BCMWLAN Will watchdog',
                    'AppleBCMWLANCore::watchdog',
                    'AppleBCMWLAN::watchDogReset',
                    'AppleBCMWLAN::handleChipWatchdog()',
                    'AppleBCMWLANCore::watchDogReset()',
                    'AppleBCMWLANCore::handleWatchdogTimeout()',
                ]

    def GetCriticalErrors(self):
        return [
                    'processCompletedCommand():  Command Failed on',
                    'Checksum failure (0x####), dropping frame',
                    'AppleBCMWLANCore::captureDebugInfo',
                    'AppleBCMWLANBusInterfaceHSIC::validUsbHdr()',
                    'CRITICAL ERROR',
                    'BCMWLAN Will dump',
                    'BCOM Unsupported',
#                    'AppleBCMWLANCore::reportProblem()',
                ]

    def GetSDIOErrors(self):
        return {
                'WiFi_SDIOGeneralErrors' : ['SDIO Error', ],
                'WiFi_SDIODataLineErrors' : [
                                                'SDIO Data Error',
                                                'SDIO Data Line Busy',
                                                'SDIO Data Signal Invalid',
                                                'SDIO Data CRC Error',
                                                'SDIO Data End Bit Error',
                                                'SDIO Data Timeout',
                                             ],
                 'WiFi_SDIOCommandLineErrors' : [
                                                    'SDIO Cmd Error',
                                                    'SDIO Cmd Line Busy',
                                                    'SDIO Cmd Signal Invalid',
                                                    'SDIO Cmd Index Error',
                                                    'SDIO Cmd Line Conflict',
                                                    'SDIO Cmd CRC Error',
                                                    'SDIO Cmd End Bit Error',
                                                    'SDIO Cmd Timeout',
                                                ],
                'WiFi_CardErrors' : [
                                        'SDIO Card Error',
                                        'No SDIO Card',
                                        'SDIO Card Ejected',
                                        'SDIO Card Write Protected',
                                    ],
                'WiFi_HostControllerErrors' : [
                                                'SDIO Error',
                                                'SDHC Error',
                                                'No SD Host Controller',
                                                'SDIO Internal Clk Unstable',
                                                'SDIO Clock Disabled',
                                                'SDIO ADMA Error',
                                                'SDIO SDMA Error',
                                                'SDIO Auto CMD12 Error',
                                                'SDIO Current Limit Error',
                                                'SDIO Vendor Error',
                                                'SDIO In Reset',
                                                'No SDIO Cmd Complete',
                                                'No SDIO Transfer Complete',
                                                'SDIO DMA Timeout',
                                            ],
            }

    def GetWiFi_HSICErrors_IOUSBFamily(self):
        return {
                    'New item' : 'failure setting configuration, attempt',
                    'Failed to open IOUSBDevice' : 'start(): Unable to open device for configuration',
                    'Device is advertising more than one configuration' : 'start(): This device has more than 1 configuration',
                    'Wrong type of provider when starting IOUSBDevice' : "start(): didn't match on an IOUSBDevice",
                    'Failed to start AppleBCMWLANHSICDevice provider' : 'super::start(provider) failed',
                    'WiFi failed to enumerate due to rdar://problem/9273794; and the workaround failed' : 'WiFi failed to enumerate due to rdar://problem/9273794',
                    'Indicates that the HSIC bus failed to come back up after an idle-disconnect. Will usually be followed by a watchdog reset.' : 'reconnectBus(): Error',
                    'A synchronous USB request was made on the workloop thread (from a callback?).  Only async requests are permitted in that case' : '0xe000404a',
                    'Clear pipe stall not recursive' : '0xe0004048',
                    'The device is not a high speed device, so the EHCI driver returns an error' : '0xe0004049',
                    'Error to hub on high speed bus trying to do split transaction' : '0xe000404b',
                    'Attempted to use user land low latency isoc calls w/out calling PrepareBuffer (on the frame list) first' : '0xe000404c',
                    'Attempted to use user land low latency isoc calls w/out calling PrepareBuffer (on the data buffer) first ' : '0xe000404d',
                    'Interface ref not recognized' : '0xe000404e',
                    'Pipe has stalled, error needs to be cleared' : '0xe000404f',
                    'The transaction has been returned to the caller' : '0xe0004050',
                    'Port was not suspended' : '0xe0004050',
                    'Transaction timed out' : '0xe0004051',
                    'Configuration Not found' : '0xe0004056',
                    'Endpoint Not found' : '0xe0004057',
                    'not enough power for selected configuration' : '0xe000405d',
                    'not enough pipes in interface' : '0xe000405e',
                    'no async port' : '0xe000405f',
                    'Too many pipes' : '0xe0004060',
                    'Pipe ref not recognized' : '0xe0004061',
                    'The transaction was returned because the port was suspended' : '0xe0004052',
                }

    def GetWiFi_HSICErrors_IOUSBFamilyHardware(self):
        return  {
                    'Link error' : '0xe0004010',
                    'Transaction not sent' : '0xe000400f',
                    'Transaction not sent-1' : '0xe000400e',
                    'Buffer Underrun (Host hardware failure on data out, PCI busy?)' : '0xe000400d',
                    'Buffer Overrun (Host hardware failure on data out, PCI busy?)' : '0xe000400c',
                    'Reserved' : '0xe000400b',
                    'Reserved-1' : '0xe000400a',
                    'Pipe stall, Bad or wrong PID' : '0xe0004007',
                    'Pipe stall, PID CRC error' : '0xe0004006',
                    'Pipe stall, Bad data toggle' : '0xe0004003',
                    'Pipe stall, bitstuffing' : '0xe0004002',
                    'Pipe stall, bad CRC' : '0xe0004001',
                }

    # And one object with all the error strings combined
    def GetAllErrors(self):
        return [
                    self.GetCriticalErrors(),
                    self.GetIORegErrorStrings(),
                    self.GetSDIOErrors(),
                    self.GetSDIOErrors(),
                    self.GetWatchdogStrings(),
                    self.GetWiFi_HSICErrors_IOUSBFamily(),
                    self.GetWiFi_HSICErrors_IOUSBFamilyHardware()
                ]
