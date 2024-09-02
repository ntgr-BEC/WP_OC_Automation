package webportal.Orbi.APRouterFunctionality.APFunctionality.PRJCBUGEN_T7501;

import static com.codeborne.selenide.Selenide.$x;
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
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiWanIPPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("APRouterFunctionality.APFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7501") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify able to access the WAN IP option.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7501") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();

        handle.gotoLoction();

        ddp.gotoPage();
    }

    @Step("Test Step 2: Choose online device, click \"Edit\" button")
    public void step2() {
        ddp.openOBDevice();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(true);
    }

    @Step("Test Step 3: Should be able to access the WAN IP option when we in AP mode.")
    public void step3() {
        boolean checkpoint = false;
        DevicesOrbiWanIPPage page = new DevicesOrbiWanIPPage(false);
        ddp.gotoPage();
        ddp.openOBDevice();
        SelenideElement wanipbtn     = $x("//a[text()='WAN IP']/..");
        wanipbtn.click();
        if(!page.txtIp.isDisplayed()) {
            checkpoint = true;
        }
        assertTrue(checkpoint, "After click WAN IP button, it will not show elements on WAN IP page");
    }
}
