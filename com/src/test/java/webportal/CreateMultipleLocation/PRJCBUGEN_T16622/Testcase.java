package webportal.CreateMultipleLocation.PRJCBUGEN_T16622;

import static org.testng.Assert.assertTrue;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author 
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    Random              r                        = new Random();
    int                 num                      = r.nextInt(10000000);
    int                count                     =0;
    int                countSuss                 =0;
    int                countFail                 =0;
	int                countinternet             =0;

    @Feature("ICP.admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16622") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify instant captive portal funcationally when user select tier type as \"social login\" with only LinkedIn in loop")                                                                                                                                                                                                                                                                                                       // Case.
    @TmsLink("PRJCBUGEN-T16622") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
//        new WirelessQuickViewPage().deleteSsidYes(ssidInfo.get("SSID"));
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();

    }

    @Step("Test Step 2: Add WIFI ssid and enable enable instant captive portal, check client connect wifi;")
    public void step2() {
        
        for(int j=0; j<= 200; j++) {
               
        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID 3-MB-Op-ECP")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect 3-MB-Op-ECP")
                .equals("true")) {
            result1 = false;
            MyCommonAPIs.sleepi(20);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect 3-MB-Op-ECP")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
              
        MyCommonAPIs.sleepsync();
        
        
        String res = new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
              "WAFruncaptive PRJCBUGEN-T166352.py www.rediff.com test test");
        
						
		   if(res.indexOf("finalresult: 1") != -1) {
            			countSuss = countSuss+1;
			 System.out.println("No of count pass" + countSuss);
        }else {
            countFail= countFail+1;
			System.out.println("No of count fail " + countFail);
			
        }
         
            
        
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
        
        MyCommonAPIs.sleepi(1200);
		
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);

        new DevicesApSummaryPage().clickReboot();

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        
        }
        
        System.out.println("No of count fail " + countFail);
        System.out.println("No of count pass" + countSuss);

    }


}