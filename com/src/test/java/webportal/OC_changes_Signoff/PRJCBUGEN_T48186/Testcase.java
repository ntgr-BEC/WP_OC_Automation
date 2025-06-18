package webportal.OC_changes_Signoff.PRJCBUGEN_T48186;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

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
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("OC_changes_Signoff") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T48186") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Edit profile in premium account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T48186") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Edit profile with enter a combination of numbers, alphabets and special;")
    public void step2() {
        Map<String, String> oldProfileInfo = new HamburgerMenuPage().getProfile();

        Map<String, String> profileInfo = new HashMap<String, String>();
        profileInfo.put("First Name", "ham11343#");
        profileInfo.put("Last Name", "ham41236");
        profileInfo.put("State", "ham3213!");
        profileInfo.put("City", "ham23213*");
        profileInfo.put("Street Address", "ham213123(");
        profileInfo.put("Apartment or Suite", "ham2423121)");
        profileInfo.put("Postal/ZIP Code", "ham55213");
        System.out.println("Edit Profile 1st time");
        new HamburgerMenuPage().editProfile(profileInfo);

        MyCommonAPIs.sleepi(10);
        
        System.out.println("Getting Edited Profile Info");
        Map<String, String> newProfileInfo = new HamburgerMenuPage().getProfile();

        boolean result = true;
        
        for (Map.Entry<String, String> entry1 : profileInfo.entrySet()) {
            String m1value = entry1.getValue() == null ? "" : entry1.getValue();
            System.out.println(m1value);
            String m2value = newProfileInfo.get(entry1.getKey()) == null ? "" : newProfileInfo.get(entry1.getKey());
            if (!m1value.equals(m2value)) {
                result = false;
                break;
            }
        }
        System.out.println(" Edit profile as old");
        new HamburgerMenuPage().editProfile(oldProfileInfo);
        MyCommonAPIs.sleepi(10);
        assertTrue(result, "Edit profile unsuccessful.");
    }

}
