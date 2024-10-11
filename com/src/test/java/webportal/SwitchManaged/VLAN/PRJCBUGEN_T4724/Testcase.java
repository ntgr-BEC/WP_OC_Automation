package webportal.SwitchManaged.VLAN.PRJCBUGEN_T4724;

import static org.testng.Assert.assertTrue;

import java.util.List;

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
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.DevicesSwitchVLANsInUsePage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 * @author xuchen
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;
    int            length;
    String         vlanId = "1";
    
    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4724") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("051-Check default VLAN configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4724") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3")
    public void test() throws Exception {
        length = WebportalParam.getSwitchPortNumber();
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
    
    @Step("Test Step 2: default VLAN")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        
        DevicesSwitchVLANsInUsePage devicesSwitchVLANsInUsePage = new DevicesSwitchVLANsInUsePage();
        
        List<String> vlans = devicesSwitchVLANsInUsePage.getVlans();
        MyCommonAPIs.sleep(3000);
        
        System.out.println("vlan is:" + vlans);
        if (vlans.contains("Management VLAN")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 1:vlanId is:" + vlans);
        }
    }
    
    @Step("Test Step 3: Check configuration on CLI")
    public void step3() {
        // check on dut CLI
        String result1 = MyCommonAPIs
                .getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false).toLowerCase();
        if (WebportalParam.sw1Model.contains("M4250")) {
        if (!SwitchCLIUtils.isTagPort("0/1", vlanId) && result1.contains("1")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1 for first port, 1 cli is:" + result1);
        }
        }else if (WebportalParam.sw1Model.contains("M4350")) {
            System.out.println(result1.contains("1"));
            if (!SwitchCLIUtils.isTagPort("1/0/1", vlanId) && result1.contains("1")) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1 for first port, 1 cli is:" + result1);
            }
        }
        
        String result2 = MyCommonAPIs
                .getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, length), false)
                .toLowerCase();
        if (WebportalParam.sw1Model.contains("M4250")) {
        if (!SwitchCLIUtils.isTagPort("0/" + length, vlanId) && result2.contains("1")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1 for last port, 1 cli is:" + result2);
        }
    }else if (WebportalParam.sw1Model.contains("M4350")) {
        if (!SwitchCLIUtils.isTagPort("1/0/" + length, vlanId) && result2.contains("1")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1 for last port, 1 cli is:" + result2);
            }
        }
    }
    
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
    }
    
}
