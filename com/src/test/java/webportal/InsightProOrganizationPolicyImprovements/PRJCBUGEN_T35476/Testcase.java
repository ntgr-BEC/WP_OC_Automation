package webportal.InsightProOrganizationPolicyImprovements.PRJCBUGEN_T35476;

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
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {
    String organizationName = "PRJCBUGEN_T35476";
    Random r        = new Random();
    int    num      = r.nextInt(10000);
    String mailname = "abcwz" + String.valueOf(num) + "@sharklasers.com";;

    @Feature("InsightProOrganizationPolicyImprovements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35476") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user edit or delete organisation then the email notification will be sent.") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35476") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to the pro account ;")
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
    
    @Step("Test Step 2:Add a organization and  make changes in the policy and Apply to all organizations;")
    public void step2() {
        OrganizationPage page = new OrganizationPage();
        page.gotoPage();

        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        
        page.addOrganization(organizationInfo);           
        
    }
    
    @Step("Test Step 3:Delete an organization and verify whether the email is sent to admin account ;")
    public void step3() {

        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName);      
    }
    
    @Step("Test Step 4:Check whether the mail is recieved for deleting an organization;")
    public void step4() {
        
        new HamburgerMenuPage(false).checkEmailMessage(mailname); 
    }
}
