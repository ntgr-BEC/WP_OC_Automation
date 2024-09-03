<?php
$ntp_std_port = "123";
$ntp_port = $_REQUEST["NTP_PORT"];

$command = "sudo /sbin/iptables -t nat    -A PREROUTING -i eth1 -p udp -m udp --dport $ntp_port -j LOG --log-level debug --log-prefix 'NTP($ntp_port): '";
exec($command, $output, $retval);
$command = "sudo /sbin/iptables -t nat    -A PREROUTING -i eth1 -p udp -m udp --dport $ntp_port -j REDIRECT --to-ports $ntp_std_port";
exec($command, $output, $retval);

$command = "sudo /sbin/iptables -t filter -A INPUT -i eth1 -p udp -m udp --dport $ntp_std_port -j ACCEPT";
exec($command, $output, $retval);

$command = "sudo /sbin/iptables -t nat    -v -n --line-numbers -L PREROUTING";
exec($command, $output, $retval);
$command = "sudo /sbin/iptables -t filter -v -n --line-numbers -L INPUT";
exec($command, $output, $retval);

echo "RETURN VALUE: $retval\n";
echo "OUTPUT:\n";
echo join("\n", $output);
?>
