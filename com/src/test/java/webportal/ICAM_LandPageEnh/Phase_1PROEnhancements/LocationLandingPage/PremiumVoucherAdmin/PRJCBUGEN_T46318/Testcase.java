package webportal.ICAM_LandPageEnh.Phase_1PROEnhancements.LocationLandingPage.PremiumVoucherAdmin.PRJCBUGEN_T46318;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("PremiumVoucherAdmin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T46318") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that able to see option for voucher admin")
    @TmsLink("PRJCBUGEN_T46318") // It's a testcase id/link from Jira Test Case.

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
    }

    @Step("Test Step 2: Verify voucher admin option in settings icon dropdown;")
    public void step2() {

        MyCommonAPIs.sleepi(10);
        new OrganizationPage(false).settingMenuAdminDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        assertTrue(new OrganizationPage(false).voucherAdminsLink.shouldBe(Condition.visible).isDisplayed(),
                "voucher admin option in settings icon dropdown is not visible");
    }

}
