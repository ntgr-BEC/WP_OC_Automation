/**
 *
 */
package webportal.param;

/**
 * please use URLParam.hrefSummary replace "#/dashboard/location/"; example : old is
 * WebCheck.checkHrefIcon("#/dashboard/location/"); new is WebCheck.checkHrefIcon(URLParam.hrefSummary);
 *
 * @author Netgear
 */
public class URLParam {
    public static String hrefSummary                                           = "#/dashboard/location/";
    public static String hrefWireless                                          = "#/wireless/wirelessQuickView";
    public static String hrefWirelessSettings                                  = "#/wireless/wirelessSettings";
    public static String hrefWirelessOrbiSettings                              = "#/wireless/wirelessOrbiSettings";
    public static String hrefWirelessOrbiSettingsEdit                          = "#/wireless/wirelessOrbiSettings/edit";
    public static String hrefWirelessCaptivePortal                             = "#/wireless/captivePortal";
    public static String hrefWirelessFastroaming                               = "#/wireless/fastRoaming";
    public static String hrefWirelessUrlfiltering                              = "#/wireless/urlFiltering";
    public static String hrefWirelessLoadBalancing                             = "#/wireless/loadBalancing";
    public static String hrefWirelessairBridgeGroups                           = "#/wireless/airBridgeGroups";
    public static String hrefWirelessairBridgeGroupsSummary                    = "#/wireless/airBridgeGroups/summary";
    public static String hrefWirelessairBridgeGroupsConfig                     = "#/wireless/airBridgeGroups/groupConfiguration";
    public static String hrefWirelessairBridgeGroupsDevices                    = "#/wireless/airBridgeGroups/devices";
    public static String hrefDeviceAirBridgeSummary                            = "#/devices/ab/summary";
    public static String hrefDeviceAirBridgeConfig                             = "#/devices/ab/config";
    public static String hrefDeviceAirBridgeGroup                              = "#/devices/ab/group";
    public static String hrefDeviceAirBridgeAntennaAlign                       = "#/devices/ab/antennaAlign";
    public static String hrefDeviceAirBridgednsLookup                          = "#/devices/ab/dnsLookup";
    public static String hrefDeviceAirBridgespeedTest                          = "#/devices/ab/speedTest";
    public static String hrefWiredQuickView                                    = "#/wired/quickView";
    public static String hrefClients                                           = "#/Clients/Clients";
    public static String hredTopology                                           = "#/topology/view";
    public static String hrefStorage                                           = "#/storage/storage";
    public static String hrefDevices                                           = "#/devices/dash";
    public static String hrefFirmware                                          = "#/firmware/available";
    public static String hrefWiredSetting                                      = "#/wired/VLAN";
    public static String hrefWiredSettingIpFiltering                           = "#/wired/IPFiltering/network";
    public static String hrefGroupPortConfig                                   = "#/wired/groupPortConfig";
    public static String hrefWiredLag                                          = "#/wired/LAG";
    public static String hrefSpanningTree                                      = "#/wired/spanningTree";
    public static String hrefSNMP                                              = "#/network/SNMP";
    public static String hreflocSNMP                                           = "#/network/snmp";
    public static String hrefPoESchedules                                      = "#/wired/PoESchedules";
    public static String hrefNetworkEditNetwork                                = "#/Network/EditNetwork";
    public static String hrefDevicesSwitchConnectedNeighbors                   = "#/devices/switch/connectedNeighbors";
    public static String hrefDevicesSwitchConnectedNeighborsPortConfigSummary  = "#/devices/switch/connectedNeighbours/portConfiq/summary";
    public static String hrefDevicesSwitchConnectedNeighborsPortConfigSettings = "#/devices/switch/connectedNeighbours/portConfiq/settings";
    public static String hrefDevicesSwitchStatistics                           = "#/devices/switch/statistics";
    public static String hrefDevicesSwitchPoE                                  = "#/devices/switch/PoE";
    public static String hrefDevicesSwitchTraffic                              = "#/devices/switch/traffic";
    public static String hrefDevicesSwitchIpsettings                           = "#/devices/switch/ipSettings";
    public static String hrefDevicesSwitchconfigBackup                         = "#/devices/switch/configBackup";
    public static String hrefEvent                                             = "#/notifications/all/";
    public static String hrefSyslog                                            = "#/network/SyslogConfiguration";
    public static String hrefNetworksSetup                                     = "#/network/networks";
    public static String hrefRouting                                           = "#/network/Routing";
    public static String hrefStaticRouting                                     = "#/devices/switch/staticRoute";
    public static String hrefmyDevice                                          = "#/notifications/all/";
    public static String hrefaccount                                           = "#/dashboard/account";
    public static String hreforganization                                      = "#/organization/dashboard";
    public static String hreforganizationDetail                                = "#/organization/details/";
    public static String hrefRadius                                            = "#/network/Radius";
    public static String hrefRadiusConfiguration                               = "#/wired/radiusConfiguration";
    public static String hrefManagerList                                       = "#/manager/list";
    public static String hrefInsightServices                                   = "#/insightServices";
    public static String hrefMyDevices                                         = "#/dashboard/myDevices";
    public static String hrefSatelliteAP                                       = "#/devices/accessPoint/satelliteAP";
    public static String hrefCopyConfiguration                                 = "#/organization/copyConfiguration";
    public static String hrefPaymentSubscription                               = "#/accountManagement/subScriptions";
    public static String hrefLocked                                            = "#/locked";
    public static String hrefCustomReport                                      = "#/reports/list";
    public static String hrefMobile                                            = "#/mobility/mobilityQuickView";
    public static String hrefEditNetwork                                       = "#/network/EditNetwork";
    public static String hrefpurchaseHistory                                   = "#/accountManagement/purchaseHistory";
    public static String registerPro                                           = "#/register";
    public static String hrefVPNServices                              		  = "#/accountManagement/vpnServices";
    public static String hrefOrg                                               = "#/organization/settings/devices";
    public static String hrefReports                                           = "#/reports/list";
    
