package webportal.MUBBilling.PRJCBUGEN_T21899;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.PostManPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num) + "@yopmail.com";

    @Feature("MUBBilling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21899") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that Admin Pro user is able to Add/Edit/Delete customer billing information at any time.") // It's a testcase title from
                                                                                                                    // Jira Test Case.
    @TmsLink("PRJCBUGEN_T21899") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @BeforeMethod(alwaysRun = true)
    public void tearUp() {

        new PostManPage().Deregister(WebportalParam.ap1serialNo);

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        new OrganizationPage().deleteOrganizationNew(WebportalParam.Organizations);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        if (new HamburgerMenuPage(false).checkCreateProAccountPage("checkNext:" + mailname)) {
            Map<String, String> proAccountInfo = new HashMap<String, String>();
            proAccountInfo.put("Confirm Email", mailname);
            proAccountInfo.put("Password", "Netgear1@");
            proAccountInfo.put("Confirm Password", "Netgear1@");
            proAccountInfo.put("Country", "United States of America");
            proAccountInfo.put("Phone Number", "1234567890");
            new HamburgerMenuPage(false).CreateProAccount(proAccountInfo);

            Map<String, String> businessInfo = new HashMap<String, String>();
            businessInfo.put("Business Name", "Netgear");
            businessInfo.put("Primary Address of Business", "test 1st");
            businessInfo.put("City", "Florida");
            businessInfo.put("State", "Florida");
            businessInfo.put("Zip Code", "32003");
            businessInfo.put("Country", "United States of America");
            businessInfo.put("Business Phone Number", "1234567890");

            assertTrue(new HamburgerMenuPage(false).freeTrail(businessInfo), " Free Trail pro Account creation failed. ");
            System.out.println(mailname);
            System.out.println("Netgear1@");
            new WebportalLoginPage(false).logintoProAccAfterCreatingacc(mailname, "Netgear1@");
            new OrganizationPage(false).clickonOkayGotit();
            assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess.");
        }
    }

    @Step("Test Step 2: create new location and onboard AP;")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", WebportalParam.Organizations);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        new HardBundlePage().verifyAndActivateFreelicenseKeys();
        OrganizationPage.openOrg(WebportalParam.Organizations);
        OrganizationPage.creditAllocation2(WebportalParam.Organizations);

        OrganizationPage.openOrg(WebportalParam.Organizations);
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);

        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction("OnBoardingTest");
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("Device Name", WebportalParam.ap1deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap1macaddress);
        new DevicesDashPage().addNewDevice(devInfo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
    }

    @Step("Test Step 3: Using the toggle button enable the MUB option; Now scroll down and click on Manage Payment Methods")
    public void step3() {
        new HamburgerMenuPage(false).GooMUB();

        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = currentDate.plusMonths(1);

        int expirationDay = expirationDate.getDayOfMonth();
        String expirationMonth = expirationDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String expirationYear = String.valueOf(expirationDate.getYear());

        System.out.println(expirationMonth + " " + expirationYear);

        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("First Name", "MUBI");
        paymentInfo.put("Last Name", "T1752");
        paymentInfo.put("Street Address", "Spring Road");
        paymentInfo.put("City", "Red Bank");
        paymentInfo.put("Zip", "32003");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        paymentInfo.put("Card Number", "4142621111111112");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", expirationMonth);
        paymentInfo.put("Expiration Year", expirationYear);
        assertTrue(new HamburgerMenuPage(false).FillPaymentMethodsAndVerify(paymentInfo), "MUB Billing payment not done successfully");

    }

}
