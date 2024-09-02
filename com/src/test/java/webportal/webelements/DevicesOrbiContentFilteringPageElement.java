/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author ann.fang
 */
public class DevicesOrbiContentFilteringPageElement extends MyCommonAPIs {
    Logger logger;

    public DevicesOrbiContentFilteringPageElement() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }
    
    public static SelenideElement inputEnableCF        = $x("//input[@id='CF_checkbox']");
    public static SelenideElement inputEnableSS        = $x("//input[@id='allowSafe_checkbox']");
    public static SelenideElement inputEnableSchedule        = $x("//input[@id='scheduleReport_checkbox']");
    public static SelenideElement inputEnableInspection        = $x("//input[@id='inspection_checkbox']");
    public static SelenideElement spanEnableCF        = $x("//input[@id='CF_checkbox']/../span");
    public static SelenideElement spanEnableSS        = $x("//input[@id='allowSafe_checkbox']/../span");
    public static SelenideElement spanEnableSchedule        = $x("//input[@id='scheduleReport_checkbox']/../span");
    public static SelenideElement spanEnableInspection        = $x("//input[@id='inspection_checkbox']/../span");
    
    public static SelenideElement btnSave             = $x("//button[contains(@class,'saveBtn')]");
    public static SelenideElement btnCancel             = $x("//button[contains(@class,'cancelBtn')]");
    
    public static SelenideElement btnYes             = $x("//button[contains(@class,'btn-default')]");
    public static SelenideElement btnNo             = $x("//button[contains(@class,'btn-primary')]");
}
