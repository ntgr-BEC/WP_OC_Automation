package webportal.Switch.VLAN.PRJCBUGEN_T4705;

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
    String         vlanId = "300";
    
    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4705") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("027-change port pvid") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4705") // It's a testcase id/link from Jira Test Case.
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
    
    @Step("Test Step 2: Create vlan 100/300，add port 1 as untag to vlan100，port 2 as tagged to vlan100/vlan300.")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        MyCommonAPIs.sleep(8000);
        
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addCustomVlanWithPorts("vlan100", "100", dut1Name, sw1port2, "untag", null, null, null, null);
        vlanPage.editVlanWithPorts("vlan100", "100", "vlan100", dut1Name, sw1port3, "tag", null, null, null, null);
        
        vlanPage.addCustomVlanWithPorts("vlan300", "300", dut1Name, sw1port2, "untag", null, null, null, null);
        vlanPage.editVlanWithPorts("vlan300", "300", "vlan300", dut1Name, sw1port3, "tag", null, null, null, null);
        
        WiredGroupPortConfigPage wiredGroupPortConfigPage2 = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage2.multiSetting(SWITCH1_PORTARRAY2, BATTCHSETTING3);
        
        WiredVLANPage wiredVLANPage1 = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage3 = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage3.multiSetting(SWITCH1_PORTARRAY3, BATTCHSETTING4);
        MyCommonAPIs.sleep(8000);
        handle.waitCmdReady("vlan300", false);
    }
    
    @Step("Test Step 3: check cli")
    public void step3() {
        String port1 = WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[0]);
        String port2 = WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[1]);
        String result1 = MyCommonAPIs.getCmdOutput("show running-config interface " + port1, false).toLowerCase();
        if (!SwitchCLIUtils.isTagPort(port1, vlanId) && result1.contains("100")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1, 1 cli is:" + result1);
        }
        
        result1 = MyCommonAPIs.getCmdOutput("show running-config interface " + port2, false).toLowerCase();
        if (SwitchCLIUtils.isTagPort(port2, vlanId) && result1.contains("100")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100, 2 cli is:" + result1);
        }
        
        String result2 = MyCommonAPIs.getCmdOutput("show running-config interface " + port1, false).toLowerCase();
        if (result2.contains("300")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1, 1 cli is:" + result1);
        }
        
        result1 = MyCommonAPIs.getCmdOutput("show running-config interface " + port2, false).toLowerCase();
        if (result1.contains("300")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100, 2 cli is:" + result1);
        }
    }
    
    @Step("Test Step 4: Check configuration on webportal")
    public void step4() {
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        List<String> vlans = vlanPage.getVlans();
        MyCommonAPIs.sleep(3000);
        
        System.out.println("vlan is:" + vlans);
        if (vlans.contains("vlan100") && vlans.contains("vlan300")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 4: vlan100 vlan300 is show:" + vlans);
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
