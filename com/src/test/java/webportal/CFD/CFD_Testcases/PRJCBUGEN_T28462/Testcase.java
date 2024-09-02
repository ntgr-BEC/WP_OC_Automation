package webportal.CFD.CFD_Testcases.PRJCBUGEN_T28462;

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
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 * @author Anusha H
 */

public class Testcase extends TestCaseBase {
    
    String organizationName = "Organization";
    int totalOrganizationrequired = 10;
    String warning   = "Cannot add devices from CSV file. Use CSV template column headings, and save file in .CVS format.";
    boolean micResult = false;
    
    @Feature("CFD.CFD_Testcases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28462") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("100 organization in one account;)") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T28462") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    
    public void test() throws Exception {
        runTest(this);
    }

 
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        for(int i = 1; i <=totalOrganizationrequired; i++){
        new OrganizationPage().deleteOrganizationNew(organizationName+i);
        }
        UserManage userManage = new UserManage();
        userManage.logout();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
    }
    @Step("Test Step 2: Create a organization and location")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        
        List<String> Loclist=new ArrayList<String>();
        int created = 0;
        for(int i = 1; i <=totalOrganizationrequired; i++){
        Loclist.add(organizationName+i);
        organizationInfo.put("Name", organizationName+i);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);
        created = created+1;
        System.out.println("Organization"+i+ "---------->"+created+" Created");
        MyCommonAPIs.sleepi(5);
        }        
        Selenide.sleep(5000);
        Selenide.refresh();
        Selenide.sleep(5000);
        Selenide.refresh();
        Selenide.sleep(5000);
        assertTrue(new AccountPage(false).VerifyCreatedOrganizatons(Loclist, totalOrganizationrequired), "Missing Networks/Locations Noted");
        MyCommonAPIs.sleepi(5);
    }
    }
   