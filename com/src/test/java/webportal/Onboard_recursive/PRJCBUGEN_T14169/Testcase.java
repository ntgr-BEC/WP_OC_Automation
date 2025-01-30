package webportal.Onboard_recursive.PRJCBUGEN_T14169;

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
import util.MyCommonAPIs;
import util.SwitchTelnet;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 * @author Anusha H
 */
public class Testcase extends TestCaseBase {

    // Map<String, String> firststdevInfo = new HashMap<String, String>();
    // WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
    // UserManage userManage = new UserManage();

    @Feature("SanityTestCase") // It's a folder/component name to make test suite more readable from Jira Test Cas
    @Story("PRJCBUGEN_14169") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Repeatly do onboard from premium to pro and vice-versa") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-14169") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true) // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Repeatly do reboot to check device can be online in cloud")
    public void step1() {

        // firststdevInfo.put("Serial Number", WebportalParam.ap1serialNo);
        // firststdevInfo.put("Mac Address", WebportalParam.ap1macaddress);
        //
        //
        // int Iteration = 0, Fail = 0;
        //
        // while (true) {
        //
        // try {
        //
        // System.out.printf("Status of Device onboard : Fail<%s>/Total<%s>\n", Fail, Iteration);
        //
        // handle.gotoLoction();
        // WebCheck.checkHrefIcon(URLParam.hrefDevices);
        // new DevicesDashPage(false).addNewDevice(firststdevInfo);
        // assertTrue(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
        // assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo),"device connecttion failled");
        //
        //
        // new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        //
        // assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
        //
        // MyCommonAPIs.sleepi(30);
        //
        //
        // userManage.logout();
        //
        //
        // webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        // new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        //
        // new AccountPage().enterLocation("office1");
        // new DevicesDashPage(false).addNewDevice(firststdevInfo);
        // assertTrue(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
        // assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo),"device connecttion failled");
        //
        // new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        // assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
        //
        // MyCommonAPIs.sleepi(30);
        //
        // userManage.logout();
        // MyCommonAPIs.sleepi(30);
        // webportalLoginPage.defaultLogin();
        //
        // Iteration++;
        // }
        // catch (Throwable e) {
        // Fail++;
        // userManage.logout();
        // MyCommonAPIs.sleepi(30);
        // webportalLoginPage.defaultLogin();
        //
        // if(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo)){
        // new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        // }
        //
        // userManage.logout();
        // MyCommonAPIs.sleepi(30);
        // webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        // new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        // new AccountPage().enterLocation("office1");
        //
        //
        // if(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo)){
        // new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        // }
        //
        // }
        // }

        for (int i = 1; i <= 15; i++) {

            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
            new MyCommonAPIs().gotoLoction(WebportalParam.location1);
            Map<String, String> devInfo1 = new HashMap<String, String>();
            devInfo1.put("Serial Number", WebportalParam.ap1serialNo);
            devInfo1.put("Device Name", WebportalParam.ap1deveiceName);
            devInfo1.put("MAC Address", WebportalParam.ap1macaddress);
            new DevicesDashPage().addNewDevice(devInfo1);
            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
            new DevicesDashPage().deleteDeviceNo(WebportalParam.ap1serialNo);

            UserManage userManage = new UserManage();
            userManage.logout();

            webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
            new OrganizationPage(false).openOrg(WebportalParam.Organizations);
            new MyCommonAPIs().gotoLoction(WebportalParam.location1);
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.ap1serialNo);
            devInfo.put("Device Name", WebportalParam.ap1deveiceName);
            devInfo.put("MAC Address", WebportalParam.ap1macaddress);
            new DevicesDashPage().addNewDevice(devInfo);
            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
            new DevicesDashPage().deleteDeviceNo(WebportalParam.ap1serialNo);

            userManage.logout();
            System.out.printf("Status of Device onboard : " + i);
        }

    }
}
