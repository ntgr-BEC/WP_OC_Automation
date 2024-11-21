package webportal.SwitchManaged.VLAN.PRJCBUGEN_T4713;

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
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 *
 * @author xuchen
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;

    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4713") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("016-Verify protocol-based VoIP function via default template of Voice VLAN") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4713") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
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
//        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
//        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
//        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
//        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
//        MyCommonAPIs.sleep(8000);

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
        
        List<String> VLAnIDS= new WiredVLANPage().getVlanIDs();  
        MyCommonAPIs.sleep(3000);
        System.out.println(VLAnIDS);

        System.out.println("vlan is:" + vlans);
        if (vlans.contains("Voice VLAN") || VLAnIDS.contains("4088")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 1:Voice VLAN is:" + vlans);
        }
    }

    @Step("Test Step 4: Check configuration on CLI")
    public void step4() {
        // check on dut CLI
        MyCommonAPIs.sleepsync();
        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, false);
        String result2 = switchTelnet.sendCLICommand("show run", null);
        System.out.println(result2);
        if (result2.toLowerCase().contains("voice vlan") || (result2.toLowerCase().contains("auto-voip")) || (result2.contains("4088"))) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 2 Fail:show auto-voip, cli is:" + result2);
        }

//        String result1 = SwitchCLIUtils.getVoiceInfo(0, 0);
//        System.out.println(result1);
//        if (result1.contains("7")) {
//            micResult = true;
//        } else {
//            micResult = false;
//            assertTrue(micResult, "----Check Point 3 Fail:show auto-voip protocol-based interface all, 1 cli is:" + result1);
//        }
//
//        result1 = SwitchCLIUtils.getVoiceInfo(0, 1);
//        if (result1.toLowerCase().contains("enabled")) {
//            micResult = true;
//        } else {
//            micResult = false;
//            assertTrue(micResult, "----Check Point 4 Fail:show auto-voip protocol-based interface all, 2 cli is:" + result1);
//       }    
//        String result1 = SwitchCLIUtils.getVoiceVlan();
        if (result2.toLowerCase().contains("voice-vlan") &&  (result2.toLowerCase().contains("voip"))) {
            micResult = true;
        }else if(result2.toLowerCase().contains("auto-voip protocol-based") &&  (result2.toLowerCase().contains("auto-voip oui-based"))) {
            micResult = true;
        }
        else {
            if ((WebportalParam.sw1Model)=="XS516TM")
                micResult = true;
            else {
                micResult = false;
                assertTrue(micResult, "----Check Point 5 Fail:show auto-voip protocol-based interface all, 3 cli is:" + result2);
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
//        vlanPage.deleteAllVlan();
        vlanPage.deleteVlan("4088");
    }
    

}
