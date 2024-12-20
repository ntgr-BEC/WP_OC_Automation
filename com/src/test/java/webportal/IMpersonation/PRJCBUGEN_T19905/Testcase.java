package webportal.IMpersonation.PRJCBUGEN_T19905;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("IMpersonation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T19905") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify if a L2 support user can login to a customer account") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19905") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword(WebportalParam.l2SupportUser, WebportalParam.loginPassword);
    }

    @Step("Test Step 2: Check login account;")
    public void step2() {
        
        
        new HamburgerMenuPage(false).clickdown.click();
        new HamburgerMenuPage(false).sleepi(3);
        new HamburgerMenuPage(false).search.click();
        new HamburgerMenuPage(false).sleepi(3);
        new HamburgerMenuPage(false).sendemail.sendKeys(WebportalParam.loginName);
        new HamburgerMenuPage(false).sleepi(3);
        new HamburgerMenuPage(false).searchbutton.click();
        new HamburgerMenuPage(false).sleepi(10);
       
        if ($x(String.format(new HamburgerMenuPage(false).supportRequestTableEmail, WebportalParam.loginName)).exists()) {  
              if (new HamburgerMenuPage(false).gotoMyAccount.exists()) {
             
                 new HamburgerMenuPage(false).logInAsThisUser(WebportalParam.loginName);

                 assertTrue(new HamburgerMenuPage().checkAccountEmail(WebportalParam.loginName), "Login account failed.");
        }
        }else {
     
        UserManage userManage = new UserManage();
        userManage.logout();
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        new HamburgerMenuPage().grantAccessToSupport();

        userManage.logout();
        webportalLoginPage.loginByUserPassword(WebportalParam.l2SupportUser, WebportalParam.loginPassword);
       
        new HamburgerMenuPage(false).logInAsThisUser(WebportalParam.loginName);

        assertTrue(new HamburgerMenuPage().checkAccountEmail(WebportalParam.loginName), "Login account failed.");
        
        userManage.logout();
        }
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.l2SupportUser, WebportalParam.loginPassword);
        if ($x(String.format(new HamburgerMenuPage(false).supportRequestTableEmail, WebportalParam.loginName)).exists()) {
            new HamburgerMenuPage(false).endAccessSupportUser(WebportalParam.loginName);
    }
    }
    }




