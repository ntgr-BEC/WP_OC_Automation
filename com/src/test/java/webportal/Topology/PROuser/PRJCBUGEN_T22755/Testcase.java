package webportal.Topology.PROuser.PRJCBUGEN_T22755;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.util.SystemOutLogger;
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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Topology") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T22755") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("line tool tip in topology") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T22755") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp14270");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);

        handle.gotoLoction();
    }

    
    @Step("Test Step 2: Add WIFI ssid and now connect client to this ssid;")
    public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp22755");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo);

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp22755")
                    .indexOf("true") != -1) {
                break;
            } else if (sum > 30) {
                assertTrue(false, "Client cannot connected.");
                break;
            }
            sum += 1;
        }

        boolean result1 = true;
        if (!new Javasocket()
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp22755 123456798 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp22755 123456798 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
    }

    @Step("Test Step 3: check  line;")
    public void step3() {
        
        int upTimeBefore = new WirelessQuickViewPage().getApUptimetopology(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage(false).GoToTopology();
        assertTrue(new WirelessQuickViewPage(false).linetooltip(WebportalParam.ap1serialNo), "Usage and speed did not appear");  
        
        
    }


}
