package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.RouterIsolation.PRJCBUGEN_T25312;

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
import util.Javasocket;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author jim.xie
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.RouterIsolation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25312") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Check whether we are able to ping the Branch office router from the remote router where Router isolation is enabled") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25312") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : delete VPN group");
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.DelVPNGroup("TestGroup");
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
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob3deveiceName, 2, false);
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T25312", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady();;
        util.MyCommonAPIs.sleep(3000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        checkpoint = page.GroupExist("TestGroup", "PRJCBUGEN-T25312", WebportalParam.ob1deveiceName, "Healthy");
        if(!checkpoint) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
    }
    
   
    @Step("Test Step 3: Check client connect to employee home router cannot ping branch office router")
    public void step3() {
        boolean checkpoint = false;
        String cmd;
        for(int i=0;i<3;i++) {
            
            // restart wifi
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFdisconnect");
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOff %s", WebportalParam.client1wifi));
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOn %s", WebportalParam.client1wifi));
            util.MyCommonAPIs.sleep(5000);
            
            // connect to bssid
            checkpoint = true;
            cmd = String.format("WAFconnectBSSID PRJCBUGEN-T25312 12345678 WPA2PSK aes %s", WebportalParam.ob2SSIDWANbssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
            System.out.println(checkpoint);
            assertTrue(checkpoint, "checkpoint 1 : client can connect to employee home router");
            util.MyCommonAPIs.sleep(10000);
            
            // get client ip
            String client_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFgetip Wi-Fi");
            String[] splited = client_ip.split("\\s+");
            client_ip = splited[1];
            System.out.println(client_ip);
            
            // ping test
            // from client to branch office router
            cmd = String.format("WAFping %s %s", WebportalParam.ob3IPaddress, client_ip);
            String ping_result = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd);
            System.out.println(ping_result);
            
            // check if clients receive icmp response
            String s = "Reply from %s:";
            if(!ping_result.contains(String.format(s, WebportalParam.ob3IPaddress)) ) {
                checkpoint = true;
                break;
            }
            checkpoint = false;
        }
        assertTrue(checkpoint, "checkpoint3 : client connect to employee home router cannot ping branch office router");
    }
    
}
