package webportal.SwitchManaged.POE.PRJCBUGEN_T5013;

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

    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5013") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("016-Test time schedule name") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5013") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
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
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wpsp.deleteAll();
        wpsp.deleteAllCli();

        wpsp.gotoPage();
        wpsp.deleteAll();
    }

    @Step("Test Step 2: Input schedule name do not start with a letter")
    public void step2() {
        wpsp.btnAdd.click();
        wpsp.txtScheduleName.setValue("1234");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().contains("must start with letter"),
                "Schedule name must contain only alphabets, numbers, underscores, hyphens and dot. Name must start with letter");
        wpsp.btnCancel.click();
    }

    @Step("Test Step 3: Input schedule name include alphabetic, numeric, dash, dot or underscore characters")
    public void step3() {
        tmpStr = "Test1-2.3_4";
        wpsp.addPoESchedule(tmpStr, 0, "None");
        assertTrue(wpsp.getPoEs().contains(tmpStr), "Save a name");
    }

    @Step("Test Step 4: Input 31 characters for schedule name")
    public void step4() {
        tmpStr = "T123456789012345678901234567890";
        wpsp.addPoESchedule(tmpStr, 0, "None");
        assertTrue(wpsp.getPoEs().contains(tmpStr), "Save a 31 name");
    }

    @Step("Test Step 5: schedule name set to \"None\" or \"none\"")
    public void step5() {
        tmpStr = "";
        wpsp.addPoESchedule(tmpStr, 0, "None");
        assertTrue(handle.getPageErrorMsg().contains("enter schedule name"), "Please enter schedule name.");
        wpsp.btnCancel.click();
    }
}
