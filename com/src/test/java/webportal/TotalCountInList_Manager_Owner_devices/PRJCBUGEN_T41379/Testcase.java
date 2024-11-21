package webportal.TotalCountInList_Manager_Owner_devices.PRJCBUGEN_T41379;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("TotalCountInList_Manager_Owner_devices") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41379") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the total number of devices count is shown in the ALL devices list.") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T41379") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }
    
    @Step("Test Step 2: Go to settings devices page and verify total devices count;")
    public void step2() {
        
        assertTrue(new DevicesDashPage(false).verifyAllcountUnderSettingsdevicesoption(), "Total devices count not showing correctly on devices page under settings");
        
    }

}
