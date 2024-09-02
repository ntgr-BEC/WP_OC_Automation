package webportal.Orbi.SSID_WAN.Banners.PRJCBUGEN_T26873;

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
import util.SwitchCLIUtils;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.webelements.DevicesDashPageElements;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.RoutersBusinessVPNPage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.Banners") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26873") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Check Webportal displays Marketing banners related to both Business VPN and Content filtering only when user on-boards Supported devices") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26873") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 3 where unsupported devices are located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction(WebportalParam.location3);
    }

    @Step("Test Step 2: Go to Devices page for each device / Check the banners should not exists")
    public void step2() {
        ddp.gotoPage();
        boolean checkpoint;
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            if (!(sModel.equals("SXR80") || sModel.equals("SRR60") || sModel.equals("SXR30") || sModel.equals("SXR50"))) {
                new DevicesDashPage().enterDevicesSwitchSummary(sNo, 3);
                DevicesOrbiSummaryPage page = new DevicesOrbiSummaryPage();
                checkpoint = page.VPNbanner.exists();
                assertFalse(checkpoint,"checkpoint 1-1 : Business VPN banner does not exist");
                checkpoint = page.CFbanner.exists();
                assertFalse(checkpoint,"checkpoint 1-2 : CF banner does not exist");
            }
            
        }
    }
}