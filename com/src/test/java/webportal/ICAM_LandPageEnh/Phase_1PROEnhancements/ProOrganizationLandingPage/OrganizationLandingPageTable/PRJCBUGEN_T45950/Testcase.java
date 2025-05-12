package webportal.ICAM_LandPageEnh.Phase_1PROEnhancements.ProOrganizationLandingPage.OrganizationLandingPageTable.PRJCBUGEN_T45950;

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
import static com.codeborne.selenide.Selenide.*;
import org.testng.annotations.Test;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("OrganizationLandingPageTable") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T45950") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify No Data Scenarios.") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T45950") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);  
        new OrganizationPage().deleteOrganizationNew("Netgear");
           
    }
    
    @Step("Test Step 2: verify organization dashboard when there is no any organization present")
    public void step2() {
        
     // Step 1: Assert no child elements inside ag-center-cols-container
        assertTrue($$(".ag-center-cols-container > *").isEmpty(),
                "Organization table is not empty as expected.");

        // Step 2: Assert that the 'no organizations' message is visible and correct
        assertTrue(
                $("p.IconColVpn").isDisplayed() &&
                $("p.IconColVpn").getText().trim().equals("You do not have any organizations in your account."),
                "Expected no-organization message is not displayed or incorrect.");
        
    }
    
    @Step("Test Step 3: Create new Organization and location onboard dummy devices")
    public void step3() {

        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", "Netgear");
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.creditAllocation("Netgear");
        MyCommonAPIs.sleepi(5);

        new OrganizationPage(false).openOrg("Netgear");
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "70184");
        locationInfo.put("Country", "Germany");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.waitReady();

        new OrganizationPage(false).openOrg("Netgear");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("Device Name", WebportalParam.ap1deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap1macaddress);
        new DevicesDashPage().addNewDevice(devInfo);

        new OrganizationPage(false).openOrg("Netgear");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo1 = new HashMap<String, String>();
        devInfo1.put("Serial Number", WebportalParam.sw1serialNo);
        devInfo1.put("Device Name", WebportalParam.sw1deveiceName);
        devInfo1.put("MAC Address", WebportalParam.sw1MacAddress);
        new DevicesDashPage().addNewDevice(devInfo1);

        new OrganizationPage(false).openOrg("Netgear");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo2 = new HashMap<String, String>();
        devInfo2.put("Serial Number", WebportalParam.ob1serialNo);
        devInfo2.put("Device Name", WebportalParam.ob1deveiceName);
        devInfo2.put("MAC Address", WebportalParam.ob1deveiceMac);
        new DevicesDashPage().addNewDevice(devInfo2);
        
        new OrganizationPage(false).openOrg("Netgear");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.sw1serialNo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob1serialNo);

    }
 
}
