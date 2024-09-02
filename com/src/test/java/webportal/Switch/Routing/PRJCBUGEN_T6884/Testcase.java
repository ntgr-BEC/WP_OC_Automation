package webportal.Switch.Routing.PRJCBUGEN_T6884;

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

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "150";
    String mask     = "255.255.255.0";
    String ip       = "11.22.33.0";
    String gw       = "11.22.33.44";

    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6884") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("032-Create max static route") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6884") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p5") // "p3"
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
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddp.gotoPage();
        ddp.openSW1();
        dssrp.gotoPage();
        dssrp.deleteAllStaticRoute();
    }

    @Step("Test Step 2: Create max number of static route")
    public void step2() {
        for (int i = 1; i < 33; i++) {
            ip = String.format("123.1.%d.0", i);
            gw = String.format("123.1.%d.1", i);
            dssrp.addStaticRoute(false, ip, mask, gw);
        }
    }

    @Step("Test Step 3: Check configuration from dut and app")
    public void step3() {
        handle.waitCmdReady(gw, false);
        String tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(ip), "check ip");
        assertTrue(tmpStr.contains(gw), "check gw");
        assertTrue(tmpStr.contains(mask) || tmpStr.contains("/24"), "check mask");
    }

    @Step("Test Step 4: Add a new static route")
    public void step4() {
        dssrp.addStaticRoute(false, "123.12.12.0", mask, "123.12.12.1");
        String sRet = handle.getPageErrorMsg();
        assertTrue(sRet.contains("limit exceeded"));
    }

}
