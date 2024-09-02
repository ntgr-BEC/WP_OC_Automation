package webportal.DeviceGroup.FeaturesInheritedfromLocation.PRJCBUGEN_T32440;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    OrganizationPage OrganizationPage = new OrganizationPage();
    HashMap<String, String> BackupInfo = new HashMap<String, String>();
    int check = 1;
    int check1 = 2;

    @Feature("DeviceGroup FeaturesInheritedfromLocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32440") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, LED settings are backed up > on-board device to CG then restored backup: verify, LED settings is NOT restored on CG's device") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32440") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToLED(WebportalParam.location1);
        new DeviceGroupPage().SetLED(0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }

    

    @Step("Test Step 3: Go to LED and enable")
    public void step3() {
        new DeviceGroupPage().GoToLED(WebportalParam.location1);
        new DeviceGroupPage().SetLED(check1);
        
        BackupInfo.put("Name", "test23092");
        BackupInfo.put("Description", "test");
        new AccountPage().ssidconfigbackupLocation(WebportalParam.location1, BackupInfo);

        assertTrue(
                new AccountPage().checkBackupIsExist(WebportalParam.location1, BackupInfo.get("Name")),"backup created sucessfully");     

    }
     
    
    @Step("Test Step 4: check whethe it is pushed to AP;")
    public void step4() {
   
        boolean status = false;
        
        new AccountPage().restoreBackup(WebportalParam.location1, BackupInfo.get("Name"));
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
 
        String result = new APUtils(WebportalParam.ap1IPaddress).LEDEnableStatus(WebportalParam.ap1Model);
        if(result.contains("ledControl 2")) {
            status = true;
        }
        
        assertTrue(status == true , "syslog  is disabled after enabling");
    }
       

}
