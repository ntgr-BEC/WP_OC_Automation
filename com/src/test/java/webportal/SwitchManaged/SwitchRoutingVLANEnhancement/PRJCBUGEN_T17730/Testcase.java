package webportal.SwitchManaged.SwitchRoutingVLANEnhancement.PRJCBUGEN_T17730;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtilsMNG;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesSwitchIpSettingsPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String                      networkName                 = "1";
    DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = null;
    boolean                     needRestore                 = false;
    boolean                     needRestoreDev              = false;
    
    @Feature("Switch.SwitchRoutingVLANEnhancement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17730") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("013-Change Network Inteface mode from Static to DHCP on device IP Setting page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17730") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        if (needRestore) {
            netsp.restoreVlan1toDHCP();
            ddpmg.gotoPage();
            ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
            netsp.gotoPage();
        }
        netsp.deleteAllNetwork();
        if (needRestoreDev) {
            ddpmg.gotoPage();
            ddpmg.enterDevice(WebportalParam.sw1serialNo);
            devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();
            assertTrue(devicesSwitchIpSettingsPage.isDHCP(), "device should be in dhcp mode");
            
            devicesSwitchIpSettingsPage.dhcpAutomatically.click();
            handle.clickButton(0);
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open WP page and Make sure switchManagement VLAN mode is None")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddpmg.gotoPage();
        
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(4);
        netsp.setVlanIpMode(2);
        netsp.finishAllStep();
        needRestore = true;
    }

    @Step("Test Step 2: Verify network ip on switches")
    public void step2() {
        ddpmg.gotoPage();
        ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
        ddpmg.rebootSwitchDevice();
        ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
        
        ddpmg.gotoPage();
        String sStaticIpWP = ddpmg.getDeviceIP(WebportalParam.sw1serialNo);
        assertTrue(SwitchCLIUtilsMNG.getNetworkIp().contains(sStaticIpWP),
                String.format("check ip is expected on cli: %s/%s", WebportalParam.sw1IPaddress, sStaticIpWP));
    }

    @Step("Test Step 3: change mode from 'DHCP' to 'Static' in Devices-->IP Setting page")
    public void step3() {
        ddpmg.enterDevice(WebportalParam.sw1serialNo);
        devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();

        devicesSwitchIpSettingsPage.dhcpAutomatically.click();
        handle.clickButton(0);
        needRestoreDev = true;
    }

    @Step("Test Step 4: change mode from 'Static' to 'DHCP'")
    public void step4() {
        ddpmg.gotoPage();
        ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
        
        devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();
        devicesSwitchIpSettingsPage.setIpToDhcp();
        needRestoreDev = false;
    }

    @Step("Test Step 5: Config success, check from Insight and Switch local")
    public void step5() {
        ddpmg.gotoPage();
        ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
        
        String sStaticIpWP = ddpmg.getDeviceIP(WebportalParam.sw1serialNo);
        assertTrue(sStaticIpWP.equals(WebportalParam.sw1IPaddress),
                String.format("check ip is expected on wp: %s/%s", WebportalParam.sw1IPaddress, sStaticIpWP));
        assertTrue(SwitchCLIUtilsMNG.getNetworkIp().contains(WebportalParam.sw1IPaddress),
                String.format("check ip is expected on cli: %s/%s", WebportalParam.sw1IPaddress, sStaticIpWP));
    }
}
