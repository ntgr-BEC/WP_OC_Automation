package webportal.InstantWIFI.PRJCBUGEN_T31738;

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
import util.APUtils;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    String              region       = "";
    Map<String, String> locationInfo = new HashMap<String, String>();
    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);

    @Feature("Instant WIFI 6.9") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31738") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Instant Wifi with non default   channel  for  NA SKU  WAC510 ,WAC505 ,WAC540 and  WAC564") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31738") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("OnBoardingTest");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }


    @Step("Test Step 2: create a location;")
    public void step2() {
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "600-8307");
        locationInfo.put("Country", "US");
        new AccountPage().addNetwork(locationInfo);
        new AccountPage(false).enterLocation(WebportalParam.location1);
        
    }
    
    
    @Step("Test Step 3: onboard AP;")
    public void step3() {
       WebCheck.checkHrefIcon(URLParam.hrefDevices);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        new DevicesDashPage(false).addNewDevice(devInfo);
       
        
        Map<String, String> devInfo1 = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap2serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap2macaddress);
        new DevicesDashPage(false).addNewDevice(devInfo);
      
        
        
        Map<String, String> devInfo2 = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap3serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap3macaddress);
        new DevicesDashPage(false).addNewDevice(devInfo);
       
        
        Map<String, String> devInfo3 = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap4serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap4macaddress);
        new DevicesDashPage(false).addNewDevice(devInfo);
        
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);        
        new RunCommand().enableSSH4AP(WebportalParam.loginPassword, WebportalParam.ap1IPaddress);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);        
        new RunCommand().enableSSH4AP(WebportalParam.loginPassword, WebportalParam.ap2IPaddress);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap3serialNo);
        new RunCommand().enableSSH4AP(WebportalParam.loginPassword, WebportalParam.ap3IPaddress);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap4serialNo);
        new RunCommand().enableSSH4AP(WebportalParam.loginPassword, WebportalParam.ap4IPaddress);
    }
    
    @Step("Test Step 3: Create SSID and click optimize now button, then check ap log;")
    public void step5() {

        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp18178");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
        new APUtils(WebportalParam.ap2IPaddress).deleteMessages();
        new APUtils(WebportalParam.ap3IPaddress).deleteMessages();
        new APUtils(WebportalParam.ap4IPaddress).deleteMessages();
        if (!new WirelessQuickViewPage().optimizeInstantWifinondefault(false)) {
            int i = 0;
            while (i < 2) {
                MyCommonAPIs.sleepi(10 * 60);
                new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
                if (new WirelessQuickViewPage().optimizeInstantWifinondefault(false)) {
                    break;
                }
            }
        }

        int count = 0;
        while (true) {
            MyCommonAPIs.sleepsync();
            if (new APUtils(WebportalParam.ap1IPaddress).checkInstantWifiProfile(false)) {
                assertTrue(true);
                break;
            } else if (count == 10) {
                assertTrue(false, "Instant wifi logs not output.");
                break;
            }
            count += 1;
        }
        
        int count2 = 0;
        while (true) {
            MyCommonAPIs.sleepsync();
            if (new APUtils(WebportalParam.ap2IPaddress).checkInstantWifiProfile(false)) {
                assertTrue(true);
                break;
            } else if (count == 10) {
                assertTrue(false, "Instant wifi logs not output.");
                break;
            }
            count2 += 1;
        }
        
        int count3 = 0;
        while (true) {
            MyCommonAPIs.sleepsync();
            if (new APUtils(WebportalParam.ap3IPaddress).checkInstantWifiProfile(false)) {
                assertTrue(true);
                break;
            } else if (count == 10) {
                assertTrue(false, "Instant wifi logs not output.");
                break;
            }
            count3 += 1;
        }
        
        int count4 = 0;
        while (true) {
            MyCommonAPIs.sleepsync();
            if (new APUtils(WebportalParam.ap4IPaddress).checkInstantWifiProfile(false)) {
                assertTrue(true);
                break;
            } else if (count == 10) {
                assertTrue(false, "Instant wifi logs not output.");
                break;
            }
            count4 += 1;
        }

    }
}

