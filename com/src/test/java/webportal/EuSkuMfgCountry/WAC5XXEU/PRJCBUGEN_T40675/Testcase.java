

package webportal.EuSkuMfgCountry.WAC5XXEU.PRJCBUGEN_T40675;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.support.ui.Sleeper;
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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("EuSkuMfgCountry.WAX5XXEU") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40675") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add AP WAX5XX in Slovenia Location") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T40675") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation(WebportalParam.location1);
        System.out.println("start to do tearDown");  
    }
    
    @Step("Test Step 1: Log in to a premium account and create a location")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
       // locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "Slovenia");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();
      }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Go to Location and onboard a device")
    public void step2() {
        handle.gotoLoction();
        new DevicesDashPage().AddNewDevice(WebportalParam.ap1serialNo,WebportalParam.ap1macaddress);
        MyCommonAPIs.sleepi(10);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        MyCommonAPIs.sleepi(10);
    }

    @Step("Test Step 2: Add WIFI ssid and now connect client to this ssid;")
    public void step3() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp40675");
        locationInfo.put("Security", "WPA2 Personal");
//        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo);

        assertTrue(new WirelessQuickViewPage().connectClient(locationInfo),"did not connect to client");
    }

    @Step("Test Step 3: Check whether connected connect is shown in client list;")
    public void step4() {
        //new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }


    
}
