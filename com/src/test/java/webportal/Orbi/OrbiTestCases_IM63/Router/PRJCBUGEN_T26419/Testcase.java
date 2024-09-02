package webportal.Orbi.OrbiTestCases_IM63.Router.PRJCBUGEN_T26419;

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
    @Feature("Orbi.OrbiTestCases_IM63") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26419") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify changing the security type of the admin type SSID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26419") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
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

    @Step("Test Step 2: Enter device page / Change SSID1 security to aes")
    public void step2() {
        boolean checkpoint;
        ddp.openOB2();
        
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(0);
        page.setSsidSecurity(1, "password-aes");
        page.btnSave.click();
        MyCommonAPIs.sleepsyncorbi();
        
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
    @Step("Test Step 3: Go to device local GUI / Check wifi1 security")
    public void step3() {
        boolean checkpoint;
        OrbiLoginPage orbiLoginPage = new OrbiLoginPage("admin", WebportalParam.loginPassword, WebportalParam.ob2IPaddress);
        OrbiAllMenueElements BrAllMenueElements = new OrbiAllMenueElements();
        OrbiBasicWirelessElements BasicWirelessElements = new OrbiBasicWirelessElements();
        
        util.MyCommonAPIs.waitReady();
        BrAllMenueElements.Wireless.click();
        util.MyCommonAPIs.waitReady();
        Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
        
        BasicWirelessElements.wireless1.click();
        util.MyCommonAPIs.waitReady();
        checkpoint = BasicWirelessElements.wireless1wpa2aes.isSelected();
        assertTrue(checkpoint,"checkpoint 1 : AES is selected");
    }
    
    @Step("Test Step 4: Login to WP / Go to Location 1 / Go to Devices dash page")
    public void step4() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 5: Enter device page / Change SSID1 security to aes-tkip")
    public void step5() {
        boolean checkpoint;
        ddp.openOB2();
        
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(0);
        page.setSsidSecurity(2, "password-aes-tkip");
        page.btnSave.click();
        MyCommonAPIs.sleepsyncorbi();
        
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
    @Step("Test Step 6: Go to device local GUI / Check wifi1 security")
    public void step6() {
        boolean checkpoint;
        OrbiLoginPage orbiLoginPage = new OrbiLoginPage("admin", WebportalParam.loginPassword, WebportalParam.ob2IPaddress);
        OrbiAllMenueElements BrAllMenueElements = new OrbiAllMenueElements();
        OrbiBasicWirelessElements BasicWirelessElements = new OrbiBasicWirelessElements();
        
        util.MyCommonAPIs.waitReady();
        BrAllMenueElements.Wireless.click();
        util.MyCommonAPIs.waitReady();
        Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
        
        BasicWirelessElements.wireless1.click();
        util.MyCommonAPIs.waitReady();
        checkpoint = BasicWirelessElements.wireless1tkipandaes.isSelected();
        assertTrue(checkpoint,"checkpoint 1 : AES-TKIP is selected");
    }
    
}