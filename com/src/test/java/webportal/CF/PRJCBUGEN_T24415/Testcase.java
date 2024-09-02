package webportal.CF.PRJCBUGEN_T24415;

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
import webportal.param.WebportalParam;
import webportal.webelements.ContentFilteringElement;
import webportal.weboperation.ContentFilteringPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.GlobalNotificationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String SSID = (WebportalParam.OrbiDefaultSSID);
    String PASSWORD = (WebportalParam.OrbiDefaultPassword);
    

    @Feature("CF") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24415") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify enforce search toggle functionality") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24415") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
     System.out.println("start to do tearDown");
     String Num = "0";
     new ContentFilteringPage().EnableOrDisableCF(Num);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccountorbi();
    }

    @Step("Test Step 2: Send Catogory to blocked list;")
    public void step2() {
        
        String Num = "1";
        new GlobalNotificationPage().enterDeviceYes(WebportalParam.ob1serialNo);
        new ContentFilteringPage().gotoCF();
        new ContentFilteringPage().EnableOrDisableCF(Num);
        new ContentFilteringPage().EnableSafeSearch(Num);      
    }
    
    @Step("Test Step 3: Connect to defauld Orbi SSId ;")
    public void step3() {
        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID "+SSID )
                    .indexOf("true") != -1) {
                break;
            } else if (sum > 30) {
                assertTrue(false, "Client cannot connected.");
                break;
            }
            sum += 1;
        }

        boolean result1 = true;
        if (!new Javasocket()
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect "+ SSID +" "+ PASSWORD +  " WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect " + SSID +" "+ PASSWORD + " WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
    }
    
    @Step("Test Step 4: Check whether Job portal page is shown or not;")
    public void step4() {
        MyCommonAPIs.sleepi(80);
        assertTrue(
                new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                        "WAFruncaptive PRJCBUGEN-T14480.py www.hclipsex.com test test").indexOf("finalresult: 1") != -1,
                "Captive portal not take effect.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }
        

}
