<?php
$conf_file = "/usr/local/etc/raddb/clients.prosafe";
$ipaddr    = $_REQUEST["ipaddr"];
$secret    = $_REQUEST["secret"];
$shortname = $_REQUEST["shortname"];
$nastype   = $_REQUEST["nastype"];

$command = "sudo /bin/cp /dev/null $conf_file";
exec($command);

$command = "sudo sh -c '/bin/echo \"client $ipaddr {\" >> $conf_file'";
exec($command);

$command = "sudo sh -c '/bin/echo \"    secret    = \\\"$secret\\\"\" >> $conf_file'";
exec($command);

$command = "sudo sh -c '/bin/echo \"    shortname = $shortname\" >> $conf_file'";
exec($command);

$command = "sudo sh -c '/bin/echo \"    nastype   = $nastype\" >> $conf_file'";
exec($command);

$command = "sudo sh -c '/bin/echo \"}\" >> $conf_file'";
exec($command);

$command = "sudo /bin/cat $conf_file";
exec($command, $output, $retval);

echo "RETURN VALUE: $retval\n";
echo "OUTPUT:\n";
echo join("\n", $output);
?>
