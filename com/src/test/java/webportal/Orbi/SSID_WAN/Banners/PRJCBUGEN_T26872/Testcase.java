package webportal.Orbi.SSID_WAN.Banners.PRJCBUGEN_T26872;

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
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesDashPage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.Banners") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26872") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether Webportal displays Marketing banners related to both Business VPN and Content filtering in location summary page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26872") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located / reonboard OB1")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob1serialNo);
        util.MyCommonAPIs.sleep(120 * 1000);
        new DevicesDashPage().addNewDevice(WebportalParam.ob1serialNo, WebportalParam.ob1deveiceName, WebportalParam.ob1deveiceMac);
        util.MyCommonAPIs.sleep(180 * 1000);
    }

    @Step("Test Step 2: Go to Devices page / Check the banners")
    public void step2() {
        boolean checkpoint;
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.ob1serialNo, 3);
        DevicesOrbiSummaryPage page = new DevicesOrbiSummaryPage();
        checkpoint = page.VPNbanner.exists();
        assertTrue(checkpoint,"checkpoint 1-1 : Business VPN banner exists");
        checkpoint = page.CFbanner.exists();
        if(checkpoint) {
            assertTrue(checkpoint,"checkpoint 1-2 : CF banner exists");
        }
    }
    
    @Step("Test Step 3: Go to Location Summary page / Check the banners")
    public void step3() {
        boolean checkpoint;
        SummaryPage page = new SummaryPage();
        page.gotoPage(WebportalParam.location1);
        checkpoint = page.VPNbanner.exists();
        assertTrue(checkpoint,"checkpoint 2-1 : Business VPN banner exists");
        checkpoint = page.CFbanner.exists();
        if(checkpoint) {
            assertTrue(checkpoint,"checkpoint 2-2 : CF banner exists");
        }
    }
    
    @Step("Test Step 4: Go to Loation 2 where the remote router is located / reonboard OB2")
    public void step4() {
        handle.gotoLoction(WebportalParam.location2);
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
        util.MyCommonAPIs.sleep(120 * 1000);
        new DevicesDashPage().addNewDevice(WebportalParam.ob2serialNo, WebportalParam.ob2deveiceName, WebportalParam.ob2MAC_Address);
        util.MyCommonAPIs.sleep(180 * 1000);
    }
    
    @Step("Test Step 5: Go to Devices page / Check the banners")
    public void step5() {
        boolean checkpoint;
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.ob2serialNo, 3);
        DevicesOrbiSummaryPage page = new DevicesOrbiSummaryPage();
        checkpoint = page.VPNbanner.exists();
        assertTrue(checkpoint,"checkpoint 3-1 : Business VPN banner exists");
        checkpoint = page.CFbanner.exists();
        if(checkpoint) {
            assertTrue(checkpoint,"checkpoint 3-2 : CF banner exists");
        }
    }
    
    @Step("Test Step 6: Go to Location Summary page / Check the banners")
    public void step6() {
        boolean checkpoint;
        SummaryPage page = new SummaryPage();
        page.gotoPage(WebportalParam.location2);
        checkpoint = page.VPNbanner.exists();
        assertTrue(checkpoint,"checkpoint 4-1 : Business VPN banner exists");
        checkpoint = page.CFbanner.exists();
        if(checkpoint) {
            assertTrue(checkpoint,"checkpoint 4-2 : CF banner exists");
        }
    }
    
}