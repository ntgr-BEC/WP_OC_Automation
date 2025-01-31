package webportal.weboperation;

//import static com.codeborne.selenide.Selenide.$x;
//import static com.codeborne.selenide.Selenide.executeJavaScript;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.logging.Logger;
//
//import com.codeborne.selenide.Condition;
//import com.codeborne.selenide.SelenideElement;
//
//import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.HamburgerMenuElement;
import webportal.webelements.HardbundlePageElement;



import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import util.APUtils;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.CommonDataType.SSIDData;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.WirelessQuickViewElement;
import java.util.Random;

public class HardBundlePage extends HardbundlePageElement {
    final static Logger logger = Logger.getLogger("DevicesApSummaryPage");

    /**
     *
     */
    public HardBundlePage() {
        // TODO Auto-generated constructor stub
        // WebCheck.checkHrefIcon(URLParam.hrefDevicesApSummary);
        MyCommonAPIs.sleep(5 * 1000);
        logger.info("init...");
    }

    public boolean checkCreditsUsed(String name ) {
        
        new HamburgerMenuPage().gotoCreditsAllocationPage();
//        SelenideElement btnEle = $x(String.format(new HamburgerMenuElement().allocateBtn, name));
//        btnEle.click();
//        MyCommonAPIs.sleepi(10);
        
        executeJavaScript("arguments[0].removeAttribute('class')", $x(String.format(showallocate, name)));
        MyCommonAPIs.sleep(3000);
        $x(String.format(clickallocate, name)).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(8 * 1000);
        boolean result = false;
      
              
       HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationStatus(name);
       
        if( creditsInfo.get("Used Devices Credits").equals("0")) {
           
            result = true;
        }
         
       return result;
        
    }
    
    public String GetcurrentPath(String locationName) {
        System.out.println("dsafasdadfadfadf");
        String hrefSummary = "#/dashboard/location";
        hrefSummary = hrefSummary + "/"+ locationName;
        System.out.println(hrefSummary);
        WebCheck.checkHrefIcon(hrefSummary);
        String pageName = this.getClass().getSimpleName();
        
        File f = new File(this.getClass().getResource("").getPath());
        String filePath  = f.getAbsolutePath().replace("\\target\\test-classes\\webportal\\weboperation", "\\src\\test\\java\\webportal\\HardBundle\\");
        
       System.out.println(filePath);
       return filePath;
         
     }
    
    public String GetcurrentPathMub(String locationName) {
        System.out.println("dsafasdadfadfadf");
        String hrefSummary = "#/dashboard/location";
        hrefSummary = hrefSummary + "/"+ locationName;
        System.out.println(hrefSummary);
        WebCheck.checkHrefIcon(hrefSummary);
        String pageName = this.getClass().getSimpleName();
        
        File f = new File(this.getClass().getResource("").getPath());
        String filePath  = f.getAbsolutePath().replace("\\target\\test-classes\\webportal\\weboperation", "\\src\\test\\java\\webportal\\MUBBilling\\");
        
       System.out.println(filePath);
       return filePath;
         
     }
    
    public void closeLockedDialog() {
        if (closeLockedWindow.exists() && closeLockedWindow.isDisplayed()) {
            
            System.out.println("123");
            closeLockedWindow.click();
            
        }
    }
    
