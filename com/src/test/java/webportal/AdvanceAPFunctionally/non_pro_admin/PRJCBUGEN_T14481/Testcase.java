package webportal.AdvanceAPFunctionally.non_pro_admin.PRJCBUGEN_T14481;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("AdvanceAPFunctionally.non_pro_admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14481") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify user is able to on boarding AP on different countries as non pro admin") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14481") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Wireless Region", "China");
        new AccountPage().editLocation(WebportalParam.location1, locationInfo);
        new WirelessQuickViewPage().deleteSsidYes("apwp14481");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Select wireless region as \"United States\",then AP should be connected and all configure should push and client connected it;")
    public void step2() {
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);

        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Wireless Region", "United States of America");
        new AccountPage().editLocation(WebportalParam.location1, locationInfo);

        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp14481");
        ssidInfo.put("Security", "Open");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("Device Name", WebportalParam.ap1deveiceName);
        new DevicesDashPage().addNewDevice(devInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14481")
                    .indexOf("true") != -1) {
                break;
            } else if (sum > 30) {
                assertTrue(false, "Client cannot connected.");
                break;
            }
            sum += 1;
        }

        boolean result1 = true;
        if (!new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect apwp14481")
                .equals("true")) {
            result1 = false;
            if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect apwp14481")
                    .equals("true")) {
                result1 = true;
            }
        }
        
        assertTrue(result1, "Client cannot connected.");

        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getRegion().indexOf("840") != -1, "Region changed unsuccessful");
    }

    @Step("Test Step 3: Select wireless region as \"Albania\",then AP should be connected and all configure should push and client connected it")
    public void step3() {
        boolean all = false; // if want to run all countries,please change all is true
        if (all) {
            String countryRegion = new APUtils(WebportalParam.ap1IPaddress).getRegion();
            for (int i = 2; i < 107; i++) {
                new AccountPage().selectWirelessRegionByIndex(WebportalParam.location1, i);

                MyCommonAPIs.sleepi(10);
                new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

                int sum = 0;
                while (true) {
                    MyCommonAPIs.sleepi(10);
                    if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                            "WAFfindSSID apwp14481").indexOf("true") != -1) {
                        break;
                    } else if (sum > 30) {
                        assertTrue(false, "Client cannot connected.");
                        break;
                    }
                    sum += 1;
                }

                boolean result1 = true;
                if (!new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect apwp14481")
                        .equals("true")) {
                    result1 = false;
                    if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect apwp14481")
                            .equals("true")) {
                        result1 = true;
                    }
                }

                assertTrue(result1, "Client cannot connected.");

                String nowRegoin = new APUtils(WebportalParam.ap1IPaddress).getRegion();
                assertTrue(nowRegoin.indexOf(countryRegion) == -1, "Region changed unsuccessfully.");
                countryRegion = nowRegoin;
            }
        } else {
            Map<String, String> locationInfo = new HashMap<String, String>();
            locationInfo.put("Wireless Region", "Albania");
            new AccountPage().editLocation(WebportalParam.location1, locationInfo);

            MyCommonAPIs.sleepi(10);
            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

            int sum = 0;
            while (true) {
                MyCommonAPIs.sleepi(10);
                if (new Javasocket()
                        .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14481")
                        .indexOf("true") != -1) {
                    break;
                } else if (sum > 30) {
                    assertTrue(false, "Client cannot connected.");
                    break;
                }
                sum += 1;
            }

            boolean result1 = true;
            if (!new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect apwp14481")
                    .equals("true")) {
                result1 = false;
                if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect apwp14481")
                        .equals("true")) {
                    result1 = true;
                }
            }

            assertTrue(result1, "Client cannot connected.");

            assertTrue(new APUtils(WebportalParam.ap1IPaddress).getRegion().indexOf("8") != -1, "Region changed unsuccessfully.");
        }
    }

}
