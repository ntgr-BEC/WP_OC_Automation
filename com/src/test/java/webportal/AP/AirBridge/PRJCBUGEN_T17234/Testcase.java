package webportal.AP.AirBridge.PRJCBUGEN_T17234;

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

import static com.codeborne.selenide.Selenide.$$;
import com.codeborne.selenide.SelenideElement;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String aAP       = null;
    String sExpected = null;

    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17234") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the Airbridge Mode has an Info / help link to explain the mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17234") // It's a testcase id/link from Jira Test Case.
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
    
    @Step("Test Step 2: Verify if the Airbridge Mode has an Info / help link to explain the mode")
    public void step2() {
        new WirelessQuickViewPage();
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.gotoPage();
        wabg.openGroup(wabg.sDefaultAirBridgeGroup);
        MyCommonAPIs.waitReady();
        wabg.selectSubGroupPage(1);
        MyCommonAPIs.waitReady();
        boolean isFound = false;
        for (SelenideElement se : $$(".justify-content-between .AccountTooltip")) {
            if (MyCommonAPIs.getText(se).contains(sExpected)) {
                isFound = true;
                break;
            }
        }
        assertTrue(isFound, "check aribridge mode has tooltip");
    }
}
