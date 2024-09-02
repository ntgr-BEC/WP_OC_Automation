package webportal.MarketPlace.Premium.PRJCBUGEN_T10321;

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
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random              r              = new Random();
    int                 num            = r.nextInt(10000000);
    String              mailname       = "";
    Map<String, String> vpnPaymentInfo = new CommonDataType().PAYMENT_INFO;

    @Feature("MarketPlace.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T10321") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that is user is able to delete VPN group if there are insufficient credits") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T10321") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation(WebportalParam.location1);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num);
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T10321");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Buy premium subscription then check buy vpn services;")
    public void step2() {
        
        new HamburgerMenuPage(false).closeLockedDialog();
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();

        handle.gotoLoction();
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.br2serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.br2deveiceMac);
                        
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "4");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T10321");
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
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);
        if (new HamburgerMenuPage(false).checkSubscriptionScreen(paymentInfo.get("Number of Device Credits"))) {
            vpnPaymentInfo.put("First Name", mailname);
            vpnPaymentInfo.put("Last Name", "T10321");
            vpnPaymentInfo.put("Email", mailname + "@mailinator.com");
            vpnPaymentInfo.put("YearNum", "1");
            vpnPaymentInfo.put("BuyNum", "2");
            new InsightServicesPage().buyVpnProducts(vpnPaymentInfo);

            assertTrue(
                    new HamburgerMenuPage().checkBuyVpnResult(vpnPaymentInfo.get("YearNum"), vpnPaymentInfo.get("BuyNum"))
                            && new HamburgerMenuPage().checkVpnServicesSubscription(vpnPaymentInfo.get("BuyNum"), vpnPaymentInfo.get("BuyNum")),
                    "Vpn services page display incorrect.");
        } else {
            assertTrue(false, "Buy premium subscription unsuccess.");
        }
    }

    @Step("Test Step 3: Add three vpn groups, check group limit and delete one group;")
    public void step3() {
      
        
        
        
        String vpnGroupName = "vpn";
        int i = 0;
        while (i < 3) {
            brrp.gotoPage();
            brrp.createVPNGroup(vpnGroupName + String.valueOf(i));
            MyCommonAPIs.sleepi(5);
            i += 1;
        }

        if (brrp.checkVpnGroupNumLimit("close")) {
            brrp.deleteGroup("vpn2");
            assertTrue(!brrp.checkVpnGroupIsExist("vpn2"), "Delete one group unsuccess.");
        } else {
            assertTrue(false, "Vpn group credits is incorrect.");
        }
    }

}
