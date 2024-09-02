package webportal.DNSLookup.Pro.PRJCBUGEN_T21792;

import static org.testng.Assert.assertFalse;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.NetworkTroubleshootPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {
    String                                domain       = "www.bing.com";
    String                                tmpStr;
    public static NetworkTroubleshootPage troubleshoot = new NetworkTroubleshootPage(false);

    @Feature("DNSLookup") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21792") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify what user type can perform the test") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T21792") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        if (troubleshoot.dnslookupclose.isDisplayed()) {
            troubleshoot.dnslookupclose.click();
        }

        UserManage userManage = new UserManage();
        userManage.logout();
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        if (new DevicesDashPage().getDeviceName(WebportalParam.ap1serialNo).equals("")) {
            new DevicesDashPage().checkDeviceInAdminAccount();
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Insight discover and manage one ap")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin");
    }

    @Step("Test Step 2: Check DNS lookup test option;")
    public void step2() {
        new NetworkTroubleshootPage();
        tmpStr = troubleshoot.runAllTest(domain);
        assertFalse(tmpStr.contains("error"), "Admin account dns lookup result is: " + tmpStr);
        assertFalse(tmpStr.contains("fail"), "Admin account dns lookup result is: " + tmpStr);

        UserManage userManage = new UserManage();
        userManage.logout();
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);

        handle.gotoLoction();
        new NetworkTroubleshootPage();
        tmpStr = troubleshoot.runAllTest(domain);
        assertFalse(tmpStr.contains("error"), "Manager account dns lookup result is: " + tmpStr);
        assertFalse(tmpStr.contains("fail"), "Manager account dns lookup result is: " + tmpStr);
    }

}
