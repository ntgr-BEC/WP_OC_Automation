package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.VLAN.PRJCBUGEN_T25406;

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
    @Feature("SSID_WAN.ConfigureBusinessVPN.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25406") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether user able to select and apply listed VLAN profile.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25406") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.DelVPNGroup("VLANGroup");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Routers page / User can edit ip address and subnet for SRR60 as remote router")
    public void step2() {
        String central_loc = "";
        String central_router = "";
        String remote_loc = "";
        String remote_router = "";
        
        if (WebportalParam.ob2Model.contains("SXR")) {
            central_loc = WebportalParam.location1;
            central_router = WebportalParam.ob1deveiceName;
            remote_loc = WebportalParam.location2;
            remote_router = WebportalParam.ob2deveiceName;
        }
        else if (WebportalParam.ob1Model.contains("SXR")) {
            central_loc = WebportalParam.location2;
            central_router = WebportalParam.ob2deveiceName;
            remote_loc = WebportalParam.location1;
            remote_router = WebportalParam.ob1deveiceName;
        }
        else {
            org.testng.Assert.fail("no router is SXR");
        }
        
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("VLANGroup","TestDescription","900");
        page.setPage2AddCentralRouter(central_loc, central_router);
        page.setPage2AddRemoteRouter(remote_loc, remote_router);
        // user edit ip address and subnet for non-default vlan Employee (20) (change 3rd byte)
        page.setPage2EditRemoteRouter(remote_router, "Employee (20)", "172.16.21.1", "172.16.21.0/24");
        checkpoint = page.RouterExist(remote_loc, remote_router, "Employee Home", "172.16.21.1", "172.16.21.0/24");
        assertTrue(checkpoint, "checkpoint 1 : SXR80 can update the vlan profile.");
       
        page.clickNext();
        page.setPage3WirelessSettings(true, "Secure_VPN", 1, "87654321");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.sleep(10000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        if(!page.GroupExist("VLANGroup", "Secure_VPN", central_router, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        page.gotoPage();
        if(!page.GroupExist("VLANGroup", "Secure_VPN", central_router, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
    }
    
    @Step("Test Step 3: Check client connect to remote router get correct ip")
    public void step3() {
        boolean checkpoint = false;
        String cmd;
        for(int i=0;i<3;i++) {
            
            // restart wifi
            new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, "WAFdisconnect");
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, String.format("WAFwifiOff %s", WebportalParam.client2wifi));
            util.MyCommonAPIs.sleep(5000);
            new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, String.format("WAFwifiOn %s", WebportalParam.client2wifi));
            util.MyCommonAPIs.sleep(5000);
            
            // connect to bssid
            checkpoint = true;
            cmd = String.format("WAFconnectBSSID Secure_VPN 87654321 WPA2PSK aes %s", WebportalParam.ob2SSIDWANbssid);
            if (!new Javasocket()
                    .sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd)
                    .equals("true")) {
                checkpoint = false;
            }
            System.out.println(checkpoint);
            assertTrue(checkpoint, "checkpoint 1 : client can connect to remote router");
            util.MyCommonAPIs.sleep(10000);
            
            // get client ip
            String client_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, "WAFgetip Wi-Fi");
            String[] splited = client_ip.split("\\s+");
            client_ip = splited[1];
            System.out.println(client_ip);
            
            if( client_ip.contains("172.16.21") ) {
                checkpoint = true;
                break;
            }
            checkpoint = false;
            
        }
        assertTrue(checkpoint, "checkpoint3 : client get vlan20 ip");
    }
    
}
