package webportal.PurchaseOrderHistoryEnhancements.Premium.HardBundleCategory.PRJCBUGEN_T41556;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selectors;

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
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;


/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {
    
    
    String AP1 =new DevicesDashPage(false).GenaraterandomSerial ("4XT");
    String SW1 =new DevicesDashPage(false).GenaraterandomSerial ("5V4");
    String OB1 =new DevicesDashPage(false).GenaraterandomSerial ("536");
    String BR1 =new DevicesDashPage(false).GenaraterandomSerial ("5JR");
    String PR1 =new DevicesDashPage(false).GenaraterandomSerial ("79W");
    String MAC = "aa:bb:cc:dd:ee:ff";
    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);
    

    @Feature("PurchaseOrderHistoryEnhancements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41556") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[Premium]Test to verify in the purchase order history we get the entry of the onboarded hard bundle device under the Insight Included with Hardware") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T41556") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("OnBoardingTest"); 
        System.out.println("start to do tearDown");
        
    }
    
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
    accountInfo.put("Country", "Switzerland");

    new HamburgerMenuPage(false).createAccount(accountInfo);
}

@Step("Test Step 2: Check buy vpn services;")
public void step2() {

  new HamburgerMenuPage(false).closeLockedDialog();
    HashMap<String, String> locationInfo = new HashMap<String, String>();
    locationInfo.put("Location Name", "OnBoardingTest");
    locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
    locationInfo.put("Zip Code", "8700");
    locationInfo.put("Country", "Switzerland");
    new AccountPage().addNetwork(locationInfo);
    
}
    
    @Step("Test Step 3: Login to premium account success.")
    public void step3() {


        new MyCommonAPIs().gotoLoction("OnBoardingTest");
        
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", AP1);
        devInfo.put("Device Name", AP1);
        devInfo.put("MAC Address", MAC);
        new DevicesDashPage().addNewDevice(devInfo);
        
        Map<String, String> devInfo1 = new HashMap<String, String>();
        devInfo1.put("Serial Number", SW1);
        devInfo1.put("Device Name", SW1);
        devInfo1.put("MAC Address", MAC);
        new DevicesDashPage().addNewDevice(devInfo1);
        
        Map<String, String> devInfo2 = new HashMap<String, String>();
        devInfo2.put("Serial Number", BR1);
        devInfo2.put("Device Name", BR1);
        devInfo2.put("MAC Address", MAC);
        new DevicesDashPage().addNewDevice(devInfo2);
        
        Map<String, String> devInfo3 = new HashMap<String, String>();
        devInfo3.put("Serial Number", OB1);
        devInfo3.put("Device Name", OB1);
        devInfo3.put("MAC Address", MAC);
        new DevicesDashPage().addNewDevice(devInfo3);
        
        Map<String, String> devInfo4 = new HashMap<String, String>();
        devInfo4.put("Serial Number", PR1);
        devInfo4.put("Device Name", PR1);
        devInfo4.put("MAC Address", MAC);
        new DevicesDashPage().addNewDevice(devInfo4);
           
    }
    
    @Step("Test Step 4: Verify One Year Insight Included with Hardware premium on admin account")
    public void step4() {
        
        new  HardBundlePage().gotoOneYearInsightIncludedwithHardwarePRO();
        MyCommonAPIs.sleepi(5);
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(AP1).exists(),"We are not getting entry for Onboarded Hardbindle AP under the Insight Included with Hardware ");
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(SW1).exists(),"We are not getting entry for Onboarded Hardbindle Switch under the Insight Included with Hardware ");
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(BR1).exists(),"We are not getting entry for Onboarded Hardbindle BR under the Insight Included with Hardware ");
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(OB1).exists(),"We are not getting entry for Onboarded Hardbindle Orbi under the Insight Included with Hardware ");
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(PR1).exists(),"We are not getting entry for Onboarded Hardbindle Orbi under the Insight Included with Hardware ");
        
    }
    
}
