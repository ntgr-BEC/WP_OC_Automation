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
    public  SelenideElement        Allnotifications = $x("//*[@id=\"notificationPadd\"]/div/div[2]/button");
    
  //span[text() = "BTA18A5SF0285, AP"]
       
 
}
