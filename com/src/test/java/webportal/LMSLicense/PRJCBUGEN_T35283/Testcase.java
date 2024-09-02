package webportal.LMSLicense.PRJCBUGEN_T35283;

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
 * @author Anuha H
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000);
    String mailname = "abcwz" + String.valueOf(num) + "@sharklasers.com";;

    @Feature("InsightPro.ProAccountCreation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35283") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add ICP Pro LICENSE") 
    @TmsLink("PRJCBUGEN_T35283") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        String url = MyCommonAPIs.getCurrentUrl();
        if (!url.endsWith("/dashboard")) {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        }
        System.out.println("start to do tearDown");
    }

    @Step("Test Step 1: Check create pro account success.")
    public void step1() {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword); 
        }

    @Step("Test Step 2: Add ICP license ")
    public void step2() {
        int count= 1;
        for(int i=0;i<50;i++) {
        String Key = new HamburgerMenuPage(false).readLicenceKeyByTxt("Write");
        String typeofOrg = "Account";
        new HamburgerMenuPage().AddKeyAndVerifyICP(Key);
        assertTrue(new HamburgerMenuPage(true).verifyICP(Key), "Not received verify email.");
        System.out.println("No of license= " +count);  
        count++;
    }
    }

}