    public void gotoOneYearInsightIncludedwithHardwarePRO () {
        if (hamburgermenu.exists()) {
            hamburgermenu.click();
            if (hamburgermenu.getAttribute("aria-expanded").equals("false")) {
                hamburgermenu.click();
            }
        } else if (hamburgermenunew.exists()) {
            hamburgermenunew.click();
            if (hamburgermenunew.getAttribute("aria-expanded").equals("false")) {
                hamburgermenunew.click();
            }
        }
        accountmanager.click();
        MyCommonAPIs.sleepi(3);
        closeLockedDialog();
        if(PurchaseHistory.isDisplayed()) {
        PurchaseHistory.click();
        }
        MyCommonAPIs.sleepi(10);
        if(OneYearInsightIncludedwithHardware.exists()) {
           OneYearInsightIncludedwithHardware.shouldBe(Condition.visible).click();
        }else if (OneYearInsightIncludedwithHardware1.exists()){
            OneYearInsightIncludedwithHardware1.shouldBe(Condition.visible).click();
        }
        MyCommonAPIs.sleepi(5);
    }

    
    public boolean checkApIsExist(String serialNumber) {
        boolean result = false;
        String sElement = String.format("//td[text()='%s']", serialNumber);
        logger.info("on element: " + sElement);
        if ($x(sElement).exists()) {
            result = true;
            logger.info("Ap SN:" + serialNumber + " is existed.");
        }
        return result;
    }
    
    public void deleteDeviceHBYes(String serialNumber) {
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        MyCommonAPIs.sleep(10000);
        if (checkApIsExist(serialNumber)) {
            logger.info("Delete device.");
            executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
            MyCommonAPIs.sleep(3000);
            deleteDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(3000);
            deleteapyes.click();
            MyCommonAPIs.sleep(5 * 1000);
        }
    }
    
    public void OnboardBulk() {
        
        BulkOnboarding.click();
        MyCommonAPIs.sleep(3000);
        SelectAllDevice.click();
        MyCommonAPIs.sleep(3000);
        UpdateDeviceList.click();
        MyCommonAPIs.sleep(3000);
        ViewList.click();
    }
    
    public boolean comparebothdates(String ActivationPremium, String ActivationPro, String ExpiredPremium, String ExpiredPro) {
        
        boolean result = false;
        
        if(ExpiredPremium.equals(ExpiredPro)) {
            
            System.out.println("Hard bundle device have same Activation and expiry date in all account");
            result = true;
        }
        return result;
        
    }
    
 public boolean CheckAllHBadded(String serialNo1, String serialNo3, String serialNo4, String serialNo5, String serialNo6, String serialNo7, String serialNo9, String serialNo10, String serialNo11, String serialNo12, String serialNo13) {
        
        boolean result = false;
        
        if(deviceExit(serialNo1).exists() && deviceExit(serialNo3).exists() && deviceExit(serialNo4).exists() && deviceExit(serialNo5).exists() && deviceExit(serialNo6).exists() && deviceExit(serialNo7).exists() && deviceExit(serialNo9).exists() && deviceExit(serialNo10).exists() && deviceExit(serialNo11).exists() && deviceExit(serialNo12).exists() && deviceExit(serialNo13).exists()) {
            result = true;
            System.out.println("All Hardbundledevice exits");
        }
        return result;
        
    }
    
 public boolean CheckOneHBadded(String serialNo1) {
     
     boolean result = false;
     
     if(deviceExit(serialNo1).exists()) {
         result = true;
         System.out.println("All Hardbundledevice exits");
     }
     return result;
     
 }
 
 public boolean OrbiProAx5YrSubscriptioncheck(String serialNo1, String accounttype ) {
     
     boolean result = false;
     
     if(deviceExit(serialNo1).exists()) {
         result = true;
         System.out.println("All Hardbundledevice exits");
     }
     return result;
     
 }
 
