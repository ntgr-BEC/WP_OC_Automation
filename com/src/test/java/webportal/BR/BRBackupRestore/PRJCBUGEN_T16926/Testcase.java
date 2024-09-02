package webportal.BR.BRBackupRestore.PRJCBUGEN_T16926;

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
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    DeviceBackupRestorePage brPage;
    boolean                 restartedBrower = false;

    @Feature("BR.BRBackupRestore") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16926") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("002-Download configuration and Upload configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16926") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        brPage = new DeviceBackupRestorePage(WebportalParam.br1serialNo);
        if (WebportalParam.enableHeadless) {
            restartedBrower = true;
            WebportalParam.browserIsFailed = true;
            WebportalParam.enableHeadless = false;
            TestCaseBase.startBrowser();
        }
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            DeviceBackupRestorePage page = new DeviceBackupRestorePage();
            page.gotoPage();
            page.deleteBackups();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (restartedBrower) {
            WebportalParam.browserIsFailed = true;
            WebportalParam.enableHeadless = true;
            TestCaseBase.startBrowser();
        }
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
    
    @Step("Test Step 2: Download/upload the configuration file")
    public void step2() {
        brPage.gotoPage();
        brPage.deleteBackups();
        brPage.createBackup();

        brPage.deleteDownloadFiles();
        brPage.downloadBackup();
        
        brPage.deleteBackups();
        brPage.uploadBackup();
    }

    @Step("Test Step 3: Restore configuration file use uploaded file")
    public void step3() {
        brPage.gotoPage();
        brPage.restoreBackup();

        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.br1serialNo);
        assertTrue(ddp.isDeviceConnected(WebportalParam.br1serialNo), "device must be online after restore");
    }
}
