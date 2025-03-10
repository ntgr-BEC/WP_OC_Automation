package webportal.ProManagedSwitch.System.PRJCBUGEN_T4642;

import static org.testng.Assert.assertEquals;
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
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author sumanta
 */
public class Testcase extends TestCaseBase implements Config {
    
    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4642") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("005-Edit Device Name") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4642") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: modify device name")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.editDeviceName(WebportalParam.sw1serialNo, DEVIENAME1);
        String newName = "";
        for (int i = 0; i < 5; i++) { // wait data to update
            handle.refresh();
            newName = devicesDashPage.getDeviceName(WebportalParam.sw1serialNo);
            if (newName.equals(DEVIENAME1)) {
                break;
            }
            MyCommonAPIs.sleepsync();
        }
        assertEquals(newName, DEVIENAME1);
    }
    
    @Step("Test Step 3: check in switch telnet")
    public void step3() {
        handle.waitCmdReady(DEVIENAME1, false);
        
        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress);
        String newName = switchTelnet.getDeviceName();
        assertTrue(newName.contains(DEVIENAME1), "check device name");
        SwitchTelnet.disconnect();
    }
    
    @Step("Test Step 4: reboot device")
    public void step4() {
        ddpmg.gotoPage();
//        ddpmg.waitDevicesReboot(WebportalParam.sw1serialNo);
        ddpmg.rebootDevice(WebportalParam.sw1serialNo);
        handle.waitDeviceOnline();
    }
    
    @Step("Test Step 5: check deviceName after reboot")
    public void step5() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.waitAllSwitchDevicesConnected();
        
        String newName = devicesDashPage.getDeviceName(WebportalParam.sw1serialNo);
        assertEquals(newName, DEVIENAME1);
    }
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        handle.refresh();
        try {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            devicesDashPage.editDeviceName(WebportalParam.sw1serialNo, WebportalParam.sw1deveiceName);
            MyCommonAPIs.sleepi(30);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
