package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11332;

import static org.testng.Assert.assertNotNull;
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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanId      = "1332";
    String networkName = "testnet" + vlanId;
    String vlanName    = "testvlan";
    String newNetName  = "name Aa-10 1234567890 1234567890";
    String newNetDesc  = "desc Aa-10 1234567890 1234567890";
    String newvlanName = "vlan Aa-10 1234567890 1234567890";
    String newNetName2 = "nm";
    String newNetDesc2 = "dc";
    
    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11332") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007-Create a network and change its setting") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11332") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Create vlan 200 from Gui,and set vlan name as vlan200.")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
    }
    
    @Step("Test Step 3: change network name and description to 32 alphanumeric characters including alphabets, numbers, hyphens and spaces")
    public void step3() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.setNetwork1(newNetName, newNetDesc, 0, vlanName, vlanId);
        netsp.finishAllStep();
    }
    
    @Step("Test Step 4: Config successfully")
    public void step4() {
        netsp.gotoPage();
        assertTrue(netsp.getNetworks().contains(newNetDesc), "network is modified on wp");
    }
    
    @Step("Test Step 5: change vlan 200 name to 32 alphanumeric characters including alphabets, numbers, hyphens and spaces")
    public void step5() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.setNetwork1(newNetName, newNetDesc, 0, newvlanName, vlanId);
        netsp.finishAllStep();
    }
    
    @Step("Test Step 6: Config successfully")
    public void step6() {
        MyCommonAPIs.waitCliReady("show vlan " + vlanId, newvlanName, false);
        assertTrue(MyCommonAPIs.getCmdOutput("show vlan " + vlanId, false).contains(newvlanName), "vlan name is changed");
    }
    
    @Step("Test Step 7: Change network 200's name/description to shortest with 2 characters by APP")
    public void step7() {
        netsp.gotoPage();
        netsp.openNetwork(newvlanName);
        netsp.setNetwork1(newNetName2, newNetDesc2, 0, newvlanName, vlanId);
        netsp.finishAllStep();
    }
    
    @Step("Test Step 8: Check vlan 200 from APP and GUI after saving")
    public void step8() {
        netsp.gotoPage();
        assertTrue(netsp.getNetworks().contains(newNetDesc2), "network2 is modified on wp");
    }
    
//    @Step("Test Step 9: From APP change vlan 200's name to 32 characters include unallowed characters eg,!@#$%^&*()")
//    public void step9() {
//        netsp.gotoPage();
//        netsp.openNetwork(newvlanName);
//        netsp.txtvlanName.setValue("!@#$%^&*()");
//        netsp.clickNextOrSkip(false);
//        assertTrue(handle.getPageErrorMsg().length() > 0, "lphabets, numbers, underscores, hyphens and spaces");
//    }
    
    // @Step("Test Step 10: From APP change vlan name to one/none character")
    // public void step10() {
    // netsp.gotoPage();
    // netsp.openNetwork(newNetName2);
    // netsp.txtvlanName.setValue("a");
    // netsp.clickNextOrSkip(false);
    // assertTrue(handle.getPageErrorMsg().length() > 0, "lphabets, numbers, underscores, hyphens and spaces");
    // }
    
//    @Step("Test Step 11: APP not allow change VLAN ID")
//    public void step11() {
//        assertNotNull(netsp.txtvlanId.getAttribute("readonly"), "check vlan id is not editable");
//    }
}
