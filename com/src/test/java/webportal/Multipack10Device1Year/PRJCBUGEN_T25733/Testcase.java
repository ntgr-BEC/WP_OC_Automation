package webportal.Multipack10Device1Year.PRJCBUGEN_T25733;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);
    Map<String, String> paymentInfo = new HashMap<String, String>();
   

    @Feature("Multipack 10Device 1Year") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25733") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Muktipack 10Device 1Year New Zealand") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25733") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("OnBoardingTest");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T10218");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "New Zealand");

        new HamburgerMenuPage(false).createAccount(accountInfo);        
        
//       
    }
    
    @Step("Test Step 2: Create new location")
    public void step2() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new HamburgerMenuPage();
        new AccountPage().addNetwork(locationInfo);
    }
    
    
    @Step("Test Step 3: Add device To the Network;")
    public void step3() {
        
        new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
       
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        
        System.out.println(firststdevInfo);
 
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);

      
    }


    @Step("Test Step 4: Check buy vpn services;")
    public void step4() {
        
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo = new CommonDataType().CARD_INFO;
        
        new HamburgerMenuPage(false).closeLockedDialog();
        new HamburgerMenuPage().gotoInsightPremiumSubscriptions();
        
        paymentInfo.put("First Name", "New");
        paymentInfo.put("Last Name", "New");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Device Credits Pack", "10");
        paymentInfo.put("Buy Year", "1");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Melville");
        paymentInfo.put("City", "Waikato");
        paymentInfo.put("Zip", "3206");
        paymentInfo.put("Country", "New Zealand");
        paymentInfo.put("State", "Waikato");
       
        new InsightServicesPage(false).buyInsightPremiumSubscriptions(paymentInfo);
        
        assertTrue(new HamburgerMenuPage(false).checkSubscriptionsPage("Insight Premium", paymentInfo.get("Device Credits Pack")),
                "Subscriptions page display error.");
    }
    
    @Step("Test Step 5: Check cancel devices credits;")
    public void step5() {
        new HamburgerMenuPage().cancelDeviceCredits();

        assertTrue(!new HamburgerMenuPage(false).checkDeviceCredits(paymentInfo.get("Device Credits Pack")));
    }

}
