package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class SummaryElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("SummaryElement");

    // elements in banners
    public static SelenideElement  VPNbanner       = $x("//div[contains(@class,'noBridgeBlock')]//div[text()='Business VPN']");
    public static SelenideElement  CFbanner        = $x("//div[contains(@class,'noBridgeBlock')]//div[text()='Content Filtering']");
    public static SelenideElement  VPNLearnMore    = $x("//div[text()='Business VPN']/../div[contains(@class,'ButtonBlockPop')]");
    public static SelenideElement  CFLearnMore     = $x("//div[text()='Content Filtering']/../div[contains(@class,'ButtonBlockPop')]");
    public static SelenideElement  VPNClose        = $x("//div[text()='Business VPN']/../../../div[2]/span");
    public static SelenideElement  CFClose         = $x("//div[text()='Content Filtering']/../../../div[2]/span");
    
    // elements in modal
    public static SelenideElement  ModalClose        = $x("//div[contains(@style,'display: block')]//button[contains(@class,'close')]");
    public static SelenideElement  insightservices   = $x("//a[contains(@href,'insight/services')]");
    public static SelenideElement  btnModalAddRouter = $x("//div[contains(@style,'display: block')]//button[text()='Add Router to Business VPN']");
    
    
    // elements in customizeMenu
    public static SelenideElement         customizeMenu     = $x("//div[@id='darkThemeSideBarButton']");
    public static SelenideElement         verticalToggle    = $x("//h5[text()='Vertical Menu']/..//span[@class='cstmSlider cstmRound']");
    public static SelenideElement         customizeMen      = $x("//div[@id='openCommonMenuBar']/div/div[@data-toggle='dropdown']/img");
    public static ElementsCollection      leftMenuList      = $$x("//div[@id='openCommonMenuBar']//ul[@class='nav navbar-nav']/li");
    public static SelenideElement         closeCustomizeMenu= $x("//div/img[@src='assets/img/whiteCross.png']");
    public static SelenideElement         routerVpnArrow    = $x("//span[text()='Router/VPN']/..//i");
    public static SelenideElement     routerVpnSubMenusmry  = $x("//span[text()='Router/VPN']/../../ul//a[text()='Summary']");
    public static SelenideElement     routerVpnSubMenusting = $x("//span[text()='Router/VPN']/../../ul//a[text()='Settings']");
    public static SelenideElement         MobileArrow       = $x("//span[text()='Mobile']/..//i");
    public static SelenideElement     MobileSubMenusmry     = $x("//span[text()='Mobile']/../../ul//a[text()='Summary']");
    public static SelenideElement     MobileSubMenusting    = $x("//span[text()='Mobile']/../../ul//a[text()='Settings']");
    public static SelenideElement         WirelessArrow     = $x("//span[text()='Wireless']/..//i");
    public static SelenideElement     wirelessSubMenusmry   = $x("//span[text()='Wireless']/../../../ul//a[text()='Summary']");
    public static SelenideElement     wirelessSubMenusting  = $x("//span[text()='Wireless']/../../../ul//a[text()='Settings']");
    public static SelenideElement         WiredArrow        = $x("//span[text()='Wired']/..//i");
    public static SelenideElement     wiredSubMenusmry      = $x("//span[text()='Wired']/../../../ul//a[text()='Summary']");
    public static SelenideElement     wiredSubMenusting     = $x("//span[text()='Wired']/../../../ul//a[text()='Settings']");
    public static SelenideElement         DeviceArrow       = $x("//span[text()='Devices']/..//i");
    public static SelenideElement         wirelesssmrytxt   = $x("//div/h2[text()='Traffic Bandwidth']");
    
    
    //pratik for clienttraverse
    public static SelenideElement   noOfconnectedClients = $x("//ul//li[5]//h3[text()='1']");
    public static SelenideElement   connectedClientsText = $x("//ul//li[5]//p[text()='Connected Clients']");
    //addedbypratikforMovedevice
    public SelenideElement nodevicesAvailable  = $x("(//p[text()='No device available'])[1]");
    public SelenideElement nodevicesAvailable1  = $x("(//p[text()='No device available'])[2]");
    public static SelenideElement     VerticalMenuToggle    = $x("//*[@id=\"enableVerticalMenu\"]/..");
    public static SelenideElement     ArrowOnLeft           = $x("//*[@class=\"verticalToggleMenu toplogyRectangle d-flex align-items-center justify-content-center\"]");
}
