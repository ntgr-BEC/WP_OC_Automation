package webportal.OrganizationWideSSIDEnhancement.PRJCBUGEN_T33716;

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

    @Feature("OrganizationWideSSIDEnhancement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33716") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Edit  organization ssid with 2.4GHz Band and WPA2 security") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T33716") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        new WirelessQuickViewPage(false).deleteSsidYes("PRJCBUGEN_T33716");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);  
      
           
    }
        
      @Step("Test Step 2: Add organizationWide SSID")

      public void step2() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);  
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          Map<String, String> locationInfo = new HashMap<String, String>();
          locationInfo.put("SSID", "PRJCBUGEN_T33716");
          locationInfo.put("Security", "WPA2 Personal");
          locationInfo.put("Password", "123456798");
          new OrganizationPage(false).CreateOrgSSId(locationInfo);

      }
      
      @Step("Test Step 3:verify whether new created ssid is push to all location in that organzation")
      public void step3() {
          
          
          Map<String, String> locationInfo = new HashMap<String, String>();
          locationInfo.put("SSID", "PRJCBUGEN_T33716");
          locationInfo.put("Security", "WPA2 Personal");
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          new OrganizationPage(false).editOrgnaizationwideSSID2GhzWPA2Security(locationInfo);
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          new OrganizationPage(false).goWirelessSetting(); 
          assertTrue(new OrganizationPage(false).verifylocation1WPA2SSID(locationInfo)," Organization wide SSID edit operation is failed.");  
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location2);
          new OrganizationPage(false).goWirelessSetting();
          assertTrue(new OrganizationPage(false).verifylocation1WPA2SSID(locationInfo)," Organization wide SSID edit operation is failed."); 
          
          new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
          new RunCommand().enableSSH4APALL(WebportalParam.ap2IPaddress);
          
          assertTrue(new APUtils(WebportalParam.ap1IPaddress).getSsidStatus1("PRJCBUGEN_T33716", WebportalParam.ap1Model),"ssid is not pushed to AP");
          assertTrue(new APUtils(WebportalParam.ap2IPaddress).getSsidStatus1("PRJCBUGEN_T33716", WebportalParam.ap2Model),"ssid is not pushed to AP");
        
      }
}
