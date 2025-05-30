package webportal.OrganizationWideSSID.CreateORGWideSSIDBeforeAddingAP.PRJCBUGEN_T24664;

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
    String locationName     = "office";
  

    @Feature("OrganizationWideSSID") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24664") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("create  ssid  with wpa3-psk   from  organization setting before  adding  AP") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T24664") // It's a testcase id/link from Jira Test Case.

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
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          locationInfo.put("SSID", "PRJCBUGEN_T24664");
          locationInfo.put("Security", "WPA3 Personal");
          locationInfo.put("Password", "123456798");
          new OrganizationPage(false).CreateOrgSSId(locationInfo);     
      }
          
      @Step("Test Step 3: Create two locations;")
      public void step3() {
          List<String> Loclist=new ArrayList<String>();  
          int totalLocationsrequired = 2;
          int created = 0;
          new OrganizationPage(false).openOrg("Netgear");
          HashMap<String, String> locationInfo = new HashMap<String, String>();
          locationInfo.put("Location Name", "office1");
          locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
          locationInfo.put("Zip Code", "70184");
          locationInfo.put("Country", "Germany");
          new AccountPage(false).addNetwork(locationInfo);
          new OrganizationPage(false).openOrg("Netgear");
          HashMap<String, String> locationInfo1 = new HashMap<String, String>();
          locationInfo1.put("Location Name", "office2");
          locationInfo1.put("Device Admin Password", WebportalParam.loginDevicePassword);
          locationInfo1.put("Zip Code", "70184");
          locationInfo1.put("Country", "Germany");
          new AccountPage(false).addNetwork(locationInfo1);
          assertTrue(new AccountPage(false).VerifyCreatedLocations(Loclist, totalLocationsrequired), "Missing Networks/Locations Noted");
      }
      
      @Step("Test Step 4: Add one ap to each location;")
      public void step4() {
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          Map<String, String> devInfo = new HashMap<String, String>();
          devInfo.put("Serial Number", WebportalParam.ap1serialNo);
          devInfo.put("Device Name", WebportalParam.ap1deveiceName);
          devInfo.put("MAC Address", WebportalParam.ap1macaddress);
          new DevicesDashPage().addNewDevice(devInfo);
          new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          new OrganizationPage(false).goWirelessSetting();
          assertTrue(new OrganizationPage(false).checkOrgSsidIsExist("PRJCBUGEN_T24664"),"ssid does not exits in Loc 1");
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location2);
          Map<String, String> devInfo1 = new HashMap<String, String>();
          devInfo1.put("Serial Number", WebportalParam.ap2serialNo);
          devInfo1.put("Device Name", WebportalParam.ap2deveiceName);
          devInfo1.put("MAC Address", WebportalParam.ap2macaddress);
          new DevicesDashPage().addNewDevice(devInfo1);
          new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location2);
          new OrganizationPage(false).goWirelessSetting();
          assertTrue(new OrganizationPage(false).checkOrgSsidIsExist("PRJCBUGEN_T24664"),"ssid does not exits in Loc 2");
      }

      
      @Step("Test Step 5:verify whether new created ssid is push to all location in that organzation")
      public void step5() {
          MyCommonAPIs.sleepi(10);
          new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
          MyCommonAPIs.sleepi(2);
          new RunCommand().enableSSH4APALL(WebportalParam.ap2IPaddress);
          MyCommonAPIs.sleepi(2);
          assertTrue(new APUtils(WebportalParam.ap1IPaddress).getSsidStatus1("PRJCBUGEN_T24664", WebportalParam.ap1Model),"ssid is not pushed to AP");
          MyCommonAPIs.sleepi(10);
          assertTrue(new APUtils(WebportalParam.ap2IPaddress).getSsidStatus1("PRJCBUGEN_T24664", WebportalParam.ap2Model),"ssid is not pushed to AP");    
        
      }
}
