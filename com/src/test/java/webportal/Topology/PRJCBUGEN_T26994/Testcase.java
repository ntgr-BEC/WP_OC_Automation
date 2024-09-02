package webportal.Topology.PRJCBUGEN_T26994;

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
    @Story("PRJCBUGEN_T26994") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Topology --> Switch IP address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26994") // It's a testcase id/link from Jira Test Case.

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
//        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: check the device status of the device;")
    public void step2() {
       new WirelessQuickViewPage(false).GoToTopology();
       new WirelessQuickViewPage(false).CheckStatus(WebportalParam.sw1serialNo);
       MyCommonAPIs.waitElement(new WirelessQuickViewPage(false).devicestatus);
       System.out.println("visible");
       String status = new WirelessQuickViewPage(false).getText(new WirelessQuickViewPage(false).devicestatus);
       System.out.println(status);
       assertTrue(status.contains("Connected"), "topology option does not exits");
    
       
    }



}
