package webportal.CFD.New_CFD_cases.PRO.PRJCBUGEN_T35149;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.CommonDataType;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.publicstep.WebCheck;
import webportal.webelements.WirelessQuickViewElement;
import webportal.param.WebportalParam;
import webportal.weboperation.*;
/**
*
* @author Ravishankar
*
*/
public class Testcase extends TestCaseBase {

   Map<String, String> ssidInfo = new HashMap<String, String>();
   WirelessQuickViewPage page= new WirelessQuickViewPage(false);
   OrganizationPage OrganizationPage = new OrganizationPage();

   @Feature("CFD") // It's a folder/component name to make test suite more readable from Jira Test Case.
   @Story("PRJCBUGEN_T35149") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("Test to verify the inside of the network and settings-->clients, here also shows the connected client count and correct number of data.") // It's a test case title from Jira Test Case.
   @TmsLink("PRJCBUGEN_T35149") // It's a testcase id/link from Jira Test Case.

   @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
   public void test() throws Exception {
       runTest(this);
   }

   @AfterMethod(alwaysRun = true)
   public void tearDown() {
       System.out.println("start to do tearDown");
       new WirelessQuickViewPage().deleteALLSSID();
   }
   
//    Each step is a single test step from Jira Test Case
   @Step("Test Step 1: Login IM WP success;")
   public void step1() {
       WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
       webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

      
       OrganizationPage.openOrg(WebportalParam.Organizations);
       handle.gotoLoction();
       new WirelessQuickViewPage().deleteALLSSID();
       
   }

   @Step("Test Step 2: Create SSID ")
   public void step2() {
       Map<String, String> ssidInfo = new HashMap<String, String>();
       ssidInfo.put("SSID", "T35149");
       ssidInfo.put("Security", "WPA3 Personal");
       ssidInfo.put("Password", "12345678");
       new WirelessQuickViewPage().addSsid(ssidInfo);
       MyCommonAPIs.sleepi(35);
       new WirelessQuickViewPage().connectClient(ssidInfo);
     
   }
   
  @Step("Test Step 3:check whether the client is getting listed in connected tab or not")
   public void step3() {

      MyCommonAPIs.sleepi(10);
      new OrganizationPage().goToOrgwideClient(WebportalParam.Organizations);
      assertTrue(new WirelessQuickViewPage().connectedCheck(),"NOT LISTED IN CLIENT");
  }
  
  @Step("Test Step 4:check whether the client is getting listed in disconnected tab after disconnecting")
  public void step4() {
      
     new WirelessQuickViewPage().deleteSsidYes("T35149");
     MyCommonAPIs.sleepi(5);
     new OrganizationPage().goToOrgwideClient(WebportalParam.Organizations);
     MyCommonAPIs.sleepi(5);
     assertTrue(new WirelessQuickViewPage().disconnectedCheck(),"NOT LISTED IN CLIENT");
          
 }
  
 
  }

     
     
   
   