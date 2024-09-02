package webportal.BR.BRIPManagement.PRJCBUGEN_T7187;

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
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String sExpect = "192.168.3.223";
    
    @Feature("BR.BRIPManagement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7187") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("003-Set invalid IP address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7187") // It's a testcase id/link from Jira Test Case.
    @Issue("PRJCBUGEN-12656")
    
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        if (!WebportalParam.skipIssueCheck)
            throw new RuntimeException("PRJCBUGEN-12656");
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.openOneBRDevice(true);
    }
    
    @Step("Test Step 2:  set invalid IP address and gateway")
    public void step2() {
        brdwip.gotoPage();
        brdwip.setDHCP(false);
        sExpect = "192.168.1.256";
        brdwip.txtIp.setValue(sExpect);
        assertTrue(handle.getPageErrorMsg().contains("enter a valid"), "ip can be: " + sExpect);
        sExpect = "192.168.1.0";
        brdwip.txtIp.setValue(sExpect);
        assertTrue(handle.getPageErrorMsg().contains("enter a valid"), "ip can be: " + sExpect);
        sExpect = "192.168.1.255";
        brdwip.txtIp.setValue(sExpect);
        assertTrue(handle.getPageErrorMsg().contains("enter a valid"), "ip can be: " + sExpect);
        sExpect = "192.168.1.-1";
        brdwip.txtIp.setValue(sExpect);
        assertTrue(handle.getPageErrorMsg().contains("enter a valid"), "ip can be: " + sExpect);
        sExpect = "192.168.256.10";
        brdwip.txtIp.setValue(sExpect);
        assertTrue(handle.getPageErrorMsg().contains("enter a valid"), "ip can be: " + sExpect);
        sExpect = "192.256.1.10";
        brdwip.txtIp.setValue(sExpect);
        assertTrue(handle.getPageErrorMsg().contains("enter a valid"), "ip can be: " + sExpect);
        sExpect = "a.b.c.d";
        brdwip.txtIp.setValue(sExpect);
        assertTrue(handle.getPageErrorMsg().contains("enter a valid"), "ip can be: " + sExpect);
    }
}
