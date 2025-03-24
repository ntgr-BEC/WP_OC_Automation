package webportal.ProManagedSwitch.LAG.PRJCBUGEN_T41068;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;

/**
 * @author Sumanta
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    
    String lagName    = "testlag1068";
    String vlanName   = "testvlan";
    String vlanId     = "865";
    String lagId      = "1";
    int    portIndex  = 4;
    int    portIndex1 = 5;
    
    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    
    @Feature("Switch.LAG") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41068") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("009-verify set lag access/trunk/delete for a special VLAN") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41068") // It's a testcase id/link from Jira Test Case.
    @Issue("PRJCBUGEN-19122")
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        portIndex = Integer.parseInt(WebportalParam.sw1LagPort1);
        portIndex1 = Integer.parseInt(WebportalParam.sw1LagPort2);
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wlp.gotoLagPage();
        wlp.deleteLag();
        MyCommonAPIs.sleepi(4);
        wlp.deleteLagCli();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: DUT1 port 1 conenct to DUT2 port 1")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        
        WiredGroupPortConfigPage wgpcp = new WiredGroupPortConfigPage();
        wgpcp.enableLagPort();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Connect two DUT with two ports")
    public void step2() {
        wlp.gotoLagPage();
        wlp.addLag(lagName, true, false);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​Create VLAN 100, and select one lag member port, set 'Access Port' mode to the VLAN;")
    public void step3() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.addLagPortToVlan(false);
        handle.waitCmdReady(lagName, false);
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
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: web portal go to VLAN 100 configure page, select one lag member port and\n" + "change 'Trunk Port' mode to the VLAN;")
    public void step5() {
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
        assertTrue(wvp.isPortsTrunk(portIndex), "all 4 ports are trunked");
        assertTrue(wvp.isPortsTrunk(portIndex1), "all 5 ports are trunked");
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 7: web portal go to VLAN 100 configure page, select one lag member port and click 'Delete' button,")
    public void step7() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.showPortMember();
        wvp.removeAllPort(false);
        wvp.btnsaveVlan.click();
        MyCommonAPIs.waitReady();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 8: Check the LAG should be removed from VLAN 100;")
    public void step8() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.showPortMember();
        assertFalse(wvp.isPortsTrunk(portIndex), "all 4 ports are untagged");
        assertFalse(wvp.isPortsTrunk(portIndex1), "all 5 ports are untagged");
        assertFalse(wvp.isPortsSelected(portIndex), "all 4 ports are not selected");
        assertFalse(wvp.isPortsSelected(portIndex1), "all 5 ports are not selected");
    }
    
}
