package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.CentralRouter.PRJCBUGEN_T25280;

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
    @Feature("SSID_WAN.ConfigureBusinessVPN.CentralRouter") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25280") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the cancel option and save option working or not.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25280") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Go to Routers page / Check cancel and save button")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.clickAddCentralRouter();
        page.SelectLocation();
        page.SelectRouter();
        // cancel option
        page.modalClickCancel();
        checkpoint = page.RouterExist(WebportalParam.location1, WebportalParam.ob1deveiceName, "Central Router");
        assertFalse(checkpoint, "checkpoint 1 : no central router in the table");
        // save option
        page.setPage2AddCentralRouter();
        checkpoint = page.RouterExist(WebportalParam.location1, WebportalParam.ob1deveiceName, "Central Router");
        assertTrue(checkpoint, "checkpoint 2 : central router in the table");
    }
}
