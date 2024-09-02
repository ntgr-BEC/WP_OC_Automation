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
import util.SwitchCLIUtilsMNG;
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
    String portsId = "1,2,3";

    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4909") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("036-Assign port to vlan") // It's a testcase title from Jira Test Case.
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

    @Step("Test Step 2: Create vlan 5,6,7")
    public void step2() {
        wvp.gotoPage();
        wvp.newVlan(vlan1, vlanId1, 0);
        wvp.newVlan(vlan2, vlanId2, 0);
        wvp.newVlan(vlan3, vlanId3, 0);
    }

    @Step("Test Step 3: Go to Networks/Wired Settings/Vlan and assign port1,2,3 to vlan 5,6,7")
    public void step3() {
        wvp.editVlanPorts(vlanId1, WebportalParam.sw1deveiceName, "", portsId, "", false);
        wvp.editVlanPorts(vlanId2, WebportalParam.sw1deveiceName, "", portsId, "", false);
        wvp.editVlanPorts(vlanId3, WebportalParam.sw1deveiceName, "", portsId, "", false);
    }

    @Step("Test Step 4: Check vlan 5,6,7 member from gui and app")
    public void step4() {
        handle.waitCmdReady(vlan3, false);

        assertTrue(SwitchCLIUtilsMNG.isPortInVlan("g1", vlanId1), "g1 is not added to vlan1 on switch");
        assertTrue(SwitchCLIUtilsMNG.isPortInVlan("g1", vlanId2), "g1 is not added to vlan2 on switch");
        assertTrue(SwitchCLIUtilsMNG.isPortInVlan("g1", vlanId3), "g1 is not added to vlan3 on switch");

        assertTrue(SwitchCLIUtilsMNG.isPortInVlan("g2", vlanId2), "g2 is not added to vlan2 on switch");
        assertTrue(SwitchCLIUtilsMNG.isPortInVlan("g3", vlanId3), "g3 is not added to vlan3 on switch");
    }

    @Step("Test Step 5: Remvoe port 1,2,3 from vlan 5")
    public void step5() {
        wvp.gotoPage();
        wvp.editVlanPorts(vlanId1, WebportalParam.sw1deveiceName, "", "", portsId, false);
    }

    @Step("Test Step 6: Check vlan 5,6,7 member from gui and app")
    public void step6() {
        handle.sleepsync();

        assertTrue(!SwitchCLIUtilsMNG.isPortInVlan("g1", vlanId1), "g1 is not remoted to vlan1 on switch");
        assertTrue(!SwitchCLIUtilsMNG.isPortInVlan("g2", vlanId1), "g2 is not remoted to vlan1 on switch");

        assertTrue(SwitchCLIUtilsMNG.isPortInVlan("g2", vlanId2), "g2 is added to vlan2 on switch");
        assertTrue(SwitchCLIUtilsMNG.isPortInVlan("g3", vlanId3), "g3 is added to vlan3 on switch");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        wvp.gotoPage();
        wvp.deleteAllVlan();
    }
}
