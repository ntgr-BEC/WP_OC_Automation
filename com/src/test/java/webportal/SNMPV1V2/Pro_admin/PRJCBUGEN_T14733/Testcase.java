package webportal.SNMPV1V2.Pro_admin.PRJCBUGEN_T14733;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SnmpUtils;
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
    String    sIp = "10.10.10.10";
    String    sPw = "test@snmp14713";
    String    tmpStr;
    SnmpUtils su;

    @Feature("SNMPV1V2") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14733") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the SNMP host machine is receiving all the supported SNMP Traps") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14733") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        sIp = webportalParam.getLocalNetIp(false);
        su = new SnmpUtils(WebportalParam.sw1IPaddress, sPw);
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        SwitchCLIUtils.cleanSNMP(true);
        su.killTrap(true);
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
        snmpp.clearSnmp();
        handle.waitCmdReady(sPw, true);
    }

    @Step("Test Step 2: Insight go to Wired-->Setting-->SNMP config page, config IP address and community string but not enable trap, click Save")
    public void step2() {
        snmpp.setSnmp(false, sIp, sPw, false);
        handle.waitCmdReady(sPw, false);

        tmpStr = SwitchCLIUtils.getSNMPInfo();
        assertTrue(tmpStr.contains(sIp), "check option on cli for text: " + sIp);
        assertTrue(!SwitchCLIUtils.SNMPClass.isTrapEnable, "check option trap status should not be enabled");
    }

    @Step("Test Step 3: Trigger event by reboot device via Webportal, Go to trap server to check the info")
    public void step3() {
        su.launchTrap();

        // shutdown need a port attached by cable
        // MyCommonAPIs.getCmdOutput("conf;interface g2;shutdown", false);
        // MyCommonAPIs.sleepi(5);
        // MyCommonAPIs.getCmdOutput("conf;interface g2;no shutdown", false);
        // MyCommonAPIs.sleepi(30);
        ddp.gotoPage();
        ddp.rebootDevice(WebportalParam.sw1serialNo);

        tmpStr = su.getTrap();
        assertTrue(!tmpStr.contains("Timeticks"), "there should no snmp trap text");
    }

    @Step("Test Step 4: Insight go to SNMP config page, enable SNMP trap, click Save")
    public void step4() {
        snmpp.gotoPage();
        snmpp.setSnmp(true, sIp, sPw, false);
        MyCommonAPIs.sleepsync();

        ddp.gotoPage();
        ddp.rebootDevice(WebportalParam.sw1serialNo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.sw1serialNo);

        // do 2nd time
        ddp.gotoPage();
        ddp.rebootDevice(WebportalParam.sw1serialNo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.sw1serialNo);

        su.killTrap(false);
        tmpStr = su.getTrap();
        assertTrue(tmpStr.contains("Timeticks") || tmpStr.contains("Received "), "there should has some snmp trap text");
    }

}
