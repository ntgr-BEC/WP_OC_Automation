package webportal.Switch.SwitchRoutingVLANEnhancement.PRJCBUGEN_T17725;

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
    String                      net1Name                    = "test1net";
    String                      vlan1Name                   = "test1vlan";
    String                      vlan1Id                     = "1725";
    String                      net2Name                    = "test2net";
    String                      vlan2Name                   = "test2vlan";
    String                      vlan2Id                     = "1726";
    String                      networkName                 = "1";
    DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = null;
    boolean                     needRestore                 = false;
    
    @Feature("Switch.SwitchRoutingVLANEnhancement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17725") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("008-Change Management VLAN mode from DHCP to None") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17725") // It's a testcase id/link from Jira Test Case.
    
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
                ddp.gotoPage();
                ddp.waitDevicesReConnected(WebportalParam.sw1serialNo);
                netsp.gotoPage();
            }
            netsp.deleteAllNetwork();
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Device Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        ddp.gotoPage();
    }
    
    @Step("Test Step 2: Create new routing VLAN with DHCP mode")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(net1Name, vlan1Id, 0);
    }
    
    @Step("Test Step 3: Create new routing VLAN with Static mode")
    public void step3() {
        netsp.gotoPage();
        netsp.createNetwork(net2Name, vlan2Id, 1);
    }
    
    @Step("Test Step 4: change mode from 'DHCP' to 'None' for Management VLAN")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(4);
        netsp.setVlanIpMode(2);
        assertTrue(netsp.btnVlanWarn.exists(), "error on set none if has dhcp vlan");
    }
    
    @Step("Test Step 5: Delete the DHCP mode routing VLAN and set to None again")
    public void step5() {
        netsp.gotoPage();
        netsp.deleteNetwork(net1Name);
        
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(4);
        netsp.setVlanIpMode(2);
        assertTrue(netsp.btnVlanWarn.exists(), "error on set none if has static vlan");
    }
    
    @Step("Test Step 6: Delete the Static mode routing VLAN and set to None again")
    public void step6() {
        netsp.gotoPage();
        netsp.deleteNetwork(net2Name);
        
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(4);
        netsp.setVlanIpMode(2);
        assertTrue(!netsp.btnVlanWarn.exists(), "ok on set none if has no dhcp/static vlan");
        netsp.finishAllStep();
        needRestore = true;
    }
    
    @Step("Test Step 7: check from Insight and Switch local")
    public void step7() {
        ddp.gotoPage();
        String sStaticIp = handle.getVlan1StaticIp(false);
        String sStaticIpWP = ddp.getDeviceIP(WebportalParam.sw1serialNo);
        for (int i = 0; i < 10; i++) {
            ddp.waitDevicesReConnected(WebportalParam.sw1serialNo);
            sStaticIpWP = ddp.getDeviceIP(WebportalParam.sw1serialNo);
            if (sStaticIpWP.equals(sStaticIp)) {
                break;
            }
        }

        ddp.gotoPage();
        sStaticIpWP = ddp.getDeviceIP(WebportalParam.sw1serialNo);
        assertTrue(sStaticIpWP.equals(sStaticIp), String.format("check ip is expected on wp: %s/%s", sStaticIp, sStaticIpWP));
        assertTrue(SwitchCLIUtils.getNetworkIp().contains(sStaticIp), String.format("check ip is expected on cli: %s/%s", sStaticIp, sStaticIpWP));
    }
}
