<?php
$command = "sudo /bin/cat /var/log/kern.debug.log";
exec($command, $output, $retval);

echo "RETURN VALUE: $retval\n";
echo "OUTPUT:\n";
echo join("\n", $output);
?>
