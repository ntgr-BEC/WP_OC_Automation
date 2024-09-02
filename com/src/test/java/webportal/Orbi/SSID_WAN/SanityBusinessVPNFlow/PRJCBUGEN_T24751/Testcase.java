package webportal.Orbi.SSID_WAN.SanityBusinessVPNFlow.PRJCBUGEN_T24751;

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
import util.Javasocket;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.SanityBusinessVPNFlow") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24751") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to Verify the Create same name of Business VPN group.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24751") // It's a testcase id/link from Jira Test Case.
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
        } catch (Throwable e) {
            System.out.println("Failed to delete vpn group");
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Go to Routers page / User can add a VPN group")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400", true , "arlo1.com");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T24751", 1, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl("DESKTOP-7ATR4H8", "08:be:ac:12:ce:c9", 0, WebportalParam.ob1deveiceName);
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 5000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); //wait 5 mins to let business vpn finish setting up
        page.gotoPage();
        page.clickAddVPNGroup();
        checkpoint = page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400", true , "arlo1.com");
        assertFalse(checkpoint, "checkpoint 1 : cannot add create same name of Business VPN group");
    }
    
}
