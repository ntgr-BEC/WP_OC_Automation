package webportal.SwitchManaged.VLAN.PRJCBUGEN_T4711;

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
    int            length;
    String         vlanId = "200";

    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4711") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("014-Add and delete all ports to a special VLAN") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4711") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        length = WebportalParam.getSwitchPortNumber() - 1;
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

    @Step("Test Step 2: Create VLAN 100, click 'Select All' and then click 'Deselect All', set to 'Trunk Port'.")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY5, BATTCHSETTING2);
        MyCommonAPIs.sleep(8000);

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addCustomVlanWithPorts("vlan200", "200", dut1Name, sw1port2, "tag", null, null, null, null);

        MyCommonAPIs.sleep(10000);
        handle.waitCmdReady("vlan200", false);

        if (!SwitchCLIUtils.isTagPort("g1", vlanId)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1 for first port");
        }

        if (!SwitchCLIUtils.isTagPort("g" + length, vlanId)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1 for last port");
        }
    }

    @Step("Test Step 3: Web Portal go to VLAN100 configure page and click 'Select All', then set to 'Access Port', save;")
    public void step3() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.editVlanWithPorts("vlan200", "200", "vlan200", dut1Name, sw1port3, "untag", null, null, null, null);
        
        handle.waitCmdReady("vlan200", false);
        MyCommonAPIs.sleep(5000);

        String result1 = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false)
                .toLowerCase();
        if (!SwitchCLIUtils.isTagPort("g1", vlanId) && result1.contains("200")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1 for first port, 1 cli is:" + result1);
        }

        String result2 = MyCommonAPIs
                .getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, length), false)
                .toLowerCase();
        if (!SwitchCLIUtils.isTagPort("g" + length, vlanId) && result2.contains("200")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1 for last port, 1 cli is:" + result2);
        }
    }

    @Step("Test Step 4: Web Portal go to VLAN100 configure page and click 'Select All', then set to 'Trunk Port', save;")
    public void step4() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.editVlanWithPorts("vlan200", "200", "vlan200", dut1Name, sw1port3, "tag", null, null, null, null);
        handle.waitCmdReady("vlan200", false);
        MyCommonAPIs.sleep(5000);

        String result1 = MyCommonAPIs
                .getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1), false).toLowerCase();
        if (SwitchCLIUtils.isTagPort("g1", vlanId) && result1.contains("200")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1 for first port, 1 cli is:" + result1);
        }

        String result2 = MyCommonAPIs
                .getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, length), false)
                .toLowerCase();
        if (SwitchCLIUtils.isTagPort("g" + length, vlanId) && result2.contains("200")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 100 on dut1 for last port, 1 cli is:" + result2);
        }
    }

    @Step("Test Step 5: Web Portal go to VLAN100 configure page and click 'Select All', then set to 'Delete', save")
    public void step5() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.editVlanWithPorts("vlan200", "200", "vlan200", dut1Name, sw1port3, "delete", null, null, null, null);
        MyCommonAPIs.sleep(5000);

        String checkPort = "g1";
//        removed Not on if condition because tagged and untagged for include and tagged and untagged is for exclude
        if (SwitchCLIUtils.isTagPort(checkPort, vlanId) && !SwitchCLIUtils.isPortInVlan(checkPort, "200")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 200 on dut1 for first port");
        }

        checkPort = "g" + length;
        if (SwitchCLIUtils.isTagPort(checkPort, vlanId) && !SwitchCLIUtils.isPortInVlan(checkPort, "200")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 2 Fail:show vlan 200 on dut1 for last port");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();

        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY5, BATTCHSETTING1);
    }

}
