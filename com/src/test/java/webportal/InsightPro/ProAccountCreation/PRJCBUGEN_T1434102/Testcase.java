package webportal.InsightPro.ProAccountCreation.PRJCBUGEN_T1434102;

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
import util.MyCommonAPIs;
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

    Random r        = new Random();
    int    num      = r.nextInt(10000);
    String mailname = "";

    @Feature("CreateProAccount") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T1434102") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Create pro account.") // It's
                                                                                                                                                      // a
                                                                                                                                                      // testcase
                                                                                                                                                      // title
                                                                                                                                                      // from
                                                                                                                                                      // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN-T1434102") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Check create pro account success.")
    public void step1() {
        int accountNum = 2;
        for (int i = 1; i <= accountNum; i++) {
            if (new HamburgerMenuPage(false).checkCreateProAccountPage("checkNext:" + mailname + "@mailcatch.com")) {
                System.out.println("Pro Account Created Successfully; now count is "+i);
                Map<String, String> proAccountInfo = new HashMap<String, String>();
                proAccountInfo.put("Confirm Email", mailname + "@mailcatch.com");
                proAccountInfo.put("Password", WebportalParam.adminPassword);
                proAccountInfo.put("Confirm Password", WebportalParam.adminPassword);
                proAccountInfo.put("Country", "United States of America");
                proAccountInfo.put("Phone Number", "1234567890");
                new HamburgerMenuPage(false).CreateProAccount(proAccountInfo);

                Map<String, String> businessInfo = new HashMap<String, String>();
                businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
                businessInfo.put("Business Name", "Netgear");
                businessInfo.put("Primary Address of Business", "test 1st");
                businessInfo.put("City", "Ontario");
                businessInfo.put("State", "test");
                businessInfo.put("Zip Code", "L6A");
                businessInfo.put("Country", "Canada");
                businessInfo.put("Business Phone Number", "1234567890");
                new HamburgerMenuPage(false).inputLicenceAndFinishSignin(businessInfo);
                new HamburgerMenuPage(false).closeLockedDialog();
                assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess.");
                if (i != accountNum) {
                    UserManage userManage = new UserManage();
                    userManage.logout();
                }
                
            }
        }
    }

}
