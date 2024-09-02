package webportal.DeviceOnBoarding.AP.pro_admin.PRJCBUGEN_T14055;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("DeviceOnBoarding.AP.pro_admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14055") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("On-boarding of AP device WAC 510 via serial number") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14055") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("OnBoardingTest");

//        new AccountPage().enterLocation(WebportalParam.location1);
//        if (WebportalParam.getApXmlConfig("WAC510", "Device Name").equals(WebportalParam.ap1deveiceName)) {
//            Map<String, String> devInfo = new HashMap<String, String>();
//            devInfo.put("Serial Number", WebportalParam.getApXmlConfig("WAC510", "Serial Number"));
//            devInfo.put("Device Name", WebportalParam.getApXmlConfig("WAC510", "Device Name"));
//            new DevicesDashPage(false).addNewDevice(devInfo);
//
//            new DevicesDashPage().waitDevicesReConnected(devInfo.get("Serial Number"));
//        }

        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2: Create new location and add device in this location;")
    public void step2() {
        if (!new DevicesDashPage().getDeviceName(WebportalParam.getApXmlConfig("WAC510", "Serial Number")).equals("")) {
            new DevicesDashPage().deleteDeviceYes(WebportalParam.getApXmlConfig("WAC510", "Serial Number"));
        }
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);

        new AccountPage().enterLocation("OnBoardingTest");
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.getApXmlConfig("WAC510", "Serial Number"));
        devInfo.put("Device Name", WebportalParam.getApXmlConfig("WAC510", "Device Name"));

        new DevicesDashPage(false).addNewDevice(devInfo);
    }

    @Step("Test Step 3: Verify device status;")
    public void step3() {
        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.getApXmlConfig("WAC510", "Serial Number")), "Device cannot online.");
    }

    @Step("Test Step 4: Rename the device name and verify device name updated successfully.")
    public void step4() {
        String DeviceName = "OnBoadring510";
        Selenide.refresh();
        new DevicesDashPage().editDeviceName(WebportalParam.getApXmlConfig("WAC510", "Serial Number"), DeviceName);
        assertTrue(new APUtils(WebportalParam.getApXmlConfig("WAC510", "Address")).getDeviceName().contains(DeviceName),
                "Device name updated unsuccessfully.");
    }

}
