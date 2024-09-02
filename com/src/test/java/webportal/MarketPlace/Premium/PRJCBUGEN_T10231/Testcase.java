package webportal.MarketPlace.Premium.PRJCBUGEN_T10231;

import static com.codeborne.selenide.Selenide.$$x;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
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

    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);
    Map<String, String> paymentInfo = new CommonDataType().PAYMENT_INFO;

    @Feature("MarketPlace.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T10231") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that complete purchsing history is present under purchased history tab.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T10231") // It's a testcase id/link from Jira Test Case.

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

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T10231");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Buy vpn services and check order history page display;")
    public void step2() {
//        new HamburgerMenuPage(false).clickAddInsightIncludedDevices();
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);
        new AccountPage(false).enterLocation(WebportalParam.location1);
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.br2serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.br2deveiceMac);
                        
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "4");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T10327");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Street 4568 James Avenue");
        paymentInfo.put("City", "INVERNESS");
        paymentInfo.put("Zip", "32751");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        paymentInfo.put("Card Number", "4112344112344113");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T10231");
        paymentInfo.put("Email", WebportalParam.loginName);
        paymentInfo.put("YearNum", "1");
        paymentInfo.put("BuyNum", "2");
        new InsightServicesPage().buyVpnProducts(paymentInfo);

        if (new HamburgerMenuPage().checkBuyVpnResult(paymentInfo.get("YearNum"), paymentInfo.get("BuyNum"))
                && !new HamburgerMenuPage(false).getOrderStatus().contains("Cancelled")) {
            new HamburgerMenuPage().cancelVpnServices();
            new HamburgerMenuPage().gotoVpnResult();

            boolean result = false;
            ElementsCollection eles = $$x(new HamburgerMenuPage(false).vpnOrderTable);
            String actOnDate = "";
            String expOnDate = "";
            String orderQty = "";
            if (new HamburgerMenuPage(false).orderhistoryqty.exists()) {
                for (SelenideElement ele : eles) {
                    if (ele.findElement(By.xpath("td[3]/p[1]")).getText().contains("Cancelled")) {
                        String actOnDateText = ele.findElement(By.xpath("td[3]/p[2]")).getText();
                        String expOnDateText = ele.findElement(By.xpath("td[4]")).getText();
                        actOnDate = actOnDateText.substring(actOnDateText.lastIndexOf(",") + 2, actOnDateText.length());
                        expOnDate = expOnDateText.substring(expOnDateText.lastIndexOf(",") + 2, expOnDateText.length());
                        orderQty = ele.findElement(By.xpath("td[2]/span")).getText();
                        break;
                    }
                }
                if (((Integer.valueOf(expOnDate) - Integer.valueOf(actOnDate)) == Integer.valueOf(paymentInfo.get("YearNum")))
                        && orderQty.equals(paymentInfo.get("BuyNum"))) {
                    result = true;
                    logger.info("Order history display correct.");
                }
            }
            assertTrue(result && new HamburgerMenuPage(false).getOrderStatus().contains("Cancelled"), "Order history display incorrect.");
        } else {
            assertTrue(false, "Order history display incorrect.");
        }
    }

}
