package webportal.RadioLevelConfigurations.PRJCBUGEN_T24838;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    HashMap<String, String> BackupInfo = new HashMap<String, String>();
    
    String cmd = "iwconfig wifi0vap0 | grep Access";

    @Feature("RadioLevelConfigurations") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24838") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify, network level radio and channel config is a part of back up and restore") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24838") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().backupDelete(WebportalParam.location1, BackupInfo);  
        System.out.println("start to do tearDown");
      
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    
    @Step("Test Step 2: Create SSid")
    public void step2() {
              
        new WirelessQuickViewPage().deleteALLSSID();
        Map<String, String> locationInfo7 = new HashMap<String, String>();
        locationInfo7.put("SSID", "ssid24");
        locationInfo7.put("Band", "Both");
        locationInfo7.put("Security", "WPA2 Personal");
        locationInfo7.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo7);
        
      
    }
    
    @Step("Test Step 3:  create backup;")
    public void step3() {

        BackupInfo.put("Name", "Backup1");
        BackupInfo.put("Description", "check");
        new AccountPage().ssidconfigbackupLocation(WebportalParam.location1, BackupInfo);
        assertTrue(
                new AccountPage().checkBackupIsExist(WebportalParam.location1, BackupInfo.get("Name")),"backup created sucessfully");
    
    }

    
    @Step("Test Step 4: Disable band ")
    public void step4() {
        String Enable = "0";
        new WirelessQuickViewPage().GoToWirelessSettings();
        new WirelessQuickViewPage(false).Enable24(Enable);
        new WirelessQuickViewPage(false).DropDown5GhzLowWireless.click();
        new WirelessQuickViewPage(false).Enable5low(Enable);
        new WirelessQuickViewPage(false).DropDown5GhzHighWireless.click();
        new WirelessQuickViewPage(false).Enable5high(Enable); 
    }
    
    @Step("Test Step 5: restore and check;")
    public void step5() {
        new AccountPage().restoreBackup(WebportalParam.location1, BackupInfo.get("Name"));
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage().GoToWirelessSettings();
        assertTrue(new WirelessQuickViewPage(false).EnableorDisable24(),"2.4 is not enabled by default");
        new WirelessQuickViewPage(false).DropDown5GhzLowWireless.click();
        assertTrue(new WirelessQuickViewPage(false).EnableorDisable5High(),"5hgh is not enabled by default");
        new WirelessQuickViewPage(false).DropDown5GhzHighWireless.click();
        assertTrue(new WirelessQuickViewPage(false).EnableorDisable5Low(),"5 low is not enabled by default");
      
    }
    
 

}
