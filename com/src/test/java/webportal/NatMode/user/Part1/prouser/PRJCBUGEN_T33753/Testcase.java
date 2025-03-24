package webportal.NatMode.user.Part1.prouser.PRJCBUGEN_T33753;

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
   @Story("PRJCBUGEN_T33753") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("VLAN ID under SSID setting configuration should be Management VLAN to enable NAT mode.") // It's a test case title from Jira Test Case.
   @TmsLink("PRJCBUGEN-T33753") // It's a testcase id/link from Jira Test Case.

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

   @Step("Test Step 2: Create NAT SSID WITH WPA2 SECURITY ")
   public void step2() {
       ssidInfo.put("SSID", "apwp14008");
       ssidInfo.put("Security", "WPA2 Personal");
       ssidInfo.put("Password", "12345678");
       new WirelessQuickViewPage().addSsidNat(ssidInfo);        
   }
   
   
   @Step("Test Step 3: Make sure that the default management vlan is 1")
   public void step3() {
       
   
             WirelessQuickViewPage page =new WirelessQuickViewPage();
             page.settingsorquickview.click();
             MyCommonAPIs.sleep(5);
             if (page.checkSsidIsExist("apwp14008")) {
                 page.clickEditSsid("apwp14008");
             }          
             MyCommonAPIs.sleepi(5);
                      
             System.out.print(page.Managementvlan.getText());
             assertTrue((page.Managementvlan.getText()).contains("Management VLAN(1)"),"Default is not proper");
                                                 
         
                          
   }
}
   

   



