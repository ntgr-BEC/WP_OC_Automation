package webportal.IGMP.Premium.PRJCBUGEN_T23101;

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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    HashMap<String, String> BackupInfo = new HashMap<String, String>();
    

    @Feature("IGMP_Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23101") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify if the feature IGMP is a part of backup and restore list ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23101") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().backupDelete(WebportalParam.location1, BackupInfo);
        new WirelessQuickViewPage().IGMPDisable();  
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        ;
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Enable IGMP and create backup;")
    public void step2() {
        
        BackupInfo.put("Name", "test23102");
        BackupInfo.put("Description", "test");
        new WirelessQuickViewPage().IGMPEnable();
        new AccountPage().ssidconfigbackupLocation(WebportalParam.location1, BackupInfo);
        assertTrue(
                new AccountPage().checkBackupIsExist(WebportalParam.location1, BackupInfo.get("Name")),"backup created sucessfully");
    }

    @Step("Test Step 3: Check whether connected connect is shown in client list;")
    public void step3() {
        new WirelessQuickViewPage().IGMPDisable();  
        new AccountPage().restoreBackup(WebportalParam.location1, BackupInfo.get("Name"));
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new RunCommand().enableSSH4AP(WebportalParam.ap1IPaddress, WebportalParam.loginPassword);
        MyCommonAPIs.sleepi(120);
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getIGMPEnableStatus(WebportalParam.ap1Model), "IGMP is disabled after restoring");
      
    }

}
