package webportal.Topology.Exceptional.PRJCBUGEN_T22752;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.util.SystemOutLogger;
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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Topology") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T22752") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Reset funcational on more details tool tip") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T22752") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2:reboot check;")
    public void step2() {
        
        int upTimeBefore = new WirelessQuickViewPage().getApUptimetopology(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage(false).GoToTopology();
        new WirelessQuickViewPage(false).CheckStatus(WebportalParam.ap1serialNo);       
        new WirelessQuickViewPage(false).ResetTopology(); 
        
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

        int upTimeAfter = new WirelessQuickViewPage().getApUptimetopology(WebportalParam.ap1serialNo);
        assertTrue(upTimeAfter < upTimeBefore, "Reboot AP device failed.");
    }


}
