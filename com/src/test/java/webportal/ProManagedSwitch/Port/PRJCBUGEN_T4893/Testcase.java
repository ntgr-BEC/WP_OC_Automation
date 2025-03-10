package webportal.ProManagedSwitch.Port.PRJCBUGEN_T4893;

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
import webportal.weboperation.DashboardLocationPage;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;

/**
 *
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase implements Config {
    static int portNumber;

    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4893") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("016-Shutdown and no shutdown ports on multiple switchs") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4893") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p4") // "p2"
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter wired setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        handle.gotoLoction();
        DashboardLocationPage dashboardLocationPage = new DashboardLocationPage();
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
    }

    @Step("Test Step 2: Select all ports on two dut(except management port),no Shutdown all ports")
    public void step2() {
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        portNumber = wiredGroupPortConfigPage.multiSettingAllPorts(BATTCHSETTING1);
        // wait 60s ,cloud need time send command to switches
        // two switches are not same, but at least 8 for one
        portNumber = 14;
    }

    @Step("Test Step 3: Check ports status on both switchs，from GUI")
    public void step3() {
        MyCommonAPIs.sleep(120000);
        // check sw1 on webportal
        for (int i = 1; i < portNumber / 2; i++) {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
            DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
            devicesSwitchSummaryPage.enterPortConfigSummary(String.valueOf(i));
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            boolean portStatus = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.isEnable();
            if (portStatus == true) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult, "fail in check switch1 port: " + i);
            }
        }
        // check sw2 on webportal
        for (int i = 1; i < portNumber / 2; i++) {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw2serialNo);
            DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
            devicesSwitchSummaryPage.enterPortConfigSummary(String.valueOf(i));
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            boolean portStatus2 = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.isEnable();
            if (portStatus2 == true) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult, "fail in check switch2,port:" + i);
            }
        }
    }

    @Step("Test Step 4: Config successfully, check all switch and port is enable on CLI")
    public void step4() {
        // check on dut CLI
        SwitchTelnet switchTelnet = new SwitchTelnet(webportalParam.sw1IPaddress);
        for (int i = 1; i < portNumber / 2; i++) {
            boolean portStatus = switchTelnet.getPortAdminMode("g" + i);
            if (portStatus) {
                micResult = true;
                System.out.println(portStatus);
            } else {
                micResult = false;
                assertTrue(micResult);
            }
        }
        SwitchTelnet.disconnect();
        SwitchTelnet switchTelnet2 = new SwitchTelnet(webportalParam.sw2IPaddress);
        for (int i = 1; i < portNumber / 2; i++) {
            boolean portStatus = switchTelnet2.getPortAdminMode("g" + i);
            if (portStatus) {
                micResult = true;
                System.out.println(portStatus);
            } else {
                micResult = false;
                assertTrue(micResult);
            }
        }
        SwitchTelnet.disconnect();
    }

    @Step("Test Step 5: Select all ports on two dut(except management port),Shutdown all ports")
    public void step5() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        portNumber = wiredGroupPortConfigPage.multiSettingAllPorts(BATTCHSETTING2);
        // two switches are not same, but at least 8 for one
        portNumber = 14;
    }

    @Step("Test Step 6: Check ports status on both switchs，from GUI")
    public void step6() {
        // wait 120s ,cloud need time send command to switches
        MyCommonAPIs.sleep(120000);
        // check sw1 on webportal
        for (int i = 1; i < portNumber / 2; i++) {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
            DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
            devicesSwitchSummaryPage.enterPortConfigSummary(String.valueOf(i));
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            boolean portStatus = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.isEnable();
            if (portStatus == false) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult, "fail in check switch1 port: " + i);
            }
        }
        // check sw2 on webportal
        for (int i = 1; i < portNumber / 2; i++) {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw2serialNo);
            DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
            devicesSwitchSummaryPage.enterPortConfigSummary(String.valueOf(i));
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            boolean portStatus2 = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.isEnable();
            if (portStatus2 == false) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult, "fail in check switch2,port:" + i);
            }
        }
    }

    @Step("Test Step 7: Config successfully, check all switch and port is enable on CLI")
    public void step7() {
        // check on dut CLI
        SwitchTelnet switchTelnet = new SwitchTelnet(webportalParam.sw1IPaddress);
        for (int i = 1; i <= portNumber / 2; i++) {
            boolean portStatus = switchTelnet.getPortAdminMode("g" + i);
            if (!portStatus) {
                micResult = true;
                System.out.println(portStatus);
            } else {
                micResult = false;
                assertTrue(micResult, "check port: " + i);
            }
        }
        SwitchTelnet.disconnect();
        SwitchTelnet switchTelnet2 = new SwitchTelnet(webportalParam.sw2IPaddress);
        for (int i = 1; i < portNumber / 2; i++) {
            boolean portStatus = switchTelnet2.getPortAdminMode("g" + i);
            if (!portStatus) {
                micResult = true;
                System.out.println(portStatus);
            } else {
                micResult = false;
                assertTrue(micResult, "check port: " + i);
            }
        }
        SwitchTelnet.disconnect();
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        step2();
    }
}
