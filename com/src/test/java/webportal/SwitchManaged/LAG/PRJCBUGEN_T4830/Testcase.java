package webportal.SwitchManaged.LAG.PRJCBUGEN_T4830;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String lagName    = "testlag4830";
    String vlanName   = "testvlan";
    String vlanId     = "830";
    String lagId      = "1";
    int    portIndex  = 4;
    int    portIndex1 = 5;

    @Feature("Switch.LAG") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4830") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("006-Create lag when DUT is offline") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4830") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        portIndex = Integer.parseInt(WebportalParam.sw1LagPort1);
        portIndex1 = Integer.parseInt(WebportalParam.sw1LagPort2);
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        SwitchCLIUtils.CloudModeSet(true);
        wlp.gotoLagPage();
        wlp.deleteLag();
        MyCommonAPIs.sleepi(4);
        wlp.deleteLagCli();
    }

    @Step("Test Step 1: Put DUT out of internet")
    public void step1() {
        SwitchCLIUtils.CloudModeSet(false);
        handle.sleepi(5);
        SwitchCLIUtils.CloudModeSet(true);
        handle.sleepi(4 * 60);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Add a LAG")
    public void step2() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wlp.gotoLagPage();
        wlp.addLag(lagName, false, false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Check lag configuraton on DUT")
    public void step3() {
        handle.waitCmdReady(lagName, false);
        String sRet;

        sRet = MyCommonAPIs.getCmdOutput(
                "show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, WebportalParam.sw1LagPort1), false);
        assertTrue(sRet.contains(" lag "), "check lag 1 is created on channel for 4");
    }
}