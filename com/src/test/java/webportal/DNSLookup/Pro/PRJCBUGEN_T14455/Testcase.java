package webportal.DNSLookup.Pro.PRJCBUGEN_T14455;

import static org.testng.Assert.assertFalse;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.NetworkTroubleshootPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author xuchen
 *
 */
public class Testcase extends TestCaseBase {
    String domain = "www.bing.com";
    String tmpStr;
    public static NetworkTroubleshootPage troubleshoot = new NetworkTroubleshootPage(false);
    

    @Feature("DNSLookup") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14455") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that user should not be able to select offline devices at the time of running DNS lookup") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14455") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Insight discover and manage one ap")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);


        handle.gotoLoction();
        ddp.gotoPage();
        ddp.rebootDevice(WebportalParam.ap1serialNo);
        MyCommonAPIs.sleepi(10);
        new NetworkTroubleshootPage();
    }

    @Step("Test Step 2: Insight go to troubleshoot to run test;")
    public void step2() {
        SelenideElement Device = troubleshoot.selectOneDevice(WebportalParam.ap1serialNo);
        Device.click();
        SelenideElement input = troubleshoot.selectOneinput(WebportalParam.ap1serialNo);
        System.out.println(input.isEnabled());
        assertFalse(input.isEnabled(), "should not be able to select offline devices.");

    }
}
