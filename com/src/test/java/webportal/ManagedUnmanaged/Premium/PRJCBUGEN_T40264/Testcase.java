package webportal.ManagedUnmanaged.Premium.PRJCBUGEN_T40264;

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

    int beforeInsightDevices = 0;

    @Feature("ManagedUnmanaged") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40264") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[Premium]   Test to verify the user can disable the managed toggle button, and then the device credit moves to the available state, and the user can onboard the new device.")
    @TmsLink("PRJCBUGEN_T40264") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2:Verify Managed Unmanaged switch on device dash page is working fine;")
    public void step2() {
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        beforeInsightDevices = new HamburgerMenuPage().verifyDeviceCredits();
        MyCommonAPIs.sleepi(5);
        assertTrue(new DevicesDashPage().disablemanagedSwitch(WebportalParam.ap5serialNo));
        MyCommonAPIs.sleepi(5);
        int afterInsightDevices = new HamburgerMenuPage().verifyDeviceCredits();
        int diff = beforeInsightDevices-afterInsightDevices;
        int disabledevicec = 1;
        assertTrue((diff==disabledevicec), "After doing unmanged device device credits was not changed accordingly");        
    }

    @Step("Test Step 3:Verify available credits when dummy Ap is onboarded;")
    public void step3() {
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap9serialNo);
        devInfo.put("Device Name", WebportalParam.ap9deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap9macaddress);
        new DevicesDashPage().addNewDevice(devInfo);

        MyCommonAPIs.sleepi(5);
        int afterenableInsightDevices = new HamburgerMenuPage().verifyDeviceCredits();
        System.out.println(afterenableInsightDevices);
        int result = afterenableInsightDevices-beforeInsightDevices;
        System.out.println(result);
        int comp = 0;
        assertTrue((result==comp), "After enabling unmanged switch device credits was not changed accordingly");
        new DevicesDashPage().enablemanagedUnmanagedSwitch(WebportalParam.ap5serialNo);

    }
}
