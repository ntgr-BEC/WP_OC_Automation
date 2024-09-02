package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class WiredQuickViewElement extends MyCommonAPIs {
    final static Logger       logger              = Logger.getLogger("WiredQuickViewElement");
    public SelenideElement    btnQuickView        = $("#wiredPoeQuickView");
    public SelenideElement    btnDetailedView     = $("#wiredPoeDetailedView");
    public SelenideElement    txtDevicesTable     = $("#hDeviceswiredQuickView");
    public ElementsCollection txtPoeSwitchName    = $$("#divWidgetLoc table tbody tr td:first-child");
    public ElementsCollection txtDeviceSwitchName = $$("#tblDeviceList tbody tr td:first-child");
    public ElementsCollection txtDeviceSwitchIp   = $$("#tblDeviceList tbody tr td:nth-child(7)");
    public String             basicList           = "//td[text()='%s']/..";
    public String             uptime              = basicList + "/td[8]";
    
    /**
     * @param  serialNumber
     * @return
     */
    public SelenideElement editModule(String serialNumber) {
        SelenideElement Ssid = $x("(//td[text()='" + serialNumber + "']/..//p)[2]");
        return Ssid;
    }
    
    /**
     * @param  serialNumber
     * @return
     */
    public SelenideElement enterDevice(String serialNumber) {
        SelenideElement device = $x("(//td[text()='" + serialNumber + "']/..//img[contains(@src, 'edit')])[2]");
        return device;
    }
    
    public SelenideElement lastKnownInfo              = $x("//p[text()='Last known information']");
    public SelenideElement lastKnownInfostarsym       = $x("//span[text()='*' and @class='colorRed']");
    public SelenideElement upTimedeviceDashVerify     = $x("//td[@id='tdUpTimedevicesDash0']");
    public SelenideElement upTimedeviceDashVerify1    = $x("//p[contains(text(),'Days')]");
  //AddedByPratikforMoveDevice
    public SelenideElement  noDevicesonWiredPage      = $x("(//p[text()='No Data Available'])[1]");
    public SelenideElement  noDevicesonWiredPage1     = $x("(//p[text()='No Data Available'])[2]");
    public SelenideElement  noDevicesonWiredPage2     = $x("//td[text()='No device available']");
    
    public SelenideElement inputName(String text) {
        SelenideElement inputname = $x("//td[text()='" + text + "']/../td[1]//input");
        return inputname;
    }
    
    public SelenideElement editName(String text) {
        SelenideElement nameelement = $x("//td[text()='" + text + "']/../td[1]");
        return nameelement;
    }
    
    public SelenideElement inputnameyes(String text) {
        SelenideElement yes = $x("(//td[text()='" + text + "']/../td[1]//button)[1]/i");
        return yes;
    }
}
