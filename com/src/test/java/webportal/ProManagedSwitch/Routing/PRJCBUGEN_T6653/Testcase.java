package webportal.ProManagedSwitch.Routing.PRJCBUGEN_T6653;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "150";
    String mask     = "255.255.255.0";
    String ipgw     = "11.22.33.44";

    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6653") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("030-Add a Default Route") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6653") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        ddpmg.gotoPage();
        ddpmg.openSW1();
        dssrp.gotoPage();
    }

    @Step("Test Step 2: Add a default route")
    public void step2() {
        dssrp.addStaticRoute(true, "", "", ipgw);
        assertTrue(dssrp.getStaticRouteGw().contains(ipgw), "check gateway");
    }

    @Step("Test Step 3: Check configuration from dut and app")
    public void step3() {
        handle.waitCmdReady(ipgw, false);
        String tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(ipgw));
    }

}
