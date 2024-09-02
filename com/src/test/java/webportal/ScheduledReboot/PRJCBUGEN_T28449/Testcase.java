package webportal.ScheduledReboot.PRJCBUGEN_T28449;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    String Name = "Schedule1";

    
    @Feature("ScheduledReboot") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28449") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, Devices part of policy will be greyed out and can not be chosen for 'Reboot Now' or creating another schedule policy") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T28449") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).DeleteSchedule(Name);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(WebportalParam.Organizations);
        handle.gotoLoction();
    }

    @Step("Test Step 2: go to Organization and check Reboot option is their or not;")
    public void step2() {
       new OrganizationPage().gotoSchedulereboot();
       assertTrue(new OrganizationPage(false).PickDateTime(Name, WebportalParam.ap1serialNo, "None"), "Warnning pop up did not appear");
    }

    @Step("Test Step 3: verify, one device can be part of one scheduled reboot;")
    public void step3() {
        new OrganizationPage().gotoSchedulereboot();
        assertTrue(new OrganizationPage(false).Multiple( WebportalParam.ap1serialNo), "one device is not part of one schedule");
    }
   
}
