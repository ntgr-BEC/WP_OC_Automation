package webportal.OrganizationWideSSID.CreateORGWideSSIDBeforeAddingAP.PRJCBUGEN_T32712;

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
    @Story("PRJCBUGEN_T32712") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify org-wide-ssid  functionally  when vlan is changed to management  vlan (1) from custom vlan.") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T32712") // It's a testcase id/link from Jira Test Case.

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
          locationInfo.put("SSID", "PRJCBUGEN_T32712");
          locationInfo.put("Security", "WPA2 Personal");
          locationInfo.put("Password", "123456798");
          locationInfo.put("VLAN Name", "Custom SSID VLAN");
          locationInfo.put("VLAN ID", "2");
          new OrganizationPage(false).CreateOrgSSId(locationInfo);
          new OrganizationPage(false).editOrgnaizationwideSSIDCustomVlan(locationInfo);
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
          int totalLocationsrequired = 2;
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
          assertTrue(new OrganizationPage(false).checkOrgSsidCustomVlanIsExist("PRJCBUGEN_T32712"),"ssid with Custom VLAN does not exits in Loc 1");
          
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
          assertTrue(new OrganizationPage(false).checkOrgSsidCustomVlanIsExist("PRJCBUGEN_T32712"),"ssid with Custom VLAN does not exits in Loc 1");
      }

      
      @Step("Test Step 5:verify whether new created ssid is push to all location in that organzation")
      public void step5() {
          MyCommonAPIs.sleepi(10);
          new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
          MyCommonAPIs.sleepi(2);
          new RunCommand().enableSSH4APALL(WebportalParam.ap2IPaddress);
          MyCommonAPIs.sleepi(2);
          assertTrue(new APUtils(WebportalParam.ap1IPaddress).getSsidStatus1("PRJCBUGEN_T32712", WebportalParam.ap1Model),"ssid is not pushed to AP");
          MyCommonAPIs.sleepi(10);
          assertTrue(new APUtils(WebportalParam.ap2IPaddress).getSsidStatus1("PRJCBUGEN_T32712", WebportalParam.ap2Model),"ssid is not pushed to AP");    
          
          MyCommonAPIs.sleepi(10);
          int sum = 0;
          while (true) {
              MyCommonAPIs.sleepi(10);
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID PRJCBUGEN_T32712")
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
                  .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect PRJCBUGEN_T32712 123456798 WPA2PSK aes")
                  .equals("true")) {
              result1 = false;
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect PRJCBUGEN_T32712 123456798 WPA2PSK aes")
                      .equals("true")) {
                  result1 = true;
              }
          }

          assertTrue(result1, "Client cannot connected.");
          MyCommonAPIs.sleepsync();
          new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
      }  
      
      @Step("Test Step 6:verify whether custom vlan ssid is push to all location in that organzation and able to connect client")
      public void step6() { 
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          
          locationInfo.put("VLAN Name", "Management VLAN");
          new OrganizationPage(false).editOrgnaizationwideSSIDMangamentVLAN(locationInfo);
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          new OrganizationPage(false).goWirelessSetting();
          assertTrue(new OrganizationPage(false).checkOrgSsidManageVlanIsExist("PRJCBUGEN_T32712"),"ssid with Management VLAN does not exits in Loc 1");
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location2);
          new OrganizationPage(false).goWirelessSetting();
          assertTrue(new OrganizationPage(false).checkOrgSsidManageVlanIsExist("PRJCBUGEN_T32712"),"ssid with Management VLAN does not exits in Loc 1");
          
          MyCommonAPIs.sleepi(10);
          int sum = 0;
          while (true) {
              MyCommonAPIs.sleepi(10);
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID PRJCBUGEN_T32712")
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
                  .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect PRJCBUGEN_T32712 123456798 WPA2PSK aes")
                  .equals("true")) {
              result1 = false;
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect PRJCBUGEN_T32712 123456798 WPA2PSK aes")
                      .equals("true")) {
                  result1 = true;
              }
          }

          assertTrue(result1, "Client cannot connected.");
          MyCommonAPIs.sleepsync();
          new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
          
      }
}
