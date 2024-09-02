package webportal.Switch.DhcpSnooping.PRJCBUGEN_T35597;
 
 
 import static com.codeborne.selenide.Selenide.$;


 import static org.testng.Assert.assertEquals;
 import static org.testng.Assert.assertFalse;
 import static org.testng.Assert.assertTrue;

 import java.util.List;

 import org.testng.annotations.AfterMethod;
 import org.testng.annotations.Test;

 import io.qameta.allure.Description;
 import io.qameta.allure.Feature;
 import io.qameta.allure.Step;
 import io.qameta.allure.Story;
 import io.qameta.allure.TmsLink;
 import testbase.TestCaseBase;
 import util.MyCommonAPIs;
 import webportal.weboperation.WebportalLoginPage;
 import webportal.weboperation.WiredDhcpSnoopingPage;
 import webportal.weboperation.WiredGroupPortConfigPage;
 import webportal.weboperation.WiredQuickViewPage;
 import webportal.weboperation.WiredVLANPage;
 import webportal.weboperation.WiredVLANPageForVLANPage;
 import webportal.param.URLParam;
 import webportal.param.WebportalParam;
 import webportal.webelements.WiredDhcpSnoopingElement;
 
 
 /**
  * @author ravi
  */
 public class Testcase extends TestCaseBase {

     @Feature("Switch.DhcpSnooping") // It's a folder/component name to make test suite more readable from Jira Test Case.
     @Story("PRJCBUGEN_T35597") // It's a testcase id/link from Jira Test Case but replace - with _.
     @Description("Test to verify global and Port configuration is present under DHCP Snooping config") // It's a testcase title from Jira Test Case.
     @TmsLink("PRJCBUGEN-T35597") // It's a testcase id/link from Jira Test Case.
     @Test(alwaysRun = true, groups = "p2")
     public void test() throws Exception {
         runTest(this);
     }
     
     
     // Each step is a single test step from Jira Test Case
     @Step("Test Step 1: enter setting page")
     public void step1() {
         // enter into the wired settings page
         WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
         webportalLoginPage.defaultLogin();

         handle.gotoLoction();
         handle.gotoLocationWireSettings();

     }

     
     // Each step is a single test step from Jira Test Case
     @Step("Test Step 2: Test to verify that DHCP Snooping  has Global Configuration & Port Configuration")
     public void step2() {
    	 
    	 String goToSnoopingGlobalConfigPage = wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingGlobalConfig);
         if (goToSnoopingGlobalConfigPage != null) {
             assertTrue(WiredDhcpSnoopingElement.dhcpSnoopingMode.exists(), "Dhcp Snooping Global Config is not available in this page");

         }

         String goToSnoopingPortConfigPage = wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingPortConfig);
         if (goToSnoopingPortConfigPage != null) {
             assertTrue(WiredDhcpSnoopingElement.selectButton("Trust Mode").exists(), "Dhcp Snooping Port Config is not available in this page");
         }

     }

 }
     