package webportal.OrganizationWideSSID.APNotNeeded.PRJCBUGEN_T24682;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

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
import webportal.publicstep.UserManage;
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


    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String organizationName = "Netgear";
    String locationName     = "office";
  

    @Feature("OrganizationWideSSID") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24682") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("create org ssid when one location has 8 ssid") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T24682") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        new OrganizationPage().deleteOrganizationNew(organizationName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);        
           
    }
        
      @Step("Test Step 2: Create an organization and add multiple locations")
      public void step2() {
          
          Map<String, String> organizationInfo = new HashMap<String, String>();
          organizationInfo.put("Name", organizationName);
          OrganizationPage OrganizationPage = new OrganizationPage();
          OrganizationPage.addOrganization(organizationInfo);
          OrganizationPage.creditAllocation(organizationName);
          OrganizationPage.openOrg(organizationName);
          
          HashMap<String, String> locationInfo = new HashMap<String, String>();
          locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
          locationInfo.put("Zip Code", "12345");
          locationInfo.put("Country", WebportalParam.Country);
          List<String> Loclist=new ArrayList<String>();  
          int totalLocationsrequired = 2;
          int created = 0;
          for(int i = 1; i <=totalLocationsrequired; i++){
              Random random = new Random();
              int num = random.nextInt(100000);
              String formatted = String.format("%05d", num);
              Loclist.add(locationName+i);
              locationInfo.put("Location Name", locationName+i);
              new AccountPage(false).addNetworkforLocationCheck(locationInfo);
              created = created+1;
              System.out.println("Location ---------->"+created+" Created");
              MyCommonAPIs.sleepi(5);             
          }  
          Selenide.sleep(5000);
          Selenide.refresh();
          Selenide.sleep(5000);
          Selenide.refresh();
          Selenide.sleep(5000);
          assertTrue(new AccountPage(false).VerifyCreatedLocations(Loclist, totalLocationsrequired), "Missing Networks/Locations Noted");
      }
      
      @Step("Test Step 3: Add Eight WIFI ssid;")
      public void step3() {

          ArrayList<String> list = new ArrayList<String>();
          Random r = new Random();
           
          int i;
          for(i=0;i<=14;i++) {
          int num = r.nextInt(10000);
          String mailname = "ssid" + String.valueOf(num);
          list.add(mailname);
          }
          System.out.println(list);
          
          new AccountPage().enterLocation("office1");
          new WirelessQuickViewPage(false).clickonWirelessTab();
          new WirelessQuickViewPage().deleteALLSSID();
          
          Map<String, String> locationInfo7 = new HashMap<String, String>();
          locationInfo7.put("SSID", "apwp19980");
          locationInfo7.put("Band", "Both");
          locationInfo7.put("Security", "WPA2 Personal");
          locationInfo7.put("Password", "123456798");
          new WirelessQuickViewPage().addSsid1(locationInfo7);
             
          Map<String, String> locationInfo = new HashMap<String, String>();
          locationInfo.put("SSID", list.get(0));
          System.out.println(list.get(0));
          locationInfo.put("Band", "Both");
          locationInfo.put("Security", "Open");
          new WirelessQuickViewPage(false).addSsid1(locationInfo);
          
          Map<String, String> locationInfo1 = new HashMap<String, String>();
          locationInfo1.put("SSID", list.get(1));
          System.out.println(list.get(1));
          locationInfo1.put("Band", "Both");
          locationInfo1.put("Security", "WPA3 Personal Mixed (WPA2 + WPA3)");
          locationInfo1.put("Password", "123456798");
          new WirelessQuickViewPage(false).addSsid1(locationInfo1);
          
          Map<String, String> locationInfo2 = new HashMap<String, String>();
          locationInfo2.put("SSID", list.get(2));
          System.out.println(list.get(2));
          locationInfo2.put("Band", "Both");
          locationInfo2.put("Security", "WPA3 Personal");
          locationInfo2.put("Password", "123456798");
          new WirelessQuickViewPage(false).addSsid1(locationInfo2);
          
          Map<String, String> locationInfo3 = new HashMap<String, String>();
          locationInfo3.put("SSID", list.get(3));
          System.out.println(list.get(3));
          locationInfo3.put("Band", "Both");
          locationInfo3.put("Security", "WPA2 Personal Mixed");
          locationInfo3.put("Password", "123456798");
          new WirelessQuickViewPage(false).addSsid1(locationInfo3);
          
          Map<String, String> locationInfo4 = new HashMap<String, String>();
          locationInfo4.put("SSID", list.get(4));
          System.out.println(list.get(4));
          locationInfo4.put("Band", "Both");
          locationInfo4.put("Security", "WPA2 Personal");
          locationInfo4.put("Password", "123456798");
          new WirelessQuickViewPage(false).addSsid1(locationInfo4);
          
          Map<String, String> locationInfo5 = new HashMap<String, String>();
          locationInfo5.put("SSID", list.get(5));
          System.out.println(list.get(5));
          locationInfo5.put("Band", "Both");
          locationInfo5.put("Security", "Open");
          new WirelessQuickViewPage(false).addSsid1(locationInfo5);
          
          Map<String, String> locationInfo6 = new HashMap<String, String>();
          locationInfo6.put("SSID", list.get(6));
          System.out.println(list.get(6));
          locationInfo6.put("Band", "Both");
          locationInfo6.put("Security", "WPA3 Personal Mixed (WPA2 + WPA3)");
          locationInfo6.put("Password", "123456798");
          new WirelessQuickViewPage(false).addSsid1(locationInfo6);
          
          new AccountPage().enterLocation("office2");
          new WirelessQuickViewPage(false).clickonWirelessTab();
          new WirelessQuickViewPage().deleteALLSSID();
          
          Map<String, String> locationInfo9 = new HashMap<String, String>();
          locationInfo9.put("SSID", list.get(7));
          System.out.println(list.get(7));
          locationInfo9.put("Band", "Both");
          locationInfo9.put("Security", "Open");
          new WirelessQuickViewPage(false).addSsid1(locationInfo9);
          
          Map<String, String> locationInfo10 = new HashMap<String, String>();
          locationInfo10.put("SSID", list.get(8));
          System.out.println(list.get(8));
          locationInfo10.put("Band", "Both");
          locationInfo10.put("Security", "WPA3 Personal Mixed (WPA2 + WPA3)");
          locationInfo10.put("Password", "123456798");
          new WirelessQuickViewPage(false).addSsid1(locationInfo10);
      }
      
      @Step("Test Step 4: Add organizationWide SSID and enable advanced rate selection")
      public void step4() {
          
          OrganizationPage OrganizationPage = new OrganizationPage();
          new OrganizationPage(false).openOrg(organizationName);
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          Map<String, String> locationInfo = new HashMap<String, String>();
          locationInfo.put("SSID", "apwp14270");
          locationInfo.put("Security", "WPA2 Personal");
          locationInfo.put("Password", "123456798");
          assertTrue(new OrganizationPage(false).organizationWideSSIDMaxLimitError(locationInfo)," Organization wide SSID is created successfully.");
          
      }
      
}