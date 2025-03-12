package webportal.ProManagedSwitch.VLAN.PRJCBUGEN_T4703;

import static org.testng.Assert.assertFalse;
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
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 * @author sumanta
 */
public class Testcase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    
    String lagName    = "testlag4703";
    String vlanName   = "testvlan";
    String vlanId     = "100";
    String lagId      = "1";
    int    portIndex  = 4;
    int    portIndex1 = 5;
    
    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    
    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4703") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("020-ADD lag port to vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4703") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        portIndex = Integer.parseInt(WebportalParam.sw1LagPort1);
        portIndex1 = Integer.parseInt(WebportalParam.sw1LagPort2);
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        wlp.gotoLagPage();
        wlp.deleteLag();
        MyCommonAPIs.sleepi(4);
        wlp.deleteLagCli();
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: DUT1 port 1 conenct to DUT2 port 1")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Connect two DUT with two ports")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        MyCommonAPIs.sleep(8000);
        wlp.gotoLagPage();
        wlp.addLag(lagName, true, false);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​Create VLAN 100, and select one lag member port, set 'Access Port' mode to the VLAN;")
    public void step3() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.addLagPortToVlan(false);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: All four lag member ports should be select at the same time;\n"
            + "Check the LAG should be untaged to VLAN100 via web portal and Web GUI;")
    public void step4() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.showPortMember();
        assertTrue(wvp.isPortsSelected(portIndex), "all 4 ports are selected with after clicked any one");
        assertTrue(wvp.isPortsSelected(portIndex1), "all 5 ports are selected with after clicked any one");
        
        assertFalse(wvp.isPortsTrunk(portIndex), "all 4 ports are untagged");
        assertFalse(wvp.isPortsTrunk(portIndex1), "all 5 ports are untagged");
        wvp.btncancelVlan.click();
        MyCommonAPIs.sleepi(1);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: web portal go to VLAN 100 configure page, select one lag member port and\n" + "change 'Trunk Port' mode to the VLAN;")
    public void step5() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.showPortMember();
        wvp.selectPort(portIndex, true, 0);
        wvp.btnsaveVlan.click();
        MyCommonAPIs.waitReady();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 6: Check the LAG should be taged to VLAN 100 via web portal and Web GUI;")
    public void step6() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.showPortMember();
        assertTrue(wvp.isPortsTrunk(portIndex), "all 4 ports are tagged");
        assertTrue(wvp.isPortsTrunk(portIndex1), "all 5 ports are tagged");
    }
    
}
