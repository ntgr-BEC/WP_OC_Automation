package webportal.DeviceGroup.CustomGroupNPI.WithAP.PRJCBUGEN_T32382;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.webelements.DeviceGroupElement;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    OrganizationPage OrganizationPage = new OrganizationPage();
    Map<String, String> locationInfo = new HashMap<String, String>();
    String urls[] = {
            "www.rediff.com",  "www.yahoo.com","www.eeandu.net", "www.cricinfo.com", "www.yopmail.com"
    };
    

    @Feature("DeviceGroup CustomGroupNPI") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32382") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("test to verify, SSID with CI") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32382") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().goinsideDG("Automation1");
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().DGWiFi.click();
        MyCommonAPIs.sleepi(5);
        new WirelessQuickViewPage(false).deleteSsidYes(locationInfo.get("SSID"));
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }

    @Step("Test Step 2: Check CG name and description shown;")
    public void step2() {
        
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().goinsideDG("Automation1");
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().DGWiFi.click();
       
    }
    
    @Step("Test Step 3: Add WIFI ssid with CI enabled;")
    public void step3() {
        
       
        locationInfo.put("SSID", "apwp21414");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo);
               
  
       new WirelessQuickViewPage(false).AddDomain(locationInfo.get("SSID"), urls);
       assertTrue(new WirelessQuickViewPage(false).AddDomainisenabled(locationInfo.get("SSID")), "add button is not disabled");
        
    }
    
    @Step("Test Step 4: Check domain name is pushed to AP")
    public void step4() {
        
        boolean result = false;
        int count = 0;
        while (count < 5) {
            MyCommonAPIs.sleepsync();
            String check = new APUtils(WebportalParam.ap1IPaddress).getSsidStatusClientIso(locationInfo.get("SSID"), WebportalParam.ap1Model);
            if (check.contains(urls[0])) {
                result = true;
                break;
            }
            count += 1;
        }
        assertTrue(result, "SSID delete failed");
        
        
    
    }

}
