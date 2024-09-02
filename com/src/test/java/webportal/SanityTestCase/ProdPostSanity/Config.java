package webportal.SanityTestCase.ProdPostSanity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface Config {
    int          aNumber            = 44 + new Random().nextInt(100);
    String       dataVlanID         = String.format("%s", 1234 + aNumber);
    String       aclVlanID          = String.format("%s", 2234 + aNumber);
    String       macACL             = "11:22:33:11:22:33";
    String       ipACL              = "11.22.33.44";
    String       ipRouting          = "11.22.33." + aNumber;
    String       dataVlanPortId     = "1";
    String       lagPortId          = "3";
    String       poePortId          = "4";
    String       groupPortId        = "5";
    String       radiusIp1          = "10.11.128." + aNumber;
    String       radiusIp2          = "10.12.138." + aNumber;
    String       snmpIp             = "10.13.148." + aNumber;
    String       lagName            = "sanityLag" + aNumber;
    String       snmpCommunity      = "test@snmp" + aNumber;
    String       rateLimit          = "90";
    String       stormControl       = "80";
    String       poeName            = "sanityPoE" + aNumber;
    String       poeTimeStart       = "21:19";
    String       poeTimeEnd         = "6:16";
    String       syslogIp           = "10.14.158." + aNumber;
    int          syslogPort         = 51400;
    List<String> lsPortMirrorDevice = new ArrayList<String>(2);
    String[]     portMirrorSrcports = { "6" };
    String[]     portMirrorDstports = { "7" };
    List<String> lsPoeSettingDevice = new ArrayList<String>(2);
}
