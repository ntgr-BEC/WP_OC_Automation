/**
 *
 */
package orbi.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author bingke.xue
 *
 */
public class DNIOrbiAdvancedMenuElement extends MyCommonAPIs {
    public static SelenideElement advancedLabel   = $("#advanced_label");
    //first level menu
    public static SelenideElement advancedHome    = $("#adv_home");
    public static SelenideElement advSetupWizard     = $("#adv_setup_wizard");
    public static SelenideElement advSetupBtn  = $("#setup_bt");
    public static SelenideElement advSecurityBtn  = $("#security_bt");
    public static SelenideElement advAdministratorBtn  = $("#admin_bt");
    public static SelenideElement advSetupLabel  = $("#advanced_bt");
    
    // second level menus for SetupWizard
    public static SelenideElement SubMenuInternetSetup  = $("#internet_setup");
    public static SelenideElement SubMenuWirelessSetup  = $("#wireless");    
    public static SelenideElement SubMenuGuestPortal  = $("#guest_portal");
    public static SelenideElement SubMenuWanSetup   = $("#wan");
    public static SelenideElement SubMenuLanSetup          = $("#lan");
    public static SelenideElement SubMenuDeviceName = $("#devname");
    
    // second level menus for Security
    public static SelenideElement SubMenuAccessControl   = $("#access_control");
    public static SelenideElement SubMenuBlockSites  = $("#block_site");
    public static SelenideElement SubMenuBlockServices  = $("#block_services");
    public static SelenideElement SubMenuSchedule   = $("#schedule ");
    public static SelenideElement SubMenuEmail   = $("#email div");
    
    
    // second level menus for Advanced setup.
    public static SelenideElement SubMenuWirelessSettings   = $("#wladv");    
    public static SelenideElement SubMenuRouter_AP_Mode   = $("#rae");
    public static SelenideElement SubMenuPortForwarding = $("#forwarding_triggering");
    public static SelenideElement SubMenuDynaMicDNS = $("#dns");
    public static SelenideElement SubMenuVpnServices = $("#vpn");
    public static SelenideElement SubMenuStaticRouters = $("#static");
    public static SelenideElement SubMenuRemoteManagement = $("#remote");
    public static SelenideElement SubMenuUpnp = $("#upnp");
    public static SelenideElement SubMenuIpv6 = $("#ipv6");
    public static SelenideElement SubMenuTrafficMeter = $("#traffic");
    public static SelenideElement SubMenuVlanBridge = $("#vlan");
    public static SelenideElement SubMenuSyncBtn = $("#syncbtn");
    
    // second level menus for Administrators.
    public static SelenideElement SubMenuRouterStatus = $("#status");
    public static SelenideElement SubMenulogs = $("#log");
    public static SelenideElement SubMenuAttachedDevices = $("#attached");
    public static SelenideElement SubMenuBackupsettings = $("#bak_set");    
    public static SelenideElement SubMenuSetPwd = $("#passwd");
    public static SelenideElement SubMenuNTPSettings = $("#ntp"); 
    public static SelenideElement SubMenuFirmwareUpdate = $("#upgrade");
    public static SelenideElement SubMenuSupport = $("#support_debug");

}
