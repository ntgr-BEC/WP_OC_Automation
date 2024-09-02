package webportal.DirectPurchase.NewProUser.PRJCBUGEN_T18344;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random              r                = new Random();
    int                 num              = r.nextInt(10000000);
    String              mailname         = "apwptest" + String.valueOf(num);
    Map<String, String> paymentInfo      = new HashMap<String, String>();
    Map<String, String> accountInfo      = new HashMap<String, String>();
    String              organizationName = "test18344";
    String              locationName     = "ntk18344";

    @Feature("DirectPurchase.NewProUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18344") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify when User cancel Pro pack within 3 days of subscription,Non Hard bundle device can't added organization and banner should present") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18344") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        String url = MyCommonAPIs.getCurrentUrl();
        if (url.contains("/devices/dash")) {
            OrganizationPage OrganizationPage = new OrganizationPage();
            OrganizationPage.deleteOrganization(organizationName);
        } else {
            Selenide.close();
            startBrowser();
            WebportalLoginPage webportallogin = new WebportalLoginPage(true);
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create new account;")
    public void step1() {
        new HamburgerMenuPage(false).openInsightSubscriptionPlanPage();
        new HamburgerMenuPage(false).clickLoginAndSubscribe("pro");

        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T18344");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Buy pro subscription and check cancel subscription;")
    public void step2() {
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T18344");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Street 4568 James Avenue");
        paymentInfo.put("City", "INVERNESS");
        paymentInfo.put("Zip", "34451");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        paymentInfo.put("Card Number", "4112344112344113");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new InsightServicesPage(false).inputPaymentPage(paymentInfo);
        MyCommonAPIs.sleepsync();

        Map<String, String> businessInfo = new HashMap<String, String>();
        businessInfo.put("Business Name", "Netgear");
        businessInfo.put("Primary Address of Business", "test 1st");
        businessInfo.put("City", "NewYork");
        businessInfo.put("State", "test");
        businessInfo.put("Zip Code", "12345");
        businessInfo.put("Country", "United States of America");
        businessInfo.put("Business Phone Number", "1234567890");
        new HamburgerMenuPage(false).inputBusinessInfo(businessInfo);
        new HamburgerMenuPage(false).clickBusinessInfoPageButton();
        MyCommonAPIs.sleepsync();

        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        new HamburgerMenuPage(false);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);

        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);

        if (new HamburgerMenuPage(true).checkProAccountSubscriptionInfo()) {
            if (new HamburgerMenuPage(false).checkDisplayCancelDirectPurchase("pro")) {
                OrganizationPage.gotoPage();
                OrganizationPage.openOrg(organizationName);

                HashMap<String, String> devInfo = new HashMap<String, String>();
                devInfo.put("Serial Number", WebportalParam.ap1serialNo);
                devInfo.put("Device Name", WebportalParam.ap1deveiceName);

                new AccountPage(false).enterLocation(locationInfo.get("Location Name"));
                assertTrue(!new DevicesDashPage().checkAddDevice(devInfo), "Add device failed.");
            } else {
                assertTrue(false, "Not found cancel button.");
            }
        } else {
            assertTrue(false, "Subscription incorrect.");
        }
    }

}
