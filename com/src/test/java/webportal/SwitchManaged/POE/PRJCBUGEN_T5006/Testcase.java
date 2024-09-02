package webportal.SwitchManaged.POE.PRJCBUGEN_T5006;

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
import util.SwitchCLIUtilsMNG;
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesDashPageMNG;
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
    @Story("PRJCBUGEN_T5006") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007-Verify PoE port configuration by rebooting") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5006") // It's a testcase id/link from Jira Test Case.

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

        DevicesDashPageMNG pageNew = new DevicesDashPageMNG();
        pageNew.openPoEDevice();

        DevicesSwitchSummaryPage page = new DevicesSwitchSummaryPage();
        page.portChoice("1").click();
    }

    @Step("Test Step 2: On webportal, select all PoE ports and set power limit to class3")
    public void step2() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page.goBatchPortConf();
        page.setPorts(false, true);
        sCurrentValue = "15.4";
        sExpectedtValue = "15400";
        page.goBatchPortConf();
        page.setPOEValue("Class3");
        new DevicesSwitchConnectedNeighboursPortConfiqSummaryPage();
    }

    @Step("Test Step 3: Show deploy successfully")
    public void step3() {
        DevicesSwitchSummaryPage page1 = new DevicesSwitchSummaryPage();
        page1.portChoice("1").click();
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page.goBatchPortConf();
        assertTrue(page.getPOEValue().equals(sCurrentValue), "verify the value is set to 15.4");
    }

    @Step("Test Step 4: On switch,the power limit data change to 15400mW for select port")
    public void step4() {
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = SwitchCLIUtilsMNG.getPoEInfo("g1");
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the power limit is set to 15.4 - 1");
        tmpStr = SwitchCLIUtilsMNG.getPoEInfo("g2");
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the power limit is set to 15.4 - 2");
        tmpStr = SwitchCLIUtilsMNG.getPoEInfo("g3");
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the power limit is set to 15.4 - 3");
    }

    @Step("Test Step 5: Change all POE ports status to disable")
    public void step5() {
        DevicesSwitchSummaryPage pageDss = new DevicesSwitchSummaryPage();
        pageDss.portChoice("1").click();

        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page.goBatchPortConf();
        page.setPorts(false, false);
    }

    @Step("Test Step 6: Show deploy successfully")
    public void step6() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        assertTrue(page.getPOEValue().equals(sCurrentValue), "verify the value is kept to 15.4");
        assertFalse(page.cbEnablePoe.is(Condition.checked), "verify the poe is disabled");
    }

    @Step("Test Step 7: Back to Switch and reboot, check PoE configuration")
    public void step7() {

        ddpmg.gotoPage();
        ddpmg.waitDevicesReboot(WebportalParam.sw1serialNo);
    }

    @Step("Test Step 8: After reboot,all PoE configuration do not lose")
    public void step8() {
        tmpStr = SwitchCLIUtilsMNG.getPoEInfo("g1");
        assertTrue(!SwitchCLIUtilsMNG.PoEClass.isEnabled, "verify g1 the power limit is disabled");
        tmpStr = SwitchCLIUtilsMNG.getPoEInfo("g2");
        assertTrue(!SwitchCLIUtilsMNG.PoEClass.isEnabled, "verify g2 the power limit is disabled");
        tmpStr = SwitchCLIUtilsMNG.getPoEInfo("g3");
        assertTrue(!SwitchCLIUtilsMNG.PoEClass.isEnabled, "verify g3 the power limit is disabled");
    }

}
