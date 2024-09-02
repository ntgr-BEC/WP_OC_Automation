package webportal.Orbi.WiredBVPN.PRJCBUGEN_T27766;

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
//import util.Javasocket;
import util.SocketCommand;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("WiredBVPN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27766") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Scenario1: verify if the tunneled interfaces IP changes in case the user changes the subnet mask of the connected routers") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27766") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
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
        } catch (Throwable e) {
            System.out.println("Failed to restore subnet and delete the VPN group");
        }
        
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
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T27766", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.sleepi(10);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "PRJCBUGEN-T27766", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        // edit remote router subnet
        page.clickEditVPNGroup("TestGroup");
        util.MyCommonAPIs.sleepi(5);
        page.clickNext();
        page.setPage2EditRemoteRouter(WebportalParam.ob2deveiceName, "192.168.2.1", "192.168.2.0/24");  
        page.clickNext();
        page.clickNext();
        page.clickNext();
        util.MyCommonAPIs.sleepi(10);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        util.MyCommonAPIs.sleepsyncorbi();
    }
    
    @Step("Test Step 3: Check remote wired client get new IP")
    public void step3() {
        boolean checkpoint = false;
        // enable nic of wired client
        new SocketCommand().EnableNIC(WebportalParam.client1ip, WebportalParam.client1port, WebportalParam.ob2CPCNIC);
        
        // get client IP
        String remote_client_ip = new SocketCommand().getNICIP(WebportalParam.client1ip, WebportalParam.client1port, WebportalParam.ob2CPCNIC);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(remote_client_ip);
        // check ip
        checkpoint = remote_client_ip.contains("192.168.2.");
        assertTrue(checkpoint, "checkpoint 1 : remote wired client get a new IP");
    }
    
}
