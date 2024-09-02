package webportal.ConfigBackupAndRestore.Premium.AP.PRJCBUGEN_T9446;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String>     ssidInfo      = new HashMap<String, String>();
    HashMap<String, String> BackupInfo    = new HashMap<String, String>();
    HashMap<String, String> BackupInfoTwo = new HashMap<String, String>();

    @Feature("ConfigBackupAndRestore.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9446") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the Wifi config for AP at location level") // It's a test case title from
                                                                    // Jira Test Case.
    @TmsLink("PRJCBUGEN-T9446") // It's a testcase id/link from Jira Test Case.

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
        BackupInfo.put("Name", "test9446");
        BackupInfo.put("Description", "test");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
//        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Check backup information after configuration backup;")
    public void step2() {
        if (new DevicesDashPage().getDeviceName(WebportalParam.ap1serialNo).equals("")) {
            assertTrue(false, "Need add one ap.");
        }

        new AccountPage().ssidconfigbackupLocation(WebportalParam.location1, BackupInfo);
        assertTrue(
                new AccountPage().checkBackupIsExist(WebportalParam.location1, BackupInfo.get("Name"))
                        && new AccountPage().checkConfigurationBackupAndRestorePageElement(WebportalParam.location1, "create and edit"),
                "Backup information incorrect.");
    }

    @Step("Test Step 3: Add ssid and add url to blacklist, then create second backup;")
    public void step3() {
        handle.gotoLoction();
        ssidInfo.put("SSID", "apwp9446");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);
        new WirelessQuickViewPage().enableUrlFilteringAndAddUrl("www.rediff.com");
        new WirelessQuickViewPage().editRateLimit(ssidInfo.get("SSID"), 20.0000, 40.0000);

        BackupInfoTwo.put("Name", "test9446two");
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
        if (!new WirelessQuickViewPage().checkUrlFilterIsExist("www.rediff.com")
                && !new WirelessQuickViewPage().checkRateLimitStatus(ssidInfo.get("SSID"))) {
            new AccountPage().restoreBackup(WebportalParam.location1, BackupInfoTwo.get("Name"));
            handle.gotoLoction();
            assertTrue(new WirelessQuickViewPage().checkUrlFilterIsExist("www.rediff.com")
                    && new WirelessQuickViewPage().checkRateLimitStatus(ssidInfo.get("SSID")), "Restore backup failed.");
        } else {
            assertTrue(false, "Restore backup failed.");
        }
    }

}
