package webportal.NatMode.user.Part2.PRJCBUGEN_T33942;

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
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;


/**
*
* @author RaviShankar
*
*/
public class Testcase extends TestCaseBase {
   
   
   Map<String, String> mpskKeyInfo = new HashMap<String, String>();
   Map<String, String> mpskKeyInfo1 = new HashMap<String, String>();

   @Feature("NatMode") // It's a folder/component name to make test suite more readable from Jira Test Case.
   @Story("PRJCBUGEN_T33942") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("Test to verify, MPSK IS PUSHED INTO AP USING BRIDGE MODE") // It's a testcase title from Jira Test Case.
   @TmsLink("PRJCBUGEN_T33942") // It's a testcase id/link from Jira Test Case.

   @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
   public void test() throws Exception {
       runTest(this);
   }

   @AfterMethod(alwaysRun = true)
   public void tearDown() {
       new WirelessQuickViewPage().deleteALLSSID();
      new WirelessQuickViewPage().deleteAllMPSKKey();
       System.out.println("start to do tearDown");
   }


   @Step("Test Step 1: Login IM WP success;")
   public void step1() {
       WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
       webportalLoginPage.defaultLogin();

       handle.gotoLoction();
   }
   

   @Step("Test Step 2: add nat ssid and chck whether warning message is poped up when mpsk is is enabled")
   public void step2() {
       
       Map<String, String> locationInfo = new HashMap<String, String>();
       locationInfo.put("SSID", "apwp14008");
       locationInfo.put("Security", "WPA2 Personal Mixed");
       locationInfo.put("Password", "123456798");
       new WirelessQuickViewPage().addSsidNat(locationInfo);      
      String check= new WirelessQuickViewPage().enableMpskNat(locationInfo.get("SSID")); 
       MyCommonAPIs.sleepi(80);
       assertTrue(check.contains("Warning"), "CONFIG FOR MPSK  PUSHED");
       assertTrue(!new APUtils(WebportalParam.ap1IPaddress).Mpskstatus(WebportalParam.ap1Model), "CONFIG FOR MPSK PUSHED");
       
       
   }
}
   
