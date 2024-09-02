package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.DomainNamesThroughVPN.PRJCBUGEN_T25269;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

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
    @Story("PRJCBUGEN_T25269") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify enter a domain name using WWW, HTTPS. HTTP, name.com.etc") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25269") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Go to Routers page / Check the validity of domain names")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        checkpoint = page.setPage1VPNGroupInfo("TestGroupDomain", "TestDescription", "1400", true, "www.netgear.com/");
        assertFalse(checkpoint,"checkpoint 1 : www.netgear.com/ is not ok");
        checkpoint = page.setPage1VPNGroupInfo("TestGroupDomain", "TestDescription", "1400", true, "https://www.netgear.com/");
        assertFalse(checkpoint,"checkpoint 2 : https://www.netgear.com/ is not ok");
        checkpoint = page.setPage1VPNGroupInfo("TestGroupDomain", "TestDescription", "1400", true, "http://www.netgear.com/");
        assertFalse(checkpoint,"checkpoint 3 : http://www.netgear.com/ is not ok");
        checkpoint = page.setPage1VPNGroupInfo("TestGroupDomain", "TestDescription", "1400", true, "netgear.com");
        assertTrue(checkpoint,"checkpoint 4 : netgear.com is ok");
    }
}
