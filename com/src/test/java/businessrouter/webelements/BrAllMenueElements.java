package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import webportal.param.WebportalParam;
public class BrAllMenueElements {
    /*layer 1*/
    public SelenideElement basic   = $x("//*[@id=\"navBar_2\"]/li[1]");
    public SelenideElement advanced   = $x("//*[@id=\"navBar_2\"]/li[2]");
    /*layer 2*/
    public SelenideElement Dashboard       = $x("//*[@id=\"sideMenu_2\"]/li[1]/span");
    public SelenideElement SetupWizard     = $x("//*[@id=\"sideMenu_2\"]/li[2]/span");
    public SelenideElement Setup           = $x("//*[@id=\"sideMenu_2\"]/li[3]/div/span/span");

    public SelenideElement AttachedDevices = $x("//*[@id=\"sideMenu_2\"]/li[4]/span");
    public SelenideElement Firewall        = $x("//*[@id=\"sideMenu_2\"]/li[2]/div/span/span");
    public SelenideElement Security        = $x("//*[@id=\"sideMenu_2\"]/li[3]/div/span/span");
    public SelenideElement VLAN            = $x("//*[@id=\"sideMenu_2\"]/li[4]/span");
    public SelenideElement DynamicDNS      = $x("//*[@id=\"sideMenu_2\"]/li[6]/span");
    public SelenideElement StaticRoutes    = $x("//*[@id=\"sideMenu_2\"]/li[7]/span");
    public SelenideElement IPv6            = $x("//*[@id=\"sideMenu_2\"]/li[8]/span");
    public SelenideElement QoSSetup        = $x("//*[@id=\"sideMenu_2\"]/li[11]/span");
    public SelenideElement UPnP            = $x("//*[@id=\"sideMenu_2\"]/li[12]/span");
    public SelenideElement BackupSettings  = $x("//*[@id=\"sideMenu_2\"]/li[13]/span");
    public SelenideElement TrafficMeter    = $x("//*[@id=\"sideMenu_2\"]/li[14]/span");
    public SelenideElement FirmwareUpdate  = $x("//*[@id=\"sideMenu_2\"]/li[15]/span");
    public SelenideElement Logs            = $x("//*[@id=\"sideMenu_2\"]/li[16]/span");
    public SelenideElement IPSecVPN        = $x("//*[@id=\"sideMenu_2\"]/li[10]/span");
    /*Layer 3*/
    public SelenideElement WANSetup            = $x("//*[@id=\"setup$Menu\"]/li[2]/span");
    public SelenideElement LANSetup            = $x("//*[@id=\"setup$Menu\"]/li[3]/span");
    public SelenideElement DeviceNameSetup     = $x("//*[@id=\"setup$Menu\"]/li[1]/span");
    public SelenideElement SetPassword         = $x("//*[@id=\"setup$Menu\"]/li[4]/span");
    public SelenideElement BasicSetup          = $x("//*[@id=\"firewall$Menu\"]/li[1]/span");
    public SelenideElement TrafficRules        = $x("//*[@id=\"firewall$Menu\"]/li[2]/span");
    public SelenideElement AccessControl       = $x("//*[@id=\"firewall$Menu\"]/li[3]/span");
    public SelenideElement PortForwardingandTriggering       = $x("//*[@id=\"firewall$Menu\"]/li[4]/span");
    public SelenideElement Schedule         = $x("//*[@id=\"security$Menu\"]/li[1]/span");
    public SelenideElement BlockSites       = $x("//*[@id=\"security$Menu\"]/li[2]/span");
    public SelenideElement BlockServices    = $x("//*[@id=\"security$Menu\"]/li[3]/span");

    
    public SelenideElement SetupBr100           = $x("//*[@id=\"sideMenu_2\"]/li[2]/div/span/span");
    public SelenideElement BackupSettingsbr100  = $x("//*[@id=\"sideMenu_2\"]/li[6]/span");
    public SelenideElement FirmwareUpdateBr100  = $x("//*[@id=\"sideMenu_2\"]/li[1]/span");
    public SelenideElement wan1br100 = $x("//div[text()='WAN1']");
    public SelenideElement TrafficRulesbr100        = $x("//*[@id=\"fireWall$Menu\"]/li[2]/span");   
    public SelenideElement PortForwardingandTriggeringbr100       = $x("//*[@id=\"fireWall$Menu\"]/li[3]/span");
    public SelenideElement StaticRoutesbr100    = $x("//*[@id=\"sideMenu_2\"]/li[5]/span");
    public SelenideElement AttachedDevicesbr100 = $x("//*[@id=\"sideMenu_2\"]/li[4]/span");
   

}