    // for Routers/VPN
    public static String hrefRouters                                           = "#/routers/quickview";
    public static String hrefVPNSettings                                       = "#/routers/settings/businessVPN";
    public static String hrefOfficeRouter                                      = "#/routers/settings/officeRouter";
    public static String hrefRemoteRouter                                      = "#/routers/settings/branchBusinessRouter";
    public static String hrefICP                                               = "#/accountManagement/instantCaptivePortal";

    // for br500/br100
    public static String hrefBRSummary            = "#/devices/br/summary";
    public static String hrefBRattachedDevices    = "#/devices/br/attachedDevices";
    public static String hrefBRDHCPServers        = "#/devices/br/dhcpServers";
    public static String hrefBRIPSecVpn           = "#/devices/br/ipsec";
    public static String hrefBRWanIP              = "#/devices/br/wLanIpSettings";
    public static String hrefBRvpnGroups          = "#/devices/br/vpnGroups";
    public static String hrefBRstatistics         = "#/devices/br/statistics";
    public static String hrefBRvpnUsers           = "#/devices/br/vpnUsers";
    public static String hrefBRtraffic            = "#/devices/br/traffic";
    public static String hrefBRVlan               = "#/devices/br/vlanInUse";
    public static String hrefBRPortConfigSummary  = "#/devices/br/portConfiq/summary";
    public static String hrefBRPortConfigSettings = "#/devices/br/portConfiq/settings";
    
    // for orbi base, add by bingke.xue at 2019/5/29
    public static String hrefOrbiSummary           = "#/devices/orbi/summary";
    public static String hrefOrbiDeviceMode        = "#/devices/orbi/deviceMode";
    public static String hrefOrbiContentFiltering  = "#/devices/orbi/contentFiltering";
    public static String hrefOrbiConnectedClients  = "#/devices/orbi/connectedClients";
    public static String hrefOrbiWifiNetworks      = "#/devices/orbi/wifiNetworks";
    public static String hrefOrbiWifiSchedules     = "#/devices/orbi/wifiSchedules";
    public static String hrefOrbiLANIP             = "#/devices/orbi/lanIp";
    public static String hrefOrbiWANIP             = "#/devices/orbi/wanIp";
    public static String hrefOrbiTraffic           = "#/devices/orbi/traffic";
    public static String hrefOrbiSatellites        = "#/devices/orbi/satellites";
    public static String hrefOrbiFirmware          = "#/devices/orbi/firmware";
    public static String hrefOrbiDiagnosticMode    = "#/devices/orbi/secureDiagnosticMode";
    public static String hrefOrbiConfigBackup      = "#/devices/orbi/configBackup";
    public static String hrefOrbiRMA               = "#/devices/orbi/rma";
    //public static String hrefOrbiTroubleShoot      = "#/devices/orbi/DNSLookup";
    public static String hrefOrbiTroubleShoot      = "#/devices/orbi/troubleshoot";
    public static String hrefNetworkTroubleShoot   = "#/Troubleshoot/Troubleshoot";
    public static String hrefAPTroubleShoot        = "#/devices/accessPoint/DNSLookup";
    public static String hrefOrbinetworkSetup      = "#/devices/orbi/networkSetup";
    public static String hrefOrbidhcpServerOrbi    = "#/devices/orbi/dhcpServerOrbi";
    public static String hrefOrbicaptivePortalOrbi = "#/devices/orbi/captivePortalOrbi";
    
    // for ap
    public static String hrefDevicesApSummary              = "#/devices/accessPoint/summary";
    public static String hrefDevicesApIpsettings           = "#/devices/accessPoint/ipSettings";
    public static String hrefDevicesApPortConfiqSummary    = "#/devices/accessPoint/portConfiq/summary";
    public static String hrefDevicesApPortConfiqSettings   = "#/devices/accessPoint/portConfiq/settings";
    public static String hrefDevicesApRadioAndChannelsPage = "#/devices/accessPoint/radioChannels";
    public static String hrefAPOoklaSpeedtest              = "#/devices/accessPoint/OoklaSpeedTest";
    
    // for voucher management
    public static String hrefVoucherOrganization           = "#/voucherManagement/organisationView";
    
    //for BR
    public static String hrefDevicesBRSummary               = "#/devices/br/summary";
    public static String hrefDevicesBRClients               = "#/devices/br/PRclients";
    public static String hrefDevicesPRWanIp                 = "#/devices/br/PRWanIp";
    public static String hrefDevicesPRQos                   = "#/devices/br/PRQos";
    
    //Added by Anusha for Screen Navigation for Pro Account
    public static String hrefDeviceLeds                                       = "#/devices/accessPoint/ledSettings";
    public static String hrefDeviceNotifications                              = "#/devices/accessPoint/notifications";
    public static String hrefPurchaseHistory                                  = "#/accountManagement/purchaseHistory";
    public static String hrefcreditAllocation                                 = "#/accountManagement/creditAllocation";
    public static String hrefSubscriptionpage                                 = "#/accountManagement/subScriptionsPro";
    public static String hrefInstantCaptivePortalPro                          = "#/accountManagement/instantCaptivePortalPro";
    public static String hrefUsageBasedBilling                                = "#/accountManagement/usageBasedBilling";
    public static String hrefManageNotifications                              = "#/accountManagement/manageNotifications";
    public static String hrefOrganizationDevices                              = "#/organization/settings/devices";
    
    
    
}
