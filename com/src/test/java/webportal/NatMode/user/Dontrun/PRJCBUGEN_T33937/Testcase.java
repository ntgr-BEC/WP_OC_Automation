package webportal.NatMode.user.Dontrun.PRJCBUGEN_T33937;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;


import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
import webportal.param.WebportalParam;
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
   @Story("PRJCBUGEN_T33937") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("verify whether the bridge mode ssid is pushed or not") // It's a test case title from Jira Test Case.
   @TmsLink("PRJCBUGEN-T33937") // It's a testcase id/link from Jira Test Case.

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

   @Step("Test Step 2: Create Bridge SSID ")
   public void step2() {
       ssidInfo.put("SSID", "apwp14008");
       ssidInfo.put("Security", "WPA2 Personal");
       ssidInfo.put("Password", "12345678");
       new WirelessQuickViewPage().addSsidEnablingFastRoaming(ssidInfo);  
//       MyCommonAPIs.sleepi(180);
   }
   @Step("Test Step 3: Check whether the configurations are pushed to the ap")
   public void step3() {
       
//       new RunCommand().enableSSH4AP(WebportalParam.ap1IPaddress, WebportalParam.loginPassword);
       MyCommonAPIs.sleepi(120);
       assertTrue(new APUtils(WebportalParam.ap1IPaddress).get11REnableStatusnew(WebportalParam.ap1Model), "CONFIG NOT PUSHED");
   }
   
   

}