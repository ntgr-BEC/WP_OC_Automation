package businessrouter.webelements;

import webportal.param.WebportalParam;


public class BrURLParam {
    public static String                               Dashboard;
    public static String                               FirewallBasicSetup;
    public static String                               FirewallTrafficRules;
    public static String                               FirewallAccessControl;
    public static String                               FirewallPortForwardingandtriggering;
    public static String                               SecuritySchedule;
    public static String                               SecurityBlockSites;
    public static String                               BlockServices;
    public static String                               VLAN;
    public static String                               DynamicDNS;
    public static String                               StaticRoutes;
    public static String                               IPv6;
    public static String                               QoSSetup;
    public static String                               UPnP;
    public static String                               BackupSettings;
    public static String                               TrafficMeter;
    public static String                               FirmwareUpdate;
    public static String                               Logs;
    public static String                               SetupWizard;
    public static String                               DeviceNameSetup;
    public static String                               WANSetup;
    public static String                               LANSetup;
    public static String                               SetPassword;
    public static String                               AttachedDevices;
    public BrURLParam() {
        Dashboard = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"dashboard";
        FirewallBasicSetup = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"firewallsetup";
        FirewallAccessControl = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"trafficrules";
        FirewallPortForwardingandtriggering ="https://"+ WebportalParam.serverUrl+"/index.htm#/"+"portForwardOrTrigger";
        SecuritySchedule ="https://"+ WebportalParam.serverUrl+"/index.htm#/"+"blockSites";
        SecurityBlockSites = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"blockServices";
        VLAN = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"vlan";
        DynamicDNS ="https://"+ WebportalParam.serverUrl+"/index.htm#/"+"dnsddns";
        StaticRoutes ="https://"+ WebportalParam.serverUrl+"/index.htm#/"+"staticRoutes";
        IPv6 ="https://"+ WebportalParam.serverUrl+"/index.htm#/"+"ipv6";
        QoSSetup = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"QoSSetup";
        UPnP = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"upnp";
        BackupSettings = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"ad_backupSettings";
        TrafficMeter = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"trafficMeter";
        FirmwareUpdate = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"ad_firmwareUpdate";
        Logs = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"ad_logs";
        SetupWizard = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"setupWizard";
        DeviceNameSetup ="https://"+ WebportalParam.serverUrl+"/index.htm#/"+"editDeviceName";
        WANSetup = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"wan";
        LANSetup = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"lansetup";
        SetPassword = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"ad_setPassword";
        AttachedDevices = "https://"+ WebportalParam.serverUrl+"/index.htm#/"+"attachedDevices";
    }

}
