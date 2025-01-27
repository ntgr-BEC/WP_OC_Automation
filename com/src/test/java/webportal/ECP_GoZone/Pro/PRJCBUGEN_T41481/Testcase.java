package webportal.ECP_GoZone.Pro.PRJCBUGEN_T41481;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Ravishankar S
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> ssidInfo = new HashMap<String, String>();
    Map<String, String> ECPInfo = new HashMap<String, String>();
    Map<String, String> ECPInfoGoZone = new HashMap<String, String>();

    @Feature("ECP_GoZone") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41481") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, Change Config from Jaze toGoZone") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41481") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteALLSSID();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        handle.gotoLoction();
       
    }

    @Step("Test Step 2: Add WIFI ssid with WPA2 Enterprise security and enable ECP;")
    public void step2() {
        
        ssidInfo.put("SSID", "apwp41481");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(ssidInfo); 
        Map<String, String> ECPInfo = new HashMap<String, String>();
        ECPInfo.put("Walled Garden", "*.jazenetworks.com");
        ECPInfo.put("Splash Page URL", "http://portal.jazenetworks.com/netgear/");
        ECPInfo.put("Captive Portal Authentication Type", "Radius");
        ECPInfo.put("IPv4 Address", "180.179.56.164");
        ECPInfo.put("Password", "jazenetworks");


        new WirelessQuickViewPage().enableECP(ssidInfo.get("SSID"), ECPInfo);
        MyCommonAPIs.sleepi(60);
    }
        
        
        @Step("Test Step 3: Check config ppush;")
        public void step3() {
            String CMD = "WalledGarden" ;
            String Result = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD);
            System.out.println(Result);
            assertTrue(Result.contains(ECPInfo.get("Walled Garden")), "ECP walled Garden is not applied");
            
            
            String CMD1 = "SplashPageURL" ;
            String Result1 = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD1);
            System.out.println(Result1);
            assertTrue(Result1.contains("http\\://portal.jazenetworks.com/netgear/"), "ECP SplashPageURL is not applied");
            
            
            String CMD2 = "wlan0:vap7 | grep -i exCpPriRadAuthServer" ;
            String Result2 = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD2);
            System.out.println(Result2);
            assertTrue(Result2.contains(ECPInfo.get("IPv4 Address"))  && Result2.contains("1812"), "ECP RadAuthServer is not applied");
            
        }
        
        @Step("Test Step 4: Edit ssid to JazzNetwork;")
        public void step4() {

            ECPInfoGoZone.put("Splash Page URL", "https://splash.4wifi-e2.net/hotspotlogin.php");
            ECPInfoGoZone.put("Captive Portal Authentication Type", "Radius");
            ECPInfoGoZone.put("Primary Address", "3.132.31.3");
            ECPInfoGoZone.put("Secondary Address", "3.18.137.68");
            ECPInfoGoZone.put("PasswordGoZone", "Bijdragl");
            ECPInfoGoZone.put("Walled Garden", "*.smartwifiplatform.com");
            ECPInfoGoZone.put("Walled Garden1", "*.4wifi-e2.net");
            new WirelessQuickViewPage().editmodeECP(ssidInfo.get("SSID"), ECPInfoGoZone);
         
    }
         
    @Step("Test Step 5: add ECP;")
    public void step5() {
        Map<String, String> ECPInfo = new HashMap<String, String>();

        new WirelessQuickViewPage().editmodeECP(ssidInfo.get("SSID"), ECPInfo);
        MyCommonAPIs.sleepi(60);
       
        String CMD = "WalledGarden" ;
        String Result = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD);
        System.out.println(Result);
        assertTrue(Result.contains(ECPInfo.get("Walled Garden")), "ECP walled Garden is not applied");
        assertTrue(Result.contains(ECPInfo.get("Walled Garden1")), "ECP walled1 Garden is not applied");
        
        
        String CMD1 = "SplashPageURL" ;
        String Result1 = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD1);
        System.out.println(Result1);
        assertTrue(Result1.contains("https\\://splash.4wifi-e2.net/hotspotlogin.php"), "Url Not Pushed");
        
        String CMD2 = "wlan0:vap7 | grep -i exCpPriRadAuthServer" ;
        String Result2 = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD2);
        System.out.println(Result2);
        assertTrue( Result2.contains(ECPInfo.get("Primary Address"))  && Result2.contains("1812"), "Primary Address not pushed");
        
        String CMD3 = "wlan0:vap7 | grep -i exCpSecRadAcntServer" ;
        String Result3 = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD3);
        System.out.println(Result3);
        assertTrue( Result3.contains(ECPInfo.get("Secondary Address"))  && Result3.contains("1813"), "Secondary Address not pushed");
        
        
    }

 
}
