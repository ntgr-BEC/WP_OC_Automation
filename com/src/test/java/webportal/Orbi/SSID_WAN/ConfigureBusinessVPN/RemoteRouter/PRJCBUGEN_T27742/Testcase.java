package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.RemoteRouter.PRJCBUGEN_T27742;

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
import orbi.telnetoperation.OrbiTelnet;
import orbi.weboperation.OrbiDebugSettingsPage;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */

// this script is modified for a general case, from 27024 and 27025
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.RemoteRouter") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27742") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify able to edit the IP address or subnet.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27742") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : delete VPN group");
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickEditVPNGroup("TestGroup");
        page.clickNext();
        page.setPage2EditRemoteRouter(WebportalParam.ob2deveiceName, WebportalParam.ob2IPaddress, WebportalParam.ob2IPsubnet);
        page.clickNext();
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady();
        util.MyCommonAPIs.sleep(5000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        page.DelVPNGroup("TestGroup");
    }

    // Each step covers the operation in one page
    
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Routers page / User can edit ip address and subnet for remote router")
    public void step2() {
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        // user edit ip address and subnet for default vlan (change 3rd byte)
        page.setPage2EditRemoteRouter(WebportalParam.ob2deveiceName, "172.16.2.1", "172.16.2.0/24");
        page.clickNext();
        page.setPage3WirelessSettings(true,"SSID-WAN-Auto-Test",1,"12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 3000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "SSID-WAN-Auto-Test", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
    }
    
    @Step("Test Step 3: Check client can get the correct ip")
    public void step3() {
        boolean checkpoint = false;
        String cmd;
        
        for(int i=0;i<3;i++) {
            
            // restart wifi
            new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, "WAFdisconnect");
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, String.format("WAFwifiOff %s", WebportalParam.client2wifi));
            util.MyCommonAPIs.sleep(10000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, String.format("WAFwifiOn %s", WebportalParam.client2wifi));
            util.MyCommonAPIs.sleep(10000);
            
            // connect to bssid
            checkpoint = true;
            cmd = String.format("WAFconnectBSSID SSID-WAN-Auto-Test 12345678 WPA2PSK aes %s", WebportalParam.ob2SSIDWANbssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
            System.out.println(checkpoint);
            assertTrue(checkpoint, "checkpoint 2 : remote client can connect to remote satellite");
            util.MyCommonAPIs.sleep(10000);
            
            // get client ip
            String remote_client_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, "WAFgetip Wi-Fi");
            String[] splited = remote_client_ip.split("\\s+");
            remote_client_ip = splited[1];
            System.out.println(remote_client_ip);
            
            if(remote_client_ip.contains("172.16.2")) {
                checkpoint = true;
                break;
            }
            
            checkpoint = false;
        }
        assertTrue(checkpoint, "checkpoint 3 : remote client get correct ip");
        
    }
    
}
