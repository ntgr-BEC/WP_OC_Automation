package webportal.Orbi.SSID_WAN.Dashboard.PRJCBUGEN_T24681;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author jim.xie
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.Dashboard") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24681") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether data displayed under \"Business VPN\" are updated") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24681") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
        util.MyCommonAPIs.sleepsyncorbi();
        try {
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            // restore : click list view and delete the vpn group
            if (page.cirbtnListView.exists()) {
                page.cirbtnListView.click();
                util.MyCommonAPIs.waitReady();
            }
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

    @Step("Test Step 2: Go to Routers page / Create VPN group / Change to graph view / Click all remote routers are displayed")
    public void step2() {
        
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob3deveiceName, 1, true);
        page.clickNext();
        page.setPage3WirelessSettings(true,"SSID-WAN-Auto-Test",1,"12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 3000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.waitReady();
        page.cirbtnGraphView.click();
        util.MyCommonAPIs.waitReady();
        util.MyCommonAPIs.sleepi(10);
        
        checkpoint = page.graphRemote1Img.exists();
        assertTrue(checkpoint,"checkpoint 1 : remote router 1 is displayed");
        checkpoint = page.graphRemote2Img.exists();
        assertTrue(checkpoint,"checkpoint 2 : remote router 2 is displayed");
        
    }
}
