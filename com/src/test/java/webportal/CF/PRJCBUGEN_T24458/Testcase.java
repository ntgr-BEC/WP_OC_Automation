package webportal.CF.PRJCBUGEN_T24458;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

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
import webportal.param.WebportalParam;
import webportal.webelements.ContentFilteringElement;
import webportal.weboperation.ContentFilteringPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.GlobalNotificationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String Domain = "myntra.com";
    String Description = "people should use whatsapp";
    String SSID = (WebportalParam.OrbiDefaultSSID);
    String PASSWORD = (WebportalParam.OrbiDefaultPassword);
    

    @Feature("CF") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24458") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the user should not be allowed to enter same domain in the allow and block list") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24458") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
     System.out.println("start to do tearDown");
     MyCommonAPIs.sleepi(20);
     new ContentFilteringPage().DeleteAllowlist(Domain);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccountorbi();
    }

    @Step("Test Step 2: Send Catogory to blocked list;")
    public void step2() {
        
        String Num = "1";
        new GlobalNotificationPage().enterDeviceYes(WebportalParam.ob1serialNo);
        new ContentFilteringPage().gotoCF();
        new ContentFilteringPage().EnableOrDisableCF(Num);   
        new ContentFilteringPage().AddAllowlist(Domain, Description);    
        assertTrue(new ContentFilteringPage().AddBlocklistError(Domain, Description), "we did not get any error");
    }
    
   

}
