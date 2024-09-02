package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.RemoteRouter.PRJCBUGEN_T27022;

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
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.RemoteRouter") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27022") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the user are able to add a router in Access point mode as remote router") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27022") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("tearDown : delete vpn group / restore to router mode if needed");
        
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.DelVPNGroup("TestGroup");
        
        ddp.gotoPage();
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.ob2serialNo, 3);
        DevicesOrbiDeviceModePage page1 = new DevicesOrbiDeviceModePage();
        page1.setDeviceMode(false);
        MyCommonAPIs.sleepsync();    
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 2 where the remote router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction(WebportalParam.location2);
    }

    @Step("Test Step 2: Set remote router as AP mode")
    public void step2() {
        boolean checkpoint;
        ddp.gotoPage();
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.ob2serialNo, 3);
        DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
        page.setDeviceMode(true);
        MyCommonAPIs.sleepsyncorbi(); 
        page.gotoPage();
        checkpoint = page.isRouterMode();
        assertFalse(checkpoint, "checkpoint 1 : device is changed to AP mode");
        MyCommonAPIs.sleep(3000);
    }
    
    @Step("Test Step 3: Go to Routers page / User can enable Aggressive keepalive")
    public void step3() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        
        page.clickNext();
        page.setPage3WirelessSettings(true,"SSID-WAN-Auto-Test",1,"12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady();
        util.MyCommonAPIs.sleep(5000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "SSID-WAN-Auto-Test", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "SSID-WAN-Auto-Test", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        page.gotoPage();
        checkpoint = page.GroupExist("TestGroup", "SSID-WAN-Auto-Test", WebportalParam.ob1deveiceName, "Healthy");
        assertTrue(checkpoint, "checkpoint 1 : group is healthy");
    }
    
}
