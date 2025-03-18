package webportal.DisconnectedTime.PRJCBUGEN_T28403;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    
    @Feature("Disconnected Time") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28403") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("* icon  for  Last known information is  shown in wireless   tab  for  premium account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T28403") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("office1");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "office1");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", WebportalParam.Country);
//        new HamburgerMenuPage();
        new AccountPage().addNetwork(locationInfo);
        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount1();
    }

    @Step("Test Step 2: Verify last known information on device dashboard page; ")
    public void step2() {
        assertTrue(new WirelessQuickViewPage().lastknowninfoverify(),"Device last known information is not verified.");
    }
}
