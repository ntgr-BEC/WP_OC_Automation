package webportal.ICAM_LandPageEnh.Phase_1PROEnhancements.LocationLandingPage.LocationLandingHeader.Premium.PRJCBUGEN_T46072;

import static org.junit.Assert.assertTrue;
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

    @Feature("LocationLandingHeader") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T46072") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that able to see and edit user’s name on location page header") // It's // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T46072") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        new AccountPage(false).editNetwork("office1");
        MyCommonAPIs.sleepi(10);
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "office1");
        new AccountPage(false).editLocation("office2", locationInfo);
        System.out.println("start to do tearDown");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to premium account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

    }

    @Step("Test Step 2: Verify Edit Location name and verify")
    public void step2() {

        new AccountPage(false).editNetwork("office1");
        MyCommonAPIs.sleepi(10);
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "office2");
        new AccountPage(false).editLocation("office1", locationInfo);
        MyCommonAPIs.sleepi(5);
        new AccountPage();
        MyCommonAPIs.sleepi(5);
        System.out.println("Visisble Location name is "+new OrganizationPage(false).verifyLocName("office2").shouldBe(Condition.visible).getText());
        assertTrue(new OrganizationPage(false).verifyLocName("office2").shouldBe(Condition.visible).isDisplayed(),
                "On location dashboard page location name is not visisble");

    }

}
