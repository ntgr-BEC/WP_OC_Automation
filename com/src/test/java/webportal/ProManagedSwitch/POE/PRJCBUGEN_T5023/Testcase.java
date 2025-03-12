package webportal.ProManagedSwitch.POE.PRJCBUGEN_T5023;

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
 * @author sumanta
 *
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "TSName5023";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5023") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("026-Disable time schedule for all ports") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5023") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wpsp.gotoPage();
    }

    @Step("Test Step 2: create a time schedule")
    public void step2() {
        wpsp.addPoESchedule(sTestStr, 0, "None");
        assertTrue(wpsp.getPoEs().contains(sTestStr), "Save a1 name");
    }

    @Step("Test Step 3: On device,config all ports used time schedule")
    public void step3() {
        wpsp.openTimeSchedule(sTestStr, 1);
        handle.clickPortPoE(1);
        handle.clickPortPoE(2);
        handle.clickButton(0);

        handle.waitCmdReady(sTestStr, false);
        tmpStr = handle.getCmdOutput(String.format("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1)), false);
        assertTrue(tmpStr.contains(sTestStr), "port g1 is binding to ts");
        tmpStr = handle.getCmdOutput(String.format("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 2)), false);
        assertTrue(tmpStr.contains(sTestStr), "port g2 is binding to ts");
    }

    @Step("Test Step 4: On app,disable time schedule for all ports")
    public void step4() {
        wpsp.openTimeSchedule(sTestStr, 1);
        handle.clickPortPoE(1);
        handle.clickPortPoE(2);
        handle.clickButton(0);

        handle.waitCmdReady(sTestStr, true);
        tmpStr = handle.getCmdOutput(String.format("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1)), false);
        assertFalse(tmpStr.contains(sTestStr), "port g1 is NOT binding to ts");
        tmpStr = handle.getCmdOutput(String.format("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 2)), false);
        assertFalse(tmpStr.contains(sTestStr), "port g2 is NOT binding to ts");
    }

}
