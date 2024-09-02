package webportal.Orbi.WiredBVPN.PRJCBUGEN_T27771;

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
import util.MyCommonAPIs;
//import util.Javasocket;
import util.SocketCommand;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.DevicesOrbiNetworkSetupPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    DevicesOrbiNetworkSetupPage orbiNetwork = null;
    String                      vlan20Name    = "Employee";
    String                      vlan1Name    = "Default";
    String                      vlan20Id      = "20";
    String                      vlan1Id      = "1";
    String                      portlist      = "1,2,3";
    
    @Feature("WiredBVPN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27771") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Scenario1: verify that when all diff ports participating in different VLANs can connect to the business VPN") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27771") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p200")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
        try {
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            // restore to original subnet
            page.clickEditVPNGroup("TestGroup");
            util.MyCommonAPIs.sleepi(5);
            page.clickNext();
            page.setPage2EditRemoteRouter(WebportalParam.ob2deveiceName, WebportalParam.ob2IPaddress, WebportalParam.ob2IPsubnet);  
            page.clickNext();
            page.clickNext();
            page.clickNext();
            util.MyCommonAPIs.sleepi(10);
            page.btnViewVPNGroup.click();
            util.MyCommonAPIs.sleepsyncorbi();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
            ddp.gotoPage();
            ddp.openOB2();
            orbiNetwork = new DevicesOrbiNetworkSetupPage();
            orbiNetwork.gotoPage();
            orbiNetwork.networkData.portList = portlist;
            orbiNetwork.networkData.portMode = 1;
            orbiNetwork.editNetwork(vlan1Name);
            MyCommonAPIs.sleepsyncorbi();
        } catch (Throwable e) {
            System.out.println("Failed to restore");
        }
        
        
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 2 where the remote router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction(WebportalParam.location2);
    }
    
    @Step("Test Step 2: Configure VLAN20 on remote router LAN port")
    public void step2() {
        ddp.gotoPage();
        ddp.openOB2();
        orbiNetwork = new DevicesOrbiNetworkSetupPage();
        orbiNetwork.gotoPage();
        orbiNetwork.networkData.portList = portlist;
        orbiNetwork.networkData.portMode = 1;
        orbiNetwork.editNetwork(vlan20Name);
        MyCommonAPIs.sleepsyncorbi();
    }
    
    @Step("Test Step 3: Go to Routers page / User create a VPN group with remote route in vlan 20.")
    public void step3() {
        
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","Test Description","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob2deveiceName, 1, true);
        page.setPage2EditRemoteRouter(WebportalParam.ob2deveiceName, "Employee (20)", "", "");
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T27771", 1, "12345678");
        page.clickNext();
        page.clickNext(); 
        page.successicon.waitUntil(Condition.appear, 30000, 5000); //click next (actual is save btn)
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "PRJCBUGEN-T27771", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        
    }
    
    @Step("Test Step 4: Check remote wired client get new IP")
    public void step4() {
        boolean checkpoint = false;
        // enable nic of wired client
        new SocketCommand().EnableNIC(WebportalParam.client1ip, WebportalParam.client1port, WebportalParam.ob2CPCNIC);
        
        // get client IP
        String remote_client_ip = new SocketCommand().getNICIP(WebportalParam.client1ip, WebportalParam.client1port, WebportalParam.ob2CPCNIC);
        
        // check ip
        checkpoint = remote_client_ip.contains("192.168.2.");
        assertTrue(checkpoint, "checkpoint 1 : remote wired client get a new IP");
    }
    
}
