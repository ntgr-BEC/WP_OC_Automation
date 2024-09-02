package webportal.Switch.System.PRJCBUGEN_T4639;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesSwitchConnectedNeighborsPage;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSummaryPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4639") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("001-Get Connected Clients in L2 physical port") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4639") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: connect sw1 and sw2")
    public void step2() {
        // WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        // WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        // WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        // wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        // MyCommonAPIs.sleep(60000);
    }

    @Step("Test Step 3: check port 1 neighbor info in neighbor page")
    public void step3() {
        DevicesDashPage devicesDashPage = new DevicesDashPage();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw2serialNo);
        DevicesSwitchConnectedNeighborsPage devicesSwitchConnectedNeighborsPage = new DevicesSwitchConnectedNeighborsPage();
        // WebCheck webCheck = new WebCheck();
        // String info = webCheck.getPageSource();
        // if (info.contains(WebportalParam.sw1deveiceName) && info.contains(WebportalParam.sw1IPaddress)
        // && info.contains(WebportalParam.sw1MacAddress)) {
        // micResult = true;
        // }
        // assertTrue(micResult);
        assertTrue(devicesSwitchConnectedNeighborsPage.ports.size() > 0, "check no of ports");
        assertTrue(devicesSwitchConnectedNeighborsPage.sIPs.size() > 0, "check no of Ip");
        assertTrue(devicesSwitchConnectedNeighborsPage.sMacs.size() > 0, "check no of Mac");
    }

    @Step("Test Step 4: check port1 in summary page")
    public void step4() {
        DevicesDashPage devicesDashPage = new DevicesDashPage();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw2serialNo);

        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage devicesSwitchConnectedNeighboursPortConfiqSummaryPage = devicesSwitchSummaryPage
                .enterPortConfigSummary(WebportalParam.sw2ManagePort);
        // WebCheck webCheck = new WebCheck();
        // String info = webCheck.getPageSource();
        // if (info.contains(WebportalParam.sw1deveiceName) && info.contains(WebportalParam.sw1IPaddress)
        // && info.contains(WebportalParam.sw1MacAddress)) {
        // micResult = true;
        // }
        // assertTrue(micResult);
        assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.getByteSent() > 0, "check bytes of send");
        assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.getByteRecv() > 0, "check bytes of recv");

        assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.ChassisID.getText().length() > 0, "check length of chassis id");
        assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.portId.getText().length() > 0, "check length of port id");
        assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.MNGTIPAddress.getText().length() > 0, "check length of ip address");
        assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.NeightborDescription.getText().length() > 0, "check length of nb desc");
        // assertTrue(devicesSwitchConnectedNeighboursPortConfiqSummaryPage.PortDescription.getText().length() > 0, "check
        // length of port desc");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        // WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        // WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        // WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        // wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING2);
    }
}
