package webportal.Orbi.OrbiTestCases_IM63.AP.PRJCBUGEN_T26651;

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
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiTroubleshootPage;
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.publicstep.UserManage;
import orbi.weboperation.OrbiLoginPage;
import orbi.webelements.DNIOrbiCommanElement;
import orbi.webelements.OrbiAllMenueElements;
import orbi.webelements.OrbiBasicGuestPortalElements;
import orbi.webelements.OrbiBasicWirelessElements;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("Orbi.OrbiTestCases_IM63") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26651") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify changing the security type of the guest type SSID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26651") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Enter device page / Change to ap mode / Change SSID4 authentication to none")
    public void step2() {
        boolean checkpoint;
        ddp.openOB2();
        
        DevicesOrbiDeviceModePage page1 = new DevicesOrbiDeviceModePage();
        if(page1.isRouterMode()) {
            page1.setDeviceMode(true);
            MyCommonAPIs.sleepsyncorbi();
        }
        ddp.gotoPage();
        ddp.openOB2();
        
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(3);
        page.authenticationSelect.selectOption(0);
        page.btnSave.click();
        MyCommonAPIs.sleepsyncorbi();
        
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
    @Step("Test Step 3: Go to device local GUI / Check authentication of guest wifi")
    public void step3() {
        boolean checkpoint;
        OrbiLoginPage orbiLoginPage = new OrbiLoginPage("admin", WebportalParam.loginPassword, WebportalParam.ob1IPaddress);
        OrbiAllMenueElements BrAllMenueElements = new OrbiAllMenueElements();
        OrbiBasicWirelessElements BasicWirelessElements = new OrbiBasicWirelessElements();
        OrbiBasicGuestPortalElements BasicGuestPortalElements = new OrbiBasicGuestPortalElements();
        
        util.MyCommonAPIs.waitReady();
        BrAllMenueElements.Wireless.click();
        util.MyCommonAPIs.waitReady();
        Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
        
        BrAllMenueElements.GuestPortal.click();
        util.MyCommonAPIs.waitReady();
        checkpoint = BasicGuestPortalElements.none.isSelected();
        assertTrue(checkpoint,"checkpoint 1 : None is selected");
    }
    
    @Step("Test Step 4: Login to WP / Go to Location 1 / Go to Devices dash page")
    public void step4() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 5: Enter device page / Change SSID4 authentication to password")
    public void step5() {
        boolean checkpoint;
        ddp.openOB2();
        
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(3);
        page.authenticationSelect.selectOption(1);
        page.SSIDPwd.setValue("PRJCBUGEN-T26651");
        page.btnSave.click();
        MyCommonAPIs.sleepsyncorbi();
        
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
    @Step("Test Step 6: Go to device local GUI / Check SSID4 has password set")
    public void step6() {
        boolean checkpoint;
        OrbiAllMenueElements BrAllMenueElements = new OrbiAllMenueElements();
        OrbiBasicGuestPortalElements BasicGuestPortalElements = new OrbiBasicGuestPortalElements();
        OrbiLoginPage orbiLoginPage = new OrbiLoginPage("admin", WebportalParam.loginPassword, WebportalParam.ob1IPaddress);
        
        //util.MyCommonAPIs.waitReady();
        BrAllMenueElements.Wireless.click();
        util.MyCommonAPIs.waitReady();
        Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
        
        BrAllMenueElements.GuestPortal.click();
        util.MyCommonAPIs.waitReady();
        checkpoint = BasicGuestPortalElements.authenticationauth.isSelected();
        assertTrue(checkpoint,"checkpoint 2 : password authentication is selected");
        
        checkpoint = BasicGuestPortalElements.authenticationpassword.getValue().equals("PRJCBUGEN-T26651");
        assertTrue(checkpoint,"checkpoint 3 : password is correct");
    }
    
}