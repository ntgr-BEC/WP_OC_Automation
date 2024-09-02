package webportal.ConfigBackupAndRestore.Premium.PRJCBUGEN_T15889;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceBackupRestorePage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random              r            = new Random();
    int                 num          = r.nextInt(10000000);
    String              mailname     = "apwptest" + String.valueOf(num);
    String              locationName = "test15889";
    Map<String, String> devInfo      = new HashMap<String, String>();

    @Feature("ConfigBackupAndRestore.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15889") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify basic user can configure backup checkpoints from web portal at device level for BR500") // It's a test case title from Jira
                                                                                                                 // Test Case.
    @TmsLink("PRJCBUGEN-T15889") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        UserManage userManage = new UserManage();
        userManage.logout();

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
        if (new DevicesDashPage().getDeviceName(devInfo.get("Serial Number")).equals("")) {
            new DevicesDashPage().addNewDevice(devInfo);
            new DevicesDashPage().waitDevicesReConnected(devInfo.get("Serial Number"));
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        devInfo.put("Serial Number", WebportalParam.br1serialNo);
        devInfo.put("Device Name", WebportalParam.br1deveiceName);
        devInfo.put("Mac Address", WebportalParam.br1deveiceMac);
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
//        new DevicesDashPage().checkDutInAdminAccount(devInfo.get("Serial Number"), devInfo.get("Device Name"),devInfo.get("MAC Address"));
    }

    @Step("Test Step 2: Create new account and try trial;")
    public void step2() {
        new DevicesDashPage().deleteDeviceYes(devInfo.get("Serial Number"));

        UserManage userManage = new UserManage();
        userManage.logout();

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T15549");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
        new HamburgerMenuPage(false).closeLockedDialog();
//        assertTrue(new HamburgerMenuPage().checkAccountTryTrial(), "Account try trial unsuccessful.");
    }

    @Step("Test Step 3: Add new location and add one switch, check create new backup checkpoint;")
    public void step3() {
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);
        new AccountPage().enterLocation(locationInfo.get("Location Name"));
        new DevicesDashPage().addNewDevice(devInfo);
        new DevicesDashPage().waitDevicesReConnected(devInfo.get("Serial Number"));

        ddp.gotoPage();
        ddp.openBR1();

        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage();
        page.createBackup();
        assertTrue(page.hasBackup(), "A backup should be there");

        new DevicesDashPage().deleteDeviceYes(devInfo.get("Serial Number"));
    }

}
