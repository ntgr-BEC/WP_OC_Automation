<?php
$log_file = "/usr/local/var/log/radius/radius.log";

$command = "sudo /bin/cat $log_file";
exec($command, $output, $retval);

echo "RETURN VALUE: $retval\n";
echo "OUTPUT:\n";
echo join("\n", $output);
?>
