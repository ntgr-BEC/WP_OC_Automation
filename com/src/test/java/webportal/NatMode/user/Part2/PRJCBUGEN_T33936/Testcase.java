package webportal.NatMode.user.Part2.PRJCBUGEN_T33936;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
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
   @Story("PRJCBUGEN_T33936") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("verify the ars enabled nat mode ssid is pushed into ap or not") // It's a test case title from Jira Test Case.
   @TmsLink("PRJCBUGEN-T33936") // It's a testcase id/link from Jira Test Case.

   @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
   public void test() throws Exception {
       runTest(this);
   }

   @AfterMethod(alwaysRun = true)
   public void tearDown() {
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

   @Step("Test Step 2: Create NAT SSID WITH ARS ENABLED AND DENSITY SET TO 3")
   public void step2() {
       ssidInfo.put("SSID", "apwp14008");
       ssidInfo.put("Security", "WPA2 Personal");
       ssidInfo.put("Password", "12345678");
       new WirelessQuickViewPage().addSsidNat(ssidInfo);  
       int densitylevel = 3;
       new WirelessQuickViewPage().GoToAdvanceRateCntrol(ssidInfo.get("SSID"));
       new WirelessQuickViewPage(false).enableSetMinimumRateControl();        
       new WirelessQuickViewPage(false).DragDensityTo(densitylevel);
       MyCommonAPIs.sleepi(120);
   }
  @Step("Test Step 3: Check whether configurations are pushed into ap or not")
   public void step3() {
      assertTrue(new APUtils(WebportalParam.ap1IPaddress).getNatStatus(WebportalParam.ap1Model), "CONFIG NOT PUSHED");
      String msg = new APUtils(WebportalParam.ap1IPaddress).getAdvancedRateSelectionConfig24( WebportalParam.ap1Model);
      assertTrue(msg.contains("densityLevel 3"),"CONFIG NOT PUSHED");
     
     
   }
   
   

}