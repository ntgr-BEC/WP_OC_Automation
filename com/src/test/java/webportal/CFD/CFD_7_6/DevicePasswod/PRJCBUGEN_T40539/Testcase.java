package webportal.CFD.CFD_7_6.DevicePasswod.PRJCBUGEN_T40539;

import static org.junit.Assert.assertFalse;
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
import webportal.publicstep.UserManage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.ManagerPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author RaviShankar
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Device_Password") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40539") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify visibility of device passwords by owners") // It's a testcase                                                                                                                           // Test Case.
    @TmsLink("PRJCBUGEN_T40539") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.ownerName, WebportalParam.ownerPassword);

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.ownerName, WebportalParam.ownerPassword);
        new OrganizationPage().openOrg(WebportalParam.Organizations);
        handle.gotoLoction();

    }

    @Step("Check for Device password under Owner")
    public void step2() {
        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        assertFalse(new DevicesDashPage(false).VerifyUserAbleToPwdReadOnly("Netgear1@"));

    }
    

}
