package webportal.Orbi.SSID_WAN.SSID_WANTestcases.AccessControl.PRJCBUGEN_T26017;

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
    @Story("PRJCBUGEN_T26017") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether user able to create an MAC ACL deny rule for same Client associated different routers in single VPN group") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26017") // It's a testcase id/link from Jira Test Case.
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
    
    @Step("Test Step 2: Go to Routers page / User add deny rule on central router and remote router for client1 in access control page")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T26017", 1, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl(WebportalParam.client1name, WebportalParam.client1wifimac, 1, WebportalParam.ob1deveiceName);
        page.cirbtnAdd.click();
        page.setPage4AccessControl(WebportalParam.client1name, WebportalParam.client1wifimac, 1, WebportalParam.ob2deveiceName);
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.sleep(10000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); //wait 5 mins to let business vpn finish setting up
    }
    
    @Step("Test Step 3: Check client1 can ping central router")
    public void step3() {
        boolean checkpoint = false;
        String cmd;
        for(int i=0;i<3;i++) {
            checkpoint = true;
            // restart wifi
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFdisconnect");
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOff %s", WebportalParam.client1wifi));
            util.MyCommonAPIs.sleep(10000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOn %s", WebportalParam.client1wifi));
            util.MyCommonAPIs.sleep(10000);
            // connect to bssid (remote router's)
            cmd = String.format("WAFconnectBSSID PRJCBUGEN-T26017 12345678 WPA2PSK aes %s", WebportalParam.ob1SSIDWANbssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
            System.out.println(checkpoint);
            assertTrue(checkpoint, "checkpoint 1 : client1 can connect to central router");
            util.MyCommonAPIs.sleep(10000);
            
            // get client ip
            String remote_client_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFgetip Wi-Fi");
            String[] splited = remote_client_ip.split("\\s+");
            remote_client_ip = splited[1];
            System.out.println(remote_client_ip);
            
            // ping test
            // from central client to central router
            cmd = String.format("WAFping %s %s", WebportalParam.ob1IPaddress, remote_client_ip);
            String ping_result = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd);
            System.out.println(ping_result);
            String loss_percentage = ping_result.substring(ping_result.indexOf("(")+1, ping_result.indexOf("%"));
            System.out.println(loss_percentage);
            
            String s = "Reply from %s:";
            if(ping_result.contains(String.format(s, WebportalParam.ob1IPaddress)) ) {
                checkpoint = true;
                break;
            }
            checkpoint = false;
            
        }
        assertTrue(checkpoint, "checkpoint 2 : client1 can ping central router");
    }
    
    @Step("Test Step 4: Check client1 can ping to remote router")
    public void step4() {
        boolean checkpoint = false;
        String cmd;
        for(int i=0;i<3;i++) {
            checkpoint = true;
            // restart wifi
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFdisconnect");
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOff %s", WebportalParam.client1wifi));
            util.MyCommonAPIs.sleep(10000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOn %s", WebportalParam.client1wifi));
            util.MyCommonAPIs.sleep(10000);
            // connect to bssid (remote router's)
            cmd = String.format("WAFconnectBSSID PRJCBUGEN-T26017 12345678 WPA2PSK aes %s", WebportalParam.ob2SSIDWANbssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
            System.out.println(checkpoint);
            assertTrue(checkpoint, "checkpoint 3 : client1 can connect to remote router");
            util.MyCommonAPIs.sleep(10000);
            
            // get client ip
            String remote_client_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFgetip Wi-Fi");
            String[] splited = remote_client_ip.split("\\s+");
            remote_client_ip = splited[1];
            System.out.println(remote_client_ip);
            
            // ping test
            // from central client to central router
            cmd = String.format("WAFping %s %s", WebportalParam.ob2IPaddress, remote_client_ip);
            String ping_result = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd);
            System.out.println(ping_result);
            
            String s = "Reply from %s:";
            if(ping_result.contains(String.format(s, WebportalParam.ob2IPaddress)) ) {
                checkpoint = true;
                break;
            }
            checkpoint = false;
            
        }
        assertTrue(checkpoint, "checkpoint 4 : client1 can ping remote router");
    }
    
}
