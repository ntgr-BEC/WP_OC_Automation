package webportal.AdvanceAPFunctionally.pro_admin.PRJCBUGEN_T14660;

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

    @Feature("AdvanceAPFunctionally.pro_admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14660") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify Schedule instant wifi as mild functionally") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14660") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().selectInstantWifiSchedule("None");
        new WirelessQuickViewPage().deleteSsidYes("apwp14660");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin");
    }

    @Step("Test Step 2: Select mild option, then check ap log;")
    public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp14660");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(locationInfo);

        new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
        if (new WirelessQuickViewPage().selectInstantWifiSchedule("Mild")) {
            int count = 0;
            while (true) {
                MyCommonAPIs.sleepsync();
                if (new APUtils(WebportalParam.ap1IPaddress).checkInstantWifiProfile(false)) {
                    assertTrue(true);
                    break;
                } else if (count == 10) {
                    assertTrue(false, "Instant wifi logs not output.");
                    break;
                }
                count += 1;
            }
        } else {
            assertTrue(false, "Instant wifi not optimize.");
        }
    }

}
