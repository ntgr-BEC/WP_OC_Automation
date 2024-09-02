package webportal.Switch.LAG.PRJCBUGEN_T4829;

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
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String lagName    = "testlag4829";
    String vlanName   = "testvlan829";
    String vlanId     = "829";
    String lagId      = "1";
    int    portIndex  = 4;
    int    portIndex1 = 5;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";

    @Feature("Switch.LAG") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4829") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("003-Create LAG with LACP mode between two DUTs") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4829") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        portIndex = Integer.parseInt(WebportalParam.sw1LagPort1);
        portIndex1 = Integer.parseInt(WebportalParam.sw1LagPort2);
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        handle.getCmdOutputShowRunningConfig(false);
        handle.getCmdOutputShowRunningConfig(true);
        wlp.gotoLagPage();
        wlp.deleteLag();
//        MyCommonAPIs.sleepi(4);
//        wlp.deleteLagCli();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: DUT1 port 1 conenct to DUT2 port 1")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        WiredGroupPortConfigPage wgpcp = new WiredGroupPortConfigPage();
        wgpcp.enableLagPort();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Connect two DUT with two ports")
    public void step2() {
        wlp.gotoLagPage();
        wlp.bEnableStatic = false;
        wlp.addLag(lagName, true, false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​Create VLAN 100, Connect two DUT with two ports\n"
            + "Create LAG 1 and 'Static LAG' mode select disable, LAG member select two ports")
    public void step3() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.addLagPortToVlan(false);
        MyCommonAPIs.sleepsync();
        handle.refresh();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: lag group id,lan menber, lag admin mode are right")
    public void step4() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.showPortMember();
        assertTrue(wvp.isPortsLag(portIndex), "all 4 ports are labelled with L1");
        assertTrue(wvp.isPortsLag(portIndex1), "all 5 ports are labelled with L1");

        wlp.gotoLagPage();
        assertTrue(wlp.getLagPortNo().equals("4"), "the number of port in LAG");
        // assertTrue(wlp.getLagID().equals(lagId), "the id in LAG");
        lagId = wlp.getLagID();
        System.out.println("lagId= " + lagId);
        assertTrue(wlp.isPortsEnable(portIndex), "all 4 ports in this lag must be active");
        assertTrue(wlp.isPortsEnable(portIndex1), "all 5 ports in this lag must be active");

        wlp.gotoLagEditPage();
        assertTrue(wlp.getAdminMode(), "admin should be enabled");
        assertFalse(wlp.getLcapMode(), "type should be static");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: lag group id,lan menber, lag admin mode are right-CLI")
    public void step5() {
        handle.sleepi(5);
        assertTrue(wvp.checkVlanLag(vlanId, lagId, false), "check lag 1 is created in vlan");
        assertTrue(wlp.checkLagCli(lagName, portIndex, true, false), "check lag 1 is created on channel for 4");
        String sRet = MyCommonAPIs.getCmdOutput(
                "show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, WebportalParam.sw1LagPort2), false);
        assertTrue(sRet.contains(" lag "), "check lag 1 is created on channel for 5");
    }
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteVlan(vlanId);
    }

}
