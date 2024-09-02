package webportal.Switch.POE.PRJCBUGEN_T5002;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
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
    boolean       bUserDefined = false;
    
    @Issue("PRJCBUGEN-16751")
    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5002") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("001-Change and verify power limit configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5002") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        if (!WebportalParam.sw1Model.contains("TUP")) {
            runTest(this);
        }
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
        
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage pageRes = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        pageRes.goBatchPortConf();
        pageRes.setPorts(false, true);
        pageRes.setPOEValue("0");
        MyCommonAPIs.sleep(120, "restore all PoE");
    }
    
    @Step("Test Step 2: Go to Wired->edit the device,on  port config page,select one port,power limit set to 3W")
    public void step2() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        bUserDefined = page.hasUserDefined();
        if (bUserDefined) {
            sCurrentValue = "3";
            sExpectedtValue = String.format(" %s000", sCurrentValue);
            page.setPOEValue(sCurrentValue);
            new DevicesSwitchConnectedNeighboursPortConfiqSummaryPage();
        }
    }
    
    @Step("Test Step 3: Show deploy successfully")
    public void step3() {
        if (bUserDefined) {
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            assertTrue(page.getPOEValue().equals(sCurrentValue), "verify the value is set");
        }
    }
    
    @Step("Test Step 4: On switch,the power limit data change to 3000mW for select port")
    public void step4() {
        if (bUserDefined) {
            handle.waitCmdReady(sExpectedtValue, false);
            tmpStr = SwitchCLIUtils.getPoEInfo("g1");
            assertTrue(tmpStr.contains(sCurrentValue), "verify the power limit is set to 3");
        }
    }
    
    @Step("Test Step 5: Go to Wired->edit the device,on  port config page,select one port,power limit set to 15W")
    public void step5() {
        if (bUserDefined) {
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            sCurrentValue = "15";
            sExpectedtValue = String.format(" %s000", sCurrentValue);
            page.setPOEValue(sCurrentValue);
        }
    }
    
    @Step("Test Step 6: Show deploy successfully")
    public void step6() {
        if (bUserDefined) {
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            assertTrue(page.getPOEValue().equals(sCurrentValue), "verify the value is set to 15");
        }
    }
    
    // @Step("Test Step 7: On switch,the power limit data change to 30000mW(default) for select port")
    // public void step7() {
    // handle.waitCmdReady("", true);
    // tmpStr = handle.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false);
    // assertFalse(tmpStr.contains("poe power limit"), "verify the power limit is set to default");
    // }
    
    @Step("Test Step 8: Go to Wired->edit the device,on  port config page,select one port,power limit set to 10W")
    public void step8() {
        if (bUserDefined) {
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            sCurrentValue = "10";
            sExpectedtValue = String.format(" %s000", sCurrentValue); // also used for default value
            page.setPOEValue(sCurrentValue);
        }
    }
    
    @Step("Test Step 9: Show deploy successfully")
    public void step9() {
        if (bUserDefined) {
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            assertTrue(page.getPOEValue().equals(sCurrentValue), "verify the value is set to 10");
        }
    }
    
    @Step("Test Step 10: On switch,the power limit data change to 10000mW for select port")
    public void step10() {
        if (bUserDefined) {
            handle.waitCmdReady(sExpectedtValue, false);
            tmpStr = SwitchCLIUtils.getPoEInfo("g1");
            assertTrue(tmpStr.contains(sCurrentValue), "1. verify the power limit is set to " + sCurrentValue);
        }
    }
    
    @Step("Test Step 11: Set power limit use default value")
    public void step11() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        sCurrentValue = "0";
        page.setPOEValue(sCurrentValue);
    }
    
    @Step("Test Step 12: Show deploy successfully")
    public void step12() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        assertTrue(page.txtPowerLimitNew.is(Condition.disabled), "verify the value is set to default");
    }
    
    @Step("Test Step 13: The power limit data change to default value 30000mW for the select port on swtich")
    public void step13() {
        if (bUserDefined) {
            MyCommonAPIs.sleepsync();
            tmpStr = SwitchCLIUtils.getPoEInfo("g1");
            assertFalse(tmpStr.contains(sExpectedtValue), "verify the power limit is set to default, not: " + sExpectedtValue);
        }
    }
}
