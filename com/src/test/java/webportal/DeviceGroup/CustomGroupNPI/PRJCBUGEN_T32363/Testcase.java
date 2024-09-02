package webportal.DeviceGroup.CustomGroupNPI.PRJCBUGEN_T32363;

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
    @Story("PRJCBUGEN_T32363") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, validation for CG Description") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32363") // It's a testcase id/link from Jira Test Case.

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
        boolean result1 = false;
        boolean result2 = false;
        boolean result3 = false;
        
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        
        String CGNameerror = new DeviceGroupPage().DeviceGroupNameValidation("BEC", "");
        if(CGNameerror.contains("Invalid description. Description can only contain alphanumeric characters, spaces, hyphens, commas, and period marks.")) {
            System.out.println(CGNameerror);
             result1 = true;
        }
        MyCommonAPIs.sleepi(5);
        String CGNameerror1 = new DeviceGroupPage().DeviceGroupNameValidation("BEC", "Now, in my recently published paper there is a new method proposed called Text Guide. Text Guide is a text selection method that allows for improved performance when compared to naive or semi-naive truncation methods. As a text selection method, Text Guide doesn’t interfere with the language model, so it can be used to improve performance of models with ‘standard’ limit of tokens (512 for transformer models) or ‘extended’ limit (4096 as for instance for the Longformer model). Summary: Text Guide is a low-computational-cost method that improves performance over naive and semi-naive truncation methods. If text instances are exceeding the limit of models");
        
        if(CGNameerror1.contains("Custom Group description can have a max. of 512 characters.")) {
            System.out.println(CGNameerror1);
             result2 = true;
        }
        MyCommonAPIs.sleepi(5);
        String CGNameerror2 = new DeviceGroupPage().DeviceGroupNameValidation("BEC", "Automation");
        
        if(CGNameerror2.contains("Your configuration has been applied. It may take a few moments to display.")) {
            System.out.println(CGNameerror2);
             result3 = true;
        }
        
        assertTrue( result1 == true && result2 == true && result3 == true  , "Validation did not went well");
    }

}
