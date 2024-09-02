package webportal.BR.BRVlan.PRJCBUGEN_T7180;

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
import util.BRUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "2180";
    String sPort    = "2";
    String sExpect  = vlanId + "t";

    @Feature("BR.BRVlan") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7180") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("002-Display VLAN info on BR device page by IM APP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7180") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wvp.gotoPage();
        wvp.deleteVlan(vlanName, false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.editVlanPorts("1", WebportalParam.br1deveiceName, "", "", sPort, true);
    }

    @Step("Test Step 2: IM APP go to Wired Setting page, create some VLANs")
    public void step2() {
        wvp.newVlan(vlanName, vlanId, 0);
        wvp.editVlanPorts(vlanId, WebportalParam.br1deveiceName, "", sPort, "", true);
    }

    @Step("Test Step 3: IM APP go to BR device page, check 'VLANs In Use';")
    public void step3() {
        handle.openOneBRDevice(true);
        brdvp.gotoPage();
        assertTrue(brdvp.getVlans().contains(vlanName));
    }

    @Step("Test Step 4: Go to BR GUI and check VLAN config;")
    public void step4() {
        handle.waitRestReady(BRUtils.api_lan_port_stats, sExpect, false, 0);
        assertTrue(new BRUtils().Dump().contains(sExpect), "vlan is not created on device");
    }

    @Step("Test Step 5: Delete one VLAN by IM APP;")
    public void step5() {
        wvp.gotoPage();
        wvp.deleteVlan(vlanId, true);
    }

    @Step("Test Step 6: Delete VLAN success;")
    public void step6() {
        handle.waitRestReady(BRUtils.api_lan_port_stats, sExpect, true, 0);
        assertFalse(new BRUtils().Dump().contains(sExpect), "vlan is not removed on device");
    }

}
