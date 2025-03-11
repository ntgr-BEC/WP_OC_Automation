package webportal.PR60X.LocationBackupRestore.prouser.PRJCBUGEN_T35176;

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
import util.APUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.MDNSPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.PRDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    HashMap<String, String> BackupInfo = new HashMap<String, String>();

    @Feature("LocationBackupRestore.PR60X") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35176") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the configuration is getting restored on the device once it comes online") // It's a test case title from Jira
                                                                                                                 // Test Case.
    @TmsLink("PRJCBUGEN-T35176") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);

        handle.gotoLoction();
        // new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Check backup information after configuration backup;")
    public void step2() {
        new AccountPage().ssidconfigbackupLocation(WebportalParam.location1, BackupInfo);
        assertTrue(
                new AccountPage().checkBackupIsExist(WebportalParam.location1, BackupInfo.get("Name"))
                        && new AccountPage().checkConfigurationBackupAndRestorePageElement(WebportalParam.location1, "create and edit"),
                "Backup information incorrect.");
    }

   
    @Step("Test Step 3: check MDNS")
    public void step3() {

        new MDNSPage();    
        new PRDashPage().addPolicyPR(WebportalParam.pr1deveiceName); 
  
    }
    
    @Step("Test Step 4: Check restore backup")
    public void step4() {
        new AccountPage().restoreBackup(WebportalParam.location1, BackupInfo.get("Name"));
        handle.gotoLoction();
        new MDNSPage(); 
               
        assertTrue(!new MDNSPage().policyName.isDisplayed(),"policy name still exits after restore");
            
        }
        
        
        
    }

