package webportal.ProManagedSwitch.POE.PRJCBUGEN_T6067;

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
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author sumanta
 *
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    String tmpStr;

    public String sCurrentValue;
    public String sExpectedtValue = "";
    boolean       bPoeStandMore   = false;

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6067") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("034-Set and check Power Mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6067") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        DevicesDashPageMNG pageNew = new DevicesDashPageMNG();
        pageNew.openPoEDevice();

        DevicesSwitchSummaryPage page = new DevicesSwitchSummaryPage();
        page.portChoice("1").click();
    }

    @Step("Test Step 2: Set switch power mode to 802.3af")
    public void step3() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        handle.setExpand(page.btnPowerManagement, false);
        handle.setSelected(page.cbEnablePoe, true, true);
        if (page.setPoEStand("Pre-802.3at")) {
            bPoeStandMore = true;
        }

        if (bPoeStandMore) {
            page.setPoEStand("802.3af");
            page.clickSave();
            if (WebportalParam.isRltkSW1) {
                sExpectedtValue = "802.3af";
            } else {
                sExpectedtValue = "no poe high-power";
            }
        }
    }

    @Step("Test Step 4: On switch,Port poe power mode is 802.3af")
    public void step4() {
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = handle.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the power limit is set to 802.3af");
    }

    @Step("Test Step 4: Set switch power mode to pre-dot3at and check on Switch")
    public void step5() {
        if (bPoeStandMore) {
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            handle.setExpand(page.btnPowerManagement, false);
            handle.setSelected(page.cbEnablePoe, true, true);
            page.setPoEStand("Pre-802.3at");
            page.clickSave();

            if (WebportalParam.isRltkSW1) {
                sExpectedtValue = "pre-802.3at";
            } else {
                sExpectedtValue = "high-power pre-dot3at";
            }

            handle.waitCmdReady(sExpectedtValue, false);
            tmpStr = handle.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
            assertTrue(tmpStr.contains(sExpectedtValue), "verify the power limit is set to " + sExpectedtValue);
        }
    }

    @Step("Test Step 5: Set switch power mode to Legacy and check on Switch")
    public void step6() {
        if (bPoeStandMore) {
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            handle.setExpand(page.btnPowerManagement, false);
            handle.setSelected(page.cbEnablePoe, true, true);
            page.setPoEStand("Legacy");
            page.clickSave();

            if (WebportalParam.isRltkSW1) {
                sExpectedtValue = "high-inrush";
            } else {
                sExpectedtValue = "high-power legacy";
            }
            handle.waitCmdReady(sExpectedtValue, false);
            tmpStr = handle.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
            assertTrue(tmpStr.contains(sExpectedtValue), "verify the power limit is set to " + sExpectedtValue);
        }
    }

    @Step("Test Step 6: Set switch power mode to 802.3at and check on Switch")
    public void step7() {
        if (bPoeStandMore) {
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            handle.setExpand(page.btnPowerManagement, false);
            handle.setSelected(page.cbEnablePoe, true, true);
            page.setPoEStand("802.3at");
            page.clickSave();
            handle.sleepsync();

            tmpStr = SwitchCLIUtils.getPoEInfo("g1");
            assertTrue(SwitchCLIUtils.PoEClass.iPoEStandard == 1, "verify the power limit is set 802.3at");
        }
    }
}
