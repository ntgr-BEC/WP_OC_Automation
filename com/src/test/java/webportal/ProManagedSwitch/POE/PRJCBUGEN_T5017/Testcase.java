package webportal.SwitchManaged.POE.PRJCBUGEN_T5017;

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

    public String sCurrentValue;
    public String sExpectedtValue;
    public String sTestStr = "TSName5017";

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5017") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("020-Delete time schedule") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5017") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
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

    @Step("Test Step 2: Create 1 new time schedule")
    public void step2() {
        wpsp.addPoESchedule(sTestStr, 0, "None");
        assertTrue(wpsp.getPoEs().contains(sTestStr), "Save a name");
        wpsp.deleteAll();
        assertFalse(wpsp.getPoEs().contains(sTestStr), "Delete a name");
    }
}
