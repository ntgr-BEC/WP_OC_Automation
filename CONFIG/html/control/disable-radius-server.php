<?php
$radius_std_port = "1812";
$radius_ip = $_REQUEST["RADIUS_IP"];
$radius_port = $_REQUEST["RADIUS_PORT"];

do {
    $command = "sudo /sbin/iptables -t filter -D INPUT -i eth1 -d $radius_ip -p udp -m udp --dport $radius_std_port -j ACCEPT";
    exec($command, $output, $retval);
} while ($retval == 0);

do {
    $command = "sudo /sbin/iptables -t nat    -D PREROUTING -i eth1 -d $radius_ip -p udp -m udp --dport $radius_port -j DNAT --to-destination $radius_ip:$radius_std_port";
    exec($command, $output, $retval);
} while ($retval == 0);

$command = "sudo /sbin/iptables -t nat    -v -n --line-numbers -L PREROUTING";
exec($command, $output, $retval);
$command = "sudo /sbin/iptables -t filter -v -n --line-numbers -L INPUT";
exec($command, $output, $retval);

echo "RETURN VALUE: $retval\n";
echo "OUTPUT:\n";
echo join("\n", $output);
?>
