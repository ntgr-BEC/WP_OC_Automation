<?php
$command = "sudo /bin/date --utc";
exec($command, $output, $retval);

echo "RETURN VALUE: $retval\n";
echo "OUTPUT:\n";
echo join("\n", $output);
?>
