package webportal.DeviceGroup.FeaturesInheritedfromLocation.PRJCBUGEN_T32447;

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

    @Feature("DeviceGroup FeaturesInheritedfromLocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32447") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify,SNMP is created for backup > on-board device to CGs then restored backup : verify SNMP config is restored on CG's device") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32447") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToSNMP(WebportalParam.location1);
        new AccountPage().backupDelete(WebportalParam.location1, BackupInfo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }

    

    @Step("Test Step 3: Go to SNMP and enable")
    public void step3() {
        new DeviceGroupPage().GoToSNMP(WebportalParam.location1);
        new DeviceGroupPage().EnableSNMP("1.1.1.1", "Automation1@");
        
        BackupInfo.put("Name", "test23092");
        BackupInfo.put("Description", "test");
        new AccountPage().ssidconfigbackupLocation(WebportalParam.location1, BackupInfo);

        assertTrue(
                new AccountPage().checkBackupIsExist(WebportalParam.location1, BackupInfo.get("Name")),"backup created sucessfully");
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToSNMP(WebportalParam.location1);
        new DeviceGroupPage().disableSNMP();       
        
    }
     
    
    @Step("Test Step 4: check whethe it is pushed to AP;")
    public void step4() {
   
        
        new AccountPage().restoreBackup(WebportalParam.location1, BackupInfo.get("Name"));
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        
        boolean status = false;
        
        String result = new APUtils(WebportalParam.ap1IPaddress).SNMPEnableStatus(WebportalParam.ap1Model);
        if(result.contains("snmpStatus 0") ) {
            status = true;
        }
        
        assertTrue(status == true , "syslog  is disabled after enabling");
    }
       

}
