package webportal.InsightCreditsAccountManagementandLandingPageEnhancements.Phase_1PROEnhancements.ProOrganizationLandingPage.OrganizationsLandingHeader.PRJCBUGEN_T45925;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import util.APUtils;
import util.MyCommonAPIs;
import java.util.List;
import java.util.stream.Collectors;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    String beforeAddingOrg = "";
    String afterAddingOrg = "";
    String afterDeleteingOrg = "";

    @Feature("OrganizationsLandingHeader") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T45925") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the Organization Name Display") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T45925") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);        
           
    }
        
      @Step("Test Step 2: Verify Rename Organization")
      public void step2() {
          
          Map<String, String> organizationInfo = new HashMap<String, String>();
          organizationInfo.put("Name", "PRJCBUGEN_T45924");       
          OrganizationPage OrganizationPage = new OrganizationPage();
          OrganizationPage.addOrganization(organizationInfo);        
          MyCommonAPIs.sleepi(5);
          
          organizationInfo.put("New Name", "PRJCBUGEN_T45924A");
          OrganizationPage.editOrganization(organizationInfo); 
          MyCommonAPIs.sleepi(5);
          
          ElementsCollection orgNameElements = $$x("//span[contains(@id,'cell-orgName')]//span");
          List<String> orgNames = orgNameElements.texts();
          System.out.println("Org Names: " + orgNames);
          assertTrue(orgNames.contains("PRJCBUGEN_T45924A"), 
                     "Expected org name not found in the list!");
          new OrganizationPage().deleteOrganizationNew("PRJCBUGEN_T45924A");
          
      }
 
}
