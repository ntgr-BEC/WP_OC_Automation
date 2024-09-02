package webportal.CFD.CFD_Pro.AP.PRJCBUGEN_T29376;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    Random              r                        = new Random();
    int                 num                      = r.nextInt(10000000);
    String organizationName = "PRJCBUGEN_T29376";
    String locationName     = "Location1";
    String locationName1     = "Location2";
    Map<String, String> organizationInfo = new HashMap<String, String>();
    OrganizationPage OrganizationPage = new OrganizationPage();

    @Feature("ICP.admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T29376") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify icp funcationally after moving ap to new location") 
    @TmsLink("PRJCBUGEN-T29376") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganizationNew(organizationName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }
       
    
    @Step("Test Step 2: Create a organization and location and allocate credit")
    public void step2() {
       
        organizationInfo.put("Name", organizationName);       
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);

        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.sleepi(30);  
        
        HashMap<String, String> locationInfo1 = new HashMap<String, String>();
        locationInfo1.put("Location Name", locationName1);
        locationInfo1.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo1.put("Zip Code", "12345");
        locationInfo1.put("Country", "United States of America");
        new AccountPage(false).addNetwork(locationInfo1);
        MyCommonAPIs.sleepi(30);  
        
        new HamburgerMenuPage().configCreditAllocation(organizationName, Integer.valueOf("2"), 0, Integer.valueOf("3"));
        
    }
    
    @Step("Test Step 3: Add device To the Network;")
    public void step3() {
//        OrganizationPage.openOrg(organizationName);
        new AccountPage().enterLocation(locationName);
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        Map<String, String> firststdevInfo1 = new HashMap<String, String>();

        
        firststdevInfo.put("Serial Number1", WebportalParam.ap2serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap2macaddress);       
        firststdevInfo1.put("Serial Number1", WebportalParam.ap1serialNo);
        firststdevInfo1.put("MAC Address1", WebportalParam.ap1macaddress);
      
        System.out.println(firststdevInfo);
        System.out.println(firststdevInfo1);

                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo1);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);     
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);     
    }
     

    @Step("Test Step 4: Add WIFI ssid and enable enable instant captive portal, check client connect wifi;")
    public void step4() {
        if (!new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            assertTrue(false, "Account need to add instant captive portal key.");
        }

        ssidInfo.put("SSID", "Location1");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        ssidInfo.put("Band", "check only 6ghz");
        new WirelessQuickViewPage().addSsid1(ssidInfo);

        Map<String, String> icpInfo = new HashMap<String, String>();
        icpInfo.put("Portal Name", "BEC Automation");
        icpInfo.put("Welcome Headline", "Welcome to Automation");
        icpInfo.put("Captive Portal Logo", "DEFAULT_LOGO");
        icpInfo.put("Desktop Background Image", "DEFAULT_BG");
        icpInfo.put("Landing Page URL", "https://www.rediff.com");
        icpInfo.put("Session Duration", "5 min");
        icpInfo.put("Step Type", "Authentication Method");
        icpInfo.put("Login Modes", "Twitter.");
        new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfo);

        MyCommonAPIs.sleepi(3 * 60);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID Location1")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect Location1 12345678 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            MyCommonAPIs.sleepi(20);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect Location1 12345678 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");

    }

    @Step("Test Step 5: Check whether captive portal page is shown or not;")
    public void step5() {
        MyCommonAPIs.sleepsync();
        assertTrue(
                new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                        "WAFruncaptive PRJCBUGEN-T16638.py www.rediff.com test test").indexOf("finalresult: 1") != -1,
                "Captive portal not take effect.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }
    
    @Step("Test Step 6: Delete AP")
    public void step6() {
    new AccountPage().enterLocation(locationName);
        
    new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
    new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap2serialNo);
    }
    
    @Step("Test Step 7: Add device To the Network;")
    public void step7() {
        OrganizationPage.openOrg(organizationName);
        new AccountPage().enterLocation(locationName1);
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        Map<String, String> firststdevInfo1 = new HashMap<String, String>();

        
        firststdevInfo.put("Serial Number1", WebportalParam.ap2serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap2macaddress);       
        firststdevInfo1.put("Serial Number1", WebportalParam.ap1serialNo);
        firststdevInfo1.put("MAC Address1", WebportalParam.ap1macaddress);
      
        System.out.println(firststdevInfo);
        System.out.println(firststdevInfo1);

                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo1);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);     
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);     
    }
     

    @Step("Test Step 8: Add WIFI ssid and enable enable instant captive portal, check client connect wifi;")
    public void step8() {
        if (!new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            assertTrue(false, "Account need to add instant captive portal key.");
        }

        ssidInfo.put("SSID", "Location2");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        ssidInfo.put("Band", "check only 6ghz");
        new WirelessQuickViewPage().addSsid1(ssidInfo);

        Map<String, String> icpInfo = new HashMap<String, String>();
        icpInfo.put("Portal Name", "BEC Automation");
        icpInfo.put("Welcome Headline", "Welcome to Automation");
        icpInfo.put("Captive Portal Logo", "DEFAULT_LOGO");
        icpInfo.put("Desktop Background Image", "DEFAULT_BG");
        icpInfo.put("Landing Page URL", "https://www.rediff.com");
        icpInfo.put("Session Duration", "5 min");
        icpInfo.put("Step Type", "Authentication Method");
        icpInfo.put("Login Modes", "Twitter.");
        new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfo);

        MyCommonAPIs.sleepi(3 * 60);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID Location2")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect Location2 12345678 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            MyCommonAPIs.sleepi(20);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect Location2 12345678 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");

    }

    @Step("Test Step 9: Check whether captive portal page is shown or not;")
    public void step9() {
        MyCommonAPIs.sleepsync();
        assertTrue(
                new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                        "WAFruncaptive PRJCBUGEN-T16638.py www.rediff.com test test").indexOf("finalresult: 1") != -1,
                "Captive portal not take effect.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }
    
    
}