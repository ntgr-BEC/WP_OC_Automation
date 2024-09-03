<?php
$radius_std_port = "1812";
$radius_ip = $_REQUEST["RADIUS_IP"];
$radius_port = $_REQUEST["RADIUS_PORT"];

$command = "sudo /sbin/iptables -t nat    -A PREROUTING -i eth1 -d $radius_ip -p udp -m udp --dport $radius_port -j LOG --log-level debug --log-prefix 'RADIUS($radius_port): '";
exec($command, $output, $retval);

$command = "sudo /sbin/iptables -t nat    -v -n --line-numbers -L PREROUTING";
exec($command, $output, $retval);
$command = "sudo /sbin/iptables -t filter -v -n --line-numbers -L INPUT";
exec($command, $output, $retval);

echo "RETURN VALUE: $retval\n";
echo "OUTPUT:\n";
echo join("\n", $output);
?>
