package webportal.ConfigBackupAndRestore.Pro.PRJCBUGEN_T16490;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    HashMap<String, String> BackupInfo    = new HashMap<String, String>();
    HashMap<String, String> BackupInfoTwo = new HashMap<String, String>();
    String                  poeName       = "Poe16490";

    @Feature("ConfigBackupAndRestore.Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16490") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the POE Scheduling configuration during back up and restore") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16490") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().restoreBackup(WebportalParam.location1, BackupInfo.get("Name"));
        new AccountPage().backupDelete(WebportalParam.location1, BackupInfo);
        new AccountPage().backupDelete(WebportalParam.location1, BackupInfoTwo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        BackupInfo.put("Name", "test16490");
        BackupInfo.put("Description", "test");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDutInNormalAccount("admin", WebportalParam.sw1serialNo, WebportalParam.sw1deveiceName, WebportalParam.sw1MacAddress);
    }

    @Step("Test Step 2: Check backup information after configuration backup;")
    public void step2() {
        if (new DevicesDashPage().getDeviceName(WebportalParam.sw1serialNo).equals("")) {
            assertTrue(false, "Need add one switch.");
        }

        new AccountPage().ssidconfigbackupLocation(WebportalParam.location1, BackupInfo);
        assertTrue(
                new AccountPage().checkBackupIsExist(WebportalParam.location1, BackupInfo.get("Name"))
                        && new AccountPage().checkConfigurationBackupAndRestorePageElement(WebportalParam.location1, "create and edit"),
                "Backup information incorrect.");
    }

    @Step("Test Step 3: Add one poe schedule and create second backup;")
    public void step3() {
        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wpsp.gotoPage();
        wpsp.addPoESchedule(poeName, 0, "None");

        BackupInfoTwo.put("Name", "test16490two");
        BackupInfoTwo.put("Description", "test");
        new AccountPage().ssidconfigbackupLocation(WebportalParam.location1, BackupInfoTwo);
        assertTrue(
                new AccountPage().checkBackupIsExist(WebportalParam.location1, BackupInfoTwo.get("Name"))
                        && new AccountPage().checkConfigurationBackupAndRestorePageElement(WebportalParam.location1, "create and edit"),
                "Backup information incorrect.");
    }

    @Step("Test Step 4: Check restore backup;")
    public void step4() {
        new AccountPage().restoreBackup(WebportalParam.location1, BackupInfo.get("Name"));
        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wpsp.gotoPage();
        if (!wpsp.getPoEs().contains(poeName)) {
            new AccountPage().restoreBackup(WebportalParam.location1, BackupInfoTwo.get("Name"));
            handle.gotoLoction();
            handle.gotoLocationWireSettings();

            wpsp.gotoPage();
            assertTrue(wpsp.getPoEs().contains(poeName), "Restore backup failed.");
        } else {
            assertTrue(false, "Restore backup failed.");
        }
    }

}
