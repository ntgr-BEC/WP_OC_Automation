package webportal.AP.InstantWifi.PRJCBUGEN_T18185;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String              WirelessRegion = "Australia";
    String              ssid           = "InstantWifi";
    Map<String, String> locationInfo   = new HashMap<String, String>();
    
    @Feature("AP.InstantWifi") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18185") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify in instant Wifi by click on optimize  now button for Australia with non default channel width in  all radio (cloned)") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18185") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        locationInfo.put("Wireless Region", WirelessRegion);
        runTest(this);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login into IM WP page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        ddp.gotoPage();
    }
    
    @Step("Test Step 2: Set wireless region to Australia")
    public void step2() {
        new AccountPage().editLocation(WebportalParam.location1, locationInfo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
    }
    
    @Step("Test Step 3: added ssid with  name ulf and wpa2 -psk")
    public void step3() {
        wlqvp.addSsidIfNot();
    }

    @Step("Test Step 4:  go to instant wifi page click on optimize now")
    public void step4() {
        APUtils.deleteMessagesAll(ddp.mapDeviceList);
        wlqvp.optimizeInstantWifi(true);
        assertTrue(APUtils.checkInstantWifiProfileAll(ddp.mapDeviceList), "All APs must be optimized");
    }

    @Step("Test Step 5:  go to instant wifi page click on optimize now again")
    public void step5() {
        APUtils.deleteMessagesAll(ddp.mapDeviceList);
        wlqvp.optimizeInstantWifi(true);
        assertTrue(APUtils.checkInstantWifiProfileAll(ddp.mapDeviceList), "All APs must be optimized again");
    }
    
}
