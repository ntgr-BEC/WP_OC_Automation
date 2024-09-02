package webportal.BR.BRIPSecVpn.PRJCBUGEN_T13647;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tocheck = null;
    
    @Feature("BR.BRIPSecVpn") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13647") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("031-GUI gateway policy:Local and remote are same") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13647") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        bripsvp.gotoPage();
        bripsvp.deletePolicyNames();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM APP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        ddp.gotoPage();
        ddp.openBR1();
    }
    
    @Step("Test Step 2: Create a vpn policy,config local subnet same as remote subnet")
    public void step2() {
        bripsvp.testData.policyName = bripsvp.testData.policyName + testcaseId;
        bripsvp.gotoPage();
        bripsvp.testData.remoteLannet = "192.168.168.0";
        bripsvp.testData.localLannet = "192.168.168.0";
        tocheck = bripsvp.addPolicy();
    }
    
    @Step("Test Step 3: can't config,give warn message")
    public void step3() {
        assertTrue(tocheck.length() > 10, "Local subnet IP address and remote subnet IP address cannot be the same.");
    }
}
