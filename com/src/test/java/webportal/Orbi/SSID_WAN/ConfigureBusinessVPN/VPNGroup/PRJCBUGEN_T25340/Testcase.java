package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.VPNGroup.PRJCBUGEN_T25340;

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
    @Feature("SSID_WAN.ConfigureBusinessVPN.VPNGroup") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25340") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the Edit VPN group data.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25340") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Go to Routers page / User can edit ip address and subnet for SXR80 as remote router")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        // user edit ip address and subnet for default vlan (change 3rd byte)
        page.setPage2EditRemoteRouter(WebportalParam.ob2deveiceName, "Iot (30)", "172.16.31.1", "172.16.31.0/24");
        checkpoint = page.RouterExist(WebportalParam.location2, WebportalParam.ob2deveiceName, "Employee Home", "172.16.31.1", "172.16.31.0/24");
        assertTrue(checkpoint, "checkpoint 2 : ip and network are updated for non-default vlan");
        
    }
}
