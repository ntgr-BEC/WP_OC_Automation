package webportal.InsightProKeyAllocationtoOrg.PRJCBUGEN_T28563;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("InsightProKeyAllocationtoOrg") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28563") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the \"Add purchase confirmation key\" option to allocate LMS key credits directly to Account.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T28563") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2: add license key ;")
    public void step2() {  
        
        String Key = new HamburgerMenuPage(false).readLicenceKeyByTxt("Write");
        System.out.println(Key);  
        String typeofOrg = "Account";
        int value=0;
        new HamburgerMenuPage().AddKeyAndVerify1(Key, typeofOrg, value);
        assertTrue(new HamburgerMenuPage(true).verifyLMS(Key), "Not received verify email.");
    }


}
