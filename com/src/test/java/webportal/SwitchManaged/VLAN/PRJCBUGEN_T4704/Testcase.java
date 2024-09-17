package webportal.SwitchManaged.VLAN.PRJCBUGEN_T4704;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 *
 * @author xuchen
 *
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String        vlanName = "testvlan";
    String        vlanId   = "704";

    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4704") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("026-Config a vlan when DUT is out of line") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4704") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        SwitchCLIUtils.CloudModeSet(true);
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
    }

    @Step("Test Step 1: Put DUT out of internet")
    public void step1() {
        SwitchCLIUtils.CloudModeSet(false);
        
        SwitchCLIUtils.CloudModeSet(true);
        handle.sleepi(5 * 60);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: ADD vlan 100 and not apply")
    public void step2() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
    }


    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Check vlan 100 on switch")
    public void step3() {
        String sRet = handle.waitCmdReady(vlanId, false);
        System.out.println(sRet);
        assertTrue(sRet.contains(vlanId), "check for " + vlanId);
    }

}
