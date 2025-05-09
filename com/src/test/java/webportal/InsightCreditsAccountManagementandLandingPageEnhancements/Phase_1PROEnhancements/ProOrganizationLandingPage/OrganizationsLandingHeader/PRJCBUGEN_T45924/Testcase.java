package webportal.InsightCreditsAccountManagementandLandingPageEnhancements.Phase_1PROEnhancements.ProOrganizationLandingPage.OrganizationsLandingHeader.PRJCBUGEN_T45924;

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
    @Story("PRJCBUGEN_T45924") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the Accuracy of the Number of Organizations.") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T45924") // It's a testcase id/link from Jira Test Case.

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
        
      @Step("Test Step 2: Verify On Organization dashboard organizations count")
      public void step2() {
          
          new OrganizationPage(false).getOrgTooltipTitle("Organizations").shouldBe(Condition.visible);
          System.out.println("Step1: " + new OrganizationPage(false).getOrgTooltipTitle("Organizations").getText());
          new OrganizationPage(false).getOrgTooltipCountHeader("Organizations").shouldBe(Condition.visible);
          System.out.println("Step2: " + new OrganizationPage(false).getOrgTooltipCountHeader("Organizations").getText());
          beforeAddingOrg = new OrganizationPage(false).getOrgTooltipCountHeader("Organizations").getText();
          
          Map<String, String> organizationInfo = new HashMap<String, String>();
          organizationInfo.put("Name", "PRJCBUGEN_T45924");
          OrganizationPage OrganizationPage = new OrganizationPage();
          OrganizationPage.addOrganization(organizationInfo);
          
          new OrganizationPage(false).getOrgTooltipCountHeader("Organizations").shouldBe(Condition.visible);
          System.out.println("Step3: " + new OrganizationPage(false).getOrgTooltipCountHeader("Organizations").getText());
          afterAddingOrg = new OrganizationPage(false).getOrgTooltipCountHeader("Organizations").getText();
          
          int num1 = Integer.parseInt(beforeAddingOrg);
          int num2 = Integer.parseInt(afterAddingOrg);

          int result = num2 - num1;

          assertTrue(((result==1) && (num2==2)), "After adding one more organization count not changed");
          
          new OrganizationPage().deleteOrganizationNew("PRJCBUGEN_T45924");
          MyCommonAPIs.sleepi(5);
          MyCommonAPIs commonAPIs = new MyCommonAPIs();
          commonAPIs.refresh();
          MyCommonAPIs.sleepi(5);
          new OrganizationPage(false).getOrgTooltipCountHeader("Organizations").shouldBe(Condition.visible);
          System.out.println("Step4: " + new OrganizationPage(false).getOrgTooltipCountHeader("Organizations").getText());
          afterDeleteingOrg = new OrganizationPage(false).getOrgTooltipCountHeader("Organizations").getText();
          System.out.println("afterDeleteingOrg: "+afterDeleteingOrg);
          int num3 = Integer.parseInt(afterDeleteingOrg);
          
          assertTrue((num1==num3), "After deleting one more organization count not changed");
          
      }
 
}
