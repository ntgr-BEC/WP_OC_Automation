package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.VPNTunnel.PRJCBUGEN_T25324;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.webelements.DNIOrbiCommanElement;
import orbi.weboperation.OrbiLoginPage;
import testbase.TestCaseBase;
import util.Javasocket;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.VPNTunnel") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25324") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the user connected with VPN SSID after that able to access the device's UI.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25324") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("tearDown : delete route / disconnect wifi / enable NIC to orbi / delete VPN group");
        
        String cmd = "route delete %s";
        new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format(cmd, WebportalParam.ob1IPaddress));
        util.MyCommonAPIs.sleep(3000);
        
        new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFdisconnect");
        util.MyCommonAPIs.sleep(5000);
        
        cmd = "netsh interface set interface %s enable";
        new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format(cmd, WebportalParam.ob1CPCNIC));
        util.MyCommonAPIs.sleep(5000);
        new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format(cmd, WebportalParam.ob2CPCNIC));
        util.MyCommonAPIs.sleep(5000);
        
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
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
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T25324", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady(); // wait business vpn group setup successfully
        util.MyCommonAPIs.sleep(5000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "PRJCBUGEN-T25324", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        UserManage userManage = new UserManage();
        userManage.logout();
        
    }
    
    @Step("Test Step 3: Disable NIC to central router and remote router on client1 / client1 connects to remote router via VPN")
    public void step3() {
        boolean checkpoint = false;
        String cmd;
        String client_ip = "";
        
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
            cmd = String.format("WAFconnectBSSID PRJCBUGEN-T25324 12345678 WPA2PSK aes %s", WebportalParam.ob2SSIDWANbssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
            System.out.println(checkpoint);
            assertTrue(checkpoint, "checkpoint 2 : client1 can connect to remote router");
            util.MyCommonAPIs.sleep(10000);
            
            // get client ip
            client_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFgetip Wi-Fi");
            String[] splited = client_ip.split("\\s+");
            client_ip = splited[1];
            System.out.println(client_ip);
            
            // ping test
            // from client1 to central router
            cmd = String.format("WAFping %s %s", WebportalParam.ob1IPaddress, client_ip);
            String ping_result = new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd);
            System.out.println(ping_result);
            
            
            String s = "Reply from %s:";
            if(ping_result.contains(String.format(s, WebportalParam.ob1IPaddress)) ) {
                checkpoint = true;
                break;
            }
            
            checkpoint = false;
        }
        assertTrue(checkpoint, "checkpoint 3 : client can ping central router");
        
        cmd = "route -p add %s MASK 255.255.255.255 %s";
        new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format(cmd, WebportalParam.ob1IPaddress, WebportalParam.ob2IPaddress));
        util.MyCommonAPIs.sleep(5000);
    }
    
    @Step("Test Step 4: Open central router UI")
    public void step4() {
        boolean checkpoint;
        OrbiLoginPage page = new OrbiLoginPage("admin", WebportalParam.loginDevicePassword, WebportalParam.ob1IPaddress);
        util.MyCommonAPIs.sleep(120); // the performance of ssid-wan is not good, so loading web page costs a lot of time
        try {
            Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
            checkpoint = true;
        }catch(Throwable e) {
            checkpoint = false;
        }
        assertTrue(checkpoint, "checkpoint 4 : client connected with remote router can open central router's local GUI");
    }
}
