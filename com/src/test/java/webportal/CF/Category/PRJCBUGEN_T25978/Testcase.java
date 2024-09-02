package webportal.CF.Category.PRJCBUGEN_T25978;

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
    
    String IP[] = {
            "18.192.201.201", "3.125.100.210", "13.52.132.95", "54.183.58.102"
    };
    String URL = "Religion";
    String SSID = (WebportalParam.OrbiDefaultSSID);
    String PASSWORD = (WebportalParam.OrbiDefaultPassword);
    

    @Feature("CF") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25978") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Religion category blocking URL.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25978") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
     String Num = "0";   
     System.out.println("start to do tearDown");
     new ContentFilteringPage().SendtoAllow(URL); 
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
        new ContentFilteringPage().SendtoDeny(URL);      
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

         String ipaddress = WebportalParam.clientip;
         
         System.out.println(ipaddress);
         String output = new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                   "nslookup thearda.com");
         System.out.println(output);
         
         boolean finalresult = false;       
    
        for (int i=0; i<= IP.length ; i++) {
            
            if(output.contains(IP[i])) {
                System.out.println(IP[i]);
                finalresult =true;
                break;
             }else {
                 System.out.println("domain is not blocked");
             }
        }
        
        assertTrue(finalresult, "domain is not blocked");
        
              
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }
        

}
