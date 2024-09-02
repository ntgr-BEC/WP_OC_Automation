package webportal.SwitchManaged.Event.PRJCBUGEN_T4938;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    String tmpStr1 = "Device resumed communication";
    String tmpStr2 = "Device lost communication";

    @Feature("Switch.Event") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4938") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("008-Insight web portal check notifications after reboot device") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4938") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open Event")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        evtp.gotoPage();
        if (!(handle.pageSource().contains(tmpStr1) || handle.pageSource().contains(tmpStr2))) {
            evtp.makeEvent(true);
        }
    }

    @Step("Test Step 2: Reboot DUT; Insight App manage DUT and check notifications again;")
    public void step2() {
        evtp.gotoPage();
        String pg = handle.pageSource();
        assertTrue((pg.contains(tmpStr1) || pg.contains(tmpStr2)), "there must be an event with connected/lost to cloud");
    }
}
