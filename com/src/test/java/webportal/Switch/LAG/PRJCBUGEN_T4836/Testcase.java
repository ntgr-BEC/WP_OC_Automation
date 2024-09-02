package webportal.Switch.LAG.PRJCBUGEN_T4836;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    
    String lagName   = "testlag4836";
    String vlanName  = "testvlan";
    String vlanId    = "836";
    String lagId     = "1";
    int    portIndex = 4;
    
    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    
    @Feature("Switch.LAG") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4836") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("010-Create a lag and name it as a used name") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4836") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        portIndex = Integer.parseInt(WebportalParam.sw1LagPort1);
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wlp.gotoLagPage();
        wlp.deleteLag();
        wlp.deleteLagCli();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: DUT1 port 1 conenct to DUT2 port 1")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        
        WiredGroupPortConfigPage wgpcp = new WiredGroupPortConfigPage();
        wgpcp.enableLagPort();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Configure LAG with 1 member port on 2 DUTs")
    public void step2() {
        wlp.gotoLagPage();
        wlp.addLag(lagName, false, false);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​Create a second lag and name it as lag1")
    public void step3() {
        wlp.gotoLagPage();
        wlp.addLag(lagName, false, true);
        
        String s = handle.getPageErrorMsg();
        assertTrue(s.contains("already exist"), "Lag group already exists.");
        handle.clickButton(1);
    }
    
}
