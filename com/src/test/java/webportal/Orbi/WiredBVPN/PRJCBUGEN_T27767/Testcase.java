package webportal.Orbi.WiredBVPN.PRJCBUGEN_T27767;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiSatellitesPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("WiredBVPN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27767") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Scenario1: verify that the IP config changes are altered in the satellites as well in case the subnet configs are changed") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27767") // It's a testcase id/link from Jira Test Case.
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
        handle.gotoLoction(WebportalParam.location2);
        
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
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T27804", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.sleepi(10);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
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
        util.MyCommonAPIs.sleepsyncorbi(); // wait the satellite info is updated to Insight
        
    }
    
    @Step("Test Step 3: Check satellite get new IP")
    public void step3() {
        ddp.gotoPage();
        ddp.openOB2();
        
        DevicesOrbiSatellitesPage page = new DevicesOrbiSatellitesPage();
        String satellite_ip = page.getFirstSatelliteIP();
        System.out.println(satellite_ip);
        ddp.gotoPage();
        
        boolean checkpoint = false;
        
        // check ip
        checkpoint = satellite_ip.contains("192.168.2.");
        assertTrue(checkpoint, "checkpoint 1 : remote satellite get a new IP");
    }
    
}
