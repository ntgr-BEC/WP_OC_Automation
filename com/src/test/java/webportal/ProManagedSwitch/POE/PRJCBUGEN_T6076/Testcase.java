package webportal.SwitchManaged.POE.PRJCBUGEN_T6076;

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
    String tclname = getClass().getName();
    String tmpStr;

    public String sCurrentValue;
    public String sExpectedtValue;
    boolean       bPoeStandMore = false;

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6076") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("043-Config every POE values for more than one ports on a switch.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6076") // It's a testcase id/link from Jira Test Case.

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

        DevicesDashPageMNG pageNew = new DevicesDashPageMNG();
        pageNew.openPoEDevice();

        DevicesSwitchSummaryPage page = new DevicesSwitchSummaryPage();
        page.portChoice("1").click();
    }

    @Step("Test Step 2: Set switch PoE class to 1 on 2 ports")
    public void step2() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page.goBatchPortConf();
        handle.clickPort(1, false);
        handle.clickPort(2, false);
        handle.setExpand(page.btnPowerManagement, false);
        handle.setSelected(page.cbEnablePoe, true, true);
        page.setClass("Class1");
        page.clickSave();
        handle.clickYesNo(true);
        handle.sleepi(10);
    }

    @Step("Test Step 3: On switch check switch PoE class to 1")
    public void step3() {
        sExpectedtValue = "4000";
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the PoE class to 1 on g1");
        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 2), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the PoE class to 1 on g2");
    }
}
