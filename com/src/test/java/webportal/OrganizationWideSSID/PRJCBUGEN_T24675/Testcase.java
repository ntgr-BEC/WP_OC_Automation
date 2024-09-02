package webportal.OrganizationWideSSID.PRJCBUGEN_T24675;

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
    @Story("PRJCBUGEN_T24675") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("org ssid with Advance Rate Selection") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T24675") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        new WirelessQuickViewPage(false).deleteOrgSsidYes("apwp14270");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);        
           
    }
        
      @Step("Test Step 2: Add organizationWide SSID and set advance rate selection")
      public void step2() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          locationInfo.put("SSID", "apwp14270");
          locationInfo.put("Security", "WPA2 Personal");
          locationInfo.put("Password", "123456798");
          new OrganizationPage(false).CreateOrgSSId(locationInfo);

          int densitylevel = 1;
          int densitylevel1 = 4;
          new OrganizationPage(false).GoToAdvanceRateselection(locationInfo.get("SSID"));
          new OrganizationPage(false).clickon25Ghz(); 
          new OrganizationPage(false).orgenableSetMinimumRateControl(); 
          new OrganizationPage(false).orgDragDensityTo(densitylevel,densitylevel1);
           
      }
      
      @Step("Test Step 3:verify whether new created ssid is push to all location in that organzation")
      public void step3() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          new OrganizationPage(false).goWirelessSetting();
          assertTrue(new OrganizationPage(false).checkOrgSsidIsExist("apwp14270"),"ssid does not exits in Loc 1");
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location2);
          new OrganizationPage(false).goWirelessSetting();
          assertTrue(new OrganizationPage(false).checkOrgSsidIsExist("apwp14270"),"ssid does not exits in Loc 2");
          
      }
      
      @Step("Test Step 4: enable SHH and check command")
      public void step4() {
          new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
          new RunCommand().enableSSH4APALL(WebportalParam.ap2IPaddress);
          
          assertTrue(new APUtils(WebportalParam.ap1IPaddress).getAdvancedRateSelectionConfig24boolean(WebportalParam.ap1Model),"ssid is not pushed to AP");
          assertTrue(new APUtils(WebportalParam.ap2IPaddress).getAdvancedRateSelectionConfig24boolean(WebportalParam.ap2Model),"ssid is not pushed to AP");
          
          assertTrue(new APUtils(WebportalParam.ap1IPaddress).getAdvancedRateSelectionConfig50boolean(WebportalParam.ap1Model),"ssid is not pushed to AP");
          assertTrue(new APUtils(WebportalParam.ap2IPaddress).getAdvancedRateSelectionConfig50boolean(WebportalParam.ap2Model),"ssid is not pushed to AP");
      }
      
}