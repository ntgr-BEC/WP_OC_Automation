package webportal.IMPro25CreditTrail.PRJCBUGEN_T28439;

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
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000);
    String mailname = "abcwz" + String.valueOf(num) + "@sharklasers.com";
    String organizationName = "PRJCBUGEN_T12112";
    String locationName     = "BulkOnboarding";
    int    Serial      = r.nextInt(1000000000);
    String SerialNo = "4MC3" + String.valueOf(Serial);
    
    
    @Feature("IMPro25CreditTrail") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28439") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether Insight Pro trial license Key get listed in Purchase Order History and Subscription page") 
    @TmsLink("PRJCBUGEN-T28439") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Check create pro account success.")
    public void step1() {
        if (new HamburgerMenuPage(false).checkCreateProAccountPage("checkNext:" + mailname)) {
            Map<String, String> proAccountInfo = new HashMap<String, String>();
            proAccountInfo.put("Confirm Email", mailname);
            proAccountInfo.put("Password", WebportalParam.adminPassword);
            proAccountInfo.put("Confirm Password", WebportalParam.adminPassword);
            proAccountInfo.put("Country", "United States of America");
            proAccountInfo.put("Phone Number", "1234567890");
            new HamburgerMenuPage(false).CreateProAccount(proAccountInfo);

            Map<String, String> businessInfo = new HashMap<String, String>();
//            businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
            businessInfo.put("Business Name", "Netgear");
            businessInfo.put("Primary Address of Business", "test 1st");
            businessInfo.put("City", "NewYork");
            businessInfo.put("State", "test");
            businessInfo.put("Zip Code", "12345");
            businessInfo.put("Country", "United States of America");
            businessInfo.put("Business Phone Number", "1234567890");
            new HamburgerMenuPage(false).PremiumTrailAndFinishSignin(businessInfo);

            
        }

    }

    @Step("Test Step 2: Check trail ")
    public void step2() {
        assertTrue(new HamburgerMenuPage().PurchaseHistoryProTrail(), "Not received verify email.");
        assertTrue(new HamburgerMenuPage(true).CheckFreeTrailPro(), "Not received verify email.");
    }
    
    @Step("Test Step 3: Create organization ")
    public void step3() {
        
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);

        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.sleepi(30);   
       
    }
    
    @Step("Test Step 4: allocate credit and add device ")
    public void step4() {
        DeviceBulkOnboardingPage DeviceBulkOnboardingPage = new DeviceBulkOnboardingPage();
        new HamburgerMenuPage().configCreditAllocation(organizationName, 25, 0, 0);
        String filePath = DeviceBulkOnboardingPage.GetcurrentPathIMPro();
        filePath = filePath + "PRJCBUGEN_T28439\\Bulkonbord.csv";
        System.out.println(filePath);
        
        new DeviceBulkOnboardingPage().ImportCvsFileWirelessscreen(filePath);
        
    }
    
    @Step("Test Step 5: Add device with credit")
    public void step5() {
       new AccountPage().enterLocation(locationName);
       String Mac = new WirelessQuickViewPage().getRandomMacAddress();
       Map<String, String>     devInfo          = new HashMap<String, String>();
       devInfo.put("Serial Number", SerialNo);
       devInfo.put("Device Name", SerialNo);
       devInfo.put("MAC Address", Mac);
       new AccountPage(false).enterLocation(locationName);
       assertTrue(new DevicesDashPage().checkDevicesCredits(devInfo).contains("you need to allocate more device credits"), "Credits limit error.");
   
        
        
    }


}
