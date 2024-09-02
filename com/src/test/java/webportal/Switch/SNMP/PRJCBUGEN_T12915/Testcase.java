package webportal.Switch.SNMP.PRJCBUGEN_T12915;

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
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String    sIp = "10.10.10.10";
    String    sPw = "test@snmp12915";
    String    tmpStr;
    SnmpUtils su;
    
    @Feature("Switch.SNMP") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T12915") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007-Enable and disable SNMP trap by Insight") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T12915") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        if (!snmpp.isSnmpDisabled()) {
            sIp = webportalParam.getLocalNetIp(false);
            su = new SnmpUtils(WebportalParam.sw1IPaddress, sPw);
            runTest(this);
        }
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (!snmpp.isSnmpDisabled()) {
            SwitchCLIUtils.cleanSNMP(true);
            su.killTrap(true);
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
    
    @Step("Test Step 3: Trigger event by failed login in trap is disabled")
    public void step3() {
        su.launchTrap();
        
        // shutdown need a port attached by cable
        // MyCommonAPIs.getCmdOutput("conf;interface g2;shutdown", false);
        // MyCommonAPIs.sleepi(5);
        // MyCommonAPIs.getCmdOutput("conf;interface g2;no shutdown", false);
        // MyCommonAPIs.sleepi(30);
        // ddp.gotoPage();
        // ddp.waitDevicesReboot(WebportalParam.sw1serialNo);
        wstp.gotoPage();
        wstp.setSTPMode(1, false, false);
        MyCommonAPIs.sleepi(30);
        wstp.setSTPMode(2, false, false);
        MyCommonAPIs.sleepi(30);
        //SwitchCLIUtils.triggerSnmpTrap();
        
        try {
            SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, "testaabbcc!!");
        } catch (Throwable e) {
            logger.info("login with invalid passwd");
        }

        MyCommonAPIs.sleepsync();

        tmpStr = su.getTrap();
        assertTrue(!tmpStr.contains("Timeticks"), "there should no snmp trap text");
    }
    
    @Step("Test Step 4: Trigger event by failed login in trap is enabled")
    public void step4() {
        snmpp.gotoPage();
        snmpp.setSnmp(true, sIp, sPw, false);
        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.sw1serialNo);
        
        //SwitchCLIUtils.triggerSnmpTrap();
        wstp.gotoPage();
        wstp.setSTPMode(1, false, false);
        MyCommonAPIs.sleepi(30);
        wstp.setSTPMode(2, false, false);
        MyCommonAPIs.sleepi(30);
        
        try {
            SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, "testaabbcc!!");
        } catch (Throwable e) {
            logger.info("login with invalid passwd");
        }
        
        MyCommonAPIs.sleepsync();
        
        tmpStr = su.getTrap();
        System.out.println(tmpStr);
        assertTrue(tmpStr.contains("Timeticks") || tmpStr.contains("Received "), "there should has some snmp trap text");
        
//        su.killTrap(true);
    }
    
}
