package webportal.SwitchManaged.VLAN.PRJCBUGEN_T4717;

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
    String         vlanId = "200";
    String         prefixport = "";

    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4717") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("008-Assign ports as tagged/untagged to a vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4717") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        if(WebportalParam.sw1Model.contains("M250")) {
            prefixport = "0/";
        } else {
            prefixport = "1/0/"; 
        }

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

    @Step("Test Step 2: Create vlan 200 from Web GUI, add a untag and tag port into this vlan")
    public void step2() {
        String port1 = WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[0]);
        String port2 = WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[1]);

        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addCustomVlanWithPorts("vlan200", "200", dut1Name, sw1port2, "untag", null, null, null, null);
        vlanPage.editVlanWithPorts("vlan200", "200", "vlan200", dut1Name, sw1port3, "tag", null, null, null, null);

        WiredGroupPortConfigPage wiredGroupPortConfigPage2 = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage2.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING3);
        handle.waitCmdReady("vlan 200", false);
        MyCommonAPIs.sleepsync();

        String result1 = MyCommonAPIs.getCmdOutput("show running-config interface " + port1, false).toLowerCase();
        String result2 = MyCommonAPIs.getCmdOutput("show running-config interface " + port2, false).toLowerCase();
        if (!SwitchCLIUtils.isTagPort(port1, vlanId) && result1.contains("200")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 200 on dut1, 1 cli is:" + result1);
        }

        if (SwitchCLIUtils.isTagPort(port2, vlanId) && result2.contains("200")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 2 Fail:show vlan 200 on dut1, 1 cli is:" + result2);
        }
    }

    @Step("Test Step 3: Web Portal change vlan 200 name to 32 alphanumeric")
    public void step3() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.editVlanWithPorts("vlan200", "200", "vlan200", dut1Name, sw1port4, "untag", null, null, null, null);
        MyCommonAPIs.sleepsync();

        String result2 = MyCommonAPIs
                .getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[1]), false)
                .toLowerCase();
        if (!SwitchCLIUtils.isTagPort(prefixport + sw1port[1], vlanId) && result2.contains("200")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 3 Fail:show vlan 200 on dut1, cli is:" + result2);
        }
    }

    @Step("Test Step 4: Check configuration on webportal")
    public void step4() {
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        List<String> vlans = vlanPage.getVlans();
        MyCommonAPIs.sleep(3000);

        System.out.println("vlan is:" + vlans);
        if (vlans.contains("vlan200")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 4: vlan200 is show:" + vlans);
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
