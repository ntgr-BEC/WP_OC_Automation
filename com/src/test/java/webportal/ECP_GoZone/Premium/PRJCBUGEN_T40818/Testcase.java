package webportal.ECP_GoZone.Premium.PRJCBUGEN_T40818;

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

    @Feature("ECP_GoZone") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40818") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, ECP config is a part of first config") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T40818") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    
    }
    
    @Step("Test Step 2: Click on x on AP under device tab;")
    public void step2() {
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
    }

    @Step("Test Step 3: Add WIFI ssid with WPA2 Enterprise security and enable ECP;")
    public void step3() {

       
        ssidInfo.put("SSID", "apwp40818");
        ssidInfo.put("Security", "WPA3 Personal");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(ssidInfo);       
        ECPInfo.put("Walled Garden", "*.smartwifiplatform.com");
        ECPInfo.put("Walled Garden1", "*.4wifi-e2.net");
        ECPInfo.put("Splash Page URL", "https://splash.4wifi-e2.net/hotspotlogin.php");
        ECPInfo.put("Captive Portal Authentication Type", "Radius");
        ECPInfo.put("Primary Address", "3.132.31.3");
        ECPInfo.put("Secondary Address", "3.18.137.68");
        ECPInfo.put("PasswordGoZone", "Bijdragl");
        new WirelessQuickViewPage().enableECP(ssidInfo.get("SSID"), ECPInfo);
    }
       
    @Step("Test Step 4: add Ap")
    public void step4() {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        new DevicesDashPage(false).addNewDevice(devInfo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        
    }
    
    @Step("Test Step5:Check config push")
    public void step5()
    {
    String CMD = "WalledGarden" ;
    String Result = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD);
    System.out.println(Result);
    assertTrue(Result.contains(ECPInfo.get("Walled Garden")), "ECP walled Garden is not applied");
    assertTrue(Result.contains(ECPInfo.get("Walled Garden1")), "ECP walled Garden is not applied");
    
    
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
    assertTrue( Result3.contains(ECPInfo.get("Secondary Address"))  && Result3.contains("1813"), "Secondary Address not push");
        
    }
    

 
}
