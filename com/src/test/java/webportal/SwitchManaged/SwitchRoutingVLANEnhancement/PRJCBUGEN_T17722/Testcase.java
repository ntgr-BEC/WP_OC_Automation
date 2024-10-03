package webportal.SwitchManaged.SwitchRoutingVLANEnhancement.PRJCBUGEN_T17722;

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
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesSwitchIpSettingsPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = null;
    boolean                     needRestore                 = false;

    @Feature("Switch.SwitchRoutingVLANEnhancement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17722") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("003-Change Management VLAN mode from DHCP to Static on device IP Setting page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17722") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (needRestore) {
            ddpmg.gotoPage();
            devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();
            devicesSwitchIpSettingsPage.setIpToDhcp();
            ddpmg.gotoPage();
            ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Device Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddpmg.gotoPage();
    }

    @Step("Test Step 2: Go to Device Ip 'Settings' step")
    public void step2() {
        ddpmg.enterDevice(WebportalParam.sw1serialNo);
    }

    @Step("Test Step 3: set invalid parameter in 'IP Settings' step -- ip")
    public void step3() {
        devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();
        devicesSwitchIpSettingsPage.dhcpAutomatically.click();
        
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.ipAddress, "0.0.0.0");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid ip 0");
        
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.ipAddress, "255.255.255.255");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid ip 255.255");
        
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.ipAddress, "192.168.1.255");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid ip 1.255");
        
        // netsp.setStaticIP(WebportalParam.sw1deveiceName, "224.1.1.1");
        // netsp.clickNextOrSkip(false);
        // assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid ip multicast");
        
        // restore
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.ipAddress, WebportalParam.sw1IPaddress);
    }

    @Step("Test Step 4: set invalid parameter in 'IP Settings' step -- netmask")
    public void step4() {
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.subnetMask, "0.0.255.255");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid mask 0.255");
        
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.subnetMask, "0.0.0.0");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid mask 0.0");
        
        // restore
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.subnetMask, "255.255.255.0");
    }
    
    @Step("Test Step 5: set invalid parameter in 'IP Settings' step -- gw")
    public void step5() {
        String gwbackup = MyCommonAPIs.getValue(devicesSwitchIpSettingsPage.gatewayAddress);
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.gatewayAddress, "0.0.0.0");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid gw 0");

        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.gatewayAddress, "255.255.255.255");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid gw 255.255");

        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.gatewayAddress, gwbackup);
    }

    @Step("Test Step 6: set invalid parameter in 'IP Settings' step -- dns")
    public void step6() {
        String backup = MyCommonAPIs.getValue(devicesSwitchIpSettingsPage.dnsServer1);
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.dnsServer1, "0.0.0.0");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid gw 0");
        
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.dnsServer1, "255.255.255.255");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid gw 255");

        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.dnsServer1, backup);
    }
    
    @Step("Test Step 7: set invalid parameter in 'IP Settings' step -- IP and Gateway in different subnet")
    public void step7() {
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.ipAddress, "10.0.1.1");
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.gatewayAddress, "10.0.2.1");
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid subnet");
    }
    
    @Step("Test Step 8: 'Ip Settigns' step, change mode from 'DHCP' to 'Static'")
    public void step8() {
        handle.refresh();
        devicesSwitchIpSettingsPage.dhcpAutomatically.click();
        MyCommonAPIs.setText(devicesSwitchIpSettingsPage.ipAddress, handle.getVlan1StaticIp(false));
        handle.clickButton(0);
        
        needRestore = true;
        ddpmg.gotoPage();
        ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
        
        ddpmg.gotoPage();
        String sStaticIp = handle.getVlan1StaticIp(false);
        String sStaticIpWP = ddpmg.getDeviceIP(WebportalParam.sw1serialNo);
        assertTrue(sStaticIpWP.equals(sStaticIp), String.format("check ip is expected on wp: %s/%s", sStaticIp, sStaticIpWP));
        assertTrue(SwitchCLIUtils.getVlan1Ip().contains(sStaticIp), String.format("check ip is expected on cli: %s/%s", sStaticIp, sStaticIpWP));
    }
}
