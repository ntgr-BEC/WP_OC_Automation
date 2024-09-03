<?php
$command = "sudo /usr/sbin/ntpdate -u -q localhost";
exec($command, $output, $retval);

echo "RETURN VALUE: $retval\n";
echo "OUTPUT:\n";
echo join("\n", $output);
?>
