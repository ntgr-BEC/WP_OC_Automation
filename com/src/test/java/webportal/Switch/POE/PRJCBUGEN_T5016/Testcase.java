package webportal.Switch.POE.PRJCBUGEN_T5016;

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
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String                       tclname = getClass().getName();
    String                       tmpStr;

    public String sTestStr = "TSName5016";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5016") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("019-Create 1 new time schedule,recurrence is Weekly") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5016") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wpsp.deleteAll();
        WebportalParam.updateSwitchOneOption(false, null);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open Device")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wpsp.gotoPage();
    }

    @Step("Test Step 2: Create 1 new time schedule,recurrence is Weekly")
    public void step2() {
        wpsp.addPoESchedule(sTestStr, 0, "Weekly");
        assertTrue(wpsp.getPoEs().contains(sTestStr), "Save a name");
        assertTrue(wpsp.getWeeklyDayStatus(1), "Sunday is active");
        assertFalse(wpsp.getWeeklyDayStatus(2), "Monday is inactive");
        assertTrue(wpsp.getWeeklyDayStatus(7), "Statuday is active");
        wpsp.deleteAll();
    }

    @Step("Test Step 3: Create 1 new time schedule,recurrence is Weekly")
    public void step3() {
        sTestStr += "full";
        wpsp.fullweek = true;
        wpsp.addPoESchedule(sTestStr, 0, "Weekly");
        assertTrue(wpsp.getPoEs().contains(sTestStr), "Save a full week name");
        assertTrue(wpsp.getWeeklyDayStatus(1), "Sunday is active");
        assertTrue(wpsp.getWeeklyDayStatus(2), "Monday is active");
        assertTrue(wpsp.getWeeklyDayStatus(7), "Statuday is active");
    }

}
