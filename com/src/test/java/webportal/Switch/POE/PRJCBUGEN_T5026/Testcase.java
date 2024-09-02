package webportal.Switch.POE.PRJCBUGEN_T5026;

import static org.testng.Assert.assertFalse;
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
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String                       tclname = getClass().getName();
    String                       tmpStr;

    public String sTestStr = "TSName5026";
    public String sCurrentValue;
    public String sExpectedtValue;
    TimeData      ddTime   = new CommonDataType().new TimeData();
    DateData      ddDate   = new CommonDataType().new DateData();

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5026") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("029-Config start day and end day") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5026") // It's a testcase id/link from Jira Test Case.
    @Issue("PRJCBUGEN-11191")

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

    @Step("Test Step 2: Create time schedule,start day same as end day")
    public void step2() {
        wpsp.btnAdd.click();
        wpsp.txtScheduleName.setValue(sTestStr);
        wpsp.txtStartTime.click();
        ddTime.setTime();
        wpsp.txtEndTime.click();
        ddTime.hour++;
        ddTime.setTime();

        wpsp.txtStartDate.click();
        ddDate.setDate(false);
        wpsp.txtEndDate.click();
        ddDate.setDate(false);
        handle.clickButton(0);
        assertFalse(handle.getPageErrorMsg().contains("greater than"), "The end date must be greater than start date.");
    }

    @Step("Test Step 3: Create time schedule,start day greater than end day")
    public void step3() {
        wpsp.gotoPage();
        wpsp.btnAdd.click();
        wpsp.txtScheduleName.setValue(sTestStr);
        handle.setSelected(wpsp.cbAllDay, true, true);
        wpsp.txtStartDate.click();
        ddDate.setDate(false);
        wpsp.txtStartDate.click();
        ddDate.setDate(1);
        wpsp.txtEndDate.click();
        ddDate.setDate(false);
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().contains("greater than"), "End time must be greater than Start time");
    }

}
