package webportal.Switch.Routing.PRJCBUGEN_T6220;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "110";
    String ipMask   = "255.255.255.0";
    String ip1      = "220.1.1.1";
    String ip2      = "220.1.1.2";
    
    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6220") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("005-Add gateway to a known ip address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6220") // It's a testcase id/link from Jira Test Case.
    
    @Test(enabled = false, alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wvp.gotoPage();
        wvp.deleteAllVlan();
        SwitchCLIUtils.cleanIpRouter();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create a new vlan 100")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        
        new AccountPage().enterEditNetworkPage();
        rtp.gotoPage();
        SwitchCLIUtils.cleanIpRouter();
    }
    
    @Step("Test Step 2: Add gateway to a known ip address")
    public void step2() {
        rtp.setGateway(ip1);
        handle.refresh();
        assertTrue(MyCommonAPIs.getValue(rtp.sGateWay).equals(ip1), "check gateway");
    }
    
    @Step("Test Step 3: Check default gateway from app and switch web gui")
    public void step3() {
        handle.waitCmdReady(ip1, false);
        String tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(ip1), "check gateway");
    }
}
