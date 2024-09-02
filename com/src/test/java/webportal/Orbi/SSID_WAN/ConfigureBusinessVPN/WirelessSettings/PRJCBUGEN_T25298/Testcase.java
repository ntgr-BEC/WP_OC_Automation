package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.WirelessSettings.PRJCBUGEN_T25298;

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
    @Feature("SSID_WAN.ConfigureBusinessVPN.WirelessSettings") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25298") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify we are able to enable the VPN SSID or not.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25298") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : delete VPN group");
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
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T25298", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady();
        util.MyCommonAPIs.sleep(5000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
       
        util.MyCommonAPIs.sleepsyncorbi(); 
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "PRJCBUGEN-T25298", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi(); 
        }
        page.gotoPage();
        // check 2 VPN groups are listed
        checkpoint = page.GroupExist("TestGroup", "PRJCBUGEN-T25298", WebportalParam.ob1deveiceName, "Healthy");
        assertTrue(checkpoint, "checkpoint 1 : SSID PRJCBUGEN-T25298 exists");
    }
    
}
