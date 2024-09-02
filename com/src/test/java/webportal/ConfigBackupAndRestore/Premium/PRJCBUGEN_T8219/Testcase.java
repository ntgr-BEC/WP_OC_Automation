package webportal.ConfigBackupAndRestore.Premium.PRJCBUGEN_T8219;

import static org.testng.Assert.assertTrue;

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

    String backupName = "test8219";

    @Feature("ConfigBackupAndRestore.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T8219") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify maximum number of backup files created by user at device level") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T8219") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage();
        page.deleteAllBackup();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
//        new DevicesDashPage().checkDutInAdminAccount(WebportalParam.sw1serialNo, WebportalParam.sw1deveiceName, WebportalParam.sw1MacAddress);
    }

    @Step("Test Step 2: Check backup limit message;")
    public void step2() {
        ddp.gotoPage();
        ddp.openSW1();

        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage();
        page.deleteAllBackup();

        for (int i = 1; i < 4; i++) {
            page.createBackup(backupName + String.valueOf(i), "test");
        }

        assertTrue(page.checkBackupLimit(), "Backup number no limit.");
    }
}
