package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11318;

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
import util.SwitchCLIUtils;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1318";
    String networkName = "testnet" + vlanId;

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11318") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("026-Config a vlan when DUT is out of line") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11318") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        SwitchCLIUtils.SwitchOfflineOnline("Connect");         //will make switch online
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        SwitchCLIUtils.SwitchOfflineOnline("Disconnect");     //will make switch offline
//        SwitchCLIUtils.CloudModeSet(false);
//        handle.sleepi(5 * 60);
//        SwitchCLIUtils.CloudModeSet(true);
     
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: ADD vlan 100 and not apply")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
    }

    @Step("Test Step 3: Get DUT connected to inernet")
    public void step3() {
       
        handle.waitCmdReady(vlanId, false);
    }

    @Step("Test Step 4: Check vlan 100 on switch")
    public void step4() {
        String tmpStr = handle.getCmdOutput("show vlan "+vlanId, false);
        assertFalse(tmpStr.contains(vlanId) , "vlan should be cached during offline");
        assertTrue(tmpStr.contains("VLAN does not exist.") , "vlan should be cached during offline");
    }
}
