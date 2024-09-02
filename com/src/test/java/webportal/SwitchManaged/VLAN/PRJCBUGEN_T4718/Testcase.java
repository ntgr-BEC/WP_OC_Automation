package webportal.SwitchManaged.VLAN.PRJCBUGEN_T4718;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtilsMNG;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 *
 * @author xuchen
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;

    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4718") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("006-Check LLDP-MED Voice VLAN config via default template by Voice VLAN") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4718") // It's a testcase id/link from Jira Test Case.
    @Issue("PRJCBUGEN-15237")
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

    @Step("Test Step 2: Web Portal go to Wired configuration page, create VLAN via default template of Voice VLAN")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        MyCommonAPIs.sleep(8000);

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addVoiceVlanWithPorts("Voice VLAN", "4088", null, null, null, null, null, null, null);
        MyCommonAPIs.sleep(20000);
    }

    @Step("Test Step 3: Check configuration on webportal")
    public void step3() {
        // check sw1 on webportal
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        List<String> vlans = vlanPage.getVlans();
        MyCommonAPIs.sleep(3000);

        System.out.println("vlan is:" + vlans);
        if (vlans.contains("Voice VLAN")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 1:Voice VLAN is:" + vlans);
        }
    }

    @Step("Test Step 4: Check configuration on CLI")
    public void step4() {
        // check on dut CLI
        boolean result2 = SwitchCLIUtilsMNG.checkVoiceVlan();
        if (result2) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show auto-voip, cli is:" + result2);
        }

        /*String result1 = SwitchCLIUtilsMNG.getVoiceVlan();
        System.out.println(result1);
        if (result1.contains("4088")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 2 Fail:show voice vlan interface all, 1 cli is:" + result1);
        }*/
        
        String result1 = SwitchCLIUtilsMNG.getVoiceVlan("4088");
        System.out.println(result1);       
        if (result1.contains("4088")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 2 Fail:show voice vlan interface all, 1 cli is:" + result1);
        }
    }

     @Step("Test Step 5: Web Portal go to Voice VLAN configure page and change ID to 1234;")
     public void step5() {
    // WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
    // WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
    // vlanPage.editVlanWithPorts("Voice VLAN", "1234", "Voice VLAN", null, null, null, null, null, null, null);
    // MyCommonAPIs.sleep(20000);
    //
    // boolean result2 = SwitchCLIUtilsMNG.checkVoiceVlan();
    // if (result2) {
    // micResult = true;
    // } else {
    // micResult = false;
    // assertTrue(micResult, "----Check Point 3 Fail:show auto-voip, cli is:" + result2);
    // }
    //
    // String result1 = SwitchCLIUtilsMNG.getVoiceVlan();
    // System.out.println(result1);
    // if (result1.contains("1234")) {
    // micResult = true;
    // } else {
    // micResult = false;
    // assertTrue(micResult, "----Check Point 4 Fail:show voice vlan interface all, 1 cli is:" + result1);
    // }
     }
    
     @Step("Test Step 6: Web Portal delete Voice VLAN, and re-create a new Voice VLAN with ID is 3000")
     public void step6() {
    // WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
    // WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
    // vlanPage.deleteAllVlan();
    //
    // vlanPage.addVoiceVlanWithPorts("Voice VLAN", "3000", null, null, null, null, null, null, null);
    // MyCommonAPIs.sleep(25000);
    //
    // boolean result2 = SwitchCLIUtilsMNG.checkVoiceVlan();
    // if (result2) {
    // micResult = true;
    // } else {
    // micResult = false;
    // assertTrue(micResult, "----Check Point 5 Fail:show auto-voip, cli is:" + result2);
    // }
    //
    // String result1 = SwitchCLIUtilsMNG.getVoiceVlan();
    // System.out.println(result1);
    // if (result1.contains("3000")) {
    // micResult = true;
    // } else {
    // micResult = false;
    // assertTrue(micResult, "----Check Point 6 Fail:show voice vlan interface all, 1 cli is:" + result1);
//     }
     }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
    }

}
