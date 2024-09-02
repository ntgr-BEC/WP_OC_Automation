package webportal.Multipack.PRJCBUGEN_T18231;

import static com.codeborne.selenide.Selenide.$x;
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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random              r            = new Random();
    int                 num          = r.nextInt(10000000);
    String              mailname     = "apwptest" + String.valueOf(num);
    String              locationName = "test18231";
    Map<String, String> devInfo      = new HashMap<String, String>();

    @Feature("Multipack") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18231") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify In order to access Device Credit Packs Basic user needs to upgrade to Premium user first using webpotal") // It's a
                                                                                                                                           // test
                                                                                                                                           // case
                                                                                                                                           // title
                                                                                                                                           // from
                                                                                                                                           // Jira
                                                                                                                                           // Test
                                                                                                                                           // Case.
    @TmsLink("PRJCBUGEN-T18231") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().deleteOneLocation("OnBoardingTest");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create new account;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", "New");
        accountInfo.put("Last Name", "New");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
        new HamburgerMenuPage(false).closeLockedDialog();
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

    @Step("Test Step 4: Check try trial;")
    public void step4() {
        new HamburgerMenuPage(false).hamburgermenunew.click();
        new HamburgerMenuPage(false).accountmanager.click();
        MyCommonAPIs.sleepi(10);
        new HamburgerMenuPage(false).subscriptions.click();
        MyCommonAPIs.sleepi(10);
        assertTrue($x("//span[text()='Device Credit Pack']").exists(), "Account try trial unsuccessful.");
    }

}
