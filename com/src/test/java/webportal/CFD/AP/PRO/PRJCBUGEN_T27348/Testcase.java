package webportal.CFD.AP.PRO.PRJCBUGEN_T27348;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini 
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    String locationName     = "BulkOnboarding";

    @Feature("AP") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27348") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("copy location configure functionally with in organization")
    @TmsLink("PRJCBUGEN-T27348") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation(locationName);
        new WirelessQuickViewPage().deleteALLSSID();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
    }
        
        @Step("Test Step 2: Add 8  ssid in first location;")
        public void step2() {
            
            ArrayList<String> list = new ArrayList<String>();
            Random r = new Random();
             
            int i;
            for(i=0;i<=6;i++) {
            int num = r.nextInt(10000);
            String mailname = "ssid" + String.valueOf(num);
            list.add(mailname);
            }
            System.out.println(list);
            
            new WirelessQuickViewPage().deleteALLSSID();
            
            Map<String, String> locationInfo7 = new HashMap<String, String>();
            locationInfo7.put("SSID", "apwp21945");
            locationInfo7.put("Band", "5 GHz");
            locationInfo7.put("Security", "WPA2 Personal");
            locationInfo7.put("Password", "123456798");
            new WirelessQuickViewPage().addSsid1(locationInfo7);
               
            Map<String, String> locationInfo = new HashMap<String, String>();
            locationInfo.put("SSID", list.get(0));
            System.out.println(list.get(0));
            locationInfo.put("Band", "5 GHz");
            locationInfo.put("Security", "Open");
            new WirelessQuickViewPage(false).addSsid1(locationInfo);
            
            Map<String, String> locationInfo1 = new HashMap<String, String>();
            locationInfo1.put("SSID", list.get(1));
            System.out.println(list.get(1));
            locationInfo1.put("Band", "5 GHz");
            locationInfo1.put("Security", "WPA3 Personal Mixed (WPA2 + WPA3)");
            locationInfo1.put("Password", "123456798");
            new WirelessQuickViewPage(false).addSsid1(locationInfo1);
            
            Map<String, String> locationInfo2 = new HashMap<String, String>();
            locationInfo2.put("SSID", list.get(2));
            System.out.println(list.get(2));
            locationInfo2.put("Band", "5 GHz");
            locationInfo2.put("Security", "WPA3 Personal");
            locationInfo2.put("Password", "123456798");
            new WirelessQuickViewPage(false).addSsid1(locationInfo2);
            
            Map<String, String> locationInfo3 = new HashMap<String, String>();
            locationInfo3.put("SSID", list.get(3));
            System.out.println(list.get(3));
            locationInfo3.put("Band", "5 GHz");
            locationInfo3.put("Security", "WPA2 Personal Mixed");
            locationInfo3.put("Password", "123456798");
            new WirelessQuickViewPage(false).addSsid1(locationInfo3);
            
            Map<String, String> locationInfo4 = new HashMap<String, String>();
            locationInfo4.put("SSID", list.get(4));
            System.out.println(list.get(4));
            locationInfo4.put("Band", "5 GHz");
            locationInfo4.put("Security", "WPA2 Personal");
            locationInfo4.put("Password", "123456798");
            new WirelessQuickViewPage(false).addSsid1(locationInfo4);
            
            Map<String, String> locationInfo5 = new HashMap<String, String>();
            locationInfo5.put("SSID", list.get(5));
            System.out.println(list.get(5));
            locationInfo5.put("Band", "5 GHz");
            locationInfo5.put("Security", "Open");
            new WirelessQuickViewPage(false).addSsid1(locationInfo5);
            
            Map<String, String> locationInfo6 = new HashMap<String, String>();
            locationInfo6.put("SSID", list.get(6));
            System.out.println(list.get(6));
            locationInfo6.put("Band", "5 GHz");
            locationInfo6.put("Security", "WPA3 Personal Mixed (WPA2 + WPA3)");
            locationInfo6.put("Password", "123456798");
            new WirelessQuickViewPage(false).addSsid1(locationInfo6);
            
        
    }
        
        @Step("Test Step 3: Create New location")
        public void step3() {
            
            
            HashMap<String, String> locationInfo = new HashMap<String, String>();
            locationInfo.put("Location Name", locationName);
            locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
            locationInfo.put("Zip Code", "12345");
            locationInfo.put("Country", "Canada");
            new AccountPage().addNetwork(locationInfo);
            MyCommonAPIs.sleepi(30);       
            
        
        }
        
        @Step("Test Step 4: Add AP to new location")
        public void step4() {
            
            handle.gotoLoction(locationName);
            
            WebCheck.checkHrefIcon(URLParam.hrefDevices);
            
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.ap2serialNo);
            devInfo.put("Device Name", WebportalParam.ap2deveiceName);
            devInfo.put("MAC Address", WebportalParam.ap2macaddress);

            new DevicesDashPage(false).addNewDevice(devInfo);

            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);
            
        }
      
        @Step("Test Step 5: Copy configuration")
        public void step5() {
       
        new WirelessQuickViewPage().CopyConfig(WebportalParam.Organizations, WebportalParam.location1, locationName );
        handle.gotoLoction(locationName);   
        
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);
        
        }
        
//        @Step("Test Step 6: validate SSID'sare copied")
//        public void step6() {
//            
//            boolean result = false;
//            int count = 0;
//            while (count < 5) {
//                MyCommonAPIs.sleepsync();
//                if (new APUtils(WebportalParam.ap1IPaddress).getSsidStatus("apwp14272", WebportalParam.ap1Model).indexOf("vapProfileStatus 0") != -1) {
//                    result = true;
//                    break;
//                }
//                count += 1;
//            }
//            assertTrue(result, "SSID delete failed");
//        }
        
        
}
