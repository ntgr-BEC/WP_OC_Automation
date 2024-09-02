package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.RemoteRouter.PRJCBUGEN_T25283;

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
    @Story("PRJCBUGEN_T25283") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the user able to select the given location in the list.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25283") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Go to Routers page / User is able to select location in add remote router.")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.clickAddRemoteRouter();
        // check location is selected
        checkpoint = page.SelectLocation(WebportalParam.location2);
        assertTrue(checkpoint,"checkpoint 1 : the location is selected");
        // check other info auto filled. improvement : more than 1 router
        checkpoint = page.inputRouterIp.getValue().equals("") || page.inputRouterIpSubnet.getValue().equals("");
        assertFalse(checkpoint,"checkpoint 2 : other info is auto filled");
        /*
        checkpoint = page.inputRouterIp.getValue().equals(WebportalParam.ob2IPaddress) && page.inputRouterIpSubnet.getValue().equals(WebportalParam.ob2IPsubnet);
        assertTrue(checkpoint,"checkpoint 2 : other info is auto filled");
        */
        // click cancel in order to logout
        page.modalClickCancel();
    }
}
