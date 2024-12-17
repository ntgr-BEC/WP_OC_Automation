package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class EventElement extends MyCommonAPIs {
    Logger            logger  = Logger.getLogger("EventElement");
    /**
     * 0 - old style, 1 - new style
     */
    public static int isNewUI = 99;

    public SelenideElement txtCount        = $("#notificationDrop span");
    public SelenideElement btnNotify       = $("#notificationDrop img");
    public String          sTableListLine1 = ".notification-list li h5";  
    public String          sTableListLine2 = ".notification-list li p";
    public SelenideElement btnSeeAll       = $("#notificationPadd button");

    public static String sTable        = "#tableBody";
    public static String sRow          = String.format("%s tr", sTable);
    public static String sCheckRow     = String.format("%s tr:first-child td:first-child", sTable);
    public static String sCheckOneRow  = "%s td:nth-child(%s) input";
    public static String cbSelectAll   = "#selectAllCheck+label";
    public static String sEventName    = String.format("%s td:nth-child(3) h5", sRow);
    public static String sEventDevType = String.format("%s td:nth-child(4) h5", sRow);
    public static String sEventType    = String.format("%s td:nth-child(5) span", sRow);
    public static String sEventDesc    = String.format("%s td:nth-child(6) h5", sRow);

    public static String sTableNew       = "tbody";
    public static String sRowNew         = String.format("%s tr", sTableNew);
    public static String sCheckRowNew    = String.format("%s tr input+i", sTableNew);
    public static String sCheckOneRowNew = "%s tr:nth-child(%s) input";
    public static String cbSelectAllNew  = "#selectAllCheck+i";
    public static String sEventNameNew   = String.format("%s td:first-child p:nth-child(1)", sRowNew);
    public static String sEventTypeNew   = String.format("%s td:first-child p:nth-child(4)", sRowNew);
    public static String sEventDescNew   = String.format("%s td:first-child p:nth-child(2)", sRowNew);

    public SelenideElement btnEditList = $("#editNotificationIcon");
    public String          cbItemSta   = "input[name*=notificationCheck]";
    public String          cbItem      = "input[name*=notificationCheck]+label";

    public SelenideElement btnFilter    = $(".filterIcon li a span");
    public SelenideElement btnFilterNew = $x("//li[@data-tooltip='Filter']");
    public String          btnTime      = "a[data-filtertype*=time]";
    public String          btnDevice    = "a[data-filtertype*=device]";
    public String          btnSeverity  = "a[data-filtertype*=alertType]";
    public String          btnLocation  = "a[data-filtertype*=location]";
    public SelenideElement btnApply     = $("#_ulCustNotificat button:last-child");
    public SelenideElement btnApplyNew  = $x("//*[@id=\"content\"]/div[1]/div[2]/div[2]/ul/li/div/div/div/div/button[2]");
    //public String          btnAction    = ".actionBtnRow button";
    public String          btnAction      =  "//div[@class='stati-footer bottomRadius7 actionBtnRow']/button";
    public SelenideElement btnCritical  = $("div.notificationTabBlock li:nth-child(2)");

    public static String sCritical      = WebportalParam.getLocText("deviceNotifications", "critical");
    public static String sWarning       = WebportalParam.getLocText("deviceNotifications", "warning");
    public static String sNotifications = WebportalParam.getLocText("deviceNotifications", "information");
    
    public SelenideElement iconNoNotifications  = $x("//p[text()='You do not have any notifications.']");
    

    public void initFlag() {
        if (isNewUI == 99) {
            if ($(sTable).exists()) {
                isNewUI = 0;
            } else {
                logger.info("it's new style page!");
                isNewUI = 1;
                sTable = sTableNew;
                sRow = sRowNew;
                sCheckRow = sCheckRowNew;
                sCheckOneRow = sCheckOneRowNew;
                cbSelectAll = cbSelectAllNew;
                sEventName = sEventNameNew;
                sEventType = sEventTypeNew;
                sEventDesc = sEventDescNew;
            }
        }
    }

    /**
     * @param btnIndex
     *                 1 - cancel, 2 - delete, 3 - share
     */
    /*public void selectButton(int btnIndex) {
        $$(btnAction).get(btnIndex).click();
    }*/
    public static SelenideElement selectButton(String btnIndex) {
        return $x(String.format("//div[@class='stati-footer bottomRadius7 actionBtnRow']/button[%s]", btnIndex));

    }
    
    /**
     * @param idx
     *                 0 - first, 1 - second, 2 - third, ...
     */
    public SelenideElement notificationcheckbox(int idx) {
        return $x("//input[@id='CheckBox" + idx + "']/../i");
    }
    
    public String getnotificationcontent(int idx) {
        return $x("//tbody//tr[" + idx + "]/td/div/div[2]/p[2]/span").getText();
    }
    
    public SelenideElement        deviceStatusOnInsight      = $x("//*[@id=\"tdDevStatusdevicesDash0\"]");
    public SelenideElement InformationEvent                  = $x("//li[text()=\"Information\"]");
  
}
