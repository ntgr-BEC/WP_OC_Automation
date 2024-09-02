package webportal.SanityTestCase.OrbiSanity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface Config {
    String[]     baseIP             = { "172.16.1.1" };
    String[]     baseMAC            = { "14:59:c0:50:06:99" };
    String[]     serialNum          = { "52K58A5G0148F" };
    int[]        numOfSatellites    = { 1 };
    int[]        numOfClients       = { 1 };
    String       newSatelliteName   = "new satellite name";
    String[]     vlanName           = { "new vlan" };
    String[]     vlanId             = { "100" };
    String[]     startIp            = { "172.16.100.1" };
    String[]     dhcpVlan           = { "Employee" };
    String[]     dhcpip             = { "172.16.20.1" };
}
