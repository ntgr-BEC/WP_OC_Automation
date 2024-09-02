package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.DomainNamesThroughVPN.PRJCBUGEN_T25268;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.DomainNamesThroughVPN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25268") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Help the text provides some valid information or not.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25268") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Routers page / User can enable domain names through VPN and enter the domain name")
    public void step2() {
        boolean checkpoint;
        //SelenideElement helptext = $x("//*p[contains(., 'This defines the list')]");
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        // set enabled, help text exists
        page.SetCheckboxSelected(page.DomainNamesthroughVPN, true);
        checkpoint = page.DomainNamesHelpText.exists();
        assertTrue(checkpoint,"checkpoint 1 : help text exists when domain names through VPN set enabled");
        // set disabled, help text exists
        page.SetCheckboxSelected(page.DomainNamesthroughVPN, false);
        checkpoint = page.DomainNamesHelpText.exists();
        assertTrue(checkpoint,"checkpoint 2 : help text exists when domain names through VPN set disabled");
    }
}
