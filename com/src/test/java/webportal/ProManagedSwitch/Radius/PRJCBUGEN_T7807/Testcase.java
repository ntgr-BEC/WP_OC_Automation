package webportal.SwitchManaged.Radius.PRJCBUGEN_T7807;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "807";
    String ip1      = "11.22.33.44";
    String ip2      = "11.22.33.45";

    @Feature("Switch.Radius") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7807") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("001 - 802.1x Radius configuration under Network level won't affect managed GC devices") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7807") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3")
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
        rdp.gotoPage();
    }

    @Step("Test Step 2: Go to Location->Edit Location-> RADIUS")
    public void step2() {
        rdp.enableAuth(ip1, ip2);
    }

    @Step("Test Step 3: RADIUS will not affect configurations over Switch")
    public void step3() {
        handle.waitCmdReady(ip1, false);
        String tmpStr = SwitchCLIUtils.getRadiusInfo("g1");
        assertTrue(SwitchCLIUtils.RadiusClass.isServerConfiged, "check radius server option");
    }

}
