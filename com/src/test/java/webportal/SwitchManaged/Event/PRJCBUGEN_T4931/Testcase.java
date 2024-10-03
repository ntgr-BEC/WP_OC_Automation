package webportal.SwitchManaged.Event.PRJCBUGEN_T4931;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.webelements.EventElement;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    String tmpStr;

    @Issue("PRJCBUGEN-10063")
    @Feature("Switch.Event") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4931") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("001-Insight web portal display/delete/upload critical logs") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4931") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, enabled = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open Event")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        evtp.gotoPage();
        evtp.makeCriticalEvent(false, "Disconnect");
        evtp.makeCriticalEvent(false, "Connect");
    }

    @Step("Test Step 2: Insight App check notifications;")
    public void step2() {
        if (!evtp.getEventType().contains(EventElement.sCritical)) {
            evtp.makeCriticalEvent(true,"Disconnect");
            evtp.makeCriticalEvent(true,"Connect");
        }

        evtp.gotoPage();
        assertTrue(evtp.getEventCount() > 0, "there must be some events");
    }

    @Step("Test Step 3: There is one new critical logs and detailed information is correct;")
    public void step3() {
        assertTrue(handle.pageSource().contains(EventElement.sCritical), "there must be a event with Critical type");
    }

    @Step("Test Step 4: Delete the critical log by Insight App;")
    public void step4() {
        System.out.println("0000000000000");
       
        List<String> lsEvent1 = evtp.getEventDesc();
        MyCommonAPIs.sleepi(30);
        System.out.println("lsEvent1"+ lsEvent1);
        evtp.deleteOneEvent(EventElement.sCritical);
        MyCommonAPIs.sleepi(20);
        System.out.println("lsEvent1"+ lsEvent1);
        List<String> lsEvent2 = evtp.getEventDesc();
        System.out.println("3333333333");
        System.out.println("lsEvent2"+ lsEvent2);

        assertTrue(!lsEvent1.equals(lsEvent2), "one Critical type is removed");
    }
}
