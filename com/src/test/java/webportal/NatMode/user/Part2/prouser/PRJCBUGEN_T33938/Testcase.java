package webportal.NatMode.user.Part2.prouser.PRJCBUGEN_T33938;

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
import util.RunCommand;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
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
   @Story("PRJCBUGEN_T33938") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("verify whether the Fastroaming is available when nat mode is selected") // It's a test case title from Jira Test Case.
   @TmsLink("PRJCBUGEN-T33938") // It's a testcase id/link from Jira Test Case.

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
       webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
       new HamburgerMenuPage(false).closeLockedDialog();
       new OrganizationPage(false).openOrg(WebportalParam.Organizations);

       handle.gotoLoction();
       // new DevicesDashPage().checkDeviceInAdminAccount();
   }

   @Step("Test Step 2: Create NAT SSID ")
   public void step2() {
       ssidInfo.put("SSID", "apwp14008");
       ssidInfo.put("Security", "WPA2 Personal");
       ssidInfo.put("Password", "12345678");
       new WirelessQuickViewPage().addSsidNat(ssidInfo);  
       MyCommonAPIs.sleepi(30);
   }
  @Step("Test Step 3: Check whether Fastroaming is available or not in Natmode")
   public void step3() {
     new WirelessQuickViewPage().EditSSID(ssidInfo);
     assertFalse(new WirelessQuickViewPage().fastroaming1.exists(), "Fastroamingvisible");
     
     
   }
   
   

}