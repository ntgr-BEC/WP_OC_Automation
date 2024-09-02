package webportal.DeviceGroup.CustomGroupNPI.PRJCBUGEN_T32362;

import static org.testng.Assert.assertTrue;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.webelements.DeviceGroupElement;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    OrganizationPage OrganizationPage = new OrganizationPage();

    @Feature("DeviceGroup CustomGroupNPI") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32362") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, validation for CG name") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32362") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }

    @Step("Test Step 2: Check add device Group button exits;")
    public void step2() {
        
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        
        boolean result1 = false;
        boolean result2 = false;
        boolean result3 = false;
        boolean result4 = false;
        boolean result5 = false;
        
        
        String CGNameerror = new DeviceGroupPage().DeviceGroupNameValidation("1", "BEC");
        if(CGNameerror.contains("Enter a name 3-24 characters long. It can include A-Z, a-z, 0-9, spaces, and special characters: !@#$%^&()_.-*")) {
            System.out.println(CGNameerror);
             result1 = true;
        }
        MyCommonAPIs.sleepi(5);
        String CGNameerror1 = new DeviceGroupPage().DeviceGroupNameValidation("12", "BEC");
        
        if(CGNameerror1.contains("Enter a name 3-24 characters long. It can include A-Z, a-z, 0-9, spaces, and special characters: !@#$%^&()_.-*")) {
            System.out.println(CGNameerror1);
             result2 = true;
        }
        MyCommonAPIs.sleepi(5);
        String CGNameerror2 = new DeviceGroupPage().DeviceGroupNameValidation("Welcome To Bec Automation, Validation Device Group Creation with more then 24 Character", "BEC");
        
        if(CGNameerror2.contains("Enter a name 3-24 characters long. It can include A-Z, a-z, 0-9, spaces, and special characters: !@#$%^&()_.-*")) {
            System.out.println(CGNameerror2);
             result3 = true;
        }
        MyCommonAPIs.sleepi(5);
        String CGNameerror3 = new DeviceGroupPage().DeviceGroupNameValidation("Welcome To Bec Automation,", "BEC");
        
        if(CGNameerror3.contains("Enter a name 3-24 characters long. It can include A-Z, a-z, 0-9, spaces, and special characters: !@#$%^&()_.-*")) {
            System.out.println(CGNameerror3);
             result4 = true;
        }
        
       String CGNameerror4 = new DeviceGroupPage().DeviceGroupNameValidation("Welcome To Bec Automation@", "BEC");
        
        if(CGNameerror4.contains("Your configuration has been applied. It may take a few moments to display.")) {
            System.out.println(CGNameerror4);
             result5 = true;
        }
        
        assertTrue( result1 == true && result2 == true && result3 == true && result4 == true && result5 == true , "Validation did not went well");
    }

}
