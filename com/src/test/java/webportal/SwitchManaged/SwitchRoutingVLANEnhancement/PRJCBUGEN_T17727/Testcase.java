package webportal.SwitchManaged.SwitchRoutingVLANEnhancement.PRJCBUGEN_T17727;

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
    String                      netName                     = "testnet";
    String                      vlanName                    = "testvlan";
    String                      vlanId                      = "727";
    String                      networkName                 = "1";
    DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = null;
    boolean                     needRestore                 = false;
    
    @Feature("Switch.SwitchRoutingVLANEnhancement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17727") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("010-Enable Management VLAN routing with DHCP mode from Network Interface scenario") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17727") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        if (!WebportalParam.isRltkSW()) {
            runTest(this);
        }
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (!WebportalParam.isRltkSW()) {
            netsp.gotoPage();
            if (needRestore) {
                netsp.restoreVlan1toDHCP();
                ddpmg.gotoPage();
                ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
                netsp.gotoPage();
            }
            netsp.deleteNetwork(netName);
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
        MyCommonAPIs.sleepsync();
        
        ddpmg.gotoPage();
        String sStaticIpWP = ddpmg.getDeviceIP(WebportalParam.sw1serialNo);
        assertTrue(SwitchCLIUtils.getNetworkIp().contains(sStaticIpWP),
                String.format("check ip is expected on cli: %s/%s", WebportalParam.sw1IPaddress, sStaticIpWP));
    }
    
    @Step("Test Step 3: Try to create new routing VLAN with DHCP/Static mode")
    public void step3() {
        netsp.gotoPage();
        netsp.createNetwork(netName, vlanId, 2);
    }
    
    @Step("Test Step 4: Config failed, Insight should popup error message")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(netName);
        netsp.gotoStep(4);
        netsp.setVlanIpMode(0);
        assertTrue(netsp.btnVlanWarn.exists(), "error on set none if has dhcp vlan");
    }
    
    @Step("Test Step 5: Network Setup page, click Management VLAN, on 'VLAN IP Configuration' step, change mode from 'None' to 'DHCP';")
    public void step5() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(4);
        netsp.setVlanIpMode(0);
        netsp.finishAllStep();
        needRestore = false;
    }
    
    @Step("Test Step 6: Config success, check by Insight and Switch local;")
    public void step6() {
        ddpmg.gotoPage();
        ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
        ddpmg.rebootSwitchDevice();
        ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);

        ddpmg.gotoPage();
        String sStaticIpWP = ddpmg.getDeviceIP(WebportalParam.sw1serialNo);
        assertTrue(sStaticIpWP.equals(WebportalParam.sw1IPaddress),
                String.format("check ip is expected on wp: %s/%s", WebportalParam.sw1IPaddress, sStaticIpWP));
        assertTrue(SwitchCLIUtils.getVlan1Ip().contains(WebportalParam.sw1IPaddress),
                String.format("check ip is expected on cli: %s/%s", WebportalParam.sw1IPaddress, sStaticIpWP));
    }
    
    @Step("Test Step 7: Then try to create new routing VLAN with DHCP/Static mode")
    public void step7() {
        netsp.gotoPage();
        netsp.openNetwork(netName);
        netsp.gotoStep(4);
        netsp.setVlanIpMode(0);
        assertTrue(!netsp.btnVlanWarn.exists(), "ok on set dhcp vlan");
    }
}
