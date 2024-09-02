package webportal.SNMPV1V2.Premium.PRJCBUGEN_T14708;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author xuchen
 *
 */
public class Testcase extends TestCaseBase {
    String sIp = "10.10.10.10";
    String sPw = "test@snmp1";
    String tmpStr;

    @Feature("SNMPV1V2") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14708") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Validate the Community String field and the rules associated.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14708") // It's a testcase id/link from Jira Test Case.
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
        new AccountPage().enterLocation(WebportalParam.location1);
        new DevicesDashPage().checkDutInProAccount("admin",WebportalParam.sw1serialNo,WebportalParam.sw1deveiceName); //must input admin or manager
  
//        handle.gotoLoction();
//        handle.gotoLocationWireSettings();
        
//        handle.gotoLocationSettings();
        snmpp.gotoPage();
    }

    @Step("Test Step 2: Insight go to Wired-->Setting-->SNMP config page, input community string with different length valid characters;")
    public void step2() {
        snmpp.txtIpAddress.setValue(sIp);
        String sStr = "test@123";
        snmpp.txtCommunityString.setValue(sStr);
        snmpp.clickSave();
        handle.waitCmdReady(sStr, false);

        tmpStr = SwitchCLIUtils.getSNMPInfo();
        assertTrue(tmpStr.contains(sStr), "check option on cli for text: " + sStr);

        sStr = "test@test123";
        snmpp.txtCommunityString.setValue(sStr);
        snmpp.clickSave();
        handle.waitCmdReady(sStr, false);

        tmpStr = SwitchCLIUtils.getSNMPInfo();
        assertTrue(tmpStr.contains(sStr), "check option on cli for text: " + sStr);

        sStr = "test@test123test";
        snmpp.txtCommunityString.setValue(sStr);
        snmpp.clickSave();
        handle.waitCmdReady(sStr, false);

        tmpStr = SwitchCLIUtils.getSNMPInfo();
        assertTrue(tmpStr.contains(sStr), "check option on cli for text: " + sStr);
    }

    @Step("Test Step 3: Insight go to Wired-->Setting-->SNMP config page, input community string with different length valid characters;")
    public void step3() {
        snmpp.txtCommunityString.setValue("l");
        snmpp.clickSave1();
        assertTrue(handle.getPageErrorMsg().contains("in length"), "be 8–16 characters");

        snmpp.txtCommunityString.setValue("ltest@1");
        snmpp.clickSave1();
        assertTrue(handle.getPageErrorMsg().contains("in length"), "be 8–16 characters");

        snmpp.txtCommunityString.setValue("test@test@test@12");
        snmpp.clickSave1();
        assertTrue(handle.getPageErrorMsg().contains("in length"), "be 8–16 characters");
    }

    @Step("Test Step 4: Input string only with alphanumeric")
    public void step4() {
        snmpp.txtCommunityString.setValue("12345678");
        snmpp.clickSave1();
        assertTrue(handle.getPageErrorMsg().contains("in length"), "be 8–16 characters");

        snmpp.txtCommunityString.setValue("abcdefghello");
        snmpp.clickSave1();
        assertTrue(handle.getPageErrorMsg().contains("in length"), "be 8–16 characters");
    }

    @Step("Test Step 5: Input string only with special characters")
    public void step5() {
        snmpp.txtCommunityString.setValue("!@#$%^&*()");
        snmpp.clickSave1();
        assertTrue(handle.getPageErrorMsg().contains("in length"), "be 8–16 characters");
    }

    @Step("Test Step 6: Input string with invalid characters")
    public void step6() {
        snmpp.txtCommunityString.setValue("test @test1");
        snmpp.clickSave1();
        assertTrue(handle.getPageErrorMsg().contains("in length"), "be 8–16 characters");
    }
}
