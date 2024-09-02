package webportal.OrbiProAx5YrsSubscription.PRJCBUGEN_T28130;

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
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;

/**
 *
 * @author Vivek
 *
 */
public class Testcase extends TestCaseBase {

    String organizationName = "OnBoardingTest";
    String locationName     = "OnBoardingTest";

    @Feature("OrbiProAx5YrsSubscription") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28130") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify String Changes for 5 year plan on hard bundle active and expiry") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T28130") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new DevicesDashPage().deleteDeviceNo(WebportalParam.ob1serialNo);
        new DevicesDashPage().deleteDeviceNo(WebportalParam.ob2serialNo);
        new DevicesDashPage().deleteDeviceNo(WebportalParam.ob3serialNo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Onboarding SXR30 Orbi Device ")
    public void step2() {
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ob1serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ob2MAC_Address);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
    }

    @Step("Test Step 3: Onboarding SXR80 Orbi Device ")
    public void step3() {
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ob2serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ob2MAC_Address);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
    }

    @Step("Test Step 4: Onboarding SXR50 Orbi Device ")
    public void step4() {
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ob3serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ob2MAC_Address);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
    }

    @Step("Test Step 5: Opening the Insight Included with Hardware Section ")
    public void step5() {
        new HamburgerMenuPage().openAccountMgmt();
        new InsightServicesPage(false).openInsightIncludedwithHardwareSection();

    }

    @Step("Test Step 6: Check SXR30 Orbi Device  will have 5 year insight support from that date of on-boarding  ")
    public void step6() {
        assertTrue(new InsightServicesPage(false).verifyInsightPremiumFiveYearsubscription(WebportalParam.ob1serialNo),
                "Not Found for SXR30 Insight Premium 5-year subscription with 1 device bundle credit Text");

    }

    @Step("Test Step 7: Check SXR80 Orbi Device  will have 5 year insight support from that date of on-boarding  ")
    public void step7() {
        assertTrue(new InsightServicesPage(false).verifyInsightPremiumFiveYearsubscription(WebportalParam.ob2serialNo),
                "Not Found for SXR80 Insight Premium 5-year subscription with 1 device bundle credit Text");

    }

    @Step("Test Step 8: Check SXR50 Orbi Device  will have 5 year insight support from that date of on-boarding  ")
    public void step8() {
        assertTrue(new InsightServicesPage(false).verifyInsightPremiumFiveYearsubscription(WebportalParam.ob3serialNo),
                "Not Found for SXR50 Insight Premium 5-year subscription with 1 device bundle credit Text");

    }

}
