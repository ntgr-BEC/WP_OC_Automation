package webportal.DeviceOnBoarding.BR.PRJCBUGEN_T14035;

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
import util.BRUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String              OneLocation = "OnBoardingTest";
    Map<String, String> devInfo     = new HashMap<String, String>();

    @Feature("DeviceOnBoarding.BR") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14035") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("On-boarding BR500 device via serial number") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14035") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        devInfo.put("Serial Number", WebportalParam.br1serialNo);
        devInfo.put("Device Name", WebportalParam.br1deveiceName);
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation(OneLocation);
        MyCommonAPIs.waitReady();
        new AccountPage().enterLocation(WebportalParam.location1);
        MyCommonAPIs.waitReady();
        if (!handle.pageSource().contains(devInfo.get("Serial Number"))) {
            new DevicesDashPage().addNewDevice(devInfo);
            MyCommonAPIs.waitReady();
            new DevicesDashPage().waitDevicesReConnected(devInfo.get("Serial Number"));
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddp.gotoPage();

        // remove sw1
        if (handle.pageSource().contains(devInfo.get("Serial Number"))) {
            ddp.deleteDeviceYes(devInfo.get("Serial Number"));
        }
    }

    @Step("Test Step 2: Create new location and add device in this location;")
    public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", OneLocation);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "123456");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();
    }

    @Step("Test Step 3: Verify device status after add switch")
    public void step3() {
        new AccountPage().enterLocation(OneLocation);
        MyCommonAPIs.waitReady();
        new DevicesDashPage().addNewDevice(devInfo);
        MyCommonAPIs.waitReady();
        assertTrue(new DevicesDashPage().waitDevicesReConnected(devInfo.get("Serial Number")),
                "Device cannot online: " + devInfo.get("Serial Number"));
    }

    @Step("Test Step 4: Rename the device name and verify device name updated successfully.")
    public void step4() {
        String newName = "OnBoadringSwitch";
        new DevicesDashPage().editDeviceName(devInfo.get("Serial Number"), newName);

        handle.waitRestReady(BRUtils.api_device_name, newName, false, 0);
        String devicename = new BRUtils().getField("name");
        assertTrue(devicename.contains(newName), "check device name on device side:" + devicename);
    }

}
