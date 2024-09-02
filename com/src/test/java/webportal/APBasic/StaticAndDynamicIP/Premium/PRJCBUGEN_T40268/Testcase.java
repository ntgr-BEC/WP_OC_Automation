package webportal.APBasic.StaticAndDynamicIP.Premium.PRJCBUGEN_T40268;

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
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesApIpSettingsPage;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ipInfo = new HashMap<String, String>();
    String              ipMain = WebportalParam.ap1IPaddress;
    String              mainIp = WebportalParam.ap1IPaddress;
    String              ip     = mainIp.substring(0, mainIp.lastIndexOf(".")) + ".250";

    @Feature("StaticAndDynamicIP") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40268") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Change IP to static then remove AP from account and onboard again") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40268") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        Map<String, String> ipInfo1 = new HashMap<String, String>();
        ipInfo1.put("IP Address", WebportalParam.ap1IPaddress);
        new DevicesApIpSettingsPage().setIp(ipInfo1);
        MyCommonAPIs.sleepi(120);
        new DevicesApIpSettingsPage().enableAssignAutomaticallyIPAddrees();
        MyCommonAPIs.sleepi(120);
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

    @Step("Test Step 2: go to ap settings and change IP adrees to static and save it then reboot Ap")
    public void step2() {

        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        ipInfo.put("IP Address", ip);
        new DevicesApIpSettingsPage().setIp(ipInfo);
        MyCommonAPIs.sleepi(120);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        boolean result = false;
        int i = 0;

        while (i < 10) {
            if (new APUtils(ipInfo.get("IP Address")).getIpStatus1(WebportalParam.ap1Model).contains(ip)) {
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

        int upTimeBefore = new WirelessQuickViewPage().getApUptime();
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new DevicesApSummaryPage().clickReboot();
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        MyCommonAPIs.sleepi(30);

    }

    @Step("Test Step 3: Now verify staic IP Address;")
    public void step3() {

        boolean result = false;
        int i = 0;

        while (i < 10) {
            if (new APUtils(ipInfo.get("IP Address")).getIpStatus1(WebportalParam.ap1Model).contains(ip)) {
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

    @Step("Test Step 4: Now remove AP from account and verify static IP;")
    public void step4() {

        assertTrue(new WirelessQuickViewPage().deleteDeviceYesVerify(WebportalParam.ap1serialNo), "Device deletyed successfully.");
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("Device Name", WebportalParam.ap1deveiceName);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);

        new DevicesDashPage(false).addNewDevice(devInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        MyCommonAPIs.sleepi(120);
        new RunCommand().enableSSH4APALL(ip);
        boolean result = false;
        int i = 0;

        while (i < 10) {
            if (new APUtils(ipInfo.get("IP Address")).getIpStatus1(WebportalParam.ap1Model).contains(ip)) {
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

}
