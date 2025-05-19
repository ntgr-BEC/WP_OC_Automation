package webportal.SwitchManaged.CFD_VLAN.PRJCBUGEN_T47656;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import webportal.SwitchManaged.CFD_VLAN.PRJCBUGEN_T41621.Config;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.*;

/**
 * @author Tejeshwinin K V
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;
    String         vlanId = "100";
    String         vlanName = "vlan100";
    
    @Feature("Switch.CFD_VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41656") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Onboard Smart switch in Day-0 to insight location with VLAN configurations configured for the switches") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41656") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Page ")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        wvp.gotoPage();
        wvp.deleteAllVlan();

    }

    @Step("Test Step 2: Reset the switch ")
    public void step2() {
        new DevicesDashPage().restoreDevice(WebportalParam.sw1serialNo);
    }

    @Step("Test Step 3: Create vlan 100 add port 1 as untag to vlan100，port 2 as tagged to vlan100.")
    public void step3() {

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addCustomVlanWithPorts("vlan100", "100", dut1Name, sw1port, "tag", null, null, null, null);

        MyCommonAPIs.sleep(8000);
        handle.waitCmdReady("vlan100", false);
    }


    
    @Step("Test Step 4:  onboard swaitch")
    public void step4() {

        WebCheck.checkHrefIcon(URLParam.hrefDevices);

        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.sw1serialNo);
        devInfo.put("MAC Address1", WebportalParam.sw1MacAddress);

        new DevicesDashPage(false).addNewDevice(devInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.sw1serialNo);

    }

    @Step("Test Step 5:  onboard swaitch")
    public void step5() {
        String port1 = WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[0]);
        String result1 = MyCommonAPIs.getCmdOutput("show running-config interface " + port1, false).toLowerCase();
        if (!SwitchCLIUtils.isTagPort(port1, vlanId) && result1.contains("100")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1, 1 cli is:" + result1);
        }
    }

    
}
