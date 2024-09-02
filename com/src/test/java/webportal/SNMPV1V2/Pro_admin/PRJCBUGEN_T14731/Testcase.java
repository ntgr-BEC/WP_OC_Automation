package webportal.SNMPV1V2.Pro_admin.PRJCBUGEN_T14731;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author xuchen
 *
 */
public class Testcase extends TestCaseBase {
    String sIp = "192.168.123.11";
    String sPw = "SnmpClear1#@";
    String tmpStr;

    @Feature("SNMPV1V2") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14731") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the SNMP entry gets deleted, when cleared from the Web Portal & Insight Application") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14731") // It's a testcase id/link from Jira Test Case.
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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);


        handle.gotoLoction();
        new DevicesDashPage().checkDutInNormalAccount("admin",WebportalParam.sw1serialNo,WebportalParam.sw1deveiceName, WebportalParam.sw1MacAddress); //must input admin or manager
        
//        handle.gotoLocationWireSettings();
        snmpp.gotoPage();
    }

    @Step("Test Step 2: Insight go to Wired-->Setting-->SNMP config page, enable SNMP trap, config IP address and community string success;")
    public void step2() {
        snmpp.setSnmp(false, sIp, sPw, false);
        handle.waitCmdReady(sIp, false);

        tmpStr = SwitchCLIUtils.getSNMPInfo();
        assertTrue(tmpStr.contains(sIp), "check option on cli for text: " + sIp);
    }

    @Step("Test Step 3: Go to SNMP config page, click Clear button by Insight")
    public void step3() {
        snmpp.clearSnmp();
        handle.waitCmdReady(sIp, true);

        tmpStr = SwitchCLIUtils.getSNMPInfo();
        assertTrue(!tmpStr.contains(sPw), "check option on cli for text: " + sPw);
    }
}
