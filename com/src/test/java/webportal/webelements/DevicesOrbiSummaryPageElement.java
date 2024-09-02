/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author bingke.xue
 *
 */
public class DevicesOrbiSummaryPageElement extends MyCommonAPIs {
    public static SelenideElement connectStatus   = $("#pFOntRegSumm");
    
    public static SelenideElement routerModeIcon    = $(".RouterModeIcon");
    public static SelenideElement apModeIcon    = $(".ApModeIcon");
    public static SelenideElement clientNumTotal     = $("#divChrtClientsColDevApClientOS h1");
    public static String    clientsNumstring    = "//ul[@class='data-availablity'/li(%d)//span";
    public static SelenideElement clientWindowsNum  = $x(String.format(clientsNumstring, 1));
    public static SelenideElement clientLinuxNum  = $x(String.format(clientsNumstring, 2));
    public static SelenideElement clientAndroidNum  = $x(String.format(clientsNumstring, 3));
    public static SelenideElement clientMACNum  = $x(String.format(clientsNumstring, 4));
    public static SelenideElement clientiOSNum  = $x(String.format(clientsNumstring, 5));
    public static SelenideElement clientOthersNum  = $x(String.format(clientsNumstring, 6));
    
    public static SelenideElement        name           = $("#devName");
    public static SelenideElement        serialNumber   = $("#divSeNoSumm");
    public static SelenideElement        model          = $("#divVlModSumm");
    public static SelenideElement        baseMACAddress = $("#ddSNoSumm");
    public static SelenideElement        upTime         = $("#ddCOunSumm");
    public static SelenideElement        srsConnectedNum      = $("#divIPAPSumm");
    public static SelenideElement        traffic        = $("#divfwSumm");
    public static SelenideElement        iPAddress      = $("#divClintSumm ");
    public static SelenideElement        firmware       = $("#divWlanOneClnSumm div");
    public static SelenideElement        clientNum       = $("#divUpTimeSumm");
    
    public static SelenideElement  Client2_4GNum   = $("#divWlanOneClnSumm");
    public static SelenideElement        Client5GNum      = $("#divWlanOneClnSumm");
    public static SelenideElement  btnActionDelete = $x("//*[@data-tooltip=\"Delete\"]");
    public static SelenideElement  btnActionReboot = $x("//*[@data-tooltip=\"Reboot\"]");
    public static SelenideElement  btnActionShare = $x("//*[@data-tooltip=\"Share\"]");
    
    public static SelenideElement  btnContinue = $x("//div[contains(@style,'display: block')]//*[contains(@class,'btn-danger')]");
    
    // elements in banners
    public static SelenideElement  VPNbanner       = $x("//div[contains(@class,'noBridgeBlock')]//div[text()='Business VPN']");
    public static SelenideElement  CFbanner        = $x("//div[contains(@class,'noBridgeBlock')]//div[text()='Content Filtering']");
    public static SelenideElement  VPNLearnMore    = $x("//div[text()='Business VPN']/../div[contains(@class,'ButtonBlockPop')]");
    public static SelenideElement  CFLearnMore     = $x("//div[text()='Content Filtering']/../div[contains(@class,'ButtonBlockPop')]");
    public static SelenideElement  VPNClose        = $x("//div[text()='Business VPN']/../../../div[2]/span");
    public static SelenideElement  CFClose         = $x("//div[text()='Content Filtering']/../../../div[2]/span");
    
    
    // elements in modal
    public static SelenideElement  ModalClose      = $x("//div[contains(@style,'display: block')]//button[contains(@class,'close')]");
    public static SelenideElement  insightservices = $x("//a[contains(@href,'insight/services')]");
    public static SelenideElement  btnModalAddRouter = $x("//div[contains(@style,'display: block')]//button[text()='Add Router to Business VPN']");
    
}
