package webportal.BR.BRIPManagement.PRJCBUGEN_T7194;

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
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String sExpect  = "192.192.192.1";
    String vlanName = "testvlan";
    String vlanId   = "1192";

    @Feature("BR.BRIPManagement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7194") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("008-Set some invalid parameters on LAN1") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7194") // It's a testcase id/link from Jira Test Case.
    @Issue("PRJCBUGEN-13519")

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

    @Step("Test Step 2: Go to BR-->DHCP Servers page, set a invalid IP on LAN1 port;")
    public void step2() {
        brddchps.gotoPage();
        brddchps.openOne("1");

        brddchps.txtIpStart.setValue("1255");
        brddchps.txtIpEnd.setValue("1254");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().length() > 5, "invalid ip can be input");

        brddchps.txtIpStart.setValue("1256");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().length() > 5, "invalid ip can be input");

        brddchps.txtIpStart.setValue("1255");
        handle.clickButton(0);
        assertTrue(handle.getPageErrorMsg().length() > 5, "invalid ip can be input");
    }
}
