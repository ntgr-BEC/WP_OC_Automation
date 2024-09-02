package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.VPNGroup.PRJCBUGEN_T25266;

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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author jim.xie
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.VPNGroup") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25266") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, Create multiple Business VPN groups.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25266") // It's a testcase id/link from Jira Test Case.
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
            page.gotoPage();
            page.DelVPNGroup("TestGroup2");
        } catch (Throwable e) {
            System.out.println("Failed to delete vpn groups");
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Go to Routers page / User add two VPN groups and they are shown in the table")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        // create 1st VPN group
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription1", "1400");
        page.setPage2AddCentralRouter(); // OB1
        page.setPage2AddRemoteRouter(); // OB2
        page.clickNext();
        page.setPage3WirelessSettings(true, "SSID-WAN-Auto-Test1", 1, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl(WebportalParam.client2name, WebportalParam.client2wifimac, 0, WebportalParam.ob1deveiceName);
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 5000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.waitReady();
        util.MyCommonAPIs.sleepi(3);
        // create 2nd VPN group
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup2", "TestDescription2", "1400");
        page.setPage2AddCentralRouter(WebportalParam.location1, WebportalParam.ob4deveiceName); // OB4
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob3deveiceName); // OB3
        page.clickNext();
        page.setPage3WirelessSettings(true, "SSID-WAN-Auto-Test2", 1, "87654321");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl(WebportalParam.client2name, WebportalParam.client2wifimac, 0, WebportalParam.ob3deveiceName);
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 5000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); // wait 5 mins to let business vpn finish setting up
        page.gotoPage();
        // check 2 VPN groups are listed
        checkpoint = page.GroupExist("TestGroup", "SSID-WAN-Auto-Test1", WebportalParam.ob1deveiceName);
        assertTrue(checkpoint, "checkpoint 1 : group 1 exists");
        checkpoint = page.GroupExist("TestGroup2", "SSID-WAN-Auto-Test2", WebportalParam.ob4deveiceName);
        assertTrue(checkpoint, "checkpoint 2 : group 2 exists");
        util.MyCommonAPIs.sleepi(10);
    }
    
}
