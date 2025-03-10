package webportal.ProManagedSwitch.Routing.PRJCBUGEN_T6232;

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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author Sumanta
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "132";
    String gateway1 = "132.1.1.1";
    String gateway2 = "132.1.1.2";
    
    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6232") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("017-Modify gateway") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6232") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        SwitchCLIUtils.cleanIpRouter();
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wvp.gotoPage();
        wvp.deleteAllVlan();
        SwitchCLIUtils.cleanIpRouter();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create a new vlan 100")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 0);
        
        new AccountPage().enterEditNetworkPage();
        rtp.gotoPage();
    }
    
    @Step("Test Step 2: Add gateway to a known IP1")
    public void step2() {
        rtp.setGateway(gateway1);
    }
    
    @Step("Test Step 3: Check configuraiton from app and local web gui")
    public void step3() {
        handle.waitCmdReady(gateway1, false);
        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config", false);
        assertTrue(tmpStr.contains(gateway1), "verify gw1 is set");
    }
    
    @Step("Test Step 4: Modify default gateway to IP2 and check")
    public void step4() {
        rtp.setGateway(gateway2);
        handle.waitCmdReady(gateway2, false);
        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config", false);
        assertTrue(tmpStr.contains(gateway2), "verify gw2 is set");
    }
}
