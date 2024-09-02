package webportal.DeviceGroup.FeaturesunderCGs.PRJCBUGEN_T32348;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

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
    String sCheck = "[alt=\"User Image\"]";

    @Feature("DeviceGroup CustomGroupNPI") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32348") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, Read-only manager can view CGs and cannot create or edit CGs") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32348") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
        System.out.println("try to do logout");
        $(sCheck).click();
        $(Selectors.byCssSelector(".open ul li:last-child a")).click();
        System.out.println("user is logout");
        MyCommonAPIs.waitReady();
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        
        new DeviceGroupPage().deleteDG("Automation1");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }

    @Step("Test Step 2: Check CG name and description shown;")
    public void step2() {
        
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().CreateDGGroup("Automation1", "Check Grop creation");    
    }


    @Step("Test Step 3: Login to read manager;")
    public void step3() {
    
   
    System.out.println("try to do logout");
    $(sCheck).click();
    $(Selectors.byCssSelector(".open ul li:last-child a")).click();
    System.out.println("user is logout");
    MyCommonAPIs.waitReady();
    
    
    WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
    webportalLoginPage.loginByUserPassword(WebportalParam.readManagerName, WebportalParam.readManagerPassword);
    
    
    OrganizationPage.openOrg(WebportalParam.Organizations);
    assertTrue(!new DeviceGroupPage().Locedit(WebportalParam.location1).exists(),"edit location exits");
    
    }
    
    
    
}