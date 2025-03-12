package webportal.MPSK.prouser.PRJCBUGEN_T29789;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("MPSK") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T29789") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, warning pop up on trying to configure MPSK on 5th SSID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T29789") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        MyCommonAPIs.sleepi(20);
        new WirelessQuickViewPage().deleteALLSSID();
        new WirelessQuickViewPage().deleteMPSKKey();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);

        handle.gotoLoction();
        // new DevicesDashPage().checkDeviceInAdminAccount();
    }
    
    @Step("Test Step 2: Add MPSK Key.")
    public void step2() {
        
        assertTrue(new WirelessQuickViewPage().addMPSKKey1(), "MPSK key is not added successfully");
        
    }

    @Step("Test Step 3: Check default scheduled wifisettings.")
    public void step3() {
        
        ArrayList<String> list = new ArrayList<String>();
        Random r = new Random();
         
        int i;
        for(i=0;i<=3;i++) {
        int num = r.nextInt(10000);
        String mailname = "ssid" + String.valueOf(num);
        list.add(mailname);
        }
        System.out.println(list);
        
        new WirelessQuickViewPage().deleteALLSSID();
        
        Map<String, String> locationInfo7 = new HashMap<String, String>();
        locationInfo7.put("SSID", "apwp19980");
        locationInfo7.put("Band", "Both");
        locationInfo7.put("Security", "WPA2 Personal");
        locationInfo7.put("Password", "123456798");
        new WirelessQuickViewPage().addMPSKSsid1(locationInfo7);
           
        Map<String, String> locationInfo = new HashMap<String, String>();
        System.out.println(list);
        locationInfo.put("SSID", list.get(0));
        System.out.println(list.get(0));
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addMPSKSsid1(locationInfo);
        
        Map<String, String> locationInfo1 = new HashMap<String, String>();
        locationInfo1.put("SSID", list.get(1));
        System.out.println(list.get(1));
        locationInfo1.put("Band", "Both");
        locationInfo1.put("Security", "WPA2 Personal");
        locationInfo1.put("Password", "123456798");
        new WirelessQuickViewPage().addMPSKSsid1(locationInfo1);
        
        Map<String, String> locationInfo2 = new HashMap<String, String>();
        locationInfo2.put("SSID", list.get(2));
        System.out.println(list.get(2));
        locationInfo2.put("Band", "Both");
        locationInfo2.put("Security", "WPA2 Personal");
        locationInfo2.put("Password", "123456798");
        new WirelessQuickViewPage().addMPSKSsid1(locationInfo2);
        
        Map<String, String> locationInfo3 = new HashMap<String, String>();
        locationInfo3.put("SSID", list.get(3));
        System.out.println(list.get(3));
        locationInfo3.put("Band", "Both");
        locationInfo3.put("Security", "WPA2 Personal");
        locationInfo3.put("Password", "123456798");
        assertTrue(new WirelessQuickViewPage().addMPSKSsid2(locationInfo3),"Max error was not recived");
        


    }

}
