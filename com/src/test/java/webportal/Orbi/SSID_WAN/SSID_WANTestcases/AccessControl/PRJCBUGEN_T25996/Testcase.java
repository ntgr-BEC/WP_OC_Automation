package webportal.Orbi.SSID_WAN.SSID_WANTestcases.AccessControl.PRJCBUGEN_T25996;

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
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.SSID_WANTestcases.AccessControl") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25996") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether user able to create an MAC ACL allow rule") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25996") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
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
    
    @Step("Test Step 2: Go to Routers page / User add one allow rule on remote router in access control page")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T25996", 1, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl(WebportalParam.client1name, WebportalParam.client1wifimac, 0, WebportalParam.ob2deveiceName);
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.sleep(10000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); //wait 5 mins to let business vpn finish setting up
    }
    
    @Step("Test Step 3: Check central client cannot connect to remote router but remote client can")
    public void step3() {
        boolean checkpoint = false;
        String cmd;
        for(int i=0;i<3;i++) {
            checkpoint = true;
            // restart wifi
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFdisconnect");
            new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, "WAFdisconnect");
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOff %s", WebportalParam.client1wifi));
            new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, String.format("WAFwifiOff %s", WebportalParam.client2wifi));
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOn %s", WebportalParam.client1wifi));
            new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, String.format("WAFwifiOn %s", WebportalParam.client2wifi));
            util.MyCommonAPIs.sleep(5000);
            // connect to bssid (remote router's)
            cmd = String.format("WAFconnectBSSID PRJCBUGEN-T25996 12345678 WPA2PSK aes %s", WebportalParam.ob2SSIDWANbssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
            System.out.println(checkpoint);
            assertTrue(checkpoint, "checkpoint 1 : central client can connect to the router");
            util.MyCommonAPIs.sleep(5000);
            
            checkpoint = true;
            cmd = String.format("WAFconnectBSSID PRJCBUGEN-T25996 12345678 WPA2PSK aes %s", WebportalParam.ob2SSIDWANbssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
            System.out.println(checkpoint);
            assertTrue(checkpoint, "checkpoint 2 : remote client can connect to remote router");
            util.MyCommonAPIs.sleep(10000);
            
            // get client ip
            String central_client_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFgetip Wi-Fi");
            String[] splited = central_client_ip.split("\\s+");
            central_client_ip = splited[1];
            System.out.println(central_client_ip);
            
            String remote_client_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, "WAFgetip Wi-Fi");
            splited = remote_client_ip.split("\\s+");
            remote_client_ip = splited[1];
            System.out.println(remote_client_ip);
            
            // ping test
            // from central client to remote router
            cmd = String.format("WAFping %s %s", WebportalParam.ob2IPaddress, central_client_ip);
            String ping_result1 = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd);
            System.out.println(ping_result1);
            // from remote client to remote router
            cmd = String.format("WAFping %s %s", WebportalParam.ob2IPaddress, remote_client_ip);
            String ping_result2 = new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd);
            System.out.println(ping_result2);
            
            String s = "Reply from %s:";
            if(!ping_result1.contains(String.format(s, WebportalParam.ob2IPaddress)) && ping_result2.contains(String.format(s, WebportalParam.ob2IPaddress))) {
                checkpoint = true;
                break;
            }
            checkpoint = false;
            
        }
        assertTrue(checkpoint, "checkpoint 3 : client1 is denied and client2 is allowed on remote router");
    }
    
}
