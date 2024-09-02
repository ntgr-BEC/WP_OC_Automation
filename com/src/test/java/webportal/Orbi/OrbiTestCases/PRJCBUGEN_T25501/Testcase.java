package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25501;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiTroubleshootPage;
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.publicstep.UserManage;
import orbi.weboperation.OrbiLoginPage;
import orbi.webelements.DNIOrbiCommanElement;
import orbi.webelements.OrbiAllMenueElements;
import orbi.webelements.OrbiBasicWirelessElements;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("OrbiTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25501") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the device details (MAC, IP) on the device list screen") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25501") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 / Go to Devices dash page")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 2: Check device name / ip address / mac address")
    public void step2() {
        String sGet = ddp.getDeviceName(WebportalParam.ob2serialNo);
        assertTrue(sGet.equals(WebportalParam.ob2deveiceName), "checkpoint 1 : device name is correct");
        sGet = ddp.getDeviceMacInfo(WebportalParam.ob2serialNo);
        assertTrue(sGet.contains(":") || sGet.contains("-"), "checkpoint 2 : device has mac address");
        sGet = ddp.getDeviceIP(WebportalParam.ob2serialNo);
        assertTrue(sGet.contains("."), "checkpoint 3 : device has ip address");
    }
    
}