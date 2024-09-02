package webportal.ClientIsolationWithDLAN.Enhancement.Pro.PRJCBUGEN_T31781;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.GlobalNotificationPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredVLANPageForVLANPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String urls[] = {
            "www.rediff.com"
    };
    
    @Feature("ClientIsolationWithDLAN Enhance") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31781") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, CI is seen on Wireless page SSID, Network level SSID, Wired page SSID and Org-wide SSID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31781") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteSsidYes("apwp14481");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin"); // must input admin or manager
    }

     @Step("Test Step 2: Create SSID with wireless ssid page")
     public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp14481");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid1(locationInfo);
  
       new WirelessQuickViewPage().AddDomain(locationInfo.get("SSID"), urls);
          
    }

     @Step("Test Step 3: Create SSID with edit Network page")
     public void step3() {
         new WirelessQuickViewPage().deleteALLSSID();
         
         new WiredVLANPageForVLANPage().CreateVLANToAddSSID();
         
         Map<String, String> locationInfo7 = new HashMap<String, String>();
         locationInfo7.put("SSID", "apwp14481a");
         locationInfo7.put("Band", "Both");
         locationInfo7.put("Security", "WPA2 Personal");
         locationInfo7.put("Password", "123456798");
         new WirelessQuickViewPage(false).addSsidFromVLANClientIso(locationInfo7, urls);
          
    }

     @Step("Test Step 4: Create SSID with edit wired page")
     public void step4() {
         new WirelessQuickViewPage().deleteALLSSID();
         
         handle.gotoLoction();
         
         new GlobalNotificationPage().EnterWirelessSettingpage();  
         
         new GlobalNotificationPage().addssidViaWired();  
         
         
         Map<String, String> locationInfo7 = new HashMap<String, String>();
         locationInfo7.put("SSID", "apwp14481b");
         locationInfo7.put("Band", "Both");
         locationInfo7.put("Security", "WPA2 Personal");
         locationInfo7.put("Password", "123456798");
         new WirelessQuickViewPage(false).addSsidFromVLANClientIso(locationInfo7, urls);
          
    }

     @Step("Test Step 5: Create org wide ssid")
     public void step5() {
         new WirelessQuickViewPage().deleteALLSSID();
         
         Map<String, String> locationInfo = new HashMap<String, String>();
         locationInfo.put("SSID", "apwp14270");
         locationInfo.put("Security", "WPA2 Personal");
         locationInfo.put("Password", "123456798");
         assertTrue(new OrganizationPage(false).organizationWideSSID(locationInfo)," Organization wide SSID is not created successfully.");
         new OrganizationPage(false).editOrgnaizationwideSSIDClientiso(locationInfo, urls);
    }
}
