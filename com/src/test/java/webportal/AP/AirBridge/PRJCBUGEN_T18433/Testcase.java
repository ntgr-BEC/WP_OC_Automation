package webportal.AP.AirBridge.PRJCBUGEN_T18433;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String aAP = null;
    
    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18433") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the Remove button functionality on the Airbridge dashboard.") // It's a testcase title from Jira Test Case.
    @TmsLinks({ @TmsLink("PRJCBUGEN-T18433"), @TmsLink("PRJCBUGEN-T19399") }) // It's a testcase id/link from Jira Test Case.

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
    }

    @Step("Test Step 2: 4. On the device dashboard, click on the cross icon")
    public void step2() {
        ddp.gotoPage();
        String[] sAPNos = { aAP };
        String[] sAPNames = { null };
        ddp.addDeviceAirBridge(sAPNos, sAPNames);
        assertTrue(ddp.getDeviceName(aAP).equals(aAP), "device is able to add");
        
        ddp.deleteDeviceYes(aAP);
        assertTrue(ddp.getDeviceName(aAP).equals(""), "device is able to remove");
    }
}
