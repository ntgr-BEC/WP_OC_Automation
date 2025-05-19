package webportal.ICAM_LandPageEnh.Phase_1PROEnhancements.ProOrganizationLandingPage.RouteRedirecting.PRJCBUGEN_T46146;

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
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("RouteRedirecting") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T46146") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Accessibility for Counts and Table Navigation.") // It's // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T46146") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).openOrg("Netgear");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ap5serialNo);
        new OrganizationPage(false).openOrg("Netgear");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        new DevicesDashPage().deleteDeviceYes(WebportalParam.sw2serialNo);
        new OrganizationPage(false).openOrg("Netgear");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }
    
    @Step("Test Step 2: Verify On Organization dashboard Total devices ")
    public void step2() {

        MyCommonAPIs.sleepi(5);
        assertTrue(new OrganizationPage(false).verifydevicesOnOrgDashboard(WebportalParam.ap1serialNo, WebportalParam.sw1serialNo,
                WebportalParam.ob1serialNo, "Total Devices"), "After Clicking all devices there device list not shown");

    }
    
    @Step("Test Step 3: Verify On Organization dashboard online devices ")
    public void step3() {

        new OrganizationPage(false).gotoPage();
        MyCommonAPIs.sleepi(5);
        assertTrue(new OrganizationPage(false).verifydevicesOnOrgDashboard(WebportalParam.ap1serialNo, WebportalParam.sw1serialNo,
                WebportalParam.ob1serialNo, "Online Devices"), "After Clicking all Online devices there device list not shown");

    }

    @Step("Test Step 4: Verify On Organization dashboard online devices ")
    public void step4() {
        
        new OrganizationPage(false).openOrg("Netgear");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap5serialNo);
        devInfo.put("Device Name", WebportalParam.ap5deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap5macaddress);
        new DevicesDashPage().addNewDevice(devInfo);

        new OrganizationPage(false).openOrg("Netgear");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo1 = new HashMap<String, String>();
        devInfo1.put("Serial Number", WebportalParam.sw2serialNo);
        devInfo1.put("Device Name", WebportalParam.sw2deveiceName);
        devInfo1.put("MAC Address", WebportalParam.sw2MacAddress);
        new DevicesDashPage().addNewDevice(devInfo1);

        new OrganizationPage(false).openOrg("Netgear");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo2 = new HashMap<String, String>();
        devInfo2.put("Serial Number", WebportalParam.ob2serialNo);
        devInfo2.put("Device Name", WebportalParam.ob2deveiceName);
        devInfo2.put("MAC Address", WebportalParam.ob2MAC_Address);
        new DevicesDashPage().addNewDevice(devInfo2);
        
        new OrganizationPage(false).gotoPage();
        MyCommonAPIs.sleepi(5);
        assertTrue(new OrganizationPage(false).verifyOfflinedevicesOnOrgDashboard(WebportalParam.ap5serialNo, WebportalParam.sw2serialNo,
                WebportalParam.ob2serialNo, "Offline Devices"), "After Clicking all offline devices there device list not shown");

    }

}
