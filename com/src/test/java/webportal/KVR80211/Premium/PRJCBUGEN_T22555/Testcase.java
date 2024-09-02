package webportal.KVR80211.Premium.PRJCBUGEN_T22555;

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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;


/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {
    
   
    Map<String, String> locationInfo7 = new HashMap<String, String>();
    Map<String, String> locationInfo = new HashMap<String, String>();
    Map<String, String> locationInfo1 = new HashMap<String, String>();
    Map<String, String> locationInfo2 = new HashMap<String, String>();
    Map<String, String> locationInfo3 = new HashMap<String, String>();
    Map<String, String> locationInfo4 = new HashMap<String, String>();
    Map<String, String> locationInfo5 = new HashMap<String, String>();
    Map<String, String> locationInfo6 = new HashMap<String, String>();
    

    @Feature("KVR802.11") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T22555") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the User is able to create 8 SSIDs with 11r enabled.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T22555") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
        new DevicesDashPage().checkDeviceInAdminAccount();
        
    }

  
    @Step("Test Step 2:  Add WIFI ssid and now connect client to this ssid;")
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
        
        
        locationInfo7.put("SSID", "apwp19980");
        locationInfo7.put("Band", "Both");
        locationInfo7.put("Security", "WPA2 Personal");
        locationInfo7.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo7);
        new WirelessQuickViewPage().enable80211(locationInfo7.get("SSID"));
           
        
        locationInfo.put("SSID", list.get(0));
        System.out.println(list.get(0));
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo);
        new WirelessQuickViewPage().enable80211(locationInfo.get("SSID"));
        
        
        locationInfo1.put("SSID", list.get(1));
        System.out.println(list.get(1));
        locationInfo1.put("Band", "Both");
        locationInfo1.put("Security", "WPA2 Personal Mixed");
        locationInfo1.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo1);
        new WirelessQuickViewPage().enable80211(locationInfo1.get("SSID"));
        
        
        locationInfo2.put("SSID", list.get(2));
        System.out.println(list.get(2));
        locationInfo2.put("Band", "Both");
        locationInfo2.put("Security", "WPA2 Personal Mixed");
        locationInfo2.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo2);
        new WirelessQuickViewPage().enable80211(locationInfo2.get("SSID"));
        
        
        locationInfo3.put("SSID", list.get(3));
        System.out.println(list.get(3));
        locationInfo3.put("Band", "Both");
        locationInfo3.put("Security", "WPA2 Personal Mixed");
        locationInfo3.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo3);
        new WirelessQuickViewPage().enable80211(locationInfo3.get("SSID"));
        
        
        locationInfo4.put("SSID", list.get(4));
        System.out.println(list.get(4));
        locationInfo4.put("Band", "Both");
        locationInfo4.put("Security", "WPA2 Personal");
        locationInfo4.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo4);
        new WirelessQuickViewPage().enable80211(locationInfo4.get("SSID"));
        
        
        locationInfo5.put("SSID", list.get(5));
        System.out.println(list.get(5));
        locationInfo5.put("Band", "Both");
        locationInfo5.put("Security", "WPA2 Personal");
        locationInfo5.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo5);
        new WirelessQuickViewPage().enable80211(locationInfo5.get("SSID"));
        
        
        locationInfo6.put("SSID", list.get(6));
        System.out.println(list.get(6));
        locationInfo6.put("Band", "Both");
        locationInfo6.put("Security", "WPA2 Personal Mixed");
        locationInfo6.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo6);
        new WirelessQuickViewPage().enable80211(locationInfo6.get("SSID"));
        
        
       

      
    }
    
    @Step("Test Step 3: check does  802.11kvr enable;")
    public void step3() {
        
        
        assertTrue(new WirelessQuickViewPage().check80211enable(locationInfo.get("SSID")), "802 is enabled");
        assertTrue(new WirelessQuickViewPage().check80211enable(locationInfo1.get("SSID")), "802 is enabled");
        assertTrue(new WirelessQuickViewPage().check80211enable(locationInfo2.get("SSID")), "802 is enabled");
        assertTrue(new WirelessQuickViewPage().check80211enable(locationInfo3.get("SSID")), "802 is enabled");
        assertTrue(new WirelessQuickViewPage().check80211enable(locationInfo4.get("SSID")), "802 is enabled");
        assertTrue(new WirelessQuickViewPage().check80211enable(locationInfo5.get("SSID")), "802 is enabled");
        assertTrue(new WirelessQuickViewPage().check80211enable(locationInfo6.get("SSID")), "802 is enabled");
        assertTrue(new WirelessQuickViewPage().check80211enable(locationInfo7.get("SSID")), "802 is enabled");
        
     }

    }
    