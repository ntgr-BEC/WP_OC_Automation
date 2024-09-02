package webportal.PaymentAndSubscription.PRJCBUGEN_T16729;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

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
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> paymentInfo = new HashMap<String, String>();

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);

    @Feature("PaymentAndSubscription") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16729") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("After getting error message with more device credit needed, minimize the page and go to web and remove the device and open the billing page to see the error message goes away and required device credit count changes") // It's
                                                                                                                                                                                                                                            // a
                                                                                                                                                                                                                                            // testcase
                                                                                                                                                                                                                                            // title
    // from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16729") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (new HamburgerMenuPage(true).checkSubscriptionScreenForBasic()) {
            new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);
        }

        new AccountPage().enterLocation(WebportalParam.location1);

        if (!new DevicesDashPage().getDeviceName(WebportalParam.ap1serialNo).equals("")) {
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        }
        if (!new DevicesDashPage().getDeviceName(WebportalParam.ap2serialNo).equals("")) {
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ap2serialNo);
        }
        if (!new DevicesDashPage().getDeviceName(WebportalParam.ap3serialNo).equals("")) {
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ap3serialNo);
        }

        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T16729");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Buy yearly premium subscription;")
    public void step2() {
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "3");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T16729");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Street 4568 James Avenue");
        paymentInfo.put("City", "INVERNESS");
        paymentInfo.put("Zip", "34451");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        paymentInfo.put("Card Number", "5116601234567894");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);

        assertTrue(
                new HamburgerMenuPage(false).checkSubscriptionScreen(paymentInfo.get("Number of Device Credits"))
                        && new HamburgerMenuPage().checkDevicesBilling(paymentInfo.get("Number of Device Credits"), "yearly", "US"),
                "Buy premium subscription unsuccess.");
    }

    @Step("Test Step 3: Add three devices and cancel subscription,check devices more needed in subscription page;")
    public void step3() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();

        handle.gotoLoction();

        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("Device Name", WebportalParam.ap1deveiceName);
        new DevicesDashPage().addNewDevice(devInfo);

        devInfo.put("Serial Number", WebportalParam.ap2serialNo);
        devInfo.put("Device Name", WebportalParam.ap2deveiceName);
        new DevicesDashPage().addNewDevice(devInfo);

        devInfo.put("Serial Number", WebportalParam.ap3serialNo);
        devInfo.put("Device Name", WebportalParam.ap3deveiceName);
        new DevicesDashPage().addNewDevice(devInfo);

        new HamburgerMenuPage().cancelSubscription();

        assertTrue(new HamburgerMenuPage(false).checkSubscriptionScreenForBasic()
                && new HamburgerMenuPage().checkMoreNeeded(paymentInfo.get("Number of Device Credits")));

    }

    @Step("Test Step 4: Buy yearly premium subscription again and delete one device, then cancel subscription and check subscription page;")
    public void step4() {
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);

        handle.gotoLoction();

        if (!new DevicesDashPage().getDeviceName(WebportalParam.ap1serialNo).equals("")) {
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        }

        new HamburgerMenuPage().cancelSubscription();

        assertTrue(new HamburgerMenuPage(false).checkSubscriptionScreenForBasic()
                && !new HamburgerMenuPage().checkMoreNeeded(paymentInfo.get("Number of Device Credits")));

    }
}
