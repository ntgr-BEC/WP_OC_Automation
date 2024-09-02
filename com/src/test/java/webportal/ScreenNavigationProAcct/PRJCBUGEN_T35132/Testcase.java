package webportal.ScreenNavigationProAcct.PRJCBUGEN_T35132;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DeviceScreenNavigationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha Hdone
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("ScreenNavigationProAcct") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35132") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Notification bell icon navigation of All Setting Tabs ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35132") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
  

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Edit Wifi ssid and let client connect it;")
    public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "PRJCBUGEN_T35132");
        locationInfo.put("Security", "WPA3 Personal");
        locationInfo.put("Password", "123456798");
        locationInfo.put("Band", "Uncheck5 GHz");
        new WirelessQuickViewPage().addAndEdit(locationInfo);
    }  

    @Step("Test Step 3: Verify Notification bell page;")
    public void step3() {
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.gotoPage();
        assertTrue(new  DeviceScreenNavigationPage().verifyNotificationsBellIcon()," NotificationsBellIcon Screen is not complete");
        MyCommonAPIs.sleepi(15);
        
    }
    }
    
    