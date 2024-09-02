package webportal.MultiAdmin.PRJCBUGEN_T30363;

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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.ManagerPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num) + "@sharklasers.com";
    String mailname1 = "apwptest1" + String.valueOf(num) + "@sharklasers.com";
    String Name     = "PRJCBUGEN_T30363";

    @Feature("MultiAdmin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T30363") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether newly invited secondary admin can invite Managers to the account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T30363") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        String url = MyCommonAPIs.getCurrentUrl();
        if (!url.endsWith("#/organization/dashboard") && !url.contains("managers")) {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.SecondadminName, WebportalParam.SecondadminPassword);
        }

        new ManagerPage().deleteManager(mailname);
        new ManagerPage().deleteManager(mailname1);
        System.out.println("start to do tearDown");
        
        
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.SecondadminName,WebportalParam.SecondadminPassword);
         new HamburgerMenuPage();
    }


    @Step("Test Step 2: Invite manager and check sign up url in invite email;")
    public void step2() {
        mailname         = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num) + "@sharklasers.com";
        Map<String, String> managerInfo = new HashMap<String, String>();
        managerInfo.put("Name", "test1232132");
        managerInfo.put("Email Address", mailname);
        managerInfo.put("Organization Name", WebportalParam.Organizations);
        managerInfo.put("Access Policy", "Read");

        new ManagerPage().addManager(managerInfo);

        Map<String, String> managerInfo1 = new HashMap<String, String>();
        managerInfo1.put("Name", "test1232132");
        managerInfo1.put("Email Address", mailname1);
        managerInfo1.put("Organization Name", WebportalParam.Organizations);
        managerInfo1.put("Access Policy", "Read/Write");
        
        new ManagerPage().addManager(managerInfo1);    

    }

}
