 package webportal.Switch.DhcpSnooping.PRJCBUGEN_T35598;
 
 
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
     @Story("PRJCBUGEN_T35598") // It's a testcase id/link from Jira Test Case but replace - with _.
     @Description("Test to verify that user is able to redirected to global config page by clicking the global config") // It's a testcase
                                                                                                                                 // title from
     // Jira Test Case.
     @TmsLink("PRJCBUGEN-T35598") // It's a testcase id/link from Jira Test Case.
     @Test(alwaysRun = true, groups = "p2")
     public void test() throws Exception {
         runTest(this);
     }
     
     
     // Each step is a single test step from Jira Test Case
     @Step("Test Step 1: enter setting page and Verify DHCP Snooping Global Config page")
     public void step1() {
         // enter into the wired settings page
         WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
         webportalLoginPage.defaultLogin();

         handle.gotoLoction();
         handle.gotoLocationWireSettings();
         
         String goToSnoopingGlobalConfigPage = wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingGlobalConfig);
         if (goToSnoopingGlobalConfigPage != null) {
             assertTrue(WiredDhcpSnoopingElement.checkDhcpGlobalConfigPage.exists(), "Dhcp Snooping Global Config page is not available");

         }
         
     }
     
    
 }