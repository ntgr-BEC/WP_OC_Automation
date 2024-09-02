package webportal.APBasic.StaticAndDynamicIP.Premium.PRJCBUGEN_T38732;

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
    
    String ipMain= WebportalParam.ap1IPaddress;
    String ipmain1 = "";
    
    @Feature("StaticAndDynamicIP") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T38732") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Change IP to static and then chnage to dynamic and reboot") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T38732") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
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

    @Step("Test Step 2: change DHCP Ip from dynamic IP address to static IP address and verify;")
    public void step2() {
        String ip1= WebportalParam.ap1IPaddress;
        System.out.println(ip1);
        String ip = ip1.substring(0, ip1.lastIndexOf(".")) + ".250";
        System.out.println(WebportalParam.ap1IPaddress);
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);

        Map<String, String> ipInfo = new HashMap<String, String>();
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
    }

    @Step("Test Step 3: Change IP address to DHCP dynamic IP address and reboot AP then again verify DHCP dynamic IP address ;")
    public void step3() {

        System.out.println(ipMain);
        ipmain1 = ipMain;
        MyCommonAPIs.sleepi(30);
        new DevicesApIpSettingsPage().setIp1(ipMain);
        MyCommonAPIs.sleepi(30);
        new DevicesApIpSettingsPage().enableAssignAutomaticallyIPAddrees();
        MyCommonAPIs.sleepi(120);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        MyCommonAPIs.sleepi(30);
        boolean result = false;
        int i = 0;

        while (i < 10) {
            if (new APUtils(ipmain1).getIpStatus1(WebportalParam.ap1Model).contains(ipmain1)) {
                result = true;
                break;
            }
            MyCommonAPIs.sleepsync();
            i++;
        }

        if (result) {
            assertTrue(new WirelessQuickViewPage().checkApIsOnline(WebportalParam.ap1serialNo)
                    && new WirelessQuickViewPage().checkApIpCorrect(WebportalParam.ap1serialNo, ipmain1));
        } else {
            assertTrue(false, "Changed ip address failed.");
        }
    }

    @Step("Test Step 4: Enable Assign IP Address Automatically,check device is online;")
    public void step4() {

        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new DevicesApSummaryPage().clickReboot();
        MyCommonAPIs.sleepi(120);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        MyCommonAPIs.sleepi(30);
        boolean result = false;
        int i = 0;

        while (i < 10) {
            if (new APUtils(ipmain1).getIpStatus1(WebportalParam.ap1Model).contains(ipmain1)) {
                result = true;
                break;
            }
            MyCommonAPIs.sleepsync();
            i++;
        }

        if (result) {
            assertTrue(new WirelessQuickViewPage().checkApIsOnline(WebportalParam.ap1serialNo)
                    && new WirelessQuickViewPage().checkApIpCorrect(WebportalParam.ap1serialNo, ipmain1));
        } else {
            assertTrue(false, "Changed ip address failed.");
        }
        
    }

}
