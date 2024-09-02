package webportal.Orbi.SXK30.PRJCBUGEN_T25028;

import static com.codeborne.selenide.Selenide.$;
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
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiTroubleshootPage;
import webportal.weboperation.DevicesOrbiWifiNetworkPage;



/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SXK30") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25028") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("changing device name") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25028") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
        ddp.editDeviceName(WebportalParam.ob2serialNo, WebportalParam.ob2deveiceName);
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 / Go to Devices dash page")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 2: Edit device name and check if it is applied / Check wifi1 should be enabled by default and cannot disable it")
    public void step2() {
        boolean checkpoint;
        ddp.editDeviceName(WebportalParam.ob2serialNo, WebportalParam.ob2deveiceName + "-new");
        MyCommonAPIs.sleepsyncorbi();
        ddp.gotoPage();
        String new_name = ddp.getDeviceName(WebportalParam.ob2serialNo);
        checkpoint = new_name.equals(WebportalParam.ob2deveiceName + "-new");
        assertTrue(checkpoint, "checkpoint 1 : device name is modified");
    }
    
}