package webportal.SrilankaSupportEUSKU.PRJCBUGEN_T37927;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("SrilankaSupportEUSKU") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T37927") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add WAX620 EU SKU in Srilanka Location") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T37927") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp37927");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success and add WAX620 AP;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().AddNewDevice(WebportalParam.ap2serialNo,WebportalParam.ap2macaddress);
        MyCommonAPIs.sleepi(10);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);
        MyCommonAPIs.sleepi(10);
    }

    @Step("Test Step 2: Edit Wifi ssid and let client connect it;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp37927");
        locationInfo.put("Security", "Open");
        locationInfo.put("Band", "ALL");
        new WirelessQuickViewPage().addSsidALL(locationInfo);
    }
}
