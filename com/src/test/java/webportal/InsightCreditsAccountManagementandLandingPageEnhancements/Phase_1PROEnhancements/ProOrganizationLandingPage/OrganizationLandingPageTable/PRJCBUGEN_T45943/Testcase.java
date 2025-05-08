package webportal.InsightCreditsAccountManagementandLandingPageEnhancements.Phase_1PROEnhancements.ProOrganizationLandingPage.OrganizationLandingPageTable.PRJCBUGEN_T45943;

import static com.codeborne.selenide.Selenide.$$x;
import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

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
import static com.codeborne.selenide.Selenide.*;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("OrganizationLandingPageTable") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T45943") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Organization Name Display.") // It's // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T45943") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }

    @Step("Test Step 2: Verify On Organization name is correct")
    public void step2() {

        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", "OrganizationLandingPageTable_PRJCBUGEN_T45943");
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        MyCommonAPIs.sleepi(5);

        ElementsCollection orgNameElements = $$x("//span[contains(@id,'cell-orgName')]//span");
        List<String> orgNames = orgNameElements.texts();
        System.out.println("Org Names: " + orgNames);
        assertTrue(orgNames.contains("OrganizationLandingPageTable_PRJCBUGEN_T45943"), "Expected org name not found in the list!");

        // Access the second element from the collection
        SelenideElement orgNameElement = orgNameElements.get(1); // Getting the second element (index starts from 0)

        // Check if the element is displayed
        assertTrue(orgNameElement.isDisplayed(), "Org name is not displayed");

        // Check for truncation using JS: scrollWidth > clientWidth indicates overflow
        boolean isTruncated = executeJavaScript("return arguments[0].scrollWidth > arguments[0].clientWidth;", orgNameElement);

        // Correct order: assertFalse(boolean condition, String message)
        assertFalse(isTruncated, "Organization name is visually truncated!");

        // Optionally, fetch style attributes and check for ellipsis or overflow
        String textOverflow = executeJavaScript("return window.getComputedStyle(arguments[0]).getPropertyValue('text-overflow');", orgNameElement);
        String overflow = executeJavaScript("return window.getComputedStyle(arguments[0]).getPropertyValue('overflow');", orgNameElement);
        String whiteSpace = executeJavaScript("return window.getComputedStyle(arguments[0]).getPropertyValue('white-space');", orgNameElement);

        // Log the styles for debug purposes
        System.out.println("Text Overflow: " + textOverflow);
        System.out.println("Overflow: " + overflow);
        System.out.println("White Space: " + whiteSpace);

        // Correct order: assertNotEquals(actual, expected, message)
        assertNotEquals(textOverflow, "ellipsis", "Text overflow should not be 'ellipsis'. The name is truncated!");
        assertNotEquals(overflow, "hidden", "Overflow should not be hidden, the name is clipped!");
        //assertEquals(whiteSpace, "nowrap", "White-space should be 'nowrap' to prevent text wrapping!");

        // Deleting the organization after validation
        OrganizationPage.deleteOrganizationNew("OrganizationLandingPageTable_PRJCBUGEN_T45943");

    }

}
