package webportal.MUBBilling.PRJCBUGEN_T21913;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("MUBBilling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21913") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that Pro admin user is allowed to Add Device/s if the organization does not have sufficient credit but Monthly usage billing is enabled") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T21913") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2: Add Non Hardbundle device to the Organization where credit is not allocated")
    public void step2() {  
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
//        firststdevInfo.put("Serial Number1", "5B42940000239");
//        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
//        
//        boolean result = true;
       
        
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new AccountPage(false).enterLocation(WebportalParam.location1);
        new DeviceBulkOnboardingPage().GoToSummaryPage(WebportalParam.location1);
        String filePath = new HardBundlePage().GetcurrentPathMub(WebportalParam.location1);
        filePath = filePath + "PRJCBUGEN_T21913\\Device.csv";
        System.out.println(filePath);
        String warningForWrongFormat = new DeviceBulkOnboardingPage().ImportWrongformatFile(filePath);
        new HardBundlePage().OnboardBulk();
    }


}
