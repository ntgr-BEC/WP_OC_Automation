package webportal.NatMode.user.Part1.PRJCBUGEN_T33754;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertFalse;
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
   @Story("PRJCBUGEN_T33754") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("NAT can not enable when managerment vlan is not 1") // It's a test case title from Jira Test Case.
   @TmsLink("PRJCBUGEN-T33754") // It's a testcase id/link from Jira Test Case.

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

   @Step("Test Step 2: Create Bridge SSID WITH WPA2 SECURITY ")
   public void step2() {
       ssidInfo.put("SSID", "apwp14008");
       ssidInfo.put("Security", "WPA2 Personal");
       ssidInfo.put("Password", "12345678");
       new WirelessQuickViewPage().addSsid(ssidInfo);     
       new WirelessQuickViewPage().changeVLANID("apwp14008");
   }
   
   
   @Step("Test Step 3: Make sure new custom vlan should not support nat mode")
   public void step3() {
       
   
             WirelessQuickViewPage page =new WirelessQuickViewPage();
             assertFalse(page.BridgeToNat("apwp14008"),"Failed check logs");
                          
   }
}
   

   



