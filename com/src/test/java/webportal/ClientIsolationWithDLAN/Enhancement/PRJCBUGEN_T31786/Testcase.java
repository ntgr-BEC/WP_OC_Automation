package webportal.ClientIsolationWithDLAN.Enhancement.PRJCBUGEN_T31786;

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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredVLANPageForVLANPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    Map<String, String> locationInfo = new HashMap<String, String>();
    String urls[] = {
            "www.rediff.com"
    };
    
    @Feature("ClientIsolationWithDLAN Enhance") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31786") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, CI is supported on all WAC500 and WAX600 models") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31786") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteSsidYes("apwp14486");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

     @Step("Test Step 2: Create SSID with wireless ssid page")
     public void step2() {
         new WirelessQuickViewPage().deleteALLSSID();
        locationInfo.put("SSID", "apwp14486");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid1(locationInfo);
  
       new WirelessQuickViewPage().AddDomain(locationInfo.get("SSID"), urls);
          
    }
     
     @Step("Test Step 3: Check domain name is pushed to AP")
     public void step3() {
         
         boolean result = false;
         int count = 0;
         while (count < 5) {
             MyCommonAPIs.sleepsync();
             String check = new APUtils(WebportalParam.ap1IPaddress).getSsidStatusClientIso("apwp14486", WebportalParam.ap1Model);
             if (check.contains(urls[0])) {
                 result = true;
                 break;
             }
             count += 1;
         }
         assertTrue(result, "SSID delete failed");
         
         
     
     }

 
}
