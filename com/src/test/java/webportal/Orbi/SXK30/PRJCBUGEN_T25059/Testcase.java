package webportal.Orbi.SXK30.PRJCBUGEN_T25059;

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
import util.BRUtils;
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
import orbi.webelements.OrbiBasicWirelessElements;
import orbi.webelements.OrbiBasicGuestPortalElements;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SXK30") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25059") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("SSID 4 with open with non default vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25059") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Enter device page / Check 4 SSIDs / Change SSID4 security to None")
    public void step2() {
        boolean checkpoint;
        ddp.openOB2();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
        ddp.gotoPage();
        ddp.openOB2();
        
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        assertTrue(page.getNumberSSID()==4, "checkpoint 1 : total 4 SSIDs on wifi network page");
        page.editSsid(3);
        page.setOptEnabled(true, true);
        page.authenticationSelect.selectOption(0);
        page.setSSIDVLANProfile(true);
        page.setSsidVlanProfile("Guest");
        page.btnSave.click();
        MyCommonAPIs.sleepsyncorbi();
        
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
    @Step("Test Step 3: Go to device local GUI / Check SSID4 security is None")
    public void step3() {
        String sGet = new BRUtils(BRUtils.api_device_guest_portal_wlan_info, 1).getField("authOption");
        assertTrue(sGet.contains("None"), "check authOption is set");
        sGet = new BRUtils(BRUtils.api_device_guest_portal_wlan_info, 1).getField("vlanProfile");
        assertTrue(sGet.contains("40"), "check vlanProfile is set");
//        boolean checkpoint;
//        OrbiLoginPage orbiLoginPage = new OrbiLoginPage("admin", WebportalParam.loginPassword, WebportalParam.ob2IPaddress);
//        OrbiAllMenueElements BrAllMenueElements = new OrbiAllMenueElements();
//        OrbiBasicWirelessElements BasicWirelessElements = new OrbiBasicWirelessElements();
//        OrbiBasicGuestPortalElements BasicGuestPortalElements = new OrbiBasicGuestPortalElements();
//        
//        util.MyCommonAPIs.waitReady();
//        BrAllMenueElements.Wireless.click();
//        util.MyCommonAPIs.waitReady();
//        Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
//        
//        BrAllMenueElements.GuestPortal.click();
//        util.MyCommonAPIs.waitReady();
//        checkpoint = BasicGuestPortalElements.none.isSelected();
//        assertTrue(checkpoint,"checkpoint 2 : None is selected");
//        
//        checkpoint = BasicGuestPortalElements.vlanenable.isSelected();
//        assertTrue(checkpoint,"checkpoint 3 : vlan profile is enabled");
//        
//        checkpoint = BasicGuestPortalElements.vlanid.getValue().equals("4");
//        assertTrue(checkpoint,"checkpoint 4 : vlan profile is Guest");
        
    }
    
}