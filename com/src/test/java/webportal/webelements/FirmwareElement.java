package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class FirmwareElement extends MyCommonAPIs {
    final static Logger logger = Logger.getLogger("FirmwareElement");

    public SelenideElement txtSWName       = $("#tbodyneedUpdate td:nth-child(1)");
    public SelenideElement txtSWVerisonCur = $("#tbodyneedUpdate td:nth-child(2)");
    public SelenideElement txtSWVerisonNew = $("#tbodyneedUpdate td:nth-child(3)");

    public String sUpdatesRow  = "#tbodyneedUpdate tr td";
    public String sUptoDateRow = "#divUpdateDetails tbody tr";
    public String sOfflineRow  = "#divUpdateDetailsTwo tbody tr";

    public boolean deviceHasUpdate() {
        if ($$(sUpdatesRow).size() > 1)
            return true;
        else
            return false;
    }

    public SelenideElement btnUpdateAll = $("#hopen .UpdateAllClick");
    public SelenideElement btnUpdate    = $("#pSerialNo0");
    public SelenideElement btnYes       = $(".firmwareUpgrade.in button:last-child");
    public SelenideElement btnYesAll    = $(".updateAllFirmwareModal button:last-child");
    public SelenideElement btnWarnYes   = $(".warningModal.in button:last-child");

    public SelenideElement txtUpdatesAvailable = $(".UpdateCount");
    public SelenideElement txtUpdateStatus     = $("#spanupdateStatus0");
    public SelenideElement txtUpdateProgress   = $("tbody .progress-bar");
    

    public SelenideElement spanEnable         = $x("(//span[contains(@class,'cstmSlider')])[4]");

    public SelenideElement divStartDate       = $x("//h5[text()='Start Date']/../div");
    public SelenideElement divStartTime       = $x("//h5[text()='Start Time']/../input");
    public SelenideElement divEndDate         = $x("//h5[text()='End Date']/../div");
    public SelenideElement divEndTime         = $x("//h5[text()='End Time']/../input");
    public SelenideElement today              = $x("//div[contains(@class,'react-datepicker__day--today')]");
    public SelenideElement btnOK              = $x("//button[@class='btn btn-ok']");
    public SelenideElement minuteup           = $x("//div[text()='Minute']/../div[2]/i");

    public SelenideElement Exminuteup         = $x("//div[text()='Minute']/../div[3]");
    public SelenideElement hourup             = $x("//div[text()='Hour']/../div[2]/i");
    public SelenideElement Exhourup           = $x("//div[text()='Hour']/../div[3]");

    public SelenideElement btnSave            = $x("//button[@class='btn saveBtn']");
    public SelenideElement Amselect            = $x("//*[@id=\"timechange\"]");

    public int getUpdateCount() {
        return Integer.parseInt(getText(txtUpdatesAvailable));
    }
        
     //Added by Anusha H 
     public SelenideElement setTargetFW                             = $x("//p[text()=\"Set Target Firmware\"]");  
     public SelenideElement table                                   = $x("//div[@class=\"modal-body no-padding\"]");   // to verify theap model, textsS
     public SelenideElement ScheduleUpdatetext                      = $x("//div[@class=\"TableHeader headerindex1\"]");
     public SelenideElement UpdatesAvailabletext                    = $x("//*[@id=\"hopen\"]");
     public SelenideElement NoUpdatesAvailabletext                  = $x("//*[@id=\"hNsaccordone\"]");
     public SelenideElement offline                                 = $x("//*[@id=\"hHeadOPenOne\"]");
     public SelenideElement elemntsInUpdateAvailableTable           = $x("//*[@id=\"trStaticTable\"]");
     public SelenideElement APserialNo                              = $x("//*[@id=\"tddeviceName0\"]");
     public SelenideElement APcurrrentVersion                       = $x("(//*[@id=\"tdcurrentVersion0\"])[1]");
     public SelenideElement APmodel                                 = $x("(//*[@id=\"tdcurrentVersion0\"])[2]");
     
     public SelenideElement SelectTargetFW(String text, String text1) {
         SelenideElement SelectTargetFW = $x("(//td[text()='" + text +"'])[2]/../td/select/option[@value='" + text1 + "']");
         return SelectTargetFW;
     }
     
     public SelenideElement ApplyTargetFW                            = $x("//button[text()=\"Apply\"]");
     public SelenideElement APtargetVersion                          = $x("//*[@id=\"tdupdateHasStarted0\"]");
     public SelenideElement targetFWsetSuccessMsg                    = $x("//*[@id=\"showMessage\"]");
     
     

     
}