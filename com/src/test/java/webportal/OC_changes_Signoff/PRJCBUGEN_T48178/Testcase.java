package webportal.OC_changes_Signoff.PRJCBUGEN_T48178;

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
import webportal.param.WebportalParam;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String              urlpriqa        = "https://accounts2-stg.netgear.com/login?redirectUrl=https:%2F%2Fbilling-stg.netgear.com%2Finsight-pro&GotoDashboard=https://pri-qa.insight.netgear.com&category_id=19&clientId=les2m0e41u7nr1asu03uk2iot";
    String              urlmaintqa      = "https://accounts-qa.netgear.com/login?redirectUrl=https:%2F%2Fbilling-qa.netgear.com%2Finsight-pro&GotoDashboard=https://maint-qa.insight.netgear.com&category_id=19&clientId=5bk75uhg5v61mcj48368s2qc9f";
    String              urlmaintbeta    = "https://accounts2-stg.netgear.com/login?redirectUrl=https:%2F%2Fbilling-stg.netgear.com%2Finsight-pro&GotoDashboard=https://maint-beta.insight.netgear.com&category_id=19&clientId=les2m0e41u7nr1asu03uk2iot";


    @Feature("OC_changes_Signoff") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T48178") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Direct purchase for premium account - account creation") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T48178") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Click login and subscribe button and create new account;")
    public void step1() {
        
        if (WebportalParam.serverUrl.contains("pri-qa")) {
            new HamburgerMenuPage(false).launchdirectpurchaseQAServerLink(urlpriqa);
        } else if (WebportalParam.serverUrl.contains("maint-qa")) {
            new HamburgerMenuPage(false).launchdirectpurchaseQAServerLink(urlmaintqa);
        } else if (WebportalParam.serverUrl.contains("maint-beta")) {
            new HamburgerMenuPage(false).launchdirectpurchaseQAServerLink(urlmaintbeta);
        } else {
            new HamburgerMenuPage(false).launchInsightSubscriptionServicesPage();
        }
        new HamburgerMenuPage(false).clickLoginAndSubscribe("premium");

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T24091");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        assertTrue(new HamburgerMenuPage(false).createAccount1(accountInfo),"Through marketing page not able create new premium account");
    }

}
