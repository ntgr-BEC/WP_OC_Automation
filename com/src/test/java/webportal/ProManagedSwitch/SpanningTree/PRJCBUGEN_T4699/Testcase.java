package webportal.SwitchManaged.SpanningTree.PRJCBUGEN_T4699;

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
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    String tmpStr;

    public String sCurrentValue;
    public String sExpectedtValue;
    int           iMode;

    @Feature("Switch.SpanningTree") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4699") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("001-Verify Spanning-Tree configuration via WebPortal") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4699") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wstp.gotoPage();
        wstp.enableRSTP();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open Device")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wstp.gotoPage();
    }

    @Step("Test Step 2: Web Portal go to 'Wired'-->'Spanning Tree', check default config")
    public void step2() {
        wstp.getSTPMode();
    }

    @Step("Test Step 3: Change Spanning-Tree mode to RSTP via Insight")
    public void step3() {
        iMode = 2;
        wstp.setSTPMode(iMode, false, false);
    }

    @Step("Test Step 4: checked by Web Portal and Web GUI;")
    public void step4() {
        assertTrue(wstp.getSTPMode() == iMode, "check rstp mode");

        handle.waitCmdReady("mode stp", false);
        System.out.println("Mode number "+SwitchCLIUtils.getSTPMode());
        assertTrue(SwitchCLIUtils.getSTPMode() == 2, "verify rstp is enabled on switch");
    }

    @Step("Test Step 5: Change Spanning-Tree mode to STP via Web Portal")
    public void step5() {
        iMode = 1;
        wstp.setSTPMode(iMode, false, false);
    }

    @Step("Test Step 6: checked by Web Portal and Web GUI;")
    public void step6() {
        assertTrue(wstp.getSTPMode() == iMode, "check stp mode");

        handle.waitCmdReady("mode stp", true);
        assertTrue(SwitchCLIUtils.getSTPMode() == 1, "verify stp is enabled on switch");
    }

    @Step("Test Step 7: Change Spanning-Tree mode to Disable via Insight;")
    public void step7() {
        iMode = 0;
        wstp.setSTPMode(iMode, false, false);
        MyCommonAPIs.sleepsync();
    }

    @Step("Test Step 8: checked by Web Portal and Web GUI;")
    public void step8() {
        assertTrue(wstp.getSTPMode() == iMode, "check disable mode");
        MyCommonAPIs.sleepsync();
        assertTrue(SwitchCLIUtils.getSTPMode() == 0, "verify stp is disabled on switch");
    }

}
