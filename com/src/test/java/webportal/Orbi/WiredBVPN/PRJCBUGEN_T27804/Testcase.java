package webportal.Orbi.WiredBVPN.PRJCBUGEN_T27804;

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
//import util.Javasocket;
import util.SocketCommand;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("WiredBVPN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27804") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Scenario2: Test to verify if a router is connected to an L2 switch then further connected hosts should not have access to the business VPN group") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27804") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p200")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
        try {
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            System.out.println("Failed to delete the VPN group");
        }
        
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        
    }

    @Step("Test Step 2: Go to Routers page / User create a VPN group with router mode set as employee home.")
    public void step2() {
        
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","Test Description","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob2deveiceName, 1, true);
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T27804", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.sleepi(10);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "PRJCBUGEN-T27804", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        
    }
    
    @Step("Test Step 3: Remote wired client cannot ping central router")
    public void step3() {
        boolean checkpoint = false;
        // enable nic of wired client
        new SocketCommand().EnableNIC(WebportalParam.client1ip, WebportalParam.client1port, WebportalParam.ob2CPCNIC);
        
        // get client IP
        String remote_client_ip = new SocketCommand().getNICIP(WebportalParam.client1ip, WebportalParam.client1port, WebportalParam.ob2CPCNIC);
        
        // ping test
        checkpoint = new SocketCommand().Pingtest(remote_client_ip, WebportalParam.ob1IPaddress, WebportalParam.client1ip, WebportalParam.client1port);
        assertFalse(checkpoint, "checkpoint 1 : remote wired client cannot ping central router");
    }
    
}
