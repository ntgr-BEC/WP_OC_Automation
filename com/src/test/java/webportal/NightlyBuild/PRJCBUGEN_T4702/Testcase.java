package webportal.NightlyBuild.PRJCBUGEN_T4702;

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
 * @author Tejeshwini K V
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;
    String         vlanId = "100";
    
    @Feature("NightlyBuild") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4701") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Edit vlan") // It's a testcase title from Jira Test Case.
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
    }
    
    @Step("Test Step 2: Create vlan 100，add port 1 as untag to vlan100，port 2 as tagged to vlan100.")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);

        
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addCustomVlanWithPorts("vlan100", "100", null, null, null, null, null, null, null);
        System.out.println("now edit vlan");
//        vlanPage.editVlanWithPorts("vlan120", "100", "vlan100", dut1Name, sw1port3, "tag", null, null, null, null);

    }
    
 
    
    @Step("Test Step 3: Check configuration on webportal")
    public void step3() {
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
    
    @Step("Test Step 4: delete vlan 100.")
    public void step4() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
       
    }
    
   
    
}
