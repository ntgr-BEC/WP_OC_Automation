/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.AccountPageElement;
import webportal.webelements.NotificationPageElement;

import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author sumanta
 */
public class NotificationPage extends NotificationPageElement {
    //final static Logger logger = Logger.getLogger("NotificationPage");
    
    Logger logger = Logger.getLogger("NotificationPage");
    
    public NotificationPage() {
        logger.info("init...");
    }
          
    public void OpenNotifications() {
      
        Notification.click();
        logger.info("...... in notification page ...");
    }
    
    
    public void SeeAllNotifications() {
        MyCommonAPIs.sleepi(5);
        waitElement(Notification);
        Notification.click();
        MyCommonAPIs.sleepi(5);
        waitElement(Allnotifications);
        Allnotifications.click();
        MyCommonAPIs.sleepi(5);
        logger.info("...... Click all notification button ...");
    }
    
    public boolean CheckAPNotifications() {
               
            boolean result = false;
      
            waitElement(NotificationAP);
            logger.info("Check AP notification in notification page ...");
            if (NotificationAP.exists()) {
                result = true;
            }
            return result;
        }
    
    //AddedbyPratik
    public boolean verifyHardbundleOnboardedDevicesNotification(String serialNumber) {
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String currentDate = sdf.format(new Date());
        System.out.println("currentDate : "+currentDate);
        MyCommonAPIs.sleepi(15);
        waitElement(verifyOnboardedHardbundleDeviceNotification(serialNumber,currentDate));
        if (verifyOnboardedHardbundleDeviceNotification(serialNumber,currentDate).exists()) {
            System.out.println((verifyOnboardedHardbundleDeviceNotification(serialNumber,currentDate)));
            logger.info("Notification shown on notifications page after onboarding Hardbundle device");
            result = true;
        }
        return result;
    }

        
   
    }
        
  

    
    
    
    
    
    