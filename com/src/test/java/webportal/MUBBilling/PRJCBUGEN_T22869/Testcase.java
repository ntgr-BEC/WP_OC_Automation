package webportal.MUBBilling.PRJCBUGEN_T22869;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    Random r        = new Random();
    int    num      = r.nextInt(10000);
    String mailname = "abcwz" + String.valueOf(num) + "@sharklasers.com";
    String organizationName = "TEST14342";

    @Feature("MUBBilling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T22869") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that the MUB option is only applicable for the North American countries") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T22869") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Check create pro account success.")
    public void step1() {
        if (new HamburgerMenuPage(false).checkCreateProAccountPage("checkNext:" + mailname)) {
            Map<String, String> proAccountInfo = new HashMap<String, String>();
            proAccountInfo.put("Confirm Email", mailname);
            proAccountInfo.put("Password", WebportalParam.adminPassword);
            proAccountInfo.put("Confirm Password", WebportalParam.adminPassword);
            proAccountInfo.put("Country", "India");
            proAccountInfo.put("Phone Number", "1234567890");
            new HamburgerMenuPage(false).CreateProAccount(proAccountInfo);

            Map<String, String> businessInfo = new HashMap<String, String>();
            businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
            businessInfo.put("Business Name", "Netgear");
            businessInfo.put("Primary Address of Business", "test 1st");
            businessInfo.put("City", "Karnataka");
            businessInfo.put("State", "test");
            businessInfo.put("Zip Code", "560057");
            businessInfo.put("Country", "India");
            businessInfo.put("Business Phone Number", "1234567890");
            new HamburgerMenuPage(false).inputLicenceAndFinishSignin(businessInfo);

            assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess.");
        }

    }
    @Step("Test Step 2: Create a organization and check its existed;")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);

        assertTrue(OrganizationPage.checkOrganizationIsExist(organizationName), "Organization not existed.");
        
    }

    @Step("Test Step 3: MUB option is only applicable for the North American countries;")
    public void step3() {
        
        new HamburgerMenuPage(false).GooMUB();
        assertTrue(new HamburgerMenuPage(false).Othercountries(),"MUB option is not only applicable for the North American countries");
    }

}
