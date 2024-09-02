package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.RemoteRouter.PRJCBUGEN_T25296;

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
    @Feature("SSID_WAN.ConfigureBusinessVPN.RemoteRouter") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25296") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify if Router Isolation is enabled/disabled then both routers are able to ping or not.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25296") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : enable nic to routers / delete VPN group");
        try {
            String cmd = "netsh interface set interface %s enable";
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port,
                    String.format(cmd, WebportalParam.ob1CPCNIC));
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port,
                    String.format(cmd, WebportalParam.ob2CPCNIC));
            util.MyCommonAPIs.sleep(5000);
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            // TODO: handle exception
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Routers page / User create a VPN group with two remote routers.")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","Test Description","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob2deveiceName, 1, true, false);
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob3deveiceName, 1, true, true);
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T25296", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady();
        util.MyCommonAPIs.sleep(3000); 
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "PRJCBUGEN-T25296", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
    }
    
    @Step("Test Step 3: Check client1 connected to 1st remoter router cannot ping 2nd remote router")
    public void step3() {
        boolean checkpoint = false;
        String cmd;
        
        // disable nic to router
        cmd = "netsh interface set interface %s disable";
        new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format(cmd, WebportalParam.ob1CPCNIC));
        util.MyCommonAPIs.sleep(5000);
        new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format(cmd, WebportalParam.ob2CPCNIC));
        util.MyCommonAPIs.sleep(5000);
        
        for(int i=0;i<3;i++) {
            
            // restart wifi
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFdisconnect");
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOff %s", WebportalParam.client1wifi));
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOn %s", WebportalParam.client1wifi));
            util.MyCommonAPIs.sleep(10000);
            
            // connect to bssid
            checkpoint = true;
            cmd = String.format("WAFconnectBSSID PRJCBUGEN-T25296 12345678 WPA2PSK aes %s", WebportalParam.ob2SSIDWANbssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
            System.out.println(checkpoint);
            assertTrue(checkpoint, "checkpoint 2 : client1 can connect to 1st remote router");
            util.MyCommonAPIs.sleep(10000);
            
            // get client ip
            String client1_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFgetip Wi-Fi");
            String[] splited = client1_ip.split("\\s+");
            client1_ip = splited[1];
            System.out.println(client1_ip);
            
            // ping test
            // from remote client to 2nd remote router
            cmd = String.format("WAFping %s %s", WebportalParam.ob3IPaddress, client1_ip);
            String ping_result = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd);
            System.out.println(ping_result);
            
            String s = "Reply from %s:";
            if(!ping_result.contains(String.format(s, WebportalParam.ob3IPaddress)) ) {
                checkpoint = true;
                break;
            }
            checkpoint = false;
        }
        assertTrue(checkpoint, "checkpoint 3 : client1 cannot ping 2nd remote router that is isolated");
    }
    
    @Step("Test Step 4: Set 2nd remote router's isolation to be disable")
    public void step4() {
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickEditVPNGroup("TestGroup");
        util.MyCommonAPIs.sleep(1000);
        page.clickNext();
        page.setPage2EditRemoteRouter(WebportalParam.ob3deveiceName, true, false);
        page.clickNext();
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady();
        util.MyCommonAPIs.sleep(3000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
    }
    
    @Step("Test Step 5: Check client1 connected to 1st remoter router can ping 2nd remote router")
    public void step5() {
        boolean checkpoint = false;
        String cmd;
        for(int i=0;i<3;i++) {
            
            // restart wifi
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFdisconnect");
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOff %s", WebportalParam.client1wifi));
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOn %s", WebportalParam.client1wifi));
            util.MyCommonAPIs.sleep(10000);
            
            // connect to bssid
            checkpoint = true;
            cmd = String.format("WAFconnectBSSID PRJCBUGEN-T25296 12345678 WPA2PSK aes %s", WebportalParam.ob2SSIDWANbssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
            System.out.println(checkpoint);
            assertTrue(checkpoint, "checkpoint 2 : client1 can connect to remote router");
            util.MyCommonAPIs.sleep(10000);
            
            // get client ip
            String client1_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFgetip Wi-Fi");
            String[] splited = client1_ip.split("\\s+");
            client1_ip = splited[1];
            System.out.println(client1_ip);
            
            
            // ping test
            // from remote client to 2nd remote router
            cmd = String.format("WAFping %s %s", WebportalParam.ob3IPaddress, client1_ip);
            String ping_result = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd);
            System.out.println(ping_result);
            
            String s = "Reply from %s:";
            if(ping_result.contains(String.format(s, WebportalParam.ob3IPaddress)) ) {
                checkpoint = true;
                break;
            }
            checkpoint = false;
        }
        assertTrue(checkpoint, "checkpoint 3 : client1 can ping 2nd remote router that is not isolated");
    }
    
}
