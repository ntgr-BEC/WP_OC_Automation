package webportal.PaymentAndSubscription.PRJCBUGEN_T16761;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(1000000);
    String mailname = "apwptest" + String.valueOf(num);

    @Feature("PaymentAndSubscription") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16761") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the user should be able to change from an unsupported country to a supported country.") // It's a testcase
                                                                                                                         // title from Jira
                                                                                                                         // Test
    // Case.
    @TmsLink("PRJCBUGEN-T16761") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T16761");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "India");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Check change account country to Australia;")
    public void step2() {
        Map<String, String> profileInfo = new HashMap<String, String>();
        profileInfo.put("Choose Country", "Australia");

        new HamburgerMenuPage().editProfile(profileInfo);

        MyCommonAPIs.sleepi(10);

        Map<String, String> newProfileInfo = new HamburgerMenuPage().getProfile();

        boolean result = true;

        for (Map.Entry<String, String> entry1 : profileInfo.entrySet()) {
            String m1value = entry1.getValue() == null ? "" : entry1.getValue();
            String m2value = newProfileInfo.get(entry1.getKey()) == null ? "" : newProfileInfo.get(entry1.getKey());
            if (!m1value.equals(m2value)) {
                result = false;
                break;
            }
        }

        assertTrue(result, "Edit profile unsuccess.");
    }

}
