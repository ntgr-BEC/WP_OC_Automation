package webportal.ConfigBackupAndRestore.Premium.PRJCBUGEN_T15900;

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
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    String backupName = "test15900";

    @Feature("ConfigBackupAndRestore.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15900") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify user can restore config for Orbi") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T15900") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob1serialNo);

        if (!new DevicesDashPage().getDeviceName(WebportalParam.ob1serialNo).contains(WebportalParam.ob1deveiceName)) {
//            new DevicesDashPage().editDeviceName(WebportalParam.ob1serialNo, WebportalParam.ob1deveiceName);
            new DevicesDashPage();
            new WirelessQuickViewPage(false).editswitchName(WebportalParam.ob1serialNo, WebportalParam.ob1deveiceName);
        }

        ddp.openOB1();
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
//        new DevicesDashPage().checkDutInAdminAccount(WebportalParam.ob1serialNo, WebportalParam.ob1deveiceName, WebportalParam.ob1deveiceMac);
    }

    @Step("Test Step 2: Create first backup;")
    public void step2() {
        ddp.gotoPage();
        ddp.openOB1();

        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage();
        page.deleteAllBackup();
        page.createBackup();
        assertTrue(page.hasBackup(), "A backup should be there");
    }

    @Step("Test Step 3: Edit device name and create second backup;")
    public void step3() {
        new DevicesDashPage();
        new WirelessQuickViewPage(false).editswitchName(WebportalParam.ob1serialNo, backupName);
        handle.gotoLoction();
        ddp.gotoPage();
        ddp.openOB1();

        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage();
        page.deleteAllBackup();
        page.createBackup(backupName, "test");
        assertTrue(page.hasBackup(backupName), "A backup should be there");

        page.restoreBackupNew();
    }

    @Step("Test Step 4: Check device name;")
    public void step4() {
        assertTrue(new DevicesDashPage().getDeviceName(WebportalParam.ob1serialNo).contains(WebportalParam.ob1deveiceName));
    }

}
