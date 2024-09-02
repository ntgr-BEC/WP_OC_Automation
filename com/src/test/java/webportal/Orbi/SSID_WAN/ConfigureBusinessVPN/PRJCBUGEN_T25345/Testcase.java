package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.PRJCBUGEN_T25345;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
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
    @Feature("SSID_WAN.ConfigureBusinessVPN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25345") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Check how many domain name user are allowed to add in Configuration \"Domain Names\"") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25345") // It's a testcase id/link from Jira Test Case.
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
        
        // check length : 63 -> ok, 64 -> ok, 65 -> fail
        String basename = "11111111112222222222333333333344444444445555555555666666666";
        String testname = basename + ".com";
    
        checkpoint = page.setPage1VPNGroupInfo("TestGroupDomain", "TestDescription", "1400", true, testname);
        assertTrue(checkpoint,"checkpoint 1 : domain name with 63 characters is ok");
        page.clickBack();
        testname = basename + "6.com";
        checkpoint = page.setPage1VPNGroupInfo("TestGroupDomain", "TestDescription", "1400", true, testname);
        assertTrue(checkpoint,"checkpoint 2 : domain name with 64 characters is ok");
        page.clickBack();
        testname = basename + "66.com";
        checkpoint = page.setPage1VPNGroupInfo("TestGroupDomain", "TestDescription", "1400", true, testname);
        assertFalse(checkpoint,"checkpoint 3 : domain name with 65 characters is not ok");
        
        // check number of domain names : 127 -> ok, 128 -> ok, 129 -> fail
        basename = "";
        for (int i=1; i<127; i++) {
            basename = basename + i + ".com,";
        }
        basename = basename + "127.com";
        testname = basename;
        System.out.println(testname);
        checkpoint = page.setPage1VPNGroupInfo("TestGroupDomain", "TestDescription", "1400", true, testname);
        assertTrue(checkpoint,"checkpoint 4 : 127 domain names is ok");
        page.clickBack();
        testname = basename + ",128.com";
        checkpoint = page.setPage1VPNGroupInfo("TestGroupDomain", "TestDescription", "1400", true, testname);
        util.MyCommonAPIs.sleep(60000);
        assertTrue(checkpoint,"checkpoint 5 : 128 domain names is ok");
        page.clickBack();
        testname = basename + ",128.com,129.com";
        checkpoint = page.setPage1VPNGroupInfo("TestGroupDomain", "TestDescription", "1400", true, testname);
        assertFalse(checkpoint,"checkpoint 6 : 129 domain names is not ok");
        
    }
}
