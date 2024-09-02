package webportal.WebPoratlPerformanceImprovement.PRJCBUGEN_T33459;

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
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;


/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {
    
    String                  locationName     = "OnBoardingTest";
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    Map<String, String> paymentInfo = new HashMap<String, String>();
    

    @Feature("Web Portal Performance Improvements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33459") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that from premium to pro user should able to see account details.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33459") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganizationNew(WebportalParam.Organizations);
    }
    
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T15589");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");
        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
          
    
    @Step("Test Step 2: Create Location ")
    public void step2() {
        
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new AccountPage().addNetwork(locationInfo);
        
    }
    
    @Step("Test Step 3: Add HB device ")
    public void step3() {
        
        new AccountPage().enterLocation(locationName);
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        Map<String, String> firststdevInfo1 = new HashMap<String, String>();
        
        
        firststdevInfo.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("sw4"));
        firststdevInfo.put("MAC Address1", WebportalParam.getDeviceMacCSV("sw4"));
        
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
        firststdevInfo1.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("sw5"));
        firststdevInfo1.put("MAC Address1", WebportalParam.getDeviceMacCSV("sw5"));
        
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo1);
        
        assertTrue(new WirelessQuickViewPage(false).checkApIsExist(WebportalParam.getDeviceSerialNoCSV("sw4")),"Device delete unsuccessful");
        assertTrue(new WirelessQuickViewPage(false).checkApIsExist(WebportalParam.getDeviceSerialNoCSV("sw5")),"Device delete unsuccessful");
       
       
    }
    
    @Step("Test Step 4: chnage premium Trail to Premium Yearly;")
    public void step4() {
        paymentInfo = new CommonDataType().CARD_INFO;
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "3");
        paymentInfo.put("First Name", "New");
        paymentInfo.put("Last Name", "New");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Springs Rd");
        paymentInfo.put("City", "Red Bank");
        paymentInfo.put("Zip", "32003");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);
        
    }
    
    @Step("Test Step 5: logout and migrates to a PRO account.")
    public void step5() {
        
           String sCheck = "[alt=\"User Image\"]";
            System.out.println("try to do logout");
            $(sCheck).click();
            $(Selectors.byCssSelector(".open ul li:last-child a")).click();
            System.out.println("user is logout");
            MyCommonAPIs.waitReady();
            
            new HardBundlePage().checkCreateProAccountPage("checkNext:" + mailname + "@mailinator.com");
            
          
            
            Map<String, String> businessInfo = new HashMap<String, String>();
            businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
            businessInfo.put("Business Name", "Netgear");
            businessInfo.put("Primary Address of Business", "test 1st");
            businessInfo.put("City", "NewYork");
            businessInfo.put("State", "test");
            businessInfo.put("Zip Code", "12345");
            businessInfo.put("Country", "United States of America");
            businessInfo.put("Business Phone Number", "1234567890");
            new HamburgerMenuPage(false).inputLicenceAndFinishSignin(businessInfo);

            assertTrue(new HardBundlePage().checkLoginSuccessful(), "Create pro account unsuccess.");
        
    }
    
    @Step("Test Step 6: logout and migrates to a PRO account.")
    public void step6() {
        
        new AccountPage().enterLocation(locationName);
        
       assertTrue(new WirelessQuickViewPage(false).checkApIsExist(WebportalParam.getDeviceSerialNoCSV("sw4")),"Device delete unsuccessful");
       assertTrue(new WirelessQuickViewPage(false).checkApIsExist(WebportalParam.getDeviceSerialNoCSV("sw5")),"Device delete unsuccessful");
    }
    
    
}
