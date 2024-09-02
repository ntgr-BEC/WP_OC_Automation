package webportal.Orbi.SSID_WAN.SanityBusinessVPNFlow.PRJCBUGEN_T24762;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.SanityBusinessVPNFlow") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24762") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to Verify on double click Business VPN group user gets redirected to the Routers --> Setting --> Office Router page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24762") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
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
    
    @Step("Test Step 2: Go to Routers page / User add a VPN group")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true, "SSID-WAN-Auto-Test", 1, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl(WebportalParam.client1name, WebportalParam.client2wifimac, 0, WebportalParam.ob1deveiceName);
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 3000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); 
        util.MyCommonAPIs.sleepsyncorbi(); 
        page.gotoPage();
        checkpoint = page.GroupExist("TestGroup", "SSID-WAN-Auto-Test", WebportalParam.ob1deveiceName, "Healthy");
        if(!checkpoint) {
            util.MyCommonAPIs.sleepsyncorbi(); 
            page.gotoPage();
        }
    }
    
    @Step("Test Step 3: Go to office router page and remote routers page / Check if routers are healthy")
    public void step3() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoGroupPage("TestGroup");
        
        String currentUrl = util.MyCommonAPIs.getCurrentUrl();
        checkpoint = currentUrl.contains(URLParam.hrefOfficeRouter);
        assertTrue(checkpoint,"checkpoint 1 : redirects office router page");
        
        page.gotoCentralRouterPage();
        checkpoint = page.RouterHealth(WebportalParam.ob1deveiceName, true, "Healthy");
        assertTrue(checkpoint, "checkpoint 2 : central router is healthy");
        page.gotoRemoteRoutersPage();
        checkpoint = page.RouterHealth(WebportalParam.ob2deveiceName, false, "Healthy");
        assertTrue(checkpoint, "checkpoint 3 : remote router is healthy");
    }
    
}
