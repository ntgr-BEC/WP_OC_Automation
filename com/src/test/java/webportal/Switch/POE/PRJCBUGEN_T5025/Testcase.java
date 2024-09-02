package webportal.Switch.POE.PRJCBUGEN_T5025;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.CommonDataType;
import webportal.param.CommonDataType.DateData;
import webportal.param.CommonDataType.TimeData;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "TSName5025";
    public String sCurrentValue;
    public String sExpectedtValue;
    TimeData      ddTime   = new CommonDataType().new TimeData();
    DateData      ddDate   = new CommonDataType().new DateData();

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5025") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("028-Config start time and end time") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5025") // It's a testcase id/link from Jira Test Case.
    @Issue("PRJCBUGEN-26651")

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        if (wpsp != null) {
            wpsp.deleteAll();
        }
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

    @Step("Test Step 2: Create a time schedule,start time same as end time")
    public void step2() {
        wpsp.btnAdd.click();
        wpsp.txtScheduleName.setValue(sTestStr);
        wpsp.txtStartDate.click();
        ddDate.setDate(true);
        wpsp.txtEndDate.click();
        ddDate.setDate(true);
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().contains("at least 2 minutes"), "PoE Schedule start time and end time must be at least 2 minutes apart. If the schedule extends across midnight, the end date must be at least 1 day after the start date.");
//        assertTrue(handle.getPageErrorMsg().contains("greater than"), "End time must be greater than Start time");
    }

    @Step("Test Step 3: start time greater than end time")
    public void step3() {
        wpsp.txtStartTime.click();
        ddTime.setTime();
        wpsp.txtEndTime.click();
        ddTime.setTime();

        wpsp.txtStartDate.click();
        ddDate.setDate(false);
        wpsp.txtEndDate.click();
        ddDate.setDate(false);
        wpsp.lbRecurrenceType.selectOption(WebportalParam.getLocText("Daily"));
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().contains("at least 2 minutes"), "PoE Schedule start time and end time must be at least 2 minutes apart. If the schedule extends across midnight, the end date must be at least 1 day after the start date.");
//        assertTrue(handle.getPageErrorMsg().contains("greater than"), "End time must be greater than Start time");
    }

}
