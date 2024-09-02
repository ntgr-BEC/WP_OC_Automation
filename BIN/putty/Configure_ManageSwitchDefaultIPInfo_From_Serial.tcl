#*********************************************************************************   
#
#  File Name        : Configure_ManageSwitchDefaultIPInfo_From_Serial.tcl
#
#  Description      :
#         Using this function to configure manage switch management ipv4 ip info from serial.
#         default ipv4 info: 169.254.100.100/169.254.100.1
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        2017-06-01     Jim Xie        Created
#
#*********************************************************************************
package require Expect

set serial_port [lindex $argv 0]
set serial_speed [lindex $argv 1]


spawn "C:/AUTOMATION/BIN/putty/plink.exe" -serial $serial_port -sercfg $serial_speed

exp_send -i $spawn_id -- "\r"
exp_sleep 1
expect {
   -i $spawn_id -re "User:" {
       exp_send -i $spawn_id -- "admin\r"
       exp_sleep 3
       exp_send -i $spawn_id -- "\r"
       exp_sleep 3
    }
}


expect {
   -i $spawn_id -re " >" {
        exp_send -i $spawn_id -- "en\r"
        exp_sleep 3
    }
}
expect {
    -i $spawn_id -re "#" {
       exp_send -i $spawn_id -- "configure\r"
       exp_sleep 5
    }
}

expect {
    -i $spawn_id -re "#" {
       exp_send -i $spawn_id -- "ip management vlan 1 169.254.100.100 /24\r"
       exp_sleep 10
    }
}

expect {
    -i $spawn_id -re "#" {
       exp_send -i $spawn_id -- "ip default-gateway 169.254.100.1\r"
       exp_sleep 10
       exp_send -i $spawn_id -- "exit\r"
       exp_sleep 3
       exp_send -i $spawn_id -- "exit\r"
       exp_sleep 3
    }
}

expect {
   -i $spawn_id -re " >" {
        exp_send -i $spawn_id -- "logout\r"
        exp_sleep 3
    }
}

exp_close -i $spawn_id




