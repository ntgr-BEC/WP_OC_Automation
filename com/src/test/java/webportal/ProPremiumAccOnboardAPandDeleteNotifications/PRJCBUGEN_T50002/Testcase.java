package webportal.ProPremiumAccOnboardAPandDeleteNotifications.PRJCBUGEN_T50002;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {


    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num) + "@mailinator.com";
    String organizationName = "TEST14342";
    
  

    @Feature("ProPremiumAccOnboardAPandDeleteNotifications") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T50002") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Login to Premium Account, Create Location and onboard AP - US country") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T50002") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
   
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
    }
    
    @Step("Test Step 2: Create a location;")
    public void step2() {
        
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        
    }
    
    @Step("Test Step 3: Onboard AP")
    public void step3() {
        
        new AccountPage(false).enterLocation(WebportalParam.location1);
        
        new AccountPage().enterLocation("OnBoardingTest");
       
       Map<String, String> firststdevInfo = new HashMap<String, String>();
      
       
       firststdevInfo.put("Serial Number1", WebportalParam.ap1serialNo);
       firststdevInfo.put("MAC Address1", WebportalParam.ap1macaddress);
       
       System.out.println(firststdevInfo);

               
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

        if (!new DevicesDashPage().getDeviceName(WebportalParam.ap1serialNo).equals("")) {
            new DevicesDashPage().editDeviceName(WebportalParam.ap1serialNo, WebportalParam.ap1deveiceName);
        }
        
    }

}
