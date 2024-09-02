package webportal.BusinessVPNPayment.SmallOfficePackage2.PRJCBUGEN_T30889;

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
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);
   

    @Feature("Business VPN Payment and Subscription Small Office - 50 User") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27672") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that user can buy Business VPN license for Small Office - 50 User - Belgium ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27672") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("OnBoardingTest");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T10218");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Belgium");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Check buy vpn services;")
    public void step2() {

        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "B1000");
        locationInfo.put("Country", "Belgium");
        new AccountPage().addNetwork(locationInfo);
        
 }
    
    
    @Step("Test Step 3: Add device To the Network;")
    public void step3() {
        
       
        new AccountPage(false).enterLocation(WebportalParam.location1);
        
Map<String, String> firststdevInfo = new HashMap<String, String>();
       
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        
        System.out.println(firststdevInfo);
 
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
        
    }

    @Step("Test Step 4: Check buy bvpn services;")
    public void step4() {
        
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo = new CommonDataType().CARD_INFO;
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T10218");
        paymentInfo.put("Email", WebportalParam.loginName);
        paymentInfo.put("Subscription Type", "Small Office - 50 User");
        paymentInfo.put("BuyNum", "2");
        paymentInfo.put("YearNum", "1");
        paymentInfo.put("Street Address", "Bodenbroekstraat");
        paymentInfo.put("City", "Bodenbroekstraat ");
        paymentInfo.put("Zip", "B1000");
        paymentInfo.put("Country", "Belgium");
        paymentInfo.put("State", "Brussels");
       
        new InsightServicesPage().BusinessVpnProducts(paymentInfo);
        assertTrue(
                new HamburgerMenuPage().checkBusinessVpnResult(paymentInfo.get("YearNum"), paymentInfo.get("BuyNum")) && 
                new HamburgerMenuPage().checkbusinessVpnServicesSubscription(paymentInfo.get("Subscription Type"), paymentInfo.get("BuyNum"), paymentInfo.get("BuyNum")),
                "Vpn services page display incorrect.");
    }

}
