package webportal.CF.PRJCBUGEN_T24454;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selectors;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.ContentFilteringElement;
import webportal.weboperation.AccountPage;
import webportal.weboperation.ContentFilteringPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.GlobalNotificationPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String SSID = (WebportalParam.OrbiDefaultSSID);
    String PASSWORD = (WebportalParam.OrbiDefaultPassword);
    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String mailname1 = "apwptest1" + String.valueOf(num);
    

    @Feature("CF") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24454") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the subscription behavior if the credit was purchased against one device and then that device is deleted from one network and added in other network") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24454") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
     System.out.println("start to do tearDown");
     new DevicesDashPage().deleteDeviceYes(WebportalParam.ob1serialNo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccountorbi();
    }

    @Step("Test Step 2: deletedevice and logout from account ")
    public void step2() {
        
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob1serialNo);
        String sCheck = "[alt=\"User Image\"]";
        System.out.println("try to do logout");
        $(sCheck).click();
        $(Selectors.byCssSelector(".open ul li:last-child a")).click();
        System.out.println("user is logout");
        MyCommonAPIs.waitReady();

    }
    
    @Step("Test Step 3: Create a New account with USA ;")
    public void step3() {
    WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T15598");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
       
    }
    
    @Step("Test Step 4: Create a Location ;")
    public void step4() {
        
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        new AccountPage(false).enterLocation(WebportalParam.location1);
        
    }
    
    @Step("Test Step 5: Add orbi to your account;")
    public void step5() {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ob1serialNo);
        devInfo.put("Device Name", WebportalParam.ob1deveiceName);

        new DevicesDashPage(false).addNewDevice(devInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob1serialNo);          }
    
    @Step("Test Step 6: Buy CF Service Subscription;")
    public void step6() {
        
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Subscription Type", "Content Filtering Service Subscription");
        paymentInfo.put("Quantity", "2");
        paymentInfo.put("YearNum", "1");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17564");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Springs Rd");
        paymentInfo.put("City", "Red Bank");
        paymentInfo.put("Zip", "32003");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        paymentInfo.put("Card Number", "5193911111111112");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new InsightServicesPage(false).buyCFProducts(paymentInfo);
        assertTrue(
                new ContentFilteringPage().checkBuyCFResult(paymentInfo.get("YearNum"), paymentInfo.get("Quantity")),
                "Vpn services page display incorrect.");
        assertTrue(new ContentFilteringPage().checkCFServicesSubscription(paymentInfo.get("Quantity"), paymentInfo.get("Quantity")),"CF page is not right");
        
    }
    
    @Step("Test Step 7: Try to enable Cf;")
    public void step7() {
        
        
        new AccountPage().enterLocation(WebportalParam.location1);
        
        new GlobalNotificationPage().enterDeviceYes(WebportalParam.ob1serialNo);
        new ContentFilteringPage().gotoCF();
        assertTrue(!new ContentFilteringPage().EnableNewAccount(), "free trail popup doesnot exits");
    }
    
    @Step("Test Step 8: deletedevice and logout from account ")
    public void step8() {
        
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob1serialNo);
        String sCheck = "[alt=\"User Image\"]";
        System.out.println("try to do logout");
        $(sCheck).click();
        $(Selectors.byCssSelector(".open ul li:last-child a")).click();
        System.out.println("user is logout");
        MyCommonAPIs.waitReady();

    }
    
    @Step("Test Step 9: Create a New account with USA ;")
    public void step9() {
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname1);
        accountInfo.put("Last Name", "T15598");
        accountInfo.put("Email Address", mailname1 + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname1 + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
       
    }
    
    @Step("Test Step 10: Create a Location ;")
    public void step10() {
        
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        new AccountPage(false).enterLocation(WebportalParam.location1);
        
    }
    
    @Step("Test Step 11: Add orbi to your account;")
    public void step11() {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ob1serialNo);
        devInfo.put("Device Name", WebportalParam.ob1deveiceName);

        new DevicesDashPage(false).addNewDevice(devInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob1serialNo);
        
    }
    
    @Step("Test Step 12: Try to enable Cf;")
    public void step12() {
        
        new GlobalNotificationPage().enterDeviceYes(WebportalParam.ob1serialNo);
        new ContentFilteringPage().gotoCF();
        assertTrue(new ContentFilteringPage().EnableNewAccount(), "free trail popup doesnot exits");
    }
}
