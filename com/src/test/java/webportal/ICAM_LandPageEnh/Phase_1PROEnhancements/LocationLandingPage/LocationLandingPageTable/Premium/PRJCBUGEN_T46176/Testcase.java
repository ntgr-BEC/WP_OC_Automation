package webportal.ICAM_LandPageEnh.Phase_1PROEnhancements.LocationLandingPage.LocationLandingPageTable.Premium.PRJCBUGEN_T46176;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
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
import static com.codeborne.selenide.Selenide.*;
import org.testng.annotations.Test;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("LocationLandingPageTable") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T46176") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that tooltip with the following: Switches : <Num>, Access Points: <Num> ,PR: <Num>") // It's // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T46176") // It's a testcase id/link from Jira Test Case.

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
    @Step("Test Step 1: Login to premium account success.")
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

    @Step("Test Step 3: Verify Location landing page tooltip points are visible")
    public void step3() {
        
        new AccountPage();
        MyCommonAPIs.sleepi(10);
        assertTrue(new OrganizationPage(false).verifyAllDeviceTypeCounts(), "Location landing page tooltip points are not visible");
        

    }

}
