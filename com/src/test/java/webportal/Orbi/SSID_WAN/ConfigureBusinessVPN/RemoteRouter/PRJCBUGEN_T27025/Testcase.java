package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.RemoteRouter.PRJCBUGEN_T27025;

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
    @Story("PRJCBUGEN_T27025") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, edit the IP address and subnet in the SRR60 device.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27025") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p200")
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

    @Step("Test Step 2: Go to Routers page / User can edit ip address and subnet for SRR60 as remote router")
    public void step2() {
        String central_loc = "";
        String central_router = "";
        String remote_loc = "";
        String remote_router = "";
        
        if (WebportalParam.ob2Model.equals("SRR60")) {
            central_loc = WebportalParam.location1;
            central_router = WebportalParam.ob1deveiceName;
            remote_loc = WebportalParam.location2;
            remote_router = WebportalParam.ob2deveiceName;
        }
        else if (WebportalParam.ob1Model.equals("SRR60")) {
            central_loc = WebportalParam.location2;
            central_router = WebportalParam.ob2deveiceName;
            remote_loc = WebportalParam.location1;
            remote_router = WebportalParam.ob1deveiceName;
        }
        else {
            org.testng.Assert.fail("no router is SRR60");
        }
        
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter(central_loc, central_router);
        page.setPage2AddRemoteRouter(remote_loc, remote_router);
        // user edit ip address and subnet (change 3rd byte)
        page.setPage2EditRemoteRouter(remote_router, "172.16.2.1", "172.16.2.0/24");
        checkpoint = page.RouterExist(remote_loc, remote_router, "Employee Home", "172.16.2.1", "172.16.2.0/24");
        assertTrue(checkpoint, "checkpoint 1 : ip and network are updated");
    }
}
