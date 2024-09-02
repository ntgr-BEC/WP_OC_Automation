package webportal.Onboard_recursive.PRJCBUGEN_T14172;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
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
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Prathik
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("OnboardingAndRemovingDevice") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T00010") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Onboard AP to pro account location1 and remove it and again onboard same AP to location2") // It's // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T00010") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }

    @Step("Test Step 2: Add and remove device from location1 and location2")
    public void step2() {

        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("Device Name", WebportalParam.ap1deveiceName);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);

        for (int i = 1; i <= 100; i++) {

            new OrganizationPage(false).openOrg(WebportalParam.Organizations);
            new MyCommonAPIs().gotoLoction(WebportalParam.location1);
            new DevicesDashPage().addNewDevice(devInfo);
            assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo), "Device is not in connected state");
            System.out.println("Device is added in location 1");
            assertTrue(new WirelessQuickViewPage().deleteDeviceYesVerfiy(WebportalParam.ap1serialNo), "Device is not deleted from location1");
            System.out.println("Device is removed from location 1");

            new OrganizationPage(false).openOrg(WebportalParam.Organizations);
            new MyCommonAPIs().gotoLoction(WebportalParam.location2);
            new DevicesDashPage().addNewDevice(devInfo);
            assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo), "Device is not in connected state");
            System.out.println("Device is added in location 2");
            assertTrue(new WirelessQuickViewPage().deleteDeviceYesVerfiy(WebportalParam.ap1serialNo), "Device is not deleted from location2");
            System.out.println("Device is removed from location 2");

            System.out.println(i + " Iteration is completed");
        }

    }
}
