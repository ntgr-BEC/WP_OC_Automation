package webportal.SwitchManaged.VLAN.PRJCBUGEN_T4701;

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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 * @author xuchen
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;
    String         vlanId = "100";
    
    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4701") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("028-config vlan for two switch at the same time") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4701") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3")
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
        
        wvp.deleteAllVlan();
        wvp.deleteAllVlanCli();
    }
    
    @Step("Test Step 2: Create vlan 100，add port 1 as untag to vlan100，port 2 as tagged to vlan100.")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        MyCommonAPIs.sleep(8000);
        
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addCustomVlanWithPorts("vlan100", "100", dut1Name, sw1port2, "untag", dut2Name, sw2port2, "untag", null);
        
        MyCommonAPIs.sleep(8000);
        handle.waitCmdReady("vlan100", false);
    }
    
    @Step("Test Step 3: check cli")
    public void step3() {
        String port1 = WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[0]);
        String port2 = WebportalParam.getSwitchPort(WebportalParam.sw2Model, sw1port[1]);

        String result1 = MyCommonAPIs.getCmdOutput("show running-config interface " + port1, false).toLowerCase();
        if (!SwitchCLIUtils.isTagPort(port1, vlanId) && result1.contains("100")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1, 1 cli is:" + result1);
        }
        
        result1 = MyCommonAPIs.getCmdOutput("show running-config interface " + port2, false).toLowerCase();
        if (!SwitchCLIUtils.isTagPort(port2, vlanId) && result1.contains("100")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1, 2 cli is:" + result1);
        }
    }
    
    @Step("Test Step 4: Check configuration on webportal")
    public void step4() {
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        List<String> vlans = vlanPage.getVlans();
        MyCommonAPIs.sleep(3000);
        
        System.out.println("vlan is:" + vlans);
        if (vlans.contains("vlan100")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 4: vlan100 is show:" + vlans);
        }
    }
    
    @Step("Test Step 5: delete vlan 100.")
    public void step5() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
        MyCommonAPIs.sleepsync();
        MyCommonAPIs.sleepsync();
        
        String result1 = handle.getCmdOutputShowRunningConfig(false);
        if (!result1.contains("vlan100")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 3 Fail:show vlan 100 on dut1, 1 cli is:" + result1);
        }
        
        String result21 = handle.getCmdOutputShowRunningConfig(true);
        if (!result21.contains("vlan100")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 3 Fail:show vlan 100 on dut2, 2 cli is:" + result1);
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
