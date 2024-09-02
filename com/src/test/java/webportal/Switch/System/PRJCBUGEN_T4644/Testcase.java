package webportal.Switch.System.PRJCBUGEN_T4644;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.NetworkEditNetworkPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {
    @Issue("PRJCBUGEN-11490")
    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4644") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007-Set max length Device Name and Location") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4644") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: check location name maxlength")
    public void step2() {
        AccountPage accountPage = new AccountPage();
        NetworkEditNetworkPage networkEditNetworkPage = accountPage.enterEditNetworkPage();
        // networkEditNetworkPage.locationName.setValue("a" + Strings.repeat("12345678901234567", 15));
        // handle.clickButton(0);
        // assertEquals(handle.getPageErrorMsg().contains("can have only 255"), "check string length 255");
        String number = networkEditNetworkPage.locationName.attr("maxlength");
        assertEquals(number, "50");
    }

    public void step3() {
        handle.gotoLoction();
        DevicesDashPage devicesDashPage = new DevicesDashPage();
        devicesDashPage.editDeviceName(WebportalParam.sw1serialNo, NAME1);
        MyCommonAPIs.sleep(60 * 1000);
        Selenide.refresh();
        String deviceName = devicesDashPage.getDeviceName(WebportalParam.sw1serialNo);
        if (deviceName.equals(WebportalParam.sw1deveiceName) && !deviceName.equals(NAME1)) {
            micResult = true;
        }
        assertTrue(micResult);
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        handle.refresh();
        DevicesDashPage devicesDashPage = new DevicesDashPage();
        devicesDashPage.editDeviceName(WebportalParam.sw1serialNo, WebportalParam.sw1deveiceName);
        MyCommonAPIs.sleepi(30);
    }
}
