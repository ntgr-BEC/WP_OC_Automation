package webportal.SwitchManaged.VLAN.PRJCBUGEN_T4715;

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
    String         vlanId = "4089";
    
    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4715") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("003-Create a vlan via the template of Video VLAN") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4715") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
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
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
        
        netsp.gotoPage();
        netsp.createNetwork("Video VLAN", 2, "", "4089");
    }
    
    @Step("Test Step 2: Web Portal check default VLAN configuration")
    public void step2() {
        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, false);
        String result2 = "";
        if (WebportalParam.isRltkSW1) {
            result2 = switchTelnet.getCLICommand("show running-config");
        } else {
            result2 = switchTelnet.getCLICommand("show vlan 4089");
        }
        
        System.out.println(result2);
        if (result2.contains("4089")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 4089, cli is:" + result2);
        }
        
//        if (!WebportalParam.isRltkSW1) {
//            result2 = switchTelnet.getCLICommand("show vlan 4088");
//        }
//        System.out.println(result2);
//        if (result2.contains("4088")) {
//            micResult = true;
//        } else {
//            micResult = false;
//            assertTrue(micResult, "----Check Point 2 Fail:show vlan 4088, cli is:" + result2);
//        }

}
    
//    @Step("Test Step 3: Web Portal delete Video VLAN, video vlan should not be deleted")
//    public void step3() {
//        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
//        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
//        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
//        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
//        MyCommonAPIs.sleep(8000);
//        
//        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
//        vlanPage.deleteVlan("4089");
//        
//        if (!WebportalParam.sw1Model.contains("GS716") && !WebportalParam.sw1Model.contains("MS510")
//                && !WebportalParam.sw1Model.contains("GS724")) {
//            SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, false);
//            String result2 = "";
//            if (WebportalParam.isRltkSW1) {
//                result2 = switchTelnet.getCLICommand("show running-config");
//            } else {
//                result2 = switchTelnet.getCLICommand("show vlan 4089");
//            }
//            
//            System.out.println(result2);
//            if (result2.contains("4089")) {
//                micResult = true;
//            } else {
//                micResult = false;
//                assertTrue(micResult, "----Check Point 2 Fail:show vlan 4089, cli is:" + result2);
//            }
//        }
//    }
    
    @Step("Test Step 4: Web Portal create Video VLAN via the template of Video VLAN with ports")
    public void step4() {
        
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteVlan("4089");
        vlanPage.addVideoVlanWithPorts("Video VLAN", "4089", dut1Name, sw1port, "tag", null, null, null, "true");
        MyCommonAPIs.sleepsync();
        handle.waitCmdReady("4089", false);
    }
    
    @Step("Test Step 5: Check configuration on webportal")
    public void step5() {
        // check sw1 on webportal
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        List<String> vlans = vlanPage.getVlans();
        MyCommonAPIs.sleep(3000);
        
        List<String> VLAnIDS= new WiredVLANPage().getVlanIDs();  
        MyCommonAPIs.sleep(3000);
       System.out.println(VLAnIDS);
       
        System.out.println("vlan is:" + vlans);
        if ((vlans.contains("Video VLAN")) || (VLAnIDS.contains("4089"))) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 2:Video VLAN is:" + vlans);
        }
    }
    
    @Step("Test Step 6: Check configuration on CLI")
    public void step6() {
        // check on dut CLI
        String port1 = WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[0]);
        String port2 = WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[1]);
        String result1 = MyCommonAPIs.getCmdOutput("show running-config interface " + port1, false).toLowerCase();
        if (SwitchCLIUtils.isTagPort(port1, vlanId) && result1.contains("4089")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 4089 on dut1, 1 cli is:" + result1);
        }
        
        String result2 = MyCommonAPIs.getCmdOutput("show running-config interface " + port2, false).toLowerCase();
        if (SwitchCLIUtils.isTagPort(port2, vlanId) && result2.contains("4089")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan 4089 on dut1, 1 cli is:" + result2);
        }
    }
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteVlan("4089");
        
        vlanPage.addVideoVlanWithPorts("Video VLAN", "4089", null, null, null, null, null, null, null);
    }
    
}
