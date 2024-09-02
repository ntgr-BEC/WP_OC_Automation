package webportal.AP.WiredVLANSupportWAC540564.PRJCBUGEN_T16553;

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
    String vlanId      = "1553";
    String networkName = "testnet";
    String ports       = "2";

    @Feature("AP.WiredVLANSupportWAC540564") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16553") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify whether user is able to create a vlan without selecting any port") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16553") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
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
    
    @Step("Test Step 2: without selecting any port for AP")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
    }
    
    @Step("Test Step 3: user should be able to view the same on the device UI as well")
    public void step3() {
        APUtils ap = new APUtils(WebportalParam.ap1IPaddress);
        ap.waitConfigReady(vlanId, false);

        String apConf = ap.getConfig(String.format("vlanID %s", vlanId));
        assertTrue(apConf.contains(vlanId), "vlan id must be on ap:" + vlanId);
    }
}
