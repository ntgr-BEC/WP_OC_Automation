package webportal.Switch.POE.PRJCBUGEN_T5021;

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
import util.MyCommonAPIs;
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

    public String sTestStr  = "TSName5021";
    public String sTestStr1 = "TSName50211";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Issue("PRJCBUGEN-10637")
    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5021") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("024-config different time schedule to same ports") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5021") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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

    @Step("Test Step 2: create a time schedule")
    public void step2() {
        wpsp.addPoESchedule(sTestStr, 0, "None");
        assertTrue(wpsp.getPoEs().contains(sTestStr), "Save a1 name");
        wpsp.addPoESchedule(sTestStr1, 0, "None");
        assertTrue(wpsp.getPoEs().contains(sTestStr1), "Save a2 name");
    }

    @Step("Test Step 3: config time1 to port3")
    public void step3() {
        wpsp.openTimeSchedule(sTestStr, 1);
        handle.clickPortPoE(1);
        handle.clickButton(0);

        wpsp.openTimeSchedule(sTestStr1, 1);
        handle.clickPortPoE(1);
        MyCommonAPIs.sleepi(4);
        assertTrue(wpsp.warningYes.isDisplayed(), "Confirm to replace port");
        wpsp.warningYes.click();
        handle.clickButton(0);

        handle.waitCmdReady(sTestStr1, false);
        tmpStr = handle.getCmdOutput(String.format("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1)), false);
        assertTrue(tmpStr.contains(sTestStr1), "port g1 is binding to 2nd ts");
    }

}