 public void GoTocreateLocation() {
     
     String url = MyCommonAPIs.getCurrentUrl();
     if (url.contains("/#/locked")) {
         closeLockedDialog();
     }
     new MyCommonAPIs().open(URLParam.hrefLocked, true);
     MyCommonAPIs.sleep(3000);
     AddHardBundle.click();
 }
 
 
 public void  checkCreateProAccountPage(String option) {
     boolean result = false;
     if (!option.equals("checkKey") && !option.equals("checkManager")) {
         System.out.println("enterd URL");
         logger.info("Open URL");
         new HamburgerMenuPage(false).openCreateProAccountUrl();
     }
     
     if (createpronextbutton.exists() && option.contains("checkNext")) {
         System.out.println("openthis");
         logger.info("Check Next loop");
         String emailaddress = option.substring(option.indexOf(":") + 1, option.length());
         System.out.println(emailaddress);
         logger.info("stop the loop");
         MyCommonAPIs.sleepi(5);
         ProemailID.sendKeys(emailaddress);
         MyCommonAPIs.sleepi(5);
         ClickOnnext.click();
         MyCommonAPIs.sleepi(5);
         if(loginPwdNew2.isDisplayed()) {
             loginPwdNew2.sendKeys("Netgear#123");
         }else {
         loginPwdNew.sendKeys("Netgear#123");
         }
         loginButtonNew.click();
         MyCommonAPIs.sleepi(5);
      } 
 }
   
 
 public boolean checkLoginSuccessful() {
     
     boolean result = false;
     MyCommonAPIs.sleepi(10);
     waitElement(SelectLocation);
     if(SelectLocation.exists()) {
         SelectLocation.click();
        MyCommonAPIs.sleepi(10);
        waitElement(AssignToOrganization);
        AssignToOrganization.click();
        MyCommonAPIs.sleepi(10);
        new MyCommonAPIs().waitElement(OrganizationName);
        OrganizationName.sendKeys(WebportalParam.Organizations);
        MyCommonAPIs.sleepi(5);
        Submit.click();
        MyCommonAPIs.sleepi(10);
        refresh();
        MyCommonAPIs.sleepi(10);
        result= true;
     }
     return result;
     
 }
 
