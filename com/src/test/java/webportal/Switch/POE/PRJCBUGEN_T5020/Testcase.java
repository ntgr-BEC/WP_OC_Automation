package webportal.Switch.POE.PRJCBUGEN_T5020;

import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
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
    
    public String sTestStr = "TSName5020";
    public String sCurrentValue;
    public String sExpectedtValue;
    
    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5020") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("023-config time schedule to all ports") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5020") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wpsp.gotoPage();
        MyCommonAPIs.sleepi(10);
        if (wpsp != null) {
            System.out.println("Now Deleting POE schedule");
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
    
    @Step("Test Step 2: create a Daily time schedule")
    public void step2() {
        wpsp.addPoESchedule(sTestStr, 0, "Daily");
        assertTrue(wpsp.getPoEs().contains(sTestStr), "Save a name");
    }
    
    @Step("Test Step 3: Batch to select port 3 from device dashboard, config time schedule for it")
    public void step3() {
        DevicesDashPage pageNew = new DevicesDashPage();
        pageNew.openPoEDevice();

        DevicesSwitchSummaryPage page = new DevicesSwitchSummaryPage();
        page.portChoice("1").click();

        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        MyCommonAPIs.sleepi(3);
        page1.goBatchPortConf();
        page1.setPorts(false, true);
        MyCommonAPIs.sleepi(10);

        handle.setExpand(page1.btnPowerManagement, false);
        page1.lbSchName.selectOption(sTestStr);
        handle.clickButton(0);
        handle.clickBoxLastButton();

        handle.waitCmdReady(sTestStr, false);
        MyCommonAPIs.sleepsync();

        tmpStr = SwitchCLIUtils.getPoETimeRange(sTestStr);
        assertTrue(tmpStr.contains(new SimpleDateFormat("yyyy").format(new Date())),
                "PRJCBUGEN-30390--can not deploy POE Downtime schedules with Daily or Weekly to the switch");

        tmpStr = MyCommonAPIs
                .getCmdOutput(String.format("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1)), false);
        assertTrue(tmpStr.contains(sTestStr), "port g1 is binding to ts");
        tmpStr = MyCommonAPIs
                .getCmdOutput(String.format("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 2)), false);
        assertTrue(tmpStr.contains(sTestStr), "port g2 is binding to ts");
        tmpStr = MyCommonAPIs
                .getCmdOutput(String.format("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 3)), false);
        assertTrue(tmpStr.contains(sTestStr), "port g3 is binding to ts");
    }
    
}
