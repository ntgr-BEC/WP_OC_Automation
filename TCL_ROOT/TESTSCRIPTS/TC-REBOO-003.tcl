####################################################################################
#  File Name   : TC-REBOO-003.tcl                                                  #
#                                                                                  #
#  Description :                                                                   #
#        This file defines the test scripts for reboot swithces.                   #
#                                                                                  #
#  Config File : TC-REBOO-003.cfg                                                  #
#                                                                                  #
#  History     :                                                                   #
#        Date              Programmer         Description                          #
#                                                                                  #
####################################################################################

foreach switch_id $REBOOT_SWITCH_LIST {
    Netgear_log_file "TC-REBOO-003.tcl" "Going to reboot the switch $switch_id"
    CALRebootSwitch $switch_id
}
