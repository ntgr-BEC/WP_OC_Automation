package webportal.APBasic.StaticAndDynamicIP.Pro.PRJCBUGEN_T38960;

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
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    
    Map<String, String> ipInfo = new HashMap<String, String>();
    String ipMain= WebportalParam.ap1IPaddress;
    String mainIp = WebportalParam.ap1IPaddress;
    String ip = mainIp.substring(0, mainIp.lastIndexOf(".")) + ".250";
    
    @Feature("StaticAndDynamicIP") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T38960") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("On Insight pro account Change IP to static and reboot AP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T38960") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        Map<String, String> ipInfo1 = new HashMap<String, String>();
        ipInfo1.put("IP Address", WebportalParam.ap1IPaddress);
        new DevicesApIpSettingsPage().setIp(ipInfo1);
        MyCommonAPIs.sleepi(30);
        new DevicesApIpSettingsPage().enableAssignAutomaticallyIPAddrees();
        MyCommonAPIs.sleepi(30);
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);  
    }

    @Step("Test Step 2: go to ap settings and change IP adrees to static and save it then reboot Ap")
    public void step2() {
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
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
        MyCommonAPIs.sleepi(120);

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

}
