package webportal.SrilankaSupportEUSKU.PRJCBUGEN_T37930;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("SrilankaSupportEUSKU") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T37930") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify the channel for 2.4 Ghz on WAX620 in Srilanka") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T37930") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
       // new WirelessQuickViewPage().deleteSsidYes("apwp28349");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
       // new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
    }

    @Step("Test Step 2: Open 620 AP Setting and in radio and channels get all channels for 2.4GHz;")
    public void step2() {
        
        new DevicesDashPage().enterDevice(WebportalParam.ap2serialNo);
        assertTrue(new DevicesApSummaryPage(false).etractValuesFromChannelsfor24GHZ(),"All channels are not shown at output");

    }
}
