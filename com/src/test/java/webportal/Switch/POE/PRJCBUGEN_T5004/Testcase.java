package webportal.Switch.POE.PRJCBUGEN_T5004;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

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
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSummaryPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    String tmpStr;

    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5004") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("005-Change 2+ PoE ports setting") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5004") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
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

        DevicesDashPage pageNew = new DevicesDashPage();
        pageNew.openPoEDevice();

        DevicesSwitchSummaryPage page = new DevicesSwitchSummaryPage();
        page.portChoice("1").click();
    }

    @Step("Test Step 2: Go to Wired->Edit device->Summary->Click one port->Setting->Batch port configuration,select 2+ PoE port and set power limit to class2")
    public void step2() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page.goBatchPortConf();
        page.setPorts(true, true);
        sCurrentValue = "7";
        sExpectedtValue = String.format(" %s000", sCurrentValue);
        page.setPOEValue("Class2");
        new DevicesSwitchConnectedNeighboursPortConfiqSummaryPage();
    }

    @Step("Test Step 3: Show deploy successfully")
    public void step3() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page.goBatchPortConf();
        assertTrue(page.getPOEValue().equals(sCurrentValue), "verify the value is set to 7");
    }

    @Step("Test Step 4: On switch,the power limit data change to 10000mW for select port")
    public void step4() {
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = SwitchCLIUtils.getPoEInfo("g1");
        assertTrue(tmpStr.contains(sCurrentValue), "verify the power limit is set to 7 - 1");
        tmpStr = SwitchCLIUtils.getPoEInfo("g2");
        assertTrue(tmpStr.contains(sCurrentValue), "verify the power limit is set to 7 - 2");
    }

    @Step("Test Step 5: Change all POE ports status to disable")
    public void step5() {
        DevicesSwitchSummaryPage pageDss = new DevicesSwitchSummaryPage();
        pageDss.portChoice("1").click();

        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page.goBatchPortConf();
        page.setPorts(true, false);
    }

    @Step("Test Step 6: Show deploy successfully")
    public void step6() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        assertTrue(page.getPOEValue().equals(sCurrentValue), "verify the value is kept to 7");
        assertFalse(page.cbEnablePoe.is(Condition.checked), "verify the poe is disabled");
    }

    @Step("Test Step 7: on switch,the status of all poe ports changed to disable")
    public void step7() {
        tmpStr = SwitchCLIUtils.getPoEInfo("g1");
        assertTrue(!SwitchCLIUtils.PoEClass.isEnabled, "verify g1 the power limit is disabled");
        tmpStr = SwitchCLIUtils.getPoEInfo("g2");
        assertTrue(!SwitchCLIUtils.PoEClass.isEnabled, "verify g2 the power limit is disabled");
    }

    @Step("Test Step 8: Change all POE ports status to enable")
    public void step8() {
        DevicesSwitchSummaryPage pageDss = new DevicesSwitchSummaryPage();
        pageDss.portChoice("1").click();

        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page.goBatchPortConf();
        page.setPorts(true, true);
        handle.refresh();
    }

    @Step("Test Step 9: Show deploy successfully")
    public void step9() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        assertTrue(page.getPOEValue().equals(sCurrentValue), "verify the value is kept to 7");
        assertTrue(page.cbEnablePoe.is(Condition.checked), "verify the poe is enabled");
    }

    @Step("Test Step 10: On switch,the POE status change to enable for select ports")
    public void step10() {
        tmpStr = SwitchCLIUtils.getPoEInfo("g1");
        assertTrue(SwitchCLIUtils.PoEClass.isEnabled, "verify g1 the power limit is enabled");
        tmpStr = SwitchCLIUtils.getPoEInfo("g2");
        assertTrue(SwitchCLIUtils.PoEClass.isEnabled, "verify g2 the power limit is enabled");
    }

}
