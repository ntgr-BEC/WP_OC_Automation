package webportal.SwitchManaged.SNMP.PRJCBUGEN_T12911;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SnmpUtils;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String sIp = "10.10.10.10";
    String sPw = "test@snmp12911";
    String tmpStr;

    @Feature("Switch.SNMP") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T12911") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("002-Create new SNMP community by Insight") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T12911") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        if (!snmpp.isSnmpDisabled()) {
            sIp = webportalParam.getLocalNetIp(false);
            SwitchCLIUtils.cleanSNMP(true);
            runTest(this);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (!snmpp.isSnmpDisabled()) {
            snmpp.gotoPage();
            snmpp.clearSnmp();
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Insight discover and manage one switch")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        snmpp.gotoPage();
        snmpp.clearSnmp();
    }

    @Step("Test Step 2: only input IP or community string, not input both same time;")
    public void step2() {
        snmpp.txtIpAddress.setValue(sIp);
        snmpp.txtCommunityString.clear();
        // assertTrue(!snmpp.btnSave.isEnabled(), "should not able to save");

        // snmpp.txtIpAddress.clear();
        // snmpp.txtCommunityString.setValue(sPw);
        // assertTrue(!snmpp.btnSave.isEnabled(), "should not able to save");
    }

    @Step("Test Step 3: Input valid IP and community string, click Save")
    public void step3() {
        snmpp.setSnmp(true, sIp, sPw, false);
//        handle.waitDeviceOnline();
        handle.waitCmdReady(sIp, false);

        tmpStr = SwitchCLIUtils.getSNMPInfo();
        assertTrue(SwitchCLIUtils.SNMPClass.isReadyOnly, "check option community should be ready only");
        assertTrue(SwitchCLIUtils.SNMPClass.isTrapEnable, "check option trap status should be disable");
        assertTrue(SwitchCLIUtils.SNMPClass.snmpResult.contains(sIp), "check option on cli ip: " + sIp);
    }

    @Step("Test Step 4: Try to get some mib data by Mib Browser")
    public void step4() {
        tmpStr = new SnmpUtils(WebportalParam.sw1IPaddress, sPw).getSysDesc();
        assertTrue(tmpStr.contains("NETGEAR ") || tmpStr.contains("Insight ") || tmpStr.contains("Smart "), "check to get sysDesc");
    }

    @Step("Test Step 5: Change SNMP IP address by Insight")
    public void step5() {
        tmpStr = "snmp@new1";
        snmpp.setSnmp(false, sIp, tmpStr, false);
        handle.waitCmdReady(tmpStr, false);

        tmpStr = new SnmpUtils(WebportalParam.sw1IPaddress, sPw).getSysDesc();
        assertTrue(!tmpStr.contains("Insight"), "check to not able to get sysDesc");
    }
}
