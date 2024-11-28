package webportal.OrganizationDeviceTableEnhancement_Notification.PRJCBUGEN_T45655;

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
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.NotificationPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> locationInfo = new HashMap<String, String>();
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String organizationName = "Netgear";
    String locationName     = "office1";
  

    @Feature("OrganizationDeviceTableEnhancement_Notification") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T45655") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Notification for Onboarding Hard Bundle Device") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T45655") // It's a testcase id/link from Jira Test Case.

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
        
      @Step("Test Step 2: Create an organization and organization wide SSID")
      public void step2() {
          
          Map<String, String> organizationInfo = new HashMap<String, String>();
          organizationInfo.put("Name", organizationName);
          OrganizationPage OrganizationPage = new OrganizationPage();
          OrganizationPage.addOrganization(organizationInfo);
          OrganizationPage.creditAllocation(organizationName);
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations); 
      }
          
      @Step("Test Step 3: Create two locations;")
      public void step3() {
          OrganizationPage OrganizationPage = new OrganizationPage();
          OrganizationPage.openOrg(organizationName);
          HashMap<String, String> locationInfo1 = new HashMap<String, String>();
          locationInfo1.put("Device Admin Password", WebportalParam.loginDevicePassword);
          locationInfo1.put("Zip Code", "12345");
          locationInfo1.put("Country", WebportalParam.Country);
          List<String> Loclist=new ArrayList<String>();  
          int totalLocationsrequired = 1;
          int created = 0;
          for(int i = 1; i <=totalLocationsrequired; i++){
              Random random = new Random();
              int num = random.nextInt(100000);
              String formatted = String.format("%05d", num);
              Loclist.add(locationName+i);
              locationInfo1.put("Location Name", locationName+i);
              new AccountPage(false).addNetworkforLocationCheck(locationInfo1);
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
      
      @Step("Test Step 4: Add hardbundle device and verify notifications for the same location;")
      public void step4() {
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          Map<String, String> devInfo = new HashMap<String, String>();
          devInfo.put("Serial Number", WebportalParam.ap5serialNo);
          devInfo.put("Device Name", WebportalParam.ap5deveiceName);
          devInfo.put("MAC Address", WebportalParam.ap5macaddress);
          new DevicesDashPage().addNewDevice(devInfo);
          new NotificationPage().SeeAllNotifications();
          assertTrue(new OrganizationPage(false).verifyHardbundleOnboardedDevicesNotification(WebportalParam.ap5serialNo),
                  "Notification not shown on notifications page after onboarding Hardbundle device");
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          Map<String, String> devInfo1 = new HashMap<String, String>();
          devInfo1.put("Serial Number", WebportalParam.sw1serialNo);
          devInfo1.put("Device Name", WebportalParam.sw1deveiceName);
          devInfo1.put("MAC Address", WebportalParam.sw1MacAddress);
          new DevicesDashPage().addNewDevice(devInfo1);
          new NotificationPage().SeeAllNotifications();
          assertTrue(new OrganizationPage(false).verifyHardbundleOnboardedDevicesNotification(WebportalParam.sw1serialNo),
                  "Notification not shown on notifications page after onboarding Hardbundle device");
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          Map<String, String> devInfo2 = new HashMap<String, String>();
          devInfo2.put("Serial Number", WebportalParam.br1serialNo);
          devInfo2.put("Device Name", WebportalParam.br1deveiceName);
          devInfo2.put("MAC Address", WebportalParam.br1deveiceMac);
          new DevicesDashPage().addNewDevice(devInfo2);
          new NotificationPage().SeeAllNotifications();
          assertTrue(new OrganizationPage(false).verifyHardbundleOnboardedDevicesNotification(WebportalParam.br1serialNo),
                  "Notification not shown on notifications page after onboarding Hardbundle device");
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          Map<String, String> devInfo3 = new HashMap<String, String>();
          devInfo3.put("Serial Number", WebportalParam.ob1serialNo);
          devInfo3.put("Device Name", WebportalParam.ob1deveiceName);
          devInfo3.put("MAC Address", WebportalParam.ob1deveiceMac);
          new DevicesDashPage().addNewDevice(devInfo3);
          new NotificationPage().SeeAllNotifications();
          assertTrue(new OrganizationPage(false).verifyHardbundleOnboardedDevicesNotification(WebportalParam.br1serialNo),
                  "Notification not shown on notifications page after onboarding Hardbundle device");
      }

}
