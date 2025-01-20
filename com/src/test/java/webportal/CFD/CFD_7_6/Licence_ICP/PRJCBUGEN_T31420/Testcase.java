package webportal.CFD.CFD_7_6.Licence_ICP.PRJCBUGEN_T31420;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author RaviShankar S
 */

/**
 * 
 * Precondition-
 * AP5-Dummy(Hardbundle)
 * 1ICP-licenceKey(Check-years)
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000);
    String mailname = "test" + String.valueOf(num) + "@sharklasers.com";;

    @Feature("AddingLicenceNewProIcp") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31420") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add ICP Pro LICENSE") 
    @TmsLink("PRJCBUGEN_T31420") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new DevicesDashPage().deleteDeviceNo(WebportalParam.ap1serialNo);
    }

    @Step("Test Step 1: Check create pro account success.")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

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
            businessInfo.put("City", "NewYork");
            businessInfo.put("State", "test");
            businessInfo.put("Zip Code", "12345");
            businessInfo.put("Country", "United States of America");
            businessInfo.put("Business Phone Number", "1234567890");
            new HamburgerMenuPage(false).PremiumTrailAndFinishSignin(businessInfo);

            assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess.");        
        }
    }
    @Step("Test Step2:Create Organization ,Location and add a dummy device")
    public void step2()
    {
        Map<String,String> oragnizationInfo = new HashMap<>();
        oragnizationInfo.put("Name", "Netgear");
           
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(oragnizationInfo);
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "office1");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage(false).addNetworkforLocationCheck(locationInfo);
    }
        
       @Step("Test Step3:Add dummy device")
       public void step3()
       {
        new AccountPage().enterLocation("office1");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
       
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        
        System.out.println(firststdevInfo);
 
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);

       }

    @Step("Test Step 4: Add ICP license ")
    public void step4() {
       
        String Key = new HamburgerMenuPage(false).readLicenceKeyByTxt("Write");
        String typeofOrg = "Account";
        new HamburgerMenuPage().AddKeyAndVerifyICP(Key);
        assertTrue(new HamburgerMenuPage(true).verifyICP(Key), "Not received verify email.");
    }
    

}
