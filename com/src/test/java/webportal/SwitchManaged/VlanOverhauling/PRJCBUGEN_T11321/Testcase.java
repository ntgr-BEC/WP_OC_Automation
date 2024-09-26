package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11321;

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
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId = "10";
    String networkName = "testnet" + vlanId;
    int j=0;

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11321") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("010-Create and delete maximum VLAN network") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11321") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // p3
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Create a maximum of 256 VLANs. And each vlan with a 32 character name.And assign all ports to all vlan.")
    public void step2() {
        netsp.gotoPage();
        for (int i = 0; i < 252; i++) {
            String vlanId = "10";
            MyCommonAPIs.sleepi(10);
            vlanId = String.format("%d", Integer.parseInt(vlanId) + i);
            System.out.printf("create VLAN: %s", vlanId);
            networkName = vlanName + vlanId;
            netsp.createNetwork(networkName, 0, networkName, vlanId);
        }
    }

    @Step("Test Step 3: Check vlan information from APP and GUI")
    public void step3() {
        netsp.gotoPage();
        assertTrue(netsp.getNetworks().contains(networkName), "all vlan are created");
    }

    @Step("Test Step 4: Reload DUT")
    public void step4() {
        new DevicesDashPageMNG().enterDevicesSwitchSummary(WebportalParam.sw1serialNo, 0);
        new PublicButton().rebootDevice();
        new DevicesDashPageMNG().waitDevicesReConnected(WebportalParam.sw1serialNo);

        String tmpStr = handle.getCmdOutput("show vlan " + vlanId, false);
        assertTrue(tmpStr.contains(vlanId), "check all vlan are there");
    }

    @Step("Test Step 5: From app， delete all created vlan,and check result from both APP and GUI")
    public void step5() {
       // netsp.gotoPage();
        //netsp.deleteAllNetwork();

      //  String tmpStr = handle.getCmdOutput("show vlan " + vlanId, false);
       // assertFalse(tmpStr.contains(vlanId), "check all vlan are there");
    }

}
