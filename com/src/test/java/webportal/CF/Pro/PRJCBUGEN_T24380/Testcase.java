package webportal.CF.Pro.PRJCBUGEN_T24380;

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
import webportal.weboperation.ContentFilteringPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.GlobalNotificationPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String cmd = "conf_get system:monitor:revSSHPort";

    @Feature("CF") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24380") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify user should be able to buy license key to use content filtering feature for a pro account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24380") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
     System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        
        new DevicesDashPage().checkDeviceInNormalAccountOrbi("admin");
    }

    @Step("Test Step 2:Try adding CF license;")
    public void step2() {
        Map<String, String> businessInfo = new HashMap<String, String>();
        businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
        new ContentFilteringPage().inputLicenceAndFinishSignin(businessInfo);
        MyCommonAPIs.sleepi(60);
        new ContentFilteringPage().inputLicenceAndFinishSignin(businessInfo);
        
        assertTrue(new ContentFilteringPage().checkCFServicesSubscription("2", "2"),"CF page is not right");
        assertTrue(new ContentFilteringPage().checkCFTopUpSubscription("2", "2"),"CF page is not right");
    }


}
