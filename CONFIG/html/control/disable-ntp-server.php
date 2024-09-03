<?php
$command = "sudo /sbin/iptables -t nat    -F PREROUTING";
exec($command, $output, $retval);

$command = "sudo /sbin/iptables -t filter -D INPUT 4";
exec($command, $output, $retval);

$command = "sudo /sbin/iptables -t nat    -v -n --line-numbers -L PREROUTING";
exec($command, $output, $retval);
$command = "sudo /sbin/iptables -t filter -v -n --line-numbers -L INPUT";
exec($command, $output, $retval);

echo "RETURN VALUE: $retval\n";
echo "OUTPUT:\n";
echo join("\n", $output);
?>
