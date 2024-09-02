package webportal.BR.BRVPNSite2Site.PRJCBUGEN_T7207;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String groupName = "test7207vpn";

    @Feature("BR.BRVPNSite2Site") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7207") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("008-Create maximum VPN Groups ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7207") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p5") // "p3"
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        brdvgp.gotoPage();
        brdvgp.deleteAllGroups();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.openOneBRDevice(true);
    }

    @Step("Test Step 2: Create the maximum number VPN Group by IM APP;")
    public void step2() {
        brdvgp.gotoPage();
        for (int iNo = 1; iNo < 100; iNo++) {
            brdvgp.createVPNGroup(String.format("%s%d", groupName, iNo));
            if (handle.getPageErrorMsg().length() > 3) {
                break;
            }
        }
        MyCommonAPIs.takess("check how many groups has been added");
    }

}
