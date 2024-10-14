package webportal.ManagedUnmanaged.Premium.PRJCBUGEN_T40267;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {
    int temp;
    int temp1;
    @Feature("ManagedUnmanaged") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40267") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[Premium]  Test to verify if the user has 5 devices in the account with managed mode then the user disabled the managed mode, and all credit moved to the available state.") // It's a testcase title from Jira Test
                                                                                                          // Case.
    @TmsLink("PRJCBUGEN_T40267") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new DevicesDashPage(false).openLocationFromotherOrg();
        MyCommonAPIs.sleepi(10);
        new DevicesDashPage().deleteDevice1(WebportalParam.ap9serialNo);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2:Verify All device credits on subscription page showing correctly after all devices are in unmanaged state;")
    public void step2() {
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap9serialNo);
        devInfo.put("Device Name", WebportalParam.ap9deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap9macaddress);
        new DevicesDashPage().addNewDevice(devInfo);
        MyCommonAPIs.sleepi(5);
        int beforeUnmanaged = new HamburgerMenuPage().verifyDeviceCredits();
        
        assertTrue(new DevicesDashPage().disablemanagedSwitch(WebportalParam.ap5serialNo));
        assertTrue(new DevicesDashPage().disablemanagedSwitch(WebportalParam.ap6serialNo));
        assertTrue(new DevicesDashPage().disablemanagedSwitch(WebportalParam.ap7serialNo));
        assertTrue(new DevicesDashPage().disablemanagedSwitch(WebportalParam.ap8serialNo));
        assertTrue(new DevicesDashPage().disablemanagedSwitch(WebportalParam.ap9serialNo));
        MyCommonAPIs.sleepi(5);
        int AfterUnmanaged = new HamburgerMenuPage().verifyDeviceCredits();
        
        int result = beforeUnmanaged-AfterUnmanaged;
        int totaldevices = 5;
        assertTrue((result==totaldevices), "All device credits on subscription page not showing correctly after all devices are in unmanaged state");
        new DevicesDashPage().enablemanagedUnmanagedSwitch(WebportalParam.ap5serialNo);
        new DevicesDashPage().enablemanagedUnmanagedSwitch(WebportalParam.ap6serialNo);
        new DevicesDashPage().enablemanagedUnmanagedSwitch(WebportalParam.ap7serialNo);
        new DevicesDashPage().enablemanagedUnmanagedSwitch(WebportalParam.ap8serialNo);
        new DevicesDashPage().enablemanagedUnmanagedSwitch(WebportalParam.ap9serialNo);
    }

}
