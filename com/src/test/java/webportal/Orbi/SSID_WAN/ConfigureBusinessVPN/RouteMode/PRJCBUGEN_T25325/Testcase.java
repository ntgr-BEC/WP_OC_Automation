package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.RouteMode.PRJCBUGEN_T25325;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author jim.xie
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.RouteMode") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25325") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether the user able to select the router mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25325") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Routers page / User is able to select the router mode.")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.clickAddRemoteRouter();
        page.SelectLocation(WebportalParam.location2);
        page.SelectRouter(WebportalParam.ob2deveiceName);
        // select Employee Home Site
        checkpoint = page.SelectRouterMode(true);
        assertTrue(checkpoint, "checkpoint 1 : select Employee Home successfully");
        // select Micro Office
        checkpoint = page.SelectRouterMode(false);
        assertTrue(checkpoint, "checkpoint 2 : select Branch Office successfully");
        // click cancel in order to logout
        page.modalClickCancel();
    }
}
