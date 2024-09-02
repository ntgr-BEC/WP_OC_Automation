package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.RouteMode.PRJCBUGEN_T25329;

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
import util.MyCommonAPIs;
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
    @Story("PRJCBUGEN_T25329") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether we are able to change the router mode from Branch office to Home office mode with the Edit option.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25329") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Go to Routers page / User is able to change router mode.")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter(); // select default employee home
        MyCommonAPIs.sleepi(1);
        // click edit (change to branch office)
        page.setPage2EditRemoteRouter(WebportalParam.ob2deveiceName, false);
        checkpoint = page.RouterExist(WebportalParam.location2, WebportalParam.ob2deveiceName, "Branch Office");
        assertTrue(checkpoint, "checkpoint 1 : select Employee Home successfully");
    }
}
