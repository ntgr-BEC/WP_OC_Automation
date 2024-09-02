package webportal.Orbi.IM68_WPA3.PRJCBUGEN_T30985;

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
    String radiusip = "10.10.1.25";
    String radiussecretkey = "12345678";
    
    @Feature("Orbi.IM68_WPA3") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T30985") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify WPA3-Enterprise with client connectivity in router mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T30985") // It's a testcase id/link from Jira Test Case.
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

    
    @Step("Test Step 2: Login to WP / Go to Location 1 / Go to Devices dash page")
    public void step2() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 3: Enter device page / Change SSID1 security to wpa3 enterprise")
    public void step3() {
        boolean checkpoint;
        ddp.openOB2();
        
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(0);
        page.setSsidSecurity(6, "");
        page.setRadiusServer(radiusip, "1812", radiussecretkey);
        page.btnSave.click();
        MyCommonAPIs.sleepi(180);
        
        UserManage userManage = new UserManage();
        userManage.logout();
        
    }
    
    @Step("Test Step 4: Go to device local GUI / Check wifi1 security")
    public void step4() {
        String sGet = new BRUtils(BRUtils.api_device_wlan_info, 1).getField("encryptionMode");
        assertTrue(sGet.contains("WPA3-Enterprise"), "check enterprise3 is set");
        sGet = new BRUtils(BRUtils.api_device_wlan_info, 1).getField("radiusIpAddress");
        assertTrue(sGet.contains(radiusip), "check radius ip");
        sGet = new BRUtils(BRUtils.api_device_wlan_info, 1).getField("radiusPort");
        assertTrue(sGet.contains("1812"), "check radius port");
        sGet = new BRUtils(BRUtils.api_device_wlan_info, 1).getField("radiusSecret");
        assertTrue(sGet.contains(radiussecretkey), "check radius port");
        
//        boolean checkpoint;
//        OrbiLoginPage orbiLoginPage = new OrbiLoginPage("admin", WebportalParam.loginPassword, WebportalParam.ob2IPaddress);
//        OrbiAllMenueElements BrAllMenueElements = new OrbiAllMenueElements();
//        OrbiBasicWirelessElements BasicWirelessElements = new OrbiBasicWirelessElements();
//        
//        util.MyCommonAPIs.waitReady();
//        BrAllMenueElements.Wireless.click();
//        util.MyCommonAPIs.waitReady();
//        Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
//        
//        BasicWirelessElements.wireless1.click();
//        util.MyCommonAPIs.waitReady();
//        checkpoint = BasicWirelessElements.wireless1wpa3enterprise.isSelected();
//        assertTrue(checkpoint,"checkpoint 1 : WPA3 Enterprise is selected");
    }
    
}