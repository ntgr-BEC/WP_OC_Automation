package webportal.Notifications.PRJCBUGEN_T31778;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.NotificationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author sumanta
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String wrongNotification= "changed to standalone";
    int a=0;

    @Feature("Notifications") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31778") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("check all notification while adding device AP") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN_T31778") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("OnBoardingAPTest");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account in United States success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T31778");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

      //  new HamburgerMenuPage(false).createAccount(accountInfo);
    }
    
    @Step("Test Step 2: Create new location")
    public void step2() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingAPTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new HamburgerMenuPage();
        new AccountPage().addNetwork(locationInfo);
    }
    
    
    @Step("Test Step 3: Add device To the Network;")
    public void step3() {
        
        new AccountPage().enterLocation("OnBoardingAPTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
   
        firststdevInfo.put("Serial Number1", WebportalParam.ap1serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        MyCommonAPIs.sleepi(520);
    }

    @Step("Test Step 6: check See all notifications;")
    public void step6() {
       WebDriver driver = WebDriverRunner.getWebDriver();
       MyCommonAPIs.sleepi(4);
       new NotificationPage().SeeAllNotifications();
       MyCommonAPIs.sleepi(4);
       
      
      List<WebElement> APnotifications= driver.findElements(By.xpath("//*[@id='alarmsTable']/tbody/tr/td[1]/div/div[2]/p[2]/span"));
      System.out.println("ALL AP NOTIFICATIONS " + APnotifications.size());
     
      for (a=0;a<APnotifications.size();a++) {
          System.out.println(APnotifications.get(a).getText());
          }
      
      boolean result = true;
      for (int i=0;i<APnotifications.size();i++) {
         // System.out.println(APnotifications.get(i).getText());
          
          if (APnotifications.get(i).getText().contains(wrongNotification)) {
              MyCommonAPIs.sleepi(4);
              //System.out.println("SUMANTA");
              result = false;
          }
      }
      
      assertTrue(result, "Wrong device notification: changed to standalone displayed");
      
    }
    
}

