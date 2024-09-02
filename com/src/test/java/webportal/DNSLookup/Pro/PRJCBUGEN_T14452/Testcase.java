package webportal.DNSLookup.Pro.PRJCBUGEN_T14452;

import static org.testng.Assert.assertFalse;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.NetworkTroubleshootPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author xuchen
 *
 */
public class Testcase extends TestCaseBase {
    String domain = "www.bing.com";
    String tmpStr;
    public static NetworkTroubleshootPage troubleshoot = new NetworkTroubleshootPage(false);
    

    @Feature("DNSLookup") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14452") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify what user type can perform the DNS Lookup test") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14452") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: login as owner to check Troubleshoot")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.ownerName,WebportalParam.loginPassword);


        handle.gotoLoction();
        assertFalse(troubleshoot.troubleshootlink.exists(), "dns lookup should not be exist on owner");
    }

    @Step("Test Step 2: login as readManager to check Troubleshoot")
    public void step2() {
        UserManage userManage = new UserManage();
        userManage.logout();
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.readManagerName,WebportalParam.loginPassword);


        handle.gotoLoction();
        assertFalse(troubleshoot.troubleshootlink.exists(), "dns lookup should not be exist on readManager");

    }



}
