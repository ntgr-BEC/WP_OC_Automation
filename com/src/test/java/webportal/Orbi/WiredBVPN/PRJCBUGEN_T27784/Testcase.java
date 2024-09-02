package webportal.Orbi.WiredBVPN.PRJCBUGEN_T27784;

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
    @Story("PRJCBUGEN_T27784") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Scenario2: verify whether the wired client can ping the wireless clients connected to the common VPN group") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27784") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
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
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T27784", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.sleepi(10);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "PRJCBUGEN-T27784", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        
    }
    
    @Step("Test Step 3: Remote wired client can ping central wireless client")
    public void step3() {
        boolean checkpoint = false;
        // enable nic
        new SocketCommand().EnableNIC(WebportalParam.client1ip, WebportalParam.client1port, WebportalParam.ob2CPCNIC);
        
        // get remote wired client IP
        String remote_client_ip = new SocketCommand().getNICIP(WebportalParam.client1ip, WebportalParam.client1port, WebportalParam.ob2CPCNIC);
        
        // connect the wireless client to central router
        new SocketCommand().DisconnectWifi(WebportalParam.client2ip, WebportalParam.client2port);
        checkpoint = new SocketCommand().BSSIDConnect(WebportalParam.client2ip, WebportalParam.client2port, "PRJCBUGEN-T27784", "12345678", WebportalParam.ob1SSIDWANbssid);
        assertTrue(checkpoint, "checkpoint 1 : wireless client can connect to central router");
        String central_client_ip = new SocketCommand().getWiFiIP(WebportalParam.client2ip, WebportalParam.client2port);
        
        // ping test
        checkpoint = new SocketCommand().Pingtest(remote_client_ip, central_client_ip, WebportalParam.client1ip, WebportalParam.client1port);
        assertTrue(checkpoint, "checkpoint 2 : remote wired client can ping central wireless client");
    }
    
    @Step("Test Step 4: Remote wireless client can ping central wired client")
    public void step4() {
        
        boolean checkpoint = false;
        // enable nic
        new SocketCommand().EnableNIC(WebportalParam.client1ip, WebportalParam.client1port, WebportalParam.ob1CPCNIC);
        
        // get central wired client IP
        String central_client_ip = new SocketCommand().getNICIP(WebportalParam.client1ip, WebportalParam.client1port, WebportalParam.ob1CPCNIC);
        
        // connect the wireless client to remote router
        new SocketCommand().DisconnectWifi(WebportalParam.client2ip, WebportalParam.client2port);
        new SocketCommand().RestartWifi(WebportalParam.client2ip, WebportalParam.client2port, WebportalParam.client2wifi);
        checkpoint = new SocketCommand().BSSIDConnect(WebportalParam.client2ip, WebportalParam.client2port, "PRJCBUGEN-T27784", "12345678", WebportalParam.ob2SSIDWANbssid);
        assertTrue(checkpoint, "checkpoint 3 : wireless client can connect to remote router");
        String remote_client_ip = new SocketCommand().getWiFiIP(WebportalParam.client2ip, WebportalParam.client2port);
        
        // ping test
        checkpoint = new SocketCommand().Pingtest(remote_client_ip, central_client_ip, WebportalParam.client2ip, WebportalParam.client2port);
        assertTrue(checkpoint, "checkpoint 4 : remote wireless client can ping central wired client");
        
    }
}
