package webportal.NatMode.user.Dontrun.PRJCBUGEN_T33939;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.MDNSPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
*
* @author Ravishankar
*
*/
public class Testcase extends TestCaseBase {

   Map<String, String> ssidInfo = new HashMap<String, String>();

   @Feature("NatMode") // It's a folder/component name to make test suite more readable from Jira Test Case.
   @Story("PRJCBUGEN_T33939") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("verify whether the Fastroaming is available when nat mode is selected") // It's a test case title from Jira Test Case.
   @TmsLink("PRJCBUGEN-T33939") // It's a testcase id/link from Jira Test Case.

   @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
   public void test() throws Exception {
       runTest(this);
   }

   @AfterMethod(alwaysRun = true)
   public void tearDown() {
       System.out.println("start to do tearDown");
       MyCommonAPIs.sleepi(10);
       new MDNSPage().deletePolicy("MDNS");
       MyCommonAPIs.sleepi(5);
       new MDNSPage(false).disableMDNS();
       System.out.println("start to do tearDown");
       new WirelessQuickViewPage().deleteALLSSID();
   }

   // Each step is a single test step from Jira Test Case
   @Step("Test Step 1: Login IM WP success;")
   public void step1() {
       WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
       webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

       handle.gotoLoction();
   }

   @Step("Test Step 2: Create BRIDGE SSID WITH MDNS ENABLED")
   public void step2() {
       ssidInfo.put("SSID", "apwp14008");
       ssidInfo.put("Security", "WPA2 Personal");
       ssidInfo.put("Password", "12345678");
       new WirelessQuickViewPage().addSsid(ssidInfo);  
       MyCommonAPIs.sleep(30);
       new MDNSPage().addPolicy();
       assertTrue(new MDNSPage(false).Policyexits.isDisplayed(),"Policy is added properly");
      
   }
  @Step("Test Step 3: Check whether Mdns enabled ssid in natmode is pushed into AP or not")
   public void step3() {
      MyCommonAPIs.sleepi(120);
      String before = new APUtils(WebportalParam.ap1IPaddress).MDNS(WebportalParam.ap1Model);        
      assertTrue(before.contains("MDNS") && before.contains("mDNSGateway 1"), "policy is not added propely");
 
     
     
   }
   
   

}