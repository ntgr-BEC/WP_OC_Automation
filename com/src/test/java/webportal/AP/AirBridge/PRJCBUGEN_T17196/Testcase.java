package webportal.AP.AirBridge.PRJCBUGEN_T17196;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessAirBridgeGroupsPage;
import static com.codeborne.selenide.Selenide.$;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String aAP = null;
    
    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17196") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the Airbridge Group Option lands the Web Portal in to the List View") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17196") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        aAP = handle.getFakeDeviceNo("WAC502");
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login into IM WP page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 2: Airbridge Group Option lands the Web Portal in to the List View")
    public void step2() {
        ddp.enterDevice(WebportalParam.ap1serialNo);
        WirelessAirBridgeGroupsPage wabgp = new WirelessAirBridgeGroupsPage();
        wabgp.selectSubDevicePage(0);
        
        wabgp.btnAirBridgeGroupLink.click();
        MyCommonAPIs.waitReady();
        wabgp.selectSubGroupPage(2);
        
        assertTrue($(wabgp.sDeviceStatus).exists(), "check device view is in list by default");
    }
}
