package webportal.SwitchManaged.SNMP.PRJCBUGEN_T12912;

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
    String sIp = "10.10.10.10";
    String sPw = "test@snmp1";
    String tmpStr;

    @Feature("Switch.SNMP") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T12912") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("003-Verify the validity of IP address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T12912") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Insight discover and manage one switch")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        snmpp.gotoPage();
    }

    @Step("Test Step 2: Insight go to Wired-->Setting-->SNMP config page, input a invalid IP address;")
    public void step2() {
        snmpp.txtIpAddress.setValue("1.1.1.256");
        snmpp.txtCommunityString.setValue(sPw);
        snmpp.clickSave();
        assertTrue(handle.getPageErrorMsg().contains("invalid"), "Invalid IP Address");

        snmpp.txtIpAddress.setValue("1.1.1.255");
        snmpp.txtCommunityString.setValue(sPw);
        snmpp.clickSave();
        assertTrue(handle.getPageErrorMsg().contains("invalid"), "Invalid IP Address");

        snmpp.txtIpAddress.setValue("1.1.1.0");
        snmpp.txtCommunityString.setValue(sPw);
        snmpp.clickSave();
        assertTrue(handle.getPageErrorMsg().contains("invalid"), "Invalid IP Address");

        snmpp.txtIpAddress.setValue("0.1.2.3");
        snmpp.txtCommunityString.setValue(sPw);
        snmpp.clickSave();
        assertTrue(handle.getPageErrorMsg().contains("invalid"), "Invalid IP Address");
    }
}
