package webportal.BR.BRIPSecVpn.PRJCBUGEN_T13697;

import static org.testng.Assert.assertTrue;

import java.util.List;

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

    @Feature("BR.BRIPSecVpn") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13697") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("100-Get notification after add,edit,delete Ipsec vpn policy") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13697") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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

    @Step("Test Step 2: Add/edit/delete ipsec policy")
    public void step2() {
        bripsvp.gotoPage();
        bripsvp.addPolicy();
        bripsvp.deletePolicyNames();
    }

    @Step("Test Step 3: Get notification under notification")
    public void step3() {
        evtp.gotoPage();
        List<String> lsEvent = evtp.getEventDesc();
        boolean hasAdd = false;
        boolean hasDelete = false;
        for (String sEvent : lsEvent) {
            if (sEvent.contains("add") && sEvent.contains("IPSEC")) {
                hasAdd = true;
                break;
            }
        }
        for (String sEvent : lsEvent) {
            if (sEvent.contains("delete") && sEvent.contains("IPSEC")) {
                hasDelete = true;
                break;
            }
        }
        assertTrue(hasAdd, "one add ipsec event");
        assertTrue(hasDelete, "one delete ipsec event");
    }
}
