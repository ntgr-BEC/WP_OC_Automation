/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author Sumanta
 */
public class NotificationPageElement extends MyCommonAPIs {
    
    
    
    //Notification 
    
    public  SelenideElement        Notification = $x("//*[@id=\"notificationDrop\"]/div/img");
    public  SelenideElement        NotificationAP = $x("//*[@id=\"notificationPadd\"]/div/ul/li[1]/p");
    public  SelenideElement        TotalAPnotifications = $x("//span[text() = \"BTA18A5SF0285, AP\"]");
    public  SelenideElement        Allnotifications = $x("//*[@id=\"notificationPadd\"]//button[text()='See All']");
    
    //AddedBypratik
    public SelenideElement verifyOnboardedHardbundleDeviceNotification(String serialNumber, String currentDate) {
        SelenideElement verifyNotificationsOnboardedAP = $x("//span[contains(text(), '" + serialNumber + "') and contains(text(), '" + currentDate + "') and contains(text(),'was added as Insight bundle device')]");
        return verifyNotificationsOnboardedAP;
    }
    
  //span[text() = "BTA18A5SF0285, AP"]
       
 
}
