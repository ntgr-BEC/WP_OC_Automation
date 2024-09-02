package webportal.ClientIsolationWithDLAN.IncreaseClientIsolationFrom5to16.Pro.PRJCBUGEN_T40694;

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
            "www.rediff.com",  "www.yahoo.com","www.eeandu.net", "www.cricinfo.com", "www.yopmail.com",
            "www.netgear.com","www.insight.com","www.amazon.in","www.flipkart.com","www.play.google.com",
            "www.instagram.com","www.meesho.com","www.facebook.com","www.in.linkedin.com","www.zomato.com",
            "www.swiggy.com"
    };
    
    @Feature("ClientIsolationWithDLAN Enhance") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40694") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that in New F/W, added 5 Domain names,wired IP address and hostmanes after reboot device config should be same as earlier.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40694") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteSsidYes("apwp40694");

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
        locationInfo.put("SSID", "apwp40694");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid1(locationInfo);
  
       new WirelessQuickViewPage().AddDomain(locationInfo.get("SSID"), urls);
       assertTrue(new WirelessQuickViewPage().AddDomainisenabled(locationInfo.get("SSID")), "Able to add 16 urls in client isolation");
       
    }

}
