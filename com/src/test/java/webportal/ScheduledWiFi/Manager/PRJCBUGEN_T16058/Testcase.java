package webportal.ScheduledWiFi.Manager.PRJCBUGEN_T16058;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author pragya
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("ScheduledWiFi.Manager") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16058") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify if user updates the timezone in Network location it gets updated in the WiFi Scheduler page.Manager") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16058") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);
    }


    @Step("Test Step 2: update network and check time zone of scheduled ssid")
    public void step2() {
        AccountPage AccountPage = new AccountPage();
        
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        String networktimezone, wifischeduletimezone;
        int a=0;
        networktimezone= AccountPage.updatenetworktimezone(WebportalParam.location1,locationInfo);
        System.out.println("Network Time Zone- "+networktimezone);
        a=networktimezone.length();
        networktimezone=networktimezone.substring(11,(a-1));
        System.out.println("Corrected Network Time Zone- "+networktimezone);
       
        locationInfo.put("Security", "Open");
        WirelessQuickViewPage QuickView=new WirelessQuickViewPage();
        wifischeduletimezone= QuickView.ssidtimezoneverification(locationInfo);
        System.out.println("Schedule Time Zone- "+wifischeduletimezone);
        wifischeduletimezone=wifischeduletimezone.substring(20, wifischeduletimezone.indexOf(" ("));
        System.out.println("Corrected Schedule Time Zone- "+wifischeduletimezone);
        
        Assert.assertTrue(networktimezone.equals(wifischeduletimezone),"Timezones are different.");
        
        networktimezone= AccountPage.setfirstnetworktimezonevalue(WebportalParam.location1,locationInfo);
        
    }
}
