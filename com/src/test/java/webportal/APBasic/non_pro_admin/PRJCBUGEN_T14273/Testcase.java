package webportal.APBasic.non_pro_admin.PRJCBUGEN_T14273;

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
import webportal.weboperation.DevicesApIpSettingsPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("APBasic.non_pro_admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14273") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify dhcp client functionally as non-pro admin") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14273") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        Map<String, String> ipInfo = new HashMap<String, String>();
        ipInfo.put("IP Address", WebportalParam.ap1IPaddress);
        new DevicesApIpSettingsPage().setIp(ipInfo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
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

    @Step("Test Step 2: Disable Assign IP Address Automatically,check device is online and address;")
    public void step2() {
        String ip = WebportalParam.ap1IPaddress.substring(0, WebportalParam.ap1IPaddress.lastIndexOf(".")) + ".160";
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);

        Map<String, String> ipInfo = new HashMap<String, String>();
        ipInfo.put("IP Address", ip);
        new DevicesApIpSettingsPage().setIp(ipInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

        boolean result = false;
        int i = 0;

        while (i < 10) {
            if (new APUtils(ipInfo.get("IP Address")).getIpStatus().contains(ip)) {
                result = true;
                break;
            }
            MyCommonAPIs.sleepsync();
            i++;
        }

        if (result) {
            assertTrue(new WirelessQuickViewPage().checkApIsOnline(WebportalParam.ap1serialNo)
                    && new WirelessQuickViewPage().checkApIpCorrect(WebportalParam.ap1serialNo, ip));
        } else {
            assertTrue(false, "Changed ip address failed.");
        }
    }

    @Step("Test Step 3: Change IP address to previous one as DHCP ip address,check device is online and address;")
    public void step3() {
        MyCommonAPIs.sleepsync();
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);

        Map<String, String> ipInfo = new HashMap<String, String>();
        ipInfo.put("IP Address", WebportalParam.ap1IPaddress);
        new DevicesApIpSettingsPage().setIp(ipInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

        boolean result = false;
        int i = 0;

        while (i < 10) {
            if (new APUtils(ipInfo.get("IP Address")).getIpStatus().contains(WebportalParam.ap1IPaddress)) {
                result = true;
                break;
            }
            MyCommonAPIs.sleepsync();
            i++;
        }

        if (result) {
            assertTrue(new WirelessQuickViewPage().checkApIsOnline(WebportalParam.ap1serialNo)
                    && new WirelessQuickViewPage().checkApIpCorrect(WebportalParam.ap1serialNo, WebportalParam.ap1IPaddress));
        } else {
            assertTrue(false, "Changed ip address failed.");
        }
    }

    @Step("Test Step 4: Enable Assign IP Address Automatically,check device is online;")
    public void step4() {
        MyCommonAPIs.sleepsync();
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new DevicesApIpSettingsPage().choiceDhcp();

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

        boolean result = false;
        int i = 0;

        while (i < 10) {
            if (!new APUtils(new DevicesDashPage().getDeviceIP(WebportalParam.ap1serialNo)).getIpStatus().equals("")) {
                result = true;
                break;
            }
            MyCommonAPIs.sleepsync();
            i++;
        }

        if (result) {
            assertTrue(new WirelessQuickViewPage().checkApIsOnline(WebportalParam.ap1serialNo));
        } else {
            assertTrue(false, "Enable assign IP address automatically failed.");
        }
    }

}
