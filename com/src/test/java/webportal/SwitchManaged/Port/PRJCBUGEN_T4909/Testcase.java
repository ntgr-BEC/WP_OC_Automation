package webportal.SwitchManaged.Port.PRJCBUGEN_T4909;

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

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {
    String vlan1   = "vlan1";
    String vlanId1 = "901";
    String vlan2   = "vlan2";
    String vlanId2 = "903";
    String vlan3   = "vlan3";
    String vlanId3 = "905";
    String portsId = "3,4,5";
    
    
    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4909") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("036-Assign tag ports to vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4909") // It's a testcase id/link from Jira Test Case.

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
    }

    @Step("Test Step 2: Create vlanId1, vlanId2 and vlanId3")
    public void step2() {
        wvp.gotoPage();
        wvp.newVlan(vlan1, vlanId1, 0);
        wvp.newVlan(vlan2, vlanId2, 0);
        wvp.newVlan(vlan3, vlanId3, 0);
    }

    @Step("Test Step 3: Go to Networks/Wired Settings/Vlan and assign port3,4,5 as tag to vlanId1,vlanId2 and vlanId3 ")
    public void step3() {
        wvp.editVlanPorts(vlanId1, WebportalParam.sw1deveiceName, "", portsId, "", false);
        wvp.editVlanPorts(vlanId2, WebportalParam.sw1deveiceName, "", portsId, "", false);
        wvp.editVlanPorts(vlanId3, WebportalParam.sw1deveiceName, "", portsId, "", false);
    }

    @Step("Test Step 4: Check vlanId1,vlanId2 and vlanId3 member from gui and app")
    public void step4() {
        MyCommonAPIs.sleepi(20);
        handle.waitCmdReady(vlan3, false);

//        assertTrue(SwitchCLIUtils.isTagPort("g3", vlanId1), "g3 is not added to vlan1 on switch");     //this method has to be changed as per the implementation of M350
//        assertTrue(SwitchCLIUtils.isTagPort("g3", vlanId2), "g3 is not added to vlan2 on switch");
//        assertTrue(SwitchCLIUtils.isTagPort("g3", vlanId3), "g3 is not added to vlan3 on switch");
//
//        assertTrue(SwitchCLIUtils.isTagPort("g4", vlanId2), "g4 is not added to vlan2 on switch");
//        assertTrue(SwitchCLIUtils.isTagPort("g5", vlanId3), "g5 is not added to vlan3 on switch");
        
        String a1 = SwitchCLIUtils.isTagPort1("g3", vlanId1);
        String a2 = SwitchCLIUtils.isTagPort1("g3", vlanId2);
        String a3 = SwitchCLIUtils.isTagPort1("g3", vlanId3);
        
        String a4 = SwitchCLIUtils.isTagPort1("g4", vlanId2);
        String a5 = SwitchCLIUtils.isTagPort1("g5", vlanId3);
        
       assertTrue(a1.contains("Include") && a1.contains("Tagged"), "g3 is not added to vlan1 on switch");
       assertTrue(a2.contains("Include") && a2.contains("Tagged"), "g3 is not added to vlan2 on switch");
       assertTrue(a3.contains("Include") && a3.contains("Tagged"), "g3 is not added to vlan3 on switch");
       assertTrue(a4.contains("Include") && a4.contains("Tagged"), "g4 is not added to vlan2 on switch");
       assertTrue(a5.contains("Include") && a5.contains("Tagged"), "g5 is not added to vlan3 on switch");
       
        
    }

    @Step("Test Step 5: Remvoe port 3,4,5 from vlanId1")
    public void step5() {
        wvp.gotoPage();
        wvp.editVlanPorts(vlanId1, WebportalParam.sw1deveiceName, "", "", portsId, false);
    }

    @Step("Test Step 6: Check vlanId1,vlanId2 and vlanId3 member from gui and app")
    public void step6() {
        MyCommonAPIs.sleepi(20);

//        assertTrue(!SwitchCLIUtils.isTagPort("g3", vlanId1), "g3 is not remoted to vlan1 on switch");   //  Tag the ports to VLAN the o/p will be Include tagged 
//        assertTrue(!SwitchCLIUtils.isTagPort("g4", vlanId1), "g4 is not remoted to vlan1 on switch");  //    then if you untag the ports o/p will be Exclude tagged
//        assertTrue(!SwitchCLIUtils.isTagPort("g5", vlanId1), "g5 is not remoted to vlan1 on switch");
//
//        assertTrue(SwitchCLIUtils.isTagPort("g4", vlanId2), "g2 is added to vlan2 on switch");
//        assertTrue(SwitchCLIUtils.isTagPort("g5", vlanId3), "g3 is added to vlan3 on switch");
        
        String a1 = SwitchCLIUtils.isTagPort1("g3", vlanId1);
        String a2 = SwitchCLIUtils.isTagPort1("g4", vlanId1);
        String a3 = SwitchCLIUtils.isTagPort1("g5", vlanId1);
        String a4 = SwitchCLIUtils.isTagPort1("g4", vlanId2);
        String a5 = SwitchCLIUtils.isTagPort1("g5", vlanId3);
        assertTrue(a1.contains("Exclude") && a1.contains("Tagged"), "g3 is not remoted to vlan1 on switch");
        assertTrue(a2.contains("Exclude") && a2.contains("Tagged"), "g4 is not remoted to vlan1 on switch");
        assertTrue(a3.contains("Exclude") && a3.contains("Tagged"), "g5 is not remoted to vlan1 on switch");
        assertTrue(a4.contains("Include") && a4.contains("Tagged"), "g4 is added to vlan2 on switch");
        assertTrue(a5.contains("Include") && a5.contains("Tagged"), "g5 is added to vlan3 on switch");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        wvp.gotoPage();
        wvp.deleteAllVlan();
    }
}
