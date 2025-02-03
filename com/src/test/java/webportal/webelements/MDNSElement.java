package webportal.webelements;

import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import util.MyCommonAPIs;

public class MDNSElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceBRVlanElement");

    public SelenideElement editNetwork                = $x("//*[@id=\"headerLocImg\"]");
    public SelenideElement MDNSGateway                = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[3]/div[9]/a");
    public SelenideElement selectGateway              = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div[2]/div[1]/div[2]/div/div[1]/select");
    public SelenideElement clickMDNS                  =  $x("//*[@id=\"enableBlackList\"]//../span");
    public SelenideElement checkMDNS                  =  $x("//*[@id=\"enableBlackList\"]");
    public SelenideElement OkGotIt                    =  $x("//*[@id=\"mdnsFirmwareInfo\"]/div/div/div[3]/button");
    public SelenideElement AddPolicy                  =  $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div[1]/div/div/div/span");
    public SelenideElement policyName                 =  $x("//*[@id=\"policyName\"]");
    public SelenideElement sharedService              =  $x("//*[@id=\"selectOption\"]");
    public SelenideElement IPAddress                  =  $x("//*[@id=\"ipAddress\"]");
    public SelenideElement ServiceVLAN                =  $x("//*[@id=\"addNewPolicy\"]/div/div/form/div[3]/div[4]/div/div");
    public SelenideElement VLAN1                      =  $x("//*[@id=\"displayBlock\"]/li/div[2]/div/div[1]/div/p[1]");
    public SelenideElement AllWirelessNetwork         =  $x("//*[@id=\"addNewPolicy\"]/div/div/form/div[3]/div[5]/div/div[1]/label/i");
    public SelenideElement Reset                      =  $x("//*[@id=\"addNewPolicy\"]/div/div/form/div[4]/button[1]");
    public SelenideElement Add                        =  $x("//*[@id=\"addNewPolicy\"]/div/div/form/div[4]/button[2]");
    public SelenideElement closepopup                 =  $x("//*[@id=\"addNewPolicy\"]/div/div/form/div[1]/button/img");
    public SelenideElement clickDiscoveredServices    =  $x("//*[@id=\"divConSecCOlMdWirSett\"]/div[2]/div[2]/div[1]/div/ul/li[2]");
    public SelenideElement Refresh                    =  $x("//*[@id=\"divConSecCOlMdWirSett\"]/div[2]/div[2]/div[1]/div/div/div/span");
    public SelenideElement Warrning                   =   $x("(//*[@class=\"modal-body\"])[10]");
    public SelenideElement Warrning1                  =  $x("//button[text()='OK']/../../../..//p[@class=\"fontSemiBold\"]");
    public SelenideElement settingsorquickview        = $x("//*[@id=\"divLocBarwirquickView\"]/div[2]/a");
    public SelenideElement addssid                    = $("#divssidWirSett");
    public SelenideElement okbutton1                   = $x("(//*[@id=\"divMainWirSett\"]/div[7]/div/div/div/button)[2]");
    public SelenideElement okbutton                   = $x("//div[contains(@class,'mDNSGatewayModalWarning')]//*[text()='OK']");
    public SelenideElement okbutton2                   = $x("//div[contains(@class,'mDNSGatewayModalWarning')]//*[text()='OK']");
    public SelenideElement okpolicy                   = $x("(//*[@id=\"myModal\"]/div/div/div[3]/button)[3]");
    public SelenideElement Policyexits                = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div[2]/div[2]/div[2]/div/div/div[1]/table/tbody");
    public SelenideElement deletessidyes                = $x("//button[text() = 'delete']");
    public SelenideElement Policyexitserror                = $x("//button[text() = 'delete']");
    public SelenideElement        ssidlistone               = $("#NetHmWirSett0");
    public String                 ssidListTable             = "#tbdyWirSett";
    public SelenideElement        ssidListTable1            = $x("#tbdyWirSett");
    protected ElementsCollection  ssidlist                  = $$(ssidListTable);
    
    public SelenideElement editMdNS(String text) {
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../../tr/td[7]/p");
        return Ssid;
    }
    public SelenideElement deleteMDNS(String text) {
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../../tr/td[7]/p/i[2]");       
        return Ssid;
    }
    public SelenideElement editMDNS(String text) {
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../../tr/td[7]/p/i[1]/img");       
        return Ssid;
    }
    
    //added by ravi
    public SelenideElement warning                  =$x("(//*[text()=\"Warning\"])[4]");
    public SelenideElement enablemdns               =$x("//*[@id=\"divConSecCOlMdWirSett\"]/div[2]/div[1]/div[2]/div/div/div/label/span");
    public SelenideElement natssidwarning           =$x("//*[@id=\"divMainWirSett\"]/div[7]/div/div/div[2]/ul/li[3]");
    //addedbyPratik
    public SelenideElement gatewaySelect            = $x("//select[contains(@class,'form-control inputTextField no-padding')]");
    public SelenideElement gatewayOkayGotItBtn      = $x("(//button[text()='OK. Got it'])[2]");
    public SelenideElement mdnsgatewayOkayBtn       = $x("(//button[text()='OK'])[4]");
    public SelenideElement mdnsWarningMessageWhenNoSSID  = $x("//p[contains(text(),'To enable mDNS Gateway, it requires the Location to have at least 1')]");
    public SelenideElement Okgotit                   =   $x("(//*[text()='OK. Got it'])[2]");
}
