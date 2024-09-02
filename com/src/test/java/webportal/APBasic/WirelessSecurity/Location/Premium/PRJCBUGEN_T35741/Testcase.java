package webportal.APBasic.WirelessSecurity.Location.Premium.PRJCBUGEN_T35741;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author sjena
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> ssidInfo = new HashMap<String, String>();
    

    @Feature("WirelessSecurity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35741") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add ECP ssid With Fast Roaming enabled pro admin user and client connectivity") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T35741") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp35741");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Add WIFI ssid ")
    public void step2() {

        ssidInfo.put("SSID", "apwp35741");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);
        new WirelessQuickViewPage().enable80211(ssidInfo.get("SSID"));
        
    }
        
        @Step("Test Step 3: add ECP and connect a client")
        public void step3() {
            Map<String, String> ECPInfo = new HashMap<String, String>();

            
            ECPInfo.put("Walled Garden", "*.jazenetworks.com");
            ECPInfo.put("Splash Page URL", "http://portal.jazenetworks.com/netgear/");
            ECPInfo.put("Captive Portal Authentication Type", "Radius");
            ECPInfo.put("IPv4 Address", "180.179.56.164");
            ECPInfo.put("Password", "jazenetworks");
         

            new WirelessQuickViewPage().enableECP(ssidInfo.get("SSID"), ECPInfo);
            MyCommonAPIs.sleepi(180);
           
            String CMD = "WalledGarden" ;
            String Result = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD);
            System.out.println(Result);
            assertTrue(Result.contains(ECPInfo.get("Walled Garden")), "ECP walled Garden is not applied");
            
            
            String CMD1 = "SplashPageURL" ;
            String Result1 = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD1);
            System.out.println(Result1);
            assertTrue(Result1.contains("http\\://portal.jazenetworks.com/netgear/"), "ECP walled Garden is not applied");
            
            
            String CMD2 = "wlan0:vap7 | grep -i exCpPriRadAuthServer" ;
            String Result2 = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD2);
            System.out.println(Result2);
            assertTrue( Result2.contains(ECPInfo.get("IPv4 Address"))  && Result2.contains("1812"), "ECP walled Garden is not applied");
            
        //connect client 
            
            assertTrue(new WirelessQuickViewPage().connectClient(ssidInfo),"did not connect to client");
    }

    @Step("Test Step 4: Check whether connected connect is shown in client list;")
    public void step4() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }
    
}