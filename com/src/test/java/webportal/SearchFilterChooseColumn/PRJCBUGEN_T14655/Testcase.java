package webportal.SearchFilterChooseColumn.PRJCBUGEN_T14655;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.MyDevicesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("SearchFilterChooseColumn") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14655") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Search/ Filter/ Choose column functionality on the devices section") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14655") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2: Check devices filter on My Devices page;")
    public void step2() {
        if (new DevicesDashPage().getDeviceName(WebportalParam.ap1serialNo).equals("")
                && new DevicesDashPage().getDeviceName(WebportalParam.sw1serialNo).equals("")) {
            assertTrue(false, "Need add one ap and one switch.");
        }

        String[] devices1 = {
                "switch"
        };
        String[] devices3 = {
                "switch,Access Point"
        };

        boolean result = true;

        new MyDevicesPage().filterSelectDevice(devices1);

        if (new MyDevicesPage(false).checkFilterFeature(WebportalParam.sw1serialNo)) {   
            System.out.println("1st if");
            if (new MyDevicesPage(false).checkFilterFeature(WebportalParam.sw1serialNo)
                    && !new MyDevicesPage(false).checkFilterFeature(WebportalParam.ap1serialNo)) {
                System.out.println("2st if");
                new MyDevicesPage().filterSelectDevice(devices3);
                if (new MyDevicesPage(false).checkFilterFeature(WebportalParam.sw1serialNo)
                        && new MyDevicesPage(false).checkFilterFeature(WebportalParam.ap1serialNo)) {
                    System.out.println("3st if");
                } else {
                    result = false;
                }
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        assertTrue(result, "Devices filter is incorrect.");
    }

    @Step("Test Step 3: Check devices filter on Devices page;")
    public void step3() {
        handle.gotoLoction();

        String[] devices1 = {
                "switch"
        };
        String[] devices3 = {
                 "Access Point"
        };

        boolean result = true;

        new DevicesDashPage().filterSelectDevice(devices1);

        if (!new DevicesDashPage(false).getDeviceName(WebportalParam.sw1serialNo).equals("")
                && new DevicesDashPage(false).getDeviceName(WebportalParam.ap1serialNo).equals("")) {
            System.out.println("1st if");
            if (!new DevicesDashPage(false).getDeviceName(WebportalParam.sw1serialNo).equals("")
                    && new DevicesDashPage(false).getDeviceName(WebportalParam.ap1serialNo).equals("")) {
                System.out.println("2st if");
                new DevicesDashPage().filterSelectDevice(devices3);
                if (!new DevicesDashPage(false).getDeviceName(WebportalParam.sw1serialNo).equals("")
                        && !new DevicesDashPage(false).getDeviceName(WebportalParam.ap1serialNo).equals("")) {
                    System.out.println("3st if");
                } else {
                    result = false;
                }
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        assertTrue(result, "Devices filter is incorrect.");
    }

    @Step("Test Step 4: Check search devices on devices page;")
    public void step4() {
        new DevicesDashPage().searchDevices(WebportalParam.ap1serialNo);

        boolean result = true;
        System.out.println("before");
        if (new DevicesDashPage(false).getDeviceName(WebportalParam.sw1serialNo).equals("")
                && !new DevicesDashPage(false).getDeviceName(WebportalParam.ap1serialNo).equals("")) {
            System.out.println("1st if");
            new DevicesDashPage().searchDevices(WebportalParam.sw1serialNo);
            if (!new DevicesDashPage(false).getDeviceName(WebportalParam.sw1serialNo).equals("")
                    && new DevicesDashPage(false).getDeviceName(WebportalParam.ap1serialNo).equals("")) {
                System.out.println("2st if");
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        assertTrue(result, "Search devices is incorrect.");
    }

}
