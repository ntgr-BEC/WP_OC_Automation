package webportal.Orbi.SSID_WAN.SSID_WANTestcases.VPNGroup.PRJCBUGEN_T24662;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.param.URLParam;

/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.SSID_WANTestcases.VPNGroup") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify user able to configure Employee SSID in wireless setting as part of creating VPN GROUP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24662") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.DelVPNGroup("TestGroup");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Go to Routers page / User add a VPN group")
    public void step2() {
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true, "SSID-WAN-Auto-Test", 1, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl(WebportalParam.client2name, WebportalParam.client2wifimac, 0, WebportalParam.ob1deveiceName);
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 5000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); 
    }
    
    
    @Step("Test Step 3: Go to wireless page and check user cannot edit wireless 2")
    public void step3() {
        boolean checkpoint;
        WirelessQuickViewPage page = new WirelessQuickViewPage(true);
        page.gotoOrbiWirelessSetting();
        checkpoint = page.editOrbiSsid("SSID-WAN-Auto-Test").exists();
        assertFalse(checkpoint, "checkpoint 1 : edit icon does not exist");
        SelenideElement devicename = $x("//p[@title='SSID-WAN-Auto-Test']/../../td[2]");
        devicename.doubleClick();
        util.MyCommonAPIs.waitReady();
        String currentUrl = util.MyCommonAPIs.getCurrentUrl();
        checkpoint = currentUrl.contains(URLParam.hrefWirelessOrbiSettingsEdit);
        assertFalse(checkpoint,"checkpoint 2 : do not redirect to edit wifi page");
    }
    
}
