package webportal.HardBundle.PRJCBUGEN_T21473;

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
    @Story("PRJCBUGEN_T21473") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if user is able to add hard bundle devices to a premium monthly account and verify that device credits are not consumed by hard bundle devices") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T21473") // It's a testcase id/link from Jira Test Case.
    
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
        accountInfo.put("Country", "Australia");
        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
   
    
    @Step("Test Step 4: Create Location ")
    public void step4() {
        new HamburgerMenuPage(false).closeLockedDialog();     
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new AccountPage().addNetwork(locationInfo);
        
    }
    
    @Step("Test Step 5: Add HB device ")
    public void step5() {
        
        new AccountPage().enterLocation(locationName);
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        Map<String, String> firststdevInfo1 = new HashMap<String, String>();
        Map<String, String> firststdevInfo2 = new HashMap<String, String>();
        Map<String, String> firststdevInfo3 = new HashMap<String, String>();
        Map<String, String> firststdevInfo4 = new HashMap<String, String>();
        Map<String, String> firststdevInfo5 = new HashMap<String, String>();
        Map<String, String> firststdevInfo6 = new HashMap<String, String>();
        Map<String, String> firststdevInfo7 = new HashMap<String, String>();
        Map<String, String> firststdevInfo8 = new HashMap<String, String>();
        Map<String, String> firststdevInfo9 = new HashMap<String, String>();
        Map<String, String> firststdevInfo10 = new HashMap<String, String>();
        Map<String, String> firststdevInfo11 = new HashMap<String, String>();
        Map<String, String> firststdevInfo12 = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("ap1"));
        firststdevInfo.put("MAC Address1", WebportalParam.getDeviceMacCSV("ap1"));
        firststdevInfo1.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("ap2"));
        firststdevInfo1.put("MAC Address1", WebportalParam.getDeviceMacCSV("ap2"));
        firststdevInfo2.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("ap3"));
        firststdevInfo2.put("MAC Address1", WebportalParam.getDeviceMacCSV("ap3"));
        firststdevInfo3.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("sw1"));
        firststdevInfo3.put("MAC Address1", WebportalParam.getDeviceMacCSV("SW1"));
        firststdevInfo4.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("sw2"));
        firststdevInfo4.put("MAC Address1", WebportalParam.getDeviceMacCSV("SW2"));
        firststdevInfo5.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("sw3"));
        firststdevInfo5.put("MAC Address1", WebportalParam.getDeviceMacCSV("SW3"));
        firststdevInfo6.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("sw4"));
        firststdevInfo6.put("MAC Address1", WebportalParam.getDeviceMacCSV("SW4"));
        firststdevInfo7.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("sw5"));
        firststdevInfo7.put("MAC Address1", WebportalParam.getDeviceMacCSV("SW5"));
        firststdevInfo8.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("sw6"));
        firststdevInfo8.put("MAC Address1", WebportalParam.getDeviceMacCSV("SW6"));
        firststdevInfo9.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("sw7"));
        firststdevInfo9.put("MAC Address1", WebportalParam.getDeviceMacCSV("SW7"));
        firststdevInfo10.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("sw8"));
        firststdevInfo10.put("MAC Address1", WebportalParam.getDeviceMacCSV("SW8"));
        firststdevInfo11.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("sw9"));
        firststdevInfo11.put("MAC Address1", WebportalParam.getDeviceMacCSV("SW9"));
        firststdevInfo12.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("br1"));
        firststdevInfo12.put("MAC Address1", WebportalParam.getDeviceMacCSV("br1"));
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        //new DevicesDashPage(false).addNewdummyDevice(firststdevInfo1);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo2);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo3);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo4);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo5);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo6);
        //new DevicesDashPage(false).addNewdummyDevice(firststdevInfo7);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo8);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo9);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo10);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo11);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo12);
        
        
        
        new  HardBundlePage().gotoOneYearInsightIncludedwithHardwarePRO();
        
        assertTrue(new HardBundlePage().CheckAllHBadded(WebportalParam.getDeviceSerialNoCSV("ap1"), WebportalParam.getDeviceSerialNoCSV("ap3"), WebportalParam.getDeviceSerialNoCSV("sw1"), WebportalParam.getDeviceSerialNoCSV("sw2"), WebportalParam.getDeviceSerialNoCSV("sw3") ,WebportalParam.getDeviceSerialNoCSV("sw4"),WebportalParam.getDeviceSerialNoCSV("sw6"),WebportalParam.getDeviceSerialNoCSV("sw7"),WebportalParam.getDeviceSerialNoCSV("sw8"), WebportalParam.getDeviceSerialNoCSV("sw9"), WebportalParam.getDeviceSerialNoCSV("br1") ),  "Activation and expiry date are not same");
        
    }
    
    
    @Step("Test Step 6: change  Basic  to Premium Monthly;")
    public void step6() {
        new AccountPage().enterLocation(locationName);
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap5serialNo);
        
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Subscription Time", "Monthly");
        paymentInfo.put("Number of Device Credits", "2");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17512");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Main Street");
        paymentInfo.put("City", "Montville");
        paymentInfo.put("Zip", "4560");
        paymentInfo.put("Country", "Australia");
        paymentInfo.put("State", "Queensland");
        paymentInfo.put("Card Number", "5193911111111112");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);
        assertTrue(new HamburgerMenuPage(false).checkMonthlySubscriptionScreen(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")) + 0)), "Amount is incorrect.");
   
    }
}
