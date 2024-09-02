package webportal.SwitchManaged.LAG.PRJCBUGEN_T4827;

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

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String lagName   = "testlag4827";
    String vlanName  = "testvlan827";
    String vlanId    = "827";
    String lagId     = "1";
    int    portIndex = 3;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";

    @Feature("Switch.LAG") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4827") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("001-Create LAG with 1 menbership between two DUTS") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4827") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        portIndex = Integer.parseInt(WebportalParam.sw1LagPort1);
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wvp.deleteAllVlan();
        wlp.gotoLagPage();
        wlp.deleteLag();
//        wlp.deleteLagCli();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: DUT1 port 1 conenct to DUT2 port 1")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wlp.gotoLagPage();
        wlp.deleteLag();
        wlp.deleteLagCli();

        WiredGroupPortConfigPage wgpcp = new WiredGroupPortConfigPage();
        wgpcp.enableLagPort();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Configure LAG with 1 member port on 2 DUTs")
    public void step2() {
        wlp.gotoLagPage();
        wlp.addLag(lagName, false, false);
        MyCommonAPIs.waitCliReady("show running-config", lagName, false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​Create VLAN 10, add the LAG and port connected to Tester as its member port")
    public void step3() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.addLagPortToVlan(false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: lag group id,lan menber, lag admin mode are right")
    public void step4() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.showPortMember();
        assertTrue(wvp.isPortsLag(portIndex), "all ports are labelled with L1");

        wlp.gotoLagPage();
        assertTrue(wlp.getLagPortNo().equals("2"), "the number of port in LAG");
        // assertTrue(wlp.getLagID().equals(lagId), "the id in LAG");
        lagId = wlp.getLagID();
        assertTrue(wlp.isPortsEnable(portIndex), "all ports in this lag must be active");

        wlp.gotoLagEditPage();
        assertTrue(wlp.getAdminMode(), "admin should be enabled");
        assertTrue(wlp.getLcapMode(), "type should be enabled");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: lag group id,lan menber, lag admin mode are right-CLI")
    public void step5() {
        assertTrue(wvp.checkVlanLag(vlanId, lagId, false), "check lag 1 is created in vlan");
        assertTrue(wlp.checkLagCli(lagName, portIndex, true, true), "check lag 1 is created on channel");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 6: Shutdown/no shutdown lag")
    public void step6() {
        String sRet;
        wlp.gotoLagPage();
        for (int i = 0; i < 3; i++) {
            System.out.println("start to check on loop of: " + i);
            wlp.gotoLagEditPage();
            wlp.shutdownLag(true);
            // sRet = handle.waitCmdReady("no adminmode", false);
            MyCommonAPIs.sleepsync();
            assertTrue(wlp.checkLag(lagId, portIndex, false, true), "check lag 1 is disabled");

            // BUG: cannot shutdown/noshutdown on this page
            wlp.cancelLagButton.click();
            wlp.gotoLagPage();
            MyCommonAPIs.waitReady();

            wlp.gotoLagEditPage();
            wlp.shutdownLag(false);
            // sRet = handle.waitCmdReady("no adminmode", true);
            MyCommonAPIs.sleepsync();
            assertTrue(wlp.checkLagCli(lagName, portIndex, true, true), "check lag 1 is enabled");

            // BUG: cannot shutdown/noshutdown on this page
            wlp.cancelLagButton.click();
            MyCommonAPIs.waitReady();
        }
    }

}
