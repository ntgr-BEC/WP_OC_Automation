package webportal.AP.WiredVLANSupportWAC540564.PRJCBUGEN_T16601;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1555";
    String vlanNameMod = vlanName + vlanId;
    String networkName = "testnet";
    String ports       = "2";
    
    @Feature("AP.WiredVLANSupportWAC540564") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16601") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the max Vlan support for WAC540/564") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16601") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p5")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        netsp.deleteAllNetwork();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: create a vlan")
    public void step2() {
        netsp.gotoPage();
        int idBase = (int) (Math.random() * 1000);
        int curLen = netsp.getNetworks().size();
        while ((32 - curLen) > 0) {
            curLen++;
            vlanId = String.format("%s", idBase + curLen);
            netsp.createNetwork(networkName + vlanId, vlanId, WebportalParam.ap1deveiceName, ports, 1);
        }
    }

    @Step("Test Step 3: user should be able to view the same on the device UI as well")
    public void step3() {
        APUtils ap = new APUtils(WebportalParam.ap1IPaddress);
        ap.waitConfigReady(vlanId, false);
        
        String apConf = ap.getConfig(String.format("vlanID %s", vlanId));
        assertTrue(apConf.contains(vlanId), "The last vlan id must be on ap:" + vlanId);
    }
}
