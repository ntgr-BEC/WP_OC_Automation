package webportal.ConfigBackupAndRestore.Pro.PRJCBUGEN_T16487;

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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    HashMap<String, String> BackupInfo    = new HashMap<String, String>();
    HashMap<String, String> BackupInfoTwo = new HashMap<String, String>();
    Map<String, String>     locationInfo  = new HashMap<String, String>();
    String                  country       = "";

    @Feature("ConfigBackupAndRestore.Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16487") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Premium user can restore backup configure checkpoints at network level from web portal") // It's a test case title from
                                                                                                                  // Jira Test Case.
    @TmsLink("PRJCBUGEN-T16487") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().backupDelete(WebportalParam.location1, BackupInfo);
        new AccountPage().backupDelete(WebportalParam.location1, BackupInfoTwo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        BackupInfo.put("Name", "test16487");
        BackupInfo.put("Description", "test");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Check backup information after configuration backup;")
    public void step2() {
        country = new AccountPage().getCountry(WebportalParam.location1);
        new AccountPage().ssidconfigbackupLocation(WebportalParam.location1, BackupInfo);
        assertTrue(
                new AccountPage().checkBackupIsExist(WebportalParam.location1, BackupInfo.get("Name"))
                        && new AccountPage().checkConfigurationBackupAndRestorePageElement(WebportalParam.location1, "create and edit"),
                "Backup information incorrect.");
    }

    @Step("Test Step 3: Edit location information and create second backup;")
    public void step3() {
        locationInfo.put("Location Name", "test16487");
        locationInfo.put("Country", "China");
        new AccountPage().editLocation(WebportalParam.location1, locationInfo);

        BackupInfoTwo.put("Name", "test16487two");
        BackupInfoTwo.put("Description", "test");
        new AccountPage().ssidconfigbackupLocation(locationInfo.get("Location Name"), BackupInfoTwo);
        assertTrue(
                new AccountPage().checkBackupIsExist(locationInfo.get("Location Name"), BackupInfoTwo.get("Name"))
                        && new AccountPage().checkConfigurationBackupAndRestorePageElement(locationInfo.get("Location Name"), "create and edit"),
                "Backup information incorrect.");
    }

    @Step("Test Step 4: Check restore backup;")
    public void step4() {
        new AccountPage().restoreBackup(locationInfo.get("Location Name"), BackupInfo.get("Name"));
        assertTrue(new AccountPage().getCountry(WebportalParam.location1).equals(country), "Restore backup failed.");
    }

}
