package webportal.OrbiProAx5YrsSubscription.PRJCBUGEN_T28129;

import static org.testng.Assert.assertFalse;
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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Vivek
 *
 */
public class Testcase extends TestCaseBase {

    String organizationName = "Netgear";
    String locationName     = "office1";

    @Feature("OrbiProAx5YrsSubscription") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28129") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, when 5 year orbi supported device SXK30 is on-boarded to pro account it must not carry 5 year plan") // It's a
                                                                                                                                       // testcase
                                                                                                                                       // title from
                                                                                                                                       // Jira Test
                                                                                                                                       // Case.
    @TmsLink("PRJCBUGEN_T28129") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new DevicesDashPage().deleteDeviceNo(WebportalParam.ob1serialNo);

    }

    @Step("Test Step 1: Login With Pro Account IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Going to Existing Location With Name 'Netgear'")
    public void step2() {
        handle.gotoLoction();
    }

    @Step("Test Step 3: Onboarding SXR30 Orbi Device ")
    public void step3() {
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ob1serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ob2MAC_Address);
        new DevicesDashPage(false).addNewdummyDeviceProAccount(firststdevInfo);
    }

    @Step("Test Step 4: Opening the Insight Included with Hardware Section ")
    public void step4() {
        new HamburgerMenuPage().openAccountMgmt();
        new InsightServicesPage(false).openInsightIncludedwithHardwareSection();
    }

    @Step("Test Step 5: Check Orbi Device  should  not have 5 year insight support from that date of on-boarding  ")
    public void step5() {
        assertFalse((new InsightServicesPage(false).verifyInsightPremiumFiveYearsubscription(WebportalParam.ob1serialNo)),
                "For Pro Account 5-year subscription should not be visible but it Visible please check---Error  ");
    }

}
