package webportal.Switch.POE.PRJCBUGEN_T6301;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
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
    boolean       bPoeStandMore = false;

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6301") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("049-Set power limit value to class0-6") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6301") // It's a testcase id/link from Jira Test Case.

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

        DevicesDashPage pageNew = new DevicesDashPage();
        pageNew.openPoEDevice();

        DevicesSwitchSummaryPage page = new DevicesSwitchSummaryPage();
        page.portChoice("1").click();
    }

    @Step("Test Step 2: Set switch PoE class to 1")
    public void step2() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        handle.setExpand(page.btnPowerManagement, false);
        handle.setSelected(page.cbEnablePoe, true, true);
        page.setClass("Class1");
        page.clickSave();
    }

    @Step("Test Step 3: On switch check switch PoE class to 1")
    public void step3() {
        sExpectedtValue = "4000";
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the PoE class to 1");
    }

    @Step("Test Step 4: Set switch PoE class to 0")
    public void step4() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        handle.setExpand(page.btnPowerManagement, false);
        handle.setSelected(page.cbEnablePoe, true, true);
        page.setClass("Class0");
        page.clickSave();
        sExpectedtValue = "15400";
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the PoE class to 0");
    }

    @Step("Test Step 5: Set switch PoE class to 2")
    public void step5() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        handle.setExpand(page.btnPowerManagement, false);
        handle.setSelected(page.cbEnablePoe, true, true);
        page.setClass("Class2");
        page.clickSave();
        sExpectedtValue = "7000";
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the PoE class to 2");
    }

    @Step("Test Step 6: Set switch PoE class to 3")
    public void step6() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        handle.setExpand(page.btnPowerManagement, false);
        handle.setSelected(page.cbEnablePoe, true, true);
        page.setClass("Class3");
        page.clickSave();
        sExpectedtValue = "15400";
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the PoE class to 3");
    }

//    @Step("Test Step 7: Set switch PoE class to 4")
//    public void step7() {
//        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
//        handle.setExpand(page.btnPowerManagement, false);
//        handle.setSelected(page.cbEnablePoe, true, true);
//        if (page.isPoEHasClass("class4")) {
//            page.setClass("Class4");
//            page.clickSave();
//            sExpectedtValue = "30000";
//            MyCommonAPIs.sleepsync();
//            tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
//            assertFalse(tmpStr.contains(sExpectedtValue), "verify the PoE class to 4");
//        }
//    }
//
//    @Step("Test Step 8: Set switch PoE class to 6")
//    public void step8() {
//        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
//        handle.setExpand(page.btnPowerManagement, false);
//        handle.setSelected(page.cbEnablePoe, true, true);
//        if (page.isPoEHasClass("class6")) {
//            page.setClass("Class6");
//            page.clickSave();
//            sExpectedtValue = "60000";
//            MyCommonAPIs.sleepsync();
//            tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
//            assertFalse(tmpStr.contains(sExpectedtValue), "verify the PoE class to 6");
//        }
//    }

}
