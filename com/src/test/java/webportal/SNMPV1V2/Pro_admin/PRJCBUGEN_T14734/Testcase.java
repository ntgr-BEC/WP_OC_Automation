package webportal.SNMPV1V2.Pro_admin.PRJCBUGEN_T14734;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author xuchen
 */
public class Testcase extends TestCaseBase {
    String sIp = "10.10.10.10";
    String sPw = "test@snmp1";
    String tmpStr;

    HashMap<String, String> BackupInfo    = new HashMap<String, String>();
    HashMap<String, String> BackupInfoTwo = new HashMap<String, String>();
    Map<String, String>     locationInfo  = new HashMap<String, String>();
    String                  country       = "";

    @Feature("SNMPV1V2") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14734") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the backup restore saves both the locally created SNMP community & Insight Community.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14734") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().backupDelete(WebportalParam.location1, BackupInfo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Insight discover and manage one switch")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDutInNormalAccount("admin", WebportalParam.sw1serialNo, WebportalParam.sw1deveiceName, WebportalParam.sw1MacAddress); // must input admin or manager
        
//        handle.gotoLocationWireSettings();
        snmpp.gotoPage();
    }

    @Step("Test Step 2: Insight go to Wired-->Setting-->SNMP config page, enable SNMP trap, config IP address and community string success;")
    public void step2() {
        snmpp.setSnmp(true, sIp, sPw, false);
        handle.waitCmdReady(sPw, false);

        tmpStr = SwitchCLIUtils.getSNMPInfo();
        assertTrue(tmpStr.contains(sPw), "check option on cli for text: " + sPw);
        assertTrue(SwitchCLIUtils.SNMPClass.isTrapEnable, "check option trap status should be enabled");

    }

    @Step("Test Step 3: Check backup information after configuration backup;")
    public void step3() {
        BackupInfo.put("Name", "test14714");
        BackupInfo.put("Description", "test");
        new AccountPage().ssidconfigbackupLocation(WebportalParam.location1, BackupInfo);
        assertTrue(
                new AccountPage().checkBackupIsExist(WebportalParam.location1, BackupInfo.get("Name"))
                        && new AccountPage().checkConfigurationBackupAndRestorePageElement(WebportalParam.location1, "create and edit"),
                "Backup information incorrect.");
    }

    @Step("Test Step 4: Insight go to Wired-->Setting-->SNMP config page, clear snmp;")
    public void step4() {
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        snmpp.gotoPage();
        String sIp2 = "10.10.10.22";
        String sPw2 = "test@Test1";
        snmpp.setSnmp(true, sIp2, sPw2, false);
        handle.waitCmdReady(sPw2, false);

        tmpStr = SwitchCLIUtils.getSNMPInfo();
        assertTrue(tmpStr.contains(sPw2), "check option on cli for text: " + sPw2);
    }

    @Step("Test Step 5: Check restore backup;")
    public void step5() {
        locationInfo.put("Location Name", WebportalParam.location1);
        new AccountPage().restoreBackup(locationInfo.get("Location Name"), BackupInfo.get("Name"));
        MyCommonAPIs.sleepi(180);
    }

    @Step("Test Step 6: Insight go to Wired-->Setting-->SNMP config page, check restore snmp;")
    public void step6() {
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        snmpp.gotoPage();

        tmpStr = SwitchCLIUtils.getSNMPInfo();
        assertTrue(tmpStr.contains(sPw), "check option on cli for text: " + sPw);
        assertTrue(SwitchCLIUtils.SNMPClass.isTrapEnable, "check option trap status should be enabled");
        tmpStr = MyCommonAPIs.checkSystemCall(2, null);
        assertTrue(tmpStr.contains(sPw), "check option on webportal for text: " + sPw);
    }

}
