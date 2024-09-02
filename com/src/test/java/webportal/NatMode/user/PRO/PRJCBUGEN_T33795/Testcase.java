package webportal.NatMode.user.PRO.PRJCBUGEN_T33795;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selectors;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.webelements.WirelessQuickViewElement;

/**
*
* @author Ravishankar
*
*/
public class Testcase extends TestCaseBase {

   Map<String, String> ssidInfo = new HashMap<String, String>();

   @Feature("NatMode") // It's a folder/component name to make test suite more readable from Jira Test Case.
   @Story("PRJCBUGEN_T33795") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("Able to create NAT mode SSID from Organizational level") // It's a test case title from Jira Test Case.
   @TmsLink("PRJCBUGEN-T33795") // It's a testcase id/link from Jira Test Case.

   @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
   public void test() throws Exception {
       runTest(this);
   }

   @AfterMethod(alwaysRun = true)
   public void tearDown() {
       System.out.println("start to do tearDown");
       new WirelessQuickViewPage().deleteALLSSID();
       new WirelessQuickViewPage(false).deleteOrgSsidYes("apwp14008");
   }

   // Each step is a single test step from Jira Test Case
   @Step("Test Step 1: Login IM WP success;")
   public void step1() {
       WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
       webportalLoginPage.loginByUserPassword(WebportalParam.managerName,WebportalParam.managerPassword);


   }

   @Step("Test Step 2: Add organizationWide SSID")
   public void step2() {
       
       new OrganizationPage().openOrg(WebportalParam.Organizations);  
       new OrganizationPage().goToOrgSsid(WebportalParam.Organizations);
       Map<String, String> locationInfo = new HashMap<String, String>();
       locationInfo.put("SSID", "apwp14008");
       locationInfo.put("Security", "WPA2 Personal");
       locationInfo.put("Password", "123456798");
       new WirelessQuickViewPage(false).addSsidNat(locationInfo);

   }
   
   
   @Step("Test Step 3: Make sure the default config are pushed")
   public void step3() {
       
   

       MyCommonAPIs.sleepi(60);
       assertTrue(new APUtils(WebportalParam.ap1IPaddress).getNatStatus(WebportalParam.ap1Model), "CONFIG FOR NAT NOT PUSHED");
         
         
                          
   }
}
   

   



