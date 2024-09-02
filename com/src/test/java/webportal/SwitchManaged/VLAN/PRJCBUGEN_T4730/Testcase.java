package webportal.SwitchManaged.VLAN.PRJCBUGEN_T4730;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtilsMNG;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author xuchen
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result   = true;
    public String  lagName  = "lag4730";
    public String  vlanName = "vlan4730";
    public String  vlanId   = "1730";
    
    @Issue("PRJCBUGEN-11343")
    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4730") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("057-Add lag port to Private vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4730") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, enabled = false, groups = "p3")
    public void test() throws Exception {
        if (!WebportalParam.isRltkSW1) {
            runTest(this);
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create a lag and a private vlan 100 from APP.")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        wlp.gotoLagPage();
        wlp.addLag(lagName, false, false);
    }
    
    @Step("Test Step 2: Add LAG port as public,employee,guest port.")
    public void step2() {
        wvp.gotoPage();
        wvp.newPrivateVlan(vlanName, vlanId, true);
    }
    
    @Step("Test Step 3: Check the configuration from Web Portal and Web GUI")
    public void step3() {
        handle.waitCmdReady(vlanName, false);
        String lagPort = WebportalParam.getSwitchLag(false, false);
        String sta = SwitchCLIUtilsMNG.getPortInfo(lagPort);
        assertTrue(SwitchCLIUtilsMNG.isLagPort(lagPort), "Port must be lag");
        assertTrue(sta.contains("private-vlan"), "Port must be belong to private-vlan");
        assertTrue(sta.contains("3098"), "Port must be belong to staff");
        assertTrue(sta.contains("3099"), "Port must be belong to guest");
    }
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
        wvp.gotoPage();
        wvp.deleteAllVlan();
    }
    
}
