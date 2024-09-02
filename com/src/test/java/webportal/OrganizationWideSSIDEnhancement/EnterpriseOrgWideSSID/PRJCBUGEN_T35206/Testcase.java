package webportal.OrganizationWideSSIDEnhancement.EnterpriseOrgWideSSID.PRJCBUGEN_T35206;

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


    Random r = new Random();
    String organizationName = "Netgear";
    String locationName     = "office";
    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("EnterpriseOrgWideSSID") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35206") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify WPA3 Enterprise SSID with 2.4 GHz band is pushed to location as well as AP") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T35206") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        new WirelessQuickViewPage(false).deleteOrgSsidYes("PRJCBUGEN_T35206");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword); 
       
           
    }
        
      @Step("Test Step 2: Add organizationWide SSID with WPA3 Enterprise Security")

      public void step2() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);  
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          Map<String, String> locationInfo = new HashMap<String, String>();
          locationInfo.put("SSID", "PRJCBUGEN_T35206");
          locationInfo.put("Security", "WPA3 Enterprise");
          new OrganizationPage(false).createOrgSSIdWPA3Enterprise24GHzBand(locationInfo);
      }
      
      @Step("Test Step 3:verify that organizationWide SSID with WPA3 Enterprise Security is pushed to both locaytions as well as AP's")
      public void step3() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          new OrganizationPage(false).goWirelessSetting(); 
          locationInfo.put("SSID", "PRJCBUGEN_T35206");
          assertTrue(new OrganizationPage(false).verifylocation1WPA3EnterpriseOrgSSID24Band(locationInfo)," org wide ssid with wpa3 enterprise security not pushed successfull.");  
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location2);
          new OrganizationPage(false).goWirelessSetting();
          locationInfo.put("SSID", "PRJCBUGEN_T35206");
          assertTrue(new OrganizationPage(false).verifylocation1WPA3EnterpriseOrgSSID24Band(locationInfo)," org wide ssid with wpa3 enterprise security not pushed successfull."); 
          
          new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
          new RunCommand().enableSSH4APALL(WebportalParam.ap2IPaddress);
          
          assertTrue(new APUtils(WebportalParam.ap1IPaddress).getSsidStatus1("PRJCBUGEN_T35206", WebportalParam.ap1Model),"org wide ssid with wpa3 enterprise security is not pushed to AP");
          assertTrue(new APUtils(WebportalParam.ap2IPaddress).getSsidStatus1("PRJCBUGEN_T35206", WebportalParam.ap2Model),"org wide ssid with wpa3 enterprise security is not pushed to AP");
        
      }
}
