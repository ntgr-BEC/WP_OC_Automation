package webportal.SwitchManaged.POE.PRJCBUGEN_T6071;

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
import webportal.weboperation.DevicesDashPageMNG;
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
    @Story("PRJCBUGEN_T6071") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("038-Set and check Port Priority") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6071") // It's a testcase id/link from Jira Test Case.
    
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
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        
        DevicesDashPageMNG pageNew = new DevicesDashPageMNG();
        pageNew.openPoEDevice();
        
        DevicesSwitchSummaryPage page = new DevicesSwitchSummaryPage();
        page.portChoice("1").click();
    }
    
    @Step("Test Step 2: Set switch Priority to Medium")
    public void step2() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        handle.setExpand(page.btnPowerManagement, false);
        handle.setSelected(page.cbEnablePoe, true, true);
        page.setPriority("Medium");
        page.setPOEValue("0");
        
        page.clickSave();
        if (WebportalParam.isRltkSW1) {
            sExpectedtValue = "medium";
        } else {
            sExpectedtValue = "poe priority Medium";
        }
    }
    
    @Step("Test Step 3: On switch check switch Priority to Medium")
    public void step3() {
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the Priority to Medium");
    }
    
    @Step("Test Step 4: Set switch Priority to High")
    public void step4() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        handle.setExpand(page.btnPowerManagement, false);
        handle.setSelected(page.cbEnablePoe, true, true);
        page.setPriority("High");
        
        page.clickSave();
        if (WebportalParam.isRltkSW1) {
            sExpectedtValue = "high";
        } else {
            sExpectedtValue = "poe priority High";
        }
    }
    
    @Step("Test Step 5: On switch check switch Priority to High")
    public void step5() {
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the Priority to High");
    }
    
    @Step("Test Step 6: Set switch Priority to Critical")
    public void step6() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        handle.setExpand(page.btnPowerManagement, false);
        handle.setSelected(page.cbEnablePoe, true, true);
        page.setPriority("Critical");
        
        page.clickSave();
        if (WebportalParam.isRltkSW1) {
            sExpectedtValue = "critical";
        } else {
            sExpectedtValue = "poe priority Crit";
        }
    }
    
    @Step("Test Step 7: On switch check switch Priority to Critical")
    public void step7() {
        handle.waitCmdReady(sExpectedtValue, false);
        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
        assertTrue(tmpStr.contains(sExpectedtValue), "verify the Priority to Critical");
    }
    
    @Step("Test Step 8: Set switch Priority to Low")
    public void step8() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        handle.setExpand(page.btnPowerManagement, false);
        handle.setSelected(page.cbEnablePoe, true, true);
        page.setPriority("Low");
        
        page.clickSave();
        if (WebportalParam.isRltkSW1) {
            sExpectedtValue = "critical";
        } else {
            sExpectedtValue = "poe priority Crit";
        }
    }
    
    @Step("Test Step 9: On switch check switch Priority to Low")
    public void step9() {
        handle.waitCmdReady(sExpectedtValue, true);
        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
        assertFalse(tmpStr.contains(sExpectedtValue), "verify the Priority to Low");
    }
    
}
