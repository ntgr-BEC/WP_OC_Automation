package webportal.Orbi.SSID_WAN.SanityBusinessVPNFlow.PRJCBUGEN_T27422;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.SanityBusinessVPNFlow") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27422") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, Create the Business VPN group using the same location and same region.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27422") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
        try {
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
            handle.gotoLoction(WebportalParam.location2);
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ob1serialNo);
            util.MyCommonAPIs.sleep(120 * 1000);
            handle.gotoLoction();
            new DevicesDashPage().addNewDevice(WebportalParam.ob1serialNo, WebportalParam.ob1deveiceName, WebportalParam.ob1deveiceMac);
            util.MyCommonAPIs.sleep(180 * 1000);
        } catch (Throwable e) {
            // TODO: handle exception
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob1serialNo);
        util.MyCommonAPIs.sleep(120 * 1000);
        handle.gotoLoction(WebportalParam.location2);
        new DevicesDashPage().addNewDevice(WebportalParam.ob1serialNo, WebportalParam.ob1deveiceName, WebportalParam.ob1deveiceMac);
        util.MyCommonAPIs.sleep(180 * 1000);
    }
    
    @Step("Test Step 2: Go to Routers page / User add a VPN group / Add a remote router after the group is created")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400");
        page.setPage2AddCentralRouter(WebportalParam.location2, WebportalParam.ob2deveiceName);
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob3deveiceName);
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T27422", 1, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl("DESKTOP-7ETR4H8", "08:be:ac:12:ce:c9", 0, WebportalParam.ob2deveiceName);
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady();
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); 
        util.MyCommonAPIs.sleepsyncorbi(); 
        page.gotoPage();
        checkpoint = page.GroupExist("TestGroup", "PRJCBUGEN-T27422", WebportalParam.ob2deveiceName, "Healthy");
        if(!checkpoint) {
            util.MyCommonAPIs.sleepsyncorbi(); 
            page.gotoPage();
            checkpoint = page.GroupExist("TestGroup", "PRJCBUGEN-T27422", WebportalParam.ob2deveiceName, "Healthy");
        }
        assertTrue(checkpoint, "checkpoint 1 : the group with routers in same location is added");
    }
    
}
