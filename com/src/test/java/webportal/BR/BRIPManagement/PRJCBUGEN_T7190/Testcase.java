package webportal.BR.BRIPManagement.PRJCBUGEN_T7190;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

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
    String sExpect = "192.168.3.223";

    @Feature("BR.BRIPManagement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7190") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("004-Discard IP address change") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7190") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3", enabled = false) // Use p1/p2/p3 to high/normal/low on priority
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

    @Step("Test Step 2: Turn on \"Assign IP address automatically\" from APP\r\n" + "Popup warning \"Changes will not be saved ....\"\r\n"
            + "Click \"Discard\", cancel the deployment")
    public void step2() {
        brdwip.gotoPage();
        brdwip.setDHCP(false);
        handle.clickButton(1);

        handle.openOneBRDevice(true);
        brdwip.gotoPage();
        assertTrue(brdwip.cbDhcp.is(Condition.checked), "device is not in dhcp");
    }
}
