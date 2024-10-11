package webportal.SwitchManaged.VLANEnhancements.PRJCBUGEN_T32024;

import static org.testng.Assert.assertTrue;

import java.util.List;

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
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;


/**
 * @author annfang
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result      = true;
    String         vlanId      = "25";
    String         vlanName    = "testvlan25";
    String         networkName = "testnet" + vlanId;
    String         vlanId2      = "35";
    String         vlanName2    = "testvlan35";
    String         networkName2 = "testnet" + vlanId2;

    @Feature("Switch.VLANEnhancements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32024") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Webportal : Verify whether user able to edit the VLAN Description using Network Setting flow and Wired Setting Flow") // It's a testcase title from Jira Test
                                                                                                            // Case.
    @TmsLink("PRJCBUGEN-T32024") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to IM-6.9 Webportal ")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: In location Dashboard , click on the Location Icon --> VLAN/Network page")
    public void step2() {
        // Create vlan 25 if it's not exist
        netsp.gotoPage();
        if (!netsp.getNetworks().contains(vlanName)) {
            netsp.createNetwork(networkName, 0, vlanName, vlanId);
        }
        // Check cli
        String tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(vlanId), "SW1 vlan should be created on switch");
        tmpStr = handle.getCmdOutputShowRunningConfig(true);
        assertTrue(tmpStr.contains(vlanId), "SW2 vlan should be created on switch");
    }

    @Step("Test Step 3: Edit the VLAN 25")
    public void step3() {
        netsp.openNetwork(vlanName);
        netsp.setNetwork1(vlanName2, null, 99, vlanName2, vlanId2);
        netsp.finishAllStep();
    }

    @Step("Test Step 4: Change the VLAN ID from 25 to 35 and click on save")
    public void step4() {
        String tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(vlanId2), "SW1 vlan should be created on switch");
        tmpStr = handle.getCmdOutputShowRunningConfig(true);
        assertTrue(tmpStr.contains(vlanId2), "SW2 vlan should be created on switch");
    }
    

    @AfterMethod(alwaysRun = true)
    public void restore() {
        // Restore
        netsp.gotoPage();
        if (netsp.getNetworks().contains(vlanName)) {
            netsp.deleteNetwork(vlanName);
        }
        if (netsp.getNetworks().contains(vlanName2)) {
            netsp.deleteNetwork(vlanName2);
        }
    }

}
