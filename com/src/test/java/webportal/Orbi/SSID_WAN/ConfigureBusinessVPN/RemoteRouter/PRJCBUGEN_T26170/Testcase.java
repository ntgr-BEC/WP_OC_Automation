package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.RemoteRouter.PRJCBUGEN_T26170;

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
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.RemoteRouter") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26170") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the to add multiple remote routers.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26170") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Go to Routers page / User can add two remote routers")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        // user add a central router and a remote router
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob3deveiceName);
        
        checkpoint = page.RouterExist(WebportalParam.location1, WebportalParam.ob1deveiceName, "Central Router");
        assertTrue(checkpoint, "checkpoint 1 : central router exists");
        checkpoint = page.RouterExist(WebportalParam.location2, WebportalParam.ob2deveiceName, "Employee Home");
        assertTrue(checkpoint, "checkpoint 2 : 1st remote router exists");
        checkpoint = page.RouterExist(WebportalParam.location2, WebportalParam.ob3deveiceName, "Employee Home");
        assertTrue(checkpoint, "checkpoint 3 : 2nd remote router exists");
    }
}
