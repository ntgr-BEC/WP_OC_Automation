package webportal.SwitchManaged.POE.PRJCBUGEN_T5007;

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
import util.SwitchCLIUtilsMNG;
import webportal.param.WebportalParam;
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
    @Story("PRJCBUGEN_T5007") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("008-Cache PoE configuration deployment") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5007") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        SwitchCLIUtilsMNG.CloudModeSet(true);
        WebportalParam.updateSwitchOneOption(false, null);
    }
    
    @Step("Test Step 1: Put DUT out of internet")
    public void step1() {
        SwitchCLIUtilsMNG.CloudModeSet(false);
        handle.sleepi(5);
        SwitchCLIUtilsMNG.CloudModeSet(true);
        handle.sleepi(4 * 60);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Open Device")
    public void step2() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        DevicesDashPageMNG pageNew = new DevicesDashPageMNG();
        pageNew.openPoEDevice();

        DevicesSwitchSummaryPage page = new DevicesSwitchSummaryPage();
        page.portChoice("1").click();
    }

    @Step("Test Step 3: Go to Wired->Edit device->Summary->Click one port->Setting->Batch port configuration,select 2+ PoE port and set power limit to class1")
    public void step3() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page.goBatchPortConf();
        page.setPorts(true, true);
        sCurrentValue = "4";
        sExpectedtValue = String.format(" %s000", sCurrentValue);
        page.setPOEValue("Class1");
        new DevicesSwitchConnectedNeighboursPortConfiqSummaryPage();
    }

    @Step("Test Step 4: Show deploy successfully")
    public void step4() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        assertTrue(page.getPOEValue().equals(sCurrentValue), "verify the value is set to 4");
    }

    @Step("Test Step 5: On switch,the power limit data change to 4000mW for select port")
    public void step5() {
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the power limit is set to 4 - 1");
        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 2), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the power limit is set to 4 - 2");
    }
}
