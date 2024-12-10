package webportal.SwitchManaged.VLAN.PRJCBUGEN_T4728;

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
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 * @author xuchen
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;

    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4728") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("055-Create Private VLAN via template of Guest+Employee Network") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4728") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, enabled = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        if (WebportalParam.isRltkSW1)
            throw new RuntimeException("not support for GS switch");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        
    }

    @Step("Test Step 2: Set VLAN Name to Guest+Employee Network")
    public void step2() {
        // WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        // WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        // WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        // wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        // MyCommonAPIs.sleep(8000);

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addGuestEmplyeeVlanWithPorts("private1000", "1000", dut1Name, sw1port2, sw1port3, sw1port4, null, null, null, null, null, null);
        handle.waitCmdReady("private1000", false);
    }

    @Step("Test Step 3: Check configuration on webportal")
    public void step3() {
        // check sw1 on webportal
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        List<String> vlans = vlanPage.getVlans();
        MyCommonAPIs.sleep(3000);

        System.out.println("vlan is:" + vlans);
        if (vlans.contains("private1000")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 1:vlanId is:" + vlans);
        }
        if (vlans.contains("vlan_3098_staff")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 1:2 vlanId is:" + vlans);
        }
        if (vlans.contains("vlan_3099_guest")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 1:3 vlanId is:" + vlans);
        }
    }

    @Step("Test Step 4: Check configuration on CLI")
    public void step4() {
        // check on dut CLI
        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, false);
        String result1 = switchTelnet.getCLICommand("show running-config");
        System.out.println(result1);
        if (result1.contains("vlan 1000") && result1.contains("private-vlan primary") && result1.contains("private-vlan association 3098-3099")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 2 Fail:vlan 1000 on dut1, 1 cli is:" + result1);
        }
        if (result1.contains("vlan 3098") && result1.contains("private-vlan community")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 2 Fail:vlan 3098 on dut1, 2 cli is:" + result1);
        }
        if (result1.contains("vlan 3099") && result1.contains("private-vlan isolated")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 2 Fail:vlan 3099 on dut1, 2 cli is:" + result1);
        }

        String result2 = switchTelnet
                .getCLICommand("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[0]));
        System.out.println(result2);
        if (result2.contains("switchport mode private-vlan promiscuous") && result2.contains("switchport private-vlan mapping 1000 3098-3099")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 3 Fail:show running-config interface g4, 1 cli is:" + result2);
        }

        String result3 = switchTelnet
                .getCLICommand("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[1]));
        System.out.println(result3);
        if (result3.contains("switchport mode private-vlan host") && result3.contains("switchport private-vlan host-association 1000 3098")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 4 Fail:show running-config interface g5, 1 cli is:" + result3);
        }

        String result4 = switchTelnet
                .getCLICommand("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[2]));
        System.out.println(result4);
        if (result4.contains("switchport mode private-vlan host") && result4.contains("switchport private-vlan host-association 1000 3099")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 5 Fail:show running-config interface g6, 1 cli is:" + result4);
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
