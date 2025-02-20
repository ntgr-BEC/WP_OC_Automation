package webportal.UpgradeProSubscription.PRJCBUGEN_T31010;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String organizationName = "TEST14342";

    @Feature("UpgradeProSubscription") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31010") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the upgrade functionality of Insight Premium to Insight Pro") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T31010") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage().deleteOrganizationNew(organizationName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T18721");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Italy");


        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Navigate to Account Management, check upgrade to pro option and click on it;")
    public void step2() {
        System.out.println("starting with setup 2");
        assertTrue(new HamburgerMenuPage().insightPritoinsightPro(),"Failed navigate to Account Management");
        
    }
    
    @Step("Test Step 3: Fill the tset data into the form and click on upgrade button;")
    public void step3() {
        
        Map<String, String> businessInfo = new HashMap<String, String>();
        businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
        businessInfo.put("Business Name", "Netgear");
        businessInfo.put("Primary Address of Business", "Via Bari");
        businessInfo.put("City", "Palermo");
        businessInfo.put("State", "Palermo");
        businessInfo.put("Zip Code", "90133");
        businessInfo.put("Country", "Italy");
        businessInfo.put("Business Phone Number", "1234567890");
        new HamburgerMenuPage(false).inputLicenceAndFinishSignin(businessInfo);

        assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess.");
        
    }

    @Step("Test Step 4: Create a organization and location;")
    public void step4() {
        
        new OrganizationPage(false).gotoDashboard();
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);

        assertTrue(OrganizationPage.checkOrganizationIsExist(organizationName), "Organization not existed.");
        
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "90133");
        locationInfo.put("Country", "Italy");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();
        
    }
    @Step("Test step5:Validation of free trial")
    public void step5()
    {
       assertTrue(new HamburgerMenuPage().checkSubscriptionsFreeTrial(),"Expected text is not present");
    }
}
