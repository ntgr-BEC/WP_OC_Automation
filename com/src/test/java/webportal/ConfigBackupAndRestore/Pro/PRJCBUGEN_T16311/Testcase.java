package webportal.ConfigBackupAndRestore.Pro.PRJCBUGEN_T16311;

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

    String editName = "";

    @Feature("ConfigBackupAndRestore.Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16311") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Premium user can edit configure backup checkpoints at Device level from web portal for switch") // It's a test case title
                                                                                                                         // from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16311") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage();
        page.deleteBackupNew(editName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDutInNormalAccount("admin", WebportalParam.sw1serialNo, WebportalParam.sw1deveiceName, WebportalParam.sw1MacAddress);
    }

    @Step("Test Step 2: Create new backup;")
    public void step2() {
        ddp.gotoPage();
        ddp.openSW1();

        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage();
        page.createBackup();
        assertTrue(page.hasBackup(), "A backup should be there");
    }

    @Step("Test Step 3: Check edit backup;")
    public void step3() {
        editName = "test16311";
        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.editBackup(page.backupName, editName, "edit");

        assertTrue(page.hasBackup(editName), "A backup should not be there");
    }

}
