package webportal.ICAM_LandPageEnh.Phase_1PROEnhancements.LocationLandingPage.Premium_Route_Redirect.PRJCBUGEN_T46317;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Premium_Route_Redirect") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T46317") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that on clicking “Offline devices”, it should route to location device table with filter option selected")
    @TmsLink("PRJCBUGEN_T46317") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ap5serialNo);
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        new DevicesDashPage().deleteDeviceYes(WebportalParam.sw2serialNo);
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        new DevicesDashPage().deleteDeviceYes(WebportalParam.pr2serialNo);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }
    
    @Step("Test Step 2: onboard dummy devices")
    public void step2() {

        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap5serialNo);
        devInfo.put("Device Name", WebportalParam.ap5deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap5macaddress);
        new DevicesDashPage().addNewDevice(devInfo);

        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo1 = new HashMap<String, String>();
        devInfo1.put("Serial Number", WebportalParam.sw2serialNo);
        devInfo1.put("Device Name", WebportalParam.sw2deveiceName);
        devInfo1.put("MAC Address", WebportalParam.sw2MacAddress);
        new DevicesDashPage().addNewDevice(devInfo1);

        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo2 = new HashMap<String, String>();
        devInfo2.put("Serial Number", WebportalParam.ob2serialNo);
        devInfo2.put("Device Name", WebportalParam.ob2deveiceName);
        devInfo2.put("MAC Address", WebportalParam.ob2MAC_Address);
        new DevicesDashPage().addNewDevice(devInfo2);
        
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo3 = new HashMap<String, String>();
        devInfo3.put("Serial Number", WebportalParam.pr2serialNo);
        devInfo3.put("Device Name", WebportalParam.pr2deveiceName);
        devInfo3.put("MAC Address", WebportalParam.pr2macaddress);
        new DevicesDashPage().addNewDevice(devInfo3);

    }

    @Step("Test Step 3: Check whether connected connect is shown in Connected Client header tab count;")
    public void step3() {

        new AccountPage();
        MyCommonAPIs.sleepi(10);
        String offlinedevices = new OrganizationPage(false).locDashOfflineDevice.shouldBe(Condition.visible).getText();
        System.out.println("Offline devices count on Location dashboard page : " + offlinedevices);
        assertTrue(offlinedevices.trim().equals("4"), "Offline devices count is not shown in Connected Client header tab count");
        assertTrue(new OrganizationPage(false).verifyOfflineDevicesCountCliableAndredirecttodevicesPageVerifyActiveFilter(),
                "on devices dashboard page active filter and offline filter is not visisble");
    }

}
