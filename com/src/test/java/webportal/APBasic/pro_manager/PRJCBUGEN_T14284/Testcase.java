package webportal.APBasic.pro_manager.PRJCBUGEN_T14284;

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
import util.APUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("APBasic.pro_manager") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14284") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify delete ssid functionally for pro manager") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14284") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp14284");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("manager"); // must input admin or manager
    }

    @Step("Test Step 2: Add WIFI ssid and check ssid config successful;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp14284");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        boolean result = false;
        int count = 0;
        while (count < 5) {
            MyCommonAPIs.sleepsync();
            if (new APUtils(WebportalParam.ap1IPaddress).getSsidStatus("apwp14284", WebportalParam.ap1Model).indexOf("vapProfileStatus 1") != -1) {
                result = true;
                break;
            }
            count += 1;
        }
        assertTrue(result, "SSID create failed");
    }

    @Step("Test Step 3: Delete ssid and check ssid delete successful;")
    public void step3() {
        new WirelessQuickViewPage().deleteSsidYes("apwp14284");

        boolean result = false;
        int count = 0;
        while (count < 5) {
            MyCommonAPIs.sleepsync();
            if (new APUtils(WebportalParam.ap1IPaddress).getSsidStatus("apwp14284", WebportalParam.ap1Model).indexOf("vapProfileStatus 0") != -1) {
                result = true;
                break;
            }
            count += 1;
        }
        assertTrue(result, "SSID delete failed");
    }

}
