package webportal.SwitchManaged.POE.PRJCBUGEN_T5019;

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
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String                       tclname = getClass().getName();
    String                       tmpStr;

    public String sTestStr = "TSName5019";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5019") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("022-config time schedule to 1 port") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5019") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        WebportalParam.updateSwitchOneOption(false, null);
        if (wpsp != null) {
            wpsp.deleteAll();
        }
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
        assertTrue(wpsp.getPoEs().contains(sTestStr), "Save a name");
    }

    @Step("Test Step 3: On Group port config page,select 1 port, config time schedule for it")
    public void step3() {
        DevicesDashPageMNG pageNew = new DevicesDashPageMNG();
        pageNew.openPoEDevice();

        DevicesSwitchSummaryPage page = new DevicesSwitchSummaryPage();
        page.portChoice("1").click();

        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        handle.setExpand(page1.btnPowerManagement, false);
        page1.lbSchName.selectOption(sTestStr);
        handle.clickButton(0);

        tmpStr = handle.getCmdOutput(String.format("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1)), false);
        assertTrue(tmpStr.contains(sTestStr), "port g1 is binding to ts");
    }

}
