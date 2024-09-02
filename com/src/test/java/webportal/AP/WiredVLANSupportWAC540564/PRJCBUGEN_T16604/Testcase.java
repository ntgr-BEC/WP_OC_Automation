package webportal.AP.WiredVLANSupportWAC540564.PRJCBUGEN_T16604;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1603";
    String networkName = "testnet";
    String ports       = "2";
    
    @Feature("AP.WiredVLANSupportWAC540564") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16604") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify whether user is able to change the port configs for Management Network") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16604") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        netsp.openNetwork("1");
        netsp.setPortMembers(WebportalParam.ap1deveiceName, ports, 0);
        netsp.finishAllStep();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Created a network location and added a few AP models ")
    public void step2() {
        netsp.gotoPage();
        netsp.openNetwork("1");
    }

    @Step("Test Step 3: Delete a port from vlan 1")
    public void step3() {
        netsp.gotoStep(2);
        netsp.setPortMembers(WebportalParam.ap1deveiceName, ports, 2);
        netsp.finishAllStep();
        MyCommonAPIs.sleepsync();
    }
    
    @Step("Test Step 4: user should be able to view the same on the device UI as well")
    public void step4() {
        APUtils ap = new APUtils(WebportalParam.ap1IPaddress);
        
        String apConf = ap.getConfig("vlanProfile0:");
        assertTrue(apConf.contains(String.format("lan%s:member 1", ports)), "Port must be a member of vlan1 -- cannot delete");
    }
}
