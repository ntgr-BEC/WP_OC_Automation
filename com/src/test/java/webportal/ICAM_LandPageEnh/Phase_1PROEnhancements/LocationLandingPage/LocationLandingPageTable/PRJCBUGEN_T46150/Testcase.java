package webportal.ICAM_LandPageEnh.Phase_1PROEnhancements.LocationLandingPage.LocationLandingPageTable.PRJCBUGEN_T46150;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import static com.codeborne.selenide.Selenide.*;
import org.testng.annotations.Test;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("LocationLandingPageTable") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T46150") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that create location") // It's // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T46150") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).openOrg("Netgear");
        new AccountPage(false).deleteOneLocation("office2");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }

    @Step("Test Step 2: Create new location and verify")
    public void step2() {

        new OrganizationPage(false).openOrg("Netgear");
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "office2");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "70184");
        locationInfo.put("Country", "Germany");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.waitReady();
        new OrganizationPage(false).openOrg("Netgear");
        MyCommonAPIs.sleepi(5);
        assertTrue(new OrganizationPage(false).verifyLocName("office2").shouldBe(Condition.visible).isDisplayed(),
                "Created location name is not displayed on location landing page");

    }

}
