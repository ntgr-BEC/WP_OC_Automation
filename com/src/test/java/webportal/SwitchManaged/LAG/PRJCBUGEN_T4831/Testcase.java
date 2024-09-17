package webportal.SwitchManaged.LAG.PRJCBUGEN_T4831;

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
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String lagName    = "testlag4831";
    String lagName1   = "testlag48311";
    String vlanName   = "testvlan";
    String vlanId     = "830";
    String lagId      = "1";
    int    portIndex  = 4;
    int    portIndex1 = 5;

    @Feature("Switch.LAG") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4831") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("004-Config lag with membership already blong to another lag") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4831") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        portIndex = Integer.parseInt(WebportalParam.sw1LagPort1);
        portIndex1 = Integer.parseInt(WebportalParam.sw1LagPort2);
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wlp.gotoLagPage();
        wlp.deleteLag();
        wlp.deleteLagCli();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Add a LAG with one port")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wlp.gotoLagPage();
        wlp.addLag(lagName, false, false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Config lag 2,and assign port 1,2,5,6,7 to it.")
    public void step2() {
        wlp.addLag(lagName1, false, false);
        assertTrue(!wlp.getLagList().contains(lagName1.toLowerCase()), "testlag48311 cannot be added");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Delete lag1, and check lag 1 status on web gui")
    public void step3() {
        wlp.gotoLagPage();
        wlp.deleteLag();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Add port 1,2 to lag 2")
    public void step4() {
        wlp.gotoLagPage();
        wlp.addLag(lagName, true, false);

        assertTrue(SwitchCLIUtils.isLagPort(WebportalParam.getSwitchLag(false, false)), "addpmgmgort lag");
        assertTrue(SwitchCLIUtils.isLagPort(WebportalParam.getSwitchLag(true, false)), "addpmgmgort lag");
    }
}
