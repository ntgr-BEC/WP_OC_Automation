package webportal.AP.AirBridge.PRJCBUGEN_T19398;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessAirBridgeGroupsPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String aAP = null;
    
    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T19398") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify \"add another air bridge device\" button functionality while onboarding") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19398") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        aAP = handle.getFakeDeviceNo("WAC502");
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        ddp.gotoPage();
        ddp.deleteDeviceYes(aAP);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login into IM WP page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 2: 8. click on 'Add another Airbridge Device' button to onboarded device")
    public void step2() {
        String[] sAPNos = { aAP };
        String[] sAPNames = { null };
        handle.setCheckPointStep(1);
        ddp.addDeviceAirBridge(sAPNos, sAPNames);
        assertTrue(handle.getCheckPointResult(), "check the airbridge grup must be selected");
    }
}
