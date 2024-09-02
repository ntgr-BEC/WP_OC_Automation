package webportal.Orbi.SSID_WAN.SanityBusinessVPNFlow.PRJCBUGEN_T26879;

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
import webportal.publicstep.UserManage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.SanityBusinessVPNFlow") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26879") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether wireless clients connected to ceiling satellite SRC60 via VPN SSID able to access VPN") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26879") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p200")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("tearDown : delete VPN group");
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
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob2deveiceName, 2, true);
        page.clickNext();
        page.setPage3WirelessSettings(true, "SSID-WAN-Auto-Test", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady(); // wait business vpn group setup successfully
        util.MyCommonAPIs.sleep(5000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        util.MyCommonAPIs.sleepsyncorbi();
    }
    
    @Step("Test Step 3: Remote client can connect to SRC60")
    public void step3() {
        boolean checkpoint = false;
        String cmd;
        String satellite_bssid = "";
        // check satellite1 and satellite2 contains SRC60
        if(WebportalParam.ob2sate1Model.equals("SRC60")) {
            satellite_bssid = WebportalParam.ob2sate1SSIDWANbssid;
        }
        else if(WebportalParam.ob2sate2Model.equals("SRC60")) {
            satellite_bssid = WebportalParam.ob2sate2SSIDWANbssid;
        }
        assertFalse(satellite_bssid.equals(""), "checkpoint 1 : SRC60 is in config");
        
        for(int i=0;i<3;i++) {
            
            // restart wifi
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFdisconnect");
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOff %s", WebportalParam.client1wifi));
            util.MyCommonAPIs.sleep(10000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format("WAFwifiOn %s", WebportalParam.client1wifi));
            util.MyCommonAPIs.sleep(10000);
            
            // connect to bssid
            checkpoint = true;
            cmd = String.format("WAFconnectBSSID SSID-WAN-Auto-Test 12345678 WPA2PSK aes %s", satellite_bssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
            System.out.println(checkpoint);
            assertTrue(checkpoint, "checkpoint 2 : remote client can connect to remote satellite");
            util.MyCommonAPIs.sleep(10000);
            
            // get client ip
            String remote_client_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFgetip Wi-Fi");
            String[] splited = remote_client_ip.split("\\s+");
            remote_client_ip = splited[1];
            System.out.println(remote_client_ip);
            
            
            // ping test
            
            // from remote client to central client
            cmd = String.format("WAFping %s %s", WebportalParam.ob1IPaddress, remote_client_ip);
            String ping_result1 = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd);
            System.out.println(ping_result1);
            
            String s = "Reply from %s:";
            if(ping_result1.contains(String.format(s, WebportalParam.ob1IPaddress)) ) {
                checkpoint = true;
                break;
            }
            
            checkpoint = false;
        }
        assertTrue(checkpoint, "checkpoint 3 : remote client can ping central router");
    }
    
}
