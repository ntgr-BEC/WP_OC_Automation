package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25500;

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
    @Story("PRJCBUGEN_T25500") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the commands/configurations on orbi should not be lost on reboot or a power outage") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25500") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Enter device page / Change SSID2 ssid name")
    public void step2() {
        ddp.openOB2();
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(1);
        page.setOptEnabled(true, true);
        page.setSsidName("PRJCBUGEN_T25500");
        page.setSsidSecurity(2, "87654321");
        page.btnSave.click();
        MyCommonAPIs.sleepsyncorbi();
    }
    
    @Step("Test Step 3: Reboot the device.")
    public void step3() {
        handle.gotoLoction();
        ddp.gotoPage();
        ddp.rebootDevice(WebportalParam.ob2serialNo);
        ddp.waitDevicesReConnected(WebportalParam.ob2serialNo);
    }
    
    @Step("Test Step 4: Check device config")
    public void step4() {
        boolean checkpoint;
        ddp.openOB2();
        
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(1);
        checkpoint = page.inputSSIDName.getValue().equals("PRJCBUGEN_T25500");
        assertTrue(checkpoint,"checkpoint 1 : SSID name is correct");
        checkpoint = page.SSIDPwd.getValue().equals("87654321");
        assertTrue(checkpoint,"checkpoint 2 : password is correct");
    }
    
}