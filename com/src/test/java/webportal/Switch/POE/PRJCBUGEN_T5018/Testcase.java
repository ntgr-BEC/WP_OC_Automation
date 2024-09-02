package webportal.Switch.POE.PRJCBUGEN_T5018;

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
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "TSName5018";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5018") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("021-Edit time schedule") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5018") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        WebportalParam.updateSwitchOneOption(false, null);
        wpsp.deleteAll();
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

    @Step("Test Step 2: Add a time schedule,the recurrence is none,and binding the time schedule one port")
    public void step2() {
        wpsp.addPoESchedule(sTestStr, 1, "None");
        assertTrue(wpsp.getPoEs().contains(sTestStr), "Save a name");
    }

    @Step("Test Step 3: Edit all options for created time schedule,then deploy")
    public void step3() {
        wpsp.openTimeSchedule(sTestStr, 0);
        wpsp.lbRecurrenceType.selectOption(WebportalParam.getLocText("Daily"));
        wpsp.txtStartDate.click();
        wpsp.ddDate.setDate(1);
        wpsp.txtEndDate.click();
        wpsp.ddDate.setDate(1);
        handle.clickButton(0);

        assertTrue(handle.pageSource().contains(","), "Day set to each 1 of month");
        handle.waitCmdReady(sTestStr, false);
        tmpStr = handle.getCmdOutput(String.format("show time-range %s", sTestStr), false);

        if (WebportalParam.isRltkSW1) {
            assertTrue(tmpStr.contains("daily"), "all day is active");
            assertTrue(tmpStr.contains("infinite"), "must be every day");
        } else {
            assertTrue(tmpStr.contains("SUN MON TUE WED THU FRI SAT"), "all day is active");
            assertTrue(tmpStr.contains("Frequency: 1"), "must be every day");
        }
    }

}
