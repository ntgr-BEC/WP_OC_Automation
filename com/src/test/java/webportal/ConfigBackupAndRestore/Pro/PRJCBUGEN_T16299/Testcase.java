package webportal.ConfigBackupAndRestore.Pro.PRJCBUGEN_T16299;

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
import webportal.weboperation.DeviceBackupRestorePage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    HashMap<String, String> BackupInfo = new HashMap<String, String>();
    String                  fileName   = "";

    @Feature("ConfigBackupAndRestore.Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16299") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("User can upload backup checkpoint configuration file only at device level for switch") // It's a test case title from Jira Test
                                                                                                         // Case.
    @TmsLink("PRJCBUGEN-T16299") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage();
        page.deleteDownloadFile(WebportalParam.sw1serialNo);
        page.deleteBackup();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        BackupInfo.put("Name", "test16299");
        BackupInfo.put("Description", "test");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDutInNormalAccount("admin", WebportalParam.sw1serialNo, WebportalParam.sw1deveiceName, WebportalParam.sw1MacAddress);
    }

    @Step("Test Step 2: Create new backup and download backup;")
    public void step2() {
        ddp.gotoPage();
        ddp.openSW1();

        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage();
        page.createBackup();
        assertTrue(page.hasBackup(), "A backup should be there");

        page.gotoPage();
        page.downloadBackup("test16299");
        fileName = page.getDownloadBackupFile(WebportalParam.sw1serialNo);

        assertTrue(!fileName.equals("") && !fileName.equals(null), "A download file should be there");
    }

    @Step("Test Step 3: Check upload backup;")
    public void step3() {
        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.deleteBackup();

        page.uploadBackup(fileName);
        assertTrue(page.hasBackup(), "A backup should be there");
    }

}
