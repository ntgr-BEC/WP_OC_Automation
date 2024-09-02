package webportal.InsightProOrganizationPolicyImprovements.PRJCBUGEN_T35474;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceScreenNavigationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.ManagerPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {
    Map<String, String> managerInfo = new HashMap<String, String>();
    Random r        = new Random();
    int    num      = r.nextInt(10000);
    String mailname = "apwptest" + String.valueOf(num) + "@sharklasers.com";
    String mailname1  = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num) + "@sharklasers.com";
    String organizationName = "PRJCBUGEN_T35474";

    @Feature("InsightProOrganizationPolicyImprovements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35469") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user create an organisation with existing owner then the invitation will be sent for the Email.") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN-T354569") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create a new account ;")
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

    @Step("Test Step 2:Add a Organization and a manager ;")
    public void step2() {
        
        OrganizationPage page = new OrganizationPage();
        page.gotoPage();

        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        
        page.addOrganization(organizationInfo);  
    
            managerInfo.put("Name", "test14409");
            managerInfo.put("Email Address", mailname1);
            managerInfo.put("Organization Name",organizationName);
            managerInfo.put("Access Policy", "Read");

            new ManagerPage().addManager(managerInfo);               
        }
    
    @Step("Test Step 3:Add another organisation with same name as existing owner;")
    public void step3() {
        
         new OrganizationPage().addOrganization2(managerInfo);
    }
      
    @Step("Test Step 4:Check whether the mail invite link is send;")
    public void step4() {
        
        assertTrue(new HamburgerMenuPage(false).checkEmailMessage(mailname1), "Created Org with Existing owner then the invitation is not sent for the Email");
        
    }
    
}
