package webportal.CF.PRJCBUGEN_T24427;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.ContentFilteringElement;
import webportal.weboperation.AccountPage;
import webportal.weboperation.ContentFilteringPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.GlobalNotificationPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("CF") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24427") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the disable content filtering message, if the user attempts to disable the feature") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24427") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccountorbi();
    }

    @Step("Test Step 2: enable content filtering ")
    public void step2() {
        String num = "1";
        new GlobalNotificationPage().enterDeviceYes(WebportalParam.ob1serialNo);
        new ContentFilteringPage().gotoCF();
        new ContentFilteringPage().EnableOrDisableCF(num);

    }
   
    @Step("Test Step 3: disable and check for PopUP ")
    public void step3() {
        
        assertTrue(new ContentFilteringPage().DisableCF(),"pop did not appear while disabling CF");
        assertTrue(new ContentFilteringPage().isenableCF(),"CF disabled sucessfully");
    }
    
   
}
