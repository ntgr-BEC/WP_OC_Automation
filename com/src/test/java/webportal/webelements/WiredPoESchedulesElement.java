package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class WiredPoESchedulesElement extends MyCommonAPIs {
    final static Logger logger = Logger.getLogger("WiredPoESchedulesElement");

    public SelenideElement btnAdd           = $("#divAdSchwiredPoeSch");
    public SelenideElement txtScheduleName  = $("#scheduleName");
    public SelenideElement cbAllDay         = $("#allDay");
    public SelenideElement txtStartTime     = $("#startTime");
    public SelenideElement txtEndTime       = $("#endTime");
    public SelenideElement lbRecurrenceType = $("#recurrenceType");
    public SelenideElement txtStartDate     = $("#startDate");
    public SelenideElement txtEndDate       = $("#endDate");
    public SelenideElement btnCancel        = $("#btnModDefultwiredPoeSch");
    public SelenideElement btnSavePorts     = $("#btnPriwiredPoeSch");
    public String          btnSavePortsid     = "btnPriwiredPoeSch";
    public SelenideElement btnSave          = $("#btnSaveSchwiredPoeSch");
    public String          staWeeklyDay     = "#ulWebBloCkWiredPoeSch0 li:nth-child(%s)";

    public SelenideElement btnSliderTime = $("#divOnOfSettWiredPoeSch  div[class*=background]");

    public String          ecPoEList       = "[id*=divOvrFlwWiredPoeSch] h3";
    public String          ecPoEListStaPre = "#hOvrFlwWiredPoeSch";
    public String          ecPoEListSta    = ecPoEListStaPre + "0";
    public String          ecPoEListBtnPre = "#iconOvrFlwWiredPoeSch";
    public String          ecPoEListBtn    = ecPoEListBtnPre + "0";
    public SelenideElement btnItemEdit     = $("[id*=divOvrFlwWiredPoeSch] button:first-child");
    public SelenideElement btnItemEditPort = $("[id*=divOvrFlwWiredPoeSch] button:nth-of-type(2)");
    public SelenideElement btnItemDelete   = $x("//button[@id='Btn3PoprPoeSch0']");
//    public SelenideElement btnItemDelete   = $("[id*=divOvrFlwWiredPoeSch] button:last-child");
    public SelenideElement btnYes          = $("div[style*=block] button:last-child");
    public String          cbWeeklyDay     = "#ulWeekListWiredPoeSch li:nth-child(%s)";

    public SelenideElement btnOK = $("#successModlWiredPoeSch");
    public SelenideElement warningYes = $x("//button[text()='Yes']");
    
    
    public String          savebtn       = "btnUpdateRghtWiredPOeSch";
    
    public void setTimeRange() {
        btnSliderTime.click(btnSliderTime.getSize().width - 20, 0);
        btnSliderTime.click(0 + 20, 0);
    }

    public String getDeviceString(String devName) {
        return String.format("//span[contains(text(),'%s')]/../../../..", devName);
    }
    
    public void waitADevice() {
        waitElement(".in .box-shadow li[id$=port_1]");
    }
    
    /**
     * @param  devName
     * @param  portId
     *                 1~n
     * @return
     */
    public SelenideElement getPort(String devName, String portId) {
        return getDevicePort(getDeviceString(devName), portId);
    }
    
    public void clickPoEPort(String devName, String portIndex) {
        getPort(devName, portIndex).click();
    }

    public void clickAllPoEPort(String portIndex) {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            getPort(DevicesDashPageElements.mapDeviceList.get(sNo).get("Name"), portIndex).click();
        }
    }

    /**
     * make sure at least one poe device is loaded
     */
    public void clickSavePort() {
        click(btnSavePorts);
        waitReady();
        waitADevice();
        waitReady();
    }
    
    public void clickSaveBtn() {
        MyCommonAPIs.clickElementIdViaJs(savebtn);
        waitReady();
    }
}
