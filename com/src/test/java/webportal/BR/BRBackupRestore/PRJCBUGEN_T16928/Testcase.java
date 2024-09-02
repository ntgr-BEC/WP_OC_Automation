package webportal.BR.BRBackupRestore.PRJCBUGEN_T16928;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DeviceBackupRestorePage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    DeviceBackupRestorePage brPage;
    String                  backStoreName = "new name";
    String                  backStoreDesc = "new desc";
    
    @Feature("BR.BRBackupRestore") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16928") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("004-Update name and description") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16928") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        brPage = new DeviceBackupRestorePage(WebportalParam.br1serialNo);
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage();
        page.deleteBackups();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM APP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        ddp.gotoPage();
        ddp.openBR1();
    }
    
    @Step("Test Step 2: Update backup name and description")
    public void step2() {
        brPage.gotoPage();
        brPage.deleteBackups();
        brPage.createBackup();
        handle.refresh();

        brPage.modifyBackName(backStoreName, backStoreDesc);
        MyCommonAPIs.sleepi(10);
        assertTrue(brPage.hasBackup(backStoreName), backStoreName + " is updated");
        assertTrue(handle.pageSource().contains(backStoreDesc), backStoreDesc + " is updated");
    }
}
