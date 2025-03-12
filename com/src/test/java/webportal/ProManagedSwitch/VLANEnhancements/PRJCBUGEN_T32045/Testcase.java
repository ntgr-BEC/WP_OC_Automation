package webportal.ProManagedSwitch.VLANEnhancements.PRJCBUGEN_T32045;

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
 * @author sumanta
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result      = true;
    String         vlanId      = "25";
    String         vlanName    = "testvlan25";
    String         networkName = "testnet" + vlanId;
    String         vlanId2      = "35";
    String         vlanName2    = "testvlan35";
    String         vlanDesc2    = "desc 35";
    String         networkName2 = "testnet" + vlanId2;
    
    String         vlanId3      = "27";
    String         vlanName3    = "testvlan27";
    String         networkName3 = "testnet" + vlanId3;
    String         vlanId4      = "37";
    String         vlanName4    = "testvlan37";
    String         vlanDesc4    = "desc 37";
    String         networkName4 = "testnet" + vlanId4;

    @Feature("Switch.VLANEnhancements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32045") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether VLAN ID and description configured are taken effect in Webportal") // It's a testcase title from Jira Test
                                                                                                            // Case.
    @TmsLink("PRJCBUGEN-T32045") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to IM-6.9 Webportal ")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go-to locations --> Wired -->  Settings --> VLAN/Network page")
    public void step2() {
        // Create vlan 25 if it's not exist
        netsp.gotoPage();
        if (!netsp.getNetworks().contains(vlanName)) {
            netsp.createNetwork(networkName, 0, vlanName, vlanId);
        }
        // Create vlan 27 if it's not exist
        netsp.gotoPage();
        if (!netsp.getNetworks().contains(vlanName3)) {
            netsp.createNetwork(networkName3, 0, vlanName3, vlanId3);
        }
        // Check cli
        String tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(vlanId), "SW1 vlan25 should be created on switch");
        tmpStr = handle.getCmdOutputShowRunningConfig(true);
        assertTrue(tmpStr.contains(vlanId), "SW2 vlan25 should be created on switch");
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(vlanId3), "SW1 vlan27 should be created on switch");
        tmpStr = handle.getCmdOutputShowRunningConfig(true);
        assertTrue(tmpStr.contains(vlanId3), "SW2 vlan27 should be created on switch");
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
    
    @Step("Test Step 5: Edit the VLAN 27\r\n" + 
            "Change the VLAN ID from 27 to 37\r\n" + 
            " Configure description")
    public void step5() {
        netsp.openNetwork(vlanName3);
        netsp.setNetwork1(vlanName4, "changed desc", 99, vlanName4, vlanId4);
        netsp.finishAllStep();
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
        if (netsp.getNetworks().contains(vlanName3)) {
            netsp.deleteNetwork(vlanName3);
        }
        if (netsp.getNetworks().contains(vlanName4)) {
            netsp.deleteNetwork(vlanName4);
        }
    }

}
