package webportal.PR60X.LocationBackupRestore.PRJCBUGEN_T35178;

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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    HashMap<String, String> BackupInfo = new HashMap<String, String>();
    HashMap<String, String> locationInfo = new HashMap<String, String>();

    @Feature("LocationBackupRestore.PR60X") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35178") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify, vlan config gets restore on PR") // It's a test case title from Jira
                                                                                                                 // Test Case.
    @TmsLink("PRJCBUGEN-T35178") // It's a testcase id/link from Jira Test Case.

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
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        BackupInfo.put("Name", "test7882");
        BackupInfo.put("Description", "test");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
    }

    @Step("Test Step 2: Check backup information after configuration backup;")
    public void step2() {
        new AccountPage().ssidconfigbackupLocation(WebportalParam.location1, BackupInfo);
        assertTrue(
                new AccountPage().checkBackupIsExist(WebportalParam.location1, BackupInfo.get("Name"))
                        && new AccountPage().checkConfigurationBackupAndRestorePageElement(WebportalParam.location1, "create and edit"),
                "Backup information incorrect.");
    }

    @Step("Test Step 3: Create VLAN;")
    public void step3() {
        AccountPage AccountPage = new AccountPage();
        
        Map<String, String> ssidInfo = new HashMap<String, String>();
        locationInfo.put("Network Name","VLAN16598");
        locationInfo.put("VLAN Name","VLAN16598");
        locationInfo.put("VLAN ID", "147");
        locationInfo.put("SSID", "apwp16598");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "123456798");
        ssidInfo.put("SSID", "apwp16598");
        MyCommonAPIs.sleep(10000);       
        AccountPage.editSSID80211wLocation(WebportalParam.location1,locationInfo);
        new WirelessQuickViewPage().enabletoggleSSID80211wsettinglocation(ssidInfo);
        
       
    }
    
    @Step("Test Step 4: Check restore backup")
    public void step4() {
    new AccountPage().restoreBackup(WebportalParam.location1, BackupInfo.get("Name"));
    
    
    AccountPage AccountPage = new AccountPage();
    assertTrue(AccountPage.checkVLAN(WebportalParam.location1,locationInfo),"name does not exits");
    

    }

}
