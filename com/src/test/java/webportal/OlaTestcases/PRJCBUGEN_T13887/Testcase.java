package webportal.OlaTestcases.PRJCBUGEN_T13887;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
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
import webportal.weboperation.FileHandling;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.weboperation.RadiusPage;

/**
 * @author Tejeshwini K V
 */
public class Testcase extends TestCaseBase {


    Random r = new Random();

    Map<String, String> SSID = new HashMap<String, String>();
    Map<String, String> SSID1 = new HashMap<String, String>();

    @Feature("OlaTestcases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13887") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Deleting a device (AP) from existing location and adding it back to another or new location.") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T13887") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        new WirelessQuickViewPage(false).deleteOrgSsidYes("PRJCBUGEN_T34389");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword); 
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction("office1");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction("office2");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);
           
    }
        
      @Step("Test Step 2: Add organizationWide SSID with WPA2 Enterprise Security")

      public void step2() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);  
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
         
          SSID.put("SSID", "PRJCBUGEN_T13884");
          SSID.put("Security", "WPA2 Enterprise");
          new OrganizationPage(false).createOrgSSIdWPA2Enterprise2Band(SSID);
          
          SSID1.put("SSID", "PRJCBUGEN_T13882");
          SSID1.put("Security", "WPA2 Personal Mixed");
          SSID1.put("Password", "123456798");
          new OrganizationPage(false).CreateOrgSSId(SSID1);

      }
      
      
      
      @Step("Test Step 3:verify that organizationWide SSID with WPA2 Enterprise Security is pushed to both locaytions as well as AP's ")
      public void step3() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          new OrganizationPage(false).goWirelessSetting(); 
          assertTrue(new OrganizationPage(false).verifylocation1WPA2EnterpriseOrgSSID2Bands(SSID)," org wide ssid with wpa2 enterprise security not pushed successfull.");  
          assertTrue(new OrganizationPage(false).verifylocation1WPA2EnterpriseOrgSSID2Bands(SSID1)," org wide ssid with wpa2 personal mixed security not pushed successfull.");  
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location2);
          new OrganizationPage(false).goWirelessSetting();
          assertTrue(new OrganizationPage(false).verifylocation1WPA2EnterpriseOrgSSID2Bands(SSID)," org wide ssid with wpa2 enterprise security not pushed successfull."); 
          assertTrue(new OrganizationPage(false).verifylocation1WPA2EnterpriseOrgSSID2Bands(SSID1)," org wide ssid with wpa2 personal mixed security not pushed successfull."); 
            
              
      }
        
      @Step("Test Step 4: Add another location as same as Wireless region of AP1")
      public void step4() {
          OrganizationPage page = new OrganizationPage();
          page.gotoPage();
          
          Map<String, String> locationInfo = new HashMap<String, String>();
          locationInfo.put("Location Name", "office3");
          locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
          locationInfo.put("Zip Code", "32003");
          locationInfo.put("Country", WebportalParam.Country);
          new HamburgerMenuPage();
          new AccountPage().addNetwork(locationInfo);
         
      }
      
      @Step("Test Step 5:Delete device ")
      public void step5() {
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
          assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
          
      }
      
      @Step("Test Step 6:Add device to new Org")
      public void step6() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location3);
          
          
          WebCheck.checkHrefIcon(URLParam.hrefDevices);
          
          Map<String, String> devInfo = new HashMap<String, String>();
          devInfo.put("Serial Number", WebportalParam.ap1serialNo);
          devInfo.put("MAC Address1", WebportalParam.ap1macaddress);

          new DevicesDashPage(false).addNewDevice(devInfo);

          new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
          MyCommonAPIs.sleepi(60);

      }
      
      
      
      @Step("Test Step 7: Create and write into a file;")
      public void step7() {     
               
              new FileHandling().createFile("C:\\Auto\\filename.txt");
              
              String VapIndex = new APUtils(WebportalParam.ap1IPaddress).Addeditssid();
                
              new FileHandling().writeFile("C:\\Auto\\filename.txt",VapIndex);       
      }
      
      
      @Step("Test Step 8: To verify the SSID ids broadcasted or not;")
      public void step8() throws IOException {
          
          String n="";
          String m="";
          String n1="";
          String m1="";
          String band = "2.4";
          String band1 = "5.0";
         
         
         String check = new FileHandling().ssidBroadcast("C:\\Auto\\filename.txt",SSID.get("SSID"), band);        
         System.out.println(check);           
         n=check.substring(4,5);
         m=check.substring(8,9);   
         System.out.print(n+" \t"+ m+"\n");
         
         String check1 = new FileHandling().ssidBroadcast("C:\\Auto\\filename.txt",SSID.get("SSID"), band1);        
         System.out.println(check1);           
         n1=check1.substring(4,5);
         m1=check1.substring(8,9);   
         System.out.print(n1+" \t"+ m1+"\n");
        
      
         assertTrue(new APUtils(WebportalParam.ap1IPaddress).Addeditssid1(n,m,WebportalParam.ap1Model), "ssid(2.4) is not broadcasted");
         assertTrue(new APUtils(WebportalParam.ap1IPaddress).Addeditssid1(n1,m1,WebportalParam.ap1Model), "ssid(5) not broadcasted");
      }   
     
             
      
}
