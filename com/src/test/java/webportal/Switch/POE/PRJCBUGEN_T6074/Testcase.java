package webportal.Switch.POE.PRJCBUGEN_T6074;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
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

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6074") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("040-Check poe configurations after restart switch") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6074") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        WebportalParam.updateSwitchOneOption(false, null);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open Device")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        DevicesDashPage pageNew = new DevicesDashPage();
        pageNew.openPoEDevice();

        DevicesSwitchSummaryPage page = new DevicesSwitchSummaryPage();
        page.portChoice("1").click();
    }


    @Step("Test Step 2: On switch set all of adv-poe options while switch was offline")
    public void step2() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        handle.setExpand(page.btnPowerManagement, false);
        handle.setSelected(page.cbEnablePoe, true, true);
        page.setDetectionType(0);
        page.setPriority("High");

        page.clickSave();
        if (WebportalParam.isRltkSW1) {
            sExpectedtValue = "high";
        } else {
            sExpectedtValue = "poe priority High";
        }
    }

    @Step("Test Step 3: Put DUT back to internet")
    public void step3() {
        ddp.gotoPage();
        ddp.waitDevicesReboot(WebportalParam.sw1serialNo); 
    }

    @Step("Test Step 4: On switch check these options on switch")
    public void step4() {
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = handle.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the Priority to High");
    }

}
