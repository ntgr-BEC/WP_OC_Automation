package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.RemoteRouter.PRJCBUGEN_T26804;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

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
    @Feature("SSID_WAN.ConfigureBusinessVPN.RemoteRouter") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26804") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Aggressive keepalive is working end to end or not.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26804") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            System.out.println("TearDown : delete VPN group");
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            System.out.println("teardown failed");
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Routers page / User can enable Aggressive keepalive")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.clickAddRemoteRouter();
        page.SelectLocation(WebportalParam.location2);
        page.SelectRouter(WebportalParam.ob2deveiceName);
        page.SelectVPNCredits(1);
        page.AggrKeepAlive.click();
        checkpoint = page.inputAggrKeepAlive.isSelected();
        assertTrue(checkpoint, "checkpoint 1 : aggressive keep alive is enabled");
        page.modalClickSave();
        page.clickNext();
        page.setPage3WirelessSettings(true,"PRJCBUGEN-T26804",1,"12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 3000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "PRJCBUGEN-T26804", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        
    }
    
    @Step("Test Step 3: Check e2e behavior")
    public void step3() {
        boolean checkpoint = false;
        String cmd;
        for(int i=0;i<5;i++) {
            
            // restart wifi
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFdisconnect");
            new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, "WAFdisconnect");
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOff %s", WebportalParam.client1wifi));
            new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, String.format("WAFwifiOff %s", WebportalParam.client2wifi));
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOn %s", WebportalParam.client1wifi));
            new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, String.format("WAFwifiOn %s", WebportalParam.client2wifi));
            util.MyCommonAPIs.sleep(10000);
            
            // connect to bssid
            checkpoint = true;
            cmd = String.format("WAFconnectBSSID PRJCBUGEN-T26804 12345678 WPA2PSK aes %s", WebportalParam.ob1SSIDWANbssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
            assertTrue(checkpoint, "checkpoint 1 : central client can connect to central router");
            util.MyCommonAPIs.sleep(10000);
            
            checkpoint = true;
            cmd = String.format("WAFconnectBSSID PRJCBUGEN-T26804 12345678 WPA2PSK aes %s", WebportalParam.ob2SSIDWANbssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
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
            // from central client to remote client
            cmd = String.format("WAFping %s %s", remote_client_ip, central_client_ip);
            String ping_result1 = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd);
            System.out.println(ping_result1);
            
            // from remote client to central client
            cmd = String.format("WAFping %s %s", central_client_ip, remote_client_ip);
            String ping_result2 = new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd);
            System.out.println(ping_result2);
            
            String s = "Reply from %s:";
            if( ping_result1.contains(String.format(s, remote_client_ip)) && ping_result2.contains(String.format(s, central_client_ip)) ) {
                checkpoint = true;
                break;
            }
            
            checkpoint = false;
        }
        assertTrue(checkpoint, "checkpoint 3 : end hosts can ping each other");
    }
    
}
