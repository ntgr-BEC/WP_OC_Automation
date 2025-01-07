package webportal.DNSLookup.Pro.PRJCBUGEN_T14507;

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
    @Story("PRJCBUGEN_T14507") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the functionality of Search button") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14507") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Insight discover and manage one ap")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);


        handle.gotoLoction();
        new NetworkTroubleshootPage();
    }

    @Step("Test Step 2: Insight go to troubleshoot to run test;")
    public void step2() {
        int devicesize = troubleshoot.setSearch(WebportalParam.ap1serialNo);
        int temp = 1;
        System.out.println(devicesize=temp);
        assertTrue(devicesize==temp, "dns lookup search result is: " + devicesize);
        troubleshoot.closePopup();

    }



}
