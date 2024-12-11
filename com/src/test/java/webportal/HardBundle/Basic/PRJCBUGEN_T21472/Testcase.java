package webportal.HardBundle.Basic.PRJCBUGEN_T21472;

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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.PostManPage;
import webportal.weboperation.WebportalLoginPage;


/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {
    
    String                  locationName     = "OnBoardingTest";
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    

    @Feature("Hardbundle") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21472") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if a user is able to add hard bundle devices to a basic paid account without buying any extra credit") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T21472") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void tearUp() {
       
       new PostManPage().Deregister(WebportalParam.ap5serialNo);
        
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().deleteOneLocation(locationName);
    }
    
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T15589");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
    
    @Step("Test Step 3: Create Location ")
    public void step3() {
        
        new HardBundlePage().GoTocreateLocation();
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        
        new AccountPage().enterLocation(locationName);
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap5serialNo);
        
    }
    
    @Step("Test Step 2: Buy devices number by basic plan;")
    public void step2() {
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Number of Device Credits", "1");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T15589");
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
        new HamburgerMenuPage().buyDeviceCredits(paymentInfo);
        assertTrue(new HamburgerMenuPage(false)
                .checkSubscriptionScreen(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")) + 1)), "Amount is incorrect.");
    }
        
    @Step("Test Step 4: Add HB device ")
    public void step4() {
        
        new AccountPage().enterLocation(locationName);
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("ap1"));
        //firststdevInfo.put("Serial Number2", WebportalParam.getDeviceSerialNoCSV("ap2"));
        firststdevInfo.put("Serial Number3", WebportalParam.getDeviceSerialNoCSV("ap2"));
        firststdevInfo.put("Serial Number4", WebportalParam.getDeviceSerialNoCSV("sw1"));
        firststdevInfo.put("Serial Number5", WebportalParam.getDeviceSerialNoCSV("sw2"));
        firststdevInfo.put("Serial Number6", WebportalParam.getDeviceSerialNoCSV("sw3"));
        firststdevInfo.put("Serial Number7", WebportalParam.getDeviceSerialNoCSV("sw4"));
        //firststdevInfo.put("Serial Number8", WebportalParam.getDeviceSerialNoCSV("sw5"));
        firststdevInfo.put("Serial Number9", WebportalParam.getDeviceSerialNoCSV("sw6"));
        firststdevInfo.put("Serial Number10", WebportalParam.getDeviceSerialNoCSV("sw7"));
        firststdevInfo.put("Serial Number11", WebportalParam.getDeviceSerialNoCSV("sw8"));
        firststdevInfo.put("Serial Number12", WebportalParam.getDeviceSerialNoCSV("sw9"));
        firststdevInfo.put("Serial Number13", WebportalParam.getDeviceSerialNoCSV("br1"));
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        new  HardBundlePage().gotoOneYearInsightIncludedwithHardwarePRO();
        
        assertTrue(new HardBundlePage().CheckAllHBadded(WebportalParam.getDeviceSerialNoCSV("ap1"), WebportalParam.getDeviceSerialNoCSV("ap3"), WebportalParam.getDeviceSerialNoCSV("sw1"), WebportalParam.getDeviceSerialNoCSV("sw2"), WebportalParam.getDeviceSerialNoCSV("sw3") ,WebportalParam.getDeviceSerialNoCSV("sw4"),WebportalParam.getDeviceSerialNoCSV("sw6"),WebportalParam.getDeviceSerialNoCSV("sw7"),WebportalParam.getDeviceSerialNoCSV("sw8"), WebportalParam.getDeviceSerialNoCSV("sw9"), WebportalParam.getDeviceSerialNoCSV("br1") ),  "Activation and expiry date are not same");
        
    }
}
