package webportal.NatMode.user.Part2.prouser.PRJCBUGEN_T33920;

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
   @Story("PRJCBUGEN_T33920") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("verify Client Isolation on SSID with NatMode") // It's a test case title from Jira Test Case.
   @TmsLink("PRJCBUGEN-T33920") // It's a testcase id/link from Jira Test Case.

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
       new WirelessQuickViewPage().deleteALLSSID();
       ssidInfo.put("SSID", "apwp14008");
       ssidInfo.put("Security", "WPA2 Personal");
       ssidInfo.put("Password", "12345678");
       new WirelessQuickViewPage().addSsidNat(ssidInfo); 
       new WirelessQuickViewPage().enableclientIsolationwired(ssidInfo.get("SSID"),WebportalParam. ap1IPaddress);
       MyCommonAPIs.sleepi(120);
   }
   
   
   @Step("Test Step 3: Add WIFI ssid and connect client to this ssid;")
   public void step3() {
       int sum = 0;
       while (true) {
           MyCommonAPIs.sleepi(10);
           if (new Javasocket()
                   .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14008")
                   .indexOf("true") != -1) {
               break;
           } else if (sum > 20) {
               assertTrue(false, "Client cannot connected.");
               break;
           }
           sum += 1;
       }
       
       MyCommonAPIs.sleepi(20);

       boolean result1 = true;
       if (!new Javasocket()
               .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14008 12345678 WPA2PSK aes")
               .equals("true")) {
           result1 = false;
           MyCommonAPIs.sleepi(20);
           if (new Javasocket()
                   .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14008 12345678 WPA2PSK aes")
                   .equals("true")) {
               result1 = true;
           }
       }

       assertTrue(result1, "Client cannot connected.");
   }
  @Step("Test Step 4: Check whether Client Isolation  is into ap or not")
   public void step4() {
      
    
      MyCommonAPIs.sleepi(120);
      String output = new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
              "ipconfig | findstr /C:\"IPv4 Address\"    ");
      String n=output.substring(36, 49);
      System.out.print(n);
      assertTrue(n.contains("10.0"), "CONFIG FOR NAT NOT PUSHED");
      assertTrue(new APUtils(WebportalParam.ap1IPaddress).getNatStatus(WebportalParam.ap1Model), "CONFIG FOR NAT NOT PUSHED");
      assertTrue(new APUtils(WebportalParam.ap1IPaddress).checkclientIsolation(WebportalParam.ap1Model), "CONFIG FOR CLIENTISO NOT PUSHED");
  }
     

   }
   
   

