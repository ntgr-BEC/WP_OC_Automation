package webportal.AP.AirBridge.PRJCBUGEN_T17231;

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
import webportal.weboperation.WirelessQuickViewPage;

import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String aAP       = null;
    String sExpected = null;

    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17231") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the Content of the Airbridge Group List View has the Lat, Long, Location, IP/ MAC, Tx/Rx & Uptime Info") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17231") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        aAP = handle.getFakeDeviceNo("WAC502");
        sExpected = WebportalParam.getLocText("im5.6Keys", "airBridgeModeInfoText");
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
    
    @Step("Test Step 2: Verify all fields of Airbridge Group List View ")
    public void step2() {
        ddp.gotoPage();
        ddp.enterDevice(WebportalParam.ap1serialNo);
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.gotoPage();
        wabg.selectSubGroupPage(2);
        MyCommonAPIs.waitReady();
        assertTrue($(wabg.sDeviceName).exists(), "check aribridge device name");
        assertTrue($(wabg.sDeviceLocation).exists(), "check aribridge device location");
        assertTrue($(wabg.sDeviceUpTime).exists(), "check aribridge device uptime");
    }
}
