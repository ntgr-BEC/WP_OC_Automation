package webportal.SrilankaSupportEUSKU.PRJCBUGEN_T37929;

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
    @Story("PRJCBUGEN_T37929") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the channel for 5 Ghz on WAX610 in Srilanka") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T37929") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        //new WirelessQuickViewPage().deleteSsidYes("apwp37929");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        //new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
    }

    @Step("Test Step 2: Open AP Setting and in radio and channels get all channels for 5GHz;")
    public void step2() {
        
        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        assertTrue(new DevicesApSummaryPage(false).etractValuesFromChannelsfor5GHZ(),"All channels are not shown at output");

    }
}
