package webportal.ProManagedSwitch.POE.PRJCBUGEN_T5014;

import static org.testng.Assert.assertFalse;
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
 *
 * @author sumanta
 *
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "TsName";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5014") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("017-Create 1 new time schedule,recurrence is none") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5014") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wpsp.deleteAll();
        wpsp.deleteAllCli();
        WebportalParam.updateSwitchOneOption(false, null);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open Device")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wpsp.gotoPage();
    }

    @Step("Test Step 2: Create 1 new time schedule,recurrence is none, Use \"Save and Pick ports\" to deploy schedule")
    public void step2() {
        wpsp.addPoESchedule(sTestStr, 1, "None");
        assertTrue(wpsp.getPoEs().contains(sTestStr), "Save a name");
    }

    @Step("Test Step 3: Check the time schedule configuration on switch")
    public void step4() {
        handle.waitCmdReady(sTestStr, false);
        tmpStr = SwitchCLIUtils.getPoEInfo("g1");
        assertTrue(tmpStr.contains(sTestStr), "verify the timer schedule is set");
        tmpStr = SwitchCLIUtils.getPoEInfo("g2");
        assertFalse(tmpStr.contains(sTestStr), "verify the timer schedule is not set");
    }
}
