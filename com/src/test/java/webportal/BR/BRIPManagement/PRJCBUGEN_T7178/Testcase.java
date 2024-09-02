package webportal.BR.BRIPManagement.PRJCBUGEN_T7178;

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

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("BR.BRIPManagement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7178") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("023-Link to device info from Networks page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7178") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
    }

    @Step("Test Step 2: IM APP go to Networks page, click Router button;")
    public void step2() {
        brrp.gotoPage();
    }

    @Step("Test Step 3: Select one device and click the link;")
    public void step3() {
        brrp.openBR();
    }

    @Step("Test Step 4: It will go to the device page success, and all parameters should be correct;")
    public void step4() {
        String sGet = brdsp.getFieldValue(1);
        assertTrue(sGet.equals(WebportalParam.br1model));
    }

}
