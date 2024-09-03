<?php
$command = "sudo /bin/cp /dev/null /var/log/kern.debug.log";
exec($command);

//$command = "sudo /usr/sbin/conntrack -F";
//exec($command);

$command = "sudo /bin/cat /var/log/kern.debug.log";
exec($command, $output, $retval);

echo "RETURN VALUE: $retval\n";
echo "OUTPUT:\n";
echo join("\n", $output);
?>
