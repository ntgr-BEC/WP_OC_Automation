package webportal.SwitchManaged.SwitchRoutingVLANEnhancement.PRJCBUGEN_T17724;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesSwitchIpSettingsPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String                      vlanName                    = "testvlan";
    String                      vlanId                      = "724";
    String                      networkName                 = "1";
    DevicesSwitchIpSettingsPage devicesSwitchIpSettingsPage = null;
    boolean                     needRestore                 = false;

    @Feature("Switch.SwitchRoutingVLANEnhancement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17724") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("005-Change Management VLAN mode from Static to DHCP on device IP Setting page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17724") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
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
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Device Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddpmg.gotoPage();
    }

    @Step("Test Step 2: Make sure Management VLAN mode is Static")
    public void step2() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(4);
        netsp.setNetwork4();
        netsp.finishAllStep();
        needRestore = true;
    }

    @Step("Test Step 3: Go to Device Ip change mode from 'Static' to 'DHCP';")
    public void step3() {
        ddpmg.gotoPage();
        ddpmg.enterDevice(WebportalParam.sw1serialNo);
        devicesSwitchIpSettingsPage = new DevicesSwitchIpSettingsPage();
        devicesSwitchIpSettingsPage.setIpToDhcp();

        ddpmg.gotoPage();
        ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
        String sStaticIpWP = ddpmg.getDeviceIP(WebportalParam.sw1serialNo);
        assertTrue(sStaticIpWP.equals(WebportalParam.sw1IPaddress),
                String.format("check ip is expected on wp: %s/%s", WebportalParam.sw1IPaddress, sStaticIpWP));
        assertTrue(SwitchCLIUtils.getVlan1Ip().contains(WebportalParam.sw1IPaddress),
                String.format("check ip is expected on cli: %s/%s", WebportalParam.sw1IPaddress, sStaticIpWP));
    }
}
