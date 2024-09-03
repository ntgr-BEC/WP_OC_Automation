<?php
$conf_file = "/usr/local/etc/raddb/users.prosafe";
$user    = $_REQUEST["user"];
$pass    = $_REQUEST["pass"];
$svctype = $_REQUEST["svctype"];

$command = "sudo /bin/cp /dev/null $conf_file";
exec($command);

$command = "sudo sh -c '/bin/echo \"$user Auth-Type := Local, User-Password == \\\"$pass\\\"\" >> $conf_file'";
exec($command);

if (!empty($svctype)) {
    $command = "sudo sh -c '/bin/echo -e \"\\tService-Type = $svctype\" >> $conf_file'";
    exec($command);
}

$command = "sudo /bin/cat $conf_file";
exec($command, $output, $retval);

echo "RETURN VALUE: $retval\n";
echo "OUTPUT:\n";
echo join("\n", $output);
?>
