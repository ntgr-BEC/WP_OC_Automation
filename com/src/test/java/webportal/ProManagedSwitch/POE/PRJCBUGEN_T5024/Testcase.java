package webportal.SwitchManaged.POE.PRJCBUGEN_T5024;

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

    public String sTestStr = "TSName5024";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5024") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("027-Create max value(100) of time schedule") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5024") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p5") // "p3"
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

    @Step("Test Step 2: Create 100 time schedule")
    public void step2() {
        if (wpsp.getPoEs().size() != 0) {
            wpsp.deleteAll();
        }

        for (int i = 0; i < 100; i++) {
            String sTestStrTmp = String.format("%s%d", sTestStr, i);
            System.out.println(sTestStrTmp);
            wpsp.addPoESchedule(sTestStrTmp, 0, "None");
        }

        assertTrue(wpsp.getPoEs().size() == 100, "we created 100 records");
    }

    @Step("Test Step 3: Create 101th time schedule")
    public void step3() {
        wpsp.addPoESchedule(sTestStr, 0, "None");
        assertTrue(handle.getPageErrorMsg().contains("max limit"));
    }

}
