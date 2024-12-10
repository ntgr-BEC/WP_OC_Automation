package webportal.PRJCBUGEN35477_CreditAllocation.PRJCBUGEN_T28362;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import java.util.Random;

public class TestCase extends TestCaseBase {
    String  organizationName = "Multi_Org_";
    String  locationName     = "office";
    String  warning          = "Cannot add devices from CSV file. Use CSV template column headings, and save file in .CVS format.";
    boolean micResult        = false;

    @Feature("PRJCBUGEN35477_CreditAllocation")                                                                                                                            // Test Case.
    @Story("PRJCBUGEN_T50005") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Create Multiple locatOrganizationsion Pro") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T50005") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority

    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        int count = 0;
        int TotalOrgCount = 70;
        List<String> Orglist = new ArrayList<String>();
        for (int i = 1; i <= TotalOrgCount; i++) {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            new OrganizationPage().deleteOrganizationNew(organizationName + i);
            count++;
            System.out.println("Deleteed Organization : " + i);
        }
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Create Multiple organizations")
    public void step2() {
        int count = 0;
        int TotalOrgCount = 70;
        List<String> Orglist = new ArrayList<String>();
        for (int i = 1; i <= TotalOrgCount; i++) {
            System.out.println("2st");
            Map<String, String> organizationInfo = new HashMap<String, String>();
            organizationInfo.put("Name", organizationName + i);
            System.out.println("3st");
            OrganizationPage OrganizationPage = new OrganizationPage();
            System.out.println("4st");
            System.out.println(organizationInfo);
            new OrganizationPage().addOrganization(organizationInfo);
            Orglist.add(organizationName + i);
            count = count + 1;
            System.out.println("Organization ------> " + count + " Created");
            MyCommonAPIs.sleepi(5);
            Selenide.refresh();
            System.out.println("1st");
            MyCommonAPIs.sleepi(5);

        }
        Selenide.sleep(5000);
        Selenide.refresh();
        System.out.println("2st");
        Selenide.sleep(5000);
        Selenide.refresh();
        System.out.println("3st");
        Selenide.sleep(5000);
        System.out.println("Count Org created = " + count);
        assertTrue(count == TotalOrgCount, "Organizations count not verified becaused organizations not created successfully");
    }

    @Step("Test Step 3: Create Multiple organizations")
    public void step3() {
        new HamburgerMenuPage().gotoCreditsAllocationPage();
        assertTrue(new HamburgerMenuPage(false).verifyOrganizationsVisibility(), "All organizations are not shown on credit allocation page");
    }

}
