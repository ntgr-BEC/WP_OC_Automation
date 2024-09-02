package webportal.BR.BRVPNSite2Site.PRJCBUGEN_T7203;

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
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String groupName = "testvpn";

    @Feature("BR.BRVPNSite2Site") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7203") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("005-Edit the VPN Group name") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7203") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
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

    @Step("Test Step 2: Create VPN Group with name include invalid characters;")
    public void step2() {
        brdvgp.gotoPage();
        brdvgp.createVPNGroup("abcd 12");
        assertTrue(handle.getPageErrorMsg().length() > 10, "error not show up for space name");
        handle.refresh();
    }

    @Step("Test Step 3: Create VPN Group with name length equal the maximum value, and name include upper case, lower case, numeric")
    public void step3() {
        groupName = "Abcde12345Abcde12345Abcde12345Abcde12345Abcde12345";
        brdvgp.createVPNGroup(groupName);
        assertTrue(brdvgp.getGroups().contains(groupName), "group cannot be added");
    }
}
