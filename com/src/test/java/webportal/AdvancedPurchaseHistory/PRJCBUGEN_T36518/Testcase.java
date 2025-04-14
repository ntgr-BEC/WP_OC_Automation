package webportal.AdvancedPurchaseHistory.PRJCBUGEN_T36518;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.PostManPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Vivek Singh
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo     = new HashMap<String, String>();
    Random              r            = new Random();
    int                 num          = r.nextInt(10000000);
    String              mailname     = "purhtry" + String.valueOf(num);
    String              locationName = "PurchaseHistoryLoc";

    @Feature("IM-7.2 AdvancedPurchaseHistory") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T36518") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Purchase History All Details after purchasing the Premium Trial to Premium Yearly For 1 Device") // It's a test
                                                                                                                                  // case title from
                                                                                                                                  // Jira
    // Test Case.
    @TmsLink("PRJCBUGEN-T36518") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @BeforeMethod(alwaysRun = true)
    public void tearUp() {

        new PostManPage().Deregister(WebportalParam.ap1serialNo);

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation(locationName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T36518");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear1@");
        accountInfo.put("Confirm Password", "Netgear1@");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Create a location")
    public void step2() {

        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        new AccountPage(false).enterLocation(locationName);

    }

    @Step("Test Step 3: On-Boarding First HBB Dummy Device")
    public void step3() {
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ap1serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        System.out.println(firststdevInfo);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        new AccountPage().enterLocation(locationName);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

    }

    @Step("Test Step 4: Verify that user can buy premium Trail to Premium Monthly")
    public void step5() {
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo = new CommonDataType().CARD_INFO;
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "2");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17562");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Springs Rd");
        paymentInfo.put("City", "Red Bank");
        paymentInfo.put("Zip", "32003");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);

    }

    @Step("Test Step 6: Open Purchase History Page and expand Subs Div Credits Section")
    public void step6() {
        new HamburgerMenuPage().openAccountMgmt();
        new HamburgerMenuPage(false).expandinsigtdivCreditsSection();

    }

    @Step("Test Step 7: Verify Premium Trial to Premium Yearly subscription Text are present there")
    public void step7() {
        assertTrue(new HamburgerMenuPage(false).VerifyThePreTrialToPreYearlySubsText(),
                "Premium Trial to Premium Yearly subscription Text are not present");

    }

    @Step("Test Step8: Verify The Credit Count should be 2")
    public void step8() {
        assertTrue(new HamburgerMenuPage(false).VerifyPreTrialToPreMonthlySubsQuantity(), "Credit Count is not 2");

    }

    @Step("Test Step9: Verify the Activation date and Expiry date should be according to testcase")
    public void step9() {
        assertTrue(new HamburgerMenuPage(false).VerifyThePreTrialActivationAndExpiryDatesYearly(), "Activation Date is not equal to Today's Date");

    }
}
