package webportal.DNSLookup.Pro.PRJCBUGEN_T21785;

import static com.codeborne.selenide.Selenide.$$x;
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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
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
    @Story("PRJCBUGEN_T21785") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that a start over button is present using which user can hit command for the selected set of devices and already entered domain by clicking this icon") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T21785") // It's a testcase id/link from Jira Test Case.
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
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Insight discover and manage one ap")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);


        handle.gotoLoction();
        new NetworkTroubleshootPage();
    }

    @Step("Test Step 2: Check DNS lookup test option;")
    public void step2() {
        tmpStr = troubleshoot.runAllTest(domain);
        assertFalse(tmpStr.contains("error"), "dns lookup result is: " + tmpStr);
        assertFalse(tmpStr.contains("fail"), "dns lookup result is: " + tmpStr);

        troubleshoot.selectAllDevice.click();
        String result = "";
        troubleshoot.btnRunTest.click();
        troubleshoot.testNow.click();
        MyCommonAPIs.waitReady();
        MyCommonAPIs.sleep(30, "wait testing result");
        assertTrue($$x("//span[text()='Success']").size() == 2, "DNS look up test failed.");
    }

}