 public boolean checkCreditsAllocationSuccessMsg() {
     boolean result = false;
     String creditallocated = new MyCommonAPIs().getText(successMsg);
     if (creditallocated.equals("2")) {
         result = true;
         logger.info("The credits have been allocated successfully");
     }
     return result;
 }

public boolean OrbiProAx5YrSubscriptioncheckPremium(String serialNo) {
    boolean result = false;   
    
    SimpleDateFormat df = new SimpleDateFormat("dd, yyyy");
    SimpleDateFormat dfNextYear = new SimpleDateFormat("yyyy");
    SimpleDateFormat dfDate = new SimpleDateFormat("dd");
    
    
    String ActivationDate = new MyCommonAPIs().getText(activationDate(serialNo));
    
    String ExpiryDate = new MyCommonAPIs().getText(ExpiryDate(serialNo));
    
    System.out.print(ActivationDate);
    System.out.print(ExpiryDate);
    
    
//    if(!ActivationDate.equals(null) && !ExpiryDate.equals(null) && ExpiryDate.contains(
//            dfDate.format(new Date()).toString() + ", " + String.valueOf(Integer.valueOf(dfNextYear.format(new Date())) + 5))){
//        
//     
    
    
    if(!ActivationDate.equals(null) && !ExpiryDate.equals(null)) {
        result = true;
        logger.info("activation and expiry date are correct");
        
    }
    
    if (ExpiryDate.contains(
            dfDate.format(new Date()).toString() + ", " + String.valueOf(Integer.valueOf(dfNextYear.format(new Date())) + 5))) {
        result = true;
        logger.info("Expiry date is  correct");
    
    }
    
    return result;
}   

public boolean OrbiProAx5YrSubscriptioncheckPro(String serialNo) {
    boolean result = false;   
    
    SimpleDateFormat df = new SimpleDateFormat("dd, yyyy");
    SimpleDateFormat dfNextYear = new SimpleDateFormat("yyyy");
    SimpleDateFormat dfDate = new SimpleDateFormat("dd");
    
 String ActivationDate = new MyCommonAPIs().getText(activationDate(serialNo));
    
    String ExpiryDate = new MyCommonAPIs().getText(ExpiryDate(serialNo));
    
    System.out.print(ActivationDate);
    System.out.print(ExpiryDate);
    
    
    if(!ActivationDate.equals(null) && !ExpiryDate.equals(null)) {
        result = true;
        logger.info("activation and expiry date are correct");
        
    }
    
    if (ExpiryDate.contains(
            dfDate.format(new Date()).toString() + ", " + String.valueOf(Integer.valueOf(dfNextYear.format(new Date())) + 1))) {
        result = true;
        logger.info("expiry date are correct");
    
    }
    
     return result;

}   

//AddedByPratik
public SelenideElement srNounderOneYearInsightIncludedwithHardwarePRO(String text) {
    SelenideElement activationDate = $x("//span[contains(text(), '"+ text +"')]");
    return activationDate;
}




//AddedByPratik
public boolean verifyOneYearInsightIncludedwithHardware() {
  boolean result = false;
  if (!OneYearInsightIncludedwithHardware.exists()) {
      result = true;
      logger.info("After deleteing all devices One Year Insight Included with Hardware is not shown");
  }
  return result;
}
//AddedByPratik
public boolean gotoPurchaseHistoiry() {
    boolean result = false;
  if (hamburgermenu.exists()) {
      hamburgermenu.click();
      if (hamburgermenu.getAttribute("aria-expanded").equals("false")) {
          hamburgermenu.click();
      }
  } else if (hamburgermenunew.exists()) {
      hamburgermenunew.click();
      if (hamburgermenunew.getAttribute("aria-expanded").equals("false")) {
          hamburgermenunew.click();
      }
  }
  accountmanager.click();
  MyCommonAPIs.sleepi(3);
  closeLockedDialog();
  if(PurchaseHistory.isDisplayed()) {
  PurchaseHistory.click();
  }
  MyCommonAPIs.sleepi(3);
  if (!OneYearInsightIncludedwithHardware.exists()) {
      result = true;
      logger.info("After deleteing all devices One Year Insight Included with Hardware is not shown");
  }
  return result;

}

//AddedByPratik
public void verifyAndActivateFreelicenseKeys() {
    MyCommonAPIs.sleepi(5);
    if (hamburgermenu.exists()) {
        hamburgermenu.click();
        if (hamburgermenu.getAttribute("aria-expanded").equals("false")) {
            hamburgermenu.click();
        }
    } else if (hamburgermenunew.exists()) {
        hamburgermenunew.click();
        if (hamburgermenunew.getAttribute("aria-expanded").equals("false")) {
            hamburgermenunew.click();
        }
    }
    accountmanager.click();
    MyCommonAPIs.sleepi(3);
    waitElement(subscriptionMenu);
    subscriptionMenu.click();
    MyCommonAPIs.sleepi(3);
    waitElement(activatefreetrail);
    activatefreetrail.click();
    MyCommonAPIs.sleepi(3);
    waitElement(activateButton);
    activateButton.click();
    MyCommonAPIs.sleepi(3);
}

    // AddedByPratik
    public boolean verifyAndActivateFreelicenseKeys1() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        if (hamburgermenu.exists()) {
            hamburgermenu.click();
            if (hamburgermenu.getAttribute("aria-expanded").equals("false")) {
                hamburgermenu.click();
            }
        } else if (hamburgermenunew.exists()) {
            hamburgermenunew.click();
            if (hamburgermenunew.getAttribute("aria-expanded").equals("false")) {
                hamburgermenunew.click();
            }
        }
        accountmanager.click();
        MyCommonAPIs.sleepi(3);
        waitElement(subscriptionMenu);
        subscriptionMenu.click();
        MyCommonAPIs.sleepi(3);
        waitElement(activatefreetrail);
        MyCommonAPIs.sleepi(1);
        if (activatefreetrail.exists()) {
            activatefreetrail.click();
            MyCommonAPIs.sleepi(3);
            waitElement(activateButton);
            MyCommonAPIs.sleepi(1);
            activateButton.click();
            MyCommonAPIs.sleepi(3);
            result = true;
        }
        return result;
    }

}
