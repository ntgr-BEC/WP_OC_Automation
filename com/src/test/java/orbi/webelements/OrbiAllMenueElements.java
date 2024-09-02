package orbi.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;


public class OrbiAllMenueElements {
    /*layer 1*/
    public SelenideElement basic   = $("#basic_label");
    public SelenideElement advanced   = $("#advanced_label");
    public SelenideElement    frame=        $("#topframe")  ;      
    /*layer 2*/
    public SelenideElement AdvancedHome       = $("#adv_home");
    public SelenideElement SetupWizard     = $x("//*[@id=\"sideMenu_2\"]/li[2]/span");
    public SelenideElement Setup           = $("#setup");
    
    public SelenideElement Home           = $("#basic_home");
    public SelenideElement Internet       = $("#basic_internet");

    public SelenideElement AttachedDevices = $("#basic_attached");
    public SelenideElement Firewall        = $x("//*[@id=\"sideMenu_2\"]/li[2]/div/span/span");
    public SelenideElement Security        = $("#security");
    public SelenideElement Administration  = $x("//*[@id='admin' or @id='admin_bt']");
    public SelenideElement Wireless        = $("#wireless");
    public SelenideElement AdvancedSetup   = $("#advanced_bt");
    public SelenideElement GuestPortal        = $("#tab4");
    public SelenideElement GuestPortal60        = $("#basic_guest_portal");
    public SelenideElement VLAN            = $("#advanced_vlan");
    public SelenideElement DynamicDNS      = $("#advanced_ddns");
    public SelenideElement StaticRoutes    = $x("//*[@id=\"sideMenu_2\"]/li[7]/span");
    public SelenideElement IPv6            = $("#advanced_ipv6");
    public SelenideElement QoSSetup        = $x("//*[@id=\"sideMenu_2\"]/li[11]/span");
    public SelenideElement UPnP            = $x("//*[@id=\"sideMenu_2\"]/li[12]/span");
    public SelenideElement BackupSettings  = $("#admin_bakup");
    public SelenideElement TrafficMeter    = $("#advanced_traffic");
    public SelenideElement FirmwareUpdate  = $x("//*[@id='admin_upgrade' or @id='upgrade']");
    public SelenideElement Logs            = $("#admin_logs");
    public SelenideElement Support            = $("#admin_support_debug");
    public SelenideElement IPSecVPN        = $x("//*[@id=\"sideMenu_2\"]/li[10]/span");
    public SelenideElement VPNservice       = $("#vpn");
    
    public SelenideElement mDNS       = $x("//*[@id='mdns']");
    
    
    /*Layer 3*/
    public SelenideElement WANSetup            = $("#wan_setup");
    public SelenideElement LANSetup            = $("#lan_setup");
    public SelenideElement DeviceName          = $("#advanced_devname");
    public SelenideElement SetPassword         = $("#admin_setpassword");
    public SelenideElement BasicSetup          = $x("//*[@id=\"firewall$Menu\"]/li[1]/span");
    public SelenideElement TrafficRules        = $x("//*[@id=\"firewall$Menu\"]/li[2]/span");
    public SelenideElement PortForwardingandTriggering       = $("#advanced_pf_pt");
    public SelenideElement RemoteManagement    =$("#advanced_remote");
    
    public SelenideElement Schedule         = $("#security_schedule");
    public SelenideElement BlockSites       = $("#security_block_site");
    public SelenideElement BlockServices    = $("#security_block_service");
    public SelenideElement AccessControl       = $("#security_access_control");
    public SelenideElement MacAcl       = $("#security_mac_acl");
    public SelenideElement Eemail       = $("#security_email");
    public SelenideElement ntpsettings       = $("#ntp_debug");
    
    
    public SelenideElement SetupBr100           = $x("//*[@id=\"sideMenu_2\"]/li[2]/div/span/span");
    public SelenideElement BackupSettingsbr100  = $x("//*[@id=\"sideMenu_2\"]/li[6]/span");
    public SelenideElement FirmwareUpdateBr100  = $x("//*[@id=\"sideMenu_2\"]/li[1]/span");
    public SelenideElement wan1br100 = $x("//div[text()='WAN1']");
    public SelenideElement TrafficRulesbr100        = $x("//*[@id=\"fireWall$Menu\"]/li[2]/span");   
    public SelenideElement PortForwardingandTriggeringbr100       = $x("//*[@id=\"fireWall$Menu\"]/li[3]/span");
    public SelenideElement StaticRoutesbr100    = $x("//*[@id=\"sideMenu_2\"]/li[5]/span");
    public SelenideElement AttachedDevicesbr100 = $x("//*[@id=\"sideMenu_2\"]/li[4]/span");
    
    //foot
    public SelenideElement FootOnlineSupport = $x("//*[@id=\"footer\"]//a[@href=\"http://support.netgear.com\"]");
    public SelenideElement FootDocumentation = $x("//*[@id=\"footer\"]//a[@href=\"http://downloadcenter.netgear.com/\"]");
    public SelenideElement SearchHelpInput = $x("//input[@name='search']");
    public SelenideElement SearchButton = $("#search_button");
    public SelenideElement FootGNUGPL   = $x("//*[@id=\"footer\"]//a[@href=\"license.htm\"]");
    public SelenideElement FootImg = $x("//div[@id='footer']//img[@src='image/footer.png']");
    
    //Language
    public SelenideElement LanguageSelect = $("#language");
    public SelenideElement LanguageSelectOption(String OptionValue) {
        SelenideElement ele = $x("//select[@id=\"language\"]/option[@value=\""+OptionValue+"\"]");
        return ele;
    }
    public SelenideElement LanguageConfirmYes = $x("//input[@name='yes']");
    

}
